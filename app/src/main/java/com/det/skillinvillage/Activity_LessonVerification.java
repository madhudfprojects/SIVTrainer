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
import com.det.skillinvillage.model.Class_Get_Topic_Review_LoadResponse;
import com.det.skillinvillage.model.Class_Get_Topic_Review_LoadResponseList;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.model.GetScheduleLessonPlanResponse;
import com.det.skillinvillage.model.GetScheduleLessonPlanResponseList;
import com.det.skillinvillage.model.LessonQuestion;
import com.det.skillinvillage.model.PostScheduleLessonUpdateRequest;
import com.det.skillinvillage.model.PostScheduleLessonUpdateResponse;
import com.det.skillinvillage.model.PostTopicReviewUpdateRequest;
import com.det.skillinvillage.model.PostTopicReviewUpdateResponse;
import com.det.skillinvillage.model.Sandbox;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferencebook_usercredential;

public class Activity_LessonVerification extends AppCompatActivity {

    Spinner questions_LPverification_Spinner;
    Button submit_bt;
    String[] status_scheduler_SpinnerArray = {"Question-1", "Question-2", "Question-3"};
    EditText Comments_ET;
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    Interface_userservice userService1;
    Class_Get_Topic_Review_LoadResponseList obj_class_loadspinner;
    Class_Get_Topic_Review_LoadResponseList[] arrayObj_class_studentpaymentresp;
    String sp_strsand_ID="",str_topicname = "", str_topiclevelid="",str_questionanswer="",str_subjectname = "",str_loginuserID="",str_docid="",str_docstatus="",str_doc_topiclevelid="",str_doctype="";
    TextView topicname_TV, subname_TV;
    public ArrayList<Class_Get_Topic_Review_LoadResponseList> lessonQuestionArrayList = null;
    SharedPreferences sharedpreferencebook_usercredential_Obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_verification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lesson Verification");
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();
        userService1 = Class_ApiUtils.getUserService();
        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_loginuserID = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();

        questions_LPverification_Spinner = (Spinner) findViewById(R.id.questions_LPverification_Spinner);
        submit_bt = (Button) findViewById(R.id.submit_LPverification_bt);
        Comments_ET = (EditText) findViewById(R.id.Comments_ET);
        topicname_TV = (TextView) findViewById(R.id.topicname_TV);
        subname_TV = (TextView) findViewById(R.id.subname_TV);
//        ArrayAdapter dataAdapter_edu = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, status_scheduler_SpinnerArray);
//        dataAdapter_edu.setDropDownViewResource(R.layout.spinnercenterstyle);
//        questions_LPverification_Spinner.setAdapter(dataAdapter_edu);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            str_docid = extras.getString("doc_ID");
            str_docstatus = extras.getString("doc_status");
            str_doc_topiclevelid = extras.getString("doc_topiclevelid");
            str_doctype = extras.getString("doc_type");

            Log.e("str_docid",str_docid);
            Log.e("str_docstatus",str_docstatus);
            if(str_docstatus.equals("Verified")){
                submit_bt.setVisibility(View.GONE);
            }else{
                submit_bt.setVisibility(View.VISIBLE);
            }

        }
        questions_LPverification_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                obj_class_loadspinner = (Class_Get_Topic_Review_LoadResponseList) questions_LPverification_Spinner.getSelectedItem();
                sp_strsand_ID = obj_class_loadspinner.getQuestionID();
                String selected_sandboxName = questions_LPverification_Spinner.getSelectedItem().toString();
                Log.i("selected_sandboxName", " : " + selected_sandboxName);



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

                } else {
                    if(isInternetPresent){
                        PostTopicReviewUpdate();

                    }else{
                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
        if(isInternetPresent){
            GetTopicReviewLoad();
        }else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();

        }

    }

    public void GetTopicReviewLoad() {

        Log.e("str_docid",str_docid);
        Log.e("topiclevelid",str_doc_topiclevelid);
        Log.e("str_doctype",str_doctype);

        Call<Class_Get_Topic_Review_LoadResponse> call = userService1.GetTopicReviewLoad(str_docid,str_doc_topiclevelid,str_doctype);//String.valueOf(str_stuID)

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
                            str_topicname = class_loginresponse.getLst().get(i).getTopicName();
                            str_subjectname = class_loginresponse.getLst().get(i).getSubjectName();
                            str_topiclevelid = class_loginresponse.getLst().get(i).getTopicLevelID();
                            str_questionanswer = class_loginresponse.getLst().get(i).getQuestionAnswer();


                        }//for loop end

                        setValues();
                        ArrayAdapter<Class_Get_Topic_Review_LoadResponseList> dataAdapter_edu = new ArrayAdapter<Class_Get_Topic_Review_LoadResponseList>(Activity_LessonVerification.this, R.layout.spinnercenterstyle, arrayObj_class_studentpaymentresp);
                        dataAdapter_edu.setDropDownViewResource(R.layout.spinnercenterstyle);
                        questions_LPverification_Spinner.setAdapter(dataAdapter_edu);


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
                topicname_TV.setText(str_topicname);
                subname_TV.setText(str_subjectname);
                try {
                    if (str_questionanswer.equals("") || str_questionanswer.equals("null") || str_questionanswer.equals(null)) {
                        Comments_ET.setText("");

                    } else {
                        Comments_ET.setText(str_questionanswer);

                    }
                }catch(Exception e){
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
        Log.e("str_doctype",str_doctype);
        Log.e("posting.usrid.", str_loginuserID);
        Log.e("posting.sp_strsand_ID.", sp_strsand_ID);
        Log.e("str_topiclevelid.",str_topiclevelid);
        Log.e("posting.remarks.", Comments_ET.getText().toString());

        Call<PostTopicReviewUpdateResponse> call = userService1.PostTopicReviewUpdate(request);
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new

                ProgressDialog(Activity_LessonVerification.this);
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
                Intent i = new Intent(Activity_LessonVerification.this, DocView_LessonPlanActivity.class);
                startActivity(i);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Activity_LessonVerification.this, DocView_LessonPlanActivity.class);
        startActivity(i);
        finish();

    }

}