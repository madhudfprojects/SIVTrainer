package com.det.skillinvillage;

import android.support.annotation.NonNull;

public class Class_Assessment_Level {

    String level_assessmentid;
    String level_assessmentname;
    String inst_assessmentid;

    public Class_Assessment_Level(){}

    public String getLevel_assessmentid() {
        return level_assessmentid;
    }

    public void setLevel_assessmentid(String level_assessmentid) {
        this.level_assessmentid = level_assessmentid;
    }

    public String getLevel_assessmentname() {
        return level_assessmentname;
    }

    public void setLevel_assessmentname(String level_assessmentname) {
        this.level_assessmentname = level_assessmentname;
    }

    public String getInst_assessmentid() {
        return inst_assessmentid;
    }

    public void setInst_assessmentid(String inst_assessmentid) {
        this.inst_assessmentid = inst_assessmentid;
    }

    @NonNull
    @Override
    public String toString() {
        return level_assessmentname;
    }

}
