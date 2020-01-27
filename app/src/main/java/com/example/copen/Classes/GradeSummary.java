package com.example.copen.Classes;

public class GradeSummary {

    private char[] Answers;
    private char[] Key;
    private int Points;

    public char[] getAnswers() {
        return Answers;
    }

    public void setAnswers(char[] answers) {
        Answers = answers;
    }

    public char[] getKey() {
        return Key;
    }

    public void setKey(char[] key) {
        Key = key;
    }

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }

    @Override
    public String toString() {
        return "" + Points;
    }
}
