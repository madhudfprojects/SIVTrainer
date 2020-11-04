package com.det.skillinvillage.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.det.skillinvillage.Class_SubMenu;
import com.det.skillinvillage.ExpandListGroup;
import com.det.skillinvillage.R;

import java.util.ArrayList;


public class MyAdapter extends BaseExpandableListAdapter
{
	ArrayList<ExpandListGroup> groups;
	Context mContext;
	
	public MyAdapter(ArrayList<ExpandListGroup> groups, Context mContext) {
		this.groups = groups;
		this.mContext = mContext;
	}
	
	public Object getChild(int groupPosition, int childPosition) {
			return groups.get(groupPosition).getChildItems().get(childPosition);

	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	class ChildViewHolder {
		TextView expandedListItem_ideadate;
		TextView expandedListItem_ideatitle_TV;
		TextView expandedListItem_ideastatus;
		TextView expandedListItem_id;


		public ChildViewHolder(View convertView) {

		}
	}
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(childPosition == 0)return convertView = infalInflater.inflate(R.layout.childheader_zero, null);
		ChildViewHolder holder = null;
		Class_SubMenu child = (Class_SubMenu) getChild(groupPosition, childPosition-1);
		//Class_SubMenu child = (Class_SubMenu) getChild(groupPosition, childPosition);

		if(convertView!=null){
			holder = (ChildViewHolder) convertView.getTag();
		}
		if(holder == null){
			convertView = infalInflater.inflate(R.layout.submenu_item, null);
			holder = new ChildViewHolder(convertView);
			convertView.setTag(holder);
		}
		holder.expandedListItem_ideadate = convertView
				.findViewById(R.id.expandedListItem_ideadate);
//		holder.expandedListItem_ideatitle_TV = convertView
//				.findViewById(R.id.expandedListItem_ideatitle_TV);
//
//		holder.expandedListItem_ideastatus = convertView
//				.findViewById(R.id.expandedListItem_ideastatus);
//		holder.expandedListItem_id = convertView
//				.findViewById(R.id.expandedListItem_id);

		if (groupPosition % 2 == 1) {
			convertView.setBackgroundColor(Color.parseColor("#C0DEF3"));
		} else {
			convertView.setBackgroundColor(Color.parseColor("#94B9D3"));

		}

		if(holder.expandedListItem_ideadate != null) {
			Log.e("child.getMenu_Name()",child.getMenu_Name());
			holder.expandedListItem_ideadate.setText(child.getMenu_Name());
		}
//		if(holder.expandedListItem_ideastatus != null) {
//			holder.expandedListItem_ideastatus.setText(child.getIdeaStatus());
//		}
//		if(holder.expandedListItem_ideatitle_TV != null) {
//			holder.expandedListItem_ideatitle_TV.setText(child.getTitle());
//		}
//		if(holder.expandedListItem_id != null) {
//			holder.expandedListItem_id.setText(String.valueOf(child.getId()));
//		}
		return convertView;

		//////////////////////////////////////////////////////////////
//		Class_ideaslist child = (Class_ideaslist) getChild(groupPosition, childPosition);
//		if (convertView == null) {
//			LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			convertView = infalInflater.inflate(R.layout.list_item, null);
//		}
//		TextView expandedListItem_ideadate = convertView
//				.findViewById(R.id.expandedListItem_ideadate);
//		TextView expandedListItem_ideatitle_TV = convertView
//				.findViewById(R.id.expandedListItem_ideatitle_TV);
//
//		TextView expandedListItem_ideastatus = convertView
//				.findViewById(R.id.expandedListItem_ideastatus);
//		TextView expandedListItem_id = convertView
//				.findViewById(R.id.expandedListItem_id);
//
//		if (groupPosition % 2 == 1) {
//			convertView.setBackgroundColor(Color.parseColor("#C0DEF3"));
//		} else {
//			convertView.setBackgroundColor(Color.parseColor("#94B9D3"));
//
//		}
//
//		if(expandedListItem_ideadate != null) {
//			expandedListItem_ideadate.setText(child.getDate());
//		}
//		if(expandedListItem_ideastatus != null) {
//			expandedListItem_ideastatus.setText(child.getIdeaStatus());
//		}
//		if(expandedListItem_ideatitle_TV != null) {
//			expandedListItem_ideatitle_TV.setText(child.getTitle());
//		}
//		if(expandedListItem_id != null) {
//			expandedListItem_id.setText(String.valueOf(child.getId()));
//		}
//
//		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).getChildItems().size()+1;
		//return groups.get(groupPosition).getChildItems().size();
	}

	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	public int getGroupCount() {
		return groups.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		ExpandListGroup group = (ExpandListGroup) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater inf = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inf.inflate(R.layout.list_group, null);
		}
		if (groupPosition % 2 == 1) {
			convertView.setBackgroundColor(Color.parseColor("#04548d"));
			//convertView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
		} else {
			convertView.setBackgroundColor(Color.parseColor("#04548d"));

			// convertView.setBackgroundColor(Color.CYAN);
		}

