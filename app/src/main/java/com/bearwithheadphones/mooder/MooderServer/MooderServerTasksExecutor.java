package com.bearwithheadphones.mooder.MooderServer;

import android.os.AsyncTask;

import com.bearwithheadphones.mooder.MooderServer.Tasks.MooderServerTask;

/**
 * Created by bartoszcwynar on 10.05.2016.
 */



public class MooderServerTasksExecutor extends AsyncTask<MooderServerTask,Void,String> {


    @Override
    protected String doInBackground(MooderServerTask... mooderServerTasks) {

        if (null != mooderServerTasks[0]) {
            return mooderServerTasks[0].execute();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        //do sth
    }
}


