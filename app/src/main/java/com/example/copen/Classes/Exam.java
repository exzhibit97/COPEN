package com.example.copen.Classes;

public class Exam {
    int id;
    String name, key, createDate;

    public Exam(int id, String name, String key, String createDate) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getCreateDate() {
        return createDate;
    }
}