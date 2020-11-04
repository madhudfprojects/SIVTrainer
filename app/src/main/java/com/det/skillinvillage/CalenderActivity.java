package com.det.skillinvillage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import com.det.skillinvillage.adapter.AndroidListAdapter;
import com.det.skillinvillage.adapter.CalendarAdapter;
import com.det.skillinvillage.util.CalendarCollection;
import com.det.skillinvillage.util.UserInfo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;
import static com.google.android.gcm.GCMBaseIntentService.TAG;


public class CalenderActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public GregorianCalendar cal_month, cal_month_copy;
    private CalendarAdapter cal_adapter;
    private TextView tv_month;

    boolean doubleBackToExitPressedOnce = false;

    String u1,p1;

    private ListView lv_android;
    private AndroidListAdapter list_adapter;

    // ArrayList<EventModule> eventModules=new ArrayList<>();

    String bookid,cohartName,fellowshipName,eventdate,start_time,userId,status,end_time,module_name,attendance;
    Boolean eventUpdate;
    ArrayList<String> bookId1;
    ArrayList<String> eventdate1;
    int sizearray;
    private SwipeRefreshLayout swipeLayout;
    ConnectionDetector internetDectector;
    Boolean isInternetPresent = false;
    //Button logout_bt;
    private Toolbar mTopToolbar;

    String Schedule_Status,Schedule_ID,Lavel_ID,Schedule_Date,End_Time,Start_Time,Schedule_Session,Subject_Name,Leason_Name,Lavel_Name,Cluster_Name,Institute_Name;
    ArrayList<UserInfo> arrayList = new ArrayList<UserInfo>();
    UserInfo[] userInfosarr;
    String str_loginuserID="",str_logout_count_Status;
    SharedPreferences sharedpref_loginuserid_Obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Schedules");

