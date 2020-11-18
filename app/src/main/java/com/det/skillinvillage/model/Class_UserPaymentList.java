package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_UserPaymentList {
    @SerializedName("Institute_ID")
    @Expose
    private Integer instituteID;
    @SerializedName("Cluster_Name")
    @Expose
    private String clusterName;
    @SerializedName("Institute_Name")
    @Expose
    private String instituteName;
    @SerializedName("Student_Count")
    @Expose
    private String studentCount;
    @SerializedName("Receivable")
    @Expose
    private String receivable;
    @SerializedName("Received")
    @Expose
    private String received;
    @SerializedName("Balance")
    @Expose
    private String balance;
    @SerializedName("Balance_Count")
    @Expose
    private String balanceCount;
    @SerializedName("Payment_Status")
    @Expose
    private String paymentStatus;

    public Integer getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(Integer instituteID) {
        this.instituteID = instituteID;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(String studentCount) {
        this.studentCount = studentCount;
    }

    public String getReceivable() {
        return receivable;
    }

    public void setReceivable(String receivable) {
        this.receivable = receivable;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalanceCount() {
        return balanceCount;
    }

    public void setBalanceCount(String balanceCount) {
        this.balanceCount = balanceCount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
