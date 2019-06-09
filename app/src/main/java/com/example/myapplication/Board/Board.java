package com.example.myapplication.Board;

public class Board {
    String id;
    String passwd;
    String name;
    String num;
    String gender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Board(String id, String passwd, String name, String num, String gender) {
        this.id = id;
        this.passwd = passwd;
        this.name = name;
        this.num = num;
        this.gender = gender;
    }
}