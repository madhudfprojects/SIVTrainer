package com.det.skillinvillage;

//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.det.skillinvillage.model.AddStudentDetailsRequest;
import com.det.skillinvillage.model.AddStudentDetailsResponse;
import com.det.skillinvillage.model.Class_Response_AddStudentDetailsList;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.District;
import com.det.skillinvillage.model.Education;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.model.LearningMode;
import com.det.skillinvillage.model.State;
import com.det.skillinvillage.model.StudentList;
import com.det.skillinvillage.model.Taluka;
import com.det.skillinvillage.model.Village;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.Activity_ViewStudentList_new.Key_sel_applnstatus;
import static com.det.skillinvillage.Activity_ViewStudentList_new.Key_sel_clustersp;
import static com.det.skillinvillage.Activity_ViewStudentList_new.Key_sel_institutesp;
import static com.det.skillinvillage.Activity_ViewStudentList_new.Key_sel_levelsp;
import static com.det.skillinvillage.Activity_ViewStudentList_new.Key_sel_sandboxsp;
import static com.det.skillinvillage.Activity_ViewStudentList_new.Key_sel_yearsp;
import static com.det.skillinvillage.Activity_ViewStudentList_new.key_studentid;
import static com.det.skillinvillage.Activity_ViewStudentList_new.sharedpreferenc_selectedspinner;
import static com.det.skillinvillage.Activity_ViewStudentList_new.sharedpreferenc_studentid_new;
import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;

public class Activity_EditRegistration extends AppCompatActivity {

    TextView sandbox_edit_tv;
    TextView academic_edit_tv;
    TextView cluster_edit_tv;
    TextView institute_edit_tv;
    TextView school_edit_tv;
    TextView level_edit_tv;
    static TextView dateofbirth_edit_tv;
    TextView maxfees_edit_tv;
    TextView paymentmode_edit_tv;
    static TextView paymentdate_edit_tv;
    TextView markslabel_edit_tv;
    EditText admissionfees_edit_et,studentName_edit_et,fathername_edit_et,mothername_edit_et,mobileno_edit_et,aadhaarno_edit_et,marks_edit_et;
    RadioButton male_edit_RB,female_edit_RB;
    RadioGroup gender_edit_RG;
    Spinner education__edit_Sp,studentstatus_edit_SP;
    public  static ImageView photo_edit_iv;
    Button submit_edit_BT,UploadImgBtn_edit_bt;
    LinearLayout admission_edit_ll;
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    Boolean RegisterResponse,isAdmission=false;
    int radiogroupIndex,int_val_studentID,int_val_sandboxID,int_val_academicid,int_val_clusterid,int_val_instituteid,int_val_levelid,int_val_schoolid;
    static String str_edit_birthdatedisp;
    String selected_studentstatus;
    String selected_edu="",sp_strEdu_ID="";
    String str_sandboxID;
    String str_academicID;
    String str_clusterID;
    String str_instID;
    String str_schoolID;
    String str_levelID;
    String str_sandbox;
    String str_academic;
    String str_cluster;
    String str_inst;
    String str_school;
    String str_level;
    String str_applNo;
    String str_studentname;
    String str_gender;
    static String str_edit_birthdate="";
    String str_marks="",str_marks_4="",str_marks_5="",str_marks_6="",str_marks_7="",str_marks_8="";
    String str_mobileno="";
    String str_fathername="";
    String str_mothername="";
    String str_aadar="",str_receiptno="";
    String str_admissionfee="";
    String str_created_date;
    String str_created_by;
    String str_imgfile="",str_fetched_imgfile="";
    int str_stuID=0;
    String str_learningOpt="";
    int sel_learopt;
    static String str_paymentDate="",str_paymentDatedisp="";
    String[] educationArray = {"Select", "4th Std", "5th Std", "6th Std", "7th Std", "8th Std","9th Std"};
    String[] studentstatusArray = {"Applicant", "Admission"};
    String[] studentstatusArray_admission = {"Admission"};
    Boolean digitalcamerabuttonpressed_new=false;
    byte[] signimageinbytesArray={0};
    String path;
    Bitmap bitmap;
    Bitmap scaledBitmap = null;
    String mCurrentPhotoPath = "";
    final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
    public static int count = 0;
    LinearLayout mainRegistration_ll,PaymentDate_edit_ll,PaymentMode_edit_ll,MaxAdmissionFees_edit_ll,admissionfees_ll;
    private ImageLoadingListener imageListener;
    public DisplayImageOptions displayoption;
    ImageLoader imgLoader = ImageLoader.getInstance();
    EditText receipt_no_edit_et;
    TextView receipt_nolabel_edit_tv;
    Spinner learnoption_Sp;
  //  Class_LearningOption obj_Class_LearningOption;
    SharedPreferences sharedpref_stuid_Obj;
    SharedPreferences sharedpref_loginuserid_Obj;
    SharedPreferences sharedpref_camera_Obj;
    public static final String sharedpreferenc_camera = "cameraflag";
    public static final String key_flagcamera = "flag_camera";

    Class_LearningOption[] Arrayclass_learningOption;
    String selected_learnOption="",sp_strLearningmode_ID="",str_Learning_Mode;
    String str_flagforcamera;
    ArrayAdapter dataAdapter_learnop;

    StudentList class_farmponddetails_offline_obj;
    StudentList[] class_farmerprofileoffline_array_obj;
   int int_newfarmercount;
    int sel_yearsp = 0, sel_sandboxsp = 0, sel_institute=0,sel_applnstatus=0,sel_clustersp = 0, sel_taluksp = 0, sel_levelsp = 0;

    SharedPreferences sharedpref_spinner_Obj;
    String[] mimeTypes =
            {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                    "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                    "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                    "text/plain",
                    "application/pdf",
                    "application/zip"};

    String filePath = "";
    File file_img;
    File file_imageFile;
    private final static int REQUEST_CAMERA = 12;
    private int SELECT_FILE = 2;
    private HashMap<String, Bitmap> cameraImgMap;
    LearningMode[] arrayObj_Class_learningmodeDetails2;
    LearningMode obj_Class_LearningOption;
    Education[] arrayObj_Class_educationDetails2;
    Education obj_Class_education;
    String str_fetched_learningmode="",str_fetched_edu="",str_fetched_statename="",str_fetched_distname="",str_fetched_talukname="",str_fetched_villagename="",str_fetched_address="";


    Spinner state_new_SP,district_new_SP,taluk_new_SP,village_new_SP;
    EditText Studentaddress_ET;
    State Obj_Class_stateDetails;
    String sp_strstate_ID="",selected_stateName="",sp_strdistrict_ID="",sp_strdistrict_state_ID="",selected_district="",sp_strTaluk_ID="",selected_taluk="",sp_strVillage_ID="",selected_village="";
    State[] arrayObj_Class_stateDetails2;
    District Obj_Class_DistrictDetails;
    District[] arrayObj_Class_DistrictListDetails2;
    Taluka Obj_Class_TalukDetails;
    Taluka[] arrayObj_Class_TalukListDetails2;
    Village Obj_Class_VillageListDetails;
    Village[] arrayObj_Class_VillageListDetails2;
    int statepos = 0, districtpos = 0, talukpos = 0, grampanchayatpos = 0, villagepos = 0;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__edit_registration);
        File newdir = new File(dir);
        if (!newdir.exists()) {
            newdir.mkdir();
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Registration Form");

        //Image URL
        initImageLoader(getApplicationContext());
        displayoption = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.transparent)
                .showStubImage(R.drawable.transparent)
                .showImageForEmptyUri(R.drawable.transparent).cacheInMemory()
                .cacheOnDisc().build();

        imageListener = new ImageDisplayListener();


        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

//        SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
//        str_created_by = myprefs.getString("login_userid", "nothing");

//        SharedPreferences myprefs_stuID = this.getSharedPreferences("studentid_edit",Context.MODE_PRIVATE);
//        str_stuID=myprefs_stuID.getString("str_studentID_edit", "nothing");


        sharedpref_spinner_Obj = getSharedPreferences(sharedpreferenc_selectedspinner, Context.MODE_PRIVATE);
        sel_yearsp = sharedpref_spinner_Obj.getInt(Key_sel_yearsp, 0);
        sel_sandboxsp = sharedpref_spinner_Obj.getInt(Key_sel_sandboxsp, 0);
        sel_clustersp = sharedpref_spinner_Obj.getInt(Key_sel_clustersp, 0);
        sel_institute = sharedpref_spinner_Obj.getInt(Key_sel_institutesp, 0);
        sel_levelsp = sharedpref_spinner_Obj.getInt(Key_sel_levelsp, 0);
        sel_applnstatus = sharedpref_spinner_Obj.getInt(Key_sel_applnstatus, 0);
       // sharedpref_stuid_Obj_new=getSharedPreferences(sharedpreferenc_studentid_new, Context.MODE_PRIVATE);
        //Stu_ID_Received= sharedpref_spinner_Obj.getString(key_studentid, "").trim();

        sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        str_created_by = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

        sharedpref_stuid_Obj=getSharedPreferences(sharedpreferenc_selectedspinner, Context.MODE_PRIVATE);
        str_stuID = sharedpref_stuid_Obj.getInt(key_studentid, 0);
        Log.e("str_stuID.oncreate..", String.valueOf(str_stuID));
        sharedpref_camera_Obj=getSharedPreferences(sharedpreferenc_camera, Context.MODE_PRIVATE);
        str_flagforcamera = sharedpref_camera_Obj.getString(key_flagcamera, "").trim();

//       if(!str_stuID.equals(""))
        if(str_stuID!=0)

        {
           int_val_studentID=str_stuID;
       }
        sandbox_edit_tv= findViewById(R.id.sandbox_edit_TV);
        academic_edit_tv= findViewById(R.id.academic_edit_TV);
        cluster_edit_tv= findViewById(R.id.cluster_edit_TV);
        institute_edit_tv= findViewById(R.id.institute_edit_TV);
        school_edit_tv= findViewById(R.id.school_edit_TV);
        level_edit_tv= findViewById(R.id.level_edit_TV);
        dateofbirth_edit_tv= findViewById(R.id.birthDate_edit_TV);
        admissionfees_edit_et= findViewById(R.id.admissionfees_edit_ET);
        maxfees_edit_tv= findViewById(R.id.maxfees_edit_TV);
        paymentmode_edit_tv= findViewById(R.id.paymentmode_edit_TV);
        paymentdate_edit_tv= findViewById(R.id.PaymentDate_edit_TV);
        markslabel_edit_tv= findViewById(R.id.markslabel_edit_TV);
        studentName_edit_et= findViewById(R.id.Studentname_edit_ET);
        fathername_edit_et= findViewById(R.id.fathername_edit_ET);
        mothername_edit_et= findViewById(R.id.mothername_edit_ET);
        mobileno_edit_et= findViewById(R.id.mobile_edit_ET);
        aadhaarno_edit_et= findViewById(R.id.aadar_edit_ET);
        male_edit_RB= findViewById(R.id.male_RB_new_edit);
        female_edit_RB= findViewById(R.id.female_RB_new_edit);
        gender_edit_RG= findViewById(R.id.gender_radiogroup_edit_new);
        submit_edit_BT= findViewById(R.id.submit_edit_BTN);
        studentstatus_edit_SP= findViewById(R.id.studentstatus_edit_sp);
        education__edit_Sp= findViewById(R.id.education__edit_Spinner);
        admission_edit_ll= findViewById(R.id.admission_edit_LL);
        marks_edit_et= findViewById(R.id.marks_edit_ET);
        photo_edit_iv= findViewById(R.id.photo_edit_IV);
        UploadImgBtn_edit_bt= findViewById(R.id.UploadImgBtn_edit_BT);
        PaymentDate_edit_ll= findViewById(R.id.PaymentDate_edit_LL);
        PaymentMode_edit_ll= findViewById(R.id.PaymentMode_edit_LL);
        MaxAdmissionFees_edit_ll= findViewById(R.id.MaxAdmissionFees_edit_LL);
        admissionfees_ll= findViewById(R.id.admissionfees_LL);
        receipt_no_edit_et= findViewById(R.id.receipt_no_edit_ET);
        receipt_nolabel_edit_tv= findViewById(R.id.receipt_nolabel_edit_TV);
        mainRegistration_ll = findViewById(R.id.mainRegistration_LL);
        learnoption_Sp = findViewById(R.id.learnoption_Sp);

        state_new_SP=findViewById(R.id.state_new_SP);
        district_new_SP=findViewById(R.id.district_new_SP);
        taluk_new_SP=findViewById(R.id.taluk_new_SP);
        village_new_SP=findViewById(R.id.village_new_SP);
        Studentaddress_ET=(EditText)findViewById(R.id.Studentaddress_ET);

        if(isInternetPresent){
//            AsyncCallWS_learningMode task=new AsyncCallWS_learningMode(Activity_EditRegistration.this);
//            task.execute();
        }
        @SuppressLint("ResourceType")
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.rotate_in);
        animation1.setDuration(1000);
        mainRegistration_ll.setAnimation(animation1);
        mainRegistration_ll.animate();
        animation1.start();

        UploadImgBtn_edit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getpic();
            }
        });




        dateofbirth_edit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogFragment fromdateFragment = new DatePickerFragmentDateOfBirth();
//                fromdateFragment.show(getFragmentManager(), "Date Picker");


                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                final DatePickerDialog datePickerDialog_birthdate = new DatePickerDialog(Activity_EditRegistration.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar cal = new GregorianCalendar(i, i1, i2);


                        DatePickerDialog dialog = new DatePickerDialog(Activity_EditRegistration.this, this, i, i1, i2);
                        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                        setBirthDate(cal);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog_birthdate.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog_birthdate.show();
            }
        });

        paymentdate_edit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogFragment fromdateFragment = new DatePickerFragmentPaymentDate();
//                fromdateFragment.show(getFragmentManager(), "Date Picker");



                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                final DatePickerDialog datePickerDialog_paymentdate = new DatePickerDialog(Activity_EditRegistration.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar cal = new GregorianCalendar(i, i1, i2);


                        DatePickerDialog dialog = new DatePickerDialog(Activity_EditRegistration.this, this, i, i1, i2);
                        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                        setPaymentDate(cal);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog_paymentdate.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog_paymentdate.show();


            }
        });


        gender_edit_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                radiogroupIndex = gender_edit_RG.indexOfChild(findViewById(checkedId));

                //Method 2 For Getting Index of RadioButton
                //  	pos1=gender_radiogroup.indexOfChild(findViewById(gender_radiogroup.getCheckedRadioButtonId()));


                switch (radiogroupIndex) {
                    case 0:
                        str_gender = "Boy";
                        break;
                    case 1:
                        str_gender = "Girl";
                        break;

                }


            }
        });//end of radiogroup

//        ArrayAdapter dataAdapter_edu = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, educationArray);
//        dataAdapter_edu.setDropDownViewResource(R.layout.spinnercenterstyle);
//        education__edit_Sp.setAdapter(dataAdapter_edu);

