package org.search;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.entity.Document;
import org.entity.SearchInput;
import org.entity.SearchOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pipeline {
    private final static Logger logger = LoggerFactory.getLogger(Pipeline.class);
    static {
        try{
            ParserConfig.getGlobalInstance().setSafeMode(true);
            JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.WriteNonStringKeyAsString.getMask();
            JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
        }catch (Throwable t){
            logger.info(t.getMessage(), t);
        }
    }
    private SearchInput searchInput;
    private SearchOutput searchOutput;
    public void recall(){
        RecallStrategy.esRecall(searchInput,searchOutput);
    }
    public void sort(){
        SortStrategy.dummySort(searchInput,searchOutput);
    }
    public void rerank(){
        RerankStrategy.JinaCobertRerank(searchInput,searchOutput);
    }
    public SearchOutput getDefaultResult(){
        // 召回
        recall();
        // 排序
        sort();
        // 重排
        rerank();
        return searchOutput;
    }
}
