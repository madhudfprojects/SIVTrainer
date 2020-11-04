package com.det.skillinvillage;

public class Class_PiechartData {

    String institute_name;
    String student_count;

    String receivable;
    String received;
    String balance;

    public Class_PiechartData(){

    }

    public Class_PiechartData(String institute_name, String student_count) {
        this.institute_name = institute_name;
        this.student_count = student_count;
    }

    public String getInstitute_name() {
        return institute_name;
    }

    public void setInstitute_name(String institute_name) {
        this.institute_name = institute_name;
    }

    public String getStudent_count() {
        return student_count;
    }

    public void setStudent_count(String student_count) {
        this.student_count = student_count;
    }


    public String getReceivable() {
        return receivable;
    }

    public void setReceivable(String receivable) {
        this.receivable = receivable;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
