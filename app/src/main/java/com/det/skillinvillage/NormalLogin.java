package com.det.skillinvillage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.AsyncTask;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.det.skillinvillage.util.UserInfo;
import com.google.firebase.iid.FirebaseInstanceId;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class NormalLogin extends AppCompatActivity implements View.OnClickListener {

    EditText username_et;
    EditText password_et;
    Button signin_bt;
    String str_username, str_pwd, login_userStatus="", login_userid, login_userEmail;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String[] permissions = new String[]{
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.CAMERA};

    String Schedule_Status,Schedule_ID,Lavel_ID,Schedule_Date,End_Time,Start_Time,Schedule_Session,Subject_Name,Leason_Name,Lavel_Name,Cluster_Name,Institute_Name;
    ArrayList<UserInfo> arrayList = new ArrayList<UserInfo>();
    UserInfo[] userInfosarr;

    String simOperatorName="",tmDevice="",mobileNumber="",tmSerial="",androidId="",
            deviceId="",deviceModelName="",deviceUSER="",devicePRODUCT="",
            deviceHARDWARE="",deviceBRAND="",myVersion="",sdkver="",regId="";
    int sdkVersion,Measuredwidth,Measuredheight;
    TelephonyManager tm1;
    String str_schedule_date;
    int versionCodes;
    String str_versioncode;
    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;



    SharedPreferences sharedpref_flag_Obj;
    public static final String sharedpreferenc_flag = "flag_sharedpreference";
    public static final String key_flag = "flag";
  //  SharedPreferences sharedpref_schedulerid_Obj;
    //SharedPreferences sharedpref_loginuserid_Obj;

    SharedPreferences sharedpref_username_Obj;
    public static final String sharedpreferenc_username = "googlelogin_name";
    public static final String Key_username = "name_googlelogin";


    SharedPreferences sharedpref_userimage_Obj;
    public static final String sharedpreferenc_userimage = "googlelogin_img";
    public static final String key_userimage = "profileimg_googlelogin";

    SharedPreferences sharedpref_loginuserid_Obj;
    public static final String sharedpreferenc_loginuserid = "userid";
    public static final String key_loginuserid = "login_userid";

    SharedPreferences sharedpref_schedulerid_Obj;
    public static final String sharedpreferenc_schedulerid = "scheduleId";
    public static final String key_schedulerid = "scheduleId";

    String str_profileimage,str_loginusername,str_loginuserid,str_schedulerid,str_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_login);
        SharedPreferences myprefs = getSharedPreferences("user", MODE_PRIVATE);

        sharedpref_username_Obj=getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
        str_loginusername = sharedpref_username_Obj.getString(Key_username, "").trim();

        sharedpref_userimage_Obj=getSharedPreferences(sharedpreferenc_userimage, Context.MODE_PRIVATE);
        str_profileimage = sharedpref_userimage_Obj.getString(key_userimage, "").trim();


        sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        str_loginuserid = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

        sharedpref_schedulerid_Obj=getSharedPreferences(sharedpreferenc_schedulerid, Context.MODE_PRIVATE);
        str_schedulerid = sharedpref_schedulerid_Obj.getString(key_schedulerid, "").trim();

        sharedpref_flag_Obj=getSharedPreferences(sharedpreferenc_flag, Context.MODE_PRIVATE);
        str_flag = sharedpref_flag_Obj.getString(key_flag, "").trim();

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        try {
            versionCodes = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        str_versioncode = Integer.toString(versionCodes);

        if (checkPermissions()) {
            //  permissions  granted.
        }

        username_et = findViewById(R.id.username_ET);
        password_et = findViewById(R.id.password_ET);
        signin_bt = findViewById(R.id.login_BT);

        signin_bt.setOnClickListener(NormalLogin.this);



    }

    @Override
    public void onClick(View view) {
        str_username = username_et.getText().toString();
        str_pwd = password_et.getText().toString();
        if (str_username.length() == 0) {
            Toast.makeText(NormalLogin.this, "Please Enter Your Email ID", Toast.LENGTH_SHORT).show();

        }
        if (str_username.length() < 1) {
            Toast.makeText(NormalLogin.this, "Please Enter Valid Email ID", Toast.LENGTH_SHORT).show();

        }

        if (!str_username.matches(emailPattern)) {
            Toast.makeText(NormalLogin.this, "Please Enter Valid Email ID", Toast.LENGTH_SHORT).show();

        }
        if (str_pwd.length() == 0) {
            Toast.makeText(NormalLogin.this, "Please Enter Your password", Toast.LENGTH_SHORT).show();

        }
        if (str_pwd.length() < 1) {
            Toast.makeText(NormalLogin.this, "Please Enter Valid password", Toast.LENGTH_SHORT).show();

        } else {
            internetDectector = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent) {


                AsyncCallWS_ValidateUser asyncCallWS_validateUser = new AsyncCallWS_ValidateUser(NormalLogin.this);
                asyncCallWS_validateUser.execute();

            }else{
                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();

            }

        }

    }

    private class AsyncCallWS_ValidateUser extends AsyncTask<String, Void, Void> {
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

            Log.e("tag","str_username="+str_username+" str_pwd="+str_pwd);
            Login_Verify(str_username, str_pwd);
            return null;
        }

        public AsyncCallWS_ValidateUser(NormalLogin activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {


            dialog.dismiss();
            if(!login_userStatus.equals("")){
            if (login_userStatus.equals("Active")) {
              //  Toast.makeText(NormalLogin.this, "Success", Toast.LENGTH_SHORT).show();
                AsyncCallWS2_Login task = new AsyncCallWS2_Login(NormalLogin.this);
                task.execute();

            } else if (login_userStatus.equals("No Records Found")){
                Toast.makeText(NormalLogin.this, "No Records Found", Toast.LENGTH_SHORT).show();

            }
            }else{
                Toast.makeText(NormalLogin.this, "No Records Found", Toast.LENGTH_SHORT).show();

            }

        }//end of onPostExecute
    }// end Async task

    public void Login_Verify(String username1, String password1) {
        Vector<SoapObject> result1 = null;

        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "ValidateNormalLogin";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/ValidateNormalLogin";

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("User_Email", username1);
            request.addProperty("User_Password", password1);

            Log.i("request", request.toString());

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

                if (response.getProperty(0).toString().contains("User_Status")) {
                    Log.e("login_userStatus", "hello");
                    if (response.getProperty(0).toString().contains("User_Status=Active")) {
                        Log.e("login_userStatusactive", "Active");

                        SoapPrimitive userid = (SoapPrimitive) obj2.getProperty("User_ID");
                        login_userid = userid.toString();
                        SoapPrimitive userEmail = (SoapPrimitive) obj2.getProperty("User_Email");
                        login_userEmail = userEmail.toString();
                        SoapPrimitive user_status = (SoapPrimitive) obj2.getProperty("User_Status");
                        login_userStatus = user_status.toString();
                        Log.e("login_userid", login_userid);
                        Log.e("login_userEmail", login_userEmail);
                        Log.e("login_userStatus", login_userStatus);


                    }
                } else {
                    Log.e("login_userStatus", "login_userStatus=null");

                }

            } catch (Throwable t) {
                Log.e("request fail", "> " + t.getMessage());
                login_userStatus = "slow internet";

            }
        } catch (Throwable t) {
            Log.e("UnRegister Receiver ", "> " + t.getMessage());

        }

    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

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

        public AsyncCallWS2_Login(NormalLogin activity) {
            context =  activity;
            //  dialog = new ProgressDialog(activity);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("tag", "doInBackground");

            fetch_all_info("raghavendra.tech@dfmail.org");

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

            Intent i = new Intent(NormalLogin.this, Activity_HomeScreen.class);

           /* SharedPreferences myprefs = NormalLogin.this.getSharedPreferences("user", MODE_PRIVATE);
            myprefs.edit().putString("login_userid", login_userid).apply();
            SharedPreferences myprefs_scheduleId = NormalLogin.this.getSharedPreferences("scheduleId", MODE_PRIVATE);
            myprefs_scheduleId.edit().putString("scheduleId", Schedule_ID).apply();*/
          /*  SharedPreferences myprefs_flag = NormalLogin.this.getSharedPreferences("flag", MODE_PRIVATE);
            myprefs_flag.edit().putString("flag", "1").apply();*/



            InsertDeviceDetailsAsynctask insertDeviceDetailsAsynctask=new InsertDeviceDetailsAsynctask(NormalLogin.this);
            insertDeviceDetailsAsynctask.execute();


            SharedPreferences.Editor  myprefs_loginuserid= sharedpref_loginuserid_Obj.edit();
            myprefs_loginuserid.putString(key_loginuserid, login_userid);
            myprefs_loginuserid.apply();


            SharedPreferences.Editor  myprefs_schedulerid= sharedpref_schedulerid_Obj.edit();
            myprefs_schedulerid.putString(key_schedulerid, Schedule_ID);
            myprefs_schedulerid.apply();

            SharedPreferences.Editor  myprefs_flag= sharedpref_flag_Obj.edit();
            myprefs_flag.putString(key_flag, "1");
            myprefs_flag.apply();

            startActivity(i);
           // finish();
        }
    }



    public void fetch_all_info(String email) {

        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "LoadScheduleEmployee";
        String Namespace = "http://mis.detedu.org:8089/", SOAP_ACTION1 = "http://mis.detedu.org:8089/LoadScheduleEmployee";

        try {
            //mis.leadcampus.org

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

            Log.i("User_ID=", login_userid);
            request.addProperty("User_ID", login_userid);
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

    private class InsertDeviceDetailsAsynctask extends AsyncTask<String, Void, Void> {
        // ProgressDialog dialog1;
        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
//            dialog1.setMessage("Please wait...");
//            dialog1.setCanceledOnTouchOutside(false);
//            dialog1.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @SuppressLint("MissingPermission")
        @Override
        protected Void doInBackground(String... params) {
            Log.i("df", "doInBackground");
            // Fetch Device info

       /* final TelephonyManager tm = (TelephonyManager) getBaseContext()
                .getSystemService(Context.TELEPHONY_SERVICE);*/

            tm1 = (TelephonyManager) getBaseContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);

            //   final String tmDevice, tmSerial, androidId;
            String NetworkType;
            //TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
            simOperatorName = tm1.getSimOperatorName();
            Log.v("Operator", "" + simOperatorName);
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

            Log.v("SIM_INTERNET_SPEED", "" + NetworkType);
//            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
            tmDevice = "" + tm1.getDeviceId();
            Log.v("DeviceIMEI", "" + tmDevice);
            mobileNumber = "" + tm1.getLine1Number();
            Log.v("getLine1Number value", "" + mobileNumber);

            String mobileNumber1 = "" + tm1.getPhoneType();
            Log.v("getPhoneType value", "" + mobileNumber1);
            tmSerial = "" + tm1.getSimSerialNumber();
            //  Log.v("GSM devices Serial Number[simcard] ", "" + tmSerial);
            androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
            Log.v("androidId CDMA devices", "" + androidId);
            UUID deviceUuid = new UUID(androidId.hashCode(),
                    ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            deviceId = deviceUuid.toString();
            //  Log.v("deviceIdUUID universally unique identifier", "" + deviceId);


            deviceModelName = android.os.Build.MODEL;
            Log.v("Model Name", "" + deviceModelName);
            deviceUSER = android.os.Build.USER;
            Log.v("Name USER", "" + deviceUSER);
            devicePRODUCT = android.os.Build.PRODUCT;
            Log.v("PRODUCT", "" + devicePRODUCT);
            deviceHARDWARE = android.os.Build.HARDWARE;
            Log.v("HARDWARE", "" + deviceHARDWARE);
            deviceBRAND = android.os.Build.BRAND;
            Log.v("BRAND", "" + deviceBRAND);
            myVersion = android.os.Build.VERSION.RELEASE;
            Log.v("VERSION.RELEASE", "" + myVersion);
            sdkVersion = android.os.Build.VERSION.SDK_INT;
            Log.v("VERSION.SDK_INT", "" + sdkVersion);
            sdkver = Integer.toString(sdkVersion);
            // Get display details

            Measuredwidth = 0;
            Measuredheight = 0;
            Point size = new Point();
            WindowManager w = getWindowManager();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                //   w.getDefaultDisplay().getSize(size);
//                Measuredwidth = w.getDefaultDisplay().getWidth();//size.x;
//                Measuredheight = w.getDefaultDisplay().getHeight();//size.y;


////////////////////////////////////////////////////////////////
                //Point size = new Point();
                getWindowManager().getDefaultDisplay().getSize(size);
                Measuredwidth = size.x;
                Measuredheight = size.y;
//////////////////////////////////////////////////////

            } else {
                Display d = w.getDefaultDisplay();
//                Measuredwidth = d.getWidth();
//                Measuredheight = d.getHeight();

                getWindowManager().getDefaultDisplay().getSize(size);
                Measuredwidth = size.x;
                Measuredheight = size.y;
//////////////////////////////////////////////////////

            }

            Log.v("SCREEN_Width", "" + Measuredwidth);
            Log.v("SCREEN_Height", "" + Measuredheight);


         //   regId = FirebaseInstanceId.getInstance().getToken();



            Log.e("regId_DeviceID", "" + regId);

/*<username>string</username>
      <DeviceId>string</DeviceId>
      <OSVersion>string</OSVersion>
      <Manufacturer>string</Manufacturer>
      <ModelNo>string</ModelNo>
      <SDKVersion>string</SDKVersion>
      <DeviceSrlNo>string</DeviceSrlNo>
      <ServiceProvider>string</ServiceProvider>
      <SIMSrlNo>string</SIMSrlNo>
      <DeviceWidth>string</DeviceWidth>
      <DeviceHeight>string</DeviceHeight>
      <AppVersion>string</AppVersion>*/


            setGCM1();
            return null;
        }

        public InsertDeviceDetailsAsynctask(NormalLogin activity) {
            context = activity;
            //dialog1 = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {


            // dialog1.dismiss();
        }//end of onPostExecute
    }// end Async task

    public void setGCM1() {

        if (2>1){
            // String WEBSERVICE_NAME = "http://dfhrms.cloudapp.net/PMSservice.asmx?WSDL";
            String SOAP_ACTION1 = "http://mis.detedu.org:8089/InsertDeviceDetails";
            String METHOD_NAME1 = "InsertDeviceDetails";
            String MAIN_NAMESPACE = "http://mis.detedu.org:8089/";
            //  String URI = Class_URL.URL_Login.toString().trim();

            String URI = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";


            SoapObject request = new SoapObject(MAIN_NAMESPACE, METHOD_NAME1);

            //	request.addProperty("LeadId", Password1);
            if(!login_userid.equals("")) {
                request.addProperty("User_ID", "13");//login_userid
            }
            request.addProperty("DeviceId", "fjhkuuMIrWM:APA91bHQF-e6lruETl4Tog5yI564J6HdRYPBNkmlDcwV6M-TRCIskUA-qh9yY9FjVnpCIx5sCRCb58h8sckItwzvbeu-Fw8fHf6zRGPFlSOCp9IgNpqq-Vf--xhHjuLwFJka3aW9rDUR");
            request.addProperty("OSVersion", "6.0.1");
            request.addProperty("Manufacturer", "Lenovo");
            request.addProperty("ModelNo", "Lenovo PB1-750M");
            request.addProperty("SDKVersion", "23");
            request.addProperty("DeviceSrlNo", "867721021796258");
            request.addProperty("ServiceProvider", "Ind-Jio");
            request.addProperty("SIMSrlNo", "89918610400008351299");
            request.addProperty("DeviceWidth", "720");
            request.addProperty("DeviceHeight", "1208");
            request.addProperty("AppVersion", "7");
            //request.addProperty("AppVersion","4.0");


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            // Set output SOAP object
            envelope.setOutputSoapObject(request);
            Log.e("deviceDetails Request","deviceDetail"+request.toString());
            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URI);
            try {

                androidHttpTransport.call(SOAP_ACTION1, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                System.out.println("Device Res"+response);

                Log.i("sending device detail", response.toString());

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("err",e.toString());
            }
        }






    }//end of GCM()
}





/*
https://stackoverflow.com/questions/11029205/ksoap2-android-receive-array-of-objects
 */