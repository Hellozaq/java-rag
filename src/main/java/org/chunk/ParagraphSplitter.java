package org.chunk;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 定义 ParagraphSplitter 类，实现 TextSplitter 接口
public class ParagraphSplitter  implements TextSplitter {

    // 实现 split 方法，用于将文本按段落分割
   public List<String> split(String text) {
        // 以换行符作为段落分隔符，将文本分割成段落数组
        String[] paragraphs = text.split("\\n\\n");
        // 将段落数组转换为 List 并返回
        return new ArrayList<>(Arrays.asList(paragraphs));
    }
}