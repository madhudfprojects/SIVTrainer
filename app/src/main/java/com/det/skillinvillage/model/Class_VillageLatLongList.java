package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_VillageLatLongList {
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("Village_ID")
    @Expose
    private Integer villageID;
    @SerializedName("village_name")
    @Expose
    private String villageName;
    @SerializedName("Lattitude")
    @Expose
    private String lattitude;
    @SerializedName("Logitude")
    @Expose
    private String logitude;
    @SerializedName("Notification_Status")
    @Expose
    private Object notificationStatus;
    @SerializedName("Count_Total")
    @Expose
    private String countTotal;
    @SerializedName("Count_Male")
    @Expose
    private String countMale;
    @SerializedName("Count_Female")
    @Expose
    private String countFemale;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getVillageID() {
        return villageID;
    }

    public void setVillageID(Integer villageID) {
        this.villageID = villageID;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLogitude() {
        return logitude;
    }

    public void setLogitude(String logitude) {
        this.logitude = logitude;
    }

    public Object getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Object notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(String countTotal) {
        this.countTotal = countTotal;
    }

    public String getCountMale() {
        return countMale;
    }

    public void setCountMale(String countMale) {
        this.countMale = countMale;
    }

    public String getCountFemale() {
        return countFemale;
    }

    public void setCountFemale(String countFemale) {
        this.countFemale = countFemale;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
