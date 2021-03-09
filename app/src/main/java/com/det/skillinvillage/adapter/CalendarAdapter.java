package com.det.skillinvillage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.det.skillinvillage.CalendarView;
import com.det.skillinvillage.EventListActivity;
import com.det.skillinvillage.R;
import com.det.skillinvillage.util.EventChoosedList;
import com.det.skillinvillage.util.UserInfoListRest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

//import com.det.skillinvillage.util.UserInfo;


public class CalendarAdapter extends BaseAdapter {
	private Context context;

	private java.util.Calendar month;
	public GregorianCalendar pmonth;
	Intent intent;
	/**
	 * calendar instance for previous month for getting complete view
	 */
	public GregorianCalendar pmonthmaxset;
	private GregorianCalendar selectedDate;
	int firstDay;
	int maxWeeknumber;
	int maxP;
	int calMaxP;
	int lastWeekDay;
	int leftDays;
	int mnthlength;
	String itemvalue, curentDateString;
	DateFormat df;

	//List<UserInfoListRest> UserInfolist = new ArrayList<UserInfoListRest>();
	String datestr,stime,etime,cohortstr,classroomstr,modulestr,facultystr;
	//Boolean eventUpdate;



	private ListView lv_android;
	private AndroidListAdapter list_adapter;

	View v;
	View GlobalView;
	private ArrayList<String> items;
	public static List<String> day_string;
	private View previousView;
	//public ArrayList<CalendarCollection> date_collection_arr;
	//public ArrayList<EventModule> event_module_arr;
	public ArrayList<UserInfoListRest> userInfo_arr;
	ArrayList<UserInfoListRest> arrayList= new ArrayList<UserInfoListRest>();
	Date Selected_Date,Current_date;
	final EventChoosedList eventChoosedList = new EventChoosedList();

//	EventChoosedList eventChoosedList=new EventChoosedList();

	Date previousDate;
	public CalendarAdapter(Context context, GregorianCalendar monthCalendar, ArrayList<UserInfoListRest> userInfo_arr) {
		this.userInfo_arr=userInfo_arr;
		CalendarAdapter.day_string = new ArrayList<String>();
		Locale.setDefault(Locale.US);
		month = monthCalendar;
		selectedDate = (GregorianCalendar) monthCalendar.clone();
		this.context = context;
		month.set(GregorianCalendar.DAY_OF_MONTH, 1);

		this.items = new ArrayList<String>();
		df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		curentDateString = df.format(selectedDate.getTime());
		refreshDays();

	}



	public void setItems(ArrayList<String> items) {
		for (int i = 0; i != items.size(); i++) {
			if (items.get(i).length() == 1) {
				items.set(i, "0" + items.get(i));
			}
		}
		this.items = items;
	}

	public int getCount() {
		return day_string.size();
	}

	public Object getItem(int position) {
		return day_string.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new view for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		v=convertView;
		View v = convertView;
		TextView dayView;
		if (convertView == null) { // if it's not recycled, initialize some
			// attributes
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.cal_item, null);

		}


		dayView = v.findViewById(R.id.date);
		String[] separatedTime = day_string.get(position).split("-");


