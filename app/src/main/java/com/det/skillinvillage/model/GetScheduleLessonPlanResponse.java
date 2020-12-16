package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetScheduleLessonPlanResponse {
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("lst1")
    @Expose
    private List<GetScheduleLessonPlanResponseList> lst1 = null;

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

    public List<GetScheduleLessonPlanResponseList> getLst1() {
        return lst1;
    }

    public void setLst1(List<GetScheduleLessonPlanResponseList> lst1) {
        this.lst1 = lst1;
    }

}
