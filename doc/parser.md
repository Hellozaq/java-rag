### `FileParser` 接口

#### 概述
`FileParser` 接口定义了文件解析的基本方法，用于解析文件并返回其文本内容。

#### 方法列表
| 方法名 | 描述 | 参数 | 返回值 | 异常 |
| ---- | ---- | ---- | ---- | ---- |
| `parse(File file)` | 解析给定的文件并返回其文本内容 | `file`：要解析的文件对象 | 解析后的字符串 | `IOException`：如果发生 I/O 错误 |
| `parse(String filePath)` | 根据文件路径解析文件并返回其文本内容 | `filePath`：要解析的文件的路径 | 解析后的字符串 | `IOException`：如果发生 I/O 错误 |

### `WordParser` 类

#### 概述
`WordParser` 类实现了 `FileParser` 接口，用于解析 Word 文件（.doc 和 .docx）并返回其文本内容。

#### 方法列表
| 方法名 | 描述 | 参数 | 返回值 | 异常 |
| ---- | ---- | ---- | ---- | ---- |
| `parse(File file)` | 解析给定的 Word 文件并返回其文本内容 | `file`：要解析的 Word 文件对象 | 解析后的字符串 | `IOException`：如果发生 I/O 错误 |
| `parse(String filePath)` | 根据文件路径解析 Word 文件并返回其文本内容 | `filePath`：要解析的 Word 文件的路径 | 解析后的字符串 | `IOException`：如果发生 I/O 错误 |

### `FileParserFactory` 类

#### 概述
`FileParserFactory` 类是一个工厂类，用于根据文件类型或文件路径获取相应的文件解析器。

#### 方法列表
| 方法名 | 描述 | 参数 | 返回值 | 异常 |
| ---- | ---- | ---- | ---- | ---- |
| `getFileParser(String fileType)` | 根据文件类型获取相应的文件解析器 | `fileType`：文件类型（如 "html", "pdf" 等） | 对应的文件解析器对象 | `IllegalArgumentException`：如果文件类型不支持 |
| `getFileParserByPath(String filePath)` | 根据文件路径获取相应的文件解析器 | `filePath`：文件的路径 | 对应的文件解析器对象 | `IllegalArgumentException`：如果文件路径无效 |
| `easyParse(String filePath)` | 简单解析文件，根据文件路径获取解析器并解析文件 | `filePath`：文件的路径 | 解析后的字符串 | `RuntimeException`：如果发生 I/O 错误 |
| `testParseFile(String filePath)` | 测试解析文件的方法，打印文件内容 | `filePath`：文件的路径 | 无 | `IOException`：如果发生 I/O 错误 |

### `HTMLParser` 类

#### 概述
`HTMLParser` 类实现了 `FileParser` 接口，用于解析 HTML 文件并返回其文本内容，还支持解析 URL 指向的 HTML 页面。

#### 方法列表
| 方法名 | 描述 | 参数 | 返回值 | 异常 |
| ---- | ---- | ---- | ---- | ---- |
| `parse(File file)` | 解析给定的 HTML 文件并返回其文本内容 | `file`：要解析的 HTML 文件对象 | 解析后的字符串 | `IOException`：如果发生 I/O 错误 |
| `parse(String filePath)` | 根据文件路径解析 HTML 文件并返回其文本内容 | `filePath`：要解析的 HTML 文件的路径 | 解析后的字符串 | `IOException`：如果发生 I/O 错误 |
| `parseHTMLFromURL(String url)` | 解析 URL 指向的 HTML 页面并返回其文本内容 | `url`：URL 字符串 | 解析后的字符串 | `IOException`：如果发生 I/O 错误 |

### `PDFParser` 类

#### 概述
`PDFParser` 类实现了 `FileParser` 接口，用于解析 PDF 文件并返回其文本内容。

#### 方法列表
| 方法名 | 描述 | 参数 | 返回值 | 异常 |
| ---- | ---- | ---- | ---- | ---- |
| `parse(File file)` | 解析给定的 PDF 文件并返回其文本内容 | `file`：要解析的 PDF 文件对象 | 解析后的字符串 | `IOException`：如果发生 I/O 错误 |
| `parse(String filePath)` | 根据文件路径解析 PDF 文件并返回其文本内容 | `filePath`：要解析的 PDF 文件的路径 | 解析后的字符串 | `IOException`：如果发生 I/O 错误 |

### `PPTParser` 类

#### 概述
`PPTParser` 类实现了 `FileParser` 接口，用于解析 PowerPoint 文件（.ppt 和 .pptx）并返回其文本内容。

#### 方法列表
| 方法名 | 描述 | 参数 | 返回值 | 异常 |
| ---- | ---- | ---- | ---- | ---- |
| `parse(File file)` | 解析给定的 PowerPoint 文件并返回其文本内容 | `file`：要解析的 PowerPoint 文件对象 | 解析后的字符串 | `IOException`：如果发生 I/O 错误 |
| `parse(String filePath)` | 根据文件路径解析 PowerPoint 文件并返回其文本内容 | `filePath`：要解析的 PowerPoint 文件的路径 | 解析后的字符串 | `IOException`：如果发生 I/O 错误 |

### `ExcelParser` 类

#### 概述
`ExcelParser` 类实现了 `FileParser` 接口，用于解析 Excel 文件（.xls 和 .xlsx）并返回其文本内容。

#### 方法列表
| 方法名 | 描述 | 参数 | 返回值 | 异常 |
| ---- | ---- | ---- | ---- | ---- |
| `parse(File file)` | 解析给定的 Excel 文件并返回其文本内容 | `file`：要解析的 Excel 文件对象 | 解析后的字符串 | `IOException`：如果发生 I/O 错误 |
| `parse(String filePath)` | 根据文件路径解析 Excel 文件并返回其文本内容 | `filePath`：要解析的 Excel 文件的路径 | 解析后的字符串 | `IOException`：如果发生 I/O 错误 |

### `PureTextParser` 类

#### 概述
`PureTextParser` 类实现了 `FileParser` 接口，用于解析纯文本文件（.txt, .md, .py, .java 等）并返回其文本内容。

#### 方法列表
| 方法名 | 描述 | 参数 | 返回值 | 异常 |
| ---- | ---- | ---- | ---- | ---- |
| `parse(File file)` | 解析给定的纯文本文件并返回其文本内容 | `file`：要解析的纯文本文件对象 | 解析后的字符串 | `IOException`：如果发生 I/O 错误 |
| `parse(String filePath)` | 根据文件路径解析纯文本文件并返回其文本内容 | `filePath`：要解析的纯文本文件的路径 | 解析后的字符串 | `IOException`：如果发生 I/O 错误 |