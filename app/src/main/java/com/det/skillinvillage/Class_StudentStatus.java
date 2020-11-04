package com.det.skillinvillage;

public class Class_StudentStatus {
    int id;
    String student_status;

    public Class_StudentStatus(){}
    public Class_StudentStatus(int id, String student_status) {
        this.id = id;
        this.student_status = student_status;
    }

    public String getStudent_status() {
        return student_status;
    }

    public void setStudent_status(String student_status) {
        this.student_status = student_status;
    }
    @Override
    public String toString() {
        return student_status;
    }


}
