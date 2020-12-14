package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMobileMenuResponse {

    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("objMenu")
    @Expose
    private List<GetMobileMenuResponseList> objMenu = null;

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

    public List<GetMobileMenuResponseList> getObjMenu() {
        return objMenu;
    }

    public void setObjMenu(List<GetMobileMenuResponseList> objMenu) {
        this.objMenu = objMenu;
    }

}


