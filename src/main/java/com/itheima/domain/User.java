package com.itheima.domain;

public class User {
    private int id;
    private String name;
    private String gender;
    private String password;
    private int gongDe;
    private int isTen;

    public int getIsTen() {
        return isTen;
    }

    public void setIsTen(Integer isTen) {
        this.isTen = isTen;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGongDe() {
        return gongDe;
    }

    public void setGongDe(int gongDe) {
        this.gongDe = gongDe;
    }
}
