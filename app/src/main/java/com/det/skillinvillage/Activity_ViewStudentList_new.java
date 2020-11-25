package com.det.skillinvillage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.det.skillinvillage.model.District;
import com.det.skillinvillage.model.State;
import com.det.skillinvillage.model.Student;
import com.det.skillinvillage.model.StudentList;
import com.det.skillinvillage.model.Taluka;
import com.det.skillinvillage.model.Village;
import com.det.skillinvillage.model.Year;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Activity_ViewStudentList_new extends AppCompatActivity {

    ImageButton downarrow_ib, uparrow_ib;
    LinearLayout spinnerlayout_ll;
    TextView viewspinner_tv;
    FloatingActionButton addfarmerdetails_fab;


    Year[] arrayObj_Class_yearDetails2;
    Year Obj_Class_yearDetails;
    State[] arrayObj_Class_stateDetails2;
    State Obj_Class_stateDetails;
    District[] arrayObj_Class_DistrictListDetails2;
    District Obj_Class_DistrictDetails;
    Taluka[] arrayObj_Class_TalukListDetails2;
    Taluka Obj_Class_TalukDetails;
    Village[] arrayObj_Class_VillageListDetails2;
    Village Obj_Class_VillageListDetails;
    //    Panchayat[] arrayObj_Class_GrampanchayatListDetails2;
//    Panchayat Obj_Class_GramanchayatDetails;
    Spinner yearlist_SP, statelist_SP, districtlist_SP, taluklist_SP, villagelist_SP, grampanchayatlist_SP;
    ListView farmer_listview;
    int sel_yearsp = 0, sel_statesp = 0, sel_districtsp = 0, sel_taluksp = 0, sel_villagesp = 0, sel_grampanchayatsp = 0;
    List<State> stateList;
    List<District> districtList;
    List<Taluka> talukList;
    List<Village> villageList;
    List<Year> yearList;
    ViewHolder holder;


    private ArrayList<StudentList> originalViewFarmerList = null;
    private ArrayList<StudentList> ViewFarmerList_arraylist;
    FarmerListViewAdapter farmerListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__view_student_list_new);
        downarrow_ib = (ImageButton) findViewById(R.id.downarrow_IB);
        uparrow_ib = (ImageButton) findViewById(R.id.uparrow_IB);
        spinnerlayout_ll = (LinearLayout) findViewById(R.id.spinnerlayout_LL);
        viewspinner_tv = (TextView) findViewById(R.id.viewspinner_TV);
        addfarmerdetails_fab = (FloatingActionButton) findViewById(R.id.addfarmerdetails_fab);

        farmer_listview = (ListView) findViewById(R.id.farmer_LISTVIEW);
        yearlist_SP = (Spinner) findViewById(R.id.yearlist_farmer_SP);
        statelist_SP = (Spinner) findViewById(R.id.statelist_farmer_SP);

        districtlist_SP = (Spinner) findViewById(R.id.districtlist_farmer_SP);
        taluklist_SP = (Spinner) findViewById(R.id.taluklist_farmer_SP);
        villagelist_SP = (Spinner) findViewById(R.id.villagelist_farmer_SP);
        grampanchayatlist_SP = (Spinner) findViewById(R.id.grampanchayatlist_farmer_SP);
        spinnerlayout_ll = (LinearLayout) findViewById(R.id.spinnerlayout_LL);
        viewspinner_tv = (TextView) findViewById(R.id.viewspinner_TV);


        stateList = new ArrayList<>();
        districtList = new ArrayList<>();
        talukList = new ArrayList<>();
        villageList = new ArrayList<>();
        yearList = new ArrayList<>();
        ViewFarmerList_arraylist = new ArrayList<StudentList>();

        farmerListViewAdapter = new FarmerListViewAdapter(Activity_ViewStudentList_new.this, ViewFarmerList_arraylist);
        // farmerList = new ArrayList<>();


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
        uploadfromDB_Yearlist();
        uploadfromDB_Statelist();
        uploadfromDB_Districtlist();
        uploadfromDB_Taluklist();
        uploadfromDB_Villagelist();
        //uploadfromDB_Grampanchayatlist();
        Update_ids_farmerlist_listview();

    }


    public void uploadfromDB_Yearlist() {

        SQLiteDatabase db_year = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_year.execSQL("CREATE TABLE IF NOT EXISTS YearListRest(YearID VARCHAR,YearName VARCHAR);");
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

    public void uploadfromDB_Statelist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor Statecount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_stateDetails2 = new State[x];
        if (cursor1.moveToFirst()) {

            do {
                State innerObj_Class_SandboxList = new State();
                innerObj_Class_SandboxList.setStateID(cursor1.getString(cursor1.getColumnIndex("StateID")));
                innerObj_Class_SandboxList.setStateName(cursor1.getString(cursor1.getColumnIndex("StateName")));
                //   innerObj_Class_SandboxList.s(cursor1.getString(cursor1.getColumnIndex("state_yearid")));


                arrayObj_Class_stateDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_stateDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            statelist_SP.setAdapter(dataAdapter);
            if (x > sel_statesp) {
                statelist_SP.setSelection(sel_statesp);
            }
        }

    }

    public void uploadfromDB_Districtlist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS DistrictListRest(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor Districtcount", Integer.toString(x));

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
            districtlist_SP.setAdapter(dataAdapter);
            if (x > sel_districtsp) {
                districtlist_SP.setSelection(sel_districtsp);
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
            districtlist_SP.setAdapter(dataAdapter);
            if (x > sel_districtsp) {
                districtlist_SP.setSelection(sel_districtsp);
            }
        }

    }

    public void uploadfromDB_Taluklist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS TalukListRest(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM TalukListRest", null);

        // Cursor cursor2 = db1.rawQuery("select T.taluka_id,V.village_id,V.village_name from master_state S, master_district D, master_taluka T, master_village V, master_panchayat P where S.state_id=D.state_id AND D.district_id=T.district_id AND T.taluka_id=V.taluk_id AND T.district_id=P.district_id AND (S.state_id in (1,12,25))",null);
        //  Cursor cursor1 = db1.rawQuery("select T.TalukID,T.TalukName,T.Taluk_districtid from DistrictList D, TalukList T where D.DistrictID=T.Taluk_districtid",null);

        int x = cursor1.getCount();
        Log.d("cursor Talukcount", Integer.toString(x));

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
            taluklist_SP.setAdapter(dataAdapter);
            if (x > sel_taluksp) {
                taluklist_SP.setSelection(sel_taluksp);
            }
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
            taluklist_SP.setAdapter(dataAdapter);
            if (x > sel_taluksp) {
                taluklist_SP.setSelection(sel_taluksp);
            }
        }

    }

    public void uploadfromDB_Villagelist() {

        SQLiteDatabase db_village = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        // db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        Cursor cursor1 = db_village.rawQuery("SELECT DISTINCT * FROM VillageListRest", null);
        int x = cursor1.getCount();
        Log.d("cursor Villagecount", Integer.toString(x));

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
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_village.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            villagelist_SP.setAdapter(dataAdapter);
            if (x > sel_villagesp) {
                villagelist_SP.setSelection(sel_villagesp);
            }
        }


    }

    public void Update_VillageId_spinner(String str_panchayatID, String str_talukid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        //  db1.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db1.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        //Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageList WHERE TalukID='" + str_talukid + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageListRest WHERE TalukID='" + str_talukid + "'AND PanchayatID='" + str_panchayatID + "'", null);
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
                // innerObj_Class_villageList.setTalukaID(cursor1.getString(cursor1.getColumnIndex("PanchayatID")));

                arrayObj_Class_VillageListDetails2[i] = innerObj_Class_villageList;
                // Log.e("village_name", cursor1.getString(cursor1.getColumnIndex("TalukName")));
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            villagelist_SP.setAdapter(dataAdapter);
            if (x > sel_villagesp) {
                villagelist_SP.setSelection(sel_villagesp);
            }

        }


    }


    public void Update_ids_farmerlist_listview() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);

