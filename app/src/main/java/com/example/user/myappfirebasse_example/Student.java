package com.example.user.myappfirebasse_example;

import java.io.Serializable;

/**
 * Created by USER on 4/13/2018.
 */

public class Student implements Serializable{
    private String name;
    private String mssv;

    public Student() {
    }

    public Student(String name, String mssv) {
        this.name = name;
        this.mssv = mssv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }
}
