package com.det.skillinvillage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.model.Institute;
import com.det.skillinvillage.model.Level;
import com.det.skillinvillage.model.Sandbox;
import com.det.skillinvillage.model.State;
import com.det.skillinvillage.model.Student;
import com.det.skillinvillage.model.StudentList;
import com.det.skillinvillage.model.Taluka;
import com.det.skillinvillage.model.Village;
import com.det.skillinvillage.model.Year;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.Key_username;
import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.key_userimage;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_userimage;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_username;
import static com.det.skillinvillage.MainActivity.sharedpreferencebook_usercredential;


public class Activity_ViewStudentList_new extends AppCompatActivity {

    ImageButton downarrow_ib, uparrow_ib;
    LinearLayout spinnerlayout_ll;
    TextView viewspinner_tv;
    FloatingActionButton addfarmerdetails_fab;


    Year[] arrayObj_Class_yearDetails2;
    Year Obj_Class_yearDetails;
    Sandbox[] arrayObj_Class_sandboxDetails2;
    Sandbox Obj_Class_sandboxDetails;
    Cluster[] arrayObj_Class_clusterDetails2;
    Cluster Obj_Class_ClusterDetails;
    Institute[] arrayObj_Class_instituteDetails2;
    Institute Obj_Class_InstDetails;
    Level[] arrayObj_Class_levelDetails2;
    Level Obj_Class_LevelDetails;
    //    Panchayat[] arrayObj_Class_GrampanchayatListDetails2;
//    Panchayat Obj_Class_GramanchayatDetails;
    Spinner yearlist_SP, sandboxlist_student_SP, clusterlist_student_SP, institutelist_student_SP, levellist_student_SP, applnstatuslist_student_SP, fees_student_SP;
    ListView student_LISTVIEW;
    int sel_yearsp = 0, sel_sandboxsp = 0, sel_institute = 0, sel_applnstatus = 0, sel_clustersp = 0, sel_taluksp = 0, sel_levelsp = 0;
    List<Sandbox> sandboxList;
    List<Cluster> clusterList;
    List<Institute> instituteList;
    List<Level> levelList;
    List<Year> yearList;



    private ArrayList<StudentList> originalViewStudentList = null;
    private ArrayList<StudentList> ViewStudentList_arraylist;
    StudentListViewAdapter studentListViewAdapter;
    String selected_leveladmissionfee = "", sp_strsand_ID = "", sp_straca_ID = "", sp_strClust_ID = "", sp_strInst_ID = "", sp_strLev_ID = "";

    String[] Fees_Filter_Array = {"All", "Fees Paid", "Fees Pending"};
    String[] Applnstatus_Array = {"Applicant", "Admission", "Dropout","Rejected"};
    int fees_filter_intval = 0;
    String selected_fees_filter = "", selected_studentstatus = "";


    public static final String Key_sel_yearsp = "sel_yearsp";
    public static final String Key_sel_sandboxsp = "sel_sandboxsp";
    public static final String Key_sel_clustersp = "sel_clustersp";
    public static final String Key_sel_institutesp = "sel_institutesp";
    public static final String Key_sel_levelsp = "sel_levelsp";
    public static final String Key_sel_applnstatus = "sel_applnstatus";
    public static final String key_studentid = "student_id_keyvalue";

    SharedPreferences sharedpref_spinner_Obj;
    public static final String sharedpreferenc_selectedspinner = "sharedpreferenc_selectedspinner";
    //StudentList Obj_Class_Studentlistdetails;
    //  int Stu_ID_Received=0;
    public static final String sharedpreferenc_studentid_new = "studentid_edit";
    EditText search_et;
    String Stu_ID_Received = "";

    StudentList[] arrayObj_Class_FarmerListDetails2;
    SharedPreferences sharedpref_username_Obj;
    SharedPreferences sharedpref_userimage_Obj;
    String str_Googlelogin_Username = "", str_Googlelogin_UserImg = "";

