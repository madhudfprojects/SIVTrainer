package com.det.skillinvillage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.det.skillinvillage.model.AddStudentDetailsRequest;
import com.det.skillinvillage.model.AddStudentDetailsResponse;
import com.det.skillinvillage.model.Class_Response_AddStudentDetailsList;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.model.StudentList;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;
import com.google.gson.Gson;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.Key_username;
import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.key_userimage;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_userimage;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_username;
import static com.det.skillinvillage.MainActivity.sharedpreferencebook_usercredential;


public class Activity_MarketingHomeScreen extends AppCompatActivity {

    //For titlebar
    Toolbar toolbar;
    //For titlebar


    //Sharedpreference to store valid offical mailID
    public static final String sharedpreferenc_mailbook = "sharedpreference_mailbook";
    public static final String NameKey_mailID = "namekey_validmailID";
    SharedPreferences sharedpref_validMailID_Obj;
    //Sharedpreference to store valid offical mailID


    Class_GPSTracker gps_object;

    ImageView student_registrationform_ib, student_referfriend_ib, applicationdetails_ib, feessubmit_ib, college_addmission_history_IB;

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;

    int int_insidecursorcount, int_cousorcount;
    String str_fetch_image, str_fetch_studentname, str_fetch_gender, str_fetch_birthdate, str_fetch_education, str_fetch_marks, str_fetch_mobileno, str_fetch_fathername, str_fetch_mothername, str_fetch_aadharno, str_fetch_studentstatus, str_fetch_admissionfee, str_fetch_createddate, str_fetch_createdby, str_fetch_receiptno, str_fetch_learnOption;
    int int_fetch_sandboxid, int_fetch_academicid, int_fetch_clusterid, int_fetch_instituteid, int_fetch_schoolid, int_fetch_levelid;
    Boolean RegisterResponse_marketingscreen;

    TextView Notuploadedcount_tv,Notuploadedcount_addnew_TV;
    int OfflineStudentTable_count;
    Cursor cursor;
    SQLiteDatabase db;
    int i = 0;
    ImageButton notupload_ib;
    String str_logout_count_Status = "", str_loginuserID = "",str_Googlelogin_Username="",str_Googlelogin_UserImg="";
    SharedPreferences sharedpref_loginuserid_Obj;
    SharedPreferences sharedpref_stuid_Obj;
    int str_stuID = 0;
    StudentList[] class_farmerprofileoffline_array_obj;
    SharedPreferences sharedpreferencebook_usercredential_Obj;

    SharedPreferences sharedpref_username_Obj;
    SharedPreferences sharedpref_userimage_Obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketingform);
        sharedpref_username_Obj = getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
        str_Googlelogin_Username = sharedpref_username_Obj.getString(Key_username, "").trim();

        sharedpref_userimage_Obj = getSharedPreferences(sharedpreferenc_userimage, Context.MODE_PRIVATE);
        str_Googlelogin_UserImg = sharedpref_userimage_Obj.getString(key_userimage, "").trim();


        // toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);

        sharedpref_validMailID_Obj = getSharedPreferences(sharedpreferenc_mailbook, Context.MODE_PRIVATE);

//        sharedpref_loginuserid_Obj = getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
//        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_loginuserID = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();


//        sharedpref_stuid_Obj = getSharedPreferences(sharedpreferenc_selectedspinner, Context.MODE_PRIVATE);
//        str_stuID = sharedpref_stuid_Obj.getInt(key_studentid, 0);
//        Log.e("str_stuID.1oncreate..", String.valueOf(str_stuID));


        //student_registrationform_ib = findViewById(R.id.student_registrationform_IB);
       // student_referfriend_ib = findViewById(R.id.student_referfriend_IB);
        applicationdetails_ib = findViewById(R.id.applicationdetails_IB);
        feessubmit_ib = findViewById(R.id.feessubmit_IB);
       // college_addmission_history_IB = findViewById(R.id.college_addmission_history_IB);

      //  Notuploadedcount_tv = findViewById(R.id.Notuploadedcount_TV);
       // Notuploadedcount_addnew_TV=(TextView)findViewById(R.id.Notuploadedcount_addnew_TV);
      //  notupload_ib = findViewById(R.id.notupload_IB);
        // Set upon the actionbar
//        setSupportActionBar(toolbar);
        // Now use actionbar methods to show navigation icon and title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Student Registration");


        //////////////Added by shivaleela on sept 3rd 2019


//        @SuppressLint("ResourceType")
//        Animation animation_stuRegistration = AnimationUtils.loadAnimation(this, R.anim.translate);
//        // animation1.setDuration(2100);
//        animation_stuRegistration.setDuration(1100);
//        student_registrationform_ib.setAnimation(animation_stuRegistration);
//        student_registrationform_ib.animate();
//        animation_stuRegistration.start();


        @SuppressLint("ResourceType")
        Animation animation_applDetails = AnimationUtils.loadAnimation(this, R.anim.translate);
        //animation2.setDuration(2100);
        animation_applDetails.setDuration(1100);
        applicationdetails_ib.setAnimation(animation_applDetails);
        applicationdetails_ib.animate();
        animation_applDetails.start();


        @SuppressLint("ResourceType")
        Animation animation_feessubmit = AnimationUtils.loadAnimation(this, R.anim.translate);
//        animation3.setDuration(2100);
        animation_feessubmit.setDuration(1100);
        feessubmit_ib.setAnimation(animation_feessubmit);
        feessubmit_ib.animate();
        animation_feessubmit.start();

