package com.bearwithheadphones.mooder.MooderServer.Tasks;

import android.util.Log;

import com.bearwithheadphones.mooder.MooderServer.MooderServerManager;
import com.facebook.AccessToken;
import com.facebook.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bartoszcwynar on 10.05.2016.
 */
public class GetUsersTask implements MooderServerTask {

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    final String requestUrl = "users/";

    public GetUsersTask(){

    }

    @Override
    public String execute() {
        try
        {

            URL url = new URL(MooderServerManager.getInstance().getMooderServerUrl()+ requestUrl);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Token " + MooderServerManager.getInstance().getAccessToken());
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }


            Log.d("GetUsersTask", buffer.toString());

            return "ok";
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return "Cannot Connect";
    }
}

