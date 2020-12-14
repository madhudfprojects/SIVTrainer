package com.det.skillinvillage.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentData_ResponseList {
    @SerializedName("Student_ID")
    @Expose
    private Integer studentID;
    @SerializedName("Attendance_Status")
    @Expose
    private String attendanceStatus;
    @SerializedName("Student_Email")
    @Expose
    private Object studentEmail;
    @SerializedName("Student_Name")
    @Expose
    private Object studentName;
    @SerializedName("Application_No")
    @Expose
    private Object applicationNo;
    @SerializedName("Student_Gender")
    @Expose
    private Object studentGender;
    @SerializedName("Learning_Mode")
    @Expose
    private Object learningMode;

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public Object getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(Object studentEmail) {
        this.studentEmail = studentEmail;
    }

    public Object getStudentName() {
        return studentName;
    }

    public void setStudentName(Object studentName) {
        this.studentName = studentName;
    }

    public Object getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(Object applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Object getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(Object studentGender) {
        this.studentGender = studentGender;
    }

    public Object getLearningMode() {
        return learningMode;
    }

    public void setLearningMode(Object learningMode) {
        this.learningMode = learningMode;
    }
}