//        education__edit_Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//              //  selected_edu = education__edit_Sp.getSelectedItem().toString();
//
//                obj_Class_education = (Education) education__edit_Sp.getSelectedItem();
//                selected_edu= obj_Class_education.getEducation_Name();
//                sp_strEdu_ID = obj_Class_education.getEducation_ID();
//
//                // selected_edu = edu_sp.getSelectedItem().toString();
//                Log.i("selected_edu", " : " + selected_edu);
//                if (selected_edu.equals("Select") || (selected_edu.equals("4th Standard"))) {
//                    marks_edit_et.setVisibility(View.GONE);
//                    markslabel_edit_tv.setVisibility(View.GONE);
//                } else {
//
//                    marks_edit_et.setVisibility(View.VISIBLE);
//                    markslabel_edit_tv.setVisibility(View.VISIBLE);
//
//                    if (selected_edu.equals("5th Standard")) {
//                        // marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
//                        markslabel_edit_tv.setText("5th Standard" + " Marks/Grade");
//                    }
//                    if (selected_edu.equals("6th Standard")) {
//                        //marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
//                        markslabel_edit_tv.setText("6th Standard" + " Marks/Grade");
//                    }
//                    if (selected_edu.equals("7th Standard")) {
//                        //marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
//                        markslabel_edit_tv.setText("7th Standard" + " Marks/Grade");
//                    }
//                    if (selected_edu.equals("8th Standard")) {
//                        // marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
//                        markslabel_edit_tv.setText("8th Standard" + " Marks/Grade");
//                    }
//                    if (selected_edu.equals("9th Standard")) {
//                        //  marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
//                        markslabel_tv.setText("9th Standard" + " Marks/Grade");
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        ArrayAdapter dataAdapter_status = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray);
        dataAdapter_status.setDropDownViewResource(R.layout.spinnercenterstyle);
        studentstatus_edit_SP.setAdapter(dataAdapter_status);

        studentstatus_edit_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_studentstatus = studentstatus_edit_SP.getSelectedItem().toString();
                Log.i("selected_studentstatus", " : " + selected_studentstatus);
                /*if (selected_studentstatus.equals("Admission")) {
                    admission_edit_ll.setVisibility(View.VISIBLE);
                    paymentdate_edit_tv.setVisibility(View.VISIBLE);
                    paymentmode_edit_tv.setVisibility(View.VISIBLE);

                } else {
                    admission_edit_ll.setVisibility(View.GONE);
                    paymentmode_edit_tv.setVisibility(View.GONE);
                    paymentdate_edit_tv.setVisibility(View.GONE);

                }*/
                if(selected_studentstatus.equals("Admission")){
                    isAdmission=true;
                    PaymentDate_edit_ll.setVisibility(View.VISIBLE);
                    PaymentMode_edit_ll.setVisibility(View.VISIBLE);
                    MaxAdmissionFees_edit_ll.setVisibility(View.VISIBLE);
                    admissionfees_ll.setVisibility(View.VISIBLE);
                    receipt_no_edit_et.setVisibility(View.VISIBLE);
                    receipt_nolabel_edit_tv.setVisibility(View.VISIBLE);
                    admissionfees_edit_et.setVisibility(View.VISIBLE);
                    admissionfees_edit_et.setEnabled(true);
                    paymentdate_edit_tv.setVisibility(View.VISIBLE);
                    paymentdate_edit_tv.setEnabled(true);

                }else{
                    isAdmission=false;
                    PaymentDate_edit_ll.setVisibility(View.GONE);
                    PaymentMode_edit_ll.setVisibility(View.GONE);
                    MaxAdmissionFees_edit_ll.setVisibility(View.GONE);
                    admissionfees_ll.setVisibility(View.GONE);
                    admissionfees_edit_et.setVisibility(View.GONE);
                    admissionfees_edit_et.setEnabled(false);
                    paymentdate_edit_tv.setVisibility(View.GONE);
                    paymentdate_edit_tv.setEnabled(false);
                    receipt_no_edit_et.setVisibility(View.GONE);
                    receipt_nolabel_edit_tv.setVisibility(View.GONE);


                    ArrayAdapter dataAdapter_status = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray);
                    dataAdapter_status.setDropDownViewResource(R.layout.spinnercenterstyle);
                    studentstatus_edit_SP.setAdapter(dataAdapter_status);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        learnoption_Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                obj_Class_LearningOption = (LearningMode) learnoption_Sp.getSelectedItem();
                selected_learnOption = obj_Class_LearningOption.getLearningMode_Name();
                sp_strLearningmode_ID = obj_Class_LearningOption.getLearningMode_ID();

                Log.e("tag","selected_learnOption="+selected_learnOption);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        education__edit_Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                obj_Class_education = (Education) education__edit_Sp.getSelectedItem();
                selected_edu= obj_Class_education.getEducation_Name();
                sp_strEdu_ID = obj_Class_education.getEducation_ID();

                // selected_edu = edu_sp.getSelectedItem().toString();
                Log.i("selected_edu", " : " + selected_edu);
                if (selected_edu.equals("Select") || (selected_edu.equals("4th Standard"))) {
                    marks_edit_et.setVisibility(View.GONE);
                    markslabel_edit_tv.setVisibility(View.GONE);
                } else {

                    marks_edit_et.setVisibility(View.VISIBLE);
                    markslabel_edit_tv.setVisibility(View.VISIBLE);

                    if (selected_edu.equals("5th Standard")) {
                        // marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
                        markslabel_edit_tv.setText("4th Standard" + " Marks/Grade");
                    }
                    if (selected_edu.equals("6th Standard")) {
                        //marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
                        markslabel_edit_tv.setText("5th Standard" + " Marks/Grade");
                    }
                    if (selected_edu.equals("7th Standard")) {
                        //marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
                        markslabel_edit_tv.setText("6th Standard" + " Marks/Grade");
                    }
                    if (selected_edu.equals("8th Standard")) {
                        // marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
                        markslabel_edit_tv.setText("7th Standard" + " Marks/Grade");
                    }
                    if (selected_edu.equals("9th Standard")) {
                        //  marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
                        markslabel_edit_tv.setText("8th Standard" + " Marks/Grade");
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        state_new_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                Obj_Class_stateDetails = (State) state_new_SP.getSelectedItem();
                sp_strstate_ID = Obj_Class_stateDetails.getStateID().toString();
                selected_stateName = state_new_SP.getSelectedItem().toString();
                int sel_statesp_new = state_new_SP.getSelectedItemPosition();

                Update_districtid_spinner(sp_strstate_ID);
               /* if(sel_statesp_new!=sel_statesp)
                {
                    sel_statesp=sel_statesp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();
                    districtlist_SP.setSelection(0);
                    taluklist_SP.setSelection(0);
                    villagelist_SP.setSelection(0);
                    grampanchayatlist_SP.setSelection(0);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        district_new_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_DistrictDetails = (District) district_new_SP.getSelectedItem();
                sp_strdistrict_ID = Obj_Class_DistrictDetails.getDistrictID();
                sp_strdistrict_state_ID = Obj_Class_DistrictDetails.getStateID();
                selected_district = district_new_SP.getSelectedItem().toString();
                int sel_districtsp_new = district_new_SP.getSelectedItemPosition();
                // Log.e("selected_district", " : " + selected_district);
//                Log.i("sp_strdistrict_state_ID", " : " + sp_strdistrict_state_ID);
                Log.e("sp_strdistrict_ID", " : " + sp_strdistrict_ID);
                // Log.i("sp_strstate_ID", " : " + sp_strstate_ID);


                // Update_TalukId_spinner("5623");

                Update_TalukId_spinner(sp_strdistrict_ID);
                // Update_GramPanchayatID_spinner(sp_strdistrict_ID);

                /*if(sel_districtsp_new!=sel_districtsp) {
                    sel_districtsp=sel_districtsp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();
                    taluklist_SP.setSelection(0);
                    villagelist_SP.setSelection(0);
                    grampanchayatlist_SP.setSelection(0);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        taluk_new_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_TalukDetails = (Taluka) taluk_new_SP.getSelectedItem();
                sp_strTaluk_ID = Obj_Class_TalukDetails.getTalukaID();
                selected_taluk = taluk_new_SP.getSelectedItem().toString();
                int sel_taluksp_new = taluk_new_SP.getSelectedItemPosition();

                // Update_VillageId_spinner("5433");//5516,sp_strTaluk_ID
//                Log.i("selected_taluk", " : " + selected_taluk);
//
//                Log.e("sp_stryear_ID..", sp_stryear_ID);
//                Log.e("sp_strstate_ID..", sp_strstate_ID);
//                Log.e("sp_strdistrict_ID..", sp_strdistrict_ID);
                //Log.e("sp_strTaluk_ID..", sp_strTaluk_ID);

                // Update_VillageId_spinner(sp_strTaluk_ID);
                // Update_GramPanchayatID_spinner(sp_strTaluk_ID);
                Update_VillageId_spinner(sp_strTaluk_ID);

               /* if(sel_taluksp_new!=sel_taluksp) {
                    sel_taluksp=sel_taluksp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();

                    villagelist_SP.setSelection(0);
                    grampanchayatlist_SP.setSelection(0);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        village_new_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_VillageListDetails = (Village) village_new_SP.getSelectedItem();
                sp_strVillage_ID = Obj_Class_VillageListDetails.getVillageID();
                selected_village = village_new_SP.getSelectedItem().toString();

                int sel_villagesp_new = village_new_SP.getSelectedItemPosition();

                /*if(sel_villagesp_new!=sel_villagesp) {
                    sel_villagesp=sel_villagesp_new;
                    ViewFarmerList_arraylist.clear();
                    farmerListViewAdapter.notifyDataSetChanged();

                    // grampanchayatlist_SP.setSelection(0);
                }
*/

                Log.e("yearselected", village_new_SP.getSelectedItem().toString());


                //  Update_ids_farmerlist_listview(sp_stryear_ID, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strgrampanchayat_ID);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit_edit_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File imagefile;

                if(digitalcamerabuttonpressed_new) {
                    imagefile = new File(CameraPhotoCapture.compressedfilepaths);
                    Bitmap myBitmap = BitmapFactory.decodeFile(imagefile.getAbsolutePath());


                    path = imagefile.getAbsolutePath();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    signimageinbytesArray = stream.toByteArray();
                    str_imgfile = Base64.encodeToString(signimageinbytesArray, Base64.DEFAULT);
                    Log.e("str_imgfile  " , str_imgfile);
                    digitalcamerabuttonpressed_new=false;
                    if (Validation()) {

//                    int_val_sandboxID = Integer.parseInt(str_sandboxID);
//                    int_val_academicid = Integer.parseInt(str_academicID);
//                    int_val_clusterid = Integer.parseInt(str_clusterID);
//                    int_val_instituteid = Integer.parseInt(str_instID);
//                    int_val_schoolid = Integer.parseInt(str_schoolID);
//                    int_val_levelid = Integer.parseInt(str_levelID);

                        //UpdateEditedData();
                        update_editedDetails_PondDetails_DB();


                    }else{
                        Toast.makeText(getApplicationContext(), "Please enter all the required data",
                                Toast.LENGTH_SHORT).show();

                    }

                }else{
                    signimageinbytesArray=null;
                    if (Validation()) {

//                    int_val_sandboxID = Integer.parseInt(str_sandboxID);
//                    int_val_academicid = Integer.parseInt(str_academicID);
//                    int_val_clusterid = Integer.parseInt(str_clusterID);
//                    int_val_instituteid = Integer.parseInt(str_instID);
//                    int_val_schoolid = Integer.parseInt(str_schoolID);
//                    int_val_levelid = Integer.parseInt(str_levelID);

                        //UpdateEditedData();
                        update_editedDetails_PondDetails_DB();


                    }else{
                        Toast.makeText(getApplicationContext(), "Please enter all the required data",
                                Toast.LENGTH_SHORT).show();

                    }
                }


//                if (Validation()) {
//
////                    int_val_sandboxID = Integer.parseInt(str_sandboxID);
////                    int_val_academicid = Integer.parseInt(str_academicID);
////                    int_val_clusterid = Integer.parseInt(str_clusterID);
////                    int_val_instituteid = Integer.parseInt(str_instID);
////                    int_val_schoolid = Integer.parseInt(str_schoolID);
////                    int_val_levelid = Integer.parseInt(str_levelID);
//
//                    //UpdateEditedData();
//                    update_editedDetails_PondDetails_DB();
//
//
//                }else{
//                    Toast.makeText(getApplicationContext(), "Please enter all the required data",
//                            Toast.LENGTH_SHORT).show();
//
//                }
            }
        });
        if (paymentdate_edit_tv.getVisibility() == View.VISIBLE) {
            setcurrentdate();
        }

       // GetStudentRecord();


        Data_from_StudentDetails_DB(str_stuID);

        uploadfromDB_LearningOptionlist();
        uploadfromDB_Educationlist();
        uploadfromDB_Statelist();
        uploadfromDB_Districtlist();
        uploadfromDB_Taluklist();
        uploadfromDB_Villagelist();


    }//end of oncreate

    public void uploadfromDB_Statelist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_stateDetails2 = new State[x];
        if (cursor1.moveToFirst()) {

            do {
                State innerObj_Class_SandboxList = new State();
                innerObj_Class_SandboxList.setStateID(cursor1.getString(cursor1.getColumnIndex("StateID")));
                innerObj_Class_SandboxList.setStateName(cursor1.getString(cursor1.getColumnIndex("StateName")));
                //  innerObj_Class_SandboxList.(cursor1.getString(cursor1.getColumnIndex("state_yearid")));


                arrayObj_Class_stateDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_stateDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            state_new_SP.setAdapter(dataAdapter);
            //state_new_SP.setSelection(statepos);
            state_new_SP.setSelection(getIndex(state_new_SP, str_fetched_statename));

            /*if(x>sel_statesp) {
                statelist_SP.setSelection(sel_statesp);
            }*/
        }

    }
    public void uploadfromDB_Districtlist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS DistrictListRest(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_DistrictListDetails2 = new District[x];
        if (cursor1.moveToFirst()) {

            do {
                District innerObj_Class_SandboxList = new District();
                innerObj_Class_SandboxList.setDistrictID(cursor1.getString(cursor1.getColumnIndex("DistrictID")));
                innerObj_Class_SandboxList.setDistrictName(cursor1.getString(cursor1.getColumnIndex("DistrictName")));
                //innerObj_Class_SandboxList.setYear_id(cursor1.getString(cursor1.getColumnIndex("Distr_yearid")));
                innerObj_Class_SandboxList.setStateID(cursor1.getString(cursor1.getColumnIndex("Distr_Stateid")));


                arrayObj_Class_DistrictListDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_DistrictListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            district_new_SP.setAdapter(dataAdapter);
            district_new_SP.setSelection(getIndex(district_new_SP, str_fetched_distname));
            /*if(x>sel_districtsp) {
                districtlist_SP.setSelection(sel_districtsp);
            }*/
        }

    }
    public void uploadfromDB_Taluklist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS TalukListRest(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM TalukListRest", null);

        // Cursor cursor2 = db1.rawQuery("select T.taluka_id,V.village_id,V.village_name from master_state S, master_district D, master_taluka T, master_village V, master_panchayat P where S.state_id=D.state_id AND D.district_id=T.district_id AND T.taluka_id=V.taluk_id AND T.district_id=P.district_id AND (S.state_id in (1,12,25))",null);
        //  Cursor cursor1 = db1.rawQuery("select T.TalukID,T.TalukName,T.Taluk_districtid from DistrictList D, TalukList T where D.DistrictID=T.Taluk_districtid",null);

        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_TalukListDetails2 = new Taluka[x];
        if (cursor1.moveToFirst()) {

            do {
                Taluka innerObj_Class_SandboxList = new Taluka();
                innerObj_Class_SandboxList.setTalukaID(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                if (cursor1.getString(cursor1.getColumnIndex("TalukName")).isEmpty()) {
                    innerObj_Class_SandboxList.setTalukaName("Empty In DB");
                } else {
                    innerObj_Class_SandboxList.setTalukaName(cursor1.getString(cursor1.getColumnIndex("TalukName")));
                }
                //innerObj_Class_SandboxList.setTaluk_name(cursor1.getString(cursor1.getColumnIndex("TalukName")));
                innerObj_Class_SandboxList.setDistrictID(cursor1.getString(cursor1.getColumnIndex("Taluk_districtid")));


                arrayObj_Class_TalukListDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_TalukListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            taluk_new_SP.setAdapter(dataAdapter);
            taluk_new_SP.setSelection(getIndex(taluk_new_SP, str_fetched_talukname));
            /*if(x>sel_taluksp) {
                taluklist_SP.setSelection(sel_taluksp);
            }*/
        }

    }
    public void uploadfromDB_Villagelist() {

        SQLiteDatabase db_village = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        //db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        Cursor cursor1 = db_village.rawQuery("SELECT DISTINCT * FROM VillageListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_VillageListDetails2 = new Village[x];
        if (cursor1.moveToFirst()) {

            do {
                Village innerObj_Class_villageList = new Village();
                innerObj_Class_villageList.setVillageID(cursor1.getString(cursor1.getColumnIndex("VillageID")));
                innerObj_Class_villageList.setVillageName(cursor1.getString(cursor1.getColumnIndex("Village")));
                innerObj_Class_villageList.setTalukaID(cursor1.getString(cursor1.getColumnIndex("TalukID")));
               // innerObj_Class_villageList.setPanchayatID(cursor1.getString(cursor1.getColumnIndex("PanchayatID")));

                arrayObj_Class_VillageListDetails2[i] = innerObj_Class_villageList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_village.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            village_new_SP.setAdapter(dataAdapter);
           // village_new_SP.setSelection(villagepos);
            village_new_SP.setSelection(getIndex(village_new_SP, str_fetched_villagename));

            /*if(x>sel_villagesp) {
                villagelist_SP.setSelection(sel_villagesp);
            }*/
        }


    }
    public void Update_districtid_spinner(String str_stateid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS DistrictListRest(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictListRest WHERE Distr_Stateid='" + str_stateid + "'", null);
        //Cursor cursor1 = db1.rawQuery("select * from DistrictList D, TalukList T where D.DistrictID=T.Taluk_districtid AND D.Distr_Stateid='" + str_stateid + "'",null);

        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_DistrictListDetails2 = new District[x];
        if (cursor1.moveToFirst()) {

            do {
                District innerObj_Class_AcademicList = new District();
                innerObj_Class_AcademicList.setDistrictID(cursor1.getString(cursor1.getColumnIndex("DistrictID")));
                innerObj_Class_AcademicList.setDistrictName(cursor1.getString(cursor1.getColumnIndex("DistrictName")));
                //innerObj_Class_AcademicList.setYear_id(cursor1.getString(cursor1.getColumnIndex("Distr_yearid")));
                innerObj_Class_AcademicList.setStateID(cursor1.getString(cursor1.getColumnIndex("Distr_Stateid")));


                arrayObj_Class_DistrictListDetails2[i] = innerObj_Class_AcademicList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_DistrictListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            district_new_SP.setAdapter(dataAdapter);
          //  district_new_SP.setSelection(districtpos);
            /*if(x>sel_districtsp) {
                districtlist_SP.setSelection(sel_districtsp);
            }*/
        }

    }

    public void Update_TalukId_spinner(String str_distid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS TalukListRest(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM TalukListRest WHERE Taluk_districtid='" + str_distid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Tcount", Integer.toString(x));

        int i = 0;

        arrayObj_Class_TalukListDetails2 = new Taluka[x];


        if (x > 0) {
            if (cursor1.moveToFirst()) {

                do {
                    Taluka innerObj_Class_talukList = new Taluka();
                    innerObj_Class_talukList.setTalukaID(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                    innerObj_Class_talukList.setTalukaName(cursor1.getString(cursor1.getColumnIndex("TalukName")));
                    innerObj_Class_talukList.setDistrictID(cursor1.getString(cursor1.getColumnIndex("Taluk_districtid")));


                    arrayObj_Class_TalukListDetails2[i] = innerObj_Class_talukList;
                    //Log.e("taluk_name",cursor1.getString(cursor1.getColumnIndex("TalukName")));
                    i++;
                } while (cursor1.moveToNext());
            }//if ends

        }
        db1.close();


        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_TalukListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            taluk_new_SP.setAdapter(dataAdapter);
         //   taluk_new_SP.setSelection(talukpos);
            /*if(x>sel_taluksp) {
                taluklist_SP.setSelection(sel_taluksp);
            }*/
        }

        /*if(x==0)
        {
            arrayObj_Class_TalukListDetails2 = new Class_TalukListDetails[1];
            Class_TalukListDetails innerObj_Class_talukList = new Class_TalukListDetails();
            innerObj_Class_talukList.setTaluk_id("2000");
            innerObj_Class_talukList.setTaluk_name("No Records");
            innerObj_Class_talukList.setDistrict_id("2000");


            arrayObj_Class_TalukListDetails2[0] = innerObj_Class_talukList;

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_TalukListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            taluklist_farmers_sp.setAdapter(dataAdapter);
            taluklist_farmers_sp.setSelection(talukpos);
        }*/

    }

    public void Update_VillageId_spinner(String str_talukid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        //db1.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db1.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        //Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageList WHERE TalukID='" + str_talukid + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageListRest WHERE TalukID='" + str_talukid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Vcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_VillageListDetails2 = new Village[x];
        if (cursor1.moveToFirst()) {

            do {
                Village innerObj_Class_villageList = new Village();
                innerObj_Class_villageList.setVillageID(cursor1.getString(cursor1.getColumnIndex("VillageID")));
                innerObj_Class_villageList.setVillageName(cursor1.getString(cursor1.getColumnIndex("Village")));
                innerObj_Class_villageList.setTalukaID(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                //innerObj_Class_villageList.setPanchayatID(cursor1.getString(cursor1.getColumnIndex("PanchayatID")));

                arrayObj_Class_VillageListDetails2[i] = innerObj_Class_villageList;
                // Log.e("village_name", cursor1.getString(cursor1.getColumnIndex("TalukName")));
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            village_new_SP.setAdapter(dataAdapter);
          //  village_new_SP.setSelection(villagepos);
            /*if(x>sel_villagesp) {
                villagelist_SP.setSelection(sel_villagesp);
            }*/

        }


        /*if(x==0)
        {
            arrayObj_Class_VillageListDetails2 = new Class_VillageListDetails[1];
            Class_VillageListDetails innerObj_Class_villageList = new Class_VillageListDetails();
            innerObj_Class_villageList.setVillage_id("2000");
            innerObj_Class_villageList.setVillage_name("No Records");
            innerObj_Class_villageList.setTaluk_id("2000");


            arrayObj_Class_VillageListDetails2[0] = innerObj_Class_villageList;
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            villagelist_farmers_sp.setAdapter(dataAdapter);
           // villagelist_farmers_sp.setSelection(villagepos);

        }*/

    }

    public void uploadfromDB_LearningOptionlist() {

        SQLiteDatabase db_village = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        // db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db_village.execSQL("CREATE TABLE IF NOT EXISTS LearningModeListRest(LearningModeID VARCHAR,LearningModeName VARCHAR);");
        Cursor cursor1 = db_village.rawQuery("SELECT DISTINCT * FROM LearningModeListRest", null);
        int x = cursor1.getCount();
        Log.e("cursor learingmodecount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_learningmodeDetails2 = new LearningMode[x];
        if (cursor1.moveToFirst()) {

            do {
                LearningMode innerObj_Class_levelList = new LearningMode();
                innerObj_Class_levelList.setLearningMode_ID(cursor1.getString(cursor1.getColumnIndex("LearningModeID")));
                innerObj_Class_levelList.setLearningMode_Name(cursor1.getString(cursor1.getColumnIndex("LearningModeName")));
                arrayObj_Class_learningmodeDetails2[i] = innerObj_Class_levelList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_village.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_learningmodeDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            learnoption_Sp.setAdapter(dataAdapter);
            learnoption_Sp.setSelection(getIndex(learnoption_Sp, str_fetched_learningmode));

//            if (x > sel_levelsp) {
//                level_sp.setSelection(sel_levelsp);
//            }
        }


    }

    public int getIndex(Spinner spinner, String myString) {
        int index = 0;
        String item = null;
        for (int i = 0; i < spinner.getCount(); i++) {
            //   Log.e("entered getIndex", "entered getIndex");

            item = spinner.getItemAtPosition(i).toString();
            if (item.equals(myString)) {
                index = i;
                //  Log.e("entered myString", "entered myString");

            }
        }
        return index;
    }

    public void uploadfromDB_Educationlist() {

        SQLiteDatabase db_village = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        // db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db_village.execSQL("CREATE TABLE IF NOT EXISTS EducationListRest(EducationID VARCHAR,EducationName VARCHAR);");
        Cursor cursor1 = db_village.rawQuery("SELECT DISTINCT * FROM EducationListRest", null);
        int x = cursor1.getCount();
        Log.e("cursor educount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_educationDetails2 = new Education[x];
        if (cursor1.moveToFirst()) {

            do {
                Education innerObj_Class_levelList = new Education();
                innerObj_Class_levelList.setEducation_ID(cursor1.getString(cursor1.getColumnIndex("EducationID")));
                innerObj_Class_levelList.setEducation_Name(cursor1.getString(cursor1.getColumnIndex("EducationName")));
                arrayObj_Class_educationDetails2[i] = innerObj_Class_levelList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_village.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_educationDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            education__edit_Sp.setAdapter(dataAdapter);
            education__edit_Sp.setSelection(getIndex(education__edit_Sp, str_fetched_edu));

//            if (x > sel_levelsp) {
//                level_sp.setSelection(sel_levelsp);
//            }
        }


    }

    public void Data_from_StudentDetails_DB(int str_studentID)
    {

        Log.e("str_studentID", String.valueOf(str_studentID));
        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);

        //SIV_DB
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR);");


        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StudentDetailsRest WHERE StudentID='" + str_studentID + "'", null);
        int x = cursor1.getCount();

        Log.e("EditSTUDEntid", String.valueOf(x));
        Log.e("Editstudent_count", String.valueOf(x));


        int i = 0;
        class_farmponddetails_offline_obj = new StudentList();
        if (x > 0) {
            if (cursor1.moveToFirst()) {

                do {
                    StudentList innerObj_Class_SandboxList = new StudentList();

                    innerObj_Class_SandboxList.setAcademicID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("AcademicID"))));
                    innerObj_Class_SandboxList.setAcademicName(cursor1.getString(cursor1.getColumnIndex("AcademicName")));
                    innerObj_Class_SandboxList.setAdmissionFee(cursor1.getString(cursor1.getColumnIndex("AdmissionFee")));
                    innerObj_Class_SandboxList.setApplicationNo(cursor1.getString(cursor1.getColumnIndex("ApplicationNo")));
                    innerObj_Class_SandboxList.setBalanceFee(cursor1.getString(cursor1.getColumnIndex("BalanceFee")));
                    innerObj_Class_SandboxList.setBirthDate(cursor1.getString(cursor1.getColumnIndex("BirthDate")));
                    innerObj_Class_SandboxList.setClusterID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("ClusterID"))));//DispFarmerTable_Farmer_Code VARCHAR
                    innerObj_Class_SandboxList.setClusterName(cursor1.getString(cursor1.getColumnIndex("ClusterName")));//DispFarmerTable_Farmer_Code VARCHAR
                    innerObj_Class_SandboxList.setCreatedDate(cursor1.getString(cursor1.getColumnIndex("CreatedDate")));
                    innerObj_Class_SandboxList.setEducation(cursor1.getString(cursor1.getColumnIndex("Education")));
                    innerObj_Class_SandboxList.setFatherName(cursor1.getString(cursor1.getColumnIndex("FatherName")));
                    innerObj_Class_SandboxList.setGender(cursor1.getString(cursor1.getColumnIndex("Gender")));
                    innerObj_Class_SandboxList.setInstituteName(cursor1.getString(cursor1.getColumnIndex("InstituteName")));
                    innerObj_Class_SandboxList.setInstituteID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("InstituteID"))));
                    innerObj_Class_SandboxList.setLevelID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("LevelID"))));
                    innerObj_Class_SandboxList.setLevelName(cursor1.getString(cursor1.getColumnIndex("LevelName")));
                    innerObj_Class_SandboxList.setMarks4(cursor1.getString(cursor1.getColumnIndex("Marks4")));
                    innerObj_Class_SandboxList.setMarks5(cursor1.getString(cursor1.getColumnIndex("Marks5")));
                    innerObj_Class_SandboxList.setMarks6(cursor1.getString(cursor1.getColumnIndex("Marks6")));
                    innerObj_Class_SandboxList.setMarks7(cursor1.getString(cursor1.getColumnIndex("Marks7")));
                    innerObj_Class_SandboxList.setMarks8(cursor1.getString(cursor1.getColumnIndex("Marks8")));
                    innerObj_Class_SandboxList.setMobile(cursor1.getString(cursor1.getColumnIndex("Mobile")));
                    innerObj_Class_SandboxList.setMotherName(cursor1.getString(cursor1.getColumnIndex("MotherName")));
                    innerObj_Class_SandboxList.setPaidFee(cursor1.getString(cursor1.getColumnIndex("PaidFee")));
                    innerObj_Class_SandboxList.setReceiptNo(cursor1.getString(cursor1.getColumnIndex("ReceiptNo")));
                    innerObj_Class_SandboxList.setSandboxID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SandboxID"))));
                    innerObj_Class_SandboxList.setSandboxName(cursor1.getString(cursor1.getColumnIndex("SandboxName")));
                    innerObj_Class_SandboxList.setSchoolID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SchoolID"))));
                    innerObj_Class_SandboxList.setSchoolName(cursor1.getString(cursor1.getColumnIndex("SchoolName")));
                    innerObj_Class_SandboxList.setStudentAadhar(cursor1.getString(cursor1.getColumnIndex("StudentAadhar")));
                    innerObj_Class_SandboxList.setStudentPhoto(cursor1.getString(cursor1.getColumnIndex("StudentPhoto")));
                    innerObj_Class_SandboxList.setStudentStatus(cursor1.getString(cursor1.getColumnIndex("StudentStatus")));
                    innerObj_Class_SandboxList.setStudentName(cursor1.getString(cursor1.getColumnIndex("StudentName")));
                    innerObj_Class_SandboxList.setStudentID(cursor1.getString(cursor1.getColumnIndex("StudentID")));
                    innerObj_Class_SandboxList.setTempID(cursor1.getString(cursor1.getColumnIndex("Stud_TempId")));
                    innerObj_Class_SandboxList.setLearningMode(cursor1.getString(cursor1.getColumnIndex("Learning_Mode")));

                    innerObj_Class_SandboxList.setState_ID(cursor1.getString(cursor1.getColumnIndex("stateid")));
                    innerObj_Class_SandboxList.setState_Name(cursor1.getString(cursor1.getColumnIndex("statename")));
                    innerObj_Class_SandboxList.setDistrict_ID(cursor1.getString(cursor1.getColumnIndex("districtid")));
                    innerObj_Class_SandboxList.setDistrict_Name(cursor1.getString(cursor1.getColumnIndex("districtname")));
                    innerObj_Class_SandboxList.setTaluk_ID(cursor1.getString(cursor1.getColumnIndex("talukid")));
                    innerObj_Class_SandboxList.setTaluk_Name(cursor1.getString(cursor1.getColumnIndex("talukname")));
                    innerObj_Class_SandboxList.setVillage_ID(cursor1.getString(cursor1.getColumnIndex("villageid")));
                    innerObj_Class_SandboxList.setVillage_Name(cursor1.getString(cursor1.getColumnIndex("villagename")));
                    innerObj_Class_SandboxList.setAddress(cursor1.getString(cursor1.getColumnIndex("student_address")));


                    str_fetched_learningmode=cursor1.getString(cursor1.getColumnIndex("Learning_Mode"));
                    str_fetched_edu=cursor1.getString(cursor1.getColumnIndex("Education"));

                    str_fetched_statename=cursor1.getString(cursor1.getColumnIndex("statename"));
                    str_fetched_distname=cursor1.getString(cursor1.getColumnIndex("districtname"));
                    str_fetched_talukname=cursor1.getString(cursor1.getColumnIndex("talukname"));
                    str_fetched_villagename=cursor1.getString(cursor1.getColumnIndex("villagename"));
                    str_fetched_address=cursor1.getString(cursor1.getColumnIndex("student_address"));

                    Log.e("Gender.fetching",cursor1.getString(cursor1.getColumnIndex("Gender")));
                    Log.e("str_fetched_statename",cursor1.getString(cursor1.getColumnIndex("statename")));
                    Log.e("str_fetched_distname",cursor1.getString(cursor1.getColumnIndex("districtname")));
                    Log.e("str_fetched_talukname",cursor1.getString(cursor1.getColumnIndex("talukname")));
                    Log.e("str_fetched_villagename",cursor1.getString(cursor1.getColumnIndex("villagename")));


                    class_farmponddetails_offline_obj = innerObj_Class_SandboxList;
                    i++;
                } while (cursor1.moveToNext());
            }//if ends

        }

        db1.close();

        if (x > 0) {
            if (class_farmponddetails_offline_obj != null) {
                DisplayData_Data_from_PondDetails_DB();

            } else {
                Log.e("onPostExecute", "class_farmponddetails_array_obj == null");
            }

        }


    }// end of Data_from_PondDetails_DB

    public void DisplayData_Data_from_PondDetails_DB() {
      //  if(!class_farmponddetails_offline_obj.getGender().equals("")) {
            if (class_farmponddetails_offline_obj.getGender().equals("Boy")) {
                male_edit_RB.setChecked(true);
                female_edit_RB.setChecked(false);
            } else {
                male_edit_RB.setChecked(false);
                female_edit_RB.setChecked(true);

            }
      //  }

        sandbox_edit_tv.setText(class_farmponddetails_offline_obj.getSandboxName());
        academic_edit_tv.setText(class_farmponddetails_offline_obj.getAcademicName());
        cluster_edit_tv.setText(class_farmponddetails_offline_obj.getClusterName());
        institute_edit_tv.setText(class_farmponddetails_offline_obj.getInstituteName());
        school_edit_tv.setText(class_farmponddetails_offline_obj.getSchoolName());
        level_edit_tv.setText(class_farmponddetails_offline_obj.getLevelName());
        if(!class_farmponddetails_offline_obj.getStudentName().equals("0")) {
            studentName_edit_et.setText(class_farmponddetails_offline_obj.getStudentName());
        }

        if(!class_farmponddetails_offline_obj.getBirthDate().equals("")) {
            dateofbirth_edit_tv.setText(class_farmponddetails_offline_obj.getBirthDate());
        }

        if(!class_farmponddetails_offline_obj.getFatherName().equals("0")) {
            fathername_edit_et.setText(class_farmponddetails_offline_obj.getFatherName());
        }
        if(!class_farmponddetails_offline_obj.getMotherName().equals("0")) {
            mothername_edit_et.setText(class_farmponddetails_offline_obj.getMotherName());
        }
        if(!class_farmponddetails_offline_obj.getMobile().equals("0")) {
            mobileno_edit_et.setText(class_farmponddetails_offline_obj.getMobile());

        }

        if(!class_farmponddetails_offline_obj.getStudentAadhar().equals("0")) {
            aadhaarno_edit_et.setText(class_farmponddetails_offline_obj.getStudentAadhar());

        }
        if(!class_farmponddetails_offline_obj.getMarks4().equals("0")) {
            //  marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
            Log.e("displayingmarks..",class_farmponddetails_offline_obj.getMarks4());
            marks_edit_et.setText(class_farmponddetails_offline_obj.getMarks4());
            str_marks=class_farmponddetails_offline_obj.getMarks4();
        }else if(!class_farmponddetails_offline_obj.getMarks5().equals("0")) {
            //   marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});


            marks_edit_et.setText(class_farmponddetails_offline_obj.getMarks5());
            str_marks=class_farmponddetails_offline_obj.getMarks5();
        } else if(!class_farmponddetails_offline_obj.getMarks6().equals("0")) {
            Log.e("str_marks_6 setvalues", "Entered in Marks6");

            // marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
            marks_edit_et.setText(class_farmponddetails_offline_obj.getMarks6());
            str_marks=class_farmponddetails_offline_obj.getMarks6();
        } else if(!class_farmponddetails_offline_obj.getMarks7().equals("0")) {
            // marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
            marks_edit_et.setText(class_farmponddetails_offline_obj.getMarks7());
            str_marks=class_farmponddetails_offline_obj.getMarks7();
        } else if(!class_farmponddetails_offline_obj.getMarks8().equals("0")) {
            //marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
            marks_edit_et.setText(class_farmponddetails_offline_obj.getMarks8());
            str_marks=class_farmponddetails_offline_obj.getMarks8();
        } else if(!class_farmponddetails_offline_obj.getAdmissionFee().equals("")) {
            admissionfees_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", class_farmponddetails_offline_obj.getAdmissionFee())});
            admissionfees_edit_et.setText(class_farmponddetails_offline_obj.getAdmissionFee());
            maxfees_edit_tv.setText(getResources().getString(R.string.Rs) +""+class_farmponddetails_offline_obj.getAdmissionFee());
        }
        paymentmode_edit_tv.setText("Cash");
        Log.e("getedu",class_farmponddetails_offline_obj.getEducation());

        if(class_farmponddetails_offline_obj.getEducation().equals("4th Standard")){
            education__edit_Sp.setSelection(1);
        }
        if(class_farmponddetails_offline_obj.getEducation().equals("5th Standard")){
            education__edit_Sp.setSelection(2);
        }
        if(class_farmponddetails_offline_obj.getEducation().equals("6th Standard")){
            education__edit_Sp.setSelection(3);
        }
        if(class_farmponddetails_offline_obj.getEducation().equals("7th Standard")){
            education__edit_Sp.setSelection(4);
        }
        if(class_farmponddetails_offline_obj.getEducation().equals("8th Standard")){
            education__edit_Sp.setSelection(5);
        }

//        if(!str_learningOpt.equalsIgnoreCase("")||str_learningOpt != null){
//            learnoption_Sp.setSelection(getIndex_remarks(learnoption_Sp, str_learningOpt));
//        }


        if(isInternetPresent) {
            if (!class_farmponddetails_offline_obj.getStudentPhoto().equals("")) {
                //StringToBitMap(class_farmponddetails_offline_obj.getStudentPhoto());
                 imgLoader.displayImage(class_farmponddetails_offline_obj.getStudentPhoto(), photo_edit_iv, displayoption, imageListener);

            }
        }else{
            if (!class_farmponddetails_offline_obj.getStudentPhoto().equals("")) {
                StringToBitMap(class_farmponddetails_offline_obj.getStudentPhoto());
                // imgLoader.displayImage(class_farmponddetails_offline_obj.getStudentPhoto(), photo_edit_iv, displayoption, imageListener);

            }

        }








        if(class_farmponddetails_offline_obj.getStudentStatus().equals("Admission")) {
            isAdmission=true;
            Log.e("getedu",class_farmponddetails_offline_obj.getEducation());
            if (class_farmponddetails_offline_obj.getEducation().equals("4th Standard")) {
                education__edit_Sp.setSelection(1);
            }
            if (class_farmponddetails_offline_obj.getEducation().equals("5th Standard")) {
                education__edit_Sp.setSelection(2);
            }
            if (class_farmponddetails_offline_obj.getEducation().equals("6th Standard")) {
                education__edit_Sp.setSelection(3);
            }
            if (class_farmponddetails_offline_obj.getEducation().equals("7th Standard")) {
                education__edit_Sp.setSelection(4);
            }
            if (class_farmponddetails_offline_obj.getEducation().equals("8th Standard")) {
                education__edit_Sp.setSelection(5);
            }
            if((!class_farmponddetails_offline_obj.getMarks4().equals("0")) && (!class_farmponddetails_offline_obj.getMarks4().equals(""))) {
                Log.e("displayingmarks.2..",class_farmponddetails_offline_obj.getMarks4());
                marks_edit_et.setText(class_farmponddetails_offline_obj.getMarks4());
                str_marks=class_farmponddetails_offline_obj.getMarks4();
            } else if((!class_farmponddetails_offline_obj.getMarks5().equals("0")) && (!class_farmponddetails_offline_obj.getMarks5().equals(""))) {
                marks_edit_et.setText(class_farmponddetails_offline_obj.getMarks5());
                str_marks=class_farmponddetails_offline_obj.getMarks5();
            }else if((!class_farmponddetails_offline_obj.getMarks6().equals("0")) && (!class_farmponddetails_offline_obj.getMarks6().equals(""))) {
                Log.e("str_marks_6 setvalues", "Entered in Marks6");
                marks_edit_et.setText(class_farmponddetails_offline_obj.getMarks6());
                str_marks=class_farmponddetails_offline_obj.getMarks6();
            }else if((!class_farmponddetails_offline_obj.getMarks7().equals("0")) && (!class_farmponddetails_offline_obj.getMarks7().equals(""))) {
                // marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
                marks_edit_et.setText(class_farmponddetails_offline_obj.getMarks7());
                str_marks=class_farmponddetails_offline_obj.getMarks7();
            }else if((!class_farmponddetails_offline_obj.getMarks8().equals("0")) && (!class_farmponddetails_offline_obj.getMarks8().equals(""))) {
                //marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
                marks_edit_et.setText(class_farmponddetails_offline_obj.getMarks8());
                str_marks=class_farmponddetails_offline_obj.getMarks8();
            }

            if (!str_admissionfee.equals("")) {
                admissionfees_edit_et.setText(str_admissionfee);
                maxfees_edit_tv.setText(str_admissionfee);
            }
//            if(!str_learningOpt.equalsIgnoreCase("")||str_learningOpt != null){
//                learnoption_Sp.setSelection(getIndex_remarks(learnoption_Sp, str_learningOpt));
//            }

            paymentmode_edit_tv.setText("Cash");

//            if(!str_fetched_imgfile.equals("0")){
//                imgLoader.displayImage(str_fetched_imgfile, photo_edit_iv, displayoption, imageListener);
//
//            }else{
//                photo_edit_iv.setImageResource(R.drawable.profileimg);
//            }
            if(isInternetPresent) {
                if (!class_farmponddetails_offline_obj.getStudentPhoto().equals("")) {
                    //StringToBitMap(class_farmponddetails_offline_obj.getStudentPhoto());
                    imgLoader.displayImage(class_farmponddetails_offline_obj.getStudentPhoto(), photo_edit_iv, displayoption, imageListener);

                }
            }else{
                if (!class_farmponddetails_offline_obj.getStudentPhoto().equals("")) {
                    StringToBitMap(class_farmponddetails_offline_obj.getStudentPhoto());
                    // imgLoader.displayImage(class_farmponddetails_offline_obj.getStudentPhoto(), photo_edit_iv, displayoption, imageListener);

                }

            }

        }else{
            isAdmission=false;
        }


        if(isAdmission) {
            PaymentDate_edit_ll.setVisibility(View.VISIBLE);
            PaymentMode_edit_ll.setVisibility(View.VISIBLE);
            MaxAdmissionFees_edit_ll.setVisibility(View.VISIBLE);
            admissionfees_ll.setVisibility(View.VISIBLE);
            receipt_no_edit_et.setVisibility(View.VISIBLE);
            receipt_nolabel_edit_tv.setVisibility(View.VISIBLE);
            admissionfees_edit_et.setVisibility(View.VISIBLE);
            admissionfees_edit_et.setEnabled(true);
            paymentdate_edit_tv.setVisibility(View.VISIBLE);
            paymentdate_edit_tv.setEnabled(true);

//                PaymentDate_edit_ll.setVisibility(View.GONE);
//                PaymentMode_edit_ll.setVisibility(View.GONE);
//                MaxAdmissionFees_edit_ll.setVisibility(View.GONE);
//                admissionfees_ll.setVisibility(View.GONE);
//                admissionfees_edit_et.setVisibility(View.GONE);
//                admissionfees_edit_et.setEnabled(false);
//                paymentdate_edit_tv.setVisibility(View.GONE);
//                paymentdate_edit_tv.setEnabled(false);
//                receipt_no_edit_et.setVisibility(View.GONE);
//                receipt_nolabel_edit_tv.setVisibility(View.GONE);
//////---------Commented on 12th oct 2020

            ArrayAdapter dataAdapter_status = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray_admission);
            dataAdapter_status.setDropDownViewResource(R.layout.spinnercenterstyle);
            studentstatus_edit_SP.setAdapter(dataAdapter_status);
        }else{
//                PaymentDate_edit_ll.setVisibility(View.VISIBLE);
//                PaymentMode_edit_ll.setVisibility(View.VISIBLE);
//                MaxAdmissionFees_edit_ll.setVisibility(View.VISIBLE);
//                admissionfees_ll.setVisibility(View.VISIBLE);
//                receipt_no_edit_et.setVisibility(View.VISIBLE);
//                receipt_nolabel_edit_tv.setVisibility(View.VISIBLE);
//                admissionfees_edit_et.setVisibility(View.VISIBLE);
//                admissionfees_edit_et.setEnabled(true);
//                paymentdate_edit_tv.setVisibility(View.VISIBLE);
//                paymentdate_edit_tv.setEnabled(true);


            PaymentDate_edit_ll.setVisibility(View.GONE);
            PaymentMode_edit_ll.setVisibility(View.GONE);
            MaxAdmissionFees_edit_ll.setVisibility(View.GONE);
            admissionfees_ll.setVisibility(View.GONE);
            admissionfees_edit_et.setVisibility(View.GONE);
            admissionfees_edit_et.setEnabled(false);
            paymentdate_edit_tv.setVisibility(View.GONE);
            paymentdate_edit_tv.setEnabled(false);
            receipt_no_edit_et.setVisibility(View.GONE);
            receipt_nolabel_edit_tv.setVisibility(View.GONE);


            ArrayAdapter dataAdapter_status = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray);
            dataAdapter_status.setDropDownViewResource(R.layout.spinnercenterstyle);
            studentstatus_edit_SP.setAdapter(dataAdapter_status);

        }


    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            photo_edit_iv.setImageBitmap(bitmap);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
    public void update_editedDetails_PondDetails_DB() {


        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);

        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR);");

        String str_stumarks4="",str_stumarks5="",str_stumarks6="",str_stumarks7="",str_stumarks8="";
        int_val_sandboxID = class_farmponddetails_offline_obj.getSandboxID();
        int_val_academicid = class_farmponddetails_offline_obj.getAcademicID();
        int_val_clusterid = class_farmponddetails_offline_obj.getClusterID();
        int_val_instituteid = class_farmponddetails_offline_obj.getInstituteID();
        int_val_schoolid = class_farmponddetails_offline_obj.getSchoolID();
        int_val_levelid = class_farmponddetails_offline_obj.getLevelID();
