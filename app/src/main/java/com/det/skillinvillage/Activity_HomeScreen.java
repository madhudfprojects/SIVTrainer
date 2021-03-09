package com.det.skillinvillage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.det.skillinvillage.adapter.CalendarAdapter;
import com.det.skillinvillage.model.AutoSyncVersion;
import com.det.skillinvillage.model.AutoSyncVersionList;
import com.det.skillinvillage.model.Class_dashboardList;
import com.det.skillinvillage.model.Class_devicedetails;
import com.det.skillinvillage.model.Class_getUserDashboardResponse;
import com.det.skillinvillage.model.Class_getdemo_Response;
import com.det.skillinvillage.model.Class_getdemo_resplist;
import com.det.skillinvillage.model.Class_gethelp_Response;
import com.det.skillinvillage.model.Class_gethelp_resplist;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.model.GetAppVersion;
import com.det.skillinvillage.model.GetAppVersionList;
import com.det.skillinvillage.model.GetMobileMenuResponse;
import com.det.skillinvillage.model.GetMobileMenuResponseList;
import com.det.skillinvillage.model.GetMobileSubMenuResponseList;
import com.det.skillinvillage.model.Location_Data;
import com.det.skillinvillage.model.Location_DataList;
import com.det.skillinvillage.model.StudCountList;
import com.det.skillinvillage.model.Student;
import com.det.skillinvillage.model.StudentList;
import com.det.skillinvillage.model.UserList;
import com.det.skillinvillage.model.ValidateSyncRequest;
import com.det.skillinvillage.model.ValidateSyncResponse;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;
import com.det.skillinvillage.util.UserInfoListRest;
import com.det.skillinvillage.util.UserInfoRest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Vector;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.KeyValue_employeeRoleName;
import static com.det.skillinvillage.MainActivity.Key_username;
import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.key_userimage;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_userimage;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_username;
import static com.det.skillinvillage.NormalLogin.key_flag;
import static com.det.skillinvillage.NormalLogin.sharedpreferenc_flag;

public class Activity_HomeScreen extends AppCompatActivity {

    Interface_userservice userService1;
    Class_InternetDectector internetDectector, internetDectector2, internetDectector3;
    Boolean isInternetPresent = false;
    //  String str_employee_id;
    private String versioncode;

    TelephonyManager tm1 = null;
    String myVersion, deviceBRAND, deviceHARDWARE, devicePRODUCT, deviceUSER, deviceModelName, deviceId, tmDevice, tmSerial, androidId, simOperatorName, sdkver, mobileNumber;
    int sdkVersion, Measuredwidth = 0, Measuredheight = 0, update_flage = 0;
    String regId = "dfagriXZ", str_userid;


    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String Key_syncId = "Sync_ID";
    SharedPreferences sharedpreferencebook_usercredential_Obj;

    public static final String MyPREFERENCE_SyncId = "MyPref_SyncId";
    public static final String SyncId = "SyncId";
    SharedPreferences shared_syncId;

    int sel_yearsp = 0, sel_statesp = 0, sel_districtsp = 0, sel_taluksp = 0, sel_villagesp = 0, sel_grampanchayatsp = 0;

    public static final String sharedpreferenc_selectedspinner = "sharedpreferenc_selectedspinner";
    //    public static final String Key_sel_yearsp = "sel_yearsp";
//    public static final String Key_sel_statesp = "sel_statesp";
//    public static final String Key_sel_districtsp = "sel_districtsp";
//    public static final String Key_sel_taluksp = "sel_taluksp";
//    public static final String Key_sel_villagesp = "sel_villagesp";
//    public static final String Key_sel_grampanchayatsp = "sel_grampanchayatsp";
    SharedPreferences sharedpref_spinner_Obj;

    Location_DataList[] location_dataLists;
    Location_DataList class_location_dataList = new Location_DataList();

    UserList[] userLists;
    UserList class_userDatalist = new UserList();

    StudentList[] StudentLists;
    StudentList class_StudentList = new StudentList();

    StudCountList[] studCountLists;
    StudCountList class_studCountLists = new StudCountList();
    String StateCount = "0", DistrictCount = "0", TalukaCount = "0", VillageCount = "0", YearCount = "0", Sync_ID = "", ClusterCount = "0", LevelCount = "0", SchoolCount = "0", InstituteCount = "0", SandboxCount = "0", Student_Count = "0";
    String VersionStatus = "false";

    LinearLayout help_ll;
    String str_VersionStatus, str_loginuserID = "", str_flag = "", str_Googlelogin_Username = "", str_Googlelogin_UserImg = "";


    SharedPreferences sharedpref_loginuserid_Obj;

    LinearLayout dashboard_iv, stu_reg_iv, scheduler_iv, usermanual_iv, docview_iv, mark_attendance_iv, assessment_iv, onlineview_iv;
    public CalendarAdapter cal_adapter1;
    public GregorianCalendar cal_month, cal_month_copy;

    TextView dislay_UserName_tv,school_countTV,applicant_countTV,admissioncount_TV;
    ImageView displ_Userimg_iv;


    SharedPreferences sharedpref_username_Obj;
    SharedPreferences sharedpref_userimage_Obj;
    String str_feedback = "";

    SharedPreferences sharedpref_feedback;
    public static final String MyPREFERENCES_Feedback = "MyPrefsFeedback";
    public static final String FeedBack = "feedBack";
    SharedPreferences sharedpref_flag_Obj;


    String Schedule_Status, Schedule_ID, Lavel_ID, Schedule_Date, End_Time, Start_Time, Schedule_Session,Schedule_Status_old,Subject_Name, Leason_Name = "", Lavel_Name, Cluster_Name, Institute_Name;
    ArrayList<UserInfoListRest> arrayList = new ArrayList<UserInfoListRest>();
    UserInfoListRest[] userInfosarr;
    GetMobileMenuResponseList[] arrayObj_class_getpaymentpendingresp;
    GetMobileSubMenuResponseList[] arrayObj_class_getMobilesubmenuresp;
    Class_SubMenu[] objclassarr_Class_SubMenu;
    ArrayList<ExpandListGroup> mainmenulist;
    ArrayList<Class_SubMenu> submenuList;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    ExpandListGroup[] objclassarr_expandedlistgroup;
    String Employee_Role="";
    ImageView seeAll_iv,googlemaps_iv;
    TextView designation_TV;
    String str_dashboard_status = "", str_no_of_villages, str_no_of_students, str_no_of_schools, str_no_of_male, str_no_of_female, str_no_of_dropouts, str_dashboard_status_barchart = "";
    Class_Scorecards_CenterSelection[] arrayObjclass_Scorecards_CenterSelection,arrayObjclass_Scorecards_CenterSelection2;
    Class_Dashboard_SandBox[] arrayObjclass_dashboard_sandBoxes,arrayObjclass_dashboard_sandBoxes2;

    String applicant_Count="",admission_Count="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fstpage);
        setContentView(R.layout.activity__home_screen_early);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Skill In Village");

        userService1 = Class_ApiUtils.getUserService();

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

//        help_ll = (LinearLayout) findViewById(R.id.help_ll);

        try {
            versioncode = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //   sharedpref_loginuserid_Obj = getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        sharedpreferencebook_usercredential_Obj = getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);

        str_loginuserID = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();
        Log.e("homepage", "str_loginuserID=" + str_loginuserID);

        //  sharedpreferencebook_usercredential_Obj = getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        //  str_employee_id = sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeid, "").trim();
        // Log.e("tag", "str_employee_id=" + str_employee_id);


        sharedpref_spinner_Obj = getSharedPreferences(sharedpreferenc_selectedspinner, Context.MODE_PRIVATE);
        shared_syncId = getSharedPreferences(MyPREFERENCE_SyncId, Context.MODE_PRIVATE);


        sharedpref_username_Obj = getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
        str_Googlelogin_Username = sharedpref_username_Obj.getString(Key_username, "").trim();

        sharedpref_userimage_Obj = getSharedPreferences(sharedpreferenc_userimage, Context.MODE_PRIVATE);
        str_Googlelogin_UserImg = sharedpref_userimage_Obj.getString(key_userimage, "").trim();

        sharedpref_flag_Obj = getSharedPreferences(sharedpreferenc_flag, Context.MODE_PRIVATE);
        str_flag = sharedpref_flag_Obj.getString(key_flag, "").trim();

        sharedpref_feedback = getSharedPreferences(MyPREFERENCES_Feedback, Context.MODE_PRIVATE);
        str_feedback = sharedpref_feedback.getString(FeedBack, "").trim();

        Log.e("tag", "sel_districtsp=" + sel_districtsp + "sel_statesp=" + sel_statesp);

       // Employee_Role=sharedpref_loginuserid_Obj.getString(KeyValue_employeeRoleName, "").trim();
        Employee_Role=Class_SaveSharedPreference.getPREF_RoleName(Activity_HomeScreen.this);
        Log.e("tag","home Employee_Role="+Employee_Role);

        LinearLayout homepagelayout_LL = findViewById(R.id.homepagelayout_ll);
        homepagelayout_LL.setVisibility(LinearLayout.VISIBLE);
        @SuppressLint("ResourceType") Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animation3.setDuration(100);
        homepagelayout_LL.setAnimation(animation3);
        homepagelayout_LL.animate();
        animation3.start();
        dislay_UserName_tv = findViewById(R.id.dislay_UserName_TV);
        displ_Userimg_iv = findViewById(R.id.displ_Userimg_IV);
        // dislay_UserName_tv.setText(str_Googlelogin_Username);
        dashboard_iv = findViewById(R.id.dashboard_IV);
        stu_reg_iv = findViewById(R.id.stu_reg_IV);
        scheduler_iv = findViewById(R.id.scheduler_iv);
        usermanual_iv = findViewById(R.id.usermanual_iv);
        docview_iv = findViewById(R.id.docview_iv);
        //mark_attendance_iv=(ImageView)findViewById(R.id.mark_attendance_iv);
        assessment_iv = findViewById(R.id.assessment_iv);
        onlineview_iv = findViewById(R.id.onlineview_iv);
        seeAll_iv=findViewById(R.id.seeall_iv);
        designation_TV=(TextView)findViewById(R.id.designation_TV);
        school_countTV=(TextView)findViewById(R.id.school_countTV);
        applicant_countTV=(TextView)findViewById(R.id.applicant_countTV);
        admissioncount_TV=(TextView)findViewById(R.id.admissioncount_TV);
        googlemaps_iv=(ImageView)findViewById(R.id.googlemaps_iv);
        //   onlineview_iv.setVisibility(View.GONE);
        if (str_flag.equals("1")) {
            dislay_UserName_tv.setText("");
        } else {
//            dislay_UserName_tv.setText(str_Googlelogin_Username);
//            try {
//                Glide.with(this).load(str_Googlelogin_UserImg).into(displ_Userimg_iv);
//            } catch (NullPointerException e) {
//                // Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
//            }


            dislay_UserName_tv.setText(Class_SaveSharedPreference.getUserName(Activity_HomeScreen.this).toString());
            designation_TV.setText(Employee_Role);

            try {
                Log.e("tag","str_Googlelogin_UserImg="+str_Googlelogin_UserImg);
                if(str_Googlelogin_UserImg.equalsIgnoreCase("")||str_Googlelogin_UserImg==null){
                    displ_Userimg_iv.setImageResource(R.drawable.profileimg);
                }else {
                    Glide.with(this).load(str_Googlelogin_UserImg).into(displ_Userimg_iv);
                }

            } catch (NullPointerException e) {
// Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
            }
        }


//        if(Employee_Role.equalsIgnoreCase("Cluster Head")) {
//            onlineview_iv.setVisibility(View.VISIBLE);
//        }else{
//            onlineview_iv.setVisibility(View.GONE);
//        }

        if (isInternetPresent) {
            AsyncCallWS2_Login task = new AsyncCallWS2_Login(Activity_HomeScreen.this);
            task.execute();

            GetMobileMenu();

//            OnlineView_Feedback task1 = new OnlineView_Feedback(Activity_HomeScreen.this);
//            task1.execute();
            //   Add_setGCM1();
            ///   GetAppVersionCheck();
//            delete_CountDetailsRestTable_B4insertion();
//            deleteStateRestTable_B4insertion();
//            deleteDistrictRestTable_B4insertion();
//            deleteTalukRestTable_B4insertion();
//            deleteVillageRestTable_B4insertion();
//            deleteYearRestTable_B4insertion();
//
//            //deleteStudentdetailsRestTable_B4insertion();
//            GetDropdownValuesRestData();
//            GetStudentValuesRestData();
            //GetStudentValuesResyncRestData();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }
        if (str_feedback != null) {
            if (str_feedback.equalsIgnoreCase("Gone") || str_feedback.equalsIgnoreCase("")) {
                onlineview_iv.setVisibility(View.GONE);
                Log.e("tag", "Oncreate-Gone");
            } else {
                onlineview_iv.setVisibility(View.VISIBLE);
                Log.e("tag", "Oncreate-Visible");
            }
        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                RelativeLayout stuRegistration_relativeLayout = findViewById(R.id.student_registration_RL);
//                stuRegistration_relativeLayout.setVisibility(LinearLayout.VISIBLE);
//                @SuppressLint("ResourceType") Animation stuRegistration_animation = AnimationUtils.loadAnimation(Activity_HomeScreen.this, R.anim.right_slide);
//                stuRegistration_animation.setDuration(1500);
//                stuRegistration_relativeLayout.setAnimation(stuRegistration_animation);
//                stuRegistration_relativeLayout.setAnimation(stuRegistration_animation);
//                stuRegistration_relativeLayout.animate();
//                stuRegistration_animation.start();

                stu_reg_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(Activity_HomeScreen.this, Activity_MarketingHomeScreen.class);
                        startActivity(i);
                        overridePendingTransition(R.animator.slide_right, R.animator.slide_right);

                    }
                });

                dashboard_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        internetDectector2 = new Class_InternetDectector(getApplicationContext());
                        isInternetPresent = internetDectector2.isConnectingToInternet();
                        if (isInternetPresent) {

                            Intent i = new Intent(Activity_HomeScreen.this, Activity_Dashboard.class);
                            startActivity(i);
                            overridePendingTransition(R.animator.slide_right, R.animator.slide_right);
                        }else{
                            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                final Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                      /*  RelativeLayout documentUpload_relativelayout = (RelativeLayout) findViewById(R.id.documentUpload_RL);
                        documentUpload_relativelayout.setVisibility(LinearLayout.VISIBLE);
                        @SuppressLint("ResourceType")
                        Animation animation_docupload = AnimationUtils.loadAnimation(Activity_HomeScreen.this, R.anim.anim);
                        animation_docupload.setDuration(500);
                        documentUpload_relativelayout.setAnimation(animation_docupload);
                        documentUpload_relativelayout.animate();
                        animation_docupload.start();

                        documentUpload_relativelayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(), "Currently document upload is disabled", Toast.LENGTH_LONG).show();

                               *//* Intent i = new Intent(Activity_HomeScreen.this, Activity_SO_DocumentUpload.class);
                                startActivity(i);
                                overridePendingTransition(R.animator.slide_right, R.animator.slide_right);*//*

                            }
                        });*/

//                        RelativeLayout scheduler_relativelayout = findViewById(R.id.scheduler_RL);
//                        scheduler_relativelayout.setVisibility(LinearLayout.VISIBLE);
//                        @SuppressLint("ResourceType")
//                        Animation animation_scheduler = AnimationUtils.loadAnimation(Activity_HomeScreen.this, R.anim.right_slide);
//                        animation_scheduler.setDuration(1500);
//                        scheduler_relativelayout.setAnimation(animation_scheduler);
//                        scheduler_relativelayout.animate();
//                        animation_scheduler.start();

                        scheduler_iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                        /*Intent i = new Intent(Activity_HomeScreen.this, Activity_SO_DocumentUpload.class);
                                        startActivity(i);*/

                                //Added isInternetPresent by shivaleela on Aug 21st 2019

                                if (isInternetPresent) {
                                    Date date = new Date();
                                    Log.i("Tag_time", "date1=" + date);
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    String PresentDayStr = sdf.format(date);
                                    Log.i("Tag_time", "PresentDayStr=" + PresentDayStr);//2019-12-12

                                    cal_adapter1.getPositionList(PresentDayStr, Activity_HomeScreen.this);
                                    overridePendingTransition(R.animator.slide_right, R.animator.slide_right);
                                } else {
                                    Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                        usermanual_iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent i = new Intent(Activity_HomeScreen.this, ContactUs_Activity.class);
                                startActivity(i);
                                overridePendingTransition(R.animator.slide_right, R.animator.slide_right);

                            }
                        });
                        googlemaps_iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                internetDectector2 = new Class_InternetDectector(getApplicationContext());
                                isInternetPresent = internetDectector2.isConnectingToInternet();
                                if (isInternetPresent) {

                                    Intent i = new Intent(Activity_HomeScreen.this, Activity_MarkerGoogleMaps.class);
                                    startActivity(i);
                                }else{
                                    Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

                        final Handler handler2 = new Handler();
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {

//                                RelativeLayout documentUpload_relativelayout = findViewById(R.id.docView_RL);
//                                documentUpload_relativelayout.setVisibility(LinearLayout.VISIBLE);
//                                @SuppressLint("ResourceType")
//                                Animation animation_docupload = AnimationUtils.loadAnimation(Activity_HomeScreen.this, R.anim.right_slide);
//                                animation_docupload.setDuration(1500);
//                                documentUpload_relativelayout.setAnimation(animation_docupload);
//                                documentUpload_relativelayout.animate();
//                                animation_docupload.start();

                                docview_iv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //     Toast.makeText(getApplicationContext(), "Currently document upload is disabled", Toast.LENGTH_LONG).show();

                                        Intent i = new Intent(Activity_HomeScreen.this, DocView_MainActivity.class);
                                        startActivity(i);
                                        overridePendingTransition(R.animator.slide_right, R.animator.slide_right);

                                    }
                                });
                                final Handler handler3 = new Handler();
                                handler3.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

//                                        RelativeLayout documentUpload_relativelayout = findViewById(R.id.feedback_RL);
//                                        documentUpload_relativelayout.setVisibility(LinearLayout.VISIBLE);
//                                        @SuppressLint("ResourceType")
//                                        Animation animation_docupload = AnimationUtils.loadAnimation(Activity_HomeScreen.this, R.anim.right_slide);
//                                        animation_docupload.setDuration(1500);
//                                        documentUpload_relativelayout.setAnimation(animation_docupload);
//                                        documentUpload_relativelayout.animate();
//                                        animation_docupload.start();

                                        onlineview_iv.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                internetDectector2 = new Class_InternetDectector(getApplicationContext());
                                                isInternetPresent = internetDectector2.isConnectingToInternet();
                                                if (isInternetPresent) {

                                                    Intent i = new Intent(Activity_HomeScreen.this, Onlineview_Navigation.class);
                                                    startActivity(i);
                                                    overridePendingTransition(R.animator.slide_right, R.animator.slide_right);
                                                }else{
                                                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

                                                }
//                                                if (str_feedback.equalsIgnoreCase("Visible")) {
//                                                    Intent i = new Intent(Activity_HomeScreen.this, Onlineview_Navigation.class);
//                                                    startActivity(i);
//                                                    overridePendingTransition(R.animator.slide_right, R.animator.slide_right);
//                                                } else {
//                                                    Toast.makeText(getApplicationContext(), "You Dont Have Access", Toast.LENGTH_SHORT).show();
//                                                    Intent i = new Intent(Activity_HomeScreen.this, Activity_HomeScreen.class);
//                                                    startActivity(i);
//                                                    finish();
//                                                }

                                            }
                                        });

                                    }
                                }, 250);
                            }
                        }, 200);
                    }
                }, 150);
            }
        }, 100);

