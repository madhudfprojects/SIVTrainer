package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post_Topic_Download_UpdateResponse {

    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("listVersion")
    @Expose
    private List<PostTopicDownloadUpdateResponseList> listVersion = null;

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

    public List<PostTopicDownloadUpdateResponseList> getListVersion() {
        return listVersion;
    }

    public void setListVersion(List<PostTopicDownloadUpdateResponseList> listVersion) {
        this.listVersion = listVersion;
    }

}
