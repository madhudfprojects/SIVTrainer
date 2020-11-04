package com.det.skillinvillage.util;

/**
 * Created by User on 4/24/2018.
 */

public class Eventlist {
    String name;
    String type;

    public Eventlist(String name, String type ) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
