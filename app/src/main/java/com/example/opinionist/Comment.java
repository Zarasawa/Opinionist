package com.example.opinionist;

public class Comment {
    private String comment;
    private Integer likes;

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

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
