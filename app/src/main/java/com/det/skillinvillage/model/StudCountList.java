package com.det.skillinvillage.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudCountList {
    @SerializedName("Count")
    @Expose
    private List<StudentCount> count = null;
    @SerializedName("Students")
    @Expose
    private List<StudentList> students = null;
    @SerializedName("Response")
    @Expose
    private String response;

    public List<StudentCount> getCount() {
        return count;
    }

    public void setCount(List<StudentCount> count) {
        this.count = count;
    }

    public List<StudentList> getStudents() {
        return students;
    }

    public void setStudents(List<StudentList> students) {
        this.students = students;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
