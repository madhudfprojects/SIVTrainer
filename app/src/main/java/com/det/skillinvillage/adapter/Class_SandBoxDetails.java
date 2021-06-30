package com.det.skillinvillage.adapter;

import android.support.annotation.NonNull;

public class Class_SandBoxDetails {

    int id;
    String sandbox_id;
    String sandbox_name;

    public Class_SandBoxDetails(){}
    public Class_SandBoxDetails(int id, String sandbox_id, String sandbox_name) {
        this.id = id;
        this.sandbox_id = sandbox_id;
        this.sandbox_name = sandbox_name;
    }

    public Class_SandBoxDetails(String sandbox_id, String sandbox_name) {
        this.sandbox_id = sandbox_id;
        this.sandbox_name = sandbox_name;
    }

    public String getSandbox_id() {
        return sandbox_id;
    }

    public void setSandbox_id(String sandbox_id) {
        this.sandbox_id = sandbox_id;
    }

    public String getSandbox_name() {
        return sandbox_name;
    }

    public void setSandbox_name(String sandbox_name) {
        this.sandbox_name = sandbox_name;
    }

    @NonNull
    @Override
    public String toString() {
        return sandbox_name;
    }
}
