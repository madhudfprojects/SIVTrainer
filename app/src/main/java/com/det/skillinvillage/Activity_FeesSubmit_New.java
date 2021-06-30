package com.det.skillinvillage;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.det.skillinvillage.model.Class_GetPaymentPendingSummaryResponseList;
import com.det.skillinvillage.model.Class_GetPendingPaymentResponseList;
import com.det.skillinvillage.model.Class_GetStudentPaymentResponseList;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.model.GetPaymentPendingSummaryResponse;
import com.det.skillinvillage.model.GetPendingPaymentResponse;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferencebook_usercredential;

public class Activity_FeesSubmit_New extends AppCompatActivity {

    ListView feessubmit_Listview;
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    Class_FeesSubmissionList_New Objclass_feesSubmissionList_new;
    Class_FeesSubmissionList_New[] ArrayObjclass_feesSubmissionList_new, ArrayObjclass_feesSubmissionList_new2;
    String str_loginuserID,str_paymentstatus;
    int loadPendingPaymentCount;
    LinearLayout PendingAmtstudentlist_LL,NoRecords_studentlist_LL;
    TableLayout tableLayout;
    TableRow tr;

    SharedPreferences sharedpref_loginuserid_Obj;
    TextView no_of_students_paid_fees_tv,feespayment_tv,amt_received_by_accountant_tv,balance_not_submitted_to_accountant_tv;
    String str_no_of_students_paid_fees,str_fees_payment,str_amt_received_by_accountant,str_balance_not_submitted_to_accountant;

   String str_paymentstatus_feesSummary;
   LinearLayout fees_summary_ll;

    Interface_userservice userService1;
    Class_GetPaymentPendingSummaryResponseList[] arrayObj_class_getpaymentpendingsummaryresp;
    Class_GetPendingPaymentResponseList[] arrayObj_class_getpaymentpendingresp;

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fees_submit__new);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" Fees Submission Pending ");

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();
        userService1 = Class_ApiUtils.getUserService();
//        SharedPreferences myprefs_userid = this.getSharedPreferences("user", Context.MODE_PRIVATE);
//        str_loginuserID = myprefs_userid.getString("login_userid", "nothing");

