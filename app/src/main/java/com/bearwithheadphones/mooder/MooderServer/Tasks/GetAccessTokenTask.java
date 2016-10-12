package com.bearwithheadphones.mooder.MooderServer.Tasks;

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
public class GetAccessTokenTask implements MooderServerTask {

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    final String requestUrl = "register-by-token/facebook/?USER=" +Profile.getCurrentProfile().getId() +"&access_token=" +AccessToken.getCurrentAccessToken().getToken();

    public GetAccessTokenTask(){

    }

    @Override
    public String execute() {
        try
        {

            URL url = new URL(MooderServerManager.getInstance().getMooderServerUrl()+ requestUrl);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }


            return buffer.toString();

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return "Cannot Connect";
    }


    @Override
    public void postExecute(String result) {

        try{
            MooderServerManager.getInstance().setAccessToken(new JSONObject(result).getString("token"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
