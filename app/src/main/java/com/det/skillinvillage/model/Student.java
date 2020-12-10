package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
    public StudentList(String academicID, String academicName, String admissionFee, String applicationNo, String balanceFee, String birthDate, String clusterID, String clusterName, String createdDate, String education, String fatherName, String gender, String instituteName, String instituteID, String levelID, String levelName, String marks4, String marks5, String marks6, String marks7s, String marks8, String motherName, String paidFee, String receiptNo, Integer sandboxID, String sandboxName, Integer schoolID, String schoolName, String studentAadhar, String studentPhoto, String studentStatus, String studentName,String stu_id) {

        this.academicID = Integer.valueOf(academicID);
        this.academicName=academicName;
        this.admissionFee=admissionFee;
        this.applicationNo=applicationNo;
        this.balanceFee=balanceFee;
        this.birthDate=birthDate;
        this.clusterID= Integer.valueOf(clusterID);
        this.clusterName=clusterName;
        this.createdDate=createdDate;
        this.education=education;
        this.fatherName=fatherName;
        this.gender=gender;
        this.instituteName=instituteName;
        this.instituteID= Integer.valueOf(instituteID);
        this.levelID= Integer.valueOf(levelID);
        this.levelName=levelName;
        this.marks4=marks4;
        this.marks5=marks5;
        this.marks6=marks6;
        this.marks7=marks7;
        this.marks8=marks8;
        this.motherName=motherName;

        this.paidFee=paidFee;
        this.receiptNo=receiptNo;
        this.sandboxID=sandboxID;
        this.sandboxName=sandboxName;
        this.schoolID=schoolID;
        this.schoolName=schoolName;
        this.studentAadhar=studentAadhar;
        this.studentPhoto=studentPhoto;
        this.studentStatus=studentStatus;
        this.studentName=studentName;
        this.studentID= stu_id;


    }

 */
public class Student {
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("lstCount1")
    @Expose
    private List<StudCountList> lstCount1 = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<StudCountList> getLstCount1() {
        return lstCount1;
    }

    public void setLstCount1(List<StudCountList> lstCount1) {
        this.lstCount1 = lstCount1;
    }
}
