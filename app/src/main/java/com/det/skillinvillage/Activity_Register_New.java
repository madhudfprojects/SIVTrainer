package com.det.skillinvillage;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;


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

    Class_academicDetails[] arrayObj_Class_academicDetails, arrayObj_Class_academicDetails2;
    Class_academicDetails obj_Class_academicDetails;

    Class_ClusterDetails[] arrayObj_Class_clusterDetails, arrayObj_Class_clusterDetails2;
    Class_ClusterDetails obj_Class_clusterDetails;

    Class_InsituteDetails[] arrayObj_Class_InstituteDetails, arrayObj_Class_InstituteDetails2;
    Class_InsituteDetails obj_Class_instituteDetails;
    String selected_instituteName;

    Class_LevelDetails[] arrayObj_Class_LevelDetails, arrayObj_Class_LevelDetails2;
    Class_LevelDetails obj_Class_levelDetails;
    String selected_levelName, selected_levelAdmissionFee;


    Class_SandBoxDetails[] arrayObj_Class_sandboxDetails, arrayObj_Class_sandboxDetails2;
    Class_SandBoxDetails obj_Class_sandboxDetails;

    Class_LearningOption[] arrayObj_Class_learnOption, arrayObj_Class_learnOption2;
    Class_LearningOption obj_Class_LearningOption;


    Class_SchoolDetails[] arrayObj_Class_SchoolDetails, arrayObj_Class_SchoolDetails2;
    Class_SchoolDetails obj_Class_SchoolDetails;
    String selected_schoolName, selected_edu;


    String selected_paymentmode, selected_sandboxName, selected_studentstatus = "", str_receipt_no = "", selected_admissionfee;
    String selected_learnOption;
    String[] studentstatusArray = {"Applicant", "Admission"};
    LinearLayout admission_ll, spinnerslayout_ll, mainRegistration_ll;
    Spinner paymentmode_sp,learnoption_Spinner;
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
    String str_loginuserID="";
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

     String str_logout_count_Status="";
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


        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

