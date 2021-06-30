package com.det.skillinvillage.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserlistDetails
{
    @SerializedName("User_ID")
    @Expose
    private String user_id;


    @SerializedName("User_Name")
    @Expose
    private String user_name;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @NonNull
    @Override
    public String toString() {
        return user_name;
    }
}

