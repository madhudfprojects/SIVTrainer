package com.det.skillinvillage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.Activity_ViewStudentList_new.key_studentid;
import static com.det.skillinvillage.Activity_ViewStudentList_new.sharedpreferenc_selectedspinner;
import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;


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
    String str_logout_count_Status = "", str_loginuserID = "";
    SharedPreferences sharedpref_loginuserid_Obj;
    SharedPreferences sharedpref_stuid_Obj;
    int str_stuID = 0;
    StudentList[] class_farmerprofileoffline_array_obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketingform);


        // toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);

        sharedpref_validMailID_Obj = getSharedPreferences(sharedpreferenc_mailbook, Context.MODE_PRIVATE);

        sharedpref_loginuserid_Obj = getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();


//        sharedpref_stuid_Obj = getSharedPreferences(sharedpreferenc_selectedspinner, Context.MODE_PRIVATE);
//        str_stuID = sharedpref_stuid_Obj.getInt(key_studentid, 0);
//        Log.e("str_stuID.1oncreate..", String.valueOf(str_stuID));


        student_registrationform_ib = findViewById(R.id.student_registrationform_IB);
        student_referfriend_ib = findViewById(R.id.student_referfriend_IB);
        applicationdetails_ib = findViewById(R.id.applicationdetails_IB);
        feessubmit_ib = findViewById(R.id.feessubmit_IB);
        college_addmission_history_IB = findViewById(R.id.college_addmission_history_IB);

        Notuploadedcount_tv = findViewById(R.id.Notuploadedcount_TV);
        Notuploadedcount_addnew_TV=(TextView)findViewById(R.id.Notuploadedcount_addnew_TV);
        notupload_ib = findViewById(R.id.notupload_IB);
        // Set upon the actionbar
//        setSupportActionBar(toolbar);
        // Now use actionbar methods to show navigation icon and title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Student Registration");


        //////////////Added by shivaleela on sept 3rd 2019


        @SuppressLint("ResourceType")
        Animation animation_stuRegistration = AnimationUtils.loadAnimation(this, R.anim.translate);
        // animation1.setDuration(2100);
        animation_stuRegistration.setDuration(1100);
        student_registrationform_ib.setAnimation(animation_stuRegistration);
        student_registrationform_ib.animate();
        animation_stuRegistration.start();


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

        @SuppressLint("ResourceType")
        Animation animation_notuploadedcount = AnimationUtils.loadAnimation(this, R.anim.translate);
//        animation4.setDuration(2100);
        animation_notuploadedcount.setDuration(1100);
        notupload_ib.setAnimation(animation_notuploadedcount);
        notupload_ib.animate();
        animation_notuploadedcount.start();

        //////////////////////////////////////////////

        gps_object = new Class_GPSTracker(Activity_MarketingHomeScreen.this);

        student_registrationform_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // if(gps_object.canGetLocation()) {
                //Commented and added by shivaleela on june 27th 2019

//                    Intent i = new Intent(Activity_MarketingHomeScreen.this, Activity_Register.class);
//                    startActivity(i);
//                    finish();

                Intent i = new Intent(Activity_MarketingHomeScreen.this, Activity_Register_New.class);
                startActivity(i);
                finish();

                //  }
//                else
//                {gps_object.showSettingsAlert();}

            }
        });

        student_referfriend_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gps_object.canGetLocation()) {
                    Toast.makeText(getApplicationContext(), "Currently Disabled", Toast.LENGTH_LONG).show();


//                    Intent i = new Intent(Activity_MarketingHomeScreen.this, Activity_Refer.class);
//                    startActivity(i);
//                    finish();
                } else {
                    gps_object.showSettingsAlert();
                }

            }
        });


        applicationdetails_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Activity_MarketingHomeScreen.this, Activity_ViewStudentList_new.class);
                startActivity(i);
                finish();


