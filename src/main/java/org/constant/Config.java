package org.constant;

public class Config {
    // API密钥
    public static final String API_KEY = "sk-fec928b1e67db9d34e092c9599e1ce3a";
    // 使用的模型
    public static final String LLM_MODEL = "Baichuan3-Turbo";
    // API的URL
    public static final String LLM_URL = "https://api.baichuan-ai.com/v1/chat/completions";


    // Redis 连接配置
    public static final String REDIS_HOST = "124.223.85.176";
    public static final int REDIS_PORT = 6379;
    public static final String REDIS_PASSWORD = "123456";

    // Redis 过期时间
    public static int REDIS_EXPIRE_SECONDS = 180;

    // ES 连接配置
    public static final String esUrl = "https://124.223.85.176:9200";
    public static final String esUserName = "elastic";
    public static final String esPassWord = "8hbdbMHjAsx9bfDJFh9U";

    // Jina API 密钥
    public static final String Jina_API_KEY = "jina_852794709b6d4a858b87ef1361ce0112hHKiLuPFe9FkYssD6RcVG9kiJD8D";

}