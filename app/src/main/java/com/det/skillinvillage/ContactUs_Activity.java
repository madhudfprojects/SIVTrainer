package com.det.skillinvillage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.det.skillinvillage.Class_InternetDectector;
import com.det.skillinvillage.Class_SaveSharedPreference;
import com.det.skillinvillage.R;
import com.det.skillinvillage.model.Class_getdemo_resplist;
import com.det.skillinvillage.model.Class_gethelp_resplist;

import org.w3c.dom.Text;

import static com.det.skillinvillage.Activity_UserManual_DownloadPDF.key_usermanualpdfurl;
import static com.det.skillinvillage.Activity_UserManual_DownloadPDF.sharedpreferenc_usermanual;
import static com.det.skillinvillage.MainActivity.Key_username;
import static com.det.skillinvillage.MainActivity.key_userimage;


public class ContactUs_Activity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView add_newfarmpond_iv;

    Class_gethelp_resplist[] class_gethelp_resplist_arrayObj;
    Class_getdemo_resplist[] class_getdemo_resplist_arrayObj;

    String str_link,str_Googlelogin_Username="",str_Googlelogin_UserImg="";
    SharedPreferences sharedpref_username_Obj;
    SharedPreferences sharedpref_userimage_Obj;
    ImageView pdficon_IV;
    TextView usermanual_TV;
    ImageView usermanual_download_iv ;
    LinearLayout downloadlayout_LL;
    SharedPreferences sharedpref_usermanualpdf_Obj;
    String str_fileurl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_contact_us);
        sharedpref_usermanualpdf_Obj=getSharedPreferences(sharedpreferenc_usermanual, Context.MODE_PRIVATE);
        str_fileurl = sharedpref_usermanualpdf_Obj.getString(key_usermanualpdfurl, "").trim();

        if (count_from_HelpDetails_table() > 0) {
            setContentView(R.layout.contactus_activity);
            pdficon_IV = (ImageView)findViewById(R.id.pdficon_IV);
            usermanual_TV = (TextView)findViewById(R.id.usermanual_TV);
            usermanual_download_iv = (ImageView)findViewById(R.id.usermanual_download_iv);
            downloadlayout_LL = (LinearLayout) findViewById(R.id.downloadlayout_LL);

           /* toolbar = (Toolbar) findViewById(R.id.toolbar_farmponddetails);
            // Set upon the actionbar
            setSupportActionBar(toolbar);*/
            // Now use actionbar methods to show navigation icon and title
            // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Set upon the actionbar

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Help");

          /*  TextView title = (TextView) toolbar.findViewById(R.id.title_name);
            add_newfarmpond_iv = (ImageView) toolbar.findViewById(R.id.add_newfarmpond_iv);
            title.setText("About Us");
            getSupportActionBar().setTitle("");
            add_newfarmpond_iv.setVisibility(View.GONE);*/

           /* LinearLayout main_ll = findViewById(R.id.main2);
            TextView title_tv = new TextView(this);
            title_tv.setText("Contact us");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            title_tv.setLayoutParams(params);
            main_ll.addView(title_tv);
*/
            Data_from_HelpDetails_table();


        } else {
            setContentView(R.layout.activity_contact_us);

           /* toolbar = (Toolbar) findViewById(R.id.toolbar_farmponddetails);
            // Set upon the actionbar
            setSupportActionBar(toolbar);*/
            // Now use actionbar methods to show navigation icon and title
            // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Set upon the actionbar

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Help");

            final TextView phone_tv = (TextView) findViewById(R.id.phone_tv);
            final TextView email_tv = (TextView) findViewById(R.id.email_tv);

             downloadlayout_LL = (LinearLayout) findViewById(R.id.downloadlayout_LL);
              pdficon_IV = (ImageView)findViewById(R.id.pdficon_IV);
              usermanual_TV = (TextView)findViewById(R.id.usermanual_TV);
              usermanual_download_iv = (ImageView)findViewById(R.id.usermanual_download_iv);


            phone_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone_tv.getText().toString()));
                    startActivity(intent);
                }
            });

            email_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email_tv));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Technology Help Line");
                    startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
                }
            });