//        @SuppressLint("ResourceType")
//        Animation animation_notuploadedcount = AnimationUtils.loadAnimation(this, R.anim.translate);
////        animation4.setDuration(2100);
//        animation_notuploadedcount.setDuration(1100);
//        notupload_ib.setAnimation(animation_notuploadedcount);
//        notupload_ib.animate();
//        animation_notuploadedcount.start();

        //////////////////////////////////////////////

        gps_object = new Class_GPSTracker(Activity_MarketingHomeScreen.this);



        applicationdetails_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Activity_MarketingHomeScreen.this, Activity_ViewStudentList_new.class);
                startActivity(i);
                finish();



            }
        });


        feessubmit_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                internetDectector = new Class_InternetDectector(getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();



                Intent i = new Intent(Activity_MarketingHomeScreen.this, Activity_FeesSubmit_New.class);
                startActivity(i);
                finish();

            }
        });

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if (isInternetPresent) {
            fetch_DB_farmerprofile_offline_data_edit();
            fetch_DB_farmerprofile_offline_data_add();
        } else {
            fetch_DB_farmerprofile_offline_data_new_edit();
            fetch_DB_farmerprofile_offline_data_new_add();

        }
    }// end of Oncreate()


    public void fetch_DB_farmerprofile_offline_data_new_add() {
        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");
       // Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE Stud_TempId LIKE'" + "newtemp%" + "'", null);
           Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE UpadateOff_Online='"+ "offline"  + "'", null);
        int x = cursor1.getCount();
        Log.e("marketing_studaddd", String.valueOf(x));
        int i = 0;
        class_farmerprofileoffline_array_obj = new StudentList[x];
        if (x > 0) {
         //   Notuploadedcount_addnew_TV.setText("Not uploaded: " + x);
        }
        db1.close();
    }

    public void fetch_DB_farmerprofile_offline_data_new_edit() {
        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");
     //   Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE Stud_TempId LIKE'" + "edittemp%" + "'", null);
        //   Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE StudentID='"+ stuid  + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE UpadateOff_Online='"+ "offlineedit"  + "'", null);
        int x = cursor1.getCount();
        Log.e("marketing_edit", String.valueOf(x));
        int i = 0;
        class_farmerprofileoffline_array_obj = new StudentList[x];
        if (x > 0) {
          //  Notuploadedcount_tv.setText("Not Uploaded: " + x);
        }
        db1.close();
    }
////////////////////////
    public void fetch_DB_farmerprofile_offline_data_edit()
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");
      //  Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE Stud_TempId LIKE'"+ "edittemp%" + "'", null);
        //   Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE StudentID='"+ stuid  + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE UpadateOff_Online='"+ "offlineedit"  + "'", null);

        int x = cursor1.getCount();
        Log.e("marketing_studentcount", String.valueOf(x));

        int i = 0;
        class_farmerprofileoffline_array_obj = new StudentList[x];
        if (x > 0) {

            if (cursor1.moveToFirst()) {

                do {

                    StudentList innerObj_Class_SandboxList = new StudentList();
                    innerObj_Class_SandboxList.setAcademicID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("AcademicID"))));
                    innerObj_Class_SandboxList.setAcademicName(cursor1.getString(cursor1.getColumnIndex("AcademicName")));
                    innerObj_Class_SandboxList.setAdmissionFee(cursor1.getString(cursor1.getColumnIndex("AdmissionFee")));
                    innerObj_Class_SandboxList.setApplicationNo(cursor1.getString(cursor1.getColumnIndex("ApplicationNo")));
                    innerObj_Class_SandboxList.setBalanceFee(cursor1.getString(cursor1.getColumnIndex("BalanceFee")));
                    innerObj_Class_SandboxList.setBirthDate(cursor1.getString(cursor1.getColumnIndex("BirthDate")));
                    innerObj_Class_SandboxList.setClusterID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("ClusterID"))));//DispFarmerTable_Farmer_Code VARCHAR
                    innerObj_Class_SandboxList.setClusterName(cursor1.getString(cursor1.getColumnIndex("ClusterName")));//DispFarmerTable_Farmer_Code VARCHAR
                    innerObj_Class_SandboxList.setCreatedDate(cursor1.getString(cursor1.getColumnIndex("CreatedDate")));
                    innerObj_Class_SandboxList.setEducation(cursor1.getString(cursor1.getColumnIndex("Education")));
                    innerObj_Class_SandboxList.setFatherName(cursor1.getString(cursor1.getColumnIndex("FatherName")));
                    innerObj_Class_SandboxList.setGender(cursor1.getString(cursor1.getColumnIndex("Gender")));
                    innerObj_Class_SandboxList.setInstituteName(cursor1.getString(cursor1.getColumnIndex("InstituteName")));
                    innerObj_Class_SandboxList.setInstituteID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("InstituteID"))));
                    innerObj_Class_SandboxList.setLevelID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("LevelID"))));
                    innerObj_Class_SandboxList.setLevelName(cursor1.getString(cursor1.getColumnIndex("LevelName")));
                    innerObj_Class_SandboxList.setMarks4(cursor1.getString(cursor1.getColumnIndex("Marks4")));
                    innerObj_Class_SandboxList.setMarks5(cursor1.getString(cursor1.getColumnIndex("Marks5")));
                    innerObj_Class_SandboxList.setMarks6(cursor1.getString(cursor1.getColumnIndex("Marks6")));
                    innerObj_Class_SandboxList.setMarks7(cursor1.getString(cursor1.getColumnIndex("Marks7")));
                    innerObj_Class_SandboxList.setMarks8(cursor1.getString(cursor1.getColumnIndex("Marks8")));
                    innerObj_Class_SandboxList.setMobile(cursor1.getString(cursor1.getColumnIndex("Mobile")));
                    innerObj_Class_SandboxList.setMotherName(cursor1.getString(cursor1.getColumnIndex("MotherName")));
                    innerObj_Class_SandboxList.setPaidFee(cursor1.getString(cursor1.getColumnIndex("PaidFee")));
                    innerObj_Class_SandboxList.setReceiptNo(cursor1.getString(cursor1.getColumnIndex("ReceiptNo")));
                    innerObj_Class_SandboxList.setSandboxID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SandboxID"))));
                    innerObj_Class_SandboxList.setSandboxName(cursor1.getString(cursor1.getColumnIndex("SandboxName")));
                    innerObj_Class_SandboxList.setSchoolID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SchoolID"))));
                    innerObj_Class_SandboxList.setSchoolName(cursor1.getString(cursor1.getColumnIndex("SchoolName")));
                    innerObj_Class_SandboxList.setStudentAadhar(cursor1.getString(cursor1.getColumnIndex("StudentAadhar")));
                    innerObj_Class_SandboxList.setStudentPhoto(cursor1.getString(cursor1.getColumnIndex("StudentPhoto")));
                    innerObj_Class_SandboxList.setStudentStatus(cursor1.getString(cursor1.getColumnIndex("StudentStatus")));
                    innerObj_Class_SandboxList.setStudentName(cursor1.getString(cursor1.getColumnIndex("StudentName")));
                    innerObj_Class_SandboxList.setStudentID(cursor1.getString(cursor1.getColumnIndex("StudentID")));
                    innerObj_Class_SandboxList.setTempID(cursor1.getString(cursor1.getColumnIndex("Stud_TempId")));
                    innerObj_Class_SandboxList.setLearningMode(cursor1.getString(cursor1.getColumnIndex("Learning_Mode")));


                    innerObj_Class_SandboxList.setState_ID(cursor1.getString(cursor1.getColumnIndex("stateid")));
                    innerObj_Class_SandboxList.setState_Name(cursor1.getString(cursor1.getColumnIndex("statename")));
                    innerObj_Class_SandboxList.setDistrict_ID(cursor1.getString(cursor1.getColumnIndex("districtid")));
                    innerObj_Class_SandboxList.setDistrict_Name(cursor1.getString(cursor1.getColumnIndex("districtname")));
                    innerObj_Class_SandboxList.setTaluk_ID(cursor1.getString(cursor1.getColumnIndex("talukid")));
                    innerObj_Class_SandboxList.setTaluk_Name(cursor1.getString(cursor1.getColumnIndex("talukname")));
                    innerObj_Class_SandboxList.setVillage_ID(cursor1.getString(cursor1.getColumnIndex("villageid")));
                    innerObj_Class_SandboxList.setVillage_Name(cursor1.getString(cursor1.getColumnIndex("villagename")));
                    innerObj_Class_SandboxList.setAddress(cursor1.getString(cursor1.getColumnIndex("student_address")));
                    innerObj_Class_SandboxList.setAlternate_Mobile(cursor1.getString(cursor1.getColumnIndex("alternate_mobile")));
                    innerObj_Class_SandboxList.setAdmission_date(cursor1.getString(cursor1.getColumnIndex("admission_date")));
                    innerObj_Class_SandboxList.setAdmission_remarks(cursor1.getString(cursor1.getColumnIndex("admission_remarks")));

                    class_farmerprofileoffline_array_obj[i] = innerObj_Class_SandboxList;
                    Log.e("fetch_DB_offline_data",cursor1.getString(cursor1.getColumnIndex("StudentName")));
                    Log.e("fetch_DB_offline_data",cursor1.getString(cursor1.getColumnIndex("StudentID")));
                    i++;
                } while (cursor1.moveToNext());
            }//if ends

        }


        db1.close();

        Log.e("length", String.valueOf(class_farmerprofileoffline_array_obj.length));

        //int_newfarmercount=0;
        for (int j = 0; j < class_farmerprofileoffline_array_obj.length; j++) {
            AddFarmerDetails(j);
        }
