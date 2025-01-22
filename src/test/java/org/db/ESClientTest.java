package org.db;



import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.utils.TrustAllCerts;

import java.io.IOException;

public class ESClientTest {

    private final CloseableHttpClient httpClient;
    private final String esUrl;

    /**
     * 构造函数，用于初始化Elasticsearch客户端和基础URL
     * @param hostname ES服务器的主机名或IP地址
     * @param username 用户名
     * @param password 密码
     */
    public ESClientTest(String hostname, String username, String password) {
        TrustAllCerts.disableSSLCertificateChecking(); // 禁用SSL证书检查
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));

        this.httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();

        this.esUrl = "https://" + hostname + ":9200";
    }

    /**
     * 测试与Elasticsearch的连接
     * @return 连接是否成功的布尔值
     */
    public boolean testConnection() {
        try {
            // 发送GET请求到Elasticsearch的健康检查API
            HttpGet request = new HttpGet(esUrl + "/_cat/health");
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                // 检查响应状态码是否为200，表示连接成功
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    System.out.println("连接到Elasticsearch成功！");
                    return true;
                } else {
                    System.out.println("连接到Elasticsearch失败，状态码：" + statusCode);
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            // 创建ES客户端测试实例
            ESClientTest esClientTest = new ESClientTest("124.223.85.176", "elastic", "8hbdbMHjAsx9bfDJFh9U");

            // 执行连接测试
            boolean isConnected = esClientTest.testConnection();

            // 根据需要可以进行其他操作...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
