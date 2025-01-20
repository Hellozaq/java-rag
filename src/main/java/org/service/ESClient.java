package org.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.constant.Config;
import org.entity.Document;
import org.utils.SnowflakeIdGenerator;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class ESClient {
    private String esUrl;
    private String username;
    private String password;
    private OkHttpClient client;

    public ESClient(String esUrl, String username, String password) {
        this.esUrl = esUrl;
        this.username = username;
        this.password = password;
        this.client = createHttpClient();
    }

    private OkHttpClient createHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // 如果提供了用户名和密码，则设置基本认证
        if (username != null && password != null) {
            builder.authenticator((route, response) -> {
                String credential = Credentials.basic(username, password);
                return response.request().newBuilder()
                        .header("Authorization", credential)
                        .build();
            });
        }

        // 禁用SSL证书验证
        builder = disableSslVerification(builder);

        return builder.build();
    }

    private OkHttpClient.Builder disableSslVerification(OkHttpClient.Builder clientBuilder) {
        try {
            // 创建一个信任所有证书的TrustManager
            TrustManager[] trustManagers = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            // 初始化SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagers, new SecureRandom());

            // 配置OkHttpClient使用上面创建的SSLContext
            clientBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagers[0]);
            clientBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return clientBuilder;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    public void testConnection() {
        Request request = new Request.Builder()
                .url(esUrl + "/_cat/health")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("连接到Elasticsearch成功！");
            } else {
                System.out.println("连接到Elasticsearch失败，状态码：" + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 向Elasticsearch添加一个新的文档块。
     *
     * @param document 要添加的文档对象
     * @return 是否成功添加
     */
    public boolean addChunk(Document document) {
        // 生成唯一的ID
        String uniqueId = SnowflakeIdGenerator.generateUniqueID(); // 使用Snowflake算法生成唯一ID

        // 将Document对象转换为JSON格式的字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(document);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // 发送POST请求到Elasticsearch
        RequestBody body = RequestBody.create(jsonString, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(esUrl + "/documents/_create/" + uniqueId)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            // 检查响应状态码是否为201，表示文档已成功添加
            if (response.isSuccessful() && response.code() == 201) {
                // 刷新索引
                flushIndex();
                System.out.println("embedding 添加成功！");
                return true;
            } else {
                System.out.println("embedding 添加失败，状态码：" + response.code());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 刷新Elasticsearch索引。
     */
    private void flushIndex() {
        Request request = new Request.Builder()
                .url(esUrl + "/_flush?refresh=true")
                .post(RequestBody.create("", MediaType.get("application/json; charset=utf-8")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("刷新索引失败，状态码：" + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ESClient esClient = new ESClient(Config.esUrl, Config.esUserName, Config.esPassWord);
        esClient.testConnection();
    }
}