package com.itheima.domain;

import lombok.Data;


public class Blog {
    Integer id;
    Integer userid;

    String username;
    String description;
    Integer likes;
    Integer dislikes;
    Boolean isliked;
    Boolean isdisliked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String discription) {
        this.description = discription;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public Boolean getIsliked() {
        return isliked;
    }

    public void setIsliked(Boolean isliked) {
        this.isliked = isliked;
    }

    public Boolean getIsdisliked() {
        return isdisliked;
    }

    public void setIsdisliked(Boolean isdisliked) {
        this.isdisliked = isdisliked;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", userid=" + userid +
                ", username='" + username + '\'' +
                ", discription='" + description + '\'' +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", isliked=" + isliked +
                ", isdisliked=" + isdisliked +
                '}';
    }
}
