package com.det.skillinvillage;

import android.support.annotation.NonNull;

public class Class_LevelDetails {

    int id;
    String level_id;
    String level_name;
    String level_SandID;
    String level_AcaID;
    String level_ClustID;
    String level_InstID;
    String level_admissionfee;


    public Class_LevelDetails(){}
    public Class_LevelDetails(int id, String level_id, String level_name) {
        this.id = id;
        this.level_id = level_id;
        this.level_name = level_name;
    }

    public Class_LevelDetails(String level_id, String level_name) {
        this.level_id = level_id;
        this.level_name = level_name;
    }

    public String getLevel_id() {
        return level_id;
    }

    public void setLevel_id(String level_id) {
        this.level_id = level_id;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public String getLevel_SandID() {
        return level_SandID;
    }

    public void setLevel_SandID(String level_SandID) {
        this.level_SandID = level_SandID;
    }

    public String getLevel_AcaID() {
        return level_AcaID;
    }

    public void setLevel_AcaID(String level_AcaID) {
        this.level_AcaID = level_AcaID;
    }

    public String getLevel_ClustID() {
        return level_ClustID;
    }

    public void setLevel_ClustID(String level_ClustID) {
        this.level_ClustID = level_ClustID;
    }

    public String getLevel_InstID() {
        return level_InstID;
    }

    public void setLevel_InstID(String level_InstID) {
        this.level_InstID = level_InstID;
    }

    public String getLevel_admissionfee() {
        return level_admissionfee;
    }

    public void setLevel_admissionfee(String level_admissionfee) {
        this.level_admissionfee = level_admissionfee;
    }
    @NonNull
    @Override
    public String toString() {
        return level_name;
    }
}
