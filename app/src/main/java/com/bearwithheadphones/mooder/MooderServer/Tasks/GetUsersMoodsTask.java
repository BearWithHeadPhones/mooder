package com.bearwithheadphones.mooder.MooderServer.Tasks;

import android.util.Log;

import com.bearwithheadphones.mooder.MooderServer.MooderServerManager;
import com.bearwithheadphones.mooder.MoodsCreator;
import com.bearwithheadphones.mooder.MoodsTimelineEntryAdapter;
import com.facebook.AccessToken;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Created by bartoszcwynar on 10.05.2016.
 */
public class GetUsersMoodsTask implements MooderServerTask {

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    final String requestUrl = "moods/";

    public GetUsersMoodsTask(){

    }


    public MoodsTimelineEntryAdapter moodsTimelineEntryAdapter;

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
            Log.d("GetUsersMoodsTask", new JSONArray(result).toString());
            Random generator = new Random();
            JSONArray moods = new JSONArray(result);
            for(int i = 0;i<moods.length();i++){
                Log.d("MOODER", moods.getJSONObject(i).get("moodType").toString());
                moodsTimelineEntryAdapter.ziomeczki.add(moods.getJSONObject(i).get("moodType").toString());
                //TODO MoodsCreator might be null here.
                moodsTimelineEntryAdapter.bitmaps.add(MoodsCreator.getInstance().getMoodBitmapByName(moods.getJSONObject(i).get("moodType").toString()));
                //moodsTimelineEntryAdapter.bitmaps.add(new MoodsCreator().createMood(1000, generator.nextInt(255), generator.nextInt(255), generator.nextInt(255)));
            }
        }

        catch (JSONException e){
            e.printStackTrace();
        }

        moodsTimelineEntryAdapter.notifyDataSetChanged();
    }
}

