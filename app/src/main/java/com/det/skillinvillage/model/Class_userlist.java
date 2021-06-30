package com.det.skillinvillage.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_userlist {
    @SerializedName("User_ID")
    @Expose
    private String userID;
    @SerializedName("User_Name")
    @Expose
    private Object userName;
    @SerializedName("User_Email")
    @Expose
    private String userEmail;
    @SerializedName("User_Password")
    @Expose
    private Object userPassword;
    @SerializedName("User_Version")
    @Expose
    private Object userVersion;
    @SerializedName("User_Status")
    @Expose
    private Object userStatus;
    @SerializedName("User_Designation")
    @Expose
    private Object userDesignation;
    @SerializedName("User_Role")
    @Expose
    private Object userRole;
    @SerializedName("Response")
    @Expose
    private Object response;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Object getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(Object userPassword) {
        this.userPassword = userPassword;
    }

    public Object getUserVersion() {
        return userVersion;
    }

    public void setUserVersion(Object userVersion) {
        this.userVersion = userVersion;
    }

    public Object getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Object userStatus) {
        this.userStatus = userStatus;
    }

    public Object getUserDesignation() {
        return userDesignation;
    }

    public void setUserDesignation(Object userDesignation) {
        this.userDesignation = userDesignation;
    }

    public Object getUserRole() {
        return userRole;
    }

    public void setUserRole(Object userRole) {
        this.userRole = userRole;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    @NonNull
    @Override
    public String toString() {
        return userEmail;
    }
}