//        SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
//        str_loginuserID = myprefs.getString("login_userid", "nothing");

        sharedpref_loginuserid_Obj = getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();


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
        admission_ll = findViewById(R.id.admission_LL);
        spinnerslayout_ll = findViewById(R.id.spinnerslayout_LL);
        mainRegistration_ll = findViewById(R.id.mainRegistration_LL);
        school_sp = findViewById(R.id.school_SP);
        photo_iv = findViewById(R.id.photo_IV);
        uploadimg_Btn = findViewById(R.id.UploadImgBtn);
        maxfees_tv = findViewById(R.id.maxfees_TV);
        receipt_no_et = findViewById(R.id.receipt_ET);
        learnoption_Spinner=findViewById(R.id.learnoption_Spinner);

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


        ArrayAdapter dataAdapter_edu = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, educationArray);
        dataAdapter_edu.setDropDownViewResource(R.layout.spinnercenterstyle);
        edu_sp.setAdapter(dataAdapter_edu);

        edu_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                selected_edu = edu_sp.getSelectedItem().toString();
                Log.i("selected_edu", " : " + selected_edu);
                if (selected_edu.equals(educationArray[0]) || (selected_edu.equals(educationArray[1]))) {
                    marks_et.setVisibility(View.GONE);
                    markslabel_tv.setVisibility(View.GONE);
                } else {

                    marks_et.setVisibility(View.VISIBLE);
                    markslabel_tv.setVisibility(View.VISIBLE);

                    if (selected_edu.equals(educationArray[2])) {
                        // marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
                        markslabel_tv.setText(educationArray[1] + " Marks/Grade");
                    }
                    if (selected_edu.equals(educationArray[3])) {
                        //marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
                        markslabel_tv.setText(educationArray[2] + " Marks/Grade");
                    }
                    if (selected_edu.equals(educationArray[4])) {
                        //marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
                        markslabel_tv.setText(educationArray[3] + " Marks/Grade");
                    }
                    if (selected_edu.equals(educationArray[5])) {
                        // marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
                        markslabel_tv.setText(educationArray[4] + " Marks/Grade");
                    }
                    if (selected_edu.equals(educationArray[6])) {
                        //  marks_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
                        markslabel_tv.setText(educationArray[5] + " Marks/Grade");
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter dataAdapter_status = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, studentstatusArray);
        dataAdapter_status.setDropDownViewResource(R.layout.spinnercenterstyle);
        StudentStatus_sp.setAdapter(dataAdapter_status);

        StudentStatus_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                selected_studentstatus = StudentStatus_sp.getSelectedItem().toString();


                Log.i("selected_studentstatus", " : " + selected_studentstatus);


                if (selected_studentstatus.equals("Admission")) {
                    admission_ll.setVisibility(View.VISIBLE);
                    paymentmode_sp.setVisibility(View.VISIBLE);
                    paymentdate_tv.setVisibility(View.VISIBLE);
                    ArrayAdapter dataAdapter_paymentmode = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, paymentArray);
                    dataAdapter_paymentmode.setDropDownViewResource(R.layout.spinnercenterstyle);
                    paymentmode_sp.setAdapter(dataAdapter_paymentmode);

                } else {
                    admission_ll.setVisibility(View.GONE);
                    paymentmode_sp.setVisibility(View.GONE);
                    paymentdate_tv.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
        school_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                obj_Class_SchoolDetails = (Class_SchoolDetails) school_sp.getSelectedItem();
                sp_strschool_ID = obj_Class_SchoolDetails.getSchool_id();

                selected_schoolName = school_sp.getSelectedItem().toString();
                Log.i("selected_schoolName", " : " + selected_schoolName);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        academic_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {

                obj_Class_academicDetails = (Class_academicDetails) academic_sp.getSelectedItem();
                sp_straca_ID = obj_Class_academicDetails.getAcademic_id();
                selected_academicname = academic_sp.getSelectedItem().toString();

                Update_clusterid_spinner(sp_strsand_ID, sp_straca_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cluster_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {


                obj_Class_clusterDetails = (Class_ClusterDetails) cluster_sp.getSelectedItem();
                sp_strClust_ID = obj_Class_clusterDetails.getCluster_id();
                selected_clusterName = cluster_sp.getSelectedItem().toString();
                Log.i("selected_clustername", " : " + selected_clusterName);
                //  Log.i("selected_clusterID", " : " + selected_clusterID);


                Update_InstituteId_spinner(sp_strsand_ID, sp_straca_ID, sp_strClust_ID);
                Update_SchoolID_spinner(sp_strsand_ID, sp_straca_ID, sp_strClust_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        institute_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {

                obj_Class_instituteDetails = (Class_InsituteDetails) institute_sp.getSelectedItem();
                sp_strInst_ID = obj_Class_instituteDetails.getInstitute_id();
                selected_instituteName = institute_sp.getSelectedItem().toString();
                Log.i("selected_instituteName", " : " + selected_instituteName);

                Update_LevelID_spinner(sp_strsand_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        level_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {

                obj_Class_levelDetails = (Class_LevelDetails) level_sp.getSelectedItem();
                sp_strLev_ID = obj_Class_levelDetails.getLevel_id();
                selected_levelAdmissionFee = obj_Class_levelDetails.getLevel_admissionfee();

                selected_levelName = level_sp.getSelectedItem().toString();
                Log.i("selected_levelName", " : " + selected_levelName);
                Log.i("selecte_Fee", " : " + selected_levelAdmissionFee);
                admissionfee_et.setFilters(new InputFilter[]{new InputFilterMinMax("1", selected_levelAdmissionFee)});
                maxfees_tv.setText(getResources().getString(R.string.Rs) + "" + selected_levelAdmissionFee);
                admissionfee_et.setText(selected_levelAdmissionFee);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sandbox_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {

                obj_Class_sandboxDetails = (Class_SandBoxDetails) sandbox_sp.getSelectedItem();
                sp_strsand_ID = obj_Class_sandboxDetails.getSandbox_id();

                selected_sandboxName = sandbox_sp.getSelectedItem().toString();
                // selected_sandboxID = obj_Class_sandboxDetails.get(position);
                /// selected_sandboxID = arrayList_sandboxID.get(position);
                Update_academicid_spinner(sp_strsand_ID);
                Log.i("selected_sandboxName", " : " + selected_sandboxName);
                ///  Log.i("selected_sandboxID", " : " + selected_sandboxID);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        learnoption_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             //   String sel_option=Arrayclass_learningOption[position].toString();
               // selected_learnOption=learnoption_Spinner.getSelectedItem().toString();
                obj_Class_LearningOption = (Class_LearningOption) learnoption_Spinner.getSelectedItem();
                selected_learnOption = obj_Class_LearningOption.getOption_ID();
                Log.e("tag","selected_learnOption="+selected_learnOption);
               // studentlist[finalI].setLearningOption(sel_option);
               // Toast.makeText(Remarks.this, "Selected item:" + " " + Arrayclass_learningOption[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        uploadfromDB_SandBoxlist();

        uploadfromDB_Academiclist();

        uploadfromDB_Clusterist();

        uploadfromDB_InstitutList();

        uploadfromDB_SchoolList();

        uploadfromDB_LevelList();
        uploadfromDB_LearningOptionlist();

    }//oncreate

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
                .setVisible(true);
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

                    LogoutCountAsynctask logoutCountAsynctask=new LogoutCountAsynctask(Activity_Register_New.this);
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
                    deleteSandBoxTable_B4insertion();
                    deleteAcademicTable_B4insertion();
                    deleteClusterTable_B4insertion();
                    deleteInstituteTable_B4insertion();
                    deleteSchoolTable_B4insertion();
                    deleteLevelTable_B4insertion();
                    deleteLearningOptionTable_B4insertion();

                    getacademiclist_Asynctask();


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

    public void getacademiclist_Asynctask() {
        GetAcademiclist_Asynctask task = new GetAcademiclist_Asynctask(Activity_Register_New.this);
        task.execute();
        AsyncCallWS_learningMode task1 = new AsyncCallWS_learningMode(Activity_Register_New.this);
        task1.execute();
    }

    private class GetAcademiclist_Asynctask extends AsyncTask<String, Void, Void> {
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


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(String... params) {
            Log.i("df", "doInBackground");

            GetAcademiclist();  // get the Year list
            return null;
        }

        public GetAcademiclist_Asynctask(Activity_Register_New activity) {
            context = activity;
            dialog = new ProgressDialog(activity, R.style.AppCompatAlertDialogStyle);
        }


        @Override
        protected void onPostExecute(Void result) {


            dialog.dismiss();

            // Update_Year_spinner_4rmDB();


            if (academiclistcount <= 0) {
                Toast.makeText(context, "No items", Toast.LENGTH_SHORT).show();
            } else {


                uploadfromDB_SandBoxlist();
                uploadfromDB_Academiclist();
                uploadfromDB_Clusterist();
                uploadfromDB_InstitutList();
                uploadfromDB_SchoolList();
                uploadfromDB_LevelList();

            }


        }//end of onPostExecute
    }// end Async task


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void GetAcademiclist() {


        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "LoadAcademicDetails";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/LoadAcademicDetails";


        try {

            int userid = Integer.parseInt(str_loginuserID);//str_loginuserID
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

            request.addProperty("User_ID", userid);//change static value later

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("resp academiclist", response.toString());
                academiclistcount = response.getPropertyCount();

                Log.e("academiclistcount", String.valueOf(response.getPropertyCount()));


                // if(academiclistcount>0) {
                arrayObj_Class_academicDetails = new Class_academicDetails[academiclistcount];
                arrayObj_Class_clusterDetails = new Class_ClusterDetails[academiclistcount];
                arrayObj_Class_InstituteDetails = new Class_InsituteDetails[academiclistcount];
                arrayObj_Class_LevelDetails = new Class_LevelDetails[academiclistcount];
                arrayObj_Class_sandboxDetails = new Class_SandBoxDetails[academiclistcount];
                arrayObj_Class_SchoolDetails = new Class_SchoolDetails[academiclistcount];


                for (int i = 0; i < academiclistcount; i++) {

                    SoapObject response_soapobj = (SoapObject) response.getProperty(i);

                    SoapPrimitive response_soapobj_academicID, response_soapobj_academic_Name, response_soapobj_clusterid,
                            response_soapobj_clustername, response_soapobj_instituteID, response_soapobj_instituteName,
                            response_soapobj_levelID, response_soapobj_levelname, response_soapobj_sandboxid, response_soapobj_sandboxname, response_soapobj_admissionfee, response_soapobj_schoolid, response_soapobj_schoolname;
                    response_soapobj_academicID = (SoapPrimitive) response_soapobj.getProperty("Academic_ID");
                    response_soapobj_academic_Name = (SoapPrimitive) response_soapobj.getProperty("Academic_Name");
                    response_soapobj_clusterid = (SoapPrimitive) response_soapobj.getProperty("Cluster_ID");
                    response_soapobj_clustername = (SoapPrimitive) response_soapobj.getProperty("Cluster_Name");
                    response_soapobj_instituteID = (SoapPrimitive) response_soapobj.getProperty("Institute_ID");
                    response_soapobj_instituteName = (SoapPrimitive) response_soapobj.getProperty("Institute_Name");
                    response_soapobj_levelID = (SoapPrimitive) response_soapobj.getProperty("Lavel_ID");
                    response_soapobj_levelname = (SoapPrimitive) response_soapobj.getProperty("Lavel_Name");
                    response_soapobj_sandboxid = (SoapPrimitive) response_soapobj.getProperty("Sandbox_ID");
                    response_soapobj_sandboxname = (SoapPrimitive) response_soapobj.getProperty("Sandbox_Name");
                    response_soapobj_schoolid = (SoapPrimitive) response_soapobj.getProperty("School_ID");
                    response_soapobj_schoolname = (SoapPrimitive) response_soapobj.getProperty("School_Name");
                    response_soapobj_admissionfee = (SoapPrimitive) response_soapobj.getProperty("Admission_Fee");


                    Class_academicDetails innerObj_Class_academic = new Class_academicDetails();
                    innerObj_Class_academic.setAcademic_id(response_soapobj_academicID.toString());
                    innerObj_Class_academic.setAcademic_name(response_soapobj_academic_Name.toString());

                    Class_ClusterDetails innerObj_Class_cluster = new Class_ClusterDetails();
                    innerObj_Class_cluster.setCluster_id(response_soapobj_clusterid.toString());
                    innerObj_Class_cluster.setCluster_name(response_soapobj_clustername.toString());

                    Class_InsituteDetails innerObj_Class_institute = new Class_InsituteDetails();
                    innerObj_Class_institute.setInstitute_id(response_soapobj_instituteID.toString());
                    innerObj_Class_institute.setInstitute_name(response_soapobj_instituteName.toString());

                    Class_LevelDetails innerObj_Class_level = new Class_LevelDetails();
                    innerObj_Class_level.setLevel_id(response_soapobj_levelID.toString());
                    innerObj_Class_level.setLevel_name(response_soapobj_levelname.toString());

                    Class_SandBoxDetails innerObj_Class_sandbox = new Class_SandBoxDetails();
                    innerObj_Class_sandbox.setSandbox_id(response_soapobj_sandboxid.toString());
                    innerObj_Class_sandbox.setSandbox_name(response_soapobj_sandboxname.toString());

                    Class_SchoolDetails innerObj_Class_school = new Class_SchoolDetails();
                    innerObj_Class_school.setSchool_id(response_soapobj_schoolid.toString());
                    innerObj_Class_school.setSchool_name(response_soapobj_schoolname.toString());


                    arrayObj_Class_academicDetails[i] = innerObj_Class_academic;
                    Log.e("class", arrayObj_Class_academicDetails[i].getAcademic_name());
                    arrayObj_Class_clusterDetails[i] = innerObj_Class_cluster;
                    arrayObj_Class_InstituteDetails[i] = innerObj_Class_institute;
                    arrayObj_Class_LevelDetails[i] = innerObj_Class_level;
                    arrayObj_Class_sandboxDetails[i] = innerObj_Class_sandbox;
                    arrayObj_Class_SchoolDetails[i] = innerObj_Class_school;

                    String aca_id = response_soapobj.getProperty("Academic_ID").toString();
                    String aca_name = response_soapobj.getProperty("Academic_Name").toString();

                    String clust_id = response_soapobj.getProperty("Cluster_ID").toString();
                    String clust_name = response_soapobj.getProperty("Cluster_Name").toString();


                    String inst_id = response_soapobj.getProperty("Institute_ID").toString();
                    String inst_name = response_soapobj.getProperty("Institute_Name").toString();

                    String level_id = response_soapobj.getProperty("Lavel_ID").toString();
                    String level_name = response_soapobj.getProperty("Lavel_Name").toString();

                    String sandbox_id = response_soapobj.getProperty("Sandbox_ID").toString();
                    String sandbox_name = response_soapobj.getProperty("Sandbox_Name").toString();

                    String school_id = response_soapobj.getProperty("School_ID").toString();
                    String school_name = response_soapobj.getProperty("School_Name").toString();

                    String admissionFee = response_soapobj.getProperty("Admission_Fee").toString();
                    Log.e("admissionFee", admissionFee);

                    DBCreate_SandBoxdetails_insert_2SQLiteDB(sandbox_id, sandbox_name);
                    DBCreate_Academicdetails_insert_2SQLiteDB(aca_id, aca_name, sandbox_id);
                    DBCreate_Clusterdetails_insert_2SQLiteDB(clust_id, clust_name, sandbox_id, aca_id);
                    DBCreate_Institutedetails_insert_2SQLiteDB(inst_id, inst_name, sandbox_id, aca_id, clust_id);
                    DBCreate_Schooldetails_insert_2SQLiteDB(school_id, school_name, sandbox_id, aca_id, clust_id);
                    DBCreate_Leveldetails_insert_2SQLiteDB(level_id, level_name, sandbox_id, aca_id, clust_id, inst_id, admissionFee);


                }//end for loop


                // }//end of if


            } catch (Throwable t) {

                Log.e("getCollege fail", "> " + t.getMessage());
                internet_issue = "slow internet";
            }
        } catch (Throwable t) {

            Log.e("UnRegister Error", "> " + t.getMessage());
        }

    }//End of uploaddetails

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

                /*ArrayAdapter dataAdapter_learnop = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, Arrayclass_learningOption);
                dataAdapter_learnop.setDropDownViewResource(R.layout.spinnercenterstyle);
                learnoption_Spinner.setAdapter(dataAdapter_learnop);*/
                uploadfromDB_LearningOptionlist();
            }

        }//end of onPostExecute
    }// end Async task

    public void list_detaile() {
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
                    DBCreate_LearningOption_insert_2SQLiteDB(Arrayclass_learningOption[i].getOption_ID(),Arrayclass_learningOption[i].getGroup_Name(),Arrayclass_learningOption[i].getOption_Name(),Arrayclass_learningOption[i].getOption_Status());
                }// End of for loop


            } catch (Throwable t) {

                Log.e("requestload fail", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("UnRegisterload  Error", "> " + t.getMessage());

        }

    }//End of leaveDetail method

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

            selected_sandboxID_int = Integer.parseInt(sp_strsand_ID);
            selected_academicID_int = Integer.parseInt(sp_straca_ID);
            selected_clusterID_int = Integer.parseInt(sp_strClust_ID);
            selected_instituteID_int = Integer.parseInt(sp_strInst_ID);
            selected_levelID_int = Integer.parseInt(sp_strLev_ID);
            selected_schoolID_int = Integer.parseInt(sp_strschool_ID);
            str_ForSavingData_studentname = studentname_et.getText().toString();
            str_ForSavingData_mobileno = mobileno_et.getText().toString();
            str_ForSavingData_fathername = fathername_et.getText().toString();
            str_ForSavingData_mothername = mothername_et.getText().toString();
            str_ForSavingData_aadar = aadar_et.getText().toString();
            selected_admissionfee = admissionfee_et.getText().toString();
            str_receipt_no = receipt_no_et.getText().toString();
            if (marks_et.getVisibility() == View.VISIBLE) {

                str_marks = marks_et.getText().toString();
            }
            Date c = Calendar.getInstance().getTime();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            createddate = df.format(c);
            createdby = str_loginuserID;
            internetDectector = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent) {
                SubmitAsyncTask task = new SubmitAsyncTask(Activity_Register_New.this);
                task.execute();
            } else {
                //Toast.makeText(getApplicationContext(), "Data saved locally..", Toast.LENGTH_LONG).show();
                // Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();

                createDatabase();
                insertIntoDBoffline();
                ClearEditTextAfterDoneTask();
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
        }
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
                    selected_studentstatus, selected_admissionfee, createddate, createdby, str_receipt_no,selected_learnOption);

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

        if (selected_edu.equals(educationArray[0])) {
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

    public void Update_academicid_spinner(String str_Sids) {

        SQLiteDatabase db_academicID = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_academicID.execSQL("CREATE TABLE IF NOT EXISTS AcademicList(AcaID VARCHAR,AcaName VARCHAR,ASandID VARCHAR);");
        Cursor cursor = db_academicID.rawQuery("SELECT DISTINCT * FROM AcademicList WHERE ASandID='" + str_Sids + "'", null);
        int x = cursor.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_academicDetails2 = new Class_academicDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_academicDetails innerObj_Class_AcademicList = new Class_academicDetails();
                innerObj_Class_AcademicList.setAcademic_id(cursor.getString(cursor.getColumnIndex("AcaID")));
                innerObj_Class_AcademicList.setAcademic_name(cursor.getString(cursor.getColumnIndex("AcaName")));
                innerObj_Class_AcademicList.setAca_Sandbox_id(cursor.getString(cursor.getColumnIndex("ASandID")));


                arrayObj_Class_academicDetails2[i] = innerObj_Class_AcademicList;
                i++;
            } while (cursor.moveToNext());
        }//if ends


        db_academicID.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_academicDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            academic_sp.setAdapter(dataAdapter);
        }

    }


    public void Update_clusterid_spinner(String str_sandboxid, String str_acaid) {

        SQLiteDatabase db_clusterID = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_clusterID.execSQL("CREATE TABLE IF NOT EXISTS ClusterList(clustID VARCHAR,clustName VARCHAR,clust_sand_ID VARCHAR,clust_aca_ID VARCHAR);");
        Cursor cursor = db_clusterID.rawQuery("SELECT DISTINCT * FROM ClusterList WHERE clust_sand_ID='" + str_sandboxid + "' AND clust_aca_ID='" + str_acaid + "'", null);
        try {
            int x = cursor.getCount();
            Log.d("cursor Dcount", Integer.toString(x));

            int i = 0;
            arrayObj_Class_clusterDetails2 = new Class_ClusterDetails[x];
            if (cursor.moveToFirst()) {

                do {
                    Class_ClusterDetails innerObj_Class_ClusterList = new Class_ClusterDetails();
                    innerObj_Class_ClusterList.setCluster_id(cursor.getString(cursor.getColumnIndex("clustID")));
                    innerObj_Class_ClusterList.setCluster_name(cursor.getString(cursor.getColumnIndex("clustName")));
                    innerObj_Class_ClusterList.setCluster_sand_id(cursor.getString(cursor.getColumnIndex("clust_sand_ID")));
                    innerObj_Class_ClusterList.setCluster_aca_id(cursor.getString(cursor.getColumnIndex("clust_aca_ID")));


                    arrayObj_Class_clusterDetails2[i] = innerObj_Class_ClusterList;
                    i++;
                } while (cursor.moveToNext());
            }//if ends


            db_clusterID.close();
            if (x > 0) {

                ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_clusterDetails2);
                dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
                cluster_sp.setAdapter(dataAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void Update_InstituteId_spinner(String str_sandboxid, String str_acaid, String str_clustid) {

        SQLiteDatabase dbinstId = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        dbinstId.execSQL("CREATE TABLE IF NOT EXISTS InstituteList(inst_ID VARCHAR,inst_Name VARCHAR,inst_sand_ID VARCHAR,inst_aca_ID VARCHAR,inst_clust_ID VARCHAR);");
        Cursor cursor = dbinstId.rawQuery("SELECT DISTINCT * FROM InstituteList WHERE inst_sand_ID='" + str_sandboxid + "' AND inst_aca_ID='" + str_acaid + "' AND inst_clust_ID='" + str_clustid + "'", null);
        try {
            int x = cursor.getCount();
            Log.d("cursor Dcount", Integer.toString(x));

            int i = 0;
            arrayObj_Class_InstituteDetails2 = new Class_InsituteDetails[x];
            if (cursor.moveToFirst()) {

                do {
                    Class_InsituteDetails innerObj_Class_InstituteList = new Class_InsituteDetails();
                    innerObj_Class_InstituteList.setInstitute_id(cursor.getString(cursor.getColumnIndex("inst_ID")));
                    innerObj_Class_InstituteList.setInstitute_name(cursor.getString(cursor.getColumnIndex("inst_Name")));
                    innerObj_Class_InstituteList.setInst_SandID(cursor.getString(cursor.getColumnIndex("inst_sand_ID")));
                    innerObj_Class_InstituteList.setInst_AcaID(cursor.getString(cursor.getColumnIndex("inst_aca_ID")));
                    innerObj_Class_InstituteList.setInst_ClustID(cursor.getString(cursor.getColumnIndex("inst_clust_ID")));

                    Log.e("InstituteDetails2", (cursor.getString(cursor.getColumnIndex("inst_Name"))));

                    arrayObj_Class_InstituteDetails2[i] = innerObj_Class_InstituteList;
                    i++;
                } while (cursor.moveToNext());
            }//if ends


            dbinstId.close();
            if (x > 0) {


                ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_InstituteDetails2);
                dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
                institute_sp.setAdapter(dataAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update_SchoolID_spinner
    public void Update_SchoolID_spinner(String str_sandboxid, String str_acaid, String str_clustid) {

        SQLiteDatabase db_schoolid = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_schoolid.execSQL("CREATE TABLE IF NOT EXISTS SchoolList(school_ID VARCHAR,school_Name VARCHAR,school_sand_ID VARCHAR,school_aca_ID VARCHAR,school_clust_ID VARCHAR);");
        Cursor cursor = db_schoolid.rawQuery("SELECT DISTINCT * FROM SchoolList WHERE school_sand_ID='" + str_sandboxid + "' AND school_aca_ID='" + str_acaid + "' AND school_clust_ID='" + str_clustid + "'", null);
        try {
            int x = cursor.getCount();
            Log.d("cursor Dcount", Integer.toString(x));

            int i = 0;
            arrayObj_Class_SchoolDetails2 = new Class_SchoolDetails[x];
            if (cursor.moveToFirst()) {

                do {
                    Class_SchoolDetails innerObj_Class_SchoolList = new Class_SchoolDetails();
                    innerObj_Class_SchoolList.setSchool_id(cursor.getString(cursor.getColumnIndex("school_ID")));
                    innerObj_Class_SchoolList.setSchool_SandID(cursor.getString(cursor.getColumnIndex("school_sand_ID")));
                    innerObj_Class_SchoolList.setSchool_AcaID(cursor.getString(cursor.getColumnIndex("school_aca_ID")));
                    innerObj_Class_SchoolList.setSchool_ClustID(cursor.getString(cursor.getColumnIndex("school_clust_ID")));
                    String school_name_db = cursor.getString(cursor.getColumnIndex("school_Name"));

                    if (cursor.getString(cursor.getColumnIndex("school_Name")).equals("0")) {
                        school_name_db = "Select";
                        innerObj_Class_SchoolList.setSchool_name(school_name_db);
                    } else {
                        innerObj_Class_SchoolList.setSchool_name(cursor.getString(cursor.getColumnIndex("school_Name")));

                    }


                    arrayObj_Class_SchoolDetails2[i] = innerObj_Class_SchoolList;
                    i++;
                } while (cursor.moveToNext());
            }//if ends


            db_schoolid.close();
            if (x > 0) {

                ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_SchoolDetails2);
                dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
                school_sp.setAdapter(dataAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Update_LevelID_spinner
    public void Update_LevelID_spinner(String str_sandboxid, String str_acaid, String str_clustid, String str_instid) {

        SQLiteDatabase db_levelid = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_levelid.execSQL("CREATE TABLE IF NOT EXISTS LevelList(LevelID VARCHAR,LevelName VARCHAR,Lev_SandID VARCHAR,Lev_AcaID VARCHAR,Lev_ClustID VARCHAR,Lev_InstID VARCHAR,Lev_AdmissionFee VARCHAR);");
        Cursor cursor = db_levelid.rawQuery("SELECT DISTINCT * FROM LevelList WHERE Lev_SandID='" + str_sandboxid + "' AND Lev_AcaID='" + str_acaid + "'AND Lev_InstID='" + str_instid + "'", null);
        // Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM LevelList WHERE Lev_SandID=1 AND Lev_AcaID=2019 AND Lev_ClustID=1 AND Lev_InstID=31", null);
        try {
            int x = cursor.getCount();
            Log.d("cursor Dcountlevelid", Integer.toString(x));

            int i = 0;
            arrayObj_Class_LevelDetails2 = new Class_LevelDetails[x];
            if (cursor.moveToFirst()) {

                do {
                    Class_LevelDetails innerObj_Class_LevelList = new Class_LevelDetails();
                    innerObj_Class_LevelList.setLevel_id(cursor.getString(cursor.getColumnIndex("LevelID")));
                    innerObj_Class_LevelList.setLevel_name(cursor.getString(cursor.getColumnIndex("LevelName")));
                    innerObj_Class_LevelList.setLevel_SandID(cursor.getString(cursor.getColumnIndex("Lev_SandID")));
                    innerObj_Class_LevelList.setLevel_AcaID(cursor.getString(cursor.getColumnIndex("Lev_AcaID")));
                    innerObj_Class_LevelList.setLevel_ClustID(cursor.getString(cursor.getColumnIndex("Lev_ClustID")));
                    innerObj_Class_LevelList.setLevel_InstID(cursor.getString(cursor.getColumnIndex("Lev_InstID")));
                    innerObj_Class_LevelList.setLevel_admissionfee(cursor.getString(cursor.getColumnIndex("Lev_AdmissionFee")));


                    arrayObj_Class_LevelDetails2[i] = innerObj_Class_LevelList;
                    i++;
                } while (cursor.moveToNext());
            }//if ends


            db_levelid.close();
            if (x > 0) {

                ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_LevelDetails2);
                dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
                level_sp.setAdapter(dataAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/////////////////////////////////////Set Spinner////////////////////////////////////////

    ///Set Spinner
    public void uploadfromDB_Academiclist() {

        SQLiteDatabase db_academiclist = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_academiclist.execSQL("CREATE TABLE IF NOT EXISTS AcademicList(AcaID VARCHAR,AcaName VARCHAR,ASandID VARCHAR);");
        Cursor cursor = db_academiclist.rawQuery("SELECT DISTINCT * FROM AcademicList ORDER BY AcaName ASC", null);
        int x = cursor.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_academicDetails2 = new Class_academicDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_academicDetails innerObj_Class_AcademicList = new Class_academicDetails();
                innerObj_Class_AcademicList.setAcademic_id(cursor.getString(cursor.getColumnIndex("AcaID")));
                innerObj_Class_AcademicList.setAcademic_name(cursor.getString(cursor.getColumnIndex("AcaName")));
                innerObj_Class_AcademicList.setAca_Sandbox_id(cursor.getString(cursor.getColumnIndex("ASandID")));

                arrayObj_Class_academicDetails2[i] = innerObj_Class_AcademicList;
                i++;

            } while (cursor.moveToNext());
            // Log.e("academicarray1",Arrays.toString(arrayObj_Class_academicDetails2));

        }//if ends

        db_academiclist.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_academicDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            academic_sp.setAdapter(dataAdapter);
        }

    }

//Learning option
public void uploadfromDB_LearningOptionlist() {



    SQLiteDatabase db_learnoption = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
    db_learnoption.execSQL("CREATE TABLE IF NOT EXISTS LearningOptionList(Option_ID VARCHAR,Group_Name VARCHAR,Option_Name VARCHAR,Option_Status VARCHAR);");
    Cursor cursor = db_learnoption.rawQuery("SELECT DISTINCT * FROM LearningOptionList", null);
    int x = cursor.getCount();
    Log.d("cursor count", Integer.toString(x));

    int i = 0;
    arrayObj_Class_learnOption2 = new Class_LearningOption[x];
    if (cursor.moveToFirst()) {

        do {
            Class_LearningOption innerObj_Class_LearningOption = new Class_LearningOption();
            innerObj_Class_LearningOption.setOption_ID(cursor.getString(cursor.getColumnIndex("Option_ID")));
            innerObj_Class_LearningOption.setGroup_Name(cursor.getString(cursor.getColumnIndex("Group_Name")));
            innerObj_Class_LearningOption.setOption_Name(cursor.getString(cursor.getColumnIndex("Option_Name")));
            innerObj_Class_LearningOption.setOption_Status(cursor.getString(cursor.getColumnIndex("Option_Status")));

            arrayObj_Class_learnOption2[i] = innerObj_Class_LearningOption;
            i++;

        } while (cursor.moveToNext());

    }//if ends

    db_learnoption.close();
    if (x > 0) {

        ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_learnOption2);
        dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
        learnoption_Spinner.setAdapter(dataAdapter);
    }

}

    //sandbox

    public void uploadfromDB_SandBoxlist() {

        SQLiteDatabase db_sandboxlist = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_sandboxlist.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandID VARCHAR,SandName VARCHAR);");
        Cursor cursor = db_sandboxlist.rawQuery("SELECT DISTINCT * FROM SandBoxList", null);
        int x = cursor.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_sandboxDetails2 = new Class_SandBoxDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_SandBoxDetails innerObj_Class_SandboxList = new Class_SandBoxDetails();
                innerObj_Class_SandboxList.setSandbox_id(cursor.getString(cursor.getColumnIndex("SandID")));
                innerObj_Class_SandboxList.setSandbox_name(cursor.getString(cursor.getColumnIndex("SandName")));
                // innerObj_Class_StatesList.setCenterCode(cursor1.getString(cursor1.getColumnIndex("CCode")));

                arrayObj_Class_sandboxDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor.moveToNext());
            // Log.e("academicarray1",Arrays.toString(arrayObj_Class_sandboxDetails2));

        }//if ends

        db_sandboxlist.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_sandboxDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            sandbox_sp.setAdapter(dataAdapter);
        }

    }

    //uploadfromDB_Clusterist
    public void uploadfromDB_Clusterist() {

        SQLiteDatabase db_clusterlist = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_clusterlist.execSQL("CREATE TABLE IF NOT EXISTS ClusterList(clustID VARCHAR,clustName VARCHAR,clust_sand_ID VARCHAR,clust_aca_ID VARCHAR);");
        Cursor cursor = db_clusterlist.rawQuery("SELECT DISTINCT * FROM ClusterList", null);
        int x = cursor.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_clusterDetails2 = new Class_ClusterDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_ClusterDetails innerObj_Class_ClusterList = new Class_ClusterDetails();
                innerObj_Class_ClusterList.setCluster_id(cursor.getString(cursor.getColumnIndex("clustID")));
                innerObj_Class_ClusterList.setCluster_name(cursor.getString(cursor.getColumnIndex("clustName")));
                innerObj_Class_ClusterList.setCluster_sand_id(cursor.getString(cursor.getColumnIndex("clust_sand_ID")));
                innerObj_Class_ClusterList.setCluster_aca_id(cursor.getString(cursor.getColumnIndex("clust_aca_ID")));

                arrayObj_Class_clusterDetails2[i] = innerObj_Class_ClusterList;
                i++;

            } while (cursor.moveToNext());
            // Log.e("academicarray1", Arrays.toString(arrayObj_Class_sandboxDetails2));

        }//if ends
        db_clusterlist.close();

        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_clusterDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            cluster_sp.setAdapter(dataAdapter);
        }

    }

    //institute
    public void uploadfromDB_InstitutList() {

        SQLiteDatabase db_institutelist = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_institutelist.execSQL("CREATE TABLE IF NOT EXISTS InstituteList(inst_ID VARCHAR,inst_Name VARCHAR,inst_sand_ID VARCHAR,inst_aca_ID VARCHAR,inst_clust_ID VARCHAR);");
        Cursor cursor = db_institutelist.rawQuery("SELECT DISTINCT * FROM InstituteList", null);
        int x = cursor.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_InstituteDetails2 = new Class_InsituteDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_InsituteDetails innerObj_Class_InstituteList = new Class_InsituteDetails();
                innerObj_Class_InstituteList.setInstitute_id(cursor.getString(cursor.getColumnIndex("inst_ID")));
                innerObj_Class_InstituteList.setInstitute_name(cursor.getString(cursor.getColumnIndex("inst_Name")));
                innerObj_Class_InstituteList.setInst_SandID(cursor.getString(cursor.getColumnIndex("inst_sand_ID")));
                innerObj_Class_InstituteList.setInst_AcaID(cursor.getString(cursor.getColumnIndex("inst_aca_ID")));
                innerObj_Class_InstituteList.setInst_ClustID(cursor.getString(cursor.getColumnIndex("inst_clust_ID")));
                Log.e("InstituteDetails", (cursor.getString(cursor.getColumnIndex("inst_Name"))));

                arrayObj_Class_InstituteDetails2[i] = innerObj_Class_InstituteList;
                i++;

            } while (cursor.moveToNext());
            // Log.e("academicarray1", Arrays.toString(arrayObj_Class_sandboxDetails2));

        }//if ends
        db_institutelist.close();

        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_InstituteDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            institute_sp.setAdapter(dataAdapter);
        }

    }

    // uploadfromDB_SchoolList

    public void uploadfromDB_SchoolList() {

        SQLiteDatabase db_schoollist = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_schoollist.execSQL("CREATE TABLE IF NOT EXISTS SchoolList(school_ID VARCHAR,school_Name VARCHAR,school_sand_ID VARCHAR,school_aca_ID VARCHAR,school_clust_ID VARCHAR);");
        Cursor cursor = db_schoollist.rawQuery("SELECT DISTINCT * FROM SchoolList", null);
        int x = cursor.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_SchoolDetails2 = new Class_SchoolDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_SchoolDetails innerObj_Class_SchoolList = new Class_SchoolDetails();
                innerObj_Class_SchoolList.setSchool_id(cursor.getString(cursor.getColumnIndex("school_ID")));
                //innerObj_Class_StatesList.setSchool_name(cursor1.getString(cursor1.getColumnIndex("school_Name")));
                innerObj_Class_SchoolList.setSchool_SandID(cursor.getString(cursor.getColumnIndex("school_sand_ID")));
                innerObj_Class_SchoolList.setSchool_AcaID(cursor.getString(cursor.getColumnIndex("school_aca_ID")));
                innerObj_Class_SchoolList.setSchool_ClustID(cursor.getString(cursor.getColumnIndex("school_clust_ID")));

                String school_name_db = cursor.getString(cursor.getColumnIndex("school_Name"));

                if (cursor.getString(cursor.getColumnIndex("school_Name")).equals("0")) {
                    school_name_db = "Select";
                    innerObj_Class_SchoolList.setSchool_name(school_name_db);
                } else {
                    innerObj_Class_SchoolList.setSchool_name(cursor.getString(cursor.getColumnIndex("school_Name")));

                }


                arrayObj_Class_SchoolDetails2[i] = innerObj_Class_SchoolList;
                i++;

            } while (cursor.moveToNext());
            // Log.e("academicarray1", Arrays.toString(arrayObj_Class_sandboxDetails2));

        }//if ends
        db_schoollist.close();

        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_SchoolDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            school_sp.setAdapter(dataAdapter);
        }

    }
