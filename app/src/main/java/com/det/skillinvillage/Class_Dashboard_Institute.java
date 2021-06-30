package com.det.skillinvillage;

import android.support.annotation.NonNull;

public class Class_Dashboard_Institute {

    String dashboard_inst_id;
    String dashboard_inst_name;

    public Class_Dashboard_Institute(){}
    public Class_Dashboard_Institute(String dashboard_inst_id, String dashboard_inst_name) {
        this.dashboard_inst_id = dashboard_inst_id;
        this.dashboard_inst_name = dashboard_inst_name;
    }

    public String getDashboard_inst_id() {
        return dashboard_inst_id;
    }

    public void setDashboard_inst_id(String dashboard_inst_id) {
        this.dashboard_inst_id = dashboard_inst_id;
    }

    public String getDashboard_inst_name() {
        return dashboard_inst_name;
    }

    public void setDashboard_inst_name(String dashboard_inst_name) {
        this.dashboard_inst_name = dashboard_inst_name;
    }
    @NonNull
    @Override
    public String toString() {
        return dashboard_inst_name;
    }

}
