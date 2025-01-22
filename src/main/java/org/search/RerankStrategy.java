package org.search;

import org.constant.Config;
import org.entity.SearchInput;
import org.entity.SearchOutput;
import org.entity.Document;
import org.service.embedding.JinaEmbeddingRerankService;
import java.util.Comparator;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RerankStrategy {
    public static void JinaCobertRerank(SearchInput searchInput, SearchOutput searchOutput){
        JinaEmbeddingRerankService service = JinaEmbeddingRerankService.instance;
        try {
            double[] input = service.getEmbedding(Config.Jina_multi_vector, searchInput.getDocument().getChunkText());
            double[][] outputs = service.getEmbeddings(Config.Jina_multi_vector, searchOutput.getDocuments().stream()
                    .map(Document::getChunkText)
                    .toArray(String[]::new));

            // 计算每个输出向量与输入向量之间的归一化平方误差距离
            List<Double> distances = IntStream.range(0, outputs.length)
                    .mapToObj(i -> normalizedSquaredErrorDistance(input, outputs[i]))
                    .collect(Collectors.toList());

            // 将距离与对应的文档一起存储
            List<DocumentWithDistance> documentsWithDistance = IntStream.range(0, searchOutput.getDocuments().size())
                    .mapToObj(i -> new DocumentWithDistance(searchOutput.getDocuments().get(i), distances.get(i)))
                    .collect(Collectors.toList());

            // 根据距离对文档进行排序
            documentsWithDistance.sort(Comparator.comparingDouble(DocumentWithDistance::getDistance));

            // 更新 searchOutput 中的 documents 顺序
            searchOutput.setDocuments(documentsWithDistance.stream()
                    .map(DocumentWithDistance::getDocument)
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static double normalizedSquaredErrorDistance(double[] v1, double[] v2) {
        double sum = IntStream.range(0, v1.length)
                .mapToDouble(i -> Math.pow(v1[i] - v2[i], 2))
                .sum();
        return sum / v1.length; // 归一化
    }

    private static class DocumentWithDistance {
        private Document document;
        private double distance;

        public DocumentWithDistance(Document document, double distance) {
            this.document = document;
            this.distance = distance;
        }

        public Document getDocument() {
            return document;
        }

        public double getDistance() {
            return distance;
        }
    }
}