package com.retrofittest.model;

import com.google.gson.annotations.SerializedName;

public class Post {

    public Post(int userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    private int userId;

    private Integer id;

    private String title;

    @SerializedName("body")
    private String text;


    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
