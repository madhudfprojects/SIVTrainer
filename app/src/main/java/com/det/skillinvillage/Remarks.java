package com.det.skillinvillage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.det.skillinvillage.adapter.CalendarAdapter;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.model.LearningMode;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;
import com.det.skillinvillage.util.Post_studData_Request;
import com.det.skillinvillage.util.StudentData_Response;
import com.det.skillinvillage.util.StudentData_ResponseList;
import com.det.skillinvillage.util.StudentInfoListRest;
import com.det.skillinvillage.util.StudentInfoRest;
import com.det.skillinvillage.util.SubjectList;
import com.det.skillinvillage.util.Subjects;
import com.det.skillinvillage.util.UserInfoListRest;
import com.det.skillinvillage.util.UserInfoRest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferencebook_usercredential;


public class Remarks extends AppCompatActivity {


    EditText mDesc;
    int j = 0, k = 0, j1 = 0, j2 = 0, j3 = 0, j4 = 0;
    int i1 = 0;
    String u1, p1, studentData = "No Data";
    EditText remark, event;
    Boolean isInternetPresent = false;
    Button submit, button2;
    String event_info, remarks_info, Event_date, result_of_submit, match_cohartName, match_fellowshipName, booking_id = "empty";
    public static String result_of_response;
    String Event_Discription = "", Event_Id = "", even_match = "no", match_bookid = "no", engage_status = "No", result_of_fetch = "NO", FellowshipName, Attandence = "1";
    String str_Leason_Name, str_Lavel_Name, str_Cluster_Name, str_Institute_Name, str_Subject_Name, str_ScheduleId;
    Switch status;
    TableLayout tl;
    LinearLayout ll_listview1;
    //    TextView student_header;
    LinearLayout student_header;
    StudentInfoListRest[] absentSudentList;
    StudentInfoListRest[] presentSudentList;
    StudentInfoListRest[] ConferencecallOptionList;
    StudentInfoListRest[] FacetoFaceOptionList;
    StudentInfoListRest[] ZoomOptionList;

    ArrayList<StudentInfoListRest> absentList, studList;
    StudentInfoListRest studentInfoObj;
    StudentInfoListRest[] unselectedAbsentStudent;
    SoapObject student_detail;
    StudentInfoRest verifyresponce;
    StudentInfoListRest[] studentlist;
    SubjectList[] subjectList_array;
    private ArrayList<String> arrLst_AbsentIds = new ArrayList<String>();
    private ArrayList<String> arrLst_PresentIds = new ArrayList<String>();
    private ArrayList<String> arrLst_ConCallIds = new ArrayList<String>();
    private ArrayList<String> arrLst_FaceIds = new ArrayList<String>();
    private ArrayList<String> arrLst_ZoomIds = new ArrayList<String>();

    String Ab_List="[",Prs_list="[",ConCall_list="[",Face_list="[",Zoom_list="[";
    String absent_studentId, present_studentId,conCall_studentId,Face_studentId,Zoom_studentId;
    String bookid, cohartName, fellowshipName, eventdate, start_time, userId, status_str, end_time, module_name, attendance;
    Boolean eventUpdate;
  /*  StudentInfo studentInfoObj1 = new StudentInfo();
    StudentInfo studentInfoObj2 = new StudentInfo();*/


    public CalendarAdapter cal_adapter1;
    public GregorianCalendar cal_month;

    public static final String MYPREF = "prefbook";
    //Sharedpreference to store valid offical mailID
    public static final String mypreferencemailID = "myprefmailID";
    public static final String NameKeymailID = "validmailID";
    SharedPreferences sharedprefvalidIDObj;

    public static final String SharedprefNovo = "prefNovoBook";
    public static final String NOVOKeyID = "keyID";
    SharedPreferences sharedprefnovoobj;
    Switch attendence;
    Spinner learningOption_sp;
    TableRow tr;

    String Schedule_Status, Schedule_ID, Lavel_ID, Schedule_Date, End_Time, Start_Time, Schedule_Session, Subject_Name, Leason_Name, Lavel_Name, Cluster_Name, Institute_Name;
    ArrayList<UserInfoListRest> arrayList = new ArrayList<UserInfoListRest>();
    UserInfoListRest[] userInfosarr;

    int studentCount;
    String str_loginuserID, str_scheduleId;
    // added by shivaleela on june 27th 2019
    String str_ScheduleId_new;

    SharedPreferences sharedpref_loginuserid_Obj;

    SharedPreferences sharedpref_schedulerid_Obj;
    public static final String sharedpreferenc_schedulerid = "scheduleId";
    public static final String key_schedulerid = "scheduleId";

    LearningMode[] Arrayclass_learningOption;

    Spinner subjectlist_SP;
    SubjectList Obj_subjectList;
    String sp_subject_ID;
    SharedPreferences sharedpreferencebook_usercredential_Obj;

    protected void onCreate(Bundle saveinstances) {
        super.onCreate(saveinstances);
        setContentView(R.layout.engage_class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Schedule Update");

        event = findViewById(R.id.event_ET);
        remark = findViewById(R.id.remarks);
        submit = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        event_info = event.getText().toString();
        remarks_info = remark.getText().toString();
        status = findViewById(R.id.mySwitch);
        tl = findViewById(R.id.studentlist);
        ll_listview1 = findViewById(R.id.ll_listview1);
        student_header = findViewById(R.id.student_header);
        subjectlist_SP = findViewById(R.id.subjectlist_SP);

        absentList = new ArrayList<>();
        studList = new ArrayList<>();
        Bundle extras = getIntent().getExtras();

        //tl.setVisibility(View.GONE);
//		SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
//		str_loginuserID = myprefs.getString("login_userid", "nothing");
//        sharedpref_loginuserid_Obj = getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
//        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();
        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_loginuserID = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();

        Log.e("str_loginuserID",str_loginuserID);
//		SharedPreferences myprefs_scheduleId = this.getSharedPreferences("scheduleId", Context.MODE_PRIVATE);
//		str_scheduleId = myprefs_scheduleId.getString("scheduleId", "nothing");

        sharedpref_schedulerid_Obj = getSharedPreferences(sharedpreferenc_schedulerid, Context.MODE_PRIVATE);
        str_scheduleId = sharedpref_schedulerid_Obj.getString(key_schedulerid, "").trim();

        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_adapter1 = new CalendarAdapter(this, cal_month, UserInfoListRest.user_info_arr);

        //    Toast.makeText(getApplicationContext(), "msgs:"+extras.getString("EventDiscription"), Toast.LENGTH_LONG).show();

        if (extras != null) {
		/*    	Event_Discription = extras.getString("EventDiscription");
		    	Event_Id = extras.getString("EventId");
		    	Event_date = extras.getString("EventDate");
				FellowshipName = extras.getString("FellowshipName");*/

            //Commented and added by shivaleela on june 27th 2019
            //str_ScheduleId = extras.getString("ScheduleId");
            str_ScheduleId_new = extras.getString("ScheduleId");
            Log.e("Tag", "str_ScheduleId_new=" + str_ScheduleId_new);
//*----------------
            str_Cluster_Name = extras.getString("Cluster_Name");
            str_Lavel_Name = extras.getString("Lavel_Name");
            str_Leason_Name = extras.getString("Leason_Name");
            str_Subject_Name = extras.getString("Subject_Name");
            str_Institute_Name = extras.getString("Institute_Name");


            //Attandence = extras.getString("Attandence");

        }

        //Log.e("Madhu","username u1="+u1+p1);
        mDesc = findViewById(R.id.event_ET);
        mDesc.setText(str_Subject_Name + " | " + str_Lavel_Name + " | " + str_Leason_Name + " | " + str_Cluster_Name + " | " + str_Institute_Name);
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        //   AsyncCallWS_learningMode task=new AsyncCallWS_learningMode(Remarks.this);
        //   task.execute();
        uploadfromDB_LearningOptionlist();
        Get_Schedule_Subject();
        AsyncCallWS2 task2 = new AsyncCallWS2();
        task2.execute();

        ll_listview1.setVisibility(View.GONE);

        status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    engage_status = "Yes";
                    Log.e("tag", "engage_status==" + engage_status);
                    ll_listview1.setVisibility(View.VISIBLE);
                    student_header.setVisibility(View.VISIBLE);
                    //student_header.setText("Student List");

                } else {
                    // The toggle is disabled
                    engage_status = "No";
                    Log.e("tag", "engage_status==" + engage_status);
                    ll_listview1.setVisibility(View.GONE);
                    student_header.setVisibility(View.GONE);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                remarks_info = remark.getText().toString();

                String Str_checkAlphabetic = remark.getText().toString().replaceAll("[^A-Za-z]+", "");


                if (remarks_info.length() == 0 || remarks_info == null) {
                    remark.setError("Empty is not allowed");
                    Toast.makeText(getApplicationContext(), "Enter the Remarks", Toast.LENGTH_SHORT).show();
                } else if (Str_checkAlphabetic.trim().length() == 0 || Str_checkAlphabetic.trim().length() <= 4) {
                    remark.setError("Minimum 5 Alphabetic Character Required");

                } else {
                    //  Intent i  = new Intent (getApplicationContext(),Slide_MainActivity.class);
                    //	startActivity(i);
                    //	finish();

                    if (isInternetPresent) {
                        if (status.isChecked()) {
                            engage_status = "Yes";
                            Log.e("tag", "engage_status==" + engage_status);
                            ll_listview1.setVisibility(View.VISIBLE);
                                /*Intent i  = new Intent (getApplicationContext(),Activity_LessonPlan.class);
                            	startActivity(i);
                            	finish();*/

                        } else {
                            engage_status = "No";
                            Log.e("tag", "engage_status==" + engage_status);
                            ll_listview1.setVisibility(View.GONE);

                           /* Intent i  = new Intent (getApplicationContext(),Activity_LessonPlan.class);
                            startActivity(i);
                            finish();*/

                        }

                        if (engage_status.equals("No")) {
                            alerts();
                        } else {
                            AsyncCallWS3 task3 = new AsyncCallWS3(Remarks.this);
                            task3.execute();
                        }

				/*		 Intent i  = new Intent (getApplicationContext(),Slide_MainActivity.class);
								startActivity(i);
								finish();*/

                    } else {
                        NormalUpdate(Remarks.this, "No Internet Connection", "Would you like to  close application.");
                    }
                    // TODO Auto-generated method stub
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                for (int p = 0; p < studentCount; p++) {
                    if (studentlist[p].getPre_Ab().equals("A")) {
                        absentSudentList[j++] = studentlist[p];
                    }
                }

                Log.e("tag", "j==" + j);

                for (int i = 0; i < j; i++) {
                    Log.e("tag", "absentSudentList[j]==" + absentSudentList[i].getStudentName());
                }
            }
        });