//                internetDectector = new Class_InternetDectector(getApplicationContext());
//                isInternetPresent = internetDectector.isConnectingToInternet();
//
//                if (isInternetPresent)
//                {
//
////                Intent i = new Intent(Activity_MarketingHomeScreen.this,Activity_ApplicationDetails.class);
////                startActivity(i);
////                finish();
//
////                    Intent i = new Intent(Activity_MarketingHomeScreen.this,Activity_Student_List.class);
////                    startActivity(i);
////                    finish();
//
//                    Intent i = new Intent(Activity_MarketingHomeScreen.this,Activity_ViewStudentList_new.class);
//                    startActivity(i);
//                    finish();
//                }else{
//                    Toast.makeText(getApplicationContext(), "Connect to Internet", Toast.LENGTH_LONG).show();
//                }
//

            }
        });


        feessubmit_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                internetDectector = new Class_InternetDectector(getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();

//                if (isInternetPresent)
//                {
//                    //Toast.makeText(getApplicationContext(), "Currently Disabled", Toast.LENGTH_LONG).show();
//
//                    Intent i = new Intent(Activity_MarketingHomeScreen.this,Activity_FeesSubmit_New.class);
//                    startActivity(i);
//                    finish();
//                }else{
//                    Toast.makeText(getApplicationContext(), "Connect to Internet", Toast.LENGTH_LONG).show();
//                }

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
            // getOfflineRecord();
        } else {
            fetch_DB_farmerprofile_offline_data_new_edit();
            fetch_DB_farmerprofile_offline_data_new_add();

            //getOfflineDBcount();
        }
       // deleteStudentdetailsRestTable_B4insertion();
    }// end of Oncreate()


    public void fetch_DB_farmerprofile_offline_data_new_add() {
        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR);");
       // Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE Stud_TempId LIKE'" + "newtemp%" + "'", null);
           Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE UpadateOff_Online='"+ "offline"  + "'", null);
        int x = cursor1.getCount();
        Log.e("marketing_studaddd", String.valueOf(x));
        int i = 0;
        class_farmerprofileoffline_array_obj = new StudentList[x];
        if (x > 0) {
            Notuploadedcount_addnew_TV.setText("Not uploaded: " + x);
        }
        db1.close();
    }

    public void fetch_DB_farmerprofile_offline_data_new_edit() {
        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR);");
     //   Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE Stud_TempId LIKE'" + "edittemp%" + "'", null);
        //   Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE StudentID='"+ stuid  + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE UpadateOff_Online='"+ "offlineedit"  + "'", null);
        int x = cursor1.getCount();
        Log.e("marketing_edit", String.valueOf(x));
        int i = 0;
        class_farmerprofileoffline_array_obj = new StudentList[x];
        if (x > 0) {
            Notuploadedcount_tv.setText("Not Uploaded: " + x);
        }
        db1.close();
    }
////////////////////////
    public void fetch_DB_farmerprofile_offline_data_edit()
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR);");
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
        request.setAdmissionFee(class_farmerprofileoffline_array_obj[j].getAdmissionFee());
        request.setStudentStatus(class_farmerprofileoffline_array_obj[j].getStudentStatus());
        request.setStudentID(class_farmerprofileoffline_array_obj[j].getStudentID());
        request.setSchoolID(String.valueOf(class_farmerprofileoffline_array_obj[j].getSchoolID()));
        request.setCreatedBy(String.valueOf(str_stuID));
        request.setCreatedDate(class_farmerprofileoffline_array_obj[j].getCreatedDate());
        //   request.setCreatedDate("2020-12-05");
        request.setReceiptManual(class_farmerprofileoffline_array_obj[j].getReceiptNo());
        //    request.setLearningMode(class_farmerprofileoffline_array_obj[j].g());
        request.setTemp_ID(class_farmerprofileoffline_array_obj[j].getTempID());
        request.setFileName(class_farmerprofileoffline_array_obj[j].getStudentPhoto());
