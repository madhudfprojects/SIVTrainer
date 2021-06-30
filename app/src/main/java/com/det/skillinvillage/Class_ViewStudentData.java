package com.det.skillinvillage;

import android.support.annotation.NonNull;

public class Class_ViewStudentData {

    int id;
    String name;
    String mobileno;
    String applicationNumber;
    String institutionName;
    String levelname;
    String balanceFee;
    String sandboxid;
    String academicid;
    String clusterid;
    String instituteid;
    String levelid;
    String appl_status;
    String studentID;

    public Class_ViewStudentData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getSandboxid() {
        return sandboxid;
    }

    public void setSandboxid(String sandboxid) {
        this.sandboxid = sandboxid;
    }

    public String getAcademicid() {
        return academicid;
    }

    public void setAcademicid(String academicid) {
        this.academicid = academicid;
    }

    public String getClusterid() {
        return clusterid;
    }

    public void setClusterid(String clusterid) {
        this.clusterid = clusterid;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public String getBalanceFee() {
        return balanceFee;
    }

    public void setBalanceFee(String balanceFee) {
        this.balanceFee = balanceFee;
    }

    public String getInstituteid() {
        return instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

    public String getLevelid() {
        return levelid;
    }

    public void setLevelid(String levelid) {
        this.levelid = levelid;
    }

    public String getAppl_status() {
        return appl_status;
    }

    public void setAppl_status(String appl_status) {
        this.appl_status = appl_status;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    @NonNull
    @Override
    public String toString() {
        return name;
    }


}


