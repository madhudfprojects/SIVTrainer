package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_GetPaymentPendingSummaryResponseList {

    @SerializedName("Payment_ID")
    @Expose
    private Integer paymentID;
    @SerializedName("Student_Count")
    @Expose
    private String studentCount;
    @SerializedName("Recevied_Fee")
    @Expose
    private String receviedFee;
    @SerializedName("Account_Recevied")
    @Expose
    private String accountRecevied;
    @SerializedName("Trainer_Pending")
    @Expose
    private String trainerPending;
    @SerializedName("Payment_Status")
    @Expose
    private String paymentStatus;

    public Integer getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Integer paymentID) {
        this.paymentID = paymentID;
    }

    public String getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(String studentCount) {
        this.studentCount = studentCount;
    }

    public String getReceviedFee() {
        return receviedFee;
    }

    public void setReceviedFee(String receviedFee) {
        this.receviedFee = receviedFee;
    }

    public String getAccountRecevied() {
        return accountRecevied;
    }

    public void setAccountRecevied(String accountRecevied) {
        this.accountRecevied = accountRecevied;
    }

    public String getTrainerPending() {
        return trainerPending;
    }

    public void setTrainerPending(String trainerPending) {
        this.trainerPending = trainerPending;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
