### `EmbeddingService` 接口

**接口描述**：
`EmbeddingService` 接口定义了获取嵌入向量的方法，用于与不同的嵌入服务进行交互。

**方法列表**：
| 方法名 | 描述 | 参数 | 返回值 | 异常 |
| --- | --- | --- | --- | --- |
| `getEmbedding(String url, String input)` | 获取单个输入文本的嵌入向量 | `url`：嵌入服务的 API URL；`input`：需要获取嵌入向量的输入文本 | `double[]`：表示输入文本的嵌入向量 | `IOException`：如果请求过程中发生 I/O 错误 |
| `getEmbeddings(String url, String[] inputs)` | 获取多个输入文本的嵌入向量 | `url`：嵌入服务的 API URL；`inputs`：需要获取嵌入向量的输入文本数组 | `double[][]`：表示多个输入文本的嵌入向量，每个输入文本对应一个 `double[]` 数组 | `IOException`：如果请求过程中发生 I/O 错误 |

### `JinaEmbeddingService` 类

**类描述**：
`JinaEmbeddingService` 类实现了 `EmbeddingService` 接口，用于与 Jina 嵌入服务进行交互，获取文本的嵌入向量。

**构造方法**：
| 方法名 | 描述 | 参数 |
| --- | --- | --- |
| `JinaEmbeddingService(String apiKey)` | 构造一个 `JinaEmbeddingService` 实例 | `apiKey`：Jina 服务的 API 密钥 |

**方法列表**：
| 方法名 | 描述 | 参数 | 返回值 | 异常 |
| --- | --- | --- | --- | --- |
| `getEmbedding(String url, String input)` | 获取单个输入文本的 Jina 嵌入向量 | `url`：Jina 嵌入服务的 API URL；`input`：需要获取嵌入向量的输入文本 | `double[]`：表示输入文本的嵌入向量 | `IOException`：如果请求过程中发生 I/O 错误 |
| `getEmbeddings(String url, String[] inputs)` | 获取多个输入文本的 Jina 嵌入向量 | `url`：Jina 嵌入服务的 API URL；`inputs`：需要获取嵌入向量的输入文本数组 | `double[][]`：表示多个输入文本的嵌入向量，每个输入文本对应一个 `double[]` 数组 | `IOException`：如果请求过程中发生 I/O 错误 |

### `BaichuanEmbeddingService` 类

**类描述**：
`BaichuanEmbeddingService` 类实现了 `EmbeddingService` 接口，用于与百川嵌入服务进行交互，获取文本的嵌入向量。

**构造方法**：
| 方法名 | 描述 | 参数 |
| --- | --- | --- |
| `BaichuanEmbeddingService(String apiKey)` | 构造一个 `BaichuanEmbeddingService` 实例 | `apiKey`：百川服务的 API 密钥 |

**方法列表**：
| 方法名 | 描述 | 参数 | 返回值 | 异常 |
| --- | --- | --- | --- | --- |
| `getEmbedding(String url, String input)` | 获取单个输入文本的百川嵌入向量 | `url`：百川嵌入服务的 API URL；`input`：需要获取嵌入向量的输入文本 | `double[]`：表示输入文本的嵌入向量 | `IOException`：如果请求过程中发生 I/O 错误 |
| `getEmbeddings(String url, String[] inputs)` | 获取多个输入文本的百川嵌入向量 | `url`：百川嵌入服务的 API URL；`inputs`：需要获取嵌入向量的输入文本数组 | `double[][]`：表示多个输入文本的嵌入向量，每个输入文本对应一个 `double[]` 数组 | `IOException`：如果请求过程中发生 I/O 错误 |

### `JinaEmbeddingRerankService` 类

**类描述**：
`JinaEmbeddingRerankService` 类实现了 `EmbeddingService` 接口，用于与 Jina 多向量嵌入服务进行交互，获取文本的嵌入向量，同时提供了重排序相关的功能。

**构造方法**：
| 方法名 | 描述 | 参数 |
| --- | --- | --- |
| `JinaEmbeddingRerankService(String apiKey)` | 构造一个 `JinaEmbeddingRerankService` 实例 | `apiKey`：Jina 服务的 API 密钥 |

**静态方法**：
| 方法名 | 描述 | 参数 | 返回值 |
| --- | --- | --- | --- |
| `getInstance()` | 获取 `JinaEmbeddingRerankService` 的单例实例 | 无 | `JinaEmbeddingRerankService`：`JinaEmbeddingRerankService` 的单例实例 |

**方法列表**：
| 方法名 | 描述 | 参数 | 返回值 | 异常 |
| --- | --- | --- | --- | --- |
| `getMultiVectorEmbeddingJSONArray(String url, String[] inputs)` | 获取多个输入文本的 Jina 多向量嵌入的 JSON 数组 | `url`：Jina 多向量嵌入服务的 API URL；`inputs`：需要获取嵌入向量的输入文本数组 | `JSONArray`：表示多个输入文本的多向量嵌入的 JSON 数组 | `IOException`：如果请求过程中发生 I/O 错误 |
| `getEmbeddings(String url, String[] inputs)` | 获取多个输入文本的 Jina 多向量嵌入向量 | `url`：Jina 多向量嵌入服务的 API URL；`inputs`：需要获取嵌入向量的输入文本数组 | `double[][]`：表示多个输入文本的嵌入向量，每个输入文本对应一个 `double[]` 数组 | `IOException`：如果请求过程中发生 I/O 错误 |
| `getEmbedding(String url, String input)` | 获取单个输入文本的 Jina 多向量嵌入向量 | `url`：Jina 多向量嵌入服务的 API URL；`input`：需要获取嵌入向量的输入文本 | `double[]`：表示输入文本的嵌入向量 | `IOException`：如果请求过程中发生 I/O 错误 |