
### ESClient 类

#### 类概述
`ESClient` 类用于与 Elasticsearch 进行交互，包括测试连接、添加文档块、搜索文档块等操作。

#### 构造方法
- `public ESClient(String esUrl, String username, String password)`
    - **参数**：
        - `esUrl`：Elasticsearch 的 URL 地址。
        - `username`：Elasticsearch 的用户名。
        - `password`：Elasticsearch 的密码。
    - **描述**：初始化 `ESClient` 实例，创建 `OkHttpClient` 对象用于后续的 HTTP 请求。

#### 静态方法
- `public static ESClient getInstance()`
    - **返回值**：返回 `ESClient` 的单例实例。
    - **描述**：获取 `ESClient` 的单例实例。

#### 实例方法
- `public void testConnection()`
    - **描述**：测试与 Elasticsearch 的连接。如果连接成功，打印“连接到 Elasticsearch 成功！”；如果连接失败，打印失败状态码。
- `public boolean addChunk(Document document)`
    - **参数**：
        - `document`：要添加的文档对象。
    - **返回值**：`boolean` 类型，表示文档是否成功添加。
    - **描述**：向 Elasticsearch 添加一个新的文档块。
- `public List<Document> searchChunk(Document document, float boost, int size)`
    - **参数**：
        - `document`：包含搜索信息的文档对象。
        - `boost`：提升值。
        - `size`：结果大小。
    - **返回值**：`List<Document>` 类型，表示搜索到的文档列表。
    - **描述**：根据给定的文档信息、提升值和结果大小，在 Elasticsearch 中搜索文档块。

### MysqlClient 类

#### 类概述
`MysqlClient` 类用于与 MySQL 数据库进行交互，包括创建连接、初始化用户表等操作。

#### 构造方法
- `public MysqlClient(String host, String user, String password, String dbName, int port)`
    - **参数**：
        - `host`：MySQL 数据库的主机地址。
        - `user`：MySQL 数据库的用户名。
        - `password`：MySQL 数据库的密码。
        - `dbName`：数据库名称。
        - `port`：MySQL 数据库的端口号。
    - **描述**：初始化 `MysqlClient` 实例，创建与 MySQL 数据库的连接。

#### 实例方法
- `public void initUserTable()`
    - **描述**：初始化用户表。如果表不存在，则创建名为 `user` 的表，包含 `user_id`、`username` 和 `password` 字段。
- `private void executeUpdate(String sql)`
    - **参数**：
        - `sql`：要执行的 SQL 语句。
    - **描述**：执行给定的 SQL 更新语句。

### MinIOClient 类

#### 类概述
`MinIOClient` 类用于与 MinIO 对象存储服务进行交互，包括上传文件、下载文件等操作。

#### 构造方法
- `private MinIOClient(String endpoint, String accessKey, String secretKey)`
    - **参数**：
        - `endpoint`：MinIO 服务的端点地址。
        - `accessKey`：访问 MinIO 服务的访问密钥。
        - `secretKey`：访问 MinIO 服务的秘密密钥。
    - **描述**：初始化 `MinIOClient` 实例，创建 `MinioClient` 对象用于后续的文件操作。

#### 静态方法
- `public static synchronized MinIOClient getInstance(String endpoint, String accessKey, String secretKey)`
    - **参数**：
        - `endpoint`：MinIO 服务的端点地址。
        - `accessKey`：访问 MinIO 服务的访问密钥。
        - `secretKey`：访问 MinIO 服务的秘密密钥。
    - **返回值**：返回 `MinIOClient` 的单例实例。
    - **描述**：获取 `MinIOClient` 的单例实例。

#### 实例方法
- `public boolean uploadFile(String bucketName, String objectName, String filePath)`
    - **参数**：
        - `bucketName`：MinIO 存储桶名称。
        - `objectName`：要上传的对象名称。
        - `filePath`：要上传的文件的本地路径。
    - **返回值**：`boolean` 类型，表示文件是否上传成功。
    - **描述**：将指定路径的文件上传到 MinIO 存储桶中。
- `public boolean downloadFile(String bucketName, String objectName, String downloadPath)`
    - **参数**：
        - `bucketName`：MinIO 存储桶名称。
        - `objectName`：要下载的对象名称。
        - `downloadPath`：下载文件的本地路径。
    - **返回值**：`boolean` 类型，表示文件是否下载成功。
    - **描述**：从 MinIO 存储桶中下载指定的文件到本地路径。

### RedisClient 类

#### 类概述
`RedisClient` 类用于与 Redis 数据库进行交互，包括添加元素到列表、获取列表元素、删除键等操作。

#### 静态方法
- `public static synchronized RedisClient getInstance()`
    - **返回值**：返回 `RedisClient` 的单例实例。
    - **描述**：获取 `RedisClient` 的单例实例。
- `public static long lpush(String key, String element, Integer expireSeconds)`
    - **参数**：
        - `key`：Redis 中的键名。
        - `element`：要添加到列表头部的元素。
        - `expireSeconds`：键的过期时间（可选）。
    - **返回值**：`long` 类型，表示列表的长度。
    - **描述**：将元素添加到 Redis 列表的头部，并设置过期时间（如果需要）。
- `public static List<String> lrange(String key, long start, long end)`
    - **参数**：
        - `key`：Redis 中的键名。
        - `start`：列表的起始索引。
        - `end`：列表的结束索引。
    - **返回值**：`List<String>` 类型，表示列表中指定范围内的元素。
    - **描述**：获取 Redis 列表中指定范围内的元素。
- `public static boolean delete(String key)`
    - **参数**：
        - `key`：Redis 中的键名。
    - **返回值**：`boolean` 类型，表示键是否删除成功。
    - **描述**：删除 Redis 中指定的键。
- `public static void close()`
    - **描述**：关闭 Jedis 连接。