//System.currentTimeMillis()
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String createddate = df.format(c);
      //  String createdby = str_stuID;


        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat dateformat_ddmmyyyy = new SimpleDateFormat("ddMMyyyy");
        String str_ddmmyyyy = dateformat_ddmmyyyy.format(c1.getTime());
        Log.e("date", str_ddmmyyyy);
        String str_TemporaryID = "edittemp" + String.valueOf(System.currentTimeMillis())+str_ddmmyyyy;
        Log.e("str_TemporaryID",str_TemporaryID);

    //    String str_TemporaryID="edittemp"+""+System.currentTimeMillis();
    //    String str_TemporaryID="edittemp";
        Log.e("str_TemporaryID",str_TemporaryID);

        String str_stuname =studentName_edit_et.getText().toString();
        String str_stufathername = fathername_edit_et.getText().toString();
        String  str_stumothername = mothername_edit_et.getText().toString();
        String  str_mobno= mobileno_edit_et.getText().toString();
        String  stu_aadharno= aadhaarno_edit_et.getText().toString();
        String str_dob=dateofbirth_edit_tv.getText().toString();
        String str_gen_new=str_gender;
//        String  str_edu=class_farmponddetails_offline_obj.getEducation();
        String  str_edu=selected_edu;

        String  str_stustatus=class_farmponddetails_offline_obj.getStudentStatus();
