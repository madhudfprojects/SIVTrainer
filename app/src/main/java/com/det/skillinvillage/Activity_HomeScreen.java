package com.det.skillinvillage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.det.skillinvillage.adapter.CalendarAdapter;
import com.det.skillinvillage.util.UserInfo;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fstpage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Home");


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

                                Intent i = new Intent(Activity_HomeScreen.this, Activity_UserManual_DownloadPDF.class);
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

}