		TextView listTitleTextView = convertView
				.findViewById(R.id.listTitle);
//		TextView listTitleTextView_count = convertView
//				.findViewById(R.id.impactarea_myidea_count_tv);

		//count_impactarea
		listTitleTextView.setTypeface(null, Typeface.BOLD);
	//	listTitleTextView_count.setTypeface(null, Typeface.BOLD);
		listTitleTextView.setText(group.getMenu_Name());
//		listTitleTextView_count.setText(group.getImpactareaCount());
//		listTitleTextView_count.setTypeface(null, Typeface.BOLD);
		return convertView;
	}

	public boolean hasStableIds() {
		return false;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
/*
package com.det.skillinvillage;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.data.HttpUrlFetcher;
import com.det.skillinvillage.adapter.Class_SandBoxDetails;
import com.det.skillinvillage.adapter.FlowerAdapter;
import com.det.skillinvillage.adapter.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import static com.det.skillinvillage.MainActivity.key_loginuserid;
import static com.det.skillinvillage.MainActivity.sharedpreferenc_loginuserid;

public class Activity_OnlineView_MainmenuList extends AppCompatActivity implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {

    ExpandableListView lv_onlineview;
    String[] listItem;

    ArrayList<ExpandListGroup>mainmenulist;
    ArrayList<Class_SubMenu>submenuList;
    String url ="http://mis.detedu.org:8089/SIVService.asmx?WSDL";
    ExpandListGroup[] objclassarr_expandedlistgroup;
    Class_SubMenu[] objclassarr_Class_SubMenu;
    String str_loginuserID="";
    SharedPreferences sharedpref_loginuserid_Obj;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__online_view__mainmenu_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Online View");
       // new BackTask().execute(url);
        sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

        lv_onlineview=(ExpandableListView)findViewById(R.id.lv_onlineview);

        BackTask task = new BackTask(Activity_OnlineView_MainmenuList.this);
        task.execute();
        lv_onlineview.setOnChildClickListener(this);
        lv_onlineview.setOnGroupClickListener(this);

        //listItem = getResources().getStringArray(R.array.array_technology);

//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
//        lv_onlineview.setAdapter(adapter);
//        lv_onlineview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        if (childPosition == 0) {

        } else {
          //  String parent_value = ((ExpandListGroup) adapter.getGroup(groupPosition)).getName();
            String child_value = ((Class_SubMenu) adapter.getChild(groupPosition, childPosition-1)).getMenu_ID();
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
//            FlowerAdapter adapter = new FlowerAdapter(Activity_OnlineView_MainmenuList.this, R.layout.flowers_list_items, objclassarr_expandedlistgroup);
//            lv_onlineview.setAdapter(adapter);
             adapter = new MyAdapter(mainmenulist, Activity_OnlineView_MainmenuList.this);
            lv_onlineview.setAdapter(adapter);

        }

    }

    public void list_detaile() {
        Vector<SoapObject> result1 = null;

        //String URL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
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

                    SoapPrimitive soap_menuname,soap_parentid,soap_Menu_ID,soap_SubMenu,soap_Menu_SORT;
                    SoapObject obj2 =(SoapObject) response.getProperty(i);
                    soap_Menu_ID = (SoapPrimitive) obj2.getProperty("Menu_ID");
                    soap_menuname = (SoapPrimitive) obj2.getProperty("Menu_Name");
                    soap_Menu_SORT = (SoapPrimitive) obj2.getProperty("Menu_Sort");

//                    SoapObject obj3 =(SoapObject) obj2.getProperty("SubMenu");

//                     soap_SubMenu = (SoapPrimitive) obj3.getProperty("SubMenu");
                    //String[] str_soap_SubMenu= new String[soap_SubMenu.toString().length()];
                    objclassarr_Class_SubMenu = new Class_SubMenu[4];
                    submenuList = new ArrayList<Class_SubMenu>();
                   // Log.e("soap_SubMenulength()", String.valueOf(soap_SubMenu.toString().length()));


                    for (int j = 0; j < objclassarr_Class_SubMenu.length; j++) {
                        Log.e("entered 2nd for loop", "def");
                        SoapObject obj3 =(SoapObject) obj2.getProperty(j);
                        SoapPrimitive soap_menuname_sub, soap_parentid_sub, soap_Menu_ID_sub, soap_Menu_Link_sub, soap_Menu_SORT_sub;

                        // SoapObject obj3 =(SoapObject) obj2.getProperty("SubMenu");
                        for (int k = 0; k < objclassarr_Class_SubMenu.length; k++) {
                            SoapObject obj4 =(SoapObject) obj3.getProperty(k);

                           // SoapObject obj2_sub = (SoapObject)obj3.getProperty(k);
                            // SoapObject obj2_sub = (SoapObject) ((SoapObject) envelope.getResponse()).getProperty(j);

                            soap_menuname_sub = (SoapPrimitive) obj4.getProperty("Menu_Name");
//                        soap_parentid_sub = (SoapPrimitive) obj2_sub.getProperty("Parent_ID");
//                        soap_Menu_ID_sub = (SoapPrimitive) obj2_sub.getProperty("Menu_ID");
//                        soap_Menu_Link_sub = (SoapPrimitive) obj2_sub.getProperty("Menu_Link");
                            //soap_Menu_SORT_sub = (SoapPrimitive) obj2_sub.getProperty("Menu_Sort");

                            Class_SubMenu child = new Class_SubMenu();
                            Log.e("soap_parentid_sub", soap_menuname_sub.toString());
                            Log.e("soap_Menu_ID", soap_Menu_ID.toString());
//                        if(soap_parentid_sub.toString().equals(soap_Menu_ID.toString())) {
//                            child.setMenu_ID(soap_Menu_ID_sub.toString());
//                            child.setMenu_Name(soap_menuname_sub.toString());
//                            Log.e("soap_menuname_sub",soap_menuname_sub.toString());
//                            child.setMenu_Link(soap_Menu_Link_sub.toString());
//                            child.setParent_ID(soap_parentid_sub.toString());
////                        child.setDate(Arraylist_class_ideaslist.get(j).getDate());
////                        child.setIdeaStatus(Arraylist_class_ideaslist.get(j).getIdeaStatus());
////                        child.setId(Arraylist_class_ideaslist.get(j).getId());
//                            submenuList.add(child);
//                        }else{
//                            Log.e("entered childelse","child");
//
//                        }

                        }
                    }
                    ExpandListGroup group = new ExpandListGroup();
                    Log.e("entered", "ExpandListGroup");
                    group.setMenu_Name(soap_menuname.toString());


                  //  group.setImpactareaCount(String.valueOf(Arraylist_class_ideaslist.size()));
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
        menu.findItem(R.id.Sync)
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

//    private ArrayList<ExpandListGroup> fillData() {
//        Arraylist_class_impactideaslist = class_loginresponse.getImpactideas();
//        arrayObj_Class_impactideas = new Class_impactideaslist[Arraylist_class_impactideaslist.size()];
//        Log.e("filldata.....", String.valueOf(arrayObj_Class_impactideas.length));
//
//        for (int i = 0; i < arrayObj_Class_impactideas.length; i++) {
//            Log.e("filldata.....", "insideforloop");
//
//            childs = new ArrayList<Class_MainMenu>();
//            Arraylist_class_ideaslist = class_loginresponse.getImpactideas().get(i).getIdeas();
//            arrayObj_Class_ideas = new Class_ideaslist[Arraylist_class_ideaslist.size()];
//
//            for (int j = 0; j < arrayObj_Class_ideas.length; j++) {
//                Class_ideaslist child = new Class_ideaslist();
//                child.setTitle(Arraylist_class_ideaslist.get(j).getTitle());
//                child.setDate(Arraylist_class_ideaslist.get(j).getDate());
//                child.setIdeaStatus(Arraylist_class_ideaslist.get(j).getIdeaStatus());
//                child.setId(Arraylist_class_ideaslist.get(j).getId());
//                childs.add(child);
//
//            }
//            // setting values to Group POJO Class
//            ExpandListGroup group = new ExpandListGroup();
//            //  group.setName(class_loginresponse.getImpactideas().get(i).getImpactAreadCode()+" - "+Arraylist_class_ideaslist.size());
//            group.setName(class_loginresponse.getImpactideas().get(i).getImpactAreadCode());
//
//            group.setImpactareaCount(String.valueOf(Arraylist_class_ideaslist.size()));
//            // adding child to Group POJO Class
//            group.setChildItems(childs);
//
//
//
//
//            groups.add(group);
//        }
//
//
//        return groups;
//
//    }

}
 */