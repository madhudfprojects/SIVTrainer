package com.det.skillinvillage;

public class Class_academicDetails {


    int id;
    String academic_id;
    String academic_name;
    String aca_Sandbox_id;


    public Class_academicDetails() {

    }

    public Class_academicDetails(String academic_id, String academic_name) {
        this.academic_id = academic_id;
        this.academic_name = academic_name;
    }


    public String getAca_Sandbox_id() {
        return aca_Sandbox_id;
    }

    public void setAca_Sandbox_id(String aca_Sandbox_id) {
        this.aca_Sandbox_id = aca_Sandbox_id;
    }

    public String getAcademic_id() {
        return academic_id;
    }

    public void setAcademic_id(String academic_id) {
        this.academic_id = academic_id;
    }

    public String getAcademic_name() {
        return academic_name;
    }

    public void setAcademic_name(String academic_name) {
        this.academic_name = academic_name;
    }


    @Override
    public String toString() {
        return academic_name;
    }
}