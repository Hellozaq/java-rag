package org.chat;
import org.entity.Document;
import org.junit.Test;
import org.rag.NaiveRAG;

public class NaiveRAGTest {

    @Test
    public void demoNaiveRAG() {
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
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "error stack trace";
        }
        System.out.println(naiveRAG.getResponse());
    }
}