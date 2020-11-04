package com.det.skillinvillage.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.det.skillinvillage.Class_AssessmentModel;
import com.det.skillinvillage.R;

import java.util.ArrayList;



public class AssessmentViewAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<Class_AssessmentModel> editModelArrayList;

    public AssessmentViewAdapter(Context context, ArrayList<Class_AssessmentModel> editModelArrayList) {

        this.context = context;
        AssessmentViewAdapter.editModelArrayList = editModelArrayList;
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

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_assessmentstudentlistview, null, true);

            holder.editText = convertView.findViewById(R.id.marks_assessment_ET);
            holder.stu_name_assessment_TV = convertView.findViewById(R.id.stu_name_assessment_TV);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }
        //holder.editText.setFilters(new InputFilter[]{new InputFilterMinMax("1", "30")});
        holder.editText.setText(editModelArrayList.get(position).getAssementModel_Marks());
        holder.stu_name_assessment_TV.setText(editModelArrayList.get(position).getAssement_StudentName());



        //commenting..
//        if(Activity_Assessmentview.intval_flag==1){
//            holder.editText.setEnabled(false);
//        }else{
//            holder.editText.setEnabled(true);
//        }
//
//        if(editModelArrayList.get(position).getAssementModel_Flag().equals("1"))
//        {
//            if (editModelArrayList.get(position).getAssementModel_status().equals("Active")) {
//
//
//                holder.editText.setEnabled(false);
//            } else {
//                holder.editText.setEnabled(true);
//            }
//        }
//       // Log.e("edit_marks",editModelArrayList.get(position).getAssementModel_Marks());
//
//        if(editModelArrayList.get(position).getAssementModel_Marks().equals("-1"))
//        {
//            editModelArrayList.get(position).setEditTextValue(holder.editText.getText().toString());
//
//        }else{
//            holder.editText.setText(editModelArrayList.get(position).getAssementModel_Marks());
//        }
//
////        if(!editModelArrayList.get(position).getAssementModel_Marks().equals("A")){
////            holder.editText.setText(editModelArrayList.get(position).getAssementModel_Marks());
////
////        }
//
//       // setNameTextChangeListener(holder, position);
//
//
//     //   holder.editText.setText(editModelArrayList.get(position).getAssementModel_Marks());
//
//
//
//
//        holder.editText.setTag(position);
//
        holder.editText.addTextChangedListener(new TextWatcher()
        {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                //int pos =(Integer)holder.editText.getTag();
                editModelArrayList.get(position).setEditTextValue(holder.editText.getText().toString());

               // editModelArrayList.get(pos).setEditTextValue(holder.editText.getText().toString());

               // holder.editText[position] = charSequence.toString();


//                int pos = (Integer)  holder.editText.getTag();
//                editModelArrayList.get(position).setEditTextValue(charSequence.toString());


            }

            @Override
            public void afterTextChanged(Editable editable) {
               // editModelArrayList.get(position).setEditTextValue(editable.toString());
                //editModelArrayList.get(position).setName(editable.toString);
            }
        });


       // holder.editText.setText(editModelArrayList.get(position).getEditTextValue());



        return convertView;
    }

    private class ViewHolder {
        TextView stu_name_assessment_TV;
        protected EditText editText;

    }



    private void setNameTextChangeListener(final ViewHolder holder, final int position) {
        holder.editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //do null check first before converting to string

                if(s.length() > 0)
                {
                    System.out.println("s.toString()  "+s.toString());
                    String str_marks= editModelArrayList.get(position).getAssementModel_Marks();
                   // editModelArrayList.set(0,);


                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }


}
