package com.det.skillinvillage;

import java.util.ArrayList;

public class LessionPlanDoc_Module {
    public String Document_ID;
    public String Document_Date;
    public String Document_Time;
    public String Document_Name;
    public String Document_Path;
    public String Document_Type;
    public String Document_Status;
    public String Document_Verification;
    public String TopicLevelID;


    public LessionPlanDoc_Module(String document_ID, String document_Date, String document_Time, String document_Name, String document_Path, String document_Type, String document_Status,String document_verification,String str_topiclevelid) {
        Document_ID = document_ID;
        Document_Date = document_Date;
        Document_Time = document_Time;
        Document_Name = document_Name;
        Document_Path = document_Path;
        Document_Type = document_Type;
        Document_Status = document_Status;
        Document_Verification=document_verification;
        TopicLevelID=str_topiclevelid;

    }
    public static ArrayList<LessionPlanDoc_Module> listview_arr=new ArrayList<LessionPlanDoc_Module>();
    public String getDocument_Verification() {
        return Document_Verification;
    }

    public void setDocument_Verification(String document_Verification) {
        Document_Verification = document_Verification;
    }

    public String getTopicLevelID() {
        return TopicLevelID;
    }

    public void setTopicLevelID(String topicLevelID) {
        TopicLevelID = topicLevelID;
    }

    public String getDocument_ID() {
        return Document_ID;
    }

    public void setDocument_ID(String document_ID) {
        Document_ID = document_ID;
    }

    public String getDocument_Date() {
        return Document_Date;
    }

    public void setDocument_Date(String document_Date) {
        Document_Date = document_Date;
    }

    public String getDocument_Time() {
        return Document_Time;
    }

    public void setDocument_Time(String document_Time) {
        Document_Time = document_Time;
    }

    public String getDocument_Name() {
        return Document_Name;
    }

    public void setDocument_Name(String document_Name) {
        Document_Name = document_Name;
    }

    public String getDocument_Path() {
        return Document_Path;
    }

    public void setDocument_Path(String document_Path) {
        Document_Path = document_Path;
    }

    public String getDocument_Type() {
        return Document_Type;
    }

    public void setDocument_Type(String document_Type) {
        Document_Type = document_Type;
    }

    public String getDocument_Status() {
        return Document_Status;
    }

    public void setDocument_Status(String document_Status) {
        Document_Status = document_Status;
    }
}
