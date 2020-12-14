package com.det.skillinvillage.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserInfoListRest {
    @SerializedName("Schedule_ID")
    @Expose
    public String scheduleID;
    @SerializedName("Lavel_ID")
    @Expose
    public String lavelID;
    @SerializedName("Leason_ID")
    @Expose
    public Integer leasonID;
    @SerializedName("Leason_Name")
    @Expose
    public String leasonName;
    @SerializedName("Level_Name")
    @Expose
    public String lavelName;
    @SerializedName("Cluster_Name")
    @Expose
    public String clusterName;
    @SerializedName("Institute_Name")
    @Expose
    public String instituteName;
    @SerializedName("Schedule_Date")
    @Expose
    public String scheduleDate;
    @SerializedName("Start_Time")
    @Expose
    public String startTime;
    @SerializedName("End_Time")
    @Expose
    public String endTime;
    @SerializedName("Schedule_Session")
    @Expose
    public String scheduleSession;
    @SerializedName("Subject_Name")
    @Expose
    public String subjectName;
    @SerializedName("Schedule_Status")
    @Expose
    public String scheduleStatus;

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getLavelID() {
        return lavelID;
    }

    public void setLavelID(String lavelID) {
        this.lavelID = lavelID;
    }

    public Integer getLeasonID() {
        return leasonID;
    }

    public void setLeasonID(Integer leasonID) {
        this.leasonID = leasonID;
    }

    public String getLeasonName() {
        return leasonName;
    }

    public void setLeasonName(String leasonName) {
        this.leasonName = leasonName;
    }

    public String getLavelName() {
        return lavelName;
    }

    public void setLavelName(String lavelName) {
        this.lavelName = lavelName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getScheduleSession() {
        return scheduleSession;
    }

    public void setScheduleSession(String scheduleSession) {
        this.scheduleSession = scheduleSession;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public static ArrayList<UserInfoListRest> user_info_arr=new ArrayList<UserInfoListRest>();

    public UserInfoListRest(String schedule_ID, String lavel_ID, String schedule_Date, String end_Time, String start_Time, String schedule_Session, String schedule_Status, String subject_Name, String lavel_Name, String leason_Name, String cluster_Name, String institute_Name) {
        scheduleID = schedule_ID;
        lavelID = lavel_ID;
        scheduleDate = schedule_Date;
        endTime = end_Time;
        startTime = start_Time;
        scheduleSession = schedule_Session;
        scheduleStatus = schedule_Status;
        subjectName = subject_Name;
        lavelName = lavel_Name;
        leasonName = leason_Name;
        clusterName = cluster_Name;
        instituteName = institute_Name;
    }

    public UserInfoListRest() {
    }
}
