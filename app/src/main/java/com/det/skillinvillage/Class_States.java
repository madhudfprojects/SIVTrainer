package com.det.skillinvillage;

import android.support.annotation.NonNull;

/**
 * Created by User on 11/10/2017.
 */

public class Class_States {


    int id;

    String StateId;
    String StateName;
    String StateStatus;

   /* <Id>1</Id>
    <State>Andaman and Nicobar Islands</State>
    <Status>Success</Status>
    */

    public Class_States(){

    }


    public Class_States(int id, String stateid, String statename,String statestatus){
        this.id = id;
        this.StateId = stateid;
        this.StateName = statename;
        this.StateStatus=statestatus;

    }


    public Class_States(String stateid, String statename,String statestatus){

        this.StateId = stateid;
        this.StateName = statename;
        this.StateStatus=statestatus;
    }

    //get and set
    public String getstate_id(){
        return this.StateId;
    }
    public void setstate_id(String stateid){
        this.StateId = stateid;
    }

    //get and set
    public String getstate_name(){
        return this.StateName;
    }
    public void setstate_name(String statename){
        this.StateName = statename;
    }



    //get and set
    public String getstate_status(){
        return this.StateStatus;
    }
    public void setstate_status(String statestatus){
        this.StateStatus = statestatus;
    }

    @NonNull
    public String toString()
    {
        return( this.StateName );
    }




}//end of class
