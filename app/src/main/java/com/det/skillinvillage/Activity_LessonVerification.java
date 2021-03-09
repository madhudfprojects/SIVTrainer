package com.det.skillinvillage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.det.skillinvillage.adapter.LessonPlanAdapter;
import com.det.skillinvillage.model.Class_Get_Topic_Review_LoadResponse;
import com.det.skillinvillage.model.Class_Get_Topic_Review_LoadResponseList;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.model.GetScheduleLessonPlanResponse;
import com.det.skillinvillage.model.GetScheduleLessonPlanResponseList;
import com.det.skillinvillage.model.LessonQuestion;
import com.det.skillinvillage.model.Level;
import com.det.skillinvillage.model.PostScheduleLessonUpdateRequest;
import com.det.skillinvillage.model.PostScheduleLessonUpdateResponse;
import com.det.skillinvillage.model.PostTopicReviewUpdateRequest;
import com.det.skillinvillage.model.PostTopicReviewUpdateResponse;
import com.det.skillinvillage.model.Sandbox;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferencebook_usercredential;

public class Activity_LessonVerification extends AppCompatActivity {

    Spinner questions_LPverification_Spinner;
    Button submit_bt;
    EditText Comments_ET;
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    Interface_userservice userService1;
    Class_Get_Topic_Review_LoadResponseList obj_class_loadspinner;
    Class_Get_Topic_Review_LoadResponseList[] arrayObj_class_studentpaymentresp;
    String str_topicId="",sp_strsand_ID = "", str_topicname = "", str_topiclevelid = "", str_questionanswer = "", str_subjectname = "", str_loginuserID = "", str_docid = "", str_docstatus = "", str_doc_topiclevelid = "", str_doctype = "";
    TextView topicname_TV, subname_TV;
    public ArrayList<Class_Get_Topic_Review_LoadResponseList> lessonQuestionArrayList = null;
    SharedPreferences sharedpreferencebook_usercredential_Obj;

    Spinner refer_Spinner1, reason_Spinner2, confident_Spinner3;
    String sp_strLev_ID = "", selected_levelname = "";
    String[] refer_Spinner1Array = {"Yes", "No", "Others"};
    String[] reason_Spinner2Array = {"The lesson plan had sufficient resources", "I did not get the time to research further", "I did not feel the need to research further", "I could not find any relevant resources", "Others"};
    String[] confident_Spinner3Array = {"Yes", "No", "May be"};

    EditText Yes_1_ET, Others_1_ET, Others_reason_ET, LPV_Answer1_ET, LPV_Answer2_ET;
    LinearLayout others_1_LL, Others_2_LL, No_1_LL, yes_1_LL;
    String selected_confident = "", selected_reason = "", selected_refer = "";

    String str_recieve_referstatus = "", str_receive_referyes = "", str_receive_referno = "", str_receive_refernothers = "", str_receive_referothers = "", str_receive_prepared = "", str_receive_lpxtweek = "", str_receive_tlm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_verification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lesson Plan Verification");
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();
        userService1 = Class_ApiUtils.getUserService();
        sharedpreferencebook_usercredential_Obj = getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_loginuserID = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();

        questions_LPverification_Spinner = (Spinner) findViewById(R.id.questions_LPverification_Spinner);
        submit_bt = (Button) findViewById(R.id.submit_LPverification_bt);
        Comments_ET = (EditText) findViewById(R.id.Comments_ET);
        topicname_TV = (TextView) findViewById(R.id.topicname_TV);
        subname_TV = (TextView) findViewById(R.id.subname_TV);
        refer_Spinner1 = (Spinner) findViewById(R.id.refer_Spinner1);
        reason_Spinner2 = (Spinner) findViewById(R.id.reason_Spinner2);
        confident_Spinner3 = (Spinner) findViewById(R.id.confident_Spinner3);

        Yes_1_ET = (EditText) findViewById(R.id.Yes_1_ET);
        Others_1_ET = (EditText) findViewById(R.id.Others_1_ET);
        Others_reason_ET = (EditText) findViewById(R.id.Others_reason_ET);
        LPV_Answer1_ET = (EditText) findViewById(R.id.LPV_Answer1_ET);
        LPV_Answer2_ET = (EditText) findViewById(R.id.LPV_Answer2_ET);

