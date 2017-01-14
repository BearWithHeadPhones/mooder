package com.bearwithheadphones.mooder.Timeline;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bearwithheadphones.mooder.MooderServer.MooderServerTasksExecutor;
import com.bearwithheadphones.mooder.MooderServer.Tasks.GetTimelineEntriesTask;
import com.bearwithheadphones.mooder.R;
import com.bearwithheadphones.mooder.Timeline.MoodsTimelineEntryAdapter;

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

        GetTimelineEntriesTask getTimelineEntriesTask = new GetTimelineEntriesTask();
        getTimelineEntriesTask.moodsTimelineEntryAdapter = moodsTimelineEntryAdapter;
        new MooderServerTasksExecutor().execute(getTimelineEntriesTask);
        listView.setAdapter(moodsTimelineEntryAdapter);


    return rootView;
    }

}
