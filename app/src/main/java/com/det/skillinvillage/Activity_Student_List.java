package com.det.skillinvillage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.det.skillinvillage.adapter.Class_SandBoxDetails;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;

public class Activity_Student_List extends AppCompatActivity {

    ListView student_listview;
    EditText searchStudent_et;
    Button search_BT;
    String str_loginuserID;
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    String str_searchStudent_et;
    int count1;
    Class_StudentListDetails Objclass_studentListDetails;
    Class_StudentListDetails[] Arrayclass_studentListDetails;
    LinearLayout studentlist_LL, Nostudentlist_LL,application_LL,mainstuName_LL;
    ImageButton details_show_ib;
    CustomAdapter adapter;
    Spinner sandbox_new_sp, academic_new_sp, cluster_new_sp, institute_new_sp, level_new_sp,StudentStatus_new_sp;

    Class_SandBoxDetails[] arrayObj_Class_sandboxDetails, arrayObj_Class_sandboxDetails2;
    Class_SandBoxDetails obj_Class_sandboxDetails;
    Class_academicDetails[] arrayObj_Class_academicDetails,arrayObj_Class_academicDetails2;
    Class_academicDetails obj_Class_academicDetails;
    Class_ClusterDetails[] arrayObj_Class_clusterDetails,arrayObj_Class_clusterDetails2;
    Class_ClusterDetails obj_Class_clusterDetails;
    Class_InsituteDetails[] arrayObj_Class_InstituteDetails,arrayObj_Class_InstituteDetails2;
    Class_InsituteDetails obj_Class_instituteDetails;
    Class_LevelDetails[] arrayObj_Class_LevelDetails, arrayObj_Class_LevelDetails2;
    Class_LevelDetails obj_Class_levelDetails;
    Class_StudentStatus[] arrayObj_Class_StudentStatus,arrayObj_Class_StudentStatus2;
    Class_StudentStatus Obj_Class_StudentStatus;
    Class_ViewStudentData[]  arrayObj_Class_ViewStudentData,arrayObj_Class_ViewStudentData2,arrayObj_Class_ViewStudentData3;
    Class_ViewStudentData Obj_Class_ViewStudentData;

    String selected_studentstatus="",sp_strsand_ID,selected_sandboxName,
            sp_straca_ID,selected_academicname,sp_strClust_ID="",selected_clusterName,
            sp_strInst_ID,selected_instituteName,sp_strLev_ID,selected_levelName;
    LinearLayout spinnerlayout_ll;
    ImageButton search_ib,downarrow_ib,uparrow_ib;
    TextView viewspinner_tv;
    TableLayout tableLayout;
    SoapPrimitive response_soapobj_studentStatus;
    boolean issubmitclickedOnAlertDialog=false;

    SharedPreferences sharedpref_loginuserid_Obj;
    SharedPreferences sharedpref_stuid_Obj;
    public static final String sharedpreferenc_studentid = "studentid_edit";
    public static final String key_studentid = "str_studentID_edit";

    SharedPreferences sharedpref_stuid_pay_Obj;
    public static final String sharedpreferenc_studentid_pay = "studentid";
    public static final String key_studentid_pay = "str_studentID";


    String selected_fees_filter,str_studentid_sharedpreference,str_studentid_pay_sharedpreference;
    Spinner fees_filter_sp;
    String[] Fees_Filter_Array = {"All", "Fees Paid","Fees Pending"};
    int fees_filter_intval=0,listcountdisplay;
    TextView listcount_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__student__list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Student List");

        adapter = new CustomAdapter();
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

//        SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
//        str_loginuserID = myprefs.getString("login_userid", "nothing");

        sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();


        sharedpref_stuid_Obj=getSharedPreferences(sharedpreferenc_studentid, Context.MODE_PRIVATE);
        str_studentid_sharedpreference = sharedpref_stuid_Obj.getString(key_studentid, "").trim();


        sharedpref_stuid_pay_Obj=getSharedPreferences(sharedpreferenc_studentid_pay, Context.MODE_PRIVATE);
        str_studentid_pay_sharedpreference = sharedpref_stuid_pay_Obj.getString(key_studentid_pay, "").trim();


        tableLayout= findViewById(R.id.tableLayout_stulist);

        student_listview = findViewById(R.id.student_LISTVIEW);
        sandbox_new_sp = findViewById(R.id.sandboxlist_new_SP);
        academic_new_sp = findViewById(R.id.academiclist_new_SP);
        cluster_new_sp = findViewById(R.id.cluster_new_SP);
        institute_new_sp = findViewById(R.id.institutelist_new_SP);
        level_new_sp = findViewById(R.id.levellist_new_SP);
        StudentStatus_new_sp = findViewById(R.id.Applstatus_new_SP);
        spinnerlayout_ll= findViewById(R.id.spinnerlayout_LL);
        viewspinner_tv= findViewById(R.id.viewspinner_TV);
       // search_ib=(ImageButton)findViewById(R.id.search_IB);
        downarrow_ib= findViewById(R.id.downarrow_IB);
        uparrow_ib= findViewById(R.id.uparrow_IB);
        fees_filter_sp= findViewById(R.id.fees_SP);
        listcount_TV= findViewById(R.id.listcount_TV);

//        search_ib.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                viewspinner_tv.setText("Search");
//                //showPopUp();
//              //Toast.makeText(getApplicationContext(),"Please wait..Loading data..",Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//        viewspinner_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // showPopUp();
//               // Toast.makeText(getApplicationContext(),"Please wait..Loading data..",Toast.LENGTH_SHORT).show();
//
//            }
//        });