//        mark_attendance_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(Activity_HomeScreen.this, Activity_markattendance.class);
//                startActivity(i);
//                overridePendingTransition(R.animator.slide_right, R.animator.slide_right);
//
//            }
//        });

        seeAll_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internetDectector2 = new Class_InternetDectector(getApplicationContext());
                isInternetPresent = internetDectector2.isConnectingToInternet();
                if (isInternetPresent) {


                    Intent i = new Intent(Activity_HomeScreen.this, Activity_Dashboard.class);
                    startActivity(i);
                    overridePendingTransition(R.animator.slide_right, R.animator.slide_right);
                }else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        assessment_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internetDectector2 = new Class_InternetDectector(getApplicationContext());
                isInternetPresent = internetDectector2.isConnectingToInternet();
                if (isInternetPresent) {

                    Intent i = new Intent(Activity_HomeScreen.this, Activity_AssessmentList.class);
                    startActivity(i);
                    overridePendingTransition(R.animator.slide_right, R.animator.slide_right);
                }else{
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

                }
            }
        });


        String MsgNotfication = "";

        Bundle extras = getIntent().getExtras();

// if (extras != null){}
        if (extras != null) {
            MsgNotfication = extras.getString("2");
            if (MsgNotfication != null || MsgNotfication != "") {
                Toast.makeText(getApplicationContext(), " " + MsgNotfication, Toast.LENGTH_LONG).show();
                Log.e("notifMsg", "notifMsg: " + MsgNotfication);
            }
        } else {
            Log.e("extranull", String.valueOf(extras));
        }

        if (isInternetPresent) {
            Add_setGCM1();
            GetAutoSyncVersion();
            GetAppVersionCheck();
            getdashboarddata();

            // GetDropdownValuesRestData();
            //GetStudentValuesRestData();
        }else{
//            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

            school_countTV.setText("");
        }


//        help_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(Activity_HomeScreen.this, ContactUs_Activity.class);
//                startActivity(i);
//
//            }
//        });


        if (VersionStatus.equalsIgnoreCase("true")) {
            internetDectector2 = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector2.isConnectingToInternet();
            if (isInternetPresent) {

                //GetDropdownValues();
                deleteStateRestTable_B4insertion();
                deleteDistrictRestTable_B4insertion();
                deleteTalukRestTable_B4insertion();
                deleteVillageRestTable_B4insertion();
                deleteYearRestTable_B4insertion();

                //   deleteStudentdetailsRestTable_B4insertion();
                deleteSchoolRestTable_B4insertion();
                deleteSandboxRestTable_B4insertion();
                deleteInstituteRestTable_B4insertion();
                deleteLevelRestTable_B4insertion();
                deleteClusterRestTable_B4insertion();


                GetDropdownValuesRestData();
                GetStudentValuesResyncRestData();


                String Sync_IDNew = shared_syncId.getString(SyncId, "");
                Log.e("tag", "Sync_IDNew=" + Sync_IDNew);
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        } else {

            internetDectector2 = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector2.isConnectingToInternet();
            if (isInternetPresent) {
                CountCheckList();
                UserStudentDataCountCheckList();
                stateListRest_dbCount();

            }
        }

        UserStudentDataCountCheckList();
    }//end of oncreate

    public void getdashboarddata() {
        deleteSandBoxDrodownTable_B4insertion();
        deleteSandBoxTable_B4insertion();
        if (isInternetPresent) {
            getdashboardinfo_new();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }


    }
    public void getdashboardinfo_new() {

        Call<Class_getUserDashboardResponse> call = userService1.getdashboardDetails(str_loginuserID);
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
        //  progressDoalog.setMax(100);
        //  progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<Class_getUserDashboardResponse>() {
            @Override
            public void onResponse(Call<Class_getUserDashboardResponse> call, Response<Class_getUserDashboardResponse> response) {
                Log.e("Entered resp", "getdashboardDetails");

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    Class_getUserDashboardResponse class_loginresponse = response.body();
                    if (class_loginresponse.getStatus()) {
                        List<Class_dashboardList> monthContents_list = response.body().getListVersion();

                        Class_dashboardList []  arrayObj_Class_monthcontents = new Class_dashboardList[monthContents_list.size()];
                        arrayObjclass_Scorecards_CenterSelection=new Class_Scorecards_CenterSelection[monthContents_list.size()];
                        arrayObjclass_dashboard_sandBoxes=new Class_Dashboard_SandBox[monthContents_list.size()];

                        for (int i = 0; i < arrayObj_Class_monthcontents.length; i++) {


                            Log.e("getdashboard", String.valueOf(class_loginresponse.getStatus()));
                            Log.e("getdashboard", class_loginresponse.getMessage());
//                            {
//                                "Dashboard_ID": 0,
//                                    "Sandbox_ID": "0",
//                                    "Sandbox_Name": "ALL",
//                                    "Count_Institute": "59",
//                                    "Count_Village": "53",
//                                    "Count_Applicant": "3822",
//                                    "Count_Admission": "3822",
//                                    "Count_Male": "0",
//                                    "Count_Female": "0",
//                                    "Count_Dropout": "525",
//                                    "Count_Student": "3804",
//                                    "Dashboard_Status": "Active"
//                            }
                            Class_dashboardList innerObj_Class_SandboxList = new Class_dashboardList();
                            innerObj_Class_SandboxList.setDashboardID(class_loginresponse.getListVersion().get(i).getDashboardID());
                            innerObj_Class_SandboxList.setSandboxID(class_loginresponse.getListVersion().get(i).getSandboxID());
                            innerObj_Class_SandboxList.setSandboxName(class_loginresponse.getListVersion().get(i).getSandboxName());
                            innerObj_Class_SandboxList.setCountInstitute(class_loginresponse.getListVersion().get(i).getCountInstitute());
                            innerObj_Class_SandboxList.setCountVillage(class_loginresponse.getListVersion().get(i).getCountVillage());
                            innerObj_Class_SandboxList.setCountApplicant(class_loginresponse.getListVersion().get(i).getCountApplicant());
                            innerObj_Class_SandboxList.setCountAdmission(class_loginresponse.getListVersion().get(i).getCountAdmission());
                            innerObj_Class_SandboxList.setCountMale(class_loginresponse.getListVersion().get(i).getCountMale());
                            innerObj_Class_SandboxList.setCountFemale(class_loginresponse.getListVersion().get(i).getCountFemale());
                            innerObj_Class_SandboxList.setCountDropout(class_loginresponse.getListVersion().get(i).getCountDropout());
                            innerObj_Class_SandboxList.setCountStudent(class_loginresponse.getListVersion().get(i).getCountStudent());
                            innerObj_Class_SandboxList.setDashboardStatus(class_loginresponse.getListVersion().get(i).getDashboardStatus());
                            arrayObj_Class_monthcontents[i]=innerObj_Class_SandboxList;



                            //////////////////////////////////////////////////////
                            Class_Scorecards_CenterSelection innerObj_class_Scorecards_CenterSelection = new Class_Scorecards_CenterSelection();
                            innerObj_class_Scorecards_CenterSelection.setScorecards_SandboxID(class_loginresponse.getListVersion().get(i).getSandboxID());
                            innerObj_class_Scorecards_CenterSelection.setScorecards_SandboxName(class_loginresponse.getListVersion().get(i).getSandboxName());
                            innerObj_class_Scorecards_CenterSelection.setScorecards_villages(class_loginresponse.getListVersion().get(i).getCountVillage());
                            innerObj_class_Scorecards_CenterSelection.setScorecards_schools(class_loginresponse.getListVersion().get(i).getCountInstitute());
                            innerObj_class_Scorecards_CenterSelection.setScorecards_students(class_loginresponse.getListVersion().get(i).getCountStudent());
                            innerObj_class_Scorecards_CenterSelection.setScorecards_male(class_loginresponse.getListVersion().get(i).getCountMale());
                            innerObj_class_Scorecards_CenterSelection.setScorecards_female(class_loginresponse.getListVersion().get(i).getCountFemale());
                            innerObj_class_Scorecards_CenterSelection.setScorecards_dropouts(class_loginresponse.getListVersion().get(i).getCountDropout());

                            Class_Dashboard_SandBox class_dashboard_sandBox = new Class_Dashboard_SandBox();
                            class_dashboard_sandBox.setDashboard_sand_id(class_loginresponse.getListVersion().get(i).getSandboxID());
                            class_dashboard_sandBox.setDashboard_sand_name(class_loginresponse.getListVersion().get(i).getSandboxName());

                            str_no_of_villages = class_loginresponse.getListVersion().get(i).getCountVillage();
                            str_no_of_students = class_loginresponse.getListVersion().get(i).getCountStudent();
                            str_no_of_schools = class_loginresponse.getListVersion().get(i).getCountInstitute();
                            str_no_of_male = class_loginresponse.getListVersion().get(i).getCountMale();
                            str_no_of_female = class_loginresponse.getListVersion().get(i).getCountFemale();
                            str_no_of_dropouts = class_loginresponse.getListVersion().get(i).getCountDropout();
                            Log.e("str_no_of_villages",str_no_of_villages);

                         String  str_dashboard_sandid = class_loginresponse.getListVersion().get(i).getSandboxID();
                            String str_dashboard_sandname = class_loginresponse.getListVersion().get(i).getSandboxName();
                            arrayObjclass_Scorecards_CenterSelection[i]=innerObj_class_Scorecards_CenterSelection;
                            arrayObjclass_dashboard_sandBoxes[i]=class_dashboard_sandBox;

                            ///////////////////////////////////////////////////////
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    // Stuff that updates the UI

                                   // setvalues();
                                }
                            });

//                            Log.e("str_dashboard_sandid",str_dashboard_sandid);
//                            Log.e("str_dashboard_sandname",str_dashboard_sandname);

                            DBCreate_dash_SandBoxdetails_insert_2SQLiteDB(str_dashboard_sandid, str_dashboard_sandname);
                            DBCreate_SandBoxdetails_insert_2SQLiteDB(str_dashboard_sandid, str_dashboard_sandname,str_no_of_villages,str_no_of_schools,str_no_of_students,str_no_of_male,str_no_of_female,str_no_of_dropouts);
                        }//for loop end
                        uploadSandboxDropdownfromDB_list();
                        uploadSandboxfromDB_list();
                        Update_id_sandbox("0");
