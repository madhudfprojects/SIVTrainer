package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddStudentDetailsRequest {
    @SerializedName("Student_ID")
    @Expose
    private String studentID;
    @SerializedName("Sandbox_ID")
    @Expose
    private String sandboxID;
    @SerializedName("Academic_ID")
    @Expose
    private String academicID;
    @SerializedName("Cluster_ID")
    @Expose
    private String clusterID;
    @SerializedName("Institute_ID")
    @Expose
    private String instituteID;
    @SerializedName("Level_ID")
    @Expose
    private String levelID;
    @SerializedName("School_ID")
    @Expose
    private String schoolID;
    @SerializedName("File_Name")
    @Expose
    private String fileName;
    @SerializedName("Student_Name")
    @Expose
    private String studentName;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Birth_Date")
    @Expose
    private String birthDate;
    @SerializedName("Education")
    @Expose
    private String education;
    @SerializedName("Marks")
    @Expose
    private String marks;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Father_Name")
    @Expose
    private String fatherName;
    @SerializedName("Mother_Name")
    @Expose
    private String motherName;
    @SerializedName("Student_Aadhar")
    @Expose
    private String studentAadhar;
    @SerializedName("Student_Status")
    @Expose
    private String studentStatus;
    @SerializedName("Admission_Fee")
    @Expose
    private String admissionFee;
    @SerializedName("Created_Date")
    @Expose
    private String createdDate;
    @SerializedName("Created_By")
    @Expose
    private String createdBy;
    @SerializedName("Receipt_Manual")
    @Expose
    private String receiptManual;
    @SerializedName("Learning_Mode")
    @Expose
    private String learningMode;

    @SerializedName("Temp_ID")
    @Expose
    private String Temp_ID;

    @SerializedName("State_ID")
    @Expose
    private String State_ID;

//    @SerializedName("State_Name")
//    @Expose
//    private String State_Name;

    @SerializedName("District_ID")
    @Expose
    private String District_ID;

//    @SerializedName("District_Name")
//    @Expose
//    private String District_Name;

    @SerializedName("Taluk_ID")
    @Expose
    private String Taluk_ID;

//    @SerializedName("Taluk_Name")
//    @Expose
//    private String Taluk_Name;

    @SerializedName("Village_ID")
    @Expose
    private String Village_ID;

//    @SerializedName("Village_Name")
//    @Expose
//    private String Village_Name;

    @SerializedName("Address")
    @Expose
    private String address;





    @SerializedName("Application_Type")
    @Expose
    private String Application_Type;

    @SerializedName("Division_ID")
    @Expose
    private String Division_ID;

    public String getDivision_ID() {
        return Division_ID;
    }

    public void setDivision_ID(String division_ID) {
        Division_ID = division_ID;
    }

    public String getApplication_Type() {
        return Application_Type;
    }

    public void setApplication_Type(String application_Type) {
        Application_Type = application_Type;
    }

    public String getState_ID() {
        return State_ID;
    }

    public void setState_ID(String state_ID) {
        State_ID = state_ID;
    }

//    public String getState_Name() {
//        return State_Name;
//    }
//
//    public void setState_Name(String state_Name) {
//        State_Name = state_Name;
//    }

    public String getDistrict_ID() {
        return District_ID;
    }

    public void setDistrict_ID(String district_ID) {
        District_ID = district_ID;
    }

//    public String getDistrict_Name() {
//        return District_Name;
//    }
//
//    public void setDistrict_Name(String district_Name) {
//        District_Name = district_Name;
//    }
//
    public String getTaluk_ID() {
        return Taluk_ID;
    }

    public void setTaluk_ID(String taluk_ID) {
        Taluk_ID = taluk_ID;
    }

//    public String getTaluk_Name() {
//        return Taluk_Name;
//    }
//
//    public void setTaluk_Name(String taluk_Name) {
//        Taluk_Name = taluk_Name;
//    }

    public String getVillage_ID() {
        return Village_ID;
    }

    public void setVillage_ID(String village_ID) {
        Village_ID = village_ID;
    }

//    public String getVillage_Name() {
//        return Village_Name;
//    }
//
//    public void setVillage_Name(String village_Name) {
//        Village_Name = village_Name;
//    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTemp_ID() {
        return Temp_ID;
    }

    public void setTemp_ID(String temp_ID) {
        Temp_ID = temp_ID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getSandboxID() {
        return sandboxID;
    }

    public void setSandboxID(String sandboxID) {
        this.sandboxID = sandboxID;
    }

    public String getAcademicID() {
        return academicID;
    }

    public void setAcademicID(String academicID) {
        this.academicID = academicID;
    }

    public String getClusterID() {
        return clusterID;
    }

    public void setClusterID(String clusterID) {
        this.clusterID = clusterID;
    }

    public String getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(String instituteID) {
        this.instituteID = instituteID;
    }

    public String getLevelID() {
        return levelID;
    }

    public void setLevelID(String levelID) {
        this.levelID = levelID;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getStudentAadhar() {
        return studentAadhar;
    }

    public void setStudentAadhar(String studentAadhar) {
        this.studentAadhar = studentAadhar;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public String getAdmissionFee() {
        return admissionFee;
    }

    public void setAdmissionFee(String admissionFee) {
        this.admissionFee = admissionFee;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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

    public String getLearningMode() {
        return learningMode;
    }

    public void setLearningMode(String learningMode) {
        this.learningMode = learningMode;
    }


}
