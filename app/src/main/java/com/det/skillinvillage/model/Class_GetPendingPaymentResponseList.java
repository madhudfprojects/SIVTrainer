package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_GetPendingPaymentResponseList {
    @SerializedName("Payment_ID")
    @Expose
    private Integer paymentID;
    @SerializedName("Cluster_Name")
    @Expose
    private String clusterName;
    @SerializedName("Lavel_Name")
    @Expose
    private String lavelName;
    @SerializedName("Institute_Name")
    @Expose
    private String instituteName;
    @SerializedName("Application_No")
    @Expose
    private String applicationNo;
    @SerializedName("Student_Name")
    @Expose
    private String studentName;
    @SerializedName("Payment_User")
    @Expose
    private String paymentUser;
    @SerializedName("Payment_Mode")
    @Expose
    private String paymentMode;
    @SerializedName("Payment_Amount")
    @Expose
    private String paymentAmount;
    @SerializedName("Payment_Date")
    @Expose
    private String paymentDate;
    @SerializedName("Payment_Status")
    @Expose
    private String paymentStatus;

    public Integer getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Integer paymentID) {
        this.paymentID = paymentID;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getLavelName() {
        return lavelName;
    }

    public void setLavelName(String lavelName) {
        this.lavelName = lavelName;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPaymentUser() {
        return paymentUser;
    }

    public void setPaymentUser(String paymentUser) {
        this.paymentUser = paymentUser;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
