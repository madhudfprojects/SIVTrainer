package com.det.skillinvillage;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.det.skillinvillage.model.Class_getdemo_Response;
import com.det.skillinvillage.model.Class_getdemo_resplist;
import com.det.skillinvillage.model.Class_gethelp_Response;
import com.det.skillinvillage.model.Class_gethelp_resplist;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;
import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_UserManual_DownloadPDF extends AppCompatActivity {

    LinearLayout viewlayout_LL,downloadlayout_LL;
    TextView usermanual_TV,emailid_TV,phone_TV,usermanual_view_TV;
    ImageView email_icon_IV,call_icon_IV,pdficon_IV,usermanual_download_iv;
    String str_mobileno_usermanual="",str_email_usermanual="",str_Status_usermanual="",str_Status_docpath="";
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    SharedPreferences sharedpref_usermanualpdf_Obj;
    public static final String sharedpreferenc_usermanual = "pdf_usermanual";
    public static final String key_usermanualpdfurl = "pdfurl";

    public static String docName;
    public static String docName1;
    public static String docName2;
    public static URL downloadUrl;
    static File outputFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__user_manual__download_pdf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Help");
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        sharedpref_usermanualpdf_Obj=getSharedPreferences(sharedpreferenc_usermanual, Context.MODE_PRIVATE);
        str_Status_docpath = sharedpref_usermanualpdf_Obj.getString(key_usermanualpdfurl, "").trim();

        usermanual_TV= findViewById(R.id.usermanual_TV);
        phone_TV= findViewById(R.id.phone_tv);
        emailid_TV= findViewById(R.id.emailid_TV);
        email_icon_IV= findViewById(R.id.email_icon_IV);
        call_icon_IV= findViewById(R.id.call_icon_IV);
        pdficon_IV= findViewById(R.id.pdficon_IV);
        usermanual_view_TV= findViewById(R.id.usermanual_view_TV);
        usermanual_download_iv= findViewById(R.id.usermanual_download_iv);

        viewlayout_LL= findViewById(R.id.viewlayout_LL);
        downloadlayout_LL= findViewById(R.id.downloadlayout_LL);


        if(Class_SaveSharedPreference.getPrefFlagUsermanual(Activity_UserManual_DownloadPDF.this).equals("1")){
            viewlayout_LL.setVisibility(View.VISIBLE);
            downloadlayout_LL.setVisibility(View.GONE);
        }else{
            viewlayout_LL.setVisibility(View.GONE);
            downloadlayout_LL.setVisibility(View.VISIBLE);

        }

        usermanual_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isInternetPresent) {
                    Intent i=new Intent(Activity_UserManual_DownloadPDF.this,Activity_UserManual_OpenPDF.class);
                    startActivity(i);

                }else{
                    Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
                }

            }
        });


        usermanual_view_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_UserManual_DownloadPDF.this, Activity_ViewUserManualPDF_Downloaded_pdf.class);
                startActivity(intent);


            }
        });

        usermanual_download_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LoadUserManualDocument loadDocument = new LoadUserManualDocument(Activity_UserManual_DownloadPDF.this);
                loadDocument.execute(str_Status_docpath,"User Manual");

                Class_SaveSharedPreference.setPrefFlagUsermanual(Activity_UserManual_DownloadPDF.this,"1");
                viewlayout_LL.setVisibility(View.VISIBLE);
                downloadlayout_LL.setVisibility(View.GONE);
            }
        });

        call_icon_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(!str_mobileno_usermanual.equals("")) {
                if(!phone_TV.getText().toString().equals("")) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" +phone_TV.getText().toString()));
                    startActivity(intent);
               }

            }
        });

        phone_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(!str_mobileno_usermanual.equals("")) {
                if(!phone_TV.getText().toString().equals("")) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    //intent.setData(Uri.parse("tel:" +str_mobileno_usermanual));
                    intent.setData(Uri.parse("tel:" +phone_TV.getText().toString()));

                    startActivity(intent);
                }

            }
        });
        if (isInternetPresent) {


            GetLoadHelpTask getLoadHelpTask = new GetLoadHelpTask(Activity_UserManual_DownloadPDF.this);
            getLoadHelpTask.execute();
        }else{

            //Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();

        }

        phone_TV.setText(Class_SaveSharedPreference.getSupportPhone(Activity_UserManual_DownloadPDF.this));
        emailid_TV.setText(Class_SaveSharedPreference.getSupportEmail(Activity_UserManual_DownloadPDF.this));


    }

    public static class LoadUserManualDocument extends AsyncTask<String, Void, Void> {
            private ProgressDialog progressDialog;
        private Context context;



        private String downloadFileName = "";
        private static final String TAG = "Download Task";
        File apkStorage = null;



        LoadUserManualDocument (Context context){
            this.context = context;
                  progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {

     //       progressBar.setVisibility(View.VISIBLE);
           progressDialog=new ProgressDialog(context);

    /*        progressDialog.setMessage("Downloading...");
            progressDialog.show();*/

           /* progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/
        }

        @Override
        protected Void doInBackground(String... params) {
          /*  ArrayList<Bitmap> bitmapLst=null;
            Bitmap bitmaplogo=null;
            try {
                Log.d("Urlssssssssssss",url.toString());
                bitmaplogo = BitmapFactory.decodeStream(url.openStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            try {


                String doc_path = params[0];
                docName1 = params[1];
                try {
                    downloadUrl = new URL(doc_path);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                   // Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();

                }
                HttpURLConnection c = null;//Open Url Connection
                try {
                    c = (HttpURLConnection) downloadUrl.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                    //Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();

                }
                try {
                    c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                } catch (ProtocolException e) {
                    e.printStackTrace();
                  //  Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();

                }
                try {
                    c.connect();//connect the URL Connection
                } catch (IOException e) {
                    e.printStackTrace();
                   // Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();

                }

                //If Connection response is not OK then show Logs
                try {
                    if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                                + " " + c.getResponseMessage());

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                   // Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();

                }


                //Get File if SD card is present
                if (new CheckForSDCard().isSDCardPresent()) {

                    apkStorage = new File(
                            Environment.getExternalStorageDirectory() + "/"
                                    + "UserManual_DocFile");
                } else
                    Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
                    Log.e(TAG, "Directory Created.");
                }

                //docName = docName.replace("%20","");
                String[] docNameArray = doc_path.split("/");
                docName2 = docNameArray[5];
                docName = docName1 + "_" + docName2;
                Log.e("doclengthisss", String.valueOf(docNameArray.length));
                Log.e("Docnameesssss", docName);
                outputFile = new File(apkStorage, docName);//Create Output file in Main File
                Log.e("outputFile", String.valueOf(outputFile));
                //Create New File if not present
                if (!outputFile.exists()) {
                    try {
                        outputFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                       // Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                    Log.e(TAG, "File Created");
                }

                FileOutputStream fos = null;//Get OutputStream for NewFile Location
                try {
                    fos = new FileOutputStream(outputFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }

                InputStream is = null;//Get InputStream for connection
                try {
                    is = c.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                   // Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }

                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                try {
                    while ((len1 = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len1);//Write new file
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                   // Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();

                }

                //Close all connection after doing task
                try {
                    fos.close();

                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                   // Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                //DocView_MainActivity.runOnUiThread(new Runnable() {
                // public void run() {
                //Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
             /* }
            });*/
            }

            return null;

            //return bitmaplogo;
        }

        @Override
        protected void onPostExecute(Void string) {
    /*        bitmapList.add(bitmap);

            Log.d("count1iss", String.valueOf(count1));
            Log.d("count2iss", String.valueOf(count2));
            Log.d("Bitmapsizess", String.valueOf(bitmapList.size()));
            if(bitmapList.size()==count2){
                img_mainGallery.setImageBitmap(bitmapList.get(0));
                txt_numOfImages.setText("Images: "+bitmapList.size());
            }*/

            try {
                if (outputFile != null) {
                       progressDialog.dismiss();
                   // progressBar.setVisibility(GONE);
                    Toast.makeText(context, "Downloaded Successfully", Toast.LENGTH_SHORT).show();

//                    try {
//                       FileOpen.openFile(context,outputFile);
//                       //Doc_LessonPlanDownloadFragment.FileOpen.openFile(context, outputFile);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 3000);

                    Log.e(TAG, "Download Failed");

                }
            } catch (Exception e) {
                e.printStackTrace();

                //Change button text if exception occurs

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 3000);
                Log.e(TAG, "Download Failed with Exception - " + e.getLocalizedMessage());

            }

               progressDialog.dismiss();

          //  progressBar.setVisibility(GONE);
        }
    }
    public static class FileOpen {

        public static void openFile(Context context, File url) throws IOException {
            // Create URI
            File file=url;
            //  Uri uri = Uri.fromFile(file);
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            // Check what kind of file you are trying to open, by comparing the url with extensions.
            // When the if condition is matched, plugin sets the correct intent (mime) type,
            // so Android knew what application to use to open the file
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if(url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if(url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if(url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if(url.toString().contains(".zip") || url.toString().contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if(url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if(url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if(url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if(url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if(url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if(url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                //if you want you can also define the intent type for any other file

                //additionally use else clause below, to manage other unknown extensions
                //in this case, Android will show all applications installed on the device
                //so you can choose which application to use
                intent.setDataAndType(uri, "*/*");
            }

            //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Activity_UserManual_DownloadPDF.this, ContactUs_Activity.class);
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
                Intent i = new Intent(Activity_UserManual_DownloadPDF.this, ContactUs_Activity.class);
                startActivity(i);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }



    private class GetLoadHelpTask extends AsyncTask<String, Void, Void> {
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

            getLoadHelp();
            return null;
        }

        public GetLoadHelpTask(Activity_UserManual_DownloadPDF activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {


            dialog.dismiss();
            if(!str_Status_usermanual.equals("")){
                if (str_Status_usermanual.equals("Active")) {
                  //    Toast.makeText(Activity_UserManual_DownloadPDF.this, "Success", Toast.LENGTH_SHORT).show();

                   setvalues();

                }
            }
        }//end of onPostExecute
    }// end Async task

    private void setvalues() {

        if(!str_mobileno_usermanual.equals("")) {
          //  phone_TV.setText(str_mobileno_usermanual);
            phone_TV.setText(Class_SaveSharedPreference.getSupportPhone(Activity_UserManual_DownloadPDF.this));


        }
        if(!str_email_usermanual.equals("")) {
//            emailid_TV.setText(str_email_usermanual);
            emailid_TV.setText(Class_SaveSharedPreference.getSupportEmail(Activity_UserManual_DownloadPDF.this));
        }

    }

    public void getLoadHelp() {
        Vector<SoapObject> result1 = null;

        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "LoadHelp";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/LoadHelp";

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            //request.addProperty("User_Email", username1);
            //request.addProperty("User_Password", password1);

           // Log.i("request", request.toString());

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

                if (response.getProperty(0).toString().contains("Status")) {
                    Log.e("Status", "hello");
                    if (response.getProperty(0).toString().contains("Status=Active")) {
                        Log.e("Status", "Active");

                        SoapPrimitive userid = (SoapPrimitive) obj2.getProperty("Mobile");
                        str_mobileno_usermanual = userid.toString();
                        SoapPrimitive userEmail = (SoapPrimitive) obj2.getProperty("Email");
                        str_email_usermanual = userEmail.toString();
                        SoapPrimitive user_status = (SoapPrimitive) obj2.getProperty("Status");
                        str_Status_usermanual = user_status.toString();
                        SoapPrimitive doc_path = (SoapPrimitive) obj2.getProperty("Doucment_Path");
                        str_Status_docpath = doc_path.toString();


                        Log.e("phone", str_mobileno_usermanual);
                        Log.e("Email", str_email_usermanual);
                        Log.e("str_Status", str_Status_usermanual);
                        Log.e("str_Status_docpath", str_Status_docpath);


                        Class_SaveSharedPreference.setSupportEmail(Activity_UserManual_DownloadPDF.this,str_email_usermanual);
                        Class_SaveSharedPreference.setSupportPhone(Activity_UserManual_DownloadPDF.this,str_mobileno_usermanual);

                        SharedPreferences.Editor  myprefs_loginuserid= sharedpref_usermanualpdf_Obj.edit();
                        myprefs_loginuserid.putString(key_usermanualpdfurl, str_Status_docpath);
                        myprefs_loginuserid.apply();

                    }
                } else {
                    Log.e("str_Status_usermanual", "str_Status_usermanual=null");

                }

            } catch (Throwable t) {
                Log.e("request fail", "> " + t.getMessage());
                str_Status_usermanual = "slow internet";

            }
        } catch (Throwable t) {
            Log.e("UnRegister Receiver ", "> " + t.getMessage());

        }

    }


}
