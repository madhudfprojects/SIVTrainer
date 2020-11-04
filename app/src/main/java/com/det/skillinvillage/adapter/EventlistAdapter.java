package com.det.skillinvillage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.det.skillinvillage.R;
import com.det.skillinvillage.util.Eventlist;

import java.util.ArrayList;


/**
 * Created by User on 4/24/2018.
 */

public class EventlistAdapter extends ArrayAdapter<Eventlist> {

    private ArrayList<Eventlist> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtDate;
        TextView txtEvent;

    }

    public EventlistAdapter(ArrayList<Eventlist> data, Context context) {
        super(context, R.layout.list_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

   /* @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Eventlist dataModel=(Eventlist) object;

        switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }*/

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Eventlist eventlist = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.txtDate = convertView.findViewById(R.id.tv_date);
            viewHolder.txtEvent = convertView.findViewById(R.id.tv_event);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.txtDate.setText(eventlist.getName());
        viewHolder.txtEvent.setText(eventlist.getType());
     //   viewHolder.txtVersion.setText(dataModel.getVersion_number());
       // viewHolder.info.setOnClickListener(this);
       // viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
