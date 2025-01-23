
### 1. `TextSplitter` 接口

#### 接口描述
`TextSplitter` 接口定义了一个文本分割器的基本行为，任何实现该接口的类都需要提供一个 `split` 方法，用于将输入的文本分割成字符串列表。

#### 方法列表
| 方法名 | 描述 | 参数 | 返回值 |
| ---- | ---- | ---- | ---- |
| `split(String text)` | 将输入的文本分割成字符串列表。 | `text`：需要分割的文本。 | 分割后的字符串列表。 |

### 2. `SemanticBlockSplitter` 类

#### 类描述
`SemanticBlockSplitter` 类实现了 `TextSplitter` 接口，用于将文本按照段落（假设每个段落都是一个语义块）进行分割。

#### 方法列表
| 方法名 | 描述 | 参数 | 返回值 |
| ---- | ---- | ---- | ---- |
| `split(String text)` | 实现 `TextSplitter` 接口的 `split` 方法，将输入文本按段落分割。 | `text`：需要分割的文本。 | 分割后的段落列表。 |

### 3. `RecursiveSplitter` 类

#### 类描述
`RecursiveSplitter` 类实现了 `TextSplitter` 接口，通过递归的方式将文本分割成多个部分。

#### 构造方法
| 方法名 | 描述 | 参数 |
| ---- | ---- | ---- |
| `RecursiveSplitter(int depth)` | 创建一个 `RecursiveSplitter` 实例，指定递归的深度。 | `depth`：递归的深度。 |

#### 方法列表
| 方法名 | 描述 | 参数 | 返回值 |
| ---- | ---- | ---- | ---- |
| `split(String text)` | 实现 `TextSplitter` 接口的 `split` 方法，调用 `recursiveSplit` 方法进行递归分割。 | `text`：需要分割的文本。 | 递归分割后的字符串列表。 |
| `recursiveSplit(String text, int currentDepth)` | 递归地将文本分割成两部分，直到达到指定的递归深度。 | `text`：需要分割的文本；`currentDepth`：当前递归的深度。 | 递归分割后的字符串列表。 |

### 4. `SentenceSplitter` 类

#### 类描述
`SentenceSplitter` 类实现了 `TextSplitter` 接口，用于将文本按照句子进行分割。

#### 方法列表
| 方法名 | 描述 | 参数 | 返回值 |
| ---- | ---- | ---- | ---- |
| `split(String text)` | 实现 `TextSplitter` 接口的 `split` 方法，将输入文本按句子分割。 | `text`：需要分割的文本。 | 分割后的句子列表。 |

### 5. `FixedSizeSplitter` 类

#### 类描述
`FixedSizeSplitter` 类实现了 `TextSplitter` 接口，用于将文本按照固定的大小进行分割。

#### 构造方法
| 方法名 | 描述 | 参数 |
| ---- | ---- | ---- |
| `FixedSizeSplitter(int size)` | 创建一个 `FixedSizeSplitter` 实例，指定分割的固定大小。 | `size`：分割的固定大小。 |

#### 方法列表
| 方法名 | 描述 | 参数 | 返回值 |
| ---- | ---- | ---- | ---- |
| `split(String text)` | 实现 `TextSplitter` 接口的 `split` 方法，将输入文本按固定大小分割。 | `text`：需要分割的文本。 | 分割后的字符串列表。 |