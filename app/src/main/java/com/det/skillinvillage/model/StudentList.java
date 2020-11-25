package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentList {

    @SerializedName("Student_ID")
    @Expose
    private Integer studentID;
    @SerializedName("Application_No")
    @Expose
    private String applicationNo;
    @SerializedName("Sandbox_ID")
    @Expose
    private Integer sandboxID;
    @SerializedName("Sandbox_Name")
    @Expose
    private String sandboxName;
    @SerializedName("Academic_ID")
    @Expose
    private Integer academicID;
    @SerializedName("Academic_Name")
    @Expose
    private String academicName;
    @SerializedName("Cluster_ID")
    @Expose
    private Integer clusterID;
    @SerializedName("Cluster_Name")
    @Expose
    private String clusterName;
    @SerializedName("Institute_ID")
    @Expose
    private Integer instituteID;
    @SerializedName("Institute_Name")
    @Expose
    private String instituteName;
    @SerializedName("School_ID")
    @Expose
    private Integer schoolID;
    @SerializedName("School_Name")
    @Expose
    private String schoolName;
    @SerializedName("Level_ID")
    @Expose
    private Integer levelID;
    @SerializedName("Level_Name")
    @Expose
    private String levelName;
    @SerializedName("Student_Name")
    @Expose
    private String studentName;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Student_Status")
    @Expose
    private String studentStatus;
    @SerializedName("Admission_Fee")
    @Expose
    private String admissionFee;
    @SerializedName("Paid_Fee")
    @Expose
    private String paidFee;
    @SerializedName("Balance_Fee")
    @Expose
    private String balanceFee;
    @SerializedName("Student_Photo")
    @Expose
    private String studentPhoto;
    @SerializedName("Created_Date")
    @Expose
    private String createdDate;
    @SerializedName("Birth_Date")
    @Expose
    private String birthDate;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Education")
    @Expose
    private String education;
    @SerializedName("Marks_4")
    @Expose
    private String marks4;
    @SerializedName("Marks_5")
    @Expose
    private String marks5;
    @SerializedName("Marks_6")
    @Expose
    private String marks6;
    @SerializedName("Marks_7")
    @Expose
    private String marks7;
    @SerializedName("Marks_8")
    @Expose
    private String marks8;
    @SerializedName("Father_Name")
    @Expose
    private String fatherName;
    @SerializedName("Mother_Name")
    @Expose
    private String motherName;
    @SerializedName("Student_Aadhar")
    @Expose
    private String studentAadhar;
    @SerializedName("Receipt_No")
    @Expose
    private String receiptNo;

    public StudentList(){}

    public StudentList(String academicID, String academicName, String admissionFee, String applicationNo, String balanceFee, String birthDate, String clusterID, String clusterName, String createdDate, String education, String fatherName, String gender, String instituteName, String instituteID, String levelID, String levelName, String marks4, String marks5, String marks6, byte[] marks7s, String marks8, String motherName, String paidFee, String receiptNo, Integer sandboxID, String sandboxName, Integer schoolID, String schoolName, String studentAadhar, String studentPhoto, String studentStatus,String studentName) {
        this.academicID = Integer.valueOf(academicID);
        this.academicName = academicName;
        this.clusterID = Integer.valueOf(clusterID);
        this.clusterName = clusterName;
        this.instituteID = Integer.valueOf(instituteID);
        this.instituteName = instituteName;
        this.schoolID = schoolID;
        this.schoolName = schoolName;
        this.levelID = Integer.valueOf(levelID);
        this.levelName = levelName;
        this.studentName = studentName;
        this.mobile = mobile;
        this.studentStatus = studentStatus;
        this.admissionFee = admissionFee;
        this.paidFee = paidFee;
        this.balanceFee = balanceFee;
        this.studentPhoto = studentPhoto;
        this.createdDate = createdDate;
        this.birthDate = birthDate;
        this.gender = gender;
        this.education = education;
        this.marks4 = marks4;
        this.marks5 = marks5;
        this.marks6 = marks6;
        this.marks7 = marks7;
        this.marks8 = marks8;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.studentAadhar = studentAadhar;
        this.receiptNo = receiptNo;
        this.studentName=studentName;

    }


    public Integer getStudentID() {
        return studentID;
    }

    public StudentList(Integer studentID, String applicationNo, Integer sandboxID, String sandboxName, Integer academicID, String academicName, Integer clusterID, String clusterName, Integer instituteID, String instituteName, Integer schoolID, String schoolName, Integer levelID, String levelName, String studentName, String mobile, String studentStatus, String admissionFee, String paidFee, String balanceFee, String studentPhoto, String createdDate, String birthDate, String gender, String education, String marks4, String marks5, String marks6, String marks7, String marks8, String fatherName, String motherName, String studentAadhar, String receiptNo) {
        this.studentID = studentID;
        this.applicationNo = applicationNo;
        this.sandboxID = sandboxID;
        this.sandboxName = sandboxName;
        this.academicID = academicID;
        this.academicName = academicName;
        this.clusterID = clusterID;
        this.clusterName = clusterName;
        this.instituteID = instituteID;
        this.instituteName = instituteName;
        this.schoolID = schoolID;
        this.schoolName = schoolName;
        this.levelID = levelID;
        this.levelName = levelName;
        this.studentName = studentName;
        this.mobile = mobile;
        this.studentStatus = studentStatus;
        this.admissionFee = admissionFee;
        this.paidFee = paidFee;
        this.balanceFee = balanceFee;
        this.studentPhoto = studentPhoto;
        this.createdDate = createdDate;
        this.birthDate = birthDate;
        this.gender = gender;
        this.education = education;
        this.marks4 = marks4;
        this.marks5 = marks5;
        this.marks6 = marks6;
        this.marks7 = marks7;
        this.marks8 = marks8;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.studentAadhar = studentAadhar;
        this.receiptNo = receiptNo;
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

    public String getSandboxName() {
        return sandboxName;
    }

    public void setSandboxName(String sandboxName) {
        this.sandboxName = sandboxName;
    }

    public Integer getAcademicID() {
        return academicID;
    }

    public void setAcademicID(Integer academicID) {
        this.academicID = academicID;
    }

    public String getAcademicName() {
        return academicName;
    }

    public void setAcademicName(String academicName) {
        this.academicName = academicName;
    }

    public Integer getClusterID() {
        return clusterID;
    }

    public void setClusterID(Integer clusterID) {
        this.clusterID = clusterID;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public Integer getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(Integer instituteID) {
        this.instituteID = instituteID;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public Integer getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getLevelID() {
        return levelID;
    }

    public void setLevelID(Integer levelID) {
        this.levelID = levelID;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public String getAdmissionFee() {
        return admissionFee;
    }

    public void setAdmissionFee(String admissionFee) {
        this.admissionFee = admissionFee;
    }

    public String getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(String paidFee) {
        this.paidFee = paidFee;
    }

    public String getBalanceFee() {
        return balanceFee;
    }

    public void setBalanceFee(String balanceFee) {
        this.balanceFee = balanceFee;
    }

    public String getStudentPhoto() {
        return studentPhoto;
    }

    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMarks4() {
        return marks4;
    }

    public void setMarks4(String marks4) {
        this.marks4 = marks4;
    }

    public String getMarks5() {
        return marks5;
    }

    public void setMarks5(String marks5) {
        this.marks5 = marks5;
    }

    public String getMarks6() {
        return marks6;
    }

    public void setMarks6(String marks6) {
        this.marks6 = marks6;
    }

    public String getMarks7() {
        return marks7;
    }

    public void setMarks7(String marks7) {
        this.marks7 = marks7;
    }

    public String getMarks8() {
        return marks8;
    }

    public void setMarks8(String marks8) {
        this.marks8 = marks8;
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

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

}
