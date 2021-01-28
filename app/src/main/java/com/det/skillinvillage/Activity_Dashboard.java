package com.det.skillinvillage;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.det.skillinvillage.model.Class_Get_User_DocumentResponse;
import com.det.skillinvillage.model.Class_ListVersion;
import com.det.skillinvillage.model.Class_UserPaymentList;
import com.det.skillinvillage.model.Class_dashboardList;
import com.det.skillinvillage.model.Class_getUserDashboardResponse;
import com.det.skillinvillage.model.Class_getUserPaymentResponse;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;
import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferencebook_usercredential;

public class Activity_Dashboard extends AppCompatActivity {

    TextView dropoutscount_TV, femalecount_TV, malecount_TV, studentcount_TV, schoolcount_TV, villagecount_TV;
    SharedPreferences sharedpref_loginuserid_Obj;
    SharedPreferences sharedpreferencebook_usercredential_Obj;
    String str_loginuserID, str_dashboard_status = "", str_no_of_villages, str_no_of_students, str_no_of_schools, str_no_of_male, str_no_of_female, str_no_of_dropouts, str_dashboard_status_barchart = "";
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    //barChart barChart;
    BarChart barChart;
    Class_PiechartData[] arrayObj_Class_BarchartData;
    SoapPrimitive response_soapobj_institute_id, response_soapobj_institute_name, response_soapobj_student_count, response_soapobj_receivable, response_soapobj_received, response_soapobj_balance,response_soapobj_sandbox_id,response_soapobj_sandbox_name;
    int barchart_count;
    ArrayList<Entry> yvalues2 = new ArrayList<Entry>();
    ArrayList<String> xVals2 = new ArrayList<String>();
    Spinner loadcenters_SP,loadSandbox_SP;
    int int_val_receivable, int_val_received, int_val_balance;
    Float float_val_receivable, float_val_received, float_val_balance;

    Class_Dashboard_Institute[] arrayObjclass_dashboard_institute, arrayObjclass_dashboard_institute2;
    Class_Dashboard_Institute Objclass_dashboard_institute;
    String str_dashboard_instid = "", str_dashboard_instname = "", selected_instituteID = "", selected_institute = "", str_dashboard_receivable, str_dashboard_received, str_dashboard_balance,str_dashboard_sandid="",str_dashboard_sandname="";
    Class_Dashboard_FeesSummary[] arrayObjclass_dashboard_feessummary, arrayObjclass_dashboard_feessummary2;
    Class_Dashboard_FeesSummary Objclass_dashboard_feessummary;

    TextView viewmaps_TV;


    String str_latlong_status = "", str_latitude = "", str_longitude = "";
    Class_GoogleLocations[] arrayObj_class_GoogleLocations;
    int latlongcount = 0;


    Class_Scorecards_CenterSelection[] arrayObjclass_Scorecards_CenterSelection,arrayObjclass_Scorecards_CenterSelection2;
    Class_Scorecards_CenterSelection Objclass_Scorecards_CenterSelection;


    Class_Dashboard_SandBox[] arrayObjclass_dashboard_sandBoxes,arrayObjclass_dashboard_sandBoxes2;
    Class_Dashboard_SandBox Objclass_dashboard_sandBox;
    //SupportMapFragment mapFragment;

    String selected_sandID="",selected_sandname="";
    LinearLayout scorecardlayout1_LL,scorecardlayout2_LL;
    TextView feesummarheading_TV;
    Interface_userservice userService1;



    //Added on Nov 9th 2020
   // LinearLayout dashboard_LL,feessummary_LL,viewmaps_LL;
    ImageView dashboard_downarrow_IV,dashboard_uparrow_IV,feessummary_IV,feessummary_uparrow_IV,viewmaps_downarrow_IV,viewmaps_uparrow_IV;
   // CardView cardview_dashboard,cardview_Feessummary,cardview_Maps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_new);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dashboard");
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();
        userService1 = Class_ApiUtils.getUserService();
        sharedpref_loginuserid_Obj = getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);

        str_loginuserID = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();

        //str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();
        dropoutscount_TV = findViewById(R.id.dropoutscount_TV);
        femalecount_TV = findViewById(R.id.femalecount_TV);
        malecount_TV = findViewById(R.id.malecount_TV);
        studentcount_TV = findViewById(R.id.studentcount_TV);
        schoolcount_TV = findViewById(R.id.schoolcount_TV);
        villagecount_TV = findViewById(R.id.villagecount_TV);
       // viewmaps_TV = findViewById(R.id.viewmaps_TV);
//        barChart = (barChart) findViewById(R.id.barChart);
//        barChart.setUsePercentValues(true);
        barChart = findViewById(R.id.barchart);
        loadcenters_SP = findViewById(R.id.loadcenters_SP);
        loadSandbox_SP= findViewById(R.id.loadSandbox_SP);
        scorecardlayout1_LL= findViewById(R.id.scorecardlayout1_LL);
        scorecardlayout2_LL= findViewById(R.id.scorecardlayout2_LL);
      //  feesummarheading_TV= findViewById(R.id.feesummarheading_TV);


        //Added by shivaleela on 9th NOv 2020
//        dashboard_LL=(LinearLayout)findViewById(R.id.dashboard_LL);
//        feessummary_LL=(LinearLayout)findViewById(R.id.feessummary_LL);
//        viewmaps_LL=(LinearLayout)findViewById(R.id.viewmaps_LL);
//        dashboard_downarrow_IV=(ImageView)findViewById(R.id.dashboard_downarrow_IV);
//        dashboard_uparrow_IV=(ImageView)findViewById(R.id.dashboard_uparrow_IV);
//        feessummary_IV=(ImageView)findViewById(R.id.feessummary_IV);
//        feessummary_uparrow_IV=(ImageView)findViewById(R.id.feessummary_uparrow_IV);
//        viewmaps_downarrow_IV=(ImageView)findViewById(R.id.viewmaps_downarrow_IV);
//        viewmaps_uparrow_IV=(ImageView)findViewById(R.id.viewmaps_uparrow_IV);

//        cardview_dashboard=(CardView) findViewById(R.id.cardview_dashboard);
//        cardview_Feessummary=(CardView)findViewById(R.id.cardview_Feessummary);
//        cardview_Maps=(CardView)findViewById(R.id.cardview_Maps);

//        cardview_dashboard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dashboard_LL.setVisibility(View.VISIBLE);
//                feessummary_LL.setVisibility(View.GONE);
//                viewmaps_LL.setVisibility(View.GONE);
//
//                dashboard_uparrow_IV.setVisibility(View.VISIBLE);
//                dashboard_downarrow_IV.setVisibility(View.GONE);
//
//                feessummary_uparrow_IV.setVisibility(View.GONE);
//                feessummary_IV.setVisibility(View.VISIBLE);
//
//
//                viewmaps_uparrow_IV.setVisibility(View.GONE);
//                viewmaps_downarrow_IV.setVisibility(View.VISIBLE);
//
//
//            }
//        });


//        dashboard_downarrow_IV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dashboard_LL.setVisibility(View.VISIBLE);
//                feessummary_LL.setVisibility(View.GONE);
//                viewmaps_LL.setVisibility(View.GONE);
//
//                dashboard_uparrow_IV.setVisibility(View.VISIBLE);
//                dashboard_downarrow_IV.setVisibility(View.GONE);
//
//                feessummary_uparrow_IV.setVisibility(View.GONE);
//                feessummary_IV.setVisibility(View.VISIBLE);
//
//
//                viewmaps_uparrow_IV.setVisibility(View.GONE);
//                viewmaps_downarrow_IV.setVisibility(View.VISIBLE);
//
//
//            }
//        });


