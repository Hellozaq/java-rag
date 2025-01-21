package org.service.embedding;

import okhttp3.MediaType;
import org.constant.Config;

import java.io.IOException;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
public class JinaEmbeddingService implements EmbeddingService {

    private final String apiKey;
    private final OkHttpClient client;

    public JinaEmbeddingService(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
    }

    @Override
    public double[] getEmbedding(String url, String input) throws IOException {
        RequestBody body = RequestBody.create(
                new JSONObject()
                        .put("model", "jina-embeddings-v2-base-zh")
                        .put("normalized", true)
                        .put("embedding_type", "float")
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

    public double[][] getEmbeddings(String url, String[] inputs) throws IOException {
        JSONArray inputArray = new JSONArray();
        for (String input : inputs) {
            inputArray.put(input);
        }

        RequestBody body = RequestBody.create(
                new JSONObject()
                        .put("model", "jina-embeddings-v2-base-zh")
                        .put("normalized", true)
                        .put("embedding_type", "float")
                        .put("inputs", inputArray)
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
            JSONArray dataArray = jsonObject.getJSONArray("data");
            double[][] embeddings = new double[dataArray.length()][];

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);
                JSONArray embeddingArray = dataObject.getJSONArray("embedding");
                double[] embedding = new double[embeddingArray.length()];
                for (int j = 0; j < embeddingArray.length(); j++) {
                    embedding[j] = embeddingArray.getDouble(j);
                }
                embeddings[i] = embedding;
            }
            return embeddings;
        }
    }


    public static void main(String[] args) {
        // 替换为您的API密钥
        String apiKey = Config.Jina_API_KEY;
        JinaEmbeddingService embeddingService = new JinaEmbeddingService(apiKey);

        try {
            // API的URL
            String url = "https://api.jina.ai/v1/embeddings";
            // 这里可以替换为您想要获取嵌入的文本
            String input = "您的查询可以是中文";
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