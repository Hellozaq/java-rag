package org.entity;

import java.util.Map;
import java.util.HashMap;

public class Document {
    private String userId;
    private String fileId;
    private String kbId;
    private Integer chunkId;
    private Integer chunkSize;
    private String chunkText;
    private float[] textEmb;
    private String docType;
    private String version;
    private String author;
    private Long createdTime;
    private Long modifiedTime;
    private String fileName;
    private String storagePath;
    private Float score;
    private float[] clipEmb;

    // 构造器、getter和setter省略

    public Map<String, Object> toMap() {
        Map<String, Object> docMap = new HashMap<>();
        // 将非null属性添加到map中
        if (userId != null) docMap.put("user_id", userId);
        if (fileId != null) docMap.put("file_id", fileId);
        if (kbId != null) docMap.put("kb_id", kbId);
        if (chunkId != null) docMap.put("chunk_id", chunkId);
        if (chunkSize != null) docMap.put("chunk_size", chunkSize);
        if (chunkText != null) docMap.put("chunk_text", chunkText);
        if (textEmb != null) docMap.put("text_emb", textEmb);
        if (docType != null) docMap.put("doc_type", docType);
        if (version != null) docMap.put("version", version);
        if (author != null) docMap.put("author", author);
        if (createdTime != null) docMap.put("created_time", createdTime);
        if (modifiedTime != null) docMap.put("modified_time", modifiedTime);
        if (fileName != null) docMap.put("file_name", fileName);
        if (storagePath != null) docMap.put("storage_path", storagePath);
        if (score != null) docMap.put("_score", score);
        if (clipEmb != null) docMap.put("clip_emb", clipEmb);
        return docMap;
    }

    public Map<String, Object> metaData() {
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("doc_type", docType);
        metaData.put("version", version);
        metaData.put("author", author);
        metaData.put("created_time", createdTime);
        metaData.put("modified_time", modifiedTime);
        return metaData;
    }
}