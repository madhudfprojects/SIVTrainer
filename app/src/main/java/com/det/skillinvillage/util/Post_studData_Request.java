package com.det.skillinvillage.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post_studData_Request {

    @SerializedName("User_ID")
    @Expose
    private String User_ID;

    @SerializedName("Schedule_ID")
    @Expose
    private String Schedule_ID;

    @SerializedName("Schedule_Status")
    @Expose
    private String Schedule_Status;

    @SerializedName("Remarks")
    @Expose
    private String Remarks;

    @SerializedName("Absent_Value")
    @Expose
    private String Absent_Value;

    @SerializedName("Present_Value")
    @Expose
    private String Present_Value;

    @SerializedName("Zoom_Value")
    @Expose
    private String Zoom_Value;

    @SerializedName("FaceToFace_Value")
    @Expose
    private String FaceToFace_Value;

    @SerializedName("Conferance_Value")
    @Expose
    private String Conferance_Value;

    @SerializedName("Subject_ID")
    @Expose
    private String Subject_ID;

    public String getSubject_ID() {
        return Subject_ID;
    }

    public void setSubject_ID(String subject_ID) {
        Subject_ID = subject_ID;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getSchedule_ID() {
        return Schedule_ID;
    }

    public void setSchedule_ID(String schedule_ID) {
        Schedule_ID = schedule_ID;
    }

    public String getSchedule_Status() {
        return Schedule_Status;
    }

    public void setSchedule_Status(String schedule_Status) {
        Schedule_Status = schedule_Status;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getAbsent_Value() {
        return Absent_Value;
    }

    public void setAbsent_Value(String absent_Value) {
        Absent_Value = absent_Value;
    }

    public String getPresent_Value() {
        return Present_Value;
    }

    public void setPresent_Value(String present_Value) {
        Present_Value = present_Value;
    }

    public String getZoom_Value() {
        return Zoom_Value;
    }

    public void setZoom_Value(String zoom_Value) {
        Zoom_Value = zoom_Value;
    }

    public String getFaceToFace_Value() {
        return FaceToFace_Value;
    }

    public void setFaceToFace_Value(String faceToFace_Value) {
        FaceToFace_Value = faceToFace_Value;
    }

    public String getConferance_Value() {
        return Conferance_Value;
    }

    public void setConferance_Value(String conferance_Value) {
        Conferance_Value = conferance_Value;
    }
}