//        String  str_stumarks4=class_farmponddetails_offline_obj.getMarks4();
        if (selected_edu.equals("4th Standard")) {
           // education__edit_Sp.setSelection(1);
        }
        if (selected_edu.equals("5th Standard")) {
              str_stumarks4=marks_edit_et.getText().toString();
        }
        if (selected_edu.equals("6th Standard")) {
              str_stumarks5=marks_edit_et.getText().toString();
        }
        if (selected_edu.equals("7th Standard")) {
              str_stumarks6=marks_edit_et.getText().toString();
        }
        if (selected_edu.equals("8th Standard")) {
              str_stumarks7=marks_edit_et.getText().toString();
        }
        if (selected_edu.equals("9th Standard")) {
              str_stumarks8=marks_edit_et.getText().toString();
        }

        //  String  str_stumarks5=class_farmponddetails_offline_obj.getMarks5();
       // String  str_stumarks6=class_farmponddetails_offline_obj.getMarks6();
      //  String  str_stumarks7=class_farmponddetails_offline_obj.getMarks7();
      //  String  str_stumarks8=class_farmponddetails_offline_obj.getMarks8();
        String  str_admissionfee=class_farmponddetails_offline_obj.getAdmissionFee();
        String  str_paymentdate=paymentdate_edit_tv.getText().toString();
        String  str_receiptno=class_farmponddetails_offline_obj.getReceiptNo();
       // String  str_stuphoto=class_farmponddetails_offline_obj.getStudentPhoto();
        String  str_stuApplno=class_farmponddetails_offline_obj.getApplicationNo();

            try {
            Log.e("StudentID", String.valueOf(class_farmponddetails_offline_obj.getStudentID()));
                Log.e("str_stufathername", str_stufathername);

                ContentValues cv = new ContentValues();
            cv.put("SandboxID", int_val_sandboxID);
            cv.put("AcademicID", int_val_academicid);//int_val_academicid
            cv.put("ClusterID", int_val_clusterid);
            cv.put("InstituteID", int_val_instituteid);
            cv.put("SchoolID", int_val_schoolid);
            cv.put("LevelID", int_val_levelid);
            cv.put("Stud_TempId", str_TemporaryID);
            cv.put("StudentName", str_stuname);
            cv.put("FatherName", str_stufathername);
            cv.put("MotherName", str_stumothername);
           // Log.e("FatherName", str_stufathername);
            //Log.e("updateLongitude", str_stumothername);
            cv.put("Mobile", str_mobno);//pond_startdate
            cv.put("StudentAadhar", stu_aadharno);
            cv.put("BirthDate", str_edit_birthdate);
            cv.put("Gender", str_gen_new);//pond_enddate
                Log.e("str_gen_new", str_gen_new);
            cv.put("Education", str_edu);
            cv.put("StudentStatus", str_stustatus);
            cv.put("Marks4", str_stumarks4);
                Log.e("str_stumarks4..updating", str_stumarks4);
            cv.put("Marks5", str_stumarks5);
            cv.put("Marks6", str_stumarks6);


                cv.put("Marks7", str_stumarks7);
            cv.put("Marks8",str_stumarks8);
            cv.put("AdmissionFee", str_admissionfee);
            ///cv.put("Reading_End", str_paymentdate);
            cv.put("ReceiptNo", str_receiptno);
            cv.put("StudentPhoto",str_imgfile);//str_stuphoto
            cv.put("Base64image",str_imgfile);
                Log.e("str_imgfile..updating", str_imgfile);
            cv.put("ApplicationNo",str_stuApplno);
            cv.put("StudentID",str_stuID);//str_TemporaryID addonly wen new student is added
            cv.put("CreatedDate",createddate);
            cv.put("UpadateOff_Online", "offlineedit");
            cv.put("Learning_Mode",selected_learnOption);//str_TemporaryID addonly wen new student is added

                cv.put("stateid",class_farmponddetails_offline_obj.getState_ID());
                cv.put("statename",class_farmponddetails_offline_obj.getState_Name());
                cv.put("districtid",class_farmponddetails_offline_obj.getDistrict_ID());
                cv.put("districtname",class_farmponddetails_offline_obj.getDistrict_Name());
                cv.put("talukid",class_farmponddetails_offline_obj.getTaluk_ID());
                cv.put("talukname",class_farmponddetails_offline_obj.getTaluk_Name());
                cv.put("villageid",class_farmponddetails_offline_obj.getVillage_ID());
                cv.put("villagename",class_farmponddetails_offline_obj.getVillage_Name());
                cv.put("student_address",class_farmponddetails_offline_obj.getAddress());


            db1.update("StudentDetailsRest", cv, "StudentID = ?", new String[]{String.valueOf(str_stuID)});
            db1.close();



            internetDectector = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent)
            {
                fetch_DB_farmerprofile_offline_data(str_stuID);

                //fetch_DB_edited_offline_data();


            } else {
                Toast.makeText(getApplicationContext(), "Edition Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Activity_EditRegistration.this, Activity_ViewStudentList_new.class);
                startActivity(intent);
                finish();
            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "error" + e.toString(), Toast.LENGTH_SHORT).show();
        }


    }
    public void fetch_DB_farmerprofile_offline_data(int stuid)
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR);");
//        Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE Stud_TempId LIKE'"+ "edittemp%" + "'", null);
       // Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE StudentID='"+ stuid  + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE UpadateOff_Online='"+ "offlineedit"  + "'", null);

        int x = cursor1.getCount();
        Log.e("addnew_studentcount", String.valueOf(x));


        int i = 0;
        class_farmerprofileoffline_array_obj = new StudentList[x];
        if (x > 0) {
            if (cursor1.moveToFirst()) {

                do {
                    StudentList innerObj_Class_SandboxList = new StudentList();
                    innerObj_Class_SandboxList.setAcademicID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("AcademicID"))));
                    innerObj_Class_SandboxList.setAcademicName(cursor1.getString(cursor1.getColumnIndex("AcademicName")));
                    innerObj_Class_SandboxList.setAdmissionFee(cursor1.getString(cursor1.getColumnIndex("AdmissionFee")));
                    innerObj_Class_SandboxList.setApplicationNo(cursor1.getString(cursor1.getColumnIndex("ApplicationNo")));
                    innerObj_Class_SandboxList.setBalanceFee(cursor1.getString(cursor1.getColumnIndex("BalanceFee")));
                    innerObj_Class_SandboxList.setBirthDate(cursor1.getString(cursor1.getColumnIndex("BirthDate")));
                    innerObj_Class_SandboxList.setClusterID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("ClusterID"))));//DispFarmerTable_Farmer_Code VARCHAR
                    innerObj_Class_SandboxList.setClusterName(cursor1.getString(cursor1.getColumnIndex("ClusterName")));//DispFarmerTable_Farmer_Code VARCHAR
                    innerObj_Class_SandboxList.setCreatedDate(cursor1.getString(cursor1.getColumnIndex("CreatedDate")));
                    innerObj_Class_SandboxList.setEducation(cursor1.getString(cursor1.getColumnIndex("Education")));
                    innerObj_Class_SandboxList.setFatherName(cursor1.getString(cursor1.getColumnIndex("FatherName")));
                    innerObj_Class_SandboxList.setGender(cursor1.getString(cursor1.getColumnIndex("Gender")));
                    innerObj_Class_SandboxList.setInstituteName(cursor1.getString(cursor1.getColumnIndex("InstituteName")));
                    innerObj_Class_SandboxList.setInstituteID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("InstituteID"))));
                    innerObj_Class_SandboxList.setLevelID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("LevelID"))));
                    innerObj_Class_SandboxList.setLevelName(cursor1.getString(cursor1.getColumnIndex("LevelName")));
                    innerObj_Class_SandboxList.setMarks4(cursor1.getString(cursor1.getColumnIndex("Marks4")));
                    innerObj_Class_SandboxList.setMarks5(cursor1.getString(cursor1.getColumnIndex("Marks5")));
                    innerObj_Class_SandboxList.setMarks6(cursor1.getString(cursor1.getColumnIndex("Marks6")));
                    innerObj_Class_SandboxList.setMarks7(cursor1.getString(cursor1.getColumnIndex("Marks7")));
                    innerObj_Class_SandboxList.setMarks8(cursor1.getString(cursor1.getColumnIndex("Marks8")));
                    innerObj_Class_SandboxList.setMobile(cursor1.getString(cursor1.getColumnIndex("Mobile")));
                    innerObj_Class_SandboxList.setMotherName(cursor1.getString(cursor1.getColumnIndex("MotherName")));
                    innerObj_Class_SandboxList.setPaidFee(cursor1.getString(cursor1.getColumnIndex("PaidFee")));
                    innerObj_Class_SandboxList.setReceiptNo(cursor1.getString(cursor1.getColumnIndex("ReceiptNo")));
                    innerObj_Class_SandboxList.setSandboxID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SandboxID"))));
                    innerObj_Class_SandboxList.setSandboxName(cursor1.getString(cursor1.getColumnIndex("SandboxName")));
                    innerObj_Class_SandboxList.setSchoolID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SchoolID"))));
                    innerObj_Class_SandboxList.setSchoolName(cursor1.getString(cursor1.getColumnIndex("SchoolName")));
                    innerObj_Class_SandboxList.setStudentAadhar(cursor1.getString(cursor1.getColumnIndex("StudentAadhar")));
                    innerObj_Class_SandboxList.setStudentPhoto(cursor1.getString(cursor1.getColumnIndex("StudentPhoto")));
                    innerObj_Class_SandboxList.setStudentStatus(cursor1.getString(cursor1.getColumnIndex("StudentStatus")));
                    innerObj_Class_SandboxList.setStudentName(cursor1.getString(cursor1.getColumnIndex("StudentName")));
                    innerObj_Class_SandboxList.setStudentID(cursor1.getString(cursor1.getColumnIndex("StudentID")));
                    innerObj_Class_SandboxList.setTempID(cursor1.getString(cursor1.getColumnIndex("Stud_TempId")));
                    innerObj_Class_SandboxList.setLearningMode(cursor1.getString(cursor1.getColumnIndex("Learning_Mode")));

                    innerObj_Class_SandboxList.setState_ID(cursor1.getString(cursor1.getColumnIndex("stateid")));
                    innerObj_Class_SandboxList.setState_Name(cursor1.getString(cursor1.getColumnIndex("statename")));
                    innerObj_Class_SandboxList.setDistrict_ID(cursor1.getString(cursor1.getColumnIndex("districtid")));
                    innerObj_Class_SandboxList.setDistrict_Name(cursor1.getString(cursor1.getColumnIndex("districtname")));
                    innerObj_Class_SandboxList.setTaluk_ID(cursor1.getString(cursor1.getColumnIndex("talukid")));
                    innerObj_Class_SandboxList.setTaluk_Name(cursor1.getString(cursor1.getColumnIndex("talukname")));
                    innerObj_Class_SandboxList.setVillage_ID(cursor1.getString(cursor1.getColumnIndex("villageid")));
                    innerObj_Class_SandboxList.setVillage_Name(cursor1.getString(cursor1.getColumnIndex("villagename")));
                    innerObj_Class_SandboxList.setAddress(cursor1.getString(cursor1.getColumnIndex("student_address")));


                    class_farmerprofileoffline_array_obj[i] = innerObj_Class_SandboxList;
                    Log.e("fetch_DB_offline_data",cursor1.getString(cursor1.getColumnIndex("StudentName")));
                    Log.e("fetch_DB_offline_data",cursor1.getString(cursor1.getColumnIndex("StudentID")));
                    i++;
                } while (cursor1.moveToNext());
            }//if ends

        }


        db1.close();

        Log.e("length", String.valueOf(class_farmerprofileoffline_array_obj.length));

        //int_newfarmercount=0;
        for (int j = 0; j < class_farmerprofileoffline_array_obj.length; j++) {
            AddFarmerDetails(j);
        }
