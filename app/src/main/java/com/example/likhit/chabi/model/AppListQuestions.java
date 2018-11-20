package com.example.likhit.chabi.model;


import org.json.JSONObject;

public class AppListQuestions {

    private String question;
    private int appId;
    private int questionId;
    private JSONObject steps;

    public AppListQuestions() {
    }

    public AppListQuestions(String question, int appId, int questionId) {
        this.question = question;
        this.appId = appId;
        this.questionId = questionId;
    }

    public AppListQuestions(String question, int appId, int questionId, JSONObject steps) {
        this.question = question;
        this.appId = appId;
        this.questionId = questionId;
        this.steps = steps;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public JSONObject getSteps() {
        return steps;
    }

    public void setSteps(JSONObject steps) {
        this.steps = steps;
    }
}