		String gridvalue = separatedTime[2].replaceFirst("^0*", "");
		if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
			dayView.setTextColor(Color.GRAY);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		} else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
			dayView.setTextColor(Color.GRAY);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		} else {
			// setting curent month's days in blue color.
			dayView.setTextColor(Color.BLACK);
		}


		if (day_string.get(position).equals(curentDateString)) {

			//v.setBackgroundColor(Color.CYAN);
			v.setBackgroundResource(R.drawable.rounded_calender_todaydate);
			dayView.setTextColor(Color.WHITE);
		} else {
			v.setBackgroundColor(Color.parseColor("#ffffff"));
		}


		dayView.setText(gridvalue);

		// create date string for comparison
		String date = day_string.get(position);

		if (date.length() == 1) {
			date = "0" + date;
		}
		String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
		if (monthStr.length() == 1) {
			monthStr = "0" + monthStr;
		}

		// show icon if date is not empty and it exists in the items array
		/*ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
		if (date.length() > 0 && items != null && items.contains(date)) {
			iw.setVisibility(View.VISIBLE);
		} else {
			iw.setVisibility(View.GONE);
		}
		*/

		setEventView(v, position,dayView);

		return v;
	}

	public View setSelected(View view, int pos) {

		TextView dayView = view.findViewById(R.id.date);
		//if (previousView != null) {
		// previousView.setBackgroundColor(Color.parseColor("#ffffff"));
			/*view.setBackgroundColor(Color.parseColor("#343434"));
			view.setBackgroundResource(R.drawable.rounded_selecteditem_false);
			dayView.setTextColor(Color.WHITE);*/
		//}

		//	view.setBackgroundColor(Color.MAGENTA);
		//view.setBackgroundColor(Color.parseColor("#343434"));
		//	int len1=EventModule.event_module_arr.size();
		int size1= UserInfoListRest.user_info_arr.size();
		arrayList.size();
		//UserInfolist.size();
		Log.e("Tag","size="+size1);
		for (int i = 0; i < size1; i++) {
			//EventModule cal_obj = EventModule.event_module_arr.get(i);
			UserInfoListRest cal_obj = UserInfoListRest.user_info_arr.get(i);
			String date = cal_obj.scheduleDate;
			SimpleDateFormat daySDF = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Selected_Date = daySDF.parse(date);
				Current_date = new Date();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//	boolean event_TF = cal_obj.eventUpdate;

			String event_status = cal_obj.scheduleStatus;
			//	Log.i("Tag","event_status="+event_status);
			try {
				if (day_string.get(pos).equals(date) && event_status.equals("Taken")) {
					view.setBackgroundResource(R.drawable.rounded_calender_item);
					dayView.setTextColor(Color.WHITE);
			/*		if (previousView != null) {
					previousView.setBackgroundResource(R.drawable.rounded_calender_item);
					}*/
				}
				if (day_string.get(pos).equals(date) && event_status.equals("Not Taken")) {
					view.setBackgroundResource(R.drawable.rounded_calender_item_not_taken);
					dayView.setTextColor(Color.WHITE);
			/*		if (previousView != null) {
					previousView.setBackgroundResource(R.drawable.rounded_calender_item);
					}*/
				}
				if (day_string.get(pos).equals(date) && event_status.equals("Pending")) {
					Log.i("Tag", "Selected_Date=" + Selected_Date + "Current_date=" + Current_date);
					if (Selected_Date.compareTo(Current_date) > 0) {
						view.setBackgroundResource(R.drawable.rounded_calender_todaydate_after);
						dayView.setTextColor(Color.WHITE);
						Log.i("Tag", "rounded_calender_todaydate=");
						//	txt.setTextColor(Color.WHITE);
					} else {
					/*if (day_string.get(pos).equals(curentDateString)) {
						view.setBackgroundColor(Color.parseColor("#ffffff"));
						view.setBackgroundResource(R.drawable.rounded_calender_todaydate);
						dayView.setTextColor(Color.WHITE);
					}else {*/
						view.setBackgroundResource(R.drawable.rounded_calender_notupdated);
						dayView.setTextColor(Color.WHITE);
						Log.i("Tag", "rounded_calender_notupdated=");
						//}
						//	txt.setTextColor(Color.WHITE);

//                    Log.e("Selected_Date", String.valueOf(Selected_Date));
//					decrementDateByOne(Selected_Date);
//					if(day_string.get(pos).equals(previousDate)){
//						Log.e("event_status", event_status);
//					}


					}
				}

				if (day_string.get(pos).equals(date) && event_status.equals("Lesson Pending")) {
					view.setBackgroundResource(R.drawable.rounded_calender_lesson_pending);
					dayView.setTextColor(Color.WHITE);
			/*		if (previousView != null) {
					previousView.setBackgroundResource(R.drawable.rounded_calender_item);
					}*/
				}

				if (day_string.get(pos).equals(date) && event_status.equalsIgnoreCase("Lesson Not Assigned")) {
					view.setBackgroundResource(R.drawable.rounded_calender_lesson_not_assigned);
					dayView.setTextColor(Color.WHITE);
				}



		/*	if (day_string.get(pos).equals(date) && event_status.equals("0")) {
				view.setBackgroundResource(R.drawable.rounded_calender_notupdated);
				dayView.setTextColor(Color.WHITE);
				/*	if (previousView != null) {
					previousView.setBackgroundResource(R.drawable.rounded_calender_notupdated);
					}*/
				//	}

				Log.i("Tag", "day_string.get(pos).equals(date)1=" + day_string.get(pos).equals(date));
				Log.i("Tag", "day_string.get(pos)=" + day_string.get(pos));

			/*else {
				view.setBackgroundColor(Color.parseColor("#343434"));
				dayView.setTextColor(Color.BLACK);
				if (previousView != null) {
					previousView.setBackgroundColor(Color.parseColor("#ffff34"));
				}
			}*/

			}catch(Exception e){
				e.printStackTrace();
			}
		}
		int len=day_string.size();
		if (len>pos) {
			if (day_string.get(pos).equals(curentDateString)) {
				view.setBackgroundColor(Color.parseColor("#ffffff"));
				view.setBackgroundResource(R.drawable.rounded_calender_todaydate);
				dayView.setTextColor(Color.WHITE);
				//view.setBackgroundColor(Color.CYAN);
			}else{
				//	previousView.setBackgroundColor(Color.parseColor("#00FFFF"));
				previousView = view;
			}
		}

		return view;
	}



	public void refreshDays() {
		// clear items
		items.clear();
		day_string.clear();
		Locale.setDefault(Locale.US);
		pmonth = (GregorianCalendar) month.clone();
		// month start day. ie; sun, mon, etc
		firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
		// finding number of weeks in current month.
		maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
		// allocating maximum row number for the gridview.
		mnthlength = maxWeeknumber * 7;
		maxP = getMaxP(); // previous month maximum day 31,30....
		calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
		/**
		 * Calendar instance for getting a complete gridview including the three
		 * month's (previous,current,next) dates.
		 */
		pmonthmaxset = (GregorianCalendar) pmonth.clone();
		/**
		 * setting the start date as previous month's required date.
		 */
		pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

		/**
		 * filling calendar gridview.
		 */
		for (int n = 0; n < mnthlength; n++) {

			itemvalue = df.format(pmonthmaxset.getTime());
			pmonthmaxset.add(GregorianCalendar.DATE, 1);
			day_string.add(itemvalue);
		}
	}

	private int getMaxP() {
		int maxP;
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			pmonth.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}
		maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

		return maxP;
	}



	public void setEventView(View v, int pos, TextView txt){

		//int len=EventModule.event_module_arr.size();
		int size_new= UserInfoListRest.user_info_arr.size();
		Log.e("tag","size calendarAdapter=="+size_new);
		for (int i = 0; i < size_new; i++) {
			//EventModule cal_obj=EventModule.event_module_arr.get(i);
			UserInfoListRest cal_obj= UserInfoListRest.user_info_arr.get(i);
			String date= cal_obj.scheduleDate; //cal_obj.strDate;
			//boolean event_TF= cal_obj.eventUpdate;
			SimpleDateFormat daySDF = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Selected_Date = daySDF.parse(date);
				Current_date = new Date();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String event_status =cal_obj.scheduleStatus;
			int len1=day_string.size();
			//Log.i("Tag","len1="+len1);
			if (len1>pos) {
				//Log.i("Tag","event_status1="+event_status);
				if (day_string.get(pos).equals(date) && event_status.equalsIgnoreCase("Taken")) {
					Log.e("event_TF","day_string="+day_string.get(pos));
					Log.e("event_TF","date="+date);
					//	Log.e("event_TF","event_TF="+event_TF);
					//v.setBackgroundColor(Color.parseColor("#343434"));
					v.setBackgroundResource(R.drawable.rounded_calender_item);
					txt.setTextColor(Color.WHITE);
				}
				if (day_string.get(pos).equals(date) && event_status.equalsIgnoreCase("Not Taken")) {
					Log.e("event_TF","day_string="+day_string.get(pos));
					Log.e("event_TF","date="+date);
					//	Log.e("event_TF","event_TF="+event_TF);
					//v.setBackgroundColor(Color.parseColor("#343434"));
					v.setBackgroundResource(R.drawable.rounded_calender_item_not_taken);
					txt.setTextColor(Color.WHITE);

				}
				if(day_string.get(pos).equals(date)&&event_status.equals("Pending")) {
					Log.i("Tag","Selected_Date="+Selected_Date+"Current_date="+Current_date);
					if(Selected_Date.compareTo(Current_date) > 0){
						v.setBackgroundResource(R.drawable.rounded_calender_todaydate_after);
						txt.setTextColor(Color.WHITE);
					}else {
					/*if (day_string.get(pos).equals(curentDateString)) {
						txt.setTextColor(Color.WHITE);
						v.setBackgroundResource(R.drawable.rounded_calender_todaydate);
					}else {*/
						Log.i("Tag","day_string.get(pos).equals(date)="+day_string.get(pos).equals(date));


						v.setBackgroundResource(R.drawable.rounded_calender_notupdated);
						txt.setTextColor(Color.WHITE);
						//}

//						Log.e("Selected_Date", String.valueOf(Selected_Date));
//						decrementDateByOne(Selected_Date);
//						if(day_string.get(pos).equals(previousDate)){
//							Log.e("event_status", event_status);
//						}

					}
				}

				if (day_string.get(pos).equals(date) && event_status.equalsIgnoreCase("Lesson Pending")) {
					Log.e("event_TF","day_string="+day_string.get(pos));
					Log.e("event_TF","date="+date);
					//	Log.e("event_TF","event_TF="+event_TF);
					//v.setBackgroundColor(Color.parseColor("#343434"));
					v.setBackgroundResource(R.drawable.rounded_calender_lesson_pending);
					txt.setTextColor(Color.WHITE);
				}


				if (day_string.get(pos).equals(date) && event_status.equalsIgnoreCase("Lesson Not Assigned")) {
					Log.e("event_TF","day_string="+day_string.get(pos));
					Log.e("event_TF","date="+date);
					//	Log.e("event_TF","event_TF="+event_TF);
					//v.setBackgroundColor(Color.parseColor("#343434"));
					v.setBackgroundResource(R.drawable.rounded_calender_lesson_not_assigned);
					txt.setTextColor(Color.WHITE);
				}

			}
		}

	}

	/*public void getWidget(){

		lv_android = (ListView) v.findViewById(R.id.lv_android);
		list_adapter=new AndroidListAdapter(v.getContext(),R.layout.list_item, CalendarCollection.date_collection_arr);
		lv_android.setAdapter(list_adapter);

	}*/
	public void getPositionList(String date, final Activity act){
		String event_date=null;
		String event_date1=null;
		ArrayList<String> arr_date=new ArrayList<>();
		ArrayList<String> arr_stime=new ArrayList<>();
		ArrayList<String> arr_etime=new ArrayList<>();
		ArrayList<String> arr_faclty=new ArrayList<>();
		ArrayList<String> arr_cohort=new ArrayList<>();
		ArrayList<String> arr_classroom=new ArrayList<>();
		ArrayList<String> arr_module=new ArrayList<>();
		ArrayList<String> arr_schedSession=new ArrayList<>();
		ArrayList<String> arr_status=new ArrayList<>();
		ArrayList<String> arr_attandence=new ArrayList<>();
		ArrayList<String> arr_scheduleId=new ArrayList<>();
		ArrayList<String> arr_prevdatestatus=new ArrayList<>();

		int size= UserInfoListRest.user_info_arr.size();
		Log.e("TAG_TIME","size=="+size);
		//int len=EventModule.event_module_arr.size();
		//boolean[] arr_eventUpdate = new boolean[size];
		arr_date.clear();
		arr_stime.clear();
		arr_etime.clear();
		arr_faclty.clear();
		arr_cohort.clear();
		arr_module.clear();
		arr_schedSession.clear();
		arr_status.clear();
		arr_attandence.clear();
		arr_scheduleId.clear();
		arr_prevdatestatus.clear();

		for (int i = 0; i < size; i++) {

			//	EventModule cal_collection=EventModule.event_module_arr.get(i);

			//ArrayList<String> event_date=new ArrayList<String>();
		/*	event_date=cal_collection.strDate;
			String event_starttime=cal_collection.strStartTime;
			String event_endtime=cal_collection.strEndTime;
			String event_faculty=cal_collection.strFacultyName;
			String event_cohort=cal_collection.strCohort;
			String event_classroom=cal_collection.strClassroom;
			String event_module=cal_collection.strModule;
			String event_fellowship=cal_collection.strFellowship;
			Boolean event_update=cal_collection.eventUpdate;
*/
			UserInfoListRest cal_collection= UserInfoListRest.user_info_arr.get(i);
			event_date=cal_collection.scheduleDate;
			String event_starttime=cal_collection.startTime;
			String event_endtime=cal_collection.endTime;
			String event_cohort=cal_collection.subjectName;
			String bookId=cal_collection.scheduleID;
			String event_scheduleSession=cal_collection.scheduleSession;
			String event_status=cal_collection.scheduleStatus;
			//String event_attandence=cal_collection.Lavel_ID;
			String event_lavelName=cal_collection.lavelName;
			String event_clusterName=cal_collection.clusterName;
			String event_leasonName=cal_collection.leasonName;
			String event_instituteName=cal_collection.instituteName;
			String event_scheduleId=cal_collection.scheduleID;
			String event_prevdatestatus=cal_collection.schedule_Status_Old;
			Log.e("event_prevdatestatus",event_prevdatestatus);
			if (date.equals(event_date)) {

				event_date1=event_date;
				eventChoosedList.setStrDate(event_date1);
				String event_starttime1=event_starttime;
				eventChoosedList.setStrStartTime(event_starttime1);
				String event_endtime1=event_endtime;
				eventChoosedList.setStrEndTime(event_endtime1);
				String event_cohort1=event_cohort;                  //Subject name
				eventChoosedList.setStrCohort(event_cohort1);//Subject name
				String event_lavelName1=event_lavelName;
				eventChoosedList.setStrLavelName(event_lavelName1);
				String event_leasonName1=event_leasonName;
				eventChoosedList.setStrLeasonName(event_leasonName1);
				String event_clusterName1=event_clusterName;
				eventChoosedList.setStrClusterName(event_clusterName1);
				String event_instituteName1=event_instituteName;
				eventChoosedList.setStrInstituteName(event_instituteName1);
				String event_scheduleId1=event_scheduleId;
				eventChoosedList.setStrScheduleId(event_scheduleId1);

				//	String event_classroom1=event_classroom;
				//	eventChoosedList.setStrClassroom(event_classroom1);

				String event_scheduleSession1=event_scheduleSession;
				eventChoosedList.setStrScheduleSession(event_scheduleSession1);

				eventChoosedList.setStrFacultyName(bookId);
				eventChoosedList.setStrEventStatus(event_status);
				String event_prevdatestatus1=event_prevdatestatus;
				eventChoosedList.setStrprevdatestatus(event_prevdatestatus1);

			//	eventChoosedList.setStrAttandence(event_attandence);
				arr_date.add(eventChoosedList.getStrDate());
				arr_stime.add(eventChoosedList.getStrStartTime());
				arr_etime.add(eventChoosedList.getStrEndTime());
				arr_cohort.add(eventChoosedList.getStrCohort());
				arr_status.add(eventChoosedList.getStrEventStatus());
				arr_attandence.add(eventChoosedList.getStrInstituteName());  //Institute name
                arr_faclty.add(eventChoosedList.getStrLavelName());  //Lavel name
                arr_classroom.add(eventChoosedList.getStrLeasonName());      //Leason name
				arr_module.add(eventChoosedList.getStrClusterName());             //Cluser name
				arr_schedSession.add(eventChoosedList.getStrScheduleSession());
				arr_scheduleId.add(eventChoosedList.getStrScheduleId());
				//arr_eventUpdate[i]=eventChoosedList.getEventUpdate();
				arr_prevdatestatus.add(eventChoosedList.getStrprevdatestatus());

				intent = new Intent(context, EventListActivity.class);
				intent.putExtra("arr_date",arr_date);
				intent.putExtra("arr_stime",arr_stime);
				intent.putExtra("arr_etime",arr_etime);
				intent.putExtra("arr_faclty",arr_faclty);//Lavel name
				intent.putExtra("arr_cohort",arr_cohort);
				//intent.putExtra("arr_bookid",arr_bookid);
                intent.putExtra("arr_classroom",arr_classroom); //Leason name
				intent.putExtra("arr_module",arr_module);//Cluser name
				intent.putExtra("arr_schedSession",arr_schedSession);
			//	intent.putExtra("arr_eventUpdate",arr_eventUpdate);
				intent.putExtra("arr_status",arr_status);
				intent.putExtra("arr_attandence",arr_attandence); //Institute name
				intent.putExtra("arr_scheduleId",arr_scheduleId);
				intent.putExtra("arr_prevdatestatus",arr_prevdatestatus);

				//	Log.i("CalendarView","arr_eventUpdate"+arr_eventUpdate);
				Log.e("CalendarView","arr_prevdatestatus="+arr_prevdatestatus);
				//	CardsAdapter adapter = new CardsAdapter(context);
				//	ListView lv_eventlist=(ListView) view.findViewById(R.id.lv_android);

				// date_tv.setText(date_str);
				//   event_tv.setText(event_msg_str);

				//		lv_eventlist.setAdapter(adapter);
				//GlobalView=context;

				/*lv_android = (ListView) v.findViewById(R.id.lv_android);
				list_adapter=new AndroidListAdapter(v.getContext(),R.layout.list_item, CalendarCollection.date_collection_arr);
				lv_android.setAdapter(list_adapter);*/
				//	getWidget();

				//	Toast.makeText(context, "Selected Date: "+event_date, Toast.LENGTH_SHORT).show();
		/*	 new AlertDialog.Builder(context)
	          .setIcon(android.R.drawable.ic_dialog_alert)
	          .setTitle("Date: "+event_date)
	           .setMessage("Event: "+event_message)
	            .setPositiveButton("OK",new android.content.DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialog, int which)
	            {
	            	act.finish();
	            }
	            }).show();
			break;	*/
			}else{
				Intent intent1  = new Intent(context, CalendarView.class);
				context.startActivity(intent1);
			}

		}
		Log.i("Tag","event_date=="+event_date1);

		if (date.equals(event_date1)) {
			context.startActivity(intent);
		}
		else{
			Intent intent1  = new Intent(context, CalendarView.class);
			context.startActivity(intent1);
		}
		//	Activity activity = (Activity) context;
		//	activity.finish();
		//((Activity) GlobalView.getContext()).finish();

	}

}