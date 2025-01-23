
### `OpenAIChatService` 接口文档

#### 类概述
`OpenAIChatService` 类用于与 OpenAI 的 API 进行交互，发送请求并获取响应。它提供了两个方法来生成文本，一个方法不涉及聊天历史记录，另一个方法会处理聊天历史记录并将新消息添加到 Redis 中。

#### 构造方法
- `OpenAIChatService(String apiKey)`
    - **描述**：构造一个 `OpenAIChatService` 实例。
    - **参数**：
        - `apiKey`：OpenAI API 的密钥，用于身份验证。
    - **返回值**：无

#### 方法
- `public String generateText(String url, JSONObject params) throws IOException`
    - **描述**：发送请求到指定的 API 并获取响应。
    - **参数**：
        - `url`：API 的 URL。
        - `params`：请求参数，以 `JSONObject` 形式表示。
    - **返回值**：生成的文本。
    - **异常**：
        - `IOException`：如果请求失败，抛出该异常。

- `public String generateText(String url, String chatId, JSONObject newMessage) throws IOException`
    - **描述**：发送请求到指定的 API 并获取响应，同时处理聊天历史记录。
    - **参数**：
        - `url`：API 的 URL。
        - `chatId`：对话的唯一标识，用于在 Redis 中存储和获取聊天历史记录。
        - `newMessage`：新的消息内容，以 `JSONObject` 形式表示。
    - **返回值**：生成的文本。
    - **异常**：
        - `IOException`：如果请求失败，抛出该异常。

### `OllamaChatService` 接口文档

#### 类概述
`OllamaChatService` 类用于与 Ollama 的 API 进行交互，发送聊天请求并获取响应。

#### 构造方法
- `OllamaChatService()`
    - **描述**：构造一个 `OllamaChatService` 实例。
    - **参数**：无
    - **返回值**：无

#### 方法
- `public String generateChatCompletion(String model, String message) throws Exception`
    - **描述**：发送聊天请求到指定的 API 并获取响应。
    - **参数**：
        - `model`：使用的模型名称。
        - `message`：用户发送的消息内容。
    - **返回值**：API 返回的响应体字符串。
    - **异常**：
        - `Exception`：如果请求失败，抛出该异常。