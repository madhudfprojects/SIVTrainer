package com.det.skillinvillage;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.det.skillinvillage.model.Class_Get_User_DocumentResponse;
import com.det.skillinvillage.model.Class_ListVersion;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferencebook_usercredential;

public class DocView_MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    ImageView iv_lessenplan, iv_qun;
    private Context context;
    String str_loginuserID;
    private int count1;
    private int count2;
    String serverPath = "http://mis.detedu.org:8089/";
    private String wordDocPath = "null";
    private String wordDocumentName;
    private String docName;
    private URL downloadUrl;
    File outputFile = null;

    ConnectionDetector internetDectector;
    Boolean isInternetPresent = false;

    SharedPreferences sharedpref_loginuserid_Obj;


    //added by shivaleela on Nov 4th 2020
    ArrayList<Class_ListVersion> Array_ClassListVersion;
    Interface_userservice userService1;
    String str_actualdocPath = null, str_DocumentPath = null, str_docID, str_DocumentDate, str_DocumentName, str_DocumentType, str_DocumentStatus, str_DocumentTime,str_doc_verification="",str_doc_TopicLevelID="";
    Class_ListVersion[] arrayObj_Class_monthcontents;
    SharedPreferences sharedpreferencebook_usercredential_Obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_view__main2);
        userService1 = Class_ApiUtils.getUserService();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Document View");


        iv_lessenplan = findViewById(R.id.iv_lessenplan);
        iv_qun = findViewById(R.id.iv_qun);

      /*  SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        str_loginuserID = myprefs.getString("login_userid", "nothing");
*/
//        sharedpref_loginuserid_Obj = getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
//        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();
        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_loginuserID = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();


        internetDectector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if (isInternetPresent) {
//            GetDocList getDocList = new GetDocList(DocView_MainActivity.this);
//            getDocList.execute();
            getdocumentlist();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }

        iv_lessenplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DocView_MainActivity.this, DocView_LessonPlanActivity.class);
                startActivity(i);
            }
        });

        iv_qun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DocView_MainActivity.this, DocView_QunPaperActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }


    private void showSnack(boolean isConnected) {
        if (!isConnected) {

            AlertDialog.Builder adb = new AlertDialog.Builder(DocView_MainActivity.this);
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

            //finish();
        }

    }

