package com.itheima.domain;

public class UserDTO {
   Integer id;
    String name;

    String gender;

    Integer gongDe;

    Integer is_ten;

    public Integer getIs_ten() {
        return is_ten;
    }

    public void setIs_ten(Integer is_ten) {
        this.is_ten = is_ten;
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
