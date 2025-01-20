package org.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.HashMap;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // 忽略 null 值字段
public class Document {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("file_id")
    private String fileId;
    @JsonProperty("kb_id")
    private String kbId;
    @JsonProperty("chunk_id")
    private Integer chunkId;
    @JsonProperty("chunk_size")
    private Integer chunkSize;
    @JsonProperty("chunk_text")
    private String chunkText;
    @JsonProperty("text_emb")
    private float[] textEmb;
    @JsonProperty("clip_emb")
    private float[] clipEmb;
    @JsonProperty("doc_type")
    private String docType;
    @JsonProperty("version")
    private String version;
    @JsonProperty("author")
    private String author;
    @JsonProperty("created_time")
    private Long createdTime;
    @JsonProperty("modified_time")
    private Long modifiedTime;
    @JsonProperty("file_name")
    private String fileName;
    @JsonProperty("storage_path")
    private String storagePath;
    @JsonProperty("_score")
    private Float score;


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

