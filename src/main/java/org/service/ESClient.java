package org.service;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    public static void main(String[] args) {
        ESClient esClient = new ESClient("https://124.223.85.176:9200", "elastic", "8hbdbMHjAsx9bfDJFh9U");
        esClient.testConnection();
    }
}