//        Log.e("tag", "FarmerFirstName==" + class_farmerprofileoffline_array_obj[j].getStr_fname());
//        Log.e("tag", "FarmerID==" + class_farmerprofileoffline_array_obj[j].getStr_farmerID());
        request.setLearningMode(class_farmerprofileoffline_array_obj[j].getLearningMode());

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
//                    Log.e("tag", "getMobileTempID=" + addFarmerResList.getMobileTempID());
//                    Log.e("tag", "getFarmerCode=" + addFarmerResList.getFarmerCode());
//                    Log.e("tag", "getFarmerID=" + addFarmerResList.getFarmerID());

                        SQLiteDatabase db1 = getApplication().openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);

                        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR);");


                        ContentValues cv = new ContentValues();
                        cv.put("StudentID", str_response_student_id);
                        cv.put("Stud_TempId", str_response_tempId);
                        cv.put("UpadateOff_Online", "onlineedit");

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
                        Toast.makeText(getApplicationContext(), "Edition Updated Successfully", Toast.LENGTH_SHORT).show();

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
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
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
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR);");
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
        request.setAdmissionFee(class_farmerprofileoffline_array_obj[j].getAdmissionFee());
        request.setStudentStatus(class_farmerprofileoffline_array_obj[j].getStudentStatus());
        request.setStudentID(class_farmerprofileoffline_array_obj[j].getStudentID());
        request.setSchoolID(String.valueOf(class_farmerprofileoffline_array_obj[j].getSchoolID()));
        request.setCreatedBy(String.valueOf(str_stuID));
        request.setCreatedDate(class_farmerprofileoffline_array_obj[j].getCreatedDate());
        //   request.setCreatedDate("2020-12-05");
        request.setReceiptManual(class_farmerprofileoffline_array_obj[j].getReceiptNo());
        //    request.setLearningMode(class_farmerprofileoffline_array_obj[j].g());
        request.setTemp_ID(class_farmerprofileoffline_array_obj[j].getTempID());
        request.setFileName(class_farmerprofileoffline_array_obj[j].getStudentPhoto());
