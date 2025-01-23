
### RecallStrategy 类接口文档

**类名**：`RecallStrategy`

**包名**：`org.search`

**描述**：该类提供了一个静态方法用于从 Elasticsearch 中召回文档。

**方法列表**：

| 方法名 | 描述 | 参数 | 返回值 |
| --- | --- | --- | --- |
| `esRecall(SearchInput searchInput, SearchOutput searchOutput)` | 从 Elasticsearch 中召回文档并将结果存储在 `SearchOutput` 对象中。 | `searchInput`：搜索输入对象，包含查询文档信息。<br>`searchOutput`：搜索输出对象，用于存储召回的文档结果。 | `void` |

### RerankStrategy 类接口文档

**类名**：`RerankStrategy`

**包名**：`org.search`

**描述**：该类提供了一个静态方法用于对召回的文档进行重排序。

**方法列表**：

| 方法名 | 描述 | 参数 | 返回值 |
| --- | --- | --- | --- |
| `JinaCobertRerank(SearchInput searchInput, SearchOutput searchOutput)` | 使用 Jina Embedding 服务对召回的文档进行重排序，根据文档与输入文档的归一化平方误差距离进行排序。 | `searchInput`：搜索输入对象，包含查询文档信息。<br>`searchOutput`：搜索输出对象，包含召回的文档列表，重排序后更新该列表。 | `void` |

### SortStrategy 类接口文档

**类名**：`SortStrategy`

**包名**：`org.search`

**描述**：该类提供了一个静态方法用于对文档列表按 `score` 字段进行降序排序。

**方法列表**：

| 方法名 | 描述 | 参数 | 返回值 |
| --- | --- | --- | --- |
| `dummySort(SearchInput searchInput, SearchOutput searchOutput)` | 对 `SearchOutput` 中的文档列表按 `score` 字段进行降序排序。 | `searchInput`：搜索输入对象（在此方法中未使用）。<br>`searchOutput`：搜索输出对象，包含文档列表，排序后更新该列表。 | `void` |

### Pipeline 类接口文档

**类名**：`Pipeline`

**包名**：`org.search`

**描述**：该类封装了搜索流程，包括召回、排序和重排序操作。

**属性列表**：

| 属性名 | 类型 | 描述 |
| --- | --- | --- |
| `searchInput` | `SearchInput` | 搜索输入对象，用于存储查询信息。 |
| `searchOutput` | `SearchOutput` | 搜索输出对象，用于存储搜索结果。 |

**方法列表**：

| 方法名 | 描述 | 参数 | 返回值 |
| --- | --- | --- | --- |
| `recall(SearchInput searchInput, SearchOutput searchOutput)` | 调用 `RecallStrategy.esRecall` 方法进行文档召回。 | `searchInput`：搜索输入对象，包含查询文档信息。<br>`searchOutput`：搜索输出对象，用于存储召回的文档结果。 | `void` |
| `recall()` | 调用 `RecallStrategy.esRecall` 方法使用类内的 `searchInput` 和 `searchOutput` 对象进行文档召回。 | 无 | `void` |
| `sort()` | 调用 `SortStrategy.dummySort` 方法对文档列表按 `score` 字段进行降序排序。 | 无 | `void` |
| `rerank(SearchInput searchInput, SearchOutput searchOutput)` | 调用 `RerankStrategy.JinaCobertRerank` 方法对文档进行重排序。 | `searchInput`：搜索输入对象，包含查询文档信息。<br>`searchOutput`：搜索输出对象，包含召回的文档列表，重排序后更新该列表。 | `void` |
| `rerank()` | 调用 `RerankStrategy.JinaCobertRerank` 方法使用类内的 `searchInput` 和 `searchOutput` 对象对文档进行重排序。 | 无 | `void` |
| `getDefaultResult()` | 按顺序执行召回、排序和重排序操作，并返回最终的搜索结果。 | 无 | `SearchOutput` |