//        if (x == 0) {
//            fetch_DB_newfarmpond_offline_data();
//        }

    }
    public void AddFarmerDetails(int j) {
        Interface_userservice userService1;
        userService1 = Class_ApiUtils.getUserService();
        String StudentID= String.valueOf(class_farmerprofileoffline_array_obj[j].getStudentID());
        Log.e("cLASS","StudentID..ABC "+class_farmerprofileoffline_array_obj[j].getStudentID());

        Log.e("tag","StudentID=="+StudentID);
        if(StudentID.startsWith("edittemp")){
            Log.e("tag","StudentID temp=="+StudentID);
            StudentID="0";
        }

        AddStudentDetailsRequest request = new AddStudentDetailsRequest();
        request.setAcademicID(String.valueOf(class_farmerprofileoffline_array_obj[j].getAcademicID()));
        request.setSandboxID(String.valueOf(class_farmerprofileoffline_array_obj[j].getSandboxID()));
        request.setClusterID(String.valueOf(class_farmerprofileoffline_array_obj[j].getClusterID()));
        request.setInstituteID(String.valueOf(class_farmerprofileoffline_array_obj[j].getInstituteID()));
        request.setLevelID(String.valueOf(class_farmerprofileoffline_array_obj[j].getLevelID()));
        request.setStudentName(class_farmerprofileoffline_array_obj[j].getStudentName());
        request.setFatherName(class_farmerprofileoffline_array_obj[j].getFatherName());
        request.setMotherName(class_farmerprofileoffline_array_obj[j].getMotherName());
        request.setBirthDate(class_farmerprofileoffline_array_obj[j].getBirthDate());
        // request.setBirthDate("2005-01-22");
        request.setStudentAadhar(class_farmerprofileoffline_array_obj[j].getStudentAadhar());
        request.setMobile(class_farmerprofileoffline_array_obj[j].getMobile());
        request.setGender(class_farmerprofileoffline_array_obj[j].getGender());
        request.setEducation(class_farmerprofileoffline_array_obj[j].getEducation());

        request.setState_ID(class_farmerprofileoffline_array_obj[j].getState_ID());
       // request.setState_Name(class_farmerprofileoffline_array_obj[j].getState_Name());
        request.setDistrict_ID(class_farmerprofileoffline_array_obj[j].getDistrict_ID());
       // request.setDistrict_Name(class_farmerprofileoffline_array_obj[j].getDistrict_Name());
        request.setTaluk_ID(class_farmerprofileoffline_array_obj[j].getTaluk_ID());
      //  request.setTaluk_Name(class_farmerprofileoffline_array_obj[j].getTaluk_Name());
        request.setVillage_ID(class_farmerprofileoffline_array_obj[j].getVillage_ID());
       // request.setVillage_Name(class_farmerprofileoffline_array_obj[j].getVillage_Name());
        request.setAddress(class_farmerprofileoffline_array_obj[j].getAddress());

        if(!class_farmerprofileoffline_array_obj[j].getMarks4().equals("")){
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks4());

        }else if(!class_farmerprofileoffline_array_obj[j].getMarks5().equals("")){
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks5());

        }else if(!class_farmerprofileoffline_array_obj[j].getMarks6().equals("")){
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks6());

        }else if(!class_farmerprofileoffline_array_obj[j].getMarks7().equals("")){
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks7());

        }else if(!class_farmerprofileoffline_array_obj[j].getMarks8().equals("")){
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks8());

        }
        request.setAdmissionFee(class_farmerprofileoffline_array_obj[j].getPaidFee());
        request.setStudentStatus(class_farmerprofileoffline_array_obj[j].getStudentStatus());
        request.setStudentID(class_farmerprofileoffline_array_obj[j].getStudentID());
        request.setSchoolID(String.valueOf(class_farmerprofileoffline_array_obj[j].getSchoolID()));
        request.setCreatedBy(str_loginuserID);
        request.setCreatedDate(class_farmerprofileoffline_array_obj[j].getCreatedDate());
        //   request.setCreatedDate("2020-12-05");
        request.setReceiptManual(class_farmerprofileoffline_array_obj[j].getReceiptNo());
        //    request.setLearningMode(class_farmerprofileoffline_array_obj[j].g());
        request.setTemp_ID(class_farmerprofileoffline_array_obj[j].getTempID());
        request.setFileName(class_farmerprofileoffline_array_obj[j].getStudentPhoto());
