package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_AssessmentStudentDataList {
    @SerializedName("Marks_ID")
    @Expose
    private Integer studentID;
    @SerializedName("Student_Name")
    @Expose
    private String studentName;
    @SerializedName("Application_No")
    @Expose
    private String applicationNo;
    @SerializedName("Assessment_Marks")
    @Expose
    private String assesmentMarks;
    @SerializedName("Assessment_Remarks")
    @Expose
    private Object assesmentRemarks;
    @SerializedName("Marks_Status")
    @Expose
    private String marksStatus;
    @SerializedName("Assessment_Entry")
    @Expose
    private String assesmentEntry;

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getAssesmentMarks() {
        return assesmentMarks;
    }

    public void setAssesmentMarks(String assesmentMarks) {
        this.assesmentMarks = assesmentMarks;
    }

    public Object getAssesmentRemarks() {
        return assesmentRemarks;
    }

    public void setAssesmentRemarks(Object assesmentRemarks) {
        this.assesmentRemarks = assesmentRemarks;
    }

    public String getMarksStatus() {
        return marksStatus;
    }

    public void setMarksStatus(String marksStatus) {
        this.marksStatus = marksStatus;
    }

    public String getAssesmentEntry() {
        return assesmentEntry;
    }

    public void setAssesmentEntry(String assesmentEntry) {
        this.assesmentEntry = assesmentEntry;
    }

}