//    public class GetDocList extends AsyncTask<Void, Void, SoapObject> {
//
//        AlertDialog alertDialog;
//        private ProgressBar progressBar;
//        //   private ProgressDialog progressDialog;
//
//        GetDocList(Context ctx) {
//            context = ctx;
//            //  progressDialog = new ProgressDialog(context);
//        }
//
//
//        @Override
//        protected SoapObject doInBackground(Void... params) {
//            //String versionCode = (String) params[2];
//
//            SoapObject response = getDocList();
//
//            //       Log.d("Soap response issssss",response.toString());
//
//            return response;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            progressBar = findViewById(R.id.progressBar);
//            progressBar.setVisibility(View.VISIBLE);
//
//            /*progressDialog.setMessage("Loading");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();*/
//
//        }
//
//        @Override
//        protected void onPostExecute(SoapObject result) {
//
//            if (result != null) {
//
//                //-----------------------------------
//
//                SoapObject soapObj = result;//(SoapObject) result.getProperty("vmDocument");
//                DocView_Module.listview_arr.clear();
//                QunPaperDoc_Module.listview_arr.clear();
//                LessionPlanDoc_Module.listview_arr.clear();
//                if (!soapObj.toString().equals("anyType{}") && !soapObj.toString().equals(null)) {
//
//                    SoapPrimitive S_DocumentPath, S_Document_ID, S_Document_Date, S_Document_Time, S_Document_Name, S_Document_Type, S_Document_Status;
//                    Object O_DocumentPath, O_Document_ID, O_Document_Date, O_Document_Time, O_Document_Name, O_Document_Type, O_Document_Status;
//                    String str_DocumentPath, str_actualdocPath = null, str_Document_ID = null, str_Document_Date = null, str_Document_Time = null, str_Document_Name = null, str_Document_Type = null, str_Document_Status = null;
//                    //count2 = soapObj.getPropertyCount();
//
//                    for (count1 = 0; count1 < soapObj.getPropertyCount(); count1++) {
//                        SoapObject doclist = (SoapObject) soapObj.getProperty(count1);
//                        Log.d("doclist", doclist.toString());
//
//                        O_Document_ID = doclist.getProperty("Document_ID");
//                        if (!O_Document_ID.toString().equals("anyType{}") && !O_Document_ID.toString().equals(null)) {
//                            S_Document_ID = (SoapPrimitive) doclist.getProperty("Document_ID");
//                            str_Document_ID = S_Document_ID.toString();
//                        }
//                        O_Document_Date = doclist.getProperty("Document_Date");
//                        if (!O_Document_Date.toString().equals("anyType{}") && !O_Document_Date.toString().equals(null)) {
//                            S_Document_Date = (SoapPrimitive) doclist.getProperty("Document_Date");
//                            str_Document_Date = S_Document_Date.toString();
//                        }
//                        O_Document_Time = doclist.getProperty("Document_Time");
//                        if (!O_Document_Time.toString().equals("anyType{}") && !O_Document_Time.toString().equals(null)) {
//                            S_Document_Time = (SoapPrimitive) doclist.getProperty("Document_Time");
//                            str_Document_Time = S_Document_Time.toString();
//                        }
//                        O_Document_Name = doclist.getProperty("Document_Name");
//                        if (!O_Document_Name.toString().equals("anyType{}") && !O_Document_Name.toString().equals(null)) {
//                            S_Document_Name = (SoapPrimitive) doclist.getProperty("Document_Name");
//                            str_Document_Name = S_Document_Name.toString();
//                        }
//                        O_Document_Type = doclist.getProperty("Document_Type");
//                        if (!O_Document_Type.toString().equals("anyType{}") && !O_Document_Type.toString().equals(null)) {
//                            S_Document_Type = (SoapPrimitive) doclist.getProperty("Document_Type");
//                            str_Document_Type = S_Document_Type.toString();
//                        }
//                        O_Document_Status = doclist.getProperty("Document_Status");
//                        if (!O_Document_Status.toString().equals("anyType{}") && !O_Document_Status.toString().equals(null)) {
//                            S_Document_Status = (SoapPrimitive) doclist.getProperty("Document_Status");
//                            str_Document_Status = S_Document_Status.toString();
//                        }
//                        O_DocumentPath = doclist.getProperty("Document_Path");
//                        if (!O_DocumentPath.toString().equals("anyType{}") && !O_DocumentPath.toString().equals(null)) {
//                            S_DocumentPath = (SoapPrimitive) doclist.getProperty("Document_Path");
//                            str_DocumentPath = S_DocumentPath.toString();
//
//
//                            //Log.d("F")
//
//                            str_actualdocPath = serverPath + str_DocumentPath.substring(2);
//                            //Log.d("Documentsssssss",str_actualdocPath);
//
//
//                            //       if(str_actualdocPath.endsWith("jpg") || str_actualdocPath.endsWith("jpeg") || str_actualdocPath.endsWith("png")){
//
//                             /*   if (str_actualdocPath.endsWith("jpg") || str_actualdocPath.endsWith("jpeg") || str_actualdocPath.endsWith("png") || str_actualdocPath.endsWith("gif") || str_actualdocPath.endsWith("psd")) {
//                                    count2++;
//
//                                    str_actualdocPath = str_actualdocPath.replace(" ", "%20");
//                                    //Log.d("str_actualdocPath", str_actualdocPath);
//
//                                    //if(!str_actualdocPath.contains("(1)")) {
//                                    try {
//                                        url = new URL(str_actualdocPath);
//                                        urllist.add(url);
//                                        Log.d("Urlsssss", url.toString());
//                                    } catch (MalformedURLException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                    imagePathList.add(str_actualdocPath);
//*//*
//                                LoadGalleryImage loadGalleryImg = new LoadGalleryImage(context);
//                                loadGalleryImg.execute();*//*
//                                }*/
//
//                            if (str_actualdocPath.endsWith("docx") || str_actualdocPath.endsWith("doc") || str_actualdocPath.endsWith("pdf")) {
//                                wordDocPath = str_actualdocPath;
//                                wordDocPath = wordDocPath.replace(" ", "%20");
//
//                                Log.d("wordDocPath", wordDocPath);
//                                Log.d("downloadUrl", String.valueOf(downloadUrl));
//                                String[] docNameArray = wordDocPath.split("/");
//                                docName = docNameArray[4];
//                                Log.d("doclengthisss", String.valueOf(docNameArray.length));
//                                Log.d("Docnameesssss", docName);
//
//
//                                try {
//                                    downloadUrl = new URL(wordDocPath);
//                                } catch (MalformedURLException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//
//                        }
//
//
//                        //     DocView_Module docView_module=new DocView_Module(str_Document_ID,str_Document_Date,str_Document_Time,str_Document_Name,str_actualdocPath,str_Document_Type,str_Document_Status);
//                        DocView_Module.listview_arr.add(new DocView_Module(str_Document_ID, str_Document_Date, str_Document_Time, str_Document_Name, wordDocPath, str_Document_Type, str_Document_Status,str_doc_verification));
//
//                        if (str_Document_Type.equalsIgnoreCase("Question Paper")) {
//                            if (str_Document_Status.equalsIgnoreCase("Active")) {
//                                QunPaperDoc_Module.listview_arr.add(new QunPaperDoc_Module(str_Document_ID, str_Document_Date, str_Document_Time, str_Document_Name, wordDocPath, str_Document_Type, str_Document_Status,str_doc_verification));
//                            }
//                        } else if (str_Document_Type.equalsIgnoreCase("Lesson Plan")) {
//                            if (str_Document_Status.equalsIgnoreCase("Active")) {
//                                LessionPlanDoc_Module.listview_arr.add(new LessionPlanDoc_Module(str_Document_ID, str_Document_Date, str_Document_Time, str_Document_Name, wordDocPath, str_Document_Type, str_Document_Status,str_doc_verification));
//                            }
//                        }
//                    }
//                    Log.e("tag", "list size==" + DocView_Module.listview_arr.size());
//
//                }
//
//                //  }
//
//
//                progressBar.setVisibility(GONE);
//
//                //progressDialog.dismiss();
//            }
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }
//    }

