package com.bearwithheadphones.mooder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by bartoszcwynar on 12.04.2016.
 */
public class MoodsTmeline extends Fragment {

    public MoodsTmeline(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.moods_timeline, container, false);


        MoodsTimelineEntryAdapter moodsTimelineEntryAdapter = new MoodsTimelineEntryAdapter(inflater);
        Random generator = new Random();
        for(int i = 0;i<100;i++){
            moodsTimelineEntryAdapter.bitmaps.add(new MoodsCreator().createMood(1000, generator.nextInt(255), generator.nextInt(255), generator.nextInt(255)));
        }


        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ListView listView = (ListView)rootView.findViewById(R.id.listView);
        listView.setAdapter(moodsTimelineEntryAdapter);



    return rootView;
    }
}
