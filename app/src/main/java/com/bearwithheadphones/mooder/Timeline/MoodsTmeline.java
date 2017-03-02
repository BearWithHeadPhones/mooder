package com.bearwithheadphones.mooder.Timeline;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bearwithheadphones.mooder.Server.ServerTasksExecutor;
import com.bearwithheadphones.mooder.Server.Tasks.GetTimelineEntriesTask;
import com.bearwithheadphones.mooder.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by bartoszcwynar on 12.04.2016.
 */
public class MoodsTmeline extends Fragment {

    private Timer timer;
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
        new ServerTasksExecutor(getActivity()).run(getTimelineEntriesTask);
        listView.setAdapter(moodsTimelineEntryAdapter);


        timer = new Timer();
        TimerTask periodicallyCheckForUpdates = new TimerTask() {
            @Override
            public void run() {
                GetTimelineEntriesTask getTimelineEntriesTask = new GetTimelineEntriesTask();
                getTimelineEntriesTask.moodsTimelineEntryAdapter = moodsTimelineEntryAdapter;
                new ServerTasksExecutor(getActivity()).run(getTimelineEntriesTask);
            }
        };
        timer.schedule(periodicallyCheckForUpdates,0,10000);
    return rootView;
    }

}
