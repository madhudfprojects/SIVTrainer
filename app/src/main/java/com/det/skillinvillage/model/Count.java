package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Count {
    @SerializedName("State_Count")
    @Expose
    private String stateCount;
    @SerializedName("District_Count")
    @Expose
    private String districtCount;
    @SerializedName("Taluka_Count")
    @Expose
    private String talukaCount;
    @SerializedName("Panchayat_Count")
    @Expose
    private Object panchayatCount;
    @SerializedName("Village_Count")
    @Expose
    private String villageCount;
    @SerializedName("Year_Count")
    @Expose
    private String yearCount;
    @SerializedName("Sync_ID")
    @Expose
    private String syncID;

    public String getStateCount() {
        return stateCount;
    }

    public void setStateCount(String stateCount) {
        this.stateCount = stateCount;
    }

    public String getDistrictCount() {
        return districtCount;
    }

    public void setDistrictCount(String districtCount) {
        this.districtCount = districtCount;
    }

    public String getTalukaCount() {
        return talukaCount;
    }

    public void setTalukaCount(String talukaCount) {
        this.talukaCount = talukaCount;
    }

    public Object getPanchayatCount() {
        return panchayatCount;
    }

    public void setPanchayatCount(Object panchayatCount) {
        this.panchayatCount = panchayatCount;
    }

    public String getVillageCount() {
        return villageCount;
    }

    public void setVillageCount(String villageCount) {
        this.villageCount = villageCount;
    }

    public String getYearCount() {
        return yearCount;
    }

    public void setYearCount(String yearCount) {
        this.yearCount = yearCount;
    }

    public String getSyncID() {
        return syncID;
    }

    public void setSyncID(String syncID) {
        this.syncID = syncID;
    }

}
