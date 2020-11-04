package com.det.skillinvillage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.net.URL;
import java.util.Vector;

//import com.android.sripad.leadnew_22_6_2018.R;

public class DocViewActivity extends AppCompatActivity {//implements ConnectivityReceiver.ConnectivityReceiverListener{

    private Context context;

    String str_loginuserID="",str_logout_count_Status;
    private int count1;
    private int count2;
    String serverPath="http://mis.detedu.org:8089/";
    private String wordDocPath="null";
    private String wordDocumentName;
    private String docName;
    private URL downloadUrl;
    File outputFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docview);

        SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        str_loginuserID = myprefs.getString("login_userid", "nothing");


       /* View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Masters/Ambssadors");*/

      /*  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/


        View view1;
        LayoutInflater inflater1 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view1 = inflater1.inflate(R.layout.docview_download_tablayout, null);

        View view2;
        LayoutInflater inflater2 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view2 = inflater2.inflate(R.layout.docview_localview_tablayout, null);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));

        TabLayout.Tab selectedTab = tabLayout.getTabAt(0);
        View view = selectedTab.getCustomView();
        TextView selectedText = view.findViewById(R.id.txt_downloadtabtitle);
        selectedText.setTextColor(Color.parseColor("#000000"));


        //tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final DocviewAdapter adapter = new DocviewAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = view.findViewById(R.id.txt_downloadtabtitle);
                selectedText.setTextColor(Color.parseColor("#000000"));
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = view.findViewById(R.id.txt_downloadtabtitle);
                selectedText.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = view.findViewById(R.id.txt_downloadtabtitle);
                selectedText.setTextColor(Color.parseColor("#000000"));
            }
        });

        /*img_eventsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        //MyApplication.getInstance().setConnectivityListener(this);
    }
/*
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }*/

    private void showSnack(boolean isConnected) {

        if (!isConnected) {

            AlertDialog.Builder adb = new AlertDialog.Builder(DocViewActivity.this);
            //adb.setView(alertDialogView);

            adb.setTitle("Sorry! Not connected to the internet");

            adb.setIcon(android.R.drawable.ic_dialog_alert);

            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            });


            adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            });

            adb.setCancelable(true);
            adb.show();
        }

/*        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinator_layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
       /* MenuItem action_editProfile = menu.findItem(R.id.action_editProfile);
        action_editProfile.setVisible(false);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


    /*    if (id == R.id.action_editProfile) {
            Intent ittEventsToEditProfile = new Intent(EventsActivity.this ,EditProfileActivity.class);
            startActivity(ittEventsToEditProfile);
            return true;
        }*/

        if (id == R.id.action_logout) {
            LogoutCountAsynctask logoutCountAsynctask=new LogoutCountAsynctask(DocViewActivity.this);
            logoutCountAsynctask.execute();

            Intent ittEventsToLogin = new Intent(DocViewActivity.this ,MainActivity.class);
            startActivity(ittEventsToLogin);
            return true;
        }

        if (id == android.R.id.home) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
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

        public LogoutCountAsynctask(DocViewActivity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {


            dialog.dismiss();
            if(!str_logout_count_Status.equals("")){
                if (str_logout_count_Status.equals("Success")) {
                  //  Toast.makeText(DocViewActivity.this, "Success", Toast.LENGTH_SHORT).show();

                } else if (str_logout_count_Status.equals("Failure")){
                   // Toast.makeText(DocViewActivity.this, "Failure", Toast.LENGTH_SHORT).show();
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
