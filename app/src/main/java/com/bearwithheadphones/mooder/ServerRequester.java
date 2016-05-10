package com.bearwithheadphones.mooder;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
 * Created by bartoszcwynar on 05.05.2016.
 */
public class ServerRequester extends AsyncTask<String, Void, String>

{



    public Activity activityref;


    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;

    // Will contain the raw JSON response as a string.
    String forecastJsonStr = null;

    @Override
    protected String doInBackground(String... str) {
        try
        {

            URL url = new URL(MooderServerManager.getInstance().getMooderServerUrl()+ "register-by-token/facebook/?USER=" +Profile.getCurrentProfile().getId() +"&access_token=" +AccessToken.getCurrentAccessToken().getToken());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            //urlConnection.setRequestProperty("Authorization","Token 8IetNm6ZoCmmpo5d1J2ljsQZynpjzq");
            //urlConnection.setRequestProperty("USER", Profile.getCurrentProfile().getId());
            //urlConnection.setRequestProperty("access_token", "123");

            Log.d("Mooder", urlConnection.toString());
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
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

    protected void onPostExecute(String result) {

        try{
            MooderServerManager.getInstance().setAccessToken(new JSONObject(result).getString("token"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        Toast.makeText(activityref, "FROM Server requester" + MooderServerManager.getInstance().getAccessToken(), Toast.LENGTH_LONG).show();

        MainActivity activityRefCast = (MainActivity) activityref;
        activityRefCast.goToActivity();

                Log.d("MOODER AsyncTask", result);

    }

}
