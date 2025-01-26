
## 类介绍

### 1. `Document` 类
`Document` 类用于表示一个文档对象，包含文档的存储路径、分块后的文本以及嵌入向量等信息。在 `AdvancedRAG` 和 `ModularRAG` 类中作为主要的数据载体使用。

### 2. `AdvancedRAG` 类
`AdvancedRAG` 类实现了一个基本的 RAG 流程，通过链式调用的方式依次完成文档解析、分块、嵌入、排序、高级筛选和大模型聊天等步骤。

#### 使用示例
```java
public static void main(String[] args) {
    AdvancedRAG advancedRAG = new AdvancedRAG(
            new Document("./202X企业规划.pdf"),
            "简要总结这篇文章");
    try {
        advancedRAG
                // 解析文档
                .parsing()
                // 分块处理
                .chunking()
                // 嵌入处理
                .embedding()
                // 排序处理
                .sorting()
                // 高级筛选
                .advancedFiltering()
                // 大模型聊天
                .LLMChat();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    System.out.println(advancedRAG.getResponse());
}
```

#### 设计思路
- **链式调用**：每个处理步骤的方法都返回当前对象 `this`，方便进行链式调用，使得代码更加简洁和易读。
- **模块化设计**：将 RAG 流程拆分为多个独立的方法，每个方法负责一个特定的任务，提高了代码的可维护性和可扩展性。
- **建造者模式（Builder Pattern）**：通过链式调用的方式逐步构建 RAG 处理流程，类似于建造者模式中的构建步骤。

### 3. `ModularRAG` 类
`ModularRAG` 类在 `AdvancedRAG` 类的基础上，增加了更多的分块策略和筛选条件，以及对生成的回复进行后处理的功能。

#### 使用示例
```java
public static void main(String[] args) {
    ModularRAG modularRAG = new ModularRAG(
            new Document("./202X企业规划.pdf"),
            "简要总结这篇文章");
    try {
        modularRAG
                // 解析文档
                .parsing()
                // 分块处理
                .chunking()
                // 嵌入处理
                .embedding()
                // 排序处理
                .sorting()
                // 高级筛选
                .advancedFiltering()
                // 对筛选后的块重新排序
                .reSortingFilteredChunks()
                // 大模型聊天
                .LLMChat()
                // 后处理
                .postProcessing();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    System.out.println(modularRAG.getResponse());
}
```

#### 设计思路
- **模块化扩展**：在 `AdvancedRAG` 类的基础上，增加了更多的分块策略和筛选条件，以及对生成的回复进行后处理的功能，提高了代码的灵活性和可扩展性。
- **复用性**：复用了 `AdvancedRAG` 类中的部分方法，减少了代码的重复。
- **装饰者模式（Decorator Pattern）**：在 `AdvancedRAG` 类的基础上，通过增加新的方法和功能，对其进行了扩展和装饰。

