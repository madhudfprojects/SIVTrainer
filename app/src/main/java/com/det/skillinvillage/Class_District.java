package com.det.skillinvillage;

import android.support.annotation.NonNull;

/**
 * Created by User on 11/10/2017.
 */

/*
           <DistrictId>1</DistrictId>
           <DistrictName>Nicobar</DistrictName>
            <Status>Success</Status>
            <Stateid>1</Stateid>*/

public class Class_District {

    int id;

    String DistrictId;
    String DistrictName;
    String DistrictStatus;
    String DStateId;

     /*<DistrictId>1</DistrictId>
           <DistrictName>Nicobar</DistrictName>
            <Status>Success</Status>
            <Stateid>1</Stateid> */

    public Class_District(){

    }


    public Class_District(int id, String districtid, String districtname,String districtstatus,String dstateid){
        this.id = id;
        this.DistrictId = districtid;
        this.DistrictName = districtname;
        this.DistrictStatus=districtstatus;
        this.DStateId=dstateid;

    }


    public Class_District(String districtid, String districtname,String districtstatus,String dstateid){

        this.DistrictId = districtid;
        this.DistrictName = districtname;
        this.DistrictStatus=districtstatus;
        this.DStateId=dstateid;
    }

    //get and set
    public String getdistrict_id(){
        return this.DistrictId;
    }
    public void setdistrict_id(String districtid){
        this.DistrictId = districtid;
    }

    //get and set
    public String getdistrict_name(){
        return this.DistrictName;
    }
    public void setdistrict_name(String districtname){
        this.DistrictName = districtname;
    }



    //get and set
    public String getdistrict_status(){
        return this.DistrictStatus;
    }
    public void setdistrict_status(String districtstatus){
        this.DistrictStatus = districtstatus;
    }


    //get and set
    public String getdstateid_id(){
        return this.DStateId;
    }
    public void setdstateid_id(String dstateid){
        this.DStateId = dstateid;
    }

    @NonNull
    public String toString()
    {
        return( this.DistrictName );
    }








}//end of class
