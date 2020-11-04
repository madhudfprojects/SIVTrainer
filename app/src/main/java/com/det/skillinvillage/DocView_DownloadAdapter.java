package com.det.skillinvillage;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


public class DocView_DownloadAdapter extends RecyclerView.Adapter<DocView_DownloadAdapter.MyViewHolder> {

    private ArrayList<QunPaperDoc_Module> dataSet;
    private Context context1;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_docName;
        TextView tv_docdate;
        TextView tv_doctime;
        ImageView imageViewIcon;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.tv_docName = itemView.findViewById(R.id.tv_docName);
            this.tv_docdate = itemView.findViewById(R.id.tv_docdate);
            this.tv_doctime = itemView.findViewById(R.id.tv_doctime);
            this.imageViewIcon = itemView.findViewById(R.id.doc_download_iv);
        }
    }

    public DocView_DownloadAdapter(Context context, ArrayList<QunPaperDoc_Module> data) {
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
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView tv_docName = holder.tv_docName;
        TextView tv_docdate = holder.tv_docdate;
        TextView tv_doctime = holder.tv_doctime;
        ImageView imageView = holder.imageViewIcon;
        final ConnectionDetector[] internetDectector = new ConnectionDetector[1];

        final Boolean[] isInternetPresent = {false};

        tv_docName.setText(dataSet.get(listPosition).getDocument_Name());
        tv_docdate.setText(dataSet.get(listPosition).getDocument_Date());
        tv_doctime.setText(dataSet.get(listPosition).getDocument_Time());
        Log.e("tag","name=="+dataSet.get(listPosition).getDocument_Name());
        //imageView.setImageResource(dataSet.get(listPosition).getDocument_Path());


        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             //   Toast.makeText(context1,"Doc View",Toast.LENGTH_SHORT).show();
                String doc_path=dataSet.get(listPosition).Document_Path;
                String doc_Name=dataSet.get(listPosition).Document_Name;

                internetDectector[0] = new ConnectionDetector(context1);
                isInternetPresent[0] = internetDectector[0].isConnectingToInternet();

                Log.e("tag","isInternetPresent="+isInternetPresent[0]);
                if(isInternetPresent[0]) {
                Doc_QunPaperDownloadFragment.LoadDocument loadDocument = new Doc_QunPaperDownloadFragment.LoadDocument(context1);
                loadDocument.execute(doc_path,doc_Name);
                }else{
                    Toast.makeText(context1,"No Internet", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
