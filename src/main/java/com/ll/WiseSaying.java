package com.ll;

public class WiseSaying {
    public static int idVal = 1;
    private int id;
    private String body;
    private String author;

    public WiseSaying() {

    }

    public WiseSaying(int id, String body, String author) {
        this.id = id;
        this.body = body;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
