package com.det.skillinvillage;

public class Class_GoogleLocations {
     String latitude;
     String longitude;
     String villagename;

    public Class_GoogleLocations(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getVillagename() {
        return villagename;
    }

    public void setVillagename(String villagename) {
        this.villagename = villagename;
    }

    public Class_GoogleLocations(){}

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}


