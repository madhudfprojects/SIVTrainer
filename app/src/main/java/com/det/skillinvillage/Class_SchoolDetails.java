package com.det.skillinvillage;

public class Class_SchoolDetails {
    int id;
    String school_id;
    String school_name;
    String school_SandID;
    String school_AcaID;
    String school_ClustID;


    public Class_SchoolDetails(){}

    public Class_SchoolDetails(int id, String school_id, String school_name) {
        this.id = id;
        this.school_id = school_id;
        this.school_name = school_name;
    }

    public Class_SchoolDetails(String school_id, String school_name) {
        this.school_id = school_id;
        this.school_name = school_name;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getSchool_name() {
        return school_name;
    }

    public String getSchool_SandID() {
        return school_SandID;
    }

    public void setSchool_SandID(String school_SandID) {
        this.school_SandID = school_SandID;
    }

    public String getSchool_AcaID() {
        return school_AcaID;
    }

    public void setSchool_AcaID(String school_AcaID) {
        this.school_AcaID = school_AcaID;
    }

    public String getSchool_ClustID() {
        return school_ClustID;
    }

    public void setSchool_ClustID(String school_ClustID) {
        this.school_ClustID = school_ClustID;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    @Override
    public String toString() {
        return school_name;
    }
}