//        sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
//        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_loginuserID = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();

        tableLayout= findViewById(R.id.tableLayout);

        no_of_students_paid_fees_tv= findViewById(R.id.no_of_students_paid_fees_TV);
        feespayment_tv= findViewById(R.id.feespayment_TV);
        amt_received_by_accountant_tv= findViewById(R.id.amt_received_by_accountant_TV);
        balance_not_submitted_to_accountant_tv= findViewById(R.id.balance_not_submitted_to_accountant_TV);
        fees_summary_ll= findViewById(R.id.fees_summary_LL);




        feessubmit_Listview = findViewById(R.id.feessubmit_LISTVIEW);
        getpendingpaymentlist();
        if(isInternetPresent) {
            fees_summary_ll.setVisibility(View.VISIBLE);
            getfeesSubmitSummary();


        }else{
            fees_summary_ll.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();

            uploadfromDB_PendingAmtStudentList();
        }
    }//oncreate

    public void getpendingpaymentlist() {

        if (isInternetPresent) {
            deletePendingStudentTable_B4insertion();
            GetPendingPayment();
//            PendingpaymentlistTask task = new PendingpaymentlistTask(Activity_FeesSubmit_New.this);
//            task.execute();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }


    }
    public void GetPendingPayment() {

        Call<GetPendingPaymentResponse> call = userService1.GetPendingPayment(str_loginuserID);//String.valueOf(str_stuID)
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_FeesSubmit_New.this);
        //  progressDoalog.setMax(100);
        //  progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<GetPendingPaymentResponse>() {
            @Override
            public void onResponse(Call<GetPendingPaymentResponse> call, Response<GetPendingPaymentResponse> response) {
                Log.e("Entered resp", "GetPendingPayment");

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    GetPendingPaymentResponse class_loginresponse = response.body();
                    if (class_loginresponse.getStatus()) {

                        List<Class_GetPendingPaymentResponseList> monthContents_list = response.body().getLst();
                        loadPendingPaymentCount=monthContents_list.size();
                        Log.e("count", String.valueOf(loadPendingPaymentCount));

                        Class_GetPendingPaymentResponseList []  arrayObj_Class_monthcontents = new Class_GetPendingPaymentResponseList[monthContents_list.size()];
                        arrayObj_class_getpaymentpendingresp = new Class_GetPendingPaymentResponseList[arrayObj_Class_monthcontents.length];

                        for (int i = 0; i < arrayObj_Class_monthcontents.length; i++) {
                            Log.e("PaymentPending", String.valueOf(class_loginresponse.getStatus()));
                            Log.e("PaymentPending", class_loginresponse.getMessage());

                            Class_GetPendingPaymentResponseList innerObj_Class_academic = new Class_GetPendingPaymentResponseList();
                            innerObj_Class_academic.setStudentName(class_loginresponse.getLst().get(i).getStudentName());
                            innerObj_Class_academic.setApplicationNo(class_loginresponse.getLst().get(i).getApplicationNo());
                            innerObj_Class_academic.setPaymentAmount(class_loginresponse.getLst().get(i).getPaymentAmount());
                            arrayObj_class_getpaymentpendingresp[i] = innerObj_Class_academic;
//                            str_paymentstatus_feesSummary = class_loginresponse.getLst().get(i).getPaymentStatus();

                            String str_stuName = class_loginresponse.getLst().get(i).getStudentName();
                            String str_ApplicationNo = class_loginresponse.getLst().get(i).getApplicationNo();
                            String str_paymentamt = class_loginresponse.getLst().get(i).getPaymentAmount();
                            Log.e("str_stuName", str_stuName);

                            DBCreate_pendinglist_insert_2SQLiteDB(str_stuName,str_ApplicationNo,str_paymentamt);

                        }//for loop end

                        uploadfromDB_PendingAmtStudentList();

                    } else {
                        progressDoalog.dismiss();
                    }
                } else {
                    progressDoalog.dismiss();
                    Log.e("Entered resp else", "");
                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.e("error message", error.getMsg());

                    if (error.getMsg() != null) {

                        Log.e("error message", error.getMsg());
//                        str_getmonthsummary_errormsg = error.getMsg();
//                        alerts_dialog_getexlistviewError();

                        //Toast.makeText(getActivity(), error.getMsg(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Activity_FeesSubmit_New.this,"Kindly restart your application", Toast.LENGTH_SHORT).show();

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




    public void alert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Activity_FeesSubmit_New.this);
        builder1.setMessage("No fees to be submitted");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i=new Intent(Activity_FeesSubmit_New.this,Activity_MarketingHomeScreen.class);
                        startActivity(i);
                        finish();

                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }







    public void getfeesSubmitSummary() {

        Call<GetPaymentPendingSummaryResponse> call = userService1.GetPaymentPendingSummary(str_loginuserID);//String.valueOf(str_stuID)
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_FeesSubmit_New.this);
        //  progressDoalog.setMax(100);
        //  progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<GetPaymentPendingSummaryResponse>() {
            @Override
            public void onResponse(Call<GetPaymentPendingSummaryResponse> call, Response<GetPaymentPendingSummaryResponse> response) {
                Log.e("Entered resp", "GetPaymentPendingSummary");

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    GetPaymentPendingSummaryResponse class_loginresponse = response.body();
                    if (class_loginresponse.getStatus()) {

                        List<Class_GetPaymentPendingSummaryResponseList> monthContents_list = response.body().getLst();
                        int count=monthContents_list.size();
                        Class_GetStudentPaymentResponseList []  arrayObj_Class_monthcontents = new Class_GetStudentPaymentResponseList[monthContents_list.size()];
                        arrayObj_class_getpaymentpendingsummaryresp = new Class_GetPaymentPendingSummaryResponseList[arrayObj_Class_monthcontents.length];

                        for (int i = 0; i < arrayObj_Class_monthcontents.length; i++) {
                            Log.e("PaymentPendingSummary", String.valueOf(class_loginresponse.getStatus()));
                            Log.e("PaymentPendingSummary", class_loginresponse.getMessage());

                            Class_GetPaymentPendingSummaryResponseList innerObj_Class_academic = new Class_GetPaymentPendingSummaryResponseList();
                            innerObj_Class_academic.setStudentCount(class_loginresponse.getLst().get(i).getStudentCount());
                            innerObj_Class_academic.setReceviedFee(class_loginresponse.getLst().get(i).getReceviedFee());
                            innerObj_Class_academic.setAccountRecevied(class_loginresponse.getLst().get(i).getAccountRecevied());
                            innerObj_Class_academic.setTrainerPending(class_loginresponse.getLst().get(i).getTrainerPending());
                            arrayObj_class_getpaymentpendingsummaryresp[i] = innerObj_Class_academic;
                            str_paymentstatus_feesSummary = class_loginresponse.getLst().get(i).getPaymentStatus();
                            if((str_paymentstatus_feesSummary.equals("No Result")) || (str_paymentstatus_feesSummary.equals("Error"))) {
                                Log.e("paymentstatusfeesSumm", str_paymentstatus_feesSummary);

                            }else{
                                str_no_of_students_paid_fees = class_loginresponse.getLst().get(i).getStudentCount();
                                str_fees_payment = class_loginresponse.getLst().get(i).getReceviedFee();
                                str_amt_received_by_accountant = class_loginresponse.getLst().get(i).getAccountRecevied();
                                str_balance_not_submitted_to_accountant = class_loginresponse.getLst().get(i).getTrainerPending();
                                Log.e("Student_Count", str_no_of_students_paid_fees);
                            }

                        }//for loop end
                        if(arrayObj_class_getpaymentpendingsummaryresp.length>0) {

                            if ((str_paymentstatus_feesSummary.equals("No Result")) || (str_paymentstatus_feesSummary.equals("Error"))) {


                                Log.e("paymentstatus_feeSmmary", "no result");

                            } else {
                                setvalues();
                            }
                        }else{
                            fees_summary_ll.setVisibility(View.GONE);
                            Toast.makeText(Activity_FeesSubmit_New.this,"No Summary data available", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        progressDoalog.dismiss();
                    }
                } else {
                    progressDoalog.dismiss();
                    Log.e("Entered resp else", "");
                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.e("error message", error.getMsg());

                    if (error.getMsg() != null) {

                        Log.e("error message", error.getMsg());
//                        str_getmonthsummary_errormsg = error.getMsg();
//                        alerts_dialog_getexlistviewError();

                        //Toast.makeText(getActivity(), error.getMsg(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Activity_FeesSubmit_New.this,"Kindly restart your application", Toast.LENGTH_SHORT).show();

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




    public void setvalues(){

        if(!str_no_of_students_paid_fees.equals("")){
         //   no_of_students_paid_fees_tv.setText("No. of students paid fees : "+str_no_of_students_paid_fees);
            no_of_students_paid_fees_tv.setText(" : "+str_no_of_students_paid_fees);

        }

        if(!str_fees_payment.equals("")){
//            feespayment_tv.setText("Fees payment : "+getResources().getString(R.string.Rs)+""+str_fees_payment);
            feespayment_tv.setText(" : "+getResources().getString(R.string.Rs)+""+str_fees_payment);

        }
        if(!str_amt_received_by_accountant.equals("")){
         //   amt_received_by_accountant_tv.setText("Amount Received by the accountant : "+getResources().getString(R.string.Rs)+""+str_amt_received_by_accountant);
            amt_received_by_accountant_tv.setText(" : "+getResources().getString(R.string.Rs)+""+str_amt_received_by_accountant);

        }
        if(!str_balance_not_submitted_to_accountant.equals("")){
          //  balance_not_submitted_to_accountant_tv.setText("Balance Fees not submitted to accountant : "+getResources().getString(R.string.Rs)+""+str_balance_not_submitted_to_accountant);

            balance_not_submitted_to_accountant_tv.setText(" : "+getResources().getString(R.string.Rs)+""+str_balance_not_submitted_to_accountant);

            if(str_balance_not_submitted_to_accountant.equals("0")){
            }else{
                balance_not_submitted_to_accountant_tv.setTextColor(Color.RED);
                balance_not_submitted_to_accountant_tv.setBackgroundColor(Color.WHITE);
            }
        }
    }
    //DB create
    public void DBCreate_pendinglist_insert_2SQLiteDB(String student_name, String applNo, String paymentAmt) {
        SQLiteDatabase db_pendinglist = this.openOrCreateDatabase("pendingamtstuListdb", Context.MODE_PRIVATE, null);
        db_pendinglist.execSQL("CREATE TABLE IF NOT EXISTS PendingAmtStudentList(PendingList_StuName VARCHAR,PendingList_ApplNo VARCHAR,PendingList_PaymentAmt VARCHAR);");
        String SQLiteQuery = "INSERT INTO PendingAmtStudentList (PendingList_StuName, PendingList_ApplNo,PendingList_PaymentAmt)" +
                " VALUES ('" + student_name + "','" + applNo + "','" + paymentAmt + "');";
        db_pendinglist.execSQL(SQLiteQuery);
        Log.d("student", "pendingamtstuListdbcreated");
    }


    public void uploadfromDB_PendingAmtStudentList() {


        SQLiteDatabase pendingamt_db = this.openOrCreateDatabase("pendingamtstuListdb", Context.MODE_PRIVATE, null);
        pendingamt_db.execSQL("CREATE TABLE IF NOT EXISTS PendingAmtStudentList(PendingList_StuName VARCHAR,PendingList_ApplNo VARCHAR,PendingList_PaymentAmt VARCHAR);");
        Cursor cursor = pendingamt_db.rawQuery("SELECT DISTINCT * FROM PendingAmtStudentList", null);
        int x = cursor.getCount();
        Log.d("cursor countlistfromdb", Integer.toString(x));

        int i = 0;
        ArrayObjclass_feesSubmissionList_new2 = new Class_FeesSubmissionList_New[x];
       // if (cursor1.moveToFirst()) {
        if (cursor.moveToLast()) {

            do {
                Log.d("view", "enteredendinglist loop");
                Class_FeesSubmissionList_New innerclass_feesSubmissionList_new = new Class_FeesSubmissionList_New();
                innerclass_feesSubmissionList_new.setStudentname_feesSubmit(cursor.getString(cursor.getColumnIndex("PendingList_StuName")));
                innerclass_feesSubmissionList_new.setApplicationNo_feesSubmit(cursor.getString(cursor.getColumnIndex("PendingList_ApplNo")));
                innerclass_feesSubmissionList_new.setPaymentamount(cursor.getString(cursor.getColumnIndex("PendingList_PaymentAmt")));

                ArrayObjclass_feesSubmissionList_new2[i] = innerclass_feesSubmissionList_new;
//////////////////////////////////////////////////////////////////
//                View tableRow = LayoutInflater.from(this).inflate(R.layout.table_feessubmitlist_item,null,false);
//                TextView studentName_TV  = (TextView) tableRow.findViewById(R.id.studentName_TV);
//                TextView applicationNumber_TV  = (TextView) tableRow.findViewById(R.id.applicationNumber_TV);
//                TextView paymentamount_TV  = (TextView) tableRow.findViewById(R.id.paymentamount_TV);
//
//                studentName_TV.setText(""+cursor1.getString(cursor1.getColumnIndex("PendingList_StuName")));
//                applicationNumber_TV.setText(""+cursor1.getString(cursor1.getColumnIndex("PendingList_ApplNo")));
//                paymentamount_TV.setText(""+cursor1.getString(cursor1.getColumnIndex("PendingList_PaymentAmt")));
//                tableLayout.addView(tableRow);
/////////////////////////////////////////////////////////////////
                i++;

//            } while (cursor1.moveToNext());

            } while (cursor.moveToPrevious());

        }//if ends
        pendingamt_db.close();
        if (x > 0)
        {

            CustomAdapter_feessubmit adapter = new CustomAdapter_feessubmit();
            feessubmit_Listview.setAdapter(adapter);

        }

    }

       public void deletePendingStudentTable_B4insertion() {

        SQLiteDatabase db_deletependingamt = openOrCreateDatabase("pendingamtstuListdb", Context.MODE_PRIVATE, null);
        db_deletependingamt.execSQL("CREATE TABLE IF NOT EXISTS PendingAmtStudentList(PendingList_StuName VARCHAR,PendingList_ApplNo VARCHAR,PendingList_PaymentAmt VARCHAR);");
        Cursor cursor = db_deletependingamt.rawQuery("SELECT * FROM PendingAmtStudentList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_deletependingamt.delete("PendingAmtStudentList", null, null);

        }
           db_deletependingamt.close();
    }

    ////////////////////////////////////////////////////////////////////
    private class Holder {
        TextView studentname_tv;
        TextView applicationNumber_tv;
        TextView paymentamt_tv;

    }
    public class CustomAdapter_feessubmit extends BaseAdapter {

        public CustomAdapter_feessubmit() {

        super();
        Log.d("Inside cfeessubmit()", "Inside CustomAdapter_feessubmit()");
    }

        @Override
        public int getCount() {

        String x = Integer.toString(ArrayObjclass_feesSubmissionList_new2.length);
        Log.d("Arrayclass.length", x);
        return ArrayObjclass_feesSubmissionList_new2.length;

    }

        @Override
        public Object getItem(int position) {
        String x = Integer.toString(position);

        Log.d("getItem position", "x");
        return ArrayObjclass_feesSubmissionList_new2[position];
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
            convertView = LayoutInflater.from(Activity_FeesSubmit_New.this).inflate(R.layout.child_feessubmit_layout, parent, false);


            PendingAmtstudentlist_LL = convertView.findViewById(R.id.studentfeessubmitlist_LL);
            NoRecords_studentlist_LL = convertView.findViewById(R.id.noRecords_FeesSubmit_LL);
            holder.studentname_tv = convertView.findViewById(R.id.studentName_TV);
            holder.applicationNumber_tv = convertView.findViewById(R.id.applicationNumber_TV);
            holder.paymentamt_tv = convertView.findViewById(R.id.paymentamount_TV);

            Log.d("Inside If convertView", "Inside If convertView");

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
            Log.d("Inside else convertView", "Inside else convertView");
        }

        Objclass_feesSubmissionList_new = (Class_FeesSubmissionList_New) getItem(position);

        if(isInternetPresent) {
            if (loadPendingPaymentCount == 0) {
                PendingAmtstudentlist_LL.setVisibility(android.view.View.GONE);
                NoRecords_studentlist_LL.setVisibility(android.view.View.VISIBLE);
            } else {
                PendingAmtstudentlist_LL.setVisibility(android.view.View.VISIBLE);
                NoRecords_studentlist_LL.setVisibility(android.view.View.GONE);

                if (Objclass_feesSubmissionList_new != null) {
                    holder.studentname_tv.setText(Objclass_feesSubmissionList_new.getStudentname_feesSubmit());
                    holder.applicationNumber_tv.setText(Objclass_feesSubmissionList_new.getApplicationNo_feesSubmit());
                   // holder.paymentamt_tv.setText(Objclass_feesSubmissionList_new.getPaymentamount());
                    holder.paymentamt_tv.setText(getResources().getString(R.string.Rs) +""+Objclass_feesSubmissionList_new.getPaymentamount());

                }// end of if
            }
        }else {
            if (Objclass_feesSubmissionList_new != null) {
                holder.studentname_tv.setText(Objclass_feesSubmissionList_new.getStudentname_feesSubmit());
                holder.applicationNumber_tv.setText(Objclass_feesSubmissionList_new.getApplicationNo_feesSubmit());
               // holder.paymentamt_tv.setText(Objclass_feesSubmissionList_new.getPaymentamount());
                holder.paymentamt_tv.setText(getResources().getString(R.string.Rs) +""+Objclass_feesSubmissionList_new.getPaymentamount());

            }// end of if
        }
            return convertView;
    }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Activity_FeesSubmit_New.this,Activity_MarketingHomeScreen.class);
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
                Intent i=new Intent(Activity_FeesSubmit_New.this,Activity_MarketingHomeScreen.class);
                startActivity(i);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
