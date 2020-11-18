package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Class_getUserDashboardResponse {
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("listVersion")
    @Expose
    private List<Class_dashboardList> listVersion = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Class_dashboardList> getListVersion() {
        return listVersion;
    }

    public void setListVersion(List<Class_dashboardList> listVersion) {
        this.listVersion = listVersion;
    }

}
