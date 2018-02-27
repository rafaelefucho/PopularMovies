package com.example.rafael.popularmovies.Utilities;

/**
 * Created by Rafael on 27/02/2018.
 */

public class Review {

    private String Author;
    private String Content;

    public Review(String author, String content) {
        Author = author;
        Content = content;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }


}
