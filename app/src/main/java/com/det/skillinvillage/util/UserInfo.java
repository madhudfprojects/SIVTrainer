package com.det.skillinvillage.util;

import java.util.ArrayList;

/**
 * Created by User on 6/4/2018.
 */

public class UserInfo {

    public String Schedule_ID;
    public String Lavel_ID;
    public String Schedule_Date;
    public String End_Time;
    public String Start_Time;
    public String Schedule_Session;
    public String Schedule_Status;
    public String Subject_Name;

    public String Lavel_Name;
    public String Leason_Name;
    public String Cluster_Name;
    public String Institute_Name;




    //*-------------------------
    public static ArrayList<UserInfo> user_info_arr=new ArrayList<UserInfo>();

  /*  public UserInfo(String schedule_ID, String lavel_ID, String schedule_Date, String end_Time, String start_Time, String schedule_Session, String schedule_Status,String subject_Name) {
        Schedule_ID = schedule_ID;
        Lavel_ID = lavel_ID;
        Schedule_Date = schedule_Date;
        End_Time = end_Time;
        Start_Time = start_Time;
        Schedule_Session = schedule_Session;
        Schedule_Status =schedule_Status;
        Subject_Name = subject_Name;
    }*/

    public UserInfo(String schedule_ID, String lavel_ID, String schedule_Date, String end_Time, String start_Time, String schedule_Session, String schedule_Status, String subject_Name, String lavel_Name, String leason_Name, String cluster_Name, String institute_Name) {
        Schedule_ID = schedule_ID;
        Lavel_ID = lavel_ID;
        Schedule_Date = schedule_Date;
        End_Time = end_Time;
        Start_Time = start_Time;
        Schedule_Session = schedule_Session;
        Schedule_Status = schedule_Status;
        Subject_Name = subject_Name;
        Lavel_Name = lavel_Name;
        Leason_Name = leason_Name;
        Cluster_Name = cluster_Name;
        Institute_Name = institute_Name;
    }

    public UserInfo() {

    }

    public String getSubject_Name() {
        return Subject_Name;
    }

    public void setSubject_Name(String subject_Name) {
        Subject_Name = subject_Name;
    }

    public String getSchedule_Status() {
        return Schedule_Status;
    }

    public void setSchedule_Status(String schedule_Status) {
        Schedule_Status = schedule_Status;
    }

    public String getSchedule_ID() {
        return Schedule_ID;
    }

    public void setSchedule_ID(String schedule_ID) {
        Schedule_ID = schedule_ID;
    }

    public String getLavel_ID() {
        return Lavel_ID;
    }

    public void setLavel_ID(String lavel_ID) {
        Lavel_ID = lavel_ID;
    }

    public String getSchedule_Date() {
        return Schedule_Date;
    }

    public void setSchedule_Date(String schedule_Date) {
        Schedule_Date = schedule_Date;
    }

    public String getEnd_Time() {
        return End_Time;
    }

    public void setEnd_Time(String end_Time) {
        End_Time = end_Time;
    }

    public String getStart_Time() {
        return Start_Time;
    }

    public void setStart_Time(String start_Time) {
        Start_Time = start_Time;
    }

    public String getSchedule_Session() {
        return Schedule_Session;
    }

    public void setSchedule_Session(String schedule_Session) {
        Schedule_Session = schedule_Session;
    }

    public String getLavel_Name() {
        return Lavel_Name;
    }

    public void setLavel_Name(String lavel_Name) {
        Lavel_Name = lavel_Name;
    }

    public String getLeason_Name() {
        return Leason_Name;
    }

    public void setLeason_Name(String leason_Name) {
        Leason_Name = leason_Name;
    }

    public String getCluster_Name() {
        return Cluster_Name;
    }

    public void setCluster_Name(String cluster_Name) {
        Cluster_Name = cluster_Name;
    }

    public String getInstitute_Name() {
        return Institute_Name;
    }

    public void setInstitute_Name(String institute_Name) {
        Institute_Name = institute_Name;
    }
}
