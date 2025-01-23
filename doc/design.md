# 面向于企业RAG的设计与实现

## 1. 为算法工程师补齐工程开发相关知识

## 2. 为开发工程师补齐算法、模型相关知识点

---

### 技术栈

#### 1. LLM

##### 1.1 API 和 参数

###### 1.1.1 API

###### 1.1.2 参数

在 ChatGPT 中，温度（Temperature）、Top-P 和 Top-K 是三个重要的参数，它们用于控制模型生成文本的随机性和多样性。下面是每个参数的含义和作用：

1. 温度（Temperature）：温度参数影响模型预测字符的概率分布。较高的温度值会使输出更加随机和多样化，而较低的温度值会使输出更加确定和集中。温度参数的范围通常是 0 到 2，其中 0 表示模型总是选择概率最高的下一个词，而 2 表示模型的选择更加随机。

2. Top-P（Nucleus Sampling）：Top-P 采样是一种解码策略，它根据候选项的可能性得分之和达到阈值 P 来选择候选项的个数。这种方法可以有效地避免选择长尾中的低概率 token 作为候选项。Top-P 值通常设置得较高，例如 0.75 或 0.9，以保持一定的随机性，同时避免过于随机的结果。

3. Top-K：Top-K 采样是另一种解码策略，它选择概率最高的前 K 个 token 作为候选项。这种方法可以在一定程度上保证全局最优，但选择 K 的值是一个挑战，因为它需要平衡探索和利用之间的关系。如果同时使用 Top-K 和 Top-P，Top-P 将在 Top-K 之后起作用。

这些参数可以根据具体的应用场景和需求进行调整，以获得最佳的生成效果。例如，对于需要高度创造性和多样性的任务，可能会选择较高的温度和 Top-P 值；而对于需要准确性和一致性的任务，则可能选择较低的温度值。

详见 [https://www.chatgpt.com](https://www.chatgpt.com) 或者 [https://platform.baichuan-ai.com/docs/api](https://platform.baichuan-ai.com/docs/api)

##### 1.2 微调 和 强化学习

###### 1.2.1 微调

- ptuning & ptuning 路由
- lora & lora 路由

###### 1.2.2 强化学习

###### 1.2.3 LLama Factory

##### 1.3 加速 & 部署

###### 1.3.1 加速

- 数据级别优化

    - 输入压缩：减少输入长度，如提示词裁剪、摘要生成等。
    - 输出组织：优化输出内容的结构，如思维骨架、子问题分解等。

    - PromptCompressor：一个用于生成压缩提示词的工具，以减少大模型的输入长度。这种方法可以帮助减少模型推理时的计算量和内存占用，从而加快推理速度。
      GitHub链接：[https://github.com/example/PromptCompressor](https://github.com/example/PromptCompressor)

- 模型级别优化

    - 结构优化：设计更高效的模型结构，如混合专家网络、稀疏注意力等。
    - 参数优化：模型压缩技术，如量化、稀疏化、知识蒸馏等。

    - SparseGPT：Facebook AI研究院开发的稀疏注意力机制，用于加速大模型推理。通过引入稀疏性，SparseGPT可以在保持模型性能的同时减少计算量。
      GitHub链接：[https://github.com/facebookresearch/SparseGPT](https://github.com/facebookresearch/SparseGPT)
    - LoRA：微软研究院提出的LoRA技术，通过微调少量参数来适应大模型。LoRA允许模型在不重新训练整个模型的情况下适应新的任务或数据，从而节省时间和资源。
      GitHub链接：[https://github.com/microsoft/LoRA](https://github.com/microsoft/LoRA)

- 系统级别优化

    - 推理引擎优化：优化模型推理过程中的关键算子，如注意力计算、线性计算等。
    - 服务系统优化：提高异步请求处理的效率，如内存管理、批处理、调度策略等。

    - vLLM：一个开源的大模型推理加速框架，通过PagedAttention高效地管理attention中缓存的张量。vLLM优化了内存管理和计算调度，以提高大模型推理的效率。
      GitHub链接：[https://github.com/vllm-project/vllm](https://github.com/vllm-project/vllm)
    - FlashAttention：Facebook AI研究院开发的注意力计算优化库，用于加速大模型推理。FlashAttention通过优化注意力计算的算法和实现，减少了计算时间和内存占用。
      GitHub链接：[https://github.com/facebookresearch/flash-attention](https://github.com/facebookresearch/flash-attention)
    - PageAttention：一种优化注意力计算的技术，通过分页管理注意力缓存来提高效率。这种方法可以有效地减少内存占用和提高计算速度。
      GitHub链接：[https://github.com/example/PageAttention](https://github.com/example/PageAttention)（示例链接，请替换为实际链接）

- 硬件加速

    - ALLO：一个基于FPGA的LLM推理加速项目，旨在提升大模型在边缘设备上的推理性能。ALLO通过定制硬件逻辑来加速模型推理，特别适合于资源受限的环境。
      GitHub链接：[https://github.com/example/ALLO](https://github.com/example/ALLO)
    - NVIDIA TensorRT：一个高性能深度学习推理优化器和运行时库，用于加速深度学习模型的推理。TensorRT可以优化模型的计算图，减少计算量，并利用GPU的并行计算能力来加速推理。
      GitHub链接：[https://github.com/NVIDIA/TensorRT](https://github.com/NVIDIA/TensorRT)

[https://arxiv.org/abs/2404.14294](https://arxiv.org/abs/2404.14294)
[https://www.bentoml.com/blog/benchmarking-llm-inference-backends](https://www.bentoml.com/blog/benchmarking-llm-inference-backends)

#### 2. 数据搜索


##### 2.1 数据库分类

- 对象存储数据库

  - 分布式文件系统
  https://www.cnblogs.com/crazymakercircle/p/15408581.html
  - minio
  https://www.minio.org.cn/docs/minio/linux/operations/concepts.html


- K-V 数据库
  - Redis
  - 
##### 2.2 向量数据库

##### 2.3 搜素方法论

#### 3. 微服务

##### 3.1 容器管理

##### 3.2 服务管理

##### 3.3 集群管理和资源调度

#### 4. 其他重要组件

##### 4.1 日志系统

##### 4.2 监控系统

##### 4.3 文档解析

#### 5. 下一代RAG

##### 5.1 Agent workflow

##### 5.2 Multi-Agent 编排

---