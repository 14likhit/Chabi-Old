package com.example.likhit.chabi.model;

import java.util.ArrayList;
import java.util.List;

public class Questions {
    String question;
    List<String> steps=new ArrayList<>();

    public Questions() {
    }

    public Questions(String question, List<String> steps) {
        this.question = question;
        this.steps = steps;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}
