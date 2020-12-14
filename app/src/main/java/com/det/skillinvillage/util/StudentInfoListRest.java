package com.det.skillinvillage.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentInfoListRest {
    @SerializedName("Student_ID")
    @Expose
    private String studentID;
    @SerializedName("Attendance_Status")
    @Expose
    private String attendanceStatus;
    @SerializedName("Student_Email")
    @Expose
    private String studentEmail;
    @SerializedName("Student_Name")
    @Expose
    private String studentName;
    @SerializedName("Application_No")
    @Expose
    private Object applicationNo;
    @SerializedName("Student_Gender")
    @Expose
    private String studentGender;
    @SerializedName("Learning_Mode")
    @Expose
    private String learningMode;

    String Pre_Ab;
    int _id;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Object getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(Object applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public String getLearningMode() {
        return learningMode;
    }

    public void setLearningMode(String learningMode) {
        this.learningMode = learningMode;
    }

    public String getPre_Ab() {
        return Pre_Ab;
    }

    public void setPre_Ab(String pre_Ab) {
        Pre_Ab = pre_Ab;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public StudentInfoListRest(){

    }
}
