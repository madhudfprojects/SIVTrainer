package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentCount {
    @SerializedName("Student_Count")
    @Expose
    private String studentCount;


    @SerializedName("Applicant_Count")
    @Expose
    private String ApplicantCount;

    @SerializedName("Admission_Count")
    @Expose
    private String AdmissionCount;

    public String getApplicantCount() {
        return ApplicantCount;
    }

    public void setApplicantCount(String applicantCount) {
        ApplicantCount = applicantCount;
    }

    public String getAdmissionCount() {
        return AdmissionCount;
    }

    public void setAdmissionCount(String admissionCount) {
        AdmissionCount = admissionCount;
    }

    public String getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(String studentCount) {
        this.studentCount = studentCount;
    }

}
