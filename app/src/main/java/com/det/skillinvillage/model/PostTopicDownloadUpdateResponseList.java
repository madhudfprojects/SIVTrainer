package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostTopicDownloadUpdateResponseList {
    @SerializedName("Document_ID")
    @Expose
    private String documentID;
    @SerializedName("TopicLevel_ID")
    @Expose
    private String topicLevelID;
    @SerializedName("Sandbox_ID")
    @Expose
    private Object sandboxID;
    @SerializedName("Academic_ID")
    @Expose
    private Object academicID;
    @SerializedName("Plan_ID")
    @Expose
    private Object planID;
    @SerializedName("Sandbox_Name")
    @Expose
    private Object sandboxName;
    @SerializedName("Academic_Name")
    @Expose
    private Object academicName;
    @SerializedName("Level_Name")
    @Expose
    private Object levelName;
    @SerializedName("Document_Type")
    @Expose
    private String documentType;
    @SerializedName("Document_Name")
    @Expose
    private String documentName;
    @SerializedName("Document_Path")
    @Expose
    private String documentPath;
    @SerializedName("Created_Date")
    @Expose
    private Object createdDate;
    @SerializedName("Created_By")
    @Expose
    private Object createdBy;
    @SerializedName("Updated_Date")
    @Expose
    private Object updatedDate;
    @SerializedName("Updated_By")
    @Expose
    private Object updatedBy;
    @SerializedName("Document_Status")
    @Expose
    private String documentStatus;
    @SerializedName("Document_Verification")
    @Expose
    private String documentVerification;

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getTopicLevelID() {
        return topicLevelID;
    }

    public void setTopicLevelID(String topicLevelID) {
        this.topicLevelID = topicLevelID;
    }

    public Object getSandboxID() {
        return sandboxID;
    }

    public void setSandboxID(Object sandboxID) {
        this.sandboxID = sandboxID;
    }

    public Object getAcademicID() {
        return academicID;
    }

    public void setAcademicID(Object academicID) {
        this.academicID = academicID;
    }

    public Object getPlanID() {
        return planID;
    }

    public void setPlanID(Object planID) {
        this.planID = planID;
    }

    public Object getSandboxName() {
        return sandboxName;
    }

    public void setSandboxName(Object sandboxName) {
        this.sandboxName = sandboxName;
    }

    public Object getAcademicName() {
        return academicName;
    }

    public void setAcademicName(Object academicName) {
        this.academicName = academicName;
    }

    public Object getLevelName() {
        return levelName;
    }

    public void setLevelName(Object levelName) {
        this.levelName = levelName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getDocumentVerification() {
        return documentVerification;
    }

    public void setDocumentVerification(String documentVerification) {
        this.documentVerification = documentVerification;
    }


}
