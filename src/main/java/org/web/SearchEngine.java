package org.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SearchEngine {

    public static JsonObject getResult(Map<String, String> parameter) {
        // 构建请求URL
        StringBuilder urlBuilder = new StringBuilder("https://serpapi.com/search.json");
        for (Map.Entry<String, String> entry : parameter.entrySet()) {
            if (urlBuilder.indexOf("?") == -1) {
                urlBuilder.append("?").append(entry.getKey()).append("=").append(entry.getValue());
            } else {
                urlBuilder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        String url = urlBuilder.toString();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                // 解析JSON响应
                return JsonParser.parseString(responseBody).getAsJsonObject();
            } else {
                System.out.println("请求失败，状态码: " + response.code());
            }
        } catch (IOException e) {
            System.out.println("请求过程中出现异常: " + e.getMessage());
        }
        return null;
    }

    public static void parseResults(JsonObject results) {
        if (results != null) {
            // 解析 search_information 中的 query_displayed 字段
            JsonObject searchInformation = results.getAsJsonObject("search_information");
            if (searchInformation != null) {
                String queryDisplayed = searchInformation.get("query_displayed").getAsString();
                System.out.println("查询关键词: " + queryDisplayed);
            }

            // 解析 organic_results 数组中的 title、link 和 snippet 字段
            JsonArray organicResults = results.getAsJsonArray("organic_results");
            if (organicResults != null) {
                for (int i = 0; i < organicResults.size(); i++) {
                    JsonObject result = organicResults.get(i).getAsJsonObject();
                    String title = result.get("title").getAsString();
                    String link = result.get("link").getAsString();
                    String snippet = "";
                    if (result.has("snippet") &&!result.get("snippet").isJsonNull()) {
                        snippet = result.get("snippet").getAsString();
                    }
                    System.out.println("第 " + (i + 1) + " 条结果:");
                    System.out.println("标题: " + title);
                    System.out.println("链接: " + link);
                    System.out.println("摘要: " + snippet);
                    System.out.println("-----------------");
                }
            }
        }
    }

    public static void main(String[] args) {
        // 构建请求参数
        Map<String, String> parameter = new HashMap<>();
        parameter.put("engine", "baidu");
        parameter.put("q", "Coffee");
        parameter.put("api_key", "1af00627e582c9238b8c947d2300dd13331a9817523811a83dc16245ed98d444");

        JsonObject results = getResult(parameter);
        if (results != null) {
            parseResults(results);
        }
    }
}