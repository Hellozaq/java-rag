<div align="center">
<a href="">
<img src="webapp/resources/biglog.png" alt="ragflow logo">
</a>
</div>

[English](README.md) | [简体中文](README_ch.md)
# JAVA-RAG

### 介绍
RAG (Retrieval-Augmented Generation)项目,pure Java 实现,不依赖JFinal,spring-boot等。提供 RAG pipeline 和 Agent 模式,更便于依托企业级环境进行改造,更利于二次开发
### 快速入门
```java
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
```

### 用法教程

#### 💽 [数据库存储](doc/db.md)
- Redis 多轮对话读写
- MinIO 文件存储
- Elastic Search 搜索引擎
#### 🧠 [LLM 对话](doc/LLM.md)
- OpenAI 聊天接口
- Ollama 聊天接口
- 带有多轮对话的聊天
#### 📚 [文档解析](doc/parser.md)
- Word
- PPT
- PDF
- EXCEL
- PPT
- MarkDow,HTML
#### ✂️ [分块](doc/chunk.md)
- 固定大小
- 句子分割
- 递归分割
- 语义分块
#### 📊 [向量化模型](doc/embedding.md)
- Jina-Cobert
- Baichuan
#### 🔎 [搜索](doc/search.md)
- 召回
- 排序
- 重排序
#### 🎁 [更多 pipeline](doc/pipeline.md)
- Advanced RAG
- Modular RAG
#### 🦾 [Agent]
- MASExample.java
### 项目结构
说明
```shell

├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── org
│   │   │       ├── chunk
│   │   │       ├── constant
│   │   │       ├── entity
│   │   │       ├── parser
│   │   │       ├── rag
│   │   │       ├── search
│   │   │       ├── service
│   │   │       │   ├── LLM
│   │   │       │   ├── db
│   │   │       │   └── embedding
│   │   │       └── utils
│   │   └── resources
│   └── test
│       └── java
│           └── org
│               ├── chat
│               └── db


```

### 🧒 简明安装教程

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

4. 创建相关数据库

```shell
sysctl -w vm.max_map_count=262144
#  创建 docker network
docker network create elastic
#  拉 ES
docker pull docker.elastic.co/elasticsearch/elasticsearch:8.11.4
# 运行 ES
docker run --name es01 --net elastic -p 9200:9200 -it -m 2GB docker.elastic.co/elasticsearch/elasticsearch:8.11.4
# 重置 password and enrollment token
docker exec -it es01 /usr/share/elasticsearch/bin/elasticsearch-reset-password -u elastic
docker exec -it es01 /usr/share/elasticsearch/bin/elasticsearch-create-enrollment-token -s kibana
# 安装minio脚本
mkdir -p ~/minio/data
docker run \ -p 9000:9000 \ -p 9090:9090 \ --name minio \ -v ~/minio/data:/data \ -e "MINIO_ROOT_USER=ROOTNAME" \ -e "MINIO_ROOT_PASSWORD=CHANGEME123" \ quay.io/minio/minio server /data --console-address ":9090"
```
### 🥸 详细安装教程
- 详见 [链接](doc/install.md)

### 功能点

- OpenAI式 LLM/Embedding 接口
- 十分简洁的依赖项管理 , pom.xml(Maven)
- 支持多用户、多知识库 管理
- 搜索策略自由编排:多路召回/粗排/精排/重排
- 文件分块自由编排：固定大小/句子分割/递归分割/语义分块
- 主流文件解析支持 Apache POI
- 主流数据库集成 Elastic Search/Redis/Mysql/MinIO
- 配置灵活度高度定制 Nacos




### 参考

- [llm-apps-java-spring-ai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main)
- [ragflow](https://github.com/infiniflow/ragflow)
- [ollama](https://github.com/ollama/ollama)
- [langchain](https://github.com/langchain-ai/langchain)