//        dashboard_uparrow_IV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dashboard_LL.setVisibility(View.GONE);
//                feessummary_LL.setVisibility(View.GONE);
//                viewmaps_LL.setVisibility(View.GONE);
//
//                dashboard_uparrow_IV.setVisibility(View.GONE);
//                dashboard_downarrow_IV.setVisibility(View.VISIBLE);
//
//
//            }
//        });

//        cardview_Feessummary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dashboard_LL.setVisibility(View.GONE);
//                feessummary_LL.setVisibility(View.VISIBLE);
//                viewmaps_LL.setVisibility(View.GONE);
//
//                feessummary_uparrow_IV.setVisibility(View.VISIBLE);
//                feessummary_IV.setVisibility(View.GONE);
//
//                dashboard_uparrow_IV.setVisibility(View.GONE);
//                dashboard_downarrow_IV.setVisibility(View.VISIBLE);
//
//                viewmaps_uparrow_IV.setVisibility(View.GONE);
//                viewmaps_downarrow_IV.setVisibility(View.VISIBLE);
//
//
//
//
//            }
//        });

//        feessummary_IV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                dashboard_LL.setVisibility(View.GONE);
////                feessummary_LL.setVisibility(View.VISIBLE);
////                viewmaps_LL.setVisibility(View.GONE);
//
//                feessummary_uparrow_IV.setVisibility(View.VISIBLE);
//                feessummary_IV.setVisibility(View.GONE);
//
//                dashboard_uparrow_IV.setVisibility(View.GONE);
//                dashboard_downarrow_IV.setVisibility(View.VISIBLE);
//
//                viewmaps_uparrow_IV.setVisibility(View.GONE);
//                viewmaps_downarrow_IV.setVisibility(View.VISIBLE);
//
//
//
//
//            }
//        });


//        feessummary_uparrow_IV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dashboard_LL.setVisibility(View.GONE);
//                viewmaps_LL.setVisibility(View.GONE);
//
//                feessummary_uparrow_IV.setVisibility(View.GONE);
//                feessummary_IV.setVisibility(View.VISIBLE);
//                feessummary_LL.setVisibility(View.GONE);
//
//            }
//        });



//        cardview_Maps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dashboard_LL.setVisibility(View.GONE);
//                feessummary_LL.setVisibility(View.GONE);
//                viewmaps_LL.setVisibility(View.VISIBLE);
//
//                viewmaps_uparrow_IV.setVisibility(View.VISIBLE);
//                viewmaps_downarrow_IV.setVisibility(View.GONE);
//
//                feessummary_uparrow_IV.setVisibility(View.GONE);
//                feessummary_IV.setVisibility(View.VISIBLE);
//
//                dashboard_uparrow_IV.setVisibility(View.GONE);
//                dashboard_downarrow_IV.setVisibility(View.VISIBLE);
//
//
//
//            }
//        });

//        viewmaps_downarrow_IV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dashboard_LL.setVisibility(View.GONE);
//                feessummary_LL.setVisibility(View.GONE);
//                viewmaps_LL.setVisibility(View.VISIBLE);
//
//                viewmaps_uparrow_IV.setVisibility(View.VISIBLE);
//                viewmaps_downarrow_IV.setVisibility(View.GONE);
//
//                feessummary_uparrow_IV.setVisibility(View.GONE);
//                feessummary_IV.setVisibility(View.VISIBLE);
//
//                dashboard_uparrow_IV.setVisibility(View.GONE);
//                dashboard_downarrow_IV.setVisibility(View.VISIBLE);
//
//
//
//            }
//        });


//        viewmaps_uparrow_IV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dashboard_LL.setVisibility(View.GONE);
//                feessummary_LL.setVisibility(View.GONE);
//                viewmaps_LL.setVisibility(View.GONE);
//
//
//                viewmaps_uparrow_IV.setVisibility(View.GONE);
//                viewmaps_downarrow_IV.setVisibility(View.VISIBLE);
//
//
//            }
//        });


//=====================================================================
        loadcenters_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Objclass_dashboard_institute = (Class_Dashboard_Institute) loadcenters_SP.getSelectedItem();
                selected_instituteID = Objclass_dashboard_institute.getDashboard_inst_id();
                selected_institute = Objclass_dashboard_institute.getDashboard_inst_name();


                Log.e("selected_instituteID..", selected_instituteID);
                Log.e("selected_institute..", selected_institute);
                Update_id_feessummarytable(selected_instituteID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadSandbox_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Objclass_dashboard_sandBox = (Class_Dashboard_SandBox) loadSandbox_SP.getSelectedItem();
                selected_sandID = Objclass_dashboard_sandBox.getDashboard_sand_id();
                selected_sandname = Objclass_dashboard_sandBox.getDashboard_sand_name();


                Log.e("selected_sandID..", selected_sandID);
                Log.e("selected_sandname..", selected_sandname);
                Update_id_sandbox(selected_sandID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        viewmaps_TV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Activity_Dashboard.this, Activity_MarkerGoogleMaps.class);
//                startActivity(i);
//            }
//        });
        getdashboarddata();
        getbarchartdata();
        // getVillagelat_long();
        uploadSandboxfromDB_list();
        uploadCentersfromDB_list();
        uploadFeesSummaryfromDB_list();
    }


    public void getdashboarddata() {
        deleteSandBoxDrodownTable_B4insertion();
        deleteSandBoxTable_B4insertion();
        if (isInternetPresent) {
            getdashboardinfo_new();
//            GetDashboardInfoTask task = new GetDashboardInfoTask(Activity_Dashboard.this);
//            task.execute();
        } else {
            loadSandbox_SP.setVisibility(View.GONE);
            scorecardlayout1_LL.setVisibility(View.GONE);
            scorecardlayout2_LL.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }


    }