        others_1_LL = (LinearLayout) findViewById(R.id.others_1_LL);
        Others_2_LL = (LinearLayout) findViewById(R.id.Others_2_LL);
        No_1_LL = (LinearLayout) findViewById(R.id.No_1_LL);
        yes_1_LL = (LinearLayout) findViewById(R.id.yes_1_LL);
        ArrayAdapter dataAdapter_edu = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, refer_Spinner1Array);
        dataAdapter_edu.setDropDownViewResource(R.layout.spinnercenterstyle);
        refer_Spinner1.setAdapter(dataAdapter_edu);

        ArrayAdapter dataAdapter_edu1 = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, reason_Spinner2Array);
        dataAdapter_edu1.setDropDownViewResource(R.layout.spinnercenterstyle);
        reason_Spinner2.setAdapter(dataAdapter_edu1);

        ArrayAdapter dataAdapter_edu2 = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, confident_Spinner3Array);
        dataAdapter_edu2.setDropDownViewResource(R.layout.spinnercenterstyle);
        confident_Spinner3.setAdapter(dataAdapter_edu2);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            str_docid = extras.getString("doc_ID");
            str_docstatus = extras.getString("doc_status");
            str_doc_topiclevelid = extras.getString("doc_topiclevelid");
            str_doctype = extras.getString("doc_type");

            Log.e("str_docid", str_docid);
            Log.e("str_docstatus", str_docstatus);
            if (str_docstatus.equals("Verified")) {
                submit_bt.setVisibility(View.GONE);
            } else {
                submit_bt.setVisibility(View.VISIBLE);
            }

        }
        refer_Spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selected_refer = refer_Spinner1.getSelectedItem().toString();
                Log.e("selected_refer", " : " + selected_refer);
                if (selected_refer.equals("Yes")) {
                    others_1_LL.setVisibility(View.GONE);
                    No_1_LL.setVisibility(View.GONE);
                    yes_1_LL.setVisibility(View.VISIBLE);
                    //  Others_reason_ET.setVisibility(View.GONE);
                    Others_2_LL.setVisibility(View.GONE);
                } else if (selected_refer.equals("No")) {
                    others_1_LL.setVisibility(View.GONE);
                    No_1_LL.setVisibility(View.VISIBLE);
                    yes_1_LL.setVisibility(View.GONE);
                    //  Others_2_LL.setVisibility(View.GONE);
                    //  Others_reason_ET.setVisibility(View.VISIBLE);
                } else if (selected_refer.equals("Others")) {
                    others_1_LL.setVisibility(View.VISIBLE);
                    No_1_LL.setVisibility(View.GONE);
                    yes_1_LL.setVisibility(View.GONE);
                    //  Others_reason_ET.setVisibility(View.GONE);
                    Others_2_LL.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        reason_Spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selected_reason = reason_Spinner2.getSelectedItem().toString();
                Log.e("selected_reason", " : " + selected_reason);
                if (selected_reason.equals("Others")) {
                    Others_2_LL.setVisibility(View.VISIBLE);
                } else {
                    Others_2_LL.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confident_Spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selected_confident = confident_Spinner3.getSelectedItem().toString();
                Log.e("selected_confident", " : " + selected_confident);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        questions_LPverification_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                obj_class_loadspinner = (Class_Get_Topic_Review_LoadResponseList) questions_LPverification_Spinner.getSelectedItem();
                sp_strsand_ID = obj_class_loadspinner.getQuestionID();
                String selected_sandboxName = questions_LPverification_Spinner.getSelectedItem().toString();
                Log.e("selected_sandboxName", " : " + selected_sandboxName);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Comments_ET.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Kindly enter comments ", Toast.LENGTH_SHORT).show();

                }
                if (selected_refer.equals("Yes")) {
                        Log.e("one", "one");

                        if (Yes_1_ET.getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Kindly enter all the required data ", Toast.LENGTH_SHORT).show();
                            Log.e("two", "two");
                        }
                    else{
                        Log.e("three", "three");

                    }

                    }

                    if (selected_refer.equals("No")) {
                        Log.e("four", "four");
                        if (selected_reason.equals("Others")) {
                            Log.e("fiv", "fiv");
                            if (Others_reason_ET.getText().toString().length() == 0) {
                                Toast.makeText(getApplicationContext(), "Kindly enter all the required data ", Toast.LENGTH_SHORT).show();
                                Log.e("six", "six");
                            } else {
                                Log.e("seven", "seven");

                            }
                        } else {
                            Log.e("eight", "eight");

                        }
                    }
                    if (selected_refer.equals("Others")) {
                        Log.e("nine", "nine");
                        if (Others_1_ET.getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Kindly enter all the required data ", Toast.LENGTH_SHORT).show();
                            Log.e("ten", "ten");
                        } else {
                            Log.e("eleven", "eleven");
                        }
                    }

                    if(LPV_Answer1_ET.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "Kindly enter all the required data ", Toast.LENGTH_SHORT).show();
                    Log.e("twelve", "twelve");
                }else if(LPV_Answer2_ET.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "Kindly enter all the required data ", Toast.LENGTH_SHORT).show();
                    Log.e("thirteen", "thirteen");
                }else {
                    Log.e("fourteen", "fourteen");
                    internetDectector = new Class_InternetDectector(getApplicationContext());
                    isInternetPresent = internetDectector.isConnectingToInternet();
                    if (isInternetPresent) {
                        Log.e("fivteen", "fivteen");
                        PostTopicReviewUpdate();

                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
        if (isInternetPresent) {
            GetTopicReviewLoad();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();

        }

    }//end of oncreate


    public void GetTopicReviewLoad() {

        Log.e("str_docid", str_docid);
        Log.e("topiclevelid", str_doc_topiclevelid);
        Log.e("str_doctype", str_doctype);

        Call<Class_Get_Topic_Review_LoadResponse> call = userService1.GetTopicReviewLoad(str_docid, str_doc_topiclevelid, str_doctype);//String.valueOf(str_stuID)

        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_LessonVerification.this);
        //  progressDoalog.setMax(100);
        //  progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<Class_Get_Topic_Review_LoadResponse>() {
            @Override
            public void onResponse(Call<Class_Get_Topic_Review_LoadResponse> call, Response<Class_Get_Topic_Review_LoadResponse> response) {
                Log.e("Entered resp", "Class_Get_Topic_Review_LoadResponse");

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    Class_Get_Topic_Review_LoadResponse class_loginresponse = response.body();
                    if (class_loginresponse.getStatus()) {

                        List<Class_Get_Topic_Review_LoadResponseList> monthContents_list = response.body().getLst();
                        Class_Get_Topic_Review_LoadResponseList[] arrayObj_Class_monthcontents = new Class_Get_Topic_Review_LoadResponseList[monthContents_list.size()];
                        arrayObj_class_studentpaymentresp = new Class_Get_Topic_Review_LoadResponseList[arrayObj_Class_monthcontents.length];
                        lessonQuestionArrayList = new ArrayList<>();
                        for (int i = 0; i < arrayObj_Class_monthcontents.length; i++) {
                            Log.e("TopicReviewLoadResponse", String.valueOf(class_loginresponse.getLst().get(i).getTopicID()));
                            Log.e("TopicReviewLoadResponse", class_loginresponse.getLst().get(i).getQuestionName());

                            Class_Get_Topic_Review_LoadResponseList innerObj_Class_academic = new Class_Get_Topic_Review_LoadResponseList();
                            innerObj_Class_academic.setTopicID(class_loginresponse.getLst().get(i).getTopicID());
                            innerObj_Class_academic.setQuestionID(class_loginresponse.getLst().get(i).getQuestionID());
                            innerObj_Class_academic.setSubjectName(class_loginresponse.getLst().get(i).getSubjectName());
                            innerObj_Class_academic.setTopicName(class_loginresponse.getLst().get(i).getTopicName());
                            innerObj_Class_academic.setTopicLevelID(class_loginresponse.getLst().get(i).getTopicLevelID());
                            innerObj_Class_academic.setQuestionName(class_loginresponse.getLst().get(i).getQuestionName());
                            arrayObj_class_studentpaymentresp[i] = innerObj_Class_academic;
                            lessonQuestionArrayList.add(innerObj_Class_academic);
                            str_topicId= class_loginresponse.getLst().get(i).getTopicID();
                            str_topicname = class_loginresponse.getLst().get(i).getTopicName();
                            str_subjectname = class_loginresponse.getLst().get(i).getSubjectName();
                            str_topiclevelid = class_loginresponse.getLst().get(i).getTopicLevelID();
                            str_questionanswer = class_loginresponse.getLst().get(i).getQuestionAnswer();


                            str_recieve_referstatus = class_loginresponse.getLst().get(i).getReferStatus();
                            str_receive_referyes = class_loginresponse.getLst().get(i).getReferYes();
                            str_receive_referno = class_loginresponse.getLst().get(i).getReferNo();
                            str_receive_refernothers = class_loginresponse.getLst().get(i).getReferNoOthers();
                            str_receive_referothers = class_loginresponse.getLst().get(i).getReferOthers();
                            str_receive_prepared = class_loginresponse.getLst().get(i).getPrepared();
                            str_receive_lpxtweek = class_loginresponse.getLst().get(i).getLPNextWeek();
                            str_receive_tlm = class_loginresponse.getLst().get(i).getTLM();

//                            Log.e("str_recieve_referstatus", str_recieve_referstatus);
//                            Log.e("str_receive_referyes", str_receive_referyes);
//                            Log.e("str_receive_referno", str_receive_referno);
//                            Log.e("str_rec_refernothers", str_receive_refernothers);
//                            Log.e("str_receive_referothers", str_receive_referothers);
//                            Log.e("str_receive_prepared", str_receive_prepared);
//                            Log.e("str_receive_lpxtweek", str_receive_lpxtweek);
//                            Log.e("str_receive_tlm", str_receive_tlm);

                        }//for loop end
                        topicname_TV.setText(str_topicname);
                        subname_TV.setText(str_subjectname);
                        ArrayAdapter<Class_Get_Topic_Review_LoadResponseList> dataAdapter_edu = new ArrayAdapter<Class_Get_Topic_Review_LoadResponseList>(Activity_LessonVerification.this, R.layout.spinnercenterstyle, arrayObj_class_studentpaymentresp);
                        dataAdapter_edu.setDropDownViewResource(R.layout.spinnercenterstyle);
                        questions_LPverification_Spinner.setAdapter(dataAdapter_edu);

                        if (str_docstatus.equals("Verified")) {

                            setValues();

                            ArrayAdapter dataAdapter_edu1 = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, Collections.singletonList(str_recieve_referstatus));
                            dataAdapter_edu1.setDropDownViewResource(R.layout.spinnercenterstyle);
                            refer_Spinner1.setAdapter(dataAdapter_edu1);

                            ArrayAdapter dataAdapter_edu2 = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, Collections.singletonList(str_receive_referno));
                            dataAdapter_edu2.setDropDownViewResource(R.layout.spinnercenterstyle);
                            reason_Spinner2.setAdapter(dataAdapter_edu2);

                            ArrayAdapter dataAdapter_edu3 = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, Collections.singletonList(str_receive_prepared));
                            dataAdapter_edu3.setDropDownViewResource(R.layout.spinnercenterstyle);
                            confident_Spinner3.setAdapter(dataAdapter_edu3);
                        }else{

                        }

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
                        Toast.makeText(Activity_LessonVerification.this, "Kindly restart your application", Toast.LENGTH_SHORT).show();

                    }

                }
            }

            private void setValues() {
//                topicname_TV.setText(str_topicname);
//                subname_TV.setText(str_subjectname);
                try {
                    if (str_questionanswer.equals("") || str_questionanswer.equals("null") || str_questionanswer.equals(null)) {
                        Comments_ET.setText("");

                    } else {
                        Comments_ET.setText(str_questionanswer);

                    }
                    if (str_receive_referyes.equals("") || str_receive_referyes.equals("null") || str_receive_referyes.equals(null)) {
                        Yes_1_ET.setText("");
                    } else {
                        Yes_1_ET.setText(str_receive_referyes);
                    }

                    if (str_receive_refernothers.equals("") || str_receive_refernothers.equals("null") || str_receive_refernothers.equals(null)) {
                        Others_reason_ET.setText("");
                    } else {
                        Others_reason_ET.setText(str_receive_refernothers);
                    }

                    if (str_receive_referothers.equals("") || str_receive_referothers.equals("null") || str_receive_referothers.equals(null)) {
                        Others_1_ET.setText("");
                    } else {
                        Others_1_ET.setText(str_receive_referothers);
                    }
                    if (str_receive_lpxtweek.equals("") || str_receive_lpxtweek.equals("null") || str_receive_lpxtweek.equals(null)) {
                        LPV_Answer1_ET.setText("");
                    } else {
                        LPV_Answer1_ET.setText(str_receive_lpxtweek);
                    }
                    if (str_receive_tlm.equals("") || str_receive_tlm.equals("null") || str_receive_tlm.equals(null)) {
                        LPV_Answer2_ET.setText("");
                    } else {
                        LPV_Answer2_ET.setText(str_receive_tlm);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
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

    public void PostTopicReviewUpdate() {
        PostTopicReviewUpdateRequest request = new PostTopicReviewUpdateRequest();
        request.setQuestion_ID(sp_strsand_ID);
        request.setQuestion_Answer(Comments_ET.getText().toString());
        request.setTopicLevel_ID(str_topiclevelid);
        request.setUser_ID(str_loginuserID);
        request.setDocument_Type(str_doctype);
        request.setTopic_ID(str_topicId);
/*
public string Refer_Status { get; set; }
       public string Refer_Yes { get; set; }
       public string Refer_No { get; set; }
       public string Refer_Others { get; set; }
       public string Refer_No_Others { get; set; }
       public string Prepared { get; set; }
       public string Prepared_Other { get; set; }
       public string LP_NextWeek { get; set; }
       public string TLM { get; set; }

 */

        request.setRefer_Status(selected_refer);
        request.setRefer_Yes(Yes_1_ET.getText().toString());
        request.setRefer_No(selected_reason);
        request.setRefer_Others(Others_1_ET.getText().toString());
        request.setRefer_No_Others(Others_reason_ET.getText().toString());
        request.setPrepared(selected_confident);
        //request.setPrepared_Other("");
        request.setLP_NextWeek(LPV_Answer1_ET.getText().toString());
        request.setTLM(LPV_Answer2_ET.getText().toString());

        Log.e("str_doctype", str_doctype);
        Log.e("posting.usrid.", str_loginuserID);
        Log.e("posting.sp_strsand_ID.", sp_strsand_ID);
        Log.e("str_topiclevelid.", str_topiclevelid);
        Log.e("posting.remarks.", Comments_ET.getText().toString());
        Log.e("selected_refer.", selected_refer);
        Log.e("Yes_1_ET.", Yes_1_ET.getText().toString());
        Log.e("selected_reason", selected_reason);
        Log.e("Others_1_ET", Others_1_ET.getText().toString());
        Log.e("Others_reason_ET.", Others_reason_ET.getText().toString());
        Log.e("selected_confident.", selected_confident);
        Log.e("LPV_Answer1_ET", LPV_Answer1_ET.getText().toString());
        Log.e("LPV_Answer2_ET", LPV_Answer2_ET.getText().toString());
        Log.e("str_topicId.", str_topicId);

        Call<PostTopicReviewUpdateResponse> call = userService1.PostTopicReviewUpdate(request);

        Log.e("LV", "Request 33: " + new Gson().toJson(request));
        Log.e("LV", "Request: " + request.toString());

        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_LessonVerification.this);
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<PostTopicReviewUpdateResponse>() {
            @Override
            public void onResponse
                    (Call<PostTopicReviewUpdateResponse> call, Response<PostTopicReviewUpdateResponse> response) {
                Log.e("Entered resp", "PostTopicReviewUpdateResponse");

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    PostTopicReviewUpdateResponse class_loginresponse = response.body();
                    if (class_loginresponse.getStatus()) {
                        submit_bt.setVisibility(View.GONE);
                        Toast.makeText(Activity_LessonVerification.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Activity_LessonVerification.this, DocView_MainActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        submit_bt.setVisibility(View.VISIBLE);
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
                        Toast.makeText(Activity_LessonVerification.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                    } else {
                        //  Toast.makeText(Activity_SchedulerLessonPlan.this,error.getMsg(), Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDoalog.dismiss();
//                str_getmonthsummary_errormsg = t.getMessage();
//                alerts_dialog_getexlistviewError();

                Toast.makeText(Activity_LessonVerification.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.schedule_menu, menu);
        menu.findItem(R.id.logout_menu)
                .setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {
            case android.R.id.home:
                if (str_doctype.equals("Question")) {
                    Intent i = new Intent(Activity_LessonVerification.this, DocView_QunPaperActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(Activity_LessonVerification.this, DocView_LessonPlanActivity.class);
                    startActivity(i);
                    finish();

                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (str_doctype.equals("Question")) {
            Intent i = new Intent(Activity_LessonVerification.this, DocView_QunPaperActivity.class);
            startActivity(i);
            finish();

        } else {
            Intent i = new Intent(Activity_LessonVerification.this, DocView_LessonPlanActivity.class);
            startActivity(i);
            finish();

        }

    }

}