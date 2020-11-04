package com.det.skillinvillage;

/**
 * Created by Admin on 20-07-2018.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

//import com.android.sripad.leadnew_22_6_2018.R;

public class DocView_Download_Fragment extends Fragment
{

    private ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.docview_donwload_fragment, container, false);

        progressBar = view.findViewById(R.id.progressBar);

        return view;
    }


}//end of fragment class