//    private class GetDashboardInfoTask extends AsyncTask<String, Void, Void> {
//        ProgressDialog dialog;
//        Context context;
//
//        protected void onPreExecute() {
//
//            dialog.setMessage("Please wait..");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//
//        }
//
//
//        @Override
//        protected Void doInBackground(String... params) {
//            Log.i("list", "doInBackground");
//
//            getdashboardinfo();  // call of details
//            return null;
//        }
//
//        public GetDashboardInfoTask(Context context1) {
//            context = context1;
//            dialog = new ProgressDialog(context1, R.style.AppCompatAlertDialogStyle);
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//            if ((dialog != null) && dialog.isShowing()) {
//                dialog.dismiss();
//
//            }
//
//
//            if (str_dashboard_status.equals("Error")) {
//
//                Toast.makeText(getApplicationContext(), "No Result", Toast.LENGTH_SHORT).show();
//
//            } else {
//                uploadSandboxDropdownfromDB_list();
//                uploadSandboxfromDB_list();
//            }
//            Log.e("tag", "Reached the onPostExecute");
//
//        }//end of onPostExecute
//    }// end Async task
//
//    private void getdashboardinfo() {
//        Vector<SoapObject> result1 = null;
//
//        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
//        String METHOD_NAME = "LoadDashboard";
//        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/LoadDashboard";
//
//        try {
//            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
//            request.addProperty("User_ID", str_loginuserID);
//            Log.i("value at request", request.toString());
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
//                SoapObject obj2 = (SoapObject) response.getProperty(0);
//                Log.i("value at response", response.toString());
//                arrayObjclass_Scorecards_CenterSelection=new Class_Scorecards_CenterSelection[response.getPropertyCount()];
//                arrayObjclass_dashboard_sandBoxes=new Class_Dashboard_SandBox[response.getPropertyCount()];
//
//                for (int i = 0; i < response.getPropertyCount(); i++) {
//                    SoapObject list = (SoapObject) response.getProperty(i);
//                    SoapPrimitive soap_no_of_students, soap_no_of_villages, soap_no_of_schools, soap_no_of_male, soap_no_of_female, soap_no_of_dropouts;
//
//                    str_dashboard_status = list.getProperty("Dashboard_Status").toString();
//                    if (str_dashboard_status.equals("Error")) {
//                        Log.e("str_dashboard_status", str_dashboard_status);
//
//                    } else {
//                        soap_no_of_students = (SoapPrimitive) obj2.getProperty("Count_Student");
//                        soap_no_of_villages = (SoapPrimitive) obj2.getProperty("Count_Village");
//                        soap_no_of_schools = (SoapPrimitive) obj2.getProperty("Count_Institute");
//                        soap_no_of_male = (SoapPrimitive) obj2.getProperty("Count_Male");
//                        soap_no_of_female = (SoapPrimitive) obj2.getProperty("Count_Female");
//                        soap_no_of_dropouts = (SoapPrimitive) obj2.getProperty("Count_Dropout");
//
//                        response_soapobj_sandbox_id = (SoapPrimitive) obj2.getProperty("Sandbox_ID");
//                        response_soapobj_sandbox_name= (SoapPrimitive) obj2.getProperty("Sandbox_Name");
//
//                        Class_Scorecards_CenterSelection innerObj_class_Scorecards_CenterSelection = new Class_Scorecards_CenterSelection();
//                        innerObj_class_Scorecards_CenterSelection.setScorecards_SandboxID(response_soapobj_sandbox_id.toString());
//                        innerObj_class_Scorecards_CenterSelection.setScorecards_SandboxName(response_soapobj_sandbox_name.toString());
//                        innerObj_class_Scorecards_CenterSelection.setScorecards_villages(soap_no_of_villages.toString());
//                        innerObj_class_Scorecards_CenterSelection.setScorecards_schools(soap_no_of_schools.toString());
//                        innerObj_class_Scorecards_CenterSelection.setScorecards_students(soap_no_of_students.toString());
//                        innerObj_class_Scorecards_CenterSelection.setScorecards_male(soap_no_of_male.toString());
//                        innerObj_class_Scorecards_CenterSelection.setScorecards_female(soap_no_of_female.toString());
//                        innerObj_class_Scorecards_CenterSelection.setScorecards_dropouts(soap_no_of_dropouts.toString());
//
//                        Class_Dashboard_SandBox class_dashboard_sandBox = new Class_Dashboard_SandBox();
//                        class_dashboard_sandBox.setDashboard_sand_id(response_soapobj_sandbox_id.toString());
//                        class_dashboard_sandBox.setDashboard_sand_name(response_soapobj_sandbox_name.toString());
//
//                        str_no_of_villages = list.getProperty("Count_Village").toString();
//                        str_no_of_students = list.getProperty("Count_Student").toString();
//                        str_no_of_schools = list.getProperty("Count_Institute").toString();
//                        str_no_of_male = list.getProperty("Count_Male").toString();
//                        str_no_of_female = list.getProperty("Count_Female").toString();
//                        str_no_of_dropouts = list.getProperty("Count_Dropout").toString();
//
//                        str_dashboard_sandid = list.getProperty("Sandbox_ID").toString();
//                        str_dashboard_sandname = list.getProperty("Sandbox_Name").toString();
//                        arrayObjclass_Scorecards_CenterSelection[i]=innerObj_class_Scorecards_CenterSelection;
//                        arrayObjclass_dashboard_sandBoxes[i]=class_dashboard_sandBox;
//
//                        Log.e("soap_no_of_students", String.valueOf(soap_no_of_students));
//
//                        runOnUiThread(new Runnable() {
//
//                            @Override
//                            public void run() {
//
//                                // Stuff that updates the UI
//
//                                setvalues();
//                            }
//                        });
//
//                        DBCreate_dash_SandBoxdetails_insert_2SQLiteDB(str_dashboard_sandid, str_dashboard_sandname);
//                        DBCreate_SandBoxdetails_insert_2SQLiteDB(str_dashboard_sandid, str_dashboard_sandname,str_no_of_villages,str_no_of_schools,str_no_of_students,str_no_of_male,str_no_of_female,str_no_of_dropouts);
//                    }
//
//
//                }// End of for loop
////                uploadSandboxDropdownfromDB_list();
////                uploadSandboxfromDB_list();
//            } catch (Throwable t) {
//
//                Log.e("request fail", "> " + t.getMessage());
//            }
//        } catch (Throwable t) {
//            Log.e("UnRegister  Error", "> " + t.getMessage());
//
//        }
//
//
//    }//end of getlist()


    public void getdashboardinfo_new() {

        Call<Class_getUserDashboardResponse> call = userService1.getdashboardDetails(str_loginuserID);
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_Dashboard.this);
        //  progressDoalog.setMax(100);
        //  progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<Class_getUserDashboardResponse>() {
            @Override
            public void onResponse(Call<Class_getUserDashboardResponse> call, Response<Class_getUserDashboardResponse> response) {
                Log.e("Entered resp", "getdashboardDetails");

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    Class_getUserDashboardResponse class_loginresponse = response.body();
                    if (class_loginresponse.getStatus()) {
                        List<Class_dashboardList> monthContents_list = response.body().getListVersion();

                        Class_dashboardList []  arrayObj_Class_monthcontents = new Class_dashboardList[monthContents_list.size()];
                        arrayObjclass_Scorecards_CenterSelection=new Class_Scorecards_CenterSelection[monthContents_list.size()];
                        arrayObjclass_dashboard_sandBoxes=new Class_Dashboard_SandBox[monthContents_list.size()];

                        for (int i = 0; i < arrayObj_Class_monthcontents.length; i++) {


                            Log.e("getdashboard", String.valueOf(class_loginresponse.getStatus()));
                            Log.e("getdashboard", class_loginresponse.getMessage());
//                            {
//                                "Dashboard_ID": 0,
//                                    "Sandbox_ID": "0",
//                                    "Sandbox_Name": "ALL",
//                                    "Count_Institute": "59",
//                                    "Count_Village": "53",
//                                    "Count_Applicant": "3822",
//                                    "Count_Admission": "3822",
//                                    "Count_Male": "0",
//                                    "Count_Female": "0",
//                                    "Count_Dropout": "525",
//                                    "Count_Student": "3804",
//                                    "Dashboard_Status": "Active"
//                            }
                            Class_dashboardList innerObj_Class_SandboxList = new Class_dashboardList();
                            innerObj_Class_SandboxList.setDashboardID(class_loginresponse.getListVersion().get(i).getDashboardID());
                            innerObj_Class_SandboxList.setSandboxID(class_loginresponse.getListVersion().get(i).getSandboxID());
                            innerObj_Class_SandboxList.setSandboxName(class_loginresponse.getListVersion().get(i).getSandboxName());
                            innerObj_Class_SandboxList.setCountInstitute(class_loginresponse.getListVersion().get(i).getCountInstitute());
                            innerObj_Class_SandboxList.setCountVillage(class_loginresponse.getListVersion().get(i).getCountVillage());
                            innerObj_Class_SandboxList.setCountApplicant(class_loginresponse.getListVersion().get(i).getCountApplicant());
                            innerObj_Class_SandboxList.setCountAdmission(class_loginresponse.getListVersion().get(i).getCountAdmission());
                            innerObj_Class_SandboxList.setCountMale(class_loginresponse.getListVersion().get(i).getCountMale());
                            innerObj_Class_SandboxList.setCountFemale(class_loginresponse.getListVersion().get(i).getCountFemale());
                            innerObj_Class_SandboxList.setCountDropout(class_loginresponse.getListVersion().get(i).getCountDropout());
                            innerObj_Class_SandboxList.setCountStudent(class_loginresponse.getListVersion().get(i).getCountStudent());
                            innerObj_Class_SandboxList.setDashboardStatus(class_loginresponse.getListVersion().get(i).getDashboardStatus());
                            arrayObj_Class_monthcontents[i]=innerObj_Class_SandboxList;



                            //////////////////////////////////////////////////////
                            Class_Scorecards_CenterSelection innerObj_class_Scorecards_CenterSelection = new Class_Scorecards_CenterSelection();
                            innerObj_class_Scorecards_CenterSelection.setScorecards_SandboxID(class_loginresponse.getListVersion().get(i).getSandboxID());
                            innerObj_class_Scorecards_CenterSelection.setScorecards_SandboxName(class_loginresponse.getListVersion().get(i).getSandboxName());
                            innerObj_class_Scorecards_CenterSelection.setScorecards_villages(class_loginresponse.getListVersion().get(i).getCountVillage());
                            innerObj_class_Scorecards_CenterSelection.setScorecards_schools(class_loginresponse.getListVersion().get(i).getCountInstitute());
                            innerObj_class_Scorecards_CenterSelection.setScorecards_students(class_loginresponse.getListVersion().get(i).getCountStudent());
                            innerObj_class_Scorecards_CenterSelection.setScorecards_male(class_loginresponse.getListVersion().get(i).getCountMale());
                            innerObj_class_Scorecards_CenterSelection.setScorecards_female(class_loginresponse.getListVersion().get(i).getCountFemale());
                            innerObj_class_Scorecards_CenterSelection.setScorecards_dropouts(class_loginresponse.getListVersion().get(i).getCountDropout());

                            Class_Dashboard_SandBox class_dashboard_sandBox = new Class_Dashboard_SandBox();
                            class_dashboard_sandBox.setDashboard_sand_id(class_loginresponse.getListVersion().get(i).getSandboxID());
                            class_dashboard_sandBox.setDashboard_sand_name(class_loginresponse.getListVersion().get(i).getSandboxName());

                            str_no_of_villages = class_loginresponse.getListVersion().get(i).getCountVillage();
                            str_no_of_students = class_loginresponse.getListVersion().get(i).getCountStudent();
                            str_no_of_schools = class_loginresponse.getListVersion().get(i).getCountInstitute();
                            str_no_of_male = class_loginresponse.getListVersion().get(i).getCountMale();
                            str_no_of_female = class_loginresponse.getListVersion().get(i).getCountFemale();
                            str_no_of_dropouts = class_loginresponse.getListVersion().get(i).getCountDropout();
                            Log.e("str_no_of_villages",str_no_of_villages);

                            str_dashboard_sandid = class_loginresponse.getListVersion().get(i).getSandboxID();
                            str_dashboard_sandname = class_loginresponse.getListVersion().get(i).getSandboxName();
                            arrayObjclass_Scorecards_CenterSelection[i]=innerObj_class_Scorecards_CenterSelection;
                            arrayObjclass_dashboard_sandBoxes[i]=class_dashboard_sandBox;

                            ///////////////////////////////////////////////////////
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    // Stuff that updates the UI

                                    setvalues();
                                }
                            });

                            Log.e("str_dashboard_sandid",str_dashboard_sandid);
                            Log.e("str_dashboard_sandname",str_dashboard_sandname);

                            DBCreate_dash_SandBoxdetails_insert_2SQLiteDB(str_dashboard_sandid, str_dashboard_sandname);
                            DBCreate_SandBoxdetails_insert_2SQLiteDB(str_dashboard_sandid, str_dashboard_sandname,str_no_of_villages,str_no_of_schools,str_no_of_students,str_no_of_male,str_no_of_female,str_no_of_dropouts);
                        }//for loop end
                        uploadSandboxDropdownfromDB_list();
                        uploadSandboxfromDB_list();
