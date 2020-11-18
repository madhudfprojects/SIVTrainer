package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NormalLogin_List {
    @SerializedName("User_ID")
    @Expose
    private String userID;
    @SerializedName("User_Email")
    @Expose
    private String userEmail;
    @SerializedName("User_Designation")
    @Expose
    private String userDesignation;
    @SerializedName("User_Status")
    @Expose
    private String userStatus;
    @SerializedName("User_Password")
    @Expose
    private String userPassword;
    @SerializedName("User_Name")
    @Expose
    private String userName;

    @SerializedName("User_Role")
    @Expose
    private List<UserRole> userRole = null;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserDesignation() {
        return userDesignation;
    }

    public void setUserDesignation(String userDesignation) {
        this.userDesignation = userDesignation;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public Object getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