//        if (x == 0) {
//            fetch_DB_newfarmpond_offline_data();
//        }

    }
    public void AddFarmerDetails(int j) {
        Interface_userservice userService1;
        userService1 = Class_ApiUtils.getUserService();
        String StudentID= String.valueOf(class_farmerprofileoffline_array_obj[j].getStudentID());
        Log.e("cLASS","StudentID..ABC "+class_farmerprofileoffline_array_obj[j].getStudentID());

        Log.e("tag","StudentID=="+StudentID);
        if(StudentID.startsWith("edittemp")){
            Log.e("tag","StudentID temp=="+StudentID);
            StudentID="0";
        }

        AddStudentDetailsRequest request = new AddStudentDetailsRequest();
        request.setAcademicID(String.valueOf(class_farmerprofileoffline_array_obj[j].getAcademicID()));
        request.setSandboxID(String.valueOf(class_farmerprofileoffline_array_obj[j].getSandboxID()));
        request.setClusterID(String.valueOf(class_farmerprofileoffline_array_obj[j].getClusterID()));
        request.setInstituteID(String.valueOf(class_farmerprofileoffline_array_obj[j].getInstituteID()));
        request.setLevelID(String.valueOf(class_farmerprofileoffline_array_obj[j].getLevelID()));
        request.setStudentName(class_farmerprofileoffline_array_obj[j].getStudentName());
        request.setFatherName(class_farmerprofileoffline_array_obj[j].getFatherName());
        request.setMotherName(class_farmerprofileoffline_array_obj[j].getMotherName());
        request.setBirthDate(class_farmerprofileoffline_array_obj[j].getBirthDate());
       // request.setBirthDate("2005-01-22");
        request.setStudentAadhar(class_farmerprofileoffline_array_obj[j].getStudentAadhar());
        request.setMobile(class_farmerprofileoffline_array_obj[j].getMobile());
        request.setGender(class_farmerprofileoffline_array_obj[j].getGender());
        request.setEducation(class_farmerprofileoffline_array_obj[j].getEducation());


        request.setState_ID(class_farmerprofileoffline_array_obj[j].getState_ID());
       // request.setState_Name(class_farmerprofileoffline_array_obj[j].getState_Name());
        request.setDistrict_ID(class_farmerprofileoffline_array_obj[j].getDistrict_ID());
      //  request.setDistrict_Name(class_farmerprofileoffline_array_obj[j].getDistrict_Name());
        request.setTaluk_ID(class_farmerprofileoffline_array_obj[j].getTaluk_ID());
       // request.setTaluk_Name(class_farmerprofileoffline_array_obj[j].getTaluk_Name());
        request.setVillage_ID(class_farmerprofileoffline_array_obj[j].getVillage_ID());
       // request.setVillage_Name(class_farmerprofileoffline_array_obj[j].getVillage_Name());
        request.setAddress(class_farmerprofileoffline_array_obj[j].getAddress());

        if(!class_farmerprofileoffline_array_obj[j].getMarks4().equals("")){
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks4());

        }else if(!class_farmerprofileoffline_array_obj[j].getMarks5().equals("")){
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks5());

        }else if(!class_farmerprofileoffline_array_obj[j].getMarks6().equals("")){
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks6());

        }else if(!class_farmerprofileoffline_array_obj[j].getMarks7().equals("")){
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks7());

        }else if(!class_farmerprofileoffline_array_obj[j].getMarks8().equals("")){
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks8());

        }
        request.setAdmissionFee(class_farmerprofileoffline_array_obj[j].getAdmissionFee());
        request.setStudentStatus(class_farmerprofileoffline_array_obj[j].getStudentStatus());
        request.setStudentID(class_farmerprofileoffline_array_obj[j].getStudentID());
        request.setSchoolID(String.valueOf(class_farmerprofileoffline_array_obj[j].getSchoolID()));
        request.setCreatedBy(String.valueOf(str_stuID));
        request.setCreatedDate(class_farmerprofileoffline_array_obj[j].getCreatedDate());
     //   request.setCreatedDate("2020-12-05");
        request.setReceiptManual(class_farmerprofileoffline_array_obj[j].getReceiptNo());
    //    request.setLearningMode(class_farmerprofileoffline_array_obj[j].g());
       request.setTemp_ID(class_farmerprofileoffline_array_obj[j].getTempID());
        request.setFileName(class_farmerprofileoffline_array_obj[j].getStudentPhoto());
        request.setLearningMode(class_farmerprofileoffline_array_obj[j].getLearningMode());
        //        Log.e("tag", "FarmerFirstName==" + class_farmerprofileoffline_array_obj[j].getStr_fname());
//        Log.e("tag", "FarmerID==" + class_farmerprofileoffline_array_obj[j].getStr_farmerID());

        Call<AddStudentDetailsResponse> call = userService1.Post_ActionStudent(request);

        Log.e("TAG", "Request 33: " + new Gson().toJson(request));
        Log.e("TAG", "Request: " + request.toString());

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_EditRegistration.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<AddStudentDetailsResponse>() {
            @Override
            public void onResponse(Call<AddStudentDetailsResponse> call, Response<AddStudentDetailsResponse> response) {
               // System.out.println("response" + response.body().toString());
            //    Log.e("tag", "response =" + response.body().toString());


                if (response.isSuccessful()) {
                    List<Class_Response_AddStudentDetailsList> addFarmerResList = response.body().getLst();
                    for (int i = 0; i < addFarmerResList.size(); i++) {


                    Log.e("tag", "addstudentresponse ID=" + addFarmerResList.get(i).getStudentID());


                    String str_response_student_id = String.valueOf(addFarmerResList.get(i).getStudentID());
                    String str_response_tempId = String.valueOf(addFarmerResList.get(i).getTemp_ID());
//                    Log.e("tag", "getMobileTempID=" + addFarmerResList.getMobileTempID());
//                    Log.e("tag", "getFarmerCode=" + addFarmerResList.getFarmerCode());
//                    Log.e("tag", "getFarmerID=" + addFarmerResList.getFarmerID());

                    SQLiteDatabase db1 = getApplication().openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);

                    db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR);");


                    ContentValues cv = new ContentValues();
                    cv.put("StudentID", str_response_student_id);
                    cv.put("Stud_TempId", str_response_tempId);
                    cv.put("UpadateOff_Online", "onlineedit");

                        //   cv.put("UploadedStatusFarmerprofile", 10);

                    db1.update("StudentDetailsRest", cv, "Stud_TempId = ?", new String[]{str_response_tempId});
                    db1.close();

             //       SQLiteDatabase db_viewfarmerlist = getApplication().openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);


//                    db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
//                            "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
//                            "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
//                            "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
//                            "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
//                            "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
//                            "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR,Farmer_Gender VARCHAR);");
//
//
//                    ContentValues cv_farmelistupdate = new ContentValues();
//                    cv_farmelistupdate.put("DispFarmerTable_Farmer_Code", str_response_farmercode);
//                    cv_farmelistupdate.put("DispFarmerTable_FarmerID", str_response_farmer_id);
//
//
//                    db_viewfarmerlist.update("ViewFarmerListRest", cv_farmelistupdate, "DispFarmerTable_Farmer_Code = ?", new String[]{str_response_tempId});
//                    db_viewfarmerlist.close();

                        Toast.makeText(getApplicationContext(), "Edition Updated Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Activity_EditRegistration.this, Activity_ViewStudentList_new.class);
                        startActivity(intent);
                        finish();

                        progressDoalog.dismiss();

//                    if(int_newfarmercount>0){int_newfarmercount++;}
//                    else{ int_newfarmercount++; }

//                    if(int_newfarmercount==class_farmerprofileoffline_array_obj.length)
//                    {
//                        fetch_DB_newfarmpond_offline_data();
//                    }
                }

                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMsg());

                    Toast.makeText(Activity_EditRegistration.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(Activity_EditRegistration.this, "WS:Error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }


    public void setcurrentdate() {
        //// Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
       // str_paymentDate = mDay + "-" + (mMonth + 1) + "-" + mYear;
        str_paymentDate = mYear + "-" + (mMonth + 1) + "-" + mDay;
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        str_paymentDatedisp=dateFormat.format(c.getTime());
       // paymentdate_edit_tv.setText(dateFormat.format(c.getTime()));
        SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
        str_paymentDate = mdyFormat.format(c.getTime());

        paymentdate_edit_tv.setText(str_paymentDate);
    }
    public void setPaymentDate ( final Calendar calendar){
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

        paymentdate_edit_tv.setText(dateFormat.format(calendar.getTime()));
        str_paymentDate = dateFormat.format(calendar.getTime());


        SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
        str_paymentDate = mdyFormat.format(calendar.getTime());
        Calendar c = Calendar.getInstance();
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");


    }



    public void setBirthDate (Calendar calendar){
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

        SimpleDateFormat mdyFormat1 = new SimpleDateFormat("dd-MM-yyyy");
        dateofbirth_edit_tv.setText(mdyFormat1.format(calendar.getTime()));
        str_edit_birthdate = dateFormat.format(calendar.getTime());


        SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
        str_edit_birthdate = mdyFormat.format(calendar.getTime());
        Log.e("str_edit_birthdate",str_edit_birthdate);
        Calendar c = Calendar.getInstance();
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");



    }

    public static void initImageLoader(Context context) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.transparent)
                .showImageOnFail(R.drawable.transparent)
                .resetViewBeforeLoading().cacheOnDisc()
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .defaultDisplayImageOptions(options)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();

        ImageLoader.getInstance().init(config);
    }

    public static class ImageDisplayListener extends
            SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);

                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 800);
                    displayedImages.add(imageUri);
                }
            }
        }




    }


////////////////////////////////////////////////////////////////////////////////
    //take photo

    public void getpic() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_EditRegistration.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    Intent intentPhotoCapture = new Intent(Activity_EditRegistration.this, CameraPhotoCapture.class);
//                    SharedPreferences myprefs_flag_camera = Activity_EditRegistration.this.getSharedPreferences("cameraflag", MODE_PRIVATE);
//                    myprefs_flag_camera.edit().putString("flag_camera", "1").apply();


                    SharedPreferences.Editor  myprefs_camera= sharedpref_camera_Obj.edit();
                    myprefs_camera.putString(key_flagcamera, "1");
                    myprefs_camera.apply();

                    startActivity(intentPhotoCapture);
                    Toast.makeText(getApplicationContext(), "choosen  take photo ",
                            Toast.LENGTH_SHORT).show();
                    digitalcamerabuttonpressed_new=true;


                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {


                Log.d("CameraDemo", "Pic saved");
                Uri imageUri = Uri.parse(mCurrentPhotoPath);
                if (imageUri.getPath() != null) {
                    File file = new File(imageUri.getPath());

                    try {
                        InputStream ims = new FileInputStream(file);
                        bitmap = BitmapFactory.decodeStream(ims);
                        rotatemethod(file.toString());
                        BitMapToString(bitmap);

                        photo_edit_iv.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        return;
                    }


                } else if (resultCode == RESULT_CANCELED) {

                    Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();

                }
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = null;
                if (selectedImage != null) {
                    c = getContentResolver().query(selectedImage, filePath, null, null, null);
                }else{
                    Log.e("selectedImage=null","selectedImage=null");
                }
                if (c != null) {
                    c.moveToFirst();
                }
                int columnIndex = 0;
                if (c != null) {
                    columnIndex = c.getColumnIndex(filePath[0]);
                }
                String picturePath = null;
                if (c != null) {
                    picturePath = c.getString(columnIndex);
                }
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail = getResizedBitmap(thumbnail, 400);
                Log.w(" gallery.", picturePath + "");
                photo_edit_iv.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);
            }
        }
