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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;



import com.det.skillinvillage.adapter.AndroidListAdapter;
import com.det.skillinvillage.adapter.CalendarAdapter;
import com.det.skillinvillage.adapter.CardsAdapter;
import com.det.skillinvillage.adapter.EventlistAdapter;
import com.det.skillinvillage.util.Eventlist;
import com.det.skillinvillage.util.ListviewEvents;
import com.det.skillinvillage.util.UserInfo;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.key_schedulerid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_schedulerid;


public class EventListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ListView lv_android;
    private AndroidListAdapter list_adapter;
    ArrayList<Eventlist> eventlists;
    Eventlist eventlist;
    private static EventlistAdapter adapter;
    String scheduleId,datestr,stime,etime,cohortstr,classroomstr,modulestr,bookIdstr,fellowershipsrt,statusStr,attandenceStr;
    //  Boolean eventUpdate;
    private SwipeRefreshLayout swipeLayout;
    public CalendarAdapter cal_adapter1;
    String bookid, cohartName, fellowshipName, eventdate, start_time, userId, status, end_time, module_name, attendance;
    String Schedule_Status,Schedule_ID,Lavel_ID,Schedule_Date,End_Time,Start_Time,Schedule_Session,Subject_Name,Leason_Name,Lavel_Name,Cluster_Name,Institute_Name;

    ArrayList<UserInfo> arrayList = new ArrayList<UserInfo>();

    UserInfo[] userInfosarr;
    public GregorianCalendar cal_month, cal_month_copy;
    String u1;
    ConnectionDetector internetDectector;
    Boolean isInternetPresent = false;
    //  Button logout_bt;
    private Toolbar mTopToolbar;
    String str_loginuserID="", str_scheduleId;

    SharedPreferences sharedpref_loginuserid_Obj;
    SharedPreferences sharedpref_schedulerid_Obj;
    String str_logout_count_Status="",selected_scheduler_instituteID="",selected_scheduler_institute="";
    Spinner loadInstitute_SP;
    Class_Scheduler_Institute Objclass_scheduler_institute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
//        str_loginuserID = myprefs.getString("login_userid", "nothing");

        sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

