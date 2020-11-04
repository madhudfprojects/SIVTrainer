package com.det.skillinvillage;

//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import static com.det.skillinvillage.Activity_Student_List.key_studentid;
import static com.det.skillinvillage.Activity_Student_List.sharedpreferenc_studentid;
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
    String selected_edu;
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
    String str_stuID="";
    String str_learningOpt="";
    int sel_learopt;
    static String str_paymentDate="",str_paymentDatedisp="";
    String[] educationArray = {"Select", "4th Standard", "5th Standard", "6th Standard", "7th Standard", "8th Standard","9th Standard"};
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
    Class_LearningOption obj_Class_LearningOption;


    SharedPreferences sharedpref_stuid_Obj;
    SharedPreferences sharedpref_loginuserid_Obj;
    SharedPreferences sharedpref_camera_Obj;
    public static final String sharedpreferenc_camera = "cameraflag";
    public static final String key_flagcamera = "flag_camera";

    Class_LearningOption[] Arrayclass_learningOption;
    String selected_learnOption,str_Learning_Mode;
    String str_flagforcamera;
    ArrayAdapter dataAdapter_learnop;

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


        sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        str_created_by = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

        sharedpref_stuid_Obj=getSharedPreferences(sharedpreferenc_studentid, Context.MODE_PRIVATE);
        str_stuID = sharedpref_stuid_Obj.getString(key_studentid, "").trim();

        sharedpref_camera_Obj=getSharedPreferences(sharedpreferenc_camera, Context.MODE_PRIVATE);
        str_flagforcamera = sharedpref_camera_Obj.getString(key_flagcamera, "").trim();

       if(!str_stuID.equals(""))
       {
           int_val_studentID=Integer.parseInt(str_stuID);
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

        if(isInternetPresent){
            AsyncCallWS_learningMode task=new AsyncCallWS_learningMode(Activity_EditRegistration.this);
            task.execute();
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

        ArrayAdapter dataAdapter_edu = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, educationArray);
        dataAdapter_edu.setDropDownViewResource(R.layout.spinnercenterstyle);
        education__edit_Sp.setAdapter(dataAdapter_edu);

        education__edit_Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_edu = education__edit_Sp.getSelectedItem().toString();
                Log.i("selected_edu", " : " + selected_edu);
                if (selected_edu.equals(educationArray[0]) || (selected_edu.equals(educationArray[1]))) {
                    marks_edit_et.setVisibility(View.GONE);
                    markslabel_edit_tv.setVisibility(View.GONE);
                } else {

                    marks_edit_et.setVisibility(View.VISIBLE);
                    markslabel_edit_tv.setVisibility(View.VISIBLE);
                    if (selected_edu.equals(educationArray[2])) {
                       // marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
                        //markslabel_edit_tv.setText(educationArray[1] + " Marks");
                        markslabel_edit_tv.setText(educationArray[1] + " Marks/Grade");

                    }
                    if (selected_edu.equals(educationArray[3])) {
                       // marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
                        //markslabel_edit_tv.setText(educationArray[2] + " Marks");
                        markslabel_edit_tv.setText(educationArray[2] + " Marks/Grade");

                    }
                    if (selected_edu.equals(educationArray[4])) {
                       // marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
                        markslabel_edit_tv.setText(educationArray[3] + " Marks/Grade");

                       // markslabel_edit_tv.setText(educationArray[3] + " Marks");
                    }
                    if (selected_edu.equals(educationArray[5])) {
                        marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
                        markslabel_edit_tv.setText(educationArray[1] + " Marks/Grade");

                      //  markslabel_edit_tv.setText(educationArray[4] + " Marks");
                    }
                    if (selected_edu.equals(educationArray[6])) {
                      //  marks_edit_et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});
                        markslabel_edit_tv.setText(educationArray[4] + " Marks/Grade");

                      //  markslabel_edit_tv.setText(educationArray[5] + " Marks");
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                //   String sel_option=Arrayclass_learningOption[position].toString();
                // selected_learnOption=learnoption_Spinner.getSelectedItem().toString();
                obj_Class_LearningOption = (Class_LearningOption) learnoption_Sp.getSelectedItem();
                selected_learnOption = obj_Class_LearningOption.getOption_ID();
                Log.e("tag","selected_learnOption="+selected_learnOption);

                int sel_learnOption_new = learnoption_Sp.getSelectedItemPosition();

               /* if(sel_learnOption_new!=sel_learopt) {
                    sel_learopt=sel_learnOption_new;
                }*/
                // studentlist[finalI].setLearningOption(sel_option);
                // Toast.makeText(Remarks.this, "Selected item:" + " " + Arrayclass_learningOption[position], Toast.LENGTH_SHORT).show();
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

                }else{
                    signimageinbytesArray=null;
                }


                if (Validation()) {

                    int_val_sandboxID = Integer.parseInt(str_sandboxID);
                    int_val_academicid = Integer.parseInt(str_academicID);
                    int_val_clusterid = Integer.parseInt(str_clusterID);
                    int_val_instituteid = Integer.parseInt(str_instID);
                    int_val_schoolid = Integer.parseInt(str_schoolID);
                    int_val_levelid = Integer.parseInt(str_levelID);

                    UpdateEditedData();
                }else{
                    Toast.makeText(getApplicationContext(), "Please enter all the required data",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
        if (paymentdate_edit_tv.getVisibility() == View.VISIBLE) {
            setcurrentdate();
        }

       // GetStudentRecord();

    }//oncreate

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
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail = getResizedBitmap(thumbnail, 400);
                Log.w(" gallery.", picturePath + "");
                photo_edit_iv.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);
            }
        }

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
            request.addProperty("Student_ID", int_val_studentID);

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
                    str_stuID = receive_studentid.toString();
                    Log.e("str_stuID", str_stuID);
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
                    str_stuID = receive_studentid.toString();
                Log.e("str_stuID", str_stuID);
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
                        Intent i=new Intent(Activity_EditRegistration.this,Activity_Student_List.class);
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
    public boolean Validation() {
        Boolean bname = true,bcontactno=true,breceiptno=true;
        if (studentName_edit_et.getText().toString().length() == 0) {
            studentName_edit_et.setError("Enter Student Name");

            Toast.makeText(getApplicationContext(), "Please Enter Student Name", Toast.LENGTH_SHORT).show();
            bname = false;

        }

        if (mobileno_edit_et.getText().toString().length() == 0 || mobileno_edit_et.getText().toString().length() < 10 || mobileno_edit_et.getText().toString().length() > 10) {
            mobileno_edit_et.setError("Enter Contact Number");
            Toast.makeText(getApplicationContext(), "Please Enter Correct Contact Number", Toast.LENGTH_SHORT).show();
            bcontactno = false;

        }


//        if (bname && bcontactno) {
//            return true;
//        } else {
//            return false;
//        }

        if(isAdmission) {

            if (receipt_no_edit_et.getText().toString().length() == 0) {
                receipt_no_edit_et.setError("Enter Receipt No.");
                Toast.makeText(getApplicationContext(), "Please Enter Receipt Number", Toast.LENGTH_SHORT).show();
                breceiptno = false;

            }


        }

        if(!isAdmission) {
            return bname && bcontactno && breceiptno;
        }else{
            return bname && bcontactno;
        }


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

    public void UpdateEditedData() {


        if (isInternetPresent) {
            UpdateEditedInfoTask task = new UpdateEditedInfoTask(Activity_EditRegistration.this);
            task.execute();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }

    }

    private class UpdateEditedInfoTask extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;
        Context context;

        @Override
        protected Void doInBackground(String... params) {

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            str_created_date = df.format(c);

            str_studentname=studentName_edit_et.getText().toString();
            str_fathername=fathername_edit_et.getText().toString();
            str_mothername=mothername_edit_et.getText().toString();
            str_mobileno=mobileno_edit_et.getText().toString();
            str_aadar=aadhaarno_edit_et.getText().toString();
            //str_edit_birthdate=dateofbirth_edit_tv.getText().toString();
            str_receiptno=receipt_no_edit_et.getText().toString();
            //str_marks=marks_edit_et.getText().toString();


            RegisterResponse=UpdateData();
            return null;
        }// doInBackground Process

        public UpdateEditedInfoTask(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1,R.style.AppCompatAlertDialogStyle);
        }

        @Override
        //Once WebService returns response
        protected void onPostExecute(Void result) {
            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();

            }

            if (!RegisterResponse) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), "Application Submitted", Toast.LENGTH_SHORT).show();
                if((path != null))
                {
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
                            Log.e("file not Deleted :" ,path);
                        }
                    }

                }

                if(CameraPhotoCapture.imagepathforupload != null)
                {
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


                Intent i=new Intent(getApplicationContext(),Activity_Student_List.class);
                startActivity(i);
                finish();

            }

        }// End of onPostExecute

        @Override
        //Make Progress Bar visible
        protected void onPreExecute() {
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }//End of onPreExecute

        @Override
        protected void onProgressUpdate(Void... values) {
        }//End of onProgressUpdates
    } // End of AsyncCallWS

    public Boolean UpdateData() {


        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String SOAP_ACTION = "http://mis.detedu.org:8089/UpdateStudentData";
        String METHOD_NAME = "UpdateStudentData";
        String NAMESPACE = "http://mis.detedu.org:8089/";

        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            if (isAdmission) {

                request.addProperty("Student_ID", int_val_studentID);
                request.addProperty("Sandbox_ID", int_val_sandboxID);
                request.addProperty("Academic_ID", int_val_academicid);
                request.addProperty("Cluster_ID", int_val_clusterid);
                request.addProperty("Institute_ID", int_val_instituteid);
                request.addProperty("Level_ID", int_val_levelid);
                request.addProperty("School_ID", int_val_schoolid);
                request.addProperty("Student_Name", str_studentname);
                request.addProperty("Gender", str_gender);
                request.addProperty("Birth_Date", str_edit_birthdate);
                request.addProperty("Education", selected_edu);
                request.addProperty("Marks", marks_edit_et.getText().toString());
                request.addProperty("Mobile", mobileno_edit_et.getText().toString());
                request.addProperty("Father_Name", str_fathername);
                request.addProperty("Mother_Name", str_mothername);
                request.addProperty("Student_Aadhar", str_aadar);
                request.addProperty("Student_Status", selected_studentstatus);
                request.addProperty("Admission_Fee", "");
                request.addProperty("Created_Date", str_created_date);  //<iYear>int</iYear>
                request.addProperty("Created_By", str_created_by);  //<Habit>string</Habit> //str_habit
                request.addProperty("File_Name", str_imgfile);
                request.addProperty("Receipt_Manual", str_receiptno);
                request.addProperty("Learning_Mode", selected_learnOption);
            } else {


            request.addProperty("Student_ID", int_val_studentID);
            request.addProperty("Sandbox_ID", int_val_sandboxID);
            request.addProperty("Academic_ID", int_val_academicid);
            request.addProperty("Cluster_ID", int_val_clusterid);
            request.addProperty("Institute_ID", int_val_instituteid);
            request.addProperty("Level_ID", int_val_levelid);
            request.addProperty("School_ID", int_val_schoolid);
            request.addProperty("Student_Name", str_studentname);
            request.addProperty("Gender", str_gender);
            request.addProperty("Birth_Date", str_edit_birthdate);
            request.addProperty("Education", selected_edu);
            request.addProperty("Marks", marks_edit_et.getText().toString());
            request.addProperty("Mobile", mobileno_edit_et.getText().toString());
            request.addProperty("Father_Name", str_fathername);
            request.addProperty("Mother_Name", str_mothername);
            request.addProperty("Student_Aadhar", str_aadar);
            request.addProperty("Student_Status", selected_studentstatus);
            if (selected_studentstatus.equals("Admission")) {
                Log.e("application_status", "Admission");
                request.addProperty("Admission_Fee", admissionfees_edit_et.getText().toString());
                request.addProperty("Receipt_Manual", receipt_no_edit_et.getText().toString());

            } else {
                Log.e("application_status", "Applicant");
                request.addProperty("Admission_Fee", "");
                request.addProperty("Receipt_Manual", "");

            }
            request.addProperty("Created_Date", str_created_date);  //<iYear>int</iYear>
            request.addProperty("Created_By", str_created_by);  //<Habit>string</Habit> //str_habit
            request.addProperty("File_Name", str_imgfile);
            request.addProperty("Learning_Mode", selected_learnOption);

        }
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            new MarshalBase64().register(envelope);
            envelope.dotNet = true;
            // Set output SOAP object
            Log.e("insert detailsedit", request.toString());
            envelope.setOutputSoapObject(request);


            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);


            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapObject response_new = (SoapObject) envelope.getResponse();
                Log.e("appsubResponse", response_new.toString());
                Log.e("status", response_new.getProperty(0).toString());

                if (response_new.getProperty(0).toString().contains("Student_Status")) {
                    if (response_new.getProperty(0).toString().contains("Student_Status=Applicant")) {
                        Log.e("Student_Status", "Applicant");
                        return true;
                    } else if (response_new.getProperty(0).toString().contains("Student_Status=Admission")) {

                        Log.e("Student_Status", "Admission");
                        return true;

                    }else if (response_new.getProperty(0).toString().contains("Student_Status=Error")) {
                        Log.e("Student_Status", "Error");
                        return false;

                    }


                }

            } catch (Throwable t) {
                Log.e("request fail tag", "request fail catched" + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("Receiver Error", "> " + t.getMessage());

        }


        return false;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Activity_EditRegistration.this,Activity_Student_List.class);
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
                Intent i=new Intent(Activity_EditRegistration.this,Activity_Student_List.class);
                startActivity(i);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
