package com.det.skillinvillage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


public class Doc_PDFAdapter extends ArrayAdapter<File> {
    Context context;
    ViewHolder viewHolder;
    ArrayList<File> al_pdf;
    ArrayList<DocView_Module> dataSet;

//    public Doc_PDFAdapter(Context context, ArrayList<File> al_pdf,ArrayList<DocView_Module> al_questionpaper) {
//        super(context, R.layout.adapter_pdf, al_pdf);
//        this.context = context;
//        this.al_pdf = al_pdf;
//        this.dataSet = al_questionpaper;
//
//    }
public Doc_PDFAdapter(Context context, ArrayList<File> al_pdf) {
    super(context, R.layout.adapter_pdf, al_pdf);
    this.context = context;
    this.al_pdf = al_pdf;
    //this.dataSet = al_questionpaper;

}


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (al_pdf.size() > 0) {
            return al_pdf.size();
        } else {
            return 1;
        }
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {


        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.adapter_pdf, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_filename = view.findViewById(R.id.tv_name);
            viewHolder.iv_img = view.findViewById(R.id.iv_image);
            viewHolder.view_lp_bt= view.findViewById(R.id.view_lp_bt);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();

        }

        viewHolder.tv_filename.setText(al_pdf.get(position).getName());
//        viewHolder.tv_filename.setText(dataSet.get(position).getDocument_ID());
//        Log.e("docid",dataSet.get(position).getDocument_ID());
        for (int j = 0; j < al_pdf.size(); j++) {
            if (al_pdf.get(position).getName().endsWith(".pdf")) {
                viewHolder.iv_img.setImageResource(R.drawable.pdficon);
            }else if (al_pdf.get(position).getName().endsWith(".doc")||al_pdf.get(position).getName().endsWith(".docx")) {
                viewHolder.iv_img.setImageResource(R.drawable.wordicon);
            }
        }


        viewHolder.view_lp_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),Activity_LessonVerification.class);
                context.startActivity(i);


            }
        });
        return view;

    }

    public class ViewHolder {

        TextView tv_filename;
        ImageView iv_img,view_lp_bt;


    }

}
