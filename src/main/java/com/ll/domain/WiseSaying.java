package com.ll.domain;

import lombok.Data;

@Data
public class WiseSaying {

    public static long idVal = 1;
    private long id;
    private String body;
    private String author;

    public WiseSaying() {

    }

    public WiseSaying(long id, String body, String author) {
        this.id = id;
        this.body = body;
        this.author = author;
    }

}
