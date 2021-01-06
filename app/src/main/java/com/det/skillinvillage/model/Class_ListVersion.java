package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_ListVersion {
    @SerializedName("Document_ID")
    @Expose
    private Integer documentID;
    @SerializedName("Document_Date")
    @Expose
    private String documentDate;
    @SerializedName("Document_Time")
    @Expose
    private String documentTime;
    @SerializedName("Document_Name")
    @Expose
    private String documentName;
    @SerializedName("Document_Path")
    @Expose
    private String documentPath;
    @SerializedName("Document_Type")
    @Expose
    private String documentType;
    @SerializedName("Document_Status")
    @Expose
    private String documentStatus;

    @SerializedName("Document_Verification")
    @Expose
    private String DocumentVerification;

    public String getDocumentVerification() {
        return DocumentVerification;
    }

    public void setDocumentVerification(String documentVerification) {
        DocumentVerification = documentVerification;
    }

    public Integer getDocumentID() {
        return documentID;
    }

    public void setDocumentID(Integer documentID) {
        this.documentID = documentID;
    }

    public String getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }

    public String getDocumentTime() {
        return documentTime;
    }

    public void setDocumentTime(String documentTime) {
        this.documentTime = documentTime;
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

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

}
