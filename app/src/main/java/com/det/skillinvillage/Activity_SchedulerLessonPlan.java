package com.det.skillinvillage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.det.skillinvillage.adapter.LessonPlanAdapter;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.model.GetScheduleLessonPlanResponse;
import com.det.skillinvillage.model.GetScheduleLessonPlanResponseList;
import com.det.skillinvillage.model.LessonQuestion;
import com.det.skillinvillage.model.PostScheduleLessonUpdateRequest;
import com.det.skillinvillage.model.PostScheduleLessonUpdateResponse;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferencebook_usercredential;

public class Activity_SchedulerLessonPlan extends AppCompatActivity {
    Spinner status_scheduler_Spinner;
    String[] status_scheduler_SpinnerArray={"Select","Yes","No","Partially"};

    NonScrollListView lv_questions_list;
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    static LessonQuestion Objclass_feesSubmissionList_new;
    static LessonQuestion[] ArrayObjclass_lessonquestion=null;
    Interface_userservice userService1;
    GetScheduleLessonPlanResponseList[] arrayObj_class_studentpaymentresp;
    TextView Institutename_Scheduler_TV;
    TextView Levelname_Scheduler_TV;
    TextView date_Scheduler_TV;
    TextView topic_Scheduler_TV;
    TextView lessonplan_Scheduler_TV;
    String selected_status="";
    static String str_lp_answer="";
    String str_loginuserID="";
    String str_date="";
    String str_inst="";
    String str_level="";
    String str_topic="";
    String str_scheduleid="";
    String str_lessonplan="";
    String lessonQuestionID="";
    String lessonQuestionname="";
    String lessonQuestionorder="";
    String lessonScheduleID="";
    String scheduleLessonAnswer="";
    String createdBy="",str_ScheduleId_received="";
    EditText Remarks_ET;
    Button Submit_lessonplan_BT;
    SharedPreferences sharedpreferencebook_usercredential_Obj;
    String  QuestionID_List="\"" ;
    String  Ans_list="\"" ;

    private ArrayList<String> arrLst_QuestionIds = new ArrayList<String>();
    private ArrayList<String> arrLst_answer = new ArrayList<String>();

    JSONArray jsonArray_schedulelessonAnswer;
    JSONArray jsonArray_lessonquestionID;
    LessonPlanAdapter adapter;
    public ArrayList<LessonQuestion> lessonQuestionArrayList;
    public ArrayList<LessonQuestion> LessonQuestion_arraylist2;
    int j=0,j1=0;
    public static ArrayList<String> checkAnsArrayList;
 //   String str_checkempty="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__scheduler_lesson_plan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lesson Plan");

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();
        userService1 = Class_ApiUtils.getUserService();
        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_loginuserID = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
		/*    	Event_Discription = extras.getString("EventDiscription");
		    	Event_Id = extras.getString("EventId");
		    	Event_date = extras.getString("EventDate");
				FellowshipName = extras.getString("FellowshipName");*/

