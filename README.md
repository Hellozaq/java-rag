<div align="center">
<a href="">
<img src="webapp/resources/biglog.png" alt="ragflow logo">
</a>
</div>


[English](README.md) | [简体中文](README_ch.md) 
# JAVA-RAG

### Introduction
RAG (Retrieval - Augmented Generation) project, implemented in pure Java without relying on frameworks like JFinal or spring - boot. It provides the RAG pipeline and Agent pattern, which makes it more convenient to adapt to the enterprise - level environment and more conducive to secondary development.
### Quick Start
```java
    public void demoNaiveRAG() {
        NaiveRAG naiveRAG = new NaiveRAG(
                new Document("./202X Enterprise Plan.pdf"),
                "Briefly summarize this article");
        try {
            naiveRAG
                    // Parsing
                   .parsering()
                    // Chunking
                   .chunking()
                    // Vectorization
                   .embedding()
                    // Sorting
                   .sorting()
                    // LLM response
                   .LLMChat();
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "error stack trace";
        }
        System.out.println(naiveRAG.getResponse());
    }
```

### Usage Tutorial

#### 💽 [Database Storage](doc/db.md)
- Read and write for multi-turn conversations in Redis
- File storage in MinIO
- Search engine with Elastic Search

#### 🧠 [LLM Conversations](doc/LLM.md)
- OpenAI chat interface
- Ollama chat interface
- Chat with multi-turn conversations

#### 📚 [Document Parsing](doc/parser.md)
- Word
- PPT
- PDF
- EXCEL
- PPT
- Markdown, HTML

#### ✂️ [Chunking](doc/chunk.md)
- Fixed size
- Sentence splitting
- Recursive splitting
- Semantic chunking

#### 📊 [Vectorization Models](doc/embedding.md)
- Jina-Cobert
- Baichuan

#### 🔎 [Search](doc/search.md)
- Recall
- Sorting
- Re-ranking

#### 🎁 [more pipeline](doc/pipeline.md)
- Advanced RAG
- Modular RAG
#### 🦾 [Agent]
- MASExample.java
### Project Structure
Explanation
```shell
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── org
│   │   │       ├── chunk
│   │   │       ├── constant
│   │   │       ├── entity
│   │   │       ├── parser
│   │   │       ├── rag
│   │   │       ├── search
│   │   │       ├── service
│   │   │       │   ├── LLM
│   │   │       │   ├── db
│   │   │       │   └── embedding
│   │   │       └── utils
│   │   └── resources
│   └── test
│       └── java
│           └── org
│               ├── chat
│               └── db
```

### 🧒 Concise Installation Tutorial

1. Clone the code
```shell
git clone https://github.com/ChinaYiqun/java-rag.git
```

2. Enter the project directory
```shell
cd java-rag
```

3. Configure Maven dependencies
```shell
mvn clean install
```

4. Create relevant databases
```shell
sysctl -w vm.max_map_count=262144
# Create a docker network
docker network create elastic
# Pull Elasticsearch
docker pull docker.elastic.co/elasticsearch/elasticsearch:8.11.4
# Run Elasticsearch
docker run --name es01 --net elastic -p 9200:9200 -it -m 2GB docker.elastic.co/elasticsearch/elasticsearch:8.11.4
# Reset password and enrollment token
docker exec -it es01 /usr/share/elasticsearch/bin/elasticsearch-reset-password -u elastic
docker exec -it es01 /usr/share/elasticsearch/bin/elasticsearch-create-enrollment-token -s kibana
# Install MinIO script
mkdir -p ~/minio/data
docker run \ -p 9000:9000 \ -p 9090:9090 \ --name minio \ -v ~/minio/data:/data \ -e "MINIO_ROOT_USER=ROOTNAME" \ -e "MINIO_ROOT_PASSWORD=CHANGEME123" \ quay.io/minio/minio server /data --console-address ":9090"
```

### 🥸 Detailed Installation Tutorial
- See [Link](doc/install.md) for details.

### Features

- OpenAI-style LLM/Embedding interfaces
- Very simple dependency management with pom.xml (Maven)
- Support for multi-user and multi-knowledge base management
- Free arrangement of search strategies: multi-channel recall, rough sorting, fine sorting, re-ranking
- Free arrangement of file chunking: fixed size, sentence splitting, recursive splitting, semantic chunking
- Support for mainstream file parsing with Apache POI
- Integration of mainstream databases: Elastic Search, Redis, Mysql, MinIO
- Highly customizable configuration with Nacos

### References

- [llm-apps-java-spring-ai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main)
- [ragflow](https://github.com/infiniflow/ragflow)
- [ollama](https://github.com/ollama/ollama)
- [langchain](https://github.com/langchain-ai/langchain)