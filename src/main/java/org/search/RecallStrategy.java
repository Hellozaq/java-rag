package org.search;

import org.entity.Document;
import org.entity.SearchInput;
import org.entity.SearchOutput;
import org.service.db.ESClient;

import java.util.List;

public class RecallStrategy {

    public static void esRecall(SearchInput searchInput,SearchOutput searchOutput){
        ESClient esClient = ESClient.getInstance();
        Document queryDoc = searchInput.getDocument();
        List<Document> chunks =  esClient.searchChunk(queryDoc,1,100);
        searchOutput.setDocuments(chunks);
    }
}
