package com.det.skillinvillage;

public class Class_Dashboard_SandBox {
    String dashboard_sand_id;
    String dashboard_sand_name;

    public Class_Dashboard_SandBox(){}
    public Class_Dashboard_SandBox(String dashboard_sand_id, String dashboard_sand_name) {
        this.dashboard_sand_id = dashboard_sand_id;
        this.dashboard_sand_name = dashboard_sand_name;
    }

    public String getDashboard_sand_id() {
        return dashboard_sand_id;
    }

    public void setDashboard_sand_id(String dashboard_sand_id) {
        this.dashboard_sand_id = dashboard_sand_id;
    }

    public String getDashboard_sand_name() {
        return dashboard_sand_name;
    }

    public void setDashboard_sand_name(String dashboard_sand_name) {
        this.dashboard_sand_name = dashboard_sand_name;
    }

    @Override
    public String toString() {
        return dashboard_sand_name;
    }

}