        // Toast.makeText(getApplicationContext(),"Please click on search to see the list of students registered",Toast.LENGTH_LONG).show();

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
        @SuppressLint("ResourceType")
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_in);
        animation1.setDuration(1000);
        spinnerlayout_ll.setAnimation(animation1);
        spinnerlayout_ll.animate();
        animation1.start();
        //showPopUp();
        sandbox_new_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                obj_Class_sandboxDetails = (Class_SandBoxDetails) sandbox_new_sp.getSelectedItem();
                sp_strsand_ID = obj_Class_sandboxDetails.getSandbox_id();
                selected_sandboxName = sandbox_new_sp.getSelectedItem().toString();
                Update_academicid_spinner(sp_strsand_ID);
                Log.i("selected_sandboxName", " : " + selected_sandboxName);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        academic_new_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                obj_Class_academicDetails = (Class_academicDetails) academic_new_sp.getSelectedItem();
                sp_straca_ID = obj_Class_academicDetails.getAcademic_id();
                selected_academicname = academic_new_sp.getSelectedItem().toString();

                Update_clusterid_spinner(sp_strsand_ID, sp_straca_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cluster_new_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                obj_Class_clusterDetails = (Class_ClusterDetails) cluster_new_sp.getSelectedItem();
                sp_strClust_ID = obj_Class_clusterDetails.getCluster_id();
                selected_clusterName = cluster_new_sp.getSelectedItem().toString();
                Log.i("selected_clustername", " : " + selected_clusterName);
               // Log.e("sp_strClust_ID.." ,sp_strClust_ID);



                Update_InstituteId_spinner(sp_strsand_ID, sp_straca_ID, sp_strClust_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        institute_new_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                obj_Class_instituteDetails = (Class_InsituteDetails) institute_new_sp.getSelectedItem();
                sp_strInst_ID = obj_Class_instituteDetails.getInstitute_id();
                selected_instituteName = institute_new_sp.getSelectedItem().toString();
                Log.i("selected_instituteName", " : " + selected_instituteName);

                Log.e("sp_strsand_ID.." ,sp_strsand_ID);
                Log.e("sp_straca_ID.." ,sp_straca_ID);
                Log.e("sp_strClust_ID.." ,sp_strClust_ID);
                Log.e("sp_strInst_ID.." ,sp_strInst_ID);
                Update_LevelID_spinner(sp_strsand_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        level_new_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                obj_Class_levelDetails = (Class_LevelDetails) level_new_sp.getSelectedItem();
                sp_strLev_ID = obj_Class_levelDetails.getLevel_id();

                selected_levelName = level_new_sp.getSelectedItem().toString();
                Log.i("selected_levelName", " : " + selected_levelName);
                if(!selected_studentstatus.equals("")) {
                    Update_ViewStuListID_spinner(sp_strsand_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID, sp_strLev_ID, selected_studentstatus);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        StudentStatus_new_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_StudentStatus = (Class_StudentStatus) StudentStatus_new_sp.getSelectedItem();
                selected_studentstatus = Obj_Class_StudentStatus.getStudent_status();

               /* Log.e("sp_strsand_ID.appl.." ,sp_strsand_ID);
                Log.e("sp_straca_ID.appl.." ,sp_straca_ID);
                Log.e("sp_strClust_ID.appl.." ,sp_strClust_ID);
                Log.e("sp_strInst_ID...appl." ,sp_strInst_ID);
                Log.e("sp_strLev_ID...appl..." ,sp_strLev_ID);
                Log.e("selected_studentstatus" , selected_studentstatus);
*/
                Update_ViewStuListID_spinner(sp_strsand_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID,sp_strLev_ID,selected_studentstatus);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter dataAdapter_status = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, Fees_Filter_Array);
        dataAdapter_status.setDropDownViewResource(R.layout.spinnercenterstyle);
        fees_filter_sp.setAdapter(dataAdapter_status);

        fees_filter_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_fees_filter = fees_filter_sp.getSelectedItem().toString();

                Log.e("selected_fees_filter", " : " + selected_fees_filter);

                if(selected_fees_filter.equals(Fees_Filter_Array[0])){
                    Update_ViewStuListID_spinner(sp_strsand_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID,sp_strLev_ID,selected_studentstatus);
                }else if(selected_fees_filter.equals(Fees_Filter_Array[1])){
                    Update_ViewStuList_fees_Equal_zero(sp_strsand_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID,sp_strLev_ID,selected_studentstatus);

                }else if(selected_fees_filter.equals(Fees_Filter_Array[2])){
                    Update_ViewStuList_fees_GT_zero(sp_strsand_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID,sp_strLev_ID,selected_studentstatus);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Log.e(" listcountdisplay", String.valueOf(listcountdisplay));

        if (isInternetPresent) {
            deleteSandBoxTable_B4insertion();
            deleteAcademicTable_B4insertion();
            deleteClusterTable_B4insertion();
            deleteInstituteTable_B4insertion();
            deleteLevelTable_B4insertion();
            deleteStudentStatusTable_B4insertion();
            deleteViewTable_B4insertion();

            AsyncCallWS2 task = new AsyncCallWS2(Activity_Student_List.this);
            task.execute();

        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }

    }//oncreate

    public void showPopUp() {
        if (isInternetPresent) {
            deleteSandBoxTable_B4insertion();
            deleteAcademicTable_B4insertion();
            deleteClusterTable_B4insertion();
            deleteInstituteTable_B4insertion();
            deleteLevelTable_B4insertion();
            deleteStudentStatusTable_B4insertion();
            deleteViewTable_B4insertion();

            AsyncCallWS2 task = new AsyncCallWS2(Activity_Student_List.this);
            task.execute();

        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }

        final View dialogView = View.inflate(Activity_Student_List.this, R.layout.custom_dialog, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(Activity_Student_List.this);
        alert.setView(dialogView);
        TextView tv = dialogView.findViewById(R.id.textView);

        sandbox_new_sp = dialogView.findViewById(R.id.sandboxlist_new_SP);
        academic_new_sp = dialogView.findViewById(R.id.academiclist_new_SP);
        cluster_new_sp = dialogView.findViewById(R.id.cluster_new_SP);
        institute_new_sp = dialogView.findViewById(R.id.institutelist_new_SP);
        level_new_sp = dialogView.findViewById(R.id.levellist_new_SP);
        StudentStatus_new_sp = dialogView.findViewById(R.id.Applstatus_new_SP);
         Button submit_alertdialog_bt= dialogView.findViewById(R.id.submit_alertdialog_BT);


        final AlertDialog alert1 = alert.show();
        submit_alertdialog_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                student_listview.setAdapter(adapter);
                alert1.dismiss();
            }
        });
        alert1.setCanceledOnTouchOutside(true);
        alert1.setCancelable(true);

        sandbox_new_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                obj_Class_sandboxDetails = (Class_SandBoxDetails) sandbox_new_sp.getSelectedItem();
                sp_strsand_ID = obj_Class_sandboxDetails.getSandbox_id();
                selected_sandboxName = sandbox_new_sp.getSelectedItem().toString();
                Update_academicid_spinner(sp_strsand_ID);
                Log.i("selected_sandboxName", " : " + selected_sandboxName);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        academic_new_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                obj_Class_academicDetails = (Class_academicDetails) academic_new_sp.getSelectedItem();
                sp_straca_ID = obj_Class_academicDetails.getAcademic_id();
                selected_academicname = academic_new_sp.getSelectedItem().toString();

                Update_clusterid_spinner(sp_strsand_ID, sp_straca_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cluster_new_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                obj_Class_clusterDetails = (Class_ClusterDetails) cluster_new_sp.getSelectedItem();
                sp_strClust_ID = obj_Class_clusterDetails.getCluster_id();
                selected_clusterName = cluster_new_sp.getSelectedItem().toString();
                Log.i("selected_clustername", " : " + selected_clusterName);



                Update_InstituteId_spinner(sp_strsand_ID, sp_straca_ID, sp_strClust_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        institute_new_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                obj_Class_instituteDetails = (Class_InsituteDetails) institute_new_sp.getSelectedItem();
                sp_strInst_ID = obj_Class_instituteDetails.getInstitute_id();
                selected_instituteName = institute_new_sp.getSelectedItem().toString();
                Log.i("selected_instituteName", " : " + selected_instituteName);

                Log.e("sp_strsand_ID.." ,sp_strsand_ID);
                Log.e("sp_straca_ID.." ,sp_straca_ID);
                Log.e("sp_strClust_ID.." ,sp_strClust_ID);
                Log.e("sp_strInst_ID.." ,sp_strInst_ID);
                Update_LevelID_spinner(sp_strsand_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        level_new_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                obj_Class_levelDetails = (Class_LevelDetails) level_new_sp.getSelectedItem();
                sp_strLev_ID = obj_Class_levelDetails.getLevel_id();

                selected_levelName = level_new_sp.getSelectedItem().toString();
                Log.i("selected_levelName", " : " + selected_levelName);
                if(!selected_studentstatus.equals("")) {
                    Update_ViewStuListID_spinner(sp_strsand_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID, sp_strLev_ID, selected_studentstatus);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        StudentStatus_new_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_StudentStatus = (Class_StudentStatus) StudentStatus_new_sp.getSelectedItem();
                selected_studentstatus = Obj_Class_StudentStatus.getStudent_status();

                Log.e("sp_strsand_ID.appl.." ,sp_strsand_ID);
                Log.e("sp_straca_ID.appl.." ,sp_straca_ID);
                Log.e("sp_strClust_ID.appl.." ,sp_strClust_ID);
                Log.e("sp_strInst_ID...appl." ,sp_strInst_ID);
                Log.e("sp_strLev_ID...appl..." ,sp_strLev_ID);
                Log.e("selected_studentstatus" , selected_studentstatus);

                Update_ViewStuListID_spinner(sp_strsand_ID, sp_straca_ID, sp_strClust_ID, sp_strInst_ID,sp_strLev_ID,selected_studentstatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//
//       final AlertDialog alert1 = alert.create();
//        alert1.show();


    }//end of showPopUp()




    private class AsyncCallWS2 extends AsyncTask<String, Void, Void> {
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

        public AsyncCallWS2(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1,R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected void onPostExecute(Void result) {

            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();

            }
            if(response_soapobj_studentStatus.toString().equals("No Records Found")){
                Log.e("onPostExecute", "no records");
                alert();

            }

            if (Arrayclass_studentListDetails != null)
            {
          //      searchStudent_et.addTextChangedListener(new GenericTextWatcher(searchStudent_et));

//                CustomAdapter adapter = new CustomAdapter();
//                student_listview.setAdapter(adapter);

                int x = Arrayclass_studentListDetails.length;



                uploadfromDB_SandBoxlist();
                uploadfromDB_Academiclist();
                uploadfromDB_Clusterist();
                uploadfromDB_InstitutList();
                uploadfromDB_LevelList();
                uploadfromDB_StatusStudentList();
                uploadfromDB_ViewStudentList();

            } else  {
                Log.d("onPostExecute", "studentlist == null");

            }


        }//end of onPostExecute
    }// end Async task


    public void list_detaile() {
        Vector<SoapObject> result1 = null;

        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "LoadStudentList";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/LoadStudentList";

        try {
            int userid = Integer.parseInt(str_loginuserID);
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("User_ID", userid);//userid


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                SoapObject obj2 =(SoapObject) response.getProperty(0);
                Log.i("value at response", response.toString());
                count1 = response.getPropertyCount();  // number of count in array in response 6,0-5,5

                Log.i("number of rows", "" + count1);
                Arrayclass_studentListDetails = new Class_StudentListDetails[count1];

                arrayObj_Class_sandboxDetails = new Class_SandBoxDetails[count1];
                arrayObj_Class_academicDetails = new Class_academicDetails[count1];
                arrayObj_Class_clusterDetails = new Class_ClusterDetails[count1];
                arrayObj_Class_InstituteDetails = new Class_InsituteDetails[count1];
                arrayObj_Class_LevelDetails = new Class_LevelDetails[count1];
                arrayObj_Class_StudentStatus = new Class_StudentStatus[count1];
                arrayObj_Class_ViewStudentData= new Class_ViewStudentData[count1];

                for (int i = 0; i < count1; i++) {
                    SoapObject list = (SoapObject) response.getProperty(i);
                    SoapPrimitive response_soapobj_studentname,response_soapobj_mobieno, response_soapobj_balancefee,response_soapobj_academicID,
                            response_soapobj_academic_Name, response_soapobj_clusterid, response_soapobj_clustername, response_soapobj_instituteID,
                            response_soapobj_instituteName, response_soapobj_levelID, response_soapobj_levelname, response_soapobj_sandboxid,
                            response_soapobj_sandboxname,response_soapobj_stuID;
                    String str_aplication_no = "",contactno="";

//                    {Student_ID=841; Sandbox_ID=1; Sandbox_Name=Hubli;
//                    Academic_ID=2019; Academic_Name=2019-2020; Cluster_ID=1;
//                    Cluster_Name=Shiggaon; Institute_ID=128; Institute_Name=Test;
//                    School_ID=0; School_Name=0; Level_ID=2; Level_Name=Level-1;
//                    Student_Name=ANAND KANADE; Mobile=9980570450;
//                    Student_Status=Applicant; Admission_Fee=1000;
//                    Paid_Fee=0; Balance_Fee=0; };
                    response_soapobj_stuID = (SoapPrimitive) obj2.getProperty("Student_ID");
                    response_soapobj_studentname = (SoapPrimitive) obj2.getProperty("Student_Name");
                    response_soapobj_instituteName = (SoapPrimitive) obj2.getProperty("Institute_Name");
                    response_soapobj_mobieno = (SoapPrimitive) obj2.getProperty("Mobile");

                    response_soapobj_balancefee = (SoapPrimitive) obj2.getProperty("Balance_Fee");
                    response_soapobj_levelname = (SoapPrimitive) obj2.getProperty("Level_Name");
                    response_soapobj_studentStatus = (SoapPrimitive) obj2.getProperty("Student_Status");


                    response_soapobj_sandboxid = (SoapPrimitive) obj2.getProperty("Sandbox_ID");
                    response_soapobj_sandboxname = (SoapPrimitive) obj2.getProperty("Sandbox_Name");
                    response_soapobj_academicID = (SoapPrimitive) obj2.getProperty("Academic_ID");
                    response_soapobj_academic_Name = (SoapPrimitive) obj2.getProperty("Academic_Name");
                    response_soapobj_clusterid = (SoapPrimitive) obj2.getProperty("Cluster_ID");
                    response_soapobj_clustername = (SoapPrimitive) obj2.getProperty("Cluster_Name");
                    response_soapobj_instituteID = (SoapPrimitive) obj2.getProperty("Institute_ID");
                    response_soapobj_levelID = (SoapPrimitive) obj2.getProperty("Level_ID");


//
                    if (list.getProperty("Student_Status").toString().equals("Admission")) {
                        Log.i("str_studentStatus", response_soapobj_studentStatus.toString());
                        str_aplication_no = list.getProperty("Application_No").toString();

                    } else {
                        str_aplication_no = "";
                    }

                    Class_StudentListDetails inner_class_StudentListDetails = new Class_StudentListDetails();
                    Log.i("value at name premitive", response_soapobj_studentname.toString());
                    inner_class_StudentListDetails.setStudent_name(response_soapobj_studentname.toString());
                    inner_class_StudentListDetails.setStudent_balanceFee(response_soapobj_balancefee.toString());
                    inner_class_StudentListDetails.setStudent_institutionName(response_soapobj_instituteName.toString());
                    inner_class_StudentListDetails.setStudent_mobileno(response_soapobj_mobieno.toString());
                   // inner_class_StudentListDetails.setStudent_mobileno(contactno.toString());

                    inner_class_StudentListDetails.setStudent_levelname(response_soapobj_levelname.toString());
                    inner_class_StudentListDetails.setStudent_status(response_soapobj_studentStatus.toString());
                    inner_class_StudentListDetails.setStudent_ID(response_soapobj_stuID.toString());


                    if (!str_aplication_no.equals("")) {
                        inner_class_StudentListDetails.setStudent_applicationNumber(str_aplication_no);
                    }

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

                    Class_StudentStatus innerObj_Class_studentstatus= new Class_StudentStatus();
                    innerObj_Class_studentstatus.setStudent_status(response_soapobj_studentStatus.toString());


                    Class_ViewStudentData  innerObj_Class_viewstudentdata=new Class_ViewStudentData();
                    innerObj_Class_viewstudentdata.setSandboxid(response_soapobj_sandboxid.toString());
                    innerObj_Class_viewstudentdata.setAcademicid(response_soapobj_academicID.toString());
                    innerObj_Class_viewstudentdata.setClusterid(response_soapobj_clusterid.toString());
                    innerObj_Class_viewstudentdata.setInstituteid(response_soapobj_instituteID.toString());
                    innerObj_Class_viewstudentdata.setLevelid(response_soapobj_levelID.toString());
                    innerObj_Class_viewstudentdata.setAppl_status(response_soapobj_studentStatus.toString());
                    innerObj_Class_viewstudentdata.setName(response_soapobj_studentname.toString());
                    innerObj_Class_viewstudentdata.setMobileno(response_soapobj_mobieno.toString());
                  //  innerObj_Class_viewstudentdata.setMobileno(contactno.toString());

                    innerObj_Class_viewstudentdata.setStudentID(response_soapobj_stuID.toString());


                    Arrayclass_studentListDetails[i] = inner_class_StudentListDetails;
                    arrayObj_Class_sandboxDetails[i] = innerObj_Class_sandbox;
                    arrayObj_Class_academicDetails[i] = innerObj_Class_academic;
                    arrayObj_Class_clusterDetails[i] = innerObj_Class_cluster;
                    arrayObj_Class_InstituteDetails[i] = innerObj_Class_institute;
                    arrayObj_Class_LevelDetails[i] = innerObj_Class_level;
                    arrayObj_Class_StudentStatus[i] = innerObj_Class_studentstatus;
                    arrayObj_Class_ViewStudentData[i]= innerObj_Class_viewstudentdata;

                    String sandbox_id = list.getProperty("Sandbox_ID").toString();
                    String sandbox_name = list.getProperty("Sandbox_Name").toString();

                    String aca_id = list.getProperty("Academic_ID").toString();
                    String aca_name = list.getProperty("Academic_Name").toString();

                    String clust_id = list.getProperty("Cluster_ID").toString();
                    String clust_name = list.getProperty("Cluster_Name").toString();


                    String inst_id = list.getProperty("Institute_ID").toString();
                    String inst_name = list.getProperty("Institute_Name").toString();

                    String level_id = list.getProperty("Level_ID").toString();
                    String level_name = list.getProperty("Level_Name").toString();

                    String student_status_str = list.getProperty("Student_Status").toString();

                    String stuName = list.getProperty("Student_Name").toString();
                    String stuMobile = list.getProperty("Mobile").toString();
                   // String stuMobile = "9448789567";

                    String stuInstitution = list.getProperty("Institute_Name").toString();
                    String stuLevel = list.getProperty("Level_Name").toString();
                    String stuBalanceFee = list.getProperty("Balance_Fee").toString();
                    String studentID = list.getProperty("Student_ID").toString();
                    String stuApplicationNo="";
                    if (list.getProperty("Student_Status").toString().equals("Admission")) {

                         stuApplicationNo = list.getProperty("Application_No").toString();
                        Log.e("entered if", stuApplicationNo);

                    }else{
                        stuApplicationNo="";//HB19TST00001
                        Log.e("entered else", stuApplicationNo);

                    }


                    DBCreate_SandBoxdetails_insert_2SQLiteDB(sandbox_id, sandbox_name);
                    DBCreate_Academicdetails_insert_2SQLiteDB(aca_id, aca_name, sandbox_id);
                    DBCreate_Clusterdetails_insert_2SQLiteDB(clust_id, clust_name, sandbox_id, aca_id);
                    DBCreate_Institutedetails_insert_2SQLiteDB(inst_id, inst_name, sandbox_id, aca_id, clust_id);
                    DBCreate_Leveldetails_insert_2SQLiteDB(level_id, level_name, sandbox_id, aca_id, clust_id, inst_id);
                    DBCreate_StudentStatusdetails_insert_2SQLiteDB(student_status_str);
                    DBCreate_Viewdetails_insert_2SQLiteDB(sandbox_id,aca_id,clust_id,inst_id,level_id,student_status_str,stuName,stuMobile,stuInstitution,stuLevel,stuBalanceFee,stuApplicationNo,studentID,stuBalanceFee);


                }// End of for loop

//                searchStudent_et.addTextChangedListener(new TextWatcher() {
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                        int textlength = s.length();
//                        ArrayList<Class_StudentListDetails> tempArrayList = new ArrayList<Class_StudentListDetails>();
//                        for(Class_StudentListDetails c: Arrayclass_studentListDetails){
//                            if (textlength <= c.getStudent_name().length()) {
//                                if (c.getStudent_name().toLowerCase().contains(s.toString().toLowerCase())) {
//                                    tempArrayList.add(c);
//                                }
//                            }
//                        }
//                        student_listview.setAdapter(adapter);
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                    }
//
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count,
//                                                  int after) {
//
//                    }
//
//
//                });


            } catch (Throwable t) {

                Log.e("requestload fail", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("UnRegisterload  Error", "> " + t.getMessage());

        }

    }//End of leaveDetail method

    public void alert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Activity_Student_List.this);
        builder1.setMessage("No Data Found");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i=new Intent(Activity_Student_List.this,Activity_MarketingHomeScreen.class);
                        startActivity(i);
                        finish();

                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private class Holder {
        TextView studentname_tv,studentName_new_tv;
        TextView applicationNumber_tv;
        TextView applicationNoLabel_tv;
        TextView Institution_tv;
        TextView level_tv;
        TextView balancefee_tv;
        TextView balancefeeLable_tv;
        TextView mobileno_tv;
        TextView studentstatus_tv;
        Button pay_bt;
        Button edit_bt;
        TextView stu_id_tv;

    }
    //End of Holder

    // InnerClass for enabling Search feature by implementing the methods

//    private class AddressFilter extends Filter
//    {
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//
////below checks the match for the cityId and adds to the filterlist
//            long cityId= Long.parseLong(constraint.toString());
//            FilterResults results = new FilterResults();
//
//            if (cityId > 0) {
//                ArrayList<Address> filterList = new ArrayList<Address>();
//                for (int i = 0; i < addressFilterList.size(); i++) {
//
//                    if ( (addressFilterList.get(i).getCityId() )== cityId) {
//
//                        Address address = addressFilterList.get(i);
//                        filterList.add(address);
//                    }
//                }
//
//                results.count = filterList.size();
//                results.values = filterList;
//
//            } else {
//
//                results.count = addressFilterList.size();
//                results.values = addressFilterList;
//
//            }
//            return results;
//        }
//        //Publishes the matches found, i.e., the selected cityids
//        @Override
//        protected void publishResults(CharSequence constraint,
//                                      FilterResults results) {
//
//            addressList = (ArrayList<Address>)results.values;
//            notifyDataSetChanged();
//        }
//    }

    public class CustomAdapter extends BaseAdapter  {


        public CustomAdapter() {

            super();
            Log.e("Inside CustomAdapter()", "Inside CustomAdapter()");
        }

        @Override
        public int getCount() {

            String x = Integer.toString(arrayObj_Class_ViewStudentData2.length);
            Log.e("Arrayclass.length", x);
            return arrayObj_Class_ViewStudentData2.length;

        }

        @Override
        public Object getItem(int position) {
            String x = Integer.toString(position);
            Log.e("getItem position", x);
            return arrayObj_Class_ViewStudentData2[position];
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
                convertView = LayoutInflater.from(Activity_Student_List.this).inflate(R.layout.child_studentlistview, parent, false);


                studentlist_LL = convertView.findViewById(R.id.studentlist_LL);
                Nostudentlist_LL = convertView.findViewById(R.id.noRecords_LL);
                application_LL= convertView.findViewById(R.id.application_LL);
                mainstuName_LL= convertView.findViewById(R.id.mainstuName_ll);
                details_show_ib= convertView.findViewById(R.id.details_show_IB);
                holder.studentname_tv = convertView.findViewById(R.id.studentName_TV);
                holder.studentName_new_tv = convertView.findViewById(R.id.studentName_new_TV);


                holder.applicationNumber_tv = convertView.findViewById(R.id.applicationNumber_TV);
                holder.applicationNoLabel_tv = convertView.findViewById(R.id.applicationnolabel_TV);
                holder.Institution_tv = convertView.findViewById(R.id.Institution_TV);
                holder.level_tv = convertView.findViewById(R.id.level_TV);
                holder.balancefee_tv = convertView.findViewById(R.id.balancefee_TV);
                holder.balancefeeLable_tv = convertView.findViewById(R.id.balancefeelabel_TV);


                holder.mobileno_tv = convertView.findViewById(R.id.mobileno_TV);
                holder.studentstatus_tv = convertView.findViewById(R.id.studentstatus_TV);
                holder.pay_bt = convertView.findViewById(R.id.pay_BT);
                holder.pay_bt.setVisibility(View.GONE);
                holder.edit_bt = convertView.findViewById(R.id.edit_BT);
                holder.stu_id_tv = convertView.findViewById(R.id.stu_id_TV);




                // font_change_row_item_new();

               // studentlist_LL.setVisibility(View.VISIBLE);

                Log.d("Inside If convertView", "Inside If convertView");

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
                Log.d("Inside else convertView", "Inside else convertView");
            }

            Obj_Class_ViewStudentData = (Class_ViewStudentData) getItem(position);

            details_show_ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    studentlist_LL.setVisibility(View.VISIBLE);
                   // mainstuName_LL.setVisibility(View.GONE);
//                    if (studentlist_LL.getVisibility() == View.VISIBLE) {
//
//                    }
                    }
            });
            if (count1 == 0) {
                studentlist_LL.setVisibility(View.GONE);
               // application_LL.setVisibility(View.GONE);
                Nostudentlist_LL.setVisibility(View.VISIBLE);
            } else {
                if (Obj_Class_ViewStudentData != null) {
                    holder.studentName_new_tv.setText(Obj_Class_ViewStudentData.getName());


                   // if (studentlist_LL.getVisibility() == View.VISIBLE) {
                        holder.stu_id_tv.setText(Obj_Class_ViewStudentData.getStudentID());
                        holder.studentname_tv.setText(Obj_Class_ViewStudentData.getName());

                        holder.level_tv.setText(Obj_Class_ViewStudentData.getLevelname());
                    holder.Institution_tv.setText(Obj_Class_ViewStudentData.getInstitutionName());//Unapproved
                    holder.mobileno_tv.setText(Obj_Class_ViewStudentData.getMobileno());
                    holder.balancefee_tv.setText(Obj_Class_ViewStudentData.getBalanceFee());
                    holder.studentstatus_tv.setText(Obj_Class_ViewStudentData.getAppl_status());

                    holder.edit_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String str_studentID = holder.stu_id_tv.getText().toString();
                            Log.e("student_ID", str_studentID);
                            Intent i = new Intent(Activity_Student_List.this, Activity_EditRegistration.class);
//                            SharedPreferences myprefs = Activity_Student_List.this.getSharedPreferences("studentid_edit", MODE_PRIVATE);
//                            myprefs.edit().putString("str_studentID_edit", str_studentID).apply();

                            SharedPreferences.Editor  myprefs_studentID= sharedpref_stuid_Obj.edit();
                            myprefs_studentID.putString(key_studentid, str_studentID);
                            myprefs_studentID.apply();


                            // SharedPreferences myprefs_flag = Activity_Student_List.this.getSharedPreferences("Editflag", MODE_PRIVATE);
//                            myprefs_flag.edit().putString("editflag", "1").apply();
                            startActivity(i);

                        }
                    });

                    if (holder.balancefee_tv.getText().toString().equals("0")) {
                        holder.pay_bt.setVisibility(View.GONE);

                    } else {
                        if(holder.studentstatus_tv.getText().toString().equals("Admission"))
                        {
                        holder.pay_bt.setVisibility(View.VISIBLE);


                        holder.pay_bt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String str_studentID = holder.stu_id_tv.getText().toString();
                                Log.e("student_ID", str_studentID);
                                Intent i = new Intent(Activity_Student_List.this, Activity_FeesPayment.class);
//                                SharedPreferences myprefs = Activity_Student_List.this.getSharedPreferences("studentid", MODE_PRIVATE);
//                                myprefs.edit().putString("str_studentID", str_studentID).apply();

                                SharedPreferences.Editor myprefs_studentID = sharedpref_stuid_pay_Obj.edit();
                                myprefs_studentID.putString(key_studentid_pay, str_studentID);
                                myprefs_studentID.apply();

                                startActivity(i);
                                finish();

                            }
                        });
                    }
                    }
                    try {
                        String str_Status = holder.studentstatus_tv.getText().toString();
                        if (str_Status.equals("Admission")) {
                            application_LL.setVisibility(View.VISIBLE);
                            holder.applicationNoLabel_tv.setVisibility(View.VISIBLE);
                            holder.applicationNumber_tv.setVisibility(View.VISIBLE);
                            holder.applicationNumber_tv.setText(Obj_Class_ViewStudentData.getApplicationNumber());
                            holder.balancefee_tv.setVisibility(View.VISIBLE);
                            holder.balancefeeLable_tv.setVisibility(View.VISIBLE);


                        } else {
                            application_LL.setVisibility(View.GONE);
                            holder.applicationNoLabel_tv.setVisibility(View.GONE);
                            holder.applicationNumber_tv.setVisibility(View.GONE);
                            holder.balancefee_tv.setVisibility(View.GONE);
                            holder.balancefeeLable_tv.setVisibility(View.GONE);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
              //  }// end of if

            }
            }



            return convertView;
        }



    }//End of CustomAdapter


    //Database