    LinearLayout offline_count;
    TextView edit_applicationCount_TV,new_applicationCount;
    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;
    StudentList[] class_farmerprofileoffline_array_obj;
    SharedPreferences sharedpreferencebook_usercredential_Obj;
    String str_loginuserID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__view_student_list_new);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Student List");

        sharedpref_spinner_Obj = getSharedPreferences(sharedpreferenc_selectedspinner, Context.MODE_PRIVATE);
        sel_yearsp = sharedpref_spinner_Obj.getInt(Key_sel_yearsp, 0);
        sel_sandboxsp = sharedpref_spinner_Obj.getInt(Key_sel_sandboxsp, 0);
        sel_clustersp = sharedpref_spinner_Obj.getInt(Key_sel_clustersp, 0);
        sel_institute = sharedpref_spinner_Obj.getInt(Key_sel_institutesp, 0);
        sel_levelsp = sharedpref_spinner_Obj.getInt(Key_sel_levelsp, 0);
        sel_applnstatus = sharedpref_spinner_Obj.getInt(Key_sel_applnstatus, 0);
        Stu_ID_Received = sharedpref_spinner_Obj.getString(key_studentid, "");
        sharedpref_username_Obj = getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
        str_Googlelogin_Username = sharedpref_username_Obj.getString(Key_username, "").trim();

        sharedpref_userimage_Obj = getSharedPreferences(sharedpreferenc_userimage, Context.MODE_PRIVATE);
        str_Googlelogin_UserImg = sharedpref_userimage_Obj.getString(key_userimage, "").trim();

        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_loginuserID = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();

        downarrow_ib = (ImageButton) findViewById(R.id.downarrow_IB);
        uparrow_ib = (ImageButton) findViewById(R.id.uparrow_IB);
        spinnerlayout_ll = (LinearLayout) findViewById(R.id.spinnerlayout_LL);
        viewspinner_tv = (TextView) findViewById(R.id.viewspinner_TV);
        addfarmerdetails_fab = (FloatingActionButton) findViewById(R.id.addfarmerdetails_fab);

        student_LISTVIEW = (ListView) findViewById(R.id.student_LISTVIEW);
        yearlist_SP = (Spinner) findViewById(R.id.yearlist_farmer_SP);
        sandboxlist_student_SP = (Spinner) findViewById(R.id.sandboxlist_student_SP);

        clusterlist_student_SP = (Spinner) findViewById(R.id.clusterlist_student_SP);
        institutelist_student_SP = (Spinner) findViewById(R.id.institutelist_student_SP);
        levellist_student_SP = (Spinner) findViewById(R.id.levellist_student_SP);
        applnstatuslist_student_SP = (Spinner) findViewById(R.id.applnstatuslist_student_SP);
        fees_student_SP = (Spinner) findViewById(R.id.fees_student_SP);
        spinnerlayout_ll = (LinearLayout) findViewById(R.id.spinnerlayout_LL);
        viewspinner_tv = (TextView) findViewById(R.id.viewspinner_TV);
        search_et = (EditText) findViewById(R.id.search_et);
        offline_count=(LinearLayout)findViewById(R.id.offline_count);
        new_applicationCount=(TextView)findViewById(R.id.new_applicationCount);
        edit_applicationCount_TV=(TextView)findViewById(R.id.edit_applicationCount);

        sandboxList = new ArrayList<>();
        clusterList = new ArrayList<>();
        instituteList = new ArrayList<>();
        levelList = new ArrayList<>();
        yearList = new ArrayList<>();
        ViewStudentList_arraylist = new ArrayList<StudentList>();

        studentListViewAdapter = new StudentListViewAdapter(Activity_ViewStudentList_new.this, ViewStudentList_arraylist);
        // farmerList = new ArrayList<>();

        ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, Applnstatus_Array);
        dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
        applnstatuslist_student_SP.setAdapter(dataAdapter);


        ArrayAdapter dataAdapter_feees = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, Fees_Filter_Array);
        dataAdapter_feees.setDropDownViewResource(R.layout.spinnercenterstyle);
        fees_student_SP.setAdapter(dataAdapter_feees);

        uparrow_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewspinner_tv.setText("Hide");
                spinnerlayout_ll.setVisibility(View.VISIBLE);
                downarrow_ib.setVisibility(View.VISIBLE);
                uparrow_ib.setVisibility(View.GONE);

            }
        });

        downarrow_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewspinner_tv.setText("Show");
                spinnerlayout_ll.setVisibility(View.GONE);
                downarrow_ib.setVisibility(View.GONE);
                uparrow_ib.setVisibility(View.VISIBLE);

            }
        });

        addfarmerdetails_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_ViewStudentList_new.this, Activity_Register_New.class);
                startActivity(i);
                finish();

            }
        });

        search_et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewspinner_tv.setText("Show");
                spinnerlayout_ll.setVisibility(View.GONE);
                downarrow_ib.setVisibility(View.GONE);
                uparrow_ib.setVisibility(View.VISIBLE);
                String text = search_et.getText().toString().toLowerCase(Locale.getDefault());
                studentListViewAdapter.filter(text, originalViewStudentList);

            }
        });

        //---------------------------------------
        fees_student_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_fees_filter = fees_student_SP.getSelectedItem().toString();

                Log.e("selected_fees_filter", " : " + selected_fees_filter);

                if (selected_fees_filter.equals(Fees_Filter_Array[0])) {
                    Update_ids_studentlist_listview(sp_straca_ID, sp_strsand_ID, sp_strClust_ID, sp_strInst_ID, sp_strLev_ID, selected_studentstatus, selected_fees_filter);
                } else if (selected_fees_filter.equals(Fees_Filter_Array[1])) {
                    // Update_ids_studentlist_listview_fees_Equal_zero(sp_strsand_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID,sp_strLev_ID,selected_studentstatus,selected_leveladmissionfee);
                    Update_ids_studentlist_listview_fees_Equal_zero(sp_straca_ID, sp_strsand_ID, sp_strClust_ID, sp_strInst_ID, sp_strLev_ID, selected_studentstatus, selected_fees_filter);

                    //  Update_ids_studentlist_listview_fees_Equal_zero(sp_strsand_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID,sp_strLev_ID,selected_studentstatus,200);

                } else if (selected_fees_filter.equals(Fees_Filter_Array[2])) {
                    // Update_ids_studentlist_listview_fees_GT_zero(sp_strsand_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID,sp_strLev_ID,selected_studentstatus,selected_fees_filter);
                    Update_ids_studentlist_listview_fees_GT_zero(sp_straca_ID, sp_strsand_ID, sp_strClust_ID, sp_strInst_ID, sp_strLev_ID, selected_studentstatus, selected_fees_filter);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sandboxlist_student_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_sandboxDetails = (Sandbox) sandboxlist_student_SP.getSelectedItem();
                sp_strsand_ID = Obj_Class_sandboxDetails.getSandboxID();
                String selected_sandboxName = sandboxlist_student_SP.getSelectedItem().toString();
                Log.i("selected_sandboxName", " : " + selected_sandboxName);
                Update_sandboxid_toyearspinner(sp_strsand_ID);
                Update_ids_studentlist_listview(sp_straca_ID, sp_strsand_ID, sp_strClust_ID, sp_strInst_ID, sp_strLev_ID, selected_studentstatus, selected_fees_filter);

                int sel_sandboxsp_new = sandboxlist_student_SP.getSelectedItemPosition();
                if (sel_sandboxsp_new != sel_sandboxsp) {
                    sel_sandboxsp = sel_sandboxsp_new;
                    ViewStudentList_arraylist.clear();
                    studentListViewAdapter.notifyDataSetChanged();
                    yearlist_SP.setSelection(0);
                    clusterlist_student_SP.setSelection(0);
                    institutelist_student_SP.setSelection(0);
                    levellist_student_SP.setSelection(0);
                    applnstatuslist_student_SP.setSelection(0);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        yearlist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_yearDetails = (Year) yearlist_SP.getSelectedItem();
                sp_straca_ID = Obj_Class_yearDetails.getAcademicID();
                String selected_academicname = yearlist_SP.getSelectedItem().toString();

                Update_clusterid_spinner(sp_strsand_ID, sp_straca_ID);
                //  Update_ids_studentlist_listview(sp_straca_ID,sp_strsand_ID, sp_strClust_ID, sp_strInst_ID,sp_strLev_ID,selected_studentstatus,selected_fees_filter);

                int sel_yearsp_new = yearlist_SP.getSelectedItemPosition();
                if (sel_yearsp_new != sel_yearsp) {
                    sel_yearsp = sel_yearsp_new;
                    ViewStudentList_arraylist.clear();
                    studentListViewAdapter.notifyDataSetChanged();
                    // sandboxlist_student_SP.setSelection(0);
                    clusterlist_student_SP.setSelection(0);
                    institutelist_student_SP.setSelection(0);
                    levellist_student_SP.setSelection(0);
                    applnstatuslist_student_SP.setSelection(0);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        clusterlist_student_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                Obj_Class_ClusterDetails = (Cluster) clusterlist_student_SP.getSelectedItem();
                sp_strClust_ID = Obj_Class_ClusterDetails.getClusterID();
                String selected_clusterName = clusterlist_student_SP.getSelectedItem().toString();
                Log.i("selected_clustername", " : " + selected_clusterName);
                // Log.e("sp_strClust_ID.." ,sp_strClust_ID);


                Update_InstId_spinner(sp_strClust_ID, sp_straca_ID);
                // Update_ids_studentlist_listview(sp_straca_ID,sp_strsand_ID, sp_strClust_ID, sp_strInst_ID,sp_strLev_ID,selected_studentstatus,selected_fees_filter);


                int sel_clustersp_new = clusterlist_student_SP.getSelectedItemPosition();
                if (sel_clustersp_new != sel_clustersp) {
                    sel_clustersp = sel_clustersp_new;
                    ViewStudentList_arraylist.clear();
                    studentListViewAdapter.notifyDataSetChanged();
                    // yearlist_SP.setSelection(0);
                    institutelist_student_SP.setSelection(0);
                    levellist_student_SP.setSelection(0);
                    applnstatuslist_student_SP.setSelection(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        institutelist_student_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_InstDetails = (Institute) institutelist_student_SP.getSelectedItem();
                sp_strInst_ID = Obj_Class_InstDetails.getInstituteID();
                String selected_instituteName = institutelist_student_SP.getSelectedItem().toString();
                Log.i("selected_instituteName", " : " + selected_instituteName);

//                Log.e("sp_strsand_ID.." ,sp_strsand_ID);
//                Log.e("sp_straca_ID.." ,sp_straca_ID);
//                Log.e("sp_strClust_ID.." ,sp_strClust_ID);
//                Log.e("sp_strInst_ID.." ,sp_strInst_ID);
                Update_LevelId_spinner(sp_straca_ID, sp_strClust_ID, sp_strInst_ID);

                if ((!sp_straca_ID.equals("")) || (!sp_strsand_ID.equals("")) || (!sp_strClust_ID.equals("")) || (!sp_strInst_ID.equals("")) || (!sp_strLev_ID.equals("")) || (!selected_studentstatus.equals("")) || (!selected_fees_filter.equals(""))) {
                    Update_ids_studentlist_listview(sp_straca_ID, sp_strsand_ID, sp_strClust_ID, sp_strInst_ID, sp_strLev_ID, selected_studentstatus, selected_fees_filter);
                }


                int sel_institute_new = institutelist_student_SP.getSelectedItemPosition();
                if (sel_institute_new != sel_institute) {
                    sel_institute = sel_institute_new;
                    ViewStudentList_arraylist.clear();
                    studentListViewAdapter.notifyDataSetChanged();
                    //sandboxlist_student_SP.setSelection(0);
                    //  yearlist_SP.setSelection(0);
                    // clusterlist_student_SP.setSelection(0);
                    levellist_student_SP.setSelection(0);
                    applnstatuslist_student_SP.setSelection(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        levellist_student_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_LevelDetails = (Level) levellist_student_SP.getSelectedItem();
                sp_strLev_ID = Obj_Class_LevelDetails.getLevelID();

                String selected_levelName = levellist_student_SP.getSelectedItem().toString();
                selected_leveladmissionfee = Obj_Class_LevelDetails.getAdmissionFee();

                Log.i("selected_levelName", " : " + selected_levelName);
//                if(!selected_studentstatus.equals("")) {
//                    Update_ViewStuListID_spinner(sp_strsand_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID, sp_strLev_ID, selected_studentstatus);
//
//                }
                // Update_ids_studentlist_listview(sp_straca_ID,sp_strsand_ID, sp_strClust_ID, sp_strInst_ID,sp_strLev_ID,selected_studentstatus,selected_fees_filter);
                if ((!sp_straca_ID.equals("")) || (!sp_strsand_ID.equals("")) || (!sp_strClust_ID.equals("")) || (!sp_strInst_ID.equals("")) || (!sp_strLev_ID.equals("")) || (!selected_studentstatus.equals("")) || (!selected_fees_filter.equals(""))) {
                    Update_ids_studentlist_listview(sp_straca_ID, sp_strsand_ID, sp_strClust_ID, sp_strInst_ID, sp_strLev_ID, selected_studentstatus, selected_fees_filter);
                }


                int sel_level_new = levellist_student_SP.getSelectedItemPosition();
                if (sel_level_new != sel_levelsp) {
                    sel_levelsp = sel_level_new;
                    ViewStudentList_arraylist.clear();
                    studentListViewAdapter.notifyDataSetChanged();
                    //sandboxlist_student_SP.setSelection(0);
                    // yearlist_SP.setSelection(0);
                    // clusterlist_student_SP.setSelection(0);
                    //  institutelist_student_SP.setSelection(0);
                    applnstatuslist_student_SP.setSelection(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        applnstatuslist_student_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Obj_Class_StudentStatus = (Class_StudentStatus) applnstatuslist_student_SP.getSelectedItem();
                selected_studentstatus = parent.getSelectedItem().toString();
                Log.e("selected_studentstatus", selected_studentstatus);

               /* Log.e("sp_strsand_ID.appl.." ,sp_strsand_ID);
                Log.e("sp_straca_ID.appl.." ,sp_straca_ID);
                Log.e("sp_strClust_ID.appl.." ,sp_strClust_ID);
                Log.e("sp_strInst_ID...appl." ,sp_strInst_ID);
                Log.e("sp_strLev_ID...appl..." ,sp_strLev_ID);
                Log.e("selected_studentstatus" , selected_studentstatus);*/

                // Update_ViewStuListID_spinner(sp_strsand_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID,sp_strLev_ID,selected_studentstatus);


                if (sandboxlist_student_SP.getSelectedItem().toString().equalsIgnoreCase("Select")
                        && clusterlist_student_SP.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                    ViewStudentList_arraylist.clear();
                    studentListViewAdapter.notifyDataSetChanged();

                } else {
                    //  Log.e("yearid", sp_stryear_ID + "-" + sp_strstate_ID + "-" + sp_strdistrict_ID + "-" + sp_strTaluk_ID + "-" + sp_strVillage_ID + "-" + sp_strgrampanchayat_ID);

                    Update_ids_studentlist_listview(sp_straca_ID, sp_strsand_ID, sp_strClust_ID, sp_strInst_ID, sp_strLev_ID, selected_studentstatus, selected_fees_filter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        student_LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                StudentList item = (StudentList) studentListViewAdapter.getItem(i);
                Log.e("stuID_onclick", item.getStudentID());
                Intent intent = new Intent(Activity_ViewStudentList_new.this, Activity_EditRegistration.class);
                SharedPreferences.Editor myprefs_spinner = sharedpref_spinner_Obj.edit();
                // sharedpref_stuid_Obj_new=getSharedPreferences(sharedpreferenc_studentid_new, Context.MODE_PRIVATE);

                myprefs_spinner.putInt(Key_sel_yearsp, sel_yearsp);
                myprefs_spinner.putInt(Key_sel_sandboxsp, sel_sandboxsp);
                myprefs_spinner.putInt(Key_sel_clustersp, sel_clustersp);
                myprefs_spinner.putInt(Key_sel_institutesp, sel_institute);
                myprefs_spinner.putInt(Key_sel_levelsp, sel_levelsp);
                myprefs_spinner.putInt(Key_sel_applnstatus, sel_applnstatus);
                try {
                    myprefs_spinner.putString(key_studentid, item.getStudentID());
                    Log.e("stulist_onclick--", item.getStudentID());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                myprefs_spinner.apply();
                startActivity(intent);

            }
        });
        //=========================================================
        uploadfromDB_Sandboxlist();
        uploadfromDB_Yearlist();
        uploadfromDB_Clusterlist();
        uploadfromDB_Institutelist();
        uploadfromDB_Levellist();



        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if (isInternetPresent) {

            fetch_newApplicationData();
            fetchEditApplicationData();
           // offline_count.setVisibility(View.GONE);
        } else {

            fetch_newApplicationcount();
            fetchEditApplicationCount();

        }
    }



    ////////////methods which are called in offline count display/////
    public void fetch_newApplicationcount() {
        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR);");
        // Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE Stud_TempId LIKE'" + "newtemp%" + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE UpadateOff_Online='"+ "offline"  + "'", null);
        int x = cursor1.getCount();
        Log.e("marketing_studaddd", String.valueOf(x));
        int i = 0;
        class_farmerprofileoffline_array_obj = new StudentList[x];
        if (x > 0) {
            offline_count.setVisibility(View.VISIBLE);
            new_applicationCount.setText(String.valueOf(x));
        }
        db1.close();
    }
    public void fetchEditApplicationCount() {
        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE UpadateOff_Online='"+ "offlineedit"  + "'", null);
        int x = cursor1.getCount();
        Log.e("marketing_edit", String.valueOf(x));
        int i = 0;
        class_farmerprofileoffline_array_obj = new StudentList[x];
        if (x > 0) {
            offline_count.setVisibility(View.VISIBLE);
            edit_applicationCount_TV.setText(String.valueOf(x));
        }
        db1.close();
    }
    public void fetchEditApplicationData()
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");
        //  Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE Stud_TempId LIKE'"+ "edittemp%" + "'", null);
        //   Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE StudentID='"+ stuid  + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE UpadateOff_Online='"+ "offlineedit"  + "'", null);

        int x = cursor1.getCount();
        Log.e("offlineeditedcount", String.valueOf(x));

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
                    innerObj_Class_SandboxList.setAlternate_Mobile(cursor1.getString(cursor1.getColumnIndex("alternate_mobile")));
                    innerObj_Class_SandboxList.setAdmission_date(cursor1.getString(cursor1.getColumnIndex("admission_date")));
                    innerObj_Class_SandboxList.setAdmission_remarks(cursor1.getString(cursor1.getColumnIndex("admission_remarks")));

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
            UpdateStudentDetails(j);
        }
//        if (x == 0) {
//            fetch_DB_newfarmpond_offline_data();
//        }

    }
    public void UpdateStudentDetails(int j) {
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
        // request.setDistrict_Name(class_farmerprofileoffline_array_obj[j].getDistrict_Name());
        request.setTaluk_ID(class_farmerprofileoffline_array_obj[j].getTaluk_ID());
        //  request.setTaluk_Name(class_farmerprofileoffline_array_obj[j].getTaluk_Name());
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
        request.setAdmissionFee(class_farmerprofileoffline_array_obj[j].getPaidFee());
        request.setStudentStatus(class_farmerprofileoffline_array_obj[j].getStudentStatus());
        request.setStudentID(class_farmerprofileoffline_array_obj[j].getStudentID());
        request.setSchoolID(String.valueOf(class_farmerprofileoffline_array_obj[j].getSchoolID()));
        request.setCreatedBy(str_loginuserID);
        request.setCreatedDate(class_farmerprofileoffline_array_obj[j].getCreatedDate());
        //   request.setCreatedDate("2020-12-05");
        request.setReceiptManual(class_farmerprofileoffline_array_obj[j].getReceiptNo());
        //    request.setLearningMode(class_farmerprofileoffline_array_obj[j].g());
        request.setTemp_ID(class_farmerprofileoffline_array_obj[j].getTempID());
        request.setFileName(class_farmerprofileoffline_array_obj[j].getStudentPhoto());
//        Log.e("tag", "FarmerFirstName==" + class_farmerprofileoffline_array_obj[j].getStr_fname());
//        Log.e("tag", "FarmerID==" + class_farmerprofileoffline_array_obj[j].getStr_farmerID());
        request.setLearningMode(class_farmerprofileoffline_array_obj[j].getLearningMode());
        request.setApplication_Type("SIV");
        request.setDivision_ID(null);
        request.setAlternate_Mobile(class_farmerprofileoffline_array_obj[j].getAlternate_Mobile());
        request.setAdmission_Date(class_farmerprofileoffline_array_obj[j].getAdmission_date());
        request.setAdmission_Remarks(class_farmerprofileoffline_array_obj[j].getAdmission_remarks());
        Call<AddStudentDetailsResponse> call = userService1.Post_ActionStudent(request);

        Log.e("Edit", "Request 33: " + new Gson().toJson(request));

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_ViewStudentList_new.this);
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


                        Log.e("tag", "editstudentresponse ID=" + addFarmerResList.get(i).getStudentID());

                        String str_response_stustatus= String.valueOf(addFarmerResList.get(i).getStudentStatus());
                        String str_response_student_id = String.valueOf(addFarmerResList.get(i).getStudentID());
                        String str_response_tempId = String.valueOf(addFarmerResList.get(i).getTemp_ID());
                        String str_response_applnNo=addFarmerResList.get(i).getApplicationNo();
//                    Log.e("tag", "getMobileTempID=" + addFarmerResList.getMobileTempID());
//                    Log.e("tag", "getFarmerCode=" + addFarmerResList.getFarmerCode());
//                    Log.e("tag", "getFarmerID=" + addFarmerResList.getFarmerID());

                        SQLiteDatabase db1 = getApplication().openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);

                        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");


                        ContentValues cv = new ContentValues();
                        cv.put("StudentID", str_response_student_id);
                        cv.put("Stud_TempId", str_response_tempId);
                        cv.put("UpadateOff_Online", "onlineedit");
                        cv.put("ApplicationNo", str_response_applnNo);
                        cv.put("StudentStatus", str_response_stustatus);

                        //   cv.put("UploadedStatusFarmerprofile", 10);

                        db1.update("StudentDetailsRest", cv, "Stud_TempId = ?", new String[]{str_response_tempId});
                        db1.close();

                        Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();

                        progressDoalog.dismiss();


                    }

                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // ??? and use it to show error information

                    // ??? or just log the issue like we???re doing :)
                    Log.d("error message", error.getMsg());

                    Toast.makeText(Activity_ViewStudentList_new.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(Activity_ViewStudentList_new.this, "WS:Error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }
    public void fetch_newApplicationData()
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");
        //  Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE Stud_TempId LIKE'"+ "newtemp%" + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT * FROM StudentDetailsRest WHERE UpadateOff_Online='"+ "offline"  + "'", null);

        int x = cursor1.getCount();
        Log.e("offlinenewdatacount", String.valueOf(x));

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
                    innerObj_Class_SandboxList.setAlternate_Mobile(cursor1.getString(cursor1.getColumnIndex("alternate_mobile")));
                    innerObj_Class_SandboxList.setAdmission_date(cursor1.getString(cursor1.getColumnIndex("admission_date")));
                    innerObj_Class_SandboxList.setAdmission_remarks(cursor1.getString(cursor1.getColumnIndex("admission_remarks")));

                    class_farmerprofileoffline_array_obj[i] = innerObj_Class_SandboxList;
                    Log.e("fetch_DB_offline_data",cursor1.getString(cursor1.getColumnIndex("StudentName")));
                    Log.e("fetch_DB_offline_data",cursor1.getString(cursor1.getColumnIndex("StudentID")));
                    i++;
                } while (cursor1.moveToNext());
            }//if ends

        }


        db1.close();

        Log.e("length", String.valueOf(class_farmerprofileoffline_array_obj.length));

        for (int j = 0; j < class_farmerprofileoffline_array_obj.length; j++) {
            AddNewStudentData(j);
        }


    }
    public void AddNewStudentData(int j) {
        Interface_userservice userService1;
        userService1 = Class_ApiUtils.getUserService();
        String StudentID= String.valueOf(class_farmerprofileoffline_array_obj[j].getStudentID());
        Log.e("cLASS","StudentID..ABC "+class_farmerprofileoffline_array_obj[j].getStudentID());

        Log.e("tag","StudentID=="+StudentID);
        if(StudentID.startsWith("temp")){
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
        //request.setDistrict_Name(class_farmerprofileoffline_array_obj[j].getDistrict_Name());
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
        request.setAdmissionFee(class_farmerprofileoffline_array_obj[j].getPaidFee());
        request.setStudentStatus(class_farmerprofileoffline_array_obj[j].getStudentStatus());
        request.setStudentID(class_farmerprofileoffline_array_obj[j].getStudentID());
        request.setSchoolID(String.valueOf(class_farmerprofileoffline_array_obj[j].getSchoolID()));
        request.setCreatedBy(str_loginuserID);
        request.setCreatedDate(class_farmerprofileoffline_array_obj[j].getCreatedDate());
        //   request.setCreatedDate("2020-12-05");
        request.setReceiptManual(class_farmerprofileoffline_array_obj[j].getReceiptNo());
        //    request.setLearningMode(class_farmerprofileoffline_array_obj[j].g());
        request.setTemp_ID(class_farmerprofileoffline_array_obj[j].getTempID());
        request.setFileName(class_farmerprofileoffline_array_obj[j].getStudentPhoto());
//        Log.e("tag", "FarmerFirstName==" + class_farmerprofileoffline_array_obj[j].getStr_fname());
//        Log.e("tag", "FarmerID==" + class_farmerprofileoffline_array_obj[j].getStr_farmerID());
        request.setLearningMode(class_farmerprofileoffline_array_obj[j].getLearningMode());
        request.setApplication_Type("SIV");
        request.setDivision_ID(null);
        request.setAlternate_Mobile(class_farmerprofileoffline_array_obj[j].getAlternate_Mobile());
        request.setAdmission_Date(class_farmerprofileoffline_array_obj[j].getAdmission_date());
        request.setAdmission_Remarks(class_farmerprofileoffline_array_obj[j].getAdmission_remarks());

        Call<AddStudentDetailsResponse> call = userService1.Post_ActionStudent(request);

        Log.e("TAG", "Request 33: " + new Gson().toJson(request));
   //     Log.e("TAG", "Request: " + request.toString());

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_ViewStudentList_new.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<AddStudentDetailsResponse>() {
            @Override
            public void onResponse(Call<AddStudentDetailsResponse> call, Response<AddStudentDetailsResponse> response) {
                //    Log.e("tag", "response =" + response.body().toString());


                if (response.isSuccessful()) {
                    List<Class_Response_AddStudentDetailsList> addFarmerResList = response.body().getLst();
                    for (int i = 0; i < addFarmerResList.size(); i++) {


                        Log.e("tag", "addstudentresponse ID=" + addFarmerResList.get(i).getStudentID());


                        String str_response_student_id = String.valueOf(addFarmerResList.get(i).getStudentID());
                        String str_response_tempId = String.valueOf(addFarmerResList.get(i).getTemp_ID());
                        String str_response_applnNo = String.valueOf(addFarmerResList.get(i).getApplicationNo());
                        String str_response_stustatus= String.valueOf(addFarmerResList.get(i).getStudentStatus());



                        SQLiteDatabase db1 = getApplication().openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);

                        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR, ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR, Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");


                        ContentValues cv = new ContentValues();
                        cv.put("StudentID", str_response_student_id);
                        cv.put("Stud_TempId", str_response_tempId);
                        cv.put("UpadateOff_Online", "online");
                        cv.put("ApplicationNo", str_response_applnNo);
                        cv.put("StudentStatus", str_response_stustatus);

                        db1.update("StudentDetailsRest", cv, "Stud_TempId = ?", new String[]{str_response_tempId});
                        db1.close();

                        Toast.makeText(getApplicationContext(), "Submitted Successfully", Toast.LENGTH_SHORT).show();

                        progressDoalog.dismiss();

                    }

                } else {
                    progressDoalog.dismiss();

                    DefaultResponse error = ErrorUtils.parseError(response);
                    // ??? and use it to show error information

                    // ??? or just log the issue like we???re doing :)
                    Log.d("error message", error.getMsg());

                    Toast.makeText(Activity_ViewStudentList_new.this, error.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(Activity_ViewStudentList_new.this, "WS:Error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call
    }

    //////////////////////////////////////eND//////////////////////////
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
            yearlist_SP.setAdapter(dataAdapter);
            if (x > sel_yearsp) {
                yearlist_SP.setSelection(sel_yearsp);
            }
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
            yearlist_SP.setAdapter(dataAdapter);
            if (x > sel_clustersp) {
                yearlist_SP.setSelection(sel_yearsp);
            }
        }

    }

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

            ArrayAdapter<Sandbox> dataAdapter = new ArrayAdapter<Sandbox>(Activity_ViewStudentList_new.this, R.layout.spinnercenterstyle, arrayObj_Class_sandboxDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            sandboxlist_student_SP.setAdapter(dataAdapter);
            if (x > sel_sandboxsp) {
                sandboxlist_student_SP.setSelection(sel_sandboxsp);
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
            clusterlist_student_SP.setAdapter(dataAdapter);
            if (x > sel_clustersp) {
                clusterlist_student_SP.setSelection(sel_clustersp);
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
            clusterlist_student_SP.setAdapter(dataAdapter);
            if (x > sel_clustersp) {
                clusterlist_student_SP.setSelection(sel_clustersp);
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
            institutelist_student_SP.setAdapter(dataAdapter);
            if (x > sel_institute) {
                institutelist_student_SP.setSelection(sel_institute);
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
            institutelist_student_SP.setAdapter(dataAdapter);
            if (x > sel_taluksp) {
                institutelist_student_SP.setSelection(sel_taluksp);
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


                arrayObj_Class_levelDetails2[i] = innerObj_Class_levelList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_village.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_levelDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            levellist_student_SP.setAdapter(dataAdapter);
            if (x > sel_levelsp) {
                levellist_student_SP.setSelection(sel_levelsp);
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
            levellist_student_SP.setAdapter(dataAdapter);
            if (x > sel_levelsp) {
                levellist_student_SP.setSelection(sel_levelsp);
            }

        }


    }


    //    public void Update_ids_studentlist_listview() {
    public void Update_ids_studentlist_listview(String str_yearid, String str_sandboxid, String str_clustid, String str_instid, String str_levelid, String studentstatus, String str_fees_filter) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);

//        db1.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
//                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
//                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
//                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
//                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
//                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
//                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR,Farmer_Gender VARCHAR);");


        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR,ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR,Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");


        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StudentDetailsRest  WHERE AcademicID='" + str_yearid + "' AND SandboxID='" + str_sandboxid + "' AND ClusterID='" + str_clustid + "'AND InstituteID='" + str_instid + "'AND LevelID='" + str_levelid + "' AND StudentStatus='" + studentstatus + "'", null);
        // Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StudentDetailsRest ", null);


        int x = cursor1.getCount();
        Log.d("cursor Studentcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_FarmerListDetails2 = new StudentList[x];
        // originalViewStudentList.clear();
        ViewStudentList_arraylist.clear();

//        if (cursor1.moveToFirst()) {
        if (cursor1.moveToLast()) {

            do {
//                 VARCHAR, VARCHAR, VARCHAR," +
//                " VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR,InstituteName VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR," +
//                        " VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR,SchoolID VARCHAR, VARCHAR, VARCHAR, VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR)   " +
//

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
                innerObj_Class_SandboxList.setAlternate_Mobile(cursor1.getString(cursor1.getColumnIndex("alternate_mobile")));
                innerObj_Class_SandboxList.setAdmission_date(cursor1.getString(cursor1.getColumnIndex("admission_date")));
                innerObj_Class_SandboxList.setAdmission_remarks(cursor1.getString(cursor1.getColumnIndex("admission_remarks")));

                String str_FarmerName = cursor1.getString(cursor1.getColumnIndex("StudentName"));
                //String str_Farmercode = cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_Farmer_Code"));
//                String str_FarmerImage = cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerImage"));
//                String str_farmerpondcount = cursor1.getString(cursor1.getColumnIndex("Farmpondcount"));

                byte[] str_LocalImg = cursor1.getBlob(cursor1.getColumnIndex("Base64image"));
                Log.e("tag", "applnno oo=" + cursor1.getString(cursor1.getColumnIndex("ApplicationNo")));
                Log.e("tag", "bal oo=" + cursor1.getString(cursor1.getColumnIndex("BalanceFee")));

                arrayObj_Class_FarmerListDetails2[i] = innerObj_Class_SandboxList;
                StudentList item;

                //item = new Class_FarmerListDetails(str_Farmercode, str_FarmerName);
                item = new StudentList(
                        cursor1.getString(cursor1.getColumnIndex("AcademicID")),
                        cursor1.getString(cursor1.getColumnIndex("AcademicName")),
                        cursor1.getString(cursor1.getColumnIndex("AdmissionFee")),
                        cursor1.getString(cursor1.getColumnIndex("ApplicationNo")),
                        cursor1.getString(cursor1.getColumnIndex("BalanceFee")),
                        cursor1.getString(cursor1.getColumnIndex("BirthDate")),
                        cursor1.getString(cursor1.getColumnIndex("ClusterID")),
                        cursor1.getString(cursor1.getColumnIndex("ClusterName")),
                        cursor1.getString(cursor1.getColumnIndex("CreatedDate")),
                        cursor1.getString(cursor1.getColumnIndex("Education")),
                        cursor1.getString(cursor1.getColumnIndex("FatherName")),
                        cursor1.getString(cursor1.getColumnIndex("Gender")),
                        cursor1.getString(cursor1.getColumnIndex("InstituteName")),
                        cursor1.getString(cursor1.getColumnIndex("InstituteID")),
                        cursor1.getString(cursor1.getColumnIndex("LevelID")),
                        cursor1.getString(cursor1.getColumnIndex("LevelName")),
                        cursor1.getString(cursor1.getColumnIndex("Marks4")),
                        cursor1.getString(cursor1.getColumnIndex("Marks5")),
                        cursor1.getString(cursor1.getColumnIndex("Marks6")),
                        cursor1.getString(cursor1.getColumnIndex("Marks7")),
                        cursor1.getString(cursor1.getColumnIndex("Marks8")),
                        cursor1.getString(cursor1.getColumnIndex("MotherName")),
                        cursor1.getString(cursor1.getColumnIndex("PaidFee")),
                        cursor1.getString(cursor1.getColumnIndex("ReceiptNo")),
                        Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SandboxID"))),
                        cursor1.getString(cursor1.getColumnIndex("SandboxName")),
                        Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SchoolID"))),
                        cursor1.getString(cursor1.getColumnIndex("SchoolName")),
                        cursor1.getString(cursor1.getColumnIndex("StudentAadhar")),
                        cursor1.getString(cursor1.getColumnIndex("StudentPhoto")),
                        cursor1.getString(cursor1.getColumnIndex("StudentStatus")),
                        cursor1.getString(cursor1.getColumnIndex("StudentName")),
                        cursor1.getString(cursor1.getColumnIndex("StudentID")),

                        cursor1.getString(cursor1.getColumnIndex("Learning_Mode")),
                        cursor1.getString(cursor1.getColumnIndex("stateid")),
                        cursor1.getString(cursor1.getColumnIndex("statename")),
                        cursor1.getString(cursor1.getColumnIndex("districtid")),
                        cursor1.getString(cursor1.getColumnIndex("districtname")),
                        cursor1.getString(cursor1.getColumnIndex("talukid")),
                        cursor1.getString(cursor1.getColumnIndex("talukname")),
                        cursor1.getString(cursor1.getColumnIndex("villageid")),
                        cursor1.getString(cursor1.getColumnIndex("villagename")),
                        cursor1.getString(cursor1.getColumnIndex("student_address"))


                );


                //farmer_image


                ViewStudentList_arraylist.add(item);
                // Log.e("ListfetchingAplNo", item.getApplicationNo());
                // Log.e("str_FarmerImage", str_FarmerImage);

                i++;

//            } while (cursor1.moveToNext());

            } while (cursor1.moveToPrevious());

        }//if ends

        db1.close();
        if (x > 0) {

//            originalViewStudentList.clear();
//           // ViewStudentList_arraylist.clear();
            originalViewStudentList = new ArrayList<StudentList>();
            originalViewStudentList.addAll(ViewStudentList_arraylist);

            if (ViewStudentList_arraylist != null) {
                studentListViewAdapter.notifyDataSetChanged();
                student_LISTVIEW.setAdapter(studentListViewAdapter);

            }
//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_FarmerListDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            student_LISTVIEW.setAdapter(dataAdapter);


//            CustomAdapter_new dataAdapter=new CustomAdapter_new();
//            student_LISTVIEW.setAdapter(dataAdapter);

        } else {
            studentListViewAdapter.notifyDataSetChanged();
            student_LISTVIEW.setAdapter(studentListViewAdapter);
            // Toast.makeText(Activity_ViewStudentList_new.this, "No  data found", Toast.LENGTH_SHORT).show();
        }

    }


    //    public void Update_ids_studentlist_listview_fees_Equal_zero(String str_yearid,String str_sandboxid,String str_clustid,String str_instid,String str_levelid,String studentstatus,String selected_leveladmissionfee) {
//
//        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
//
////        db1.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
////                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
////                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
////                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
////                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
////                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
////                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR,Farmer_Gender VARCHAR);");
//
//
//        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR,ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR,Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR);");
//
//
////        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StudentDetailsRest  WHERE AcademicID='" + str_yearid + "' AND SandboxID='" + str_sandboxid + "' AND ClusterID='" + str_clustid + "'AND InstituteID='" + str_instid + "'AND LevelID='" + str_levelid + "' AND StudentStatus='" + studentstatus + "' AND BalanceFee='" + fees_filter_intval + "'", null);
//        // Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StudentDetailsRest ", null);
//      //  Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StudentDetailsRest  WHERE AcademicID='" + str_yearid + "' AND SandboxID='" + str_sandboxid + "' AND ClusterID='" + str_clustid + "'AND InstituteID='" + str_instid + "'AND LevelID='" + str_levelid + "' AND StudentStatus='" + studentstatus + "' AND AdmissionFee='"+ str_fees_filter +"'", null);
//
//
//        @SuppressLint("Recycle")
//        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StudentDetailsRest  WHERE AcademicID='" + str_yearid + "' AND SandboxID='" + str_sandboxid + "' AND ClusterID='" + str_clustid + "'AND InstituteID='" + str_instid + "'AND LevelID='" + str_levelid + "' AND StudentStatus='" + studentstatus + "' AND PaidFee='"+ selected_leveladmissionfee +"'", null);
//
//        int x = cursor1.getCount();
//        Log.e("fees=0", Integer.toString(x));
//
//        int i = 0;
//         arrayObj_Class_FarmerListDetails2 = new StudentList[x];
//        // originalViewStudentList.clear();
//        ViewStudentList_arraylist.clear();
//
//        //if (cursor1.moveToFirst()) {
//            if (cursor1.moveToLast()) {
//
//            do {
////                 VARCHAR, VARCHAR, VARCHAR," +
////                " VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR,InstituteName VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR," +
////                        " VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR,SchoolID VARCHAR, VARCHAR, VARCHAR, VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR)   " +
////
//
//                StudentList innerObj_Class_SandboxList = new StudentList();
//                innerObj_Class_SandboxList.setAcademicID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("AcademicID"))));
//                innerObj_Class_SandboxList.setAcademicName(cursor1.getString(cursor1.getColumnIndex("AcademicName")));
//                innerObj_Class_SandboxList.setAdmissionFee(cursor1.getString(cursor1.getColumnIndex("AdmissionFee")));
//                innerObj_Class_SandboxList.setApplicationNo(cursor1.getString(cursor1.getColumnIndex("ApplicationNo")));
//                innerObj_Class_SandboxList.setBalanceFee(cursor1.getString(cursor1.getColumnIndex("BalanceFee")));
//                innerObj_Class_SandboxList.setBirthDate(cursor1.getString(cursor1.getColumnIndex("BirthDate")));
//                innerObj_Class_SandboxList.setClusterID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("ClusterID"))));//DispFarmerTable_Farmer_Code VARCHAR
//                innerObj_Class_SandboxList.setClusterName(cursor1.getString(cursor1.getColumnIndex("ClusterName")));//DispFarmerTable_Farmer_Code VARCHAR
//                innerObj_Class_SandboxList.setCreatedDate(cursor1.getString(cursor1.getColumnIndex("CreatedDate")));
//                innerObj_Class_SandboxList.setEducation(cursor1.getString(cursor1.getColumnIndex("Education")));
//                innerObj_Class_SandboxList.setFatherName(cursor1.getString(cursor1.getColumnIndex("FatherName")));
//                innerObj_Class_SandboxList.setGender(cursor1.getString(cursor1.getColumnIndex("Gender")));
//                innerObj_Class_SandboxList.setInstituteName(cursor1.getString(cursor1.getColumnIndex("InstituteName")));
//                innerObj_Class_SandboxList.setInstituteID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("InstituteID"))));
//                innerObj_Class_SandboxList.setLevelID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("LevelID"))));
//                innerObj_Class_SandboxList.setLevelName(cursor1.getString(cursor1.getColumnIndex("LevelName")));
//                innerObj_Class_SandboxList.setMarks4(cursor1.getString(cursor1.getColumnIndex("Marks4")));
//                innerObj_Class_SandboxList.setMarks5(cursor1.getString(cursor1.getColumnIndex("Marks5")));
//                innerObj_Class_SandboxList.setMarks6(cursor1.getString(cursor1.getColumnIndex("Marks6")));
//                innerObj_Class_SandboxList.setMarks7(cursor1.getString(cursor1.getColumnIndex("Marks7")));
//                innerObj_Class_SandboxList.setMarks8(cursor1.getString(cursor1.getColumnIndex("Marks8")));
//                innerObj_Class_SandboxList.setMobile(cursor1.getString(cursor1.getColumnIndex("Mobile")));
//                innerObj_Class_SandboxList.setMotherName(cursor1.getString(cursor1.getColumnIndex("MotherName")));
//                innerObj_Class_SandboxList.setPaidFee(cursor1.getString(cursor1.getColumnIndex("PaidFee")));
//                innerObj_Class_SandboxList.setReceiptNo(cursor1.getString(cursor1.getColumnIndex("ReceiptNo")));
//                innerObj_Class_SandboxList.setSandboxID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SandboxID"))));
//                innerObj_Class_SandboxList.setSandboxName(cursor1.getString(cursor1.getColumnIndex("SandboxName")));
//                innerObj_Class_SandboxList.setSchoolID(Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SchoolID"))));
//                innerObj_Class_SandboxList.setSchoolName(cursor1.getString(cursor1.getColumnIndex("SchoolName")));
//                innerObj_Class_SandboxList.setStudentAadhar(cursor1.getString(cursor1.getColumnIndex("StudentAadhar")));
//                innerObj_Class_SandboxList.setStudentPhoto(cursor1.getString(cursor1.getColumnIndex("StudentPhoto")));
//                innerObj_Class_SandboxList.setStudentStatus(cursor1.getString(cursor1.getColumnIndex("StudentStatus")));
//                innerObj_Class_SandboxList.setStudentName(cursor1.getString(cursor1.getColumnIndex("StudentName")));
//                innerObj_Class_SandboxList.setStudentID(cursor1.getString(cursor1.getColumnIndex("StudentID")));
//
//
//                innerObj_Class_SandboxList.setLearningMode(cursor1.getString(cursor1.getColumnIndex("Learning_Mode")));
//                innerObj_Class_SandboxList.setState_ID(cursor1.getString(cursor1.getColumnIndex("stateid")));
//                innerObj_Class_SandboxList.setState_Name(cursor1.getString(cursor1.getColumnIndex("statename")));
//                innerObj_Class_SandboxList.setDistrict_ID(cursor1.getString(cursor1.getColumnIndex("districtid")));
//                innerObj_Class_SandboxList.setDistrict_Name(cursor1.getString(cursor1.getColumnIndex("districtname")));
//                innerObj_Class_SandboxList.setTaluk_ID(cursor1.getString(cursor1.getColumnIndex("talukid")));
//                innerObj_Class_SandboxList.setTaluk_Name(cursor1.getString(cursor1.getColumnIndex("talukname")));
//                innerObj_Class_SandboxList.setVillage_ID(cursor1.getString(cursor1.getColumnIndex("villageid")));
//                innerObj_Class_SandboxList.setVillage_Name(cursor1.getString(cursor1.getColumnIndex("villagename")));
//                innerObj_Class_SandboxList.setAddress(cursor1.getString(cursor1.getColumnIndex("student_address")));
//
//                String str_FarmerName = cursor1.getString(cursor1.getColumnIndex("StudentName"));
//                //String str_Farmercode = cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_Farmer_Code"));
////                String str_FarmerImage = cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerImage"));
////                String str_farmerpondcount = cursor1.getString(cursor1.getColumnIndex("Farmpondcount"));
//
//                byte[] str_LocalImg = cursor1.getBlob(cursor1.getColumnIndex("Base64image"));
//
//                Log.e("tag", "bal oo=" + cursor1.getString(cursor1.getColumnIndex("BalanceFee")));
//
//                arrayObj_Class_FarmerListDetails2[i] = innerObj_Class_SandboxList;
//                StudentList item;
//
//                //item = new Class_FarmerListDetails(str_Farmercode, str_FarmerName);
//                item = new StudentList(
//                        cursor1.getString(cursor1.getColumnIndex("AcademicID")),
//                        cursor1.getString(cursor1.getColumnIndex("AcademicName")),
//                        cursor1.getString(cursor1.getColumnIndex("AdmissionFee")),
//                        cursor1.getString(cursor1.getColumnIndex("ApplicationNo")),
//                        cursor1.getString(cursor1.getColumnIndex("BalanceFee")),
//                        cursor1.getString(cursor1.getColumnIndex("BirthDate")),
//                        cursor1.getString(cursor1.getColumnIndex("ClusterID")),
//                        cursor1.getString(cursor1.getColumnIndex("ClusterName")),
//                        cursor1.getString(cursor1.getColumnIndex("CreatedDate")),
//                        cursor1.getString(cursor1.getColumnIndex("Education")),
//                        cursor1.getString(cursor1.getColumnIndex("FatherName")),
//                        cursor1.getString(cursor1.getColumnIndex("Gender")),
//                        cursor1.getString(cursor1.getColumnIndex("InstituteName")),
//                        cursor1.getString(cursor1.getColumnIndex("InstituteID")),
//                        cursor1.getString(cursor1.getColumnIndex("LevelID")),
//                        cursor1.getString(cursor1.getColumnIndex("LevelName")),
//                        cursor1.getString(cursor1.getColumnIndex("Marks4")),
//                        cursor1.getString(cursor1.getColumnIndex("Marks5")),
//                        cursor1.getString(cursor1.getColumnIndex("Marks6")),
//                        cursor1.getString(cursor1.getColumnIndex("Marks7")),
//                        cursor1.getString(cursor1.getColumnIndex("Marks8")),
//                        cursor1.getString(cursor1.getColumnIndex("MotherName")),
//                        cursor1.getString(cursor1.getColumnIndex("PaidFee")),
//                        cursor1.getString(cursor1.getColumnIndex("ReceiptNo")),
//                        Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SandboxID"))),
//                        cursor1.getString(cursor1.getColumnIndex("SandboxName")),
//                        Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SchoolID"))),
//                        cursor1.getString(cursor1.getColumnIndex("SchoolName")),
//                        cursor1.getString(cursor1.getColumnIndex("StudentAadhar")),
//                        cursor1.getString(cursor1.getColumnIndex("StudentPhoto")),
//                        cursor1.getString(cursor1.getColumnIndex("StudentStatus")),
//                        cursor1.getString(cursor1.getColumnIndex("StudentName")),
//                        cursor1.getString(cursor1.getColumnIndex("StudentID")),
//
//                        cursor1.getString(cursor1.getColumnIndex("Learning_Mode")),
//                        cursor1.getString(cursor1.getColumnIndex("stateid")),
//                        cursor1.getString(cursor1.getColumnIndex("statename")),
//                        cursor1.getString(cursor1.getColumnIndex("districtid")),
//                        cursor1.getString(cursor1.getColumnIndex("districtname")),
//                        cursor1.getString(cursor1.getColumnIndex("talukid")),
//                        cursor1.getString(cursor1.getColumnIndex("talukname")),
//                        cursor1.getString(cursor1.getColumnIndex("villageid")),
//                        cursor1.getString(cursor1.getColumnIndex("villagename")),
//                        cursor1.getString(cursor1.getColumnIndex("student_address"))
//
//
//                );
//
//
//                //farmer_image
//
//
//                ViewStudentList_arraylist.add(item);
//                Log.e("str_FarmerName2id", str_FarmerName);
//                // Log.e("str_FarmerImage", str_FarmerImage);
//
//                i++;
//
////            } while (cursor1.moveToNext());
//
//            } while (cursor1.moveToPrevious());
//
//        }//if ends
//
//        db1.close();
//        if (x > 0) {
//
////            originalViewStudentList.clear();
////           // ViewStudentList_arraylist.clear();
//            originalViewStudentList = new ArrayList<StudentList>();
//            originalViewStudentList.addAll(ViewStudentList_arraylist);
//
//            if (ViewStudentList_arraylist != null) {
//                studentListViewAdapter.notifyDataSetChanged();
//                student_LISTVIEW.setAdapter(studentListViewAdapter);
//
//            }
////            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_FarmerListDetails2);
////            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
////            student_LISTVIEW.setAdapter(dataAdapter);
//
//
////            CustomAdapter_new dataAdapter=new CustomAdapter_new();
////            student_LISTVIEW.setAdapter(dataAdapter);
//
//        }else{
//            studentListViewAdapter.notifyDataSetChanged();
//            student_LISTVIEW.setAdapter(studentListViewAdapter);
//            Toast.makeText(Activity_ViewStudentList_new.this,"No  data found",Toast.LENGTH_SHORT).show();
//        }
//
//    }
    public void Update_ids_studentlist_listview_fees_Equal_zero(String str_yearid, String str_sandboxid, String str_clustid, String str_instid, String str_levelid, String studentstatus, String str_fees_filter) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);

//        db1.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
//                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
//                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
//                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
//                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
//                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
//                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR,Farmer_Gender VARCHAR);");


        // db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR,ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR,Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR);");
        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR,ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR,Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");


        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StudentDetailsRest  WHERE AcademicID='" + str_yearid + "' AND SandboxID='" + str_sandboxid + "' AND ClusterID='" + str_clustid + "'AND InstituteID='" + str_instid + "'AND LevelID='" + str_levelid + "' AND StudentStatus='" + studentstatus + "' AND BalanceFee='" + fees_filter_intval + "'", null);
        // Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StudentDetailsRest ", null);
        //Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StudentDetailsRest  WHERE AcademicID='" + str_yearid + "' AND SandboxID='" + str_sandboxid + "' AND ClusterID='" + str_clustid + "'AND InstituteID='" + str_instid + "'AND LevelID='" + str_levelid + "' AND StudentStatus='" + studentstatus + "'", null);


        int x = cursor1.getCount();
        //   Log.d("cursor Studentcount", Integer.toString(x));
        Log.e("fees=0", Integer.toString(x));

        int i = 0;
        arrayObj_Class_FarmerListDetails2 = new StudentList[x];
        // originalViewStudentList.clear();
        ViewStudentList_arraylist.clear();

//        if (cursor1.moveToFirst()) {
        if (cursor1.moveToLast()) {

            do {
//                 VARCHAR, VARCHAR, VARCHAR," +
//                " VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR,InstituteName VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR," +
//                        " VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR,SchoolID VARCHAR, VARCHAR, VARCHAR, VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR)   " +
//

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
                innerObj_Class_SandboxList.setAlternate_Mobile(cursor1.getString(cursor1.getColumnIndex("alternate_mobile")));
                innerObj_Class_SandboxList.setAdmission_date(cursor1.getString(cursor1.getColumnIndex("admission_date")));
                innerObj_Class_SandboxList.setAdmission_remarks(cursor1.getString(cursor1.getColumnIndex("admission_remarks")));

                String str_FarmerName = cursor1.getString(cursor1.getColumnIndex("StudentName"));
                //String str_Farmercode = cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_Farmer_Code"));
//                String str_FarmerImage = cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerImage"));
//                String str_farmerpondcount = cursor1.getString(cursor1.getColumnIndex("Farmpondcount"));

                byte[] str_LocalImg = cursor1.getBlob(cursor1.getColumnIndex("Base64image"));
                Log.e("tag", "applnno oo=" + cursor1.getString(cursor1.getColumnIndex("ApplicationNo")));
                Log.e("tag", "bal oo=" + cursor1.getString(cursor1.getColumnIndex("BalanceFee")));

                arrayObj_Class_FarmerListDetails2[i] = innerObj_Class_SandboxList;
                StudentList item;

                //item = new Class_FarmerListDetails(str_Farmercode, str_FarmerName);
                item = new StudentList(
                        cursor1.getString(cursor1.getColumnIndex("AcademicID")),
                        cursor1.getString(cursor1.getColumnIndex("AcademicName")),
                        cursor1.getString(cursor1.getColumnIndex("AdmissionFee")),
                        cursor1.getString(cursor1.getColumnIndex("ApplicationNo")),
                        cursor1.getString(cursor1.getColumnIndex("BalanceFee")),
                        cursor1.getString(cursor1.getColumnIndex("BirthDate")),
                        cursor1.getString(cursor1.getColumnIndex("ClusterID")),
                        cursor1.getString(cursor1.getColumnIndex("ClusterName")),
                        cursor1.getString(cursor1.getColumnIndex("CreatedDate")),
                        cursor1.getString(cursor1.getColumnIndex("Education")),
                        cursor1.getString(cursor1.getColumnIndex("FatherName")),
                        cursor1.getString(cursor1.getColumnIndex("Gender")),
                        cursor1.getString(cursor1.getColumnIndex("InstituteName")),
                        cursor1.getString(cursor1.getColumnIndex("InstituteID")),
                        cursor1.getString(cursor1.getColumnIndex("LevelID")),
                        cursor1.getString(cursor1.getColumnIndex("LevelName")),
                        cursor1.getString(cursor1.getColumnIndex("Marks4")),
                        cursor1.getString(cursor1.getColumnIndex("Marks5")),
                        cursor1.getString(cursor1.getColumnIndex("Marks6")),
                        cursor1.getString(cursor1.getColumnIndex("Marks7")),
                        cursor1.getString(cursor1.getColumnIndex("Marks8")),
                        cursor1.getString(cursor1.getColumnIndex("MotherName")),
                        cursor1.getString(cursor1.getColumnIndex("PaidFee")),
                        cursor1.getString(cursor1.getColumnIndex("ReceiptNo")),
                        Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SandboxID"))),
                        cursor1.getString(cursor1.getColumnIndex("SandboxName")),
                        Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SchoolID"))),
                        cursor1.getString(cursor1.getColumnIndex("SchoolName")),
                        cursor1.getString(cursor1.getColumnIndex("StudentAadhar")),
                        cursor1.getString(cursor1.getColumnIndex("StudentPhoto")),
                        cursor1.getString(cursor1.getColumnIndex("StudentStatus")),
                        cursor1.getString(cursor1.getColumnIndex("StudentName")),
                        cursor1.getString(cursor1.getColumnIndex("StudentID")),

                        cursor1.getString(cursor1.getColumnIndex("Learning_Mode")),
                        cursor1.getString(cursor1.getColumnIndex("stateid")),
                        cursor1.getString(cursor1.getColumnIndex("statename")),
                        cursor1.getString(cursor1.getColumnIndex("districtid")),
                        cursor1.getString(cursor1.getColumnIndex("districtname")),
                        cursor1.getString(cursor1.getColumnIndex("talukid")),
                        cursor1.getString(cursor1.getColumnIndex("talukname")),
                        cursor1.getString(cursor1.getColumnIndex("villageid")),
                        cursor1.getString(cursor1.getColumnIndex("villagename")),
                        cursor1.getString(cursor1.getColumnIndex("student_address"))


                );


                //farmer_image


                ViewStudentList_arraylist.add(item);
                //   Log.e("ListfetchingAplNo", item.getApplicationNo());
                // Log.e("str_FarmerImage", str_FarmerImage);

                i++;

//            } while (cursor1.moveToNext());

            } while (cursor1.moveToPrevious());

        }//if ends

        db1.close();
        if (x > 0) {

//            originalViewStudentList.clear();
//           // ViewStudentList_arraylist.clear();
            originalViewStudentList = new ArrayList<StudentList>();
            originalViewStudentList.addAll(ViewStudentList_arraylist);

            if (ViewStudentList_arraylist != null) {
                studentListViewAdapter.notifyDataSetChanged();
                student_LISTVIEW.setAdapter(studentListViewAdapter);

            }
//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_FarmerListDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            student_LISTVIEW.setAdapter(dataAdapter);


//            CustomAdapter_new dataAdapter=new CustomAdapter_new();
//            student_LISTVIEW.setAdapter(dataAdapter);

        } else {
            studentListViewAdapter.notifyDataSetChanged();
            student_LISTVIEW.setAdapter(studentListViewAdapter);
            //  Toast.makeText(Activity_ViewStudentList_new.this, "No  data found", Toast.LENGTH_SHORT).show();
        }

    }


    public void Update_ids_studentlist_listview_fees_GT_zero(String str_yearid, String str_sandboxid, String str_clustid, String str_instid, String str_levelid, String studentstatus, String str_fees_filter) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);

//        db1.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
//                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
//                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
//                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
//                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
//                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
//                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR,Farmer_Gender VARCHAR);");


        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR,ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR,Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR, Stud_TempId VARCHAR,UpadateOff_Online VARCHAR,Learning_Mode VARCHAR,stateid VARCHAR,statename VARCHAR,districtid VARCHAR,districtname VARCHAR,talukid VARCHAR,talukname VARCHAR,villageid VARCHAR,villagename VARCHAR,student_address VARCHAR,alternate_mobile VARCHAR,admission_date VARCHAR,admission_remarks VARCHAR);");


        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StudentDetailsRest  WHERE AcademicID='" + str_yearid + "' AND SandboxID='" + str_sandboxid + "' AND ClusterID='" + str_clustid + "'AND InstituteID='" + str_instid + "'AND LevelID='" + str_levelid + "' AND StudentStatus='" + studentstatus + "' AND BalanceFee>'" + fees_filter_intval + "'", null);
        // Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StudentDetailsRest ", null);
        //    Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StudentDetailsRest  WHERE AcademicID='" + str_yearid + "' AND SandboxID='" + str_sandboxid + "' AND ClusterID='" + str_clustid + "'AND InstituteID='" + str_instid + "'AND LevelID='" + str_levelid + "' AND StudentStatus='" + studentstatus + "' AND PaidFee<AdmissionFee '", null);
        // Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StudentDetailsRest  WHERE AcademicID='" + str_yearid + "' AND SandboxID='" + str_sandboxid + "' AND ClusterID='" + str_clustid + "'AND InstituteID='" + str_instid + "'AND LevelID='" + str_levelid + "' AND StudentStatus='" + studentstatus + "' AND PaidFee<'" + 200 + "'", null);


        int x = cursor1.getCount();
        Log.e("fees>0", Integer.toString(x));
        int i = 0;
        arrayObj_Class_FarmerListDetails2 = new StudentList[x];
        // originalViewStudentList.clear();
        ViewStudentList_arraylist.clear();

        //  if (cursor1.moveToFirst()) {
        if (cursor1.moveToLast()) {

            do {
//                 VARCHAR, VARCHAR, VARCHAR," +
//                " VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR,InstituteName VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR," +
//                        " VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR,SchoolID VARCHAR, VARCHAR, VARCHAR, VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR)   " +
//

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
                innerObj_Class_SandboxList.setAlternate_Mobile(cursor1.getString(cursor1.getColumnIndex("alternate_mobile")));
                innerObj_Class_SandboxList.setAdmission_date(cursor1.getString(cursor1.getColumnIndex("admission_date")));
                innerObj_Class_SandboxList.setAdmission_remarks(cursor1.getString(cursor1.getColumnIndex("admission_remarks")));


                String str_FarmerName = cursor1.getString(cursor1.getColumnIndex("StudentName"));
                //String str_Farmercode = cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_Farmer_Code"));
//                String str_FarmerImage = cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerImage"));
//                String str_farmerpondcount = cursor1.getString(cursor1.getColumnIndex("Farmpondcount"));

                byte[] str_LocalImg = cursor1.getBlob(cursor1.getColumnIndex("Base64image"));

                //Log.e("tag", "str_LocalImg oo=" + str_LocalImg);

                arrayObj_Class_FarmerListDetails2[i] = innerObj_Class_SandboxList;
                StudentList item;

                //item = new Class_FarmerListDetails(str_Farmercode, str_FarmerName);
                item = new StudentList(
                        cursor1.getString(cursor1.getColumnIndex("AcademicID")),
                        cursor1.getString(cursor1.getColumnIndex("AcademicName")),
                        cursor1.getString(cursor1.getColumnIndex("AdmissionFee")),
                        cursor1.getString(cursor1.getColumnIndex("ApplicationNo")),
                        cursor1.getString(cursor1.getColumnIndex("BalanceFee")),
                        cursor1.getString(cursor1.getColumnIndex("BirthDate")),
                        cursor1.getString(cursor1.getColumnIndex("ClusterID")),
                        cursor1.getString(cursor1.getColumnIndex("ClusterName")),
                        cursor1.getString(cursor1.getColumnIndex("CreatedDate")),
                        cursor1.getString(cursor1.getColumnIndex("Education")),
                        cursor1.getString(cursor1.getColumnIndex("FatherName")),
                        cursor1.getString(cursor1.getColumnIndex("Gender")),
                        cursor1.getString(cursor1.getColumnIndex("InstituteName")),
                        cursor1.getString(cursor1.getColumnIndex("InstituteID")),
                        cursor1.getString(cursor1.getColumnIndex("LevelID")),
                        cursor1.getString(cursor1.getColumnIndex("LevelName")),
                        cursor1.getString(cursor1.getColumnIndex("Marks4")),
                        cursor1.getString(cursor1.getColumnIndex("Marks5")),
                        cursor1.getString(cursor1.getColumnIndex("Marks6")),
                        cursor1.getString(cursor1.getColumnIndex("Marks7")),
                        cursor1.getString(cursor1.getColumnIndex("Marks8")),
                        cursor1.getString(cursor1.getColumnIndex("MotherName")),
                        cursor1.getString(cursor1.getColumnIndex("PaidFee")),
                        cursor1.getString(cursor1.getColumnIndex("ReceiptNo")),
                        Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SandboxID"))),
                        cursor1.getString(cursor1.getColumnIndex("SandboxName")),
                        Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("SchoolID"))),
                        cursor1.getString(cursor1.getColumnIndex("SchoolName")),
                        cursor1.getString(cursor1.getColumnIndex("StudentAadhar")),
                        cursor1.getString(cursor1.getColumnIndex("StudentPhoto")),
                        cursor1.getString(cursor1.getColumnIndex("StudentStatus")),
                        cursor1.getString(cursor1.getColumnIndex("StudentName")),
                        cursor1.getString(cursor1.getColumnIndex("StudentID")),

                        cursor1.getString(cursor1.getColumnIndex("Learning_Mode")),
                        cursor1.getString(cursor1.getColumnIndex("stateid")),
                        cursor1.getString(cursor1.getColumnIndex("statename")),
                        cursor1.getString(cursor1.getColumnIndex("districtid")),
                        cursor1.getString(cursor1.getColumnIndex("districtname")),
                        cursor1.getString(cursor1.getColumnIndex("talukid")),
                        cursor1.getString(cursor1.getColumnIndex("talukname")),
                        cursor1.getString(cursor1.getColumnIndex("villageid")),
                        cursor1.getString(cursor1.getColumnIndex("villagename")),
                        cursor1.getString(cursor1.getColumnIndex("student_address"))


                );


                ViewStudentList_arraylist.add(item);
                Log.e("str_FarmerName2id", str_FarmerName);
                // Log.e("str_FarmerImage", str_FarmerImage);

                i++;

//            } while (cursor1.moveToNext());
            } while (cursor1.moveToPrevious());


        }//if ends

        db1.close();
        if (x > 0) {

//            originalViewStudentList.clear();
//           // ViewStudentList_arraylist.clear();
            originalViewStudentList = new ArrayList<StudentList>();
            originalViewStudentList.addAll(ViewStudentList_arraylist);

            if (ViewStudentList_arraylist != null) {
                studentListViewAdapter.notifyDataSetChanged();
                student_LISTVIEW.setAdapter(studentListViewAdapter);

            }
//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_FarmerListDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            student_LISTVIEW.setAdapter(dataAdapter);


//            CustomAdapter_new dataAdapter=new CustomAdapter_new();
//            student_LISTVIEW.setAdapter(dataAdapter);

        } else {
            studentListViewAdapter.notifyDataSetChanged();
            student_LISTVIEW.setAdapter(studentListViewAdapter);
            //  Toast.makeText(Activity_ViewStudentList_new.this, "No  data found", Toast.LENGTH_SHORT).show();
        }

    }


    //////////////////////////////////////////////////////////////////////////////////////////
    public static class ViewHolder {


        ///////////////////////sept16th2019
        TextView FarmerNamelabel_tv;
        TextView FarmerName_tv;
        TextView FarmerIDlabel_tv;
        TextView farmercode_tv, scheduleid_tv;
        Button farmpond_bt;

        ImageView fees_payment_IV;
        ///////////////////////sept16th2019

    }

    public class StudentListViewAdapter extends BaseAdapter {


        //public ArrayList<StudentList> projList=null;
        Activity activity;
        public ArrayList<StudentList> mDisplayedValues = null;

        public StudentListViewAdapter(Activity activity, ArrayList<StudentList> projList) {
            super();
            this.activity = activity;
           // this.projList = projList;
            this.mDisplayedValues = projList;
        }

        @Override
        public int getCount() {
            //return projList.size();
            Log.e("size", String.valueOf(mDisplayedValues.size()));
            return mDisplayedValues.size();

        }

        @Override
        public StudentList getItem(int position) {

            //return projList.get(position);
            return mDisplayedValues.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
//            ViewHolder holder;
            LayoutInflater inflater = activity.getLayoutInflater();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.child_studentlist_item, null);
                holder = new ViewHolder();

                holder.FarmerNamelabel_tv = (TextView) convertView.findViewById(R.id.StudentNamelabel_tv);
                holder.FarmerName_tv = (TextView) convertView.findViewById(R.id.StudentName_tv);
                //  holder.FarmerIDlabel_tv = (TextView) convertView.findViewById(R.id.FarmerIDlabel_tv);
                holder.farmercode_tv = (TextView) convertView.findViewById(R.id.Applno_tv);
                holder.farmpond_bt = (Button) convertView.findViewById(R.id.farmpond_bt);
                holder.fees_payment_IV = (ImageView) convertView.findViewById(R.id.fees_payment_IV);
                holder.scheduleid_tv = (TextView) convertView.findViewById(R.id.scheduleid_tv);

                // holder.status_tv=(TextView)convertView.findViewById(R.id.status_tv);
              //  holder.fees_payment_IV.setTag(position);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            StudentList Obj_Class_farmerlistdetails = (StudentList) getItem(position);


            if (Obj_Class_farmerlistdetails != null) {

                String FullName = Obj_Class_farmerlistdetails.getStudentName();
                holder.FarmerName_tv.setText(FullName);
                Log.e("getStudentName()", Obj_Class_farmerlistdetails.getStudentName());
                // Log.e("getApplicationNo()", Obj_Class_farmerlistdetails.getApplicationNo());
                try {
                    if (Obj_Class_farmerlistdetails.getApplicationNo().equals("") || Obj_Class_farmerlistdetails.getApplicationNo().equals("null")) {
                        holder.farmercode_tv.setText("Not generated");
                    } else {
                        holder.farmercode_tv.setText(Obj_Class_farmerlistdetails.getApplicationNo());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                holder.scheduleid_tv.setText(Obj_Class_farmerlistdetails.getStudentID());


//                try {
//                    if (Obj_Class_farmerlistdetails.getStudentID().equals("") || Obj_Class_farmerlistdetails.getStudentID().equals("null")) {
//                        holder.scheduleid_tv.setText("");
//                    } else {
//                        holder.scheduleid_tv.setText(Obj_Class_farmerlistdetails.getStudentID());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

//                    holder.fees_payment_IV.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent i = new Intent(Activity_ViewStudentList_new.this, Activity_FeesPayment.class);
//                            startActivity(i);
//                            finish();
//                        }
//
//
//                    });

                if (selected_studentstatus.equals("Applicant")) {
                    holder.fees_payment_IV.setVisibility(View.GONE);
                } else  if (selected_studentstatus.equals("Dropout")) {
                    holder.fees_payment_IV.setVisibility(View.GONE);
                }else  if (selected_studentstatus.equals("Rejected")) {
                    holder.fees_payment_IV.setVisibility(View.GONE);
                }else if (selected_studentstatus.equals("Admission")) {
                    if (Obj_Class_farmerlistdetails.getAdmissionFee().equals("0")) {
                        holder.fees_payment_IV.setVisibility(View.GONE);
                    } else {
                        holder.fees_payment_IV.setVisibility(View.VISIBLE);

                        holder.fees_payment_IV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String stuid_holder = holder.scheduleid_tv.getText().toString();
                                String name_holder = holder.FarmerName_tv.getText().toString();
                                Log.e("stuid_holder", holder.scheduleid_tv.getText().toString());
                                Log.e("name_holder", holder.FarmerName_tv.getText().toString());
                                Intent i = new Intent(Activity_ViewStudentList_new.this, Activity_FeesPayment.class);
                                i.putExtra("StudentID", stuid_holder);
                                startActivity(i);
                                finish();
                            }


                        });
                    }
                }

                ///  }

            }

            return convertView;
        }


        public void filter(String charText, ArrayList<StudentList> projectList) {
            charText = charText.toLowerCase(Locale.getDefault());
            this.mDisplayedValues.clear();

            if (charText != null) {
                 try{
                if (projectList != null) {
                    if (charText.isEmpty() || charText.length() == 0) {
                        this.mDisplayedValues.addAll(projectList);
                    } else {
                        for (StudentList wp : projectList) {

//                            if (wp.getStudentName().toLowerCase(Locale.getDefault()).contains(charText.toLowerCase()) || wp.getApplicationNo().toLowerCase(Locale.getDefault()).contains(charText.toLowerCase())) {
//                                this.mDisplayedValues.add(wp);
//                            }


                          if(wp.getStudentStatus().equals("Applicant")){
                              if (wp.getStudentName().toLowerCase(Locale.getDefault()).contains(charText.toLowerCase())) {
                                  this.mDisplayedValues.add(wp);
                              }

                          }else{
                              if (wp.getStudentName().toLowerCase(Locale.getDefault()).contains(charText.toLowerCase()) || wp.getApplicationNo().toLowerCase(Locale.getDefault()).contains(charText.toLowerCase())) {
                                this.mDisplayedValues.add(wp);
                            }
                          }


                        }
                    }
                    notifyDataSetChanged();

                    //FarmerListViewAdapter.updateList(mDisplayedValues);
                }
            }catch(Exception e){
                     e.printStackTrace();
                 }
            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Activity_ViewStudentList_new.this, Activity_MarketingHomeScreen.class);
        startActivity(i);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu items
        getMenuInflater().inflate(R.menu.menu_register, menu);
        menu.findItem(R.id.addnewstudent_menu_id)
                .setVisible(true);
        menu.findItem(R.id.save)
                .setVisible(false);

        return super.onCreateOptionsMenu(menu);
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
                SharedPreferences.Editor myprefs_UserImg = sharedpref_userimage_Obj.edit();
                myprefs_UserImg.putString(key_userimage, "");
                myprefs_UserImg.apply();
                SharedPreferences.Editor myprefs_Username = sharedpref_username_Obj.edit();
                myprefs_Username.putString(Key_username, "");
                myprefs_Username.apply();


                AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_ViewStudentList_new.this);
                dialog.setCancelable(false);
                dialog.setTitle(R.string.alert);
                dialog.setMessage("Are you sure want to Logout?");

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {

                        //   SaveSharedPreference.setUserName(Activity_HomeScreen.this, "");
                        Class_SaveSharedPreference.setUserName(getApplicationContext(),"");

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("logout_key1", "yes");
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();


                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Action for "Cancel".
                                dialog.dismiss();
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                    }
                });
                alert.show();
                break;
            case R.id.addnewstudent_menu_id:
                Intent intent = new Intent(Activity_ViewStudentList_new.this, Activity_Register_New.class);
                startActivity(intent);
                finish();
                break;

            case android.R.id.home:
                Intent backpressintent = new Intent(Activity_ViewStudentList_new.this, Activity_MarketingHomeScreen.class);
                startActivity(backpressintent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}