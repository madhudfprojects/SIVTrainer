package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_GetStudentPaymentResponseList {
    @SerializedName("Student_ID")
    @Expose
    private Integer studentID;
    @SerializedName("Application_No")
    @Expose
    private String applicationNo;
    @SerializedName("Student_Name")
    @Expose
    private String studentName;
    @SerializedName("Sandbox_Name")
    @Expose
    private String sandboxName;
    @SerializedName("Academic_Name")
    @Expose
    private String academicName;
    @SerializedName("Cluster_Name")
    @Expose
    private String clusterName;
    @SerializedName("Institute_Name")
    @Expose
    private String instituteName;
    @SerializedName("Lavel_Name")
    @Expose
    private String lavelName;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Student_Status")
    @Expose
    private String studentStatus;
    @SerializedName("Payment_Receivable")
    @Expose
    private String paymentReceivable;
    @SerializedName("Payment_Received")
    @Expose
    private String paymentReceived;
    @SerializedName("Payment_Balance")
    @Expose
    private String paymentBalance;
    @SerializedName("Payment_Mode")
    @Expose
    private Object paymentMode;
    @SerializedName("Payment_Type")
    @Expose
    private Object paymentType;
    @SerializedName("Payment_Amount")
    @Expose
    private Object paymentAmount;
    @SerializedName("Payment_Status")
    @Expose
    private Object paymentStatus;
    @SerializedName("Received_Mode")
    @Expose
    private Object receivedMode;
    @SerializedName("Received_Remarks")
    @Expose
    private Object receivedRemarks;
    @SerializedName("Payment_Date_String")
    @Expose
    private Object paymentDateString;
    @SerializedName("Received_Date_String")
    @Expose
    private Object receivedDateString;
    @SerializedName("Payment_User")
    @Expose
    private Object paymentUser;
    @SerializedName("Received_User")
    @Expose
    private Object receivedUser;
    @SerializedName("Payment_Date")
    @Expose
    private Object paymentDate;
    @SerializedName("Payment_Remarks")
    @Expose
    private Object paymentRemarks;
    @SerializedName("Created_By")
    @Expose
    private Object createdBy;
    @SerializedName("Receipt_Manual")
    @Expose
    private Object receiptManual;

    @SerializedName("Receipt_No")
    @Expose
    private String Receipt_No;

    public String getReceipt_No() {
        return Receipt_No;
    }

    public void setReceipt_No(String receipt_No) {
        Receipt_No = receipt_No;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
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

    public String getSandboxName() {
        return sandboxName;
    }

    public void setSandboxName(String sandboxName) {
        this.sandboxName = sandboxName;
    }

    public String getAcademicName() {
        return academicName;
    }

    public void setAcademicName(String academicName) {
        this.academicName = academicName;
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

    public String getLavelName() {
        return lavelName;
    }

    public void setLavelName(String lavelName) {
        this.lavelName = lavelName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public String getPaymentReceivable() {
        return paymentReceivable;
    }

    public void setPaymentReceivable(String paymentReceivable) {
        this.paymentReceivable = paymentReceivable;
    }

    public String getPaymentReceived() {
        return paymentReceived;
    }

    public void setPaymentReceived(String paymentReceived) {
        this.paymentReceived = paymentReceived;
    }

    public String getPaymentBalance() {
        return paymentBalance;
    }

    public void setPaymentBalance(String paymentBalance) {
        this.paymentBalance = paymentBalance;
    }

    public Object getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Object paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Object getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Object paymentType) {
        this.paymentType = paymentType;
    }

    public Object getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Object paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Object getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Object paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Object getReceivedMode() {
        return receivedMode;
    }

    public void setReceivedMode(Object receivedMode) {
        this.receivedMode = receivedMode;
    }

    public Object getReceivedRemarks() {
        return receivedRemarks;
    }

    public void setReceivedRemarks(Object receivedRemarks) {
        this.receivedRemarks = receivedRemarks;
    }

    public Object getPaymentDateString() {
        return paymentDateString;
    }

    public void setPaymentDateString(Object paymentDateString) {
        this.paymentDateString = paymentDateString;
    }

    public Object getReceivedDateString() {
        return receivedDateString;
    }

    public void setReceivedDateString(Object receivedDateString) {
        this.receivedDateString = receivedDateString;
    }

    public Object getPaymentUser() {
        return paymentUser;
    }

    public void setPaymentUser(Object paymentUser) {
        this.paymentUser = paymentUser;
    }

    public Object getReceivedUser() {
        return receivedUser;
    }

    public void setReceivedUser(Object receivedUser) {
        this.receivedUser = receivedUser;
    }

    public Object getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Object paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Object getPaymentRemarks() {
        return paymentRemarks;
    }

    public void setPaymentRemarks(Object paymentRemarks) {
        this.paymentRemarks = paymentRemarks;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getReceiptManual() {
        return receiptManual;
    }

    public void setReceiptManual(Object receiptManual) {
        this.receiptManual = receiptManual;
    }

}