//        SharedPreferences myprefs_scheduleId = this.getSharedPreferences("scheduleId", Context.MODE_PRIVATE);
//        str_scheduleId = myprefs_scheduleId.getString("scheduleId", "nothing");
		sharedpref_schedulerid_Obj=getSharedPreferences(sharedpreferenc_schedulerid, Context.MODE_PRIVATE);
		str_scheduleId = sharedpref_schedulerid_Obj.getString(key_schedulerid, "").trim();


        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();

        cal_adapter1 = new CalendarAdapter(this, cal_month, UserInfo.user_info_arr);
        swipeLayout = findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);

        Intent intent = getIntent();
        loadInstitute_SP= findViewById(R.id.loadInstitute_SP);
        ListView lv_eventlist = findViewById(R.id.lv_eventlist);
        //  TextView today_date = (TextView) findViewById(R.id.today_date);
        //   ImageView month_btn=(ImageView) findViewById(R.id.monthview);
        //    logout_bt=(Button) findViewById(R.id.logout);

       /* SharedPreferences myprefs = getSharedPreferences("user", MODE_PRIVATE);
        //Toast.makeText(Instant.this,myprefs.getAll().toString(),Toast.LENGTH_LONG).show();
        String prefName = myprefs.getString("user1", "");

        String prefName2 = myprefs.getString("pwd", "");

        u1 = prefName.toString();*/

        List<String> arr_scheduleId = intent.getStringArrayListExtra("arr_scheduleId");
        List<String> arr_date = intent.getStringArrayListExtra("arr_date");
        List<String> arr_stime = intent.getStringArrayListExtra("arr_stime");
        List<String> arr_etime = intent.getStringArrayListExtra("arr_etime");
        List<String> arr_bookId = intent.getStringArrayListExtra("arr_faclty");
        List<String> arr_cohort = intent.getStringArrayListExtra("arr_cohort");
        List<String> arr_classroom = intent.getStringArrayListExtra("arr_classroom"); //Leason_Name
        List<String> arr_module = intent.getStringArrayListExtra("arr_module");
        List<String> arr_schedSession = intent.getStringArrayListExtra("arr_schedSession");
        List<String> arr_status = intent.getStringArrayListExtra("arr_status");
        List<String> arr_attandence=intent.getStringArrayListExtra("arr_attandence");

        // boolean[] arr_eventUpdate = intent.getBooleanArrayExtra("arr_eventUpdate");

        Log.i("Tag", "Main-date=" + arr_date + "arr_module=" + arr_module);
        //  Log.i("Tagg", "arr_eventUpdate=" + arr_eventUpdate[0]);
        Log.i("tag", "Main-event=" + arr_stime  + "arr_bookId=" + arr_bookId + "arr_cohort=" + arr_cohort );

        String Schedule_Status,Schedule_ID,Lavel_ID,Schedule_Date,End_Time,Start_Time,Schedule_Session,Subject_Name;
        ArrayList<UserInfo> arrayList = new ArrayList<UserInfo>();
        UserInfo[] userInfosarr;

        CardsAdapter adapter = new CardsAdapter(this, ListviewEvents.listview_info_arr);

        for (int i = 0; i < arr_date.size(); i++) {
            scheduleId = arr_scheduleId.get(i);
            datestr = arr_date.get(i);
            stime = arr_stime.get(i);
            fellowershipsrt = arr_schedSession.get(i);
            //  etime="null";
            classroomstr="null";
            // modulestr="null";
            etime = arr_etime.get(i);
            bookIdstr = arr_bookId.get(i);
            cohortstr = arr_cohort.get(i);
            classroomstr = arr_classroom.get(i);    //Leason_Name
            modulestr = arr_module.get(i);
            //     eventUpdate = arr_eventUpdate[i];
            statusStr = arr_status.get(i);
            attandenceStr=arr_attandence.get(i);

            // eventUpdate=arr_eventUpdate[i];

            ListviewEvents.listview_info_arr.add(new ListviewEvents(scheduleId,datestr, stime,etime, bookIdstr, cohortstr, classroomstr, modulestr, fellowershipsrt,statusStr,attandenceStr));
            adapter.add(new ListviewEvents(scheduleId,datestr, stime,etime, bookIdstr, cohortstr, classroomstr, modulestr, fellowershipsrt,statusStr,attandenceStr));

            //    Log.i("Tag", "eventUpdate" + arr_eventUpdate.length);
            Log.i("Tag", "arr_stime.get(i).length()" + arr_stime.get(i).length());
         /*   if(arr_eventUpdate[i]==true){
                Log.i("arr_eventUpdate","arr_eventUpdate true=="+arr_eventUpdate[i]);
                lv_eventlist.setBackgroundColor(Color.parseColor("#A9F5D0"));
            }else if(arr_eventUpdate[i]==false){
                Log.i("arr_eventUpdate","arr_eventUpdate false=="+arr_eventUpdate[i]);
                lv_eventlist.setBackgroundColor(Color.parseColor("#F5A9A9"));
            }*/
            // date_tv.setText(date_str);
            //   event_tv.setText(event_msg_str);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            //  SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

            try {
                Date today = sdf1.parse(datestr);
                String today_dateStr = sdf.format(today);
                Log.i("Date","today_dateStr"+today_dateStr);
                getSupportActionBar().setTitle(today_dateStr);
                /// today_date.setText(today_dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            lv_eventlist.setAdapter(adapter);


        }
        Log.i("Tag","arr_stime="+arr_stime);
        arr_scheduleId.clear();
        arr_date.clear();
        arr_stime.clear();
        arr_etime.clear();
        arr_bookId.clear();
        arr_cohort.clear();
        arr_module.clear();
        arr_schedSession.clear();
        arr_status.clear();
        arr_attandence.clear();
        Log.i("Tag","arr_stime1="+arr_stime);
     /*   lv_eventlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //   String item = ((TextView)view).getText().toString();
               /* Intent intent = new Intent(MainActivity.this, EventUpdateActivity.class);
                intent.putExtra("cohortstr",cohortstr);
                intent.putExtra("classroomstr",classroomstr);
                intent.putExtra("modulestr",modulestr);
                startActivity(intent);*/

            /*    Intent i = new Intent(EventListActivity.this, Remarks.class);
               /* i.putExtra("EventDiscription", "23623|test test|TEST-DSF");
                i.putExtra("EventId", "23623|test test|TEST-DSF");
                i.putExtra("EventDate", "Monday, April 27, 9:30 – 11:30 AM  GMT+05:30");*/
               /*
                i.putExtra("EventDiscription", "23625|Orientation|HB17DSF001");
                i.putExtra("EventId", "23625|Orientation|HB17DSF001");
                i.putExtra("EventDate", "Friday, April 27, 6:00 – 7:00 AM  GMT+05:30");*/

            /*    i.putExtra("EventDiscription",bookIdstr);
                i.putExtra("EventId",bookIdstr);
                i.putExtra("EventDate",stime);
                i.putExtra("FellowshipName",fellowershipsrt);
                i.putExtra("CohortName",cohortstr);
                startActivity(i);
                Toast.makeText(getBaseContext(), "abc", Toast.LENGTH_LONG).show();
            }
        });*/


//        loadInstitute_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                Objclass_scheduler_institute = (Class_Scheduler_Institute) loadInstitute_SP.getSelectedItem();
//                selected_scheduler_instituteID = Objclass_scheduler_institute.getScheduler_inst_id();
//                selected_scheduler_institute = Objclass_scheduler_institute.getScheduler_inst_name();
//                Log.e("scheduler_instituteID", selected_scheduler_instituteID);
//                Log.e("scheduler_institute", selected_scheduler_institute);
//               // Update_id_feessummarytable(selected_instituteID);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


       /* month_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i  = new Intent (getApplicationContext(),CalendarView.class);
                startActivity(i);
            }
        });*/
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
    public void onBackPressed() {
        // your code.
        Intent i  = new Intent (getApplicationContext(),Activity_HomeScreen.class);
        startActivity(i);
        //   super.onBackPressed();
    }

    @Override
    public void onRefresh() {

        AsyncCallWS2 task=new AsyncCallWS2(EventListActivity.this);
        task.execute();
        swipeLayout.setRefreshing(false);
    }

    private class AsyncCallWS2 extends AsyncTask<String, Void, Void>
    {
        ProgressDialog dialog;

        Context context;

        @Override
        protected void onPreExecute()
        {
            Log.i("tag", "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("tag", "onProgressUpdate---tab2");
        }

        public AsyncCallWS2(EventListActivity activity) {
            context =  activity;
            dialog = new ProgressDialog(activity,R.style.AppCompatAlertDialogStyle);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("tag", "doInBackground");


            fetch_all_info(u1);
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            dialog.dismiss();
            Log.i("tag", "onPostExecute");

            // startActivity(i);
            Date date = new Date();
            Log.i("Tag_time","date1="+date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String PresentDayStr=sdf.format(date);
            Log.i("Tag_time","PresentDayStr="+PresentDayStr);

            cal_adapter1.getPositionList(PresentDayStr,EventListActivity.this);
            finish();
        }
    }

 /*   public void fetch_all_info(String email) {
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

                        String a = list.getProperty("notes").toString().trim();
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

    public void fetch_all_info(String email) {

        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "LoadScheduleEmployee";
        String Namespace = "http://mis.detedu.org:8089/", SOAP_ACTION1 = "http://mis.detedu.org:8089/LoadScheduleEmployee";

        try {
            //mis.leadcampus.org

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            if(!str_loginuserID.equals("")) {
                Log.i("User_ID=", str_loginuserID);
                request.addProperty("User_ID", str_loginuserID);//str_loginuserID
                Log.d("request :", request.toString());
            }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.schedule_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.monthview) {
            Intent i  = new Intent (getApplicationContext(),CalendarView.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.action_logout) {
            internetDectector = new ConnectionDetector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent) {

                Class_SaveSharedPreference.setUserName(getApplicationContext(),"");

                LogoutCountAsynctask logoutCountAsynctask=new LogoutCountAsynctask(EventListActivity.this);
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
            Intent i = new Intent(EventListActivity.this, Activity_HomeScreen.class);
            startActivity(i);
            finish();
            return true;
        }
        // break;
        return super.onOptionsItemSelected(item);
    }


    /////////////////////////////////////////////logoutcounts//////////////////


    private class LogoutCountAsynctask extends AsyncTask<String, Void, Void> {
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

        public LogoutCountAsynctask(EventListActivity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {


            dialog.dismiss();
            if(!str_logout_count_Status.equals("")){
                if (str_logout_count_Status.equals("Success")) {
                   // Toast.makeText(EventListActivity.this, "Success", Toast.LENGTH_SHORT).show();

                } else if (str_logout_count_Status.equals("Failure")){
                    //Toast.makeText(EventListActivity.this, "Failure", Toast.LENGTH_SHORT).show();
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
                    if (response.getProperty(0).toString().contains("Status=Success")) {
                        Log.e("str_logout_count_Status", "Success");

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









    public void DBCreate_SchedulerInstdetails_insert_2SQLiteDB(String str_instID, String str_instname) {
        SQLiteDatabase db_schedulerinstid = this.openOrCreateDatabase("scheduler_db", Context.MODE_PRIVATE, null);
        db_schedulerinstid.execSQL("CREATE TABLE IF NOT EXISTS Scheduler_InstList(Scheduler_InstIDList VARCHAR,Scheduler_InstName VARCHAR);");


        String SQLiteQuery = "INSERT INTO Scheduler_InstList (Scheduler_InstIDList, Scheduler_InstName)" +
                " VALUES ('" + str_instID + "','" + str_instname + "');";
        db_schedulerinstid.execSQL(SQLiteQuery);
        Log.e("str_instID DB", str_instID);
        Log.e("str_instname DB", str_instname);
        db_schedulerinstid.close();
    }



    public void deleteSchedulerInstituteTable_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("scheduler_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Scheduler_InstList(Scheduler_InstIDList VARCHAR,Scheduler_InstName VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM Scheduler_InstList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db1.delete("Scheduler_InstList", null, null);

        }
        db1.close();
    }


}
