package com.det.skillinvillage.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Location_DataList {
    @SerializedName("Count")
    @Expose
    private List<Count> count = null;
    @SerializedName("State")
    @Expose
    private List<State> state = null;
    @SerializedName("District")
    @Expose
    private List<District> district = null;
    @SerializedName("Taluka")
    @Expose
    private List<Taluka> taluka = null;
    @SerializedName("Village")
    @Expose
    private List<Village> village = null;
    @SerializedName("Year")
    @Expose
    private List<Year> year = null;
    @SerializedName("Sandbox")
    @Expose
    private List<Sandbox> sandbox = null;
    @SerializedName("Cluster")
    @Expose
    private List<Cluster> cluster = null;
    @SerializedName("Institute")
    @Expose
    private List<Institute> institute = null;
    @SerializedName("School")
    @Expose
    private List<School> school = null;
    @SerializedName("Level")
    @Expose
    private List<Level> level = null;
    @SerializedName("LearningMode")
    @Expose
    private List<LearningMode> learningMode = null;
    @SerializedName("Assessment")
    @Expose
    private List<Assessment> assessment = null;
    @SerializedName("Education")
    @Expose
    private List<Education> education = null;

    @SerializedName("Response")
    @Expose
    private String response;



    public List<State> getState() {
        return state;
    }

    public void setState(List<State> state) {
        this.state = state;
    }

    public List<District> getDistrict() {
        return district;
    }

    public void setDistrict(List<District> district) {
        this.district = district;
    }

    public List<Taluka> getTaluka() {
        return taluka;
    }

    public void setTaluka(List<Taluka> taluka) {
        this.taluka = taluka;
    }

    public List<Village> getVillage() {
        return village;
    }

    public void setVillage(List<Village> village) {
        this.village = village;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Year> getYear() {
        return year;
    }

    public void setYear(List<Year> year) {
        this.year = year;
    }


    public List<Count> getCount() {
        return count;
    }

    public void setCount(List<Count> count) {
        this.count = count;
    }

    public List<Sandbox> getSandbox() {
        return sandbox;
    }

    public void setSandbox(List<Sandbox> sandbox) {
        this.sandbox = sandbox;
    }

    public List<Cluster> getCluster() {
        return cluster;
    }

    public void setCluster(List<Cluster> cluster) {
        this.cluster = cluster;
    }

    public List<Institute> getInstitute() {
        return institute;
    }

    public void setInstitute(List<Institute> institute) {
        this.institute = institute;
    }

    public List<School> getSchool() {
        return school;
    }

    public void setSchool(List<School> school) {
        this.school = school;
    }

    public List<Level> getLevel() {
        return level;
    }

    public void setLevel(List<Level> level) {
        this.level = level;
    }

    public List<LearningMode> getLearningMode() {
        return learningMode;
    }

    public void setLearningMode(List<LearningMode> learningMode) {
        this.learningMode = learningMode;
    }

    public List<Assessment> getAssessment() {
        return assessment;
    }

    public void setAssessment(List<Assessment> assessment) {
        this.assessment = assessment;
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }
}

