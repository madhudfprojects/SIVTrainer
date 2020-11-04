package com.det.skillinvillage;

public class Class_Dashboard_FeesSummary {

    String institute_ID;
    String institute_Name;
    String receivable;
    String received;
    String balance;

    public Class_Dashboard_FeesSummary(){}

    public Class_Dashboard_FeesSummary(String institute_ID, String institute_Name, String receivable, String received, String balance) {
        this.institute_ID = institute_ID;
        this.institute_Name = institute_Name;
        this.receivable = receivable;
        this.received = received;
        this.balance = balance;
    }

    public String getInstitute_ID() {
        return institute_ID;
    }

    public void setInstitute_ID(String institute_ID) {
        this.institute_ID = institute_ID;
    }

    public String getInstitute_Name() {
        return institute_Name;
    }

    public void setInstitute_Name(String institute_Name) {
        this.institute_Name = institute_Name;
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
