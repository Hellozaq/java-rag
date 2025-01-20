# java-rag

#### 介绍
RAG (Retrieval-Augmented Generation)项目,pure Java 实现,不依赖JFinal,spring-boot等。十分便于依托于企业级环境二次开发

#### 项目结构
说明
```shell
──package org
    ├── constant
    │   └── Config.java
    ├── entity
    │   ├── Document.java
    │   ├── File.java
    │   ├── KnowledgeBase.java
    │   └── User.java
    ├── parser
    │   ├── ExcelParser.java
    │   ├── FileParser.java
    │   ├── FileParserFactory.java
    │   ├── HTMLParser.java
    │   ├── PDFParser.java
    │   ├── PPTParser.java
    │   ├── PureTextParser.java
    │   └── WordParser.java
    └── service
        ├── ESClient.java
        ├── EmbeddingService.java
        ├── LLMService.java
        ├── Main.java
        ├── MinIOClient.java
        ├── MysqlClient.java
        ├── RedisClient.java
        └── TrustAllCerts.java

```

#### 安装教程

1.  clone 代码
```shell
git clone https://gitee.com/ChinaYiqun/java-rag.git
```    
2. 进入项目目录
```shell
cd java-rag
```
3. 配置 Maven 依赖
```shell
mvn clean install
```


#### 特技

- OpenAI式 LLM/Embedding 接口
- 十分简洁的依赖项管理 , pom.xml(Maven)
- 支持多用户、多知识库 管理
- 搜索策略自由编排:多路召回/粗排/精排/重排
- 文件分块自由编排：固定大小/句子分割/递归分割/语义分块
- 主流文件解析支持 Apache POI
- 主流数据库集成 Elastic Search/Redis/Mysql/MinIO
- 配置灵活度高度定制 Nacos