//////////////////////////////////////////////


    }

    public void rotatemethod(String path) {
        //       check the rotation of the image and display it properly
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                //matrix.postRotate(90);
                matrix.setRotate(90);
                Log.d("EXIF", "Exif90: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix,
                    true);

            photo_edit_iv.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        str_imgfile = Base64.encodeToString(b, Base64.DEFAULT);
        Log.e("str_imgfile.." , str_imgfile);

        return str_imgfile;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    ////////////////////////////////////////////////////////////////////////////
    //dob CLASS
    public static class DatePickerFragmentDateOfBirth extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                    this, year, month, day);
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());


            return dialog;


        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
            setDate(cal);

        }

        public void setDate(final Calendar calendar) {
            final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);


           // dateofbirth_edit_tv.setText(dateFormat.format(calendar.getTime()));
            str_edit_birthdatedisp = dateFormat.format(calendar.getTime());
            Log.e("str_birthdatedisp..." ,dateFormat.format(calendar.getTime()));


            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
            str_edit_birthdate = mdyFormat.format(calendar.getTime());
            SimpleDateFormat mdyFormat1 = new SimpleDateFormat("dd-MM-yyyy");
            String str_edit_birthdate1 = mdyFormat1.format(calendar.getTime());
            dateofbirth_edit_tv.setText(str_edit_birthdate1);
            Log.e("str_edit_birthdate.." ,str_edit_birthdate);


        }

    }
/////////////////////////////////////////////////////////////////////
    //payment date
