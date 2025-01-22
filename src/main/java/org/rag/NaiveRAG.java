package org.rag;


import org.chunk.FixedSizeSplitter;
import org.constant.Config;
import org.entity.Document;
import org.json.JSONObject;
import org.parser.FileParserFactory;
import org.parser.WordParser;
import org.service.LLM.OpenAIChatService;
import org.service.embedding.BaichuanEmbeddingService;
import org.service.embedding.EmbeddingService;
import org.utils.DistanceUtils;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NaiveRAG {
    Document document;
    List<Document> chunks;
    String query;
    String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public NaiveRAG() {
    }

    public NaiveRAG(Document document, String query) {
        this.document = document;
        this.query = query;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public NaiveRAG parsering(){
        String filePath = document.getStoragePath();
        String chunkText = FileParserFactory.easyParse(filePath);
        document.setChunkText(chunkText);
        return this;
    }

    public NaiveRAG chunking(){
        FixedSizeSplitter fixedSizeSplitter = new FixedSizeSplitter(512);
        List<String> stringList =  fixedSizeSplitter.split(document.getChunkText());
        chunks = stringList.stream()
                .map(chunkText -> {
                    Document chunkDoc = new Document();
                    chunkDoc.setChunkText(chunkText);
                    return chunkDoc;
                })
                .collect(Collectors.toList());
        return this;
    }

    public NaiveRAG embedding() throws IOException {

        BaichuanEmbeddingService embeddingService = new BaichuanEmbeddingService(Config.API_KEY);
        // 获取查询的嵌入向量
        double[] queryEmbedding = embeddingService.getEmbedding(Config.EMBEDDING_API_URL, query);

        // 为每个文档块生成嵌入向量
        for (Document chunk : chunks) {
            double[] chunkEmbedding = embeddingService.getEmbedding(Config.EMBEDDING_API_URL, chunk.getChunkText());
            chunk.setTextEmb(chunkEmbedding);
        }

        return this;
    }

    public NaiveRAG sorting() throws IOException {

        BaichuanEmbeddingService embeddingService = new BaichuanEmbeddingService(Config.API_KEY);
        // 获取查询的嵌入向量
        double[] queryEmbedding = embeddingService.getEmbedding(Config.EMBEDDING_API_URL, query);
        // 根据嵌入向量与查询的嵌入向量之间的距离对文档块进行排序
        chunks.sort(Comparator.comparingDouble(chunk -> DistanceUtils.squaredErrorDistance(queryEmbedding, chunk.getTextEmb())));
        return this;
    }

    public NaiveRAG LLMChat(){
        // 替换为您的API密钥
        String apiKey = Config.API_KEY;
        // 使用百川Baichuan3-Turbo模型
        String model = Config.LLM_MODEL;
        // API的URL
        String url = Config.LLM_URL;

        OpenAIChatService openAIChatService = new OpenAIChatService(apiKey);

        try {
            // 构建请求参数
            JSONObject params = new JSONObject()
                    .put("model", model)
                    .put("messages", new JSONObject[] {
                            new JSONObject().put("role", "user").put("content", "1+1 = ?")
                    })
                    .put("temperature", 0.3)
                    .put("stream", false);

            // 这里可以替换为您想要询问的问题
            response = openAIChatService.generateText(url, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public static void main(String[] args) {
        NaiveRAG naiveRAG = new NaiveRAG(
                new Document("./202X企业规划.pdf"),
                "简要总结这篇文章");
        try {
            naiveRAG
                    // 解析
                    .parsering()
                    // 分块
                    .chunking()
                    // 向量化
                    .embedding()
                    // 排序
                    .sorting()
                    // 大模型回复
                    .LLMChat();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(naiveRAG.response);
    }

}