//        db1.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerListRest(MTempId INTEGER PRIMARY KEY,DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR," +
//                "DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR," +
//                "DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR," +
//                "DispFarmerTable_FarmerName VARCHAR,FarmerMName_DB VARCHAR,FarmerLName_DB VARCHAR,Farmerage_DB VARCHAR," +
//                "Farmercellno_DB VARCHAR,FIncome_DB VARCHAR,Ffamilymember_DB VARCHAR,FIDprooftype_DB VARCHAR,FIDProofNo_DB VARCHAR,UploadedStatusFarmerprofile_DB VARCHAR," +
//                "FarmerImageB64str_DB VARCHAR,DispFarmerTable_FarmerImage VARCHAR," +
//                "LocalFarmerImg BLOB,Farmpondcount VARCHAR,Submitted_Date VARCHAR,Created_By VARCHAR,Created_Date VARCHAR,Created_User VARCHAR,Response VARCHAR,Response_Action VARCHAR,Farmer_Gender VARCHAR);");


        db1.execSQL("CREATE TABLE IF NOT EXISTS StudentDetailsRest(AcademicID VARCHAR, AcademicName VARCHAR, AdmissionFee VARCHAR,ApplicationNo VARCHAR,BalanceFee VARCHAR,BirthDate VARCHAR,ClusterID VARCHAR," +
                "ClusterName VARCHAR,CreatedDate VARCHAR,Education VARCHAR,FatherName VARCHAR,Gender VARCHAR,InstituteName VARCHAR,InstituteID VARCHAR,LevelID VARCHAR,LevelName VARCHAR,Marks4 VARCHAR,Marks5 VARCHAR,Marks6 VARCHAR,Marks7 VARCHAR,Marks8 VARCHAR," +
                "Mobile VARCHAR,MotherName VARCHAR,PaidFee VARCHAR,ReceiptNo VARCHAR,SandboxID VARCHAR,SandboxName VARCHAR,SchoolID VARCHAR,SchoolName VARCHAR,StudentAadhar VARCHAR,StudentID VARCHAR,StudentName VARCHAR,StudentPhoto VARCHAR,StudentStatus VARCHAR, Base64image VARCHAR);");


        // Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ViewFarmerListRest  WHERE DispFarmerTable_YearID='" + str_yearid + "' AND DispFarmerTable_StateID='" + str_stateid + "' AND DispFarmerTable_DistrictID='" + str_distid + "'  AND DispFarmerTable_TalukID='" + str_talukid + "' AND DispFarmerTable_VillageID='" + str_villageid + "' AND DispFarmerTable_GrampanchayatID='" + str_panchayatid + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StudentDetailsRest ", null);


        int x = cursor1.getCount();
        Log.d("cursor Farmercount", Integer.toString(x));

        int i = 0;
        StudentList[] arrayObj_Class_FarmerListDetails2 = new StudentList[x];
        // originalViewFarmerList.clear();
        ViewFarmerList_arraylist.clear();

        if (cursor1.moveToFirst()) {

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
                        cursor1.getBlob(cursor1.getColumnIndex("Marks7")),
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
                        cursor1.getString(cursor1.getColumnIndex("StudentName"))


                );//farmer_image


                ViewFarmerList_arraylist.add(item);
                Log.e("str_FarmerName2id", str_FarmerName);
                // Log.e("str_FarmerImage", str_FarmerImage);

                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