public static class DatePickerFragmentPaymentDate extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                this, year, month, day);

        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return dialog;


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        setDate(cal);

    }

    public void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

        //paymentdate_edit_tv.setText(dateFormat.format(calendar.getTime()));
        str_paymentDatedisp = dateFormat.format(calendar.getTime());
        Log.e("paymentdate_tv..." ,dateFormat.format(calendar.getTime()));


        SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
        str_paymentDate = mdyFormat.format(calendar.getTime());
        Log.e("str_paymentDate.." , str_paymentDate);
        paymentdate_edit_tv.setText(str_paymentDate);

    }

}
/////////////////////////////////////////////////////////




    public void GetStudentRecord() {
        if (isInternetPresent) {
            GetStudentRecordTask task = new GetStudentRecordTask(Activity_EditRegistration.this);
            task.execute();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }

    }

    private class AsyncCallWS_learningMode extends AsyncTask<String, Void, Void> {
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
            dialog = new ProgressDialog(context1,R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected void onPostExecute(Void result) {

            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();
                dataAdapter_learnop = new ArrayAdapter(Activity_EditRegistration.this, R.layout.spinnercenterstyle, Arrayclass_learningOption);
                // Drop down layout style - list view with radio button
                dataAdapter_learnop.setDropDownViewResource(R.layout.spinnercenterstyle);
                // attaching data adapter to spinner
                learnoption_Sp.setAdapter(dataAdapter_learnop);
/*                dataAdapter_learnop = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, Arrayclass_learningOption);
                dataAdapter_learnop.setDropDownViewResource(R.layout.spinnercenterstyle);
                learnoption_Sp.setAdapter(dataAdapter_learnop);*/
                GetStudentRecord();
            }
        }//end of onPostExecute
    }// end Async task

    public void list_detaile() {

        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "Learning_Mode_Options";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/Learning_Mode_Options";

        try {
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

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

    private class GetStudentRecordTask extends AsyncTask<String, Void, Void> {
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

            getstudentdata();  // call of details
            return null;
        }

        public GetStudentRecordTask(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1,R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected void onPostExecute(Void result) {

            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();

            }
            if (selected_studentstatus.equals("Error")) {
                Toast.makeText(Activity_EditRegistration.this, "No Data Found", Toast.LENGTH_LONG).show();
                alert();

            } else {
                SetValues();

            }

            Log.e("tag", "Reached the onPostExecute");

        }//end of onPostExecute
    }// end Async task


    public void getstudentdata() {
        Vector<SoapObject> result1 = null;

        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "LoadStudentRecord";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/LoadStudentRecord";

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("Student_ID", "97");

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
                SoapObject obj2 = (SoapObject) response.getProperty(0);
                Log.e("obj2", obj2.toString());

                SoapPrimitive receive_studentstatus = (SoapPrimitive) obj2.getProperty("Student_Status");
                selected_studentstatus = receive_studentstatus.toString();
                Log.e("str_application_status", receive_studentstatus.toString());

                if (selected_studentstatus.equals("Admission")) {
                    isAdmission=true;

                    SoapPrimitive receive_studentid = (SoapPrimitive) obj2.getProperty("Student_ID");
                   // str_stuID = receive_studentid.toString();
                   // Log.e("str_stuID", str_stuID);
                    SoapPrimitive receive_sandboxID = (SoapPrimitive) obj2.getProperty("Sandbox_ID");
                    str_sandboxID = receive_sandboxID.toString();
                    Log.e("str_sandboxID", str_sandboxID);
                    SoapPrimitive receive_sandbox = (SoapPrimitive) obj2.getProperty("Sandbox_Name");
                    str_sandbox = receive_sandbox.toString();
                    Log.e("str_sandbox", str_sandbox);
                    SoapPrimitive receive_academicID = (SoapPrimitive) obj2.getProperty("Academic_ID");
                    str_academicID = receive_academicID.toString();
                    Log.e("str_academicID", str_academicID);
                    SoapPrimitive receive_academic = (SoapPrimitive) obj2.getProperty("Academic_Name");
                    str_academic = receive_academic.toString();
                    Log.e("str_academic", str_academic);
                    SoapPrimitive receive_clusterID = (SoapPrimitive) obj2.getProperty("Cluster_ID");
                    str_clusterID = receive_clusterID.toString();
                    Log.e("str_clusterID", str_clusterID);
                    SoapPrimitive receive_cluster = (SoapPrimitive) obj2.getProperty("Cluster_Name");
                    str_cluster = receive_cluster.toString();
                    Log.e("str_cluster", str_cluster);
                    SoapPrimitive receive_instituteID = (SoapPrimitive) obj2.getProperty("Institute_ID");
                    str_instID = receive_instituteID.toString();
                    Log.e("str_instID", str_instID);
                    SoapPrimitive receive_institute = (SoapPrimitive) obj2.getProperty("Institute_Name");
                    str_inst = receive_institute.toString();
                    Log.e("str_inst", str_inst);
                    SoapPrimitive receive_schoolID = (SoapPrimitive) obj2.getProperty("School_ID");
                    str_schoolID = receive_schoolID.toString();
                    Log.e("str_schoolID", str_schoolID);
                    SoapPrimitive receive_school = (SoapPrimitive) obj2.getProperty("School_Name");
                    str_school = receive_school.toString();
                    Log.e("str_school", str_school);
                    SoapPrimitive receive_levelID = (SoapPrimitive) obj2.getProperty("Level_ID");
                    str_levelID = receive_levelID.toString();
                    Log.e("str_levelID", str_levelID);
                    SoapPrimitive receive_level = (SoapPrimitive) obj2.getProperty("Level_Name");
                    str_level = receive_level.toString();
                    Log.e("str_level", str_level);
                    SoapPrimitive receive_studentname = (SoapPrimitive) obj2.getProperty("Student_Name");
                    str_studentname = receive_studentname.toString();
                    Log.e("str_studentname", str_studentname);
                    SoapPrimitive soap_gender = (SoapPrimitive) obj2.getProperty("Gender");
                    str_gender = soap_gender.toString();
                    Log.e("str_gender", str_gender);
                    SoapPrimitive soap_birthdate = (SoapPrimitive) obj2.getProperty("Birth_Date");
                    str_edit_birthdate = soap_birthdate.toString();
                    Log.e("str_edit_birthdate", str_edit_birthdate);
                    SoapPrimitive soap_edu = (SoapPrimitive) obj2.getProperty("Education");
                    selected_edu = soap_edu.toString();
                    Log.e("selected_edu", selected_edu);
                    SoapPrimitive soap_mobno= (SoapPrimitive) obj2.getProperty("Mobile");
                    str_mobileno = soap_mobno.toString();
                    Log.e("str_mobileno", str_mobileno);
                    SoapPrimitive soap_fname = (SoapPrimitive) obj2.getProperty("Father_Name");
                    str_fathername = soap_fname.toString();
                    Log.e("str_fathername", str_fathername);
                    SoapPrimitive soap_mothername = (SoapPrimitive) obj2.getProperty("Mother_Name");
                    str_mothername = soap_mothername.toString();
                Log.e("str_mothername", str_mothername);
                    SoapPrimitive soap_aadhar = (SoapPrimitive) obj2.getProperty("Student_Aadhar");
                    str_aadar = soap_aadhar.toString();
                    Log.e("str_aadar", str_aadar);
                    SoapPrimitive soap_createddate = (SoapPrimitive) obj2.getProperty("Created_Date");
                    str_created_date = soap_createddate.toString();
                    Log.e("str_created_date", str_created_date);
                    SoapPrimitive soap_createdby = (SoapPrimitive) obj2.getProperty("Created_By");
                    str_created_by = soap_createdby.toString();
                    Log.e("str_created_by", str_created_by);
                    SoapPrimitive soap_learningOpt = (SoapPrimitive) obj2.getProperty("Learning_Mode");
                    str_learningOpt = soap_learningOpt.toString().trim();
                    Log.e("str_learningOpt=", str_learningOpt);
                    SoapPrimitive soap_imagefile = (SoapPrimitive) obj2.getProperty("Student_Photo");
                    str_fetched_imgfile = soap_imagefile.toString();
                   // str_fetched_imgfile="";
                    Log.e("str_fetched_imgfile", str_fetched_imgfile);

                   // imgLoader.displayImage(str_imgfile, photo_edit_iv, displayoption, imageListener);

                    SoapPrimitive receive_applno = (SoapPrimitive) obj2.getProperty("Application_No");
                    str_applNo = receive_applno.toString();
                    SoapPrimitive soap_marks4 = (SoapPrimitive) obj2.getProperty("Marks_4");
                    str_marks_4 = soap_marks4.toString();
                    SoapPrimitive soap_marks5 = (SoapPrimitive) obj2.getProperty("Marks_5");
                    str_marks_5 = soap_marks5.toString();
                    SoapPrimitive soap_marks6 = (SoapPrimitive) obj2.getProperty("Marks_6");
                    str_marks_6 = soap_marks6.toString();
                    Log.e("str_marks_6", str_marks_6);

                    SoapPrimitive soap_marks7 = (SoapPrimitive) obj2.getProperty("Marks_7");
                    str_marks_7 = soap_marks7.toString();
                    SoapPrimitive soap_marks8 = (SoapPrimitive) obj2.getProperty("Marks_8");
                    str_marks_8 = soap_marks8.toString();

                    SoapPrimitive soap_admissionfee = (SoapPrimitive) obj2.getProperty("Application_Fee");
                    str_admissionfee = soap_admissionfee.toString();
                    Log.e("str_admissionfee", str_admissionfee);

                }else if (selected_studentstatus.equals("Applicant")){
                    isAdmission=false;
                    SoapPrimitive receive_studentid = (SoapPrimitive) obj2.getProperty("Student_ID");
                  //  str_stuID = receive_studentid.toString();
               // Log.e("str_stuID", str_stuID);
                    SoapPrimitive receive_sandboxID = (SoapPrimitive) obj2.getProperty("Sandbox_ID");
                    str_sandboxID = receive_sandboxID.toString();
                Log.e("str_sandboxID", str_sandboxID);
                    SoapPrimitive receive_sandbox = (SoapPrimitive) obj2.getProperty("Sandbox_Name");
                    str_sandbox = receive_sandbox.toString();
                Log.e("str_sandbox", str_sandbox);
                    SoapPrimitive receive_academicID = (SoapPrimitive) obj2.getProperty("Academic_ID");
                    str_academicID = receive_academicID.toString();
                Log.e("str_academicID", str_academicID);
                    SoapPrimitive receive_academic = (SoapPrimitive) obj2.getProperty("Academic_Name");
                    str_academic = receive_academic.toString();
                Log.e("str_academic", str_academic);
                    SoapPrimitive receive_clusterID = (SoapPrimitive) obj2.getProperty("Cluster_ID");
                    str_clusterID = receive_clusterID.toString();
                Log.e("str_clusterID", str_clusterID);
                    SoapPrimitive receive_cluster = (SoapPrimitive) obj2.getProperty("Cluster_Name");
                    str_cluster = receive_cluster.toString();
                Log.e("str_cluster", str_cluster);
                    SoapPrimitive receive_instituteID = (SoapPrimitive) obj2.getProperty("Institute_ID");
                    str_instID = receive_instituteID.toString();
                Log.e("str_instID", str_instID);
                    SoapPrimitive receive_institute = (SoapPrimitive) obj2.getProperty("Institute_Name");
                    str_inst = receive_institute.toString();
                Log.e("str_inst", str_inst);
                    SoapPrimitive receive_schoolID = (SoapPrimitive) obj2.getProperty("School_ID");
                    str_schoolID = receive_schoolID.toString();
                Log.e("str_schoolID", str_schoolID);
                    SoapPrimitive receive_school = (SoapPrimitive) obj2.getProperty("School_Name");
                    str_school = receive_school.toString();
                Log.e("str_school", str_school);
                    SoapPrimitive receive_levelID = (SoapPrimitive) obj2.getProperty("Level_ID");
                    str_levelID = receive_levelID.toString();
                Log.e("str_levelID", str_levelID);
                    SoapPrimitive receive_level = (SoapPrimitive) obj2.getProperty("Level_Name");
                    str_level = receive_level.toString();
                Log.e("str_level", str_level);
                    SoapPrimitive receive_studentname = (SoapPrimitive) obj2.getProperty("Student_Name");
                    str_studentname = receive_studentname.toString();
                Log.e("str_studentname", str_studentname);
                    SoapPrimitive soap_gender = (SoapPrimitive) obj2.getProperty("Gender");
                    str_gender = soap_gender.toString();
                Log.e("str_gender", str_gender);
                    SoapPrimitive soap_birthdate = (SoapPrimitive) obj2.getProperty("Birth_Date");
                    str_edit_birthdate = soap_birthdate.toString();
                Log.e("str_edit_birthdate", str_edit_birthdate);
                    SoapPrimitive soap_edu = (SoapPrimitive) obj2.getProperty("Education");
                    selected_edu = soap_edu.toString();
                Log.e("selected_edu", selected_edu);
                    SoapPrimitive soap_mobno= (SoapPrimitive) obj2.getProperty("Mobile");
                    str_mobileno = soap_mobno.toString();
                Log.e("str_mobileno", str_mobileno);
                    SoapPrimitive soap_fname = (SoapPrimitive) obj2.getProperty("Father_Name");
                    str_fathername = soap_fname.toString();
                Log.e("str_fathername", str_fathername);
                    SoapPrimitive soap_mothername = (SoapPrimitive) obj2.getProperty("Mother_Name");
                    str_mothername = soap_mothername.toString();
                Log.e("str_mothername", str_mothername);
                    SoapPrimitive soap_aadhar = (SoapPrimitive) obj2.getProperty("Student_Aadhar");
                    str_aadar = soap_aadhar.toString();
                Log.e("str_aadar", str_aadar);
                    SoapPrimitive soap_createddate = (SoapPrimitive) obj2.getProperty("Created_Date");
                    str_created_date = soap_createddate.toString();
                Log.e("str_created_date", str_created_date);
                    SoapPrimitive soap_createdby = (SoapPrimitive) obj2.getProperty("Created_By");
                    str_created_by = soap_createdby.toString();
                Log.e("str_created_by", str_created_by);
                    SoapPrimitive soap_learningOpt = (SoapPrimitive) obj2.getProperty("Learning_Mode");
                    str_learningOpt = soap_learningOpt.toString().trim();
                    Log.e("str_learningOpt=", str_learningOpt);
                    SoapPrimitive soap_imagefile = (SoapPrimitive) obj2.getProperty("Student_Photo");
                    str_fetched_imgfile = soap_imagefile.toString();
                    //str_fetched_imgfile="";
                    Log.e("str_fetched_imgfile", str_fetched_imgfile);
                    //imgLoader.displayImage(str_imgfile, photo_edit_iv, displayoption, imageListener);

                    SoapPrimitive soap_marks4 = (SoapPrimitive) obj2.getProperty("Marks_4");
                    str_marks_4 = soap_marks4.toString();
                    SoapPrimitive soap_marks5 = (SoapPrimitive) obj2.getProperty("Marks_5");
                    str_marks_5 = soap_marks5.toString();
                    SoapPrimitive soap_marks6 = (SoapPrimitive) obj2.getProperty("Marks_6");
                    str_marks_6 = soap_marks6.toString();
                    Log.e("str_marks_6 app", str_marks_6);

                    SoapPrimitive soap_marks7 = (SoapPrimitive) obj2.getProperty("Marks_7");
                    str_marks_7 = soap_marks7.toString();
                    SoapPrimitive soap_marks8 = (SoapPrimitive) obj2.getProperty("Marks_8");
                    str_marks_8 = soap_marks8.toString();
                    SoapPrimitive soap_admissionfee = (SoapPrimitive) obj2.getProperty("Application_Fee");
                    str_admissionfee = soap_admissionfee.toString();
                    Log.e("str_admissionfee", str_admissionfee);

                } else if (selected_studentstatus.equals("Error")) {
                    Log.e("errormsg else", selected_studentstatus);

                }

            } catch (Throwable t) {
                Log.e("request fail", "> " + t.getMessage());


            }
        } catch (Throwable t) {
            Log.e("UnRegister Receiver ", "> " + t.getMessage());

        }

    }

    public void alert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Activity_EditRegistration.this);
        builder1.setMessage("No Data Found");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i=new Intent(Activity_EditRegistration.this,Activity_ViewStudentList_new.class);
                        startActivity(i);
                        finish();

                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    /*public void SetValues() {

        if(str_gender.equals("Boy")) {
            male_edit_RB.setChecked(true);
            female_edit_RB.setChecked(false);
        }else {
            male_edit_RB.setChecked(false);
            female_edit_RB.setChecked(true);

        }

        sandbox_edit_tv.setText(str_sandbox);
        academic_edit_tv.setText(str_academic);
        cluster_edit_tv.setText(str_cluster);
        institute_edit_tv.setText(str_inst);
        school_edit_tv.setText(str_school);
        level_edit_tv.setText(str_level);
        if(!str_studentname.equals("0")) {
            studentName_edit_et.setText(str_studentname);
        }

        if(!str_edit_birthdate.equals("")) {
            dateofbirth_edit_tv.setText(str_edit_birthdate);
        }

       if(!str_fathername.equals("0")) {
           fathername_edit_et.setText(str_fathername);
       }
        if(!str_mothername.equals("0")) {
            mothername_edit_et.setText(str_mothername);
        }
        if(!str_mobileno.equals("0")) {
            mobileno_edit_et.setText(str_mobileno);

        }

        if(!str_aadar.equals("0")) {
            aadhaarno_edit_et.setText(str_aadar);

        }
        if(!str_marks_4.equals("0")) {
          //  marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
            marks_edit_et.setText(str_marks_4);
            str_marks=str_marks_4;
        }
        if(!str_marks_5.equals("0")) {
         //   marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});


            marks_edit_et.setText(str_marks_5);
            str_marks=str_marks_5;
        }

        Log.e("str_marks_6 setvalues", str_marks_6);
        if(!str_marks_6.equals("0")) {
            Log.e("str_marks_6 setvalues", "Entered in Marks6");

            // marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
            marks_edit_et.setText(str_marks_6);
            str_marks=str_marks_6;
        }
        if(!str_marks_7.equals("0")) {
           // marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
            marks_edit_et.setText(str_marks_7);
            str_marks=str_marks_7;
        }
        if(!str_marks_8.equals("0")) {
            //marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
            marks_edit_et.setText(str_marks_8);
            str_marks=str_marks_8;
        }


        if(!str_admissionfee.equals("")) {
            admissionfees_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", str_admissionfee)});
            admissionfees_edit_et.setText(str_admissionfee);
            maxfees_edit_tv.setText(getResources().getString(R.string.Rs) +""+str_admissionfee);
        }
        if(!str_learningOpt.equalsIgnoreCase("")||str_learningOpt != null){
           // Integer int_learopt=Integer. parseInt(str_learningOpt);
            //learnoption_Sp.setSelection(int_learopt);
           *//* for (int i = 39; i < learnoption_Sp.getCount(); i++) {
                if (learnoption_Sp.getItemAtPosition(i).toString().equals(str_learningOpt))
                {
                    learnoption_Sp.setSelection(i);
                    break;
                }
            }*//*

           *//* Integer int_learopt=Integer. parseInt(str_learningOpt);
            int sel_gottoknow = dataAdapter_learnop.getPosition(int_learopt);
            learnoption_Sp.setSelection(sel_gottoknow);*//*

  *//*              int spinnerPosition = dataAdapter_learnop.getPosition(str_learningOpt);
            learnoption_Sp.setSelection(spinnerPosition);*//*
            learnoption_Sp.setSelection(getIndex_remarks(learnoption_Sp, str_learningOpt));

        }
        paymentmode_edit_tv.setText("Cash");

        if(selected_edu.equals("4th Standard")){
            education__edit_Sp.setSelection(1);
        }
        if(selected_edu.equals("5th Standard")){
            education__edit_Sp.setSelection(2);
        }
        if(selected_edu.equals("6th Standard")){
            education__edit_Sp.setSelection(3);
        }
        if(selected_edu.equals("7th Standard")){
            education__edit_Sp.setSelection(4);
        }
        if(selected_edu.equals("8th Standard")){
            education__edit_Sp.setSelection(5);
        }


        if(!str_fetched_imgfile.equals("")) {
            imgLoader.displayImage(str_fetched_imgfile, photo_edit_iv, displayoption, imageListener);

        }


//        if(!str_fetched_imgfile.equals("0")){
//            imgLoader.displayImage(str_fetched_imgfile, photo_edit_iv, displayoption, imageListener);
//
//        }else if(str_fetched_imgfile.equals("")){
//            photo_edit_iv.setImageResource(R.drawable.profileimg);
//        }




        if(selected_studentstatus.equals("Admission")){
            isAdmission=true;
            if(selected_edu.equals("4th Standard")){
                education__edit_Sp.setSelection(1);
            }
            if(selected_edu.equals("5th Standard")){
                education__edit_Sp.setSelection(2);
            }
            if(selected_edu.equals("6th Standard")){
                education__edit_Sp.setSelection(3);
            }
            if(selected_edu.equals("7th Standard")){
                education__edit_Sp.setSelection(4);
            }
            if(selected_edu.equals("8th Standard")){
                education__edit_Sp.setSelection(5);
            }
            if(!str_admissionfee.equals("")) {
                admissionfees_edit_et.setText(str_admissionfee);
                maxfees_edit_tv.setText(str_admissionfee);
            }
           *//* if(!str_learningOpt.equalsIgnoreCase("")){
                Integer int_learopt=Integer. parseInt(str_learningOpt);
                learnoption_Sp.setSelection(int_learopt);
            }*//*
            if(!str_learningOpt.equalsIgnoreCase("")||str_learningOpt != null){
                // Integer int_learopt=Integer. parseInt(str_learningOpt);
                //learnoption_Sp.setSelection(int_learopt);
           *//* for (int i = 39; i < learnoption_Sp.getCount(); i++) {
                if (learnoption_Sp.getItemAtPosition(i).toString().equals(str_learningOpt))
                {
                    learnoption_Sp.setSelection(i);
                    break;
                }
            }*//*
              *//*  Integer int_learopt=Integer. parseInt(str_learningOpt);
                sel_learopt = dataAdapter_learnop.getPosition(int_learopt);
                learnoption_Sp.setSelection(sel_learopt);*//*
                *//*int spinnerPosition = dataAdapter_learnop.getPosition(str_learningOpt);
                learnoption_Sp.setSelection(spinnerPosition);*//*
                learnoption_Sp.setSelection(getIndex_remarks(learnoption_Sp, str_learningOpt));
            }

            paymentmode_edit_tv.setText("Cash");

//            if(!str_fetched_imgfile.equals("0")){
//                imgLoader.displayImage(str_fetched_imgfile, photo_edit_iv, displayoption, imageListener);
//
//            }else{
//                photo_edit_iv.setImageResource(R.drawable.profileimg);
//            }
            if(!str_fetched_imgfile.equals("")) {
                imgLoader.displayImage(str_fetched_imgfile, photo_edit_iv, displayoption, imageListener);

            }


            if(isAdmission) {
                PaymentDate_edit_ll.setVisibility(View.GONE);
                PaymentMode_edit_ll.setVisibility(View.GONE);
                MaxAdmissionFees_edit_ll.setVisibility(View.GONE);
                admissionfees_ll.setVisibility(View.GONE);

                admissionfees_edit_et.setVisibility(View.GONE);
                admissionfees_edit_et.setEnabled(false);
                paymentdate_edit_tv.setVisibility(View.GONE);
                paymentdate_edit_tv.setEnabled(false);
                receipt_no_edit_et.setVisibility(View.GONE);
                receipt_nolabel_edit_tv.setVisibility(View.GONE);

                ArrayAdapter dataAdapter_status = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray_admission);
                dataAdapter_status.setDropDownViewResource(R.layout.spinnercenterstyle);
                studentstatus_edit_SP.setAdapter(dataAdapter_status);
            }else{
                PaymentDate_edit_ll.setVisibility(View.VISIBLE);
                PaymentMode_edit_ll.setVisibility(View.VISIBLE);
                MaxAdmissionFees_edit_ll.setVisibility(View.VISIBLE);
                admissionfees_ll.setVisibility(View.VISIBLE);
                receipt_no_edit_et.setVisibility(View.VISIBLE);
                receipt_nolabel_edit_tv.setVisibility(View.VISIBLE);


                admissionfees_edit_et.setVisibility(View.VISIBLE);
                admissionfees_edit_et.setEnabled(true);
                paymentdate_edit_tv.setVisibility(View.VISIBLE);
                paymentdate_edit_tv.setEnabled(true);
                ArrayAdapter dataAdapter_status = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray);
                dataAdapter_status.setDropDownViewResource(R.layout.spinnercenterstyle);
                studentstatus_edit_SP.setAdapter(dataAdapter_status);

            }

        }
        else{
            isAdmission=false;
        }


    }*/

    public void SetValues() {



        if(str_gender.equals("Boy")) {
            male_edit_RB.setChecked(true);
            female_edit_RB.setChecked(false);
        }else {
            male_edit_RB.setChecked(false);
            female_edit_RB.setChecked(true);

        }

        sandbox_edit_tv.setText(str_sandbox);
        academic_edit_tv.setText(str_academic);
        cluster_edit_tv.setText(str_cluster);
        institute_edit_tv.setText(str_inst);
        school_edit_tv.setText(str_school);
        level_edit_tv.setText(str_level);
        if(!str_studentname.equals("0")) {
            studentName_edit_et.setText(str_studentname);
        }

        if(!str_edit_birthdate.equals("")) {
            dateofbirth_edit_tv.setText(str_edit_birthdate);
        }

        if(!str_fathername.equals("0")) {
            fathername_edit_et.setText(str_fathername);
        }
        if(!str_mothername.equals("0")) {
            mothername_edit_et.setText(str_mothername);
        }
        if(!str_mobileno.equals("0")) {
            mobileno_edit_et.setText(str_mobileno);

        }

        if(!str_aadar.equals("0")) {
            aadhaarno_edit_et.setText(str_aadar);

        }
        if(!str_marks_4.equals("0")) {
            //  marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
            marks_edit_et.setText(str_marks_4);
            str_marks=str_marks_4;
        }
        if(!str_marks_5.equals("0")) {
            //   marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});


            marks_edit_et.setText(str_marks_5);
            str_marks=str_marks_5;
        }

        Log.e("str_marks_6 setvalues", str_marks_6);
        if(!str_marks_6.equals("0")) {
            Log.e("str_marks_6 setvalues", "Entered in Marks6");

            // marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
            marks_edit_et.setText(str_marks_6);
            str_marks=str_marks_6;
        }
        if(!str_marks_7.equals("0")) {
            // marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
            marks_edit_et.setText(str_marks_7);
            str_marks=str_marks_7;
        }
        if(!str_marks_8.equals("0")) {
            //marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
            marks_edit_et.setText(str_marks_8);
            str_marks=str_marks_8;
        }


        if(!str_admissionfee.equals("")) {
            admissionfees_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", str_admissionfee)});
            admissionfees_edit_et.setText(str_admissionfee);
            maxfees_edit_tv.setText(getResources().getString(R.string.Rs) +""+str_admissionfee);
        }
        paymentmode_edit_tv.setText("Cash");

        if(selected_edu.equals("4th Standard")){
            education__edit_Sp.setSelection(1);
        }
        if(selected_edu.equals("5th Standard")){
            education__edit_Sp.setSelection(2);
        }
        if(selected_edu.equals("6th Standard")){
            education__edit_Sp.setSelection(3);
        }
        if(selected_edu.equals("7th Standard")){
            education__edit_Sp.setSelection(4);
        }
        if(selected_edu.equals("8th Standard")){
            education__edit_Sp.setSelection(5);
        }

        if(!str_learningOpt.equalsIgnoreCase("")||str_learningOpt != null){
        learnoption_Sp.setSelection(getIndex_remarks(learnoption_Sp, str_learningOpt));
        }


        if(!str_fetched_imgfile.equals("")) {
            imgLoader.displayImage(str_fetched_imgfile, photo_edit_iv, displayoption, imageListener);

        }


//        if(!str_fetched_imgfile.equals("0")){
//            imgLoader.displayImage(str_fetched_imgfile, photo_edit_iv, displayoption, imageListener);
//
//        }else if(str_fetched_imgfile.equals("")){
//            photo_edit_iv.setImageResource(R.drawable.profileimg);
//        }




        if(selected_studentstatus.equals("Admission")) {
            isAdmission=true;
            if (selected_edu.equals("4th Standard")) {
                education__edit_Sp.setSelection(1);
            }
            if (selected_edu.equals("5th Standard")) {
                education__edit_Sp.setSelection(2);
            }
            if (selected_edu.equals("6th Standard")) {
                education__edit_Sp.setSelection(3);
            }
            if (selected_edu.equals("7th Standard")) {
                education__edit_Sp.setSelection(4);
            }
            if (selected_edu.equals("8th Standard")) {
                education__edit_Sp.setSelection(5);
            }
            if (!str_admissionfee.equals("")) {
                admissionfees_edit_et.setText(str_admissionfee);
                maxfees_edit_tv.setText(str_admissionfee);
            }
            if(!str_learningOpt.equalsIgnoreCase("")||str_learningOpt != null){
                learnoption_Sp.setSelection(getIndex_remarks(learnoption_Sp, str_learningOpt));
            }

            paymentmode_edit_tv.setText("Cash");

//            if(!str_fetched_imgfile.equals("0")){
//                imgLoader.displayImage(str_fetched_imgfile, photo_edit_iv, displayoption, imageListener);
//
//            }else{
//                photo_edit_iv.setImageResource(R.drawable.profileimg);
//            }
            if (!str_fetched_imgfile.equals("")) {
                imgLoader.displayImage(str_fetched_imgfile, photo_edit_iv, displayoption, imageListener);

            }
        }else{
            isAdmission=false;
        }


        if(isAdmission) {
            PaymentDate_edit_ll.setVisibility(View.VISIBLE);
            PaymentMode_edit_ll.setVisibility(View.VISIBLE);
            MaxAdmissionFees_edit_ll.setVisibility(View.VISIBLE);
            admissionfees_ll.setVisibility(View.VISIBLE);
            receipt_no_edit_et.setVisibility(View.VISIBLE);
            receipt_nolabel_edit_tv.setVisibility(View.VISIBLE);
            admissionfees_edit_et.setVisibility(View.VISIBLE);
            admissionfees_edit_et.setEnabled(true);
            paymentdate_edit_tv.setVisibility(View.VISIBLE);
            paymentdate_edit_tv.setEnabled(true);

//                PaymentDate_edit_ll.setVisibility(View.GONE);
//                PaymentMode_edit_ll.setVisibility(View.GONE);
//                MaxAdmissionFees_edit_ll.setVisibility(View.GONE);
//                admissionfees_ll.setVisibility(View.GONE);
//                admissionfees_edit_et.setVisibility(View.GONE);
//                admissionfees_edit_et.setEnabled(false);
//                paymentdate_edit_tv.setVisibility(View.GONE);
//                paymentdate_edit_tv.setEnabled(false);
//                receipt_no_edit_et.setVisibility(View.GONE);
//                receipt_nolabel_edit_tv.setVisibility(View.GONE);
//////---------Commented on 12th oct 2020

            ArrayAdapter dataAdapter_status = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray_admission);
            dataAdapter_status.setDropDownViewResource(R.layout.spinnercenterstyle);
            studentstatus_edit_SP.setAdapter(dataAdapter_status);
        }else{
//                PaymentDate_edit_ll.setVisibility(View.VISIBLE);
//                PaymentMode_edit_ll.setVisibility(View.VISIBLE);
//                MaxAdmissionFees_edit_ll.setVisibility(View.VISIBLE);
//                admissionfees_ll.setVisibility(View.VISIBLE);
//                receipt_no_edit_et.setVisibility(View.VISIBLE);
//                receipt_nolabel_edit_tv.setVisibility(View.VISIBLE);
//                admissionfees_edit_et.setVisibility(View.VISIBLE);
//                admissionfees_edit_et.setEnabled(true);
//                paymentdate_edit_tv.setVisibility(View.VISIBLE);
//                paymentdate_edit_tv.setEnabled(true);


            PaymentDate_edit_ll.setVisibility(View.GONE);
            PaymentMode_edit_ll.setVisibility(View.GONE);
            MaxAdmissionFees_edit_ll.setVisibility(View.GONE);
            admissionfees_ll.setVisibility(View.GONE);
            admissionfees_edit_et.setVisibility(View.GONE);
            admissionfees_edit_et.setEnabled(false);
            paymentdate_edit_tv.setVisibility(View.GONE);
            paymentdate_edit_tv.setEnabled(false);
            receipt_no_edit_et.setVisibility(View.GONE);
            receipt_nolabel_edit_tv.setVisibility(View.GONE);


            ArrayAdapter dataAdapter_status = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray);
            dataAdapter_status.setDropDownViewResource(R.layout.spinnercenterstyle);
            studentstatus_edit_SP.setAdapter(dataAdapter_status);

        }

    }
