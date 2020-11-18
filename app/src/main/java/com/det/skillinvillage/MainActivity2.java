package com.det.skillinvillage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.det.skillinvillage.model.Class_AssementList;
import com.det.skillinvillage.model.Class_AssessmentData;
import com.det.skillinvillage.model.Class_AssessmentStudentDataList;
import com.det.skillinvillage.model.Class_Get_User_DocumentResponse;
import com.det.skillinvillage.model.Class_ListVersion;
import com.det.skillinvillage.model.Class_PostUpdateAssessmentSubmitList;
import com.det.skillinvillage.model.Class_PostUpdateStudentAssessmentList;
import com.det.skillinvillage.model.Class_PostUpdateStudentAssessmentRequest;
import com.det.skillinvillage.model.Class_PostUpdateStudentAssessmentResponse;
import com.det.skillinvillage.model.Class_Post_UpdateStudentAssessmentSubmitRequest;
import com.det.skillinvillage.model.Class_Post_UpdateStudentAssessmentSubmitResponse;
import com.det.skillinvillage.model.Class_getassessmentlistResponse;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;

public class MainActivity2 extends AppCompatActivity {

    private Button btn;
    private ListView lv;
    private CustomeAdapter customeAdapter;
    public ArrayList<EditModel> editModelArrayList;
    public ArrayList<EditModel> EditModel_arraylist2;

    int int_count1, int_noOfobjects;
    Class_StudentAssement[] studentAssetment_arrayobj;
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    TextView message_TV;
    Button save_assesmentView_BT, submit_assesmentView_BT;
    String str_loginuserID = "", str_marks_status = "", str_Recived_assessmentstatus = "", str_save_marks_status = "", str_response_update = "", str_response_submit = "", str_Assesment_StudentID = "", str_Assesment_StudentMarks = "";
    SharedPreferences sharedpref_loginuserid_Obj;
    String str_assessmentID = "", str_Student_Name = "";
    public static int intval_flag = 0;
    private ArrayList<String> arrLst_studentIds = new ArrayList<String>();
    private ArrayList<String> arrLst_assessment_marks = new ArrayList<String>();
    Class_AssessmentModel[] arrayObj_Class_ViewAssessment_StudentList, arrayObj_Class_assesmentmodel_Status;
    int saveAssessmentCount, submitAssessmentCount;
    JSONArray jsonArray_MARKS;
    JSONArray jsonArray_Studentid;
    Interface_userservice userService1;

    Class_AssessmentStudentDataList[] arrayObj_Class_monthcontents;
    Class_PostUpdateStudentAssessmentList[] arrayObjUpdateStudentAssessmentList;

    Class_PostUpdateAssessmentSubmitList[] arrayObjPostUpdateAssessmentSubmitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lv = findViewById(R.id.listView);
        //  btn = (Button) findViewById(R.id.btn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Student List");
        userService1 = Class_ApiUtils.getUserService();
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();
        message_TV = findViewById(R.id.message_tv);
        save_assesmentView_BT = findViewById(R.id.save_assesmentView_BT);
        submit_assesmentView_BT = findViewById(R.id.submit_assesmentView_BT);
        sharedpref_loginuserid_Obj = getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();
        Intent intent = getIntent();
        str_assessmentID = intent.getStringExtra("str_assessmentid");
        intval_flag = intent.getIntExtra("Flag", 0);
        str_Recived_assessmentstatus = intent.getStringExtra("str_assessmentstatus");

        Log.e("Zyx", str_Recived_assessmentstatus);


        if (isInternetPresent) {
//            AsyncCallWS2 task = new AsyncCallWS2(this);
//            task.execute();
            getassessmentStudentData();
        } else {
            Toast.makeText(MainActivity2.this, "No Internet", Toast.LENGTH_SHORT).show();

        }

       /* editModelArrayList = populateList();

        customeAdapter = new CustomeAdapter(this,editModelArrayList);
        lv.setAdapter(customeAdapter);*/

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /*Intent intent = new Intent(MainActivity2.this,NextActivity.class);
//                startActivity(intent);*/
//            }
//        });


        save_assesmentView_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Callmethod();
            }

        });


        submit_assesmentView_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayAlert();
            }
        });
