package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostSavePaymentRequest {

    @SerializedName("Student_ID")
    @Expose
    private String studentID;
    @SerializedName("Payment_Type")
    @Expose
    private String paymentType;
    @SerializedName("Payment_Mode")
    @Expose
    private String paymentMode;
    @SerializedName("Payment_Amount")
    @Expose
    private String paymentAmount;
    @SerializedName("Payment_Date")
    @Expose
    private String paymentDate;
    @SerializedName("Payment_Remarks")
    @Expose
    private String paymentRemarks;
    @SerializedName("Created_By")
    @Expose
    private String createdBy;
    @SerializedName("Receipt_Manual")
    @Expose
    private String receiptManual;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public String getPaymentRemarks() {
        return paymentRemarks;
    }

    public void setPaymentRemarks(String paymentRemarks) {
        this.paymentRemarks = paymentRemarks;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getReceiptManual() {
        return receiptManual;
    }

    public void setReceiptManual(String receiptManual) {
        this.receiptManual = receiptManual;
    }

}
