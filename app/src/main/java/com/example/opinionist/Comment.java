package com.example.opinionist;

import java.util.ArrayList;
import java.util.List;

public class Comment {
    private Integer id;
    private String comment;
    private Integer likes;
    private Integer parentid;
    private String author;
    private ArrayList<String> upvoters;

    public Comment() {
        author = "Anonymous";
        likes = 0;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArrayList<String> getUpvoters() {
        return upvoters;
    }

    public void setUpvoters(ArrayList<String> upvoters) {
        this.upvoters = upvoters;
    }
}
