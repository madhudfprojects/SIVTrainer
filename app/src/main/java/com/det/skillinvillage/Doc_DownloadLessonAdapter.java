package com.det.skillinvillage;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


public class Doc_DownloadLessonAdapter extends RecyclerView.Adapter<Doc_DownloadLessonAdapter.MyViewHolder> {

    private ArrayList<LessionPlanDoc_Module> dataSet;
    private Context context1;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_docName;
        TextView tv_docdate;
        TextView tv_doctime,tv_docid,tv_docstatus,tv_doctopiclevelid;
        ImageView imageViewIcon,view_lp_bt;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.tv_docName = itemView.findViewById(R.id.tv_docName);
            this.tv_docdate = itemView.findViewById(R.id.tv_docdate);
            this.tv_doctime = itemView.findViewById(R.id.tv_doctime);
            this.imageViewIcon = itemView.findViewById(R.id.doc_download_iv);
            this.view_lp_bt= itemView.findViewById(R.id.view_lp_bt);
            this.tv_docid=itemView.findViewById(R.id.tv_docid);
            this.tv_docstatus=itemView.findViewById(R.id.tv_docstatus);
            this.tv_doctopiclevelid=itemView.findViewById(R.id.tv_doctopiclevelid);

        }
    }

    public Doc_DownloadLessonAdapter(Context context, ArrayList<LessionPlanDoc_Module> data) {
        this.context1=context;
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

      //  view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int listPosition) {

        TextView tv_docName = holder.tv_docName;
        TextView tv_docdate = holder.tv_docdate;
        TextView tv_doctime = holder.tv_doctime;
        ImageView imageView = holder.imageViewIcon;
        TextView tv_docid=holder.tv_docid;
        TextView tv_docstatus=holder.tv_docstatus;
        TextView tv_doctopiclevelid=holder.tv_doctopiclevelid;

        final ConnectionDetector[] internetDectector = new ConnectionDetector[1];
        final Boolean[] isInternetPresent = {false};

        tv_docName.setText(dataSet.get(listPosition).getDocument_Name());
        tv_docdate.setText(dataSet.get(listPosition).getDocument_Date());
        tv_doctime.setText(dataSet.get(listPosition).getDocument_Time());
        tv_docid.setText(dataSet.get(listPosition).getDocument_ID());
        tv_docstatus.setText(dataSet.get(listPosition).getDocument_Verification());
        tv_doctopiclevelid.setText(dataSet.get(listPosition).getTopicLevelID());
        Log.e("tag","name=="+dataSet.get(listPosition).getDocument_Name());
        //imageView.setImageResource(dataSet.get(listPosition).getDocument_Path());

        try {
            if (dataSet.get(listPosition).getDocument_Verification().equals("") || dataSet.get(listPosition).getDocument_Verification().equals("null") || dataSet.get(listPosition).getDocument_Verification().equals(null)) {

            } else {
                if (dataSet.get(listPosition).getDocument_Verification().equals("Pending")) {
                    holder.imageViewIcon.setVisibility(View.GONE);
                    holder.view_lp_bt.setVisibility(View.GONE);
                } else if (dataSet.get(listPosition).getDocument_Verification().equals("Active")) {
                    holder.imageViewIcon.setVisibility(View.VISIBLE);
                    holder.view_lp_bt.setVisibility(View.GONE);
                } else if (dataSet.get(listPosition).getDocument_Verification().equals("Downloaded")) {
                    holder.imageViewIcon.setVisibility(View.GONE);
                    holder.view_lp_bt.setVisibility(View.VISIBLE);
                } else if (dataSet.get(listPosition).getDocument_Verification().equals("Verified")) {
                    holder.imageViewIcon.setVisibility(View.GONE);
                    holder.view_lp_bt.setVisibility(View.VISIBLE);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               // Toast.makeText(context1,"Doc View",Toast.LENGTH_SHORT).show();
                String doc_path=dataSet.get(listPosition).Document_Path;
                String doc_Name=dataSet.get(listPosition).Document_Name;
                String doc_TopicLevelID=dataSet.get(listPosition).TopicLevelID;
                internetDectector[0] = new ConnectionDetector(context1);
                isInternetPresent[0] = internetDectector[0].isConnectingToInternet();

                if(isInternetPresent[0]) {
                Doc_LessonPlanDownloadFragment.LoadDocument loadDocument = new Doc_LessonPlanDownloadFragment.LoadDocument(context1);
                loadDocument.execute(doc_path,doc_Name,doc_TopicLevelID);

                }else{
                    Toast.makeText(context1,"No Internet", Toast.LENGTH_SHORT).show();

                }
            }
        });


        holder.view_lp_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doc_ID=holder.tv_docid.getText().toString();
                String doc_status=holder.tv_docstatus.getText().toString();
                String doc_topiclevelid=holder.tv_doctopiclevelid.getText().toString();


                Log.e("doc_ID",doc_ID);
                Log.e("doc_status",doc_status);
                Log.e("doc_topiclevelid",doc_topiclevelid);

                Intent i=new Intent(context1,Activity_LessonVerification.class);
                i.putExtra("doc_ID",doc_ID);
                i.putExtra("doc_status",doc_status);
                i.putExtra("doc_topiclevelid",doc_topiclevelid);
                i.putExtra("doc_type","Lesson");
                context1.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
