package com.det.skillinvillage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
	
	private Context _context;
	
	public ConnectionDetector(Context context){
		this._context = context;
	}

	public boolean isConnectingToInternet(){
		ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
		  if (connectivity != null) 
		  {
			  NetworkInfo[] info = connectivity.getAllNetworkInfo();
			  if (info != null)
				  for (int i = 0; i < info.length; i++) 
					  if (info[i].getState() == NetworkInfo.State.CONNECTED)
					  {
						  return true;
					  }
			  NetworkInfo wifiNetwork = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			  if (wifiNetwork != null && wifiNetwork.isConnected()) {
				  return true;
			  }

			  NetworkInfo mobileNetwork = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
              return mobileNetwork != null && mobileNetwork.isConnected();
		  }
		  return false;
	}
}
