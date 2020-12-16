package com.det.skillinvillage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.det.skillinvillage.model.Class_GetStudentPaymentResponseList;
import com.det.skillinvillage.model.Class_PostSavePaymentResponseList;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.model.GetScheduleLessonPlanResponse;
import com.det.skillinvillage.model.GetScheduleLessonPlanResponseList;
import com.det.skillinvillage.model.GetStudentPaymentResponse;
import com.det.skillinvillage.model.LessonQuestion;
import com.det.skillinvillage.model.PostSavePaymentRequest;
import com.det.skillinvillage.model.PostScheduleLessonUpdateRequest;
import com.det.skillinvillage.model.PostScheduleLessonUpdateResponse;
import com.det.skillinvillage.model.Post_Save_PaymentResponse;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferencebook_usercredential;

public class Activity_SchedulerLessonPlan extends AppCompatActivity {
    Spinner status_scheduler_Spinner;
    String[] status_scheduler_SpinnerArray={"Yes","No","Partially"};

    ListView lv_questions_list;
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    LessonQuestion Objclass_feesSubmissionList_new;
    LessonQuestion[] ArrayObjclass_lessonquestion=null;
    Interface_userservice userService1;
    GetScheduleLessonPlanResponseList[] arrayObj_class_studentpaymentresp;
    TextView Institutename_Scheduler_TV;
    TextView Levelname_Scheduler_TV;
    TextView date_Scheduler_TV;
    TextView topic_Scheduler_TV;
    TextView lessonplan_Scheduler_TV;
    String selected_status="",str_lp_answer="",str_loginuserID="",str_date="",str_inst="",str_level="",str_topic="",str_scheduleid="",str_lessonplan="",lessonQuestionID="",lessonQuestionname="",lessonQuestionorder="",lessonScheduleID="",scheduleLessonAnswer="",createdBy="";
    EditText Remarks_ET;
    Button Submit_lessonplan_BT;
    SharedPreferences sharedpreferencebook_usercredential_Obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__scheduler_lesson_plan);
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();
        userService1 = Class_ApiUtils.getUserService();
        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_loginuserID = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();

        status_scheduler_Spinner=(Spinner)findViewById(R.id.status_scheduler_Spinner);
        lv_questions_list=(ListView)findViewById(R.id.lv_questions_list);
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
                String str_Remarks=Remarks_ET.getText().toString();
               // str_scheduleid,str_loginuserID,lessonQuestionID,str_lp_answer,ScheduleLesson_Answer,LessonQuestion_ID
                PostScheduleLessonUpdate();
            }

        });
        GetScheduleLessonPlan();
    }//end of oncreate
    public void PostScheduleLessonUpdate() {

        PostScheduleLessonUpdateRequest request = new PostScheduleLessonUpdateRequest();
        request.setUserID(String.valueOf(str_loginuserID));//String.valueOf(str_stuID)
        request.setScheduleID(str_scheduleid);
        request.setLessonQuestionID(lessonQuestionID);
        request.setLessonRemarks(Remarks_ET.getText().toString());
        request.setLessonStatus(selected_status);
        request.setScheduleLessonAnswer(str_lp_answer);

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

                        Toast.makeText(Activity_SchedulerLessonPlan.this, class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(Activity_SchedulerLessonPlan.this,EventListActivity.class);
                        startActivity(i);
                        finish();

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
                        Toast.makeText(Activity_SchedulerLessonPlan.this,error.getMsg(), Toast.LENGTH_SHORT).show();

                    }else{
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

    public void GetScheduleLessonPlan() {

        Call<GetScheduleLessonPlanResponse> call = userService1.GetScheduleLessonPlan("56");//String.valueOf(str_stuID)

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

                            }
                            CustomAdapter adapter = new CustomAdapter();
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






    private class Holder {
        TextView LP_Question_TV;
        EditText LP_Answer_ET;
        TextView LP_Question_ID_TV;
    }

    public class CustomAdapter extends BaseAdapter {

        public CustomAdapter() {

            super();
            Log.d("Inside cfeessubmit()", "Inside CustomAdapter_feessubmit()");
        }

        @Override
        public int getCount() {

            String x = Integer.toString(ArrayObjclass_lessonquestion.length);
            Log.d("Arrayclass.length", x);
            return ArrayObjclass_lessonquestion.length;

        }

        @Override
        public Object getItem(int position) {
            String x = Integer.toString(position);

            Log.d("getItem position", "x");
            return ArrayObjclass_lessonquestion[position];
        }

        @Override
        public long getItemId(int position) {
            String x = Integer.toString(position);
            Log.d("getItemId position", x);
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Holder holder;

            Log.d("CustomAdapter", "position: " + position);

            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(Activity_SchedulerLessonPlan.this).inflate(R.layout.child_lessonplan_layout, parent, false);


                holder.LP_Question_TV = (TextView) convertView.findViewById(R.id.LP_Question_TV);
                holder.LP_Answer_ET = (EditText) convertView.findViewById(R.id.LP_Answer_ET);
                holder.LP_Question_ID_TV = (TextView) convertView.findViewById(R.id.LP_Question_ID_TV);

                Log.d("Inside If convertView", "Inside If convertView");

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
                Log.d("Inside else convertView", "Inside else convertView");
            }

            Objclass_feesSubmissionList_new = (LessonQuestion) getItem(position);

            if(isInternetPresent) {
//                if (loadPendingPaymentCount == 0) {
//                    PendingAmtstudentlist_LL.setVisibility(android.view.View.GONE);
//                    NoRecords_studentlist_LL.setVisibility(android.view.View.VISIBLE);
//                } else {

//                    PendingAmtstudentlist_LL.setVisibility(android.view.View.VISIBLE);
//                    NoRecords_studentlist_LL.setVisibility(android.view.View.GONE);

                    if (Objclass_feesSubmissionList_new != null) {
                         str_lp_answer=holder.LP_Answer_ET.getText().toString();
                        holder.LP_Question_TV.setText(Objclass_feesSubmissionList_new.getQuestionName());
                        holder.LP_Answer_ET.setText(holder.LP_Answer_ET.getText().toString());
                        holder.LP_Question_ID_TV.setText(Objclass_feesSubmissionList_new.getLessonQuestionID());

                    }// end of if
                }else{
                holder.LP_Question_TV.setVisibility(View.GONE);
                holder.LP_Answer_ET.setVisibility(View.GONE);
            }

            return convertView;
        }



    }

}