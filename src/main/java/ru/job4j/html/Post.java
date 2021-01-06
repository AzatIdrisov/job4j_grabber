package ru.job4j.html;

import java.util.Date;

public class Post {
    private String postName;
    private String link;
    private String author;
    private Date dateCreation;
    private int id;
    private String text;

    public Post(String postName, String text, String author, Date date, String link) {
        this.postName = postName;
        this.text = text;
        this.author = author;
        this.dateCreation = date;
        this.link = link;
    }
}
