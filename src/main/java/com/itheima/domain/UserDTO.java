package com.itheima.domain;

public class UserDTO {
   Integer id;
    String name;

    String gender;

    Integer gongDe;

    Integer isTen;

    public Integer getIsTen() {
        return isTen;
    }

    public void setIsTen(Integer isTen) {
        this.isTen = isTen;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getGongDe() {
        return gongDe;
    }

    public void setGongDe(Integer gongDe) {
        this.gongDe = gongDe;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", gongDe=" + gongDe +
                ", is_ten=" + isTen +
                '}';
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


}
