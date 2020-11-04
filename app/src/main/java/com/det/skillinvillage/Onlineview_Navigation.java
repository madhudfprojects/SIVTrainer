package com.det.skillinvillage;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;

public class Onlineview_Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    ExpandListGroup[] objclassarr_expandedlistgroup;
    Class_SubMenu[] objclassarr_Class_SubMenu;
    ArrayList<ExpandListGroup>mainmenulist;
    ArrayList<Class_SubMenu>submenuList;

    SharedPreferences sharedpref_loginuserid_Obj;
    String str_loginuserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlineview__navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

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

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent i = new Intent(Onlineview_Navigation.this, Activity_HomeScreen.class);
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
                Intent i = new Intent(Onlineview_Navigation.this, Activity_HomeScreen.class);
                startActivity(i);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.onlineview__navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
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

    private void prepareMenuData() {
        BackTask task = new BackTask(Onlineview_Navigation.this);
        task.execute();


//        MenuModel menuModel = new MenuModel("Android WebView Tutorial", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
//        headerList.add(menuModel);
//
//        if (!menuModel.hasChildren) {
//            childList.put(menuModel, null);
//        }
//
//        menuModel = new MenuModel("Java Tutorials", true, true, ""); //Menu of Java Tutorials
//        headerList.add(menuModel);
//        List<MenuModel> childModelsList = new ArrayList<>();
//        MenuModel childModel = new MenuModel("Core Java Tutorial", false, false, "https://www.journaldev.com/7153/core-java-tutorial");
//        childModelsList.add(childModel);
//
//        childModel = new MenuModel("Java FileInputStream", false, false, "https://www.journaldev.com/19187/java-fileinputstream");
//        childModelsList.add(childModel);
//
//        childModel = new MenuModel("Java FileReader", false, false, "https://www.journaldev.com/19115/java-filereader");
//        childModelsList.add(childModel);
//
//
//        if (menuModel.hasChildren) {
//            Log.d("API123","here");
//            childList.put(menuModel, childModelsList);
//        }
//
//        childModelsList = new ArrayList<>();
//        menuModel = new MenuModel("Python Tutorials", true, true, ""); //Menu of Python Tutorials
//        headerList.add(menuModel);
//        childModel = new MenuModel("Python AST – Abstract Syntax Tree", false, false, "https://www.journaldev.com/19243/python-ast-abstract-syntax-tree");
//        childModelsList.add(childModel);
//
//        childModel = new MenuModel("Python Fractions", false, false, "https://www.journaldev.com/19226/python-fractions");
//        childModelsList.add(childModel);
//
//        if (menuModel.hasChildren) {
//            childList.put(menuModel, childModelsList);
//        }
    }

    private void populateExpandableList() {

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
                        onBackPressed();
                    }
                }

                return false;
            }
        });
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
            populateExpandableList();
//            MyAdapter   adapter = new MyAdapter(mainmenulist, Onlineview_Navigation.this);
//            expandableListView.setAdapter(adapter);

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
                if (response.toString().equalsIgnoreCase("anyType{}") || response.toString().equalsIgnoreCase("") || response.toString().equalsIgnoreCase(null)) {

                    Toast.makeText(getApplicationContext(),"No data Found",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Onlineview_Navigation.this, Activity_HomeScreen.class);
                    startActivity(i);
                    finish();
                } else {
                    int count1 = response.getPropertyCount();  // number of count in array in response 6,0-5,5
                    Log.i("number of rows", "" + count1);
                    objclassarr_expandedlistgroup = new ExpandListGroup[count1];
                    mainmenulist = new ArrayList<ExpandListGroup>();


                    SoapPrimitive soap_Menu_Name_submenu = null;
                    String str_soap_Menu_Link_submenu = "";
                    for (int i = 0; i < objclassarr_expandedlistgroup.length; i++) {
                        Log.e("entered 1st for loop", "abc");

                        SoapPrimitive soap_menuname, soap_parentid, soap_Menu_ID, soap_Menu_SORT;
                        SoapObject obj2 = (SoapObject) response.getProperty(i);
                        soap_Menu_ID = (SoapPrimitive) obj2.getProperty("Menu_ID");
                        soap_menuname = (SoapPrimitive) obj2.getProperty("Menu_Name");
                        soap_Menu_SORT = (SoapPrimitive) obj2.getProperty("Menu_Sort");
                        SoapObject soap_SubMenu = (SoapObject) obj2.getProperty("SubMenu");
                        int count2 = soap_SubMenu.getPropertyCount();  // number of count in array in response 6,0-5,5

                        objclassarr_Class_SubMenu = new Class_SubMenu[count2];
                        submenuList = new ArrayList<Class_SubMenu>();
                        MenuModel menuModel = null;
                        List<MenuModel> childModelsList = new ArrayList<>();

                        Log.e("soap_SubMenulength()", String.valueOf(count2));


                        for (int j = 0; j < objclassarr_Class_SubMenu.length; j++) {
                            SoapObject obj3 = (SoapObject) soap_SubMenu.getProperty(j);
                            SoapPrimitive soap_Menu_ID_submenu = (SoapPrimitive) obj3.getProperty("Menu_ID");
                            soap_Menu_Name_submenu = (SoapPrimitive) obj3.getProperty("Menu_Name");
                            SoapPrimitive soap_Menu_Link_submenu = (SoapPrimitive) obj3.getProperty("Menu_Link");
                            SoapPrimitive soap_Parent_ID_submenu = (SoapPrimitive) obj3.getProperty("Parent_ID");
                            str_soap_Menu_Link_submenu = soap_Menu_Link_submenu.toString() + "/" + userid;
//                        menuModel = new MenuModel(soap_menuname.toString(), true, true, ""); //Menu of Java Tutorials
//                        headerList.add(menuModel);
////                        List<MenuModel> childModelsList = new ArrayList<>();
                            if (soap_Parent_ID_submenu.toString().equals(soap_Menu_ID.toString())) {

                                MenuModel childModel = new MenuModel(soap_Menu_Name_submenu.toString(), false, false, str_soap_Menu_Link_submenu);
                                childModelsList.add(childModel);
//                            if (menuModel.hasChildren) {
//                                Log.d("API123", "here");
//                                childList.put(menuModel, childModelsList);
//                            }
                            }


                        }
                        menuModel = new MenuModel(soap_menuname.toString(), true, true, ""); //Menu of Java Tutorials
                        headerList.add(menuModel);
//
                        if (menuModel.hasChildren) {
                            Log.d("API123", "here");
                            childList.put(menuModel, childModelsList);
                        }

//                    childModel = new MenuModel("Java FileInputStream", false, false, "https://www.journaldev.com/19187/java-fileinputstream");
//                    childModelsList.add(childModel);
//
//                    childModel = new MenuModel("Java FileReader", false, false, "https://www.journaldev.com/19115/java-filereader");
//                    childModelsList.add(childModel);
//


                        ////////////////////////////////////////////////////
                    }
                }
            }catch(Throwable t){
                    Log.e("requestload fail", "> " + t.getMessage());
                }

            } catch (Throwable t) {
                Log.e("UnRegisterload  Error", "> " + t.getMessage());

            }


    }//End of leaveDetail method

}