//    public boolean Validation() {
//        Boolean bname = true,bcontactno=true,breceiptno=true;
//        if (studentName_edit_et.getText().toString().length() == 0) {
//            studentName_edit_et.setError("Enter Student Name");
//
//            Toast.makeText(getApplicationContext(), "Please Enter Student Name", Toast.LENGTH_SHORT).show();
//            bname = false;
//
//        }
//
//        if (mobileno_edit_et.getText().toString().length() == 0 || mobileno_edit_et.getText().toString().length() < 10 || mobileno_edit_et.getText().toString().length() > 10) {
//            mobileno_edit_et.setError("Enter Contact Number");
//            Toast.makeText(getApplicationContext(), "Please Enter Correct Contact Number", Toast.LENGTH_SHORT).show();
//            bcontactno = false;
//
//        }
//
//
////        if (bname && bcontactno) {
////            return true;
////        } else {
////            return false;
////        }
//
//        if(isAdmission) {
//
//            if (receipt_no_edit_et.getText().toString().length() == 0) {
//                receipt_no_edit_et.setError("Enter Receipt No.");
//                Toast.makeText(getApplicationContext(), "Please Enter Receipt Number", Toast.LENGTH_SHORT).show();
//                breceiptno = false;
//
//            }
//
//
//        }
//
//        if(!isAdmission) {
//            return bname && bcontactno && breceiptno;
//        }else{
//            return bname && bcontactno;
//        }
//
//
//    }
public boolean Validation() {


    Boolean bname = true, bedu = true, bbirthdate = true, bcellnumber = true, bphoto = true;
    Boolean brecno = true, bmothername = true, baadar = true, baadar_length = true, bpaymentdate = true, bfee = true, bmarks = true;


    boolean flags = true;
    //For Student Name

    if ((studentName_edit_et.getText().toString().length() == 0) || (studentName_edit_et.getText().toString().length() <= 2)) {
        studentName_edit_et.setError("InValid  Name");
        // flags=false;
        Toast.makeText(getApplicationContext(), "Please Enter Valid Name", Toast.LENGTH_LONG).show();
        bname = false;

    }
//        if (!studentname_et.getText().toString().matches(String.valueOf(name_pattern))) {
//            studentname_et.setError("InValid  Name");
//            bname = false;
//
//        }


//        if (aadar_et.getText().toString().length() == 0) {
//            aadar_et.setError("Empty is not allowed");
//            // flags=false;
//            baadar = false;
//            Toast.makeText(getApplicationContext(), "Please Enter Aadhar Number", Toast.LENGTH_LONG).show();
//
//
//        }
//
//        if(aadar_et.getText().toString().length()<12){
//            aadar_et.setError("Invalid Aadhar No.");
//            baadar_length = false;
//            Toast.makeText(getApplicationContext(), "Please Enter Valid Aadhar Number", Toast.LENGTH_LONG).show();
//
//        }

    if (mobileno_edit_et.getText().toString().length() == 0 || mobileno_edit_et.getText().toString().length() < 10) {
        mobileno_edit_et.setError("InValid Mobile Number");
        Toast.makeText(getApplicationContext(), "Please Enter correct Mobile Number", Toast.LENGTH_LONG).show();

        // flags=false;
        bcellnumber = false;

    }

   // if (str_edit_birthdate.length() == 0) {
        if(dateofbirth_edit_tv.getText().toString().equals("Select Birth Date")){
        //  birthdate_TV.setError("Empty is not allowed");
        bbirthdate = false;
        Toast.makeText(getApplicationContext(), "Please select birth date", Toast.LENGTH_LONG).show();

    }


//        if (fathername_et.getText().toString().length() == 0 || fathername_et.getText().toString().length() < 1) {
//            fathername_et.setError("Father Name must be Minimum 1 character");
//            //  flags=false;
//            bfathername = false;
//        }
//        if (mothername_et.getText().toString().length() == 0 || mothername_et.getText().toString().length() < 1) {
//            mothername_et.setError("Mother Name must be Minimum 1 character");
//            //  flags=false;
//            bmothername = false;
//        }

//        if (marks_et.getVisibility() == View.VISIBLE) {
//            if (marks_et.getText().toString().length() == 0) {
//                marks_et.setError("Empty is not allowed");
//                bmarks = false;
//                Toast.makeText(getApplicationContext(), "Please Enter Marks", Toast.LENGTH_LONG).show();
//
//            }
//        }

    if (selected_edu.equals("Select")) {
        bedu = false;
        Toast.makeText(getApplicationContext(), "Please Select Education", Toast.LENGTH_LONG).show();

    }

    if (paymentdate_edit_tv.getVisibility() == View.VISIBLE) {
        if (str_paymentDate.length() == 0) {
            //paymentdate_tv.setError("Empty is not allowed");
            bpaymentdate = false;
            Toast.makeText(getApplicationContext(), "Please select payment date", Toast.LENGTH_LONG).show();

        }

        if (admissionfees_edit_et.getText().toString().length() == 0) {
            bfee = false;
            Toast.makeText(getApplicationContext(), "Please Enter Admission fees", Toast.LENGTH_LONG).show();

        }
//            if (admissionfee_et.getText().toString()>selected_admissionfee) {
//                bfee = false;
//                Toast.makeText(getApplicationContext(), "Please Enter Valid Admission fees", Toast.LENGTH_LONG).show();
//
//            }

        if (receipt_no_edit_et.getText().toString().length() == 0) {
            brecno = false;
            Toast.makeText(getApplicationContext(), "Please Enter Receipt Number", Toast.LENGTH_LONG).show();

        }
    }

//        if (str_img.length() == 0) {
//            bphoto = false;
//            Toast.makeText(getApplicationContext(), "Please upload photo", Toast.LENGTH_LONG).show();
//
//        }

//        if (bname && bcellnumber && bfathername && bpaymentdate && bedu && bmothername && bbirthdate && baadar && baadar_length && bphoto && bfee && bmarks) {
//            return true;
//        } else {
//            return false;
//        }

    if (paymentdate_edit_tv.getVisibility() == View.VISIBLE) {
        return bname && bcellnumber && bpaymentdate && bedu && bbirthdate && bfee && brecno;

    } else {
        return bname && bcellnumber && bedu && bbirthdate && bfee;

    }

//        if (bname && bcellnumber && bpaymentdate && bedu && bbirthdate && bfee) {
//            return true;
//        } else {
//            return false;
//        }


}

    private int getIndex_remarks(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {

// Log.e("spinner",spinner.getItemAtPosition(i).toString());
if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
        return i;
    }
}

return 0;
        }

//    public void UpdateEditedData() {
//
//
//        if (isInternetPresent) {
//            UpdateEditedInfoTask task = new UpdateEditedInfoTask(Activity_EditRegistration.this);
//            task.execute();
//        } else {
//            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
//        }
//
//    }

//    private class UpdateEditedInfoTask extends AsyncTask<String, Void, Void> {
//        ProgressDialog dialog;
//        Context context;
//
//        @Override
//        protected Void doInBackground(String... params) {
//
//            Date c = Calendar.getInstance().getTime();
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            str_created_date = df.format(c);
//
//            str_studentname=studentName_edit_et.getText().toString();
//            str_fathername=fathername_edit_et.getText().toString();
//            str_mothername=mothername_edit_et.getText().toString();
//            str_mobileno=mobileno_edit_et.getText().toString();
//            str_aadar=aadhaarno_edit_et.getText().toString();
//            //str_edit_birthdate=dateofbirth_edit_tv.getText().toString();
//            str_receiptno=receipt_no_edit_et.getText().toString();
//            //str_marks=marks_edit_et.getText().toString();
//
//
//            RegisterResponse=UpdateData();
//            return null;
//        }// doInBackground Process
//
//        public UpdateEditedInfoTask(Context context1) {
//            context = context1;
//            dialog = new ProgressDialog(context1,R.style.AppCompatAlertDialogStyle);
//        }
//
//        @Override
//        //Once WebService returns response
//        protected void onPostExecute(Void result) {
//            if ((dialog != null) && dialog.isShowing()) {
//                dialog.dismiss();
//
//            }
//
//            if (!RegisterResponse) {
//                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
//
//            } else {
//                Toast.makeText(getApplicationContext(), "Application Submitted", Toast.LENGTH_SHORT).show();
//                if((path != null))
//                {
//                    File fdelete = new File(path);
//                    if (fdelete.exists()) {
//                        if (fdelete.delete()) {
//                            Log.v("log_tag", "file Deleted =" + path);
//                            File dir = new File(Environment.getExternalStorageDirectory() + "GetSignature");
//                            File dir1 = new File(Environment.getExternalStorageDirectory() + "DetSkillsSign/Images");
//                            if (dir.isDirectory()) {
//                                dir.delete();
//                            }
//                            if (dir1.isDirectory()) {
//                                dir1.delete();
//                            }
//                        } else {
//                            Log.e("file not Deleted :" ,path);
//                        }
//                    }
//
//                }
//
////                if(CameraPhotoCapture.imagepathforupload != null)
////                {
////                    File imagefilepath = new File(CameraPhotoCapture.imagepathforupload);
////                    Log.v("log_tag", "imagefilepath=" + imagefilepath);
////
////                    if (imagefilepath.exists()) {
////                        if (imagefilepath.delete()) {
////                            Log.v("log_tag", "imagefilepath deleted=" + imagefilepath);
////                        } else {
////                            Log.v("log_tag", "imagefilepath not deleted=" + imagefilepath);
////                        }
////                    }
////                }
//
//
//                Intent i=new Intent(getApplicationContext(),Activity_ViewStudentList_new.class);
//                startActivity(i);
//                finish();
//
//            }
//
//        }// End of onPostExecute
//
//        @Override
//        //Make Progress Bar visible
//        protected void onPreExecute() {
//            dialog.setMessage("Please wait..");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//
//        }//End of onPreExecute
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//        }//End of onProgressUpdates
//    } // End of AsyncCallWS
//
//    public Boolean UpdateData() {
//
//
//        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
//        String SOAP_ACTION = "http://mis.detedu.org:8089/UpdateStudentData";
//        String METHOD_NAME = "UpdateStudentData";
//        String NAMESPACE = "http://mis.detedu.org:8089/";
//
//        try {
//
//            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//
//            if (isAdmission) {
//
//                request.addProperty("Student_ID", int_val_studentID);
//                request.addProperty("Sandbox_ID", int_val_sandboxID);
//                request.addProperty("Academic_ID", int_val_academicid);
//                request.addProperty("Cluster_ID", int_val_clusterid);
//                request.addProperty("Institute_ID", int_val_instituteid);
//                request.addProperty("Level_ID", int_val_levelid);
//                request.addProperty("School_ID", int_val_schoolid);
//                request.addProperty("Student_Name", str_studentname);
//                request.addProperty("Gender", str_gender);
//                request.addProperty("Birth_Date", str_edit_birthdate);
//                request.addProperty("Education", selected_edu);
//                request.addProperty("Marks", marks_edit_et.getText().toString());
//                request.addProperty("Mobile", mobileno_edit_et.getText().toString());
//                request.addProperty("Father_Name", str_fathername);
//                request.addProperty("Mother_Name", str_mothername);
//                request.addProperty("Student_Aadhar", str_aadar);
//                request.addProperty("Student_Status", selected_studentstatus);
//                request.addProperty("Admission_Fee", "");
//                request.addProperty("Created_Date", str_created_date);  //<iYear>int</iYear>
//                request.addProperty("Created_By", str_created_by);  //<Habit>string</Habit> //str_habit
//                request.addProperty("File_Name", str_imgfile);
//                request.addProperty("Receipt_Manual", str_receiptno);
//                request.addProperty("Learning_Mode", selected_learnOption);
//            } else {
//
//
//            request.addProperty("Student_ID", int_val_studentID);
//            request.addProperty("Sandbox_ID", int_val_sandboxID);
//            request.addProperty("Academic_ID", int_val_academicid);
//            request.addProperty("Cluster_ID", int_val_clusterid);
//            request.addProperty("Institute_ID", int_val_instituteid);
//            request.addProperty("Level_ID", int_val_levelid);
//            request.addProperty("School_ID", int_val_schoolid);
//            request.addProperty("Student_Name", str_studentname);
//            request.addProperty("Gender", str_gender);
//            request.addProperty("Birth_Date", str_edit_birthdate);
//            request.addProperty("Education", selected_edu);
//            request.addProperty("Marks", marks_edit_et.getText().toString());
//            request.addProperty("Mobile", mobileno_edit_et.getText().toString());
//            request.addProperty("Father_Name", str_fathername);
//            request.addProperty("Mother_Name", str_mothername);
//            request.addProperty("Student_Aadhar", str_aadar);
//            request.addProperty("Student_Status", selected_studentstatus);
//            if (selected_studentstatus.equals("Admission")) {
//                Log.e("application_status", "Admission");
//                request.addProperty("Admission_Fee", admissionfees_edit_et.getText().toString());
//                request.addProperty("Receipt_Manual", receipt_no_edit_et.getText().toString());
//
//            } else {
//                Log.e("application_status", "Applicant");
//                request.addProperty("Admission_Fee", "");
//                request.addProperty("Receipt_Manual", "");
//
//            }
//            request.addProperty("Created_Date", str_created_date);  //<iYear>int</iYear>
//            request.addProperty("Created_By", str_created_by);  //<Habit>string</Habit> //str_habit
//            request.addProperty("File_Name", str_imgfile);
//            request.addProperty("Learning_Mode", selected_learnOption);
//
//        }
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//                    SoapEnvelope.VER11);
//            new MarshalBase64().register(envelope);
//            envelope.dotNet = true;
//            // Set output SOAP object
//            Log.e("insert detailsedit", request.toString());
//            envelope.setOutputSoapObject(request);
//
//
//            // Create HTTP call object
//            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
//
//
//            try {
//                androidHttpTransport.call(SOAP_ACTION, envelope);
//                SoapObject response_new = (SoapObject) envelope.getResponse();
//                Log.e("appsubResponse", response_new.toString());
//                Log.e("status", response_new.getProperty(0).toString());
//
//                if (response_new.getProperty(0).toString().contains("Student_Status")) {
//                    if (response_new.getProperty(0).toString().contains("Student_Status=Applicant")) {
//                        Log.e("Student_Status", "Applicant");
//                        return true;
//                    } else if (response_new.getProperty(0).toString().contains("Student_Status=Admission")) {
//
//                        Log.e("Student_Status", "Admission");
//                        return true;
//
//                    }else if (response_new.getProperty(0).toString().contains("Student_Status=Error")) {
//                        Log.e("Student_Status", "Error");
//                        return false;
//
//                    }
//
//
//                }
//
//            } catch (Throwable t) {
//                Log.e("request fail tag", "request fail catched" + t.getMessage());
//            }
//        } catch (Throwable t) {
//            Log.e("Receiver Error", "> " + t.getMessage());
//
//        }
//
//
//        return false;
//
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Activity_EditRegistration.this,Activity_ViewStudentList_new.class);
//        startActivity(i);
//        finish();
        SharedPreferences.Editor myprefs_spinner = sharedpref_spinner_Obj.edit();
        sharedpref_stuid_Obj=getSharedPreferences(sharedpreferenc_studentid_new, Context.MODE_PRIVATE);


        myprefs_spinner.putInt(Key_sel_yearsp, sel_yearsp);
        myprefs_spinner.putInt(Key_sel_sandboxsp, sel_sandboxsp);
        myprefs_spinner.putInt(Key_sel_clustersp, sel_clustersp);
        myprefs_spinner.putInt(Key_sel_institutesp, sel_institute);
        myprefs_spinner.putInt(Key_sel_levelsp, sel_levelsp);
        myprefs_spinner.putInt(Key_sel_applnstatus, sel_applnstatus);
        myprefs_spinner.putInt(key_studentid, str_stuID);
        myprefs_spinner.apply();
        startActivity(i);
        finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu items
        getMenuInflater().inflate(R.menu.menu_register, menu);
        menu.findItem(R.id.Sync)
                .setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {


            case android.R.id.home:
                Intent i=new Intent(Activity_EditRegistration.this,Activity_ViewStudentList_new.class);
                startActivity(i);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
