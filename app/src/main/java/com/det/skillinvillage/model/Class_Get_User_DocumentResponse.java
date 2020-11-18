package com.det.skillinvillage.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
public class Class_Get_User_DocumentResponse {

    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("listVersion")
    @Expose
    private List<Class_ListVersion> listVersion = null;

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

    public List<Class_ListVersion> getClass_ListVersion() {
        return listVersion;
    }

    public void setClass_ListVersion(List<Class_ListVersion> listVersion) {
        this.listVersion = listVersion;
    }

}