//                        RecyclerView.Adapter adapter1 = new Emp_monthcontents_RecyclerViewAdapter(Array_ClassListVersion);
//
//                        recyclerview.setAdapter(adapter1);


                    } else {
                        progressDoalog.dismiss();
//                        str_getmonthsummary_errormsg = class_loginresponse.getMessage();
//                        alerts_dialog_getmonthsummaryError();

                        // Toast.makeText(getContext(), class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDoalog.dismiss();
                    Log.e("Entered resp else", "");
                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
//                    Log.e("error message", error.getMsg());
//                    str_getmonthsummary_errormsg = error.getMsg();
//                    alerts_dialog_getexlistviewError();

                    //  Toast.makeText(getContext(), error.getMsg(), Toast.LENGTH_SHORT).show();

                    if (error.getMsg() != null) {

                        Log.e("error message", error.getMsg());
//                        str_getmonthsummary_errormsg = error.getMsg();
//                        alerts_dialog_getexlistviewError();

                        //Toast.makeText(getActivity(), error.getMsg(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Activity_Dashboard.this,"Kindly restart your application", Toast.LENGTH_SHORT).show();

                    }

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


    public void setvalues() {

        if (!str_no_of_villages.equals("")) {
            villagecount_TV.setText(str_no_of_villages);
        }


        if (!str_no_of_schools.equals("")) {
            schoolcount_TV.setText(str_no_of_schools);

        }
        if (!str_no_of_students.equals("")) {
            studentcount_TV.setText(str_no_of_students);

        }
        if (!str_no_of_male.equals("")) {
            malecount_TV.setText(str_no_of_male);
        }

        if (!str_no_of_female.equals("")) {
            femalecount_TV.setText(str_no_of_female);
        }

        if (!str_no_of_dropouts.equals("")) {
            dropoutscount_TV.setText(str_no_of_dropouts);
        }


    }

    public void getbarchartdata() {

        if (isInternetPresent) {
            deleteCenterTable_B4insertion();
            deleteFeesSummaryTable_B4insertion();
//            GetBarChartInfoTask task = new GetBarChartInfoTask(Activity_Dashboard.this);
//            task.execute();
            getbarchartinfo_new();
        } else {
            feesummarheading_TV.setVisibility(View.GONE);
            loadcenters_SP.setVisibility(View.GONE);
            barChart.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }


    }

    private class GetBarChartInfoTask extends AsyncTask<String, Void, Void> {
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

            getbarchartinfo();  // call of details
            return null;
        }

        public GetBarChartInfoTask(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1, R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected void onPostExecute(Void result) {

            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();

            }


            if (str_dashboard_status_barchart.equals("No Result")) {

                Toast.makeText(getApplicationContext(), "No Result", Toast.LENGTH_SHORT).show();

            }
            if (str_dashboard_status_barchart.equals("Error")) {

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

            } else if (str_dashboard_status_barchart.equals("Active")) {


                uploadCentersfromDB_list();
                uploadFeesSummaryfromDB_list();
            }

            Log.e("tag", "Reached the onPostExecute");

        }//end of onPostExecute
    }// end Async task

    public void getbarchartinfo() {


        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "LoadUserPaymentStatus";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/LoadUserPaymentStatus";


        try {

            //  int userid = Integer.parseInt(str_loginuserID);
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

            request.addProperty("User_ID", str_loginuserID);//change static value later

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
                barchart_count = response.getPropertyCount();

                Log.e("responsecount", String.valueOf(response.getPropertyCount()));

                arrayObj_Class_BarchartData = new Class_PiechartData[response.getPropertyCount()];
                arrayObjclass_dashboard_institute = new Class_Dashboard_Institute[response.getPropertyCount()];
                arrayObjclass_dashboard_feessummary = new Class_Dashboard_FeesSummary[response.getPropertyCount()];

                for (int i = 0; i < response.getPropertyCount(); i++) {

                    SoapObject response_soapobj = (SoapObject) response.getProperty(i);

                    response_soapobj_institute_id = (SoapPrimitive) response_soapobj.getProperty("Institute_ID");
                    response_soapobj_institute_name = (SoapPrimitive) response_soapobj.getProperty("Institute_Name");
                    response_soapobj_student_count = (SoapPrimitive) response_soapobj.getProperty("Student_Count");
                    response_soapobj_receivable = (SoapPrimitive) response_soapobj.getProperty("Receivable");
                    response_soapobj_received = (SoapPrimitive) response_soapobj.getProperty("Received");
                    response_soapobj_balance = (SoapPrimitive) response_soapobj.getProperty("Balance");
                    str_dashboard_status_barchart = response_soapobj.getProperty("Payment_Status").toString();



                    Class_PiechartData innerObj_Class_barchart = new Class_PiechartData();
                    innerObj_Class_barchart.setInstitute_name(response_soapobj_institute_name.toString());
                    innerObj_Class_barchart.setStudent_count(response_soapobj_student_count.toString());
                    innerObj_Class_barchart.setReceivable(response_soapobj_receivable.toString());
                    innerObj_Class_barchart.setReceived(response_soapobj_received.toString());
                    innerObj_Class_barchart.setBalance(response_soapobj_balance.toString());


                    Class_Dashboard_Institute innerObj_Class_dashboard_inst = new Class_Dashboard_Institute();
                    innerObj_Class_dashboard_inst.setDashboard_inst_id(response_soapobj_institute_id.toString());
                    innerObj_Class_dashboard_inst.setDashboard_inst_name(response_soapobj_institute_name.toString());


                    Class_Dashboard_FeesSummary innerObj_class_Dashboard_FeesSummary = new Class_Dashboard_FeesSummary();
                    innerObj_class_Dashboard_FeesSummary.setInstitute_ID(response_soapobj_institute_id.toString());
                    innerObj_class_Dashboard_FeesSummary.setInstitute_Name(response_soapobj_institute_name.toString());
                    innerObj_class_Dashboard_FeesSummary.setReceivable(response_soapobj_receivable.toString());
                    innerObj_class_Dashboard_FeesSummary.setReceived(response_soapobj_received.toString());
                    innerObj_class_Dashboard_FeesSummary.setBalance(response_soapobj_balance.toString());


                    arrayObjclass_dashboard_institute[i] = innerObj_Class_dashboard_inst;
                    arrayObj_Class_BarchartData[i] = innerObj_Class_barchart;
                    arrayObjclass_dashboard_feessummary[i] = innerObj_class_Dashboard_FeesSummary;

                    Log.e("class", arrayObj_Class_BarchartData[i].getInstitute_name());
                    int int_val_no_of_students = Integer.parseInt(arrayObj_Class_BarchartData[i].getStudent_count());
                    Float students_float = Float.valueOf(int_val_no_of_students).floatValue();


                    int_val_receivable = Integer.parseInt(arrayObj_Class_BarchartData[i].getReceivable());
                    float_val_receivable = Float.valueOf(int_val_receivable).floatValue();


                    int_val_received = Integer.parseInt(arrayObj_Class_BarchartData[i].getReceived());
                    float_val_received = Float.valueOf(int_val_received).floatValue();

                    int_val_balance = Integer.parseInt(arrayObj_Class_BarchartData[i].getBalance());
                    float_val_balance = Float.valueOf(int_val_balance).floatValue();

                    str_dashboard_instid = response_soapobj.getProperty("Institute_ID").toString();
                    str_dashboard_instname = response_soapobj.getProperty("Institute_Name").toString();
                    str_dashboard_receivable = response_soapobj.getProperty("Receivable").toString();
                    str_dashboard_received = response_soapobj.getProperty("Received").toString();
                    str_dashboard_balance = response_soapobj.getProperty("Balance").toString();



                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            newbarchart();
                        }
                    });

                    DBCreate_Instdetails_insert_2SQLiteDB(str_dashboard_instid, str_dashboard_instname);
                    DBCreate_FeesSummaryDatadetails_insert_2SQLiteDB(str_dashboard_instid, str_dashboard_instname, str_dashboard_receivable, str_dashboard_received, str_dashboard_balance, selected_instituteID);
                }//end for loop



            } catch (Throwable t) {

                Log.e("getCollege fail", "> " + t.getMessage());
                //internet_issue = "slow internet";
            }
        } catch (Throwable t) {

            Log.e("UnRegister Error", "> " + t.getMessage());
        }

    }//End of uploaddetails


    public void getbarchartinfo_new() {

        Call<Class_getUserPaymentResponse> call = userService1.getuserpaymentstatus(str_loginuserID);
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_Dashboard.this);
        //  progressDoalog.setMax(100);
        //  progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<Class_getUserPaymentResponse>() {
            @Override
            public void onResponse(Call<Class_getUserPaymentResponse> call, Response<Class_getUserPaymentResponse> response) {
                Log.e("Entered resp", "getUserPaymentResponse");

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    Class_getUserPaymentResponse class_loginresponse = response.body();
                    if (class_loginresponse.getStatus()) {
                        List<Class_UserPaymentList> monthContents_list = response.body().getuserPaymentList();

                        Class_UserPaymentList []  arrayObj_Class_monthcontents = new Class_UserPaymentList[monthContents_list.size()];
                        arrayObj_Class_BarchartData = new Class_PiechartData[monthContents_list.size()];
                        arrayObjclass_dashboard_institute = new Class_Dashboard_Institute[monthContents_list.size()];
                        arrayObjclass_dashboard_feessummary = new Class_Dashboard_FeesSummary[monthContents_list.size()];

                        for (int i = 0; i < arrayObj_Class_monthcontents.length; i++) {
                            Log.e("getUserPayment", String.valueOf(class_loginresponse.getStatus()));
                            Log.e("getUserPayment", class_loginresponse.getMessage());

                            Class_PiechartData innerObj_Class_barchart = new Class_PiechartData();
                            innerObj_Class_barchart.setInstitute_name(class_loginresponse.getuserPaymentList().get(i).getInstituteName());
                            innerObj_Class_barchart.setStudent_count(class_loginresponse.getuserPaymentList().get(i).getStudentCount());
                            innerObj_Class_barchart.setReceivable(class_loginresponse.getuserPaymentList().get(i).getReceivable());
                            innerObj_Class_barchart.setReceived(class_loginresponse.getuserPaymentList().get(i).getReceived());
                            innerObj_Class_barchart.setBalance(class_loginresponse.getuserPaymentList().get(i).getBalance());


                            Class_Dashboard_Institute innerObj_Class_dashboard_inst = new Class_Dashboard_Institute();
                            innerObj_Class_dashboard_inst.setDashboard_inst_id(String.valueOf(class_loginresponse.getuserPaymentList().get(i).getInstituteID()));
                            innerObj_Class_dashboard_inst.setDashboard_inst_name(class_loginresponse.getuserPaymentList().get(i).getInstituteName());


                            Class_Dashboard_FeesSummary innerObj_class_Dashboard_FeesSummary = new Class_Dashboard_FeesSummary();
                            innerObj_class_Dashboard_FeesSummary.setInstitute_ID(String.valueOf(class_loginresponse.getuserPaymentList().get(i).getInstituteID()));
                            innerObj_class_Dashboard_FeesSummary.setInstitute_Name(class_loginresponse.getuserPaymentList().get(i).getInstituteName());
                            innerObj_class_Dashboard_FeesSummary.setReceivable(class_loginresponse.getuserPaymentList().get(i).getReceivable());
                            innerObj_class_Dashboard_FeesSummary.setReceived(class_loginresponse.getuserPaymentList().get(i).getReceived());
                            innerObj_class_Dashboard_FeesSummary.setBalance(class_loginresponse.getuserPaymentList().get(i).getBalance());


                            arrayObjclass_dashboard_institute[i] = innerObj_Class_dashboard_inst;
                            arrayObj_Class_BarchartData[i] = innerObj_Class_barchart;
                            arrayObjclass_dashboard_feessummary[i] = innerObj_class_Dashboard_FeesSummary;

                            Log.e("class", arrayObj_Class_BarchartData[i].getInstitute_name());
                            int int_val_no_of_students = Integer.parseInt(arrayObj_Class_BarchartData[i].getStudent_count());
                            Float students_float = Float.valueOf(int_val_no_of_students).floatValue();


                            int_val_receivable = Integer.parseInt(arrayObj_Class_BarchartData[i].getReceivable());
                            float_val_receivable = Float.valueOf(int_val_receivable).floatValue();


                            int_val_received = Integer.parseInt(arrayObj_Class_BarchartData[i].getReceived());
                            float_val_received = Float.valueOf(int_val_received).floatValue();

                            int_val_balance = Integer.parseInt(arrayObj_Class_BarchartData[i].getBalance());
                            float_val_balance = Float.valueOf(int_val_balance).floatValue();

                            str_dashboard_instid = String.valueOf(class_loginresponse.getuserPaymentList().get(i).getInstituteID());
                            str_dashboard_instname = class_loginresponse.getuserPaymentList().get(i).getInstituteName();
                            str_dashboard_receivable = class_loginresponse.getuserPaymentList().get(i).getReceivable();
                            str_dashboard_received = class_loginresponse.getuserPaymentList().get(i).getReceived();
                            str_dashboard_balance = class_loginresponse.getuserPaymentList().get(i).getBalance();



                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    newbarchart();
                                }
                            });

                            DBCreate_Instdetails_insert_2SQLiteDB(str_dashboard_instid, str_dashboard_instname);
                            DBCreate_FeesSummaryDatadetails_insert_2SQLiteDB(str_dashboard_instid, str_dashboard_instname, str_dashboard_receivable, str_dashboard_received, str_dashboard_balance, selected_instituteID);



                        }//for loop end

                        uploadCentersfromDB_list();
                        uploadFeesSummaryfromDB_list();


                    } else {
                        progressDoalog.dismiss();
//                        str_getmonthsummary_errormsg = class_loginresponse.getMessage();
//                        alerts_dialog_getmonthsummaryError();

                        // Toast.makeText(getContext(), class_loginresponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDoalog.dismiss();
                    Log.e("Entered resp else", "");
                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
//                    Log.e("error message", error.getMsg());
//                    str_getmonthsummary_errormsg = error.getMsg();
//                    alerts_dialog_getexlistviewError();

                    //  Toast.makeText(getContext(), error.getMsg(), Toast.LENGTH_SHORT).show();

                    if (error.getMsg() != null) {

                        Log.e("error message", error.getMsg());
//                        str_getmonthsummary_errormsg = error.getMsg();
//                        alerts_dialog_getexlistviewError();

                        //Toast.makeText(getActivity(), error.getMsg(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Activity_Dashboard.this,"Kindly restart your application", Toast.LENGTH_SHORT).show();

                    }

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


    public void DBCreate_FeesSummaryDatadetails_insert_2SQLiteDB(String str_instID, String str_instname, String str_receivable, String str_received, String str_balance, String str_selected_instid) {
        SQLiteDatabase db_feessummary = this.openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db_feessummary.execSQL("CREATE TABLE IF NOT EXISTS FeesSummaryList(Inst_Feessummary_ID VARCHAR,Inst_Feessummary_Name VARCHAR,Inst_Feessummary_Receivable VARCHAR,Inst_Feessummary_Received VARCHAR,Inst_Feessummary_Balance VARCHAR,Inst_Feessummary_selectedInstID VARCHAR);");


        String SQLiteQuery = "INSERT INTO FeesSummaryList (Inst_Feessummary_ID, Inst_Feessummary_Name,Inst_Feessummary_Receivable,Inst_Feessummary_Received,Inst_Feessummary_Balance,Inst_Feessummary_selectedInstID)" +
                " VALUES ('" + str_instID + "','" + str_instname + "','" + str_receivable + "','" + str_received + "','" + str_balance + "','" + str_selected_instid + "');";
        db_feessummary.execSQL(SQLiteQuery);
        Log.e("str_instID DB", str_instID);
        Log.e("str_instname DB", str_instname);
        db_feessummary.close();
    }
    public void DBCreate_Instdetails_insert_2SQLiteDB(String str_instID, String str_instname) {
        SQLiteDatabase db_feessummary = this.openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db_feessummary.execSQL("CREATE TABLE IF NOT EXISTS CenterList(Inst_DashboardID VARCHAR,Inst_DashboardName VARCHAR);");


        String SQLiteQuery = "INSERT INTO CenterList (Inst_DashboardID, Inst_DashboardName)" +
                " VALUES ('" + str_instID + "','" + str_instname + "');";
        db_feessummary.execSQL(SQLiteQuery);
        Log.e("str_instID DB", str_instID);
        Log.e("str_instname DB", str_instname);
        db_feessummary.close();
    }
    public void DBCreate_SandBoxdetails_insert_2SQLiteDB(String str_sandID, String str_sandname,String str_village,String str_school,String str_students,String str_male,String str_no_of_female,String str_no_of_dropouts) {
        SQLiteDatabase db_sandbox = this.openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db_sandbox.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandBox_DashboardID VARCHAR,SandBox_DashboardName VARCHAR,SandBox_DashboardVillages VARCHAR,SandBox_DashboardSchools VARCHAR,SandBox_DashboardStudents VARCHAR,SandBox_DashboardMale VARCHAR,SandBox_DashboardFemale VARCHAR,SandBox_DashboardDropouts VARCHAR);");


        String SQLiteQuery = "INSERT INTO SandBoxList(SandBox_DashboardID,SandBox_DashboardName,SandBox_DashboardVillages,SandBox_DashboardSchools,SandBox_DashboardStudents,SandBox_DashboardMale,SandBox_DashboardFemale,SandBox_DashboardDropouts)" +
                " VALUES ('" + str_sandID + "','" + str_sandname +  "','" + str_village + "','" + str_school + "','" + str_students + "','" + str_male + "','" + str_no_of_female + "','" + str_no_of_dropouts +"');";
        db_sandbox.execSQL(SQLiteQuery);
        Log.e("str_sandID DB", str_sandID);
        Log.e("str_sandname DB", str_sandname);
        db_sandbox.close();
    }
    public void DBCreate_dash_SandBoxdetails_insert_2SQLiteDB(String str_sandboxID, String str_sandboxname) {
        SQLiteDatabase db_sandbox = this.openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db_sandbox.execSQL("CREATE TABLE IF NOT EXISTS DropSandBoxList(SandBox_ID VARCHAR,SandBox_Name VARCHAR);");
        String SQLiteQuery = "INSERT INTO DropSandBoxList(SandBox_ID,SandBox_Name)" +
                " VALUES ('" + str_sandboxID + "','" + str_sandboxname +"');";
        db_sandbox.execSQL(SQLiteQuery);
        Log.e("inset DB", str_sandboxID);
        Log.e("insert DB", str_sandboxname);
        db_sandbox.close();
    }


    public void uploadSandboxDropdownfromDB_list() {

        SQLiteDatabase db_centers = this.openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db_centers.execSQL("CREATE TABLE IF NOT EXISTS DropSandBoxList(SandBox_ID VARCHAR,SandBox_Name VARCHAR);");
        Cursor cursor1 = db_centers.rawQuery("SELECT DISTINCT * FROM DropSandBoxList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObjclass_dashboard_sandBoxes2 = new Class_Dashboard_SandBox[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_Dashboard_SandBox innerObj_Class_SandboxList = new Class_Dashboard_SandBox();
                innerObj_Class_SandboxList.setDashboard_sand_id(cursor1.getString(cursor1.getColumnIndex("SandBox_ID")));
                innerObj_Class_SandboxList.setDashboard_sand_name(cursor1.getString(cursor1.getColumnIndex("SandBox_Name")));

                arrayObjclass_dashboard_sandBoxes2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_centers.close();
        if (x > 0) {

            ArrayAdapter<Class_Dashboard_SandBox> dataAdapter = new ArrayAdapter<Class_Dashboard_SandBox>(getApplicationContext(), R.layout.spinnercenterstyle, arrayObjclass_dashboard_sandBoxes2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            loadSandbox_SP.setAdapter(dataAdapter);
        }

    }
    public void uploadCentersfromDB_list() {

        SQLiteDatabase db_centers = this.openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db_centers.execSQL("CREATE TABLE IF NOT EXISTS CenterList(Inst_DashboardID VARCHAR,Inst_DashboardName VARCHAR);");
        Cursor cursor1 = db_centers.rawQuery("SELECT DISTINCT * FROM CenterList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObjclass_dashboard_institute2 = new Class_Dashboard_Institute[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_Dashboard_Institute innerObj_Class_SandboxList = new Class_Dashboard_Institute();
                innerObj_Class_SandboxList.setDashboard_inst_id(cursor1.getString(cursor1.getColumnIndex("Inst_DashboardID")));
                innerObj_Class_SandboxList.setDashboard_inst_name(cursor1.getString(cursor1.getColumnIndex("Inst_DashboardName")));
                arrayObjclass_dashboard_institute2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_centers.close();
        if (x > 0) {

            ArrayAdapter<Class_Dashboard_Institute> dataAdapter = new ArrayAdapter<Class_Dashboard_Institute>(getApplicationContext(), R.layout.spinnercenterstyle, arrayObjclass_dashboard_institute2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            loadcenters_SP.setAdapter(dataAdapter);
        }

    }
    public void uploadSandboxfromDB_list() {

        SQLiteDatabase db_centers = this.openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db_centers.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandBox_DashboardID VARCHAR,SandBox_DashboardName VARCHAR,SandBox_DashboardVillages VARCHAR,SandBox_DashboardSchools VARCHAR,SandBox_DashboardStudents VARCHAR,SandBox_DashboardMale VARCHAR,SandBox_DashboardFemale VARCHAR,SandBox_DashboardDropouts VARCHAR);");
        Cursor cursor1 = db_centers.rawQuery("SELECT DISTINCT * FROM SandBoxList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObjclass_Scorecards_CenterSelection2 = new Class_Scorecards_CenterSelection[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_Scorecards_CenterSelection innerObj_Class_SandboxList = new Class_Scorecards_CenterSelection();
                innerObj_Class_SandboxList.setScorecards_SandboxID(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardID")));
                innerObj_Class_SandboxList.setScorecards_SandboxName(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardName")));
                innerObj_Class_SandboxList.setScorecards_villages(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardVillages")));
                innerObj_Class_SandboxList.setScorecards_schools(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardSchools")));
                innerObj_Class_SandboxList.setScorecards_students(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardStudents")));
                innerObj_Class_SandboxList.setScorecards_male(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardMale")));
                innerObj_Class_SandboxList.setScorecards_female(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardFemale")));
                innerObj_Class_SandboxList.setScorecards_dropouts(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardDropouts")));

                arrayObjclass_Scorecards_CenterSelection2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_centers.close();
        if (x > 0) {

//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObjclass_Scorecards_CenterSelection2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            loadSandbox_SP.setAdapter(dataAdapter);
        }

    }
    public void uploadFeesSummaryfromDB_list() {

        SQLiteDatabase db_feessummary = this.openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db_feessummary.execSQL("CREATE TABLE IF NOT EXISTS FeesSummaryList(Inst_Feessummary_ID VARCHAR,Inst_Feessummary_Name VARCHAR,Inst_Feessummary_Receivable VARCHAR,Inst_Feessummary_Received VARCHAR,Inst_Feessummary_Balance VARCHAR,Inst_Feessummary_selectedInstID VARCHAR);");
        Cursor cursor1 = db_feessummary.rawQuery("SELECT DISTINCT * FROM FeesSummaryList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObjclass_dashboard_feessummary2 = new Class_Dashboard_FeesSummary[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_Dashboard_FeesSummary innerObj_Class_FeesSummaryList = new Class_Dashboard_FeesSummary();
                innerObj_Class_FeesSummaryList.setReceived(cursor1.getString(cursor1.getColumnIndex("Inst_Feessummary_Received")));
                innerObj_Class_FeesSummaryList.setReceivable(cursor1.getString(cursor1.getColumnIndex("Inst_Feessummary_Receivable")));
                innerObj_Class_FeesSummaryList.setBalance(cursor1.getString(cursor1.getColumnIndex("Inst_Feessummary_Balance")));
                innerObj_Class_FeesSummaryList.setInstitute_ID(cursor1.getString(cursor1.getColumnIndex("Inst_Feessummary_ID")));
                innerObj_Class_FeesSummaryList.setInstitute_Name(cursor1.getString(cursor1.getColumnIndex("Inst_Feessummary_Name")));


                arrayObjclass_dashboard_feessummary2[i] = innerObj_Class_FeesSummaryList;
                int_val_receivable = Integer.parseInt(arrayObjclass_dashboard_feessummary2[i].getReceivable());
                float_val_receivable = Float.valueOf(int_val_receivable).floatValue();
                int_val_received = Integer.parseInt(arrayObjclass_dashboard_feessummary2[i].getReceived());
                float_val_received = Float.valueOf(int_val_received).floatValue();
                int_val_balance = Integer.parseInt(arrayObjclass_dashboard_feessummary2[i].getBalance());
                float_val_balance = Float.valueOf(int_val_balance).floatValue();


                newbarchart();

                i++;

            } while (cursor1.moveToNext());

        }//if ends

        db_feessummary.close();
        if (x > 0) {

        }

    }
    public void Update_id_feessummarytable(String str_FeeSummaryid) {

        SQLiteDatabase db_feessummary_idupdate = this.openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db_feessummary_idupdate.execSQL("CREATE TABLE IF NOT EXISTS FeesSummaryList(Inst_Feessummary_ID VARCHAR,Inst_Feessummary_Name VARCHAR,Inst_Feessummary_Receivable VARCHAR,Inst_Feessummary_Received VARCHAR,Inst_Feessummary_Balance VARCHAR,Inst_Feessummary_selectedInstID VARCHAR);");
        Cursor cursor1 = db_feessummary_idupdate.rawQuery("SELECT DISTINCT * FROM FeesSummaryList WHERE Inst_Feessummary_ID='" + str_FeeSummaryid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObjclass_dashboard_feessummary2 = new Class_Dashboard_FeesSummary[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_Dashboard_FeesSummary innerObj_Class_FeesSummaryList = new Class_Dashboard_FeesSummary();
                innerObj_Class_FeesSummaryList.setReceived(cursor1.getString(cursor1.getColumnIndex("Inst_Feessummary_Received")));
                innerObj_Class_FeesSummaryList.setReceivable(cursor1.getString(cursor1.getColumnIndex("Inst_Feessummary_Receivable")));
                innerObj_Class_FeesSummaryList.setBalance(cursor1.getString(cursor1.getColumnIndex("Inst_Feessummary_Balance")));
                innerObj_Class_FeesSummaryList.setInstitute_ID(cursor1.getString(cursor1.getColumnIndex("Inst_Feessummary_ID")));
                innerObj_Class_FeesSummaryList.setInstitute_Name(cursor1.getString(cursor1.getColumnIndex("Inst_Feessummary_Name")));


                arrayObjclass_dashboard_feessummary2[i] = innerObj_Class_FeesSummaryList;
                int_val_receivable = Integer.parseInt(arrayObjclass_dashboard_feessummary2[i].getReceivable());
                float_val_receivable = Float.valueOf(int_val_receivable).floatValue();
                int_val_received = Integer.parseInt(arrayObjclass_dashboard_feessummary2[i].getReceived());
                float_val_received = Float.valueOf(int_val_received).floatValue();
                int_val_balance = Integer.parseInt(arrayObjclass_dashboard_feessummary2[i].getBalance());
                float_val_balance = Float.valueOf(int_val_balance).floatValue();
                newbarchart();
                i++;

            } while (cursor1.moveToNext());
        }//if ends


        db_feessummary_idupdate.close();
        if (x > 0) {
        }

    }
    public void Update_id_sandbox(String str_sandid) {

        SQLiteDatabase db_feessummary_idupdate = this.openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db_feessummary_idupdate.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandBox_DashboardID VARCHAR,SandBox_DashboardName VARCHAR,SandBox_DashboardVillages VARCHAR,SandBox_DashboardSchools VARCHAR,SandBox_DashboardStudents VARCHAR,SandBox_DashboardMale VARCHAR,SandBox_DashboardFemale VARCHAR,SandBox_DashboardDropouts VARCHAR);");
        Cursor cursor1 = db_feessummary_idupdate.rawQuery("SELECT DISTINCT * FROM SandBoxList WHERE SandBox_DashboardID='" + str_sandid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObjclass_Scorecards_CenterSelection2 = new Class_Scorecards_CenterSelection[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_Scorecards_CenterSelection innerObj_Class_SandboxList = new Class_Scorecards_CenterSelection();
                innerObj_Class_SandboxList.setScorecards_SandboxID(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardID")));
                innerObj_Class_SandboxList.setScorecards_SandboxName(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardName")));
                innerObj_Class_SandboxList.setScorecards_villages(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardVillages")));
                innerObj_Class_SandboxList.setScorecards_schools(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardSchools")));
                innerObj_Class_SandboxList.setScorecards_students(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardStudents")));
                innerObj_Class_SandboxList.setScorecards_male(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardMale")));
                innerObj_Class_SandboxList.setScorecards_female(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardFemale")));
                innerObj_Class_SandboxList.setScorecards_dropouts(cursor1.getString(cursor1.getColumnIndex("SandBox_DashboardDropouts")));

                arrayObjclass_Scorecards_CenterSelection2[i] = innerObj_Class_SandboxList;
                str_no_of_villages=arrayObjclass_Scorecards_CenterSelection2[i].getScorecards_villages();
                str_no_of_schools=arrayObjclass_Scorecards_CenterSelection2[i].getScorecards_schools();
                str_no_of_students=arrayObjclass_Scorecards_CenterSelection2[i].getScorecards_students();
                str_no_of_male=arrayObjclass_Scorecards_CenterSelection2[i].getScorecards_male();
                str_no_of_female=arrayObjclass_Scorecards_CenterSelection2[i].getScorecards_female();
                str_no_of_dropouts=arrayObjclass_Scorecards_CenterSelection2[i].getScorecards_dropouts();

                setvalues();
                i++;

            } while (cursor1.moveToNext());
        }//if ends


        db_feessummary_idupdate.close();
        if (x > 0) {
        }

    }


    public void deleteCenterTable_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS CenterList(Inst_DashboardID VARCHAR,Inst_DashboardName VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM CenterList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db1.delete("CenterList", null, null);

        }
        db1.close();
    }
    public void deleteSandBoxTable_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandBox_DashboardID VARCHAR,SandBox_DashboardName VARCHAR,SandBox_DashboardVillages VARCHAR,SandBox_DashboardSchools VARCHAR,SandBox_DashboardStudents VARCHAR,SandBox_DashboardMale VARCHAR,SandBox_DashboardFemale VARCHAR,SandBox_DashboardDropouts VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM SandBoxList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db1.delete("SandBoxList", null, null);

        }
        db1.close();
    }
    public void deleteSandBoxDrodownTable_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS DropSandBoxList(SandBox_ID VARCHAR,SandBox_Name VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM DropSandBoxList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db1.delete("DropSandBoxList", null, null);

        }
        db1.close();
    }
    public void deleteFeesSummaryTable_B4insertion() {

        SQLiteDatabase db1 = openOrCreateDatabase("dashboard_db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS FeesSummaryList(Inst_Feessummary_ID VARCHAR,Inst_Feessummary_Name VARCHAR,Inst_Feessummary_Receivable VARCHAR,Inst_Feessummary_Received VARCHAR,Inst_Feessummary_Balance VARCHAR,Inst_Feessummary_selectedInstID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM FeesSummaryList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db1.delete("FeesSummaryList", null, null);

        }
        db1.close();
    }








    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent i = new Intent(Activity_Dashboard.this, Activity_HomeScreen.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu items
        getMenuInflater().inflate(R.menu.menu_register, menu);
        menu.findItem(R.id.addnewstudent_menu_id)
                .setVisible(false);
        menu.findItem(R.id.save)
                .setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {


            case android.R.id.home:
                Intent i = new Intent(Activity_Dashboard.this, Activity_HomeScreen.class);
                startActivity(i);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public void newbarchart() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        if (float_val_receivable != null) {
            if (float_val_receivable != 0) {
                entries.add(new BarEntry(float_val_receivable, 0));
            }
        }
        if (float_val_received != null) {
            if (float_val_received != 0) {
                entries.add(new BarEntry(float_val_received, 1));
            }
        }
        if (float_val_balance != null) {
            if (float_val_balance != 0) {
                entries.add(new BarEntry(float_val_balance, 2));
            }
        }

        ArrayList<String> labels = new ArrayList<String>();
        if (float_val_receivable != null) {
            if (float_val_receivable != 0) {
                labels.add("Receivable");
            }
        }
        if (float_val_received != null) {
            if (float_val_received != 0) {
                labels.add("Received");
            }
        }
        if (float_val_balance != null) {
            if (float_val_balance != 0) {
                labels.add("Balance");
            }
        }
        BarDataSet bardataset = new BarDataSet(entries, null);


        BarData data1 = new BarData(labels, bardataset);
        barChart.setData(data1); // set the data and list of lables into chart


        //   barChart.setDescription("");  // set the description
        final int[] MY_COLORS = {Color.rgb(45, 170, 165), Color.rgb(243, 200, 61), Color.rgb(198, 53, 53),
                Color.rgb(144, 193, 51), Color.rgb(146, 208, 80), Color.rgb(0, 176, 80), Color.rgb(79, 129, 189)};
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : MY_COLORS) colors.add(c);

        bardataset.setColors(colors);


        Legend legend = barChart.getLegend();
        legend.setEnabled(false);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        legend.setForm(Legend.LegendForm.SQUARE);


        barChart.setDescription(null);
        XAxis xLabels = barChart.getXAxis();
        //  xLabels.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xLabels.setTextColor(Color.BLACK);
        YAxis yLabels = barChart.getAxisLeft();
        yLabels.setTextColor(Color.BLACK);
        xLabels.setAxisMinValue((float) 0.0);


        YAxis yLabels1 = barChart.getAxisRight();
        yLabels1.setTextColor(Color.BLACK);
        // xLabels.setLabelRotationAngle(-30f);

        barChart.setDescriptionColor(Color.WHITE);
        barChart.animateY(3000);

    }

    /////////////////webservice/////////////////////////////////////

}
