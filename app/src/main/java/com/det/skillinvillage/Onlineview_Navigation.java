package com.det.skillinvillage;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.det.skillinvillage.adapter.ExpandableListAdapter;
import com.det.skillinvillage.model.DefaultResponse;
import com.det.skillinvillage.model.ErrorUtils;
import com.det.skillinvillage.model.GetMobileMenuResponse;
import com.det.skillinvillage.model.GetMobileMenuResponseList;
import com.det.skillinvillage.model.GetMobileSubMenuResponseList;
import com.det.skillinvillage.remote.Class_ApiUtils;
import com.det.skillinvillage.remote.Interface_userservice;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferencebook_usercredential;

public class Onlineview_Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    ExpandListGroup[] objclassarr_expandedlistgroup;
    Class_SubMenu[] objclassarr_Class_SubMenu;
    ArrayList<ExpandListGroup>mainmenulist;
    //ArrayList<Class_SubMenu>submenuList;

    SharedPreferences sharedpref_loginuserid_Obj;
    String str_loginuserID;

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    GetMobileMenuResponseList[] arrayObj_class_getpaymentpendingresp;
    GetMobileSubMenuResponseList[] arrayObj_class_getMobilesubmenuresp;
    Interface_userservice userService1;
    String str_menulink_userid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlineview__navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
//        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();
        userService1 = Class_ApiUtils.getUserService();

        // sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
//        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();


        sharedpreferencebook_usercredential_Obj=getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);
        str_loginuserID = sharedpreferencebook_usercredential_Obj.getString(key_loginuserid, "").trim();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
//        populateExpandableList();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.openDrawer(GravityCompat.START);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

//    @Override
//    public void onBackPressed() {
//        // super.onBackPressed();
//        Intent i = new Intent(Onlineview_Navigation.this, Activity_HomeScreen.class);
//        startActivity(i);
//        finish();
//    }

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
                Intent i = new Intent(Onlineview_Navigation.this, Activity_HomeScreen.class);
                startActivity(i);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void prepareMenuData() {


        GetMobileMenu();

    }

    public void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
