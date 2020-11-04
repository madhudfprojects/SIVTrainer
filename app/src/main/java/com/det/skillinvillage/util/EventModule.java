package com.det.skillinvillage.util;

import java.util.ArrayList;

/**
 * Created by User on 4/25/2018.
 */

public class EventModule {
    public String strDate;
    public String strStartTime;
    public String strEndTime;
    public String strFacultyName;
    public String strCohort;
    public String strClassroom;
    public String strModule;
    public String strFellowship;
    public boolean eventUpdate;



    public static ArrayList<EventModule> event_module_arr=new ArrayList<>();
    public EventModule(String strDate, String strStartTime, String strEndTime, String strFacultyName, String strCohort, String strClassroom, String strModule, String strFellowship, boolean eventUpdate) {
        this.strDate = strDate;
        this.strStartTime = strStartTime;
        this.strEndTime = strEndTime;
        this.strFacultyName = strFacultyName;
        this.strCohort = strCohort;
        this.strClassroom = strClassroom;
        this.strModule = strModule;
        this.strFellowship = strFellowship;
        this.eventUpdate = eventUpdate;
    }


}