//        Log.e("tag", "FarmerFirstName==" + class_farmerprofileoffline_array_obj[j].getStr_fname());
//        Log.e("tag", "FarmerID==" + class_farmerprofileoffline_array_obj[j].getStr_farmerID());
        request.setLearningMode(class_farmerprofileoffline_array_obj[j].getLearningMode());
        request.setApplication_Type("SIV");
        request.setDivision_ID(null);
        request.setAlternate_Mobile(class_farmerprofileoffline_array_obj[j].getAlternate_Mobile());
        request.setAdmission_Date(class_farmerprofileoffline_array_obj[j].getAdmission_date());
        request.setAdmission_Remarks(class_farmerprofileoffline_array_obj[j].getAdmission_remarks());
        Call<AddStudentDetailsResponse> call = userService1.Post_ActionStudent(request);

        Log.e("TAG", "Request 33: " + new Gson().toJson(request));
        Log.e("TAG", "Request: " + request.toString());

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_MarketingHomeScreen.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<AddStudentDetailsResponse>() {
            @Override
            public void onResponse(Call<AddStudentDetailsResponse> call, Response<AddStudentDetailsResponse> response) {
                // System.out.println("response" + response.body().toString());
                //    Log.e("tag", "response =" + response.body().toString());


                if (response.isSuccessful()) {
                    List<Class_Response_AddStudentDetailsList> addFarmerResList = response.body().getLst();
                    for (int i = 0; i < addFarmerResList.size(); i++) {


                        Log.e("tag", "addstudentresponse ID=" + addFarmerResList.get(i).getStudentID());

                        String str_response_stustatus= String.valueOf(addFarmerResList.get(i).getStudentStatus());
                        String str_response_student_id = String.valueOf(addFarmerResList.get(i).getStudentID());
                        String str_response_tempId = String.valueOf(addFarmerResList.get(i).getTemp_ID());
                        String str_response_applnNo=addFarmerResList.get(i).getApplicationNo();
//                    Log.e("tag", "getMobileTempID=" + addFarmerResList.getMobileTempID());
//                    Log.e("tag", "getFarmerCode=" + addFarmerResList.getFarmerCode());
//                    Log.e("tag", "getFarmerID=" + addFarmerResList.getFarmerID());

                        SQLiteDatabase db1 = getApplication().openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);

                        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");


                        ContentValues cv = new ContentValues();
                        cv.put("StudentID", str_response_student_id);
                        cv.put("Stud_TempId", str_response_tempId);
                        cv.put("UpadateOff_Online", "onlineedit");
                        cv.put("ApplicationNo", str_response_applnNo);
                        cv.put("StudentStatus", str_response_stustatus);
                        //   cv.put("UploadedStatusFarmerprofile", 10);

                        db1.update("StudentDetailsRest", cv, "Stud_TempId = ?", new String[]{str_response_tempId});
                        db1.close();

                        //       SQLiteDatabase db_viewfarmerlist = getApplication().openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);


//                    db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
//                            "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
//                            "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
//                            "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
//                            "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
//                            "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
//                            "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR,Farmer_Gender VARCHAR);");
//
//
//                    ContentValues cv_farmelistupdate = new ContentValues();
//                    cv_farmelistupdate.put("DispFarmerTable_Farmer_Code", str_response_farmercode);
//                    cv_farmelistupdate.put("DispFarmerTable_FarmerID", str_response_farmer_id);
//
//
//                    db_viewfarmerlist.update("ViewFarmerListRest", cv_farmelistupdate, "DispFarmerTable_Farmer_Code = ?", new String[]{str_response_tempId});
//                    db_viewfarmerlist.close();
                        Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();

                        progressDoalog.dismiss();

//                    if(int_newfarmercount>0){int_newfarmercount++;}
//                    else{ int_newfarmercount++; }

//                    if(int_newfarmercount==class_farmerprofileoffline_array_obj.length)
//                    {
//                        fetch_DB_newfarmpond_offline_data();
//                    }
                    }

                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // ??? and use it to show error information

                    // ??? or just log the issue like we???re doing :)
                    Log.d("error message", error.getMsg());

                    Toast.makeText(Activity_MarketingHomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(Activity_MarketingHomeScreen.this, "WS:Error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }

//////////////////////////////////////

    public void fetch_DB_farmerprofile_offline_data_add()
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");
      //  Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE Stud_TempId LIKE'"+ "newtemp%" + "'", null);
           Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE UpadateOff_Online='"+ "offline"  + "'", null);

        int x = cursor1.getCount();
        Log.e("marketing_studentcount", String.valueOf(x));

        int i = 0;
        class_farmerprofileoffline_array_obj = new StudentList[x];
        if (x > 0) {

            if (cursor1.moveToFirst()) {

                do {

                    StudentList innerObj_Class_SandboxList = new StudentList();
                    innerObj_Class_SandboxList.setAcademicID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("AcademicID"))));
                    innerObj_Class_SandboxList.setAcademicName(cursor1.getString(cursor1.getColumnIndex("AcademicName")));
                    innerObj_Class_SandboxList.setAdmissionFee(cursor1.getString(cursor1.getColumnIndex("AdmissionFee")));
                    innerObj_Class_SandboxList.setApplicationNo(cursor1.getString(cursor1.getColumnIndex("ApplicationNo")));
                    innerObj_Class_SandboxList.setBalanceFee(cursor1.getString(cursor1.getColumnIndex("BalanceFee")));
                    innerObj_Class_SandboxList.setBirthDate(cursor1.getString(cursor1.getColumnIndex("BirthDate")));
                    innerObj_Class_SandboxList.setClusterID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("ClusterID"))));//DispFarmerTable_Farmer_Code VARCHAR
                    innerObj_Class_SandboxList.setClusterName(cursor1.getString(cursor1.getColumnIndex("ClusterName")));//DispFarmerTable_Farmer_Code VARCHAR
                    innerObj_Class_SandboxList.setCreatedDate(cursor1.getString(cursor1.getColumnIndex("CreatedDate")));
                    innerObj_Class_SandboxList.setEducation(cursor1.getString(cursor1.getColumnIndex("Education")));
                    innerObj_Class_SandboxList.setFatherName(cursor1.getString(cursor1.getColumnIndex("FatherName")));
                    innerObj_Class_SandboxList.setGender(cursor1.getString(cursor1.getColumnIndex("Gender")));
                    innerObj_Class_SandboxList.setInstituteName(cursor1.getString(cursor1.getColumnIndex("InstituteName")));
                    innerObj_Class_SandboxList.setInstituteID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("InstituteID"))));
                    innerObj_Class_SandboxList.setLevelID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("LevelID"))));
                    innerObj_Class_SandboxList.setLevelName(cursor1.getString(cursor1.getColumnIndex("LevelName")));
                    innerObj_Class_SandboxList.setMarks4(cursor1.getString(cursor1.getColumnIndex("Marks4")));
                    innerObj_Class_SandboxList.setMarks5(cursor1.getString(cursor1.getColumnIndex("Marks5")));
                    innerObj_Class_SandboxList.setMarks6(cursor1.getString(cursor1.getColumnIndex("Marks6")));
                    innerObj_Class_SandboxList.setMarks7(cursor1.getString(cursor1.getColumnIndex("Marks7")));
                    innerObj_Class_SandboxList.setMarks8(cursor1.getString(cursor1.getColumnIndex("Marks8")));
                    innerObj_Class_SandboxList.setMobile(cursor1.getString(cursor1.getColumnIndex("Mobile")));
                    innerObj_Class_SandboxList.setMotherName(cursor1.getString(cursor1.getColumnIndex("MotherName")));
                    innerObj_Class_SandboxList.setPaidFee(cursor1.getString(cursor1.getColumnIndex("PaidFee")));
                    innerObj_Class_SandboxList.setReceiptNo(cursor1.getString(cursor1.getColumnIndex("ReceiptNo")));
                    innerObj_Class_SandboxList.setSandboxID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SandboxID"))));
                    innerObj_Class_SandboxList.setSandboxName(cursor1.getString(cursor1.getColumnIndex("SandboxName")));
                    innerObj_Class_SandboxList.setSchoolID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SchoolID"))));
                    innerObj_Class_SandboxList.setSchoolName(cursor1.getString(cursor1.getColumnIndex("SchoolName")));
                    innerObj_Class_SandboxList.setStudentAadhar(cursor1.getString(cursor1.getColumnIndex("StudentAadhar")));
                    innerObj_Class_SandboxList.setStudentPhoto(cursor1.getString(cursor1.getColumnIndex("StudentPhoto")));
                    innerObj_Class_SandboxList.setStudentStatus(cursor1.getString(cursor1.getColumnIndex("StudentStatus")));
                    innerObj_Class_SandboxList.setStudentName(cursor1.getString(cursor1.getColumnIndex("StudentName")));
                    innerObj_Class_SandboxList.setStudentID(cursor1.getString(cursor1.getColumnIndex("StudentID")));
                    innerObj_Class_SandboxList.setTempID(cursor1.getString(cursor1.getColumnIndex("Stud_TempId")));
                    innerObj_Class_SandboxList.setLearningMode(cursor1.getString(cursor1.getColumnIndex("Learning_Mode")));

                    innerObj_Class_SandboxList.setState_ID(cursor1.getString(cursor1.getColumnIndex("stateid")));
                    innerObj_Class_SandboxList.setState_Name(cursor1.getString(cursor1.getColumnIndex("statename")));
                    innerObj_Class_SandboxList.setDistrict_ID(cursor1.getString(cursor1.getColumnIndex("districtid")));
                    innerObj_Class_SandboxList.setDistrict_Name(cursor1.getString(cursor1.getColumnIndex("districtname")));
                    innerObj_Class_SandboxList.setTaluk_ID(cursor1.getString(cursor1.getColumnIndex("talukid")));
                    innerObj_Class_SandboxList.setTaluk_Name(cursor1.getString(cursor1.getColumnIndex("talukname")));
                    innerObj_Class_SandboxList.setVillage_ID(cursor1.getString(cursor1.getColumnIndex("villageid")));
                    innerObj_Class_SandboxList.setVillage_Name(cursor1.getString(cursor1.getColumnIndex("villagename")));
                    innerObj_Class_SandboxList.setAddress(cursor1.getString(cursor1.getColumnIndex("student_address")));
                    innerObj_Class_SandboxList.setAlternate_Mobile(cursor1.getString(cursor1.getColumnIndex("alternate_mobile")));
                    innerObj_Class_SandboxList.setAdmission_date(cursor1.getString(cursor1.getColumnIndex("admission_date")));
                    innerObj_Class_SandboxList.setAdmission_remarks(cursor1.getString(cursor1.getColumnIndex("admission_remarks")));

                    class_farmerprofileoffline_array_obj[i] = innerObj_Class_SandboxList;
                    Log.e("fetch_DB_offline_data",cursor1.getString(cursor1.getColumnIndex("StudentName")));
                    Log.e("fetch_DB_offline_data",cursor1.getString(cursor1.getColumnIndex("StudentID")));
                    i++;
                } while (cursor1.moveToNext());
            }//if ends

        }


        db1.close();

        Log.e("length", String.valueOf(class_farmerprofileoffline_array_obj.length));

        //int_newfarmercount=0;
        for (int j = 0; j < class_farmerprofileoffline_array_obj.length; j++) {
            AddFarmerDetails_new(j);
        }
