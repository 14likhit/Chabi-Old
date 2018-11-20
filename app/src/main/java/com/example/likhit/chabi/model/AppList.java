package com.example.likhit.chabi.model;

import org.json.JSONObject;

public class AppList {
    private String appName;
    private int imageId;
    private JSONObject appQuestion;


    public AppList() {
    }

    public AppList(String appName, int imageId) {
        this.appName = appName;
        this.imageId = imageId;
    }

    public AppList(String appName, int imageId, JSONObject appQuestion) {
        this.appName = appName;
        this.imageId = imageId;
        this.appQuestion = appQuestion;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public JSONObject getAppQuestion() {
        return appQuestion;
    }

    public void setAppQuestion(JSONObject appQuestion) {
        this.appQuestion = appQuestion;
    }
}