//            usermanual_TV.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i=new Intent(ContactUs_Activity.this,Activity_UserManual_OpenPDF.class);
//                    startActivity(i);
//
////                    if (isInternetPresent) {
////                        Intent i=new Intent(ContactUs_Activity.this,Activity_UserManual_OpenPDF.class);
////                        startActivity(i);
////
////                    }else{
////                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
////                    }
//
//                }
//            });
//
//
////            usermanual_TV.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    Intent intent = new Intent(ContactUs_Activity.this, Activity_ViewUserManualPDF_Downloaded_pdf.class);
////                    startActivity(intent);
////                }
////            });
//
//            usermanual_download_iv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////            "Content": "http://mis.detedu.org:8090/Document/Help/SIV_User_manual_1.0.pdf"
//
//                    Activity_UserManual_DownloadPDF.LoadUserManualDocument loadDocument = new Activity_UserManual_DownloadPDF.LoadUserManualDocument(ContactUs_Activity.this);
//                    loadDocument.execute("http://mis.detedu.org:8090/Document/Help/SIV_User_manual_1.0.pdf","User Manual");
//
////                    Class_SaveSharedPreference.setPrefFlagUsermanual(Activity_UserManual_DownloadPDF.this,"1");
////                    viewlayout_LL.setVisibility(View.VISIBLE);
//                    downloadlayout_LL.setVisibility(View.GONE);
//                }
//            });

           /* TextView title = (TextView) toolbar.findViewById(R.id.title_name);
            add_newfarmpond_iv = (ImageView) toolbar.findViewById(R.id.add_newfarmpond_iv);
            title.setText("About Us");
            getSupportActionBar().setTitle("");
            add_newfarmpond_iv.setVisibility(View.GONE);
            setContentView(R.layout.activity_contact_us);*/
        }


        //  Data_from_HelpDetails_table();


    }// end of Oncreate


    public int count_from_HelpDetails_table() {
        SQLiteDatabase db2 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS HelpDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,TitleDB VARCHAR,ContentDB VARCHAR);");
        Cursor cursor = db2.rawQuery("SELECT * FROM HelpDetails_table", null);
        int x = cursor.getCount();
        return x;
    }


    public void Data_from_HelpDetails_table() {
        SQLiteDatabase db2 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS HelpDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,TitleDB VARCHAR,ContentDB VARCHAR);");
        Cursor cursor = db2.rawQuery("SELECT * FROM HelpDetails_table", null);
        int x = cursor.getCount();

        Log.e("helpcount", String.valueOf(x));

        //class_gethelp_responses_arrayObj

        class_gethelp_resplist_arrayObj = new Class_gethelp_resplist[x];

        int i = 0;

        if (x > 0) {
            if (cursor.moveToFirst()) {
                do {

                    Class_gethelp_resplist innerObj_Class_gethelp_resplist = new Class_gethelp_resplist();
                    innerObj_Class_gethelp_resplist.setTitle(cursor.getString(cursor.getColumnIndex("TitleDB")));
                    innerObj_Class_gethelp_resplist.setContent(cursor.getString(cursor.getColumnIndex("ContentDB")));

                    Log.e("title", cursor.getString(cursor.getColumnIndex("TitleDB")).toString());
                    Log.e("content", cursor.getString(cursor.getColumnIndex("TitleDB")).toString());

                    class_gethelp_resplist_arrayObj[i] = innerObj_Class_gethelp_resplist;
                    i++;
                } while (cursor.moveToNext());
            }

        }


        LinearLayout help_ll = findViewById(R.id.help_ll);


        for (int k = 0; k < class_gethelp_resplist_arrayObj.length; k++) {
            if (x > 0) {
                String str_num = String.valueOf(k);
                /*TextView title_tv = new TextView(this);
                title_tv.setText("Contact us");*/


                TextView title_tv = new TextView(this);
                final TextView content_tv = new TextView(this);
                TextView nextline_tv = new TextView(this);

                ImageView  pdficon_new_IV = new ImageView(this);;
//                TextView usermanual_TV =new TextView(this);
//                ImageView  usermanual_download_iv = new ImageView(this);
                //0123
//                ImageView usermanual_download_iv = new ImageView(this);
//                TextView usermanual_TV= new TextView(this);
//                usermanual_download_iv.setImageResource(R.drawable.pdficon);
                title_tv.setText(class_gethelp_resplist_arrayObj[k].getTitle());


//                if(class_gethelp_resplist_arrayObj[k].getTitle().equals("User Manual")) {
//                    title_tv.setText("Guide");
//                }else {
//                    title_tv.setText(class_gethelp_resplist_arrayObj[k].getTitle());
//                }

                title_tv.setTypeface(null, Typeface.BOLD);

                title_tv.setTextSize(18);
                //text.setGravity(Gravity.LEFT);
                title_tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                help_ll.addView(title_tv);

//                if(class_gethelp_resplist_arrayObj[k].getContent().endsWith("SIV_User_manual_1.0.pdf")) {
                if(class_gethelp_resplist_arrayObj[k].getTitle().equals("User Manual")) {

                    // content_tv.setText("");
                    content_tv.setText("Click here");
                  //  downloadlayout_LL.setVisibility(View.VISIBLE);
                 //   usermanual_TV.setText("User Manual");
                    str_fileurl=class_gethelp_resplist_arrayObj[k].getContent();
                    SharedPreferences.Editor  myprefs_loginuserid= sharedpref_usermanualpdf_Obj.edit();
                    myprefs_loginuserid.putString(key_usermanualpdfurl, str_fileurl);
                    myprefs_loginuserid.apply();

                }else {
                    content_tv.setText(class_gethelp_resplist_arrayObj[k].getContent());
                }
                content_tv.setTextSize(12);
                //text.setGravity(Gravity.LEFT);
                content_tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                help_ll.addView(content_tv);


                nextline_tv.setText("\n");
                help_ll.addView(nextline_tv);

                if (title_tv.getText().equals("Contact Number")) {
                    content_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + content_tv.getText().toString()));
                            startActivity(intent);
                        }
                    });


                }
                if (title_tv.getText().equals("Email Address")) {
                    content_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + content_tv.getText().toString()));
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Technology Help Line");
                            startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
                        }
                    });
                }

                if (title_tv.getText().equals("User Manual")) {
                  //  downloadlayout_LL.setVisibility(View.VISIBLE);

 //                   pdficon_new_IV.setImageResource(R.drawable.pdficon);
//                    usermanual_download_iv.setImageResource(R.drawable.down_arrow);
//                    pdficon_IV.setVisibility(View.VISIBLE);
//                    usermanual_TV.setVisibility(View.VISIBLE);
//                    usermanual_download_iv.setVisibility(View.VISIBLE);
                  //  content_tv.setText("Click here");
                    final int finalK = k;
                    content_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Activity_UserManual_DownloadPDF.LoadUserManualDocument loadDocument = new Activity_UserManual_DownloadPDF.LoadUserManualDocument(ContactUs_Activity.this);
                            loadDocument.execute(class_gethelp_resplist_arrayObj[finalK].getContent(),"User Manual");

                            Intent i=new Intent(ContactUs_Activity.this,Activity_UserManual_OpenPDF.class);
                            startActivity(i);

                        }
                    });


                    title_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Activity_UserManual_DownloadPDF.LoadUserManualDocument loadDocument = new Activity_UserManual_DownloadPDF.LoadUserManualDocument(ContactUs_Activity.this);