//        Log.e("tag", "FarmerFirstName==" + class_farmerprofileoffline_array_obj[j].getStr_fname());
//        Log.e("tag", "FarmerID==" + class_farmerprofileoffline_array_obj[j].getStr_farmerID());
        request.setLearningMode(class_farmerprofileoffline_array_obj[j].getLearningMode());

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
//                    Log.e("tag", "getMobileTempID=" + addFarmerResList.getMobileTempID());
//                    Log.e("tag", "getFarmerCode=" + addFarmerResList.getFarmerCode());
//                    Log.e("tag", "getFarmerID=" + addFarmerResList.getFarmerID());

                        SQLiteDatabase db1 = getApplication().openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);

                        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR);");


                        ContentValues cv = new ContentValues();
                        cv.put("StudentID", str_response_student_id);
                        cv.put("Stud_TempId", str_response_tempId);
                        cv.put("UpadateOff_Online", "online");

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
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
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




   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu items
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent i =new Intent(Activity_MarketingHomeScreen.this,Activity_HomeScreen.class);
                startActivity(i);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.schedule_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share) {
            // Toast.makeText(CalenderActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            internetDectector = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent) {

                Class_SaveSharedPreference.setUserName(getApplicationContext(),"");
                deleteSandBoxTable_B4insertion();
                deleteAcademicTable_B4insertion();
                deleteClusterTable_B4insertion();
                deleteInstituteTable_B4insertion();
                deleteSchoolTable_B4insertion();
                deleteLevelTable_B4insertion();
                deleteLearningOptionTable_B4insertion();

                LogoutCountAsynctask logoutCountAsynctask=new LogoutCountAsynctask(Activity_MarketingHomeScreen.this);
                logoutCountAsynctask.execute();




                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("logout_key1", "yes");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

//                Intent i = new Intent(getApplicationContext(), NormalLogin.class);
//                i.putExtra("logout_key1", "yes");
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(i);
                //finish();
                //}
            }
            else{
                Toast.makeText(getApplicationContext(),"No Internet", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if(id== android.R.id.home) {
            //  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Activity_MarketingHomeScreen.this, Activity_HomeScreen.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void deleteStudentdetailsRestTable_B4insertion() {

        SQLiteDatabase db_studentDetails = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_studentDetails.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR,AcademicName VARCHAR,AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR," +
                "ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR," +
                "Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR,Base64image VARCHAR,Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR);");
        @SuppressLint("Recycle")
        Cursor cursor = db_studentDetails.rawQuery("SELECT * FROM StudentDetailsRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_studentDetails.delete("StudentDetailsRest", null, null);

            Log.e("StudentDetailsRest","deleted table successfully");
        }
        db_studentDetails.close();
        Log.e("deleted", "deleteStudentdetailsRestTable_B4insertion");

    }


    private void getOfflineRecord() {
        db = this.openOrCreateDatabase("StudentRegistrationOfflineDB", Context.MODE_PRIVATE, null);
        // db1.execSQL("CREATE TABLE IF NOT EXISTS OfflineStudentTable(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,sandboxid VARCHAR,academicid VARCHAR,clusterid VARCHAR,instituteid VARCHAR,levelid VARCHAR,schoolid VARCHAR,image BLOB NOT NULL,studentname VARCHAR,gender VARCHAR,birthdate VARCHAR,education VARCHAR,marks VARCHAR,mobileno VARCHAR,fathername VARCHAR,mothername VARCHAR,aadharno VARCHAR,studentstatus VARCHAR,admissionfee VARCHAR,createddate VARCHAR,createdby VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS OfflineStudentTable(sandboxid VARCHAR,academicid VARCHAR,clusterid VARCHAR,instituteid VARCHAR,levelid VARCHAR,schoolid VARCHAR,image BLOB NOT NULL,studentname VARCHAR,gender VARCHAR,birthdate VARCHAR,education VARCHAR,marks VARCHAR,mobileno VARCHAR,fathername VARCHAR,mothername VARCHAR,aadharno VARCHAR,studentstatus VARCHAR,admissionfee VARCHAR,createddate VARCHAR,createdby VARCHAR,receiptno VARCHAR,learnOption VARCHAR);");

        cursor = db.rawQuery("SELECT DISTINCT * FROM OfflineStudentTable", null);
        int x = cursor.getCount();

        int_cousorcount=0;
        int_insidecursorcount=0;

        int_cousorcount=x;
        Log.d("cursor count", Integer.toString(x));
//         int i = 0;
        if (cursor.getCount() > 0) {
            Submit_Data();
        }
//        if (cursor.getCount() > 0) {
////            do {
//                if (cursor.moveToFirst())
//                {
//                    do {
//
//                    try {
//
//                        int_fetch_sandboxid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("sandboxid")));
//                        int_fetch_academicid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("academicid")));
//                        int_fetch_clusterid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("clusterid")));
//                        int_fetch_instituteid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("instituteid")));
//                        int_fetch_schoolid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("schoolid")));
//                        int_fetch_levelid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("levelid")));
//                        str_fetch_image = cursor.getString(cursor.getColumnIndex("image"));
//                        str_fetch_studentname = cursor.getString(cursor.getColumnIndex("studentname"));
//                        str_fetch_gender = cursor.getString(cursor.getColumnIndex("gender"));
//                        str_fetch_birthdate = cursor.getString(cursor.getColumnIndex("birthdate"));
//                        str_fetch_education = cursor.getString(cursor.getColumnIndex("education"));
//                        str_fetch_marks = cursor.getString(cursor.getColumnIndex("marks"));
//                        str_fetch_mobileno = cursor.getString(cursor.getColumnIndex("mobileno"));
//                        str_fetch_fathername = cursor.getString(cursor.getColumnIndex("fathername"));
//                        str_fetch_mothername = cursor.getString(cursor.getColumnIndex("mothername"));
//                        str_fetch_aadharno = cursor.getString(cursor.getColumnIndex("aadharno"));
//                        str_fetch_studentstatus = cursor.getString(cursor.getColumnIndex("studentstatus"));
//                        str_fetch_admissionfee = cursor.getString(cursor.getColumnIndex("admissionfee"));
//                        str_fetch_createddate = cursor.getString(cursor.getColumnIndex("createddate"));
//                        str_fetch_createdby = cursor.getString(cursor.getColumnIndex("createdby"));
//
//                        Log.e("str_fetch_studentname", str_fetch_studentname);
////                        Log.e("str_fetch_studentstatus", str_fetch_studentstatus);
//
//                        Log.e("val-before", String.valueOf(i));
//
//                      //  Submit_Data();
//                       // Log.e("after_studentname", str_fetch_studentname);
//
//
//                        i++;
//                Log.e("val-after inc", String.valueOf(i));
//
//                        int_insidecursorcount = i;
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    } while (cursor.moveToNext());
//                    db.close();
//                }
//                Log.e("val-after", String.valueOf(i));
//                i++;
//                Log.e("val-after inc", String.valueOf(i));
//            } while (cursor.moveToNext());



//            db1.close();
        // }


    }


    private void Submit_Data() {
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();
        if (isInternetPresent) {
            SubmitMethodAsyncTask task = new SubmitMethodAsyncTask();
            task.execute();
        } else {

            Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_LONG).show();
        }
    }

    public class SubmitMethodAsyncTask extends AsyncTask<String, Void, Void> {
        ProgressDialog progressDialog = new ProgressDialog(Activity_MarketingHomeScreen.this,R.style.AppCompatAlertDialogStyle);

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(Activity_MarketingHomeScreen.this, "Syncing", "Data syncing please wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params)
        {

            // if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    try {

                        int_fetch_sandboxid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("sandboxid")));
                        int_fetch_academicid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("academicid")));
                        int_fetch_clusterid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("clusterid")));
                        int_fetch_instituteid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("instituteid")));
                        int_fetch_schoolid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("schoolid")));
                        int_fetch_levelid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("levelid")));
                        str_fetch_image = cursor.getString(cursor.getColumnIndex("image"));
                        str_fetch_studentname = cursor.getString(cursor.getColumnIndex("studentname"));
                        str_fetch_gender = cursor.getString(cursor.getColumnIndex("gender"));
                        str_fetch_birthdate = cursor.getString(cursor.getColumnIndex("birthdate"));
                        str_fetch_education = cursor.getString(cursor.getColumnIndex("education"));
                        str_fetch_marks = cursor.getString(cursor.getColumnIndex("marks"));
                        str_fetch_mobileno = cursor.getString(cursor.getColumnIndex("mobileno"));
                        str_fetch_fathername = cursor.getString(cursor.getColumnIndex("fathername"));
                        str_fetch_mothername = cursor.getString(cursor.getColumnIndex("mothername"));
                        str_fetch_aadharno = cursor.getString(cursor.getColumnIndex("aadharno"));
                        str_fetch_studentstatus = cursor.getString(cursor.getColumnIndex("studentstatus"));
                        str_fetch_admissionfee = cursor.getString(cursor.getColumnIndex("admissionfee"));
                        str_fetch_createddate = cursor.getString(cursor.getColumnIndex("createddate"));
                        str_fetch_createdby = cursor.getString(cursor.getColumnIndex("createdby"));
                        str_fetch_receiptno= cursor.getString(cursor.getColumnIndex("receiptno"));
                        str_fetch_learnOption= cursor.getString(cursor.getColumnIndex("learnOption"));
                        Log.e("str_fetch_studentname", str_fetch_studentname);
                        Log.e("val-before", String.valueOf(i));
                        Log.e("str_fetch_studentname", str_fetch_studentname);


                        RegisterResponse_marketingscreen = WebserviceRegister.register_new(int_fetch_sandboxid,
                                int_fetch_academicid, int_fetch_clusterid, int_fetch_instituteid,
                                int_fetch_levelid, int_fetch_schoolid, str_fetch_image, str_fetch_studentname, str_fetch_gender,
                                str_fetch_birthdate, str_fetch_education, str_fetch_marks, str_fetch_mobileno, str_fetch_fathername, str_fetch_mothername, str_fetch_aadharno,
                                str_fetch_studentstatus, str_fetch_admissionfee, str_fetch_createddate, str_fetch_createdby,str_fetch_receiptno,str_fetch_learnOption);


                        Log.e("RegiResponse_mar.", String.valueOf(RegisterResponse_marketingscreen));


                        i++;
                        Log.e("val-after inc", String.valueOf(i));

                        int_insidecursorcount = i;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
                db.close();
            }
            // }

            return null;
        }// doInBackground Process

        @Override
        //Once WebService returns response
        protected void onPostExecute(Void result) {


            //Make Progress Bar invisible
            progressDialog.dismiss();

            if (!RegisterResponse_marketingscreen) {


                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

            } else {
                Log.e("int_cousorcount", String.valueOf(int_cousorcount));
                Log.e("insidecousorcount", String.valueOf(int_insidecursorcount));

                if(int_cousorcount==int_insidecursorcount) {

                    deleteOfflineTable();
                }

                Toast.makeText(getApplicationContext(), "Application Submitted", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }//End of onProgressUpdates
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

    private void getOfflineDBcount() {
        SQLiteDatabase db = this.openOrCreateDatabase("StudentRegistrationOfflineDB", Context.MODE_PRIVATE, null);
        // db1.execSQL("CREATE TABLE IF NOT EXISTS OfflineStudentTable(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,sandboxid VARCHAR,academicid VARCHAR,clusterid VARCHAR,instituteid VARCHAR,levelid VARCHAR,schoolid VARCHAR,image BLOB NOT NULL,studentname VARCHAR,gender VARCHAR,birthdate VARCHAR,education VARCHAR,marks VARCHAR,mobileno VARCHAR,fathername VARCHAR,mothername VARCHAR,aadharno VARCHAR,studentstatus VARCHAR,admissionfee VARCHAR,createddate VARCHAR,createdby VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS OfflineStudentTable(sandboxid VARCHAR,academicid VARCHAR,clusterid VARCHAR,instituteid VARCHAR,levelid VARCHAR,schoolid VARCHAR,image BLOB NOT NULL,studentname VARCHAR,gender VARCHAR,birthdate VARCHAR,education VARCHAR,marks VARCHAR,mobileno VARCHAR,fathername VARCHAR,mothername VARCHAR,aadharno VARCHAR,studentstatus VARCHAR,admissionfee VARCHAR,createddate VARCHAR,createdby VARCHAR,receiptno VARCHAR);");

        Cursor cursor = db.rawQuery("SELECT DISTINCT * FROM OfflineStudentTable", null);
        OfflineStudentTable_count = cursor.getCount();
        if(OfflineStudentTable_count>0) {
//            Notuploadedcount_tv.setText("Not Uploaded count : " + OfflineStudentTable_count);
            Notuploadedcount_tv.setText(""+OfflineStudentTable_count);

        }
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







    /////////////////////////////////////////////logoutcounts//////////////////


    public class LogoutCountAsynctask extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;
        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("df", "doInBackground");

            getlogoutcount();
            return null;
        }

        public LogoutCountAsynctask(Activity_MarketingHomeScreen activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {


            dialog.dismiss();
            if(!str_logout_count_Status.equals("")){
                if (str_logout_count_Status.equals("Success")) {
                    //Toast.makeText(Activity_MarketingHomeScreen.this, "Success", Toast.LENGTH_SHORT).show();

                } else if (str_logout_count_Status.equals("Failure")){
                    //Toast.makeText(Activity_MarketingHomeScreen.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            }
        }//end of onPostExecute
    }// end Async task

    public void getlogoutcount() {
        Vector<SoapObject> result1 = null;

        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "userLogoutrecord";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/userLogoutrecord";

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            if(!str_loginuserID.equals("")) {
                request.addProperty("user_id", str_loginuserID);
                Log.i("request", request.toString());
            }
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("value at response", response.getProperty(0).toString());
                SoapObject obj2 =(SoapObject) response.getProperty(0);
                Log.e("obj2", obj2.toString());

                if (response.getProperty(0).toString().contains("status")) {
                    Log.e("str_logout_count_Status", "hello");
                    if (response.getProperty(0).toString().contains("status=Success")) {
                        Log.e("str_logout_count_Status", "success");

                        SoapPrimitive soap_status = (SoapPrimitive) obj2.getProperty("status");
                        str_logout_count_Status = soap_status.toString();
                        Log.e("str_logout_count_Status", str_logout_count_Status);


                    }
                } else {
                    Log.e("str_logout_count_Status", "str_logout_count_Status=null");

                }

            } catch (Throwable t) {
                Log.e("request fail", "> " + t.getMessage());
                str_logout_count_Status = "slow internet";

            }
        } catch (Throwable t) {
            Log.e("UnRegister Receiver ", "> " + t.getMessage());

        }

    }


    public void deleteSandBoxTable_B4insertion() {

        SQLiteDatabase db_sandboxtable_delete = openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_sandboxtable_delete.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandID VARCHAR,SandName VARCHAR);");
        Cursor cursor = db_sandboxtable_delete.rawQuery("SELECT * FROM SandBoxList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_sandboxtable_delete.delete("SandBoxList", null, null);
            // Toast.makeText(getApplicationContext(),"All records deleted",Toast.LENGTH_LONG).show();
        }
        db_sandboxtable_delete.close();
    }

    public void deleteAcademicTable_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS AcademicList(AcaID VARCHAR,AcaName VARCHAR,ASandID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM AcademicList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db1.delete("AcademicList", null, null);

        }
        db1.close();
    }

    public void deleteClusterTable_B4insertion() {

        SQLiteDatabase db_clustertable_delete = openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_clustertable_delete.execSQL("CREATE TABLE IF NOT EXISTS ClusterList(clustID VARCHAR,clustName VARCHAR,clust_sand_ID VARCHAR,clust_aca_ID VARCHAR);");
        Cursor cursor = db_clustertable_delete.rawQuery("SELECT * FROM ClusterList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_clustertable_delete.delete("ClusterList", null, null);

        }
        db_clustertable_delete.close();
    }

    public void deleteInstituteTable_B4insertion() {

        SQLiteDatabase db_inst_delete = openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_inst_delete.execSQL("CREATE TABLE IF NOT EXISTS InstituteList(inst_ID VARCHAR,inst_Name VARCHAR,inst_sand_ID VARCHAR,inst_aca_ID VARCHAR,inst_clust_ID VARCHAR);");
        Cursor cursor = db_inst_delete.rawQuery("SELECT * FROM InstituteList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_inst_delete.delete("InstituteList", null, null);
            // Toast.makeText(getApplicationContext(),"All records deleted",Toast.LENGTH_LONG).show();
        }
        db_inst_delete.close();
    }

    public void deleteSchoolTable_B4insertion() {

        SQLiteDatabase db_school_delete = openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_school_delete.execSQL("CREATE TABLE IF NOT EXISTS SchoolList(school_ID VARCHAR,school_Name VARCHAR,school_sand_ID VARCHAR,school_aca_ID VARCHAR,school_clust_ID VARCHAR);");
        Cursor cursor = db_school_delete.rawQuery("SELECT * FROM SchoolList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_school_delete.delete("SchoolList", null, null);
        }
        db_school_delete.close();
    }

    public void deleteLevelTable_B4insertion() {

        SQLiteDatabase db_leveldelete = openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_leveldelete.execSQL("CREATE TABLE IF NOT EXISTS LevelList(LevelID VARCHAR,LevelName VARCHAR,Lev_SandID VARCHAR,Lev_AcaID VARCHAR,Lev_ClustID VARCHAR,Lev_InstID VARCHAR,Lev_AdmissionFee VARCHAR);");
        Cursor cursor = db_leveldelete.rawQuery("SELECT * FROM LevelList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_leveldelete.delete("LevelList", null, null);

        }
        db_leveldelete.close();
    }
    public void deleteLearningOptionTable_B4insertion() {

        SQLiteDatabase db_LearningOption_delete = openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_LearningOption_delete.execSQL("CREATE TABLE IF NOT EXISTS LearningOptionList(Option_ID VARCHAR,Group_Name VARCHAR,Option_Name VARCHAR,Option_Status VARCHAR);");
        Cursor cursor = db_LearningOption_delete.rawQuery("SELECT * FROM LearningOptionList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_LearningOption_delete.delete("LearningOptionList", null, null);
            // Toast.makeText(getApplicationContext(),"All records deleted",Toast.LENGTH_LONG).show();
        }
        db_LearningOption_delete.close();
    }







}// end of class