//            originalViewFarmerList.clear();
//           // ViewFarmerList_arraylist.clear();
            originalViewFarmerList = new ArrayList<StudentList>();
            originalViewFarmerList.addAll(ViewFarmerList_arraylist);

            if (ViewFarmerList_arraylist != null) {
                farmerListViewAdapter.notifyDataSetChanged();
                farmer_listview.setAdapter(farmerListViewAdapter);

            }
//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_FarmerListDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            farmer_listview.setAdapter(dataAdapter);


//            CustomAdapter_new dataAdapter=new CustomAdapter_new();
//            farmer_listview.setAdapter(dataAdapter);

        }

    }

    public static class ViewHolder {


        public ImageView farmerimage_iv;
        ///////////////////////sept16th2019
        TextView FarmerNamelabel_tv;
        TextView FarmerName_tv;
        TextView FarmerIDlabel_tv;
        TextView farmercode_tv;
        Button farmpond_bt;

        ///////////////////////sept16th2019

    }

    public class FarmerListViewAdapter extends BaseAdapter {


        public ArrayList<StudentList> projList;
        Activity activity;
        private ArrayList<StudentList> mDisplayedValues = null;

        public FarmerListViewAdapter(Activity activity, ArrayList<StudentList> projList) {
            super();
            this.activity = activity;
            this.projList = projList;
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
                holder.farmerimage_iv = (ImageView) convertView.findViewById(R.id.farmerimage_iv);


                // holder.status_tv=(TextView)convertView.findViewById(R.id.status_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            StudentList Obj_Class_farmerlistdetails = (StudentList) getItem(position);

//
            if (Obj_Class_farmerlistdetails != null) {
             //   Log.e("listfarmername", Obj_Class_farmerlistdetails.getStudentName());
               // Log.e("studentid", String.valueOf(Obj_Class_farmerlistdetails.getStudentID()));

                /*if(!(Obj_Class_farmerlistdetails.getFarmername().equalsIgnoreCase("name")||
                        !(Obj_Class_farmerlistdetails.getFarmercode().equalsIgnoreCase("farmer_code"))))*/
                {

                    //Log.e("tag","Name="+Obj_Class_farmerlistdetails.getFarmerFirstName()+" "+Obj_Class_farmerlistdetails.getFarmerMiddleName()+" "+Obj_Class_farmerlistdetails.getFarmerLastName());
                    String FullName = Obj_Class_farmerlistdetails.getStudentName();

                    holder.FarmerName_tv.setText(FullName);
                    holder.farmercode_tv.setText(Obj_Class_farmerlistdetails.getApplicationNo());

                   // holder.farmerimage_iv.setTag(position);
                    //   String str_fetched_imgfile = Class_URL.URL_farmerimageurl + Obj_Class_farmerlistdetails.getFarmerPhoto();
//                    Log.e("Obj_ClFarmerimage()", Obj_Class_farmerlistdetails.getFarmerPhoto());
//                    Log.e("getLocalfarmerimage()", String.valueOf(Obj_Class_farmerlistdetails.getLocalfarmerimage()));


//                    holder.status_tv.setText(Obj_Class_farmerlistdetails.getResponse());
                    //  holder.farmerpondcount_tv.setText(Obj_Class_farmerlistdetails.get);

                    /*if(Obj_Class_farmerlistdetails.getFarmpondcount().isEmpty()
                            ||Obj_Class_farmerlistdetails.getFarmpondcount().equals(null))*/
                        /*if(Obj_Class_farmerlistdetails.getFarmpondcount().equals(null))
                    {
                        holder.farmerpondcount_tv.setText("0");
                    }else{
                        holder.farmerpondcount_tv.setText(Obj_Class_farmerlistdetails.getFarmpondcount());
                    }*/

               /* if(Obj_Class_farmerlistdetails.getFarmerimage()!=null) {
                    imgLoader.displayImage(str_fetched_imgfile, holder.farmerimage_iv, displayoption, imageListener);
                }*/

//                    if (Obj_Class_farmerlistdetails.getStr_base64() != null) {
//
//
//                        // Log.e("imagelist_if",Obj_Class_farmerlistdetails.getStr_base64().toString());
//                        if (Obj_Class_farmerlistdetails.getStr_base64().equalsIgnoreCase("null")) {
//                        } else {
//                            imageBytes_list = Base64.decode(Obj_Class_farmerlistdetails.getStr_base64(), Base64.DEFAULT);
//                            Bitmap bmp_decodedImage1 = BitmapFactory.decodeByteArray(imageBytes_list, 0, imageBytes_list.length);
//                            holder.farmerimage_iv.setImageBitmap(bmp_decodedImage1);
//                        }
//                    } else {
//                        //   Log.e("imagelist_else", Obj_Class_farmerlistdetails.getStr_base64().toString());
//                    }


//                    if (Obj_Class_farmerlistdetails.getLocalfarmerimage() != null) {
//
//                        if (Obj_Class_farmerlistdetails.getLocalfarmerimage().toString().contains(".jpeg")
//                                || Obj_Class_farmerlistdetails.getLocalfarmerimage().toString().contains(".jpg")
//                                || Obj_Class_farmerlistdetails.getLocalfarmerimage().toString().contains(".png")) {
//                           /* byte[] str_imggg = Obj_Class_farmerlistdetails.getLocalfarmerimage();
//                            Bitmap bmp = BitmapFactory.decodeByteArray(str_imggg, 0, str_imggg.length);
//                            holder.farmerimage_iv.setImageBitmap(bmp);*/
//                        }
//
//                        // }
//
//                    } else {
//                        //    if (!Obj_Class_farmerlistdetails.getFarmerimage().equalsIgnoreCase("image/profileimg.png")) {
//
//
////                        if (Obj_Class_farmerlistdetails.getFarmerPhoto() != null) {
////                            if (Obj_Class_farmerlistdetails.getFarmerPhoto().toString().contains(".jpeg")
////                                    || Obj_Class_farmerlistdetails.getFarmerPhoto().toString().contains(".jpg")
////                                    || Obj_Class_farmerlistdetails.getFarmerPhoto().toString().contains(".png")) {
////
////                                // imgLoader.displayImage(str_fetched_imgfile, holder.farmerimage_iv, displayoption, imageListener);
////                            }
////                        } else {
////                            Log.e("farmerImage", "farmerimage null");
////                        }
//                    }

//                    holder.farmerimage_iv.setOnClickListener(new View.OnClickListener() {
//                        @SuppressLint("NewApi")
//                        @Override
//                        public void onClick(View v) {
//
//                            int pos = (Integer) v.getTag();
//                            Student Obj_Class_farmerlistdetails = (Student) getItem(pos);
//                            Log.e("farmerimg", String.valueOf(pos));
//                            // displayalert();
//                            str_selected_farmerID_forimagesaving = Obj_Class_farmerlistdetails.getFarmerID();
//                            Log.e("img_farmerid_onclick", str_selected_farmerID_forimagesaving);
//
//                            Log.e("farmerID", Obj_Class_farmerlistdetails.getFarmerID());
//
//                            Intent i = new Intent(getApplicationContext(), AddFarmer_Activity1.class);
//                            i.putExtra("farmerID", Obj_Class_farmerlistdetails.getFarmerID());
//                            SharedPreferences.Editor myprefs_spinner = sharedpref_spinner_Obj.edit();
//                            myprefs_spinner.putString(Key_sel_yearsp, String.valueOf(sel_yearsp));
//                            myprefs_spinner.putString(Key_sel_statesp, String.valueOf(sel_statesp));
//                            myprefs_spinner.putString(Key_sel_districtsp, String.valueOf(sel_districtsp));
//                            myprefs_spinner.putString(Key_sel_taluksp, String.valueOf(sel_taluksp));
//                            myprefs_spinner.putString(Key_sel_villagesp, String.valueOf(sel_villagesp));
//                            myprefs_spinner.putString(Key_sel_grampanchayatsp, String.valueOf(sel_grampanchayatsp));
//
//                            myprefs_spinner.apply();
//                            startActivity(i);
//
//                            //selectImage();
//                        }
//                    });

                }

            }
            /*else
            {

            }*/
            // }


            return convertView;
        }


        //////////////////////////////////////7thoct///////////////////////////////////


//        private void selectImage() {
//            final CharSequence[] items = {"Take Photo", "Choose from Library",
//                    "Cancel"};
//            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_ViewFarmers.this);
//            builder.setTitle("Add Photo!");
//            builder.setItems(items, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int item) {
//                    boolean result = Class_utility.checkPermission(Activity_ViewFarmers.this);
//                    if (items[item].equals("Take Photo")) {
//                        userChoosenTask = "Take Photo";
//                        if (result)
//                            cameraIntent();
//                    } else if (items[item].equals("Choose from Library")) {
//                        userChoosenTask = "Choose from Library";
//                        if (result)
//                            galleryIntent();
//                    } else if (items[item].equals("Cancel")) {
//                        dialog.dismiss();
//                    }
//                }
//            });
//            builder.show();
//        }


        //////////////////////////////////////7thoct///////////////////////////////////////

//        public void filter(String charText, ArrayList<Farmer> projectList) {
//            charText = charText.toLowerCase(Locale.getDefault());
//            this.mDisplayedValues.clear();
//
//            if (charText != null) {
//                if (projectList != null) {
//                    if (charText.isEmpty() || charText.length() == 0) {
//                        this.mDisplayedValues.addAll(projectList);
//                    } else {
//                        for (Farmer wp : projectList) {
//                            if (wp.getFarmerFirstName().toLowerCase(Locale.getDefault()).contains(charText.toLowerCase())) {
//                                this.mDisplayedValues.add(wp);
//                            }
//                        }
//                    }
//                    notifyDataSetChanged();
//
//                    //FarmerListViewAdapter.updateList(mDisplayedValues);
//                }
//            }
//        }


//    public void updateList(ArrayList<Class_FarmerListDetails> list){
//        projList = list;
//        notifyDataSetChanged();
//    }

    }


}