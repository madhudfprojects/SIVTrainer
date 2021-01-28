package com.det.skillinvillage;


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
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.det.skillinvillage.adapter.MyAdapter;
import com.det.skillinvillage.model.Class_GetPendingPaymentResponseList;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.model.GetMobileMenuResponse;
import com.det.skillinvillage.model.GetMobileMenuResponseList;
import com.det.skillinvillage.model.GetMobileSubMenuResponseList;
import com.det.skillinvillage.model.GetPendingPaymentResponse;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferencebook_usercredential;

public class Activity_OnlineView_MainmenuList extends AppCompatActivity implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {
    ExpandableListView lv_onlineview;
    ArrayList<ExpandListGroup>mainmenulist;
    ArrayList<Class_SubMenu>submenuList;
    ExpandListGroup[] objclassarr_expandedlistgroup;
    Class_SubMenu[] objclassarr_Class_SubMenu;
    String str_loginuserID="";
    SharedPreferences sharedpref_loginuserid_Obj;
    private MyAdapter adapter;
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    Interface_userservice userService1;
    SharedPreferences sharedpreferencebook_usercredential_Obj;
    GetMobileMenuResponseList[] arrayObj_class_getpaymentpendingresp;
    GetMobileSubMenuResponseList[] arrayObj_class_getMobilesubmenuresp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__online_view__mainmenu_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Online View");
        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();
        userService1 = Class_ApiUtils.getUserService();

       // sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
//        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();


        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_loginuserID = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();


        lv_onlineview=(ExpandableListView)findViewById(R.id.lv_onlineview);

        lv_onlineview.setOnChildClickListener(this);
        lv_onlineview.setOnGroupClickListener(this);

        lv_onlineview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition)
            {
                if(groupPosition != previousItem )
                    lv_onlineview.collapseGroup(previousItem );
                    previousItem = groupPosition;

            }
        });

        if (isInternetPresent) {
            BackTask task = new BackTask(Activity_OnlineView_MainmenuList.this);
            task.execute();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        if (childPosition == 0) {

        } else {
            String child_value_ideaid = ((Class_SubMenu) adapter.getChild(groupPosition,childPosition-1)).getMenu_Link();
            Class_SaveSharedPreference.setPREF_MENU_link(Activity_OnlineView_MainmenuList.this, child_value_ideaid);
            String parent_value = ((Class_SubMenu) adapter.getChild(groupPosition,childPosition-1)).getMenu_Link();
            Log.e("child_value_ideaid",parent_value);
            Intent i = new Intent(Activity_OnlineView_MainmenuList.this, CDR_Openactivity.class);
            startActivity(i);
        }

        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
    }

    public class BackTask extends AsyncTask<String, Void, Void> {
        Context context;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            list_detaile();
            //return HttpULRConnect.getData(url);
            return null;
        }

        public BackTask(Context context1) {
            context = context1;
          //  dialog = new ProgressDialog(context1,R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected void onPostExecute(Void s) {
            adapter = new MyAdapter(mainmenulist, Activity_OnlineView_MainmenuList.this);
            lv_onlineview.setAdapter(adapter);

        }

    }

    public void list_detaile() {
        Vector<SoapObject> result1 = null;
        String url ="http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String METHOD_NAME = "LoadMobileMenu";
        String Namespace = "http://mis.detedu.org:8089/", SOAPACTION = "http://mis.detedu.org:8089/LoadMobileMenu";

        try {
            int userid = Integer.parseInt(str_loginuserID);
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("User_ID", userid);//userid


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(url);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();

                Log.i("value at response", response.toString());
                int  count1 = response.getPropertyCount();  // number of count in array in response 6,0-5,5
                Log.i("number of rows", "" + count1);
                objclassarr_expandedlistgroup = new ExpandListGroup[count1];
                mainmenulist = new ArrayList<ExpandListGroup>();




                for (int i = 0; i<objclassarr_expandedlistgroup.length; i++) {
                    Log.e("entered 1st for loop","abc" );

                    SoapPrimitive soap_menuname,soap_parentid,soap_Menu_ID,soap_Menu_SORT;
                    SoapObject obj2 =(SoapObject) response.getProperty(i);
                    soap_Menu_ID = (SoapPrimitive) obj2.getProperty("Menu_ID");
                    soap_menuname = (SoapPrimitive) obj2.getProperty("Menu_Name");
                    soap_Menu_SORT = (SoapPrimitive) obj2.getProperty("Menu_Sort");
                    SoapObject   soap_SubMenu = (SoapObject) obj2.getProperty("SubMenu");
                    int  count2 = soap_SubMenu.getPropertyCount();  // number of count in array in response 6,0-5,5

                    objclassarr_Class_SubMenu = new Class_SubMenu[count2];
                    submenuList = new ArrayList<Class_SubMenu>();
                    Log.e("soap_SubMenulength()", String.valueOf(count2));

                    for (int j = 0; j < objclassarr_Class_SubMenu.length; j++) {
                        Log.e("entered 2nd for loop", "def");
                        SoapObject   obj3 = (SoapObject) soap_SubMenu.getProperty(j);
                        SoapPrimitive soap_Menu_ID_submenu = (SoapPrimitive) obj3.getProperty("Menu_ID");
                        SoapPrimitive soap_Menu_Name_submenu = (SoapPrimitive) obj3.getProperty("Menu_Name");
                        SoapPrimitive soap_Menu_Link_submenu = (SoapPrimitive) obj3.getProperty("Menu_Link");
                        SoapPrimitive soap_Parent_ID_submenu = (SoapPrimitive) obj3.getProperty("Parent_ID");
                        String str_soap_Menu_Link_submenu=soap_Menu_Link_submenu.toString()+"/"+userid;
                        Log.e("soap_Menu_ID1()", str_soap_Menu_Link_submenu);
                          Class_SubMenu child = new Class_SubMenu();
                          if(soap_Parent_ID_submenu.toString().equals(soap_Menu_ID.toString())) {
                            child.setMenu_ID(soap_Menu_ID_submenu.toString());
                            child.setMenu_Name(soap_Menu_Name_submenu.toString());
                            Log.e("inside",soap_Menu_Name_submenu.toString());
                            child.setMenu_Link(str_soap_Menu_Link_submenu);
                            child.setParent_ID(soap_Parent_ID_submenu.toString());
                        }else{

                        }
                        submenuList.add(child);
                    }

                    ExpandListGroup group = new ExpandListGroup();
                    Log.e("entered", "ExpandListGroup");
                    group.setMenu_Name(soap_menuname.toString());
                    Log.e("main", soap_menuname.toString());
                    // adding child to Group POJO Class
                    group.setChildItems(submenuList);
                    mainmenulist.add(group);
                }
            } catch (Throwable t) {
                Log.e("requestload fail", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("UnRegisterload  Error", "> " + t.getMessage());

        }

    }//End of leaveDetail method





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {


            case android.R.id.home:
                Intent i = new Intent(Activity_OnlineView_MainmenuList.this, Activity_HomeScreen.class);
                startActivity(i);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Activity_OnlineView_MainmenuList.this, Activity_HomeScreen.class);
        startActivity(i);
        finish();

    }
}