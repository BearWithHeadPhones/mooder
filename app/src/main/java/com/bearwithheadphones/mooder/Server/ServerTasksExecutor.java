package com.bearwithheadphones.mooder.Server;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.bearwithheadphones.mooder.Server.Tasks.ServerTask;
import com.bearwithheadphones.mooder.Connectivity.ConnectionManager;

/**
 * Created by bartoszcwynar on 10.05.2016.
 */



public class ServerTasksExecutor extends AsyncTask<ServerTask,Void,String> {

    private Context context;
    public ServerTasksExecutor(Context context){
        this.context = context;
    }
    ServerTask serverTask;

    public void run(ServerTask serverTask){

        if(!ConnectionManager.getInstance(context).isInternetConnectionAvailable()) {
            serverTask.handleResult(ServerTask.Result.NoInternetConnection);
            return;
        }

        execute(serverTask);
    }

    @Override
    protected String doInBackground(ServerTask... serverTasks) {

        if (null != serverTasks[0]) {
            serverTask = serverTasks[0];
            return serverTask.execute();
        }

        return "FAILURE";
    }

    @Override
    protected void onPostExecute(String s) {
        serverTask.postExecute(s);
    }
}


