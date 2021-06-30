package com.det.skillinvillage;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.det.skillinvillage.adapter.MyAdapter;
import com.det.skillinvillage.model.GetMobileMenuResponseList;
import com.det.skillinvillage.model.GetMobileSubMenuResponseList;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;


import java.util.ArrayList;


import static com.det.skillinvillage.MainActivity.key_loginuserid;
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

//        if (isInternetPresent) {
//            BackTask task = new BackTask(Activity_OnlineView_MainmenuList.this);
//            task.execute();
//        }else{
//            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
//
//        }
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