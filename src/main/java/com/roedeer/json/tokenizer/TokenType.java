package com.roedeer.json.tokenizer;

/**
 BEGIN_OBJECT（{）
 END_OBJECT（}）
 BEGIN_ARRAY（[）
 END_ARRAY（]）
 NULL（null）
 NUMBER（数字）
 STRING（字符串）
 BOOLEAN（true/false）
 SEP_COLON（:）
 SEP_COMMA（,）
 END_DOCUMENT（表示JSON文档结束）

 在TokenType中我们为每一种类型都赋一个数字，目的是在Parser做一些优化操作（通过位运算来判断是否是期望出现的类型）
 */

public enum TokenType {
    BEGIN_OBJECT(1),
    END_OBJECT(2),
    BEGIN_ARRAY(4),
    END_ARRAY(8),
    NULL(16),
    NUMBER(32),
    STRING(64),
    BOOLEAN(128),
    SEP_COLON(256),
    SEP_COMMA(512),
    END_DOCUMENT(1024);

    private int code;    // 每个类型的编号

    TokenType(int code) {
        this.code = code;
    }

    public int getTokenCode() {
        return code;
    }
}
