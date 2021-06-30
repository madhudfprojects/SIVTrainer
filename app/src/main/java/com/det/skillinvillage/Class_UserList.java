package com.det.skillinvillage;

import android.support.annotation.NonNull;

public class Class_UserList {
    String User_id;
    String User_EmailID;

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getUser_EmailID() {
        return User_EmailID;
    }

    public void setUser_EmailID(String user_EmailID) {
        User_EmailID = user_EmailID;
    }
    @NonNull
    @Override
    public String toString() {
        return User_EmailID;
    }

}
