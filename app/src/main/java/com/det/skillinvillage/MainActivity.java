package com.det.skillinvillage;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/*
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
*/
import com.det.skillinvillage.model.Class_getuserlist;
import com.det.skillinvillage.model.Class_userlist;
import com.det.skillinvillage.model.NormalLogin_List;
import com.det.skillinvillage.model.NormalLogin_Response;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{

    Interface_userservice userService1;
    //commit

    private static final int RC_SIGN_IN = 234;////a constant for detecting the login intent result
    private static final String TAG = "dffarmpond";
    // GoogleSignInClient googlesigninclient_obj;
    FirebaseAuth firebaseauth_obj;
    SignInButton google_signin_bt;
    GoogleSignInClient googlesigninclient_obj;
    GoogleSignInAccount account;


    public final static String COLOR = "#1565C0";


    //GoogleSignInAccount account;

    String str_googletokenid;


    EditText username_et;
    Button techlogin_bt,managerlogin_bt;
    String str_gmailid;

    Context context_obj;

    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String KeyValue_employeename = "KeyValue_employeename";
    public static final String KeyValue_employee_mailid = "KeyValue_employee_mailid";
    //public static final String KeyValue_employeecategory = "KeyValue_employeecategory";
    //  public static final String KeyValue_employeesandbox = "KeyValue_employeesandbox";
    public static final String KeyValue_perdayamount = "KeyValue_perdayamount";
    public static final String KeyValue_employeeRoleName = "KeyValue_employeeRoleName";
    public static final String KeyValue_employeeRoleId = "KeyValue_employeeRoleId";

    SharedPreferences sharedpreferencebook_usercredential_Obj;

    SharedPreferences.Editor editor_obj,editorversion_obj;


    public static final String sharedpreferencebook_User_pastCredential = "sharedpreferencebook_User_pastCredential";
    public static final String KeyValue_pastUser_ID = "KeyValue_pastUser_ID";
    SharedPreferences sharedpreferencebook_user_pastCredential_obj;

    String str_token;
    String str_tokenfromprefrence;



    public static final String sharedpreferencebook_techversion = "sharedpreferencebook_techversion";
    public static final String KeyValue_techmode = "KeyValue_techmode";
    SharedPreferences sharedpreferencebook_techversion_Obj;





    public static final String sharedpreferenc_username = "googlelogin_name";
    public static final String Key_username = "name_googlelogin";
    SharedPreferences sharedpref_userimage_Obj;
    public static final String sharedpreferenc_userimage = "googlelogin_img";
    public static final String key_userimage = "profileimg_googlelogin";
    SharedPreferences sharedpref_username_Obj;

    String str_loginusername,str_profileimage;

    TextView version_tv;

    String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.INTERNET
    };
    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.



    NormalLogin_List[] arrayObj_Class_yeardetails;

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;

    String Employee_Role,str_emailid_fordeveloper;

    Spinner userlist_sp;
    int int_verclickcount;

    String str_enabletechmode,str_loginuserid="";

    public static final String key_loginuserid = "login_userid";
    public static final String sharedpreferenc_loginuserid = "userid";
    SharedPreferences sharedpref_loginuserid_Obj;

    Button normallogin_bt;
    EditText Email_ET,Password_ET;
    TextInputLayout textInputEmail,textInputPassword;
    String str_gmailpwd="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        google_signin_bt =(SignInButton)findViewById(R.id.google_signin_bt);
        normallogin_bt=(Button)findViewById(R.id.normallogin_bt);
        google_signin_bt.setColorScheme(SignInButton.COLOR_DARK);
        setGooglePlusButtonText(google_signin_bt,"  Sign in with DF mail  ");


        userService1 = Class_ApiUtils.getUserService();


        // sharedpreferencebookRest_usercredential_Obj=this.getSharedPreferences(sharedpreferencebook_User_Credential, Context.MODE_PRIVATE);
        sharedpreferencebook_usercredential_Obj=this.getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);

        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        Employee_Role=sharedpreferencebook_usercredential_Obj.getString(KeyValue_employeeRoleName, "").trim();
      //  sharedpref_loginuserid_Obj = getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);

        str_loginuserid = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();

        sharedpref_username_Obj=getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
        str_loginusername = sharedpref_username_Obj.getString(Key_username, "").trim();
        sharedpref_userimage_Obj=getSharedPreferences(sharedpreferenc_userimage, Context.MODE_PRIVATE);
        str_profileimage = sharedpref_userimage_Obj.getString(key_userimage, "").trim();


        Log.e("tag","Employee_Role="+Employee_Role);

        sharedpreferencebook_techversion_Obj=this.getSharedPreferences(sharedpreferencebook_techversion, Context.MODE_PRIVATE);
        sharedpreferencebook_techversion_Obj=getSharedPreferences(sharedpreferencebook_techversion, Context.MODE_PRIVATE);
        str_enabletechmode=sharedpreferencebook_techversion_Obj.getString(KeyValue_techmode, "").trim();

        version_tv=(TextView)findViewById(R.id.version_tv);
        techlogin_bt =(Button)findViewById(R.id.techlogin_bt);
        Email_ET=(EditText)findViewById(R.id.editTextEmail);
        Password_ET=(EditText)findViewById(R.id.editTextPassword);
        userlist_sp=(Spinner)findViewById(R.id.userlist_sp);
        textInputEmail=(TextInputLayout)findViewById(R.id.textInputEmail);
        textInputPassword=(TextInputLayout)findViewById(R.id.textInputPassword);


        context_obj=this;
        int_verclickcount=0;



        Log.e("tag","techmode="+str_enabletechmode);

        if(str_enabletechmode.trim().length()>0)
        {
            if(str_enabletechmode.equalsIgnoreCase("yes"))
            {

                AsyncTask_Get_UserList();
                /*google_signin_bt.setVisibility(View.INVISIBLE);
                version_tv.setText("DF Agri For Technology Team Ver 1.2");
                userlist_sp.setVisibility(View.VISIBLE);
                techlogin_bt.setVisibility(View.VISIBLE);*/
            }else{
                if(str_enabletechmode.equalsIgnoreCase("no"))
                {
                    google_signin_bt.setVisibility(View.VISIBLE);
                    //  version_tv.setText("DF Agri Test Ver 0.45");
                    userlist_sp.setVisibility(View.GONE);
                    techlogin_bt.setVisibility(View.GONE);
                }
            }
        }else{
            str_enabletechmode="no";
        }


        if(Class_SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {
            // call Login Activity
        }
        else
        {
            Log.e("sharedvalue",Class_SaveSharedPreference.getUserName(MainActivity.this).toString());

            if(Employee_Role.equalsIgnoreCase("Trainer")) {
                Intent i = new Intent(MainActivity.this, Activity_HomeScreen.class);
                startActivity(i);
                finish();
            }

            
            if(Employee_Role.equalsIgnoreCase("Cluster Head")) {
                Intent i = new Intent(MainActivity.this, Activity_HomeScreen.class);
                startActivity(i);
                finish();
          //      Toast.makeText(getApplicationContext(), "Only Trainers are allowed to login ", Toast.LENGTH_SHORT).show();

            }

            if(Employee_Role.equalsIgnoreCase("Administrator")) {
//                Intent i = new Intent(MainActivity.this, Activity_HomeScreen.class);
//                startActivity(i);
//                finish();
                Toast.makeText(getApplicationContext(), "Only Trainers are allowed to login ", Toast.LENGTH_SHORT).show();

            }
            // Stay at the current activity.
        }


        //Google Sign initializing
        firebaseauth_obj = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googlesigninclient_obj = GoogleSignIn.getClient(this, gso);
        //Google Sign initializing






        // Signout function

        Intent myIntent = getIntent();

        if(myIntent!=null)
        {

            String logout="no";
            logout = myIntent.getStringExtra("Key_Logout");
            if(logout!=null && (logout.equalsIgnoreCase("yes")))
            {
                signOut();
            }
        }

        // Signout function


        google_signin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                internetDectector = new Class_InternetDectector(getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();

                if (checkPermissions())
                {
                    if (isInternetPresent)
                    {
                        google_sign();
                    }else{
                        Toast.makeText(MainActivity.this, "Kindly connect to internet", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        normallogin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_gmailid=Email_ET.getText().toString();
                str_gmailpwd=Password_ET.getText().toString();
                internetDectector = new Class_InternetDectector(getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();

                if (checkPermissions())
                {
                    if (isInternetPresent)
                    {
                        if(Email_ET.getText().toString().length()==0){
                            Toast.makeText(MainActivity.this, "Kindly enter your Email-ID", Toast.LENGTH_SHORT).show();

                        }else if(Password_ET.getText().toString().length()==0){
                        Toast.makeText(MainActivity.this, "Kindly enter your password", Toast.LENGTH_SHORT).show();

                    }else {
                            AsyncTask_normalloginverify();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Kindly connect to internet", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        userlist_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                str_emailid_fordeveloper = userlist_sp.getSelectedItem().toString();

                //Toast.makeText(getApplicationContext(),""+str_emailid_fordeveloper,Toast.LENGTH_SHORT).show();
                str_gmailid=str_emailid_fordeveloper;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        //normal login comment while releasing apk
        techlogin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (checkPermissions())
                {
                    //  permissions  granted.

                    AsyncTask_loginverify();
                }



            }
        });


        //normal login comment while releasing apk







        version_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int_verclickcount=int_verclickcount+1;

                //DF Agri For Technology Team Ver 1.1
                if(int_verclickcount>6)
                {
                    internetDectector = new Class_InternetDectector(getApplicationContext());
                    isInternetPresent = internetDectector.isConnectingToInternet();
                    if(isInternetPresent)
                    {
                        if((checkPermissions()))
                        {


                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setCancelable(false);
                            dialog.setTitle(R.string.alert);
                            if(str_enabletechmode.equalsIgnoreCase("no"))
                            {dialog.setMessage("Are you sure want to Enable Technologly Team Version?");}
                            else{
                                dialog.setMessage("Are you sure want to Enable User Version?");
                            }


                            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    if(str_enabletechmode.equalsIgnoreCase("no")) {
                                        dialog.dismiss();
                                        version_alertdialog();
                                    }else{
                                        google_signin_bt.setVisibility(View.VISIBLE);
                                        normallogin_bt.setVisibility(View.VISIBLE);
                                        Email_ET.setVisibility(View.VISIBLE);
                                        Password_ET.setVisibility(View.VISIBLE);
                                        textInputEmail.setVisibility(View.VISIBLE);
                                        textInputPassword.setVisibility(View.VISIBLE);

                                        version_tv.setText("DF SIV Version 3.1");
                                        userlist_sp.setVisibility(View.GONE);
                                        techlogin_bt.setVisibility(View.GONE);

                                        editorversion_obj = sharedpreferencebook_techversion_Obj.edit();
                                        editorversion_obj.putString(KeyValue_techmode,"no");
                                        editorversion_obj.apply();
                                        dialog.dismiss();
                                    }


                                }
                            })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Action for "Cancel".
                                            int_verclickcount=0;
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


                        }
                    }

                }

            }
        });




    }// end of onCreate()





    private void google_sign() {
        //getting the google signin intent
        Intent signInIntent = googlesigninclient_obj.getSignInIntent();
        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {
            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                account = task.getResult(ApiException.class);
                //authenticating with firebase
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                Toast.makeText(MainActivity.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }




    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct)
    {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        str_gmailid=acct.getEmail().toString();

        //Now using firebase we are signing in the user here
        firebaseauth_obj.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseauth_obj.getCurrentUser();


                            SharedPreferences.Editor myprefs_Username = sharedpref_username_Obj.edit();
                            myprefs_Username.putString(Key_username, acct.getDisplayName());
                            myprefs_Username.apply();
                            Class_SaveSharedPreference.setUserName(MainActivity.this,account.getDisplayName());
                            Class_SaveSharedPreference.setUserMailID(MainActivity.this,str_gmailid);

                            SharedPreferences.Editor myprefs_UserImg = sharedpref_userimage_Obj.edit();
                            myprefs_UserImg.putString(key_userimage, String.valueOf(acct.getPhotoUrl()));
                            myprefs_UserImg.apply();



//////////////////////////////////////////


                            Toast.makeText(MainActivity.this, "User Signed In:"+str_gmailid, Toast.LENGTH_SHORT).show();



                            str_enabletechmode="no";
                            AsyncTask_loginverify();


                            /*try {
                                postRequest();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/

                        } else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }










    private void AsyncTask_loginverify()
    {

        final ProgressDialog login_progressDoalog;
        login_progressDoalog = new ProgressDialog(MainActivity.this);
        login_progressDoalog.setMessage("Fetching the crendentials....");
        login_progressDoalog.setTitle("Please wait....");
        login_progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        login_progressDoalog.show();

        String str_appversion="1.2";
        retrofit2.Call call = userService1.getValidateLoginPostNew(str_gmailid);

        call.enqueue(new Callback()
        {
            @Override
            public void onResponse(retrofit2.Call call, Response response) {

                // Toast.makeText(MainActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();

                Log.e("response",response.body().toString());
                NormalLogin_Response user_object = new NormalLogin_Response();
                user_object = (NormalLogin_Response) response.body();
                // String x=response.body().toString();

                Log.e("response userdata:", "" + new Gson().toJson(response));

                if (user_object.getStatus().toString().equalsIgnoreCase("true"))
                {
                   // Log.e("response userdata:",);
                    if(user_object.getLst1().get(0).getUserRole().get(0).getRoleName().equalsIgnoreCase("Trainer")) {
                        Intent i = new Intent(MainActivity.this, Activity_HomeScreen.class);
                        startActivity(i);
                        finish();
                    }
                    if(user_object.getLst1().get(0).getUserRole().get(0).getRoleName().equalsIgnoreCase("Cluster Head")) {
//                Intent i = new Intent(MainActivity.this, Activity_HomeScreen.class);
//                startActivity(i);
//                finish();
                        Toast.makeText(getApplicationContext(), "Only Trainers are allowed to login ", Toast.LENGTH_SHORT).show();

                    }

                    if(user_object.getLst1().get(0).getUserRole().get(0).getRoleName().equalsIgnoreCase("Administrator")) {
//                Intent i = new Intent(MainActivity.this, Activity_HomeScreen.class);
//                startActivity(i);
//                finish();
                        Toast.makeText(getApplicationContext(), "Only Trainers are allowed to login ", Toast.LENGTH_SHORT).show();

                    }
//                    Intent intent = new Intent(MainActivity.this, Activity_HomeScreen.class);
//                    startActivity(intent);



                    Log.e("response", user_object.getStatus().toString());

                   // Toast.makeText(MainActivity.this, "" + user_object.getStatus().toString(), Toast.LENGTH_LONG).show();

                    Toast.makeText(MainActivity.this, "" + user_object.getLst1().get(0).getUserEmail(), Toast.LENGTH_LONG).show();

                    Class_SaveSharedPreference.setUserMailID(MainActivity.this, user_object.getLst1().get(0).getUserID());
                    Class_SaveSharedPreference.setUserName(MainActivity.this, user_object.getLst1().get(0).getUserName());
                    editor_obj = sharedpreferencebook_usercredential_Obj.edit();

                    for(int i=0;i<user_object.getLst1().size();i++){
                        Log.e("RoleName", user_object.getLst1().get(i).getUserRole().get(i).getRoleName().toString());
                        editor_obj.putString(KeyValue_employeeRoleName, user_object.getLst1().get(i).getUserRole().get(i).getRoleName());
                        editor_obj.putString(KeyValue_employeeRoleId, user_object.getLst1().get(i).getUserRole().get(i).getRoleID());
                        editor_obj.putString(key_loginuserid, user_object.getLst1().get(i).getUserRole().get(i).getRoleID());
                        //Employee_Role=user_object.getLst1().get(i).getUserRole().get(i).getRoleName();
                    }

                    Class_SaveSharedPreference.setPREF_RoleName(MainActivity.this, user_object.getLst1().get(0).getUserRole().get(0).getRoleName());
                    editor_obj.putString(KeyValue_employeeid, user_object.getLst1().get(0).getUserID());
                    editor_obj.putString(KeyValue_employeename, user_object.getLst1().get(0).getUserName());
                    editor_obj.putString(KeyValue_employee_mailid, user_object.getLst1().get(0).getUserEmail());
                    editor_obj.putString(key_loginuserid, user_object.getLst1().get(0).getUserID());
                    //  editor_obj.putString(KeyValue_employeesandbox, user_object.getLst1().get(0).getUserState());
                    //  editor_obj.putString(KeyValue_perdayamount, user_object.getLst1().get(0).getUserStateAmount());

//                    SharedPreferences.Editor myprefs_loginuserid = sharedpref_loginuserid_Obj.edit();
//            myprefs_loginuserid.putString(key_loginuserid, user_object.getLst1().get(0).getUserEmail());
//            myprefs_loginuserid.apply();

//            SharedPreferences.Editor myprefs_schedulerid = sharedpref_schedulerid_Obj.edit();
//            myprefs_schedulerid.putString(key_schedulerid, Schedule_ID);
//            myprefs_schedulerid.apply();

                    editor_obj.apply();

                    login_progressDoalog.dismiss();

                    Log.e("tag", "mailid==" + Class_SaveSharedPreference.getUsermailID(MainActivity.this));


                   /* if(user_object.getLst1().get(0).getUserRole().equalsIgnoreCase("Field Facilitator"))
                    {
                        Intent i = new Intent(MainActivity.this, Activity_HomeScreen.class);
                        startActivity(i);
                    }*/
                   /* else if(user_object.getLst().get(0).getUserRole().equalsIgnoreCase("Cluster Head")){
                        Intent i = new Intent(MainActivity.this, ClusterHomeActivity.class);
                        startActivity(i);
                    }
                    else if(user_object.getLst().get(0).getUserRole().equalsIgnoreCase("Admin")){
                        Intent i = new Intent(MainActivity.this, ClusterHomeActivity.class);
                        startActivity(i);
                    }else if(user_object.getLst().get(0).getUserRole().trim().equalsIgnoreCase("Cluster Head_Field Facilitator")){
                        Log.e("clusterhead","clusterhead");
                        Intent i = new Intent(MainActivity.this, ClusterHomeActivity.class);
                        startActivity(i);
                        finish();
                    }*/
                   /* else if(user_object.getLst().get(0).getUserRole().equalsIgnoreCase("Admin_Cluster Head_Field Facilitator"))
                    {
                        Intent i = new Intent(MainActivity.this, ClusterHomeActivity.class);
                        startActivity(i);
                }*/
                /*    else{
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setCancelable(false);
                        dialog.setTitle("DF Agri");
                        dialog.setMessage(" You are not authorized to access. \n Please contact to Tech Support \n Mob-No:9606947789");
                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                Class_SaveSharedPreference.setUserName(MainActivity.this, "");
                                   *//* Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    i.putExtra("Key_Logout", "yes");
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);*//*
                                signOut_InvalidUser();
                            }
                        });
                        final AlertDialog alert = dialog.create();
                        alert.setOnShowListener( new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface arg0) {
                                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                            }
                        });
                        alert.show();
                    }*/

                } else {
                    Toast.makeText(MainActivity.this, user_object.getMessage().toString(), Toast.LENGTH_LONG).show();
                    login_progressDoalog.dismiss();
                    signOut_InvalidUser();
                }

            }

            @Override
            public void onFailure(Call call, Throwable t)
            {
                login_progressDoalog.dismiss();
                Log.e("WS","error: "+t.getMessage());
                Toast.makeText(MainActivity.this, "WS:"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void AsyncTask_normalloginverify()
    {

        final ProgressDialog login_progressDoalog;
        login_progressDoalog = new ProgressDialog(MainActivity.this);
        login_progressDoalog.setMessage("Fetching the crendentials....");
        login_progressDoalog.setTitle("Please wait....");
        login_progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        login_progressDoalog.show();

        String str_appversion="1.2";
        retrofit2.Call call = userService1.getNormalLoginPost(str_gmailid,str_gmailpwd);

        call.enqueue(new Callback()
        {
            @Override
            public void onResponse(retrofit2.Call call, Response response) {

                // Toast.makeText(MainActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();

                Log.e("response",response.body().toString());
                NormalLogin_Response user_object = new NormalLogin_Response();
                user_object = (NormalLogin_Response) response.body();
                // String x=response.body().toString();

                Log.e("response userdata:", "" + new Gson().toJson(response));

                if (user_object.getStatus().toString().equalsIgnoreCase("true"))
                {

//                    Intent intent = new Intent(MainActivity.this, Activity_HomeScreen.class);
//                    startActivity(intent);

                    if(user_object.getLst1().get(0).getUserRole().get(0).getRoleName().equalsIgnoreCase("Trainer")) {
                        Intent i = new Intent(MainActivity.this, Activity_HomeScreen.class);
                        startActivity(i);
                        finish();
                    }
                    if(user_object.getLst1().get(0).getUserRole().get(0).getRoleName().equalsIgnoreCase("Cluster Head")) {
//                Intent i = new Intent(MainActivity.this, Activity_HomeScreen.class);
//                startActivity(i);
//                finish();
                        Toast.makeText(getApplicationContext(), "Only Trainers are allowed to login ", Toast.LENGTH_SHORT).show();

                    }

                    if(user_object.getLst1().get(0).getUserRole().get(0).getRoleName().equalsIgnoreCase("Administrator")) {
//                Intent i = new Intent(MainActivity.this, Activity_HomeScreen.class);
//                startActivity(i);
//                finish();
                        Toast.makeText(getApplicationContext(), "Only Trainers are allowed to login ", Toast.LENGTH_SHORT).show();

                    }
                    Log.e("response", user_object.getStatus().toString());


                    Toast.makeText(MainActivity.this, "" + user_object.getLst1().get(0).getUserEmail(), Toast.LENGTH_LONG).show();

                    Class_SaveSharedPreference.setUserMailID(MainActivity.this, user_object.getLst1().get(0).getUserID());
                    Class_SaveSharedPreference.setUserName(MainActivity.this, user_object.getLst1().get(0).getUserName());
                    editor_obj = sharedpreferencebook_usercredential_Obj.edit();

                    for(int i=0;i<user_object.getLst1().size();i++){
                        Log.e("RoleName", user_object.getLst1().get(i).getUserRole().get(i).getRoleName().toString());
                        editor_obj.putString(KeyValue_employeeRoleName, user_object.getLst1().get(i).getUserRole().get(i).getRoleName());
                        editor_obj.putString(KeyValue_employeeRoleId, user_object.getLst1().get(i).getUserRole().get(i).getRoleID());
                        editor_obj.putString(key_loginuserid, user_object.getLst1().get(i).getUserRole().get(i).getRoleID());

                    }

                    Class_SaveSharedPreference.setPREF_RoleName(MainActivity.this, user_object.getLst1().get(0).getUserRole().get(0).getRoleName());
                    editor_obj.putString(KeyValue_employeeid, user_object.getLst1().get(0).getUserID());
                    editor_obj.putString(KeyValue_employeename, user_object.getLst1().get(0).getUserName());
                    editor_obj.putString(KeyValue_employee_mailid, user_object.getLst1().get(0).getUserEmail());
                    editor_obj.putString(key_loginuserid, user_object.getLst1().get(0).getUserID());
                    //  editor_obj.putString(KeyValue_employeesandbox, user_object.getLst1().get(0).getUserState());
                    //  editor_obj.putString(KeyValue_perdayamount, user_object.getLst1().get(0).getUserStateAmount());

                    editor_obj.apply();

                    login_progressDoalog.dismiss();

                    Log.e("tag", "mailid==" + Class_SaveSharedPreference.getUsermailID(MainActivity.this));


                   /* if(user_object.getLst1().get(0).getUserRole().equalsIgnoreCase("Field Facilitator"))
                    {
                        Intent i = new Intent(MainActivity.this, Activity_HomeScreen.class);
                        startActivity(i);
                    }*/
                   /* else if(user_object.getLst().get(0).getUserRole().equalsIgnoreCase("Cluster Head")){
                        Intent i = new Intent(MainActivity.this, ClusterHomeActivity.class);
                        startActivity(i);
                    }
                    else if(user_object.getLst().get(0).getUserRole().equalsIgnoreCase("Admin")){
                        Intent i = new Intent(MainActivity.this, ClusterHomeActivity.class);
                        startActivity(i);
                    }else if(user_object.getLst().get(0).getUserRole().trim().equalsIgnoreCase("Cluster Head_Field Facilitator")){
                        Log.e("clusterhead","clusterhead");
                        Intent i = new Intent(MainActivity.this, ClusterHomeActivity.class);
                        startActivity(i);
                        finish();
                    }*/
                   /* else if(user_object.getLst().get(0).getUserRole().equalsIgnoreCase("Admin_Cluster Head_Field Facilitator"))
                    {
                        Intent i = new Intent(MainActivity.this, ClusterHomeActivity.class);
                        startActivity(i);
                }*/
                /*    else{
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setCancelable(false);
                        dialog.setTitle("DF Agri");
                        dialog.setMessage(" You are not authorized to access. \n Please contact to Tech Support \n Mob-No:9606947789");
                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                Class_SaveSharedPreference.setUserName(MainActivity.this, "");
                                   *//* Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    i.putExtra("Key_Logout", "yes");
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);*//*
                                signOut_InvalidUser();
                            }
                        });
                        final AlertDialog alert = dialog.create();
                        alert.setOnShowListener( new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface arg0) {
                                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                            }
                        });
                        alert.show();
                    }*/

                } else {
                    Toast.makeText(MainActivity.this, user_object.getMessage().toString(), Toast.LENGTH_LONG).show();

                    login_progressDoalog.dismiss();
                    signOut_InvalidUser();
                }

            }

            @Override
            public void onFailure(Call call, Throwable t)
            {
                login_progressDoalog.dismiss();
                Log.e("WS","error: "+t.getMessage());
                Toast.makeText(MainActivity.this, "WS:"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }




    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText)
    {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                // tv.setBackgroundColor(Color.CYAN);
//                tv.setBackgroundDrawable(
//                        new ColorDrawable(Color.parseColor(COLOR)));
//                tv.setTextColor(Color.WHITE);
              //  tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/laouibold.ttf"));
                return;
            }
        }
    }




    private void signOut_InvalidUser()
    {
        googlesigninclient_obj.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(MainActivity.this,"Sigined Out: InValid User",Toast.LENGTH_SHORT).show();
                    }
                });
    }





    private void signOut() {
        googlesigninclient_obj.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(MainActivity.this,"Sigined Out Successfully",Toast.LENGTH_SHORT).show();
                    }
                });
    }








    public void AsyncTask_Get_UserList() {

        final ProgressDialog login_progressDoalog;
        login_progressDoalog = new ProgressDialog(MainActivity.this);
        login_progressDoalog.setMessage("Fetching the UserList....");
        login_progressDoalog.setTitle("Please wait....");
        login_progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        login_progressDoalog.show();


        retrofit2.Call call = userService1.get_userlist();

        call.enqueue(new Callback<Class_getuserlist>() {
            @Override
            public void onResponse(Call<Class_getuserlist> call, Response<Class_getuserlist> response)
            {

                if(response.isSuccessful())
                {


                    Class_getuserlist getuserlist_obj = response.body();
                    List<Class_userlist> userlist_obj=response.body().getListUser();

                    Log.e("userlist", String.valueOf(userlist_obj.size()));

                    Class_userlist[] userlist_arrayObj= new Class_userlist[userlist_obj.size()];




                    for(int i=0;i<userlist_obj.size();i++)
                    {
                        Class_userlist inner_userlst= new Class_userlist();
                        inner_userlst.setUserID(getuserlist_obj.getListUser().get(i).getUserID());
                        inner_userlst.setUserEmail(getuserlist_obj.getListUser().get(i).getUserEmail());

                        userlist_arrayObj[i]=inner_userlst;

                    }

                    ArrayAdapter dataAdapter = new ArrayAdapter(MainActivity.this, R.layout.spinnercenterstyle, userlist_arrayObj);
                    dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
                    userlist_sp.setAdapter(dataAdapter);



                    //Log.e("user",userlist_arrayObj[0].getUser_name().toString());


                    google_signin_bt.setVisibility(View.GONE);
                    version_tv.setText("DF SIV For Technology Team Ver 3.1");
                    userlist_sp.setVisibility(View.VISIBLE);
                    techlogin_bt.setVisibility(View.VISIBLE);
                    normallogin_bt.setVisibility(View.GONE);
                    Email_ET.setVisibility(View.GONE);
                    Password_ET.setVisibility(View.GONE);
                    textInputEmail.setVisibility(View.GONE);
                    textInputPassword.setVisibility(View.GONE);


                    editorversion_obj = sharedpreferencebook_techversion_Obj.edit();
                    editorversion_obj.putString(KeyValue_techmode,"yes");
                    editorversion_obj.commit();
                    str_enabletechmode="yes";
                    login_progressDoalog.dismiss();


                }




            }

            @Override
            public void onFailure(Call call, Throwable t) {

                Log.e("TAG", "onFailure: " + t.toString());

                Log.e("tag", "Error:" + t.getMessage());
                Toast.makeText(MainActivity.this, "WS:Error:UserList" + t.getMessage(), Toast.LENGTH_SHORT).show();

                editorversion_obj = sharedpreferencebook_techversion_Obj.edit();
                editorversion_obj.putString(KeyValue_techmode,"no");
                editorversion_obj.commit();
                str_enabletechmode="no";
                login_progressDoalog.dismiss();
            }
        });


    }

    private void version_alertdialog()
    {


        LayoutInflater li = LayoutInflater.from(context_obj);
        View promptsView = li.inflate(R.layout.techteam_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context_obj);

        alertDialogBuilder.setView(promptsView);

        final EditText techcredentialdialog_et = (EditText) promptsView
                .findViewById(R.id.techcredentialdialog_et);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id)
                            {

                                if(techcredentialdialog_et.getText().toString().trim().equalsIgnoreCase("2468"))
                                {
                                    //Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_SHORT).show();

                                    AsyncTask_Get_UserList();

                                }else{
                                    Toast.makeText(getApplicationContext(),"Wrong",Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                //dialog.cancel();
                                dialog.dismiss();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }


    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
       // startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }


}// end of class


























/////////////////Shivaleela old code below(replaced with new code above on 12th NOv 2020////////////////////////////

//package com.det.skillinvillage;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.graphics.Point;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.telephony.TelephonyManager;
//import android.util.Log;
//import android.view.Display;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import com.det.skillinvillage.adapter.Class_SandBoxDetails;
//import com.det.skillinvillage.util.UserInfo;
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.GoogleAuthProvider;
//import com.google.firebase.iid.FirebaseInstanceId;
//
//import org.ksoap2.SoapEnvelope;
//import org.ksoap2.serialization.SoapObject;
//import org.ksoap2.serialization.SoapPrimitive;
//import org.ksoap2.serialization.SoapSerializationEnvelope;
//import org.ksoap2.transport.HttpTransportSE;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import java.util.Vector;
//
//
//public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
//
//    public final static String COLOR = "#1565C0";
//    private static final String TAG = "MainActivity";
//    private SignInButton signInButton;
//    private GoogleApiClient googleApiClient;
//    private static final int RC_SIGN_IN = 1;
//    String name, email;
//    String idToken;
//    private FirebaseAuth firebaseAuth;
//    private FirebaseAuth.AuthStateListener authStateListener;
//
//    SharedPreferences sharedpref_validMailID_Obj;
//    public static final String sharedpreferenc_mailbook = "sharedpreference_mailbook";
//    SharedPreferences sharedpreferencebook_centerid_Obj;
//    public static final String NameKey_mailID = "namekey_validmailID";
//    public static String gmailid = null;
//
//    Boolean isInternetPresent = false;
//    Class_InternetDectector internetDectector;
//
//    String str_gmail_ID, str_username, str_pwd, login_userStatus = "", login_userid="", login_userEmail = "";
//    String Schedule_Status, Schedule_ID, Lavel_ID, Schedule_Date, End_Time, Start_Time, Schedule_Session, Subject_Name, Leason_Name, Lavel_Name, Cluster_Name, Institute_Name;
//    ArrayList<UserInfo> arrayList = new ArrayList<UserInfo>();
//    UserInfo[] userInfosarr;
//
//    String[] permissions = new String[]{
//            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            android.Manifest.permission.ACCESS_COARSE_LOCATION,
//            android.Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.CAMERA};
//    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.
//    Button nextscreen_BT;
//
//    SharedPreferences sharedpref_username_Obj;
//    public static final String sharedpreferenc_username = "googlelogin_name";
//    public static final String Key_username = "name_googlelogin";
//
//
//    SharedPreferences sharedpref_userimage_Obj;
//    public static final String sharedpreferenc_userimage = "googlelogin_img";
//    public static final String key_userimage = "profileimg_googlelogin";
//
//    SharedPreferences sharedpref_loginuserid_Obj;
//    public static final String sharedpreferenc_loginuserid = "userid";
//    public static final String key_loginuserid = "login_userid";
//
//    SharedPreferences sharedpref_schedulerid_Obj;
//    public static final String sharedpreferenc_schedulerid = "scheduleId";
//    public static final String key_schedulerid = "scheduleId";
//
//
//    String str_profileimage, str_loginusername, str_loginuserid="", str_schedulerid;
//    String str_versioncode;
//    TextView version_tv;
//    int versionCodes;
//    String versiontext = "Version ";
//    String str_appversion_status;
//    boolean versionval;
//    String login_count_Status;
//
//    String simOperatorName="",tmDevice="",mobileNumber="",tmSerial="",androidId="",
//            deviceId="",deviceModelName="",deviceUSER="",devicePRODUCT="",
//            deviceHARDWARE="",deviceBRAND="",myVersion="",sdkver="",regId="";
//    int sdkVersion,Measuredwidth,Measuredheight;
//    TelephonyManager tm1;
//
//    Spinner UserList_SP;
//    Class_UserList[]  arrayObj_Class_sandboxDetails;
//    Class_UserList obj_Class_Userlist;
//    String selected_useremailID="",sp_user_ID="";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_main);
//        version_tv = findViewById(R.id.version_TV);
//        UserList_SP=(Spinner)findViewById(R.id.UserList_SP);
//
//        try {
//            versionCodes = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
//
//        } catch (PackageManager.NameNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        str_versioncode = Integer.toString(versionCodes);
//        // version_tv.setText(versiontext+str_versioncode);
//
//
//
////        // Fetch Device info
////
////       /* final TelephonyManager tm = (TelephonyManager) getBaseContext()
////                .getSystemService(Context.TELEPHONY_SERVICE);*/
////
////        tm1 = (TelephonyManager) getBaseContext()
////                .getSystemService(Context.TELEPHONY_SERVICE);
////
////        //   final String tmDevice, tmSerial, androidId;
////        String NetworkType;
////        //TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
////        simOperatorName = tm1.getSimOperatorName();
////        Log.v("Operator", "" + simOperatorName);
////        NetworkType = "GPRS";
////
////
////        int simSpeed = tm1.getNetworkType();
////        if (simSpeed == 1)
////            NetworkType = "Gprs";
////        else if (simSpeed == 4)
////            NetworkType = "Edge";
////        else if (simSpeed == 8)
////            NetworkType = "HSDPA";
////        else if (simSpeed == 13)
////            NetworkType = "LTE";
////        else if (simSpeed == 3)
////            NetworkType = "UMTS";
////        else
////            NetworkType = "Unknown";
////
////        Log.v("SIM_INTERNET_SPEED", "" + NetworkType);
////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
////            // TODO: Consider calling
////            //    ActivityCompat#requestPermissions
////            // here to request the missing permissions, and then overriding
////            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////            //                                          int[] grantResults)
////            // to handle the case where the user grants the permission. See the documentation
////            // for ActivityCompat#requestPermissions for more details.
////            return;
////        }
////        tmDevice = "" + tm1.getDeviceId();
////        Log.v("DeviceIMEI", "" + tmDevice);
////        mobileNumber = "" + tm1.getLine1Number();
////        Log.v("getLine1Number value", "" + mobileNumber);
////
////        String mobileNumber1 = "" + tm1.getPhoneType();
////        Log.v("getPhoneType value", "" + mobileNumber1);
////        tmSerial = "" + tm1.getSimSerialNumber();
////        //  Log.v("GSM devices Serial Number[simcard] ", "" + tmSerial);
////        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),
////                android.provider.Settings.Secure.ANDROID_ID);
////        Log.v("androidId CDMA devices", "" + androidId);
////        UUID deviceUuid = new UUID(androidId.hashCode(),
////                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
////        deviceId = deviceUuid.toString();
////        //  Log.v("deviceIdUUID universally unique identifier", "" + deviceId);
////
////
////        deviceModelName = android.os.Build.MODEL;
////        Log.v("Model Name", "" + deviceModelName);
////        deviceUSER = android.os.Build.USER;
////        Log.v("Name USER", "" + deviceUSER);
////        devicePRODUCT = android.os.Build.PRODUCT;
////        Log.v("PRODUCT", "" + devicePRODUCT);
////        deviceHARDWARE = android.os.Build.HARDWARE;
////        Log.v("HARDWARE", "" + deviceHARDWARE);
////        deviceBRAND = android.os.Build.BRAND;
////        Log.v("BRAND", "" + deviceBRAND);
////        myVersion = android.os.Build.VERSION.RELEASE;
////        Log.v("VERSION.RELEASE", "" + myVersion);
////        sdkVersion = android.os.Build.VERSION.SDK_INT;
////        Log.v("VERSION.SDK_INT", "" + sdkVersion);
////        sdkver = Integer.toString(sdkVersion);
////        // Get display details
////
////        Measuredwidth = 0;
////        Measuredheight = 0;
////        Point size = new Point();
////        WindowManager w = getWindowManager();
////
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
////            //   w.getDefaultDisplay().getSize(size);
////            Measuredwidth = w.getDefaultDisplay().getWidth();//size.x;
////            Measuredheight = w.getDefaultDisplay().getHeight();//size.y;
////        } else {
////            Display d = w.getDefaultDisplay();
////            Measuredwidth = d.getWidth();
////            Measuredheight = d.getHeight();
////        }
////
////        Log.v("SCREEN_Width", "" + Measuredwidth);
////        Log.v("SCREEN_Height", "" + Measuredheight);
////
////
////        regId = FirebaseInstanceId.getInstance().getToken();
////
////
////
////        Log.e("regId_DeviceID", "" + regId);
////
/////*<username>string</username>
////      <DeviceId>string</DeviceId>
////      <OSVersion>string</OSVersion>
////      <Manufacturer>string</Manufacturer>
////      <ModelNo>string</ModelNo>
////      <SDKVersion>string</SDKVersion>
////      <DeviceSrlNo>string</DeviceSrlNo>
////      <ServiceProvider>string</ServiceProvider>
////      <SIMSrlNo>string</SIMSrlNo>
////      <DeviceWidth>string</DeviceWidth>
////      <DeviceHeight>string</DeviceHeight>
////      <AppVersion>string</AppVersion>*/
////
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        nextscreen_BT = (Button) findViewById(R.id.nextscreen_BT);
////        if(!login_userEmail.equals("")){
////            Intent i=new Intent(MainActivity.this,Activity_HomeScreen.class);
////            startActivity(i);
////            finish();
////        }
//
//        if (Class_Class_SaveSharedPreference.getUserName(MainActivity.this).length() == 0) {
//            // call Login Activity
//        } else {
//            internetDectector = new Class_InternetDectector(getApplicationContext());
//            isInternetPresent = internetDectector.isConnectingToInternet();
//
//            if (isInternetPresent) {
//                AsyncCallForceUpdate_new task1 = new AsyncCallForceUpdate_new(MainActivity.this);
//                task1.execute();
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
//
//            }
//
///*
//                    Intent i=new Intent(MainActivity.this,Activity_HomeScreen.class);
//                    startActivity(i);
//                    finish();
//
// */
//
//            // Stay at the current activity.
//        }
//
//
//        sharedpref_validMailID_Obj = getSharedPreferences(sharedpreferenc_mailbook, Context.MODE_PRIVATE);
////        sharedpreferencebook_centerid_Obj=getSharedPreferences(sharedpreferencebook_centerid, Context.MODE_PRIVATE);
//        sharedpref_validMailID_Obj.getString(NameKey_mailID, "").trim();
//        gmailid = sharedpref_validMailID_Obj.getString(NameKey_mailID, "").trim();
//        Log.d("Mail_Id:", gmailid);
//
//
//        sharedpref_username_Obj = getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
//        str_loginusername = sharedpref_username_Obj.getString(Key_username, "").trim();
//
//        sharedpref_userimage_Obj = getSharedPreferences(sharedpreferenc_userimage, Context.MODE_PRIVATE);
//        str_profileimage = sharedpref_userimage_Obj.getString(key_userimage, "").trim();
//
//
//        sharedpref_loginuserid_Obj = getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
//        str_loginuserid = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();
//
//        sharedpref_schedulerid_Obj = getSharedPreferences(sharedpreferenc_schedulerid, Context.MODE_PRIVATE);
//        str_schedulerid = sharedpref_schedulerid_Obj.getString(key_schedulerid, "").trim();
//
//
//        if (checkPermissions()) {
//            //  permissions  granted.
//        }
//
//
//        firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
//        //this is where we start the Auth state Listener to listen for whether the user is signed in or not
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                // Get signedIn user
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                //if user is signed in, we call a helper method to save the user details to Firebase
//                if (user != null) {
//                    // User is signed in
//                    // you could place other firebase code
//                    //logic to save the user details to Firebase
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//            }
//        };
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.web_client_id))//you can also use R.string.default_web_client_id
//                .requestEmail()
//                .build();
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//
//        signInButton = findViewById(R.id.sign_in_button);
//        setGooglePlusButtonText(signInButton, "Sign in with DF mail");
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//                startActivityForResult(intent, RC_SIGN_IN);
//            }
//        });
//
//
//        UserList_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                obj_Class_Userlist = (Class_UserList) UserList_SP.getSelectedItem();
//                sp_user_ID = obj_Class_Userlist.getUser_id();
//                selected_useremailID = UserList_SP.getSelectedItem().toString();
//                Log.i("selected_useremailID", " : " + selected_useremailID);
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//        nextscreen_BT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
////                SharedPreferences.Editor editorObjmailID = sharedpref_validMailID_Obj.edit();
////                editorObjmailID.putString(NameKey_mailID, "raghavendra.tech@dfmail.org");
////                editorObjmailID.commit();
////
////                SharedPreferences.Editor editorObj_centerid= sharedpreferencebook_centerid_Obj.edit();
////                editorObj_centerid.putString(KeyValue_centerid,"1");
////                editorObj_centerid.commit();
////
////                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
////
////                AsyncCallWS2_Login task = new AsyncCallWS2_Login(MainActivity.this);
////                task.execute();
//
////                Intent i = new Intent(MainActivity.this, NormalLogin.class);
////                startActivity(i);
////                finish();
//
//
//                AsyncCallWS_ValidateUser asyncCallWS_validateUser = new AsyncCallWS_ValidateUser(MainActivity.this);
//                asyncCallWS_validateUser.execute();
//
//
//            }
//
//        });
//        internetDectector = new Class_InternetDectector(getApplicationContext());
//        isInternetPresent = internetDectector.isConnectingToInternet();
//
//        if (isInternetPresent) {
//
//
//            AsyncCallForceUpdate task1 = new AsyncCallForceUpdate(MainActivity.this);
//            task1.execute();
//        } else {
//            // Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
//
//        }
//
//
//        UserListAsyncTask task2 = new UserListAsyncTask(MainActivity.this);
//        task2.execute();
//
//    }//end of oncreate
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//        }
//    }
//
//    private void handleSignInResult(GoogleSignInResult result) {
//        if (result.isSuccess()) {
//            GoogleSignInAccount account = result.getSignInAccount();
//            idToken = account.getIdToken();
//            name = account.getDisplayName();
//            email = account.getEmail();
//
//
////            SharedPreferences myprefs_Username= MainActivity.this.getSharedPreferences("googlelogin_name", MODE_PRIVATE);
////            myprefs_Username.edit().putString("name_googlelogin", name).apply();
////            SharedPreferences myprefs_UserImg= MainActivity.this.getSharedPreferences("googlelogin_img", MODE_PRIVATE);
////            myprefs_UserImg.edit().putString("profileimg_googlelogin", String.valueOf(account.getPhotoUrl())).apply();
//
//
//            SharedPreferences.Editor myprefs_Username = sharedpref_username_Obj.edit();
//            myprefs_Username.putString(Key_username, name);
//            myprefs_Username.apply();
//
//            SharedPreferences.Editor myprefs_UserImg = sharedpref_userimage_Obj.edit();
//            myprefs_UserImg.putString(key_userimage, String.valueOf(account.getPhotoUrl()));
//            myprefs_UserImg.apply();
//
//
//            Class_Class_SaveSharedPreference.setUserName(MainActivity.this, account.getDisplayName());
//
//            // you can store user data to SharedPreference
//            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//            firebaseAuthWithGoogle(credential);
//        } else {
//            // Google Sign In failed, update UI appropriately
//            Log.e(TAG, "Login Unsuccessful. " + result);
//            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void firebaseAuthWithGoogle(AuthCredential credential) {
//
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
//                        if (task.isSuccessful()) {
//
//                            /*
//                             Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//
//                             str_gmail_ID=user.getEmail().toString();
//
//                            Toast.makeText(MainActivity.this, ""+str_gmail_ID.toString(), Toast.LENGTH_SHORT).show();
//
//
//                            internetDectector = new Class_InternetDectector(getApplicationContext());
//                            isInternetPresent = internetDectector.isConnectingToInternet();
//
//
//                            if (isInternetPresent)
//                            {
//
//                                AsyncCallWS_ValidateUser task_validateuser = new AsyncCallWS_ValidateUser(MainActivity.this);
//                                task_validateuser.execute();
//
//                                /*Intent i = new Intent(MainActivity.this, Activity_HomeScreen.class);
//                                startActivity(i);
//                                finish();
//                        }
//
//                             */
//
//                            //commented and added by shivaleela the below code
///////////////////////////////////////////////////////////////////////////////////
//                            //Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                            str_gmail_ID = user.getEmail();
//                            //System.out.println("str_gmail_ID..."+str_gmail_ID);
//
//                            // Toast.makeText(MainActivity.this, ""+str_gmail_ID.toString(), Toast.LENGTH_SHORT).show();
//                            googleApiClient.clearDefaultAccountAndReconnect();
//
//
//                            internetDectector = new Class_InternetDectector(getApplicationContext());
//                            isInternetPresent = internetDectector.isConnectingToInternet();
//
//                            if (isInternetPresent) {
//
//
//                                AsyncCallWS_ValidateUser asyncCallWS_validateUser = new AsyncCallWS_ValidateUser(MainActivity.this);
//                                asyncCallWS_validateUser.execute();
//
//                            } else {
//                                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
//
//                            }
//
//
//                        } else {
//                            Log.w(TAG, "signInWithCredential" + task.getException().getMessage());
//                            task.getException().printStackTrace();
//                            Toast.makeText(MainActivity.this, "No Internet",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//    }
//
//
//    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
//        // Find the TextView that is inside of the SignInButton and set its text
//        for (int i = 0; i < signInButton.getChildCount(); i++) {
//            View v = signInButton.getChildAt(i);
//
//            if (v instanceof TextView) {
//                TextView tv = (TextView) v;
//                tv.setText(buttonText);
//                // tv.setBackgroundColor(Color.CYAN);
////                tv.setBackgroundDrawable(
////                        new ColorDrawable(Color.parseColor(COLOR)));
//                tv.setBackgroundColor(Color.parseColor(COLOR));
//                tv.setTextColor(Color.WHITE);
//               // tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/laouibold.ttf"));
//                return;
//            }
//        }
//    }
//
//    private boolean checkPermissions() {
//        int result;
//        List<String> listPermissionsNeeded = new ArrayList<>();
//        for (String p : permissions) {
//            result = ContextCompat.checkSelfPermission(this, p);
//            if (result != PackageManager.PERMISSION_GRANTED) {
//                listPermissionsNeeded.add(p);
//            }
//        }
//        if (!listPermissionsNeeded.isEmpty()) {
//            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
//            return false;
//        }
//        return true;
//    }
//
//
//    private class AsyncCallWS_ValidateUser extends AsyncTask<String, Void, Void> {
//        ProgressDialog dialog;
//        Context context;
//
//        protected void onPreExecute() {
//            //  Log.i(TAG, "onPreExecute---tab2");
//            dialog.setMessage("Please wait...");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            //Log.i(TAG, "onProgressUpdate---tab2");
//        }
//
//
//        @Override
//        protected Void doInBackground(String... params) {
//            Log.i("df", "doInBackground");
//
////            Login_Verify(str_gmail_ID);
//            Log.e("selected_useremailID", selected_useremailID);
//            if(!selected_useremailID.equals("")) {
//                Login_Verify(selected_useremailID);
//            }
//
//            return null;
//        }
//
//        public AsyncCallWS_ValidateUser(MainActivity activity) {
//            context = activity;
//            dialog = new ProgressDialog(activity);
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//
//            dialog.dismiss();
//            if (!login_userStatus.equals("")) {
//                if (login_userStatus.equals("Active")) {
//                    Class_Class_SaveSharedPreference.setUserName(MainActivity.this, selected_useremailID);
//
//                    //  Toast.makeText(NormalLogin.this, "Success", Toast.LENGTH_SHORT).show();
//                    AsyncCallWS2_Login task = new AsyncCallWS2_Login(MainActivity.this);
//                    task.execute();
//
//                   // setGCM1();
//                    InsertDeviceDetailsAsynctask insertDeviceDetailsAsynctask=new InsertDeviceDetailsAsynctask(MainActivity.this);
//                    insertDeviceDetailsAsynctask.execute();
//
//                    LoginCountAsynctask loginCountAsynctask = new LoginCountAsynctask(MainActivity.this);
//                    loginCountAsynctask.execute();
//
//
//                } else if (login_userStatus.equals("No Records Found")) {
////                    Toast.makeText(MainActivity.this, "No Records Found", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(MainActivity.this, "Access Denied", Toast.LENGTH_SHORT).show();
//
//                }
//            } else {
//                Toast.makeText(MainActivity.this, "Access Denied", Toast.LENGTH_SHORT).show();
//
//            }
//
//        }//end of onPostExecute
//    }// end Async task
//
//    public void Login_Verify(String username1) {
//        Vector<SoapObject> result1 = null;
//
//        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
//        String METHOD_NAME = "ValidateGoogleLogin";
//        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/ValidateGoogleLogin";
//
//        try {
//
//            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
//            request.addProperty("User_Email", username1);
//            //request.addProperty("User_Password", password1);
//
//            Log.i("request", request.toString());
//
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
//                SoapObject response = (SoapObject) envelope.getResponse();
//                Log.e("value at response", response.getProperty(0).toString());
//                SoapObject obj2 = (SoapObject) response.getProperty(0);
//                Log.e("obj2", obj2.toString());
//
//                if (response.getProperty(0).toString().contains("User_Status")) {
//                    Log.e("login_userStatus", "hello");
//                    if (response.getProperty(0).toString().contains("User_Status=Active")) {
//                        Log.e("login_userStatusactive", "Active");
//
//                        SoapPrimitive userid = (SoapPrimitive) obj2.getProperty("User_ID");
//                        login_userid = userid.toString();
//                        SoapPrimitive userEmail = (SoapPrimitive) obj2.getProperty("User_Email");
//                        login_userEmail = userEmail.toString();
//                        SoapPrimitive user_status = (SoapPrimitive) obj2.getProperty("User_Status");
//                        login_userStatus = user_status.toString();
//                        Log.e("login_userid", login_userid);
//                        Log.e("login_userEmail", login_userEmail);
//                        Log.e("login_userStatus", login_userStatus);
////                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
////                        SharedPreferences.Editor editor = settings.edit();
////                        editor.putString("login_userEmail", login_userEmail);
//
//                    }
//                } else {
//                    Log.e("login_userStatus", "login_userStatus=null");
//
//                }
//
//            } catch (Throwable t) {
//                Log.e("request fail", "> " + t.getMessage());
//                login_userStatus = "slow internet";
//
//            }
//        } catch (Throwable t) {
//            Log.e("UnRegister Receiver ", "> " + t.getMessage());
//
//        }
//
//    }
//
//
//    private class AsyncCallWS2_Login extends AsyncTask<String, Void, Void> {
//        // ProgressDialog dialog;
//
//        Context context;
//
//        @Override
//        protected void onPreExecute() {
//            Log.i("tag", "onPreExecute---tab2");
//            /*dialog.setMessage("Please wait..");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();*/
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            Log.i("tag", "onProgressUpdate---tab2");
//        }
//
//        public AsyncCallWS2_Login(MainActivity activity) {
//            context = activity;
//            //  dialog = new ProgressDialog(activity);
//        }
//
//        @Override
//        protected Void doInBackground(String... params) {
//            Log.i("tag", "doInBackground");
//
//            //   fetch_all_info("");
//
//			/*  if(!login_result.equals("Fail"))
//			  {
//				  GetAllEvents(u1,p1);
//			  }*/
//
//            //GetAllEvents(u1,p1);
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            //  dialog.dismiss();
//
//
//            Intent i = new Intent(MainActivity.this, Activity_HomeScreen.class);
//
//
////            SharedPreferences myprefs = MainActivity.this.getSharedPreferences("user", MODE_PRIVATE);
////            myprefs.edit().putString("login_userid", login_userid).apply();
////            SharedPreferences myprefs_scheduleId = MainActivity.this.getSharedPreferences("scheduleId", MODE_PRIVATE);
////            myprefs_scheduleId.edit().putString("scheduleId", Schedule_ID).apply();
//
//
//            SharedPreferences.Editor myprefs_loginuserid = sharedpref_loginuserid_Obj.edit();
//            myprefs_loginuserid.putString(key_loginuserid, login_userid);
//            myprefs_loginuserid.apply();
//
//            SharedPreferences.Editor myprefs_schedulerid = sharedpref_schedulerid_Obj.edit();
//            myprefs_schedulerid.putString(key_schedulerid, Schedule_ID);
//            myprefs_schedulerid.apply();
//
//            startActivity(i);
//            finish();
//        }
//    }
//
//
//    public void fetch_all_info(String email) {
//
//
//        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
//        String METHOD_NAME = "LoadScheduleEmployee";
//        String Namespace = "http://mis.detedu.org:8089/", SOAP_ACTION1 = "http://mis.detedu.org:8089/LoadScheduleEmployee";
//
//        try {
//            //mis.leadcampus.org
//
//            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
//
//            Log.i("User_ID=", login_userid);
//            request.addProperty("User_ID", login_userid);
//            Log.d("request :", request.toString());
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;
//
//            envelope.dotNet = true;
//            //Set output SOAP object
//            envelope.setOutputSoapObject(request);
//            //Create HTTP call object
//            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
//            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
//            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
//
//            try {
//                androidHttpTransport.call(SOAP_ACTION1, envelope);
//                Log.d("soap responseyyyyyyy", envelope.getResponse().toString());
//                SoapObject response = (SoapObject) envelope.getResponse();
//                Log.d("soap responseyyyyyyy", response.toString());
//                //	for (SoapObject cs : result1)
//                if (response.getPropertyCount() > 0) {
//                    int Count = response.getPropertyCount();
//
//                    for (int i = 0; i < Count; i++) {
//                        // sizearray=result1.size();
//                        //  Log.i("Tag","sizearray="+sizearray);
//                        /*  <Schedule_ID>int</Schedule_ID>
//          <Lavel_ID>int</Lavel_ID>
//          <Schedule_Date>string</Schedule_Date>
//          <Start_Time>string</Start_Time>
//          <End_Time>string</End_Time>
//          <Schedule_Session>string</Schedule_Session>
//          <Subject_Name>string</Subject_Name>
//          <Schedule_Status>string</Schedule_Status>*/
//
//
//                        SoapObject list = (SoapObject) response.getProperty(i);
//                        Schedule_Status = list.getProperty("Schedule_Status").toString();
//
//                        Schedule_ID = list.getProperty("Schedule_ID").toString();
//                        Lavel_ID = list.getProperty("Lavel_ID").toString();
//                        Schedule_Date = list.getProperty("Schedule_Date").toString();
//                        End_Time = list.getProperty("End_Time").toString();
//                        Start_Time = list.getProperty("Start_Time").toString();
//                        Subject_Name = list.getProperty("Subject_Name").toString();
//                        Schedule_Session = list.getProperty("Schedule_Session").toString();
//                        Leason_Name = list.getProperty("Leason_Name").toString();
//                        Lavel_Name = list.getProperty("Lavel_Name").toString();
//                        Institute_Name = list.getProperty("Institute_Name").toString();
//                        Cluster_Name = list.getProperty("Cluster_Name").toString();
//
//                        //   State cat = new State(c.getString("state_id"),c.getString("state_name"));
//                        UserInfo userInfo = new UserInfo(Schedule_ID, Lavel_ID, Schedule_Date, End_Time, Start_Time, Schedule_Session, Schedule_Status, Subject_Name, Lavel_Name, Leason_Name, Cluster_Name, Institute_Name);
//                        arrayList.add(userInfo);
//
//                    }
//
//                    final String[] items = new String[Count];
//                    userInfosarr = new UserInfo[Count];
//                    UserInfo obj = new UserInfo();
//
//                    UserInfo.user_info_arr.clear();
//                    for (int i = 0; i < response.getPropertyCount(); i++) {
//                        Schedule_ID = arrayList.get(i).Schedule_ID;
//                        Lavel_ID = arrayList.get(i).Lavel_ID;
//                        Schedule_Date = arrayList.get(i).Schedule_Date;
//                        End_Time = arrayList.get(i).End_Time;
//                        Start_Time = arrayList.get(i).Start_Time;
//                        Schedule_Session = arrayList.get(i).Schedule_Session;
//                        Schedule_Status = arrayList.get(i).Schedule_Status;
//                        Subject_Name = arrayList.get(i).Subject_Name;
//                        Lavel_Name = arrayList.get(i).Lavel_Name;
//                        Leason_Name = arrayList.get(i).Leason_Name;
//                        Cluster_Name = arrayList.get(i).Cluster_Name;
//                        Institute_Name = arrayList.get(i).Institute_Name;
//
//                        obj.setSchedule_ID(Schedule_ID);
//                        obj.setLavel_ID(Lavel_ID);
//                        obj.setSchedule_Date(Schedule_Date);
//                        obj.setEnd_Time(End_Time);
//                        obj.setStart_Time(Start_Time);
//                        obj.setSchedule_Session(Schedule_Session);
//                        obj.setSchedule_Status(Schedule_Status);
//                        obj.setSubject_Name(Subject_Name);
//                        obj.setLavel_Name(Lavel_Name);
//                        obj.setLeason_Name(Leason_Name);
//                        obj.setInstitute_Name(Institute_Name);
//                        obj.setCluster_Name(Cluster_Name);
//
//                        userInfosarr[i] = obj;
//                        //   UserInfo.user_info_arr =new ArrayList<UserInfo>() ;
//
//                        UserInfo.user_info_arr.add(new UserInfo(Schedule_ID, Lavel_ID, Schedule_Date, End_Time, Start_Time, Schedule_Session, Schedule_Status, Subject_Name, Lavel_Name, Leason_Name, Cluster_Name, Institute_Name));
//
//                        Log.i("Tag", "items aa=" + arrayList.get(i).Schedule_ID);
//
//                    }
//
//                    Log.i("Tag", "items=" + items.length);
//                }
//                //  Log.e("TAG","bookid="+bookid+"cohartName="+cohartName+"fellowshipName="+fellowshipName+"eventdate="+eventdate+"start_time"+start_time);
//
//			/*	 for(int i=0;i<result1.size();i++);
//				 {
//					 String booking_id_temp, user_id_temp,date_temp,notes_temp,start_time_temp;
//					 if(Event_Discription.equals(result1.elementAt(i)))
//
//				 }*/
//
//                //	 SoapPrimitive messege = (SoapPrimitive)response.getProperty("Status");
//                // version = (SoapPrimitive)response.getProperty("AppVersion");
//                // release_not = (SoapPrimitive)response.getProperty("ReleseNote");
//
//            } catch (Throwable t) {
//                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
//                //		Toast.LENGTH_LONG).show();
//                Log.e("request fail 5", "> " + t.getMessage());
//            }
//        } catch (Throwable t) {
//            Log.e("Tag", "UnRegister Receiver Error 5" + " > " + t.getMessage());
//
//        }
//
//    }
//
///////////////////////////////////////////////////////////////////////////////////////
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (authStateListener != null) {
//            FirebaseAuth.getInstance().signOut();
//        }
//        firebaseAuth.addAuthStateListener(authStateListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (authStateListener != null) {
//            firebaseAuth.removeAuthStateListener(authStateListener);
//        }
//    }
//
//
//    public class AsyncCallForceUpdate extends AsyncTask<String, Void, Void> {
//
//       // ProgressDialog dialog;
//
//        Context context;
////        boolean versionval;
//
//        @Override
//        protected void onPreExecute() {
//            Log.i(TAG, "onPreExecute---tab2");
////            dialog.setMessage("Please wait..");
////            dialog.setCanceledOnTouchOutside(false);
////            dialog.show();
////
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            Log.i(TAG, "onProgressUpdate---tab2");
//        }
//
//        public AsyncCallForceUpdate(MainActivity activity) {
//            context = activity;
//            //dialog = new ProgressDialog(activity);
//        }
//
//        @Override
//        protected Void doInBackground(String... params) {
//            Log.i(TAG, "doInBackground");
//
//            versionval = appversioncheck(str_versioncode);
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
////            if (dialog.isShowing()) {
////                dialog.dismiss();
////
////            }
//            // Log.i(TAG, "onPostExecute");
//
//            if (versionval) {
//            } else {
//                //Toast.makeText(getApplicationContext(),"Update from playstore",Toast.LENGTH_LONG).show();
//                // alerts();
//                alerts_dialog();
//            }
//
//
//        }
//    }//end of normal login AsynTask
//
//    //ForceUpdate
//    public boolean appversioncheck(String versionNos) {
//        int verInt = 0;
//        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
//        String METHOD_NAME = "AppReleaseVersion";//AppReleaseVersion
//        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/AppReleaseVersion";
//        String mailresponse = "flag";
//
//
//        SoapObject request = new SoapObject(Namespace, METHOD_NAME);
//
//        request.addProperty("UserAppVersion", versionNos);
//        Log.e("appversion", request.toString());
//
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        envelope.dotNet = true;
//        //Set output SOAP object
//        envelope.setOutputSoapObject(request);
//        //Create HTTP call object
//        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
//
//        try {
//            androidHttpTransport.call(SOAPACTION, envelope);
//
//            SoapObject response = (SoapObject) envelope.getResponse();
//            Log.e("apprelease response", response.toString());
//
//
//            String object2string = response.getProperty(0).toString();
//
//            mailresponse = object2string;
//            verInt = Integer.valueOf(object2string);
//
//            String str_appversion = response.getProperty("AppVersion").toString();
//            String str_releasenote = response.getProperty("ReleseNote").toString();
//            str_appversion_status = response.getProperty("Status").toString();
//
//            Log.e("str_appversion", str_appversion);
//            Log.e("str_releasenote", str_releasenote);
//            Log.e("str_appversion_status", str_appversion_status);
//
//            //Toast.makeText(getApplicationContext(), "hi"+object2string, Toast.LENGTH_LONG).show();
//            Log.e("force verInt", object2string);
//        } catch (Throwable t) {
//
//            Log.e("request fail", "> " + t.getMessage());
//        }
//
//
//        //9>=10
//        return versionCodes >= verInt;
//
//    }
//
//    public void alerts_dialog() {
//
//        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//        dialog.setCancelable(false);
//        dialog.setTitle("Skill In Village");
//        dialog.setMessage("Kindly update from playstore");
//        //dialog.setMessage("Kindly contact SIV team for new version");
//        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                Intent	intent = new Intent(android.content.Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.det.skillinvillage"));
//                startActivity(intent);
//                finish();
//            }
//        });
////        dialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                //  finish();
////                dialog.dismiss();
////            }
////        });
//
//
//        final AlertDialog alert = dialog.create();
//        alert.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface arg0) {
//                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
//                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
//
//            }
//        });
//        alert.show();
//
//    }
//
//
//    private class AsyncCallForceUpdate_new extends AsyncTask<String, Void, Void> {
//
//        ProgressDialog dialog;
//
//        Context context;
//        boolean versionval_new;
//
//        @Override
//        protected void onPreExecute() {
//            Log.i(TAG, "onPreExecute---tab2");
//            dialog.setMessage("Please wait..");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            Log.i(TAG, "onProgressUpdate---tab2");
//        }
//
//        public AsyncCallForceUpdate_new(MainActivity activity) {
//            context = activity;
//            dialog = new ProgressDialog(activity);
//        }
//
//        @Override
//        protected Void doInBackground(String... params) {
//            Log.i(TAG, "doInBackground");
//
//            versionval_new = appversioncheck_new(str_versioncode);//str_versioncode
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//
//            }
//            // Log.i(TAG, "onPostExecute");
//
//            if (versionval_new) {
//                Intent i = new Intent(MainActivity.this, Activity_HomeScreen.class);
//                startActivity(i);
//                finish();
//
//            } else {
//                //Toast.makeText(getApplicationContext(),"Update from playstore",Toast.LENGTH_LONG).show();
//                // alerts();
//               // alerts_dialog_new();
//                alerts_dialog();
//            }
////            if(str_appversion_status.equals("Please send valid Version code")){
////                Toast.makeText(getApplicationContext(),str_appversion_status,Toast.LENGTH_LONG).show();
////
////            }
//
//
//        }
//    }//end of normal login AsynTask
//
//    //ForceUpdate
//    public boolean appversioncheck_new(String versionNos) {
//        int verInt = 0;
//        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
//        String METHOD_NAME = "AppReleaseVersion";//AppTestingVersion,AppReleaseVersion
//        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/AppReleaseVersion";
//        String mailresponse = "flag";
//
//
//        SoapObject request = new SoapObject(Namespace, METHOD_NAME);
//
//        request.addProperty("UserAppVersion", versionNos);//versionNos
//        Log.e("appversion", request.toString());
//
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        envelope.dotNet = true;
//        //Set output SOAP object
//        envelope.setOutputSoapObject(request);
//        //Create HTTP call object
//        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
//
//        try {
//            androidHttpTransport.call(SOAPACTION, envelope);
//
//            SoapObject response = (SoapObject) envelope.getResponse();
//            Log.e("apprelease response", response.toString());
//
//
//            String object2string = response.getProperty(0).toString();
//
//            mailresponse = object2string;
//            verInt = Integer.valueOf(object2string);
//
//            String str_appversion = response.getProperty("AppVersion").toString();
//            String str_releasenote = response.getProperty("ReleseNote").toString();
//            str_appversion_status = response.getProperty("Status").toString();
//
//            Log.e("str_appversion", str_appversion);
//            Log.e("str_releasenote", str_releasenote);
//            Log.e("str_appversion_status", str_appversion_status);
//
//            //Toast.makeText(getApplicationContext(), "hi"+object2string, Toast.LENGTH_LONG).show();
//            Log.e("force verInt", object2string);
//        } catch (Throwable t) {
//
//            Log.e("request fail", "> " + t.getMessage());
//        }
//
//
//        //9>=10
//        return versionCodes >= verInt;
//
//    }
//
//    public void alerts_dialog_new() {
//
//        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//        dialog.setCancelable(false);
//        dialog.setTitle("Skill In Village");
//        // dialog.setMessage("Kindly update from playstore");
//        dialog.setMessage("Kindly contact SIV team for new version");
//        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//               /* Intent	intent = new Intent(android.content.Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.leadcampusapp"));
//                startActivity(intent);*/
//              //  finish();
//
//
//
//                Intent	intent = new Intent(android.content.Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.det.skillinvillage"));
//                startActivity(intent);
//                finish();
//
//            }
//        });
////        dialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                //  finish();
////                dialog.dismiss();
////            }
////        });
//
//
//        final AlertDialog alert = dialog.create();
//        alert.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface arg0) {
//                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
//                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
//
//            }
//        });
//        alert.show();
//
//    }
//
///*
//2019-10-12 14:35:14.376 24954-30182/com.det.skillinvillage E/insert detailsedit: UpdateStudentData{Student_ID=1237; Sandbox_ID=0; Academic_ID=2019; Cluster_ID=1; Institute_ID=39; Level_ID=385; School_ID=144; Student_Name=Laxmi Vadakannavar; Gender=Female; Birth_Date=2007-04-20; Education=Select; Marks=; Mobile=8495053867; Father_Name=Chandrappa; Mother_Name=Ningavva; Student_Aadhar=488281931787; Student_Status=Applicant; Admission_Fee=; Created_Date=2019-10-12; Created_By=72; File_Name=; Receipt_Manual=; }
//2019-10-12 14:35:14.662 24954-30182/com.det.skillinvillage E/appsubResponse: anyType{vmApiStudent=anyType{Student_ID=0; Sandbox_ID=0; Academic_ID=0; Cluster_ID=0; Institute_ID=0; School_ID=0; Level_ID=0; Student_Name=0; Gender=0; Birth_Date=0; Education=0; Mobile=0; Father_Name=0; Mother_Name=0; Student_Aadhar=0; Student_Status=Error; Admission_Fee=0; Created_Date=0; Created_By=0; File_Name=0; Receipt_Manual=0; }; }
//2019-10-12 14:35:14.662 24954-30182/com.det.skillinvillage E/status: anyType{Student_ID=0; Sandbox_ID=0; Academic_ID=0; Cluster_ID=0; Institute_ID=0; School_ID=0; Level_ID=0; Student_Name=0; Gender=0; Birth_Date=0; Education=0; Mobile=0; Father_Name=0; Mother_Name=0; Student_Aadhar=0; Student_Status=Error; Admission_Fee=0; Created_Date=0; Created_By=0; File_Name=0; Receipt_Manual=0; }
//2019-10-12 14:35:14.662 24954-30182/com.det.skillinvillage E/Student_Status: Error
//2019-10-12 14:35:14.741 24954-24954/com.det.skillinvillage E/ViewRootImpl: sendUserActionEvent() returned.
//
// */
//
//
///////////////////////UserlOGIN_COUNTS/////////////
//
//    private class LoginCountAsynctask extends AsyncTask<String, Void, Void> {
//       // ProgressDialog dialog;
//        Context context;
//
//        protected void onPreExecute() {
//            //  Log.i(TAG, "onPreExecute---tab2");
////            dialog.setMessage("Please wait...");
////            dialog.setCanceledOnTouchOutside(false);
////            dialog.show();
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            //Log.i(TAG, "onProgressUpdate---tab2");
//        }
//
//
//        @Override
//        protected Void doInBackground(String... params) {
//            Log.i("df", "doInBackground");
//
//            getlogincount();
//            return null;
//        }
//
//        public LoginCountAsynctask(MainActivity activity) {
//            context = activity;
//           // dialog = new ProgressDialog(activity);
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//
//           // dialog.dismiss();
//            if (!login_count_Status.equals("")) {
//                if (login_count_Status.equals("Success")) {
//                   // Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//
//                } else if (login_count_Status.equals("Failure")) {
//                   // Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }//end of onPostExecute
//    }// end Async task
//
//    public void getlogincount() {
//        Vector<SoapObject> result1 = null;
//
//        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
//        String METHOD_NAME = "userLoginrecord";
//        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/userLoginrecord";
//
//        try {
//
//            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
//            if (!login_userid.equals("")) {
//                request.addProperty("user_id", login_userid);
//                Log.i("request", request.toString());
//            }
//
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
//                SoapObject response = (SoapObject) envelope.getResponse();
//                Log.e("value at response", response.getProperty(0).toString());
//                SoapObject obj2 = (SoapObject) response.getProperty(0);
//                Log.e("obj2", obj2.toString());
//
//                SoapPrimitive soap_status = (SoapPrimitive) obj2.getProperty("status");
//                login_count_Status = soap_status.toString();
//                Log.e("login_count_Status", login_count_Status);
//
//
////                if (response.getProperty(0).toString().contains("status")) {
////                    Log.e("login_count_Status", "hello");
////                    if (response.getProperty(0).toString().contains("status=Success")) {
////                        Log.e("login_count_Status", "success");
////
////                        SoapPrimitive soap_status = (SoapPrimitive) obj2.getProperty("status");
////                        login_count_Status = soap_status.toString();
////                        Log.e("login_count_Status", login_count_Status);
////
////
////                    }
////                } else {
////                    Log.e("login_count_Status", "login_count_Status=null");
////
////                }
//
//            } catch (Throwable t) {
//                Log.e("request fail", "> " + t.getMessage());
//                login_count_Status = "slow internet";
//
//            }
//        } catch (Throwable t) {
//            Log.e("UnRegister Receiver ", "> " + t.getMessage());
//
//        }
//
//    }
//
//
////////////////////////////////////////////////////5THNOVEMBER/////////
//
//    private class InsertDeviceDetailsAsynctask extends AsyncTask<String, Void, Void> {
//       // ProgressDialog dialog1;
//        Context context;
//
//        protected void onPreExecute() {
//            //  Log.i(TAG, "onPreExecute---tab2");
////            dialog1.setMessage("Please wait...");
////            dialog1.setCanceledOnTouchOutside(false);
////            dialog1.show();
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            //Log.i(TAG, "onProgressUpdate---tab2");
//        }
//
//
//        @SuppressLint("MissingPermission")
//        @Override
//        protected Void doInBackground(String... params) {
//            Log.i("df", "doInBackground");
//            // Fetch Device info
//
//       /* final TelephonyManager tm = (TelephonyManager) getBaseContext()
//                .getSystemService(Context.TELEPHONY_SERVICE);*/
//
//            tm1 = (TelephonyManager) getBaseContext()
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//
//            //   final String tmDevice, tmSerial, androidId;
//            String NetworkType;
//            //TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
//            simOperatorName = tm1.getSimOperatorName();
//            Log.v("Operator", "" + simOperatorName);
//            NetworkType = "GPRS";
//
//
//            int simSpeed = tm1.getNetworkType();
//            if (simSpeed == 1)
//                NetworkType = "Gprs";
//            else if (simSpeed == 4)
//                NetworkType = "Edge";
//            else if (simSpeed == 8)
//                NetworkType = "HSDPA";
//            else if (simSpeed == 13)
//                NetworkType = "LTE";
//            else if (simSpeed == 3)
//                NetworkType = "UMTS";
//            else
//                NetworkType = "Unknown";
//
//            Log.v("SIM_INTERNET_SPEED", "" + NetworkType);
////            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
////                // TODO: Consider calling
////                //    ActivityCompat#requestPermissions
////                // here to request the missing permissions, and then overriding
////                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////                //                                          int[] grantResults)
////                // to handle the case where the user grants the permission. See the documentation
////                // for ActivityCompat#requestPermissions for more details.
////                return;
////            }
//            tmDevice = "" + tm1.getDeviceId();
//            Log.v("DeviceIMEI", "" + tmDevice);
//            mobileNumber = "" + tm1.getLine1Number();
//            Log.v("getLine1Number value", "" + mobileNumber);
//
//            String mobileNumber1 = "" + tm1.getPhoneType();
//            Log.v("getPhoneType value", "" + mobileNumber1);
//            tmSerial = "" + tm1.getSimSerialNumber();
//            //  Log.v("GSM devices Serial Number[simcard] ", "" + tmSerial);
//            androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),
//                    android.provider.Settings.Secure.ANDROID_ID);
//            Log.v("androidId CDMA devices", "" + androidId);
//            UUID deviceUuid = new UUID(androidId.hashCode(),
//                    ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
//            deviceId = deviceUuid.toString();
//            //  Log.v("deviceIdUUID universally unique identifier", "" + deviceId);
//
//
//            deviceModelName = android.os.Build.MODEL;
//            Log.v("Model Name", "" + deviceModelName);
//            deviceUSER = android.os.Build.USER;
//            Log.v("Name USER", "" + deviceUSER);
//            devicePRODUCT = android.os.Build.PRODUCT;
//            Log.v("PRODUCT", "" + devicePRODUCT);
//            deviceHARDWARE = android.os.Build.HARDWARE;
//            Log.v("HARDWARE", "" + deviceHARDWARE);
//            deviceBRAND = android.os.Build.BRAND;
//            Log.v("BRAND", "" + deviceBRAND);
//            myVersion = android.os.Build.VERSION.RELEASE;
//            Log.v("VERSION.RELEASE", "" + myVersion);
//            sdkVersion = android.os.Build.VERSION.SDK_INT;
//            Log.v("VERSION.SDK_INT", "" + sdkVersion);
//            sdkver = Integer.toString(sdkVersion);
//            // Get display details
//
//            Measuredwidth = 0;
//            Measuredheight = 0;
//            Point size = new Point();
//            WindowManager w = getWindowManager();
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                //   w.getDefaultDisplay().getSize(size);
////                Measuredwidth = w.getDefaultDisplay().getWidth();//size.x;
////                Measuredheight = w.getDefaultDisplay().getHeight();//size.y;
//
//
//////////////////////////////////////////////////////////////////
//                //Point size = new Point();
//                getWindowManager().getDefaultDisplay().getSize(size);
//                Measuredwidth = size.x;
//                Measuredheight = size.y;
////////////////////////////////////////////////////////
//
//            } else {
//                Display d = w.getDefaultDisplay();
////                Measuredwidth = d.getWidth();
////                Measuredheight = d.getHeight();
//
//                getWindowManager().getDefaultDisplay().getSize(size);
//                Measuredwidth = size.x;
//                Measuredheight = size.y;
////////////////////////////////////////////////////////
//
//            }
//
//            Log.v("SCREEN_Width", "" + Measuredwidth);
//            Log.v("SCREEN_Height", "" + Measuredheight);
//
//
//            regId = FirebaseInstanceId.getInstance().getToken();
//
//
//
//            Log.e("regId_DeviceID", "" + regId);
//
///*<username>string</username>
//      <DeviceId>string</DeviceId>
//      <OSVersion>string</OSVersion>
//      <Manufacturer>string</Manufacturer>
//      <ModelNo>string</ModelNo>
//      <SDKVersion>string</SDKVersion>
//      <DeviceSrlNo>string</DeviceSrlNo>
//      <ServiceProvider>string</ServiceProvider>
//      <SIMSrlNo>string</SIMSrlNo>
//      <DeviceWidth>string</DeviceWidth>
//      <DeviceHeight>string</DeviceHeight>
//      <AppVersion>string</AppVersion>*/
//
//
//            setGCM1();
//            return null;
//        }
//
//        public InsertDeviceDetailsAsynctask(MainActivity activity) {
//            context = activity;
//            //dialog1 = new ProgressDialog(activity);
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//
//           // dialog1.dismiss();
//        }//end of onPostExecute
//    }// end Async task
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    public void setGCM1() {
//
//
//
//
////        // Fetch Device info
////
////       /* final TelephonyManager tm = (TelephonyManager) getBaseContext()
////                .getSystemService(Context.TELEPHONY_SERVICE);*/
////
////         tm1 = (TelephonyManager) getBaseContext()
////                .getSystemService(Context.TELEPHONY_SERVICE);
////
////        //   final String tmDevice, tmSerial, androidId;
////        String NetworkType;
////        //TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
////        simOperatorName = tm1.getSimOperatorName();
////        Log.v("Operator", "" + simOperatorName);
////        NetworkType = "GPRS";
////
////
////        int simSpeed = tm1.getNetworkType();
////        if (simSpeed == 1)
////            NetworkType = "Gprs";
////        else if (simSpeed == 4)
////            NetworkType = "Edge";
////        else if (simSpeed == 8)
////            NetworkType = "HSDPA";
////        else if (simSpeed == 13)
////            NetworkType = "LTE";
////        else if (simSpeed == 3)
////            NetworkType = "UMTS";
////        else
////            NetworkType = "Unknown";
////
////        Log.v("SIM_INTERNET_SPEED", "" + NetworkType);
////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
////            // TODO: Consider calling
////            //    ActivityCompat#requestPermissions
////            // here to request the missing permissions, and then overriding
////            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////            //                                          int[] grantResults)
////            // to handle the case where the user grants the permission. See the documentation
////            // for ActivityCompat#requestPermissions for more details.
////            return;
////        }
////        tmDevice = "" + tm1.getDeviceId();
////        Log.v("DeviceIMEI", "" + tmDevice);
////        mobileNumber = "" + tm1.getLine1Number();
////        Log.v("getLine1Number value", "" + mobileNumber);
////
////        String mobileNumber1 = "" + tm1.getPhoneType();
////        Log.v("getPhoneType value", "" + mobileNumber1);
////        tmSerial = "" + tm1.getSimSerialNumber();
////        //  Log.v("GSM devices Serial Number[simcard] ", "" + tmSerial);
////        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),
////                android.provider.Settings.Secure.ANDROID_ID);
////        Log.v("androidId CDMA devices", "" + androidId);
////        UUID deviceUuid = new UUID(androidId.hashCode(),
////                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
////        deviceId = deviceUuid.toString();
////        //  Log.v("deviceIdUUID universally unique identifier", "" + deviceId);
////
////
////        deviceModelName = android.os.Build.MODEL;
////        Log.v("Model Name", "" + deviceModelName);
////        deviceUSER = android.os.Build.USER;
////        Log.v("Name USER", "" + deviceUSER);
////        devicePRODUCT = android.os.Build.PRODUCT;
////        Log.v("PRODUCT", "" + devicePRODUCT);
////        deviceHARDWARE = android.os.Build.HARDWARE;
////        Log.v("HARDWARE", "" + deviceHARDWARE);
////        deviceBRAND = android.os.Build.BRAND;
////        Log.v("BRAND", "" + deviceBRAND);
////        myVersion = android.os.Build.VERSION.RELEASE;
////        Log.v("VERSION.RELEASE", "" + myVersion);
////        sdkVersion = android.os.Build.VERSION.SDK_INT;
////        Log.v("VERSION.SDK_INT", "" + sdkVersion);
////        sdkver = Integer.toString(sdkVersion);
////        // Get display details
////
////        Measuredwidth = 0;
////        Measuredheight = 0;
////        Point size = new Point();
////        WindowManager w = getWindowManager();
////
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
////            //   w.getDefaultDisplay().getSize(size);
////            Measuredwidth = w.getDefaultDisplay().getWidth();//size.x;
////            Measuredheight = w.getDefaultDisplay().getHeight();//size.y;
////        } else {
////            Display d = w.getDefaultDisplay();
////            Measuredwidth = d.getWidth();
////            Measuredheight = d.getHeight();
////        }
////
////        Log.v("SCREEN_Width", "" + Measuredwidth);
////        Log.v("SCREEN_Height", "" + Measuredheight);
////
////
////        regId = FirebaseInstanceId.getInstance().getToken();
////
////
////
////        Log.e("regId_DeviceID", "" + regId);
////
/////*<username>string</username>
////      <DeviceId>string</DeviceId>
////      <OSVersion>string</OSVersion>
////      <Manufacturer>string</Manufacturer>
////      <ModelNo>string</ModelNo>
////      <SDKVersion>string</SDKVersion>
////      <DeviceSrlNo>string</DeviceSrlNo>
////      <ServiceProvider>string</ServiceProvider>
////      <SIMSrlNo>string</SIMSrlNo>
////      <DeviceWidth>string</DeviceWidth>
////      <DeviceHeight>string</DeviceHeight>
////      <AppVersion>string</AppVersion>*/
//
//        //if (!regId.equals("")){
//        if (2>1){
//            // String WEBSERVICE_NAME = "http://dfhrms.cloudapp.net/PMSservice.asmx?WSDL";
//            String SOAP_ACTION1 = "http://mis.detedu.org:8089/InsertDeviceDetails";
//            String METHOD_NAME1 = "InsertDeviceDetails";
//            String MAIN_NAMESPACE = "http://mis.detedu.org:8089/";
//          //  String URI = Class_URL.URL_Login.toString().trim();
//
//            String URI = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
//
//
//
//
//
//
//            SoapObject request = new SoapObject(MAIN_NAMESPACE, METHOD_NAME1);
//
//            //	request.addProperty("LeadId", Password1);
//            if(!login_userid.equals("")) {
//                request.addProperty("User_ID", login_userid);//login_userid
//            }
//            request.addProperty("DeviceId", regId);
//            request.addProperty("OSVersion", myVersion);
//            request.addProperty("Manufacturer", deviceBRAND);
//            request.addProperty("ModelNo", deviceModelName);
//            request.addProperty("SDKVersion", sdkver);
//            request.addProperty("DeviceSrlNo", tmDevice);
//            request.addProperty("ServiceProvider", simOperatorName);
//            request.addProperty("SIMSrlNo", tmSerial);
//            request.addProperty("DeviceWidth", Measuredwidth);
//            request.addProperty("DeviceHeight", Measuredheight);
//            request.addProperty("AppVersion", str_versioncode);
//            //request.addProperty("AppVersion","4.0");
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//                    SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            // Set output SOAP object
//            envelope.setOutputSoapObject(request);
//            Log.e("deviceDetails Request","deviceDetail"+request.toString());
//            // Create HTTP call object
//            HttpTransportSE androidHttpTransport = new HttpTransportSE(URI);
//            try {
//
//                androidHttpTransport.call(SOAP_ACTION1, envelope);
//                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
//
//                System.out.println("Device Res"+response);
//
//                Log.i("sending device detail", response.toString());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.i("err",e.toString());
//            }
//        }
//
//
//
//
//
//
//    }//end of GCM()
//
//    public class UserListAsyncTask extends AsyncTask<String, Void, Void> {
//        ProgressDialog dialog;
//
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
//            Log.e("def", "doInBackground");
//
//            UserList();  // call of details
//            return null;
//        }
//
//        public UserListAsyncTask(Context context1) {
//            context = context1;
//            dialog = new ProgressDialog(context1,R.style.AppCompatAlertDialogStyle);
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
//
//            ArrayAdapter<Class_UserList> dataAdapter = new ArrayAdapter<Class_UserList>(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_sandboxDetails);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            UserList_SP.setAdapter(dataAdapter);
//
//
//
//        }//end of onPostExecute
//    }// end Async task
//
//
//    public void UserList() {
//        Vector<SoapObject> result1 = null;
//
//        String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
//        String METHOD_NAME = "UserList";
//        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/UserList";
//
//        try {
//          //  int userid = Integer.parseInt(str_loginuserID);
//            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
//         //   request.addProperty("User_ID", userid);//userid
//
//
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
//                SoapObject obj2 =(SoapObject) response.getProperty(0);
//                Log.e("Userlist resp", "obj2.toString()");
//
//                Log.e("Userlist resp", response.toString());
//                int count1 = response.getPropertyCount();  // number of count in array in response 6,0-5,5
//
//                Log.e("number of rows", "" + count1);
//
//                  arrayObj_Class_sandboxDetails = new Class_UserList[count1];
//
//                for (int i = 0; i < count1; i++) {
//                    SoapObject list = (SoapObject) response.getProperty(i);
//                    SoapPrimitive response_soapobj_studentname,response_soapobj_mobieno, response_soapobj_balancefee,response_soapobj_academicID,
//                            response_soapobj_academic_Name, response_soapobj_clusterid, response_soapobj_clustername, response_soapobj_instituteID,
//                            response_soapobj_instituteName, response_soapobj_levelID, response_soapobj_levelname, response_soapobj_sandboxid,
//                            response_soapobj_sandboxname,response_soapobj_stuID;
//                    String str_aplication_no = "",contactno="";
//
//
//
//                    response_soapobj_sandboxid = (SoapPrimitive) obj2.getProperty("User_ID");
//                    response_soapobj_sandboxname = (SoapPrimitive) obj2.getProperty("User_Email");
//
//                    Class_UserList innerObj_Class_sandbox = new Class_UserList();
//                    //innerObj_Class_sandbox.setUser_id(response_soapobj_sandboxid.toString());
//                   // innerObj_Class_sandbox.setUser_EmailID(response_soapobj_sandboxname.toString());
//                    Log.e("abc",response_soapobj_sandboxname.toString());
//                    String str_userID = list.getProperty("User_ID").toString();
//                    String str_useremaild = list.getProperty("User_Email").toString();
//                    Log.e("str_useremaild",str_useremaild);
//                    innerObj_Class_sandbox.setUser_id(str_userID);
//                    innerObj_Class_sandbox.setUser_EmailID(str_useremaild);
//                    arrayObj_Class_sandboxDetails[i] = innerObj_Class_sandbox;
//
//                }// End of for loop
//
//
//
//
//            } catch (Throwable t) {
//
//                Log.e("requestload fail", "> " + t.getMessage());
//            }
//        } catch (Throwable t) {
//            Log.e("UnRegisterload  Error", "> " + t.getMessage());
//
//        }
//
//    }//End of leaveDetail method
//
//
//}