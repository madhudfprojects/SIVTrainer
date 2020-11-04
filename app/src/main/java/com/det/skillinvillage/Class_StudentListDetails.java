package com.det.skillinvillage;

public class Class_StudentListDetails {
    int _id;
    String student_name;
    String student_mobileno;
    String student_applicationNumber;
    String student_institutionName;
    String student_levelname;
    String student_balanceFee;
    String student_status;
    String student_ID;

    public Class_StudentListDetails(){}
    public Class_StudentListDetails(int _id, String student_name, String student_mobileno, String student_applicationNumber, String student_institutionName, String student_levelname, String student_balanceFee) {
        this._id = _id;
        this.student_name = student_name;
        this.student_mobileno = student_mobileno;
        this.student_applicationNumber = student_applicationNumber;
        this.student_institutionName = student_institutionName;
        this.student_levelname = student_levelname;
        this.student_balanceFee = student_balanceFee;
    }

    public Class_StudentListDetails(String student_name, String student_mobileno, String student_applicationNumber, String student_institutionName, String student_levelname, String student_balanceFee) {
        this.student_name = student_name;
        this.student_mobileno = student_mobileno;
        this.student_applicationNumber = student_applicationNumber;
        this.student_institutionName = student_institutionName;
        this.student_levelname = student_levelname;
        this.student_balanceFee = student_balanceFee;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_mobileno() {
        return student_mobileno;
    }

    public void setStudent_mobileno(String student_mobileno) {
        this.student_mobileno = student_mobileno;
    }

    public String getStudent_applicationNumber() {
        return student_applicationNumber;
    }

    public void setStudent_applicationNumber(String student_applicationNumber) {
        this.student_applicationNumber = student_applicationNumber;
    }

    public String getStudent_institutionName() {
        return student_institutionName;
    }

    public void setStudent_institutionName(String student_institutionName) {
        this.student_institutionName = student_institutionName;
    }

    public String getStudent_levelname() {
        return student_levelname;
    }

    public void setStudent_levelname(String student_levelname) {
        this.student_levelname = student_levelname;
    }

    public String getStudent_balanceFee() {
        return student_balanceFee;
    }

    public void setStudent_balanceFee(String student_balanceFee) {
        this.student_balanceFee = student_balanceFee;
    }

    public String getStudent_status() {
        return student_status;
    }

    public void setStudent_status(String student_status) {
        this.student_status = student_status;
    }



    public String getStudent_ID() {
        return student_ID;
    }

    public void setStudent_ID(String student_ID) {
        this.student_ID = student_ID;
    }

}
