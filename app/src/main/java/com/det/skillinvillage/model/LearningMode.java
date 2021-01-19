package com.det.skillinvillage.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LearningMode {
    @SerializedName("LearningMode_ID")
    @Expose
    private String learningMode_ID;
    @SerializedName("LearningMode_Name")
    @Expose
    private String learningMode_Name;

    public String getLearningMode_ID() {
        return learningMode_ID;
    }

    public void setLearningMode_ID(String learningMode_ID) {
        this.learningMode_ID = learningMode_ID;
    }

    public String getLearningMode_Name() {
        return learningMode_Name;
    }

    public void setLearningMode_Name(String learningMode_Name) {
        this.learningMode_Name = learningMode_Name;
    }

    @NonNull
    @Override
    public String toString() {
        return learningMode_Name;
    }

}