//    private SoapObject getDocList() {
//        String METHOD_NAME = "LoadDocument";
//        String SOAP_ACTION1 = "http://mis.detedu.org:8089/LoadDocument";
//        String NAMESPACE = "http://mis.detedu.org:8089/";
//
//        try {
//            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//
//
//            request.addProperty("User_ID", str_loginuserID);
//
//            Log.e("tag", "User_ID docview:" + str_loginuserID);
//
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            envelope.setOutputSoapObject(request);
//            HttpTransportSE androidHttpTransport = new HttpTransportSE("http://mis.detedu.org:8089/SIVService.asmx?WSDL");
//            try {
//                androidHttpTransport.call(SOAP_ACTION1, envelope);
//
//                SoapObject response = (SoapObject) envelope.getResponse();
//                Log.e("soap docview response:", response.toString());
//                return response;
//
//            } catch (OutOfMemoryError ex) {
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        Toast.makeText(context, "Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
//                    }
//                });
//            } catch (Exception t) {
//                Log.e("request fail", "> " + t.getMessage());
//                final String exceptionStr = t.getMessage();
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        Toast.makeText(context, "Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        } catch (OutOfMemoryError ex) {
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    Toast.makeText(context, "Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
//                }
//            });
//        } catch (Exception t) {
//            Log.e("exception outside", t.getMessage());
//            final String exceptionStr = t.getMessage();
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    Toast.makeText(context, "Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//        return null;
//
//    }

    public void getdocumentlist() {

        Call<Class_Get_User_DocumentResponse> call = userService1.getdocumentview(str_loginuserID);
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(DocView_MainActivity.this);
        //  progressDoalog.setMax(100);
        //  progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<Class_Get_User_DocumentResponse>() {
            @Override
            public void onResponse(Call<Class_Get_User_DocumentResponse> call, Response<Class_Get_User_DocumentResponse> response) {
                Log.e("Entered respmonth", "getdocumentview");

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    Class_Get_User_DocumentResponse class_loginresponse = response.body();
                    if (class_loginresponse.getStatus()) {
                        DocView_Module.listview_arr.clear();
                        QunPaperDoc_Module.listview_arr.clear();
                        LessionPlanDoc_Module.listview_arr.clear();
                        //Array_ClassListVersion.clear();
                        List<Class_ListVersion> monthContents_list = response.body().getClass_ListVersion();

                        arrayObj_Class_monthcontents = new Class_ListVersion[monthContents_list.size()];
                        Log.e("arrayObj_Class_mosize", String.valueOf(arrayObj_Class_monthcontents.length));

                        for (int i = 0; i < arrayObj_Class_monthcontents.length; i++) {


                            Log.e("getdocumentview", String.valueOf(class_loginresponse.getStatus()));
                            Log.e("getdocumentview", class_loginresponse.getMessage());

                            Class_ListVersion innerObj_Class_SandboxList = new Class_ListVersion();
                            innerObj_Class_SandboxList.setDocumentID(class_loginresponse.getClass_ListVersion().get(i).getDocumentID());
                            innerObj_Class_SandboxList.setDocumentDate(class_loginresponse.getClass_ListVersion().get(i).getDocumentDate());
                            innerObj_Class_SandboxList.setDocumentName(class_loginresponse.getClass_ListVersion().get(i).getDocumentName());
                            innerObj_Class_SandboxList.setDocumentPath(class_loginresponse.getClass_ListVersion().get(i).getDocumentPath());
                            innerObj_Class_SandboxList.setDocumentType(class_loginresponse.getClass_ListVersion().get(i).getDocumentType());
                            innerObj_Class_SandboxList.setDocumentStatus(class_loginresponse.getClass_ListVersion().get(i).getDocumentStatus());
                            innerObj_Class_SandboxList.setDocumentVerification(class_loginresponse.getClass_ListVersion().get(i).getDocumentVerification());
                            innerObj_Class_SandboxList.setTopicLevel_ID(class_loginresponse.getClass_ListVersion().get(i).getTopicLevel_ID());

                            arrayObj_Class_monthcontents[i] = innerObj_Class_SandboxList;
                            Log.e("spinner", arrayObj_Class_monthcontents[i].getDocumentName());

                            // Array_ClassListVersion.add(arrayObj_Class_monthcontents[i]);
                            //  for (count1 = 0; count1 < arrayObj_Class_monthcontents.length; count1++) {

                            str_docID = String.valueOf(class_loginresponse.getClass_ListVersion().get(i).getDocumentID());
                            str_DocumentDate = class_loginresponse.getClass_ListVersion().get(i).getDocumentDate();
                            str_DocumentTime = class_loginresponse.getClass_ListVersion().get(i).getDocumentTime();
                            str_DocumentName = class_loginresponse.getClass_ListVersion().get(i).getDocumentName();
                            str_DocumentType = class_loginresponse.getClass_ListVersion().get(i).getDocumentType();
                            str_DocumentStatus = class_loginresponse.getClass_ListVersion().get(i).getDocumentStatus();
                            str_doc_verification=class_loginresponse.getClass_ListVersion().get(i).getDocumentVerification();
                            str_doc_TopicLevelID=class_loginresponse.getClass_ListVersion().get(i).getTopicLevel_ID();
                            str_DocumentPath = class_loginresponse.getClass_ListVersion().get(i).getDocumentPath();
                            if (str_DocumentPath != null) {

//                                      str_actualdocPath = serverPath+str_DocumentPath.substring(2);
                                str_actualdocPath = str_DocumentPath;

                                if (str_actualdocPath.endsWith("docx") || str_actualdocPath.endsWith("doc") || str_actualdocPath.endsWith("pdf")) {
                                    wordDocPath = str_actualdocPath;
                                    wordDocPath = wordDocPath.replace(" ", "%20");

                                    Log.d("wordDocPath", wordDocPath);
                                    Log.d("downloadUrl", String.valueOf(downloadUrl));
                                    String[] docNameArray = wordDocPath.split("/");
                                    docName = docNameArray[5];
                                    Log.d("doclengthisss", String.valueOf(docNameArray.length));
                                    Log.d("Docnameesssss", docName);


                                    try {
                                        downloadUrl = new URL(wordDocPath);
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }

                                }

                            }


                            DocView_Module.listview_arr.add(new DocView_Module(str_docID, str_DocumentDate, str_DocumentTime, str_DocumentName, wordDocPath, str_DocumentType, str_DocumentStatus,str_doc_verification,str_doc_TopicLevelID));

                            if (str_DocumentType.equalsIgnoreCase("Question Paper")) {
                                if (str_DocumentStatus.equalsIgnoreCase("Active")) {
                                    QunPaperDoc_Module.listview_arr.add(new QunPaperDoc_Module(str_docID, str_DocumentDate, str_DocumentTime, str_DocumentName, wordDocPath, str_DocumentType, str_DocumentStatus,str_doc_verification,str_doc_TopicLevelID));
                                }
                            } else if (str_DocumentType.equalsIgnoreCase("Lesson Plan")) {
                                if (str_DocumentStatus.equalsIgnoreCase("Active")) {
                                    LessionPlanDoc_Module.listview_arr.add(new LessionPlanDoc_Module(str_docID, str_DocumentDate, str_DocumentTime, str_DocumentName, wordDocPath, str_DocumentType, str_DocumentStatus,str_doc_verification,str_doc_TopicLevelID));
                                }
                            }
                            //  }
                            Log.e("tag", "list size==" + DocView_Module.listview_arr.size());


                        }

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
                    } else {
                        Toast.makeText(DocView_MainActivity.this, "Kindly restart your application", Toast.LENGTH_SHORT).show();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {


            case android.R.id.home:
                Intent i = new Intent(DocView_MainActivity.this, Activity_HomeScreen.class);
                startActivity(i);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(DocView_MainActivity.this, Activity_HomeScreen.class);
        startActivity(i);
        finish();

    }

}
