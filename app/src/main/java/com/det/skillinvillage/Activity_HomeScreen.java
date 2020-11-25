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
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.det.skillinvillage.adapter.CalendarAdapter;
import com.det.skillinvillage.model.Class_devicedetails;
import com.det.skillinvillage.model.Class_getdemo_Response;
import com.det.skillinvillage.model.Class_getdemo_resplist;
import com.det.skillinvillage.model.Class_gethelp_Response;
import com.det.skillinvillage.model.Class_gethelp_resplist;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.model.GetAppVersion;
import com.det.skillinvillage.model.GetAppVersionList;
import com.det.skillinvillage.model.Location_Data;
import com.det.skillinvillage.model.Location_DataList;
import com.det.skillinvillage.model.Student;
import com.det.skillinvillage.model.StudentList;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;
import com.det.skillinvillage.util.UserInfo;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
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
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.Key_username;
import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.key_userimage;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_userimage;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_username;
import static com.det.skillinvillage.NormalLogin.key_flag;
import static com.det.skillinvillage.NormalLogin.sharedpreferenc_flag;

public class Activity_HomeScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public CalendarAdapter cal_adapter1;
    public GregorianCalendar cal_month, cal_month_copy;
    ConnectionDetector internetDectector;
    Boolean isInternetPresent = false;

    TextView dislay_UserName_tv;
    ImageView displ_Userimg_iv;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    String str_Googlelogin_Username, str_Googlelogin_UserImg, str_loginuserID="";
    String Schedule_Status,Schedule_ID,Lavel_ID,Schedule_Date,End_Time,Start_Time,Schedule_Session,Subject_Name,Leason_Name,Lavel_Name,Cluster_Name,Institute_Name;
    ArrayList<UserInfo> arrayList = new ArrayList<UserInfo>();
    UserInfo[] userInfosarr;
    String str_flag,str_logout_count_Status="";



    SharedPreferences sharedpref_username_Obj;
    SharedPreferences sharedpref_userimage_Obj;
    SharedPreferences sharedpref_loginuserid_Obj;
    SharedPreferences sharedpref_flag_Obj;

    SharedPreferences sharedpref_feedback;
    public static final String MyPREFERENCES_Feedback = "MyPrefsFeedback" ;
    public static final String FeedBack = "feedBack";
    String str_feedback="";

    ImageView dashboard_iv,stu_reg_iv,scheduler_iv,usermanual_iv,docview_iv,mark_attendance_iv,assessment_iv,onlineview_iv;



    TelephonyManager tm1 = null;
    String myVersion, deviceBRAND, deviceHARDWARE, devicePRODUCT, deviceUSER, deviceModelName, deviceId, tmDevice, tmSerial, androidId, simOperatorName, sdkver, mobileNumber;
    int sdkVersion, Measuredwidth = 0, Measuredheight = 0, update_flage = 0;
    String regId = "dfagriXZ", str_userid;

    private String versioncode;
    Interface_userservice userService1;
    Location_DataList[] location_dataLists;
    Location_DataList class_location_dataList = new Location_DataList();
    StudentList[] StudentLists;
    StudentList class_StudentList = new StudentList();


    public static final String MyPREFERENCE_SyncId = "MyPref_SyncId" ;
    public static final String SyncId = "SyncId";
    SharedPreferences shared_syncId;
    SharedPreferences sharedpref_spinner_Obj;

    public static final String sharedpreferenc_selectedspinner = "sharedpreferenc_selectedspinner";
    public static final String Key_syncId = "Sync_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fstpage);
        userService1 = Class_ApiUtils.getUserService();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Home");
        try {
            versioncode = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //Added by shivaleela on aug 14th 2019
        //  Class_SaveSharedPreference.getUserName(Activity_HomeScreen.this);

       /* SharedPreferences myprefs_name = this.getSharedPreferences("googlelogin_name", Context.MODE_PRIVATE);
        str_Googlelogin_Username = myprefs_name.getString("name_googlelogin", "nothing");

        SharedPreferences myprefs_img = this.getSharedPreferences("googlelogin_img", Context.MODE_PRIVATE);
        str_Googlelogin_UserImg = myprefs_img.getString("profileimg_googlelogin", "nothing");

        SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        str_loginuserID = myprefs.getString("login_userid", "nothing");

        SharedPreferences myprefs_flag = this.getSharedPreferences("flag", Context.MODE_PRIVATE);
       str_flag = myprefs_flag.getString("flag", "nothing");
*/

        sharedpref_username_Obj=getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
        str_Googlelogin_Username = sharedpref_username_Obj.getString(Key_username, "").trim();

        sharedpref_userimage_Obj=getSharedPreferences(sharedpreferenc_userimage, Context.MODE_PRIVATE);
        str_Googlelogin_UserImg = sharedpref_userimage_Obj.getString(key_userimage, "").trim();

        sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

        sharedpref_flag_Obj=getSharedPreferences(sharedpreferenc_flag, Context.MODE_PRIVATE);
        str_flag = sharedpref_flag_Obj.getString(key_flag, "").trim();

        sharedpref_feedback = getSharedPreferences(MyPREFERENCES_Feedback, Context.MODE_PRIVATE);
        str_feedback = sharedpref_feedback.getString(FeedBack, "").trim();


        sharedpref_spinner_Obj = getSharedPreferences(sharedpreferenc_selectedspinner, Context.MODE_PRIVATE);
        shared_syncId = getSharedPreferences(MyPREFERENCE_SyncId, Context.MODE_PRIVATE);

        // if(str_flag.equals("1")) {
        Log.e("username",Class_SaveSharedPreference.getUserName(Activity_HomeScreen.this));
            if (Class_SaveSharedPreference.getUserName(Activity_HomeScreen.this).length() == 0) {
                Intent i = new Intent(Activity_HomeScreen.this, MainActivity.class);
                startActivity(i);
                finish();
                // call Login Activity
            } else {
                // Stay at the current activity.
            }

//        }else {
//
//            if (Class_SaveSharedPreference.getUserName(Activity_HomeScreen.this).length() == 0) {
//                Intent i = new Intent(Activity_HomeScreen.this, MainActivity.class);
//                startActivity(i);
//                finish();
//                // call Login Activity
//            } else {
//                // Stay at the current activity.
//            }
//        }

        internetDectector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();



        LinearLayout homepagelayout_LL = findViewById(R.id.homepagelayout_ll);
        homepagelayout_LL.setVisibility(LinearLayout.VISIBLE);
        @SuppressLint("ResourceType") Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animation3.setDuration(100);
        homepagelayout_LL.setAnimation(animation3);
        homepagelayout_LL.animate();
        animation3.start();


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        dislay_UserName_tv = findViewById(R.id.dislay_UserName_TV);
        displ_Userimg_iv = findViewById(R.id.displ_Userimg_IV);
        // dislay_UserName_tv.setText(str_Googlelogin_Username);
        dashboard_iv= findViewById(R.id.dashboard_IV);
        stu_reg_iv= findViewById(R.id.stu_reg_IV);
        scheduler_iv= findViewById(R.id.scheduler_iv);
        usermanual_iv= findViewById(R.id.usermanual_iv);
        docview_iv= findViewById(R.id.docview_iv);
        //mark_attendance_iv=(ImageView)findViewById(R.id.mark_attendance_iv);
        assessment_iv= findViewById(R.id.assessment_iv);
        onlineview_iv=findViewById(R.id.onlineview_iv);
     //   onlineview_iv.setVisibility(View.GONE);
        if(str_flag.equals("1")){
            dislay_UserName_tv.setText("");
        }else {
            dislay_UserName_tv.setText(str_Googlelogin_Username);
            try {
                Glide.with(this).load(str_Googlelogin_UserImg).into(displ_Userimg_iv);
            } catch (NullPointerException e) {
                // Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
            }
        }

        if(isInternetPresent) {
            AsyncCallWS2_Login task = new AsyncCallWS2_Login(Activity_HomeScreen.this);
            task.execute();
            OnlineView_Feedback task1 = new OnlineView_Feedback(Activity_HomeScreen.this);
            task1.execute();
            Add_setGCM1();
            GetAppVersionCheck();
            delete_CountDetailsRestTable_B4insertion();
            deleteStateRestTable_B4insertion();
            deleteDistrictRestTable_B4insertion();
            deleteTalukRestTable_B4insertion();
            deleteVillageRestTable_B4insertion();
            deleteYearRestTable_B4insertion();
            GetDropdownValuesRestData();
            GetStudentValuesRestData();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }
        if(str_feedback!=null){
            if (str_feedback.equalsIgnoreCase("Gone")||str_feedback.equalsIgnoreCase("")){
                onlineview_iv.setVisibility(View.GONE);
                Log.e("tag","Oncreate-Gone");
            }
            else {
                onlineview_iv.setVisibility(View.VISIBLE);
                Log.e("tag","Oncreate-Visible");
            }
        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RelativeLayout stuRegistration_relativeLayout = findViewById(R.id.student_registration_RL);
                stuRegistration_relativeLayout.setVisibility(LinearLayout.VISIBLE);
                @SuppressLint("ResourceType") Animation stuRegistration_animation = AnimationUtils.loadAnimation(Activity_HomeScreen.this, R.anim.right_slide);
                stuRegistration_animation.setDuration(1500);
                stuRegistration_relativeLayout.setAnimation(stuRegistration_animation);
                stuRegistration_relativeLayout.setAnimation(stuRegistration_animation);
                stuRegistration_relativeLayout.animate();
                stuRegistration_animation.start();

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

                        Intent i = new Intent(Activity_HomeScreen.this, Activity_Dashboard.class);
                        startActivity(i);
                        overridePendingTransition(R.animator.slide_right, R.animator.slide_right);

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

                        RelativeLayout scheduler_relativelayout = findViewById(R.id.scheduler_RL);
                        scheduler_relativelayout.setVisibility(LinearLayout.VISIBLE);
                        @SuppressLint("ResourceType")
                        Animation animation_scheduler = AnimationUtils.loadAnimation(Activity_HomeScreen.this, R.anim.right_slide);
                        animation_scheduler.setDuration(1500);
                        scheduler_relativelayout.setAnimation(animation_scheduler);
                        scheduler_relativelayout.animate();
                        animation_scheduler.start();

                        scheduler_iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                        /*Intent i = new Intent(Activity_HomeScreen.this, Activity_SO_DocumentUpload.class);
                                        startActivity(i);*/

                                //Added isInternetPresent by shivaleela on Aug 21st 2019
                                internetDectector = new ConnectionDetector(getApplicationContext());
                                isInternetPresent = internetDectector.isConnectingToInternet();

                                if(isInternetPresent) {
                                    Date date = new Date();
                                    Log.i("Tag_time", "date1=" + date);
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    String PresentDayStr = sdf.format(date);
                                    Log.i("Tag_time", "PresentDayStr=" + PresentDayStr);//2019-12-12

                                    cal_adapter1.getPositionList(PresentDayStr, Activity_HomeScreen.this);
                                    overridePendingTransition(R.animator.slide_right, R.animator.slide_right);
                                }else{
                                    Toast.makeText(getApplicationContext(),"No Internet", Toast.LENGTH_SHORT).show();

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


                        final Handler handler2 = new Handler();
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                RelativeLayout documentUpload_relativelayout = findViewById(R.id.docView_RL);
                                documentUpload_relativelayout.setVisibility(LinearLayout.VISIBLE);
                                @SuppressLint("ResourceType")
                                Animation animation_docupload = AnimationUtils.loadAnimation(Activity_HomeScreen.this, R.anim.right_slide);
                                animation_docupload.setDuration(1500);
                                documentUpload_relativelayout.setAnimation(animation_docupload);
                                documentUpload_relativelayout.animate();
                                animation_docupload.start();

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

                                        RelativeLayout documentUpload_relativelayout = findViewById(R.id.feedback_RL);
                                        documentUpload_relativelayout.setVisibility(LinearLayout.VISIBLE);
                                        @SuppressLint("ResourceType")
                                        Animation animation_docupload = AnimationUtils.loadAnimation(Activity_HomeScreen.this, R.anim.right_slide);
                                        animation_docupload.setDuration(1500);
                                        documentUpload_relativelayout.setAnimation(animation_docupload);
                                        documentUpload_relativelayout.animate();
                                        animation_docupload.start();

                                        onlineview_iv.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(str_feedback.equalsIgnoreCase("Visible")) {
                                                    Intent i = new Intent(Activity_HomeScreen.this, Onlineview_Navigation.class);
                                                    startActivity(i);
                                                    overridePendingTransition(R.animator.slide_right, R.animator.slide_right);
                                                }else{
                                                    Toast.makeText(getApplicationContext(),"You Dont Have Access",Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(Activity_HomeScreen.this, Activity_HomeScreen.class);
                                                    startActivity(i);
                                                    finish();
                                                }

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

        assessment_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_HomeScreen.this, Activity_AssessmentList.class);
                startActivity(i);
                overridePendingTransition(R.animator.slide_right, R.animator.slide_right);

            }
        });




        String MsgNotfication = "";

        Bundle extras = getIntent().getExtras();