//////////////////////////////////dbcreate/////////////////////////////////////

    public void DBCreate_SandBoxdetails_insert_2SQLiteDB(String str_sandID, String str_sandname) {
        SQLiteDatabase db_sandbox = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db_sandbox.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandID VARCHAR,SandName VARCHAR);");


        String SQLiteQuery = "INSERT INTO SandBoxList (SandID, SandName)" +
                " VALUES ('" + str_sandID + "','" + str_sandname + "');";
        db_sandbox.execSQL(SQLiteQuery);
        Log.e("str_sandID DB",str_sandID);
        Log.e("str_sandname DB",str_sandname);
        db_sandbox.close();
    }
    public void DBCreate_Academicdetails_insert_2SQLiteDB(String str_stateID, String str_statename, String sandboxid) {
        SQLiteDatabase db_academic = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db_academic.execSQL("CREATE TABLE IF NOT EXISTS AcademicList(AcaID VARCHAR,AcaName VARCHAR,ASandID VARCHAR);");
        String SQLiteQuery = "INSERT INTO AcademicList (AcaID, AcaName,ASandID)" +
                " VALUES ('" + str_stateID + "','" + str_statename + "','" + sandboxid + "');";
        db_academic.execSQL(SQLiteQuery);
        db_academic.close();

    }

    public void DBCreate_Clusterdetails_insert_2SQLiteDB(String str_stateID, String str_statename, String str_sandid, String str_acaID) {

        SQLiteDatabase db_cluster = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db_cluster.execSQL("CREATE TABLE IF NOT EXISTS ClusterList(clustID VARCHAR,clustName VARCHAR,clust_sand_ID VARCHAR,clust_aca_ID VARCHAR);");
        String SQLiteQuery = "INSERT INTO ClusterList (clustID, clustName,clust_sand_ID,clust_aca_ID)" +
                " VALUES ('" + str_stateID + "','" + str_statename + "','" + str_sandid + "','" + str_acaID + "');";
        db_cluster.execSQL(SQLiteQuery);
        db_cluster.close();
    }


    public void DBCreate_Institutedetails_insert_2SQLiteDB(String str_instid, String str_instname, String str_sandid, String str_acaID, String str_clustID) {

        SQLiteDatabase db_inst = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db_inst.execSQL("CREATE TABLE IF NOT EXISTS InstituteList(inst_ID VARCHAR,inst_Name VARCHAR,inst_sand_ID VARCHAR,inst_aca_ID VARCHAR,inst_clust_ID VARCHAR);");
        String SQLiteQuery = "INSERT INTO InstituteList (inst_ID,inst_Name,inst_sand_ID,inst_aca_ID,inst_clust_ID)" +
                " VALUES ('" + str_instid + "','" + str_instname + "','" + str_sandid + "','" + str_acaID + "','" + str_clustID + "');";
        db_inst.execSQL(SQLiteQuery);
        db_inst.close();

    }


    public void DBCreate_Leveldetails_insert_2SQLiteDB(String str_levelID, String str_levelName, String str_sandboxid, String str_acaid, String str_clustID, String str_instID) {
        SQLiteDatabase db_level = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db_level.execSQL("CREATE TABLE IF NOT EXISTS LevelList(LevelID VARCHAR,LevelName VARCHAR,Lev_SandID VARCHAR,Lev_AcaID VARCHAR,Lev_ClustID VARCHAR,Lev_InstID VARCHAR);");
        String SQLiteQuery = "INSERT INTO LevelList (LevelID, LevelName,Lev_SandID,Lev_AcaID,Lev_ClustID,Lev_InstID)" +
                " VALUES ('" + str_levelID + "','" + str_levelName + "','" + str_sandboxid + "' ,'" + str_acaid + "','" + str_clustID + "' ,'" + str_instID + "');";
        db_level.execSQL(SQLiteQuery);
        Log.d("level", "leveldbcreated");
        db_level.close();
    }

    public void DBCreate_StudentStatusdetails_insert_2SQLiteDB(String str_studentstatus) {
        SQLiteDatabase db_apl_status = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db_apl_status.execSQL("CREATE TABLE IF NOT EXISTS StatusStudentList(db_studentstatus VARCHAR);");
        String SQLiteQuery = "INSERT INTO StatusStudentList (db_studentstatus)" +
                " VALUES ('" + str_studentstatus + "');";
        db_apl_status.execSQL(SQLiteQuery);
        Log.d("student", "statusdbcreated");
        db_apl_status.close();
    }

    public void DBCreate_Viewdetails_insert_2SQLiteDB(String str_sandboxid,String str_acaid,String str_clusterid,String str_instid,String str_levelid,String str_stuStatus,String str_stuName,String str_stuMObile,String str_stuInstName,String str_stuLevelName,String str_stuBalanceFee,String str_StuAppl,String str_StudentID,String str_balance) {
        SQLiteDatabase db_viewlist = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db_viewlist.execSQL("CREATE TABLE IF NOT EXISTS ViewStudentList(ViewList_SandID VARCHAR,ViewList_AcaID VARCHAR,ViewList_ClustID VARCHAR,ViewList_InstID VARCHAR,ViewList_LevelID VARCHAR,ViewList_StuStatus VARCHAR,ViewList_StuName VARCHAR,ViewList_StuMobile VARCHAR,ViewList_StuInstName VARCHAR,ViewList_stuLevelName VARCHAR,ViewList_stuBalanceFee VARCHAR,ViewList_stuApplNo VARCHAR,ViewList_stuID VARCHAR,ViewList_balance VARCHAR);");
        String SQLiteQuery = "INSERT INTO ViewStudentList (ViewList_SandID, ViewList_AcaID,ViewList_ClustID,ViewList_InstID,ViewList_LevelID,ViewList_StuStatus,ViewList_StuName,ViewList_StuMobile,ViewList_StuInstName,ViewList_stuLevelName,ViewList_stuBalanceFee,ViewList_stuApplNo,ViewList_stuID,ViewList_balance)" +
                " VALUES ('" + str_sandboxid + "','" + str_acaid + "','" + str_clusterid + "' ,'" + str_instid + "','" + str_levelid + "' ,'" + str_stuStatus + "' ,'" + str_stuName +"' ,'" + str_stuMObile + "' ,'" + str_stuInstName +"' ,'" + str_stuLevelName + "' ,'" + str_stuBalanceFee + "' ,'" + str_StuAppl +"'  ,'" + str_StudentID +"'  ,'" + str_balance + "');";
        db_viewlist.execSQL(SQLiteQuery);
        Log.d("student", "stuListdbcreated");
        db_viewlist.close();
    }


    private void getRecord() {
        SQLiteDatabase db = this.openOrCreateDatabase("StudentRegistrationOfflineDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS ViewStudentList(ViewList_SandID VARCHAR,ViewList_AcaID VARCHAR,ViewList_ClustID VARCHAR,ViewList_InstID VARCHAR,ViewList_LevelID VARCHAR,ViewList_StuStatus VARCHAR,ViewList_StuName VARCHAR,ViewList_StuMobile VARCHAR,ViewList_StuInstName VARCHAR,ViewList_stuLevelName VARCHAR,ViewList_stuBalanceFee VARCHAR,ViewList_stuApplNo VARCHAR,ViewList_stuID VARCHAR,ViewList_balance VARCHAR);");

        Cursor cursor1 = db.rawQuery("SELECT DISTINCT * FROM ViewStudentList WHERE ViewList_balance='0'", null);
        int x = cursor1.getCount();

//        int_cousorcount=0;
//        int_insidecursorcount=0;
//
        //int_cousorcount=x;
        Log.d("cursor count", Integer.toString(x));
        int i = 0;
        arrayObj_Class_ViewStudentData2 = new Class_ViewStudentData[x];
        Log.d("lenth", String.valueOf(arrayObj_Class_ViewStudentData2.length));
        if (cursor1.getCount() > 0) {
//            do {
            if (cursor1.moveToFirst()) {
                do {

                    try {

                        Class_ViewStudentData innerObj_Class_stuList = new Class_ViewStudentData();
                        innerObj_Class_stuList.setSandboxid(cursor1.getString(cursor1.getColumnIndex("ViewList_SandID")));
                        innerObj_Class_stuList.setAcademicid(cursor1.getString(cursor1.getColumnIndex("ViewList_AcaID")));
                        innerObj_Class_stuList.setClusterid(cursor1.getString(cursor1.getColumnIndex("ViewList_ClustID")));
                        innerObj_Class_stuList.setInstituteid(cursor1.getString(cursor1.getColumnIndex("ViewList_InstID")));
                        innerObj_Class_stuList.setLevelid(cursor1.getString(cursor1.getColumnIndex("ViewList_LevelID")));
                        innerObj_Class_stuList.setAppl_status(cursor1.getString(cursor1.getColumnIndex("ViewList_StuStatus")));
                        innerObj_Class_stuList.setName(cursor1.getString(cursor1.getColumnIndex("ViewList_StuName")));
                        innerObj_Class_stuList.setMobileno(cursor1.getString(cursor1.getColumnIndex("ViewList_StuMobile")));
                        innerObj_Class_stuList.setApplicationNumber(cursor1.getString(cursor1.getColumnIndex("ViewList_stuApplNo")));
                        innerObj_Class_stuList.setInstitutionName(cursor1.getString(cursor1.getColumnIndex("ViewList_StuInstName")));
                        innerObj_Class_stuList.setLevelname(cursor1.getString(cursor1.getColumnIndex("ViewList_stuLevelName")));
                        innerObj_Class_stuList.setBalanceFee(cursor1.getString(cursor1.getColumnIndex("ViewList_stuBalanceFee")));
                        innerObj_Class_stuList.setStudentID(cursor1.getString(cursor1.getColumnIndex("ViewList_stuID")));

                        arrayObj_Class_ViewStudentData2[i] = innerObj_Class_stuList;
                        i++;

//                        Log.e("str_fetch_studentname", str_fetch_studentname);
//                        Log.e("val-before", String.valueOf(i));
//                        i++;
                        // Log.e("val-after inc", String.valueOf(i));

                        //  int_insidecursorcount = i;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor1.moveToPrevious());
/////////////////////Changed moveToNext to moveToPrevious on aug 27th 2019

            }//if ends
            db.close();

            if (x > 0) {
                // Log.e("viewstudentaarya", arrayObj_Class_ViewStudentData2[i].getName());

                CustomAdapter adapter = new CustomAdapter();
                student_listview.setAdapter(adapter);
            }
        }

    }



    ////////////////////////////////update////////////////////////////////////////
public void uploadfromDB_SandBoxlist() {

    SQLiteDatabase db1 = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
    db1.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandID VARCHAR,SandName VARCHAR);");
    Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM SandBoxList", null);
    int x = cursor1.getCount();
    Log.d("cursor count", Integer.toString(x));

    int i = 0;
    arrayObj_Class_sandboxDetails2 = new Class_SandBoxDetails[x];
    if (cursor1.moveToFirst()) {

        do {
            Class_SandBoxDetails innerObj_Class_SandboxList = new Class_SandBoxDetails();
            innerObj_Class_SandboxList.setSandbox_id(cursor1.getString(cursor1.getColumnIndex("SandID")));
            innerObj_Class_SandboxList.setSandbox_name(cursor1.getString(cursor1.getColumnIndex("SandName")));


            arrayObj_Class_sandboxDetails2[i] = innerObj_Class_SandboxList;
            i++;

        } while (cursor1.moveToNext());
        // Log.e("academicarray1",Arrays.toString(arrayObj_Class_sandboxDetails2));

    }//if ends

    db1.close();
    if (x > 0) {

        ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_sandboxDetails2);
        dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
        sandbox_new_sp.setAdapter(dataAdapter);
    }

}
public void uploadfromDB_Academiclist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS AcademicList(AcaID VARCHAR,AcaName VARCHAR,ASandID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM AcademicList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_academicDetails2 = new Class_academicDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_academicDetails innerObj_Class_AcademicList = new Class_academicDetails();
                innerObj_Class_AcademicList.setAcademic_id(cursor1.getString(cursor1.getColumnIndex("AcaID")));
                innerObj_Class_AcademicList.setAcademic_name(cursor1.getString(cursor1.getColumnIndex("AcaName")));
                innerObj_Class_AcademicList.setAca_Sandbox_id(cursor1.getString(cursor1.getColumnIndex("ASandID")));

                arrayObj_Class_academicDetails2[i] = innerObj_Class_AcademicList;
                i++;

            } while (cursor1.moveToNext());
            // Log.e("academicarray1",Arrays.toString(arrayObj_Class_academicDetails2));

        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_academicDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            academic_new_sp.setAdapter(dataAdapter);
        }

    }



    public void uploadfromDB_Clusterist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ClusterList(clustID VARCHAR,clustName VARCHAR,clust_sand_ID VARCHAR,clust_aca_ID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ClusterList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_clusterDetails2 = new Class_ClusterDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_ClusterDetails innerObj_Class_ClusterList = new Class_ClusterDetails();
                innerObj_Class_ClusterList.setCluster_id(cursor1.getString(cursor1.getColumnIndex("clustID")));
                innerObj_Class_ClusterList.setCluster_name(cursor1.getString(cursor1.getColumnIndex("clustName")));
                innerObj_Class_ClusterList.setCluster_sand_id(cursor1.getString(cursor1.getColumnIndex("clust_sand_ID")));
                innerObj_Class_ClusterList.setCluster_aca_id(cursor1.getString(cursor1.getColumnIndex("clust_aca_ID")));

                arrayObj_Class_clusterDetails2[i] = innerObj_Class_ClusterList;
                i++;

            } while (cursor1.moveToNext());
            // Log.e("academicarray1", Arrays.toString(arrayObj_Class_sandboxDetails2));

        }//if ends
        db1.close();

        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_clusterDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            cluster_new_sp.setAdapter(dataAdapter);
        }

    }

    //institute
    public void uploadfromDB_InstitutList() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS InstituteList(inst_ID VARCHAR,inst_Name VARCHAR,inst_sand_ID VARCHAR,inst_aca_ID VARCHAR,inst_clust_ID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM InstituteList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_InstituteDetails2 = new Class_InsituteDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_InsituteDetails innerObj_Class_InstituteList = new Class_InsituteDetails();
                innerObj_Class_InstituteList.setInstitute_id(cursor1.getString(cursor1.getColumnIndex("inst_ID")));
                innerObj_Class_InstituteList.setInstitute_name(cursor1.getString(cursor1.getColumnIndex("inst_Name")));
                innerObj_Class_InstituteList.setInst_SandID(cursor1.getString(cursor1.getColumnIndex("inst_sand_ID")));
                innerObj_Class_InstituteList.setInst_AcaID(cursor1.getString(cursor1.getColumnIndex("inst_aca_ID")));
                innerObj_Class_InstituteList.setInst_ClustID(cursor1.getString(cursor1.getColumnIndex("inst_clust_ID")));
                Log.e("InstituteDetails", (cursor1.getString(cursor1.getColumnIndex("inst_Name"))));

                arrayObj_Class_InstituteDetails2[i] = innerObj_Class_InstituteList;
                i++;

            } while (cursor1.moveToNext());
            // Log.e("academicarray1", Arrays.toString(arrayObj_Class_sandboxDetails2));

        }//if ends
        db1.close();

        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_InstituteDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            institute_new_sp.setAdapter(dataAdapter);
        }

    }

    // uploadfromDB_SchoolList


