package com.bearwithheadphones.mooder.MooderServer.Tasks;

import com.bearwithheadphones.mooder.MooderServer.MooderServerManager;
import com.bearwithheadphones.mooder.Timeline.MoodsTimelineEntryAdapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bartoszcwynar on 10.05.2016.
 */
public class UpdateUsersMoodTask implements MooderServerTask {

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    //final String requestUrl = "moods/";
    String moodType;
    String description;

    final String requestUrl = "moods/";



    public UpdateUsersMoodTask(String moodType, String description){

        this.moodType = moodType;
        this.description =description;
    }


    public MoodsTimelineEntryAdapter moodsTimelineEntryAdapter;

    @Override
    public String execute() {
        try
        {

            URL url = new URL(MooderServerManager.getInstance().getMooderServerUrl()+ requestUrl );

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Authorization", "Token " + MooderServerManager.getInstance().getAccessToken());

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write("moodType="+moodType);
            writer.write("&description="+description);

            writer.flush();
            writer.close();
            os.close();
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

    }
}

