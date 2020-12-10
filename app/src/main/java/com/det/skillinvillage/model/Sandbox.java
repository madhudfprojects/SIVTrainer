package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sandbox {
    @SerializedName("Sandbox_ID")
    @Expose
    private String sandboxID;
    @SerializedName("Sandbox_Name")
    @Expose
    private String sandboxName;

    public String getSandboxID() {
        return sandboxID;
    }

    public void setSandboxID(String sandboxID) {
        this.sandboxID = sandboxID;
    }

    public String getSandboxName() {
        return sandboxName;
    }

    public void setSandboxName(String sandboxName) {
        this.sandboxName = sandboxName;
    }

    @Override
    public String toString() {
        return sandboxName;
    }
}
