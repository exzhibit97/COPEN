package com.example.copen.Extensions;

import android.app.Application;

public class GlobalClass extends Application {

    private String TestName;

    private char[] Answers;

    public char[] getAnswers() {
        return Answers;
    }

    public void setAnswers(char[] answers) {
        Answers = answers;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }
}
