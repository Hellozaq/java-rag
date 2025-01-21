- 建表语句
PUT /documents
- 字段Mapping
POST /documents/_mapping
{

  "properties": {

    "user_id": {

      "type": "keyword"

    },

    "file_id": {

      "type": "keyword"

    },

    "kb_id": {

      "type": "keyword"

    },

    "chunk_id": {

      "type": "integer"

    },

    "chunk_size": {

      "type": "integer"

    },

    "chunk_text": {

      "type": "text",

      "analyzer": "ik_max_word",

      "search_analyzer": "ik_smart"

    },

    "text_emb": {

      "type": "dense_vector",

      "dims": 512

    },
    "clip_emb":{
      "type": "dense_vector",

      "dims": 512

    },
    "doc_type": {

      "type": "keyword"

    },

    "version": {

      "type": "keyword"

    },

    "author": {

      "type": "keyword"

    },

    "created_time": {

      "type": "integer"

    },

    "modified_time": {

      "type": "integer"

    },

    "file_name": {

      "type": "text",

      "analyzer": "ik_max_word",

      "search_analyzer": "ik_smart"

    },

    "storage_path": {

      "type": "keyword"

    }

  }

}