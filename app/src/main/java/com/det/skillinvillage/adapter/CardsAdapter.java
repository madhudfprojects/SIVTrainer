package com.det.skillinvillage.adapter;

import android.content.Context;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.det.skillinvillage.R;
import com.det.skillinvillage.Remarks;
import com.det.skillinvillage.util.ListviewEvents;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * @author Alhaytham Alfeel on 10/10/2016.
 */

public class CardsAdapter extends ArrayAdapter<ListviewEvents> {
    private Context context1;
    String schedule_id,datestr,stime,etime,cohortstr,classroomstr,modulestr,bookIdstr,fellowershipsrt,statusStr;
    String scheduleid_holder,book_holder,startTime_holder,endTime_holder,date_holder,cohort_holder,fellowership_holder,module_holder,attandence_holder;
    private Date date;
    private Date dateCompareOne;
    private Date dateCompareTwo;

    public  ArrayList<ListviewEvents> listview_info_arr=new ArrayList<>();

    int dmin,dHour,dsec;

    public CardsAdapter(Context context, ArrayList<ListviewEvents> listview_info_arr) {
        super(context, R.layout.card_item);
        this.context1=context;
        this.listview_info_arr=listview_info_arr;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            convertView = inflater.inflate(R.layout.card_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        ListviewEvents model = getItem(position);
        int mm=getCount();

        Log.i("TAG","mm="+mm+" getItemId(position)="+ getItemId(position)+"getItem="+getItem(position));

        schedule_id=model.getStrScheduleId();
        datestr=model.getStrDate();
        stime=model.getStrStartTime();
        etime=model.getStrEndTime();
        cohortstr=model.getStrCohort();
        bookIdstr=model.getStrFacultyName();
        fellowershipsrt=model.getStrFellowship();
        modulestr=model.getStrModule();
        statusStr=model.getStrstatus();
        listview_info_arr.get(position);

       // datestr_arr=listview_info_arr.get(position);
        stime=model.getStrStartTime();
        etime=model.getStrEndTime();
        cohortstr=model.getStrCohort();
        bookIdstr=model.getStrFacultyName();
        fellowershipsrt=model.getStrFellowship();
        modulestr=model.getStrModule();
        statusStr=model.getStrstatus();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf_HM_format = new SimpleDateFormat("HH:mm");
        Date HM_format_Stime= null;
        Date HM_format_Etime=null;
        String Stime=null,Etime = null;
        try {
            HM_format_Stime = sdf_HM_format.parse(stime);
            HM_format_Etime=sdf_HM_format.parse(etime);
            Stime=sdf.format(HM_format_Stime);
            Etime=sdf.format(HM_format_Etime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.e("Tag_","Model=="+model+"stime="+stime+"bookIdstr="+bookIdstr);
        Log.e("Tag_","Stime="+Stime);
        Log.i("tag","listview_info_arr"+listview_info_arr.get(position).getStrDate());
        Log.i("tag","listview_info_arr"+listview_info_arr.get(position).getStrFacultyName());
        Log.i("tag","listview_info_arr"+listview_info_arr.get(position).getStrStartTime());
        Log.i("tag","listview_info_arr"+listview_info_arr.get(position).getStrModule());
      //  Object o = holder.btUpdate.bringPointIntoView(position);

        //   holder.imageView.setImageResource(model.getImageId());
        holder.tvFaculty.setText( model.getStrModule());
        holder.tvTime.setText(stime);
        holder.tvTime2.setText(etime);
        holder.tvDate.setText(model.getStrDate());
        holder.tvCohort.setText( model.getStrCohort());
        holder.tvfellowship.setText( model.getStrClassroom());        //Leason_Name
        holder.tvModule.setText( model.getStrFacultyName());
        holder.tv_attandence.setText(model.getStrAttandence());
        holder.tv_scheduleId.setText(model.getStrScheduleId());

    //    holder.today_date.setText( model.getStrDate());
        holder.tvfellowship.setVisibility(View.VISIBLE);
        holder.tv_attandence.setVisibility(View.VISIBLE);
        holder.tvDate.setVisibility(View.GONE);
        holder.tv_scheduleId.setVisibility(View.GONE);
        holder.tvFaculty.setVisibility(View.VISIBLE);
        Log.i("tag","statusStr="+statusStr);
        if(statusStr.equals("Taken") || statusStr.equals("Not Taken")){
            //holder.btUpdate.
            holder.btUpdate1.setVisibility(View.GONE);
        }else {

            holder.btUpdate1.setVisibility(View.VISIBLE);
        }

        //holder.tvClassRoom.setText(model.getStrClassroom());
      //  holder.tvModule.setText(model.getStrModule());

       /*int stimeLenth = model.getStrStartTime();
        String sstime[] = new String[stimeLenth];
        Log.i("Tag_sstime","stimeLenth"+stimeLenth);
       for(int i=0;i<stimeLenth;i++){

           sstime[i]=model.getStrStartTime();
           Log.i("Tag_sstime","sstime"+sstime[i]);

       }*/

      /*  holder.btIncharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(getContext(), InchargeActivity.class);
              /*  i.putExtra("EventDiscription", mTitle.getText().toString());
                i.putExtra("EventId", mTitle.getText());
                i.putExtra("EventDate", mWhenDateTime.getText().toString());*/

         /*       i.putExtra("EventDiscription", "23625|Orientation|HB17DSF001");
                i.putExtra("EventId", "23625|Orientation|HB17DSF001");
                i.putExtra("EventDate", "Friday, April 27, 6:00 – 7:00 AM  GMT+05:30");
                context1.startActivity(i);

            }
        });
        holder.btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), Remarks.class);
               /* i.putExtra("EventDiscription", "23623|test test|TEST-DSF");
                i.putExtra("EventId", "23623|test test|TEST-DSF");
                i.putExtra("EventDate", "Monday, April 27, 9:30 – 11:30 AM  GMT+05:30");*/
         /*       i.putExtra("EventDiscription", "23625|Orientation|HB17DSF001");
                i.putExtra("EventId", "23625|Orientation|HB17DSF001");
                i.putExtra("EventDate", "Friday, April 27, 6:00 – 7:00 AM  GMT+05:30");
                context1.startActivity(i);
            }
        });*/


        holder.btUpdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               book_holder=  holder.tvFaculty.getText().toString();
               startTime_holder=holder.tvTime.getText().toString();
               endTime_holder=holder.tvTime2.getText().toString();
               date_holder=holder.tvDate.getText().toString();
               cohort_holder=holder.tvCohort.getText().toString();
               fellowership_holder=holder.tvfellowship.getText().toString();
               module_holder=holder.tvModule.getText().toString();
               attandence_holder=holder.tv_attandence.getText().toString();
               scheduleid_holder=holder.tv_scheduleId.getText().toString();

              Log.i("Tag","book="+book_holder);
                Log.i("Tag","startTime="+startTime_holder);
                Log.i("Tag","endTime="+endTime_holder);
                Log.i("Tag","date="+date_holder);
                Log.i("Tag","fellowership_holder="+fellowership_holder);
                Log.i("Tag","cohort="+cohort_holder);
                Log.e("Tag","scheduleid_holder="+scheduleid_holder);
                try {
                    int id=v.getId();

                    Integer position=(Integer) v.getTag();
                    Log.i("TAG_","position="+position+"ID="+id);
                    String dateandtime = date_holder + " " + startTime_holder;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());

                    long currentday=calendar.getTimeInMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat daySDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                    Date StartTimeToCompare = sdf.parse(stime);
                    Log.i("Tag_time","dateandtime="+dateandtime);
                    Date SelectedDat = daySDF.parse(dateandtime);
                    Date currentdate1=new Date(currentday);
                    String finaldate=daySDF.format(currentdate1);
                    Date eventTime=daySDF.parse(finaldate);

                    Log.i("Tag_time","SelectedDat="+SelectedDat);
                    Log.i("Tag_time","eventTime="+eventTime);
                    // Log.i("Tag_time","finaldate="+finaldate);
                    String formattedTime = sdf.format(StartTimeToCompare);
                    Date StartTimeToCompare1=sdf.parse(formattedTime);
                    dHour=calendar.get(Calendar.HOUR_OF_DAY);
                    dmin=calendar.get(Calendar.MINUTE);
                    dsec=calendar.get(Calendar.SECOND);
                    date = sdf.parse(dHour + ":" + dmin + ":" + dsec);

                    Date date1 = new Date();
                    // Log.i("Tag_time","StartTimeToCompare1="+StartTimeToCompare1);
                    Log.i("Tag_time","datestr="+datestr);
                    Log.i("Tag_time","date1="+date1);
                    Log.i("Tag_time","date="+date);
                    Log.i("Tag_time","StartTimeToCompare="+StartTimeToCompare);

                    String PresentTimeStr=sdf.format(date1);
                    Date PresentTime=sdf.parse(PresentTimeStr);

                    Log.i("Tag_time","PresentTime="+PresentTime);
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTimeInMillis(System.currentTimeMillis());
                    calendar1.set(Calendar.HOUR_OF_DAY, 23);
                    calendar1.set(Calendar.MINUTE, 00);
                    calendar1.set(Calendar.SECOND, 00);

                    long mEventEnd=calendar1.getTimeInMillis();
                    Date EventEnddate = new Date(mEventEnd);

                    String EventEndTimeStr = daySDF.format(EventEnddate);
                    Date EventEndTime = daySDF.parse(EventEndTimeStr);
                    Log.i("Tag","EventEndTime="+EventEndTime);
                    String endtimeformat=sdf.format(EventEndTime);
                    Date EndTimeFinal=sdf.parse(endtimeformat);
                    SimpleDateFormat sdf_date = new SimpleDateFormat("MM-dd-yyyy");
                    String formattedTime1 = sdf_date.format(SelectedDat);
                    String formattedTime2 = sdf_date.format(date1);
                    Log.i("Madhu","formattedTime1=="+formattedTime1);
                    Log.i("Madhu","formattedTime2=="+formattedTime2);
                    Date d1=sdf_date.parse(formattedTime1);
                    Date d2=sdf_date.parse(formattedTime2);
                    Log.i("Madhu","d1=="+d1);
                    Log.i("Madhu","d2=="+d2);
                    Log.i("Tag_time","EndTimeFinal=="+EndTimeFinal);
                    Log.i("Tag_time","StartTimeToCompare=="+StartTimeToCompare);
                    if(SelectedDat.compareTo(date1) > 0 )
                    //  if(SelectedDat.compareTo(date1)>0)
                    //if(SelectedDat.compareTo(eventTime) > 0 )
                    {
                        Log.i("Tag_time","Inside if ");
                      //     holder.btUpdate.setEnabled(false);
                        // System.out.println("StartTimeToCompare is Greater than my date1");
                        Toast.makeText(context1, "Update after the class time", Toast.LENGTH_SHORT).show();
                    }
                //    else if(d2.compareTo(d1)>0 || PresentTime.compareTo(StartTimeToCompare)<0){


                    /////////////////////////////////////////CHanged on 8th november to remove restriction for updating schedules.
//                    else if(d2.compareTo(d1) > 0 || EndTimeFinal.compareTo(PresentTime) < 0){
//                        Toast.makeText(context1, "Allocated time for event has been over", Toast.LENGTH_SHORT).show();
//                    }

                    /////////////////////////////////////////CHanged on 8th november to remove restriction for updating schedules.



                    else{
                     //   holder.btUpdate.setEnabled(true);
                       //  holder.btUpdate.setEnabled(true);
                Intent i = new Intent(getContext(), Remarks.class);

                i.putExtra("ScheduleId",scheduleid_holder);
                i.putExtra("Cluster_Name",book_holder);
                i.putExtra("Lavel_Name",module_holder);
              //  i.putExtra("EventDate",stime);
                i.putExtra("Leason_Name",fellowership_holder);
                i.putExtra("Subject_Name",cohort_holder);
                i.putExtra("Institute_Name",attandence_holder);
                context1.startActivity(i);
                    }
                    // Log.i("Tag_time","dateCompareTwo="+dateCompareTwo);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });

                return convertView;
    }

    static class ViewHolder {
     //   ImageView imageView;
        TextView tvFaculty;
        TextView tvTime;
        TextView tvTime2;
        TextView tvCohort;
        TextView tvDate;
        TextView tvClassRoom;
        TextView tvModule;
        TextView tvfellowship;
        TextView tv_attandence;
     //   Button btIncharge;
        ImageView btUpdate1;
        TextView tv_scheduleId;
       // Button btUpdate;


        ViewHolder(View view) {
          //  imageView = (ImageView) view.findViewById(R.id.image);
            tvFaculty = view.findViewById(R.id.tv_Faculty);
            tvTime = view.findViewById(R.id.tv_time);
            tvTime2 = view.findViewById(R.id.tv_time2);
            tvDate = view.findViewById(R.id.tv_date);
            tvfellowship = view.findViewById(R.id.tv_fellowship);
            tvCohort = view.findViewById(R.id.tv_cohort);
            tv_attandence = view.findViewById(R.id.tv_attandence);
            tv_scheduleId = view.findViewById(R.id.tv_scheduleId);
            tvModule = view.findViewById(R.id.tv_Module);
          //  btIncharge = (Button) view.findViewById(R.id.incharge_bt);
           // btUpdate = (Button) view.findViewById(R.id.update_bt);
            btUpdate1 = view.findViewById(R.id.update_bt1);
         //   today_date = (TextView) view.findViewById(R.id.today_date);
        }
    }
}
