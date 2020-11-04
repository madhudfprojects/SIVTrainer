package com.det.skillinvillage;

public class Class_ClusterDetails {
    int id;
    String cluster_id;
    String cluster_name;
    String cluster_sand_id;
    String cluster_aca_id;



    public Class_ClusterDetails(String cluster_id, String cluster_name, String cluster_aca_id) {
        this.cluster_id = cluster_id;
        this.cluster_name = cluster_name;
        this.cluster_aca_id = cluster_aca_id;
    }

    public Class_ClusterDetails() {

    }

    public String getCluster_sand_id() {
        return cluster_sand_id;
    }

    public void setCluster_sand_id(String cluster_sand_id) {
        this.cluster_sand_id = cluster_sand_id;
    }

    public String getCluster_aca_id() {
        return cluster_aca_id;
    }

    public void setCluster_aca_id(String cluster_aca_id) {
        this.cluster_aca_id = cluster_aca_id;
    }

    public Class_ClusterDetails(int id, String cluster_id, String cluster_name) {
        this.id = id;
        this.cluster_id = cluster_id;
        this.cluster_name = cluster_name;
    }

    public String getCluster_id() {
        return cluster_id;
    }

    public void setCluster_id(String cluster_id) {
        this.cluster_id = cluster_id;
    }

    public String getCluster_name() {
        return cluster_name;
    }

    public void setCluster_name(String cluster_name) {
        this.cluster_name = cluster_name;
    }

    @Override
    public String toString() {
        return cluster_name;
    }
}
