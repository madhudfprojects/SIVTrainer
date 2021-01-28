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
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.det.skillinvillage.model.Class_AssementList;
import com.det.skillinvillage.model.Class_dashboardList;
import com.det.skillinvillage.model.Class_getUserDashboardResponse;
import com.det.skillinvillage.model.Class_getassessmentlistResponse;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.Key_username;
import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.key_userimage;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_userimage;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_username;
import static com.det.skillinvillage.MainActivity.sharedpreferencebook_usercredential;

public class Activity_AssessmentList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeLayout;
    SharedPreferences sharedpref_loginuserid_Obj;
    SharedPreferences sharedpreferencebook_usercredential_Obj;
    String str_loginuserID = "";
    Class_Assessments_List[] userInfosarr, arrayObj_Class_ViewlistStudentData2, userInfosarr2;
    ArrayList<Class_Assessments_List> assessment_array_List = new ArrayList<Class_Assessments_List>();
    String str_Assesment_ID, str_Assesment_Name, str_Assesment_Date, str_Assesment_Status, str_Lavel_Name, str_Institute_ID = "", str_Institute_Name;
    ListView lv_eventlist;
    // AssessmentCardsAdapter adapter;
    //AssessmentCardsAdapter_new adapter2;
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;


    Spinner institute_assessment_SP, level_assessment_SP, Status_assessment_SP;
    Class_Assessment_Institute obj_Class_Assessment_institute;
    Class_Assessment_Institute[] arrayobjClass_Assessment_institute, arrayobjClass_Assessment_institute2;
    String selected_assessment_instituteName = "", sp_strAssessment_Inst_ID = "";
    Class_Assessment_Level obj_Class_Assessment_level;
    Class_Assessment_Level[] arrayobjClass_Assessment_level, arrayobjClass_Assessment_level2;
    String str_save = "", str_maxmarks = "", str_totalcount = "", str_presentcount = "", selected_assessment_status = "", str_level_ID = "", selected_assessment_levelName = "", sp_strAssessment_level_ID = "";
    Class_Assessment_Status obj_Class_Assessment_status;
    Class_Assessment_Status[] arrayobjClass_Assessment_status, arrayobjClass_Assessment_status3;
    String[] arrayobjClass_Assessment_status2 = {"All", "Completed", "Active"};


    Class_ViewAssessmentListview obj_class_ViewAssessmentListview;
    Class_ViewAssessmentListview[] arrayobjclass_ViewAssessmentListview, arrayobjclass_ViewAssessmentListview2;


    Interface_userservice userService1;
    Class_AssementList[] arrayObj_Class_monthcontents;
    SharedPreferences sharedpref_username_Obj;
    SharedPreferences sharedpref_userimage_Obj;
    String str_Googlelogin_Username = "", str_Googlelogin_UserImg = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__assessment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Assessment List");
        userService1 = Class_ApiUtils.getUserService();

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();
        // sharedpref_loginuserid_Obj = getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);

        sharedpreferencebook_usercredential_Obj = getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_loginuserID = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();
        sharedpref_username_Obj = getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
        str_Googlelogin_Username = sharedpref_username_Obj.getString(Key_username, "").trim();

        sharedpref_userimage_Obj = getSharedPreferences(sharedpreferenc_userimage, Context.MODE_PRIVATE);
        str_Googlelogin_UserImg = sharedpref_userimage_Obj.getString(key_userimage, "").trim();


        swipeLayout = findViewById(R.id.swipe_container_assessment);
        swipeLayout.setOnRefreshListener(this);
        lv_eventlist = findViewById(R.id.lv_assessmentlist);


        institute_assessment_SP = findViewById(R.id.institute_assessment_SP);
        level_assessment_SP = findViewById(R.id.level_assessment_SP);
        Status_assessment_SP = findViewById(R.id.Status_assessment_SP);
