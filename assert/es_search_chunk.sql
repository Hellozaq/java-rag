{
    "query": {
        "bool": {
            "should": [
                {"match": {"chunk_text": {"query": "%s", "boost": %f}}}
            ],
            "filter": [
                {"term": {"user_id": "%s"}}
            ]
        }
    },
    "highlight": {
        "fields": {
            "chunk_text": {}
        }
    },
    "size": %d
}