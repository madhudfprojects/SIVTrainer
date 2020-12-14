package com.det.skillinvillage;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 03-Jan-17.
 */
public class CustomeAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<EditModel> editModelArrayList;

    public CustomeAdapter(Context context, ArrayList<EditModel> editModelArrayList) {

        this.context = context;
        CustomeAdapter.editModelArrayList = editModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return editModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return editModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
      //  String str_studentmarks="";
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            holder.viewholder_editText = convertView.findViewById(R.id.editid);
            holder.viewholder_studentname_tv = convertView.findViewById(R.id.studentname_tv);
            holder.viewholder_studentpresent_tv = convertView.findViewById(R.id.studentpresent_tv);
            holder.viewholder_studentabsent_tv = convertView.findViewById(R.id.studentabsent_tv);

            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }
        holder.viewholder_editText.setFilters(new InputFilter[]{new InputFilterMinMax("0", editModelArrayList.get(position).getMaxMarks())});
        holder.viewholder_editText.setText(editModelArrayList.get(position).getAssementMarks());
        holder.viewholder_studentname_tv.setText(editModelArrayList.get(position).getStudentname());


        String   str_studentmarks = editModelArrayList.get(position).getAssementMarks();
         Log.e("str_studentmarks",str_studentmarks);


//        if (str_studentmarks.equalsIgnoreCase("A")) {
//            holder.viewholder_studentabsent_tv.setVisibility(View.VISIBLE);
//            holder.viewholder_studentpresent_tv.setVisibility(View.GONE);
//        } else {
//            holder.viewholder_studentpresent_tv.setVisibility(View.VISIBLE);
//        }
       // if(editModelArrayList.get(position).getFlag().equals("1"))
       // {
            if (editModelArrayList.get(position).getFlag().equals("Completed")) {
                holder.viewholder_editText.setEnabled(false);

                //holder.editText.setEnabled(false);
            } else {
                holder.viewholder_editText.setEnabled(true);
            }
       // }





        if (str_studentmarks.equalsIgnoreCase("A")) {
            holder.viewholder_studentabsent_tv.setVisibility(View.VISIBLE);
            holder.viewholder_studentpresent_tv.setVisibility(View.GONE);
        } else  if (holder.viewholder_editText.getText().toString().equals("")) {
            holder.viewholder_studentpresent_tv.setVisibility(View.GONE);
            holder.viewholder_studentabsent_tv.setVisibility(View.VISIBLE);

        }else{
            holder.viewholder_studentpresent_tv.setVisibility(View.VISIBLE);

        }

        holder.viewholder_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editModelArrayList.get(position).setAssementMarks(holder.viewholder_editText.getText().toString());


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editModelArrayList.get(position).getAssementMarks().trim().length() == 0) {
                    holder.viewholder_studentabsent_tv.setVisibility(View.VISIBLE);
                    holder.viewholder_studentpresent_tv.setVisibility(View.GONE);
                } else {
                    holder.viewholder_studentabsent_tv.setVisibility(View.GONE);
                    holder.viewholder_studentpresent_tv.setVisibility(View.VISIBLE);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected EditText viewholder_editText;
        protected TextView viewholder_studentname_tv;
        protected TextView viewholder_studentpresent_tv;
        protected TextView viewholder_studentabsent_tv;


    }

}
