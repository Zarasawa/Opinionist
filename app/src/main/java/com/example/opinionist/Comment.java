package com.example.opinionist;

public class Comment {
    private Integer id;
    private String comment;
    private Integer likes;
    private Integer parentid;

    public Comment() {
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
}