//        if (x == 0) {
//            fetch_DB_newfarmpond_offline_data();
//        }

    }
    public void AddFarmerDetails_new(int j) {
        Interface_userservice userService1;
        userService1 = Class_ApiUtils.getUserService();
        String StudentID= String.valueOf(class_farmerprofileoffline_array_obj[j].getStudentID());
        Log.e("cLASS","StudentID..ABC "+class_farmerprofileoffline_array_obj[j].getStudentID());

        Log.e("tag","StudentID=="+StudentID);
        if(StudentID.startsWith("temp")){
            Log.e("tag","StudentID temp=="+StudentID);
            StudentID="0";
        }

        AddStudentDetailsRequest request = new AddStudentDetailsRequest();
        request.setAcademicID(String.valueOf(class_farmerprofileoffline_array_obj[j].getAcademicID()));
        request.setSandboxID(String.valueOf(class_farmerprofileoffline_array_obj[j].getSandboxID()));
        request.setClusterID(String.valueOf(class_farmerprofileoffline_array_obj[j].getClusterID()));
        request.setInstituteID(String.valueOf(class_farmerprofileoffline_array_obj[j].getInstituteID()));
        request.setLevelID(String.valueOf(class_farmerprofileoffline_array_obj[j].getLevelID()));
        request.setStudentName(class_farmerprofileoffline_array_obj[j].getStudentName());
        request.setFatherName(class_farmerprofileoffline_array_obj[j].getFatherName());
        request.setMotherName(class_farmerprofileoffline_array_obj[j].getMotherName());
        request.setBirthDate(class_farmerprofileoffline_array_obj[j].getBirthDate());
        // request.setBirthDate("2005-01-22");
        request.setStudentAadhar(class_farmerprofileoffline_array_obj[j].getStudentAadhar());
        request.setMobile(class_farmerprofileoffline_array_obj[j].getMobile());
        request.setGender(class_farmerprofileoffline_array_obj[j].getGender());
        request.setEducation(class_farmerprofileoffline_array_obj[j].getEducation());


        request.setState_ID(class_farmerprofileoffline_array_obj[j].getState_ID());
       // request.setState_Name(class_farmerprofileoffline_array_obj[j].getState_Name());
        request.setDistrict_ID(class_farmerprofileoffline_array_obj[j].getDistrict_ID());
        //request.setDistrict_Name(class_farmerprofileoffline_array_obj[j].getDistrict_Name());
        request.setTaluk_ID(class_farmerprofileoffline_array_obj[j].getTaluk_ID());
       // request.setTaluk_Name(class_farmerprofileoffline_array_obj[j].getTaluk_Name());
        request.setVillage_ID(class_farmerprofileoffline_array_obj[j].getVillage_ID());
       // request.setVillage_Name(class_farmerprofileoffline_array_obj[j].getVillage_Name());
        request.setAddress(class_farmerprofileoffline_array_obj[j].getAddress());

        if(!class_farmerprofileoffline_array_obj[j].getMarks4().equals("")){
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks4());

        }else if(!class_farmerprofileoffline_array_obj[j].getMarks5().equals("")){
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks5());

        }else if(!class_farmerprofileoffline_array_obj[j].getMarks6().equals("")){
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks6());

        }else if(!class_farmerprofileoffline_array_obj[j].getMarks7().equals("")){
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks7());

        }else if(!class_farmerprofileoffline_array_obj[j].getMarks8().equals("")){
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks8());

        }
        request.setAdmissionFee(class_farmerprofileoffline_array_obj[j].getPaidFee());
        request.setStudentStatus(class_farmerprofileoffline_array_obj[j].getStudentStatus());
        request.setStudentID(class_farmerprofileoffline_array_obj[j].getStudentID());
        request.setSchoolID(String.valueOf(class_farmerprofileoffline_array_obj[j].getSchoolID()));
        request.setCreatedBy(str_loginuserID);
        request.setCreatedDate(class_farmerprofileoffline_array_obj[j].getCreatedDate());
        //   request.setCreatedDate("2020-12-05");
        request.setReceiptManual(class_farmerprofileoffline_array_obj[j].getReceiptNo());
        //    request.setLearningMode(class_farmerprofileoffline_array_obj[j].g());
        request.setTemp_ID(class_farmerprofileoffline_array_obj[j].getTempID());
        request.setFileName(class_farmerprofileoffline_array_obj[j].getStudentPhoto());
