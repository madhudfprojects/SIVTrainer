package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_dashboardList {
    @SerializedName("Dashboard_ID")
    @Expose
    private Integer dashboardID;
    @SerializedName("Sandbox_ID")
    @Expose
    private String sandboxID;
    @SerializedName("Sandbox_Name")
    @Expose
    private String sandboxName;
    @SerializedName("Count_Institute")
    @Expose
    private String countInstitute;
    @SerializedName("Count_Village")
    @Expose
    private String countVillage;
    @SerializedName("Count_Applicant")
    @Expose
    private String countApplicant;
    @SerializedName("Count_Admission")
    @Expose
    private String countAdmission;
    @SerializedName("Count_Male")
    @Expose
    private String countMale;
    @SerializedName("Count_Female")
    @Expose
    private String countFemale;
    @SerializedName("Count_Dropout")
    @Expose
    private String countDropout;
    @SerializedName("Count_Student")
    @Expose
    private String countStudent;
    @SerializedName("Dashboard_Status")
    @Expose
    private String dashboardStatus;

    public Integer getDashboardID() {
        return dashboardID;
    }

    public void setDashboardID(Integer dashboardID) {
        this.dashboardID = dashboardID;
    }

    public String getSandboxID() {
        return sandboxID;
    }

    public void setSandboxID(String sandboxID) {
        this.sandboxID = sandboxID;
    }

    public String getSandboxName() {
        return sandboxName;
    }

    public void setSandboxName(String sandboxName) {
        this.sandboxName = sandboxName;
    }

    public String getCountInstitute() {
        return countInstitute;
    }

    public void setCountInstitute(String countInstitute) {
        this.countInstitute = countInstitute;
    }

    public String getCountVillage() {
        return countVillage;
    }

    public void setCountVillage(String countVillage) {
        this.countVillage = countVillage;
    }

    public String getCountApplicant() {
        return countApplicant;
    }

    public void setCountApplicant(String countApplicant) {
        this.countApplicant = countApplicant;
    }

    public String getCountAdmission() {
        return countAdmission;
    }

    public void setCountAdmission(String countAdmission) {
        this.countAdmission = countAdmission;
    }

    public String getCountMale() {
        return countMale;
    }

    public void setCountMale(String countMale) {
        this.countMale = countMale;
    }

    public String getCountFemale() {
        return countFemale;
    }

    public void setCountFemale(String countFemale) {
        this.countFemale = countFemale;
    }

    public String getCountDropout() {
        return countDropout;
    }

    public void setCountDropout(String countDropout) {
        this.countDropout = countDropout;
    }

    public String getCountStudent() {
        return countStudent;
    }

    public void setCountStudent(String countStudent) {
        this.countStudent = countStudent;
    }

    public String getDashboardStatus() {
        return dashboardStatus;
    }

    public void setDashboardStatus(String dashboardStatus) {
        this.dashboardStatus = dashboardStatus;
    }

}
