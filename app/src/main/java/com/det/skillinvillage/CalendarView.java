package com.det.skillinvillage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.det.skillinvillage.adapter.AndroidListAdapter;
import com.det.skillinvillage.adapter.CalendarAdapter;
import com.det.skillinvillage.util.UserInfoListRest;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarView extends AppCompatActivity {

	public GregorianCalendar month, itemmonth,cal_month_copy;// calendar instances.
	private CalendarAdapter cal_adapter;

	public CalendarAdapter adapter;// adapter instance
	public Handler handler;// for grabbing some event values for showing the dot
							// marker.
	public ArrayList<String> items; // container to store calendar items which
									// needs showing the event marker

	private Toolbar mTopToolbar;
	String u1,p1;

	private ListView lv_android;
	private AndroidListAdapter list_adapter;

	// ArrayList<EventModule> eventModules=new ArrayList<>();

	String bookid,cohartName,fellowshipName,eventdate,start_time,userId,status,end_time,module_name,attendance;
	Boolean eventUpdate;
	ArrayList<String> bookId1;
	ArrayList<String> eventdate1;
	int sizearray;
	private SwipeRefreshLayout swipeLayout;
	ConnectionDetector internetDectector;
	Boolean isInternetPresent = false;
	//Button logout_bt;
	String Schedule_Status,Schedule_ID,Lavel_ID,Schedule_Date,End_Time,Start_Time,Schedule_Session,Subject_Name,Leason_Name,Lavel_Name,Cluster_Name,Institute_Name;
	/*ArrayList<UserInfo> arrayList = new ArrayList<UserInfo>();
	UserInfo[] userInfosarr;*/
	String str_loginuserID;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		/*mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
		setSupportActionBar(mTopToolbar);
*/
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Schedules");

		 Locale.setDefault( Locale.US );
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		cal_month_copy = (GregorianCalendar) month.clone();

		itemmonth = (GregorianCalendar) month.clone();

		SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
		str_loginuserID = myprefs.getString("login_userid", "nothing");

		items = new ArrayList<String>();
		adapter = new CalendarAdapter(this, month, UserInfoListRest.user_info_arr);

		GridView gridview = findViewById(R.id.gv_calendar);
		gridview.setAdapter(adapter);

		/*handler = new Handler();
		handler.post(calendarUpdater);*/

		TextView title = findViewById(R.id.tv_month);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

		RelativeLayout previous = findViewById(R.id.previous);

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});

		RelativeLayout next = findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();

			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

				((CalendarAdapter) parent.getAdapter()).setSelected(v,position);
				String selectedGridDate = CalendarAdapter.day_string
						.get(position);
				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*",
						"");// taking last part of date. ie; 2 from 2012-12-02.
				int gridvalue = Integer.parseInt(gridvalueString);
				// navigate to next or previous month on clicking offdays.
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				((CalendarAdapter) parent.getAdapter()).setSelected(v,position);
				((CalendarAdapter) parent.getAdapter()).getPositionList(selectedGridDate, CalendarView.this);

			//	showToast(selectedGridDate);

			}
		});
	}

	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) + 1);
		}

	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}

	}

	protected void showToast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

	}

	public void refreshCalendar() {
		TextView title = findViewById(R.id.tv_month);

		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		//handler.post(calendarUpdater); // generate some calendar items

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}

	/*public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();

			// Print dates of the current week
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
			String itemvalue;
			for (int i = 0; i < 7; i++) {
				itemvalue = df.format(itemmonth.getTime());
				itemmonth.add(GregorianCalendar.DATE, 1);
				items.add("2019-09-12");
				items.add("2019-10-07");
				items.add("2019-10-15");
				items.add("2019-10-20");
				items.add("2019-11-30");
				items.add("2019-11-28");
			}

			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};*/

	@Override
	public void onBackPressed() {


    /*    Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }*/

		Intent i = new Intent(CalendarView.this, Activity_HomeScreen.class);
		startActivity(i);

		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedule_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.share) {
			// Toast.makeText(CalenderActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
			internetDectector = new ConnectionDetector(getApplicationContext());
			isInternetPresent = internetDectector.isConnectingToInternet();

			if (isInternetPresent) {

				Class_SaveSharedPreference.setUserName(getApplicationContext(),"");

				deleteSandBoxTable_B4insertion();
				deleteAcademicTable_B4insertion();
				deleteClusterTable_B4insertion();
				deleteInstituteTable_B4insertion();
				deleteSchoolTable_B4insertion();
				deleteLevelTable_B4insertion();
				deleteLearningOptionTable_B4insertion();

				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				i.putExtra("logout_key1", "yes");
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(i);
				finish();

//                Intent i = new Intent(getApplicationContext(), NormalLogin.class);
//                i.putExtra("logout_key1", "yes");
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(i);

				//}
			}
			else{
				Toast.makeText(getApplicationContext(),"No Internet", Toast.LENGTH_SHORT).show();
			}
			return true;
		}
		if(id== android.R.id.home) {
			//  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(CalendarView.this, Activity_HomeScreen.class);
			startActivity(i);
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	public void deleteSandBoxTable_B4insertion() {

		SQLiteDatabase db_sandboxtable_delete = openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
		db_sandboxtable_delete.execSQL("CREATE TABLE IF NOT EXISTS SandBoxList(SandID VARCHAR,SandName VARCHAR);");
		Cursor cursor = db_sandboxtable_delete.rawQuery("SELECT * FROM SandBoxList", null);
		int x = cursor.getCount();

		if (x > 0) {
			db_sandboxtable_delete.delete("SandBoxList", null, null);
			// Toast.makeText(getApplicationContext(),"All records deleted",Toast.LENGTH_LONG).show();
		}
		db_sandboxtable_delete.close();
	}

	public void deleteAcademicTable_B4insertion() {

		SQLiteDatabase db1 = openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
		db1.execSQL("CREATE TABLE IF NOT EXISTS AcademicList(AcaID VARCHAR,AcaName VARCHAR,ASandID VARCHAR);");
		Cursor cursor1 = db1.rawQuery("SELECT * FROM AcademicList", null);
		int x = cursor1.getCount();

		if (x > 0) {
			db1.delete("AcademicList", null, null);

		}
		db1.close();
	}

	public void deleteClusterTable_B4insertion() {

		SQLiteDatabase db_clustertable_delete = openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
		db_clustertable_delete.execSQL("CREATE TABLE IF NOT EXISTS ClusterList(clustID VARCHAR,clustName VARCHAR,clust_sand_ID VARCHAR,clust_aca_ID VARCHAR);");
		Cursor cursor = db_clustertable_delete.rawQuery("SELECT * FROM ClusterList", null);
		int x = cursor.getCount();

		if (x > 0) {
			db_clustertable_delete.delete("ClusterList", null, null);

		}
		db_clustertable_delete.close();
	}

	public void deleteInstituteTable_B4insertion() {

		SQLiteDatabase db_inst_delete = openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
		db_inst_delete.execSQL("CREATE TABLE IF NOT EXISTS InstituteList(inst_ID VARCHAR,inst_Name VARCHAR,inst_sand_ID VARCHAR,inst_aca_ID VARCHAR,inst_clust_ID VARCHAR);");
		Cursor cursor = db_inst_delete.rawQuery("SELECT * FROM InstituteList", null);
		int x = cursor.getCount();

		if (x > 0) {
			db_inst_delete.delete("InstituteList", null, null);
			// Toast.makeText(getApplicationContext(),"All records deleted",Toast.LENGTH_LONG).show();
		}
		db_inst_delete.close();
	}

	public void deleteSchoolTable_B4insertion() {

		SQLiteDatabase db_school_delete = openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
		db_school_delete.execSQL("CREATE TABLE IF NOT EXISTS SchoolList(school_ID VARCHAR,school_Name VARCHAR,school_sand_ID VARCHAR,school_aca_ID VARCHAR,school_clust_ID VARCHAR);");
		Cursor cursor = db_school_delete.rawQuery("SELECT * FROM SchoolList", null);
		int x = cursor.getCount();

		if (x > 0) {
			db_school_delete.delete("SchoolList", null, null);
		}
		db_school_delete.close();
	}

	public void deleteLevelTable_B4insertion() {

		SQLiteDatabase db_leveldelete = openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
		db_leveldelete.execSQL("CREATE TABLE IF NOT EXISTS LevelList(LevelID VARCHAR,LevelName VARCHAR,Lev_SandID VARCHAR,Lev_AcaID VARCHAR,Lev_ClustID VARCHAR,Lev_InstID VARCHAR,Lev_AdmissionFee VARCHAR);");
		Cursor cursor = db_leveldelete.rawQuery("SELECT * FROM LevelList", null);
		int x = cursor.getCount();

		if (x > 0) {
			db_leveldelete.delete("LevelList", null, null);

		}
		db_leveldelete.close();
	}
	public void deleteLearningOptionTable_B4insertion() {

		SQLiteDatabase db_LearningOption_delete = openOrCreateDatabase("sivdb", Context.MODE_PRIVATE, null);
		db_LearningOption_delete.execSQL("CREATE TABLE IF NOT EXISTS LearningOptionList(Option_ID VARCHAR,Group_Name VARCHAR,Option_Name VARCHAR,Option_Status VARCHAR);");
		Cursor cursor = db_LearningOption_delete.rawQuery("SELECT * FROM LearningOptionList", null);
		int x = cursor.getCount();

		if (x > 0) {
			db_LearningOption_delete.delete("LearningOptionList", null, null);
			// Toast.makeText(getApplicationContext(),"All records deleted",Toast.LENGTH_LONG).show();
		}
		db_LearningOption_delete.close();
	}

}
