package com.det.skillinvillage;

import android.support.annotation.NonNull;

/**
 * Created by User on 11/13/2017.
 */

public class Class_Taluk
{

    int id;

    String TalukId;
    String Taluk_DID;
    String TalukName;
    String TalukStatus;

  /*
    <Id>70</Id>
    <Distict_Id>266</Distict_Id>
    <Taluk_Name>Hubli</Taluk_Name>
    <Status>Success</Status>

    */

    public Class_Taluk(){

    }



    public Class_Taluk(int id, String talukId,String taluk_did, String talukname,String talukstatus){
        this.id = id;
        this.TalukId = talukId;
        this.Taluk_DID=taluk_did;
        this.TalukName = talukname;
        this.TalukStatus=talukstatus;
    }

    public Class_Taluk(String talukId,String taluk_did, String talukname,String talukstatus){

        this.TalukId = talukId;
        this.Taluk_DID=taluk_did;
        this.TalukName = talukname;
        this.TalukStatus=talukstatus;
    }



    //get and set
    public String gettaluk_id(){
        return this.TalukId;
    }
    public void settaluk_id(String talukId){
        this.TalukId = talukId;
    }

    //get and set
    public String gettaluk_did(){
        return this.Taluk_DID;
    }
    public void settaluk_did(String taluk_did){
        this.Taluk_DID = taluk_did;
    }


    //get and set
    public String gettalukname(){
        return this.TalukName;
    }
    public void settalukname(String talukname){
        this.TalukName = talukname;
    }


    //get and set
    public String gettalukstatus(){
        return this.TalukStatus;
    }
    public void settalukstatus(String talukstatus){
        this.TalukStatus = talukstatus;
    }


    //set the string
    @NonNull
    @Override
    public String toString()
    {
        return( this.TalukName );
    }


}// end of class
