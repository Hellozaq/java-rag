package org.service.embedding;

import java.io.IOException;

public interface EmbeddingService {
    double[] getEmbedding(String url, String input) throws IOException;
}
