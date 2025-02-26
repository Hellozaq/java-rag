package org.web;

import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class UrlToMarkdownConverter {

    /**
     * 从指定 URL 获取 HTML 内容
     * @param url 网页的 URL
     * @return HTML 内容字符串
     * @throws IOException 如果网络请求出现问题
     */
    public static String getHtmlFromUrl(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                .get();
        return doc.html();
    }

    /**
     * 将 HTML 内容转换为 Markdown 内容
     * @param html HTML 内容字符串
     * @return Markdown 内容字符串
     */
    public static String convertHtmlToMarkdown(String html) {
        MutableDataSet options = new MutableDataSet();
        FlexmarkHtmlConverter converter = FlexmarkHtmlConverter.builder(options).build();
        return converter.convert(html);
    }

    /**
     * 从 URL 直接转换为 Markdown 内容
     * @param url 网页的 URL
     * @return Markdown 内容字符串
     * @throws IOException 如果网络请求出现问题
     */
    public static String convertUrlToMarkdown(String url) throws IOException {
        String html = getHtmlFromUrl(url);
        return convertHtmlToMarkdown(html);
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis(); // 记录程序开始时间
        String url = "https://zh.wikipedia.org/wiki/哪吒之魔童闹海";
        try {
            String markdown = convertUrlToMarkdown(url);
            System.out.println(markdown);
        } catch (IOException e) {
            System.err.println("获取网页内容时出现错误: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis(); // 记录程序结束时间
        long duration = (endTime - startTime) / 1000; // 计算运行时间（秒）
        System.out.println("程序运行时间为：" + duration + " 秒");
    }

}