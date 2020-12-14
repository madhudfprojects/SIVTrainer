package com.det.skillinvillage;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.det.skillinvillage.adapter.Class_SandBoxDetails;
import com.det.skillinvillage.model.AddStudentDetailsRequest;
import com.det.skillinvillage.model.AddStudentDetailsResponse;
import com.det.skillinvillage.model.Class_Response_AddStudentDetailsList;
import com.det.skillinvillage.model.Cluster;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.District;
import com.det.skillinvillage.model.Education;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.model.Institute;
import com.det.skillinvillage.model.LearningMode;
import com.det.skillinvillage.model.Level;
import com.det.skillinvillage.model.Sandbox;
import com.det.skillinvillage.model.School;
import com.det.skillinvillage.model.State;
import com.det.skillinvillage.model.Student;
import com.det.skillinvillage.model.StudentList;
import com.det.skillinvillage.model.Taluka;
import com.det.skillinvillage.model.Village;
import com.det.skillinvillage.model.Year;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;
import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferencebook_usercredential;


public class Activity_Register_New extends AppCompatActivity {

    public final static String TRANSFORMATION = "transformation";
    public final static String CUBE_OUT_DEPTH_TRANSFORMATION = "cube_out_depth transformation";
    public static String imagepathfordeletion_new = "";
    public static String compressedfilepaths = "";
    public static String imagepathforupload = "";


    Button submit_bt;
    TextView markslabel_tv;
    EditText studentname_et, fathername_et, mothername_et, mobileno_et, aadar_et, marks_et;
    Spinner school_sp, sandbox_sp, academic_sp, cluster_sp, institute_sp, level_sp, edu_sp, StudentStatus_sp;
    static TextView birthdate_TV;
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    String internet_issue = "false";
    int academiclistcount;

    String selected_academicname = "", selected_clusterName;

    //    Class_academicDetails[] arrayObj_Class_academicDetails, arrayObj_Class_academicDetails2;
//    Class_academicDetails obj_Class_academicDetails;
//
//    Class_ClusterDetails[] arrayObj_Class_clusterDetails, arrayObj_Class_clusterDetails2;
//    Class_ClusterDetails obj_Class_clusterDetails;
//
//    Class_InsituteDetails[] arrayObj_Class_InstituteDetails, arrayObj_Class_InstituteDetails2;
//    Class_InsituteDetails obj_Class_instituteDetails;
//    String selected_instituteName;
//
//    Class_LevelDetails[] arrayObj_Class_LevelDetails, arrayObj_Class_LevelDetails2;
//    Class_LevelDetails obj_Class_levelDetails;
//    String selected_levelName, selected_levelAdmissionFee;
//
//
//    Class_SandBoxDetails[] arrayObj_Class_sandboxDetails, arrayObj_Class_sandboxDetails2;
//    Class_SandBoxDetails obj_Class_sandboxDetails;
//
//    Class_LearningOption[] arrayObj_Class_learnOption, arrayObj_Class_learnOption2;
//    Class_LearningOption obj_Class_LearningOption;
//
//
//    Class_SchoolDetails[] arrayObj_Class_SchoolDetails, arrayObj_Class_SchoolDetails2;
//    Class_SchoolDetails obj_Class_SchoolDetails;
    String selected_schoolName = "", selected_edu = "", sp_strEdu_ID = "";
    String selected_levelName = "", selected_levelAdmissionFee = "";
    String selected_instituteName = "";


    String selected_paymentmode = "", selected_sandboxName = "", selected_studentstatus = "", str_receipt_no = "", selected_admissionfee = "";
    String selected_learnOption = "", sp_strLearningmode_ID = "";
    String[] studentstatusArray = {"Applicant", "Admission"};
    LinearLayout admission_ll, spinnerslayout_ll, mainRegistration_ll;
    Spinner paymentmode_sp, learnoption_Spinner;
    String[] paymentArray = {"Cash"};
    EditText admissionfee_et;
    static TextView paymentdate_tv;
    String[] educationArray = {"Select", "4th Standard", "5th Standard", "6th Standard", "7th Standard", "8th Standard", "9th Standard"};
    public static ImageView photo_iv;
    Button uploadimg_Btn;
    ProgressBar progressbar;
    Boolean RegisterResponse;
    String str_img = "";
    static String str_birthdate = "";
    static String str_paymentdate = "";
    static String yyyyMMdd_birthdate = "";
    static String yyyyMMdd_paymentdate = "";

    RadioGroup gender_radiogroup;
    RadioButton rb_male, rb_female;
    int radiogroupIndex;
    String gender = "Boy";
    String path;
    Bitmap bitmap;
    Bitmap scaledBitmap = null;
    String str_loginuserID = "";
    String str_marks = "", sp_strsand_ID, sp_strLev_ID, sp_strschool_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID;
    TextView maxfees_tv;
    final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
    public static int count = 0;
    String mCurrentPhotoPath = "";


    boolean insertedoffline = false;
    String str_ForSavingData_studentname, str_ForSavingData_mobileno, str_ForSavingData_fathername,
            str_ForSavingData_mothername, str_ForSavingData_aadar;

    SQLiteDatabase db;
    int strval, counter, selected_sandboxID_int, selected_academicID_int, selected_clusterID_int, selected_instituteID_int, selected_schoolID_int, selected_levelID_int;
    String createddate, createdby;
    Boolean digitalcamerabuttonpressed = false;
    byte[] signimageinbytesArray = {0};
    EditText receipt_no_et;


    SharedPreferences sharedpref_loginuserid_Obj;
    private String blockCharacterSet = "~#^|$%&*!.DEFGHIJKLMOPTWXYZ";

    String str_logout_count_Status = "";
    Class_LearningOption[] Arrayclass_learningOption;
    //    private InputFilter filter = new InputFilter() {
//
//        @Override
//        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//
//            if (source != null && blockCharacterSet.contains(("" + source))) {
//                return "";
//            }
//            return null;
//        }
//    };

    StudentList[] class_farmerprofileoffline_array_obj;

    int sel_yearsp = 0, sel_sandboxsp = 0, sel_institute = 0, sel_school = 0, sel_applnstatus = 0, sel_clustersp = 0, sel_taluksp = 0, sel_levelsp = 0, sel_learnmode = 0, sel_edusp = 0;
    List<Sandbox> sandboxList;
    List<Cluster> clusterList;
    List<Institute> instituteList;
    List<Level> levelList;
    List<Year> yearList;

    Sandbox[] arrayObj_Class_sandboxDetails2;
    Sandbox Obj_Class_sandboxDetails;
    Cluster[] arrayObj_Class_clusterDetails2;
    Cluster Obj_Class_ClusterDetails;
    Institute[] arrayObj_Class_instituteDetails2;
    Institute Obj_Class_InstDetails;
    School[] arrayObj_Class_schoolDetails2;
    School Obj_Class_SchoolDetails;
    Level[] arrayObj_Class_levelDetails2;
    Level Obj_Class_LevelDetails;
    Year Obj_Class_yearDetails;
    Year[] arrayObj_Class_yearDetails2;
    LearningMode[] arrayObj_Class_learningmodeDetails2;
    LearningMode obj_Class_LearningOption;
    Education[] arrayObj_Class_educationDetails2;
    Education obj_Class_education;

    Spinner state_new_SP, district_new_SP, taluk_new_SP, village_new_SP;
    EditText Studentaddress_ET;

    State Obj_Class_stateDetails;
    String sp_strstate_ID = "", selected_stateName = "", sp_strdistrict_ID = "", sp_strdistrict_state_ID = "", selected_district = "", sp_strTaluk_ID = "", selected_taluk = "", sp_strVillage_ID = "", selected_village = "";
    State[] arrayObj_Class_stateDetails2;
    District Obj_Class_DistrictDetails;
    District[] arrayObj_Class_DistrictListDetails2;
    Taluka Obj_Class_TalukDetails;
    Taluka[] arrayObj_Class_TalukListDetails2;
    Village Obj_Class_VillageListDetails;
    Village[] arrayObj_Class_VillageListDetails2;
    int statepos = 0, districtpos = 0, talukpos = 0, grampanchayatpos = 0, villagepos = 0;

    SharedPreferences sharedpreferencebook_usercredential_Obj;

    String[] studentstatusArray_admission = {"Admission"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__register__new);


        File newdir = new File(dir);
        if (!newdir.exists()) {
            newdir.mkdir();
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registration Form");
        sandboxList = new ArrayList<>();
        clusterList = new ArrayList<>();
        instituteList = new ArrayList<>();
        levelList = new ArrayList<>();
        yearList = new ArrayList<>();


        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

//        SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
//        str_loginuserID = myprefs.getString("login_userid", "nothing");

//        sharedpref_loginuserid_Obj = getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
//        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();
        sharedpreferencebook_usercredential_Obj = getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_loginuserID = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();


        progressbar = findViewById(R.id.progressBar1); //Progress Bar

        submit_bt = findViewById(R.id.submit_BTN);
        studentname_et = findViewById(R.id.Studentname_ET);
        fathername_et = findViewById(R.id.fathername_ET);
        mothername_et = findViewById(R.id.mothername_ET);
        mobileno_et = findViewById(R.id.mobile_ET);
        aadar_et = findViewById(R.id.aadar_ET);
        marks_et = findViewById(R.id.marks_ET);
        sandbox_sp = findViewById(R.id.sandboxlist_SP);
        academic_sp = findViewById(R.id.academiclist_SP);
        cluster_sp = findViewById(R.id.cluster_SP);
        institute_sp = findViewById(R.id.institutelist_SP);
        level_sp = findViewById(R.id.levellist_SP);

        state_new_SP = findViewById(R.id.state_new_SP);
        district_new_SP = findViewById(R.id.district_new_SP);
        taluk_new_SP = findViewById(R.id.taluk_new_SP);
        village_new_SP = findViewById(R.id.village_new_SP);
        Studentaddress_ET = (EditText) findViewById(R.id.Studentaddress_ET);

//        gender_sp = (Spinner) findViewById(R.id.gender_SP);
        gender_radiogroup = findViewById(R.id.gender_radiogroup_new);
        rb_male = findViewById(R.id.male_RB_new);
        rb_female = findViewById(R.id.female_RB_new);
        markslabel_tv = findViewById(R.id.markslabel_TV);
        edu_sp = findViewById(R.id.education_Spinner);
        StudentStatus_sp = findViewById(R.id.studentstatus_sp);
        birthdate_TV = findViewById(R.id.tv_birthDate);
        admissionfee_et = findViewById(R.id.admissionfees_TV);
        paymentdate_tv = findViewById(R.id.PaymentDate_TV);
        paymentmode_sp = findViewById(R.id.paymentmode_Spinner);
        admission_ll = (LinearLayout) findViewById(R.id.admission_LL);
        spinnerslayout_ll = findViewById(R.id.spinnerslayout_LL);
        mainRegistration_ll = findViewById(R.id.mainRegistration_LL);
        school_sp = findViewById(R.id.school_SP);
        photo_iv = findViewById(R.id.photo_IV);
        uploadimg_Btn = findViewById(R.id.UploadImgBtn);
        maxfees_tv = findViewById(R.id.maxfees_TV);
        receipt_no_et = findViewById(R.id.receipt_ET);
        learnoption_Spinner = findViewById(R.id.learnoption_Spinner);

        @SuppressLint("ResourceType")
        Animation animation_maillayout = AnimationUtils.loadAnimation(this, R.anim.rotate_in);
        animation_maillayout.setDuration(1000);
        mainRegistration_ll.setAnimation(animation_maillayout);
        mainRegistration_ll.animate();
        animation_maillayout.start();


        if (paymentdate_tv.getVisibility() == View.VISIBLE) {
            setcurrentdate();
        }
        /*if(isInternetPresent){
            AsyncCallWS_learningMode task1 = new AsyncCallWS_learningMode(Activity_Register_New.this);
            task1.execute();
        }*/

        gender_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                radiogroupIndex = gender_radiogroup.indexOfChild(findViewById(checkedId));

                //Method 2 For Getting Index of RadioButton
                //  	pos1=gender_radiogroup.indexOfChild(findViewById(gender_radiogroup.getCheckedRadioButtonId()));


                switch (radiogroupIndex) {
                    case 0:
                        gender = "Boy";
                        break;
                    case 1:
                        gender = "Girl";
                        break;

                }


            }
        });//end of radiogroup


        birthdate_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogFragment fromdateFragment = new DatePickerFragmentBirthDate();
//                fromdateFragment.show(getFragmentManager(), "Date Picker");


                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                final DatePickerDialog datePickerDialog_birthdate = new DatePickerDialog(Activity_Register_New.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar cal = new GregorianCalendar(i, i1, i2);


                        DatePickerDialog dialog = new DatePickerDialog(Activity_Register_New.this, this, i, i1, i2);
                        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                        setBirthDate(cal);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog_birthdate.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog_birthdate.show();

            }
        });


        uploadimg_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


//        ArrayAdapter dataAdapter_edu = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, educationArray);
//        dataAdapter_edu.setDropDownViewResource(R.layout.spinnercenterstyle);
//        edu_sp.setAdapter(dataAdapter_edu);


//        ArrayAdapter dataAdapter_status = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray);
//        dataAdapter_status.setDropDownViewResource(R.layout.spinnercenterstyle);
//        StudentStatus_sp.setAdapter(dataAdapter_status);


        paymentdate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogFragment fromdateFragment = new DatePickerFragmentPaymentDate();