//        Log.e("tag", "FarmerFirstName==" + class_farmerprofileoffline_array_obj[j].getStr_fname());
//        Log.e("tag", "FarmerID==" + class_farmerprofileoffline_array_obj[j].getStr_farmerID());
        request.setLearningMode(class_farmerprofileoffline_array_obj[j].getLearningMode());
        request.setApplication_Type("SIV");
        request.setDivision_ID(null);
        request.setAlternate_Mobile(class_farmerprofileoffline_array_obj[j].getAlternate_Mobile());
        request.setAdmission_Date(class_farmerprofileoffline_array_obj[j].getAdmission_date());
        request.setAdmission_Remarks(class_farmerprofileoffline_array_obj[j].getAdmission_remarks());
        Call<AddStudentDetailsResponse> call = userService1.Post_ActionStudent(request);

        Log.e("TAG", "Request 33: " + new Gson().toJson(request));
        Log.e("TAG", "Request: " + request.toString());

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_MarketingHomeScreen.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<AddStudentDetailsResponse>() {
            @Override
            public void onResponse(Call<AddStudentDetailsResponse> call, Response<AddStudentDetailsResponse> response) {
                // System.out.println("response" + response.body().toString());
                //    Log.e("tag", "response =" + response.body().toString());


                if (response.isSuccessful()) {
                    List<Class_Response_AddStudentDetailsList> addFarmerResList = response.body().getLst();
                    for (int i = 0; i < addFarmerResList.size(); i++) {


                        Log.e("tag", "addstudentresponse ID=" + addFarmerResList.get(i).getStudentID());


                        String str_response_student_id = String.valueOf(addFarmerResList.get(i).getStudentID());
                        String str_response_tempId = String.valueOf(addFarmerResList.get(i).getTemp_ID());
                        String str_response_applnNo = String.valueOf(addFarmerResList.get(i).getApplicationNo());
                        String str_response_stustatus= String.valueOf(addFarmerResList.get(i).getStudentStatus());


                        //                    Log.e("tag", "getMobileTempID=" + addFarmerResList.getMobileTempID());
//                    Log.e("tag", "getFarmerCode=" + addFarmerResList.getFarmerCode());
//                    Log.e("tag", "getFarmerID=" + addFarmerResList.getFarmerID());

                        SQLiteDatabase db1 = getApplication().openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);

                        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");


                        ContentValues cv = new ContentValues();
                        cv.put("StudentID", str_response_student_id);
                        cv.put("Stud_TempId", str_response_tempId);
                        cv.put("UpadateOff_Online", "online");
                        cv.put("ApplicationNo", str_response_applnNo);
                        cv.put("StudentStatus", str_response_stustatus);

                        db1.update("StudentDetailsRest", cv, "Stud_TempId = ?", new String[]{str_response_tempId});
                        db1.close();

                        //       SQLiteDatabase db_viewfarmerlist = getApplication().openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);


//                    db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
//                            "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
//                            "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
//                            "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
//                            "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
//                            "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
//                            "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR,Farmer_Gender VARCHAR);");
//
//
//                    ContentValues cv_farmelistupdate = new ContentValues();
//                    cv_farmelistupdate.put("DispFarmerTable_Farmer_Code", str_response_farmercode);
//                    cv_farmelistupdate.put("DispFarmerTable_FarmerID", str_response_farmer_id);
//
//
//                    db_viewfarmerlist.update("ViewFarmerListRest", cv_farmelistupdate, "DispFarmerTable_Farmer_Code = ?", new String[]{str_response_tempId});
//                    db_viewfarmerlist.close();
                        Toast.makeText(getApplicationContext(), "Submitted Successfully", Toast.LENGTH_SHORT).show();

                        progressDoalog.dismiss();

//                    if(int_newfarmercount>0){int_newfarmercount++;}
//                    else{ int_newfarmercount++; }

//                    if(int_newfarmercount==class_farmerprofileoffline_array_obj.length)
//                    {
//                        fetch_DB_newfarmpond_offline_data();
//                    }
                    }

                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // ??? and use it to show error information

                    // ??? or just log the issue like we???re doing :)
                    Log.d("error message", error.getMsg());

                    Toast.makeText(Activity_MarketingHomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(Activity_MarketingHomeScreen.this, "WS:Error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.schedule_menu, menu);
//        return true;
        getMenuInflater().inflate(R.menu.schedule_menu, menu);
        menu.findItem(R.id.logout_menu)
                .setVisible(false);
        return super.onCreateOptionsMenu(menu);

    }








    public void deleteOfflineTable(){

        SQLiteDatabase db_deleteTable= openOrCreateDatabase("StudentRegistrationOfflineDB", Context.MODE_PRIVATE, null);//id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        //  db1.execSQL("CREATE TABLE IF NOT EXISTS OfflineStudentTable(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,sandboxid VARCHAR,academicid VARCHAR,clusterid VARCHAR,instituteid VARCHAR,levelid VARCHAR,schoolid VARCHAR,image BLOB NOT NULL,studentname VARCHAR,gender VARCHAR,birthdate VARCHAR,education VARCHAR,marks VARCHAR,mobileno VARCHAR,fathername VARCHAR,mothername VARCHAR,aadharno VARCHAR,studentstatus VARCHAR,admissionfee VARCHAR,createddate VARCHAR,createdby VARCHAR);");
        db_deleteTable.execSQL("CREATE TABLE IF NOT EXISTS OfflineStudentTable(sandboxid VARCHAR,academicid VARCHAR,clusterid VARCHAR,instituteid VARCHAR,levelid VARCHAR,schoolid VARCHAR,image BLOB NOT NULL,studentname VARCHAR,gender VARCHAR,birthdate VARCHAR,education VARCHAR,marks VARCHAR,mobileno VARCHAR,fathername VARCHAR,mothername VARCHAR,aadharno VARCHAR,studentstatus VARCHAR,admissionfee VARCHAR,createddate VARCHAR,createdby VARCHAR,receiptno VARCHAR);");

        Cursor cursor = db_deleteTable.rawQuery("SELECT * FROM OfflineStudentTable", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_deleteTable.delete("OfflineStudentTable", null, null);
            //  Toast.makeText(getApplicationContext(),"All records deleted",Toast.LENGTH_LONG).show();
        }
        db_deleteTable.close();

    }



    @Override
    public void onBackPressed() {
       /* super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Activity_HomeScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();*/
        Intent i = new Intent(Activity_MarketingHomeScreen.this, Activity_HomeScreen.class);
        startActivity(i);
        finish();
    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {
            case R.id.logout_menu:
                Class_SaveSharedPreference.setUserName(Activity_MarketingHomeScreen.this, "");
                Class_SaveSharedPreference.setPREF_MENU_link(Activity_MarketingHomeScreen.this, "");
                Class_SaveSharedPreference.setPrefFlagUsermanual(Activity_MarketingHomeScreen.this, "");
                Class_SaveSharedPreference.setSupportEmail(Activity_MarketingHomeScreen.this, "");
                Class_SaveSharedPreference.setUserMailID(Activity_MarketingHomeScreen.this, "");
                Class_SaveSharedPreference.setSupportPhone(Activity_MarketingHomeScreen.this, "");

                SharedPreferences.Editor myprefs_UserImg = sharedpref_userimage_Obj.edit();
                myprefs_UserImg.putString(key_userimage, "");
                myprefs_UserImg.apply();
                SharedPreferences.Editor myprefs_Username = sharedpref_username_Obj.edit();
                myprefs_Username.putString(Key_username, "");
                myprefs_Username.apply();

                deleteStateRestTable_B4insertion();
                deleteDistrictRestTable_B4insertion();
                deleteTalukRestTable_B4insertion();
                deleteVillageRestTable_B4insertion();
                deleteYearRestTable_B4insertion();
                deleteSchoolRestTable_B4insertion();
                deleteSandboxRestTable_B4insertion();
                deleteInstituteRestTable_B4insertion();
                deleteLevelRestTable_B4insertion();
                deleteClusterRestTable_B4insertion();

                AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_MarketingHomeScreen.this);
                dialog.setCancelable(false);
                dialog.setTitle(R.string.alert);
                dialog.setMessage("Are you sure want to Logout?");

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {

                        //   SaveSharedPreference.setUserName(Activity_HomeScreen.this, "");
                        Class_SaveSharedPreference.setUserName(getApplicationContext(),"");

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("logout_key1", "yes");
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();


                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Action for "Cancel".
                                dialog.dismiss();
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                    }
                });
                alert.show();
                break;
            case android.R.id.home:
                Intent backpressintent = new Intent(Activity_MarketingHomeScreen.this, Activity_HomeScreen.class);
                startActivity(backpressintent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public void deleteStateRestTable_B4insertion() {

        SQLiteDatabase db_statelist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_statelist_delete.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");
        Cursor cursor = db_statelist_delete.rawQuery("SELECT * FROM StateListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_statelist_delete.delete("StateListRest", null, null);

        }
        db_statelist_delete.close();
    }
    public void deleteDistrictRestTable_B4insertion() {

        SQLiteDatabase db_districtlist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_districtlist_delete.execSQL("CREATE TABLE IF NOT EXISTS DistrictListRest(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db_districtlist_delete.rawQuery("SELECT * FROM DistrictListRest", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_districtlist_delete.delete("DistrictListRest", null, null);

        }
        db_districtlist_delete.close();
    }
    public void deleteTalukRestTable_B4insertion() {

        SQLiteDatabase db_taluklist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_taluklist_delete.execSQL("CREATE TABLE IF NOT EXISTS TalukListRest(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db_taluklist_delete.rawQuery("SELECT * FROM TalukListRest", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_taluklist_delete.delete("TalukListRest", null, null);

        }
        db_taluklist_delete.close();
    }
    public void deleteVillageRestTable_B4insertion() {

        SQLiteDatabase db_villagelist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        // db_villagelist_delete.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db_villagelist_delete.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        Cursor cursor1 = db_villagelist_delete.rawQuery("SELECT * FROM VillageListRest", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_villagelist_delete.delete("VillageListRest", null, null);

        }
        db_villagelist_delete.close();
    }
    public void deleteYearRestTable_B4insertion() {

        SQLiteDatabase db_yearlist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_yearlist_delete.execSQL("CREATE TABLE IF NOT EXISTS YearListRest(YearID VARCHAR,YearName VARCHAR,Sandbox_ID VARCHAR);");
        Cursor cursor = db_yearlist_delete.rawQuery("SELECT * FROM YearListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_yearlist_delete.delete("YearListRest", null, null);

        }
        db_yearlist_delete.close();
    }
    public void deleteSchoolRestTable_B4insertion() {

        SQLiteDatabase db_Schoollist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_Schoollist_delete.execSQL("CREATE TABLE IF NOT EXISTS SchoolListRest(SchoolName VARCHAR, SchoolID VARCHAR, School_InstituteID VARCHAR);");
        Cursor cursor = db_Schoollist_delete.rawQuery("SELECT * FROM SchoolListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_Schoollist_delete.delete("SchoolListRest", null, null);

        }
        db_Schoollist_delete.close();
    }
    public void deleteSandboxRestTable_B4insertion() {

        SQLiteDatabase db_Sandboxlist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_Sandboxlist_delete.execSQL("CREATE TABLE IF NOT EXISTS SandboxListRest(SandboxName VARCHAR,SandboxID VARCHAR);");
        Cursor cursor = db_Sandboxlist_delete.rawQuery("SELECT * FROM SandboxListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_Sandboxlist_delete.delete("SandboxListRest", null, null);

        }
        db_Sandboxlist_delete.close();
    }
    public void deleteInstituteRestTable_B4insertion() {

        SQLiteDatabase db_Institutelist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_Institutelist_delete.execSQL("CREATE TABLE IF NOT EXISTS InstituteListRest(InstituteName VARCHAR, InstituteID VARCHAR,Inst_AcademicID VARCHAR,Inst_ClusterID VARCHAR);");
        Cursor cursor = db_Institutelist_delete.rawQuery("SELECT * FROM InstituteListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_Institutelist_delete.delete("InstituteListRest", null, null);

        }
        db_Institutelist_delete.close();
    }
    public void deleteLevelRestTable_B4insertion() {

        SQLiteDatabase db_Levellist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_Levellist_delete.execSQL("CREATE TABLE IF NOT EXISTS LevelListRest(LevelName VARCHAR, LevelID VARCHAR, Level_InstituteID VARCHAR,Level_AcademicID VARCHAR,Level_ClusterID VARCHAR,Level_AdmissionFee VARCHAR);");
        Cursor cursor = db_Levellist_delete.rawQuery("SELECT * FROM LevelListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_Levellist_delete.delete("LevelListRest", null, null);

        }
        db_Levellist_delete.close();
    }
    public void deleteClusterRestTable_B4insertion() {

        SQLiteDatabase db_Clusterlist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_Clusterlist_delete.execSQL("CREATE TABLE IF NOT EXISTS ClusterListRest(ClusterName VARCHAR,ClusterID VARCHAR,Clust_AcademicID VARCHAR, Clust_SandboxID VARCHAR);");
        Cursor cursor = db_Clusterlist_delete.rawQuery("SELECT * FROM ClusterListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_Clusterlist_delete.delete("ClusterListRest", null, null);

        }
        db_Clusterlist_delete.close();
    }






















}// end of class
