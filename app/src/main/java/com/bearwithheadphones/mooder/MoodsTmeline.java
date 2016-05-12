package com.bearwithheadphones.mooder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bearwithheadphones.mooder.MooderServer.MooderServerTasksExecutor;
import com.bearwithheadphones.mooder.MooderServer.Tasks.GetUsersMoodsTask;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Random;

/**
 * Created by bartoszcwynar on 12.04.2016.
 */
public class MoodsTmeline extends Fragment {


    public MoodsTmeline(){

    }

    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.moods_timeline, container, false);


        final MoodsTimelineEntryAdapter moodsTimelineEntryAdapter = new MoodsTimelineEntryAdapter(inflater);

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listView = (ListView)rootView.findViewById(R.id.listView);

        GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            JSONArray rawName = response.getJSONObject().getJSONArray("data");

                            for(int i = 0;i<rawName.length();i++){
                                Log.d("MOODER", rawName.getJSONObject(i).get("name").toString());
                                moodsTimelineEntryAdapter.ziomeczki.add(i, rawName.getJSONObject(i).get("name").toString());
                                Random generator = new Random();
                                moodsTimelineEntryAdapter.bitmaps.add(new MoodsCreator().createMood(1000, generator.nextInt(255), generator.nextInt(255), generator.nextInt(255)));

                            }
                            //listView.setAdapter(moodsTimelineEntryAdapter);


                            Log.d("MOODER",rawName.toString(4));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).executeAsync();


        for(int i = 0;i<10;i++){

            moodsTimelineEntryAdapter.ziomeczki.add("Bartosz Cwynar De La Vega");
            Random generator = new Random();
            moodsTimelineEntryAdapter.bitmaps.add(new MoodsCreator().createMood(1000, generator.nextInt(255), generator.nextInt(255), generator.nextInt(255)));
        }


        new MooderServerTasksExecutor().execute(new GetUsersMoodsTask());
        listView.setAdapter(moodsTimelineEntryAdapter);


    return rootView;
    }
}