//                fromdateFragment.show(getFragmentManager(), "Date Picker");

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                final DatePickerDialog datePickerDialog_paymentdate = new DatePickerDialog(Activity_Register_New.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar cal = new GregorianCalendar(i, i1, i2);


                        DatePickerDialog dialog = new DatePickerDialog(Activity_Register_New.this, this, i, i1, i2);
                        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                        setPaymentDate(cal);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog_paymentdate.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog_paymentdate.show();


            }
        });

        // }
        paymentmode_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                selected_paymentmode = paymentmode_sp.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sandbox_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {

                Obj_Class_sandboxDetails = (Sandbox) sandbox_sp.getSelectedItem();
                sp_strsand_ID = Obj_Class_sandboxDetails.getSandboxID();
                selected_sandboxName = sandbox_sp.getSelectedItem().toString();
                Log.i("selected_sandboxName", " : " + selected_sandboxName);
                Update_sandboxid_toyearspinner(sp_strsand_ID);
                //Update_ids_studentlist_listview(sp_straca_ID,sp_strsand_ID, sp_strClust_ID, sp_strInst_ID,sp_strLev_ID,selected_studentstatus,selected_fees_filter);

                int sel_sandboxsp_new = sandbox_sp.getSelectedItemPosition();
                if (sel_sandboxsp_new != sel_sandboxsp) {
                    sel_sandboxsp = sel_sandboxsp_new;
                    //  ViewStudentList_arraylist.clear();
                    // studentListViewAdapter.notifyDataSetChanged();
                    academic_sp.setSelection(0);
                    cluster_sp.setSelection(0);
                    institute_sp.setSelection(0);
                    school_sp.setSelection(0);
                    level_sp.setSelection(0);
                    StudentStatus_sp.setSelection(0);
                }


//                if(selected_levelAdmissionFee.equals("")){
//                    Log.e("enter","selected_levelAdmissionFee");
//
//                    ArrayAdapter dataAdapter_status1 = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray_admission);
//                    dataAdapter_status1.setDropDownViewResource(R.layout.spinnercenterstyle);
//                    StudentStatus_sp.setAdapter(dataAdapter_status1);
//                    admission_ll.setVisibility(View.GONE);
//                    paymentdate_tv.setVisibility(View.GONE);
//
//                }else {
//                    Log.e("fee_create..sand",selected_levelAdmissionFee);
//
//                    admissionfee_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", selected_levelAdmissionFee)});
//                    maxfees_tv.setText(getResources().getString(R.string.Rs) + "" + selected_levelAdmissionFee);
//                    admissionfee_et.setText(selected_levelAdmissionFee);
//
//                    if (selected_levelAdmissionFee.equals("0")) {
//                        ArrayAdapter dataAdapter_status1 = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray_admission);
//                        dataAdapter_status1.setDropDownViewResource(R.layout.spinnercenterstyle);
//                        StudentStatus_sp.setAdapter(dataAdapter_status1);
//                        admission_ll.setVisibility(View.GONE);
//                        paymentdate_tv.setVisibility(View.GONE);
//
//                    } else {
//                        ArrayAdapter dataAdapter_status2 = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray);
//                        dataAdapter_status2.setDropDownViewResource(R.layout.spinnercenterstyle);
//                        StudentStatus_sp.setAdapter(dataAdapter_status2);
//                        admission_ll.setVisibility(View.VISIBLE);
//                        paymentdate_tv.setVisibility(View.VISIBLE);
//
//                    }
//                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        academic_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {

                Obj_Class_yearDetails = (Year) academic_sp.getSelectedItem();
                sp_straca_ID = Obj_Class_yearDetails.getAcademicID();
                selected_academicname = academic_sp.getSelectedItem().toString();

                Update_clusterid_spinner(sp_strsand_ID, sp_straca_ID);
                //  Update_ids_studentlist_listview(sp_straca_ID,sp_strsand_ID, sp_strClust_ID, sp_strInst_ID,sp_strLev_ID,selected_studentstatus,selected_fees_filter);

                int sel_yearsp_new = academic_sp.getSelectedItemPosition();
                if (sel_yearsp_new != sel_yearsp) {
                    sel_yearsp = sel_yearsp_new;
                    // ViewStudentList_arraylist.clear();
                    //studentListViewAdapter.notifyDataSetChanged();
                    // sandboxlist_student_SP.setSelection(0);
                    cluster_sp.setSelection(0);
                    institute_sp.setSelection(0);
                    school_sp.setSelection(0);
                    level_sp.setSelection(0);
                    StudentStatus_sp.setSelection(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cluster_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {


                Obj_Class_ClusterDetails = (Cluster) cluster_sp.getSelectedItem();
                sp_strClust_ID = Obj_Class_ClusterDetails.getClusterID();
                selected_clusterName = cluster_sp.getSelectedItem().toString();
                Log.i("selected_clustername", " : " + selected_clusterName);
                // Log.e("sp_strClust_ID.." ,sp_strClust_ID);


                Update_InstId_spinner(sp_strClust_ID, sp_straca_ID);
                // Update_ids_studentlist_listview(sp_straca_ID,sp_strsand_ID, sp_strClust_ID, sp_strInst_ID,sp_strLev_ID,selected_studentstatus,selected_fees_filter);


                int sel_clustersp_new = cluster_sp.getSelectedItemPosition();
                if (sel_clustersp_new != sel_clustersp) {
                    sel_clustersp = sel_clustersp_new;
                    // ViewStudentList_arraylist.clear();
                    // studentListViewAdapter.notifyDataSetChanged();
                    // yearlist_SP.setSelection(0);
                    //  cluster_sp.setSelection(0);
                    institute_sp.setSelection(0);
                    school_sp.setSelection(0);
                    level_sp.setSelection(0);
                    StudentStatus_sp.setSelection(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        institute_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {

                Obj_Class_InstDetails = (Institute) institute_sp.getSelectedItem();
                sp_strInst_ID = Obj_Class_InstDetails.getInstituteID();
                selected_instituteName = institute_sp.getSelectedItem().toString();
                Log.i("selected_instituteName", " : " + selected_instituteName);

//                Log.e("sp_strsand_ID.." ,sp_strsand_ID);
//                Log.e("sp_straca_ID.." ,sp_straca_ID);
//                Log.e("sp_strClust_ID.." ,sp_strClust_ID);
//                Log.e("sp_strInst_ID.." ,sp_strInst_ID);
                Update_SchoolId_spinner(sp_strInst_ID);
                Update_LevelId_spinner(sp_straca_ID, sp_strClust_ID, sp_strInst_ID);
                int sel_institute_new = institute_sp.getSelectedItemPosition();
                if (sel_institute_new != sel_institute) {
                    sel_institute = sel_institute_new;
                    school_sp.setSelection(0);
                    level_sp.setSelection(0);
                    StudentStatus_sp.setSelection(0);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        school_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                Obj_Class_SchoolDetails = (School) school_sp.getSelectedItem();
                sp_strschool_ID = Obj_Class_SchoolDetails.getSchoolID();
                selected_schoolName = school_sp.getSelectedItem().toString();
                Log.i("selected_schoolname", " : " + selected_schoolName);
                Update_LevelId_spinner(sp_straca_ID, sp_strClust_ID, sp_strInst_ID);
                int sel_school_new = school_sp.getSelectedItemPosition();
                if (sel_school_new != sel_school) {
                    sel_school = sel_school_new;
                    level_sp.setSelection(0);
                    StudentStatus_sp.setSelection(0);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        level_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {

                Obj_Class_LevelDetails = (Level) level_sp.getSelectedItem();
                sp_strLev_ID = Obj_Class_LevelDetails.getLevelID();
                selected_levelAdmissionFee = Obj_Class_LevelDetails.getAdmissionFee();
                selected_levelName = level_sp.getSelectedItem().toString();
                Log.i("selectedAdmissionFee", " : " + selected_levelAdmissionFee);


                int sel_level_new = level_sp.getSelectedItemPosition();
                if (sel_level_new != sel_levelsp) {
                    sel_levelsp = sel_level_new;
                    StudentStatus_sp.setSelection(0);
                }
//                if (selected_studentstatus.equals("Applicant")) {
//                    Log.e("enter", "select=applicant");
//                    ArrayAdapter dataAdapter_status2 = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray);
//                    dataAdapter_status2.setDropDownViewResource(R.layout.spinnercenterstyle);
//                    StudentStatus_sp.setAdapter(dataAdapter_status2);
//
//                    admission_ll.setVisibility(View.GONE);
//                    paymentdate_tv.setVisibility(View.GONE);
//
//                } else {

                    if (selected_levelAdmissionFee.equals("0")) {
                        Log.e("enter", "selected_levelAdmissionFee=0");
                        ArrayAdapter dataAdapter_status = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray_admission);
                        dataAdapter_status.setDropDownViewResource(R.layout.spinnercenterstyle);
                        StudentStatus_sp.setAdapter(dataAdapter_status);
                        admission_ll.setVisibility(View.GONE);
                        paymentdate_tv.setVisibility(View.GONE);

                    } else {
                        Log.e("fee_create-level", selected_levelAdmissionFee);

                        admissionfee_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", selected_levelAdmissionFee)});
                        maxfees_tv.setText(getResources().getString(R.string.Rs) + "" + selected_levelAdmissionFee);
                        admissionfee_et.setText(selected_levelAdmissionFee);

                        ArrayAdapter dataAdapter_status = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray);
                        dataAdapter_status.setDropDownViewResource(R.layout.spinnercenterstyle);
                        StudentStatus_sp.setAdapter(dataAdapter_status);
                        admission_ll.setVisibility(View.VISIBLE);
                        paymentdate_tv.setVisibility(View.VISIBLE);

                    }

              //  }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        learnoption_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                obj_Class_LearningOption = (LearningMode) learnoption_Spinner.getSelectedItem();
                selected_learnOption = obj_Class_LearningOption.getLearningMode_Name();
                sp_strLearningmode_ID = obj_Class_LearningOption.getLearningMode_ID();

                Log.e("tag", "selected_learnOption=" + selected_learnOption);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        edu_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                obj_Class_education = (Education) edu_sp.getSelectedItem();
                selected_edu = obj_Class_education.getEducation_Name();
                sp_strEdu_ID = obj_Class_education.getEducation_ID();

                // selected_edu = edu_sp.getSelectedItem().toString();
                Log.i("selected_edu", " : " + selected_edu);
                if (selected_edu.equals("Select") || (selected_edu.equals("4th Standard"))) {
                    marks_et.setVisibility(View.GONE);
                    markslabel_tv.setVisibility(View.GONE);
                } else {

                    marks_et.setVisibility(View.VISIBLE);
                    markslabel_tv.setVisibility(View.VISIBLE);

                    if (selected_edu.equals("5th Standard")) {
                        // marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
                        markslabel_tv.setText("4th Standard" + " Marks/Grade");
                    }
                    if (selected_edu.equals("6th Standard")) {
                        //marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
                        markslabel_tv.setText("5th Standard" + " Marks/Grade");
                    }
                    if (selected_edu.equals("7th Standard")) {
                        //marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
                        markslabel_tv.setText("6th Standard" + " Marks/Grade");
                    }
                    if (selected_edu.equals("8th Standard")) {
                        // marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
                        markslabel_tv.setText("7th Standard" + " Marks/Grade");
                    }
                    if (selected_edu.equals("9th Standard")) {
                        //  marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
                        markslabel_tv.setText("8th Standard" + " Marks/Grade");
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

        ArrayAdapter dataAdapter_status2 = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray);
        dataAdapter_status2.setDropDownViewResource(R.layout.spinnercenterstyle);
        StudentStatus_sp.setAdapter(dataAdapter_status2);

        StudentStatus_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                selected_studentstatus = StudentStatus_sp.getSelectedItem().toString();
                Log.i("selected_studentstatus", " : " + selected_studentstatus);

                if (selected_levelAdmissionFee.equals("")) {
                        Log.e("enter", "selected_levelAdmissionFee=null");
                        ArrayAdapter dataAdapter_status1 = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray);
                        dataAdapter_status1.setDropDownViewResource(R.layout.spinnercenterstyle);
                        StudentStatus_sp.setAdapter(dataAdapter_status1);
                        admission_ll.setVisibility(View.GONE);
                        paymentdate_tv.setVisibility(View.GONE);

                    } else if (selected_levelAdmissionFee.equals("0")) {
                        Log.e("enter", "selected_levelAdmissionFee=0");
                        ArrayAdapter dataAdapter_status1 = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray_admission);
                        dataAdapter_status1.setDropDownViewResource(R.layout.spinnercenterstyle);
                        StudentStatus_sp.setAdapter(dataAdapter_status1);
                        admission_ll.setVisibility(View.GONE);
                        paymentdate_tv.setVisibility(View.GONE);

                    } else {
                        if (selected_studentstatus.equals("Admission")) {
                            admission_ll.setVisibility(View.VISIBLE);
                            paymentmode_sp.setVisibility(View.VISIBLE);
                            paymentdate_tv.setVisibility(View.VISIBLE);
                            ArrayAdapter dataAdapter_paymentmode = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, paymentArray);
                            dataAdapter_paymentmode.setDropDownViewResource(R.layout.spinnercenterstyle);
                            paymentmode_sp.setAdapter(dataAdapter_paymentmode);
                            Log.e("fee_create--1..", selected_levelAdmissionFee);

                            admissionfee_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", selected_levelAdmissionFee)});
                            maxfees_tv.setText(getResources().getString(R.string.Rs) + "" + selected_levelAdmissionFee);
                            admissionfee_et.setText(selected_levelAdmissionFee);


                        } else {
                            admission_ll.setVisibility(View.GONE);
                            paymentmode_sp.setVisibility(View.GONE);
                            paymentdate_tv.setVisibility(View.GONE);

                        }


                    }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        uploadfromDB_Sandboxlist();
        uploadfromDB_Yearlist();
        uploadfromDB_Clusterlist();
        uploadfromDB_Institutelist();
        uploadfromDB_Schoollist();
        uploadfromDB_Levellist();
        uploadfromDB_LearningOptionlist();
        uploadfromDB_Educationlist();
        uploadfromDB_Statelist();
        uploadfromDB_Districtlist();
        uploadfromDB_Taluklist();
        uploadfromDB_Villagelist();



            if (selected_levelAdmissionFee.equals("")) {
                Log.e("enter", "selected_levelAdmissionFee=NULL");
                ArrayAdapter dataAdapter_status1 = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray);
                dataAdapter_status1.setDropDownViewResource(R.layout.spinnercenterstyle);
                StudentStatus_sp.setAdapter(dataAdapter_status1);
                admission_ll.setVisibility(View.GONE);
                paymentdate_tv.setVisibility(View.GONE);


            } else if (selected_levelAdmissionFee.equals("0")) {
                Log.e("enter", "selected_levelAdmissionFee=0");
                ArrayAdapter dataAdapter_status1 = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray_admission);
                dataAdapter_status1.setDropDownViewResource(R.layout.spinnercenterstyle);
                StudentStatus_sp.setAdapter(dataAdapter_status1);
                admission_ll.setVisibility(View.GONE);
                paymentdate_tv.setVisibility(View.GONE);

            } else {
                Log.e("fee_create", selected_levelAdmissionFee);
                admissionfee_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", selected_levelAdmissionFee)});
                maxfees_tv.setText(getResources().getString(R.string.Rs) + "" + selected_levelAdmissionFee);
                admissionfee_et.setText(selected_levelAdmissionFee);

                ArrayAdapter dataAdapter_status21 = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray);
                dataAdapter_status21.setDropDownViewResource(R.layout.spinnercenterstyle);
                StudentStatus_sp.setAdapter(dataAdapter_status21);
                admission_ll.setVisibility(View.VISIBLE);
                paymentdate_tv.setVisibility(View.VISIBLE);

            }




    }//oncreate

    public void uploadfromDB_Sandboxlist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS SandboxListRest(SandboxName VARCHAR,SandboxID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM SandboxListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor sandboxcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_sandboxDetails2 = new Sandbox[x];
        if (cursor1.moveToFirst()) {

            do {
                Sandbox innerObj_Class_SandboxList = new Sandbox();
                innerObj_Class_SandboxList.setSandboxID(cursor1.getString(cursor1.getColumnIndex("SandboxID")));
                innerObj_Class_SandboxList.setSandboxName(cursor1.getString(cursor1.getColumnIndex("SandboxName")));

                Log.e("cursor SandboxID", cursor1.getString(cursor1.getColumnIndex("SandboxID")));

                arrayObj_Class_sandboxDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter<Sandbox> dataAdapter = new ArrayAdapter<Sandbox>(Activity_Register_New.this, R.layout.spinnercenterstyle, arrayObj_Class_sandboxDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            sandbox_sp.setAdapter(dataAdapter);
            if (x > sel_sandboxsp) {
                sandbox_sp.setSelection(sel_sandboxsp);
            }
        }

    }

    public void uploadfromDB_Yearlist() {

        SQLiteDatabase db_year = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_year.execSQL("CREATE TABLE IF NOT EXISTS YearListRest(YearID VARCHAR,YearName VARCHAR,Sandbox_ID VARCHAR);");
        @SuppressLint("Recycle")
        Cursor cursor = db_year.rawQuery("SELECT DISTINCT * FROM YearListRest", null);
        int x = cursor.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_yearDetails2 = new Year[x];

        if (cursor.moveToFirst()) {

            do {
                Year innerObj_Class_yearList = new Year();
                innerObj_Class_yearList.setAcademicID(cursor.getString(cursor.getColumnIndex("YearID")));
                innerObj_Class_yearList.setAcademicName(cursor.getString(cursor.getColumnIndex("YearName")));
                innerObj_Class_yearList.setSandbox_ID(cursor.getString(cursor.getColumnIndex("Sandbox_ID")));

                arrayObj_Class_yearDetails2[i] = innerObj_Class_yearList;
                Log.e("tag", "innerObj_Class_yearList" + innerObj_Class_yearList);

                Log.e("tag", "arrayObj_Class_yearDetails2" + arrayObj_Class_yearDetails2[i]);
                i++;

            } while (cursor.moveToNext());


        }//if ends

        db_year.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_yearDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            academic_sp.setAdapter(dataAdapter);
            if (x > sel_yearsp) {
                academic_sp.setSelection(sel_yearsp);
            }
        }

    }

    public void uploadfromDB_Clusterlist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ClusterListRest(ClusterName VARCHAR,ClusterID VARCHAR,Clust_AcademicID VARCHAR, Clust_SandboxID VARCHAR);");
        @SuppressLint("Recycle")
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ClusterListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor Clustercount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_clusterDetails2 = new Cluster[x];
        if (cursor1.moveToFirst()) {

            do {
                Cluster innerObj_Class_SandboxList = new Cluster();
                innerObj_Class_SandboxList.setClusterID(cursor1.getString(cursor1.getColumnIndex("ClusterID")));
                innerObj_Class_SandboxList.setClusterName(cursor1.getString(cursor1.getColumnIndex("ClusterName")));
                innerObj_Class_SandboxList.setAcademicID(cursor1.getString(cursor1.getColumnIndex("Clust_AcademicID")));
                innerObj_Class_SandboxList.setSandboxID(cursor1.getString(cursor1.getColumnIndex("Clust_SandboxID")));


                arrayObj_Class_clusterDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_clusterDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            cluster_sp.setAdapter(dataAdapter);
            if (x > sel_clustersp) {
                cluster_sp.setSelection(sel_clustersp);
            }
        }

    }

    public void uploadfromDB_Institutelist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS InstituteListRest(InstituteName VARCHAR, InstituteID VARCHAR,Inst_AcademicID VARCHAR,Inst_ClusterID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM InstituteListRest", null);

        // Cursor cursor2 = db1.rawQuery("select T.taluka_id,V.village_id,V.village_name from master_state S, master_district D, master_taluka T, master_village V, master_panchayat P where S.state_id=D.state_id AND D.district_id=T.district_id AND T.taluka_id=V.taluk_id AND T.district_id=P.district_id AND (S.state_id in (1,12,25))",null);
        //  Cursor cursor1 = db1.rawQuery("select T.TalukID,T.TalukName,T.Taluk_districtid from DistrictList D, TalukList T where D.DistrictID=T.Taluk_districtid",null);

        int x = cursor1.getCount();
        Log.d("cursor Instcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_instituteDetails2 = new Institute[x];
        if (cursor1.moveToFirst()) {

            do {
                Institute innerObj_Class_SandboxList = new Institute();
                innerObj_Class_SandboxList.setInstituteID(cursor1.getString(cursor1.getColumnIndex("InstituteID")));
                if (cursor1.getString(cursor1.getColumnIndex("InstituteName")).isEmpty()) {
                    innerObj_Class_SandboxList.setInstituteName("Empty In DB");
                } else {
                    innerObj_Class_SandboxList.setInstituteName(cursor1.getString(cursor1.getColumnIndex("InstituteName")));
                }
                innerObj_Class_SandboxList.setAcademicID(cursor1.getString(cursor1.getColumnIndex("Inst_AcademicID")));
                innerObj_Class_SandboxList.setClusterID(cursor1.getString(cursor1.getColumnIndex("Inst_ClusterID")));


                arrayObj_Class_instituteDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_instituteDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            institute_sp.setAdapter(dataAdapter);
            if (x > sel_institute) {
                institute_sp.setSelection(sel_institute);
            }
        }

    }

    public void uploadfromDB_Schoollist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS SchoolListRest(SchoolName VARCHAR, SchoolID VARCHAR, School_InstituteID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM SchoolListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor schoolcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_schoolDetails2 = new School[x];
        if (cursor1.moveToFirst()) {

            do {
                School innerObj_Class_SandboxList = new School();
                innerObj_Class_SandboxList.setSchoolID(cursor1.getString(cursor1.getColumnIndex("SchoolID")));
                innerObj_Class_SandboxList.setSchoolName(cursor1.getString(cursor1.getColumnIndex("SchoolName")));
                innerObj_Class_SandboxList.setInstituteID(cursor1.getString(cursor1.getColumnIndex("School_InstituteID")));

                Log.e("cursor SchoolID", cursor1.getString(cursor1.getColumnIndex("SchoolID")));

                arrayObj_Class_schoolDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter<School> dataAdapter = new ArrayAdapter<School>(Activity_Register_New.this, R.layout.spinnercenterstyle, arrayObj_Class_schoolDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            school_sp.setAdapter(dataAdapter);
            if (x > sel_school) {
                school_sp.setSelection(sel_school);
            }
        }

    }

    public void uploadfromDB_Levellist() {

        SQLiteDatabase db_village = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        // db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db_village.execSQL("CREATE TABLE IF NOT EXISTS LevelListRest(LevelName VARCHAR, LevelID VARCHAR, Level_InstituteID VARCHAR,Level_AcademicID VARCHAR,Level_ClusterID VARCHAR,Level_AdmissionFee VARCHAR);");
        Cursor cursor1 = db_village.rawQuery("SELECT DISTINCT * FROM LevelListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor levelcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_levelDetails2 = new Level[x];
        if (cursor1.moveToFirst()) {

            do {
                Level innerObj_Class_levelList = new Level();
                innerObj_Class_levelList.setLevelName(cursor1.getString(cursor1.getColumnIndex("LevelName")));
                innerObj_Class_levelList.setLevelID(cursor1.getString(cursor1.getColumnIndex("LevelID")));
                innerObj_Class_levelList.setInstituteID(cursor1.getString(cursor1.getColumnIndex("Level_InstituteID")));
                innerObj_Class_levelList.setAcademicID(cursor1.getString(cursor1.getColumnIndex("Level_AcademicID")));
                innerObj_Class_levelList.setClusterID(cursor1.getString(cursor1.getColumnIndex("Level_ClusterID")));
                innerObj_Class_levelList.setAdmissionFee(cursor1.getString(cursor1.getColumnIndex("Level_AdmissionFee")));
                Log.e("cursor fee", cursor1.getString(cursor1.getColumnIndex("Level_AdmissionFee")));

                arrayObj_Class_levelDetails2[i] = innerObj_Class_levelList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_village.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_levelDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            level_sp.setAdapter(dataAdapter);
            if (x > sel_levelsp) {
                level_sp.setSelection(sel_levelsp);
            }
        }


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
            learnoption_Spinner.setAdapter(dataAdapter);
            if (x > sel_learnmode) {
                learnoption_Spinner.setSelection(sel_learnmode);
            }
        }


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
            edu_sp.setAdapter(dataAdapter);
            if (x > sel_edusp) {
                edu_sp.setSelection(sel_edusp);
            }
        }


    }

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
            state_new_SP.setSelection(statepos);
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
            district_new_SP.setSelection(districtpos);
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
            taluk_new_SP.setSelection(talukpos);
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
            village_new_SP.setSelection(villagepos);
            /*if(x>sel_villagesp) {
                villagelist_SP.setSelection(sel_villagesp);
            }*/
        }


    }

    public void Update_sandboxid_toyearspinner(String str_sandboxid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS YearListRest(YearID VARCHAR,YearName VARCHAR,Sandbox_ID VARCHAR);");
        //   Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictListRest WHERE Distr_Stateid='" + str_stateid + "'", null);
        Cursor cursor = db1.rawQuery("SELECT DISTINCT * from YearListRest WHERE Sandbox_ID='" + str_sandboxid + "'", null);

        int x = cursor.getCount();
        Log.d("cursor year", Integer.toString(x));

        int i = 0;
        arrayObj_Class_yearDetails2 = new Year[x];
        if (cursor.moveToFirst()) {

            do {

                Year innerObj_Class_yearList = new Year();
                innerObj_Class_yearList.setAcademicID(cursor.getString(cursor.getColumnIndex("YearID")));
                innerObj_Class_yearList.setAcademicName(cursor.getString(cursor.getColumnIndex("YearName")));
                innerObj_Class_yearList.setSandbox_ID(cursor.getString(cursor.getColumnIndex("Sandbox_ID")));

                arrayObj_Class_yearDetails2[i] = innerObj_Class_yearList;
                Log.e("tag", "innerObj_Class_yearList" + innerObj_Class_yearList);

                Log.e("tag", "arrayObj_Class_yearDetails2" + arrayObj_Class_yearDetails2[i]);
                i++;


            } while (cursor.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_yearDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            academic_sp.setAdapter(dataAdapter);
            if (x > sel_clustersp) {
                academic_sp.setSelection(sel_yearsp);
            }
        }

    }

    public void Update_clusterid_spinner(String str_sandboxid, String str_academicid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ClusterListRest(ClusterName VARCHAR,ClusterID VARCHAR,Clust_AcademicID VARCHAR, Clust_SandboxID VARCHAR);");
        //   Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictListRest WHERE Distr_Stateid='" + str_stateid + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * from ClusterListRest WHERE Clust_AcademicID='" + str_academicid + "'AND Clust_SandboxID='" + str_sandboxid + "'", null);

        int x = cursor1.getCount();
        Log.d("cursor Clustercount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_clusterDetails2 = new Cluster[x];
        if (cursor1.moveToFirst()) {

            do {

                Cluster innerObj_Class_clusterList = new Cluster();
                innerObj_Class_clusterList.setClusterID(cursor1.getString(cursor1.getColumnIndex("ClusterID")));
                innerObj_Class_clusterList.setClusterName(cursor1.getString(cursor1.getColumnIndex("ClusterName")));
                innerObj_Class_clusterList.setAcademicID(cursor1.getString(cursor1.getColumnIndex("Clust_AcademicID")));
                innerObj_Class_clusterList.setSandboxID(cursor1.getString(cursor1.getColumnIndex("Clust_SandboxID")));

                arrayObj_Class_clusterDetails2[i] = innerObj_Class_clusterList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_clusterDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            cluster_sp.setAdapter(dataAdapter);
            if (x > sel_clustersp) {
                cluster_sp.setSelection(sel_clustersp);
            }
        }

    }

    public void Update_InstId_spinner(String str_clustid, String str_academicid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS InstituteListRest(InstituteName VARCHAR, InstituteID VARCHAR,Inst_AcademicID VARCHAR,Inst_ClusterID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM InstituteListRest WHERE Inst_ClusterID='" + str_clustid + "' AND Inst_AcademicID='" + str_academicid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Instcount", Integer.toString(x));

        int i = 0;

        arrayObj_Class_instituteDetails2 = new Institute[x];


        if (x > 0) {
            if (cursor1.moveToFirst()) {

                do {
                    Institute innerObj_Class_instituteList = new Institute();
                    innerObj_Class_instituteList.setInstituteID(cursor1.getString(cursor1.getColumnIndex("InstituteID")));
                    innerObj_Class_instituteList.setInstituteName(cursor1.getString(cursor1.getColumnIndex("InstituteName")));
                    innerObj_Class_instituteList.setAcademicID(cursor1.getString(cursor1.getColumnIndex("Inst_AcademicID")));
                    innerObj_Class_instituteList.setClusterID(cursor1.getString(cursor1.getColumnIndex("Inst_ClusterID")));


                    arrayObj_Class_instituteDetails2[i] = innerObj_Class_instituteList;
                    //Log.e("taluk_name",cursor1.getString(cursor1.getColumnIndex("TalukName")));
                    i++;
                } while (cursor1.moveToNext());
            }//if ends

        }
        db1.close();


        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_instituteDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            institute_sp.setAdapter(dataAdapter);
            if (x > sel_institute) {
                institute_sp.setSelection(sel_institute);
            }
        }

    }

    public void Update_SchoolId_spinner(String str_instid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        //  db1.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db1.execSQL("CREATE TABLE IF NOT EXISTS SchoolListRest(SchoolName VARCHAR, SchoolID VARCHAR, School_InstituteID VARCHAR);");
        //Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageList WHERE TalukID='" + str_talukid + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM SchoolListRest WHERE School_InstituteID='" + str_instid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor schoolcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_schoolDetails2 = new School[x];
        if (cursor1.moveToFirst()) {

            do {
                School innerObj_Class_SandboxList = new School();
                innerObj_Class_SandboxList.setSchoolID(cursor1.getString(cursor1.getColumnIndex("SchoolID")));
                innerObj_Class_SandboxList.setSchoolName(cursor1.getString(cursor1.getColumnIndex("SchoolName")));
                innerObj_Class_SandboxList.setInstituteID(cursor1.getString(cursor1.getColumnIndex("School_InstituteID")));

                Log.e("cursor SchoolID", cursor1.getString(cursor1.getColumnIndex("SchoolID")));

                arrayObj_Class_schoolDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter<School> dataAdapter = new ArrayAdapter<School>(Activity_Register_New.this, R.layout.spinnercenterstyle, arrayObj_Class_schoolDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            school_sp.setAdapter(dataAdapter);
            if (x > sel_school) {
                school_sp.setSelection(sel_school);
            }


        }


    }

    public void Update_LevelId_spinner(String str_academicID, String str_clustid, String str_instid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        //  db1.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db1.execSQL("CREATE TABLE IF NOT EXISTS LevelListRest(LevelName VARCHAR, LevelID VARCHAR, Level_InstituteID VARCHAR,Level_AcademicID VARCHAR,Level_ClusterID VARCHAR,Level_AdmissionFee VARCHAR);");
        //Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageList WHERE TalukID='" + str_talukid + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM LevelListRest WHERE Level_AcademicID='" + str_academicID + "'AND Level_ClusterID='" + str_clustid + "'AND Level_InstituteID='" + str_instid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Levelcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_levelDetails2 = new Level[x];
        if (cursor1.moveToFirst()) {

            do {
                Level innerObj_Class_levelList = new Level();
                innerObj_Class_levelList.setLevelName(cursor1.getString(cursor1.getColumnIndex("LevelName")));
                innerObj_Class_levelList.setLevelID(cursor1.getString(cursor1.getColumnIndex("LevelID")));
                innerObj_Class_levelList.setInstituteID(cursor1.getString(cursor1.getColumnIndex("Level_InstituteID")));
                innerObj_Class_levelList.setAcademicID(cursor1.getString(cursor1.getColumnIndex("Level_AcademicID")));
                innerObj_Class_levelList.setClusterID(cursor1.getString(cursor1.getColumnIndex("Level_ClusterID")));
                innerObj_Class_levelList.setAdmissionFee(cursor1.getString(cursor1.getColumnIndex("Level_AdmissionFee")));

                arrayObj_Class_levelDetails2[i] = innerObj_Class_levelList;
                // Log.e("village_name", cursor1.getString(cursor1.getColumnIndex("TalukName")));
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_levelDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            level_sp.setAdapter(dataAdapter);
            if (x > sel_levelsp) {
                level_sp.setSelection(sel_levelsp);
            }

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
            district_new_SP.setSelection(districtpos);
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
            taluk_new_SP.setSelection(talukpos);
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
                // innerObj_Class_villageList.setPanchayatID(cursor1.getString(cursor1.getColumnIndex("PanchayatID")));

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
            village_new_SP.setSelection(villagepos);
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


    public void Submit_StudentDetails_DB() {


        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);

        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR);");

        String str_stumarks4 = "", str_stumarks5 = "", str_stumarks6 = "", str_stumarks7 = "", str_stumarks8 = "";
//        selected_sandboxID_int = Integer.parseInt(sp_strsand_ID);
//        selected_academicID_int = Integer.parseInt(sp_straca_ID);
//        selected_clusterID_int = Integer.parseInt(sp_strClust_ID);
//        selected_instituteID_int = Integer.parseInt(sp_strInst_ID);
//        selected_levelID_int = Integer.parseInt(sp_strLev_ID);
//        selected_schoolID_int = Integer.parseInt(sp_strschool_ID);
//        str_ForSavingData_studentname = studentname_et.getText().toString();
//        str_ForSavingData_mobileno = mobileno_et.getText().toString();
//        str_ForSavingData_fathername = fathername_et.getText().toString();
//        str_ForSavingData_mothername = mothername_et.getText().toString();
//        str_ForSavingData_aadar = aadar_et.getText().toString();
//        selected_admissionfee = admissionfee_et.getText().toString();
//        str_receipt_no = receipt_no_et.getText().toString();
//        if (marks_et.getVisibility() == View.VISIBLE) {
//
//            str_marks = marks_et.getText().toString();
//        }
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        createddate = df.format(c);
        createdby = "12";


//        int_val_sandboxID = class_farmponddetails_offline_obj.getSandboxID();
//        int_val_academicid = class_farmponddetails_offline_obj.getAcademicID();
//        int_val_clusterid = class_farmponddetails_offline_obj.getClusterID();
//        int_val_instituteid = class_farmponddetails_offline_obj.getInstituteID();
//        int_val_schoolid = class_farmponddetails_offline_obj.getSchoolID();
//        int_val_levelid = class_farmponddetails_offline_obj.getLevelID();
////System.currentTimeMillis()
//
//
//        Date c = Calendar.getInstance().getTime();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        String createddate = df.format(c);
//        //  String createdby = str_stuID;


        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat dateformat_ddmmyyyy = new SimpleDateFormat("ddMMyyyy");
        String str_ddmmyyyy = dateformat_ddmmyyyy.format(c1.getTime());
        Log.e("date", str_ddmmyyyy);
        String str_TemporaryID = "temp" + String.valueOf(System.currentTimeMillis()) + str_ddmmyyyy;
        Log.e("str_TemporaryID", str_TemporaryID);

//        String str_stuname =studentName_edit_et.getText().toString();
//        String str_stufathername = fathername_edit_et.getText().toString();
//        String  str_stumothername = mothername_edit_et.getText().toString();
//        String  str_mobno= mobileno_edit_et.getText().toString();
//        String  stu_aadharno= aadhaarno_edit_et.getText().toString();
//        String str_dob=dateofbirth_edit_tv.getText().toString();
//        String str_gen_new=str_gender;
//        String  str_edu=class_farmponddetails_offline_obj.getEducation();

        //  String  str_stustatus=class_farmponddetails_offline_obj.getStudentStatus();
//        String  str_stumarks4=class_farmponddetails_offline_obj.getMarks4();
        if (selected_edu.equals("4th Standard")) {
            // education__edit_Sp.setSelection(1);
        }
        if (selected_edu.equals("5th Standard")) {
            str_stumarks4 = marks_et.getText().toString();
        }
        if (selected_edu.equals("6th Standard")) {
            str_stumarks5 = marks_et.getText().toString();
        }
        if (selected_edu.equals("7th Standard")) {
            str_stumarks6 = marks_et.getText().toString();
        }
        if (selected_edu.equals("8th Standard")) {
            str_stumarks7 = marks_et.getText().toString();
        }
        if (selected_edu.equals("9th Standard")) {
            str_stumarks8 = marks_et.getText().toString();
        }

        //  String  str_stumarks5=class_farmponddetails_offline_obj.getMarks5();
        // String  str_stumarks6=class_farmponddetails_offline_obj.getMarks6();
        //  String  str_stumarks7=class_farmponddetails_offline_obj.getMarks7();
        //  String  str_stumarks8=class_farmponddetails_offline_obj.getMarks8();
        //  String  str_admissionfee=class_farmponddetails_offline_obj.getAdmissionFee();
        //   String  str_paymentdate=paymentdate_tv.getText().toString();
        //   String  str_receiptno=receipt_no_et.getText().toString();
        // String  str_stuphoto=class_farmponddetails_offline_obj.getStudentPhoto();
        //String  str_stuApplno=class_farmponddetails_offline_obj.getApplicationNo();

//        try {
//            ContentValues cv = new ContentValues();
//            cv.put("SandboxID", "1");
//            cv.put("AcademicID", "2020");//int_val_academicid
//            cv.put("ClusterID", "1");
//            cv.put("InstituteID", "128");
//            cv.put("SchoolID", "129");
//            cv.put("LevelID", "861");
//            cv.put("Stud_TempId", str_TemporaryID);
//            cv.put("StudentName", studentname_et.getText().toString());
//            cv.put("FatherName", fathername_et.getText().toString());
//            cv.put("MotherName", mothername_et.getText().toString());
//            cv.put("Mobile", mobileno_et.getText().toString());//pond_startdate
//            cv.put("StudentAadhar", aadar_et.getText().toString());
//            cv.put("BirthDate", birthdate_TV.getText().toString());
//            cv.put("Gender", gender);//pond_enddate
//            cv.put("Education", str_edu);
//            cv.put("StudentStatus", selected_studentstatus);
//            cv.put("Marks4", str_stumarks4);
//            Log.e("str_stumarks4..adding", str_stumarks4);
//            cv.put("Marks5", str_stumarks5);
//            cv.put("Marks6", str_stumarks6);
//
//
//            cv.put("Marks7", str_stumarks7);
//            cv.put("Marks8",str_stumarks8);
//            cv.put("AdmissionFee", admissionfee_et.getText().toString());
//            ///cv.put("Reading_End", str_paymentdate);
//            cv.put("ReceiptNo", str_receiptno);
//            cv.put("StudentPhoto",str_img);//str_stuphoto
//            cv.put("Base64image",str_img);
//            Log.e("str_imgfile..submiting", str_img);
//            cv.put("ApplicationNo","");
//            cv.put("StudentID",str_TemporaryID);//str_TemporaryID addonly wen new student is added
//            cv.put("CreatedDate",createddate);
//
//
//
//            db1.update("StudentDetailsRest", cv, "StudentID = ?", new String[]{String.valueOf(str_stuID)});
//            db1.close();

        try {

            Log.e("inserting..stateid", sp_strstate_ID);
            Log.e("inserting..stateName", selected_stateName);
            Log.e("inserting..distid", sp_strdistrict_ID);
            Log.e("inserting..DIST", selected_district);
            Log.e("inserting..TALUKid", sp_strTaluk_ID);
            Log.e("inserting..villageid", sp_strVillage_ID);

            String SQLiteQuery = "INSERT INTO StudentDetailsRest (AcademicID,AcademicName,AdmissionFee," +
                    "ApplicationNo,BalanceFee,BirthDate,ClusterID,ClusterName,CreatedDate,Education,FatherName,Gender,InstituteName,InstituteID,LevelID,LevelName,Marks4,Marks5,Marks6,Marks7,Marks8,Mobile,MotherName,PaidFee,ReceiptNo,SandboxID,SandboxName,SchoolID,SchoolName,StudentAadhar,StudentID,StudentName,StudentPhoto,StudentStatus, Base64image, Stud_TempId,UpadateOff_Online,Learning_Mode,stateid,statename,districtid,districtname,talukid,talukname,villageid,villagename,student_address)" +
                    " VALUES ('" + sp_straca_ID + "','" + selected_academicname + "','" + admissionfee_et.getText().toString() + "','" + "" + "','" + "0" + "','" + yyyyMMdd_birthdate + "','" + sp_strClust_ID + "'," +
                    "'" + selected_clusterName + "','" + createddate + "','" + selected_edu + "','" + fathername_et.getText().toString() + "','" + gender + "','" + selected_instituteName + "'," +
                    "'" + sp_strInst_ID + "','" + sp_strLev_ID + "','" + selected_levelName + "','" + marks_et.getText().toString() + "','" + marks_et.getText().toString() + "','" + marks_et.getText().toString() + "'," +
                    "'" + marks_et.getText().toString() + "','" + marks_et.getText().toString() + "','" + mobileno_et.getText().toString() + "','" + mothername_et.getText().toString() + "','" + admissionfee_et.getText().toString() + "','" + receipt_no_et.getText().toString() + "','" + sp_strsand_ID + "','" + selected_sandboxName + "'," +
                    "'" + sp_strschool_ID + "','" + selected_schoolName + "','" + aadar_et.getText().toString() + "','" + str_TemporaryID + "'," +
                    "'" + studentname_et.getText().toString() + "','" + str_img + "','" + selected_studentstatus + "','" + str_img + "','" + str_TemporaryID + "','" + "offline" + "','" + selected_learnOption + "','" + sp_strstate_ID + "','" + selected_stateName + "','" + sp_strdistrict_ID + "','" + selected_district + "','" + sp_strTaluk_ID + "','" + selected_taluk + "','" + sp_strVillage_ID + "','" + selected_village + "','" + Studentaddress_ET.getText().toString() + "');";

            Log.e("admissionfee.", admissionfee_et.getText().toString());

            db1.execSQL(SQLiteQuery);
            db1.close();


            internetDectector = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent) {
                fetch_DB_farmerprofile_offline_data(str_TemporaryID);

                //fetch_DB_edited_offline_data();


            } else {
                Toast.makeText(getApplicationContext(), "Added new data Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Activity_Register_New.this, Activity_ViewStudentList_new.class);
                startActivity(intent);
                finish();
            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "error" + e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    public void fetch_DB_farmerprofile_offline_data(String str_tempid) {
        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR);");
//        Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE Stud_TempId LIKE'"+ "edittemp%" + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE Stud_TempId='" + str_tempid + "'", null);

        int x = cursor1.getCount();
        Log.e("FETCH_studentcount", String.valueOf(x));


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
                    Log.e("Gender---", cursor1.getString(cursor1.getColumnIndex("Gender")));
                    Log.e("fetch_DB_offline_data", cursor1.getString(cursor1.getColumnIndex("StudentID")));
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
        String StudentID = String.valueOf(class_farmerprofileoffline_array_obj[j].getStudentID());
        Log.e("cLASS", "StudentID..ABC " + class_farmerprofileoffline_array_obj[j].getStudentID());

        Log.e("tag", "StudentID==" + StudentID);
        if (StudentID.startsWith("temp")) {
            Log.e("tag", "StudentID temp==" + StudentID);
            StudentID = "0";
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
        //request.setDistrict_Name(class_farmerprofileoffline_array_obj[j].getDistrict_Name());
        request.setTaluk_ID(class_farmerprofileoffline_array_obj[j].getTaluk_ID());
        //request.setTaluk_Name(class_farmerprofileoffline_array_obj[j].getTaluk_Name());
        request.setVillage_ID(class_farmerprofileoffline_array_obj[j].getVillage_ID());
        // request.setVillage_Name(class_farmerprofileoffline_array_obj[j].getVillage_Name());
        request.setAddress(class_farmerprofileoffline_array_obj[j].getAddress());

        if (!class_farmerprofileoffline_array_obj[j].getMarks4().equals("")) {
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks4());

        } else if (!class_farmerprofileoffline_array_obj[j].getMarks5().equals("")) {
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks5());

        } else if (!class_farmerprofileoffline_array_obj[j].getMarks6().equals("")) {
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks6());

        } else if (!class_farmerprofileoffline_array_obj[j].getMarks7().equals("")) {
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks7());

        } else if (!class_farmerprofileoffline_array_obj[j].getMarks8().equals("")) {
            request.setMarks(class_farmerprofileoffline_array_obj[j].getMarks8());

        }
        request.setAdmissionFee(class_farmerprofileoffline_array_obj[j].getAdmissionFee());
        request.setStudentStatus(class_farmerprofileoffline_array_obj[j].getStudentStatus());
//        request.setStudentID(String.valueOf(class_farmerprofileoffline_array_obj[j].getStudentID()));
        request.setStudentID(StudentID);


        request.setSchoolID(String.valueOf(class_farmerprofileoffline_array_obj[j].getSchoolID()));
        request.setCreatedBy(str_loginuserID);
        request.setCreatedDate(class_farmerprofileoffline_array_obj[j].getCreatedDate());
        //   request.setCreatedDate("2020-12-05");
        request.setReceiptManual(class_farmerprofileoffline_array_obj[j].getReceiptNo());
        request.setLearningMode(class_farmerprofileoffline_array_obj[j].getLearningMode());
        //  String str_temp_changed=""+class_farmerprofileoffline_array_obj[j].getTempID();
        request.setTemp_ID(class_farmerprofileoffline_array_obj[j].getTempID());
        request.setFileName(class_farmerprofileoffline_array_obj[j].getStudentPhoto());
//        Log.e("tag", "FarmerFirstName==" + class_farmerprofileoffline_array_obj[j].getStr_fname());
//        Log.e("tag", "FarmerID==" + class_farmerprofileoffline_array_obj[j].getStr_farmerID());
        request.setApplication_Type("SIV");
        Call<AddStudentDetailsResponse> call = userService1.Post_ActionStudent(request);

        Log.e("TAG", "Request 33: " + new Gson().toJson(request));
        Log.e("TAG", "Request: " + request.toString());

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_Register_New.this);
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

                        //String str_updatetabewithnewtempid="newtemp"+""+str_response_tempId;

                        ContentValues cv = new ContentValues();
                        cv.put("StudentID", str_response_student_id);
                        cv.put("Stud_TempId", str_response_tempId);
                        cv.put("UpadateOff_Online", "online");

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
                        ClearEditTextAfterDoneTask();
                        Toast.makeText(getApplicationContext(), "Submitted Successfully", Toast.LENGTH_SHORT).show();

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

                    Toast.makeText(Activity_Register_New.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(Activity_Register_New.this, "WS:Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }


    public void setcurrentdate() {
        //// Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        str_paymentdate = mDay + "-" + (mMonth + 1) + "-" + mYear;
        //paymentdate_tv.setText(str_paymentdate);

        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

        paymentdate_tv.setText(dateFormat.format(c.getTime()));


    }

    public void setPaymentDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

        paymentdate_tv.setText(dateFormat.format(calendar.getTime()));
        str_paymentdate = dateFormat.format(calendar.getTime());


        SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
        yyyyMMdd_paymentdate = mdyFormat.format(calendar.getTime());
        Calendar c = Calendar.getInstance();
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");


    }

    public void setBirthDate(Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);


        birthdate_TV.setText(dateFormat.format(calendar.getTime()));
        str_birthdate = dateFormat.format(calendar.getTime());


        SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
        yyyyMMdd_birthdate = mdyFormat.format(calendar.getTime());
        Calendar c = Calendar.getInstance();
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");


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

            case R.id.share:

                internetDectector = new Class_InternetDectector(getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();

                if (isInternetPresent) {

                    Class_SaveSharedPreference.setUserName(getApplicationContext(), "");

                    LogoutCountAsynctask logoutCountAsynctask = new LogoutCountAsynctask(Activity_Register_New.this);
                    logoutCountAsynctask.execute();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.putExtra("logout_key1", "yes");
                    startActivity(i);
                    finish();

//                    Intent i = new Intent(getApplicationContext(), NormalLogin.class);
//                    i.putExtra("logout_key1", "yes");
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(i);
                    //finish();


                } else {
                    Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
                }
            case R.id.Sync:

                if (isInternetPresent) {
//                    deleteSandBoxTable_B4insertion();
//                    deleteAcademicTable_B4insertion();
//                    deleteClusterTable_B4insertion();
//                    deleteInstituteTable_B4insertion();
//                    deleteSchoolTable_B4insertion();
//                    deleteLevelTable_B4insertion();
//                    deleteLearningOptionTable_B4insertion();
//
//                    getacademiclist_Asynctask();


                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


                break;

            case android.R.id.home:

                Intent i = new Intent(Activity_Register_New.this, Activity_MarketingHomeScreen.class);
                startActivity(i);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

//    public void getacademiclist_Asynctask() {
//        GetAcademiclist_Asynctask task = new GetAcademiclist_Asynctask(Activity_Register_New.this);
//        task.execute();
//        AsyncCallWS_learningMode task1 = new AsyncCallWS_learningMode(Activity_Register_New.this);
//        task1.execute();
//    }
//
//    private class GetAcademiclist_Asynctask extends AsyncTask<String, Void, Void> {
//        ProgressDialog dialog;
//        Context context;
//
//        protected void onPreExecute() {
//            //  Log.i(TAG, "onPreExecute---tab2");
//            dialog.setMessage("Please wait...");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            //Log.i(TAG, "onProgressUpdate---tab2");
//        }
//
//
//        @RequiresApi(api = Build.VERSION_CODES.N)
//        @Override
//        protected Void doInBackground(String... params) {
//            Log.i("df", "doInBackground");
//
//            GetAcademiclist();  // get the Year list
//            return null;
//        }
//
//        public GetAcademiclist_Asynctask(Activity_Register_New activity) {
//            context = activity;
//            dialog = new ProgressDialog(activity, R.style.AppCompatAlertDialogStyle);
//        }
//
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//
//            dialog.dismiss();
//
//            // Update_Year_spinner_4rmDB();
//
//
//            if (academiclistcount <= 0) {
//                Toast.makeText(context, "No items", Toast.LENGTH_SHORT).show();
//            } else {
//
//
//                uploadfromDB_SandBoxlist();
//                uploadfromDB_Academiclist();
//                uploadfromDB_Clusterist();
//                uploadfromDB_InstitutList();
//                uploadfromDB_SchoolList();
//                uploadfromDB_LevelList();
//
//            }
//
//
//        }//end of onPostExecute
//    }// end Async task


//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public void GetAcademiclist() {
//
//
//        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
//        String METHOD_NAME = "LoadAcademicDetails";
//        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/LoadAcademicDetails";
//
//
//        try {
//
//            int userid = Integer.parseInt(str_loginuserID);//str_loginuserID
//            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
//
//            request.addProperty("User_ID", userid);//change static value later
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            //Set output SOAP object
//            envelope.setOutputSoapObject(request);
//            //Create HTTP call object
//            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
//
//            try {
//
//                androidHttpTransport.call(SOAPACTION, envelope);
//
//                SoapObject response = (SoapObject) envelope.getResponse();
//                Log.e("resp academiclist", response.toString());
//                academiclistcount = response.getPropertyCount();
//
//                Log.e("academiclistcount", String.valueOf(response.getPropertyCount()));
//
//
//                // if(academiclistcount>0) {
//                arrayObj_Class_academicDetails = new Class_academicDetails[academiclistcount];
//                arrayObj_Class_clusterDetails = new Class_ClusterDetails[academiclistcount];
//                arrayObj_Class_InstituteDetails = new Class_InsituteDetails[academiclistcount];
//                arrayObj_Class_LevelDetails = new Class_LevelDetails[academiclistcount];
//                arrayObj_Class_sandboxDetails = new Class_SandBoxDetails[academiclistcount];
//                arrayObj_Class_SchoolDetails = new Class_SchoolDetails[academiclistcount];
//
//
//                for (int i = 0; i < academiclistcount; i++) {
//
//                    SoapObject response_soapobj = (SoapObject) response.getProperty(i);
//
//                    SoapPrimitive response_soapobj_academicID, response_soapobj_academic_Name, response_soapobj_clusterid,
//                            response_soapobj_clustername, response_soapobj_instituteID, response_soapobj_instituteName,
//                            response_soapobj_levelID, response_soapobj_levelname, response_soapobj_sandboxid, response_soapobj_sandboxname, response_soapobj_admissionfee, response_soapobj_schoolid, response_soapobj_schoolname;
//                    response_soapobj_academicID = (SoapPrimitive) response_soapobj.getProperty("Academic_ID");
//                    response_soapobj_academic_Name = (SoapPrimitive) response_soapobj.getProperty("Academic_Name");
//                    response_soapobj_clusterid = (SoapPrimitive) response_soapobj.getProperty("Cluster_ID");
//                    response_soapobj_clustername = (SoapPrimitive) response_soapobj.getProperty("Cluster_Name");
//                    response_soapobj_instituteID = (SoapPrimitive) response_soapobj.getProperty("Institute_ID");
//                    response_soapobj_instituteName = (SoapPrimitive) response_soapobj.getProperty("Institute_Name");
//                    response_soapobj_levelID = (SoapPrimitive) response_soapobj.getProperty("Lavel_ID");
//                    response_soapobj_levelname = (SoapPrimitive) response_soapobj.getProperty("Lavel_Name");
//                    response_soapobj_sandboxid = (SoapPrimitive) response_soapobj.getProperty("Sandbox_ID");
//                    response_soapobj_sandboxname = (SoapPrimitive) response_soapobj.getProperty("Sandbox_Name");
//                    response_soapobj_schoolid = (SoapPrimitive) response_soapobj.getProperty("School_ID");
//                    response_soapobj_schoolname = (SoapPrimitive) response_soapobj.getProperty("School_Name");
//                    response_soapobj_admissionfee = (SoapPrimitive) response_soapobj.getProperty("Admission_Fee");
//
//
//                    Class_academicDetails innerObj_Class_academic = new Class_academicDetails();
//                    innerObj_Class_academic.setAcademic_id(response_soapobj_academicID.toString());
//                    innerObj_Class_academic.setAcademic_name(response_soapobj_academic_Name.toString());
//
//                    Class_ClusterDetails innerObj_Class_cluster = new Class_ClusterDetails();
//                    innerObj_Class_cluster.setCluster_id(response_soapobj_clusterid.toString());
//                    innerObj_Class_cluster.setCluster_name(response_soapobj_clustername.toString());
//
//                    Class_InsituteDetails innerObj_Class_institute = new Class_InsituteDetails();
//                    innerObj_Class_institute.setInstitute_id(response_soapobj_instituteID.toString());
//                    innerObj_Class_institute.setInstitute_name(response_soapobj_instituteName.toString());
//
//                    Class_LevelDetails innerObj_Class_level = new Class_LevelDetails();
//                    innerObj_Class_level.setLevel_id(response_soapobj_levelID.toString());
//                    innerObj_Class_level.setLevel_name(response_soapobj_levelname.toString());
//
//                    Class_SandBoxDetails innerObj_Class_sandbox = new Class_SandBoxDetails();
//                    innerObj_Class_sandbox.setSandbox_id(response_soapobj_sandboxid.toString());
//                    innerObj_Class_sandbox.setSandbox_name(response_soapobj_sandboxname.toString());
//
//                    Class_SchoolDetails innerObj_Class_school = new Class_SchoolDetails();
//                    innerObj_Class_school.setSchool_id(response_soapobj_schoolid.toString());
//                    innerObj_Class_school.setSchool_name(response_soapobj_schoolname.toString());
//
//
//                    arrayObj_Class_academicDetails[i] = innerObj_Class_academic;
//                    Log.e("class", arrayObj_Class_academicDetails[i].getAcademic_name());
//                    arrayObj_Class_clusterDetails[i] = innerObj_Class_cluster;
//                    arrayObj_Class_InstituteDetails[i] = innerObj_Class_institute;
//                    arrayObj_Class_LevelDetails[i] = innerObj_Class_level;
//                    arrayObj_Class_sandboxDetails[i] = innerObj_Class_sandbox;
//                    arrayObj_Class_SchoolDetails[i] = innerObj_Class_school;
//
//                    String aca_id = response_soapobj.getProperty("Academic_ID").toString();
//                    String aca_name = response_soapobj.getProperty("Academic_Name").toString();
//
//                    String clust_id = response_soapobj.getProperty("Cluster_ID").toString();
//                    String clust_name = response_soapobj.getProperty("Cluster_Name").toString();
//
//
//                    String inst_id = response_soapobj.getProperty("Institute_ID").toString();
//                    String inst_name = response_soapobj.getProperty("Institute_Name").toString();
//
//                    String level_id = response_soapobj.getProperty("Lavel_ID").toString();
//                    String level_name = response_soapobj.getProperty("Lavel_Name").toString();
//
//                    String sandbox_id = response_soapobj.getProperty("Sandbox_ID").toString();
//                    String sandbox_name = response_soapobj.getProperty("Sandbox_Name").toString();
//
//                    String school_id = response_soapobj.getProperty("School_ID").toString();
//                    String school_name = response_soapobj.getProperty("School_Name").toString();
//
//                    String admissionFee = response_soapobj.getProperty("Admission_Fee").toString();
//                    Log.e("admissionFee", admissionFee);
//
//                    DBCreate_SandBoxdetails_insert_2SQLiteDB(sandbox_id, sandbox_name);
//                    DBCreate_Academicdetails_insert_2SQLiteDB(aca_id, aca_name, sandbox_id);
//                    DBCreate_Clusterdetails_insert_2SQLiteDB(clust_id, clust_name, sandbox_id, aca_id);
//                    DBCreate_Institutedetails_insert_2SQLiteDB(inst_id, inst_name, sandbox_id, aca_id, clust_id);
//                    DBCreate_Schooldetails_insert_2SQLiteDB(school_id, school_name, sandbox_id, aca_id, clust_id);
//                    DBCreate_Leveldetails_insert_2SQLiteDB(level_id, level_name, sandbox_id, aca_id, clust_id, inst_id, admissionFee);
//
//
//                }//end for loop
//
//
//                // }//end of if
//
//
//            } catch (Throwable t) {
//
//                Log.e("getCollege fail", "> " + t.getMessage());
//                internet_issue = "slow internet";
//            }
//        } catch (Throwable t) {
//
//            Log.e("UnRegister Error", "> " + t.getMessage());
//        }
//
//    }//End of uploaddetails

//    private class AsyncCallWS_learningMode extends AsyncTask<String, Void, Void> {
//        ProgressDialog dialog;
//
//        Context context;
//
//        protected void onPreExecute() {
//
//            dialog.setMessage("Please wait..");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//        }
//
//        @Override
//        protected Void doInBackground(String... params) {
//            Log.i("list", "doInBackground");
//
//            list_detaile();  // call of details
//            return null;
//        }
//
//        public AsyncCallWS_learningMode(Context context1) {
//            context = context1;
//            dialog = new ProgressDialog(context1,R.style.AppCompatAlertDialogStyle);
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//            if ((dialog != null) && dialog.isShowing()) {
//                dialog.dismiss();
//
//                /*ArrayAdapter dataAdapter_learnop = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, Arrayclass_learningOption);
//                dataAdapter_learnop.setDropDownViewResource(R.layout.spinnercenterstyle);
//                learnoption_Spinner.setAdapter(dataAdapter_learnop);*/
//                uploadfromDB_LearningOptionlist();
//            }
//
//        }//end of onPostExecute
//    }// end Async task
//
//    public void list_detaile() {
//        Vector<SoapObject> result1 = null;
//
//        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
//        String METHOD_NAME = "Learning_Mode_Options";
//        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/Learning_Mode_Options";
//
//        try {
//            //   int userid = Integer.parseInt(str_loginuserID);
//            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
//            // request.addProperty("User_ID", userid);//userid
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            //Set output SOAP object
//            envelope.setOutputSoapObject(request);
//            //Create HTTP call object
//            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
//
//            try {
//
//                androidHttpTransport.call(SOAPACTION, envelope);
//
//                SoapObject response = (SoapObject) envelope.getResponse();
//                Log.i("value at response", response.toString());
//                int count1 = response.getPropertyCount();  // number of count in array in response 6,0-5,5
//
//                Log.i("number of rows", "" + count1);
//                Arrayclass_learningOption = new Class_LearningOption[count1];
//
//                for (int i = 0; i < count1; i++) {
//                    SoapObject obj2 =(SoapObject) response.getProperty(i);
//
//                    SoapObject list = (SoapObject) response.getProperty(i);
//                    SoapPrimitive response_soapobj_Option_ID,response_soapobj_Group_Name, response_soapobj_Option_Name,response_soapobj_Option_Status;
//
//
//                    response_soapobj_Option_ID = (SoapPrimitive) obj2.getProperty("Option_ID");
//                    response_soapobj_Group_Name = (SoapPrimitive) obj2.getProperty("Group_Name");
//                    response_soapobj_Option_Name = (SoapPrimitive) obj2.getProperty("Option_Name");
//                    response_soapobj_Option_Status = (SoapPrimitive) obj2.getProperty("Option_Status");
//
//
//                    Class_LearningOption innerObj_Class_learning = new Class_LearningOption();
//                    innerObj_Class_learning.setOption_ID(response_soapobj_Option_ID.toString());
//                    innerObj_Class_learning.setGroup_Name(response_soapobj_Group_Name.toString());
//                    innerObj_Class_learning.setOption_Name(response_soapobj_Option_Name.toString());
//                    innerObj_Class_learning.setOption_Status(response_soapobj_Option_Status.toString());
//
//                    Arrayclass_learningOption[i] = innerObj_Class_learning;
//
//
//                    Log.e("tag","Option_ID="+response_soapobj_Option_ID);
//                    Log.e("tag","Arrayclass_learningOption="+Arrayclass_learningOption[i].getOption_Name().toString());
//                    DBCreate_LearningOption_insert_2SQLiteDB(Arrayclass_learningOption[i].getOption_ID(),Arrayclass_learningOption[i].getGroup_Name(),Arrayclass_learningOption[i].getOption_Name(),Arrayclass_learningOption[i].getOption_Status());
//                }// End of for loop
//
//
//            } catch (Throwable t) {
//
//                Log.e("requestload fail", "> " + t.getMessage());
//            }
//        } catch (Throwable t) {
//            Log.e("UnRegisterload  Error", "> " + t.getMessage());
//
//        }
//
//    }//End of leaveDetail method

    private Bitmap checkRotationFromCamera(Bitmap bitmap, String pathToFile, int rotate) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return rotatedBitmap;
    }


    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        str_img = Base64.encodeToString(b, Base64.DEFAULT);
        Log.e("str_img..", str_img);
//                if (str_img.equals("") || str_img.equals(null)) {
        return str_img;
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


    public void submit_method(View view) {

        File imagefile;

        if (digitalcamerabuttonpressed) {
            imagefile = new File(CameraPhotoCapture.compressedfilepaths);
            Bitmap myBitmap = BitmapFactory.decodeFile(imagefile.getAbsolutePath());


            path = imagefile.getAbsolutePath();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            signimageinbytesArray = stream.toByteArray();
            str_img = Base64.encodeToString(signimageinbytesArray, Base64.DEFAULT);
            // Log.e("byteArray", "byteArray" + Arrays.toString(signimageinbytesArray));
            // Log.e("str_img  " , str_img);
            digitalcamerabuttonpressed = false;

        } else {
            signimageinbytesArray = null;
        }
        if (validation()) {

            Submit_StudentDetails_DB();
        } else {
            Toast.makeText(getApplicationContext(), "Please Enter all the Required data", Toast.LENGTH_LONG).show();

        }
//        if (validation()) {
//
//            selected_sandboxID_int = Integer.parseInt(sp_strsand_ID);
//            selected_academicID_int = Integer.parseInt(sp_straca_ID);
//            selected_clusterID_int = Integer.parseInt(sp_strClust_ID);
//            selected_instituteID_int = Integer.parseInt(sp_strInst_ID);
//            selected_levelID_int = Integer.parseInt(sp_strLev_ID);
//            selected_schoolID_int = Integer.parseInt(sp_strschool_ID);
//            str_ForSavingData_studentname = studentname_et.getText().toString();
//            str_ForSavingData_mobileno = mobileno_et.getText().toString();
//            str_ForSavingData_fathername = fathername_et.getText().toString();
//            str_ForSavingData_mothername = mothername_et.getText().toString();
//            str_ForSavingData_aadar = aadar_et.getText().toString();
//            selected_admissionfee = admissionfee_et.getText().toString();
//            str_receipt_no = receipt_no_et.getText().toString();
//            if (marks_et.getVisibility() == View.VISIBLE) {
//
//                str_marks = marks_et.getText().toString();
//            }
//            Date c = Calendar.getInstance().getTime();
//
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            createddate = df.format(c);
//            createdby = str_loginuserID;
//
//
//            internetDectector = new Class_InternetDectector(getApplicationContext());
//            isInternetPresent = internetDectector.isConnectingToInternet();
//
//            if (isInternetPresent) {
//                SubmitAsyncTask task = new SubmitAsyncTask(Activity_Register_New.this);
//                task.execute();
//            } else {
//                //Toast.makeText(getApplicationContext(), "Data saved locally..", Toast.LENGTH_LONG).show();
//                // Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
//
//                createDatabase();
//                insertIntoDBoffline();
//                ClearEditTextAfterDoneTask();
//                if ((path != null)) {
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
//                        }
//                    }
//
//                }
//
////                if (CameraPhotoCapture.imagepathforupload != null) {
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
////
//
//            }
//        }
    }

    private class SubmitAsyncTask extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;
        Context context;

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(String... params) {


            RegisterResponse = WebserviceRegister.register_new(selected_sandboxID_int,
                    selected_academicID_int, selected_clusterID_int, selected_instituteID_int,
                    selected_levelID_int, selected_schoolID_int, str_img, str_ForSavingData_studentname, gender, yyyyMMdd_birthdate,
                    selected_edu, str_marks, str_ForSavingData_mobileno, str_ForSavingData_fathername, str_ForSavingData_mothername, str_ForSavingData_aadar,
                    selected_studentstatus, selected_admissionfee, createddate, createdby, str_receipt_no, selected_learnOption);

            return null;
        }// doInBackground Process

        public SubmitAsyncTask(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1, R.style.AppCompatAlertDialogStyle);
        }

        @Override
        //Once WebService returns response
        protected void onPostExecute(Void result) {
            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();

            }


            //Make Progress Bar invisible
            // progressbar.setVisibility(View.INVISIBLE);

            if (!RegisterResponse) {


                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

            } else {
                ClearEditTextAfterDoneTask();
                Toast.makeText(getApplicationContext(), "Application Submitted", Toast.LENGTH_SHORT).show();


                if ((path != null)) {
                    File fdelete = new File(path);
                    if (fdelete.exists()) {
                        if (fdelete.delete()) {
                            Log.v("log_tag", "file Deleted =" + path);
                            File dir = new File(Environment.getExternalStorageDirectory() + "GetSignature");
                            File dir1 = new File(Environment.getExternalStorageDirectory() + "DetSkillsSign/Images");
                            if (dir.isDirectory()) {
                                dir.delete();
                            }
                            if (dir1.isDirectory()) {
                                dir1.delete();
                            }
                        } else {
                        }
                    }

                }

                if (CameraPhotoCapture.imagepathforupload != null) {
                    File imagefilepath = new File(CameraPhotoCapture.imagepathforupload);
                    Log.v("log_tag", "imagefilepath=" + imagefilepath);

                    if (imagefilepath.exists()) {
                        if (imagefilepath.delete()) {
                            Log.v("log_tag", "imagefilepath deleted=" + imagefilepath);
                        } else {
                            Log.v("log_tag", "imagefilepath not deleted=" + imagefilepath);
                        }
                    }
                }


            }

        }// End of onPostExecute

        @Override
        //Make Progress Bar visible
        protected void onPreExecute() {


            // progressbar.setVisibility(View.VISIBLE);

            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }//End of onPreExecute

        @Override
        protected void onProgressUpdate(Void... values) {
        }//End of onProgressUpdates
    } // End of AsyncCallWS

    public void ClearEditTextAfterDoneTask() {
        studentname_et.getText().clear();
        fathername_et.getText().clear();
        mobileno_et.getText().clear();
        mothername_et.getText().clear();
        aadar_et.getText().clear();
        if (marks_et.getVisibility() == View.VISIBLE) {

            marks_et.getText().clear();
        }

        photo_iv.setImageResource(R.drawable.profileimg);
        //    photo_iv.setBackgroundResource(R.drawable.uploadimg);

        birthdate_TV.setText("Select BirthDate");

        if (selected_studentstatus.equals("Admission")) {

            paymentdate_tv.setText("Select Payment Date");
            admissionfee_et.getText().clear();
            receipt_no_et.getText().clear();
        }


        edu_sp.setSelection(0);
        learnoption_Spinner.setSelection(0);
        StudentStatus_sp.setSelection(0);
        rb_female.setChecked(false);
        rb_male.setChecked(true);

        if (paymentdate_tv.getVisibility() == View.VISIBLE) {
            setcurrentdate();
        }


    }


    //Birth DATE

    @SuppressLint("ValidFragment")
    public static class DatePickerFragmentBirthDate extends DialogFragment
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


            birthdate_TV.setText(dateFormat.format(calendar.getTime()));
            str_birthdate = dateFormat.format(calendar.getTime());


            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
            yyyyMMdd_birthdate = mdyFormat.format(calendar.getTime());
            Calendar c = Calendar.getInstance();
            DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");


        }

    }

    //Payment Date
    //Birth DATE

    @SuppressLint("ValidFragment")
    public static class DatePickerFragmentPaymentDate extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, month, day, year);
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

            paymentdate_tv.setText(dateFormat.format(calendar.getTime()));
            str_paymentdate = dateFormat.format(calendar.getTime());


            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
            yyyyMMdd_paymentdate = mdyFormat.format(calendar.getTime());
            Calendar c = Calendar.getInstance();
            DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");


        }

    }

    public boolean validation() {


        Boolean bname = true, bedu = true, bbirthdate = true, bcellnumber = true, bphoto = true;
        Boolean brecno = true, bmothername = true, baadar = true, baadar_length = true, bpaymentdate = true, bfee = true, bmarks = true;


        boolean flags = true;
        //For Student Name

        if ((studentname_et.getText().toString().length() == 0) || (studentname_et.getText().toString().length() <= 2)) {
            studentname_et.setError("InValid  Name");
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

        if (mobileno_et.getText().toString().length() == 0 || mobileno_et.getText().toString().length() < 10) {
            mobileno_et.setError("InValid Mobile Number");
            Toast.makeText(getApplicationContext(), "Please Enter correct Mobile Number", Toast.LENGTH_LONG).show();

            // flags=false;
            bcellnumber = false;

        }

        if (str_birthdate.length() == 0) {
            // if(dateofbirth_edit_tv.getText().toString().equals("Select Birth Date")){

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

        if (paymentdate_tv.getVisibility() == View.VISIBLE) {
            if (str_paymentdate.length() == 0) {
                //paymentdate_tv.setError("Empty is not allowed");
                bpaymentdate = false;
                Toast.makeText(getApplicationContext(), "Please select payment date", Toast.LENGTH_LONG).show();

            }

            if (admissionfee_et.getText().toString().length() == 0) {
                bfee = false;
                Toast.makeText(getApplicationContext(), "Please Enter Admission fees", Toast.LENGTH_LONG).show();

            }
//            if (admissionfee_et.getText().toString()>selected_admissionfee) {
//                bfee = false;
//                Toast.makeText(getApplicationContext(), "Please Enter Valid Admission fees", Toast.LENGTH_LONG).show();
//
//            }

            if (receipt_no_et.getText().toString().length() == 0) {
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

        if (paymentdate_tv.getVisibility() == View.VISIBLE) {
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


    public class LoadImagesFromSDCard2 extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(Activity_Register_New.this, R.style.AppCompatAlertDialogStyle);

        Bitmap mBitmap;

        protected void onPreExecute() {
            /****** NOTE: You can call UI Element here. *****/

            // Progress Dialog
            Dialog.setMessage(" Loading the image..");
            Dialog.show();
        }


        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            Bitmap bitmap = null;
            Bitmap newBitmap = null;
            Uri uri = null;


            try {

                /**  Uri.withAppendedPath Method Description
                 * Parameters
                 *    baseUri  Uri to append path segment to
                 *    pathSegment  encoded path segment to append
                 * Returns
                 *    a new Uri based on baseUri with the given segment appended to the path
                 */

                uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + urls[0]);
                Log.i("log_tag", "URI" + uri);
                /**************  Decode an input stream into a bitmap. *********/
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

                compressImage_new(imagepathfordeletion_new); // imagepathfordeletion_new is original path of image
                // calling the compressImage_new method
                if (bitmap != null) {

                    /********* Creates a new bitmap, scaled from an existing bitmap. ***********/

                    newBitmap = Bitmap.createScaledBitmap(bitmap, 170, 170, true);

                    bitmap.recycle();

                    if (newBitmap != null) {

                        mBitmap = newBitmap;
                    }
                }
            } catch (IOException e) {
                // Error fetching image, try to recover

                /********* Cancel execution of this task. **********/
                cancel(true);
            }

            return null;
        }


        protected void onPostExecute(Void unused) {

            // NOTE: You can call UI Element here.

            // Close progress dialog
            if (Dialog != null) {
                Dialog.dismiss();
            }

            if (mBitmap != null) {
                // Set Image to ImageView

                //  IMG.setImageBitmap(mBitmap);
                // if(scaledBitmap!=null) {
                photo_iv.setImageBitmap(scaledBitmap);

                //}
            }

        }

    }


    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public String compressImage_new(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        //  Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//       by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//       you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//       max Height and width values of the compressed image is taken as 816x612

         /*float maxHeight = 816.0f;
         float maxWidth = 612.0f;*/


        float maxHeight = 216.0f;
        float maxWidth = 212.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//       width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//       setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//       inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//       this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//           load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//       check the rotation of the image and display it properly
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);

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
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);

            // IMG.setImageBitmap(scaledBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        compressedfilepaths = filename;

        try {
            out = new FileOutputStream(filename);

//           write the compressed bitmap at the destination specified by filename.
            //scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

            scaledBitmap.compress(Bitmap.CompressFormat.PNG, 80, out);
            BitMapToString(scaledBitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "DetSkillsSign/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        // String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        String uriSting = (file.getAbsolutePath() + "/" + "f" + System.currentTimeMillis() + ".png");
        return uriSting;

    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

//    public static String convertImageUriToFile(Uri imageUri, Activity activity) {
//
//        Cursor cursor = null;
//        int imageID = 0;
//
//        try {
//
//            /*********** Which columns values want to get *******/
//            String[] proj = {
//                    MediaStore.Images.Media.DATA,
//                    MediaStore.Images.Media._ID,
//                    MediaStore.Images.Thumbnails._ID,
//                    MediaStore.Images.ImageColumns.ORIENTATION
//            };
//
//            cursor = activity.managedQuery(
//
//                    imageUri,         //  Get data for specific image URI
//                    proj,             //  Which columns to return
//                    null,             //  WHERE clause; which rows to return (all rows)
//                    null,             //  WHERE clause selection arguments (none)
//                    null              //  Order-by clause (ascending by name)
//
//            );
//
//            //  Get Query Data
//
//            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
//            int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
//            int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//
//            //int orientation_ColumnIndex = cursor.
//            //    getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);
//
//            int size = cursor.getCount();
//
//            /*******  If size is 0, there are no images on the SD Card. *****/
//
//            if (size == 0) {
//
//
//                //imageDetails.setText("No Image");
//            } else {
//
//                int thumbID = 0;
//                if (cursor.moveToFirst()) {
//
//                    /**************** Captured image details ************/
//
//                    /*****  Used to show image on view in LoadImagesFromSDCard class ******/
//                    imageID = cursor.getInt(columnIndex);
//
//                    thumbID = cursor.getInt(columnIndexThumb);
//
//                    String Path = cursor.getString(file_ColumnIndex);
//
//
//                    imagepathfordeletion_new = Path.toString();
//                    imagepathforupload = Path.toString();
//
//                    Log.v("log_tag", "imagepathforupload" + imagepathforupload);
//                    //String orientation =  cursor.getString(orientation_ColumnIndex);
//
//                    String CapturedImageDetails = " CapturedImageDetails : \n\n"
//                            + " ImageID :" + imageID + "\n"
//                            + " ThumbID :" + thumbID + "\n"
//                            + " Path :" + Path + "\n";
//
//                    // Show Captured Image detail on activity
//                    //imageDetails.setText( CapturedImageDetails );
//
//                }
//            }
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//
//        // Return Captured Image ImageID ( By this ImageID Image will load from sdcard )
//
//        return "" + imageID;
//    }

    public void clearimage() {

        ContentResolver contentResolver = getContentResolver();
        contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.ImageColumns.DATA + "=?", new String[]{imagepathfordeletion_new});

        imagepathforupload = "";
        compressedfilepaths = "";
        Intent i = new Intent(Activity_Register_New.this, Activity_Register_New.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

//        photo_iv.setImageResource(0);
//        photo_iv.setImageBitmap(null);
//        str_img="";
//        bitmap=null;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Activity_Register_New.this, Activity_MarketingHomeScreen.class);
        // i  = new Intent(Activity_Register_New.this, TransformationActivity.class);
        i.putExtra(TRANSFORMATION, CUBE_OUT_DEPTH_TRANSFORMATION);
        startActivity(i);

        //startActivity(i);
        finish();

    }

    /////////////////////////////////////spinner-id update////////////////////////////////////////
    ///onclick of spinner-id update

//    public void Update_academicid_spinner(String str_Sids) {
//
//        SQLiteDatabase db_academicID = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
//        db_academicID.execSQL("CREATE TABLE IF NOT EXISTS AcademicList(AcaID VARCHAR,AcaName VARCHAR,ASandID VARCHAR);");
//        Cursor cursor = db_academicID.rawQuery("SELECT DISTINCT * FROM AcademicList WHERE ASandID='" + str_Sids + "'", null);
//        int x = cursor.getCount();
//        Log.d("cursor Dcount", Integer.toString(x));
//
//        int i = 0;
//        arrayObj_Class_academicDetails2 = new Class_academicDetails[x];
//        if (cursor.moveToFirst()) {
//
//            do {
//                Class_academicDetails innerObj_Class_AcademicList = new Class_academicDetails();
//                innerObj_Class_AcademicList.setAcademic_id(cursor.getString(cursor.getColumnIndex("AcaID")));
//                innerObj_Class_AcademicList.setAcademic_name(cursor.getString(cursor.getColumnIndex("AcaName")));
//                innerObj_Class_AcademicList.setAca_Sandbox_id(cursor.getString(cursor.getColumnIndex("ASandID")));
//
//
//                arrayObj_Class_academicDetails2[i] = innerObj_Class_AcademicList;
//                i++;
//            } while (cursor.moveToNext());
//        }//if ends
//
//
//        db_academicID.close();
//        if (x > 0) {
//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_academicDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            academic_sp.setAdapter(dataAdapter);
//        }
//
//    }
//
//
//    public void Update_clusterrid_spinner(String str_sandboxid, String str_acaid) {
//
//        SQLiteDatabase db_clusterID = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
//        db_clusterID.execSQL("CREATE TABLE IF NOT EXISTS ClusterList(clustID VARCHAR,clustName VARCHAR,clust_sand_ID VARCHAR,clust_aca_ID VARCHAR);");
//        Cursor cursor = db_clusterID.rawQuery("SELECT DISTINCT * FROM ClusterList WHERE clust_sand_ID='" + str_sandboxid + "' AND clust_aca_ID='" + str_acaid + "'", null);
//        try {
//            int x = cursor.getCount();
//            Log.d("cursor Dcount", Integer.toString(x));
//
//            int i = 0;
//            arrayObj_Class_clusterDetails2 = new Class_ClusterDetails[x];
//            if (cursor.moveToFirst()) {
//
//                do {
//                    Class_ClusterDetails innerObj_Class_ClusterList = new Class_ClusterDetails();
//                    innerObj_Class_ClusterList.setCluster_id(cursor.getString(cursor.getColumnIndex("clustID")));
//                    innerObj_Class_ClusterList.setCluster_name(cursor.getString(cursor.getColumnIndex("clustName")));
//                    innerObj_Class_ClusterList.setCluster_sand_id(cursor.getString(cursor.getColumnIndex("clust_sand_ID")));
//                    innerObj_Class_ClusterList.setCluster_aca_id(cursor.getString(cursor.getColumnIndex("clust_aca_ID")));
//
//
//                    arrayObj_Class_clusterDetails2[i] = innerObj_Class_ClusterList;
//                    i++;
//                } while (cursor.moveToNext());
//            }//if ends
//
//
//            db_clusterID.close();
//            if (x > 0) {
//
//                ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_clusterDetails2);
//                dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//                cluster_sp.setAdapter(dataAdapter);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void Update_InstituteId_spinner(String str_sandboxid, String str_acaid, String str_clustid) {
//
//        SQLiteDatabase dbinstId = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
//        dbinstId.execSQL("CREATE TABLE IF NOT EXISTS InstituteList(inst_ID VARCHAR,inst_Name VARCHAR,inst_sand_ID VARCHAR,inst_aca_ID VARCHAR,inst_clust_ID VARCHAR);");
//        Cursor cursor = dbinstId.rawQuery("SELECT DISTINCT * FROM InstituteList WHERE inst_sand_ID='" + str_sandboxid + "' AND inst_aca_ID='" + str_acaid + "' AND inst_clust_ID='" + str_clustid + "'", null);
//        try {
//            int x = cursor.getCount();
//            Log.d("cursor Dcount", Integer.toString(x));
//
//            int i = 0;
//            arrayObj_Class_InstituteDetails2 = new Class_InsituteDetails[x];
//            if (cursor.moveToFirst()) {
//
//                do {
//                    Class_InsituteDetails innerObj_Class_InstituteList = new Class_InsituteDetails();
//                    innerObj_Class_InstituteList.setInstitute_id(cursor.getString(cursor.getColumnIndex("inst_ID")));
//                    innerObj_Class_InstituteList.setInstitute_name(cursor.getString(cursor.getColumnIndex("inst_Name")));
//                    innerObj_Class_InstituteList.setInst_SandID(cursor.getString(cursor.getColumnIndex("inst_sand_ID")));
//                    innerObj_Class_InstituteList.setInst_AcaID(cursor.getString(cursor.getColumnIndex("inst_aca_ID")));
//                    innerObj_Class_InstituteList.setInst_ClustID(cursor.getString(cursor.getColumnIndex("inst_clust_ID")));
//
//                    Log.e("InstituteDetails2", (cursor.getString(cursor.getColumnIndex("inst_Name"))));
//
//                    arrayObj_Class_InstituteDetails2[i] = innerObj_Class_InstituteList;
//                    i++;
//                } while (cursor.moveToNext());
//            }//if ends
//
//
//            dbinstId.close();
//            if (x > 0) {
//
//
//                ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_InstituteDetails2);
//                dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//                institute_sp.setAdapter(dataAdapter);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Update_SchoolID_spinner
//    public void Update_SchoolID_spinner(String str_sandboxid, String str_acaid, String str_clustid) {
//
//        SQLiteDatabase db_schoolid = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
//        db_schoolid.execSQL("CREATE TABLE IF NOT EXISTS SchoolList(school_ID VARCHAR,school_Name VARCHAR,school_sand_ID VARCHAR,school_aca_ID VARCHAR,school_clust_ID VARCHAR);");
//        Cursor cursor = db_schoolid.rawQuery("SELECT DISTINCT * FROM SchoolList WHERE school_sand_ID='" + str_sandboxid + "' AND school_aca_ID='" + str_acaid + "' AND school_clust_ID='" + str_clustid + "'", null);
//        try {
//            int x = cursor.getCount();
//            Log.d("cursor Dcount", Integer.toString(x));
//
//            int i = 0;
//            arrayObj_Class_SchoolDetails2 = new Class_SchoolDetails[x];
//            if (cursor.moveToFirst()) {
//
//                do {
//                    Class_SchoolDetails innerObj_Class_SchoolList = new Class_SchoolDetails();
//                    innerObj_Class_SchoolList.setSchool_id(cursor.getString(cursor.getColumnIndex("school_ID")));
//                    innerObj_Class_SchoolList.setSchool_SandID(cursor.getString(cursor.getColumnIndex("school_sand_ID")));
//                    innerObj_Class_SchoolList.setSchool_AcaID(cursor.getString(cursor.getColumnIndex("school_aca_ID")));
//                    innerObj_Class_SchoolList.setSchool_ClustID(cursor.getString(cursor.getColumnIndex("school_clust_ID")));
//                    String school_name_db = cursor.getString(cursor.getColumnIndex("school_Name"));
//
//                    if (cursor.getString(cursor.getColumnIndex("school_Name")).equals("0")) {
//                        school_name_db = "Select";
//                        innerObj_Class_SchoolList.setSchool_name(school_name_db);
//                    } else {
//                        innerObj_Class_SchoolList.setSchool_name(cursor.getString(cursor.getColumnIndex("school_Name")));
//
//                    }
//
//
//                    arrayObj_Class_SchoolDetails2[i] = innerObj_Class_SchoolList;
//                    i++;
//                } while (cursor.moveToNext());
//            }//if ends
//
//
//            db_schoolid.close();
//            if (x > 0) {
//
//                ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_SchoolDetails2);
//                dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//                school_sp.setAdapter(dataAdapter);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    //Update_LevelID_spinner
//    public void Update_LevelID_spinner(String str_sandboxid, String str_acaid, String str_clustid, String str_instid) {
//
//        SQLiteDatabase db_levelid = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
//        db_levelid.execSQL("CREATE TABLE IF NOT EXISTS LevelList(LevelID VARCHAR,LevelName VARCHAR,Lev_SandID VARCHAR,Lev_AcaID VARCHAR,Lev_ClustID VARCHAR,Lev_InstID VARCHAR,Lev_AdmissionFee VARCHAR);");
//        Cursor cursor = db_levelid.rawQuery("SELECT DISTINCT * FROM LevelList WHERE Lev_SandID='" + str_sandboxid + "' AND Lev_AcaID='" + str_acaid + "'AND Lev_InstID='" + str_instid + "'", null);
//        // Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM LevelList WHERE Lev_SandID=1 AND Lev_AcaID=2019 AND Lev_ClustID=1 AND Lev_InstID=31", null);
//        try {
//            int x = cursor.getCount();
//            Log.d("cursor Dcountlevelid", Integer.toString(x));
//
//            int i = 0;
//            arrayObj_Class_LevelDetails2 = new Class_LevelDetails[x];
//            if (cursor.moveToFirst()) {
//
//                do {
//                    Class_LevelDetails innerObj_Class_LevelList = new Class_LevelDetails();
//                    innerObj_Class_LevelList.setLevel_id(cursor.getString(cursor.getColumnIndex("LevelID")));
//                    innerObj_Class_LevelList.setLevel_name(cursor.getString(cursor.getColumnIndex("LevelName")));
//                    innerObj_Class_LevelList.setLevel_SandID(cursor.getString(cursor.getColumnIndex("Lev_SandID")));
//                    innerObj_Class_LevelList.setLevel_AcaID(cursor.getString(cursor.getColumnIndex("Lev_AcaID")));
//                    innerObj_Class_LevelList.setLevel_ClustID(cursor.getString(cursor.getColumnIndex("Lev_ClustID")));
//                    innerObj_Class_LevelList.setLevel_InstID(cursor.getString(cursor.getColumnIndex("Lev_InstID")));
//                    innerObj_Class_LevelList.setLevel_admissionfee(cursor.getString(cursor.getColumnIndex("Lev_AdmissionFee")));
//
//
//                    arrayObj_Class_LevelDetails2[i] = innerObj_Class_LevelList;
//                    i++;
//                } while (cursor.moveToNext());
//            }//if ends
//
//
//            db_levelid.close();
//            if (x > 0) {
//
//                ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_LevelDetails2);
//                dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//                level_sp.setAdapter(dataAdapter);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
/////////////////////////////////////Set Spinner////////////////////////////////////////

    ///Set Spinner
//    public void uploadfromDB_Academiclist() {
//
//        SQLiteDatabase db_academiclist = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
//        db_academiclist.execSQL("CREATE TABLE IF NOT EXISTS AcademicList(AcaID VARCHAR,AcaName VARCHAR,ASandID VARCHAR);");
//        Cursor cursor = db_academiclist.rawQuery("SELECT DISTINCT * FROM AcademicList ORDER BY AcaName ASC", null);
//        int x = cursor.getCount();
//        Log.d("cursor count", Integer.toString(x));
//
//        int i = 0;
//        arrayObj_Class_academicDetails2 = new Class_academicDetails[x];
//        if (cursor.moveToFirst()) {
//
//            do {
//                Class_academicDetails innerObj_Class_AcademicList = new Class_academicDetails();
//                innerObj_Class_AcademicList.setAcademic_id(cursor.getString(cursor.getColumnIndex("AcaID")));
//                innerObj_Class_AcademicList.setAcademic_name(cursor.getString(cursor.getColumnIndex("AcaName")));
//                innerObj_Class_AcademicList.setAca_Sandbox_id(cursor.getString(cursor.getColumnIndex("ASandID")));
//
//                arrayObj_Class_academicDetails2[i] = innerObj_Class_AcademicList;
//                i++;
//
//            } while (cursor.moveToNext());
//            // Log.e("academicarray1",Arrays.toString(arrayObj_Class_academicDetails2));
//
//        }//if ends
//
//        db_academiclist.close();
//        if (x > 0) {
//
//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_academicDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            academic_sp.setAdapter(dataAdapter);
//        }
//
//    }

//Learning option
//  public void uploadfromDB_LearningOptionlist() {
//
//
//
//    SQLiteDatabase db_learnoption = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
//    db_learnoption.execSQL("CREATE TABLE IF NOT EXISTS LearningOptionList(Option_ID VARCHAR,Group_Name VARCHAR,Option_Name VARCHAR,Option_Status VARCHAR);");
//    Cursor cursor = db_learnoption.rawQuery("SELECT DISTINCT * FROM LearningOptionList", null);
//    int x = cursor.getCount();
//    Log.d("cursor count", Integer.toString(x));
//
//    int i = 0;
//    arrayObj_Class_learnOption2 = new Class_LearningOption[x];
//    if (cursor.moveToFirst()) {
//
//        do {
//            Class_LearningOption innerObj_Class_LearningOption = new Class_LearningOption();
//            innerObj_Class_LearningOption.setOption_ID(cursor.getString(cursor.getColumnIndex("Option_ID")));
//            innerObj_Class_LearningOption.setGroup_Name(cursor.getString(cursor.getColumnIndex("Group_Name")));
//            innerObj_Class_LearningOption.setOption_Name(cursor.getString(cursor.getColumnIndex("Option_Name")));
//            innerObj_Class_LearningOption.setOption_Status(cursor.getString(cursor.getColumnIndex("Option_Status")));
//
//            arrayObj_Class_learnOption2[i] = innerObj_Class_LearningOption;
//            i++;
//
//        } while (cursor.moveToNext());
//
//    }//if ends
//
//    db_learnoption.close();
//    if (x > 0) {
//
//        ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_learnOption2);
//        dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//        learnoption_Spinner.setAdapter(dataAdapter);
//    }
//
//}
//
//    //sandbox
//
//    public void uploadfromDB_SandBoxlist() {
//
//        SQLiteDatabase db_sandboxlist = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
//        db_sandboxlist.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandID VARCHAR,SandName VARCHAR);");
//        Cursor cursor = db_sandboxlist.rawQuery("SELECT DISTINCT * FROM SandBoxList", null);
//        int x = cursor.getCount();
//        Log.d("cursor count", Integer.toString(x));
//
//        int i = 0;
//        arrayObj_Class_sandboxDetails2 = new Class_SandBoxDetails[x];
//        if (cursor.moveToFirst()) {
//
//            do {
//                Class_SandBoxDetails innerObj_Class_SandboxList = new Class_SandBoxDetails();
//                innerObj_Class_SandboxList.setSandbox_id(cursor.getString(cursor.getColumnIndex("SandID")));
//                innerObj_Class_SandboxList.setSandbox_name(cursor.getString(cursor.getColumnIndex("SandName")));
//                // innerObj_Class_StatesList.setCenterCode(cursor1.getString(cursor1.getColumnIndex("CCode")));
//
//                arrayObj_Class_sandboxDetails2[i] = innerObj_Class_SandboxList;
//                i++;
//
//            } while (cursor.moveToNext());
//            // Log.e("academicarray1",Arrays.toString(arrayObj_Class_sandboxDetails2));
//
//        }//if ends
//
//        db_sandboxlist.close();
//        if (x > 0) {
//
//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_sandboxDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            sandbox_sp.setAdapter(dataAdapter);
//        }
//
//    }
//
//    //uploadfromDB_Clusterist
//    public void uploadfromDB_Clusterist() {
//
//        SQLiteDatabase db_clusterlist = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
//        db_clusterlist.execSQL("CREATE TABLE IF NOT EXISTS ClusterList(clustID VARCHAR,clustName VARCHAR,clust_sand_ID VARCHAR,clust_aca_ID VARCHAR);");
//        Cursor cursor = db_clusterlist.rawQuery("SELECT DISTINCT * FROM ClusterList", null);
//        int x = cursor.getCount();
//        Log.d("cursor count", Integer.toString(x));
//
//        int i = 0;
//        arrayObj_Class_clusterDetails2 = new Class_ClusterDetails[x];
//        if (cursor.moveToFirst()) {
//
//            do {
//                Class_ClusterDetails innerObj_Class_ClusterList = new Class_ClusterDetails();
//                innerObj_Class_ClusterList.setCluster_id(cursor.getString(cursor.getColumnIndex("clustID")));
//                innerObj_Class_ClusterList.setCluster_name(cursor.getString(cursor.getColumnIndex("clustName")));
//                innerObj_Class_ClusterList.setCluster_sand_id(cursor.getString(cursor.getColumnIndex("clust_sand_ID")));
//                innerObj_Class_ClusterList.setCluster_aca_id(cursor.getString(cursor.getColumnIndex("clust_aca_ID")));
//
//                arrayObj_Class_clusterDetails2[i] = innerObj_Class_ClusterList;
//                i++;
//
//            } while (cursor.moveToNext());
//            // Log.e("academicarray1", Arrays.toString(arrayObj_Class_sandboxDetails2));
//
//        }//if ends
//        db_clusterlist.close();
//
//        if (x > 0) {
//
//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_clusterDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            cluster_sp.setAdapter(dataAdapter);
//        }
//
//    }
//
//    //institute
//    public void uploadfromDB_InstitutList() {
//
//        SQLiteDatabase db_institutelist = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
//        db_institutelist.execSQL("CREATE TABLE IF NOT EXISTS InstituteList(inst_ID VARCHAR,inst_Name VARCHAR,inst_sand_ID VARCHAR,inst_aca_ID VARCHAR,inst_clust_ID VARCHAR);");
//        Cursor cursor = db_institutelist.rawQuery("SELECT DISTINCT * FROM InstituteList", null);
//        int x = cursor.getCount();
//        Log.d("cursor count", Integer.toString(x));
//
//        int i = 0;
//        arrayObj_Class_InstituteDetails2 = new Class_InsituteDetails[x];
//        if (cursor.moveToFirst()) {
//
//            do {
//                Class_InsituteDetails innerObj_Class_InstituteList = new Class_InsituteDetails();
//                innerObj_Class_InstituteList.setInstitute_id(cursor.getString(cursor.getColumnIndex("inst_ID")));
//                innerObj_Class_InstituteList.setInstitute_name(cursor.getString(cursor.getColumnIndex("inst_Name")));
//                innerObj_Class_InstituteList.setInst_SandID(cursor.getString(cursor.getColumnIndex("inst_sand_ID")));
//                innerObj_Class_InstituteList.setInst_AcaID(cursor.getString(cursor.getColumnIndex("inst_aca_ID")));
//                innerObj_Class_InstituteList.setInst_ClustID(cursor.getString(cursor.getColumnIndex("inst_clust_ID")));
//                Log.e("InstituteDetails", (cursor.getString(cursor.getColumnIndex("inst_Name"))));
//
//                arrayObj_Class_InstituteDetails2[i] = innerObj_Class_InstituteList;
//                i++;
//
//            } while (cursor.moveToNext());
//            // Log.e("academicarray1", Arrays.toString(arrayObj_Class_sandboxDetails2));
//
//        }//if ends
//        db_institutelist.close();
//
//        if (x > 0) {
//
//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_InstituteDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            institute_sp.setAdapter(dataAdapter);
//        }
//
//    }
//
//    // uploadfromDB_SchoolList
//
//    public void uploadfromDB_SchoolList() {
//
//        SQLiteDatabase db_schoollist = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
//        db_schoollist.execSQL("CREATE TABLE IF NOT EXISTS SchoolList(school_ID VARCHAR,school_Name VARCHAR,school_sand_ID VARCHAR,school_aca_ID VARCHAR,school_clust_ID VARCHAR);");
//        Cursor cursor = db_schoollist.rawQuery("SELECT DISTINCT * FROM SchoolList", null);
//        int x = cursor.getCount();
//        Log.d("cursor count", Integer.toString(x));
//
//        int i = 0;
//        arrayObj_Class_SchoolDetails2 = new Class_SchoolDetails[x];
//        if (cursor.moveToFirst()) {
//
//            do {
//                Class_SchoolDetails innerObj_Class_SchoolList = new Class_SchoolDetails();
//                innerObj_Class_SchoolList.setSchool_id(cursor.getString(cursor.getColumnIndex("school_ID")));
//                //innerObj_Class_StatesList.setSchool_name(cursor1.getString(cursor1.getColumnIndex("school_Name")));
//                innerObj_Class_SchoolList.setSchool_SandID(cursor.getString(cursor.getColumnIndex("school_sand_ID")));
//                innerObj_Class_SchoolList.setSchool_AcaID(cursor.getString(cursor.getColumnIndex("school_aca_ID")));
//                innerObj_Class_SchoolList.setSchool_ClustID(cursor.getString(cursor.getColumnIndex("school_clust_ID")));
//
//                String school_name_db = cursor.getString(cursor.getColumnIndex("school_Name"));
//
//                if (cursor.getString(cursor.getColumnIndex("school_Name")).equals("0")) {
//                    school_name_db = "Select";
//                    innerObj_Class_SchoolList.setSchool_name(school_name_db);
//                } else {
//                    innerObj_Class_SchoolList.setSchool_name(cursor.getString(cursor.getColumnIndex("school_Name")));
//
//                }
//
//
//                arrayObj_Class_SchoolDetails2[i] = innerObj_Class_SchoolList;
//                i++;
//
//            } while (cursor.moveToNext());
//            // Log.e("academicarray1", Arrays.toString(arrayObj_Class_sandboxDetails2));
//
//        }//if ends
//        db_schoollist.close();
//
//        if (x > 0) {
//
//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_SchoolDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            school_sp.setAdapter(dataAdapter);
//        }
//
//    }
//uploadfromDB_LevelList


//    public void uploadfromDB_LevelList() {
//
//        SQLiteDatabase db_levellist = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
//        db_levellist.execSQL("CREATE TABLE IF NOT EXISTS LevelList(LevelID VARCHAR,LevelName VARCHAR,Lev_SandID VARCHAR,Lev_AcaID VARCHAR,Lev_ClustID VARCHAR,Lev_InstID VARCHAR,Lev_AdmissionFee VARCHAR);");
//        Cursor cursor = db_levellist.rawQuery("SELECT DISTINCT * FROM LevelList", null);
//        int x = cursor.getCount();
//        Log.d("cursor countlevelfromdb", Integer.toString(x));
//
//        int i = 0;
//        arrayObj_Class_LevelDetails2 = new Class_LevelDetails[x];
//        if (cursor.moveToFirst()) {
//
//            do {
//                Log.d("level", "enteredlevel loop");
//                Class_LevelDetails innerObj_Class_LevelList = new Class_LevelDetails();
//                innerObj_Class_LevelList.setLevel_id(cursor.getString(cursor.getColumnIndex("LevelID")));
//                innerObj_Class_LevelList.setLevel_name(cursor.getString(cursor.getColumnIndex("LevelName")));
//                innerObj_Class_LevelList.setLevel_SandID(cursor.getString(cursor.getColumnIndex("Lev_SandID")));
//                innerObj_Class_LevelList.setLevel_AcaID(cursor.getString(cursor.getColumnIndex("Lev_AcaID")));
//                innerObj_Class_LevelList.setLevel_ClustID(cursor.getString(cursor.getColumnIndex("Lev_ClustID")));
//                innerObj_Class_LevelList.setLevel_InstID(cursor.getString(cursor.getColumnIndex("Lev_InstID")));
//                innerObj_Class_LevelList.setLevel_admissionfee(cursor.getString(cursor.getColumnIndex("Lev_AdmissionFee")));
//
//                arrayObj_Class_LevelDetails2[i] = innerObj_Class_LevelList;
//                i++;
//
//            } while (cursor.moveToNext());
//            // Log.e("academicarray1", Arrays.toString(arrayObj_Class_sandboxDetails2));
//
//        }//if ends
//        db_levellist.close();
//
//        if (x > 0) {
//
//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_LevelDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            level_sp.setAdapter(dataAdapter);
//        }
//
//    }

/////////////////////////////////////Insert////////////////////////////////////////

    public void DBCreate_Academicdetails_insert_2SQLiteDB(String str_academicID, String str_academicname, String sandboxid) {
        SQLiteDatabase db_academic = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_academic.execSQL("CREATE TABLE IF NOT EXISTS AcademicList(AcaID VARCHAR,AcaName VARCHAR,ASandID VARCHAR);");


        String SQLiteQuery = "INSERT INTO AcademicList (AcaID, AcaName,ASandID)" +
                " VALUES ('" + str_academicID + "','" + str_academicname + "','" + sandboxid + "');";
        db_academic.execSQL(SQLiteQuery);
        db_academic.close();

    }

    public void DBCreate_Clusterdetails_insert_2SQLiteDB(String str_clusterID, String str_clustername, String str_sandid, String str_acaID) {


        SQLiteDatabase db_cluster = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_cluster.execSQL("CREATE TABLE IF NOT EXISTS ClusterList(clustID VARCHAR,clustName VARCHAR,clust_sand_ID VARCHAR,clust_aca_ID VARCHAR);");


        String SQLiteQuery = "INSERT INTO ClusterList (clustID, clustName,clust_sand_ID,clust_aca_ID)" +
                " VALUES ('" + str_clusterID + "','" + str_clustername + "','" + str_sandid + "','" + str_acaID + "');";
        db_cluster.execSQL(SQLiteQuery);
        db_cluster.close();
    }

    public void DBCreate_Institutedetails_insert_2SQLiteDB(String str_instid, String str_instname, String str_sandid, String str_acaID, String str_clustID) {

        SQLiteDatabase db_inst = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);

        db_inst.execSQL("CREATE TABLE IF NOT EXISTS InstituteList(inst_ID VARCHAR,inst_Name VARCHAR,inst_sand_ID VARCHAR,inst_aca_ID VARCHAR,inst_clust_ID VARCHAR);");


        String SQLiteQuery = "INSERT INTO InstituteList (inst_ID,inst_Name,inst_sand_ID,inst_aca_ID,inst_clust_ID)" +
                " VALUES ('" + str_instid + "','" + str_instname + "','" + str_sandid + "','" + str_acaID + "','" + str_clustID + "');";
        db_inst.execSQL(SQLiteQuery);
        db_inst.close();

    }

    public void DBCreate_Schooldetails_insert_2SQLiteDB(String str_schoolid, String str_schoolname, String str_sandid, String str_acaID, String str_clustID) {
        SQLiteDatabase db_school = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_school.execSQL("CREATE TABLE IF NOT EXISTS SchoolList(school_ID VARCHAR,school_Name VARCHAR,school_sand_ID VARCHAR,school_aca_ID VARCHAR,school_clust_ID VARCHAR);");


        String SQLiteQuery = "INSERT INTO SchoolList (school_ID,school_Name,school_sand_ID,school_aca_ID,school_clust_ID)" +
                " VALUES ('" + str_schoolid + "','" + str_schoolname + "','" + str_sandid + "','" + str_acaID + "','" + str_clustID + "');";
        db_school.execSQL(SQLiteQuery);
        db_school.close();

    }

    public void DBCreate_SandBoxdetails_insert_2SQLiteDB(String str_sandboxID, String str_sandboxname) {
        SQLiteDatabase db_sandbox = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_sandbox.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandID VARCHAR,SandName VARCHAR);");


        String SQLiteQuery = "INSERT INTO SandBoxList (SandID, SandName)" +
                " VALUES ('" + str_sandboxID + "','" + str_sandboxname + "');";
        db_sandbox.execSQL(SQLiteQuery);

        db_sandbox.close();
    }

    public void DBCreate_Leveldetails_insert_2SQLiteDB(String str_levelID, String str_levelName, String str_sandboxid, String str_acaid, String str_clustID, String str_instID, String str_fee) {
        SQLiteDatabase db_level = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_level.execSQL("CREATE TABLE IF NOT EXISTS LevelList(LevelID VARCHAR,LevelName VARCHAR,Lev_SandID VARCHAR,Lev_AcaID VARCHAR,Lev_ClustID VARCHAR,Lev_InstID VARCHAR,Lev_AdmissionFee VARCHAR);");


        String SQLiteQuery = "INSERT INTO LevelList (LevelID, LevelName,Lev_SandID,Lev_AcaID,Lev_ClustID,Lev_InstID,Lev_AdmissionFee)" +
                " VALUES ('" + str_levelID + "','" + str_levelName + "','" + str_sandboxid + "' ,'" + str_acaid + "','" + str_clustID + "' ,'" + str_instID + "' ,'" + str_fee + "');";
        db_level.execSQL(SQLiteQuery);
        Log.d("level", "leveldbcreated");
        db_level.close();

    }

    public void DBCreate_LearningOption_insert_2SQLiteDB(String Option_ID, String Group_Name, String Option_Name, String Option_Status) {
        SQLiteDatabase db_learnoption = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_learnoption.execSQL("CREATE TABLE IF NOT EXISTS LearningOptionList(Option_ID VARCHAR,Group_Name VARCHAR,Option_Name VARCHAR,Option_Status VARCHAR);");


        String SQLiteQuery = "INSERT INTO LearningOptionList (Option_ID, Group_Name,Option_Name,Option_Status)" +
                " VALUES ('" + Option_ID + "','" + Group_Name + "','" + Option_Name + "','" + Option_Status + "');";
        db_learnoption.execSQL(SQLiteQuery);

        db_learnoption.close();
    }

    ///////////////////////////////////////////DELETE//////////////////////////////////////////////////////
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
////////////////////////////////////////////////////////////////////////////////////////

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Register_New.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

//                    Intent intentPhotoCapture = new Intent(Activity_Register_New.this, CameraPhotoCapture.class);
//                    startActivity(intentPhotoCapture);
//                    Toast.makeText(getApplicationContext(), "choosen  take photo ",
//                            Toast.LENGTH_SHORT).show();
//                    digitalcamerabuttonpressed = true;


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
        //    if (resultCode == RESULT_OK) {
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

                        photo_iv.setImageBitmap(bitmap);
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
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail = getResizedBitmap(thumbnail, 400);
                Log.w(" gallery.", picturePath + "");
                photo_iv.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);
            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
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

            photo_iv.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    protected void createDatabase() {

        Log.d("inside", "createdatabasepppp");

        db = openOrCreateDatabase("StudentRegistrationOfflineDB", Context.MODE_PRIVATE, null);

        //  db.execSQL("CREATE TABLE IF NOT EXISTS OfflineStudentTable(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,sandboxid VARCHAR,academicid VARCHAR,clusterid VARCHAR,instituteid VARCHAR,levelid VARCHAR,schoolid VARCHAR,image BLOB NOT NULL,studentname VARCHAR,gender VARCHAR,birthdate VARCHAR,education VARCHAR,marks VARCHAR,mobileno VARCHAR,fathername VARCHAR,mothername VARCHAR,aadharno VARCHAR,studentstatus VARCHAR,admissionfee VARCHAR,createddate VARCHAR,createdby VARCHAR);");
//id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        db.execSQL("CREATE TABLE IF NOT EXISTS OfflineStudentTable(sandboxid VARCHAR,academicid VARCHAR,clusterid VARCHAR,instituteid VARCHAR,levelid VARCHAR,schoolid VARCHAR,image BLOB NOT NULL,studentname VARCHAR,gender VARCHAR,birthdate VARCHAR,education VARCHAR,marks VARCHAR,mobileno VARCHAR,fathername VARCHAR,mothername VARCHAR,aadharno VARCHAR,studentstatus VARCHAR,admissionfee VARCHAR,createddate VARCHAR,createdby VARCHAR,receiptno VARCHAR,learnOption VARCHAR);");

    }

    protected void insertIntoDBoffline() {
        String query = "INSERT INTO OfflineStudentTable (sandboxid,academicid," +
                "clusterid,instituteid,levelid,schoolid,image,studentname," +
                "gender,birthdate,education,marks,mobileno,fathername,mothername,aadharno," +
                "studentstatus,admissionfee,createddate,createdby,receiptno) VALUES('" +
                selected_sandboxID_int + "','" + selected_academicID_int + "', '" + selected_clusterID_int + "','" +
                selected_instituteID_int + "','" + selected_levelID_int + "','" + selected_schoolID_int + "','" +
                str_img + "','" + studentname_et.getText().toString() + "', '" + gender + "','" +
                yyyyMMdd_birthdate + "','" + selected_edu + "','" + str_marks +
                "','" + mobileno_et.getText().toString() + "','" + fathername_et.getText().toString() + "','" + mothername_et.getText().toString()
                + "','" + aadar_et.getText().toString() + "','" + selected_studentstatus + "','" + selected_admissionfee + "','" +
                createddate + "','" + createdby + "','" + str_receipt_no + "','" + selected_learnOption + "');";//str_receipt_no


        db.execSQL(query);
        Log.e("tag", "offline1" + query);

        insertedoffline = true;

        counter++;
        strval = counter;
        Toast.makeText(getApplicationContext(), "Application Saved", Toast.LENGTH_LONG).show();
        Log.e("tag", "show:" + query);
        int countt = checkdb();
        Log.e("tag", "offlinecountt" + countt);
        String cstr2 = String.valueOf(countt);
//        SharedPreferences myprefs = Activity_Register_New.this.getSharedPreferences("user", MODE_PRIVATE);
//        myprefs.edit().putString("countt", cstr2).apply();


    }


    /////////////////////////////////////////////////////////9th sept

    public int checkdb() {
        Cursor c = db.rawQuery("SELECT * FROM OfflineStudentTable", null);

        int count = c.getCount();
        c.close();
        return count;
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

        public LogoutCountAsynctask(Activity_Register_New activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {


            dialog.dismiss();
            if (!str_logout_count_Status.equals("")) {
                if (str_logout_count_Status.equals("Success")) {
                    // Toast.makeText(Activity_Register_New.this, "Success", Toast.LENGTH_SHORT).show();

                } else if (str_logout_count_Status.equals("Failure")) {
                    //Toast.makeText(Activity_Register_New.this, "Failure", Toast.LENGTH_SHORT).show();
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
            if (!str_loginuserID.equals("")) {
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
                SoapObject obj2 = (SoapObject) response.getProperty(0);
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


}