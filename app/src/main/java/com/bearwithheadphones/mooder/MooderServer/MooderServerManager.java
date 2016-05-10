package com.bearwithheadphones.mooder.MooderServer;

/**
 * Created by bartoszcwynar on 10.05.2016.
 */
public class MooderServerManager {

    private static MooderServerManager mooderServerManager;
    private final String mooderServerUrl = "http://192.168.1.101:8000/";
    private String accessToken = "";

    private MooderServerManager(){

    }

    public static synchronized  MooderServerManager getInstance(){
        if(mooderServerManager == null){
            mooderServerManager = new MooderServerManager();
        }
        return mooderServerManager;
    }


    public synchronized String getAccessToken(){
        return accessToken;
    }

    public synchronized void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public synchronized String getMooderServerUrl(){
        return mooderServerUrl;
    }
}