// if (extras != null){}
        if (extras != null)
        {
            MsgNotfication = extras.getString("2");
            if(MsgNotfication!=null||MsgNotfication!="")
            {
                Toast.makeText(getApplicationContext()," "+ MsgNotfication,Toast.LENGTH_LONG).show();
                Log.e("notifMsg","notifMsg: "+ MsgNotfication);
            }
        }else{
            Log.e("extranull", String.valueOf(extras));
        }

       // gethelp();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
//        if (opr.isDone()) {
//            GoogleSignInResult result = opr.get();
//            handleSignInResult(result);
//        } else {
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
//                    //handleSignInResult(googleSignInResult);
//                }
//            });
//        }
//    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            dislay_UserName_tv.setText(account.getDisplayName());
            try {
                Glide.with(this).load(account.getPhotoUrl()).into(displ_Userimg_iv);
            } catch (NullPointerException e) {
                // Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
            }

        } else {
            //gotoMainActivity();
        }
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu items
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {

           *//* case R.id.next:
                Intent in =new Intent(Activity_HomeScreen.this,Home1.class);
                startActivity(in);
                finish();
                break;*//*
            case R.id.share:
                Toast.makeText(Activity_HomeScreen.this, "Action clicked", Toast.LENGTH_LONG).show();
                internetDectector = new ConnectionDetector(getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();

                if (isInternetPresent) {


                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("logout_key1", "yes");
                    startActivity(i);
                    finish();
                    //}
                }
                else{
                    Toast.makeText(getApplicationContext(),"No Internet", Toast.LENGTH_SHORT).show();
                }
            case android.R.id.home:
                Intent i =new Intent(Activity_HomeScreen.this,MainActivity.class);
                startActivity(i);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }*/

    private class AsyncCallWS2_Login extends AsyncTask<String, Void, Void>
    {
        // ProgressDialog dialog;

        Context context;

        @Override
        protected void onPreExecute()
        {
            Log.i("tag", "onPreExecute---tab2");
            /*dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("tag", "onProgressUpdate---tab2");
        }

        public AsyncCallWS2_Login(Activity_HomeScreen activity) {
            context =  activity;
            //  dialog = new ProgressDialog(activity);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("tag", "doInBackground");

            fetch_all_info("");

			/*  if(!login_result.equals("Fail"))
			  {
				  GetAllEvents(u1,p1);
			  }*/

            //GetAllEvents(u1,p1);



            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            //  dialog.dismiss();

            cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
            cal_month_copy = (GregorianCalendar) cal_month.clone();
            cal_adapter1 = new CalendarAdapter(Activity_HomeScreen.this, cal_month, UserInfo.user_info_arr);

           /* Intent i = new Intent(Activity_HomeScreen.this, Activity_HomeScreen.class);
            SharedPreferences myprefs = Activity_HomeScreen.this.getSharedPreferences("user", MODE_PRIVATE);
            myprefs.edit().putString("login_userid", str_loginuserID).apply();
            SharedPreferences myprefs_scheduleId = Activity_HomeScreen.this.getSharedPreferences("scheduleId", MODE_PRIVATE);
            myprefs_scheduleId.edit().putString("scheduleId", Schedule_ID).apply();
            startActivity(i);
            finish();*/
        }
    }



    public void fetch_all_info(String email) {


        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "LoadScheduleEmployee";
        String Namespace = "http://mis.detedu.org:8089/", SOAP_ACTION1 = "http://mis.detedu.org:8089/LoadScheduleEmployee";

        try {
            //mis.leadcampus.org

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

            Log.i("User_ID=", str_loginuserID);
            request.addProperty("User_ID", str_loginuserID);//str_loginuserID
            Log.d("request :", request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try {
                androidHttpTransport.call(SOAP_ACTION1, envelope);
                Log.d("soap responseyyyyyyy", envelope.getResponse().toString());
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.d("soap responseyyyyyyy", response.toString());
                //	for (SoapObject cs : result1)
                if (response.getPropertyCount() > 0) {
                    int Count = response.getPropertyCount();

                    for (int i = 0; i < Count; i++) {
                        // sizearray=result1.size();
                        //  Log.i("Tag","sizearray="+sizearray);
                        /*  <Schedule_ID>int</Schedule_ID>
          <Lavel_ID>int</Lavel_ID>
          <Schedule_Date>string</Schedule_Date>
          <Start_Time>string</Start_Time>
          <End_Time>string</End_Time>
          <Schedule_Session>string</Schedule_Session>
          <Subject_Name>string</Subject_Name>
          <Schedule_Status>string</Schedule_Status>*/


                        SoapObject list = (SoapObject) response.getProperty(i);
                        Schedule_Status = list.getProperty("Schedule_Status").toString();

                        Schedule_ID = list.getProperty("Schedule_ID").toString();
                        Lavel_ID = list.getProperty("Lavel_ID").toString();
                        Schedule_Date = list.getProperty("Schedule_Date").toString();
                        End_Time = list.getProperty("End_Time").toString();
                        Start_Time = list.getProperty("Start_Time").toString();
                        Subject_Name = list.getProperty("Subject_Name").toString();
                        Schedule_Session = list.getProperty("Schedule_Session").toString();
                        Leason_Name = list.getProperty("Leason_Name").toString();
                        Lavel_Name = list.getProperty("Lavel_Name").toString();
                        Institute_Name = list.getProperty("Institute_Name").toString();
                        Cluster_Name = list.getProperty("Cluster_Name").toString();

                        //   State cat = new State(c.getString("state_id"),c.getString("state_name"));
                        UserInfo userInfo = new UserInfo(Schedule_ID, Lavel_ID, Schedule_Date, End_Time, Start_Time, Schedule_Session,Schedule_Status,Subject_Name,Lavel_Name,Leason_Name,Cluster_Name,Institute_Name);
                        arrayList.add(userInfo);

                    }

                    final String[] items = new String[Count];
                    userInfosarr = new UserInfo[Count];
                    UserInfo obj = new UserInfo();

                    UserInfo.user_info_arr.clear();
                    for (int i = 0; i < response.getPropertyCount(); i++) {
                        Schedule_ID = arrayList.get(i).Schedule_ID;
                        Lavel_ID = arrayList.get(i).Lavel_ID;
                        Schedule_Date = arrayList.get(i).Schedule_Date;
                        End_Time = arrayList.get(i).End_Time;
                        Start_Time = arrayList.get(i).Start_Time;
                        Schedule_Session = arrayList.get(i).Schedule_Session;
                        Schedule_Status = arrayList.get(i).Schedule_Status;
                        Subject_Name = arrayList.get(i).Subject_Name;
                        Lavel_Name = arrayList.get(i).Lavel_Name;
                        Leason_Name = arrayList.get(i).Leason_Name;
                        Cluster_Name = arrayList.get(i).Cluster_Name;
                        Institute_Name = arrayList.get(i).Institute_Name;

                        obj.setSchedule_ID(Schedule_ID);
                        obj.setLavel_ID(Lavel_ID);
                        obj.setSchedule_Date(Schedule_Date);
                        obj.setEnd_Time(End_Time);
                        obj.setStart_Time(Start_Time);
                        obj.setSchedule_Session(Schedule_Session);
                        obj.setSchedule_Status(Schedule_Status);
                        obj.setSubject_Name(Subject_Name);
                        obj.setLavel_Name(Lavel_Name);
                        obj.setLeason_Name(Leason_Name);
                        obj.setInstitute_Name(Institute_Name);
                        obj.setCluster_Name(Cluster_Name);

                        userInfosarr[i] = obj;
                        //   UserInfo.user_info_arr =new ArrayList<UserInfo>() ;

                        UserInfo.user_info_arr.add(new UserInfo(Schedule_ID, Lavel_ID, Schedule_Date, End_Time, Start_Time, Schedule_Session,Schedule_Status,Subject_Name,Lavel_Name, Leason_Name,Cluster_Name, Institute_Name));

                        Log.i("Tag", "items aa=" + arrayList.get(i).Schedule_ID);

                    }

                    Log.i("Tag", "items=" + items.length);
                }
                //  Log.e("TAG","bookid="+bookid+"cohartName="+cohartName+"fellowshipName="+fellowshipName+"eventdate="+eventdate+"start_time"+start_time);

			/*	 for(int i=0;i<result1.size();i++);
				 {
					 String booking_id_temp, user_id_temp,date_temp,notes_temp,start_time_temp;
					 if(Event_Discription.equals(result1.elementAt(i)))

				 }*/

                //	 SoapPrimitive messege = (SoapPrimitive)response.getProperty("Status");
                // version = (SoapPrimitive)response.getProperty("AppVersion");
                // release_not = (SoapPrimitive)response.getProperty("ReleseNote");

            } catch (Throwable t) {
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //		Toast.LENGTH_LONG).show();
                Log.e("request fail 5", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("Tag", "UnRegister Receiver Error 5" + " > " + t.getMessage());

        }

    }

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
            if (str_feedback.toString().equalsIgnoreCase("")|| str_feedback.toString().equalsIgnoreCase("anyType{}")  || str_feedback.toString().equalsIgnoreCase(null)) {
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
            }
            //populateExpandableList();
//            MyAdapter   adapter = new MyAdapter(mainmenulist, Onlineview_Navigation.this);
//            expandableListView.setAdapter(adapter);

        }

    }

    public void list_detaile() {
        Vector<SoapObject> result1 = null;
        String url ="http://mis.detedu.org:8089/SIVService.asmx?WSDL";
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
                str_feedback="";
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

            }catch(Throwable t){
                Log.e("requestload fail", "> " + t.getMessage());
            }

        } catch (Throwable t) {
            Log.e("UnRegisterload  Error", "> " + t.getMessage());

        }


    }//End of leaveDetail method

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
            internetDectector = new ConnectionDetector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent) {

//                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                SharedPreferences.Editor editor = settings.edit();
//                editor.remove("login_userEmail");
                Class_SaveSharedPreference.setUserName(Activity_HomeScreen.this, "");
                deleteSandBoxTable_B4insertion();
                deleteAcademicTable_B4insertion();
                deleteClusterTable_B4insertion();
                deleteInstituteTable_B4insertion();
                deleteSchoolTable_B4insertion();
                deleteLevelTable_B4insertion();
                deleteLearningOptionTable_B4insertion();

                LogoutCountAsynctask logoutCountAsynctask=new LogoutCountAsynctask(Activity_HomeScreen.this);
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

                //}
            } else {
                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (id == android.R.id.home) {
            //  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Activity_HomeScreen.this, Activity_HomeScreen.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



    public class LogoutCountAsynctask extends AsyncTask<String, Void, Void> {
       // ProgressDialog dialog;
        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
//            dialog.setMessage("Please wait...");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();

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

        public LogoutCountAsynctask(Activity_HomeScreen activity) {
            context = activity;
            //dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {


           // dialog.dismiss();
            if(!str_logout_count_Status.equals("")){
                if (str_logout_count_Status.equals("Success")) {
                   // Toast.makeText(Activity_HomeScreen.this, "Success", Toast.LENGTH_SHORT).show();

                } else if (str_logout_count_Status.equals("Failure")){
                    //Toast.makeText(Activity_HomeScreen.this, "Failure", Toast.LENGTH_SHORT).show();
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





    public void gethelp()
    {
        internetDectector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if(isInternetPresent)
        {
             gethelp_api();
        }
    }

    private void gethelp_api()
    {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
        progressDoalog.setMessage("Fetching Helpdetails....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();

        Call<Class_gethelp_Response> call = userService.GetHelp("12");


        call.enqueue(new Callback<Class_gethelp_Response>() {
            @Override
            public void onResponse(Call<Class_gethelp_Response> call, Response<Class_gethelp_Response> response) {

                // Toast.makeText(MainActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();

                Log.e("response_gethelp", "response_gethelp: " + new Gson().toJson(response));

               /* Class_gethelp_Response gethelp_response_obj = new Class_gethelp_Response();
                gethelp_response_obj = (Class_gethelp_Response) response.body();*/



                if(response.isSuccessful())
                {
                    DBCreate_Helpdetails();
                    Class_gethelp_Response gethelp_response_obj = response.body();
                    Log.e("response.body", response.body().getLst().toString());


                    if (gethelp_response_obj.getStatus().equals(true))
                    {

                        List<Class_gethelp_resplist> helplist = response.body().getLst();
                        Log.e("length", String.valueOf(helplist.size()));
                        int int_helpcount=helplist.size();

                        for(int i=0;i<int_helpcount;i++)
                        {
                            Log.e("title",helplist.get(i).getTitle().toString());

                            String str_title=helplist.get(i).getTitle().toString();
                            String str_content=helplist.get(i).getContent().toString();
                            DBCreate_HelpDetails_insert_2sqliteDB(str_title,str_content);
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



    public void DBCreate_HelpDetails_insert_2sqliteDB(String title,String content)
    {
        SQLiteDatabase db2 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS HelpDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,TitleDB VARCHAR,ContentDB VARCHAR);");

        ContentValues cv = new ContentValues();
        cv.put("TitleDB", title);
        cv.put("ContentDB", content);
        db2.insert("HelpDetails_table", null, cv);
        db2.close();

        Log.e("insert","DBCreate_HelpDetails_insert_2sqliteDB");

    }


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




    public void Data_from_HelpDetails_table()
    {
        SQLiteDatabase db2 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS HelpDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,TitleDB VARCHAR,ContentDB VARCHAR);");
        Cursor cursor = db2.rawQuery("SELECT * FROM HelpDetails_table", null);
        int x = cursor.getCount();

        Log.e("helpcount", String.valueOf(x));

    }

    public void getdemo()
    {
        internetDectector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if(isInternetPresent)
        {
            getdemo_api();
        }
    }

    private void getdemo_api()
    {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
        progressDoalog.setMessage("Fetching Demodetails....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();

        Call<Class_getdemo_Response> call = userService.GetDemo("12");


        call.enqueue(new Callback<Class_getdemo_Response>() {
            @Override
            public void onResponse(Call<Class_getdemo_Response> call, Response<Class_getdemo_Response> response) {
                Log.e("response_gethelp", "response_gethelp: " + new Gson().toJson(response));

               /* Class_gethelp_Response gethelp_response_obj = new Class_gethelp_Response();
                gethelp_response_obj = (Class_gethelp_Response) response.body();*/



                if(response.isSuccessful())
                {
                    DBCreate_Demodetails();
                    Class_getdemo_Response getdemo_response_obj = response.body();
                    Log.e("response.body", response.body().getLst().toString());


                    if (getdemo_response_obj.getStatus().equals(true))
                    {

                        List<Class_getdemo_resplist> demolist = response.body().getLst();
                        Log.e("length", String.valueOf(demolist.size()));
                        int int_helpcount=demolist.size();

                        for(int i=0;i<int_helpcount;i++)
                        {
                            Log.e("language",demolist.get(i).getLanguage_Name().toString());

                            String str_languagename=demolist.get(i).getLanguage_Name().toString();
                            String str_languagelink=demolist.get(i).getLanguage_Link().toString();
                            DBCreate_DemoDetails_insert_2sqliteDB(str_languagename,str_languagelink);
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




    public void DBCreate_DemoDetails_insert_2sqliteDB(String str_languagename,String str_languagelink)
    {
        SQLiteDatabase db2 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS DemoDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,LanguageDB VARCHAR,LinkDB VARCHAR);");

        ContentValues cv = new ContentValues();
        cv.put("LanguageDB", str_languagename);
        cv.put("LinkDB", str_languagelink);
        db2.insert("DemoDetails_table", null, cv);
        db2.close();

        Log.e("insert","DBCreate_DemoDetails_insert_2sqliteDB");

    }



    private void GetAppVersionCheck(){
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
        call.enqueue(new Callback<GetAppVersion>()
        {
            @Override
            public void onResponse(Call<GetAppVersion> call, Response<GetAppVersion> response)
            {
                Log.e("response",response.toString());
                Log.e("TAG", "response: "+new Gson().toJson(response) );
                Log.e("response body", String.valueOf(response.body()));

                if(response.isSuccessful())
                {
                    //        progressDoalog.dismiss();
                    GetAppVersion  class_loginresponse = response.body();
                    Log.e("tag","res=="+class_loginresponse.toString());
                    if(class_loginresponse.getStatus()) {


                        List<GetAppVersionList> getAppVersionList=response.body().getlistVersion();
                        String str_releaseVer=getAppVersionList.get(0).getAppVersion();

                        int int_versioncode= Integer.parseInt(versioncode);
                        int int_releaseVer= Integer.parseInt(str_releaseVer);

                        Log.e("tag","str_releaseVer="+str_releaseVer);
                        if(int_releaseVer>int_versioncode)
                        {
                            //call pop
                            Log.e("popup","popup");
                            //alerts();
                            alerts_dialog_playstoreupdate();
                        }
                        else{

                        }
                        progressDoalog.dismiss();
                    }else{
                        progressDoalog.dismiss();
                        Toast.makeText(Activity_HomeScreen.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMsg());

                    Toast.makeText(Activity_HomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t)
            {
                progressDoalog.dismiss();
                Log.e("TAG", "onFailure: "+t.toString() );

                Log.e("tag","Error:"+t.getMessage());
                Toast.makeText(Activity_HomeScreen.this, "WS:Error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public  void alerts_dialog_playstoreupdate()
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_HomeScreen.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.alert);
        dialog.setMessage("Kindly update from playstore");

        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=df.farmponds"));
                startActivity(intent);
            }
        });


        final AlertDialog alert = dialog.create();
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();

    }

    private void Add_setGCM1()
    {
        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();

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
        tmDevice = "" + tm1.getDeviceId();
        Log.e("DeviceIMEI", "" + tmDevice);
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
        mobileNumber = "" + tm1.getLine1Number();
        Log.e("getLine1Number value", "" + mobileNumber);

        String mobileNumber1 = "" + tm1.getPhoneType();
        Log.e("getPhoneType value", "" + mobileNumber1);
        tmSerial = "" + tm1.getSimSerialNumber();
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
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
        request.setUser_ID("12");
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

       // Log.e("gcm() usrid", "" + str_loginuserID);
        Log.e("regId", "" + regId);
        Log.e("myVersion()", "" + myVersion);
        Log.e("deviceBRAND()", "" + deviceBRAND);
        Log.e("deviceModelName()", "" + deviceModelName);
        Log.e("sdkver()", "" + sdkver);
        Log.e("tmDevice()", "" + tmDevice);
        Log.e("simOperatorName()", "" + simOperatorName);
        Log.e("tmSerial()", "" + tmSerial);
        Log.e("Measuredwidth()", "" + Measuredwidth);
        Log.e("Measuredheight()", "" + Measuredheight);
        Log.e("versioncode()", "" + versioncode);

        {
            retrofit2.Call call = userService.Post_ActionDeviceDetails(request);

            call.enqueue(new Callback<Class_devicedetails>()
            {
                @Override
                public void onResponse(retrofit2.Call<Class_devicedetails> call, Response<Class_devicedetails> response) {


                    Log.e("response", response.toString());
                    Log.e("response_body", String.valueOf(response.body()));

                    if (response.isSuccessful())
                    {
                        //  progressDoalog.dismiss();


                        Class_devicedetails class_addfarmponddetailsresponse = response.body();

                        if (class_addfarmponddetailsresponse.getStatus().equals("true"))
                        {
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


    private void GetDropdownValuesRestData() {

        Call<Location_Data> call = userService1.getLocationData("12");
        //  Call<Location_Data> call = userService1.getLocationData("90");
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_HomeScreen.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.enqueue(new Callback<Location_Data>() {
            @Override
            public void onResponse(Call<Location_Data> call, Response<Location_Data> response) {
                Log.e("Entered resp", response.message());
                //     Log.e("Entered resp", response.body().getMessage());

                if (response.isSuccessful())
                {
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
                                SharedPreferences.Editor editor = shared_syncId.edit();
                                editor.putString(SyncId, Sync_ID);
                                editor.commit();
                                SharedPreferences.Editor myprefs_spinner = sharedpref_spinner_Obj.edit();
                                myprefs_spinner.putString(Key_syncId, Sync_ID);
                                myprefs_spinner.apply();
                                DBCreate_CountDetailsRest_insert_2SQLiteDB(SCount,DCount,TCount,VCount,YCount,Sync_ID);

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
                                DBCreate_YeardetailsRest_insert_2SQLiteDB(YearId, YearName, j);
                            }
                        }

                        /*uploadfromDB_Yearlist();
                        uploadfromDB_Statelist();
                        uploadfromDB_Districtlist();
                        uploadfromDB_Taluklist();
                        uploadfromDB_Villagelist();
                        uploadfromDB_Grampanchayatlist();*/
                    } else {
                        Log.e("getLocationData",class_locaitonData.getMessage());
                        Toast.makeText(Activity_HomeScreen.this, class_locaitonData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDoalog.dismiss();
                    Log.e("tag","working");
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

                Log.e("tag",t.getMessage());
                Toast.makeText(Activity_HomeScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }
    private void GetStudentValuesRestData() {

        Call<Student> call = userService1.getStudentData("12");
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

                if (response.isSuccessful())
                {
                    Student class_studentData = response.body();
                    Log.e("response.body", response.body().getLst().toString());
                    if (class_studentData.getStatus().equals(true)) {
                        List<StudentList> studentLists = response.body().getLst();
                        Log.e("tag","studentlist.size()="+ String.valueOf(studentLists.size()));

                        StudentLists = new StudentList[studentLists.size()];
                        // Toast.makeText(getContext(), "" + class_monthCounts.getMessage(), Toast.LENGTH_SHORT).show();


                        for (int i = 0; i < studentLists.size(); i++) {


                            Log.e("status", String.valueOf(class_StudentList.getStudentStatus()));
                           /* Log.e("msg", class_loginresponse.getMessage());
                            Log.e("list", class_loginresponse.getList().get(i).getId());
                            Log.e("list", class_loginresponse.getList().get(i).getProgramCode());
                            Log.e("size", String.valueOf(class_loginresponse.getList().size()));*/

                            String AcademicID= String.valueOf(class_studentData.getLst().get(i).getAcademicID());
                            String AcademicName=class_studentData.getLst().get(i).getAcademicName();
                            String AdmissionFee=class_studentData.getLst().get(i).getAdmissionFee();
                            String ApplicationNo=class_studentData.getLst().get(i).getApplicationNo();
                            String BalanceFee=class_studentData.getLst().get(i).getBalanceFee();
                            String BirthDate=class_studentData.getLst().get(i).getBirthDate();
                            String ClusterID= String.valueOf(class_studentData.getLst().get(i).getClusterID());
                            String ClusterName=class_studentData.getLst().get(i).getClusterName();
                            String CreatedDate=class_studentData.getLst().get(i).getCreatedDate();
                            String Education=class_studentData.getLst().get(i).getEducation();
                            String FatherName=class_studentData.getLst().get(i).getFatherName();
                            String Gender=class_studentData.getLst().get(i).getGender();
                            String InstituteName=class_studentData.getLst().get(i).getInstituteName();
                            String InstituteID= String.valueOf(class_studentData.getLst().get(i).getInstituteID());
                            String LevelID= String.valueOf(class_studentData.getLst().get(i).getLevelID());
                            String LevelName=class_studentData.getLst().get(i).getLevelName();
                            String Marks4=class_studentData.getLst().get(i).getMarks4();
                            String Marks5=class_studentData.getLst().get(i).getMarks5();
                            String Marks6=class_studentData.getLst().get(i).getMarks6();
                            String Marks7=class_studentData.getLst().get(i).getMarks7();
                            String Marks8=class_studentData.getLst().get(i).getMarks8();
                            String Mobile=class_studentData.getLst().get(i).getMobile();
                            String MotherName=class_studentData.getLst().get(i).getMotherName();
                            String PaidFee=class_studentData.getLst().get(i).getPaidFee();
                            String ReceiptNo=class_studentData.getLst().get(i).getReceiptNo();
                            String SandboxID= String.valueOf(class_studentData.getLst().get(i).getSandboxID());
                            String SandboxName=class_studentData.getLst().get(i).getSandboxName();
                            String SchoolID= String.valueOf(class_studentData.getLst().get(i).getSchoolID());
                            String SchoolName=class_studentData.getLst().get(i).getSchoolName();
                            String StudentAadhar=class_studentData.getLst().get(i).getStudentAadhar();
                            String StudentID= String.valueOf(class_studentData.getLst().get(i).getStudentID());
                            String StudentName=class_studentData.getLst().get(i).getStudentName();
                            String StudentPhoto=class_studentData.getLst().get(i).getStudentPhoto();
                            String StudentStatus=class_studentData.getLst().get(i).getStudentStatus();

                            Log.e("tag","StudentName="+StudentName+"StudentStatus="+StudentStatus+"AcademicName="+AcademicName);

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

                            DBCreate_StudentdetailsRest_insert_2SQLiteDB(AcademicID, AcademicName, AdmissionFee,ApplicationNo,BalanceFee,BirthDate,ClusterID,
                                    ClusterName,CreatedDate,Education,FatherName,Gender,InstituteName,InstituteID,LevelID,LevelName,Marks4,Marks5,Marks6,Marks7,Marks8,
                                    Mobile,MotherName,PaidFee,ReceiptNo,SandboxID,SandboxName,SchoolID,SchoolName,StudentAadhar,StudentID,StudentName,StudentPhoto,StudentStatus,str_base64image);


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

                        /*uploadfromDB_Yearlist();
                        uploadfromDB_Statelist();
                        uploadfromDB_Districtlist();
                        uploadfromDB_Taluklist();
                        uploadfromDB_Villagelist();
                        uploadfromDB_Grampanchayatlist();*/
                    } else {
                        Log.e("getStudentData",class_studentData.getMessage());
                        Toast.makeText(Activity_HomeScreen.this, class_studentData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDoalog.dismiss();
                    Log.e("tag","working");
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

                Log.e("tag",t.getMessage());
                Toast.makeText(Activity_HomeScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }



    public void DBCreate_CountDetailsRest_insert_2SQLiteDB(String str_sCount, String str_dCount, String str_tCount,String str_vCount,String yearCount,String str_Sync_Id) {
        SQLiteDatabase db_locationCount = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_locationCount.execSQL("CREATE TABLE IF NOT EXISTS LocationCountListRest(StateCount VARCHAR,DistrictCount VARCHAR,TalukaCount VARCHAR,VillageCount VARCHAR,YearCount VARCHAR,Sync_ID VARCHAR);");

        String SQLiteQuery = "INSERT INTO LocationCountListRest (StateCount,DistrictCount,TalukaCount,VillageCount,YearCount,Sync_ID)" +
                " VALUES ('" + str_sCount + "','" + str_dCount +"','"+ str_tCount +"','"+str_vCount + "','" + yearCount + "','" + str_Sync_Id +"');";
        db_locationCount.execSQL(SQLiteQuery);

        Log.e("str_sCount DB", str_sCount);
        Log.e("str_dCount DB", str_dCount);
        Log.e("str_tCount DB", str_tCount);
//        Log.e("str_Sync_Id DB", str_Sync_Id);
        db_locationCount.close();
    }
    public void delete_CountDetailsRestTable_B4insertion() {

        SQLiteDatabase db_locationCount = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_locationCount.execSQL("CREATE TABLE IF NOT EXISTS LocationCountListRest(StateCount VARCHAR,DistrictCount VARCHAR,TalukaCount VARCHAR,PanchayatCount VARCHAR,VillageCount VARCHAR,YearCount VARCHAR,MachineCount VARCHAR,MachineCostCount VARCHAR,Farmer_Count VARCHAR,Pond_Count VARCHAR,Sync_ID VARCHAR);");
        Cursor cursor = db_locationCount.rawQuery("SELECT * FROM LocationCountListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_locationCount.delete("LocationCountListRest", null, null);

        }
        db_locationCount.close();
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
            String SQLiteQuery = "INSERT INTO TalukListRest (TalukID, TalukName,Taluk_districtid)" +
                    " VALUES ('" + "0" + "','" + "Select" + "','" + "0" + "');";
            db_taluklist.execSQL(SQLiteQuery);
        }

        String SQLiteQuery = "INSERT INTO TalukListRest (TalukID, TalukName,Taluk_districtid)" +
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

    public void DBCreate_YeardetailsRest_insert_2SQLiteDB(String str_yearID, String str_yearname, int i) {
        SQLiteDatabase db_yearlist = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_yearlist.execSQL("CREATE TABLE IF NOT EXISTS YearListRest(YearID VARCHAR,YearName VARCHAR);");

        String SQLiteQuery = "INSERT INTO YearListRest (YearID, YearName)" +
                " VALUES ('" + str_yearID + "','" + str_yearname + "');";
        db_yearlist.execSQL(SQLiteQuery);

        Log.e("str_yearname DB", str_yearname);
        db_yearlist.close();
    }

    public void deleteYearRestTable_B4insertion() {

        SQLiteDatabase db_yearlist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_yearlist_delete.execSQL("CREATE TABLE IF NOT EXISTS YearListRest(YearID VARCHAR,YearName VARCHAR);");
        Cursor cursor = db_yearlist_delete.rawQuery("SELECT * FROM YearListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_yearlist_delete.delete("YearListRest", null, null);

        }
        db_yearlist_delete.close();
    }

    public void DBCreate_StudentdetailsRest_insert_2SQLiteDB(String AcademicID,String AcademicName,String AdmissionFee,String ApplicationNo,String BalanceFee,String BirthDate,String ClusterID,
                                                             String ClusterName,String CreatedDate,String Education,String FatherName,String Gender,String InstituteName,String InstituteID,String LevelID,String LevelName,String Marks4,String Marks5,String Marks6,String Marks7,String Marks8,
                                                             String Mobile,String MotherName,String PaidFee,String ReceiptNo,String SandboxID,String SandboxName,String SchoolID,String SchoolName,String StudentAadhar,String StudentID,String StudentName,String StudentPhoto,String StudentStatus, String str_base64image) {
        SQLiteDatabase db_studentDetails = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_studentDetails.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR," +
                "ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR," +
                "Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR);");

        String SQLiteQuery = "INSERT INTO StudentDetailsRest (AcademicID, AcademicName, AdmissionFee,ApplicationNo,BalanceFee,BirthDate,ClusterID," +
                "ClusterName,CreatedDate,Education,FatherName,Gender,InstituteName,InstituteID,LevelID,LevelName,Marks4,Marks5,Marks6,Marks7,Marks8," +
                "Mobile,MotherName,PaidFee,ReceiptNo,SandboxID,SandboxName,SchoolID,SchoolName,StudentAadhar,StudentID,StudentName,StudentPhoto,StudentStatus,Base64image)" +
                " VALUES ('" + AcademicID+ "','" + AcademicName+ "','" +AdmissionFee+ "','" +ApplicationNo+ "','" +BalanceFee+ "','" +BirthDate+ "','" +ClusterID+ "','" +
                ClusterName+ "','" +CreatedDate+ "','" +Education+ "','" +FatherName+ "','" +Gender+ "','" +InstituteName+ "','" +InstituteID+ "','" +LevelID+ "','" +LevelName+ "','" +Marks4+ "','" +Marks5+ "','" +Marks6+ "','" +Marks7+ "','" +Marks8+ "','" +
                Mobile+ "','" +MotherName+ "','" +PaidFee+ "','" +ReceiptNo+ "','" +SandboxID+ "','" +SandboxName+ "','" +SchoolID+ "','" +SchoolName+ "','" +StudentAadhar+ "','" +StudentID+ "','" +StudentName+ "','" +StudentPhoto+ "','" +StudentStatus +"','" +str_base64image +"');";
        db_studentDetails.execSQL(SQLiteQuery);

        //  Log.e("ApplicationNo DB", ApplicationNo);
        Log.e("StudentName DB", StudentName);
        Log.e("SandboxName DB", SandboxName);
//
        db_studentDetails.close();
    }


}
