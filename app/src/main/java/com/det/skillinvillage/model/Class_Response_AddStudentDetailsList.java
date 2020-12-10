package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_Response_AddStudentDetailsList {
    @SerializedName("Student_ID")
    @Expose
    private Integer studentID;
    @SerializedName("Application_No")
    @Expose
    private String applicationNo;
    @SerializedName("Sandbox_ID")
    @Expose
    private Integer sandboxID;
    @SerializedName("Academic_ID")
    @Expose
    private Integer academicID;
    @SerializedName("Cluster_ID")
    @Expose
    private Integer clusterID;
    @SerializedName("Institute_ID")
    @Expose
    private Integer instituteID;
    @SerializedName("School_ID")
    @Expose
    private Integer schoolID;
    @SerializedName("Level_ID")
    @Expose
    private Integer levelID;
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
    private Object admissionFee;
    @SerializedName("Created_Date")
    @Expose
    private String createdDate;
    @SerializedName("Created_By")
    @Expose
    private String createdBy;
    @SerializedName("File_Name")
    @Expose
    private Object fileName;
    @SerializedName("Receipt_Manual")
    @Expose
    private Object receiptManual;
    @SerializedName("Learning_Mode")
    @Expose
    private String learningMode;
    @SerializedName("Marks")
    @Expose
    private Object marks;

    @SerializedName("Temp_ID")
    @Expose
    private Object Temp_ID;

    public Object getTemp_ID() {
        return Temp_ID;
    }

    public void setTemp_ID(Object temp_ID) {
        Temp_ID = temp_ID;
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

    public Integer getSandboxID() {
        return sandboxID;
    }

    public void setSandboxID(Integer sandboxID) {
        this.sandboxID = sandboxID;
    }

    public Integer getAcademicID() {
        return academicID;
    }

    public void setAcademicID(Integer academicID) {
        this.academicID = academicID;
    }

    public Integer getClusterID() {
        return clusterID;
    }

    public void setClusterID(Integer clusterID) {
        this.clusterID = clusterID;
    }

    public Integer getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(Integer instituteID) {
        this.instituteID = instituteID;
    }

    public Integer getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }

    public Integer getLevelID() {
        return levelID;
    }

    public void setLevelID(Integer levelID) {
        this.levelID = levelID;
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

    public Object getAdmissionFee() {
        return admissionFee;
    }

    public void setAdmissionFee(Object admissionFee) {
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

    public Object getFileName() {
        return fileName;
    }

    public void setFileName(Object fileName) {
        this.fileName = fileName;
    }

    public Object getReceiptManual() {
        return receiptManual;
    }

    public void setReceiptManual(Object receiptManual) {
        this.receiptManual = receiptManual;
    }

    public String getLearningMode() {
        return learningMode;
    }

    public void setLearningMode(String learningMode) {
        this.learningMode = learningMode;
    }

    public Object getMarks() {
        return marks;
    }

    public void setMarks(Object marks) {
        this.marks = marks;
    }

}
