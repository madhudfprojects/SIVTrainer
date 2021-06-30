package com.det.skillinvillage.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.det.skillinvillage.R;
import com.det.skillinvillage.model.LessonQuestion;

import java.util.ArrayList;


public  class LessonPlanAdapter extends BaseAdapter {
    private Context context;
    public static ArrayList<LessonQuestion> lessonQuestionArrayList=null;

    public LessonPlanAdapter() {

        super();
        Log.d("Inside cfeessubmit()", "Inside CustomAdapter_feessubmit()");
    }

    public LessonPlanAdapter(Context context, ArrayList<LessonQuestion> lessonQuestionArrayList) {

        this.context = context;
        LessonPlanAdapter.lessonQuestionArrayList = lessonQuestionArrayList;
    }
//        @Override
//        public int getCount() {
//
//            String x = Integer.toString(lessonQuestionArrayList.size());
//            Log.d("Arrayclass.length", x);
//            return lessonQuestionArrayList.size();
//
//        }

    @Override
    public int getCount() {
        return lessonQuestionArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public Object getItem(int position) {
        String x = Integer.toString(position);

        Log.d("getItem position", "x");
        return lessonQuestionArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
//            String x = Integer.toString(position);
//            Log.d("getItemId position", x);
//            return position;
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Holder holder;

        Log.d("CustomAdapter", "position: " + position);

        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.child_lessonplan_layout, parent, false);


            holder.LP_Question_TV = (TextView) convertView.findViewById(R.id.LP_Question_TV);
            holder.LP_Answer_ET = (EditText) convertView.findViewById(R.id.LP_Answer_ET);
            holder.LP_Question_ID_TV = (TextView) convertView.findViewById(R.id.LP_Question_ID_TV);

            Log.d("Inside If convertView", "Inside If convertView");

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
            Log.d("Inside else convertView", "Inside else convertView");
        }

      //  Objclass_feesSubmissionList_new = (LessonQuestion) getItem(position);

        //   if(isInternetPresent) {
//                if (loadPendingPaymentCount == 0) {
//                    PendingAmtstudentlist_LL.setVisibility(android.view.View.GONE);
//                    NoRecords_studentlist_LL.setVisibility(android.view.View.VISIBLE);
//                } else {

//                    PendingAmtstudentlist_LL.setVisibility(android.view.View.VISIBLE);
//                    NoRecords_studentlist_LL.setVisibility(android.view.View.GONE);

      //  if (Objclass_feesSubmissionList_new != null) {
          String str_lp_answer=holder.LP_Answer_ET.getText().toString();
//                        holder.LP_Question_TV.setText(Objclass_feesSubmissionList_new.getQuestionName());
//                        holder.LP_Answer_ET.setText(holder.LP_Answer_ET.getText().toString());
//                        holder.LP_Question_ID_TV.setText(Objclass_feesSubmissionList_new.getLessonQuestionID());
            holder.LP_Question_TV.setText(lessonQuestionArrayList.get(position).getQuestionName());
          //  holder.LP_Answer_ET.setText(holder.LP_Answer_ET.getText().toString());
            holder.LP_Question_ID_TV.setText(lessonQuestionArrayList.get(position).getLessonQuestionID());
//          lessonQuestionArrayList.get(position).setScheduleLessonAnswer(holder.LP_Answer_ET.getText().toString());

//        holder.LP_Answer_ET.setText(lessonQuestionArrayList.get(position).getScheduleLessonAnswer());
//        Log.e("edtans",lessonQuestionArrayList.get(position).getScheduleLessonAnswer());
        // end of if
//                }else{
//                holder.LP_Question_TV.setVisibility(View.GONE);
//                holder.LP_Answer_ET.setVisibility(View.GONE);
//            }



        if(holder.LP_Answer_ET.getText().toString().equals("")){
            holder.LP_Answer_ET.setError("text is empty!");
           // holder.LP_Answer_ET.setText("");
        }else{
            holder.LP_Answer_ET.setText(lessonQuestionArrayList.get(position).getScheduleLessonAnswer());
            Log.e("edtans",lessonQuestionArrayList.get(position).getScheduleLessonAnswer());
        }











        holder.LP_Answer_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             //   editModelArrayList.get(position).setAssementMarks(holder.viewholder_editText.getText().toString());
                if(holder.LP_Answer_ET.getText().toString().equals("")){
                   // holder.LP_Answer_ET.setError("text is empty!");
                    // holder.LP_Answer_ET.setText("");
                    lessonQuestionArrayList.get(position).setScheduleLessonAnswer("");
                }else {
                    lessonQuestionArrayList.get(position).setScheduleLessonAnswer(holder.LP_Answer_ET.getText().toString());

                    Log.e("ed22tans", lessonQuestionArrayList.get(position).getScheduleLessonAnswer());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

//                if (editModelArrayList.get(position).getAssementMarks().trim().length() == 0) {
//                    holder.viewholder_studentabsent_tv.setVisibility(View.VISIBLE);
//                    holder.viewholder_studentpresent_tv.setVisibility(View.GONE);
//                } else {
//                    holder.viewholder_studentabsent_tv.setVisibility(View.GONE);
//                    holder.viewholder_studentpresent_tv.setVisibility(View.VISIBLE);
//                }


                if(holder.LP_Answer_ET.getText().toString().equals("")){
                   // holder.LP_Answer_ET.setError("text is empty!");
                    // holder.LP_Answer_ET.setText("");
                    lessonQuestionArrayList.get(position).setScheduleLessonAnswer("");
                }else {
                    lessonQuestionArrayList.get(position).setScheduleLessonAnswer(holder.LP_Answer_ET.getText().toString());
                    Log.e("edt33ans",lessonQuestionArrayList.get(position).getScheduleLessonAnswer());
                }
            }
        });


        return convertView;
    }
    private  class Holder {
        TextView LP_Question_TV;
        protected  EditText LP_Answer_ET;
        TextView LP_Question_ID_TV;
    }



}