//        ArrayAdapter dataAdapter_status = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayobjClass_Assessment_status2);
//        dataAdapter_status.setDropDownViewResource(R.layout.spinnercenterstyle);
//        Status_assessment_SP.setAdapter(dataAdapter_status);

        //  adapter = new AssessmentCardsAdapter(this, Class_Assessments_List.assesmentlistview_info_arr);
        // adapter2 = new AssessmentCardsAdapter_new(this, Class_Assessments_List.assesmentlistview_info_arr_new);

        if (isInternetPresent) {
            deleteVIewlistTable_B4insertion();
            deleteInstiTable_B4insertion();
            deleteLevelTable_B4insertion();
            deleteStatusTable_B4insertion();
//            AsyncCall_GetAssessmentList task = new AsyncCall_GetAssessmentList(Activity_AssessmentList.this);
//            task.execute();

            getassessmentlist();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }


        institute_assessment_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {

                obj_Class_Assessment_institute = (Class_Assessment_Institute) institute_assessment_SP.getSelectedItem();
                sp_strAssessment_Inst_ID = obj_Class_Assessment_institute.getInstitute_id();
                selected_assessment_instituteName = institute_assessment_SP.getSelectedItem().toString();
                Log.i("selected_instituteName", " : " + selected_assessment_instituteName);

                Update_instid_InLeveltable_spinner(sp_strAssessment_Inst_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        level_assessment_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {

                obj_Class_Assessment_level = (Class_Assessment_Level) level_assessment_SP.getSelectedItem();
                sp_strAssessment_level_ID = obj_Class_Assessment_level.getLevel_assessmentid();
                selected_assessment_levelName = obj_Class_Assessment_level.getLevel_assessmentname();
                Log.i("selected_levelName", " : " + selected_assessment_levelName);
                Log.i("selecte_Fee", " : " + sp_strAssessment_level_ID);
                Update_instidlevelid_InStatus_spinner(sp_strAssessment_Inst_ID, sp_strAssessment_level_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Status_assessment_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                selected_assessment_status = Status_assessment_SP.getSelectedItem().toString();

                Update_ViewListID_spinner(sp_strAssessment_Inst_ID, sp_strAssessment_level_ID, selected_assessment_status);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onRefresh() {
        //   adapter = new AssessmentCardsAdapter(this, Class_Assessments_List.assesmentlistview_info_arr);

        if (isInternetPresent) {
            deleteVIewlistTable_B4insertion();
            deleteInstiTable_B4insertion();
            deleteLevelTable_B4insertion();
            deleteStatusTable_B4insertion();
//            AsyncCall_GetAssessmentList task = new AsyncCall_GetAssessmentList(Activity_AssessmentList.this);
//            task.execute();

            getassessmentlist();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }
        swipeLayout.setRefreshing(false);
    }

    private class AsyncCall_GetAssessmentList extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;
        Context context;

        @Override
        protected void onPreExecute() {
            Log.i("tag", "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i("tag", "onProgressUpdate---tab2");
        }

        public AsyncCall_GetAssessmentList(Activity_AssessmentList activity) {
            context = activity;
            dialog = new ProgressDialog(activity, R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i("tag", "doInBackground");
            fetch_all_info();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();
            Log.i("tag", "onPostExecute");
            if (str_Assesment_Status.equals("No Result")) {
                Toast.makeText(getApplicationContext(), "Sorry..No scheduled assignments", Toast.LENGTH_SHORT).show();
                finish();
            } else {

                //lv_eventlist.setAdapter(adapter);
                uploadfromDB_INstlist();
                uploadfromDB_levellist();
                uploadfromDB_statuslist();
                Uploadfrom_ViewList_spinner();

            }

        }
    }

    public void fetch_all_info() {

        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "GetUserAssesmentList";
        String Namespace = "http://mis.detedu.org:8089/", SOAP_ACTION1 = "http://mis.detedu.org:8089/GetUserAssesmentList";

        try {
            //mis.leadcampus.org

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            if (!str_loginuserID.equals("")) {
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
                    arrayobjClass_Assessment_institute = new Class_Assessment_Institute[Count];
                    arrayobjClass_Assessment_level = new Class_Assessment_Level[Count];
                    arrayobjClass_Assessment_status = new Class_Assessment_Status[Count];
                    arrayobjclass_ViewAssessmentListview = new Class_ViewAssessmentListview[Count];

                    for (int i = 0; i < Count; i++) {
                        SoapObject list = (SoapObject) response.getProperty(i);
                        str_Assesment_ID = list.getProperty("Assesment_ID").toString();
                        str_Assesment_Name = list.getProperty("Assesment_Name").toString();
                        str_Assesment_Date = list.getProperty("Assesment_Date").toString();
                        str_Assesment_Status = list.getProperty("Assesment_Status").toString();
                        str_Lavel_Name = list.getProperty("Lavel_Name").toString();
                        str_Institute_Name = list.getProperty("Institute_Name").toString();
                        str_Institute_ID = list.getProperty("Institute_ID").toString();
                        str_level_ID = list.getProperty("Level_ID").toString();
                        str_presentcount = list.getProperty("Present_Count").toString();
                        str_totalcount = list.getProperty("Total_Count").toString();
                        str_maxmarks = list.getProperty("Max_Marks").toString();

                        str_save = list.getProperty("Save").toString();


                        Class_Assessments_List userInfo = new Class_Assessments_List(str_Assesment_ID, str_Assesment_Name, str_Assesment_Date, str_Assesment_Status, str_Lavel_Name, str_Institute_Name, str_Institute_ID, str_level_ID, str_presentcount, str_totalcount, str_maxmarks, str_save);
                        assessment_array_List.add(userInfo);

                    }

                    final String[] items = new String[Count];
                    userInfosarr = new Class_Assessments_List[Count];
                    Class_Assessments_List obj = new Class_Assessments_List();

                    Class_Assessments_List.assesmentlistview_info_arr.clear();
                    for (int i = 0; i < response.getPropertyCount(); i++) {
                        str_Assesment_ID = assessment_array_List.get(i).getAssessmentID();
                        str_Assesment_Name = assessment_array_List.get(i).getAssessmentName();
                        str_Assesment_Date = assessment_array_List.get(i).getAssessmentDate();
                        str_Assesment_Status = assessment_array_List.get(i).getAssessmentStatus();
                        str_Lavel_Name = assessment_array_List.get(i).getAssessment_levelName();
                        str_Institute_Name = assessment_array_List.get(i).getAssessment_instituteName();
                        str_Institute_ID = assessment_array_List.get(i).getAssessment_instituteID();
                        str_level_ID = assessment_array_List.get(i).getAssessment_levelID();
                        str_presentcount = assessment_array_List.get(i).getAssessment_presentstudentcount();
                        str_totalcount = assessment_array_List.get(i).getAssessment_totalstudentcount();
                        str_maxmarks = assessment_array_List.get(i).getAssessment_maxmarks();
                        str_save = assessment_array_List.get(i).getAssessment_save();

                        Class_Assessment_Institute innerObj_Class_institute = new Class_Assessment_Institute();
                        innerObj_Class_institute.setInstitute_id(str_Institute_ID);
                        innerObj_Class_institute.setinstitute_assessment_name(str_Institute_Name);


                        Class_Assessment_Level innerObj_Class_level = new Class_Assessment_Level();
                        innerObj_Class_level.setInst_assessmentid(str_Institute_ID);
                        innerObj_Class_level.setLevel_assessmentid(str_level_ID);
                        innerObj_Class_level.setLevel_assessmentname(str_Lavel_Name);


                        Class_Assessment_Status innerObj_Class_status = new Class_Assessment_Status();
                        innerObj_Class_status.setAssessment_instituteID(str_Institute_ID);
                        innerObj_Class_status.setAssessment_levelID(str_level_ID);
                        innerObj_Class_status.setStatus(str_Assesment_Status);

                        Class_ViewAssessmentListview innerObjClass_ViewAssessmentListview = new Class_ViewAssessmentListview();
                        innerObjClass_ViewAssessmentListview.setAssessment_instituteID(str_Institute_ID);
                        innerObjClass_ViewAssessmentListview.setAssessment_instituteName(str_Institute_Name);
                        innerObjClass_ViewAssessmentListview.setAssessment_levelID(str_level_ID);
                        innerObjClass_ViewAssessmentListview.setAssessment_levelName(str_Lavel_Name);
                        innerObjClass_ViewAssessmentListview.setAssessment_presentstudentcount(str_presentcount);
                        innerObjClass_ViewAssessmentListview.setAssessment_totalstudentcount(str_totalcount);
                        innerObjClass_ViewAssessmentListview.setAssessment_maxmarks(str_maxmarks);
                        innerObjClass_ViewAssessmentListview.setAssessmentDate(str_Assesment_Date);
                        innerObjClass_ViewAssessmentListview.setAssessmentID(str_Assesment_ID);
                        innerObjClass_ViewAssessmentListview.setAssessmentName(str_Assesment_Name);
                        innerObjClass_ViewAssessmentListview.setAssessmentStatus(str_Assesment_Status);
                        innerObjClass_ViewAssessmentListview.setAssessment_save(str_save);


                        obj.setAssessmentID(str_Assesment_ID);
                        obj.setAssessmentName(str_Assesment_Name);
                        obj.setAssessmentDate(str_Assesment_Date);
                        obj.setAssessmentStatus(str_Assesment_Status);
                        obj.setAssessment_levelName(str_Lavel_Name);
                        obj.setAssessment_instituteID(str_Institute_ID);
                        obj.setAssessment_instituteName(str_Institute_Name);
                        obj.setAssessment_levelID(str_level_ID);
                        obj.setAssessment_presentstudentcount(str_presentcount);
                        obj.setAssessment_totalstudentcount(str_totalcount);
                        obj.setAssessment_maxmarks(str_maxmarks);
                        obj.setAssessment_save(str_save);


                        userInfosarr[i] = obj;
                        arrayobjClass_Assessment_institute[i] = innerObj_Class_institute;
                        arrayobjClass_Assessment_level[i] = innerObj_Class_level;
                        arrayobjClass_Assessment_status[i] = innerObj_Class_status;
                        arrayobjclass_ViewAssessmentListview[i] = innerObjClass_ViewAssessmentListview;


                        Log.i("Tag", "items aa=" + assessment_array_List.get(i).getAssessment_levelName());
                        Class_Assessments_List.assesmentlistview_info_arr.add(new Class_Assessments_List(str_Assesment_ID, str_Assesment_Name, str_Assesment_Date, str_Assesment_Status, str_Lavel_Name, str_Institute_Name, str_Institute_ID, str_level_ID, str_presentcount, str_totalcount, str_maxmarks, str_save));
                        // adapter.add(new Class_Assessments_List(str_Assesment_ID, str_Assesment_Name, str_Assesment_Date, str_Assesment_Status, str_Lavel_Name, str_Institute_Name,str_Institute_ID,str_level_ID,str_presentcount,str_totalcount,str_maxmarks,str_save));


                        DBCreate_INstdetails_insert_2SQLiteDB(str_Institute_ID, str_Institute_Name);
                        DBCreate_Leveldetails_insert_2SQLiteDB(str_Institute_ID, str_level_ID, str_Lavel_Name);
                        DBCreate_Statusdetails_insert_2SQLiteDB(str_Institute_ID, str_level_ID, str_Assesment_Status);
                        DBCreate_ViewListdetails_insert_2SQLiteDB(str_Institute_ID, str_level_ID, str_Assesment_Status, str_Assesment_ID, str_Assesment_Name, str_Assesment_Date, str_presentcount, str_totalcount, str_maxmarks, str_Lavel_Name, str_Institute_Name, str_save);
                    }

                    Log.i("Tag", "items=" + items.length);
                }

            } catch (Throwable t) {
                Log.e("request fail 5", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("Tag", "UnRegister Receiver Error 5" + " > " + t.getMessage());

        }

    }
//=======================Added by shivaleela on Nov 9th 2020

    public void getassessmentlist() {

        Call<Class_getassessmentlistResponse> call = userService1.GetAssesmentList(str_loginuserID);
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_AssessmentList.this);
        //  progressDoalog.setMax(100);
        //  progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<Class_getassessmentlistResponse>() {
            @Override
            public void onResponse(Call<Class_getassessmentlistResponse> call, Response<Class_getassessmentlistResponse> response) {
                Log.e("Entered resp", "getassessmentlist");

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    Class_getassessmentlistResponse class_loginresponse = response.body();
                    if (class_loginresponse.getStatus()) {
                        List<Class_AssementList> monthContents_list = response.body().getLst();
                        Log.e("size", String.valueOf(monthContents_list.size()));

                        arrayObj_Class_monthcontents = new Class_AssementList[monthContents_list.size()];
                        arrayobjClass_Assessment_institute = new Class_Assessment_Institute[arrayObj_Class_monthcontents.length];
                        arrayobjClass_Assessment_level = new Class_Assessment_Level[arrayObj_Class_monthcontents.length];
                        arrayobjClass_Assessment_status = new Class_Assessment_Status[arrayObj_Class_monthcontents.length];
                        arrayobjclass_ViewAssessmentListview = new Class_ViewAssessmentListview[arrayObj_Class_monthcontents.length];


                        for (int i = 0; i < arrayObj_Class_monthcontents.length; i++) {
                            Log.e("getassessmentlist", String.valueOf(class_loginresponse.getStatus()));
                            Log.e("getassessmentlist", class_loginresponse.getMessage());
                            str_Assesment_ID = String.valueOf(class_loginresponse.getLst().get(i).getAssesmentID());
                            str_Assesment_Name = class_loginresponse.getLst().get(i).getAssesmentName();
                            str_Assesment_Date = class_loginresponse.getLst().get(i).getAssesmentDate();
                            str_Assesment_Status = class_loginresponse.getLst().get(i).getAssesmentStatus();
                            str_Lavel_Name = class_loginresponse.getLst().get(i).getLavelName();
                            str_Institute_Name = class_loginresponse.getLst().get(i).getInstituteName();
                            str_Institute_ID = String.valueOf(class_loginresponse.getLst().get(i).getInstituteID());
                            str_level_ID = String.valueOf(class_loginresponse.getLst().get(i).getLevelID());
                            str_presentcount = class_loginresponse.getLst().get(i).getPresentCount();
                            str_totalcount = class_loginresponse.getLst().get(i).getTotalCount();
                            str_maxmarks = class_loginresponse.getLst().get(i).getMaxMarks();
                            str_save = class_loginresponse.getLst().get(i).getSave();

                            Class_Assessments_List userInfo = new Class_Assessments_List(str_Assesment_ID, str_Assesment_Name, str_Assesment_Date, str_Assesment_Status, str_Lavel_Name, str_Institute_Name, str_Institute_ID, str_level_ID, str_presentcount, str_totalcount, str_maxmarks, str_save);
                            assessment_array_List.add(userInfo);
                        }

                        final String[] items = new String[arrayObj_Class_monthcontents.length];
                        userInfosarr = new Class_Assessments_List[arrayObj_Class_monthcontents.length];
                        Class_Assessments_List obj = new Class_Assessments_List();

                        Class_Assessments_List.assesmentlistview_info_arr.clear();
                        for (int i = 0; i < arrayObj_Class_monthcontents.length; i++) {
                            str_Assesment_ID = assessment_array_List.get(i).getAssessmentID();
                            str_Assesment_Name = assessment_array_List.get(i).getAssessmentName();
                            str_Assesment_Date = assessment_array_List.get(i).getAssessmentDate();
                            str_Assesment_Status = assessment_array_List.get(i).getAssessmentStatus();
                            str_Lavel_Name = assessment_array_List.get(i).getAssessment_levelName();
                            str_Institute_Name = assessment_array_List.get(i).getAssessment_instituteName();
                            str_Institute_ID = assessment_array_List.get(i).getAssessment_instituteID();
                            str_level_ID = assessment_array_List.get(i).getAssessment_levelID();
                            str_presentcount = assessment_array_List.get(i).getAssessment_presentstudentcount();
                            str_totalcount = assessment_array_List.get(i).getAssessment_totalstudentcount();
                            str_maxmarks = assessment_array_List.get(i).getAssessment_maxmarks();
                            str_save = assessment_array_List.get(i).getAssessment_save();

                            Class_Assessment_Institute innerObj_Class_institute = new Class_Assessment_Institute();
                            innerObj_Class_institute.setInstitute_id(str_Institute_ID);
                            innerObj_Class_institute.setinstitute_assessment_name(str_Institute_Name);


                            Class_Assessment_Level innerObj_Class_level = new Class_Assessment_Level();
                            innerObj_Class_level.setInst_assessmentid(str_Institute_ID);
                            innerObj_Class_level.setLevel_assessmentid(str_level_ID);
                            innerObj_Class_level.setLevel_assessmentname(str_Lavel_Name);


                            Class_Assessment_Status innerObj_Class_status = new Class_Assessment_Status();
                            innerObj_Class_status.setAssessment_instituteID(str_Institute_ID);
                            innerObj_Class_status.setAssessment_levelID(str_level_ID);
                            innerObj_Class_status.setStatus(str_Assesment_Status);

                            Class_ViewAssessmentListview innerObjClass_ViewAssessmentListview = new Class_ViewAssessmentListview();
                            innerObjClass_ViewAssessmentListview.setAssessment_instituteID(str_Institute_ID);
                            innerObjClass_ViewAssessmentListview.setAssessment_instituteName(str_Institute_Name);
                            innerObjClass_ViewAssessmentListview.setAssessment_levelID(str_level_ID);
                            innerObjClass_ViewAssessmentListview.setAssessment_levelName(str_Lavel_Name);
                            innerObjClass_ViewAssessmentListview.setAssessment_presentstudentcount(str_presentcount);
                            innerObjClass_ViewAssessmentListview.setAssessment_totalstudentcount(str_totalcount);
                            innerObjClass_ViewAssessmentListview.setAssessment_maxmarks(str_maxmarks);
                            innerObjClass_ViewAssessmentListview.setAssessmentDate(str_Assesment_Date);
                            innerObjClass_ViewAssessmentListview.setAssessmentID(str_Assesment_ID);
                            innerObjClass_ViewAssessmentListview.setAssessmentName(str_Assesment_Name);
                            innerObjClass_ViewAssessmentListview.setAssessmentStatus(str_Assesment_Status);
                            innerObjClass_ViewAssessmentListview.setAssessment_save(str_save);


                            obj.setAssessmentID(str_Assesment_ID);
                            obj.setAssessmentName(str_Assesment_Name);
                            obj.setAssessmentDate(str_Assesment_Date);
                            obj.setAssessmentStatus(str_Assesment_Status);
                            obj.setAssessment_levelName(str_Lavel_Name);
                            obj.setAssessment_instituteID(str_Institute_ID);
                            obj.setAssessment_instituteName(str_Institute_Name);
                            obj.setAssessment_levelID(str_level_ID);
                            obj.setAssessment_presentstudentcount(str_presentcount);
                            obj.setAssessment_totalstudentcount(str_totalcount);
                            obj.setAssessment_maxmarks(str_maxmarks);
                            obj.setAssessment_save(str_save);


                            userInfosarr[i] = obj;
                            arrayobjClass_Assessment_institute[i] = innerObj_Class_institute;
                            arrayobjClass_Assessment_level[i] = innerObj_Class_level;
                            arrayobjClass_Assessment_status[i] = innerObj_Class_status;
                            arrayobjclass_ViewAssessmentListview[i] = innerObjClass_ViewAssessmentListview;


                            Log.i("Tag", "items aa=" + assessment_array_List.get(i).getAssessment_levelName());
                            Class_Assessments_List.assesmentlistview_info_arr.add(new Class_Assessments_List(str_Assesment_ID, str_Assesment_Name, str_Assesment_Date, str_Assesment_Status, str_Lavel_Name, str_Institute_Name, str_Institute_ID, str_level_ID, str_presentcount, str_totalcount, str_maxmarks, str_save));
                            // adapter.add(new Class_Assessments_List(str_Assesment_ID, str_Assesment_Name, str_Assesment_Date, str_Assesment_Status, str_Lavel_Name, str_Institute_Name,str_Institute_ID,str_level_ID,str_presentcount,str_totalcount,str_maxmarks,str_save));


                            DBCreate_INstdetails_insert_2SQLiteDB(str_Institute_ID, str_Institute_Name);
                            DBCreate_Leveldetails_insert_2SQLiteDB(str_Institute_ID, str_level_ID, str_Lavel_Name);
                            DBCreate_Statusdetails_insert_2SQLiteDB(str_Institute_ID, str_level_ID, str_Assesment_Status);
                            DBCreate_ViewListdetails_insert_2SQLiteDB(str_Institute_ID, str_level_ID, str_Assesment_Status, str_Assesment_ID, str_Assesment_Name, str_Assesment_Date, str_presentcount, str_totalcount, str_maxmarks, str_Lavel_Name, str_Institute_Name, str_save);
                        }

                        Log.i("Tag", "items=" + items.length);


                        uploadfromDB_INstlist();
                        uploadfromDB_levellist();
                        uploadfromDB_statuslist();
                        Uploadfrom_ViewList_spinner();


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
                    } else {
                        Toast.makeText(Activity_AssessmentList.this, "Kindly restart your application", Toast.LENGTH_SHORT).show();

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


    //================================================================


    public void DBCreate_INstdetails_insert_2SQLiteDB(String str_insID, String str_insname) {
        SQLiteDatabase db_sandbox = this.openOrCreateDatabase("assessmentdb", Context.MODE_PRIVATE, null);
        db_sandbox.execSQL("CREATE TABLE IF NOT EXISTS INstList(instiID VARCHAR,instiName VARCHAR);");


        String SQLiteQuery = "INSERT INTO INstList (instiID, instiName)" +
                " VALUES ('" + str_insID + "','" + str_insname + "');";
        db_sandbox.execSQL(SQLiteQuery);

        db_sandbox.close();
    }

    public void uploadfromDB_INstlist() {

        SQLiteDatabase db_sandboxlist = this.openOrCreateDatabase("assessmentdb", Context.MODE_PRIVATE, null);
        db_sandboxlist.execSQL("CREATE TABLE IF NOT EXISTS INstList(instiID VARCHAR,instiName VARCHAR);");
        Cursor cursor = db_sandboxlist.rawQuery("SELECT DISTINCT * FROM INstList", null);
        int x = cursor.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayobjClass_Assessment_institute2 = new Class_Assessment_Institute[x];
        if (cursor.moveToFirst()) {

            do {
                Class_Assessment_Institute innerObj_Class_SandboxList = new Class_Assessment_Institute();
                innerObj_Class_SandboxList.setInstitute_id(cursor.getString(cursor.getColumnIndex("instiID")));
                innerObj_Class_SandboxList.setinstitute_assessment_name(cursor.getString(cursor.getColumnIndex("instiName")));
                // innerObj_Class_StatesList.setCenterCode(cursor1.getString(cursor1.getColumnIndex("CCode")));

                arrayobjClass_Assessment_institute2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor.moveToNext());
            // Log.e("academicarray1",Arrays.toString(arrayObj_Class_sandboxDetails2));

        }//if ends

        db_sandboxlist.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayobjClass_Assessment_institute2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            institute_assessment_SP.setAdapter(dataAdapter);
        }

    }

    public void Update_instid_InLeveltable_spinner(String str_insid) {

        SQLiteDatabase db_levelid = this.openOrCreateDatabase("assessmentdb", Context.MODE_PRIVATE, null);
        db_levelid.execSQL("CREATE TABLE IF NOT EXISTS Level_Assessment_List(insti_assessmentID VARCHAR,levelID VARCHAR,levelname VARCHAR);");
        Cursor cursor = db_levelid.rawQuery("SELECT DISTINCT * FROM Level_Assessment_List WHERE insti_assessmentID='" + str_insid + "'", null);

        try {
            int x = cursor.getCount();
            Log.d("cursor Dcountlevelid", Integer.toString(x));

            int i = 0;
            arrayobjClass_Assessment_level2 = new Class_Assessment_Level[x];
            if (cursor.moveToFirst()) {

                do {
                    Class_Assessment_Level innerObj_Class_SandboxList = new Class_Assessment_Level();
                    innerObj_Class_SandboxList.setInst_assessmentid(cursor.getString(cursor.getColumnIndex("insti_assessmentID")));
                    innerObj_Class_SandboxList.setLevel_assessmentid(cursor.getString(cursor.getColumnIndex("levelID")));
                    innerObj_Class_SandboxList.setLevel_assessmentname(cursor.getString(cursor.getColumnIndex("levelname")));

                    // innerObj_Class_StatesList.setCenterCode(cursor1.getString(cursor1.getColumnIndex("CCode")));

                    arrayobjClass_Assessment_level2[i] = innerObj_Class_SandboxList;
                    i++;
                } while (cursor.moveToNext());
            }//if ends


            db_levelid.close();
            if (x > 0) {

                ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayobjClass_Assessment_level2);
                dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
                level_assessment_SP.setAdapter(dataAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DBCreate_Leveldetails_insert_2SQLiteDB(String str_insID, String str_levelID, String str_levelname) {
        SQLiteDatabase db_sandbox = this.openOrCreateDatabase("assessmentdb", Context.MODE_PRIVATE, null);
        db_sandbox.execSQL("CREATE TABLE IF NOT EXISTS Level_Assessment_List(insti_assessmentID VARCHAR,levelID VARCHAR,levelname VARCHAR);");
        String SQLiteQuery = "INSERT INTO Level_Assessment_List (insti_assessmentID, levelID,levelname)" +
                " VALUES ('" + str_insID + "','" + str_levelID + "','" + str_levelname + "');";
        db_sandbox.execSQL(SQLiteQuery);
        db_sandbox.close();
    }

    public void uploadfromDB_levellist() {

        SQLiteDatabase db_sandboxlist = this.openOrCreateDatabase("assessmentdb", Context.MODE_PRIVATE, null);
        db_sandboxlist.execSQL("CREATE TABLE IF NOT EXISTS Level_Assessment_List(insti_assessmentID VARCHAR,levelID VARCHAR,levelname VARCHAR);");
        Cursor cursor = db_sandboxlist.rawQuery("SELECT DISTINCT * FROM Level_Assessment_List", null);
        int x = cursor.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayobjClass_Assessment_level2 = new Class_Assessment_Level[x];
        if (cursor.moveToFirst()) {

            do {
                Class_Assessment_Level innerObj_Class_SandboxList = new Class_Assessment_Level();
                innerObj_Class_SandboxList.setInst_assessmentid(cursor.getString(cursor.getColumnIndex("insti_assessmentID")));
                innerObj_Class_SandboxList.setLevel_assessmentid(cursor.getString(cursor.getColumnIndex("levelID")));
                innerObj_Class_SandboxList.setLevel_assessmentname(cursor.getString(cursor.getColumnIndex("levelname")));

                // innerObj_Class_StatesList.setCenterCode(cursor1.getString(cursor1.getColumnIndex("CCode")));

                arrayobjClass_Assessment_level2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor.moveToNext());
            // Log.e("academicarray1",Arrays.toString(arrayObj_Class_sandboxDetails2));

        }//if ends

        db_sandboxlist.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayobjClass_Assessment_level2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            level_assessment_SP.setAdapter(dataAdapter);
        }

    }

    public void DBCreate_Statusdetails_insert_2SQLiteDB(String str_insID, String str_levelID, String str_status) {
        SQLiteDatabase db_sandbox = this.openOrCreateDatabase("assessmentdb", Context.MODE_PRIVATE, null);
        db_sandbox.execSQL("CREATE TABLE IF NOT EXISTS Status_Assessment_List(instituteid VARCHAR,levelid VARCHAR,status VARCHAR);");
        String SQLiteQuery = "INSERT INTO Status_Assessment_List (instituteid, levelid,status)" +
                " VALUES ('" + str_insID + "','" + str_levelID + "','" + str_status + "');";
        db_sandbox.execSQL(SQLiteQuery);
        db_sandbox.close();
    }

    public void uploadfromDB_statuslist() {

        SQLiteDatabase db_sandboxlist = this.openOrCreateDatabase("assessmentdb", Context.MODE_PRIVATE, null);
        db_sandboxlist.execSQL("CREATE TABLE IF NOT EXISTS Status_Assessment_List(instituteid VARCHAR,levelid VARCHAR,status VARCHAR);");
        Cursor cursor = db_sandboxlist.rawQuery("SELECT DISTINCT * FROM Status_Assessment_List", null);
        int x = cursor.getCount();
        Log.d("cursor count", Integer.toString(x));


        int i = 0;
        arrayobjClass_Assessment_status3 = new Class_Assessment_Status[x];
        if (cursor.moveToFirst()) {

            do {
                Class_Assessment_Status innerObj_Class_SandboxList = new Class_Assessment_Status();
                innerObj_Class_SandboxList.setAssessment_instituteID(cursor.getString(cursor.getColumnIndex("instituteid")));
                innerObj_Class_SandboxList.setAssessment_levelID(cursor.getString(cursor.getColumnIndex("levelid")));
                innerObj_Class_SandboxList.setStatus(cursor.getString(cursor.getColumnIndex("status")));

                arrayobjClass_Assessment_status3[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor.moveToNext());


        }//if ends

        db_sandboxlist.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayobjClass_Assessment_status3);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            Status_assessment_SP.setAdapter(dataAdapter);
        }

    }

    public void Update_instidlevelid_InStatus_spinner(String str_insid, String strlevelid) {

        SQLiteDatabase db_levelid = this.openOrCreateDatabase("assessmentdb", Context.MODE_PRIVATE, null);
        db_levelid.execSQL("CREATE TABLE IF NOT EXISTS Status_Assessment_List(instituteid VARCHAR,levelid VARCHAR,status VARCHAR);");
        Cursor cursor = db_levelid.rawQuery("SELECT DISTINCT * FROM Status_Assessment_List WHERE instituteid='" + str_insid + "'AND levelid='" + strlevelid + "'", null);

        try {
            int x = cursor.getCount();
            Log.d("cursor Dcountlevelid", Integer.toString(x));

            int i = 0;
            arrayobjClass_Assessment_status3 = new Class_Assessment_Status[x];
            if (cursor.moveToFirst()) {

                do {
                    Class_Assessment_Status innerObj_Class_SandboxList = new Class_Assessment_Status();
                    innerObj_Class_SandboxList.setAssessment_levelID(cursor.getString(cursor.getColumnIndex("levelid")));
                    innerObj_Class_SandboxList.setAssessment_instituteID(cursor.getString(cursor.getColumnIndex("instituteid")));
                    innerObj_Class_SandboxList.setStatus(cursor.getString(cursor.getColumnIndex("status")));

                    // innerObj_Class_StatesList.setCenterCode(cursor1.getString(cursor1.getColumnIndex("CCode")));

                    arrayobjClass_Assessment_status3[i] = innerObj_Class_SandboxList;
                    i++;
                } while (cursor.moveToNext());
            }//if ends


            db_levelid.close();
            if (x > 0) {

                ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayobjClass_Assessment_status3);
                dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
                Status_assessment_SP.setAdapter(dataAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteInstiTable_B4insertion() {

        SQLiteDatabase db_sandboxtable_delete = openOrCreateDatabase("assessmentdb", Context.MODE_PRIVATE, null);
        db_sandboxtable_delete.execSQL("CREATE TABLE IF NOT EXISTS INstList(instiID VARCHAR,instiName VARCHAR);");
        Cursor cursor = db_sandboxtable_delete.rawQuery("SELECT * FROM INstList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_sandboxtable_delete.delete("INstList", null, null);
        }
        db_sandboxtable_delete.close();
    }

    public void deleteLevelTable_B4insertion() {

        SQLiteDatabase db_sandboxtable_delete = openOrCreateDatabase("assessmentdb", Context.MODE_PRIVATE, null);
        db_sandboxtable_delete.execSQL("CREATE TABLE IF NOT EXISTS Level_Assessment_List(insti_assessmentID VARCHAR,levelID VARCHAR,levelname VARCHAR);");
        Cursor cursor = db_sandboxtable_delete.rawQuery("SELECT * FROM Level_Assessment_List", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_sandboxtable_delete.delete("Level_Assessment_List", null, null);
        }
        db_sandboxtable_delete.close();
    }

    public void deleteStatusTable_B4insertion() {

        SQLiteDatabase db_sandboxtable_delete = openOrCreateDatabase("assessmentdb", Context.MODE_PRIVATE, null);
        db_sandboxtable_delete.execSQL("CREATE TABLE IF NOT EXISTS Status_Assessment_List(instituteid VARCHAR,levelid VARCHAR,status VARCHAR);");
        Cursor cursor = db_sandboxtable_delete.rawQuery("SELECT * FROM Status_Assessment_List", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_sandboxtable_delete.delete("Status_Assessment_List", null, null);
        }
        db_sandboxtable_delete.close();
    }

    public void DBCreate_ViewListdetails_insert_2SQLiteDB(String str_insID, String str_levelid, String strstatus, String str_assessmentid, String str_assessmentname, String str_date, String str_presentcount, String str_totalcount, String str_maxmarks, String strlevelname, String strinstname, String strsave) {
        SQLiteDatabase db_sandbox = this.openOrCreateDatabase("assessmentdb", Context.MODE_PRIVATE, null);
        db_sandbox.execSQL("CREATE TABLE IF NOT EXISTS VIewAssessmentList(view_instiID VARCHAR,view_levelid VARCHAR,view_status VARCHAR,view_assessmentid VARCHAR,view_assessmentname VARCHAR,view_date VARCHAR,view_presentcount VARCHAR,view_totalcount VARCHAR,view_maxmarks VARCHAR,view_levelname VARCHAR,view_instname VARCHAR,view_save VARCHAR);");


        String SQLiteQuery = "INSERT INTO VIewAssessmentList (view_instiID, view_levelid,view_status,view_assessmentid,view_assessmentname,view_date,view_presentcount,view_totalcount,view_maxmarks,view_levelname,view_instname,view_save)" +
                " VALUES ('" + str_insID + "','" + str_levelid + "','" + strstatus + "','" + str_assessmentid + "','" + str_assessmentname + "','" + str_date + "','" + str_presentcount + "','" + str_totalcount + "','" + str_maxmarks + "','" + strlevelname + "','" + strinstname + "','" + strsave + "');";
        db_sandbox.execSQL(SQLiteQuery);

        db_sandbox.close();
    }

    public void deleteVIewlistTable_B4insertion() {

        SQLiteDatabase db_sandboxtable_delete = openOrCreateDatabase("assessmentdb", Context.MODE_PRIVATE, null);
        db_sandboxtable_delete.execSQL("CREATE TABLE IF NOT EXISTS VIewAssessmentList(view_instiID VARCHAR,view_levelid VARCHAR,view_status VARCHAR,view_assessmentid VARCHAR,view_assessmentname VARCHAR,view_date VARCHAR,view_presentcount VARCHAR,view_totalcount VARCHAR,view_maxmarks VARCHAR,view_levelname VARCHAR,view_instname VARCHAR,view_save VARCHAR);");
        Cursor cursor = db_sandboxtable_delete.rawQuery("SELECT * FROM VIewAssessmentList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_sandboxtable_delete.delete("VIewAssessmentList", null, null);
        }
        db_sandboxtable_delete.close();
    }

    public void Uploadfrom_ViewList_spinner() {
        SQLiteDatabase db1 = this.openOrCreateDatabase("assessmentdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS VIewAssessmentList(view_instiID VARCHAR,view_levelid VARCHAR,view_status VARCHAR,view_assessmentid VARCHAR,view_assessmentname VARCHAR,view_date VARCHAR,view_presentcount VARCHAR,view_totalcount VARCHAR,view_maxmarks VARCHAR,view_levelname VARCHAR,view_instname VARCHAR,view_save VARCHAR);");
        // Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VIewAssessmentList WHERE view_instiID='" + str_insID + "' AND view_levelid='" + str_levelid + "'AND view_status='" + strstatus + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VIewAssessmentList", null);

        try {
            int x = cursor1.getCount();
            int i = 0;
            arrayobjclass_ViewAssessmentListview2 = new Class_ViewAssessmentListview[x];

            if (cursor1.moveToFirst()) {

                do {

                    Log.d("view", "enteredviewlist loop");
                    Class_ViewAssessmentListview innerObj_Class_stuList = new Class_ViewAssessmentListview();
                    innerObj_Class_stuList.setAssessmentID(cursor1.getString(cursor1.getColumnIndex("view_assessmentid")));
                    String s1 = cursor1.getString(cursor1.getColumnIndex("view_assessmentid"));
                    innerObj_Class_stuList.setAssessmentName(cursor1.getString(cursor1.getColumnIndex("view_assessmentname")));
                    String s2 = cursor1.getString(cursor1.getColumnIndex("view_assessmentname"));

                    innerObj_Class_stuList.setAssessmentDate(cursor1.getString(cursor1.getColumnIndex("view_date")));
                    String s3 = cursor1.getString(cursor1.getColumnIndex("view_date"));

                    innerObj_Class_stuList.setAssessment_levelID(cursor1.getString(cursor1.getColumnIndex("view_levelid")));
                    String s4 = cursor1.getString(cursor1.getColumnIndex("view_levelid"));

                    innerObj_Class_stuList.setAssessment_instituteName(cursor1.getString(cursor1.getColumnIndex("view_instname")));
                    String s5 = cursor1.getString(cursor1.getColumnIndex("view_instname"));

                    innerObj_Class_stuList.setAssessment_instituteID(cursor1.getString(cursor1.getColumnIndex("view_instiID")));
                    String s6 = cursor1.getString(cursor1.getColumnIndex("view_instiID"));

                    innerObj_Class_stuList.setAssessment_maxmarks(cursor1.getString(cursor1.getColumnIndex("view_maxmarks")));
                    String s7 = cursor1.getString(cursor1.getColumnIndex("view_maxmarks"));

                    innerObj_Class_stuList.setAssessment_presentstudentcount(cursor1.getString(cursor1.getColumnIndex("view_presentcount")));
                    String s8 = cursor1.getString(cursor1.getColumnIndex("view_presentcount"));

                    innerObj_Class_stuList.setAssessment_totalstudentcount(cursor1.getString(cursor1.getColumnIndex("view_totalcount")));
                    String s9 = cursor1.getString(cursor1.getColumnIndex("view_totalcount"));

                    innerObj_Class_stuList.setAssessmentStatus(cursor1.getString(cursor1.getColumnIndex("view_status")));
                    String s10 = cursor1.getString(cursor1.getColumnIndex("view_status"));

                    innerObj_Class_stuList.setAssessment_levelName(cursor1.getString(cursor1.getColumnIndex("view_levelname")));
                    String s11 = cursor1.getString(cursor1.getColumnIndex("view_levelname"));
                    innerObj_Class_stuList.setAssessment_save(cursor1.getString(cursor1.getColumnIndex("view_save")));

                    arrayobjclass_ViewAssessmentListview2[i] = innerObj_Class_stuList;


                    i++;

//                    Class_Assessments_List.assesmentlistview_info_arr_new.add(new Class_Assessments_List(s1, s2, s3, s10, s11, s5,s6,s4,s8,s9,s7));
//                    adapter2.add(new Class_Assessments_List(s1, s2, s3, s10, s11, s5,s6,s4,s8,s9,s7));

                } while (cursor1.moveToNext());


            }//if ends
            db1.close();

            if (x > 0) {
                // adapter2 = new AssessmentCardsAdapter_new(this, Class_Assessments_List.assesmentlistview_info_arr_new);
                CustomAdapter adapter = new CustomAdapter();
                lv_eventlist.setAdapter(adapter);

                // lv_eventlist.setAdapter(adapter2);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Update_ViewListID_spinner(String str_insID, String str_levelid, String strstatus) {
        SQLiteDatabase db1 = this.openOrCreateDatabase("assessmentdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS VIewAssessmentList(view_instiID VARCHAR,view_levelid VARCHAR,view_status VARCHAR,view_assessmentid VARCHAR,view_assessmentname VARCHAR,view_date VARCHAR,view_presentcount VARCHAR,view_totalcount VARCHAR,view_maxmarks VARCHAR,view_levelname VARCHAR,view_instname VARCHAR,view_save VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VIewAssessmentList WHERE view_instiID='" + str_insID + "' AND view_levelid='" + str_levelid + "'AND view_status='" + strstatus + "'", null);

        try {
            int x = cursor1.getCount();
            int i = 0;
            // userInfosarr2 = new Class_Assessments_List[x];
            arrayobjclass_ViewAssessmentListview2 = new Class_ViewAssessmentListview[x];
            if (cursor1.moveToFirst()) {

                do {

                    Log.d("view", "enteredviewlist loop");
                    Class_ViewAssessmentListview innerObj_Class_stuList = new Class_ViewAssessmentListview();
                    innerObj_Class_stuList.setAssessmentID(cursor1.getString(cursor1.getColumnIndex("view_assessmentid")));
                    String s1 = cursor1.getString(cursor1.getColumnIndex("view_assessmentid"));
                    innerObj_Class_stuList.setAssessmentName(cursor1.getString(cursor1.getColumnIndex("view_assessmentname")));
                    String s2 = cursor1.getString(cursor1.getColumnIndex("view_assessmentname"));

                    innerObj_Class_stuList.setAssessmentDate(cursor1.getString(cursor1.getColumnIndex("view_date")));
                    String s3 = cursor1.getString(cursor1.getColumnIndex("view_date"));

                    innerObj_Class_stuList.setAssessment_levelID(cursor1.getString(cursor1.getColumnIndex("view_levelid")));
                    String s4 = cursor1.getString(cursor1.getColumnIndex("view_levelid"));

                    innerObj_Class_stuList.setAssessment_instituteName(cursor1.getString(cursor1.getColumnIndex("view_instname")));
                    String s5 = cursor1.getString(cursor1.getColumnIndex("view_instname"));

                    innerObj_Class_stuList.setAssessment_instituteID(cursor1.getString(cursor1.getColumnIndex("view_instiID")));
                    String s6 = cursor1.getString(cursor1.getColumnIndex("view_instiID"));

                    innerObj_Class_stuList.setAssessment_maxmarks(cursor1.getString(cursor1.getColumnIndex("view_maxmarks")));
                    String s7 = cursor1.getString(cursor1.getColumnIndex("view_maxmarks"));

                    innerObj_Class_stuList.setAssessment_presentstudentcount(cursor1.getString(cursor1.getColumnIndex("view_presentcount")));
                    String s8 = cursor1.getString(cursor1.getColumnIndex("view_presentcount"));

                    innerObj_Class_stuList.setAssessment_totalstudentcount(cursor1.getString(cursor1.getColumnIndex("view_totalcount")));
                    String s9 = cursor1.getString(cursor1.getColumnIndex("view_totalcount"));

                    innerObj_Class_stuList.setAssessmentStatus(cursor1.getString(cursor1.getColumnIndex("view_status")));
                    String s10 = cursor1.getString(cursor1.getColumnIndex("view_status"));

                    innerObj_Class_stuList.setAssessment_levelName(cursor1.getString(cursor1.getColumnIndex("view_levelname")));
                    String s11 = cursor1.getString(cursor1.getColumnIndex("view_levelname"));

                    innerObj_Class_stuList.setAssessment_save(cursor1.getString(cursor1.getColumnIndex("view_save")));

                    //  userInfosarr2[i] = innerObj_Class_stuList;
                    arrayobjclass_ViewAssessmentListview2[i] = innerObj_Class_stuList;

                    // Class_Assessments_List.assesmentlistview_info_arr.add(new Class_Assessments_List(s1, s2, s3, s10, s11, s5,s6,s4,s8,s9,s7));
                    // adapter.add(new Class_Assessments_List(s1, s2, s3, s10, s11, s5,s6,s4,s8,s9,s7));

                    i++;

                } while (cursor1.moveToNext());


            }//if ends
            db1.close();

            if (x > 0) {
                //  adapter = new AssessmentCardsAdapter(this, Class_Assessments_List.assesmentlistview_info_arr);
                //  lv_eventlist.setAdapter(adapter);
                CustomAdapter adapter = new CustomAdapter();
                lv_eventlist.setAdapter(adapter);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu items
        getMenuInflater().inflate(R.menu.schedule_menu, menu);
        return true;
    }

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
                sharedpref_username_Obj = getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
                str_Googlelogin_Username = sharedpref_username_Obj.getString(Key_username, "").trim();

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

                Intent i = new Intent(Activity_AssessmentList.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

                break;
            case android.R.id.home:
                Intent intent = new Intent(Activity_AssessmentList.this, Activity_HomeScreen.class);
                startActivity(intent);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Activity_AssessmentList.this, Activity_HomeScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();

    }


    private class ViewHolder {

        TextView level_assessment_TV;
        TextView institute_assessment_TV;
        TextView date_assessment_TV;
        TextView Time1_assessment_TV;
        TextView Time2_assessment_TV;
        ImageView edit_assessment_bt;
        ImageView view_assessment_bt;
        TextView tv_assessmentId;
        TextView tv_assessmentStatus;
        TextView assessmentname_TV;
        TextView studentcount_assessment_TV;
        TextView assessment_maxmarks_TV;
        CardView card_view_layout;


//        ViewHolder(View view) {
//
//            level_assessment_TV = (TextView) view.findViewById(R.id.level_assessment_TV);
//            institute_assessment_TV = (TextView) view.findViewById(R.id.institute_assessment_TV);
//            date_assessment_TV =(TextView) view.findViewById(R.id.date_assessment_TV);
////            Time1_assessment_TV = (TextView) view.findViewById(R.id.Time1_assessment_TV);
//            Time2_assessment_TV = (TextView) view.findViewById(R.id.Time2_assessment_TV);
//            tv_assessmentId = (TextView) view.findViewById(R.id.tv_assessmentId);
//            tv_assessmentStatus = (TextView) view.findViewById(R.id.tv_assessmentStatus);
//            studentcount_assessment_TV=(TextView)view.findViewById(R.id.studentcount_assessment_TV);
//
//            edit_assessment_bt = (ImageView) view.findViewById(R.id.edit_assessment_bt);
//            view_assessment_bt=(ImageView)view.findViewById(R.id.view_assessment_bt);
//            assessmentname_TV=(TextView)view.findViewById(R.id.assessmentname_TV);
//            assessment_maxmarks_TV=(TextView)view.findViewById(R.id.assessment_maxmarks_TV);
//
//            card_view_layout=(CardView)view.findViewById(R.id.card_view_layout);
//        }
    }

    public class CustomAdapter extends BaseAdapter {
        String statusStr, stime, etime, str_assessmentid, str_assessmentdate, str_assessmentname, str_assessmentstatus, str_assessmentlevel;


        public CustomAdapter() {

            super();
            Log.d("Inside CustomAdapter()", "Inside CustomAdapter()");
        }

        @Override
        public int getCount() {

            String x = Integer.toString(arrayobjclass_ViewAssessmentListview2.length);
            Log.d("Arrayclass.length", x);
            return arrayobjclass_ViewAssessmentListview2.length;

        }

        @Override
        public Object getItem(int position) {
            String x = Integer.toString(position);
            Log.d("getItem position", "x");
            return arrayobjclass_ViewAssessmentListview2[position];
        }

        @Override
        public long getItemId(int position) {
            String x = Integer.toString(position);
            Log.d("getItemId position", x);
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();

                convertView = LayoutInflater.from(Activity_AssessmentList.this).inflate(R.layout.assessmentcard_item, parent, false);

                //LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//                convertView = inflater.inflate(R.layout.assessmentcard_item, parent, false);


                holder.level_assessment_TV = convertView.findViewById(R.id.level_assessment_TV);
                holder.institute_assessment_TV = convertView.findViewById(R.id.institute_assessment_TV);
                holder.date_assessment_TV = convertView.findViewById(R.id.date_assessment_TV);
//            Time1_assessment_TV = (TextView) view.findViewById(R.id.Time1_assessment_TV);
                holder.Time2_assessment_TV = convertView.findViewById(R.id.Time2_assessment_TV);
                holder.tv_assessmentId = convertView.findViewById(R.id.tv_assessmentId);
                holder.tv_assessmentStatus = convertView.findViewById(R.id.tv_assessmentStatus);
                holder.studentcount_assessment_TV = convertView.findViewById(R.id.studentcount_assessment_TV);

                holder.edit_assessment_bt = convertView.findViewById(R.id.edit_assessment_bt);
                holder.view_assessment_bt = convertView.findViewById(R.id.view_assessment_bt);
                holder.assessmentname_TV = convertView.findViewById(R.id.assessmentname_TV);
                holder.assessment_maxmarks_TV = convertView.findViewById(R.id.assessment_maxmarks_TV);

                holder.card_view_layout = convertView.findViewById(R.id.card_view_layout);


                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();

            }
            obj_class_ViewAssessmentListview = (Class_ViewAssessmentListview) getItem(position);

            //  Class_Assessments_List model = getItem(position);
            int mm = getCount();
            Log.e("abac", obj_class_ViewAssessmentListview.getAssessmentID());
            // Log.i("TAG", "mm=" + mm + " getItemId(position)=" + getItemId(position) + "getItem=" + getItem(position));
            if (obj_class_ViewAssessmentListview != null) {
                str_assessmentid = obj_class_ViewAssessmentListview.getAssessmentID();
                str_assessmentdate = obj_class_ViewAssessmentListview.getAssessmentDate();
                str_assessmentname = obj_class_ViewAssessmentListview.getAssessmentName();
                str_assessmentstatus = obj_class_ViewAssessmentListview.getAssessmentStatus();
                str_assessmentlevel = obj_class_ViewAssessmentListview.getAssessment_levelName();

//            assesmentlistview_info_arr.get(position);
//
//
//            Log.i("tag", "listview_info_arr" + assesmentlistview_info_arr.get(position).getAssessmentID());
//            Log.i("tag", "listview_info_arr" + assesmentlistview_info_arr.get(position).getAssessmentName());
//            Log.i("tag", "listview_info_arr" + assesmentlistview_info_arr.get(position).getAssessmentStatus());
//            Log.i("tag", "listview_info_arr" + assesmentlistview_info_arr.get(position).getAssessment_levelName());


                holder.level_assessment_TV.setText(obj_class_ViewAssessmentListview.getAssessment_levelName());
                //  holder.Time1_assessment_TV.setText(stime);
                //  holder.Time2_assessment_TV.setText(etime);
                holder.institute_assessment_TV.setText(obj_class_ViewAssessmentListview.getAssessment_instituteName());
                holder.date_assessment_TV.setText(obj_class_ViewAssessmentListview.getAssessmentDate());
                //  Log.i("tag", "model.getAssessmentID()=" + obj_class_ViewAssessmentListview.getAssessmentID());
                holder.tv_assessmentId.setText(obj_class_ViewAssessmentListview.getAssessmentID());
                holder.tv_assessmentStatus.setText(obj_class_ViewAssessmentListview.getAssessmentStatus());
                holder.assessmentname_TV.setText(obj_class_ViewAssessmentListview.getAssessmentName());
                holder.studentcount_assessment_TV.setText(obj_class_ViewAssessmentListview.getAssessment_presentstudentcount() + " / " + obj_class_ViewAssessmentListview.getAssessment_totalstudentcount());
                holder.assessment_maxmarks_TV.setText("Max. Marks : " + obj_class_ViewAssessmentListview.getAssessment_maxmarks());
                //Log.i("tag", "statusStr=" + statusStr);


                if (str_assessmentstatus.equals("Active")) {
                    holder.card_view_layout.setBackgroundResource(R.color.red);
                    holder.edit_assessment_bt.setVisibility(View.VISIBLE);
                    holder.view_assessment_bt.setVisibility(View.GONE);

                } else if (str_assessmentstatus.equals("Completed")) {
                    holder.card_view_layout.setBackgroundResource(R.color.green);
                    holder.edit_assessment_bt.setVisibility(View.GONE);
                    holder.view_assessment_bt.setVisibility(View.VISIBLE);

                } else if (str_assessmentstatus.equals("Started")) {
                    holder.card_view_layout.setBackgroundResource(R.color.yellow);
                    holder.edit_assessment_bt.setVisibility(View.VISIBLE);
                    holder.view_assessment_bt.setVisibility(View.GONE);

                } else if (str_assessmentstatus.equals("Pending Submission")) {
                    holder.card_view_layout.setBackgroundResource(R.color.yellow);
                    holder.edit_assessment_bt.setVisibility(View.VISIBLE);
                    holder.view_assessment_bt.setVisibility(View.GONE);

                }
                //Pending Submission

//                if (str_assessmentstatus.equals("Active")) {
//                    holder.card_view_layout.setBackgroundResource(R.color.red);
//                } else if (str_assessmentstatus.equals("Completed")) {
//                    holder.card_view_layout.setBackgroundResource(R.color.green);
//                }

//                if (obj_class_ViewAssessmentListview.getAssessment_save().equals("1")) {
//                    holder.card_view_layout.setBackgroundResource(R.color.yellow);
//                } else if (obj_class_ViewAssessmentListview.getAssessment_save().equals("3")) {
//                    holder.card_view_layout.setBackgroundResource(R.color.green);
//                }

//                if (obj_class_ViewAssessmentListview.getAssessment_save().equals("2")) {
//                    holder.card_view_layout.setBackgroundResource(R.color.red);
//                } else if (obj_class_ViewAssessmentListview.getAssessment_save().equals("3")) {
//                    holder.card_view_layout.setBackgroundResource(R.color.green);
//                } else if (obj_class_ViewAssessmentListview.getAssessment_save().equals("1")){
//                    holder.card_view_layout.setBackgroundResource(R.color.yellow);
//                }
                ///2-started,1-active,3-completed


//                Log.e("save", "save=" + obj_class_ViewAssessmentListview.getAssessment_save());


                holder.edit_assessment_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        str_assessmentid = holder.tv_assessmentId.getText().toString();
                        str_assessmentstatus = holder.tv_assessmentStatus.getText().toString();

                        Log.i("str_assessmentid", "str_assessmentid=" + str_assessmentid);

                        Intent i = new Intent(Activity_AssessmentList.this, MainActivity2.class);

                        i.putExtra("str_assessmentid", str_assessmentid);
                        i.putExtra("Flag", 0);
                        i.putExtra("str_assessmentstatus", str_assessmentstatus);


                        startActivity(i);


                    }
                });


                holder.view_assessment_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        str_assessmentid = holder.tv_assessmentId.getText().toString();
                        str_assessmentstatus = holder.tv_assessmentStatus.getText().toString();
                        Intent i = new Intent(Activity_AssessmentList.this, MainActivity2.class);
                        i.putExtra("str_assessmentid", str_assessmentid);
                        i.putExtra("Flag", 1);
                        i.putExtra("str_assessmentstatus", str_assessmentstatus);
                        startActivity(i);

                    }
                });
            }
            return convertView;
        }


    }//End of CustomAdapter


}
