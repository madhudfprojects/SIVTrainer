package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_getdemo_resplist {
    @SerializedName("Language_Name")
    @Expose
    private String Language_Name;


    @SerializedName("Language_Link")
    @Expose
    private String Language_Link;

    public String getLanguage_Name() {
        return Language_Name;
    }

    public void setLanguage_Name(String language_Name) {
        Language_Name = language_Name;
    }

    public String getLanguage_Link() {
        return Language_Link;
    }

    public void setLanguage_Link(String language_Link) {
        Language_Link = language_Link;
    }

}
