package com.det.skillinvillage;

public class Class_InsituteDetails {

    int id;
    String institute_id;
    String institute_name;
    String inst_SandID;
    String inst_AcaID;
    String inst_ClustID;

    public String getInst_SandID() {
        return inst_SandID;
    }

    public void setInst_SandID(String inst_SandID) {
        this.inst_SandID = inst_SandID;
    }

    public String getInst_AcaID() {
        return inst_AcaID;
    }

    public void setInst_AcaID(String inst_AcaID) {
        this.inst_AcaID = inst_AcaID;
    }

    public String getInst_ClustID() {
        return inst_ClustID;
    }

    public void setInst_ClustID(String inst_ClustID) {
        this.inst_ClustID = inst_ClustID;
    }

    public Class_InsituteDetails(){}
    public Class_InsituteDetails(int id, String institute_id, String institute_name) {
        this.id = id;
        this.institute_id = institute_id;
        this.institute_name = institute_name;
    }

    public Class_InsituteDetails(String institute_id, String institute_name) {
        this.institute_id = institute_id;
        this.institute_name = institute_name;
    }

    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }

    public String getInstitute_name() {
        return institute_name;
    }

    public void setInstitute_name(String institute_name) {
        this.institute_name = institute_name;
    }

    @Override
    public String toString() {
        return institute_name;
    }
}