//                        loadDocument.execute("http://mis.detedu.org:8090/Document/Help/SIV_User_manual_1.0.pdf","User Manual");

//                        Activity_UserManual_DownloadPDF.LoadUserManualDocument loadDocument = new Activity_UserManual_DownloadPDF.LoadUserManualDocument(ContactUs_Activity.this);
//                        loadDocument.execute(class_gethelp_resplist_arrayObj[finalK].getContent(),"User Manual");

                        Activity_UserManual_DownloadPDF.LoadUserManualDocument loadDocument = new Activity_UserManual_DownloadPDF.LoadUserManualDocument(ContactUs_Activity.this);
                        loadDocument.execute(class_gethelp_resplist_arrayObj[finalK].getContent(),"User Manual");

                        Intent i=new Intent(ContactUs_Activity.this,Activity_UserManual_OpenPDF.class);
                        startActivity(i);

                    }
                });
            }

            }
        }

//demo_ll

        Data_from_DemoDetails_table();
    }


    public void Data_from_DemoDetails_table() {
        SQLiteDatabase db2 = this.openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db2.execSQL("CREATE TABLE IF NOT EXISTS DemoDetails_table(SlNo INTEGER PRIMARY KEY AUTOINCREMENT,LanguageDB VARCHAR,LinkDB VARCHAR);");
        Cursor cursor = db2.rawQuery("SELECT * FROM DemoDetails_table", null);
        int x = cursor.getCount();

        Log.e("democount", String.valueOf(x));

        class_getdemo_resplist_arrayObj = new Class_getdemo_resplist[x];

        int i = 0;

        if (x > 0) {
            if (cursor.moveToFirst()) {
                do {

                    Class_getdemo_resplist innerObj_Class_getdemo_resplist = new Class_getdemo_resplist();
                    innerObj_Class_getdemo_resplist.setLanguage_Name(cursor.getString(cursor.getColumnIndex("LanguageDB")));
                    innerObj_Class_getdemo_resplist.setLanguage_Link(cursor.getString(cursor.getColumnIndex("LinkDB")));

                    Log.e("LanguageDB", cursor.getString(cursor.getColumnIndex("LanguageDB")).toString());
                    Log.e("LinkDB", cursor.getString(cursor.getColumnIndex("LinkDB")).toString());

                    class_getdemo_resplist_arrayObj[i] = innerObj_Class_getdemo_resplist;
                    i++;
                } while (cursor.moveToNext());
            }

        }


        LinearLayout demo_ll = findViewById(R.id.demo_ll);


        for (int k = 0; k < class_getdemo_resplist_arrayObj.length; k++) {
            if (x > 0) {


                TextView language_tv = new TextView(this);
                TextView forlanguagename_tv = new TextView(this);
                TextView link_tv = new TextView(this);
                TextView nextline_tv = new TextView(this);
                //0123

                language_tv.setText(class_getdemo_resplist_arrayObj[k].getLanguage_Name());
                language_tv.setTypeface(null, Typeface.BOLD);

                language_tv.setTextSize(18);
                // text.setGravity(Gravity.LEFT);
                language_tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                demo_ll.addView(language_tv);


                forlanguagename_tv.setText("Demo, Click here");
                forlanguagename_tv.setTextSize(12);
                forlanguagename_tv.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
                forlanguagename_tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                demo_ll.addView(forlanguagename_tv);


                link_tv.setText(class_getdemo_resplist_arrayObj[k].getLanguage_Link());
                link_tv.setTextSize(12);
                //text.setGravity(Gravity.LEFT);
                link_tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                link_tv.setVisibility(View.GONE);

                str_link = class_getdemo_resplist_arrayObj[k].getLanguage_Link();
                link_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(class_getdemo_resplist_arrayObj[k].getLanguage_Link()));
                        /*Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(class_getdemo_resplist_arrayObj[k].getLanguage_Link()));*/


                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str_link));
                        startActivity(intent);
                        try {
                            //startActivity(appIntent);
                            //startActivity(webIntent);
                        } catch (ActivityNotFoundException ex) {
                            //
                        }
                    }
                });


                forlanguagename_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str_link));
                            startActivity(intent);
                        } catch (ActivityNotFoundException ex) {

                            Toast.makeText(getApplicationContext(), "WS:error:ex", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                //     demo_ll.addView(link_tv);


                nextline_tv.setText("\n");
                //     demo_ll.addView(nextline_tv);


            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.schedule_menu, menu);
        return true;
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


                deleteStateRestTable_B4insertion();
                deleteDistrictRestTable_B4insertion();
                deleteTalukRestTable_B4insertion();
                deleteVillageRestTable_B4insertion();
                deleteYearRestTable_B4insertion();
                deleteSchoolRestTable_B4insertion();
                deleteSandboxRestTable_B4insertion();
                deleteInstituteRestTable_B4insertion();
                deleteLevelRestTable_B4insertion();
                deleteClusterRestTable_B4insertion();

                AlertDialog.Builder dialog = new AlertDialog.Builder(ContactUs_Activity.this);
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
            case android.R.id.home:
                Intent intent = new Intent(ContactUs_Activity.this, Activity_HomeScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();


                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteStateRestTable_B4insertion() {
        SQLiteDatabase db_statelist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_statelist_delete.execSQL("CREATE TABLE IF NOT EXISTS StateListRest(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");
        Cursor cursor = db_statelist_delete.rawQuery("SELECT * FROM StateListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_statelist_delete.delete("StateListRest", null, null);

        }
        db_statelist_delete.close();
    }

    public void deleteDistrictRestTable_B4insertion() {

        SQLiteDatabase db_districtlist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_districtlist_delete.execSQL("CREATE TABLE IF NOT EXISTS DistrictListRest(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db_districtlist_delete.rawQuery("SELECT * FROM DistrictListRest", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_districtlist_delete.delete("DistrictListRest", null, null);

        }
        db_districtlist_delete.close();
    }

    public void deleteTalukRestTable_B4insertion() {

        SQLiteDatabase db_taluklist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_taluklist_delete.execSQL("CREATE TABLE IF NOT EXISTS TalukListRest(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db_taluklist_delete.rawQuery("SELECT * FROM TalukListRest", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_taluklist_delete.delete("TalukListRest", null, null);

        }
        db_taluklist_delete.close();
    }

    public void deleteVillageRestTable_B4insertion() {

        SQLiteDatabase db_villagelist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        // db_villagelist_delete.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        db_villagelist_delete.execSQL("CREATE TABLE IF NOT EXISTS VillageListRest(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        Cursor cursor1 = db_villagelist_delete.rawQuery("SELECT * FROM VillageListRest", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_villagelist_delete.delete("VillageListRest", null, null);

        }
        db_villagelist_delete.close();
    }

    public void deleteYearRestTable_B4insertion() {

        SQLiteDatabase db_yearlist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_yearlist_delete.execSQL("CREATE TABLE IF NOT EXISTS YearListRest(YearID VARCHAR,YearName VARCHAR,Sandbox_ID VARCHAR);");
        Cursor cursor = db_yearlist_delete.rawQuery("SELECT * FROM YearListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_yearlist_delete.delete("YearListRest", null, null);

        }
        db_yearlist_delete.close();
    }

    public void deleteSchoolRestTable_B4insertion() {

        SQLiteDatabase db_Schoollist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_Schoollist_delete.execSQL("CREATE TABLE IF NOT EXISTS SchoolListRest(SchoolName VARCHAR, SchoolID VARCHAR, School_InstituteID VARCHAR);");
        Cursor cursor = db_Schoollist_delete.rawQuery("SELECT * FROM SchoolListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_Schoollist_delete.delete("SchoolListRest", null, null);

        }
        db_Schoollist_delete.close();
    }

    public void deleteSandboxRestTable_B4insertion() {

        SQLiteDatabase db_Sandboxlist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_Sandboxlist_delete.execSQL("CREATE TABLE IF NOT EXISTS SandboxListRest(SandboxName VARCHAR,SandboxID VARCHAR);");
        Cursor cursor = db_Sandboxlist_delete.rawQuery("SELECT * FROM SandboxListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_Sandboxlist_delete.delete("SandboxListRest", null, null);

        }
        db_Sandboxlist_delete.close();
    }

    public void deleteInstituteRestTable_B4insertion() {

        SQLiteDatabase db_Institutelist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_Institutelist_delete.execSQL("CREATE TABLE IF NOT EXISTS InstituteListRest(InstituteName VARCHAR, InstituteID VARCHAR,Inst_AcademicID VARCHAR,Inst_ClusterID VARCHAR);");
        Cursor cursor = db_Institutelist_delete.rawQuery("SELECT * FROM InstituteListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_Institutelist_delete.delete("InstituteListRest", null, null);

        }
        db_Institutelist_delete.close();
    }

    public void deleteLevelRestTable_B4insertion() {

        SQLiteDatabase db_Levellist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_Levellist_delete.execSQL("CREATE TABLE IF NOT EXISTS LevelListRest(LevelName VARCHAR, LevelID VARCHAR, Level_InstituteID VARCHAR,Level_AcademicID VARCHAR,Level_ClusterID VARCHAR,Level_AdmissionFee VARCHAR);");
        Cursor cursor = db_Levellist_delete.rawQuery("SELECT * FROM LevelListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_Levellist_delete.delete("LevelListRest", null, null);

        }
        db_Levellist_delete.close();
    }

    public void deleteClusterRestTable_B4insertion() {

        SQLiteDatabase db_Clusterlist_delete = openOrCreateDatabase("SIV_DB", Context.MODE_PRIVATE, null);
        db_Clusterlist_delete.execSQL("CREATE TABLE IF NOT EXISTS ClusterListRest(ClusterName VARCHAR,ClusterID VARCHAR,Clust_AcademicID VARCHAR, Clust_SandboxID VARCHAR);");
        Cursor cursor = db_Clusterlist_delete.rawQuery("SELECT * FROM ClusterListRest", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_Clusterlist_delete.delete("ClusterListRest", null, null);

        }
        db_Clusterlist_delete.close();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ContactUs_Activity.this, Activity_HomeScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }


}