package org.chunk;

import java.util.List;

public interface TextSplitter {
    List<String> split(String text);
}