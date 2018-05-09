package com.example.kristinamatuleviciute.myapplication;

public class Book {

    private String bookId;
    private String bookName;
    private int bookRating;

    public Book() {}

    public Book(String bookId, String bookName, int bookRating) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookRating = bookRating;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public int getBookRating() {
        return bookRating;
    }
}