        subjectlist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {

                Obj_subjectList = (SubjectList) subjectlist_SP.getSelectedItem();
                sp_subject_ID = Obj_subjectList.getSubjectID();
                Log.e("sp_subject_ID",sp_subject_ID);
                // selected_academicname = subjectlist_SP.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void alerts() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, Class Not Taken ?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        AsyncCallWS3 task3 = new AsyncCallWS3(Remarks.this);
                        task3.execute();

                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  finish();
                dialog.dismiss();
            }
        });

        //	AlertDialog alertDialog = alertDialogBuilder.create();
        //	alertDialog.show();
        final AlertDialog alert = alertDialogBuilder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {

                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
            }
        });
        alert.show();
    }

    private class AsyncCallWS2 extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(Remarks.this, R.style.AppCompatAlertDialogStyle);

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait, Loading..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i("Madhu", "doInBackground");

            //GetAllEvents(u1,p1);
			/*fetch_all_info(u1,p1);
			if(even_match.equals("yes"))
			{
			fetchStudent();
		//	StorestudentData();
			}*/
            //fetchStudentNew();

            //Commented and added by shivaleela on june 27th 2019
            //if(!str_scheduleId.equals("null")){

            if (!str_ScheduleId_new.equals("null")) {
                Log.i("madhu", "not null");
                even_match = "yes";
                if (Attandence.equals("1")) {

                    //  fetchStudentNew();
                    Get_User_Schedule_Students();
                }

            } else {
                Log.i("madhu", "null");
            }


            return null;
        }

        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(Void result) {
            Log.i("madhu", "onPostExecute");
            if (dialog != null) {
                dialog.dismiss();
            }
            //  spinner.setVisibility(View.GONE);

        }
    }

    /* private class AsyncCallWS_learningMode extends AsyncTask<String, Void, Void> {
         ProgressDialog dialog;

         Context context;

         protected void onPreExecute() {

             dialog.setMessage("Please wait..");
             dialog.setCanceledOnTouchOutside(false);
             dialog.show();
         }

         @Override
         protected void onProgressUpdate(Void... values) {
         }

         @Override
         protected Void doInBackground(String... params) {
             Log.i("list", "doInBackground");

             list_detaile();  // call of details
             return null;
         }

         public AsyncCallWS_learningMode(Context context1) {
             context = context1;
             dialog = new ProgressDialog(context1, R.style.AppCompatAlertDialogStyle);
         }

         @Override
         protected void onPostExecute(Void result) {

             if ((dialog != null) && dialog.isShowing()) {
                 dialog.dismiss();
                *//* ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(Remarks.this,
                        android.R.layout.simple_spinner_dropdown_item, Arrayclass_learningOption);
                learningOption_sp.setAdapter(spinnerArrayAdapter);*//*
            }


        }//end of onPostExecute
    }// end Async task
*/
    /*public void list_detaile() {
        Vector<SoapObject> result1 = null;

        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "Learning_Mode_Options";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/Learning_Mode_Options";

        try {
         //   int userid = Integer.parseInt(str_loginuserID);
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
           // request.addProperty("User_ID", userid);//userid

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                Log.i("value at response", response.toString());
                int count1 = response.getPropertyCount();  // number of count in array in response 6,0-5,5

                Log.i("number of rows", "" + count1);
                Arrayclass_learningOption = new Class_LearningOption[count1];

                for (int i = 0; i < count1; i++) {
                    SoapObject obj2 =(SoapObject) response.getProperty(i);

                    SoapObject list = (SoapObject) response.getProperty(i);
                    SoapPrimitive response_soapobj_Option_ID,response_soapobj_Group_Name, response_soapobj_Option_Name,response_soapobj_Option_Status;


                    response_soapobj_Option_ID = (SoapPrimitive) obj2.getProperty("Option_ID");
                    response_soapobj_Group_Name = (SoapPrimitive) obj2.getProperty("Group_Name");
                    response_soapobj_Option_Name = (SoapPrimitive) obj2.getProperty("Option_Name");
                    response_soapobj_Option_Status = (SoapPrimitive) obj2.getProperty("Option_Status");


                    Class_LearningOption innerObj_Class_learning = new Class_LearningOption();
                    innerObj_Class_learning.setOption_ID(response_soapobj_Option_ID.toString());
                    innerObj_Class_learning.setGroup_Name(response_soapobj_Group_Name.toString());
                    innerObj_Class_learning.setOption_Name(response_soapobj_Option_Name.toString());
                    innerObj_Class_learning.setOption_Status(response_soapobj_Option_Status.toString());

                    Arrayclass_learningOption[i] = innerObj_Class_learning;


                    Log.e("tag","Option_ID="+response_soapobj_Option_ID);
                    Log.e("tag","Arrayclass_learningOption="+Arrayclass_learningOption[i].getOption_Name().toString());
                }// End of for loop


            } catch (Throwable t) {

                Log.e("requestload fail", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("UnRegisterload  Error", "> " + t.getMessage());

        }

    }//End of leaveDetail method
*/
    public void uploadfromDB_LearningOptionlist() {

        SQLiteDatabase db_village = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        // db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db_village.execSQL("CREATE TABLE IF NOT EXISTS LearningModeListRest(LearningModeID VARCHAR,LearningModeName VARCHAR);");
        Cursor cursor1 = db_village.rawQuery("SELECT DISTINCT * FROM LearningModeListRest", null);
        int x = cursor1.getCount();
        Log.e("cursor learingmodecount", Integer.toString(x));

        int i = 0;
        Arrayclass_learningOption = new LearningMode[x];
        if (cursor1.moveToFirst()) {

            do {
                LearningMode innerObj_Class_levelList = new LearningMode();
                innerObj_Class_levelList.setLearningMode_ID(cursor1.getString(cursor1.getColumnIndex("LearningModeID")));
                innerObj_Class_levelList.setLearningMode_Name(cursor1.getString(cursor1.getColumnIndex("LearningModeName")));
                // arrayObj_Class_learningmodeDetails2[i] = innerObj_Class_levelList;
                Arrayclass_learningOption[i] = innerObj_Class_levelList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_village.close();
       /* if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_learningmodeDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            learnoption_Spinner.setAdapter(dataAdapter);
            if (x > sel_learnmode) {
                learnoption_Spinner.setSelection(sel_learnmode);
            }
        }*/


    }
   /* public void fetchStudentNew() {
        //student_header.setText("Student List");
        Vector<SoapObject> result1 = null;
        //	 String URL = "http://detmis.cloudapp.net/DETServices.asmx";//"Login.asmx?WSDL";

        String URL = "http://mis.detedu.org:8089/SIVService.asmx";
        // String METHOD_NAME = "intCount";//"NewAppReleseDetails";
        // String Namespace="http://www.example.com", SOAPACTION="http://www.example.com/intCount";
        // String URL = "http://192.168.1.196:8080/deterp_ws/server4.php?wsdl";//"Login.asmx?WSDL";
        String METHOD_NAME = "LoadScheduleAttendace";//"NewAppReleseDetails";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/LoadScheduleAttendace";
        try {
            // String  versioncode = this.getPackageManager()
            //		    .getPackageInfo(this.getPackageName(), 0).versionName;
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            Log.i("tag", "Schedule_ID str_ScheduleId_new=" + str_ScheduleId_new);
            request.addProperty("Schedule_ID", str_ScheduleId_new);//331
            //request.addProperty("Schedule_ID", "334");
            //request.addProperty("CohortName", match_cohartName);
            //request.addProperty("CohortName", "12A");

            //	request.addProperty("to", 9);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            Log.e("madhu", "envelope=" + envelope);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);
                //	Log.i(TAG, "GetAllLoginDetails is running");
                //		result1 = (Vector<SoapObject>) envelope.getResponse();
                SoapObject response = (SoapObject) envelope.getResponse();
                // image = (SoapPrimitive)response.getProperty("cover");
                Log.i("madhu", "response=" + response.toString());

                student_detail = response;
                verifyresponce = (SoapObject) response.getProperty(0);
                //Log.i("madhu","verifyresponce1="+verifyresponce.getProperty("Message").toString());
                //  studentData= verifyresponce.getProperty("Message");
                // Create the image
				*//*if(verifyresponce.getProperty("Message").toString().equals("Error")){
					Log.i("madhu","verifyresponce2="+verifyresponce.getProperty("Message").toString());
					Toast.makeText(Remarks.this, "Student's List Not Found ", Toast.LENGTH_LONG).show();
				}
				else //if(!verifyresponce.getProperty("Message").toString().equals("No Data")||!verifyresponce.getProperty("Message").toString().equals("Error"))
				{*//*
                if (response.getPropertyCount() > 0) {
                    studentCount = response.getPropertyCount();
                    studentlist = new StudentInfo[response.getPropertyCount()];

                    for (int i = 0; i < studentCount; i++) {
                        SoapObject Onresponce = (SoapObject) response.getProperty(i);

                        StudentInfo Onestudent = new StudentInfo();
                        Onestudent.setstudId(Onresponce.getProperty("Student_ID").toString());
                        Log.e("tag", "Student_ID=" + Onestudent.getstudId());
                        Onestudent.setAttendance_Status(Onresponce.getProperty("Attendance_Status").toString());
                        Onestudent.setStudentname(Onresponce.getProperty("Student_Name").toString());
                        Onestudent.setStudEmail(Onresponce.getProperty("Student_Email").toString());
                        Onestudent.setApplication_No(Onresponce.getProperty("Application_No").toString());
                        Onestudent.setLearningOption(Onresponce.getProperty("Learning_Mode").toString());
                        //	Onestudent.se(Onresponce.getProperty("Student_Gender").toString());

							*//*if(!Onresponce.getProperty("studentMailId").toString().equals("anyType{}"))
							{
								Onestudent.setStudEmail(Onresponce.getProperty("studentMailId").toString()) ;
							}*//*
                        Onestudent.setPre_Ab("P");
                        // ash[i].setStatus(messeg1.getProperty("ProjectStatus").toString()) ;
                        studentlist[i] = Onestudent;
                        studentInfoObj = new StudentInfo();
                        studentInfoObj.setID(Onestudent.getID());
                        studentInfoObj.setStudentname(Onestudent.getStudentname());
                        studentInfoObj.setStudEmail(Onestudent.getStudEmail());
                        studentInfoObj.setstudId(Onestudent.getstudId());
                        studentInfoObj.setLearningOption(Onestudent.getLearningOption());
                        //studList.set(i).setID(Onestudent.getID());
                        studList.add(studentInfoObj);


                    }
                }
                //}
				*//*else{
					Toast.makeText(Remarks.this, "Student's List Not Found ", Toast.LENGTH_LONG).show();
				}*//*


            } catch (Throwable t) {
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //		Toast.LENGTH_LONG).show();
                Log.e("request fail", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("madhu", "UnRegister Receiver Error" + "> " + t.getMessage());

        }

    }*/

    public void Get_Schedule_Subject(){
        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();

        Log.e("tag","ScheduleId="+str_ScheduleId_new);
        Call<Subjects> call = userService.get_Schedule_Subject(str_ScheduleId_new);


        call.enqueue(new Callback<Subjects>() {
            @Override
            public void onResponse(Call<Subjects> call, Response<Subjects> response) {
                Log.e("response_schSubject", "response_schSubject: " + new Gson().toJson(response));

                if(response.isSuccessful())
                {
                    Subjects subjects = response.body();

                    Log.e("response schedule subj", response.body().getLst().toString());


                    if (subjects.getStatus().equals(true))
                    {

                        List<SubjectList> subjectLists = response.body().getLst();
                        Log.e("length", String.valueOf(subjectLists.size()));
                        int int_usercount=subjectLists.size();
                        subjectList_array = new SubjectList[int_usercount];
                        for(int i=0;i<int_usercount;i++)
                        {
                            Log.e("SubjectName",subjectLists.get(i).getSubjectName().toString());
                            SubjectList Onestudent = new SubjectList();
                            Onestudent.setSubjectID(subjectLists.get(i).getSubjectID().toString());
                            Log.e("tag", "Subject_ID=" + Onestudent.getSubjectID());
                            Onestudent.setSubjectName(subjectLists.get(i).getSubjectName().toString());

                            if(subjectLists.get(i).getSubjectID()==null||subjectLists.get(i).getSubjectID().equals("")){
                                Onestudent.setSubjectID("");
                            }else {
                                Onestudent.setSubjectID(subjectLists.get(i).getSubjectID().toString());
                            }
                            if(subjectLists.get(i).getSubjectName()==null||subjectLists.get(i).getSubjectName().equals("")){
                                Onestudent.setSubjectName("");
                            }else {
                                Onestudent.setSubjectName(subjectLists.get(i).getSubjectName().toString());
                            }
                            //	Toast.makeText(Remarks.this,even_match,Toast.LENGTH_LONG).show();
                            subjectList_array[i]=Onestudent;
                        }
                        ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, subjectList_array);
                        dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
                        subjectlist_SP.setAdapter(dataAdapter);

                    }
                    // Log.e("response.body", response.body().size);
                    subjectlist_SP.setSelection(getIndex_remarks(subjectlist_SP, str_Subject_Name));

                }



            }

            @Override
            public void onFailure(Call call, Throwable t) {

                Log.e("WS", "error" + t.getMessage());
                Toast.makeText(Remarks.this, "WS:" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Get_User_Schedule_Students(){
        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();

        Log.e("tag","ScheduleId="+str_ScheduleId_new);
        Call<StudentInfoRest> call = userService.get_User_Schedule_Students(str_ScheduleId_new);


        call.enqueue(new Callback<StudentInfoRest>() {
            @Override
            public void onResponse(Call<StudentInfoRest> call, Response<StudentInfoRest> response) {
                Log.e("response_schStud", "response_userschStud: " + new Gson().toJson(response));

               /* Class_gethelp_Response gethelp_response_obj = new Class_gethelp_Response();
                gethelp_response_obj = (Class_gethelp_Response) response.body();*/



                if(response.isSuccessful())
                {
                    StudentInfoRest studentInfoRest = response.body();
                    verifyresponce = response.body();
                    Log.e("response schedule schd", response.body().getListVersion().toString());


                    if (studentInfoRest.getStatus().equals(true))
                    {

                        List<StudentInfoListRest> studentInfoList = response.body().getListVersion();
                        Log.e("length", String.valueOf(studentInfoList.size()));
                        int int_usercount=studentInfoList.size();
                        studentlist = new StudentInfoListRest[int_usercount];
                        for(int i=0;i<int_usercount;i++)
                        {
                            Log.e("student name",studentInfoList.get(i).getStudentName().toString());
                            StudentInfoListRest Onestudent = new StudentInfoListRest();
                            Onestudent.setStudentID(studentInfoList.get(i).getStudentID().toString());
                            Log.e("tag", "Student_ID=" + Onestudent.getStudentID());
                            Onestudent.setAttendanceStatus(studentInfoList.get(i).getAttendanceStatus().toString());
                            Onestudent.setStudentName(studentInfoList.get(i).getStudentName().toString());
                            Onestudent.setStudentEmail(studentInfoList.get(i).getStudentEmail().toString());
                            //  Onestudent.setSubject_Name(studentInfoList.get(i).getSubject_Name().toString());

                            if(studentInfoList.get(i).getApplicationNo()==null||studentInfoList.get(i).getApplicationNo().equals("")){
                                Onestudent.setApplicationNo("");
                            }else {
                                Onestudent.setApplicationNo(studentInfoList.get(i).getApplicationNo().toString());
                            }
                            if(studentInfoList.get(i).getLearningMode()==null||studentInfoList.get(i).getLearningMode().equals("")){
                                Onestudent.setLearningMode("Zoom");
                            }else {
                                Onestudent.setLearningMode(studentInfoList.get(i).getLearningMode().toString());
                            }

                            Onestudent.setPre_Ab("P");
                            // ash[i].setStatus(messeg1.getProperty("ProjectStatus").toString()) ;
                            studentlist[i] = Onestudent;
                            studentInfoObj = new StudentInfoListRest();
                            studentInfoObj.set_id(Onestudent.get_id());
                            studentInfoObj.setStudentName(Onestudent.getStudentName());
                            studentInfoObj.setStudentEmail(Onestudent.getStudentEmail());
                            studentInfoObj.setStudentID(Onestudent.getStudentID());
                            studentInfoObj.setLearningMode(Onestudent.getLearningMode());
                            //studList.set(i).setID(Onestudent.getID());
                            studList.add(studentInfoObj);

                            if (Attandence.equals("1")) {
                                if (even_match.equals("no")) {
                                    NormalUpdate(Remarks.this, "Event Match", "No schedule matches");

                                } else {
                                    if (!even_match.equals("yes")) {
                                        NormalUpdate(Remarks.this, even_match, "Ntwork issue");
                                    } else {
                                        //	Log.e("madhu","verifyresponce"+verifyresponce.getProperty("Message").toString());
                                        //if (!verifyresponce.getProperty("Attendance_Status").toString().equals("Error")) {
                                        if (!Onestudent.getAttendanceStatus().equals("Error")) {
                                            if (!Onestudent.getAttendanceStatus().equals("No Data") || !Onestudent.getAttendanceStatus().equals("Error")) {
                                                if (int_usercount > 0) {
                                                    studentCount = int_usercount;


                                                    absentSudentList = new StudentInfoListRest[studentCount];
                                                    presentSudentList = new StudentInfoListRest[studentCount];
                                                    ZoomOptionList = new StudentInfoListRest[studentCount];
                                                    ConferencecallOptionList = new StudentInfoListRest[studentCount];
                                                    FacetoFaceOptionList = new StudentInfoListRest[studentCount];

                                                    unselectedAbsentStudent = new StudentInfoListRest[studentCount];
                                   /* ConferencecallOptionList = new  StudentInfo[studentCount];
                                    ConferencecallOptionList = new  StudentInfo[studentCount];
                                    ConferencecallOptionList = new  StudentInfo[studentCount];*/

                                                    //   for (int i = 0; i < studentCount; i++) {

                                                    String date = "hello";// get the first variable
                                                    String weight_kg = "hi";// get the second variable
                                                    // Create the table row
                                                    tr = new TableRow(Remarks.this);
                                                    //  tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT,1f));
                                                    // tr.setBackgroundResource(R.drawable.row_change);
                                                    tr.setId(i);
//                                        tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                                                    tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));

                                                    //Create two columns to add as table data
                                                    // Create a TextView to add date
                                                    learningOption_sp = new Spinner(Remarks.this);
                                                    //  learningOption_sp .setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));

                                                    ArrayAdapter<LearningMode> spinnerArrayAdapter = new ArrayAdapter<LearningMode>(Remarks.this,
                                                            R.layout.spinnercenterstyle, Arrayclass_learningOption);
                                                    learningOption_sp.setAdapter(spinnerArrayAdapter);

                                                    // learningOption_sp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                                    //ArrayAdapter arrayAdapter = new ArrayAdapter(Remarks.this, android.R.layout.simple_spinner_item, personNames);
                                                    //learningOption_sp.setAdapter(arrayAdapter);
                                                    learningOption_sp.setId(i);
                                                    String str_learningOpt=studentlist[i].getLearningMode();
                                                    Log.e("str_learningOpt","str_learningOpt");
                                                    Log.e("str_learningOpt",str_learningOpt);

                                                    learningOption_sp.setSelection(getIndex_remarks(learningOption_sp, str_learningOpt));
                                                    attendence = new Switch(Remarks.this);
                                                    attendence.setId(i);
                                                    attendence.setTextOn("A");
                                                    attendence.setTextOff("P");
                                                    //attendence.getThumbDrawable().setColorFilter(checked ? Color.BLACK : Color.WHITE, PorterDuff.Mode.MULTIPLY);
									/*
									        android:thumbTint="@color/blue"
        android:trackTint="@color/white"
									 */
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                        attendence.setShowText(true);
                                                    }

                                                    // TextView labelDATE = new TextView(Remarks.this);
                                                    //  labelDATE.setId(i);
                                                    //  labelDATE.setWidth(50);

                                                    //  labelDATE.setText(studentlist[i].getStudentID());
                                                    //  labelDATE.setTextColor(Color.YELLOW);
                                                    // labelDATE.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                                                    // tr.addView(labelDATE);
                                                    TextView labelWEIGHT = new TextView(Remarks.this);
                                                    //  labelWEIGHT.setBackgroundColor(R.drawable.button_change);
                                                    labelWEIGHT.setId(i);
//                                                    labelWEIGHT.setPaintFlags(labelWEIGHT.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                                    labelWEIGHT.setPaintFlags(labelWEIGHT.getPaintFlags());

                                                    labelWEIGHT.setText(studentlist[i].getStudentName());
                                                    labelWEIGHT.setWidth(300);


                                                    //   labelWEIGHT.setGravity(0x00800005);
                                                    labelWEIGHT.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                                                    tr.addView(labelWEIGHT);//student name
                                                    tr.addView(attendence);// persent or absent
                                                    tr.addView(learningOption_sp);// spinner learning option


                                                    final int finalI = i;
                                                    learningOption_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                        @Override
                                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                            String sel_option=Arrayclass_learningOption[position].toString();
                                                            studentlist[finalI].setLearningMode(sel_option);
                                                            //  Toast.makeText(Remarks.this, "Selected item:" + " " + Arrayclass_learningOption[position], Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onNothingSelected(AdapterView<?> parent) {
                                                        }
                                                    });

                                                    attendence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                        @Override
                                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                            Log.v("Switch State=", "" + isChecked);

                                                            //	Toast.makeText(Remarks.this,"Id: "+attendence.getId(), Toast.LENGTH_SHORT).show();
                                                            //	Toast.makeText(Remarks.this,"studentlist[finalI].getStudentname(): "+studentlist[finalI].getStudentname(), Toast.LENGTH_SHORT).show();

                                                            //  Toast.makeText(getApplicationContext(),""+isChecked, Toast.LENGTH_SHORT).show();
                                                            String value = "" + isChecked;
                                                            if (isChecked) {
                                                                studentlist[finalI].setPre_Ab("A");
                                                                //StudentInfo studentInfoObj=new StudentInfo();

                                                                Log.i("tag", "studentlist=" + studentlist[finalI].getPre_Ab());
                                                                //	absentList.set(j++).setID(absentList.get(finalI));
                                                                //	absentSudentList[j++] = studentlist[finalI];
                                                                Log.i("tag", "absentSudentList=" + absentSudentList.toString());
											/*	for(int t=0;t<absentList.size();t++) {
													Log.e("tag", "absentList1=" + absentList.get(t).getStudentname());
												}*/
                                                            }
                                                            if (!isChecked) {
                                                                studentlist[finalI].setPre_Ab("P");

                                                            }

                                                        }

                                                    });


                                                    tr.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                            TableRow t = (TableRow) v;
                                                            TextView firstTextView = (TextView) t.getChildAt(0);
                                                            TextView secondTextView = (TextView) t.getChildAt(1);
                                                            String firstText = firstTextView.getText().toString();
                                                            String secondText = secondTextView.getText().toString();
                                                            //  Toast.makeText(getApplicationContext(),"first Text ="+firstText, Toast.LENGTH_SHORT).show();
                                                            //   Toast.makeText(getApplicationContext(),"second Text ="+secondText, Toast.LENGTH_SHORT).show();


                                                        }
                                                    });

                                                    tl.addView(tr, new TableLayout.LayoutParams(
                                                            LayoutParams.WRAP_CONTENT,
                                                            LayoutParams.WRAP_CONTENT, 1f));

                                                    //} for loop
                                                }
                                            }
                                        } else {
                                            Toast.makeText(Remarks.this, "No students in this class", Toast.LENGTH_LONG).show();
                                        }

                                    }

                                }

                                //	Toast.makeText(Remarks.this,even_match,Toast.LENGTH_LONG).show();

                            }
                        }

                    }
                    // Log.e("response.body", response.body().size);

                }



            }

            @Override
            public void onFailure(Call call, Throwable t) {

                Log.e("WS", "error" + t.getMessage());
                Toast.makeText(Remarks.this, "WS:" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void UpdateStudentData()
    {
        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();

        for (int p = 0; p < studentCount; p++) {
            if (studentlist[p].getPre_Ab().equals("A")) {
                absentSudentList[j++] = studentlist[p];
            }
            if (studentlist[p].getPre_Ab().equals("P")) {
                presentSudentList[j1++] = studentlist[p];
            }
            if (studentlist[p].getLearningMode().equalsIgnoreCase("Conference call")){
                ConferencecallOptionList[j2++] = studentlist[p];
            }
            if (studentlist[p].getLearningMode().equalsIgnoreCase("Face to Face")||studentlist[p].getLearningMode().equalsIgnoreCase("Face to Face ")){
                FacetoFaceOptionList[j3++] = studentlist[p];
            }
            if (studentlist[p].getLearningMode().equalsIgnoreCase("Zoom")){
                ZoomOptionList[j4++] = studentlist[p];
            }

        }
        for (int i = 0; i < j; i++) {
            absent_studentId = absentSudentList[i].getStudentID();

            if (absent_studentId != null) {
                arrLst_AbsentIds.add(absent_studentId);
                Ab_List+=absent_studentId+",";
            }
        }
        for (int i1 = 0; i1 < j1; i1++) {
            present_studentId = presentSudentList[i1].getStudentID();

            if (present_studentId != null) {
                arrLst_PresentIds.add(present_studentId);
                Prs_list+=present_studentId+",";
            }
        }
        for (int i1 = 0; i1 < j2; i1++) {
            conCall_studentId = ConferencecallOptionList[i1].getStudentID();

            if (conCall_studentId != null) {
                arrLst_ConCallIds.add(conCall_studentId);
                ConCall_list+=conCall_studentId+",";
            }
        }
        for (int i1 = 0; i1 < j3; i1++) {
            Face_studentId = FacetoFaceOptionList[i1].getStudentID();

            if (Face_studentId != null) {
                arrLst_FaceIds.add(Face_studentId);
                Face_list+=Face_studentId+",";
            }
        }
        for (int i1 = 0; i1 < j4; i1++) {
            Zoom_studentId = ZoomOptionList[i1].getStudentID();

            if (Zoom_studentId != null) {
                arrLst_ZoomIds.add(Zoom_studentId);
                Zoom_list+=Zoom_studentId+",";
            }
        }

        Log.e("tag", "arrLst_AbsentIds=" + arrLst_AbsentIds);
        Log.e("tag", "arrLst_PresentIds=" + arrLst_PresentIds);
        Log.e("tag", "arrLst_ConCallIds=" + arrLst_ConCallIds);
        Log.e("tag", "arrLst_FaceIds=" + arrLst_FaceIds);
        Log.e("tag", "arrLst_ZoomIds=" + arrLst_ZoomIds);
        Log.e("tag", "Ab_List==" + Ab_List+"\"");

        String Status_class = "Pending";
        if (engage_status.equals("Yes")) {
            Status_class = "Taken";
        } else if (engage_status.equals("No")) {
            Status_class = "Not Taken";
        }

        Post_studData_Request request = new Post_studData_Request();
        request.setUser_ID(str_loginuserID);
        request.setSchedule_ID(str_ScheduleId_new);
        request.setSchedule_Status(Status_class);
        request.setRemarks(remarks_info);
        request.setSubject_ID(sp_subject_ID);
        request.setAbsent_Value(Ab_List+"]");
        request.setPresent_Value(Prs_list+"]");
        request.setZoom_Value(Zoom_list+"]");
        request.setFaceToFace_Value(Face_list+"]");
        request.setConferance_Value(ConCall_list+"]");
        request.setClassRoom_Value("");
        Log.e("tag","request=="+request);


        {
            Log.e("request_userschd", "request_userschd: " + new Gson().toJson(request));

            Call call = userService.post_ActionScheduleAttendance(request);

            call.enqueue(new Callback<StudentData_Response>()
            {
                @Override
                //  public void onResponse(retrofit2.Call<StudentData_Response> call, Response<StudentData_Response> response) {
                public void onResponse(Call<StudentData_Response> call, Response<StudentData_Response> response) {


                    Log.e("response", response.toString());
                    Log.e("response_body", String.valueOf(response.body()));

                    if (response.isSuccessful())
                    {
                        //  progressDoalog.dismiss();


                        StudentData_Response class_addfarmponddetailsresponse = response.body();
                        result_of_response= class_addfarmponddetailsresponse.getLst().getAttendanceStatus();
                        //result_of_response= "Taken"; //
                        Log.e("tag", "result_of_response=="+result_of_response);
                       /* if (class_addfarmponddetailsresponse.getStatus().equals("true"))
                        {
                            StudentData_ResponseList studentData_responseLists = response.body().getLst();
                            result_of_response=studentData_responseLists.getAttendanceStatus().toString();
                            Log.e("tag", "result_of_response=="+result_of_response);

                        } else if (class_addfarmponddetailsresponse.getStatus().equals("false")) {
                            //     progressDoalog.dismiss();
                            Toast.makeText(Remarks.this, class_addfarmponddetailsresponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
*/

                    } else {
                        //   progressDoalog.dismiss();
                        DefaultResponse error = ErrorUtils.parseError(response);
                        Log.e("result_of_responseerror", error.getMsg());
                        Toast.makeText(Remarks.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                    }
                    get_User_Schedule(result_of_response);

                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(Remarks.this, "error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("response_error", t.getMessage().toString());
                }
            });

        }
    }
    /*private void Update_studentData()
    {
        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();

        final ProgressDialog login_progressDoalog;
       *//* login_progressDoalog = new ProgressDialog(Remarks.this);
        login_progressDoalog.setMessage("Loading....");
        login_progressDoalog.setTitle("Please wait....");
        login_progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        login_progressDoalog.show();*//*

        JSONArray jsArrayAb = new JSONArray();
        JSONArray jsArrayPre = new JSONArray();
        JSONArray jsArrayConcall = new JSONArray();
        JSONArray jsArrayFace = new JSONArray();
        JSONArray jsArrayZoom = new JSONArray();

        for (int p = 0; p < studentCount; p++) {
            if (studentlist[p].getPre_Ab().equals("A")) {
                absentSudentList[j++] = studentlist[p];
            }
            if (studentlist[p].getPre_Ab().equals("P")) {
                presentSudentList[j1++] = studentlist[p];
            }
            if (studentlist[p].getLearningMode().equalsIgnoreCase("Conference call")){
                ConferencecallOptionList[j2++] = studentlist[p];
            }
            if (studentlist[p].getLearningMode().equalsIgnoreCase("Face to Face")||studentlist[p].getLearningMode().equalsIgnoreCase("Face to Face ")){
                FacetoFaceOptionList[j3++] = studentlist[p];
            }
            if (studentlist[p].getLearningMode().equalsIgnoreCase("Zoom")){
                ZoomOptionList[j4++] = studentlist[p];
            }

        }
        for (int i = 0; i < j; i++) {
            absent_studentId = absentSudentList[i].getStudentID();

            if (absent_studentId != null) {
                arrLst_AbsentIds.add(absent_studentId);
                Ab_List+=absent_studentId+",";
            }
        }
        for (int i1 = 0; i1 < j1; i1++) {
            present_studentId = presentSudentList[i1].getStudentID();

            if (present_studentId != null) {
                arrLst_PresentIds.add(present_studentId);
                Prs_list+=present_studentId+",";
            }
        }
        for (int i1 = 0; i1 < j2; i1++) {
            conCall_studentId = ConferencecallOptionList[i1].getStudentID();

            if (conCall_studentId != null) {
                arrLst_ConCallIds.add(conCall_studentId);
                ConCall_list+=conCall_studentId+",";
            }
        }
        for (int i1 = 0; i1 < j3; i1++) {
            Face_studentId = FacetoFaceOptionList[i1].getStudentID();

            if (Face_studentId != null) {
                arrLst_FaceIds.add(Face_studentId);
                Face_list+=Face_studentId+",";
            }
        }
        for (int i1 = 0; i1 < j4; i1++) {
            Zoom_studentId = ZoomOptionList[i1].getStudentID();

            if (Zoom_studentId != null) {
                arrLst_ZoomIds.add(Zoom_studentId);
                Zoom_list+=Zoom_studentId+",";
            }
        }
        jsArrayAb = new JSONArray(arrLst_AbsentIds);
        jsArrayPre = new JSONArray(arrLst_PresentIds);
        jsArrayConcall = new JSONArray(arrLst_ConCallIds);
        jsArrayFace = new JSONArray(arrLst_FaceIds);
        jsArrayZoom = new JSONArray(arrLst_ZoomIds);

        Log.e("tag", "arrLst_AbsentIds=" + arrLst_AbsentIds);
        Log.e("tag", "arrLst_PresentIds=" + arrLst_PresentIds);
        Log.e("tag", "arrLst_ConCallIds=" + arrLst_ConCallIds);
        Log.e("tag", "arrLst_FaceIds=" + arrLst_FaceIds);
        Log.e("tag", "arrLst_ZoomIds=" + arrLst_ZoomIds);
        Log.e("tag", "Ab_List==" + Ab_List+"\"");

        Log.e("tag", "jsArrayAb.toString()=" + jsArrayAb.toString());
        Log.e("tag", "jsArrayPre.toString()=" + jsArrayPre.toString());
        Log.e("tag", "jsArrayZoom.toString()=" + jsArrayZoom.toString());
        Log.e("tag", "jsArrayConcall.toString()=" + jsArrayConcall.toString());
        Log.e("tag", "jjsArrayFace.toString()=" + jsArrayFace.toString());

        String Status_class = "Pending";
        if (engage_status.equals("Yes")) {
            Status_class = "Taken";
        } else if (engage_status.equals("No")) {
            Status_class = "Not Taken";
        }

        Post_studData_Request request = new Post_studData_Request();
        request.setUser_ID(str_loginuserID);
        request.setSchedule_ID(str_ScheduleId_new);
        request.setSchedule_Status(Status_class);
        request.setRemarks(remarks_info);
        request.setAbsent_Value(Ab_List+"\"");
        request.setPresent_Value(Prs_list+"\"");
        request.setZoom_Value(Zoom_list+"\"");
        request.setFaceToFace_Value(Face_list+"\"");
        request.setConferance_Value(ConCall_list+"\"");
        request.setClassRoom_Value(null);
        Log.e("tag","request=="+request);
        Call call = userService.post_ActionScheduleAttendance(request);


        call.enqueue(new Callback()
        {
            @Override
            public void onResponse(Call call, Response response) {

                // Toast.makeText(MainActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();

                Log.e("response", response.toString());
                Log.e("response_body", String.valueOf(response.body()));
                if (response.isSuccessful())
                {
                    //  progressDoalog.dismiss();


                 //   Class_devicedetails class_addfarmponddetailsresponse = response.body();
*//*
                    if (class_addfarmponddetailsresponse.getStatus().equals("true"))
                    {
                        Log.e("devicedetails", "devicedetails_Added");

                        gethelp();

                    } else if (class_addfarmponddetailsresponse.getStatus().equals("false")) {
                        //     progressDoalog.dismiss();
                        Toast.makeText(Activity_HomeScreen.this, class_addfarmponddetailsresponse.getMessage(), Toast.LENGTH_SHORT).show();
                        gethelp();
                    }*//*
                } else {
                    //   progressDoalog.dismiss();
                    DefaultResponse error = ErrorUtils.parseError(response);
                    Log.e("student", error.getMsg());
//                    Toast.makeText(Activity_HomeScreen.this, error.getMsg(), Toast.LENGTH_SHORT).show();
//                    gethelp();


                }


            }

            @Override
            public void onFailure(Call call, Throwable t)
            {
              //  login_progressDoalog.dismiss();
                Log.e("WS","error: "+t.getMessage());
                Toast.makeText(Remarks.this, "WS:"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }*/
    private int getIndex_remarks(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {

// Log.e("spinner",spinner.getItemAtPosition(i).toString());
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;
    }


    private class AsyncCallWS3 extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        @Override
        protected void onPreExecute() {
            Log.i("madhu", "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //	Log.i("madhu", "onProgressUpdate---tab2");
        }

        public AsyncCallWS3(Remarks activity) {
            context = activity;
            //dialog = new ProgressDialog(activity, R.style.AppCompatAlertDialogStyle);
            dialog = new ProgressDialog(Remarks.this, R.style.AppCompatAlertDialogStyle);

        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i("madhu", "doInBackground");

			/*submit_status();
			fetch_all_info1(u1);
			if(engage_status.equals("Yes"))
			{
				if(Attandence.equals("1")){
			*/
            // StorestudentData();
            UpdateStudentData();
//            Log.e("result_of_response1", result_of_response);
            // fetch_all_info1(u1);
//            get_User_Schedule();
            //  Log.e("result_of_response2", result_of_response);
				/*}
			}*/

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog.isShowing()) {
                dialog.dismiss();

            }
            Log.i("madhu", "onPostExecute");

            /*if (result_of_response != null && result_of_response.equalsIgnoreCase("Taken") || result_of_response.equalsIgnoreCase("Not Taken")) {
                Toast.makeText(Remarks.this, "Successfull Submission", Toast.LENGTH_LONG).show();
                Date date = new Date();
                Log.i("Tag_time", "date1=" + date);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String PresentDayStr = sdf.format(date);
                Log.i("Tag_time", "PresentDayStr=" + PresentDayStr);

                cal_adapter1.getPositionList(PresentDayStr, Remarks.this);
            } else {
                Toast.makeText(Remarks.this, "Error while saving", Toast.LENGTH_LONG).show();
            }*/

			/* Intent i  = new Intent (getApplicationContext(),EventListActivity.class);

			    startActivity(i);
			    finish(); */

            //  spinner.setVisibility(View.GONE);

        }
    }

    public void get_User_Schedule(String response){

        result_of_response=response;
        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();

        Log.i("User_ID=", str_loginuserID);
        Call<UserInfoRest> call = userService.get_User_Schedule(str_loginuserID);

        call.enqueue(new Callback<UserInfoRest>() {
            @Override
            public void onResponse(Call<UserInfoRest> call, Response<UserInfoRest> response) {
                Log.e("response_userschd", "response_userschd: " + new Gson().toJson(response));

               /* Class_gethelp_Response gethelp_response_obj = new Class_gethelp_Response();
                gethelp_response_obj = (Class_gethelp_Response) response.body();*/



                if(response.isSuccessful())
                {
                    UserInfoRest userInfoRest = response.body();
                    Log.e("response.user schedule", response.body().getListVersion().toString());


                    if (userInfoRest.getStatus().equals(true))
                    {

                        List<UserInfoListRest> usershedulelist = response.body().getListVersion();
                        Log.e("length", String.valueOf(usershedulelist.size()));
                        int int_usercount=usershedulelist.size();

                        for(int i=0;i<int_usercount;i++)
                        {
                            Log.e("clus name",usershedulelist.get(i).getClusterName().toString());

                            Schedule_Status = usershedulelist.get(i).getScheduleStatus().toString();

                            Schedule_ID = usershedulelist.get(i).getScheduleID().toString();
                            Lavel_ID = usershedulelist.get(i).getLavelID().toString();
                            Schedule_Date = usershedulelist.get(i).getScheduleDate().toString();
                            End_Time = usershedulelist.get(i).getEndTime().toString();
                            Start_Time = usershedulelist.get(i).getStartTime().toString();
                            Subject_Name = usershedulelist.get(i).getSubjectName().toString();
                            Schedule_Session = usershedulelist.get(i).getScheduleSession().toString();
                            if(usershedulelist.get(i).getLeasonName()==null||usershedulelist.get(i).getLeasonName().equals("")){
                                Leason_Name="";
                            }else {
                                Leason_Name = usershedulelist.get(i).getLeasonName().toString();
                            }
                            Lavel_Name = usershedulelist.get(i).getLavelName().toString();
                            Institute_Name = usershedulelist.get(i).getInstituteName().toString();
                            Cluster_Name = usershedulelist.get(i).getClusterName().toString();

                            //   State cat = new State(c.getString("state_id"),c.getString("state_name"));
                            UserInfoListRest userInfo = new UserInfoListRest(Schedule_ID, Lavel_ID, Schedule_Date, End_Time, Start_Time, Schedule_Session,Schedule_Status,Subject_Name,Lavel_Name,Leason_Name,Cluster_Name,Institute_Name);
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

                            userInfosarr[i] = obj;
                            //   UserInfo.user_info_arr =new ArrayList<UserInfo>() ;

                            UserInfoListRest.user_info_arr.add(new UserInfoListRest(Schedule_ID, Lavel_ID, Schedule_Date, End_Time, Start_Time, Schedule_Session, Schedule_Status, Subject_Name, Lavel_Name, Leason_Name, Cluster_Name, Institute_Name));

                            Log.i("Tag", "items aa=" + arrayList.get(i).scheduleID);
                        }
                        //Data_from_HelpDetails_table();

                        //helplist.get(0).

                    }
                    // Log.e("response.body", response.body().size);

                }
                if (result_of_response != null && result_of_response.equalsIgnoreCase("Taken") || result_of_response.equalsIgnoreCase("Not Taken")) {
                    Toast.makeText(Remarks.this, "Successfull Submission", Toast.LENGTH_LONG).show();
                    Date date = new Date();
                    Log.i("Tag_time", "date1=" + date);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String PresentDayStr = sdf.format(date);
                    Log.i("Tag_time", "PresentDayStr=" + PresentDayStr);

                    cal_adapter1.getPositionList(PresentDayStr, Remarks.this);
                } else {
                    Toast.makeText(Remarks.this, "Error while saving", Toast.LENGTH_LONG).show();
                }



            }

            @Override
            public void onFailure(Call call, Throwable t) {

                Log.e("WS", "error" + t.getMessage());
                Toast.makeText(Remarks.this, "WS:" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    /*public void fetch_all_info1(String email) {
     *//*	Log.e("result fetch",result_of_submit);
		result_of_response=result_of_submit;*//*

        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "LoadScheduleEmployee";
        String Namespace = "http://mis.detedu.org:8089/", SOAP_ACTION1 = "http://mis.detedu.org:8089/LoadScheduleEmployee";

        try {
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.detedu.org:8089/", METHOD_NAME);

            //  Log.i("User_ID=", login_userid);
            request.addProperty("User_ID", str_loginuserID);//str_loginuserID

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
                        *//*  <Schedule_ID>int</Schedule_ID>
          <Lavel_ID>int</Lavel_ID>
          <Schedule_Date>string</Schedule_Date>
          <Start_Time>string</Start_Time>
          <End_Time>string</End_Time>
          <Schedule_Session>string</Schedule_Session>
          <Subject_Name>string</Subject_Name>
          <Schedule_Status>string</Schedule_Status>*//*


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
                        UserInfo userInfo = new UserInfo(Schedule_ID, Lavel_ID, Schedule_Date, End_Time, Start_Time, Schedule_Session, Schedule_Status, Subject_Name, Lavel_Name, Leason_Name, Cluster_Name, Institute_Name);
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

                        UserInfo.user_info_arr.add(new UserInfo(Schedule_ID, Lavel_ID, Schedule_Date, End_Time, Start_Time, Schedule_Session, Schedule_Status, Subject_Name, Lavel_Name, Leason_Name, Cluster_Name, Institute_Name));

                        Log.i("Tag", "items aa=" + arrayList.get(i).Schedule_ID);

                    }

                    Log.i("Tag", "items=" + items.length);
                }
                //  Log.e("TAG","bookid="+bookid+"cohartName="+cohartName+"fellowshipName="+fellowshipName+"eventdate="+eventdate+"start_time"+start_time);

            } catch (Throwable t) {
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //		Toast.LENGTH_LONG).show();
                Log.e("request fail 5", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("Tag", "UnRegister Receiver Error 5" + " > " + t.getMessage());

        }

    }*/



    //----------------------------------------------ASP Webservice edited by madhu----------------

    public void StorestudentData() {

//	String	engage_class1= status.getTag().toString();
        //	Vector<SoapObject> result1 = null;
        //	String URL = "http://elearning.detedu.org/detscheduler/webservices/Appwebservice/singleAbsentDetail.php";//"Login.asmx?WSDL";
        // String METHOD_NAME = "intCount";//"NewAppReleseDetails";
        // String Namespace="http://www.example.com", SOAPACTION="http://www.example.com/intCount";
        // String URL = "http://192.168.1.133:81/webservice/singleAbsentDetail.php?wsdl";//"Login.asmx?WSDL";
		/*String METHOD_NAME = "getUserInfo";//"NewAppReleseDetails";
		String Namespace="http://localhost:81", SOAPACTION="http://localhost:81/getUserInfo";*/

        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "UpdateScheduleAttendace";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/UpdateScheduleAttendace";
        JSONArray jsArrayAb = new JSONArray();
        JSONArray jsArrayPre = new JSONArray();
        JSONArray jsArrayConcall = new JSONArray();
        JSONArray jsArrayFace = new JSONArray();
        JSONArray jsArrayZoom = new JSONArray();

        for (int p = 0; p < studentCount; p++) {
            if (studentlist[p].getPre_Ab().equals("A")) {
                absentSudentList[j++] = studentlist[p];
            }
            if (studentlist[p].getPre_Ab().equals("P")) {
                presentSudentList[j1++] = studentlist[p];
            }
            if (studentlist[p].getLearningMode().equalsIgnoreCase("Conference call")){
                ConferencecallOptionList[j2++] = studentlist[p];
            }
            if (studentlist[p].getLearningMode().equalsIgnoreCase("Face to Face")||studentlist[p].getLearningMode().equalsIgnoreCase("Face to Face ")){
                FacetoFaceOptionList[j3++] = studentlist[p];
            }
            if (studentlist[p].getLearningMode().equalsIgnoreCase("Zoom")){
                ZoomOptionList[j4++] = studentlist[p];
            }
            //	Log.i("tag","present_studentId="+present_studentId + "absent_studentId="+absent_studentId);
		/*	if(!absent_studentId.equals("null")||absent_studentId!=null) {
				arrLst_AbsentIds.add(absent_studentId);
			}
			if(!present_studentId.equals("null")||present_studentId!=null) {
				arrLst_PresentIds.add(present_studentId);
			}*/
        }
        for (int i = 0; i < j; i++) {
            absent_studentId = absentSudentList[i].getStudentID();

            if (absent_studentId != null) {
                arrLst_AbsentIds.add(absent_studentId);
            }
        }
        for (int i1 = 0; i1 < j1; i1++) {
            present_studentId = presentSudentList[i1].getStudentID();

            if (present_studentId != null) {
                arrLst_PresentIds.add(present_studentId);
            }
        }
        for (int i1 = 0; i1 < j2; i1++) {
            conCall_studentId = ConferencecallOptionList[i1].getStudentID();

            if (conCall_studentId != null) {
                arrLst_ConCallIds.add(conCall_studentId);
            }
        }
        for (int i1 = 0; i1 < j3; i1++) {
            Face_studentId = FacetoFaceOptionList[i1].getStudentID();

            if (Face_studentId != null) {
                arrLst_FaceIds.add(Face_studentId);
            }
        }
        for (int i1 = 0; i1 < j4; i1++) {
            Zoom_studentId = ZoomOptionList[i1].getStudentID();

            if (Zoom_studentId != null) {
                arrLst_ZoomIds.add(Zoom_studentId);
            }
        }
        jsArrayAb = new JSONArray(arrLst_AbsentIds);
        jsArrayPre = new JSONArray(arrLst_PresentIds);
        jsArrayConcall = new JSONArray(arrLst_ConCallIds);
        jsArrayFace = new JSONArray(arrLst_FaceIds);
        jsArrayZoom = new JSONArray(arrLst_ZoomIds);

        Log.e("tag", "arrLst_AbsentIds=" + arrLst_AbsentIds);
        Log.e("tag", "arrLst_ConCallIds=" + arrLst_ConCallIds);
        Log.e("tag", "arrLst_FaceIds=" + arrLst_FaceIds);
        Log.e("tag", "arrLst_ZoomIds=" + arrLst_ZoomIds);

        String Status_class = "Pending";
        if (engage_status.equals("Yes")) {
            Status_class = "Taken";
        } else if (engage_status.equals("No")) {
            Status_class = "Not Taken";
        }
        try {
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);


            request.addProperty("User_ID", str_loginuserID);
            //Commented and added by shivaleela on june 27th 2019
            //request.addProperty("Schedule_ID", str_ScheduleId);

            request.addProperty("Schedule_ID", str_ScheduleId_new);
            //*--------------------------
            request.addProperty("Schedule_Status", Status_class);
            request.addProperty("Remarks", remarks_info);
            request.addProperty("Absent_Value", jsArrayAb.toString());
            request.addProperty("Present_Value", jsArrayPre.toString());
            request.addProperty("Zoom_Value", jsArrayZoom.toString());
            request.addProperty("FaceToFace_Value", jsArrayFace.toString());
            request.addProperty("Conferance_Value", jsArrayConcall.toString());


            Log.d("Request List=", request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);
                //	Log.i(TAG, "GetAllLoginDetails is running");
                //	result1 = (Vector<SoapObject>) envelope.getResponse();

                Log.e("madhu", "envelope response" + envelope.getResponse().toString());
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("madhu", "string value at response" + response.toString());

                //responseanyType{vmApiSchedulerAttendance=anyType{Student_ID=0; Attendance_Status=Taken; }; }  output

                SoapObject response1 = (SoapObject) response.getProperty("vmApiSchedulerAttendance");
                result_of_submit = response1.getProperty("Attendance_Status").toString().trim();
                Log.i("madhu", "response1 students=" + response1.toString());

                Log.i("madhu", "result_of_submit students=" + result_of_submit);
                result_of_response = result_of_submit;

            } catch (Throwable t) {
                Log.e("request fail", "> " + t.getMessage());

            }

        } catch (Throwable t) {
            Log.e("madhu", "UnRegister Receiver Error" + "> " + t.getMessage());

        }

    }


    public void submit_status() {

        String URL = "http://mis.detedu.org/DETServices.asmx?WSDL";
        String METHOD_NAME = "UpdateTrainerSchedule_New";
        String Namespace = "http://mis.detedu.org/", SOAPACTION = "http://mis.detedu.org/UpdateTrainerSchedule_New";

        //for final
        try {

            String Status_class = "0";
            if (engage_status.equals("Yes")) {
                Status_class = "1";
            } else if (engage_status.equals("No")) {
                Status_class = "2";
            }
            Log.i("madhu", "Status_class=" + Status_class);
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            // request.addProperty("distid", "0");//short
            request.addProperty("Schedule_ID", Event_Discription);
            request.addProperty("Status", Status_class);
            request.addProperty("Remarks", remarks_info);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);

                //SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("tag", "soap response Updateresponse" + response.toString());

                String status = response.toString();
                Log.e("tag", "status=" + response.toString());
            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("request fail", "> " + t.getMessage());

            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }
    }

    protected void NormalUpdate(Context context, String title, String msg1) {
        // TODO Auto-generated method stub
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setCancelable(true);
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(msg1);

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Add your code for the button here.
                //   Toast.makeText(getApplicationContext(), "well come", 1).show();
                dialog.dismiss();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                // TODO Auto-generated method stub
                // Toast.makeText(getApplicationContext(), "yoy have pressed cancel", 1).show();
                dialog.dismiss();
                finish();


            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu items
        getMenuInflater().inflate(R.menu.menu_register, menu);
        menu.findItem(R.id.addnewstudent_menu_id)
                .setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {


            case android.R.id.home:

				/*Intent i = new Intent(Remarks.this, EventListActivity.class);
				startActivity(i);*/
                Date date = new Date();
                Log.i("Tag_time", "date1=" + date);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String PresentDayStr = sdf.format(date);
                Log.i("Tag_time", "PresentDayStr=" + PresentDayStr);

                cal_adapter1.getPositionList(PresentDayStr, Remarks.this);
                finish();
                break;


            case R.id.save:

                remarks_info = remark.getText().toString();

                String Str_checkAlphabetic = remark.getText().toString().replaceAll("[^A-Za-z]+", "");


                if (remarks_info.length() == 0 || remarks_info == null) {
                    remark.setError("Empty is not allowed");
                    Toast.makeText(getApplicationContext(), "Enter the Remarks", Toast.LENGTH_SHORT).show();
                } else if (Str_checkAlphabetic.trim().length() == 0 || Str_checkAlphabetic.trim().length() <= 4) {
                    remark.setError("Minimum 5 Alphabetic Character Required");

                } else {
                    //  Intent i  = new Intent (getApplicationContext(),Slide_MainActivity.class);
                    //	startActivity(i);
                    //	finish();

                    if (isInternetPresent) {
                        if (status.isChecked()) {
                            engage_status = "Yes";
                            Log.e("tag", "engage_status==" + engage_status);
                            ll_listview1.setVisibility(View.VISIBLE);
                                /*Intent i  = new Intent (getApplicationContext(),Activity_LessonPlan.class);
                            	startActivity(i);
                            	finish();*/

                        } else {
                            engage_status = "No";
                            Log.e("tag", "engage_status==" + engage_status);
                            ll_listview1.setVisibility(View.GONE);

                           /* Intent i  = new Intent (getApplicationContext(),Activity_LessonPlan.class);
                            startActivity(i);
                            finish();*/

                        }

                        if (engage_status.equals("No")) {
                            alerts();
                        } else {
                            AsyncCallWS3 task3 = new AsyncCallWS3(Remarks.this);
                            task3.execute();
                        }

				/*		 Intent i  = new Intent (getApplicationContext(),Slide_MainActivity.class);
								startActivity(i);
								finish();*/

                    } else {
                        NormalUpdate(Remarks.this, "No Internet Connection", "Would you like to  close application.");
                    }
                    // TODO Auto-generated method stub
                }

                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
		/*Intent i = new Intent(Remarks.this, EventListActivity.class);
		startActivity(i);*/
        Date date = new Date();
        Log.i("Tag_time", "date1=" + date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String PresentDayStr = sdf.format(date);
        Log.i("Tag_time", "PresentDayStr=" + PresentDayStr);

        cal_adapter1.getPositionList(PresentDayStr, Remarks.this);
        finish();

    }

}