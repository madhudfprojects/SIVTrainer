package com.det.skillinvillage;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;

public class Activity_MarkerGoogleMaps extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MainActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;
    SupportMapFragment mapFragment;

    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    String str_loginuserID="",str_latlong_status="",str_latitude="",str_longitude="";
    SharedPreferences sharedpref_loginuserid_Obj;
    Class_GoogleLocations[] arrayObj_class_GoogleLocations;
    int latlongcount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__marker_google_maps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Locations of SIV Centers");

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();
        sharedpref_loginuserid_Obj = getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();


        if(isServicesOK()){
            getVillagelat_long();
        }

    }


    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Activity_MarkerGoogleMaps.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Activity_MarkerGoogleMaps.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }





    /////////////////webservice/////////////////////////////////////

    public void getVillagelat_long() {

        if (isInternetPresent) {
            GetVillageLocationTask task = new GetVillageLocationTask(Activity_MarkerGoogleMaps.this);
            task.execute();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }


    }

    class GetVillageLocationTask extends AsyncTask<String, Void, Void> implements OnMapReadyCallback {
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

            getvillagelocationinfo();

            // call of details
            return null;
        }

        public GetVillageLocationTask(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1, R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected void onPostExecute(Void result) {

            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();

            }


            if (str_latlong_status.equals("success")) {

                mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                        int i=0;

                        Log.e("latlongcount", String.valueOf(latlongcount));
                        for(i=0;i<latlongcount;i++){
                            Log.e("lat abc", arrayObj_class_GoogleLocations[i].getLatitude());

                            Double lat=Double.parseDouble(arrayObj_class_GoogleLocations[i].getLatitude());
                            Double longi=Double.parseDouble(arrayObj_class_GoogleLocations[i].getLongitude());
                            Log.e("lat oncreate", String.valueOf(lat));
                            Log.e("longi oncreate", String.valueOf(longi));

                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(lat, longi))
                                    .title(arrayObj_class_GoogleLocations[i].getVillagename())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));


                        }
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(15.539836, 75.056725), 4));

                    }
                });

            } else {

            }
            Log.e("tag", "Reached the onPostExecute");

        }//end of onPostExecute

        @Override
        public void onMapReady(GoogleMap googleMap) {
            int i=0;


            for(i=0;i<latlongcount;i++){
                Double lat=Double.parseDouble(arrayObj_class_GoogleLocations[i].getLatitude());
                Double longi=Double.parseDouble(arrayObj_class_GoogleLocations[i].getLongitude());
                Log.e("lat", String.valueOf(lat));
                Log.e("longi", String.valueOf(longi));

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, longi))
                        .title(arrayObj_class_GoogleLocations[i].getVillagename()));


            }
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(15.539836, 75.056725), 4));

        }
    }// end Async task

    private void getvillagelocationinfo() {
        Vector<SoapObject> result1 = null;

        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "GetVillageLocations";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/GetVillageLocations";

        try {
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
           // request.addProperty("User_ID", str_loginuserID);
          //  Log.i("value at request", request.toString());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                //SoapObject obj2 = (SoapObject) response.getProperty(0);
                Log.i("value at response", response.toString());
                latlongcount=response.getPropertyCount();
              //  arrayObj_class_GoogleLocations=null;
                arrayObj_class_GoogleLocations = new Class_GoogleLocations[response.getPropertyCount()];

                for (int i = 0; i < response.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) response.getProperty(i);
                    SoapPrimitive soap_latitude,soap_longitude,soap_villagename;
                    str_latlong_status = list.getProperty("status").toString();
                    if (str_latlong_status.equals("Error")) {
                        Log.e("str_latlong_status", str_latlong_status);

                    } else {

                        soap_latitude = (SoapPrimitive) list.getProperty("Lattitude");
                        soap_longitude = (SoapPrimitive) list.getProperty("Logitude");
                        soap_villagename = (SoapPrimitive) list.getProperty("village_name");


                        Class_GoogleLocations innerObj_Class_academic = new Class_GoogleLocations();
                        innerObj_Class_academic.setLatitude(soap_latitude.toString());
                        innerObj_Class_academic.setLongitude(soap_longitude.toString());
                        innerObj_Class_academic.setVillagename(soap_villagename.toString());

                        arrayObj_class_GoogleLocations[i] = innerObj_Class_academic;

                        str_latitude = list.getProperty("Lattitude").toString();
                        str_longitude= list.getProperty("Logitude").toString();

                        Log.e("soap_latitude", String.valueOf(soap_latitude));

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // Stuff that updates the UI

                              //  setvalues();
                            }
                        });


                    }


                }// End of for loop

            } catch (Throwable t) {

                Log.e("request fail", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("UnRegister  Error", "> " + t.getMessage());

        }


    }//end of getlist()



    ////////////////webservice////////////////////////////////////////


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Activity_MarkerGoogleMaps.this, Activity_Dashboard.class);
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
                Intent i = new Intent(Activity_MarkerGoogleMaps.this, Activity_Dashboard.class);
                startActivity(i);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
