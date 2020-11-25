package com.det.skillinvillage.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Village {
    @SerializedName("Taluka_ID")
    @Expose
    private String talukaID;
    @SerializedName("Panchayat_ID")
    @Expose
    private Object panchayatID;
    @SerializedName("Village_ID")
    @Expose
    private String villageID;
    @SerializedName("Village_Name")
    @Expose
    private String villageName;

    public String getTalukaID() {
        return talukaID;
    }

    public void setTalukaID(String talukaID) {
        this.talukaID = talukaID;
    }

    public Object getPanchayatID() {
        return panchayatID;
    }

    public void setPanchayatID(Object panchayatID) {
        this.panchayatID = panchayatID;
    }

    public String getVillageID() {
        return villageID;
    }

    public void setVillageID(String villageID) {
        this.villageID = villageID;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }


    @NonNull
    @Override
    public String toString() {
        return villageName;
    }
}
