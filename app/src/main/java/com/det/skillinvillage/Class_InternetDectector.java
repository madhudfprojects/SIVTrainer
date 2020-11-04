package com.det.skillinvillage;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by User on 8/16/2017.
 */

public class Class_InternetDectector {

    private Context _context;

    public Class_InternetDectector(Context context){
        this._context = context;
    }

    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)

                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                    //Added by shivaleela on aug 10th 2019
                    ///************************************************
//                    if (info[i].getState() == NetworkInfo.State.)
//                    {
//                        return false;
//                    }
//
//                    if (info[i].getState() == NetworkInfo.State.DISCONNECTED)
//                    {
//                        return false;
//                    }
                  ///************************************************

                }
        }
        return false;
    }
}