//                        RecyclerView.Adapter adapter1 = new Emp_monthcontents_RecyclerViewAdapter(Array_ClassListVersion);
//
//                        recyclerview.setAdapter(adapter1);


                    } else {
                        progressDoalog.dismiss();
//                        str_getmonthsummary_errormsg = class_loginresponse.getMessage();
//                        alerts_dialog_getmonthsummaryError();

                        // Toast.makeText(getContext(), class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDoalog.dismiss();
                    Log.e("Entered resp else", "");
                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
//                    Log.e("error message", error.getMsg());
//                    str_getmonthsummary_errormsg = error.getMsg();
//                    alerts_dialog_getexlistviewError();

                    //  Toast.makeText(getContext(), error.getMsg(), Toast.LENGTH_SHORT).show();

                    if (error.getMsg() != null) {

                        Log.e("error message", error.getMsg());
//                        str_getmonthsummary_errormsg = error.getMsg();
//                        alerts_dialog_getexlistviewError();

                        //Toast.makeText(getActivity(), error.getMsg(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Activity_HomeScreen.this,"Kindly restart your application", Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDoalog.dismiss();
//                str_getmonthsummary_errormsg = t.getMessage();
//                alerts_dialog_getexlistviewError();

                // Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }


    public void Update_id_sandbox(String str_sandid) {

        SQLiteDatabase db_feessummary_idupdate = this.openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db_feessummary_idupdate.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandBox_DashboardID VARCHAR,SandBox_DashboardName VARCHAR,SandBox_DashboardVillages VARCHAR,SandBox_DashboardSchools VARCHAR,SandBox_DashboardStudents VARCHAR,SandBox_DashboardMale VARCHAR,SandBox_DashboardFemale VARCHAR,SandBox_DashboardDropouts VARCHAR);");
        Cursor cursor1 = db_feessummary_idupdate.rawQuery("SELECT DISTINCT * FROM SandBoxList WHERE SandBox_DashboardID='" + str_sandid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObjclass_Scorecards_CenterSelection2 = new Class_Scorecards_CenterSelection[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_Scorecards_CenterSelection innerObj_Class_SandboxList = new Class_Scorecards_CenterSelection();
                innerObj_Class_SandboxList.setScorecards_SandboxID(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardID")));
                innerObj_Class_SandboxList.setScorecards_SandboxName(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardName")));
                innerObj_Class_SandboxList.setScorecards_villages(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardVillages")));
                innerObj_Class_SandboxList.setScorecards_schools(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardSchools")));
                innerObj_Class_SandboxList.setScorecards_students(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardStudents")));
                innerObj_Class_SandboxList.setScorecards_male(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardMale")));
                innerObj_Class_SandboxList.setScorecards_female(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardFemale")));
                innerObj_Class_SandboxList.setScorecards_dropouts(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardDropouts")));

                arrayObjclass_Scorecards_CenterSelection2[i] = innerObj_Class_SandboxList;
                str_no_of_villages=arrayObjclass_Scorecards_CenterSelection2[i].getScorecards_villages();
                str_no_of_schools=arrayObjclass_Scorecards_CenterSelection2[i].getScorecards_schools();
                str_no_of_students=arrayObjclass_Scorecards_CenterSelection2[i].getScorecards_students();
                str_no_of_male=arrayObjclass_Scorecards_CenterSelection2[i].getScorecards_male();
                str_no_of_female=arrayObjclass_Scorecards_CenterSelection2[i].getScorecards_female();
                str_no_of_dropouts=arrayObjclass_Scorecards_CenterSelection2[i].getScorecards_dropouts();
                school_countTV.setText(str_no_of_schools);

               // setvalues();
                i++;

            } while (cursor1.moveToNext());
        }//if ends


        db_feessummary_idupdate.close();
        if (x > 0) {
            school_countTV.setText(str_no_of_schools);

        }

    }

    public void DBCreate_dash_SandBoxdetails_insert_2SQLiteDB(String str_sandboxID, String str_sandboxname) {
        SQLiteDatabase db_sandbox = this.openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db_sandbox.execSQL("CREATE TABLE IF NOT EXISTS DropSandBoxList(SandBox_ID VARCHAR,SandBox_Name VARCHAR);");
        String SQLiteQuery = "INSERT INTO DropSandBoxList(SandBox_ID,SandBox_Name)" +
                " VALUES ('" + str_sandboxID + "','" + str_sandboxname +"');";
        db_sandbox.execSQL(SQLiteQuery);
        Log.e("inset DB", str_sandboxID);
        Log.e("insert DB", str_sandboxname);
        db_sandbox.close();
    }
    public void DBCreate_SandBoxdetails_insert_2SQLiteDB(String str_sandID, String str_sandname,String str_village,String str_school,String str_students,String str_male,String str_no_of_female,String str_no_of_dropouts) {
        SQLiteDatabase db_sandbox = this.openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db_sandbox.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandBox_DashboardID VARCHAR,SandBox_DashboardName VARCHAR,SandBox_DashboardVillages VARCHAR,SandBox_DashboardSchools VARCHAR,SandBox_DashboardStudents VARCHAR,SandBox_DashboardMale VARCHAR,SandBox_DashboardFemale VARCHAR,SandBox_DashboardDropouts VARCHAR);");


        String SQLiteQuery = "INSERT INTO SandBoxList(SandBox_DashboardID,SandBox_DashboardName,SandBox_DashboardVillages,SandBox_DashboardSchools,SandBox_DashboardStudents,SandBox_DashboardMale,SandBox_DashboardFemale,SandBox_DashboardDropouts)" +
                " VALUES ('" + str_sandID + "','" + str_sandname +  "','" + str_village + "','" + str_school + "','" + str_students + "','" + str_male + "','" + str_no_of_female + "','" + str_no_of_dropouts +"');";
        db_sandbox.execSQL(SQLiteQuery);
        Log.e("str_sandID DB", str_sandID);
        Log.e("str_sandname DB", str_sandname);
        db_sandbox.close();
    }
    public void uploadSandboxDropdownfromDB_list() {

        SQLiteDatabase db_centers = this.openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db_centers.execSQL("CREATE TABLE IF NOT EXISTS DropSandBoxList(SandBox_ID VARCHAR,SandBox_Name VARCHAR);");
        Cursor cursor1 = db_centers.rawQuery("SELECT DISTINCT * FROM DropSandBoxList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObjclass_dashboard_sandBoxes2 = new Class_Dashboard_SandBox[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_Dashboard_SandBox innerObj_Class_SandboxList = new Class_Dashboard_SandBox();
                innerObj_Class_SandboxList.setDashboard_sand_id(cursor1.getString(cursor1.getColumnIndex("SandBox_ID")));
                innerObj_Class_SandboxList.setDashboard_sand_name(cursor1.getString(cursor1.getColumnIndex("SandBox_Name")));

                arrayObjclass_dashboard_sandBoxes2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_centers.close();
        if (x > 0) {

//            ArrayAdapter<Class_Dashboard_SandBox> dataAdapter = new ArrayAdapter<Class_Dashboard_SandBox>(getApplicationContext(), R.layout.spinnercenterstyle, arrayObjclass_dashboard_sandBoxes2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            loadSandbox_SP.setAdapter(dataAdapter);
        }

    }
    public void uploadSandboxfromDB_list() {

        SQLiteDatabase db_centers = this.openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db_centers.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandBox_DashboardID VARCHAR,SandBox_DashboardName VARCHAR,SandBox_DashboardVillages VARCHAR,SandBox_DashboardSchools VARCHAR,SandBox_DashboardStudents VARCHAR,SandBox_DashboardMale VARCHAR,SandBox_DashboardFemale VARCHAR,SandBox_DashboardDropouts VARCHAR);");
        Cursor cursor1 = db_centers.rawQuery("SELECT DISTINCT * FROM SandBoxList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObjclass_Scorecards_CenterSelection2 = new Class_Scorecards_CenterSelection[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_Scorecards_CenterSelection innerObj_Class_SandboxList = new Class_Scorecards_CenterSelection();
                innerObj_Class_SandboxList.setScorecards_SandboxID(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardID")));
                innerObj_Class_SandboxList.setScorecards_SandboxName(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardName")));
                innerObj_Class_SandboxList.setScorecards_villages(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardVillages")));
                innerObj_Class_SandboxList.setScorecards_schools(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardSchools")));
                innerObj_Class_SandboxList.setScorecards_students(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardStudents")));
                innerObj_Class_SandboxList.setScorecards_male(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardMale")));
                innerObj_Class_SandboxList.setScorecards_female(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardFemale")));
                innerObj_Class_SandboxList.setScorecards_dropouts(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardDropouts")));

                arrayObjclass_Scorecards_CenterSelection2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_centers.close();
        if (x > 0) {

//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObjclass_Scorecards_CenterSelection2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            loadSandbox_SP.setAdapter(dataAdapter);
        }

    }

    public void deleteSandBoxTable_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandBox_DashboardID VARCHAR,SandBox_DashboardName VARCHAR,SandBox_DashboardVillages VARCHAR,SandBox_DashboardSchools VARCHAR,SandBox_DashboardStudents VARCHAR,SandBox_DashboardMale VARCHAR,SandBox_DashboardFemale VARCHAR,SandBox_DashboardDropouts VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM SandBoxList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db1.delete("SandBoxList", null, null);

        }
        db1.close();
    }


    public void deleteSandBoxDrodownTable_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS DropSandBoxList(SandBox_ID VARCHAR,SandBox_Name VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM DropSandBoxList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db1.delete("DropSandBoxList", null, null);

        }
        db1.close();
    }

    public class AsyncCallWS2_Login extends AsyncTask<String, Void, Void> {
        // ProgressDialog dialog;
        ProgressDialog progressDoalog;
        Context context;

        @Override
        protected void onPreExecute() {
            Log.i("tag", "onPreExecute---tab2");
            /*dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/

            progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
            progressDoalog.setMessage("Loading....");
            progressDoalog.setTitle("Please wait....");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i("tag", "onProgressUpdate---tab2");
        }

        public AsyncCallWS2_Login(Activity_HomeScreen activity) {
            context = activity;
            //  dialog = new ProgressDialog(activity);
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i("tag", "doInBackground");

            //  fetch_all_info("");

			/*  if(!login_result.equals("Fail"))
			  {
				  GetAllEvents(u1,p1);
			  }*/

            //GetAllEvents(u1,p1);

            get_User_Schedule();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //  dialog.dismiss();
            progressDoalog.dismiss();
            cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
            cal_month_copy = (GregorianCalendar) cal_month.clone();
            cal_adapter1 = new CalendarAdapter(Activity_HomeScreen.this, cal_month, UserInfoListRest.user_info_arr);

           /* Intent i = new Intent(Activity_HomeScreen.this, Activity_HomeScreen.class);
            SharedPreferences myprefs = Activity_HomeScreen.this.getSharedPreferences("user", MODE_PRIVATE);
            myprefs.edit().putString("login_userid", str_loginuserID).apply();
            SharedPreferences myprefs_scheduleId = Activity_HomeScreen.this.getSharedPreferences("scheduleId", MODE_PRIVATE);
            myprefs_scheduleId.edit().putString("scheduleId", Schedule_ID).apply();
            startActivity(i);
            finish();*/
        }
    }

    public void get_User_Schedule() {


//        Interface_userservice userService;
//        userService = Class_ApiUtils.getUserService();

        Log.i("User_ID=", str_loginuserID);
        Call<UserInfoRest> call = userService1.get_User_Schedule(str_loginuserID);

        call.enqueue(new Callback<UserInfoRest>() {
            @Override
            public void onResponse(Call<UserInfoRest> call, Response<UserInfoRest> response) {
                Log.e("response_userschd", "response_userschd: " + new Gson().toJson(response));

               /* Class_gethelp_Response gethelp_response_obj = new Class_gethelp_Response();
                gethelp_response_obj = (Class_gethelp_Response) response.body();*/


                if (response.isSuccessful()) {
                    UserInfoRest userInfoRest = response.body();
                    Log.e("response.user schedule", response.body().getListVersion().toString());


                    if (userInfoRest.getStatus().equals(true)) {

                        List<UserInfoListRest> usershedulelist = response.body().getListVersion();
                        Log.e("length", String.valueOf(usershedulelist.size()));
                        int int_usercount = usershedulelist.size();

                        for (int i = 0; i < int_usercount; i++) {
                            Log.e("prevstatus1", usershedulelist.get(i).getSchedule_Status_Old().toString());

                            Schedule_Status = usershedulelist.get(i).getScheduleStatus().toString();

                            Schedule_ID = usershedulelist.get(i).getScheduleID().toString();
                            Lavel_ID = usershedulelist.get(i).getLavelID().toString();
                            Schedule_Date = usershedulelist.get(i).getScheduleDate().toString();
                            End_Time = usershedulelist.get(i).getEndTime().toString();
                            Start_Time = usershedulelist.get(i).getStartTime().toString();
                            if (usershedulelist.get(i).getSubjectName() == null || usershedulelist.get(i).getSubjectName().equals("")) {
                                Subject_Name = "";
                            }else{
                                Subject_Name = usershedulelist.get(i).getSubjectName().toString();
                            }

                            Schedule_Session = usershedulelist.get(i).getScheduleSession().toString();
                            Schedule_Status_old = usershedulelist.get(i).getSchedule_Status_Old().toString();

                            if (usershedulelist.get(i).getLeasonName() == null || usershedulelist.get(i).getLeasonName().equals("")) {
                                Leason_Name = "";
                            } else {
                                Leason_Name = usershedulelist.get(i).getLeasonName().toString();
                            }
                            Lavel_Name = usershedulelist.get(i).getLavelName().toString();
                            Institute_Name = usershedulelist.get(i).getInstituteName().toString();
                            Cluster_Name = usershedulelist.get(i).getClusterName().toString();

                            //   State cat = new State(c.getString("state_id"),c.getString("state_name"));
                            UserInfoListRest userInfo = new UserInfoListRest(Schedule_ID, Lavel_ID, Schedule_Date, End_Time, Start_Time, Schedule_Session, Schedule_Status, Subject_Name, Lavel_Name, Leason_Name, Cluster_Name, Institute_Name,Schedule_Status_old);
                            arrayList.add(userInfo);

                        }
                        final String[] items = new String[int_usercount];
                        userInfosarr = new UserInfoListRest[int_usercount];
                        UserInfoListRest obj = new UserInfoListRest();

                        UserInfoListRest.user_info_arr.clear();
                        for (int i = 0; i < int_usercount; i++) {
                            Schedule_ID = arrayList.get(i).scheduleID;
                            Lavel_ID = arrayList.get(i).lavelID;
                            Schedule_Date = arrayList.get(i).scheduleDate;
                            End_Time = arrayList.get(i).endTime;
                            Start_Time = arrayList.get(i).startTime;
                            Schedule_Session = arrayList.get(i).scheduleSession;
                            Schedule_Status = arrayList.get(i).scheduleStatus;
                            Subject_Name = arrayList.get(i).subjectName;
                            Lavel_Name = arrayList.get(i).lavelName;
                            Leason_Name = arrayList.get(i).leasonName;
                            Cluster_Name = arrayList.get(i).clusterName;
                            Institute_Name = arrayList.get(i).instituteName;
                            Schedule_Status_old = arrayList.get(i).schedule_Status_Old;

                            obj.setScheduleID(Schedule_ID);
                            obj.setLavelID(Lavel_ID);
                            obj.setScheduleDate(Schedule_Date);
                            obj.setEndTime(End_Time);
                            obj.setStartTime(Start_Time);
                            obj.setScheduleSession(Schedule_Session);
                            obj.setScheduleStatus(Schedule_Status);
                            obj.setSubjectName(Subject_Name);
                            obj.setLavelName(Lavel_Name);
                            obj.setLeasonName(Leason_Name);
                            obj.setInstituteName(Institute_Name);
                            obj.setClusterName(Cluster_Name);
                            obj.setSchedule_Status_Old(Schedule_Status_old);

                            userInfosarr[i] = obj;
                            //   UserInfo.user_info_arr =new ArrayList<UserInfo>() ;

                            UserInfoListRest.user_info_arr.add(new UserInfoListRest(Schedule_ID, Lavel_ID, Schedule_Date, End_Time, Start_Time, Schedule_Session, Schedule_Status, Subject_Name, Lavel_Name, Leason_Name, Cluster_Name, Institute_Name,Schedule_Status_old));

                            Log.i("Tag", "items aa=" + arrayList.get(i).scheduleID);
                        }
                        //Data_from_HelpDetails_table();

                        //helplist.get(0).

                    }
                    // Log.e("response.body", response.body().size);

                }


            }

            @Override
            public void onFailure(Call call, Throwable t) {

                Log.e("WS", "error" + t.getMessage());
                Toast.makeText(Activity_HomeScreen.this, "WS:" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    ////////////////////////////////// Auto Sync 1st time //////////////////////////////////////

    public void stateListRest_dbCount() {
        SQLiteDatabase db_statelist = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_statelist.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");
        Cursor cursor = db_statelist.rawQuery("SELECT * FROM StateListRest", null);
        int State_x = cursor.getCount();
        Log.e("cursor State_xcount", Integer.toString(State_x));

        if (State_x == 0) {
            GetDropdownValuesRestData();
            GetStudentValuesRestData();
        } else {
            SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
            db1.execSQL("CREATE TABLE IF NOT EXISTS DistrictListRest(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");
            Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictListRest", null);
            int District_x = cursor1.getCount();
            Log.e("cursor Talukcount", Integer.toString(District_x));

            SQLiteDatabase db2 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
            db2.execSQL("CREATE TABLE IF NOT EXISTS TalukListRest(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
            Cursor cursor2 = db2.rawQuery("SELECT DISTINCT * FROM TalukListRest", null);
            int Taluk_x = cursor2.getCount();
            Log.e("cursor Talukcount", Integer.toString(Taluk_x));

            SQLiteDatabase db_village = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
            db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR,PanchayatID VARCHAR);");
            Cursor cursor3 = db_village.rawQuery("SELECT DISTINCT * FROM VillageListRest", null);
            int village_x = cursor3.getCount();
            Log.e("cursor village_xcount", Integer.toString(village_x));

            SQLiteDatabase db_year = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
            db_year.execSQL("CREATE TABLE IF NOT EXISTS YearListRest(YearID VARCHAR,YearName VARCHAR,Sandbox_ID VARCHAR);");
            Cursor cursor5 = db_year.rawQuery("SELECT DISTINCT * FROM YearListRest", null);
            int Year_x = cursor5.getCount();
            Log.d("cursor Yearcount", Integer.toString(Year_x));

            SQLiteDatabase db_clusterlist = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
            db_clusterlist.execSQL("CREATE TABLE IF NOT EXISTS ClusterListRest(ClusterName VARCHAR,ClusterID VARCHAR,Clust_AcademicID VARCHAR, Clust_SandboxID VARCHAR);");
            Cursor cursor6 = db_clusterlist.rawQuery("SELECT DISTINCT * FROM ClusterListRest", null);
            int cluster_x = cursor6.getCount();
            Log.d("cursor clustercount", Integer.toString(cluster_x));

            SQLiteDatabase db_Institutelist = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
            db_Institutelist.execSQL("CREATE TABLE IF NOT EXISTS InstituteListRest(InstituteName VARCHAR, InstituteID VARCHAR,Inst_AcademicID VARCHAR,Inst_ClusterID VARCHAR);");
            Cursor cursor7 = db_Institutelist.rawQuery("SELECT * FROM InstituteListRest", null);
            int Institute_x = cursor7.getCount();
            Log.d("cursor Institute_x", Integer.toString(Institute_x));

            SQLiteDatabase db_Levellist = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
            db_Levellist.execSQL("CREATE TABLE IF NOT EXISTS LevelListRest(LevelName VARCHAR, LevelID VARCHAR, Level_InstituteID VARCHAR,Level_AcademicID VARCHAR,Level_ClusterID VARCHAR,Level_AdmissionFee VARCHAR);");
            Cursor cursor8 = db_Levellist.rawQuery("SELECT * FROM LevelListRest", null);
            int Level_x = cursor8.getCount();
            Log.d("cursor Level_x", Integer.toString(Level_x));

            SQLiteDatabase db_Sandboxlist = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
            db_Sandboxlist.execSQL("CREATE TABLE IF NOT EXISTS SandboxListRest(SandboxName VARCHAR,SandboxID VARCHAR);");
            Cursor cursor9 = db_Sandboxlist.rawQuery("SELECT * FROM SandboxListRest", null);
            int Sandbox_x = cursor9.getCount();
            Log.d("cursor Sandbox_x", Integer.toString(Sandbox_x));

            SQLiteDatabase db_Schoollist = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
            db_Schoollist.execSQL("CREATE TABLE IF NOT EXISTS SchoolListRest(SchoolName VARCHAR, SchoolID VARCHAR, School_InstituteID VARCHAR);");
            Cursor cursor10 = db_Schoollist.rawQuery("SELECT * FROM SchoolListRest", null);
            int School_x = cursor10.getCount();
            Log.d("cursor School_x", Integer.toString(School_x));

            SQLiteDatabase db_studentDetails = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
            db_studentDetails.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR," +
                    "ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR," +
                    "Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");
            Cursor cursor14 = db_studentDetails.rawQuery("SELECT * FROM StudentDetailsRest", null);
            int studentDetails_x = cursor14.getCount();
            Log.e("tag", "studentDetails_x=" + studentDetails_x);
            cursor14.close();

            State_x = State_x - 1;
            District_x = District_x - 1;
            Taluk_x = Taluk_x - 1;
            village_x = village_x - 1;
            Year_x = Year_x - 1;
            cluster_x = cluster_x - 1;
            Institute_x = Institute_x - 1;
            Level_x = Level_x - 1;
            Sandbox_x = Sandbox_x - 1;
            School_x = School_x - 1;

            Log.e("State_x", String.valueOf(State_x));
            Log.e("StateCount", StateCount);
            Log.e("District_x", String.valueOf(District_x));
            Log.e("DistrictCount", DistrictCount);
            Log.e("Taluk_x", String.valueOf(Taluk_x));
            Log.e("TalukaCount", TalukaCount);
            Log.e("village_x", String.valueOf(village_x));
            Log.e("VillageCount", VillageCount);
            Log.e("Year_x", String.valueOf(Year_x));
            Log.e("YearCount", YearCount);
            Log.e("cluster_x", String.valueOf(cluster_x));
            Log.e("ClusterCount", ClusterCount);
            Log.e("Institute_x", String.valueOf(Institute_x));
            Log.e("InstituteCount", InstituteCount);
            Log.e("Level_x", String.valueOf(Level_x));
            Log.e("LevelCount", LevelCount);
            Log.e("School_x", String.valueOf(School_x));
            Log.e("SchoolCount", SchoolCount);
            Log.e("studentDetails_x", String.valueOf(studentDetails_x));
            Log.e("Student_Count", Student_Count);
            if (State_x == Integer.valueOf(StateCount) && District_x == Integer.valueOf(DistrictCount) && Taluk_x == Integer.valueOf(TalukaCount) && village_x == Integer.valueOf(VillageCount) && Year_x == Integer.valueOf(YearCount)
                    && cluster_x == Integer.valueOf(ClusterCount) && Institute_x == Integer.valueOf(InstituteCount) && Level_x == Integer.valueOf(LevelCount) && Sandbox_x == Integer.valueOf(SandboxCount) && School_x == Integer.valueOf(SchoolCount)) {

            } else {
                delete_CountDetailsRestTable_B4insertion();
                deleteStateRestTable_B4insertion();
                deleteDistrictRestTable_B4insertion();
                deleteTalukRestTable_B4insertion();
                deleteVillageRestTable_B4insertion();
                deleteYearRestTable_B4insertion();

                deleteClusterRestTable_B4insertion();
                deleteLevelRestTable_B4insertion();
                deleteInstituteRestTable_B4insertion();
                deleteSandboxRestTable_B4insertion();
                deleteSchoolRestTable_B4insertion();

                GetDropdownValuesRestData();

            }
            if (studentDetails_x == Integer.valueOf(Student_Count)) {

            } else if (studentDetails_x < Integer.valueOf(Student_Count)) {
                deleteStudentdetailsRestTable_B4insertion();
                GetStudentValuesRestData();

            }
            //AddFarmerDetailsNew();
        }
    }

    public void CountCheckList() {
        SQLiteDatabase db_locationCount = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_locationCount.execSQL("CREATE TABLE IF NOT EXISTS LocationCountListRest(StateCount VARCHAR,DistrictCount VARCHAR,TalukaCount VARCHAR,VillageCount VARCHAR,YearCount VARCHAR,Sync_ID VARCHAR,ClusterCount VARCHAR,LevelCount VARCHAR,InstituteCount VARCHAR,SandboxCount VARCHAR,SchoolCount VARCHAR);");
        Cursor cursor1 = db_locationCount.rawQuery("SELECT DISTINCT * FROM LocationCountListRest", null);

        int x = cursor1.getCount();
        Log.d("cursor countlist", Integer.toString(x));

        int i = 0;
        if (cursor1.moveToFirst()) {

            do {
                StateCount = cursor1.getString(cursor1.getColumnIndex("StateCount"));
                DistrictCount = cursor1.getString(cursor1.getColumnIndex("DistrictCount"));
                TalukaCount = cursor1.getString(cursor1.getColumnIndex("TalukaCount"));
                //     PanchayatCount=cursor1.getString(cursor1.getColumnIndex("PanchayatCount"));
                VillageCount = cursor1.getString(cursor1.getColumnIndex("VillageCount"));
                YearCount = cursor1.getString(cursor1.getColumnIndex("YearCount"));
                Sync_ID = cursor1.getString(cursor1.getColumnIndex("Sync_ID"));
                ClusterCount = cursor1.getString(cursor1.getColumnIndex("ClusterCount"));
                LevelCount = cursor1.getString(cursor1.getColumnIndex("LevelCount"));
                InstituteCount = cursor1.getString(cursor1.getColumnIndex("InstituteCount"));
                SandboxCount = cursor1.getString(cursor1.getColumnIndex("SandboxCount"));
                SchoolCount = cursor1.getString(cursor1.getColumnIndex("SchoolCount"));
                // arrayObj_Class_DistrictListDetails2[i] = innerObj_Class_AcademicList;
                i++;
            } while (cursor1.moveToNext());
            Log.e("tag", "StateCount=" + StateCount);
            Log.e("tag", "DistrictCount=" + DistrictCount);
            Log.e("tag", "VillageCount=" + VillageCount);
            Log.e("tag", "Sync_ID=" + Sync_ID);
            SharedPreferences.Editor editor = shared_syncId.edit();
            editor.putString(SyncId, Sync_ID);
            editor.commit();
            SharedPreferences.Editor myprefs_spinner = sharedpref_spinner_Obj.edit();
            myprefs_spinner.putString(Key_syncId, Sync_ID);
            myprefs_spinner.apply();
        }//if ends

    }

    public void UserStudentDataCountCheckList() {
        SQLiteDatabase db_userdataCount = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_userdataCount.execSQL("CREATE TABLE IF NOT EXISTS StudDataCountListRest(Student_Count VARCHAR,Applicant_Count VARCHAR,Admission_Count VARCHAR);");
        Cursor cursor1 = db_userdataCount.rawQuery("SELECT DISTINCT * FROM StudDataCountListRest", null);

        int x = cursor1.getCount();
        Log.d("tag", "cursor userdatacountlist" + Integer.toString(x));

        int i = 0;
        if (cursor1.moveToFirst()) {

            do {
                Student_Count = cursor1.getString(cursor1.getColumnIndex("Student_Count"));
                applicant_Count = cursor1.getString(cursor1.getColumnIndex("Applicant_Count"));
                admission_Count = cursor1.getString(cursor1.getColumnIndex("Admission_Count"));

                i++;
            } while (cursor1.moveToNext());
            Log.e("tag", "Student_Count=" + Student_Count);
            applicant_countTV.setText(applicant_Count);
            admissioncount_TV.setText(admission_Count);

            if (Student_Count == null || Student_Count.equalsIgnoreCase("")) {
                Student_Count = "0";
            }

        }//if ends
    }

    ////////////////////////////////// API Call /////////////////////////////////////////////////
    public void GetDropdownValuesRestData() {
        Log.e("Entered ", "GetAppVersionCheck");
        Log.e("getLocationData", "str_loginuserID=" + str_loginuserID);
        Call<Location_Data> call = userService1.getLocationData(str_loginuserID, "SIV");
        //  Call<Location_Data> call = userService1.getLocationData("90");
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.enqueue(new Callback<Location_Data>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<Location_Data> call, Response<Location_Data> response) {
                Log.e("Entered resp", response.message());
                //     Log.e("Entered resp", response.body().getMessage());

                if (response.isSuccessful()) {
                    Location_Data class_locaitonData = response.body();
                    Log.e("response.body", response.body().getLst().toString());
                    if (class_locaitonData.getStatus().equals(true)) {
                        List<Location_DataList> yearlist = response.body().getLst();
                        Log.e("programlist.size()", String.valueOf(yearlist.size()));

                        location_dataLists = new Location_DataList[yearlist.size()];
                        // Toast.makeText(getContext(), "" + class_monthCounts.getMessage(), Toast.LENGTH_SHORT).show();

                        for (int i = 0; i < location_dataLists.length; i++) {
                            Log.e("status", String.valueOf(class_locaitonData.getStatus()));
                           /* Log.e("msg", class_loginresponse.getMessage());
                            Log.e("list", class_loginresponse.getList().get(i).getId());
                            Log.e("list", class_loginresponse.getList().get(i).getProgramCode());
                            Log.e("size", String.valueOf(class_loginresponse.getList().size()));*/

                            class_location_dataList.setState((class_locaitonData.getLst().get(i).getState()));
                            class_location_dataList.setDistrict(class_locaitonData.getLst().get(i).getDistrict());
                            class_location_dataList.setTaluka(class_locaitonData.getLst().get(i).getTaluka());
                            class_location_dataList.setVillage(class_locaitonData.getLst().get(i).getVillage());
                            class_location_dataList.setYear(class_locaitonData.getLst().get(i).getYear());
                            class_location_dataList.setCount(class_locaitonData.getLst().get(i).getCount());

                            int sizeCount = class_locaitonData.getLst().get(i).getCount().size();
                            for (int j = 0; j < sizeCount; j++) {
                                Log.e("tag", "PanchayatCount ==" + class_locaitonData.getLst().get(i).getCount().get(j).getPanchayatCount());

                                Log.e("tag", "VillageCount==" + class_locaitonData.getLst().get(i).getCount().get(j).getVillageCount());
                                String SCount = class_locaitonData.getLst().get(i).getCount().get(j).getStateCount();
                                String DCount = class_locaitonData.getLst().get(i).getCount().get(j).getDistrictCount();
                                String TCount = class_locaitonData.getLst().get(i).getCount().get(j).getTalukaCount();
                                //    String PCount = class_locaitonData.getLst().get(i).getCount().get(j).getPanchayatCount();
                                String VCount = class_locaitonData.getLst().get(i).getCount().get(j).getVillageCount();
                                String YCount = class_locaitonData.getLst().get(i).getCount().get(j).getYearCount();
                                String Sync_ID = class_locaitonData.getLst().get(i).getCount().get(j).getSyncID();
                                String ClusterCount = class_locaitonData.getLst().get(i).getCount().get(j).getClusterCount();
                                String LevelCount = class_locaitonData.getLst().get(i).getCount().get(j).getLevelCount();
                                String InstituteCount = class_locaitonData.getLst().get(i).getCount().get(j).getInstituteCount();
                                String SandboxCount = class_locaitonData.getLst().get(i).getCount().get(j).getSandboxCount();
                                String SchoolCount = class_locaitonData.getLst().get(i).getCount().get(j).getSchoolCount();
                                SharedPreferences.Editor editor = shared_syncId.edit();
                                editor.putString(SyncId, Sync_ID);
                                editor.commit();
                                SharedPreferences.Editor myprefs_spinner = sharedpref_spinner_Obj.edit();
                                myprefs_spinner.putString(Key_syncId, Sync_ID);
                                myprefs_spinner.apply();
                                DBCreate_CountDetailsRest_insert_2SQLiteDB(SCount, DCount, TCount, VCount, YCount, Sync_ID, ClusterCount, LevelCount, InstituteCount, SandboxCount, SchoolCount);

                            }

                            int sizeState = class_locaitonData.getLst().get(i).getState().size();
                            for (int j = 0; j < sizeState; j++) {
                                Log.e("tag", "state name==" + class_locaitonData.getLst().get(i).getState().get(j).getStateName());
                                String StateName = class_locaitonData.getLst().get(i).getState().get(j).getStateName();
                                String StateId = class_locaitonData.getLst().get(i).getState().get(j).getStateID();
                                DBCreate_StatedetailsRest_insert_2SQLiteDB(StateId, StateName, StateId, j);
                            }
                            int sizeDistrict = class_locaitonData.getLst().get(i).getDistrict().size();
                            for (int j = 0; j < sizeDistrict; j++) {
                                Log.e("tag", "District name==" + class_locaitonData.getLst().get(i).getDistrict().get(j).getDistrictName());
                                String DistrictName = class_locaitonData.getLst().get(i).getDistrict().get(j).getDistrictName();
                                String DistrictId = class_locaitonData.getLst().get(i).getDistrict().get(j).getDistrictID();
                                String DistrictStateId = class_locaitonData.getLst().get(i).getDistrict().get(j).getStateID();
                                DBCreate_DistrictdetailsRest_insert_2SQLiteDB(DistrictId, DistrictName, "Y1", DistrictStateId, j);
                            }
                            Log.e("tag", "size==" + class_locaitonData.getLst().get(i).getTaluka().size());
                            int sizeTaluka = class_locaitonData.getLst().get(i).getTaluka().size();
                            for (int j = 0; j < sizeTaluka; j++) {
                                Log.e("tag", "Taluka name==" + class_locaitonData.getLst().get(i).getTaluka().get(j).getTalukaName());
                                String TalukaName = class_locaitonData.getLst().get(i).getTaluka().get(j).getTalukaName();
                                String TalukaId = class_locaitonData.getLst().get(i).getTaluka().get(j).getTalukaID();
                                String TalukaDistrictId = class_locaitonData.getLst().get(i).getTaluka().get(j).getDistrictID();
                                DBCreate_TalukdetailsRest_insert_2SQLiteDB(TalukaId, TalukaName, TalukaDistrictId, j);
                            }

                            int sizeVillage = class_locaitonData.getLst().get(i).getVillage().size();
                            for (int j = 0; j < sizeVillage; j++) {
                                Log.e("tag", "Village name==" + class_locaitonData.getLst().get(i).getVillage().get(j).getVillageName());
                                String VillageName = class_locaitonData.getLst().get(i).getVillage().get(j).getVillageName();
                                String VillageId = class_locaitonData.getLst().get(i).getVillage().get(j).getVillageID();
                                //  String VillagePanchayatId = class_locaitonData.getLst().get(i).getVillage().get(j).getPanchayatID();
                                String VillageTalukId = class_locaitonData.getLst().get(i).getVillage().get(j).getTalukaID();
                                DBCreate_VillagedetailsRest_insert_2SQLiteDB(VillageId, VillageName, VillageTalukId, j);
                            }
                            int sizeYear = class_locaitonData.getLst().get(i).getYear().size();
                            for (int j = 0; j < sizeYear; j++) {
                                Log.e("tag", "Year name==" + class_locaitonData.getLst().get(i).getYear().get(j).getAcademicName());
                                String YearName = class_locaitonData.getLst().get(i).getYear().get(j).getAcademicName();
                                String YearId = class_locaitonData.getLst().get(i).getYear().get(j).getAcademicID();
                                String Sandbox_ID = class_locaitonData.getLst().get(i).getYear().get(j).getSandbox_ID();
                                DBCreate_YeardetailsRest_insert_2SQLiteDB(YearId, YearName, Sandbox_ID, j);
                            }
                            int sizeSandbox = class_locaitonData.getLst().get(i).getSandbox().size();
                            for (int j = 0; j < sizeSandbox; j++) {
                                Log.e("tag", "Sandbox name==" + class_locaitonData.getLst().get(i).getSandbox().get(j).getSandboxName());
                                String SandboxName = class_locaitonData.getLst().get(i).getSandbox().get(j).getSandboxName();
                                String SandboxID = class_locaitonData.getLst().get(i).getSandbox().get(j).getSandboxID();
                                DBCreate_SandboxdetailsRest_insert_2SQLiteDB(SandboxName, SandboxID, j);
                            }
                            int sizeCluster = class_locaitonData.getLst().get(i).getCluster().size();
                            for (int j = 0; j < sizeCluster; j++) {
                                Log.e("tag", "Cluster name==" + class_locaitonData.getLst().get(i).getCluster().get(j).getClusterName());
                                String ClusterName = class_locaitonData.getLst().get(i).getCluster().get(j).getClusterName();
                                String ClusterID = class_locaitonData.getLst().get(i).getCluster().get(j).getClusterID();
                                String Clust_AcademicID = class_locaitonData.getLst().get(i).getCluster().get(j).getAcademicID();
                                String Clust_SandboxID = class_locaitonData.getLst().get(i).getCluster().get(j).getSandboxID();
                                DBCreate_ClusterdetailsRest_insert_2SQLiteDB(ClusterName, ClusterID, Clust_AcademicID, Clust_SandboxID, j);
                            }
                            int sizeInstitute = class_locaitonData.getLst().get(i).getInstitute().size();
                            for (int j = 0; j < sizeInstitute; j++) {
                                Log.e("tag", "Institute name==" + class_locaitonData.getLst().get(i).getInstitute().get(j).getInstituteName());
                                String InstituteName = class_locaitonData.getLst().get(i).getInstitute().get(j).getInstituteName();
                                String InstituteID = class_locaitonData.getLst().get(i).getInstitute().get(j).getInstituteID();
                                String Inst_AcademicID = class_locaitonData.getLst().get(i).getInstitute().get(j).getAcademicID();
                                String Inst_ClusterID = class_locaitonData.getLst().get(i).getInstitute().get(j).getClusterID();
                                DBCreate_InstitutedetailsRest_insert_2SQLiteDB(InstituteName, InstituteID, Inst_AcademicID, Inst_ClusterID, j);
                            }
                            int sizeSchool = class_locaitonData.getLst().get(i).getSchool().size();
                            for (int j = 0; j < sizeSchool; j++) {
                                Log.e("tag", "School name==" + class_locaitonData.getLst().get(i).getSchool().get(j).getSchoolName());
                                String SchoolName = class_locaitonData.getLst().get(i).getSchool().get(j).getSchoolName();
                                String SchoolID = class_locaitonData.getLst().get(i).getSchool().get(j).getSchoolID();
                                String School_InstituteID = class_locaitonData.getLst().get(i).getSchool().get(j).getInstituteID();
                                DBCreate_SchooldetailsRest_insert_2SQLiteDB(SchoolName, SchoolID, School_InstituteID, j);
                            }
                            int sizeLevel = class_locaitonData.getLst().get(i).getLevel().size();
                            for (int j = 0; j < sizeLevel; j++) {
                                Log.e("tag", "Level ID==" + class_locaitonData.getLst().get(i).getLevel().get(j).getLevelID());
                                Log.e("tag", "Level name==" + class_locaitonData.getLst().get(i).getLevel().get(j).getLevelName());
                                String LevelName = class_locaitonData.getLst().get(i).getLevel().get(j).getLevelName();
                                String LevelID = class_locaitonData.getLst().get(i).getLevel().get(j).getLevelID();
                                String Level_InstituteID = class_locaitonData.getLst().get(i).getLevel().get(j).getInstituteID();
                                String Level_AcademicID = class_locaitonData.getLst().get(i).getLevel().get(j).getAcademicID();
                                String Level_ClusterID = class_locaitonData.getLst().get(i).getLevel().get(j).getClusterID();
                                String Level_AdmissionFee = class_locaitonData.getLst().get(i).getLevel().get(j).getAdmissionFee();

                                //  String Level_AdmissionFee = "0";

                                DBCreate_LeveldetailsRest_insert_2SQLiteDB(LevelName, LevelID, Level_InstituteID, Level_AcademicID, Level_ClusterID, Level_AdmissionFee, j);


                            }

                            int sizeAssessment = class_locaitonData.getLst().get(i).getAssessment().size();
                            for (int j = 0; j < sizeAssessment; j++) {
                                Log.e("tag", "Assement name==" + class_locaitonData.getLst().get(i).getAssessment().get(j).getAssessment_Name());
                                String Assessment_Name = class_locaitonData.getLst().get(i).getAssessment().get(j).getAssessment_Name();
                                String Assessment_ID = class_locaitonData.getLst().get(i).getAssessment().get(j).getAssessment_ID();
                                //  String OptionID = class_locaitonData.getLst().get(i).getAssessment().get(j).g();
                                DBCreate_AssementRest_insert_2SQLiteDB(Assessment_Name, Assessment_ID, j);
                            }
                            int sizeLearningMode = class_locaitonData.getLst().get(i).getLearningMode().size();
                            for (int j = 0; j < sizeLearningMode; j++) {
                                Log.e("tag", "LearningMode name==" + class_locaitonData.getLst().get(i).getLearningMode().get(j).getLearningMode_Name());
                                String LearningMode_Name = class_locaitonData.getLst().get(i).getLearningMode().get(j).getLearningMode_Name();
                                String LearningMode_ID = class_locaitonData.getLst().get(i).getLearningMode().get(j).getLearningMode_ID();

                                DBCreate_LearningModeRest_insert_2SQLiteDB(LearningMode_ID, LearningMode_Name, j);
                            }
                            int sizeEducation = class_locaitonData.getLst().get(i).getEducation().size();
                            for (int j = 0; j < sizeEducation; j++) {
                                Log.e("tag", "Education name==" + class_locaitonData.getLst().get(i).getEducation().get(j).getEducation_Name());
                                String Education_Name = class_locaitonData.getLst().get(i).getEducation().get(j).getEducation_Name();
                                String Education_ID = class_locaitonData.getLst().get(i).getEducation().get(j).getEducation_ID();

                                DBCreate_EducationRest_insert_2SQLiteDB(Education_ID, Education_Name, j);
                            }


                        }

                        /*uploadfromDB_Yearlist();
                        uploadfromDB_Statelist();
                        uploadfromDB_Districtlist();
                        uploadfromDB_Taluklist();
                        uploadfromDB_Villagelist();
                        uploadfromDB_Grampanchayatlist();*/
                    } else {
                        Toast.makeText(Activity_HomeScreen.this, class_locaitonData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDoalog.dismiss();
                    Log.e("tag", "working");
                } else {
                    progressDoalog.dismiss();
                    Log.e("Entered resp else", "");
                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.e("GetDropdownValuesRestData", "error message=" + error.getMsg());

                    Toast.makeText(Activity_HomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

                Log.e("tag", t.getMessage());
                Toast.makeText(Activity_HomeScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }

    @SuppressLint("LongLogTag")
    public void GetStudentValuesRestData() {
        Log.e("Entered ", "GetStudentValuesRestData");

        Log.e("str_loginuser_id", str_loginuserID);
        Call<Student> call = userService1.getStudentData(str_loginuserID);
        //  Call<Location_Data> call = userService1.getLocationData("90");
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.enqueue(new Callback<Student>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                Log.e("Entered resp", response.message());
                //     Log.e("Entered resp", response.body().getMessage());

                if (response.isSuccessful()) {
                    Student class_studentData = response.body();
                    Log.e("response.body", response.body().getLstCount1().toString());
                    if (class_studentData.getStatus().equals(true)) {
                        List<StudCountList> studentcountLists = response.body().getLstCount1();
                        Log.e("tag", "studentlist.size()=" + String.valueOf(studentcountLists.size()));

                        studCountLists = new StudCountList[studentcountLists.size()];
                        for (int i1 = 0; i1 < studCountLists.length; i1++) {


                            // Toast.makeText(getContext(), "" + class_monthCounts.getMessage(), Toast.LENGTH_SHORT).show();
                            class_studCountLists.setCount(class_studentData.getLstCount1().get(i1).getCount());
                            class_studCountLists.setStudents(class_studentData.getLstCount1().get(i1).getStudents());
                            int sizeCount = 0;
                            if (class_studentData.getLstCount1().get(i1).getCount() != null) {
                                sizeCount = class_studentData.getLstCount1().get(i1).getCount().size();
                            }
                            for (int j = 0; j < sizeCount; j++) {
                                String Student_Count = class_studentData.getLstCount1().get(i1).getCount().get(j).getStudentCount();
                                 applicant_Count = class_studentData.getLstCount1().get(i1).getCount().get(j).getApplicantCount();
                                 admission_Count = class_studentData.getLstCount1().get(i1).getCount().get(j).getAdmissionCount();
                                Log.e("applicant_Count", String.valueOf(applicant_Count));
                                Log.e("admission_Count", String.valueOf(admission_Count));

                                DBCreate_StudDataCountListRest_insert_2SQLiteDB(Student_Count,applicant_Count,admission_Count);
                            }
                            int sizeStudList = 0;
                            if (class_studentData.getLstCount1().get(i1).getStudents() != null) {
                                sizeStudList = class_studentData.getLstCount1().get(i1).getStudents().size();
                            }
                            for (int i = 0; i < sizeStudList; i++) {


                                Log.e("status", String.valueOf(class_StudentList.getStudentStatus()));
                           /* Log.e("msg", class_loginresponse.getMessage());
                            Log.e("list", class_loginresponse.getList().get(i).getId());
                            Log.e("list", class_loginresponse.getList().get(i).getProgramCode());
                            Log.e("size", String.valueOf(class_loginresponse.getList().size()));*/
                                String Stud_TempId = "";
                                String AcademicID = String.valueOf(class_studentData.getLstCount1().get(i1).getStudents().get(i).getAcademicID());
                                String AcademicName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getAcademicName();
                                String AdmissionFee = class_studentData.getLstCount1().get(i1).getStudents().get(i).getAdmissionFee();
                                String ApplicationNo = class_studentData.getLstCount1().get(i1).getStudents().get(i).getApplicationNo();
                                String BalanceFee = class_studentData.getLstCount1().get(i1).getStudents().get(i).getBalanceFee();
                                String BirthDate = class_studentData.getLstCount1().get(i1).getStudents().get(i).getBirthDate();
                                String ClusterID = String.valueOf(class_studentData.getLstCount1().get(i1).getStudents().get(i).getClusterID());
                                String ClusterName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getClusterName();
                                String CreatedDate = class_studentData.getLstCount1().get(i1).getStudents().get(i).getCreatedDate();
                                String Education = class_studentData.getLstCount1().get(i1).getStudents().get(i).getEducation();
                                String FatherName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getFatherName();
                                String Gender = class_studentData.getLstCount1().get(i1).getStudents().get(i).getGender();
                                String InstituteName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getInstituteName();
                                String InstituteID = String.valueOf(class_studentData.getLstCount1().get(i1).getStudents().get(i).getInstituteID());
                                String LevelID = String.valueOf(class_studentData.getLstCount1().get(i1).getStudents().get(i).getLevelID());
                                String LevelName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getLevelName();
                                String Marks4 = class_studentData.getLstCount1().get(i1).getStudents().get(i).getMarks4();
                                String Marks5 = class_studentData.getLstCount1().get(i1).getStudents().get(i).getMarks5();
                                String Marks6 = class_studentData.getLstCount1().get(i1).getStudents().get(i).getMarks6();
                                String Marks7 = class_studentData.getLstCount1().get(i1).getStudents().get(i).getMarks7();
                                String Marks8 = class_studentData.getLstCount1().get(i1).getStudents().get(i).getMarks8();
                                String Mobile = class_studentData.getLstCount1().get(i1).getStudents().get(i).getMobile();
                                String MotherName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getMotherName();
                                String PaidFee = class_studentData.getLstCount1().get(i1).getStudents().get(i).getPaidFee();
                                String ReceiptNo = class_studentData.getLstCount1().get(i1).getStudents().get(i).getReceiptNo();
                                String SandboxID = String.valueOf(class_studentData.getLstCount1().get(i1).getStudents().get(i).getSandboxID());
                                String SandboxName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getSandboxName();
                                String SchoolID = String.valueOf(class_studentData.getLstCount1().get(i1).getStudents().get(i).getSchoolID());
                                String SchoolName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getSchoolName();
                                String StudentAadhar = class_studentData.getLstCount1().get(i1).getStudents().get(i).getStudentAadhar();
                                String StudentID = String.valueOf(class_studentData.getLstCount1().get(i1).getStudents().get(i).getStudentID());
                                String StudentName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getStudentName();
                                String StudentPhoto = class_studentData.getLstCount1().get(i1).getStudents().get(i).getStudentPhoto();
                                String StudentStatus = class_studentData.getLstCount1().get(i1).getStudents().get(i).getStudentStatus();
                                try {
                                    if (class_studentData.getLstCount1().get(i1).getStudents().get(i).getTempID().equals("null")) {
                                        Stud_TempId = "";

                                    } else {
                                        Stud_TempId = class_studentData.getLstCount1().get(i1).getStudents().get(i).getTempID();
                                    }
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                String Learning_Mode = class_studentData.getLstCount1().get(i1).getStudents().get(i).getLearningMode();

                                String str_stateid = class_studentData.getLstCount1().get(i1).getStudents().get(i).getState_ID();
                                String str_statename = class_studentData.getLstCount1().get(i1).getStudents().get(i).getState_Name();
                                String str_distid = class_studentData.getLstCount1().get(i1).getStudents().get(i).getDistrict_ID();
                                String str_distname = class_studentData.getLstCount1().get(i1).getStudents().get(i).getDistrict_Name();
                                String str_talukid = class_studentData.getLstCount1().get(i1).getStudents().get(i).getTaluk_ID();
                                String str_talukname = class_studentData.getLstCount1().get(i1).getStudents().get(i).getTaluk_Name();
                                String str_villageid = class_studentData.getLstCount1().get(i1).getStudents().get(i).getVillage_ID();
                                String str_villagename = class_studentData.getLstCount1().get(i1).getStudents().get(i).getVillage_Name();
                                String str_address = class_studentData.getLstCount1().get(i1).getStudents().get(i).getAddress();
                                String str_alternatemobno= class_studentData.getLstCount1().get(i1).getStudents().get(i).getAlternate_Mobile();

                                Log.e("tag", "StudentName=" + StudentName + "StudentStatus=" + StudentStatus + "ApplicationNo=" + ApplicationNo);

                                String str_imageurl = StudentPhoto;
                                Log.e("tag", "str_imageurl==" + str_imageurl);
                                String str_base64image = null;
                                if (str_imageurl == null || str_imageurl.equals("") || str_imageurl.equals("0")) {
                                } else {
                                    //  str_imageurl=class_farmerlistdetails_arrayobj2[i].getFarmerPhoto();

                                    String str_farmpondimageurl = str_imageurl;

                                    InputStream inputstream_obj = null;
                                    try {
                                        if (android.os.Build.VERSION.SDK_INT > 9) {
                                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                            StrictMode.setThreadPolicy(policy);
                                            inputstream_obj = new URL(str_farmpondimageurl).openStream();

                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        Bitmap mIcon12 = BitmapFactory.decodeStream(inputstream_obj);
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        mIcon12.compress(Bitmap.CompressFormat.PNG, 60, baos);
                                        byte[] b = baos.toByteArray();
                                        str_base64image = Base64.encodeToString(b, Base64.DEFAULT);

                                        Log.e("tag", "byteArray img=" + b);

                                        //  str_base64image1 = str_base64image;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }

                                DBCreate_StudentdetailsRest_insert_2SQLiteDB(AcademicID, AcademicName, AdmissionFee, ApplicationNo, BalanceFee, BirthDate, ClusterID,
                                        ClusterName, CreatedDate, Education, FatherName, Gender, InstituteName, InstituteID, LevelID, LevelName, Marks4, Marks5, Marks6, Marks7, Marks8,
                                        Mobile, MotherName, PaidFee, ReceiptNo, SandboxID, SandboxName, SchoolID, SchoolName, StudentAadhar, StudentID, StudentName, StudentPhoto, StudentStatus, str_base64image, Stud_TempId, "online", Learning_Mode, str_stateid, str_statename, str_distid, str_distname, str_talukid, str_talukname, str_villageid, str_villagename, str_address,str_alternatemobno);


                          /*  class_StudentList.setAcademicID((class_studentData.getLst().get(i).getAcademicID()));
                            class_StudentList.setAcademicName(class_studentData.getLst().get(i).getAcademicName());
                            class_StudentList.setAdmissionFee(class_studentData.getLst().get(i).getAdmissionFee());
                            class_StudentList.setApplicationNo(class_studentData.getLst().get(i).getApplicationNo());
                            class_StudentList.setBalanceFee(class_studentData.getLst().get(i).getBalanceFee());
                            class_StudentList.setBirthDate(class_studentData.getLst().get(i).getBirthDate());
                            class_StudentList.setClusterID(class_studentData.getLst().get(i).getClusterID());
                            class_StudentList.setClusterName(class_studentData.getLst().get(i).getClusterName());
                            class_StudentList.setCreatedDate(class_studentData.getLst().get(i).getCreatedDate());
                            class_StudentList.setEducation(class_studentData.getLst().get(i).getEducation());
                            class_StudentList.setFatherName(class_studentData.getLst().get(i).getFatherName());
                            class_StudentList.setGender(class_studentData.getLst().get(i).getGender());
                            class_StudentList.setInstituteName(class_studentData.getLst().get(i).getInstituteName());
                            class_StudentList.setInstituteID(class_studentData.getLst().get(i).getInstituteID());
                            class_StudentList.setLevelID(class_studentData.getLst().get(i).getLevelID());
                            class_StudentList.setLevelName(class_studentData.getLst().get(i).getLevelName());
                            class_StudentList.setMarks4(class_studentData.getLst().get(i).getMarks4());
                            class_StudentList.setMarks5(class_studentData.getLst().get(i).getMarks5());
                            class_StudentList.setMarks6(class_studentData.getLst().get(i).getMarks6());
                            class_StudentList.setMarks7(class_studentData.getLst().get(i).getMarks7());
                            class_StudentList.setMarks8(class_studentData.getLst().get(i).getMarks8());
                            class_StudentList.setMobile(class_studentData.getLst().get(i).getMobile());
                            class_StudentList.setMotherName(class_studentData.getLst().get(i).getMotherName());
                            class_StudentList.setPaidFee(class_studentData.getLst().get(i).getPaidFee());
                            class_StudentList.setReceiptNo(class_studentData.getLst().get(i).getReceiptNo());
                            class_StudentList.setSandboxID(class_studentData.getLst().get(i).getSandboxID());
                            class_StudentList.setSandboxName(class_studentData.getLst().get(i).getSandboxName());
                            class_StudentList.setSchoolID(class_studentData.getLst().get(i).getSchoolID());
                            class_StudentList.setSchoolName(class_studentData.getLst().get(i).getSchoolName());
                            class_StudentList.setStudentAadhar(class_studentData.getLst().get(i).getStudentAadhar());
                            class_StudentList.setStudentID(class_studentData.getLst().get(i).getStudentID());
                            class_StudentList.setStudentName(class_studentData.getLst().get(i).getStudentName());
                            class_StudentList.setStudentPhoto(class_studentData.getLst().get(i).getStudentPhoto());
                            class_StudentList.setStudentStatus(class_studentData.getLst().get(i).getStudentStatus());*/
                            }
                        }
                        /*uploadfromDB_Yearlist();
                        uploadfromDB_Statelist();
                        uploadfromDB_Districtlist();
                        uploadfromDB_Taluklist();
                        uploadfromDB_Villagelist();
                        uploadfromDB_Grampanchayatlist();*/


                    } else {
                        Toast.makeText(Activity_HomeScreen.this, class_studentData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDoalog.dismiss();
                    Log.e("tag", "working");
                    AddStudentDetailsNew();
                } else {
                    progressDoalog.dismiss();
                    Log.e("Entered resp else", "");
                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    //  Log.e("error message", error.getMsg());
                    Log.e("GetStudentValuesRestData", "error message=" + error.getMsg());

                    Toast.makeText(Activity_HomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

                Log.e("tag", t.getMessage());
                Toast.makeText(Activity_HomeScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }

    public void GetAppVersionCheck() {
        Log.e("Entered ", "GetAppVersionCheck");

//        Map<String,String> params = new HashMap<String, String>();
//
//        params.put("User_ID","90");// for dynamic

        retrofit2.Call call = userService1.getAppVersion();
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.enqueue(new Callback<GetAppVersion>() {
            @Override
            public void onResponse(Call<GetAppVersion> call, Response<GetAppVersion> response) {
                Log.e("response", response.toString());
                Log.e("TAG", "response: " + new Gson().toJson(response));
                Log.e("response body", String.valueOf(response.body()));

                if (response.isSuccessful()) {
                    //        progressDoalog.dismiss();
                    GetAppVersion class_loginresponse = response.body();
                    Log.e("tag", "res==" + class_loginresponse.toString());
                    if (class_loginresponse.getStatus()) {


                        List<GetAppVersionList> getAppVersionList = response.body().getlistVersion();
                        String str_releaseVer = getAppVersionList.get(0).getAppVersion();

                        int int_versioncode = Integer.parseInt(versioncode);
                        int int_releaseVer = Integer.parseInt(str_releaseVer);

                        Log.e("tag", "str_releaseVer=" + str_releaseVer);
                        if (int_releaseVer > int_versioncode) {
                            //call pop
                            Log.e("popup", "popup");
                            //alerts();
                            alerts_dialog_playstoreupdate();
                        } else {

                        }
                        progressDoalog.dismiss();
                    } else {
                        progressDoalog.dismiss();
                        Toast.makeText(Activity_HomeScreen.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    // Log.d("error message", error.getMsg());
                    Log.e("GetAppVersionCheck", "error message" + error.getMsg());

                    Toast.makeText(Activity_HomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDoalog.dismiss();
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(Activity_HomeScreen.this, "WS:Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("HardwareIds")
    public void Add_setGCM1() {
        Log.e("Entered ", "Add_setGCM1");

        tm1 = (TelephonyManager) getBaseContext()
                .getSystemService(Context.TELEPHONY_SERVICE);

        String NetworkType;
        simOperatorName = tm1.getSimOperatorName();
        Log.e("Operator", "" + simOperatorName);
        NetworkType = "GPRS";


        int simSpeed = tm1.getNetworkType();
        if (simSpeed == 1)
            NetworkType = "Gprs";
        else if (simSpeed == 4)
            NetworkType = "Edge";
        else if (simSpeed == 8)
            NetworkType = "HSDPA";
        else if (simSpeed == 13)
            NetworkType = "LTE";
        else if (simSpeed == 3)
            NetworkType = "UMTS";
        else
            NetworkType = "Unknown";

        Log.e("SIM_INTERNET_SPEED", "" + NetworkType);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        tmDevice = "" + tm1.getDeviceId();
//        Log.e("DeviceIMEI", "" + tmDevice);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tmDevice = Settings.Secure.getString(
                    Activity_HomeScreen.this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            if (tm1.getDeviceId() != null) {
                tmDevice = tm1.getDeviceId();
            } else {
                tmDevice = Settings.Secure.getString(
                        Activity_HomeScreen.this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }

        Log.e("tmDevice", "" + tmDevice);

        mobileNumber = "" + tm1.getLine1Number();
        Log.e("getLine1Number value", "" + mobileNumber);

        String mobileNumber1 = "" + tm1.getPhoneType();
        Log.e("getPhoneType value", "" + mobileNumber1);
        // tmSerial = "" + tm1.getSimSerialNumber();

        /*added by shivaleela */
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tmSerial = Settings.Secure.getString(
                    Activity_HomeScreen.this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            // tmSerial = "" + tm1.getSimSerialNumber();

        } else {
            if (tm1.getSimSerialNumber() != null) {
                tmSerial = tm1.getSimSerialNumber();
            } else {
                tmSerial = Settings.Secure.getString(
                        Activity_HomeScreen.this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }
        Log.e("tmSerial", "" + tmSerial);


        //  Log.v("GSM devices Serial Number[simcard] ", "" + tmSerial);
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        Log.e("androidId CDMA devices", "" + androidId);
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        deviceId = deviceUuid.toString();
        //  Log.v("deviceIdUUID universally unique identifier", "" + deviceId);


        deviceModelName = Build.MODEL;
        Log.v("Model Name", "" + deviceModelName);
        deviceUSER = Build.USER;
        Log.v("Name USER", "" + deviceUSER);
        devicePRODUCT = Build.PRODUCT;
        Log.v("PRODUCT", "" + devicePRODUCT);
        deviceHARDWARE = Build.HARDWARE;
        Log.v("HARDWARE", "" + deviceHARDWARE);
        deviceBRAND = Build.BRAND;
        Log.v("BRAND", "" + deviceBRAND);
        myVersion = Build.VERSION.RELEASE;
        Log.v("VERSION.RELEASE", "" + myVersion);
        sdkVersion = Build.VERSION.SDK_INT;
        Log.v("VERSION.SDK_INT", "" + sdkVersion);
        sdkver = Integer.toString(sdkVersion);
        // Get display details

        Measuredwidth = 0;
        Measuredheight = 0;
        Point size = new Point();
        WindowManager w = getWindowManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //   w.getDefaultDisplay().getSize(size);
            Measuredwidth = w.getDefaultDisplay().getWidth();//size.x;
            Measuredheight = w.getDefaultDisplay().getHeight();//size.y;
        } else {
            Display d = w.getDefaultDisplay();
            Measuredwidth = d.getWidth();
            Measuredheight = d.getHeight();
        }

        Log.e("SCREEN_Width", "" + Measuredwidth);
        Log.e("SCREEN_Height", "" + Measuredheight);


        regId = FirebaseInstanceId.getInstance().getToken();


        Log.e("regId_DeviceID", "" + regId);

        Class_devicedetails request = new Class_devicedetails();
        Log.e("str_userid1", "" + str_userid);

        request.setUser_ID(str_loginuserID);
        request.setDeviceId(regId);
        request.setOSVersion(myVersion);
        request.setManufacturer(deviceBRAND);
        request.setModelNo(deviceModelName);
        request.setSDKVersion(sdkver);
        request.setDeviceSrlNo(tmDevice);
        request.setServiceProvider(simOperatorName);
        request.setSIMSrlNo(tmSerial);
        request.setDeviceWidth(String.valueOf(Measuredwidth));
        request.setDeviceHeight(String.valueOf(Measuredheight));
        request.setAppVersion(versioncode);


        {
            retrofit2.Call call = userService1.Post_ActionDeviceDetails(request);

            call.enqueue(new Callback<Class_devicedetails>() {
                @Override
                public void onResponse(retrofit2.Call<Class_devicedetails> call, Response<Class_devicedetails> response) {


                    Log.e("response1", response.toString());
                    Log.e("response_body1", String.valueOf(response.body()));

                    if (response.isSuccessful()) {
                        //  progressDoalog.dismiss();


                        Class_devicedetails class_addfarmponddetailsresponse = response.body();

                        if (class_addfarmponddetailsresponse.getStatus().equals("true")) {
                            Log.e("devicedetails", "devicedetails_Added");

                            gethelp();

                        } else if (class_addfarmponddetailsresponse.getStatus().equals("false")) {
                            //     progressDoalog.dismiss();
                            Toast.makeText(Activity_HomeScreen.this, class_addfarmponddetailsresponse.getMessage(), Toast.LENGTH_SHORT).show();
                            gethelp();
                        }
                    } else {
                        //   progressDoalog.dismiss();
                        DefaultResponse error = ErrorUtils.parseError(response);
                        Log.e("devicedetailserror", error.getMsg());
                        Toast.makeText(Activity_HomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();
                        gethelp();


                    }

                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(Activity_HomeScreen.this, "error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("response_error", t.getMessage().toString());
                }
            });

        }
    }

    public void gethelp() {
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if (isInternetPresent) {
            gethelp_api();
            //getdemo();
        }
    }

    private void gethelp_api() {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();

        Call<Class_gethelp_Response> call = userService.GetHelp(str_loginuserID);


        call.enqueue(new Callback<Class_gethelp_Response>() {
            @Override
            public void onResponse(Call<Class_gethelp_Response> call, Response<Class_gethelp_Response> response) {

                // Toast.makeText(MainActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();

                Log.e("response_gethelp", "response_gethelp: " + new Gson().toJson(response));

               /* Class_gethelp_Response gethelp_response_obj = new Class_gethelp_Response();
                gethelp_response_obj = (Class_gethelp_Response) response.body();*/


                if (response.isSuccessful()) {
                    DBCreate_Helpdetails();
                    Class_gethelp_Response gethelp_response_obj = response.body();
                    Log.e("response.body", response.body().getLst().toString());


                    if (gethelp_response_obj.getStatus().equals(true)) {

                        List<Class_gethelp_resplist> helplist = response.body().getLst();
                        Log.e("length", String.valueOf(helplist.size()));
                        int int_helpcount = helplist.size();

                        for (int i = 0; i < int_helpcount; i++) {
                            Log.e("title", helplist.get(i).getTitle().toString());

                            String str_title = helplist.get(i).getTitle().toString();
                            String str_content = helplist.get(i).getContent().toString();
                            DBCreate_HelpDetails_insert_2sqliteDB(str_title, str_content);
                        }


                        // Data_from_HelpDetails_table();

                        //helplist.get(0).
                        progressDoalog.dismiss();

                        getdemo();
                    }
                    // Log.e("response.body", response.body().size);

                }


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDoalog.dismiss();
                Log.e("WS", "error" + t.getMessage());
                Toast.makeText(Activity_HomeScreen.this, "WS:" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getdemo() {
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if (isInternetPresent) {
            getdemo_api();
        }
    }

    private void getdemo_api() {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();

        Call<Class_getdemo_Response> call = userService.GetDemo(str_loginuserID);//str_userid


        call.enqueue(new Callback<Class_getdemo_Response>() {
            @Override
            public void onResponse(Call<Class_getdemo_Response> call, Response<Class_getdemo_Response> response) {
                Log.e("response_gethelp", "response_gethelp: " + new Gson().toJson(response));

               /* Class_gethelp_Response gethelp_response_obj = new Class_gethelp_Response();
                gethelp_response_obj = (Class_gethelp_Response) response.body();*/


                if (response.isSuccessful()) {
                    DBCreate_Demodetails();
                    Class_getdemo_Response getdemo_response_obj = response.body();
                    Log.e("response.body", response.body().getLst().toString());


                    if (getdemo_response_obj.getStatus().equals(true)) {

                        List<Class_getdemo_resplist> demolist = response.body().getLst();
                        Log.e("length", String.valueOf(demolist.size()));
                        int int_helpcount = demolist.size();

                        for (int i = 0; i < int_helpcount; i++) {
                            Log.e("language", demolist.get(i).getLanguage_Name().toString());

                            String str_languagename = demolist.get(i).getLanguage_Name().toString();
                            String str_languagelink = demolist.get(i).getLanguage_Link().toString();
                            DBCreate_DemoDetails_insert_2sqliteDB(str_languagename, str_languagelink);
                        }

                        //Data_from_HelpDetails_table();

                        //helplist.get(0).
                        progressDoalog.dismiss();
                    }
                    // Log.e("response.body", response.body().size);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDoalog.dismiss();
                Log.e("WS", "error" + t.getMessage());
                Toast.makeText(Activity_HomeScreen.this, "WS:" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void GetAutoSyncVersion() {
        Log.e("Entered ", "GetAutoSyncVersion");

//        Map<String,String> params = new HashMap<String, String>();
//
//        params.put("User_ID","90");// for dynamic
        Log.e("GetAutoSyncVersion", " str_loginuserID=" + str_loginuserID);

        retrofit2.Call call = userService1.getAutoSyncVersion(str_loginuserID);//str_employee_id
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.enqueue(new Callback<AutoSyncVersion>() {
            @Override
            public void onResponse(Call<AutoSyncVersion> call, Response<AutoSyncVersion> response) {
                Log.e("tag", "response AutoSyncVersion=" + response.toString());
                Log.e("TAG", "response AutoSyncVersion: " + new Gson().toJson(response));
                Log.e("response body", String.valueOf(response.body()));

                if (response.isSuccessful()) {
                    //  progressDoalog.dismiss();
                    AutoSyncVersion class_loginresponse = response.body();
                    Log.e("tag", "res==" + class_loginresponse.toString());
                    VersionStatus = String.valueOf(class_loginresponse.getStatus());
                    Log.e("tag", "res==" + VersionStatus);

                    if (class_loginresponse.getStatus()) {

                        List<AutoSyncVersionList> addFarmerResList = response.body().getListVersion();
                        Log.e("tag", "getUserSync =" + addFarmerResList.get(0).getUserSync());
                        Log.e("tag", "getUserID =" + addFarmerResList.get(0).getUserID());

                        //  str_VersionStatus = String.valueOf(class_loginresponse.getStatus());
                        alerts_dialog_AutoSyncVersion();
                        progressDoalog.dismiss();

                    } else {
                        progressDoalog.dismiss();

                        Log.e("tag", "class_loginresponse.getMessage() =" + class_loginresponse.getMessage());

                        //  Toast.makeText(Activity_MarketingHomeScreen.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.e("autosync", "error message" + error.getMsg());

                    // Toast.makeText(Activity_MarketingHomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(Activity_HomeScreen.this, "WS:Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void AddFarmerDetailsNew() {

        Log.e("tag", "Location_Response2=" + class_location_dataList.getResponse());
        //  Log.e("tag","UserData_Response2="+class_userDatalist.getResponse());
        String locationData = null, userData = null;
        if (class_location_dataList.getResponse() != null) {
            if (class_location_dataList.getResponse().equalsIgnoreCase("Success")) {
                locationData = "Success";
            } else {
                locationData = "Error";
            }
        }
       /* if(class_userDatalist.getResponse()!=null) {

            if (class_userDatalist.getResponse().equalsIgnoreCase("Success")) {
                userData = "Success";
            }
            else{
                userData = "Error";
            }
        }*/
        Log.e("tag", "Location_Response3=" + locationData);
        Log.e("tag", "UserData_Response3=" + userData);

        String Sync_IDNew = shared_syncId.getString(SyncId, "");
        Log.e("tag", "Sync_IDNew=" + Sync_IDNew);

        sharedpref_spinner_Obj = getSharedPreferences(sharedpreferenc_selectedspinner, Context.MODE_PRIVATE);
        String Sync_IDNew1 = sharedpref_spinner_Obj.getString(Key_syncId, "").trim();
        Log.e("tag", "Sync_IDNew1=" + Sync_IDNew1);

        ValidateSyncRequest request = new ValidateSyncRequest();
        request.setSyncID(Sync_IDNew);
        request.setSyncVersion(versioncode);
        request.setSyncStatus("Success");

        Call<ValidateSyncResponse> call = userService1.Post_ValidateSync(request);

        Log.e("TAG", "Post_ValidateSync Request: " + new Gson().toJson(call.request()));
        Log.e("TAG", "Request Post_ValidateSync: " + request.toString());

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        call.enqueue(new Callback<ValidateSyncResponse>() {
            @Override
            public void onResponse(Call<ValidateSyncResponse> call, Response<ValidateSyncResponse> response) {
                Log.e("response", response.toString());
                Log.e("TAG", "ValidateSyncResponse : " + new Gson().toJson(response));
                Log.e("tag", "ValidateSyncResponse body" + String.valueOf(response.body()));
                //   DefaultResponse error1 = ErrorUtils.parseError(response);
                   /* Log.e("response new:",error1.getMsg());
                    Log.e("response new status:", String.valueOf(error1.getstatus()));*/
                // Log.e("response",Gson.fromJson(response.toString(),AddFarmer_Activity1.class));

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    ValidateSyncResponse class_loginresponse = response.body();
                    Log.e("tag", "res==" + class_loginresponse.toString());
                    Toast.makeText(Activity_HomeScreen.this, "Sync completed successfully", Toast.LENGTH_SHORT).show();

                    if (class_loginresponse.getStatus().equals("true")) {

                    } else if (class_loginresponse.getStatus().equals("false")) {
                        //  progressDoalog.dismiss();
                        Toast.makeText(Activity_HomeScreen.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMsg());

                    // Toast.makeText(Activity_ViewFarmers.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(Activity_HomeScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    public void alerts_dialog_playstoreupdate() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_HomeScreen.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.alert);
        dialog.setMessage("Kindly update from playstore");

        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.det.skillinvillage"));
                startActivity(intent);
            }
        });


        final AlertDialog alert = dialog.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();

    }

    public void alerts_dialog_AutoSyncVersion() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_HomeScreen.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.alert);
        dialog.setMessage("Kindly Re-Sync your data");

        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                if (VersionStatus.equalsIgnoreCase("true")) {
                    internetDectector2 = new Class_InternetDectector(getApplicationContext());
                    isInternetPresent = internetDectector2.isConnectingToInternet();
                    if (isInternetPresent) {

                        //GetDropdownValues();
                        deleteStateRestTable_B4insertion();
                        deleteDistrictRestTable_B4insertion();
                        deleteTalukRestTable_B4insertion();
                        deleteVillageRestTable_B4insertion();
                        deleteYearRestTable_B4insertion();

                        //   deleteStudentdetailsRestTable_B4insertion();
                        deleteSchoolRestTable_B4insertion();
                        deleteSandboxRestTable_B4insertion();
                        deleteInstituteRestTable_B4insertion();
                        deleteLevelRestTable_B4insertion();
                        deleteClusterRestTable_B4insertion();


                        GetDropdownValuesRestData();
                        GetStudentValuesResyncRestData();

                        String Sync_IDNew = shared_syncId.getString(SyncId, "");
                        Log.e("tag", "Sync_IDNew=" + Sync_IDNew);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    internetDectector2 = new Class_InternetDectector(getApplicationContext());
                    isInternetPresent = internetDectector2.isConnectingToInternet();
                    if (isInternetPresent) {
                        CountCheckList();
                        UserStudentDataCountCheckList();
                        stateListRest_dbCount();

                    }
                }
                /*if (isInternetPresent)
                {

                    //working
                    //fetch_DB_farmerprofile_offline_data();

                    *//* fetch_DB_edited_offline_data();*//*
                    // fetch_DB_Edited_farmerprofile_offline_data();
                }*/

               /* Intent i = new Intent(Activity_HomeScreen.this, Activity_ViewFarmers.class);
                i.putExtra("VersionStatus", String.valueOf(str_VersionStatus));
                SharedPreferences.Editor myprefs_spinner = sharedpref_spinner_Obj.edit();
                myprefs_spinner.putString(Key_sel_yearsp, "0");
                myprefs_spinner.putString(Key_sel_statesp, "0");
                myprefs_spinner.putString(Key_sel_districtsp, "0");
                myprefs_spinner.putString(Key_sel_taluksp, "0");
                myprefs_spinner.putString(Key_sel_villagesp, "0");
                myprefs_spinner.putString(Key_sel_grampanchayatsp, "0");

                myprefs_spinner.apply();
                startActivity(i);*/
            }
        });


        final AlertDialog alert = dialog.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();

    }
    ///////////////////////////////////SQLite DB///////////////////////////////////////////////

    public void DBCreate_Helpdetails() {

        SQLiteDatabase db2 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS HelpDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,TitleDB VARCHAR,ContentDB VARCHAR);");
        Cursor cursor = db2.rawQuery("SELECT * FROM HelpDetails_table", null);
        int x = cursor.getCount();
        if (x > 0) {
            db2.delete("HelpDetails_table", null, null);
        }
        db2.close();
    }

    public void DBCreate_HelpDetails_insert_2sqliteDB(String title, String content) {
        SQLiteDatabase db2 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS HelpDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,TitleDB VARCHAR,ContentDB VARCHAR);");

        ContentValues cv = new ContentValues();
        cv.put("TitleDB", title);
        cv.put("ContentDB", content);
        db2.insert("HelpDetails_table", null, cv);
        db2.close();

        Log.e("insert", "DBCreate_HelpDetails_insert_2sqliteDB");

    }

    public void DBCreate_Demodetails() {

        SQLiteDatabase db2 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS DemoDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,LanguageDB VARCHAR,LinkDB VARCHAR);");
        Cursor cursor = db2.rawQuery("SELECT * FROM DemoDetails_table", null);
        int x = cursor.getCount();
        if (x > 0) {
            db2.delete("DemoDetails_table", null, null);
        }
        db2.close();
    }

    public void DBCreate_DemoDetails_insert_2sqliteDB(String str_languagename, String str_languagelink) {
        SQLiteDatabase db2 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS DemoDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,LanguageDB VARCHAR,LinkDB VARCHAR);");

        ContentValues cv = new ContentValues();
        cv.put("LanguageDB", str_languagename);
        cv.put("LinkDB", str_languagelink);
        db2.insert("DemoDetails_table", null, cv);
        db2.close();

        Log.e("insert", "DBCreate_DemoDetails_insert_2sqliteDB");

    }

    public void DBCreate_CountDetailsRest_insert_2SQLiteDB(String str_sCount, String str_dCount, String str_tCount, String str_vCount, String yearCount, String str_Sync_Id, String ClusterCount, String LevelCount, String InstituteCount, String SandboxCount, String SchoolCount) {
        SQLiteDatabase db_locationCount = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_locationCount.execSQL("CREATE TABLE IF NOT EXISTS LocationCountListRest(StateCount VARCHAR,DistrictCount VARCHAR,TalukaCount VARCHAR,VillageCount VARCHAR,YearCount VARCHAR,Sync_ID VARCHAR,ClusterCount VARCHAR,LevelCount VARCHAR,InstituteCount VARCHAR,SandboxCount VARCHAR,SchoolCount VARCHAR);");

        String SQLiteQuery = "INSERT INTO LocationCountListRest (StateCount,DistrictCount,TalukaCount,VillageCount,YearCount,Sync_ID,ClusterCount,LevelCount,InstituteCount,SandboxCount,SchoolCount)" +
                " VALUES ('" + str_sCount + "','" + str_dCount + "','" + str_tCount + "','" + str_vCount + "','" + yearCount + "','" + str_Sync_Id + "','" + ClusterCount + "','" + LevelCount + "','" + InstituteCount + "','" + SandboxCount + "','" + SchoolCount + "');";
        db_locationCount.execSQL(SQLiteQuery);

        Log.e("str_sCount DB", str_sCount);
        Log.e("str_dCount DB", str_dCount);
        Log.e("str_tCount DB", str_tCount);
//        Log.e("str_Sync_Id DB", str_Sync_Id);
        db_locationCount.close();
    }

    public void delete_CountDetailsRestTable_B4insertion() {

        SQLiteDatabase db_locationCount = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_locationCount.execSQL("CREATE TABLE IF NOT EXISTS LocationCountListRest(StateCount VARCHAR,DistrictCount VARCHAR,TalukaCount VARCHAR,VillageCount VARCHAR,YearCount VARCHAR,Sync_ID VARCHAR,ClusterCount VARCHAR,LevelCount VARCHAR,InstituteCount VARCHAR,SandboxCount VARCHAR,SchoolCount VARCHAR);");
        Cursor cursor = db_locationCount.rawQuery("SELECT * FROM LocationCountListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_locationCount.delete("LocationCountListRest", null, null);

        }
        db_locationCount.close();
    }

    public void DBCreate_StudDataCountListRest_insert_2SQLiteDB(String str_StudentCount,String str_ApplicantCount,String str_AdmissionCount) {
        SQLiteDatabase db_userdataCount = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_userdataCount.execSQL("CREATE TABLE IF NOT EXISTS StudDataCountListRest(Student_Count VARCHAR,Applicant_Count VARCHAR,Admission_Count VARCHAR);");

        String SQLiteQuery = "INSERT INTO StudDataCountListRest (Student_Count,Applicant_Count,Admission_Count)" +
                " VALUES ('" + str_StudentCount +  "','" +str_ApplicantCount + "','" +str_AdmissionCount +"');";
        db_userdataCount.execSQL(SQLiteQuery);

        Log.e("str_StudentCount DB", str_StudentCount);

        db_userdataCount.close();
    }

    public void delete_StudDataCountListRestTable_B4insertion() {

        SQLiteDatabase db_userdataCount = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_userdataCount.execSQL("CREATE TABLE IF NOT EXISTS StudDataCountListRest(Student_Count VARCHAR,Applicant_Count VARCHAR,Admission_Count VARCHAR);");
        Cursor cursor = db_userdataCount.rawQuery("SELECT * FROM StudDataCountListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_userdataCount.delete("StudDataCountListRest", null, null);

        }
        db_userdataCount.close();
    }

    public void DBCreate_StatedetailsRest_insert_2SQLiteDB(String str_stateID, String str_statename, String str_yearid, int i) {
        SQLiteDatabase db_statelist = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        //  db_statelist.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR);");
        db_statelist.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");


        if (i == 0) {
            String SQLiteQuery = "INSERT INTO StateListRest (StateID,StateName)" +
                    " VALUES ('" + "0" + "','" + "Select" + "');";
            db_statelist.execSQL(SQLiteQuery);
        }

        String SQLiteQuery = "INSERT INTO StateListRest (StateID,StateName)" +
                " VALUES ('" + str_stateID + "','" + str_statename + "');";
        db_statelist.execSQL(SQLiteQuery);

        Log.e("str_stateID DB", str_stateID);
        Log.e("str_statename DB", str_statename);
        Log.e("str_stateyearid DB", str_yearid);
        db_statelist.close();
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

    public void DBCreate_DistrictdetailsRest_insert_2SQLiteDB(String str_districtID, String str_districtname, String str_yearid, String str_stateid, int i) {
        SQLiteDatabase db_districtlist = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_districtlist.execSQL("CREATE TABLE IF NOT EXISTS DistrictListRest(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");


        if (i == 0) {
            String SQLiteQuery = "INSERT INTO DistrictListRest (DistrictID, DistrictName,Distr_yearid,Distr_Stateid)" +
                    " VALUES ('" + "0" + "','" + "Select" + "','" + "0" + "','" + "0" + "');";
            db_districtlist.execSQL(SQLiteQuery);
        }
        String SQLiteQuery = "INSERT INTO DistrictListRest (DistrictID, DistrictName,Distr_yearid,Distr_Stateid)" +
                " VALUES ('" + str_districtID + "','" + str_districtname + "','" + str_yearid + "','" + str_stateid + "');";
        db_districtlist.execSQL(SQLiteQuery);

//        Log.e("str_districtID DB", str_districtID);
        Log.e("str_districtname DB", str_districtname);
//        Log.e("str_yearid DB", str_yearid);
//        Log.e("str_stateid DB", str_stateid);
        db_districtlist.close();
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

    public void DBCreate_TalukdetailsRest_insert_2SQLiteDB(String str_talukID, String str_talukname, String str_districtid, int i) {

        SQLiteDatabase db_taluklist = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_taluklist.execSQL("CREATE TABLE IF NOT EXISTS TalukListRest(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");


        if (i == 0) {
            String SQLiteQuery = "INSERT INTO TalukListRest (TalukID,TalukName,Taluk_districtid)" +
                    " VALUES ('" + "0" + "','" + "Select" + "','" + "0" + "');";
            db_taluklist.execSQL(SQLiteQuery);
        }

        String SQLiteQuery = "INSERT INTO TalukListRest (TalukID,TalukName,Taluk_districtid)" +
                " VALUES ('" + str_talukID + "','" + str_talukname + "','" + str_districtid + "');";
        db_taluklist.execSQL(SQLiteQuery);

//        Log.e("str_talukID DB", str_talukID);
        Log.e("str_talukname DB", str_talukname);
//        Log.e("str_districtid DB", str_districtid);
        db_taluklist.close();
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

    public void DBCreate_VillagedetailsRest_insert_2SQLiteDB(String str_villageID, String str_village, String str_talukid, int i) {
        SQLiteDatabase db_villagelist = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_villagelist.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");


        if (i == 0) {
            String SQLiteQuery = "INSERT INTO VillageListRest (VillageID, Village,TalukID)" +
                    " VALUES ('" + "0" + "','" + "Select" + "','" + "0" + "');";
            db_villagelist.execSQL(SQLiteQuery);
        }

        String SQLiteQuery = "INSERT INTO VillageListRest (VillageID, Village,TalukID)" +
                " VALUES ('" + str_villageID + "','" + str_village + "','" + str_talukid + "');";
        db_villagelist.execSQL(SQLiteQuery);

//        Log.e("str_villageID DB", str_villageID);
//        Log.e("str_village DB", str_village);
//        Log.e("str_talukid DB", str_talukid);
        db_villagelist.close();
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

    public void DBCreate_YeardetailsRest_insert_2SQLiteDB(String str_yearID, String str_yearname, String Sandbox_ID, int i) {
        SQLiteDatabase db_yearlist = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_yearlist.execSQL("CREATE TABLE IF NOT EXISTS YearListRest(YearID VARCHAR,YearName VARCHAR,Sandbox_ID VARCHAR);");

        if (i == 0) {
            String SQLiteQuery = "INSERT INTO YearListRest (YearID,YearName,Sandbox_ID)" +
                    " VALUES ('" + "0" + "','" + "Select" + "','" + "0" + "');";
            db_yearlist.execSQL(SQLiteQuery);
        }

        String SQLiteQuery = "INSERT INTO YearListRest (YearID, YearName,Sandbox_ID)" +
                " VALUES ('" + str_yearID + "','" + str_yearname + "','" + Sandbox_ID + "');";
        db_yearlist.execSQL(SQLiteQuery);

        Log.e("str_yearname DB", str_yearname);
        db_yearlist.close();
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

    public void DBCreate_ClusterdetailsRest_insert_2SQLiteDB(String ClusterName, String ClusterID, String Clust_AcademicID, String Clust_SandboxID, int i) {
        SQLiteDatabase db_clusterlist = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_clusterlist.execSQL("CREATE TABLE IF NOT EXISTS ClusterListRest(ClusterName VARCHAR,ClusterID VARCHAR,Clust_AcademicID VARCHAR, Clust_SandboxID VARCHAR);");

        if (i == 0) {
            String SQLiteQuery = "INSERT INTO ClusterListRest (ClusterName,ClusterID,Clust_AcademicID, Clust_SandboxID)" +
                    " VALUES ('" + "Select" + "','" + "0" + "','" + "0" + "','" + "0" + "');";
            db_clusterlist.execSQL(SQLiteQuery);
        }
//        else {
//            String SQLiteQuery = "INSERT INTO ClusterListRest (ClusterName,ClusterID,Clust_AcademicID, Clust_SandboxID)" +
//                    " VALUES ('" + ClusterName + "','" + ClusterID + "','" + Clust_AcademicID + "','" + Clust_SandboxID + "');";
//            db_clusterlist.execSQL(SQLiteQuery);
//        }

        String SQLiteQuery = "INSERT INTO ClusterListRest (ClusterName,ClusterID,Clust_AcademicID, Clust_SandboxID)" +
                " VALUES ('" + ClusterName + "','" + ClusterID + "','" + Clust_AcademicID + "','" + Clust_SandboxID + "');";
        db_clusterlist.execSQL(SQLiteQuery);


        Log.e("ClusterName DB", ClusterName);
        db_clusterlist.close();
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

    public void DBCreate_InstitutedetailsRest_insert_2SQLiteDB(String InstituteName, String InstituteID, String Inst_AcademicID, String Inst_ClusterID, int i) {
        SQLiteDatabase db_Institutelist = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_Institutelist.execSQL("CREATE TABLE IF NOT EXISTS InstituteListRest(InstituteName VARCHAR, InstituteID VARCHAR,Inst_AcademicID VARCHAR,Inst_ClusterID VARCHAR);");

        if (i == 0) {
            String SQLiteQuery = "INSERT INTO InstituteListRest (InstituteName, InstituteID, Inst_AcademicID,Inst_ClusterID)" +
                    " VALUES ('" + "Select" + "','" + "0" + "','" + "0" + "','" + "0" + "');";
            db_Institutelist.execSQL(SQLiteQuery);
        }
        //else {


        String SQLiteQuery = "INSERT INTO InstituteListRest (InstituteName, InstituteID, Inst_AcademicID,Inst_ClusterID)" +
                " VALUES ('" + InstituteName + "','" + InstituteID + "','" + Inst_AcademicID + "','" + Inst_ClusterID + "');";
        db_Institutelist.execSQL(SQLiteQuery);
        // }
        Log.e("InstituteName DB", InstituteName);
        db_Institutelist.close();
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

    public void DBCreate_LeveldetailsRest_insert_2SQLiteDB(String LevelName, String LevelID, String Level_InstituteID, String Level_AcademicID, String Level_ClusterID, String Level_AdmissionFee, int i) {
        SQLiteDatabase db_Levellist = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_Levellist.execSQL("CREATE TABLE IF NOT EXISTS LevelListRest(LevelName VARCHAR, LevelID VARCHAR, Level_InstituteID VARCHAR,Level_AcademicID VARCHAR,Level_ClusterID VARCHAR,Level_AdmissionFee VARCHAR);");

        if (i == 0) {
            String SQLiteQuery = "INSERT INTO LevelListRest (LevelName,LevelID,Level_InstituteID,Level_AcademicID,Level_ClusterID,Level_AdmissionFee)" +
                    " VALUES ('" + "Select" + "','" + "0" + "','" + "0" + "','" + "0" + "','" + "0" + "','" + "0" + "');";
            db_Levellist.execSQL(SQLiteQuery);
        }

        //else {
        String SQLiteQuery = "INSERT INTO LevelListRest (LevelName, LevelID, Level_InstituteID,Level_AcademicID,Level_ClusterID,Level_AdmissionFee)" +
                " VALUES ('" + LevelName + "','" + LevelID + "','" + Level_InstituteID + "','" + Level_AcademicID + "','" + Level_ClusterID + "','" + Level_AdmissionFee + "');";
        db_Levellist.execSQL(SQLiteQuery);
        // }
        Log.e("Level_AdmissionFee DB", Level_AdmissionFee);
        db_Levellist.close();
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

    public void DBCreate_AssementRest_insert_2SQLiteDB(String str_AssementID, String str_Assementname, int i) {
        SQLiteDatabase db_yearlist = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_yearlist.execSQL("CREATE TABLE IF NOT EXISTS AssementListRest(AssementID VARCHAR,AssementName VARCHAR);");

        String SQLiteQuery = "INSERT INTO AssementListRest (AssementID, AssementName)" +
                " VALUES ('" + str_AssementID + "','" + str_Assementname + "');";
        db_yearlist.execSQL(SQLiteQuery);

        // Log.e("str_Assementname DB", str_Assementname);
        db_yearlist.close();
    }

    public void deleteAssementRestTable_B4insertion() {

        SQLiteDatabase db_yearlist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_yearlist_delete.execSQL("CREATE TABLE IF NOT EXISTS AssementListRest(AssementID VARCHAR,AssementName VARCHAR);");
        Cursor cursor = db_yearlist_delete.rawQuery("SELECT * FROM AssementListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_yearlist_delete.delete("AssementListRest", null, null);

        }
        db_yearlist_delete.close();
    }

    public void DBCreate_LearningModeRest_insert_2SQLiteDB(String str_LearningModeID, String str_LearningModename, int i) {
        SQLiteDatabase db_yearlist = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_yearlist.execSQL("CREATE TABLE IF NOT EXISTS LearningModeListRest(LearningModeID VARCHAR,LearningModeName VARCHAR);");

        String SQLiteQuery = "INSERT INTO LearningModeListRest (LearningModeID, LearningModeName)" +
                " VALUES ('" + str_LearningModeID + "','" + str_LearningModename + "');";
        db_yearlist.execSQL(SQLiteQuery);

        Log.e("str_LearningModename DB", str_LearningModename);
        db_yearlist.close();
    }

    public void deleteLearningModeRestTable_B4insertion() {

        SQLiteDatabase db_yearlist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_yearlist_delete.execSQL("CREATE TABLE IF NOT EXISTS LearningModeListRest(LearningModeID VARCHAR,LearningModeName VARCHAR);");
        Cursor cursor = db_yearlist_delete.rawQuery("SELECT * FROM LearningModeListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_yearlist_delete.delete("LearningModeListRest", null, null);

        }
        db_yearlist_delete.close();
    }

    public void DBCreate_EducationRest_insert_2SQLiteDB(String str_EducationID, String str_Educationname, int i) {
        SQLiteDatabase db_yearlist = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_yearlist.execSQL("CREATE TABLE IF NOT EXISTS EducationListRest(EducationID VARCHAR,EducationName VARCHAR);");

        String SQLiteQuery = "INSERT INTO EducationListRest (EducationID, EducationName)" +
                " VALUES ('" + str_EducationID + "','" + str_Educationname + "');";
        db_yearlist.execSQL(SQLiteQuery);

        Log.e("str_Educationname DB", str_Educationname);
        db_yearlist.close();
    }

    public void deleteEducationRestTable_B4insertion() {

        SQLiteDatabase db_yearlist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_yearlist_delete.execSQL("CREATE TABLE IF NOT EXISTS EducationListRest(EducationID VARCHAR,EducationName VARCHAR);");
        Cursor cursor = db_yearlist_delete.rawQuery("SELECT * FROM EducationListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_yearlist_delete.delete("EducationListRest", null, null);

        }
        db_yearlist_delete.close();
    }


    public void DBCreate_SandboxdetailsRest_insert_2SQLiteDB(String SandboxName, String SandboxID, int i) {
        SQLiteDatabase db_Sandboxlist = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_Sandboxlist.execSQL("CREATE TABLE IF NOT EXISTS SandboxListRest(SandboxName VARCHAR,SandboxID VARCHAR);");

        if (i == 0) {
            String SQLiteQuery = "INSERT INTO SandboxListRest (SandboxName, SandboxID)" +
                    " VALUES ('" + "Select" + "','" + "0" + "');";
            db_Sandboxlist.execSQL(SQLiteQuery);
        }
//        else {
//            String SQLiteQuery = "INSERT INTO SandboxListRest (SandboxName, SandboxID)" +
//                    " VALUES ('" + SandboxName + "','" + SandboxID + "');";
//            db_Sandboxlist.execSQL(SQLiteQuery);
//        }

        String SQLiteQuery = "INSERT INTO SandboxListRest (SandboxName, SandboxID)" +
                " VALUES ('" + SandboxName + "','" + SandboxID + "');";
        db_Sandboxlist.execSQL(SQLiteQuery);

        Log.e("SandboxName DB", SandboxName);
        db_Sandboxlist.close();
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

    public void DBCreate_SchooldetailsRest_insert_2SQLiteDB(String SchoolName, String SchoolID, String School_InstituteID, int i) {
        SQLiteDatabase db_Schoollist = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_Schoollist.execSQL("CREATE TABLE IF NOT EXISTS SchoolListRest(SchoolName VARCHAR, SchoolID VARCHAR, School_InstituteID VARCHAR);");
        if (i == 0) {
            String SQLiteQuery = "INSERT INTO SchoolListRest (SchoolName, SchoolID, School_InstituteID)" +
                    " VALUES ('" + "Select" + "','" + "0" + "','" + "0" + "');";
            db_Schoollist.execSQL(SQLiteQuery);
        }
        //else {
        String SQLiteQuery = "INSERT INTO SchoolListRest (SchoolName, SchoolID, School_InstituteID)" +
                " VALUES ('" + SchoolName + "','" + SchoolID + "','" + School_InstituteID + "');";
        db_Schoollist.execSQL(SQLiteQuery);
        // }
        Log.e("SchoolName DB", SchoolName);
        db_Schoollist.close();
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

    public void DBCreate_StudentdetailsRest_insert_2SQLiteDB(String AcademicID, String AcademicName, String AdmissionFee, String ApplicationNo, String BalanceFee, String BirthDate, String ClusterID,
                                                             String ClusterName, String CreatedDate, String Education, String FatherName, String Gender, String InstituteName, String InstituteID, String LevelID, String LevelName, String Marks4, String Marks5, String Marks6, String Marks7, String Marks8,
                                                             String Mobile, String MotherName, String PaidFee, String ReceiptNo, String SandboxID, String SandboxName, String SchoolID, String SchoolName, String StudentAadhar, String StudentID, String StudentName, String StudentPhoto, String StudentStatus, String str_base64image, String Stud_TempId, String str_online, String str_learningmode, String str_stateid, String str_statename, String str_districtid, String str_districtname, String str_talukid, String str_talukname, String str_villageid, String str_villagename, String str_stuaddress,String str_alternatemobno) {
        SQLiteDatabase db_studentDetails = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_studentDetails.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR," +
                "ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR," +
                "Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");

        String SQLiteQuery = "INSERT INTO StudentDetailsRest (AcademicID, AcademicName, AdmissionFee,ApplicationNo,BalanceFee,BirthDate,ClusterID," +
                "ClusterName,CreatedDate,Education,FatherName,Gender,InstituteName,InstituteID,LevelID,LevelName,Marks4,Marks5,Marks6,Marks7,Marks8," +
                "Mobile,MotherName,PaidFee,ReceiptNo,SandboxID,SandboxName,SchoolID,SchoolName,StudentAadhar,StudentID,StudentName,StudentPhoto,StudentStatus,Base64image,Stud_TempId,UpadateOff_Online,Learning_Mode,stateid,statename,districtid,districtname,talukid,talukname,villageid,villagename,student_address,alternate_mobile,admission_date,admission_remarks)" +
                " VALUES ('" + AcademicID + "','" + AcademicName + "','" + AdmissionFee + "','" + ApplicationNo + "','" + BalanceFee + "','" + BirthDate + "','" + ClusterID + "','" +
                ClusterName + "','" + CreatedDate + "','" + Education + "','" + FatherName + "','" + Gender + "','" + InstituteName + "','" + InstituteID + "','" + LevelID + "','" + LevelName + "','" + Marks4 + "','" + Marks5 + "','" + Marks6 + "','" + Marks7 + "','" + Marks8 + "','" +
                Mobile + "','" + MotherName + "','" + PaidFee + "','" + ReceiptNo + "','" + SandboxID + "','" + SandboxName + "','" + SchoolID + "','" + SchoolName + "','" + StudentAadhar + "','" + StudentID + "','" + StudentName + "','" + StudentPhoto + "','" + StudentStatus + "','" + str_base64image + "','" + Stud_TempId + "','" + str_online + "','" + str_learningmode + "','" + str_stateid + "','" + str_statename + "','" + str_districtid + "','" + str_districtname + "','" + str_talukid + "','" + str_talukname + "','" + str_villageid + "','" + str_villagename + "','" + str_stuaddress + "','" + str_alternatemobno + "','" + "" + "','" + "" +  "');";
        db_studentDetails.execSQL(SQLiteQuery);

        // Log.e("ApplicationNo DB", ApplicationNo);
        Log.e("StudentName DB", StudentName);
        Log.e("SandboxName DB", SandboxName);
//
        db_studentDetails.close();
    }

    public void deleteStudentdetailsRestTable_B4insertion() {

        SQLiteDatabase db_studentDetails = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_studentDetails.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR,AcademicName VARCHAR,AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR," +
                "ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR," +
                "Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR,Base64image VARCHAR,Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");
        @SuppressLint("Recycle")
        Cursor cursor = db_studentDetails.rawQuery("SELECT * FROM StudentDetailsRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_studentDetails.delete("StudentDetailsRest", null, null);

            Log.e("StudentDetailsRest", "deleted table successfully");
        }
        db_studentDetails.close();
        Log.e("deleted", "deleteStudentdetailsRestTable_B4insertion");

    }


    //////////////////////////////////////////////////////////////////////////////////////////


    public class OnlineView_Feedback extends AsyncTask<String, Void, Void> {
        Context context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            list_detaile();
            //return HttpULRConnect.getData(url);
            return null;
        }

        public OnlineView_Feedback(Context context1) {
            context = context1;
            //  dialog = new ProgressDialog(context1,R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected void onPostExecute(Void s) {
            if (str_feedback.toString().equalsIgnoreCase("") || str_feedback.toString().equalsIgnoreCase("anyType{}") || str_feedback.toString().equalsIgnoreCase(null)) {
                onlineview_iv.setVisibility(View.GONE);
                Log.e("tag", "API-Gone");
                SharedPreferences.Editor editor = sharedpref_feedback.edit();
                editor.putString(FeedBack, "Gone");
                editor.commit();
            } else {
                onlineview_iv.setVisibility(View.VISIBLE);
                Log.e("tag", "API-Visible");
                SharedPreferences.Editor editor = sharedpref_feedback.edit();
                editor.putString(FeedBack, "Visible");
                editor.commit();
            }
            //populateExpandableList();
//            MyAdapter   adapter = new MyAdapter(mainmenulist, Onlineview_Navigation.this);
//            expandableListView.setAdapter(adapter);

        }

    }

    public void list_detaile() {
        Vector<SoapObject> result1 = null;
        String url = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "LoadMobileMenu";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/LoadMobileMenu";

        try {
            int userid = Integer.parseInt(str_loginuserID);
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("User_ID", userid);//userid


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(url);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();

                Log.i("value at response", response.toString());
                if (response.toString().equalsIgnoreCase("anyType{}") || response.toString().equalsIgnoreCase("") || response.toString().equalsIgnoreCase(null)) {
                    str_feedback = "";
                }
                /*if (response.toString().equalsIgnoreCase("anyType{}") || response.toString().equalsIgnoreCase("") || response.toString().equalsIgnoreCase(null)) {
                    onlineview_iv.setVisibility(View.GONE);
                    Log.e("tag","API-Gone");
                    SharedPreferences.Editor editor = sharedpref_feedback.edit();
                    editor.putString(FeedBack, "Gone");
                    editor.commit();
                }else {
                    onlineview_iv.setVisibility(View.VISIBLE);
                    Log.e("tag","API-Visible");
                    SharedPreferences.Editor editor = sharedpref_feedback.edit();
                    editor.putString(FeedBack, "Visible");
                    editor.commit();
                }*/

            } catch (Throwable t) {
                Log.e("requestload fail", "> " + t.getMessage());
            }

        } catch (Throwable t) {
            Log.e("UnRegisterload  Error", "> " + t.getMessage());

        }


    }//End of leaveDetail method

    public void GetStudentValuesResyncRestData() {

        Call<Student> call = userService1.getStudentDataReSync(str_loginuserID);//str_loginuserID
        //  Call<Location_Data> call = userService1.getLocationData("90");
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                Log.e("Entered resp", response.message());
                //     Log.e("Entered resp", response.body().getMessage());
                AddStudentDetailsNew();
                if (response.isSuccessful()) {
                    Student class_studentData = response.body();
                    Log.e("response.body", response.body().getLstCount1().toString());
                    if (class_studentData.getStatus().equals(true)) {
                        List<StudCountList> studentcountLists = response.body().getLstCount1();
                        Log.e("tag", "studentlist.size()=" + String.valueOf(studentcountLists.size()));

                        studCountLists = new StudCountList[studentcountLists.size()];
                        for (int i1 = 0; i1 < studCountLists.length; i1++) {


                            // Toast.makeText(getContext(), "" + class_monthCounts.getMessage(), Toast.LENGTH_SHORT).show();
                            class_studCountLists.setCount(class_studentData.getLstCount1().get(i1).getCount());
                            class_studCountLists.setStudents(class_studentData.getLstCount1().get(i1).getStudents());
                            class_studCountLists.setResponse(class_studentData.getLstCount1().get(i1).getResponse());
                            int sizeCount = 0;
                            if (class_studentData.getLstCount1().get(i1).getCount() != null) {
                                sizeCount = class_studentData.getLstCount1().get(i1).getCount().size();
                            }
                            for (int j = 0; j < sizeCount; j++) {
                                String Student_Count = class_studentData.getLstCount1().get(i1).getCount().get(j).getStudentCount();
                                 applicant_Count = class_studentData.getLstCount1().get(i1).getCount().get(j).getApplicantCount();
                                 admission_Count = class_studentData.getLstCount1().get(i1).getCount().get(j).getAdmissionCount();
                                Log.e("applicant_Count", String.valueOf(applicant_Count));
                                Log.e("admission_Count", String.valueOf(admission_Count));

                                DBCreate_StudDataCountListRest_insert_2SQLiteDB(Student_Count,applicant_Count,admission_Count);
                            }
                            int sizeStudList = 0;
                            if (class_studentData.getLstCount1().get(i1).getStudents() != null) {
                                sizeStudList = class_studentData.getLstCount1().get(i1).getStudents().size();
                            }
                            for (int i = 0; i < sizeStudList; i++) {


                                Log.e("status", String.valueOf(class_StudentList.getStudentStatus()));
                           /* Log.e("msg", class_loginresponse.getMessage());
                            Log.e("list", class_loginresponse.getList().get(i).getId());
                            Log.e("list", class_loginresponse.getList().get(i).getProgramCode());
                            Log.e("size", String.valueOf(class_loginresponse.getList().size()));*/
                                String Stud_TempId ="";
                                String AcademicID = String.valueOf(class_studentData.getLstCount1().get(i1).getStudents().get(i).getAcademicID());
                                String AcademicName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getAcademicName();
                                String AdmissionFee = class_studentData.getLstCount1().get(i1).getStudents().get(i).getAdmissionFee();
                                String ApplicationNo = class_studentData.getLstCount1().get(i1).getStudents().get(i).getApplicationNo();
                                String BalanceFee = class_studentData.getLstCount1().get(i1).getStudents().get(i).getBalanceFee();
                                String BirthDate = class_studentData.getLstCount1().get(i1).getStudents().get(i).getBirthDate();
                                String ClusterID = String.valueOf(class_studentData.getLstCount1().get(i1).getStudents().get(i).getClusterID());
                                String ClusterName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getClusterName();
                                String CreatedDate = class_studentData.getLstCount1().get(i1).getStudents().get(i).getCreatedDate();
                                String Education = class_studentData.getLstCount1().get(i1).getStudents().get(i).getEducation();
                                String FatherName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getFatherName();
                                String Gender = class_studentData.getLstCount1().get(i1).getStudents().get(i).getGender();
                                String InstituteName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getInstituteName();
                                String InstituteID = String.valueOf(class_studentData.getLstCount1().get(i1).getStudents().get(i).getInstituteID());
                                String LevelID = String.valueOf(class_studentData.getLstCount1().get(i1).getStudents().get(i).getLevelID());
                                String LevelName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getLevelName();
                                String Marks4 = class_studentData.getLstCount1().get(i1).getStudents().get(i).getMarks4();
                                String Marks5 = class_studentData.getLstCount1().get(i1).getStudents().get(i).getMarks5();
                                String Marks6 = class_studentData.getLstCount1().get(i1).getStudents().get(i).getMarks6();
                                String Marks7 = class_studentData.getLstCount1().get(i1).getStudents().get(i).getMarks7();
                                String Marks8 = class_studentData.getLstCount1().get(i1).getStudents().get(i).getMarks8();
                                String Mobile = class_studentData.getLstCount1().get(i1).getStudents().get(i).getMobile();
                                String MotherName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getMotherName();
                                String PaidFee = class_studentData.getLstCount1().get(i1).getStudents().get(i).getPaidFee();
                                String ReceiptNo = class_studentData.getLstCount1().get(i1).getStudents().get(i).getReceiptNo();
                                String SandboxID = String.valueOf(class_studentData.getLstCount1().get(i1).getStudents().get(i).getSandboxID());
                                String SandboxName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getSandboxName();
                                String SchoolID = String.valueOf(class_studentData.getLstCount1().get(i1).getStudents().get(i).getSchoolID());
                                String SchoolName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getSchoolName();
                                String StudentAadhar = class_studentData.getLstCount1().get(i1).getStudents().get(i).getStudentAadhar();
                                String StudentID = class_studentData.getLstCount1().get(i1).getStudents().get(i).getStudentID();
                                String StudentName = class_studentData.getLstCount1().get(i1).getStudents().get(i).getStudentName();
                                String StudentPhoto = class_studentData.getLstCount1().get(i1).getStudents().get(i).getStudentPhoto();
                                String StudentStatus = class_studentData.getLstCount1().get(i1).getStudents().get(i).getStudentStatus();
                              //  String Stud_TempId = class_studentData.getLstCount1().get(i1).getStudents().get(i).getTempID();

                                try{
                                    if(class_studentData.getLstCount1().get(i1).getStudents().get(i).getTempID().equals("null")){
                                    Stud_TempId = "";

                                }else {
                                    Stud_TempId = class_studentData.getLstCount1().get(i1).getStudents().get(i).getTempID();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                                String Learning_Mode = class_studentData.getLstCount1().get(i1).getStudents().get(i).getLearningMode();

                                String str_stateid = class_studentData.getLstCount1().get(i1).getStudents().get(i).getState_ID();
                                String str_statename = class_studentData.getLstCount1().get(i1).getStudents().get(i).getState_Name();
                                String str_distid = class_studentData.getLstCount1().get(i1).getStudents().get(i).getDistrict_ID();
                                String str_distname = class_studentData.getLstCount1().get(i1).getStudents().get(i).getDistrict_Name();
                                String str_talukid = class_studentData.getLstCount1().get(i1).getStudents().get(i).getTaluk_ID();
                                String str_talukname = class_studentData.getLstCount1().get(i1).getStudents().get(i).getTaluk_Name();
                                String str_villageid = class_studentData.getLstCount1().get(i1).getStudents().get(i).getVillage_ID();
                                String str_villagename = class_studentData.getLstCount1().get(i1).getStudents().get(i).getVillage_Name();
                                String str_address = class_studentData.getLstCount1().get(i1).getStudents().get(i).getAddress();
                                String str_alternatemobno = class_studentData.getLstCount1().get(i1).getStudents().get(i).getAlternate_Mobile();
                                Log.e("tag", "StudentName=" + StudentName + "StudentStatus=" + StudentStatus + "AcademicName=" + AcademicName);

                                String str_imageurl = StudentPhoto;
                                Log.e("tag", "str_imageurl==" + str_imageurl);
                                String str_base64image = null;
                                if (str_imageurl == null || str_imageurl.equals("") || str_imageurl.equals("0")) {
                                } else {
                                    //  str_imageurl=class_farmerlistdetails_arrayobj2[i].getFarmerPhoto();

                                    String str_farmpondimageurl = str_imageurl;

                                    InputStream inputstream_obj = null;
                                    try {
                                        if (android.os.Build.VERSION.SDK_INT > 9) {
                                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                            StrictMode.setThreadPolicy(policy);
                                            inputstream_obj = new URL(str_farmpondimageurl).openStream();

                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Bitmap mIcon12 = BitmapFactory.decodeStream(inputstream_obj);
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    mIcon12.compress(Bitmap.CompressFormat.PNG, 60, baos);
                                    byte[] b = baos.toByteArray();
                                    str_base64image = Base64.encodeToString(b, Base64.DEFAULT);

                                    Log.e("tag", "byteArray img=" + b);

                                    //  str_base64image1 = str_base64image;
                                }

                                DBCreate_StudentResyncdetailsRest_insert_2SQLiteDB(AcademicID,AcademicName,AdmissionFee, ApplicationNo, BalanceFee, BirthDate, ClusterID, ClusterName, CreatedDate, Education, FatherName, Gender, InstituteName, InstituteID, LevelID, LevelName, Marks4, Marks5, Marks6, Marks7, Marks8, Mobile, MotherName, PaidFee, ReceiptNo, SandboxID, SandboxName, SchoolID, SchoolName, StudentAadhar, StudentID, StudentName, StudentPhoto, StudentStatus, str_base64image, Stud_TempId, "online", Learning_Mode, str_stateid, str_statename, str_distid, str_distname, str_talukid, str_talukname, str_villageid, str_villagename,str_address,str_alternatemobno);


                            }
                        }

                        /*uploadfromDB_Yearlist();
                        uploadfromDB_Statelist();
                        uploadfromDB_Districtlist();
                        uploadfromDB_Taluklist();
                        uploadfromDB_Villagelist();
                        uploadfromDB_Grampanchayatlist();*/
                    } else {
                        Toast.makeText(Activity_HomeScreen.this, class_studentData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDoalog.dismiss();
                    Log.e("tag", "working");
                } else {
                    progressDoalog.dismiss();
                    Log.e("Entered resp else", "");
                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.e("error message", error.getMsg());

                    Toast.makeText(Activity_HomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

                Log.e("tag", t.getMessage());
                Toast.makeText(Activity_HomeScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }

    private void AddStudentDetailsNew() {

        Log.e("tag", "Location_Response2=" + class_location_dataList.getResponse());
        //  Log.e("tag","UserData_Response2="+class_userDatalist.getResponse());
        String locationData = null, userData = null;
        if (class_location_dataList.getResponse() != null) {
            if (class_location_dataList.getResponse().equalsIgnoreCase("Success")) {
                locationData = "Success";
            } else {
                locationData = "Error";
            }
        }
        if (class_studCountLists.getResponse() != null) {

            if (class_studCountLists.getResponse().equalsIgnoreCase("Success")) {
                userData = "Success";
            } else {
                userData = "Error";
            }
        }
        Log.e("tag", "Location_Response3=" + locationData);
        Log.e("tag", "UserData_Response3=" + userData);

        String Sync_IDNew = shared_syncId.getString(SyncId, "");
        Log.e("tag", "Sync_IDNew=" + Sync_IDNew);

        sharedpref_spinner_Obj = getSharedPreferences(sharedpreferenc_selectedspinner, Context.MODE_PRIVATE);
        String Sync_IDNew1 = sharedpref_spinner_Obj.getString(Key_syncId, "").trim();
        Log.e("tag", "Sync_IDNew1=" + Sync_IDNew1);

        ValidateSyncRequest request = new ValidateSyncRequest();
        request.setSyncID(Sync_IDNew);
        request.setSyncVersion(versioncode);
        request.setSyncStatus("Success");
        /*request.setSyncID("1129");
        request.setSyncVersion("8");
        request.setSyncStatus("Success");*/
        Log.e("tag", "Sync_ID=" + Sync_IDNew + "versioncode=" + versioncode + "SyncStatus=Success");
        Call<ValidateSyncResponse> call = userService1.Post_ValidateSync(request);

        Log.e("TAG", "Post_ValidateSync Request: " + new Gson().toJson(call.request()));
        Log.e("TAG", "Request Post_ValidateSync: " + request.toString());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(Activity_HomeScreen.this);
        progressDialog.setMessage("Loading....");
        progressDialog.setTitle("Please wait....");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        call.enqueue(new Callback<ValidateSyncResponse>() {
            @Override
            public void onResponse(Call<ValidateSyncResponse> call, Response<ValidateSyncResponse> response) {
                Log.e("response", response.toString());
                Log.e("TAG", "ValidateSyncResponse : " + new Gson().toJson(response));
                Log.e("tag", "ValidateSyncResponse body" + String.valueOf(response.body()));
                //   DefaultResponse error1 = ErrorUtils.parseError(response);
                   /* Log.e("response new:",error1.getMsg());
                    Log.e("response new status:", String.valueOf(error1.getstatus()));*/
                // Log.e("response",Gson.fromJson(response.toString(),AddFarmer_Activity1.class));

                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    ValidateSyncResponse class_loginresponse = response.body();
                    Log.e("tag", "res==" + class_loginresponse.toString());
                    Toast.makeText(Activity_HomeScreen.this, "Sync completed successfully", Toast.LENGTH_SHORT).show();

                    if (class_loginresponse.getStatus().equals("true")) {

                    } else if (class_loginresponse.getStatus().equals("false")) {
                        //  progressDoalog.dismiss();
                        //       Toast.makeText(Activity_HomeScreen.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressDialog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMsg());

                    // Toast.makeText(Activity_ViewFarmers.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                //    Toast.makeText(Activity_HomeScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }

    public void DBCreate_StudentResyncdetailsRest_insert_2SQLiteDB(String AcademicID, String AcademicName, String AdmissionFee, String ApplicationNo, String BalanceFee, String BirthDate, String ClusterID,
                                                                   String ClusterName, String CreatedDate, String Education, String FatherName, String Gender, String InstituteName, String InstituteID, String LevelID, String LevelName, String Marks4, String Marks5, String Marks6, String Marks7, String Marks8,
                                                                   String Mobile, String MotherName, String PaidFee, String ReceiptNo, String SandboxID, String SandboxName, String SchoolID, String SchoolName, String StudentAadhar, String StudentID, String StudentName, String StudentPhoto, String StudentStatus, String str_base64image, String Stud_TempId, String str_online_offline, String Learning_Mode, String str_stateid, String str_statename, String str_districtid, String str_districtname, String str_talukid, String str_talukname, String str_villageid, String str_villagename, String str_stuaddress,String str_alternatemobileno) {
        SQLiteDatabase db_studentDetails = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);

        @SuppressLint("Recycle")
        Cursor cursor = db_studentDetails.rawQuery("SELECT * FROM StudentDetailsRest WHERE StudentID='" + StudentID + "'", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_studentDetails.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR," +
                    "ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR," +
                    "Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");

            ContentValues cv_studentlistupdate = new ContentValues();
            cv_studentlistupdate.put("AcademicID", AcademicID);
            cv_studentlistupdate.put("AcademicName", AcademicName);
            cv_studentlistupdate.put("AdmissionFee", AdmissionFee);
            cv_studentlistupdate.put("ApplicationNo", ApplicationNo);
            cv_studentlistupdate.put("BalanceFee", BalanceFee);
            cv_studentlistupdate.put("BirthDate", BirthDate);
            cv_studentlistupdate.put("ClusterID", ClusterID);
            cv_studentlistupdate.put("ClusterName", ClusterName);
            cv_studentlistupdate.put("CreatedDate", CreatedDate);
            cv_studentlistupdate.put("Education", Education);
            cv_studentlistupdate.put("FatherName", FatherName);
            cv_studentlistupdate.put("Gender", Gender);
            cv_studentlistupdate.put("InstituteName", InstituteName);
            cv_studentlistupdate.put("InstituteID", InstituteID);
            cv_studentlistupdate.put("LevelID", LevelID);
            cv_studentlistupdate.put("LevelName", LevelName);
            cv_studentlistupdate.put("Marks4", Marks4);
            cv_studentlistupdate.put("Marks5", Marks5);
            cv_studentlistupdate.put("Marks6", Marks6);
            cv_studentlistupdate.put("Marks7", Marks7);
            cv_studentlistupdate.put("Marks8", Marks8);
            cv_studentlistupdate.put("Mobile", Mobile);
            cv_studentlistupdate.put("MotherName", MotherName);
            cv_studentlistupdate.put("PaidFee", PaidFee);
            cv_studentlistupdate.put("ReceiptNo", ReceiptNo);
            cv_studentlistupdate.put("SandboxID", SandboxID);
            cv_studentlistupdate.put("SandboxName", SandboxName);
            cv_studentlistupdate.put("SchoolID", SchoolID);
            cv_studentlistupdate.put("StudentAadhar", StudentAadhar);
            cv_studentlistupdate.put("StudentName", StudentName);
            cv_studentlistupdate.put("StudentPhoto", StudentPhoto);
            cv_studentlistupdate.put("StudentStatus", StudentStatus);
            cv_studentlistupdate.put("Base64image", str_base64image);
            cv_studentlistupdate.put("Stud_TempId", Stud_TempId);
            cv_studentlistupdate.put("UpadateOff_Online", str_online_offline);
            cv_studentlistupdate.put("Learning_Mode", Learning_Mode);
            cv_studentlistupdate.put("stateid", str_stateid);
            cv_studentlistupdate.put("statename", str_statename);
            cv_studentlistupdate.put("districtid", str_districtid);
            cv_studentlistupdate.put("districtname", str_districtname);
            cv_studentlistupdate.put("talukid", str_talukid);
            cv_studentlistupdate.put("talukname", str_talukname);
            cv_studentlistupdate.put("villageid", str_villageid);
            cv_studentlistupdate.put("villagename", str_villagename);
            cv_studentlistupdate.put("student_address", str_stuaddress);
//alternate_mobile VARCHAR
          //  admission_date VARCHAR,admission_remarks VARCHAR
            cv_studentlistupdate.put("admission_date", "");
            cv_studentlistupdate.put("admission_remarks", "");

            cv_studentlistupdate.put("alternate_mobile", str_alternatemobileno);

            //   Log.e("ApplicationNo..updating..",ApplicationNo);
            db_studentDetails.update("StudentDetailsRest", cv_studentlistupdate, "StudentID = ?", new String[]{StudentID});
            db_studentDetails.close();
        } else {
            db_studentDetails.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR," +
                    "ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR," +
                    "Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");

            String SQLiteQuery = "INSERT INTO StudentDetailsRest (AcademicID, AcademicName, AdmissionFee,ApplicationNo,BalanceFee,BirthDate,ClusterID," +
                    "ClusterName,CreatedDate,Education,FatherName,Gender,InstituteName,InstituteID,LevelID,LevelName,Marks4,Marks5,Marks6,Marks7,Marks8," +
                    "Mobile,MotherName,PaidFee,ReceiptNo,SandboxID,SandboxName,SchoolID,SchoolName,StudentAadhar,StudentID,StudentName,StudentPhoto,StudentStatus,Base64image,Stud_TempId,UpadateOff_Online,Learning_Mode,stateid,statename,districtid,districtname,talukid,talukname,villageid,villagename,student_address,alternate_mobile,admission_date,admission_remarks)" +
                    " VALUES ('" + AcademicID + "','" + AcademicName + "','" + AdmissionFee + "','" + ApplicationNo + "','" + BalanceFee + "','" + BirthDate + "','" + ClusterID + "','" +
                    ClusterName + "','" + CreatedDate + "','" + Education + "','" + FatherName + "','" + Gender + "','" + InstituteName + "','" + InstituteID + "','" + LevelID + "','" + LevelName + "','" + Marks4 + "','" + Marks5 + "','" + Marks6 + "','" + Marks7 + "','" + Marks8 + "','" +
                    Mobile + "','" + MotherName + "','" + PaidFee + "','" + ReceiptNo + "','" + SandboxID + "','" + SandboxName + "','" + SchoolID + "','" + SchoolName + "','" + StudentAadhar + "','" + StudentID + "','" + StudentName + "','" + StudentPhoto + "','" + StudentStatus + "','" + str_base64image + "','" + Stud_TempId + "','" + "online" + "','" + Learning_Mode + "','" + str_stateid + "','" + str_statename + "','" + str_districtid + "','" + str_districtname + "','" + str_talukid + "','" + str_talukname + "','" + str_villageid + "','" + str_villagename + "','" + str_stuaddress +  "','" + str_alternatemobileno + "','" + "" + "','" + "" +"');";
            db_studentDetails.execSQL(SQLiteQuery);

            // Log.e("ApplicationNoinserting", ApplicationNo);
            Log.e("StudentName DB", StudentName);
            Log.e("SandboxName DB", SandboxName);

            db_studentDetails.close();
        }
    }


    public void GetMobileMenu() {

        Call<GetMobileMenuResponse> call = userService1.GetMobileMenu(str_loginuserID);//String.valueOf(str_stuID)
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
        //  progressDoalog.setMax(100);
        //  progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<GetMobileMenuResponse>() {
            @Override
            public void onResponse(Call<GetMobileMenuResponse> call, Response<GetMobileMenuResponse> response) {
                Log.e("Entered resp", "GetMobileMenu");

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    GetMobileMenuResponse class_loginresponse = response.body();
                    if (class_loginresponse.getStatus()) {

                        List<GetMobileMenuResponseList> monthContents_list = response.body().getObjMenu();
                        int loadPendingPaymentCount = monthContents_list.size();
                        Log.e("count", String.valueOf(loadPendingPaymentCount));

                        GetMobileMenuResponseList[] arrayObj_Class_monthcontents = new GetMobileMenuResponseList[monthContents_list.size()];
                        arrayObj_class_getpaymentpendingresp = new GetMobileMenuResponseList[arrayObj_Class_monthcontents.length];
                        objclassarr_expandedlistgroup = new ExpandListGroup[arrayObj_class_getpaymentpendingresp.length];
                        mainmenulist = new ArrayList<ExpandListGroup>();

                        for (int i = 0; i < arrayObj_Class_monthcontents.length; i++) {
                            Log.e("GetMobileMenu", String.valueOf(class_loginresponse.getStatus()));
                            Log.e("GetMobileMenu", class_loginresponse.getMessage());

                            GetMobileMenuResponseList innerObj_Class_academic = new GetMobileMenuResponseList();
                            innerObj_Class_academic.setMenuID(class_loginresponse.getObjMenu().get(i).getMenuID());
                            innerObj_Class_academic.setMenuName(class_loginresponse.getObjMenu().get(i).getMenuName());
                            innerObj_Class_academic.setMenuSort(class_loginresponse.getObjMenu().get(i).getMenuSort());
                            innerObj_Class_academic.setSubMenu(class_loginresponse.getObjMenu().get(i).getSubMenu());
                            arrayObj_class_getpaymentpendingresp[i] = innerObj_Class_academic;

                            List<GetMobileMenuResponseList> monthContents_list_submenu = response.body().getObjMenu();

                            //  GetMobileSubMenuResponseList []  arrayObj_Class_submenu= new GetMobileSubMenuResponseList[monthContents_list_submenu.size()];

                            objclassarr_Class_SubMenu = new Class_SubMenu[monthContents_list_submenu.size()];
                            // submenuList = new ArrayList<Class_SubMenu>();
                            Log.e("soap_SubMenulength()", String.valueOf(monthContents_list_submenu.size()));
                            arrayObj_class_getMobilesubmenuresp = new GetMobileSubMenuResponseList[monthContents_list_submenu.size()];
                            Class_SubMenu child = new Class_SubMenu();
                            MenuModel menuModel = null;
                            List<MenuModel> childModelsList = new ArrayList<>();


                            for (int j = 0; j < objclassarr_Class_SubMenu.length; j++) {
                                Log.e("entered 2nd for loop", "def");

                                GetMobileSubMenuResponseList innerObj_Class = new GetMobileSubMenuResponseList();
                                innerObj_Class.setMenuID(class_loginresponse.getObjMenu().get(j).getSubMenu().get(j).getMenuID());
                                innerObj_Class.setMenuName(class_loginresponse.getObjMenu().get(j).getSubMenu().get(j).getMenuName());
                                innerObj_Class.setMenuLink(class_loginresponse.getObjMenu().get(j).getSubMenu().get(j).getMenuLink());
                                innerObj_Class.setParentID(class_loginresponse.getObjMenu().get(j).getSubMenu().get(j).getParentID());
                                arrayObj_class_getMobilesubmenuresp[i] = innerObj_Class;

                                if (class_loginresponse.getObjMenu().get(j).getSubMenu().get(j).getParentID().equals(class_loginresponse.getObjMenu().get(i).getMenuID())) {
//                                    child.setMenu_ID(String.valueOf(class_loginresponse.getObjMenu().get(j).getSubMenu().get(j).getMenuID()));
//                                    child.setMenu_Name(class_loginresponse.getObjMenu().get(j).getSubMenu().get(j).getMenuName());
//                                    //   Log.e("inside",soap_Menu_Name_submenu.toString());
//                                    child.setMenu_Link(class_loginresponse.getObjMenu().get(j).getSubMenu().get(j).getMenuLink());
//                                    child.setParent_ID(String.valueOf(class_loginresponse.getObjMenu().get(j).getSubMenu().get(j).getParentID()));

                                    MenuModel childModel = new MenuModel(class_loginresponse.getObjMenu().get(j).getSubMenu().get(j).getMenuName(), false, false, class_loginresponse.getObjMenu().get(j).getSubMenu().get(j).getMenuLink());
                                    childModelsList.add(childModel);


                                } else {

                                }
                                //  submenuList.add(child);


                            }
                            menuModel = new MenuModel(class_loginresponse.getObjMenu().get(i).getMenuName(), true, true, ""); //Menu of Java Tutorials
                            headerList.add(menuModel);
//
                            if (menuModel.hasChildren) {
                                Log.d("API123", "here");
                                childList.put(menuModel, childModelsList);
                            }

//                            ExpandListGroup group = new ExpandListGroup();
//                            Log.e("entered", "ExpandListGroup");
//                            group.setMenu_Name(class_loginresponse.getObjMenu().get(i).getMenuName());
//                            Log.e("main", class_loginresponse.getObjMenu().get(i).getMenuName());
//                            // adding child to Group POJO Class
//                            group.setChildItems(submenuList);
//                            mainmenulist.add(group);
//


                        }//for loop end
                        Log.e("arrayObjclassndingresp", String.valueOf(arrayObj_class_getpaymentpendingresp.length));

                        if (arrayObj_class_getpaymentpendingresp.length == 0) {
                            str_feedback = "";
                        } else {
                            str_feedback = "1";
                        }
                        if (str_feedback.equals("")) {
                            onlineview_iv.setVisibility(View.GONE);
                            Log.e("tag", "API-Gone");
                            SharedPreferences.Editor editor = sharedpref_feedback.edit();
                            editor.putString(FeedBack, "Gone");
                            editor.apply();
                        } else {
                            onlineview_iv.setVisibility(View.VISIBLE);
                            Log.e("tag", "API-Visible");
                            SharedPreferences.Editor editor = sharedpref_feedback.edit();
                            editor.putString(FeedBack, "Visible");
                            editor.apply();
                        }
                        // populateExpandableList();
                    } else {
                        progressDoalog.dismiss();
                    }
                } else {
                    progressDoalog.dismiss();
                    Log.e("Entered resp else", "");
                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.e("error message", error.getMsg());

                    if (error.getMsg() != null) {

                        Log.e("error message", error.getMsg());
//                        str_getmonthsummary_errormsg = error.getMsg();
//                        alerts_dialog_getexlistviewError();

                        //Toast.makeText(getActivity(), error.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity_HomeScreen.this, "Kindly restart your application", Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDoalog.dismiss();
//                str_getmonthsummary_errormsg = t.getMessage();
//                alerts_dialog_getexlistviewError();

                // Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }


    //////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.schedule_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {
            case R.id.logout_menu:
                Class_SaveSharedPreference.setUserName(getApplicationContext(), "");
                Class_SaveSharedPreference.setPREF_MENU_link(getApplicationContext(), "");
                Class_SaveSharedPreference.setPrefFlagUsermanual(getApplicationContext(), "");
                Class_SaveSharedPreference.setSupportEmail(getApplicationContext(), "");
                Class_SaveSharedPreference.setUserMailID(getApplicationContext(), "");
                Class_SaveSharedPreference.setSupportPhone(getApplicationContext(), "");

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

                AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_HomeScreen.this);
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
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);

                break;

        }
        return super.onOptionsItemSelected(item);
    }


}