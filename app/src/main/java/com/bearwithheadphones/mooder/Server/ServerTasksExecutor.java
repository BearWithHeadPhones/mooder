package com.bearwithheadphones.mooder.Server;

import android.os.AsyncTask;

import com.bearwithheadphones.mooder.Server.Tasks.ServerTask;

/**
 * Created by bartoszcwynar on 10.05.2016.
 */



public class ServerTasksExecutor extends AsyncTask<ServerTask,Void,String> {
    ServerTask serverTask;

    @Override
    protected String doInBackground(ServerTask... serverTasks) {

        if (null != serverTasks[0]) {
            serverTask = serverTasks[0];
            return serverTask.execute();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        serverTask.postExecute(s);
    }
}


