package com.bearwithheadphones.mooder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.bearwithheadphones.mooder.MooderServer.MooderServerManager;
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



        listView = (ListView)rootView.findViewById(R.id.listView);


        /*GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            JSONArray rawName = response.getJSONObject().getJSONArray("data");
                            int x = 1000000000;
                            while(x> 0){
                                x--;
                            }
                            x = 1000000000;
                            while(x> 0){
                                x--;
                            }
                            x = 1000000000;
                            while(x> 0){
                                x--;
                            }
                            x = 1000000000;
                            while(x> 0){
                                x--;
                            }

                                Random generator = new Random();
                                int costam = 10; //generator.nextInt(5);
                                for(int i = 0;i<costam;i++){

                                    moodsTimelineEntryAdapter.ziomeczki.add("Bartosz Cwynar De La Vega");
                                    moodsTimelineEntryAdapter.bitmaps.add(new MoodsCreator().createMood(1000, generator.nextInt(255), generator.nextInt(255), generator.nextInt(255)));
                                    moodsTimelineEntryAdapter.notifyDataSetChanged();
                                }


                            //listView.setAdapter(moodsTimelineEntryAdapter);


                            Log.d("MOODER",rawName.toString(4));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).executeAsync();*/



        GetUsersMoodsTask getUsersMoodsTask = new GetUsersMoodsTask();
        getUsersMoodsTask.moodsTimelineEntryAdapter = moodsTimelineEntryAdapter;
        new MooderServerTasksExecutor().execute(getUsersMoodsTask);
        listView.setAdapter(moodsTimelineEntryAdapter);


    return rootView;
    }

}
