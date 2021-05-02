package com.example.opinionist;

public class Comment {
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