//                        WebView webView = findViewById(R.id.webView);
//                        webView.loadUrl(headerList.get(groupPosition).url);


                        ///////////////////////////////////
                        final ProgressDialog progressDialog = new ProgressDialog(Onlineview_Navigation.this);
                        progressDialog.setMessage("Loading Data...");
                        progressDialog.setCancelable(false);
                        WebView web_view = findViewById(R.id.webView);
                        web_view.requestFocus();
                        web_view.getSettings().setJavaScriptEnabled(true);
                        web_view.getSettings().setUserAgentString(System.getProperty( "http.agent" ));

                        String url = headerList.get(groupPosition).url;

                        //  String url=Class_SaveSharedPreference.getPREF_MENU_link(CDR_Openactivity.this);
                        Log.e("child(click url)",url);

                        web_view.loadUrl(url);

                        web_view.setWebViewClient(new WebViewClient() {
                            @Override
                            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                                Log.e("Failure Url :" , failingUrl);
                            }

                            @Override
                            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                                Log.e("Ssl Error:",handler.toString() + "error:" +  error);
                                handler.proceed();
                            }

                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                view.loadUrl(url);
                                return true;
                            }
                        });
                        web_view.setWebChromeClient(new WebChromeClient() {
                            public void onProgressChanged(WebView view, int progress) {
                                if (progress < 100) {
                                    progressDialog.show();
                                }
                                if (progress == 100) {
                                    progressDialog.dismiss();
                                }
                            }
                        });

                        //////////////////////////////
                        onBackPressed();
                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    if (model.url.length() > 0) {
//                        WebView webView = findViewById(R.id.webView);
//                        webView.loadUrl(model.url);
                        ///////////////////////////////////
                        final ProgressDialog progressDialog = new ProgressDialog(Onlineview_Navigation.this);
                        progressDialog.setMessage("Loading Data...");
                        progressDialog.setCancelable(false);
                        WebView web_view = findViewById(R.id.webView);
                        web_view.requestFocus();
                        web_view.getSettings().setJavaScriptEnabled(true);
                        web_view.getSettings().setUserAgentString(System.getProperty( "http.agent" ));

                        String url = model.url;
                        //  String url=Class_SaveSharedPreference.getPREF_MENU_link(CDR_Openactivity.this);
                        Log.e("child(click url)",url);

                        web_view.loadUrl(url);

                        web_view.setWebViewClient(new WebViewClient() {
                            @Override
                            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                                Log.e("Failure Url :" , failingUrl);
                            }

                            @Override
                            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                                Log.e("Ssl Error:",handler.toString() + "error:" +  error);
                                handler.proceed();
                            }

                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                view.loadUrl(url);
                                return true;
                            }
                        });
                        web_view.setWebChromeClient(new WebChromeClient() {
                            public void onProgressChanged(WebView view, int progress) {
                                if (progress < 100) {
                                    progressDialog.show();
                                }
                                if (progress == 100) {
                                    progressDialog.dismiss();
                                }
                            }
                        });

                        //////////////////////////////
                       // onBackPressed();
                    }
                }

                return false;
            }
        });
    }



    public void GetMobileMenu() {

        Call<GetMobileMenuResponse> call = userService1.GetMobileMenu(str_loginuserID);//String.valueOf(str_stuID)
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Onlineview_Navigation.this);
        //  progressDoalog.setMax(100);
        //  progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<GetMobileMenuResponse>() {
            @Override
            public void onResponse(Call<GetMobileMenuResponse> call, Response<GetMobileMenuResponse> response) {
                Log.e("Entered resp", "GetMobileMenu");

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    GetMobileMenuResponse class_loginresponse = response.body();
                    if (class_loginresponse.getStatus()) {

                        List<GetMobileMenuResponseList> monthContents_list = response.body().getObjMenu();
                        int loadPendingPaymentCount=monthContents_list.size();
                        Log.e("count", String.valueOf(loadPendingPaymentCount));

                        GetMobileMenuResponseList []  arrayObj_Class_monthcontents = new GetMobileMenuResponseList[monthContents_list.size()];
                        arrayObj_class_getpaymentpendingresp = new GetMobileMenuResponseList[arrayObj_Class_monthcontents.length];
                        objclassarr_expandedlistgroup = new ExpandListGroup[arrayObj_class_getpaymentpendingresp.length];
                        mainmenulist = new ArrayList<ExpandListGroup>();

                        for (int i = 0; i < arrayObj_Class_monthcontents.length; i++) {
                            Log.e("GetMobileMenu", String.valueOf(class_loginresponse.getStatus()));
                            Log.e("GetMobileMenu", class_loginresponse.getMessage());

                            GetMobileMenuResponseList innerObj_Class_academic = new GetMobileMenuResponseList();
                            innerObj_Class_academic.setMenuID(class_loginresponse.getObjMenu().get(i).getMenuID());
                            innerObj_Class_academic.setMenuName(class_loginresponse.getObjMenu().get(i).getMenuName());
                            innerObj_Class_academic.setMenuSort(class_loginresponse.getObjMenu().get(i).getMenuSort());
                            innerObj_Class_academic.setSubMenu(class_loginresponse.getObjMenu().get(i).getSubMenu());
                            arrayObj_class_getpaymentpendingresp[i] = innerObj_Class_academic;

                            List<GetMobileSubMenuResponseList> monthContents_list_submenu = response.body().getObjMenu().get(i).getSubMenu();

                            GetMobileSubMenuResponseList []  arrayObj_Class_submenu= new GetMobileSubMenuResponseList[monthContents_list_submenu.size()];

                            //objclassarr_Class_SubMenu = new Class_SubMenu[monthContents_list_submenu.size()];
                            //submenuList = new ArrayList<Class_SubMenu>();
                         //   Log.e("soap_SubMenulength()", String.valueOf(monthContents_list_submenu.size()));
                            arrayObj_class_getMobilesubmenuresp=new GetMobileSubMenuResponseList[arrayObj_Class_submenu.length];
                            //Class_SubMenu child = new Class_SubMenu();
                            MenuModel menuModel = null;
                            List<MenuModel> childModelsList = new ArrayList<>();

                           // Log.e("arraylengthmenu", String.valueOf(arrayObj_class_getMobilesubmenuresp.length));

                            for (int j = 0; j < arrayObj_class_getMobilesubmenuresp.length; j++) {
                                Log.e("entered 2nd for loop", "def");

                                GetMobileSubMenuResponseList innerObj_Class = new GetMobileSubMenuResponseList();
                                innerObj_Class.setMenuID(class_loginresponse.getObjMenu().get(i).getSubMenu().get(j).getMenuID());
                                innerObj_Class.setMenuName(class_loginresponse.getObjMenu().get(i).getSubMenu().get(j).getMenuName());
                                 str_menulink_userid=class_loginresponse.getObjMenu().get(i).getSubMenu().get(j).getMenuLink()+"/id="+str_loginuserID;
                                Log.e("str_menulink",str_menulink_userid);
                                innerObj_Class.setMenuLink(str_menulink_userid);
                                innerObj_Class.setParentID(class_loginresponse.getObjMenu().get(i).getSubMenu().get(j).getParentID());
                               // Log.e("abcd", String.valueOf(class_loginresponse.getObjMenu().get(i).getSubMenu().get(j).getMenuName()));

                                arrayObj_class_getMobilesubmenuresp[j] = innerObj_Class;
//                                Log.e("parentid", String.valueOf(class_loginresponse.getObjMenu().get(i).getSubMenu().get(j).getParentID()));
//                                Log.e("menuID", String.valueOf(class_loginresponse.getObjMenu().get(i).getMenuID()));

                                if (class_loginresponse.getObjMenu().get(i).getSubMenu().get(j).getParentID().equals(class_loginresponse.getObjMenu().get(i).getMenuID())) {
//                                    child.setMenu_ID(String.valueOf(class_loginresponse.getObjMenu().get(j).getSubMenu().get(j).getMenuID()));
//                                    child.setMenu_Name(class_loginresponse.getObjMenu().get(j).getSubMenu().get(j).getMenuName());
//                                    //   Log.e("inside",soap_Menu_Name_submenu.toString());
//                                    child.setMenu_Link(class_loginresponse.getObjMenu().get(j).getSubMenu().get(j).getMenuLink());
//                                    child.setParent_ID(String.valueOf(class_loginresponse.getObjMenu().get(j).getSubMenu().get(j).getParentID()));
//                                    Log.e("menuname",class_loginresponse.getObjMenu().get(i).getSubMenu().get(j).getMenuName());
//                                    Log.e("menulink",class_loginresponse.getObjMenu().get(i).getSubMenu().get(j).getMenuLink());

                                    MenuModel childModel = new MenuModel(class_loginresponse.getObjMenu().get(i).getSubMenu().get(j).getMenuName(), false, false, str_menulink_userid);
                                    childModelsList.add(childModel);


                                } else {

                                }
                               // submenuList.add(child);


                            }
                            menuModel = new MenuModel(class_loginresponse.getObjMenu().get(i).getMenuName(), true, true, ""); //Menu of Java Tutorials
                            headerList.add(menuModel);
//
                            if (menuModel.hasChildren) {
                                Log.d("API123", "here");
                                childList.put(menuModel, childModelsList);
                            }

//                            ExpandListGroup group = new ExpandListGroup();
//                            Log.e("entered", "ExpandListGroup");
//                            group.setMenu_Name(class_loginresponse.getObjMenu().get(i).getMenuName());
//                            Log.e("main", class_loginresponse.getObjMenu().get(i).getMenuName());
//                            // adding child to Group POJO Class
//                            group.setChildItems(submenuList);
//                            mainmenulist.add(group);
//



                        }//for loop end

                        populateExpandableList();
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
                        Toast.makeText(Onlineview_Navigation.this,"Kindly restart your application", Toast.LENGTH_SHORT).show();

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

}
