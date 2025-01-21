package org.service.embedding;

import okhttp3.*;
import org.constant.Config;
import org.json.JSONObject;

import java.io.IOException;

public class BaichuanEmbeddingService implements EmbeddingService{

    private final String apiKey;
    private final OkHttpClient client;

    public BaichuanEmbeddingService(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
    }

    /**
     * 发送请求到指定的Embeddings API并获取响应
     *
     * @param url   API的URL
     * @param input 输入文本
     * @return 嵌入向量
     * @throws IOException 如果请求失败
     */
    public double[] getEmbedding(String url, String input) throws IOException {
        RequestBody body = RequestBody.create(
                new JSONObject()
                        .put("model", "Baichuan-Text-Embedding")
                        .put("input", input)
                        .toString(),
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String responseBody = response.body().string();
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONObject dataObject = jsonObject.getJSONArray("data").getJSONObject(0);
            double[] embedding = new double[dataObject.getJSONArray("embedding").length()];
            for (int i = 0; i < embedding.length; i++) {
                embedding[i] = dataObject.getJSONArray("embedding").getDouble(i);
            }
            return embedding;
        }
    }

    public static void main(String[] args) {
        // 替换为您的API密钥
        String apiKey = Config.API_KEY;
        BaichuanEmbeddingService embeddingService = new BaichuanEmbeddingService(apiKey);

        try {
            // API的URL
            String url = "https://api.baichuan-ai.com/v1/embeddings";
            // 这里可以替换为您想要获取嵌入的文本
            String input = "百川大模型";
            double[] embedding = embeddingService.getEmbedding(url, input);
            // 打印嵌入向量
            for (double value : embedding) {
                System.out.println(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}