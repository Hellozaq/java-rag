//package org.db;
//
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClients;
//
//import java.io.IOException;
//
//public class ElasticsearchConnectionTest {
//
//    public static void main(String[] args) {
//        // 创建 RestHighLevelClient 实例
//        RestHighLevelClient client = new RestHighLevelClient(
//                RestClients.create("http://124.223.85.176/")
//                        .setHttpClientConfigCallback(httpClientBuilder ->
//                                httpClientBuilder.setDefaultCredentialsProvider(
//                                        new DefaultCredentialsProvider()
//                                                .add(new AuthScope("124.223.85.176", 443), new UsernamePasswordCredentials("elastic", "8hbdbMHjAsx9bfDJFh9U")))
//                        )
//        );
//
//        try {
//            // 测试连接
//            boolean isConnected = testElasticsearchConnection(client);
//            if (isConnected) {
//                System.out.println("Successfully connected to Elasticsearch.");
//            } else {
//                System.out.println("Failed to connect to Elasticsearch.");
//            }
//        } catch (Exception e) {
//            System.out.println("An error occurred while testing the connection: " + e.getMessage());
//        } finally {
//            // 关闭客户端
//            try {
//                client.close();
//            } catch (IOException e) {
//                System.out.println("Error closing Elasticsearch client: " + e.getMessage());
//            }
//        }
//    }
//
//    private static boolean testElasticsearchConnection(RestHighLevelClient client) throws IOException {
//        try {
//            // 执行一个简单的请求来测试连接
//            client.info(RequestOptions.DEFAULT);
//            return true;
//        } catch (Exception e) {
//            // 如果发生异常，则认为连接失败
//            return false;
//        }
//    }
//}