//uploadfromDB_LevelList


    public void uploadfromDB_LevelList() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS LevelList(LevelID VARCHAR,LevelName VARCHAR,Lev_SandID VARCHAR,Lev_AcaID VARCHAR,Lev_ClustID VARCHAR,Lev_InstID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM LevelList ORDER BY LevelName DESC ", null);
        int x = cursor1.getCount();
        Log.d("cursor countlevelfromdb", Integer.toString(x));

        int i = 0;
        arrayObj_Class_LevelDetails2 = new Class_LevelDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Log.d("level", "enteredlevel loop");
                Class_LevelDetails innerObj_Class_LevelList = new Class_LevelDetails();
                innerObj_Class_LevelList.setLevel_id(cursor1.getString(cursor1.getColumnIndex("LevelID")));
                innerObj_Class_LevelList.setLevel_name(cursor1.getString(cursor1.getColumnIndex("LevelName")));
                innerObj_Class_LevelList.setLevel_SandID(cursor1.getString(cursor1.getColumnIndex("Lev_SandID")));
                innerObj_Class_LevelList.setLevel_AcaID(cursor1.getString(cursor1.getColumnIndex("Lev_AcaID")));
                innerObj_Class_LevelList.setLevel_ClustID(cursor1.getString(cursor1.getColumnIndex("Lev_ClustID")));
                innerObj_Class_LevelList.setLevel_InstID(cursor1.getString(cursor1.getColumnIndex("Lev_InstID")));

                arrayObj_Class_LevelDetails2[i] = innerObj_Class_LevelList;
                i++;

            } while (cursor1.moveToNext());
            // Log.e("academicarray1", Arrays.toString(arrayObj_Class_sandboxDetails2));

        }//if ends
        db1.close();

        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_LevelDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            level_new_sp.setAdapter(dataAdapter);
        }

    }


    public void uploadfromDB_StatusStudentList() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StatusStudentList(db_studentstatus VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StatusStudentList", null);
        int x = cursor1.getCount();
        Log.d("cursor countlevelfromdb", Integer.toString(x));

        int i = 0;
        arrayObj_Class_StudentStatus2 = new Class_StudentStatus[x];
        if (cursor1.moveToFirst()) {

            do {
                Log.d("level", "enteredlevel loop");
                Class_StudentStatus innerObj_Class_statusList = new Class_StudentStatus();
                innerObj_Class_statusList.setStudent_status(cursor1.getString(cursor1.getColumnIndex("db_studentstatus")));

                arrayObj_Class_StudentStatus2[i] = innerObj_Class_statusList;
                i++;

            } while (cursor1.moveToNext());
            // Log.e("academicarray1", Arrays.toString(arrayObj_Class_sandboxDetails2));

        }//if ends
        db1.close();

        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_StudentStatus2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            StudentStatus_new_sp.setAdapter(dataAdapter);
        }

    }



    public void uploadfromDB_ViewStudentList() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
       // db1.execSQL("CREATE TABLE IF NOT EXISTS ViewStudentList(ViewList_SandID VARCHAR,ViewList_AcaID VARCHAR,ViewList_ClustID VARCHAR,ViewList_InstID VARCHAR,ViewList_LevelID VARCHAR,ViewList_StuStatus VARCHAR,ViewList_StuName VARCHAR,ViewList_StuMobile VARCHAR,ViewList_StuInstName VARCHAR,ViewList_stuLevelName VARCHAR,ViewList_stuBalanceFee VARCHAR,ViewList_stuApplNo VARCHAR,ViewList_stuID VARCHAR);");
        db1.execSQL("CREATE TABLE IF NOT EXISTS ViewStudentList(ViewList_SandID VARCHAR,ViewList_AcaID VARCHAR,ViewList_ClustID VARCHAR,ViewList_InstID VARCHAR,ViewList_LevelID VARCHAR,ViewList_StuStatus VARCHAR,ViewList_StuName VARCHAR,ViewList_StuMobile VARCHAR,ViewList_StuInstName VARCHAR,ViewList_stuLevelName VARCHAR,ViewList_stuBalanceFee VARCHAR,ViewList_stuApplNo VARCHAR,ViewList_stuID VARCHAR,ViewList_balance VARCHAR);");

        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ViewStudentList", null);
        int x = cursor1.getCount();
        Log.d("cursor countlistfromdb", Integer.toString(x));

        int i = 0;
        arrayObj_Class_ViewStudentData2 = new Class_ViewStudentData[x];
        if (cursor1.moveToFirst()) {

            do {
                Log.d("view", "enteredviewlist loop");
                Class_ViewStudentData innerObj_Class_stuList = new Class_ViewStudentData();
                innerObj_Class_stuList.setSandboxid(cursor1.getString(cursor1.getColumnIndex("ViewList_SandID")));
                innerObj_Class_stuList.setAcademicid(cursor1.getString(cursor1.getColumnIndex("ViewList_AcaID")));
                innerObj_Class_stuList.setClusterid(cursor1.getString(cursor1.getColumnIndex("ViewList_ClustID")));
                innerObj_Class_stuList.setInstituteid(cursor1.getString(cursor1.getColumnIndex("ViewList_InstID")));
                innerObj_Class_stuList.setLevelid(cursor1.getString(cursor1.getColumnIndex("ViewList_LevelID")));
                innerObj_Class_stuList.setAppl_status(cursor1.getString(cursor1.getColumnIndex("ViewList_StuStatus")));
                innerObj_Class_stuList.setName(cursor1.getString(cursor1.getColumnIndex("ViewList_StuName")));
                innerObj_Class_stuList.setMobileno(cursor1.getString(cursor1.getColumnIndex("ViewList_StuMobile")));
                innerObj_Class_stuList.setApplicationNumber(cursor1.getString(cursor1.getColumnIndex("ViewList_stuApplNo")));
                innerObj_Class_stuList.setInstitutionName(cursor1.getString(cursor1.getColumnIndex("ViewList_StuInstName")));
                innerObj_Class_stuList.setLevelname(cursor1.getString(cursor1.getColumnIndex("ViewList_stuLevelName")));
                innerObj_Class_stuList.setBalanceFee(cursor1.getString(cursor1.getColumnIndex("ViewList_stuBalanceFee")));
                innerObj_Class_stuList.setStudentID(cursor1.getString(cursor1.getColumnIndex("ViewList_stuID")));

//                View tableRow = LayoutInflater.from(this).inflate(R.layout.table_feessubmitlist_item,null,false);
//                TextView studentName_TV  = (TextView) tableRow.findViewById(R.id.studentName_TV);
//                TextView applicationNumber_TV  = (TextView) tableRow.findViewById(R.id.applicationNumber_TV);
//                TextView paymentamount_TV  = (TextView) tableRow.findViewById(R.id.paymentamount_TV);
//                applicationNumber_TV.setVisibility(View.GONE);
//                studentName_TV.setText(""+cursor1.getString(cursor1.getColumnIndex("ViewList_StuName")));
//               // applicationNumber_TV.setText(""+cursor1.getString(cursor1.getColumnIndex("ViewList_stuApplNo")));
//                paymentamount_TV.setText(""+cursor1.getString(cursor1.getColumnIndex("ViewList_stuBalanceFee")));
//                tableLayout.addView(tableRow);

                arrayObj_Class_ViewStudentData2[i] = innerObj_Class_stuList;
                i++;

            } while (cursor1.moveToNext());

            // Log.e("academicarray1", Arrays.toString(arrayObj_Class_sandboxDetails2));

        }//if ends
        db1.close();

        if (x > 0) {
//
//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_ViewStudentData2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            student_listview.setAdapter(dataAdapter);
            CustomAdapter adapter = new CustomAdapter();
            student_listview.setAdapter(adapter);

        }

    }

    ///////////////////////////////////////////DELETE//////////////////////////////////////////////////////
    public void deleteSandBoxTable_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandID VARCHAR,SandName VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM SandBoxList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db1.delete("SandBoxList", null, null);
            // Toast.makeText(getApplicationContext(),"All records deleted",Toast.LENGTH_LONG).show();
        }
        db1.close();
    }

    public void deleteAcademicTable_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS AcademicList(AcaID VARCHAR,AcaName VARCHAR,ASandID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM AcademicList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db1.delete("AcademicList", null, null);
            // Toast.makeText(getApplicationContext(),"All records deleted",Toast.LENGTH_LONG).show();
        }
        db1.close();
    }

    public void deleteClusterTable_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ClusterList(clustID VARCHAR,clustName VARCHAR,clust_sand_ID VARCHAR,clust_aca_ID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM ClusterList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db1.delete("ClusterList", null, null);
            // Toast.makeText(getApplicationContext(),"All records deleted",Toast.LENGTH_LONG).show();
        }
        db1.close();
    }

    public void deleteInstituteTable_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS InstituteList(inst_ID VARCHAR,inst_Name VARCHAR,inst_sand_ID VARCHAR,inst_aca_ID VARCHAR,inst_clust_ID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM InstituteList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db1.delete("InstituteList", null, null);
            // Toast.makeText(getApplicationContext(),"All records deleted",Toast.LENGTH_LONG).show();
        }
        db1.close();
    }

    public void deleteStudentStatusTable_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StatusStudentList(db_studentstatus VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM StatusStudentList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db1.delete("StatusStudentList", null, null);
            // Toast.makeText(getApplicationContext(),"All records deleted",Toast.LENGTH_LONG).show();
        }
        db1.close();
    }

    public void deleteLevelTable_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS LevelList(LevelID VARCHAR,LevelName VARCHAR,Lev_SandID VARCHAR,Lev_AcaID VARCHAR,Lev_ClustID VARCHAR,Lev_InstID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM LevelList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db1.delete("LevelList", null, null);
            // Toast.makeText(getApplicationContext(),"All records deleted",Toast.LENGTH_LONG).show();
        }
        db1.close();
    }

    public void deleteViewTable_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ViewStudentList(ViewList_SandID VARCHAR,ViewList_AcaID VARCHAR,ViewList_ClustID VARCHAR,ViewList_InstID VARCHAR,ViewList_LevelID VARCHAR,ViewList_StuStatus VARCHAR,ViewList_StuName VARCHAR,ViewList_StuMobile VARCHAR,ViewList_StuInstName VARCHAR,ViewList_stuLevelName VARCHAR,ViewList_stuBalanceFee VARCHAR,ViewList_stuApplNo VARCHAR,ViewList_stuID VARCHAR,ViewList_balance VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM ViewStudentList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db1.delete("ViewStudentList", null, null);
            // Toast.makeText(getApplicationContext(),"All records deleted",Toast.LENGTH_LONG).show();
        }
        db1.close();
    }

