package com.example.likhit.chabi.model;

import org.json.JSONObject;

public class App {
    String appname;
    //JSONObject questions;
    Object questions;

    public App() {
    }

    public App(String appname, Object questions) {
        this.appname = appname;
        this.questions = questions;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public Object getQuestions() {
        return questions;
    }

    public void setQuestions(Object questions) {
        this.questions = questions;
    }

    //
//    public JSONObject getQuestions() {
//        return questions;
//    }
//
//    public void setQuestions(JSONObject questions) {
//        this.questions = questions;
//    }
}
