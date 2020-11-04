package com.det.skillinvillage.util;

import java.util.ArrayList;

/**
 * Created by User on 4/26/2018.
 */

public class ListviewEvents {
    public String strScheduleId;
    public String strDate;
    public String strStartTime;
    public String strEndTime;
    public String strFacultyName;
    public String strCohort;
    public String strClassroom;
    public String strModule;
    public String strFellowship;
 //   public boolean eventUpdate;
    public String strstatus;
    public String strAttandence;

    public ListviewEvents() {
    }
    public static ArrayList<ListviewEvents> listview_info_arr=new ArrayList<ListviewEvents>();

    public ListviewEvents(String strScheduleId,String strDate, String strStartTime, String strEndTime, String strFacultyName, String strCohort, String strClassroom, String strModule, String strFellowship, String strstatus,String strAttandence) {
        this.strScheduleId = strScheduleId;
        this.strDate = strDate;
        this.strStartTime = strStartTime;
        this.strEndTime = strEndTime;
        this.strFacultyName = strFacultyName;
        this.strCohort = strCohort;
        this.strClassroom = strClassroom;
        this.strModule = strModule;
        this.strFellowship = strFellowship;
        this.strstatus=strstatus;
        this.strAttandence=strAttandence;
    }

    public String getStrScheduleId() {
        return strScheduleId;
    }

    public void setStrScheduleId(String strScheduleId) {
        this.strScheduleId = strScheduleId;
    }

    public String getStrAttandence() {
        return strAttandence;
    }

    public void setStrAttandence(String strAttandence) {
        this.strAttandence = strAttandence;
    }

    public String getStrstatus() {
        return strstatus;
    }

    public void setStrstatus(String strstatus) {
        this.strstatus = strstatus;
    }

    public String getStrFellowship() {
        return strFellowship;
    }

    public void setStrFellowship(String strFellowship) {
        this.strFellowship = strFellowship;
    }

    public String getStrDate() {
        return strDate;
    }

    public String getStrStartTime() {
        return strStartTime;
    }

    public String getStrEndTime() {
        return strEndTime;
    }

    public String getStrFacultyName() {
        return strFacultyName;
    }

    public String getStrCohort() {
        return strCohort;
    }

    public String getStrClassroom() {
        return strClassroom;
    }

    public String getStrModule() {
        return strModule;
    }


}