            //Commented and added by shivaleela on june 27th 2019
            //str_ScheduleId = extras.getString("ScheduleId");
            str_ScheduleId_received = extras.getString("ScheduleId");
            Log.e("Tag", "str_ScheduleId_received=" + str_ScheduleId_received);
        }
            status_scheduler_Spinner=(Spinner)findViewById(R.id.status_scheduler_Spinner);
        lv_questions_list=(NonScrollListView)findViewById(R.id.lv_questions_list);
        Institutename_Scheduler_TV=(TextView)findViewById(R.id.Institutename_Scheduler_TV);
        Levelname_Scheduler_TV=(TextView)findViewById(R.id.Levelname_Scheduler_TV);
        date_Scheduler_TV=(TextView)findViewById(R.id.date_Scheduler_TV);
        topic_Scheduler_TV=(TextView)findViewById(R.id.topic_Scheduler_TV);
        lessonplan_Scheduler_TV=(TextView)findViewById(R.id.lessonplan_Scheduler_TV);
        Remarks_ET=(EditText)findViewById(R.id.Remarks_ET);
        Submit_lessonplan_BT=(Button)findViewById(R.id.Submit_lessonplan_BT);

        ArrayAdapter dataAdapter_edu = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, status_scheduler_SpinnerArray);
        dataAdapter_edu.setDropDownViewResource(R.layout.spinnercenterstyle);
        status_scheduler_Spinner.setAdapter(dataAdapter_edu);

        status_scheduler_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_status = status_scheduler_Spinner.getSelectedItem().toString();
                if(selected_status.equals("No")){
                    lv_questions_list.setVisibility(View.GONE);
                }else{
                    lv_questions_list.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Submit_lessonplan_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Remarks_ET.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "Kindly enter remarks", Toast.LENGTH_SHORT).show();

                }else if (selected_status.equals("Select")){
                    Toast.makeText(getApplicationContext(), "Kindly select the status", Toast.LENGTH_SHORT).show();

                }else{
                    PostScheduleLessonUpdate();
                }
            }

        });
        GetScheduleLessonPlan();
    }//end of oncreate



    public void PostScheduleLessonUpdate() {
        checkAnsArrayList=new ArrayList<>();
        Log.e("submitsize", String.valueOf(LessonPlanAdapter.lessonQuestionArrayList.size()));
        for (int i = 0; i <LessonPlanAdapter.lessonQuestionArrayList.size(); i++) {
            String questionId = LessonPlanAdapter.lessonQuestionArrayList.get(i).getLessonQuestionID();
            if (questionId != null) {
                arrLst_QuestionIds.add(questionId);
                QuestionID_List+=questionId+"$";
            }
            String str_lessonanswer = LessonPlanAdapter.lessonQuestionArrayList.get(i).getScheduleLessonAnswer();
            Log.e("tag", "present_studentId==" + str_lessonanswer);

            if(str_lessonanswer.equals("null")||str_lessonanswer.equals("") || str_lessonanswer == null){
               String str_checkempty="Empty";
                checkAnsArrayList.add(str_checkempty);

            }else{
                String str_checkempty="Full";
                checkAnsArrayList.add(str_checkempty);
               // if (str_lessonanswer != null) {
                arrLst_answer.add(str_lessonanswer);
                Ans_list+=str_lessonanswer+"$";
              //  }

            }

        }
        Log.e("tag", "QuestionID_List==" + QuestionID_List+"\"");
        Log.e("tag", "Ans_list==" + Ans_list+"\"");
        Log.e("arrays",Arrays.toString(new ArrayList[]{checkAnsArrayList}));


        if(selected_status.equals("No")){
//            if(checkAnsArrayList.contains("Empty")){
//                Log.e("checkAnsArrayList", "empty");
//                Toast.makeText(Activity_SchedulerLessonPlan.this,"Please Enter all required data",Toast.LENGTH_SHORT).show();
//
//            }else {

                Log.e("checkAnsArrayList", "full");
                //  Toast.makeText(Activity_SchedulerLessonPlan.this,"full",Toast.LENGTH_SHORT).show();


                PostScheduleLessonUpdateRequest request = new PostScheduleLessonUpdateRequest();
                request.setUserID(String.valueOf(str_loginuserID));//String.valueOf(str_stuID)
                request.setScheduleID(str_scheduleid);
//        request.setLessonQuestionID(lessonQuestionID);
                request.setLessonQuestionID("");//QuestionID_List
                request.setLessonRemarks(Remarks_ET.getText().toString());
                request.setLessonStatus(selected_status);
//        request.setScheduleLessonAnswer(str_lp_answer);
                request.setScheduleLessonAnswer("");//Ans_list

                Log.e("posting.usrid.", str_loginuserID);
                Log.e("posting.scheduleid.", str_scheduleid);
                Log.e("posting.qustid.", "");
                Log.e("posting.ans.", "");
                Log.e("posting.remarks.", Remarks_ET.getText().toString());

                Call<PostScheduleLessonUpdateResponse> call = userService1.PostScheduleLessonUpdate(request);
                // Set up progress before call
                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(Activity_SchedulerLessonPlan.this);
                progressDoalog.setTitle("Please wait....");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                // show it
                progressDoalog.show();

                call.enqueue(new Callback<PostScheduleLessonUpdateResponse>() {
                    @Override
                    public void onResponse(Call<PostScheduleLessonUpdateResponse> call, Response<PostScheduleLessonUpdateResponse> response) {
                        Log.e("Entered resp", "PostScheduleLessonUpdate");

                        if (response.isSuccessful()) {
                            progressDoalog.dismiss();
                            PostScheduleLessonUpdateResponse class_loginresponse = response.body();
                            if (class_loginresponse.getStatus()) {
                                Submit_lessonplan_BT.setVisibility(View.GONE);
                                Toast.makeText(Activity_SchedulerLessonPlan.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Activity_SchedulerLessonPlan.this, Activity_HomeScreen.class);
                                startActivity(i);
                                finish();

                            } else {
                                Submit_lessonplan_BT.setVisibility(View.VISIBLE);
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
                                Toast.makeText(Activity_SchedulerLessonPlan.this, error.getMsg(), Toast.LENGTH_SHORT).show();

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

                        Toast.makeText(Activity_SchedulerLessonPlan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });// end of call

          //  }

        }else{

            if(checkAnsArrayList.contains("Empty")){
                Log.e("checkAnsArrayList", "empty");
                Toast.makeText(Activity_SchedulerLessonPlan.this,"Please Enter all required data",Toast.LENGTH_SHORT).show();

            }else {
                Log.e("checkAnsArrayList", "full");
                //  Toast.makeText(Activity_SchedulerLessonPlan.this,"full",Toast.LENGTH_SHORT).show();


                PostScheduleLessonUpdateRequest request = new PostScheduleLessonUpdateRequest();
                request.setUserID(String.valueOf(str_loginuserID));//String.valueOf(str_stuID)
                request.setScheduleID(str_scheduleid);
//        request.setLessonQuestionID(lessonQuestionID);
                request.setLessonQuestionID(QuestionID_List);
                request.setLessonRemarks(Remarks_ET.getText().toString());
                request.setLessonStatus(selected_status);
//        request.setScheduleLessonAnswer(str_lp_answer);
                request.setScheduleLessonAnswer(Ans_list);

                Log.e("posting.usrid.", str_loginuserID);
                Log.e("posting.scheduleid.", str_scheduleid);
                Log.e("posting.qustid.", QuestionID_List);
                Log.e("posting.ans.", Ans_list);
                Log.e("posting.remarks.", Remarks_ET.getText().toString());

                Call<PostScheduleLessonUpdateResponse> call = userService1.PostScheduleLessonUpdate(request);
                // Set up progress before call
                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(Activity_SchedulerLessonPlan.this);
                progressDoalog.setTitle("Please wait....");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                // show it
                progressDoalog.show();

                call.enqueue(new Callback<PostScheduleLessonUpdateResponse>() {
                    @Override
                    public void onResponse(Call<PostScheduleLessonUpdateResponse> call, Response<PostScheduleLessonUpdateResponse> response) {
                        Log.e("Entered resp", "PostScheduleLessonUpdate");

                        if (response.isSuccessful()) {
                            progressDoalog.dismiss();
                            PostScheduleLessonUpdateResponse class_loginresponse = response.body();
                            if (class_loginresponse.getStatus()) {
                                Submit_lessonplan_BT.setVisibility(View.GONE);
                                Toast.makeText(Activity_SchedulerLessonPlan.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Activity_SchedulerLessonPlan.this, Activity_HomeScreen.class);
                                startActivity(i);
                                finish();

                            } else {
                                Submit_lessonplan_BT.setVisibility(View.VISIBLE);
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
                                Toast.makeText(Activity_SchedulerLessonPlan.this, error.getMsg(), Toast.LENGTH_SHORT).show();

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

                        Toast.makeText(Activity_SchedulerLessonPlan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });// end of call

            }


        }

    }

    public void GetScheduleLessonPlan() {

        Call<GetScheduleLessonPlanResponse> call = userService1.GetScheduleLessonPlan(str_ScheduleId_received);//String.valueOf(str_stuID)

        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_SchedulerLessonPlan.this);
        //  progressDoalog.setMax(100);
        //  progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<GetScheduleLessonPlanResponse>() {
            @Override
            public void onResponse(Call<GetScheduleLessonPlanResponse> call, Response<GetScheduleLessonPlanResponse> response) {
                Log.e("Entered resp", "GetScheduleLessonPlanResponse");

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    GetScheduleLessonPlanResponse class_loginresponse = response.body();
                    if (class_loginresponse.getStatus()) {

                        List<GetScheduleLessonPlanResponseList> monthContents_list = response.body().getLst1();
                        GetScheduleLessonPlanResponseList []  arrayObj_Class_monthcontents = new GetScheduleLessonPlanResponseList[monthContents_list.size()];
                        arrayObj_class_studentpaymentresp = new GetScheduleLessonPlanResponseList[arrayObj_Class_monthcontents.length];
                     //   lessonQuestionArrayList.clear();
                      //  LessonQuestion_arraylist2.clear();

                        for (int i = 0; i < arrayObj_Class_monthcontents.length; i++) {
                            Log.e("GetScheduleLessonPlan", String.valueOf(class_loginresponse.getStatus()));
                            Log.e("GetScheduleLessonPlan", class_loginresponse.getMessage());

                            GetScheduleLessonPlanResponseList innerObj_Class_academic = new GetScheduleLessonPlanResponseList();
                            innerObj_Class_academic.setScheduleDate(class_loginresponse.getLst1().get(i).getScheduleDate());
                            innerObj_Class_academic.setSandboxName(class_loginresponse.getLst1().get(i).getSandboxName());
                            innerObj_Class_academic.setAcademicName(class_loginresponse.getLst1().get(i).getAcademicName());
                            innerObj_Class_academic.setClusterName(class_loginresponse.getLst1().get(i).getClusterName());
                            innerObj_Class_academic.setInstituteName(class_loginresponse.getLst1().get(i).getInstituteName());
                            innerObj_Class_academic.setLessonName(class_loginresponse.getLst1().get(i).getLevelName());
                            innerObj_Class_academic.setLessonName(class_loginresponse.getLst1().get(i).getLessonName());
                            innerObj_Class_academic.setTopicName(class_loginresponse.getLst1().get(i).getTopicName());
                            arrayObj_class_studentpaymentresp[i] = innerObj_Class_academic;
                             str_date=class_loginresponse.getLst1().get(i).getScheduleDate();
                             str_inst=class_loginresponse.getLst1().get(i).getInstituteName();
                             str_level=class_loginresponse.getLst1().get(i).getLevelName();
                             str_topic=class_loginresponse.getLst1().get(i).getTopicName();
                             str_lessonplan=class_loginresponse.getLst1().get(i).getLessonName();
                            str_scheduleid=class_loginresponse.getLst1().get(i).getScheduleID();

                            Log.e("topic", class_loginresponse.getLst1().get(i).getTopicName());
                            int sizeCount = class_loginresponse.getLst1().get(i).getLessonQuestion().size();
                            List<LessonQuestion> LessonQuestion_list = response.body().getLst1().get(i).getLessonQuestion();
                            LessonQuestion []  arrayObj_LessonQuestion= new LessonQuestion[LessonQuestion_list.size()];
                            ArrayObjclass_lessonquestion = new LessonQuestion[arrayObj_LessonQuestion.length];
                            Log.e("sizeCount", String.valueOf(sizeCount));
                            Log.e("LessonQuestion_list", String.valueOf(LessonQuestion_list.size()));
                            Log.e("arrayObj_LessonQuestion", String.valueOf(arrayObj_LessonQuestion.length));

                            LessonQuestion_arraylist2 = new ArrayList<>();
                            for (int j = 0; j < sizeCount; j++) {
                                lessonQuestionID = class_loginresponse.getLst1().get(i).getLessonQuestion().get(j).getLessonQuestionID();
                                 lessonQuestionname = class_loginresponse.getLst1().get(i).getLessonQuestion().get(j).getQuestionName();
                                 lessonQuestionorder= class_loginresponse.getLst1().get(i).getLessonQuestion().get(j).getQuestionOrder();
                                 lessonScheduleID = String.valueOf(class_loginresponse.getLst1().get(i).getLessonQuestion().get(j).getScheduleID());
                                 scheduleLessonAnswer = String.valueOf(class_loginresponse.getLst1().get(i).getLessonQuestion().get(j).getScheduleLessonAnswer());
                                 createdBy= class_loginresponse.getLst1().get(i).getLessonQuestion().get(i).getCreatedBy();
                                Log.e("lessonQuestionname", LessonQuestion_list.get(j).getQuestionName());
                                LessonQuestion innerObj_Class = new LessonQuestion();
                                innerObj_Class.setLessonQuestionID(lessonQuestionID);
                                innerObj_Class.setQuestionName(lessonQuestionname);
                                innerObj_Class.setQuestionOrder(lessonQuestionorder);
                                innerObj_Class.setScheduleID(lessonScheduleID);
                                innerObj_Class.setScheduleLessonAnswer(scheduleLessonAnswer);
                                innerObj_Class.setCreatedBy(createdBy);
                                ArrayObjclass_lessonquestion[j] = innerObj_Class;
                                LessonQuestion_arraylist2.add(innerObj_Class);
                            }
                          //  CustomAdapter adapter = new CustomAdapter();
                         //   lv_questions_list.setAdapter(adapter);
                            lessonQuestionArrayList = LessonQuestion_arraylist2;
                            adapter = new LessonPlanAdapter(getApplicationContext(), lessonQuestionArrayList);
                            lv_questions_list.setAdapter(adapter);


                        }//for loop end

                        setValues();


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
                    }else{
                        Toast.makeText(Activity_SchedulerLessonPlan.this,"Kindly restart your application", Toast.LENGTH_SHORT).show();

                    }

                }
            }

            private void setValues() {
                Institutename_Scheduler_TV.setText(str_inst);
                Levelname_Scheduler_TV.setText(str_level);
                date_Scheduler_TV.setText(str_date);
                topic_Scheduler_TV.setText(str_topic);
                lessonplan_Scheduler_TV.setText(str_lessonplan);
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
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}