//        if (isInternetPresent) {
//            AsyncCall_GetUserAssesmentData task = new AsyncCall_GetUserAssesmentData(MainActivity2.this);
//            task.execute();
//        } else {
//            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
//        }


    }

    private void Callmethod() {
        List<String> list_finalsubmit = new ArrayList<String>();
        List<String> list_studentid = new ArrayList<String>();

        list_finalsubmit.clear();
        list_studentid.clear();
        for (int i = 0; i < CustomeAdapter.editModelArrayList.size(); i++) {


            if (CustomeAdapter.editModelArrayList.get(i).getAssementMarks().trim().length() == 0) {
                list_finalsubmit.add("A");
                String str_value_assesment_studentid = CustomeAdapter.editModelArrayList.get(i).getStudent_id().trim();
                list_studentid.add(str_value_assesment_studentid);

            } else {
                String str_value_assesmentmarks = CustomeAdapter.editModelArrayList.get(i).getAssementMarks().trim();
                list_finalsubmit.add(str_value_assesmentmarks);
                String str_value_assesment_studentid = CustomeAdapter.editModelArrayList.get(i).getStudent_id().trim();
                list_studentid.add(str_value_assesment_studentid);

            }

        }

        jsonArray_MARKS = new JSONArray(list_finalsubmit);
        jsonArray_Studentid = new JSONArray(list_studentid);
        Log.e("jsonArray", jsonArray_MARKS.toString());

        if (isInternetPresent) {
//            AsyncCall_save_assessmentview task3 = new AsyncCall_save_assessmentview(MainActivity2.this);
//            task3.execute();

            SaveMarks_new();
        } else {
            Toast.makeText(MainActivity2.this, "No Internet", Toast.LENGTH_SHORT).show();
        }


    }

    private ArrayList<EditModel> populateList() {

        ArrayList<EditModel> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            EditModel editModel = new EditModel();
            editModel.setAssementMarks(String.valueOf(i));
            list.add(editModel);
        }

        return list;
    }


    private class AsyncCallWS2 extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
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

            student_marks_details();
            return null;
        }

        public AsyncCallWS2(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @Override
        protected void onPostExecute(Void result) {

           /* if ((this.dialog != null) && this.dialog.isShowing()) {
                dialog.dismiss();

            }*/

            dialog.dismiss();

            /////////////////////////////////////////////////////


            if (str_marks_status.equals("No Result")) {
                DisplayAlertForEmptyStudentList();
                Toast.makeText(getApplicationContext(), "No Students present in this level..", Toast.LENGTH_SHORT).show();
                save_assesmentView_BT.setVisibility(View.GONE);
                submit_assesmentView_BT.setVisibility(View.GONE);
                message_TV.setVisibility(View.GONE);
            } else {
                if (str_Recived_assessmentstatus.equals("Completed")) {

                    save_assesmentView_BT.setVisibility(View.GONE);
                    submit_assesmentView_BT.setVisibility(View.GONE);
                    message_TV.setVisibility(View.GONE);

                } else {
                    save_assesmentView_BT.setVisibility(View.VISIBLE);
                    submit_assesmentView_BT.setVisibility(View.VISIBLE);
                    message_TV.setVisibility(View.VISIBLE);

                }

            }


            /////////////////////////////////////////////////////


            editModelArrayList = EditModel_arraylist2;
            customeAdapter = new CustomeAdapter(getApplicationContext(), editModelArrayList);
            lv.setAdapter(customeAdapter);

           /* if (studentAssetment_arrayobj != null) {
                CustomAdapter adapter = new CustomAdapter();
                listView_studentlist.setAdapter(adapter);

                int x = studentAssetment_arrayobj.length;

            } else {
            }*/


            System.out.println("Reached the onPostExecute");

        }//end of onPostExecute
    }// end Async task


    public void student_marks_details() {

        //SIV
        String URL = " http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "GetUserAssesmentData";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/GetUserAssesmentData";
        //SIV
        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("Assesment_ID", str_assessmentID);//str_assessmentID
            //<Assesment_ID>string</Assesment_ID>


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("value at response", response.toString());
                int_count1 = response.getPropertyCount();

                Log.e("number of rows", "" + int_count1);

                int_noOfobjects = int_count1;
                System.out.println("Number of object" + int_noOfobjects);
/*
                <Student_ID>1462</Student_ID>
<Student_Name>RAM S</Student_Name>
<Application_No>HB19TST002</Application_No>
<Assesment_Entry>30</Assesment_Entry>
</vmAssesmentStudent>*/


                studentAssetment_arrayobj = new Class_StudentAssement[int_count1];

                EditModel_arraylist2 = new ArrayList<>();

                for (int i = 0; i < int_count1; i++) {
                    SoapObject list = (SoapObject) response.getProperty(i);
                    SoapPrimitive soapprtv_studentid, soapprtv_studentname, soapprtv_marksstatus, soapprtv_assementmarks, soapprtv_maxmarks;
                    soapprtv_studentid = (SoapPrimitive) list.getProperty("Student_ID");//Student_ID
                    soapprtv_studentname = (SoapPrimitive) list.getProperty("Student_Name");//Student_Name
                    soapprtv_marksstatus = (SoapPrimitive) list.getProperty("Marks_Status");//Application_No
                    soapprtv_assementmarks = (SoapPrimitive) list.getProperty("Assesment_Marks");//Application_No
                    soapprtv_maxmarks = (SoapPrimitive) list.getProperty("Assesment_Entry");

                    str_marks_status = soapprtv_marksstatus.toString();

                    //Class_StudentAssement studentAssement_innerObj = new Class_StudentAssement();
                    EditModel editmodel_innerObj = new EditModel();
                    editmodel_innerObj.setStudent_id(soapprtv_studentid.toString());
                    editmodel_innerObj.setStudentname(soapprtv_studentname.toString());
                    editmodel_innerObj.setstudent_marks_status(soapprtv_marksstatus.toString());
                    editmodel_innerObj.setAssementMarks(soapprtv_assementmarks.toString());
                    editmodel_innerObj.setMaxMarks(soapprtv_maxmarks.toString());
                    editmodel_innerObj.setFlag(str_Recived_assessmentstatus);
                    //  studentAssetment_arrayobj[i] = editmodel_innerObj;
                    EditModel_arraylist2.add(editmodel_innerObj);

                }


            } catch (Throwable t) {
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("request fail", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("UnRegister  Error", "> " + t.getMessage());

        }

    }//End of SIV method


    //=======================Added by shivaleela on Nov 9th 2020

    /*
                "Student_ID": 1472,
            "Student_Name": "ANAND",
            "Application_No": "HB19TST008",
            "Assesment_Marks": "ab",
            "Assesment_Remarks": "",
            "Marks_Status": "Active",
            "Assesment_Entry": "30"

     */
    public void getassessmentStudentData() {

        Call<Class_AssessmentData> call = userService1.GetAssesmentData(str_assessmentID);
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(MainActivity2.this);
        //  progressDoalog.setMax(100);
        //  progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<Class_AssessmentData>() {
            @Override
            public void onResponse(Call<Class_AssessmentData> call, Response<Class_AssessmentData> response) {
                Log.e("Entered resp", "GetAssesmentData");

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    Class_AssessmentData class_loginresponse = response.body();
                    if (class_loginresponse.getStatus()) {
                        List<Class_AssessmentStudentDataList> monthContents_list = response.body().getLst();
                        arrayObj_Class_monthcontents = new Class_AssessmentStudentDataList[monthContents_list.size()];
                        studentAssetment_arrayobj = new Class_StudentAssement[arrayObj_Class_monthcontents.length];
                        EditModel_arraylist2 = new ArrayList<>();

                        for (int i = 0; i < arrayObj_Class_monthcontents.length; i++) {

                            String str_student_ID = String.valueOf(class_loginresponse.getLst().get(i).getStudentID());
                            String str_student_name = class_loginresponse.getLst().get(i).getStudentName();
                            String str_student_markstatus = class_loginresponse.getLst().get(i).getMarksStatus();
                            String str_student_assessmentmarks = class_loginresponse.getLst().get(i).getAssesmentMarks();
                            String str_student_assessmententry = String.valueOf(class_loginresponse.getLst().get(i).getAssesmentEntry());
                            str_marks_status = str_student_markstatus;

                            EditModel editmodel_innerObj = new EditModel();
                            editmodel_innerObj.setStudent_id(str_student_ID);
                            editmodel_innerObj.setStudentname(str_student_name);
                            editmodel_innerObj.setstudent_marks_status(str_student_markstatus);
                            editmodel_innerObj.setAssementMarks(str_student_assessmentmarks);
                            editmodel_innerObj.setMaxMarks(str_student_assessmententry);
                            editmodel_innerObj.setFlag(str_Recived_assessmentstatus);
                            //  studentAssetment_arrayobj[i] = editmodel_innerObj;
                            EditModel_arraylist2.add(editmodel_innerObj);

                        }


                        if (str_marks_status.equals("No Result")) {
                            DisplayAlertForEmptyStudentList();
                            Toast.makeText(getApplicationContext(), "No Students present in this level..", Toast.LENGTH_SHORT).show();
                            save_assesmentView_BT.setVisibility(View.GONE);
                            submit_assesmentView_BT.setVisibility(View.GONE);
                            message_TV.setVisibility(View.GONE);
                        } else {
                            if (str_Recived_assessmentstatus.equals("Completed")) {

                                save_assesmentView_BT.setVisibility(View.GONE);
                                submit_assesmentView_BT.setVisibility(View.GONE);
                                message_TV.setVisibility(View.GONE);

                            } else {
                                save_assesmentView_BT.setVisibility(View.VISIBLE);
                                submit_assesmentView_BT.setVisibility(View.VISIBLE);
                                message_TV.setVisibility(View.VISIBLE);

                            }

                        }

                        editModelArrayList = EditModel_arraylist2;
                        customeAdapter = new CustomeAdapter(getApplicationContext(), editModelArrayList);
                        lv.setAdapter(customeAdapter);


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
                        Toast.makeText(MainActivity2.this, "Kindly restart your application", Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDoalog.dismiss();
//                str_getmonthsummary_errormsg = t.getMessage();
//                alerts_dialog_getexlistviewError();

                Toast.makeText(MainActivity2.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }


    //================================================================

    /////////Call save
    private class AsyncCall_save_assessmentview extends AsyncTask<String, Void, Void> {
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

        public AsyncCall_save_assessmentview(MainActivity2 activity) {
            context = activity;
            dialog = new ProgressDialog(activity, R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i("madhu", "doInBackground");

            SaveMarks();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog.isShowing()) {
                dialog.dismiss();

            }
            if (str_save_marks_status.equals("Active")) {
                Toast.makeText(getApplicationContext(), "Marks entered are saved successfully..", Toast.LENGTH_SHORT).show();
                //finish();
            } else {
                Toast.makeText(getApplicationContext(), "Marks entered are not saved....", Toast.LENGTH_SHORT).show();

            }
            Log.i("madhu", "onPostExecute");

        }
    }


    public void SaveMarks() {


        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "UpdateStudentAssessment";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/UpdateStudentAssessment";

        try {
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("User_ID", str_loginuserID);
            request.addProperty("Assesment_ID", str_assessmentID);
            request.addProperty("Student_ID", jsonArray_Studentid.toString());
            request.addProperty("Student_Marks", jsonArray_MARKS.toString());

            Log.d("Request List=", request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);
                Log.e("madhu", "envelope response" + envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("madhu", "string value at response" + response.toString());
                saveAssessmentCount = response.getPropertyCount();
//                SoapObject obj2 =(SoapObject) response.getProperty(0);
                //   arrayObj_Class_assesmentmodel_Status=new Class_AssessmentModel[response.getPropertyCount()];
                // for (int i = 0; i < response.getPropertyCount(); i++) {
                SoapObject obj2 = (SoapObject) response.getProperty(0);
                SoapPrimitive response_soapobj_marksStatus = (SoapPrimitive) obj2.getProperty("Marks_Status");
                str_save_marks_status = response_soapobj_marksStatus.toString();
                Log.i("madhu", "response1 students=" + response.toString());

            } catch (Throwable t) {
                Log.e("request fail", "> " + t.getMessage());

            }

        } catch (Throwable t) {
            Log.e("madhu", "UnRegister Receiver Error" + "> " + t.getMessage());

        }


    }


    public void SaveMarks_new() {

        Class_PostUpdateStudentAssessmentRequest request = new Class_PostUpdateStudentAssessmentRequest();
        request.setAssesmentID(str_assessmentID);
        request.setStudentID(jsonArray_Studentid.toString());
        request.setStudentMarks(jsonArray_MARKS.toString());
        request.setUserID(str_loginuserID);


        Call<Class_PostUpdateStudentAssessmentResponse> call = userService1.PostUpdateStudentAssessment(request);
        Log.e("TAG", "Request 33: " + new Gson().toJson(request));
        Log.e("TAG", "Request: " + request.toString());

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(MainActivity2.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<Class_PostUpdateStudentAssessmentResponse>() {
            @Override
            public void onResponse(Call<Class_PostUpdateStudentAssessmentResponse> call, Response<Class_PostUpdateStudentAssessmentResponse> response) {
                //System.out.println("response" + response.body().toString());

                  /*  Log.e("response",response.toString());
                    Log.e("TAG", "response 33: "+new Gson().toJson(response) );
                    Log.e("response body", String.valueOf(response.body()));*/
                //   DefaultResponse error1 = ErrorUtils.parseError(response);
                   /* Log.e("response new:",error1.getMsg());
                    Log.e("response new status:", String.valueOf(error1.getstatus()));*/
                // Log.e("response",Gson.fromJson(response.toString(),AddFarmer_Activity1.class));

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    Class_PostUpdateStudentAssessmentResponse class_loginresponse = response.body();
                    List<Class_PostUpdateStudentAssessmentList> monthContents_list = response.body().getLst();
                    arrayObjUpdateStudentAssessmentList = new Class_PostUpdateStudentAssessmentList[monthContents_list.size()];
                    if (class_loginresponse.getStatus()) {
                        for (int i = 0; i < arrayObjUpdateStudentAssessmentList.length; i++) {
                            str_save_marks_status = class_loginresponse.getLst().get(i).getMarksStatus();
                        }

                        if (str_save_marks_status.equals("Active")) {
                            Toast.makeText(getApplicationContext(), "Marks entered are saved successfully..", Toast.LENGTH_SHORT).show();
                            //finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Marks entered are not saved....", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(MainActivity2.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMsg());

                    Toast.makeText(MainActivity2.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(MainActivity2.this, "WS:Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }


    //////////alert
    public void DisplayAlert() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity2.this);
        dialog.setCancelable(false);
        dialog.setTitle("ALERT");
        dialog.setMessage("Are you sure you want to Submit?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (isInternetPresent) {
                    Callmethod();
//                AsyncCall_submit_assessmentview task3 = new AsyncCall_submit_assessmentview(MainActivity2.this);
//                task3.execute();
                    SubmitMarks_new();

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        final AlertDialog alert = dialog.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);

            }
        });
        alert.show();

    }

    public void DisplayAlertForEmptyStudentList() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity2.this);
        dialog.setCancelable(false);
        dialog.setTitle("ALERT");
        dialog.setMessage("No students present in this level");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
                dialog.dismiss();
            }
        });


        final AlertDialog alert = dialog.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);

            }
        });
        alert.show();

    }


    private class AsyncCall_submit_assessmentview extends AsyncTask<String, Void, Void> {
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

        }

        public AsyncCall_submit_assessmentview(MainActivity2 activity) {
            context = activity;
            dialog = new ProgressDialog(activity, R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i("madhu", "doInBackground");
            SubmitMarks();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Log.i("madhu", "onPostExecute");


            if (str_response_submit.equals("Completed")) {
//                editModelArrayList = DisablepopulatedList();
//                customeAdapter = new AssessmentViewAdapter(Activity_Assessmentview.this, editModelArrayList);
//                lv_assessment_studentsview.setAdapter(customeAdapter);
                Toast.makeText(getApplicationContext(), "Marks entered are submitted successfully....", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity2.this, Activity_AssessmentList.class);
                startActivity(i);
                finish();


            } else {
                Toast.makeText(getApplicationContext(), "Marks entered are not submitted....", Toast.LENGTH_SHORT).show();

            }

        }
    }


    public void SubmitMarks() {


        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "UpdateStudentAssessmentSubmit";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/UpdateStudentAssessmentSubmit";

        try {
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("User_ID", str_loginuserID);
            request.addProperty("Assesment_ID", str_assessmentID);

            Log.e("Request List=", request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);
                Log.e("madhu", "envelope response" + envelope.getResponse().toString());
                //SoapObject response = (SoapObject) envelope.getResponse();


                SoapObject response = (SoapObject) envelope.getResponse();

                Log.e("madhu", "string value at response" + response.toString());
                submitAssessmentCount = response.getPropertyCount();
//                SoapObject obj2 =(SoapObject) response.getProperty(0);
                arrayObj_Class_assesmentmodel_Status = new Class_AssessmentModel[response.getPropertyCount()];

                EditModel_arraylist2 = new ArrayList<>();

                for (int i = 0; i < response.getPropertyCount(); i++) {
                    SoapObject obj2 = (SoapObject) response.getProperty(i);
                    SoapPrimitive response_soapobj_marksStatus = (SoapPrimitive) obj2.getProperty("Marks_Status");
                    str_response_submit = response_soapobj_marksStatus.toString();
                    Log.i("madhu", "response1 students=" + response.toString());

                    Class_AssessmentModel innerObj_Class_AssessmentModel = new Class_AssessmentModel();
                    innerObj_Class_AssessmentModel.setAssementModel_status(str_response_submit);
                    innerObj_Class_AssessmentModel.setAssementModel_Flag("1");


                    EditModel editmodel_innerObj = new EditModel();
                    editmodel_innerObj.setFlag(str_Recived_assessmentstatus);
                    EditModel_arraylist2.add(editmodel_innerObj);

                    arrayObj_Class_assesmentmodel_Status[i] = innerObj_Class_AssessmentModel;


                }


            } catch (Throwable t) {
                Log.e("request fail", "> " + t.getMessage());

            }

        } catch (Throwable t) {
            Log.e("madhu", "UnRegister Receiver Error" + "> " + t.getMessage());

        }


    }


    public void SubmitMarks_new() {
        Class_Post_UpdateStudentAssessmentSubmitRequest request = new Class_Post_UpdateStudentAssessmentSubmitRequest();
        request.setAssesmentID(str_assessmentID);
        request.setUserID(str_loginuserID);

        Call<Class_Post_UpdateStudentAssessmentSubmitResponse> call = userService1.PostUpdateStudentAssessmentSubmit(request);
        Log.e("TAG", "Request 33: " + new Gson().toJson(request));
        Log.e("TAG", "Request: " + request.toString());

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(MainActivity2.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<Class_Post_UpdateStudentAssessmentSubmitResponse>() {
            @Override
            public void onResponse(Call<Class_Post_UpdateStudentAssessmentSubmitResponse> call, Response<Class_Post_UpdateStudentAssessmentSubmitResponse> response) {
                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    arrayObj_Class_assesmentmodel_Status = new Class_AssessmentModel[1];
                    EditModel_arraylist2 = new ArrayList<>();
                    Class_Post_UpdateStudentAssessmentSubmitResponse class_loginresponse = response.body();
                    List<Class_PostUpdateAssessmentSubmitList> monthContents_list = response.body().getLst();
                    arrayObjPostUpdateAssessmentSubmitList = new Class_PostUpdateAssessmentSubmitList[monthContents_list.size()];
                    if (class_loginresponse.getStatus()) {
                        for (int i = 0; i < arrayObjPostUpdateAssessmentSubmitList.length; i++) {
                            str_response_submit = class_loginresponse.getLst().get(i).getMarksStatus();
                            Class_AssessmentModel innerObj_Class_AssessmentModel = new Class_AssessmentModel();
                            innerObj_Class_AssessmentModel.setAssementModel_status(str_response_submit);
                            innerObj_Class_AssessmentModel.setAssementModel_Flag("1");


                            EditModel editmodel_innerObj = new EditModel();
                            editmodel_innerObj.setFlag(str_Recived_assessmentstatus);
                            EditModel_arraylist2.add(editmodel_innerObj);

                            arrayObj_Class_assesmentmodel_Status[i] = innerObj_Class_AssessmentModel;

                        }
                        if (str_response_submit.equals("Completed")) {
                            Toast.makeText(getApplicationContext(), "Marks entered are submitted successfully....", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity2.this, Activity_AssessmentList.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "Marks entered are not submitted....", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity2.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    } else {
                        progressDoalog.dismiss();

                        DefaultResponse error = ErrorUtils.parseError(response);
                        // … and use it to show error information

                        // … or just log the issue like we’re doing :)
                        Log.d("error message", error.getMsg());

                        Toast.makeText(MainActivity2.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure (Call call, Throwable t){
                    Log.e("TAG", "onFailure: " + t.toString());

                    Log.e("tag", "Error:" + t.getMessage());
                    Toast.makeText(MainActivity2.this, "WS:Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });// end of call
        }


        @Override
        public boolean onCreateOptionsMenu (Menu menu){

            // Inflate menu items
            getMenuInflater().inflate(R.menu.menu_register, menu);
            menu.findItem(R.id.Sync)
                    .setVisible(false);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){

            // Show toast when menu items selected
            switch (item.getItemId()) {


                case android.R.id.home:
                    Intent i = new Intent(MainActivity2.this, Activity_AssessmentList.class);
                    startActivity(i);
                    finish();

                    break;

            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onBackPressed () {
            super.onBackPressed();
            Intent i = new Intent(MainActivity2.this, Activity_AssessmentList.class);
            startActivity(i);
            finish();

        }


    }//end of class


   /* @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }
}*/