//        SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
//        str_loginuserID = myprefs.getString("login_userid", "nothing");

        sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        cal_adapter = new CalendarAdapter(this, cal_month, UserInfo.user_info_arr);

        swipeLayout = findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        tv_month = findViewById(R.id.tv_month);
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
        //   logout_bt=(Button) findViewById(R.id.logout);

     /*   SharedPreferences myprefs= getSharedPreferences("user", MODE_PRIVATE);
        //Toast.makeText(Instant.this,myprefs.getAll().toString(),Toast.LENGTH_LONG).show();
        u1= myprefs.getString("user1", "nothing");
        p1 = myprefs.getString("pwd", "nothing");*/

        ImageButton previous = findViewById(R.id.ib_prev);




        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        ImageButton next = findViewById(R.id.Ib_next);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });

        final GridView gridview = findViewById(R.id.gv_calendar);
        gridview.setAdapter(cal_adapter);
        //gridview.setClickable(false);


        //
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                v=v;
                ((CalendarAdapter) parent.getAdapter()).setSelected(v,position);
                String selectedGridDate = CalendarAdapter.day_string
                        .get(position);

                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*","");
                int gridvalue = Integer.parseInt(gridvalueString);

                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }
                Log.e("Tag_time","selectedGridDate="+selectedGridDate);
                Log.e("Tag_time","position="+position);
                ((CalendarAdapter) parent.getAdapter()).setSelected(v,position);

                ((CalendarAdapter) parent.getAdapter()).getPositionList(selectedGridDate, CalenderActivity.this);


            }

        });

   /*     EventModule.event_module_arr=new ArrayList<EventModule>() ;
        EventModule.event_module_arr.add(new EventModule("2018-04-01","8.30","9.30","Madhushree","DSF","L11","Module1","Fellowership1",true));
        EventModule.event_module_arr.add(new EventModule("2018-04-01","9.30","11.30","Xyz","DSF","L11","Module1","Fellowership1",true));
        EventModule.event_module_arr.add(new EventModule("2018-04-01","12.30","2.30","Abc","DSF","L11","Module1","Fellowership1",true));

        EventModule.event_module_arr.add(new EventModule("2018-04-02","8.30","9.30","Madhushree","DSF","L11","Module1","Fellowership1",true));
        EventModule.event_module_arr.add(new EventModule("2018-04-02","9.30","11.30","Xyz","DSF","L11","Module1","Fellowership1",false));
        EventModule.event_module_arr.add(new EventModule("2018-04-02","8.30","9.30","Abc","DSF","L11","Module1","Fellowership1",false));
*/
        CalendarCollection.date_collection_arr=new ArrayList<CalendarCollection>();
        CalendarCollection.date_collection_arr.add(new CalendarCollection("2018-04-01","DSF|L11|Test"));
        CalendarCollection.date_collection_arr.add(new CalendarCollection("2018-04-01","DFP|L12|Test"));
        CalendarCollection.date_collection_arr.add(new CalendarCollection("2018-04-06","DKF|L13|Test"));
        CalendarCollection.date_collection_arr.add(new CalendarCollection("2018-04-02","DSF|L14|Test"));

        //  AsyncCallWS2 task2=new AsyncCallWS2();
        //task2.execute();
    }

   /* public void logout(View view) {
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


    }*/

    @Override
    public void onRefresh() {

      /*  AsyncCallWS2 task=new AsyncCallWS2(CalenderActivity.this);
        task.execute();
        refreshCalendar();*/
        Intent i=new Intent(CalenderActivity.this,CalenderActivity.class);
        startActivity(i);
        swipeLayout.setRefreshing(false);
    }

    private class AsyncCallWS2 extends AsyncTask<String, Void, Void>
    {
        ProgressDialog dialog;

        Context context;

        @Override
        protected void onPreExecute()
        {
            Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i(TAG, "onProgressUpdate---tab2");
        }

        public AsyncCallWS2(CalenderActivity activity) {
            context =  activity;
            dialog = new ProgressDialog(activity);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i(TAG, "doInBackground");


            fetch_all_info(u1);
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            dialog.dismiss();
            Log.i(TAG, "onPostExecute");

            // startActivity(i);
            Date date = new Date();
            Log.i("Tag_time","date1="+date);
            // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String PresentDayStr=sdf.format(date);
            Log.i("Tag_time","PresentDayStr="+PresentDayStr);

            cal_adapter.getPositionList(PresentDayStr,CalenderActivity.this);
            finish();
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

/*
    public void fetch_all_info(String email) {
        String METHOD_NAME = "GetTrainerScheduleList";
        String SOAP_ACTION1 = "http://mis.detedu.org/GetTrainerScheduleList";

        try {
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.detedu.org/", METHOD_NAME);

            Log.i("email=", email);
            request.addProperty("Email", email);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE("http://mis.detedu.org/DETServices.asmx?WSDL");
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
                        SoapObject list = (SoapObject) response.getProperty(i);
                        status = list.getProperty("status").toString();

                     //   String a = list.getProperty("notes").toString().trim();
                        bookid = list.getProperty("booking_id").toString();
                        cohartName = list.getProperty("cohortname").toString();
                        fellowshipName = list.getProperty("fellowshipname").toString();
                        eventdate = list.getProperty("date").toString();
                        start_time = list.getProperty("start_time").toString();
                        userId = list.getProperty("user_id").toString();
                        end_time = list.getProperty("end_time").toString();

                        module_name = list.getProperty("module_name").toString();
                        attendance = list.getProperty("attendance").toString();
                        eventUpdate = true;

                        //   State cat = new State(c.getString("state_id"),c.getString("state_name"));
                        UserInfo userInfo = new UserInfo(bookid, userId, eventdate, start_time, cohartName, fellowshipName, eventUpdate, end_time, module_name, status, attendance);
                        arrayList.add(userInfo);

                        Log.e("TAG", "bookid=" + bookid + "module_name=" + module_name + "cohartName=" + cohartName + "fellowshipName=" + fellowshipName + "eventdate=" + eventdate + "start_time" + start_time);

                    }

                    final String[] items = new String[Count];
                    userInfosarr = new UserInfo[Count];
                    UserInfo obj = new UserInfo();

                    UserInfo.user_info_arr.clear();
                    for (int i = 0; i < response.getPropertyCount(); i++) {
                        bookid = arrayList.get(i).booking_id;
                        cohartName = arrayList.get(i).cohortname;
                        userId = arrayList.get(i).user_id;
                        eventUpdate = arrayList.get(i).eventUpdate;
                        start_time = arrayList.get(i).start_time;
                        fellowshipName = arrayList.get(i).fellowshipname;
                        eventdate = arrayList.get(i).date1;
                        end_time = arrayList.get(i).end_time;
                        status = arrayList.get(i).event_status;
                        module_name = arrayList.get(i).module_name;
                        attendance = arrayList.get(i).attendance;
                        obj.setBooking_id(bookid);
                        obj.setCohortname(cohartName);
                        obj.setDate1(eventdate);
                        obj.setFellowshipname(fellowshipName);
                        obj.setStart_time(start_time);
                        obj.setUser_id(userId);
                        obj.setEnd_time(end_time);
                        obj.setEvent_status(status);
                        obj.setModule_name(module_name);
                        obj.setAttendance(attendance);
                        userInfosarr[i] = obj;
                        //   UserInfo.user_info_arr =new ArrayList<UserInfo>() ;

                        UserInfo.user_info_arr.add(new UserInfo(bookid, userId, eventdate, start_time, cohartName, fellowshipName, eventUpdate, end_time, module_name, status, attendance));

                        Log.i("Tag", "items aa=" + arrayList.get(i).booking_id);

                    }

                    Log.i("Tag", "items=" + items.length);
                }

            } catch (Throwable t) {
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //		Toast.LENGTH_LONG).show();
                Log.e("request fail 5", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("Tag", "UnRegister Receiver Error" + " > " + t.getMessage());

        }

    }*/


    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1),
                    cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1),
                    cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) - 1);
        }

    }

    public void refreshCalendar() {
        cal_adapter.refreshDays();
        cal_adapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }

    @Override
    public void onBackPressed() {


    /*    Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }*/

        Intent i = new Intent(CalenderActivity.this, Activity_HomeScreen.class);
        startActivity(i);

        finish();
    }

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

                Class_SaveSharedPreference.setUserName(getApplicationContext(),"");
                deleteSandBoxTable_B4insertion();
                deleteAcademicTable_B4insertion();
                deleteClusterTable_B4insertion();
                deleteInstituteTable_B4insertion();
                deleteSchoolTable_B4insertion();
                deleteLevelTable_B4insertion();
                deleteLearningOptionTable_B4insertion();

                LogoutCountAsynctask logoutCountAsynctask=new LogoutCountAsynctask(CalenderActivity.this);
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
            }
            else{
                Toast.makeText(getApplicationContext(),"No Internet", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if(id== android.R.id.home) {
            //  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(CalenderActivity.this, Activity_HomeScreen.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public class LogoutCountAsynctask extends AsyncTask<String, Void, Void> {
        //ProgressDialog dialog;
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

        public LogoutCountAsynctask(CalenderActivity activity) {
            context = activity;
           // dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {


           // dialog.dismiss();
            if(!str_logout_count_Status.equals("")){
                if (str_logout_count_Status.equals("Success")) {
                    //Toast.makeText(CalenderActivity.this, "Success", Toast.LENGTH_SHORT).show();

                } else if (str_logout_count_Status.equals("Failure")){
                    //Toast.makeText(CalenderActivity.this, "Failure", Toast.LENGTH_SHORT).show();
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
