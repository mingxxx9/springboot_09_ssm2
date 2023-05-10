package com.itheima.tools;


import com.itheima.domain.User;
import com.itheima.domain.UserDTO;

public  class UserOps {

    private static final ThreadLocal<UserDTO> localuser=new ThreadLocal<>();
    public static UserDTO getUser() {
        return localuser.get();
    }

    public static void setUser(UserDTO u) {
        localuser.set(u);
    }

    public static Integer getID(){
        return  localuser.get().getId();
    }

}
