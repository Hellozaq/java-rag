//package org.db;
//
//import org.json.JSONObject;
//import org.junit.Before;
//import org.junit.Test;
//import org.service.LLM.OpenAIChatService;
//
//import java.io.IOException;
//
//import static org.junit.Assert.*;
//
//public class OpenAIChatServiceTest {
//
//    private OpenAIChatService openAIChatService;
//    private String apiKey = "your_api_key_here"; // 使用有效的API密钥
//
//    @Before
//    public void setUp() {
//        openAIChatService = new OpenAIChatService(apiKey);
//    }
//
//    @Test
//    public void testGenerateTextWithoutChatId() throws IOException {
//        String url = "https://api.openai.com/v1/chat/completions";
//        JSONObject params = new JSONObject()
//                .put("model", "gpt-3.5")
//                .put("messages", new JSONObject[] {
//                        new JSONObject().put("role", "user").put("content", "1+1 = ?")
//                })
//                .put("temperature", 0.3)
//                .put("stream", false);
//
//        String result = openAIChatService.generateText(url, params);
//        assertNotNull(result);
//        assertTrue(result.contains("2"));
//    }
//
//    @Test
//    public void testGenerateTextWithChatId() throws IOException {
//        String url = "https://api.openai.com/v1/chat/completions";
//        String chatId = "chat123";
//        JSONObject newMessage = new JSONObject().put("role", "user").put("content", "What was my last question?");
//
//        String result = openAIChatService.generateText(url, chatId, newMessage);
//        assertNotNull(result);
//        // 根据实际情况调整断言内容
//        assertTrue(result.contains("last question"));
//    }
//}