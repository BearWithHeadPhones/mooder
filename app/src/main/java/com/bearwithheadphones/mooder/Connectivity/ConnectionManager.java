package com.bearwithheadphones.mooder.Connectivity;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by bartoszcwynar on 01.03.2017.
 */
public class ConnectionManager {

    ConnectivityManager connectivityManager;
    private static ConnectionManager connectionManager;

    private ConnectionManager(Context context){
        connectivityManager = (ConnectivityManager)context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }


    public static synchronized  ConnectionManager getInstance(Context context){
        if(connectionManager == null){
            connectionManager = new ConnectionManager(context);
        }
        return connectionManager;
    }

    public boolean isInternetConnectionAvailable(){
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
