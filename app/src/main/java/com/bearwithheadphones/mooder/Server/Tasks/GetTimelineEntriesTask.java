package com.bearwithheadphones.mooder.Server.Tasks;

import android.util.Log;

import com.bearwithheadphones.mooder.Server.MooderServerManager;
import com.bearwithheadphones.mooder.MoodsCreator;
import com.bearwithheadphones.mooder.Timeline.MoodsTimelineEntryAdapter;
import com.bearwithheadphones.mooder.Timeline.TimelineEntry;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by bartoszcwynar on 10.05.2016.
 */
public class GetTimelineEntriesTask implements ServerTask {

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    final String requestUrl = "moods/";

    public GetTimelineEntriesTask(){

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
            //TODO need to fix this!!!
            //return execute();
        }
        return "Cannot Connect";
    }


    @Override
    public void postExecute(String result) {

        try{
            Log.d("GetTimelineEntryTask", new JSONArray(result).toString());
            JSONArray timelineEntriesJson = new JSONArray(result);
            for(int i = 0;i<timelineEntriesJson.length();i++){
                Log.d("MOODER", timelineEntriesJson.getJSONObject(i).get("moodType").toString());

                JSONObject timelineEntryJson = timelineEntriesJson.getJSONObject(i);

                Date created = new Date();
                try{
                    created = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(timelineEntryJson.get("created").toString());
                }catch(ParseException e)
                {
                    e.printStackTrace();
                }


                TimelineEntry timelineEntry = new TimelineEntry(timelineEntryJson.getJSONObject("userProfile").get("username").toString(),
                        timelineEntryJson.getJSONObject("userProfile").get("name").toString(),
                        MoodsCreator.getInstance().getMoodBitmapByName(timelineEntryJson.get("moodType").toString(), 1, 1),
                        timelineEntryJson.get("description").toString(),
                        created);


                moodsTimelineEntryAdapter.entries.add(timelineEntry);

            }
        }

        catch (JSONException e){
            e.printStackTrace();
        }

        moodsTimelineEntryAdapter.notifyDataSetChanged();
    }
}