//uploadfromDB_LevelList


    public void uploadfromDB_LevelList() {

        SQLiteDatabase db_levellist = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_levellist.execSQL("CREATE TABLE IF NOT EXISTS LevelList(LevelID VARCHAR,LevelName VARCHAR,Lev_SandID VARCHAR,Lev_AcaID VARCHAR,Lev_ClustID VARCHAR,Lev_InstID VARCHAR,Lev_AdmissionFee VARCHAR);");
        Cursor cursor = db_levellist.rawQuery("SELECT DISTINCT * FROM LevelList", null);
        int x = cursor.getCount();
        Log.d("cursor countlevelfromdb", Integer.toString(x));

        int i = 0;
        arrayObj_Class_LevelDetails2 = new Class_LevelDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Log.d("level", "enteredlevel loop");
                Class_LevelDetails innerObj_Class_LevelList = new Class_LevelDetails();
                innerObj_Class_LevelList.setLevel_id(cursor.getString(cursor.getColumnIndex("LevelID")));
                innerObj_Class_LevelList.setLevel_name(cursor.getString(cursor.getColumnIndex("LevelName")));
                innerObj_Class_LevelList.setLevel_SandID(cursor.getString(cursor.getColumnIndex("Lev_SandID")));
                innerObj_Class_LevelList.setLevel_AcaID(cursor.getString(cursor.getColumnIndex("Lev_AcaID")));
                innerObj_Class_LevelList.setLevel_ClustID(cursor.getString(cursor.getColumnIndex("Lev_ClustID")));
                innerObj_Class_LevelList.setLevel_InstID(cursor.getString(cursor.getColumnIndex("Lev_InstID")));
                innerObj_Class_LevelList.setLevel_admissionfee(cursor.getString(cursor.getColumnIndex("Lev_AdmissionFee")));

                arrayObj_Class_LevelDetails2[i] = innerObj_Class_LevelList;
                i++;

            } while (cursor.moveToNext());
            // Log.e("academicarray1", Arrays.toString(arrayObj_Class_sandboxDetails2));

        }//if ends
        db_levellist.close();

        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_LevelDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            level_sp.setAdapter(dataAdapter);
        }

    }

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

    public void DBCreate_LearningOption_insert_2SQLiteDB(String Option_ID, String Group_Name,String Option_Name,String Option_Status) {
        SQLiteDatabase db_learnoption = this.openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
        db_learnoption.execSQL("CREATE TABLE IF NOT EXISTS LearningOptionList(Option_ID VARCHAR,Group_Name VARCHAR,Option_Name VARCHAR,Option_Status VARCHAR);");


        String SQLiteQuery = "INSERT INTO LearningOptionList (Option_ID, Group_Name,Option_Name,Option_Status)" +
                " VALUES ('" + Option_ID + "','" + Group_Name + "','" + Option_Name + "','" + Option_Status +"');";
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

                    Intent intentPhotoCapture = new Intent(Activity_Register_New.this, CameraPhotoCapture.class);
                    startActivity(intentPhotoCapture);
                    Toast.makeText(getApplicationContext(), "choosen  take photo ",
                            Toast.LENGTH_SHORT).show();
                    digitalcamerabuttonpressed = true;


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
                createddate + "','" + createdby + "','" + str_receipt_no +"','" + selected_learnOption + "');";//str_receipt_no


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
            if(!str_logout_count_Status.equals("")){
                if (str_logout_count_Status.equals("Success")) {
                   // Toast.makeText(Activity_Register_New.this, "Success", Toast.LENGTH_SHORT).show();

                } else if (str_logout_count_Status.equals("Failure")){
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