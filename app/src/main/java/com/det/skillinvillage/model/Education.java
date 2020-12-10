package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Education {
    @SerializedName("Education_ID")
    @Expose
    private String education_ID;

    @SerializedName("Education_Name")
    @Expose
    private String education_Name;

    public String getEducation_ID() {
        return education_ID;
    }

    public void setEducation_ID(String education_ID) {
        this.education_ID = education_ID;
    }

    public String getEducation_Name() {
        return education_Name;
    }

    public void setEducation_Name(String education_Name) {
        this.education_Name = education_Name;
    }

    @Override
    public String toString() {
        return education_Name;
    }
}
