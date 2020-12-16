package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetScheduleLessonPlanResponseList {
    @SerializedName("Schedule_ID")
    @Expose
    private String scheduleID;
    @SerializedName("Schedule_Date")
    @Expose
    private String scheduleDate;
    @SerializedName("Sandbox_Name")
    @Expose
    private String sandboxName;
    @SerializedName("Cluster_Name")
    @Expose
    private String clusterName;
    @SerializedName("Academic_Name")
    @Expose
    private String academicName;
    @SerializedName("Institute_Name")
    @Expose
    private String instituteName;
    @SerializedName("Level_Name")
    @Expose
    private String levelName;
    @SerializedName("Lesson_ID")
    @Expose
    private String lessonID;
    @SerializedName("Topic_ID")
    @Expose
    private String topicID;
    @SerializedName("Lesson_Name")
    @Expose
    private String lessonName;
    @SerializedName("Topic_Name")
    @Expose
    private String topicName;
    @SerializedName("Student_Count")
    @Expose
    private String studentCount;
    @SerializedName("Present_Count")
    @Expose
    private String presentCount;
    @SerializedName("Absent_Count")
    @Expose
    private String absentCount;
    @SerializedName("Lesson_Question")
    @Expose
    private List<LessonQuestion> lessonQuestion = null;

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getSandboxName() {
        return sandboxName;
    }

    public void setSandboxName(String sandboxName) {
        this.sandboxName = sandboxName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getAcademicName() {
        return academicName;
    }

    public void setAcademicName(String academicName) {
        this.academicName = academicName;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }

    public String getTopicID() {
        return topicID;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(String studentCount) {
        this.studentCount = studentCount;
    }

    public String getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(String presentCount) {
        this.presentCount = presentCount;
    }

    public String getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(String absentCount) {
        this.absentCount = absentCount;
    }

    public List<LessonQuestion> getLessonQuestion() {
        return lessonQuestion;
    }

    public void setLessonQuestion(List<LessonQuestion> lessonQuestion) {
        this.lessonQuestion = lessonQuestion;
    }

}
