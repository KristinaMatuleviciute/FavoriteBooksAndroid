package com.example.kristinamatuleviciute.myapplication;

public class Author {

    String authorId;
    String authorName;
    String authorGendre;

    public Author(){

    }

    public Author(String authorId, String authorName, String aristGendre) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorGendre = aristGendre;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAristGendre() {
        return authorGendre;
    }
}
