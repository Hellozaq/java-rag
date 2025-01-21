package org.service.db;

import com.alibaba.fastjson.JSON;

import okhttp3.*;
import org.constant.Config;
import org.entity.Document;
import org.utils.HttpClientUtil;
import org.utils.SnowflakeIdGenerator;
import java.io.IOException;


public class ESClient {
    private String esUrl;
    private String username;
    private String password;
    private OkHttpClient client;

    public ESClient(String esUrl, String username, String password) {
        this.esUrl = esUrl;
        this.username = username;
        this.password = password;
        this.client = HttpClientUtil.createHttpClient(username, password);
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
        String jsonString = JSON.toJSONString(document);

        // 发送POST请求到Elasticsearch
        RequestBody body = RequestBody.create(jsonString, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(esUrl + "/documents/_create/" + uniqueId)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            // 检查响应状态码是否为201，表示文档已成功添加
            if (response.isSuccessful() && response.code() == 201) {
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



    public static void main(String[] args) {
        ESClient esClient = new ESClient(Config.esUrl, Config.esUserName, Config.esPassWord);
        Document d = new Document();
        d.setUserId("200");
        esClient.addChunk(d);


    }
}