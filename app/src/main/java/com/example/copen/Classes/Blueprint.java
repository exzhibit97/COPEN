package com.example.copen.Classes;

import java.sql.Timestamp;

public class Blueprint {
    private String id = "Test_" + new Timestamp((new java.util.Date().getTime()));
    private char[] answers;

    public Blueprint(char[] answers) {
        //this.id = "Test_" + new Timestamp((new java.util.Date().getTime()));
        this.answers = answers;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public char[] getAnswers() {
        return answers;
    }

    public void setAnswers(char[] answers) {
        this.answers = answers;
    }
}