////////////////////////////////////updateid's//////////////////////////////////////////////

    public void Update_academicid_spinner(String str_Sids) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS AcademicList(AcaID VARCHAR,AcaName VARCHAR,ASandID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM AcademicList WHERE ASandID='" + str_Sids + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_academicDetails2 = new Class_academicDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_academicDetails innerObj_Class_AcademicList = new Class_academicDetails();
                innerObj_Class_AcademicList.setAcademic_id(cursor1.getString(cursor1.getColumnIndex("AcaID")));
                innerObj_Class_AcademicList.setAcademic_name(cursor1.getString(cursor1.getColumnIndex("AcaName")));
                innerObj_Class_AcademicList.setAca_Sandbox_id(cursor1.getString(cursor1.getColumnIndex("ASandID")));


                arrayObj_Class_academicDetails2[i] = innerObj_Class_AcademicList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_academicDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            academic_new_sp.setAdapter(dataAdapter);
        }

    }


    public void Update_clusterid_spinner(String str_sandboxid, String str_acaid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ClusterList(clustID VARCHAR,clustName VARCHAR,clust_sand_ID VARCHAR,clust_aca_ID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ClusterList WHERE clust_sand_ID='" + str_sandboxid + "' AND clust_aca_ID='" + str_acaid + "'", null);
        try {
            int x = cursor1.getCount();
            Log.d("cursor Dcount", Integer.toString(x));

            int i = 0;
            arrayObj_Class_clusterDetails2 = new Class_ClusterDetails[x];
            if (cursor1.moveToFirst()) {

                do {
                    Class_ClusterDetails innerObj_Class_ClusterList = new Class_ClusterDetails();
                    innerObj_Class_ClusterList.setCluster_id(cursor1.getString(cursor1.getColumnIndex("clustID")));
                    innerObj_Class_ClusterList.setCluster_name(cursor1.getString(cursor1.getColumnIndex("clustName")));
                    innerObj_Class_ClusterList.setCluster_sand_id(cursor1.getString(cursor1.getColumnIndex("clust_sand_ID")));
                    innerObj_Class_ClusterList.setCluster_aca_id(cursor1.getString(cursor1.getColumnIndex("clust_aca_ID")));


                    arrayObj_Class_clusterDetails2[i] = innerObj_Class_ClusterList;
                    i++;
                } while (cursor1.moveToNext());
            }//if ends


            db1.close();
            if (x > 0) {

                ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_clusterDetails2);
                dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
                cluster_new_sp.setAdapter(dataAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void Update_InstituteId_spinner(String str_sandboxid, String str_acaid, String str_clustid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS InstituteList(inst_ID VARCHAR,inst_Name VARCHAR,inst_sand_ID VARCHAR,inst_aca_ID VARCHAR,inst_clust_ID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM InstituteList WHERE inst_sand_ID='" + str_sandboxid + "' AND inst_aca_ID='" + str_acaid + "' AND inst_clust_ID='" + str_clustid + "'", null);
        try {
            int x = cursor1.getCount();
            Log.d("cursor Dcount", Integer.toString(x));

            int i = 0;
            arrayObj_Class_InstituteDetails2 = new Class_InsituteDetails[x];
            if (cursor1.moveToFirst()) {

                do {
                    Class_InsituteDetails innerObj_Class_InstituteList = new Class_InsituteDetails();
                    innerObj_Class_InstituteList.setInstitute_id(cursor1.getString(cursor1.getColumnIndex("inst_ID")));
                    innerObj_Class_InstituteList.setInstitute_name(cursor1.getString(cursor1.getColumnIndex("inst_Name")));
                    innerObj_Class_InstituteList.setInst_SandID(cursor1.getString(cursor1.getColumnIndex("inst_sand_ID")));
                    innerObj_Class_InstituteList.setInst_AcaID(cursor1.getString(cursor1.getColumnIndex("inst_aca_ID")));
                    innerObj_Class_InstituteList.setInst_ClustID(cursor1.getString(cursor1.getColumnIndex("inst_clust_ID")));

                    Log.e("InstituteDetails2", (cursor1.getString(cursor1.getColumnIndex("inst_Name"))));

                    arrayObj_Class_InstituteDetails2[i] = innerObj_Class_InstituteList;
                    i++;
                } while (cursor1.moveToNext());
            }//if ends


            db1.close();
            if (x > 0) {


                ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_InstituteDetails2);
                dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
                institute_new_sp.setAdapter(dataAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //Update_LevelID_spinner
    public void Update_LevelID_spinner(String str_sandboxid, String str_acaid, String str_clustid, String str_instid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS LevelList(LevelID VARCHAR,LevelName VARCHAR,Lev_SandID VARCHAR,Lev_AcaID VARCHAR,Lev_ClustID VARCHAR,Lev_InstID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM LevelList WHERE Lev_SandID='" + str_sandboxid + "' AND Lev_AcaID='" + str_acaid + "'AND Lev_InstID='" + str_instid + "'", null);

        try {
            int x = cursor1.getCount();
            Log.d("cursor Dcountlevelid", Integer.toString(x));

            int i = 0;
            arrayObj_Class_LevelDetails2 = new Class_LevelDetails[x];
            if (cursor1.moveToFirst()) {

                do {
                    Class_LevelDetails innerObj_Class_LevelList = new Class_LevelDetails();
                    innerObj_Class_LevelList.setLevel_id(cursor1.getString(cursor1.getColumnIndex("LevelID")));
                    innerObj_Class_LevelList.setLevel_name(cursor1.getString(cursor1.getColumnIndex("LevelName")));
                    innerObj_Class_LevelList.setLevel_SandID(cursor1.getString(cursor1.getColumnIndex("Lev_SandID")));
                    innerObj_Class_LevelList.setLevel_AcaID(cursor1.getString(cursor1.getColumnIndex("Lev_AcaID")));
                    innerObj_Class_LevelList.setLevel_ClustID(cursor1.getString(cursor1.getColumnIndex("Lev_ClustID")));
                    innerObj_Class_LevelList.setLevel_InstID(cursor1.getString(cursor1.getColumnIndex("Lev_InstID")));


                    arrayObj_Class_LevelDetails2[i] = innerObj_Class_LevelList;
                    i++;
                } while (cursor1.moveToNext());
            }//if ends


            db1.close();
            if (x > 0) {

                ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_LevelDetails2);
                dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
                level_new_sp.setAdapter(dataAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void Update_ViewStuListID_spinner(String str_sandboxid, String str_acaid, String str_clustid, String str_instid,String str_levelid,String stuStatus) {
        SQLiteDatabase db1 = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ViewStudentList(ViewList_SandID VARCHAR,ViewList_AcaID VARCHAR,ViewList_ClustID VARCHAR,ViewList_InstID VARCHAR,ViewList_LevelID VARCHAR,ViewList_StuStatus VARCHAR,ViewList_StuName VARCHAR,ViewList_StuMobile VARCHAR,ViewList_StuInstName VARCHAR,ViewList_stuLevelName VARCHAR,ViewList_stuBalanceFee VARCHAR,ViewList_stuApplNo VARCHAR,ViewList_stuID VARCHAR,ViewList_balance VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ViewStudentList WHERE ViewList_SandID='" + str_sandboxid + "' AND ViewList_AcaID='" + str_acaid + "'AND ViewList_ClustID='" + str_clustid + "'AND ViewList_InstID='" + str_instid + "'AND ViewList_LevelID='" + str_levelid + "'AND ViewList_StuStatus='" + stuStatus + "'", null);

        try {
            int x = cursor1.getCount();
            listcountdisplay=x;
            Log.e("cursor Dcountlevelid", Integer.toString(x));
            Log.e("cursor listcoutdisplay1", String.valueOf(listcountdisplay));
            listcount_TV.setText(String.valueOf(listcountdisplay));
            int i = 0;
            arrayObj_Class_ViewStudentData2 = new Class_ViewStudentData[x];
          //  Changed moveToFirst to moveToLast on aug 27th 2019

            // if (cursor1.moveToFirst()) {
                if (cursor1.moveToLast()) {

                do {

                Log.d("view", "enteredviewlist loop");
                Class_ViewStudentData innerObj_Class_stuList = new Class_ViewStudentData();
                innerObj_Class_stuList.setSandboxid(cursor1.getString(cursor1.getColumnIndex("ViewList_SandID")));
                innerObj_Class_stuList.setAcademicid(cursor1.getString(cursor1.getColumnIndex("ViewList_AcaID")));
                innerObj_Class_stuList.setClusterid(cursor1.getString(cursor1.getColumnIndex("ViewList_ClustID")));
                innerObj_Class_stuList.setInstituteid(cursor1.getString(cursor1.getColumnIndex("ViewList_InstID")));
                innerObj_Class_stuList.setLevelid(cursor1.getString(cursor1.getColumnIndex("ViewList_LevelID")));
                innerObj_Class_stuList.setAppl_status(cursor1.getString(cursor1.getColumnIndex("ViewList_StuStatus")));
                innerObj_Class_stuList.setName(cursor1.getString(cursor1.getColumnIndex("ViewList_StuName")));
                innerObj_Class_stuList.setMobileno(cursor1.getString(cursor1.getColumnIndex("ViewList_StuMobile")));
                innerObj_Class_stuList.setApplicationNumber(cursor1.getString(cursor1.getColumnIndex("ViewList_stuApplNo")));
                innerObj_Class_stuList.setInstitutionName(cursor1.getString(cursor1.getColumnIndex("ViewList_StuInstName")));
                innerObj_Class_stuList.setLevelname(cursor1.getString(cursor1.getColumnIndex("ViewList_stuLevelName")));
                innerObj_Class_stuList.setBalanceFee(cursor1.getString(cursor1.getColumnIndex("ViewList_stuBalanceFee")));
                innerObj_Class_stuList.setStudentID(cursor1.getString(cursor1.getColumnIndex("ViewList_stuID")));
                arrayObj_Class_ViewStudentData2[i] = innerObj_Class_stuList;
                i++;

                } while (cursor1.moveToPrevious());
/////////////////////Changed moveToNext to moveToPrevious on aug 27th 2019

        }//if ends
        db1.close();

        if (x > 0)
        {
           // Log.e("viewstudentaarya", arrayObj_Class_ViewStudentData2[i].getName());
                CustomAdapter adapter = new CustomAdapter();
                student_listview.setAdapter(adapter);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //fees_GT_zero


    public void Update_ViewStuList_fees_Equal_zero(String str_sandboxid, String str_acaid, String str_clustid, String str_instid,String str_levelid,String stuStatus) {
        SQLiteDatabase db1 = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ViewStudentList(ViewList_SandID VARCHAR,ViewList_AcaID VARCHAR,ViewList_ClustID VARCHAR,ViewList_InstID VARCHAR,ViewList_LevelID VARCHAR,ViewList_StuStatus VARCHAR,ViewList_StuName VARCHAR,ViewList_StuMobile VARCHAR,ViewList_StuInstName VARCHAR,ViewList_stuLevelName VARCHAR,ViewList_stuBalanceFee VARCHAR,ViewList_stuApplNo VARCHAR,ViewList_stuID VARCHAR,ViewList_balance VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ViewStudentList WHERE ViewList_SandID='" + str_sandboxid + "' AND ViewList_AcaID='" + str_acaid + "'AND ViewList_ClustID='" + str_clustid + "'AND ViewList_InstID='" + str_instid + "'AND ViewList_LevelID='" + str_levelid + "'AND ViewList_StuStatus='" + stuStatus + "' AND ViewList_balance='" + fees_filter_intval + "'", null);

        try {
            int x = cursor1.getCount();
            listcountdisplay=x;
            Log.e("cursor listcoutdisplay2", String.valueOf(listcountdisplay));
            listcount_TV.setText(String.valueOf(listcountdisplay));

            Log.d("cursor Dcountlevelid", Integer.toString(x));

            int i = 0;
            arrayObj_Class_ViewStudentData2 = new Class_ViewStudentData[x];
            //  Changed moveToFirst to moveToLast on aug 27th 2019

            // if (cursor1.moveToFirst()) {
            if (cursor1.moveToLast()) {

                do {

                    Log.d("view", "enteredviewlist loop");
                    Class_ViewStudentData innerObj_Class_stuList = new Class_ViewStudentData();
                    innerObj_Class_stuList.setSandboxid(cursor1.getString(cursor1.getColumnIndex("ViewList_SandID")));
                    innerObj_Class_stuList.setAcademicid(cursor1.getString(cursor1.getColumnIndex("ViewList_AcaID")));
                    innerObj_Class_stuList.setClusterid(cursor1.getString(cursor1.getColumnIndex("ViewList_ClustID")));
                    innerObj_Class_stuList.setInstituteid(cursor1.getString(cursor1.getColumnIndex("ViewList_InstID")));
                    innerObj_Class_stuList.setLevelid(cursor1.getString(cursor1.getColumnIndex("ViewList_LevelID")));
                    innerObj_Class_stuList.setAppl_status(cursor1.getString(cursor1.getColumnIndex("ViewList_StuStatus")));
                    innerObj_Class_stuList.setName(cursor1.getString(cursor1.getColumnIndex("ViewList_StuName")));
                    innerObj_Class_stuList.setMobileno(cursor1.getString(cursor1.getColumnIndex("ViewList_StuMobile")));
                    innerObj_Class_stuList.setApplicationNumber(cursor1.getString(cursor1.getColumnIndex("ViewList_stuApplNo")));
                    innerObj_Class_stuList.setInstitutionName(cursor1.getString(cursor1.getColumnIndex("ViewList_StuInstName")));
                    innerObj_Class_stuList.setLevelname(cursor1.getString(cursor1.getColumnIndex("ViewList_stuLevelName")));
                    innerObj_Class_stuList.setBalanceFee(cursor1.getString(cursor1.getColumnIndex("ViewList_stuBalanceFee")));
                    innerObj_Class_stuList.setStudentID(cursor1.getString(cursor1.getColumnIndex("ViewList_stuID")));

                    arrayObj_Class_ViewStudentData2[i] = innerObj_Class_stuList;
                    i++;

                } while (cursor1.moveToPrevious());
/////////////////////Changed moveToNext to moveToPrevious on aug 27th 2019

            }//if ends
            db1.close();

            if (x > 0)
            {
                // Log.e("viewstudentaarya", arrayObj_Class_ViewStudentData2[i].getName());

                CustomAdapter adapter = new CustomAdapter();
                student_listview.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void Update_ViewStuList_fees_GT_zero(String str_sandboxid, String str_acaid, String str_clustid, String str_instid,String str_levelid,String stuStatus) {
        SQLiteDatabase db1 = this.openOrCreateDatabase("stuListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ViewStudentList(ViewList_SandID VARCHAR,ViewList_AcaID VARCHAR,ViewList_ClustID VARCHAR,ViewList_InstID VARCHAR,ViewList_LevelID VARCHAR,ViewList_StuStatus VARCHAR,ViewList_StuName VARCHAR,ViewList_StuMobile VARCHAR,ViewList_StuInstName VARCHAR,ViewList_stuLevelName VARCHAR,ViewList_stuBalanceFee VARCHAR,ViewList_stuApplNo VARCHAR,ViewList_stuID VARCHAR,ViewList_balance VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ViewStudentList WHERE ViewList_SandID='" + str_sandboxid + "' AND ViewList_AcaID='" + str_acaid + "'AND ViewList_ClustID='" + str_clustid + "'AND ViewList_InstID='" + str_instid + "'AND ViewList_LevelID='" + str_levelid + "'AND ViewList_StuStatus='" + stuStatus + "' AND ViewList_balance>'" + fees_filter_intval + "'", null);

        try {
            int x = cursor1.getCount();
            listcountdisplay=x;
            Log.e("cursor listcoutdisplay3", String.valueOf(listcountdisplay));
            listcount_TV.setText(String.valueOf(listcountdisplay));
            Log.d("cursor Dcountlevelid", Integer.toString(x));

            int i = 0;
            arrayObj_Class_ViewStudentData2 = new Class_ViewStudentData[x];
            //  Changed moveToFirst to moveToLast on aug 27th 2019

            // if (cursor1.moveToFirst()) {
            if (cursor1.moveToLast()) {
                do {

                    Log.d("view", "enteredviewlist loop");
                    Class_ViewStudentData innerObj_Class_stuList = new Class_ViewStudentData();
                    innerObj_Class_stuList.setSandboxid(cursor1.getString(cursor1.getColumnIndex("ViewList_SandID")));
                    innerObj_Class_stuList.setAcademicid(cursor1.getString(cursor1.getColumnIndex("ViewList_AcaID")));
                    innerObj_Class_stuList.setClusterid(cursor1.getString(cursor1.getColumnIndex("ViewList_ClustID")));
                    innerObj_Class_stuList.setInstituteid(cursor1.getString(cursor1.getColumnIndex("ViewList_InstID")));
                    innerObj_Class_stuList.setLevelid(cursor1.getString(cursor1.getColumnIndex("ViewList_LevelID")));
                    innerObj_Class_stuList.setAppl_status(cursor1.getString(cursor1.getColumnIndex("ViewList_StuStatus")));
                    innerObj_Class_stuList.setName(cursor1.getString(cursor1.getColumnIndex("ViewList_StuName")));
                    innerObj_Class_stuList.setMobileno(cursor1.getString(cursor1.getColumnIndex("ViewList_StuMobile")));
                    innerObj_Class_stuList.setApplicationNumber(cursor1.getString(cursor1.getColumnIndex("ViewList_stuApplNo")));
                    innerObj_Class_stuList.setInstitutionName(cursor1.getString(cursor1.getColumnIndex("ViewList_StuInstName")));
                    innerObj_Class_stuList.setLevelname(cursor1.getString(cursor1.getColumnIndex("ViewList_stuLevelName")));
                    innerObj_Class_stuList.setBalanceFee(cursor1.getString(cursor1.getColumnIndex("ViewList_stuBalanceFee")));
                    innerObj_Class_stuList.setStudentID(cursor1.getString(cursor1.getColumnIndex("ViewList_stuID")));

                    arrayObj_Class_ViewStudentData2[i] = innerObj_Class_stuList;
                    i++;

                } while (cursor1.moveToPrevious());
/////////////////////Changed moveToNext to moveToPrevious on aug 27th 2019

            }//if ends
            db1.close();

            if (x > 0)
            {
                // Log.e("viewstudentaarya", arrayObj_Class_ViewStudentData2[i].getName());

                CustomAdapter adapter = new CustomAdapter();
                student_listview.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

                Intent i = new Intent(Activity_Student_List.this, Activity_MarketingHomeScreen.class);
                startActivity(i);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Activity_Student_List.this, Activity_MarketingHomeScreen.class);
        startActivity(i);
        finish();

    }

}
