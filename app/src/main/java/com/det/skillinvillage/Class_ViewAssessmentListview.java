package com.det.skillinvillage;

import java.util.ArrayList;

public class Class_ViewAssessmentListview {


    String assessmentID;
    String assessmentName;
    String assessmentDate;
    String assessmentStatus;
    String assessment_levelName;
    String assessment_instituteName;
    String assessment_instituteID;
    String assessment_levelID;
    String assessment_presentstudentcount;
    String assessment_totalstudentcount;
    String assessment_maxmarks;
    String assessment_save;


    public String getAssessment_maxmarks() {
        return assessment_maxmarks;
    }

    public void setAssessment_maxmarks(String assessment_maxmarks) {
        this.assessment_maxmarks = assessment_maxmarks;
    }

    public static ArrayList<Class_Assessments_List> assesmentlistview_info_arr = new ArrayList<Class_Assessments_List>();

    public Class_ViewAssessmentListview() {
    }

    public String getAssessment_save() {
        return assessment_save;
    }

    public void setAssessment_save(String assessment_save) {
        this.assessment_save = assessment_save;
    }

    public Class_ViewAssessmentListview(String assessmentID, String assessmentName, String assessmentDate, String assessmentStatus, String assessment_levelName, String assessment_instituteName, String assessment_instituteID, String assessment_levelID, String assessment_presentstudentcount, String assessment_totalstudentcount, String assessment_maxmarks) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.assessmentDate = assessmentDate;
        this.assessmentStatus = assessmentStatus;
        this.assessment_levelName = assessment_levelName;
        this.assessment_instituteName = assessment_instituteName;
        this.assessment_instituteID = assessment_instituteID;
        this.assessment_levelID = assessment_levelID;

        this.assessment_presentstudentcount = assessment_presentstudentcount;
        this.assessment_totalstudentcount = assessment_totalstudentcount;
        this.assessment_maxmarks = assessment_maxmarks;
    }

    public String getAssessment_presentstudentcount() {
        return assessment_presentstudentcount;
    }

    public void setAssessment_presentstudentcount(String assessment_presentstudentcount) {
        this.assessment_presentstudentcount = assessment_presentstudentcount;
    }

    public String getAssessment_totalstudentcount() {
        return assessment_totalstudentcount;
    }

    public void setAssessment_totalstudentcount(String assessment_totalstudentcount) {
        this.assessment_totalstudentcount = assessment_totalstudentcount;
    }

    public String getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(String assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(String assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public String getAssessmentStatus() {
        return assessmentStatus;
    }

    public void setAssessmentStatus(String assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }

    public String getAssessment_levelName() {
        return assessment_levelName;
    }

    public void setAssessment_levelName(String assessment_levelName) {
        this.assessment_levelName = assessment_levelName;
    }

    public String getAssessment_instituteName() {
        return assessment_instituteName;
    }

    public void setAssessment_instituteName(String assessment_instituteName) {
        this.assessment_instituteName = assessment_instituteName;
    }

    public String getAssessment_instituteID() {
        return assessment_instituteID;
    }

    public void setAssessment_instituteID(String assessment_instituteID) {
        this.assessment_instituteID = assessment_instituteID;
    }

    public String getAssessment_levelID() {
        return assessment_levelID;
    }

    public void setAssessment_levelID(String assessment_levelID) {
        this.assessment_levelID = assessment_levelID;
    }
}