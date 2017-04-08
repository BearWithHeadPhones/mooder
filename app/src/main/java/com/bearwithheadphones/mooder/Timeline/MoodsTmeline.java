package com.bearwithheadphones.mooder.Timeline;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.bearwithheadphones.mooder.Server.ServerTasksExecutor;
import com.bearwithheadphones.mooder.Server.Tasks.GetTimelineEntriesTask;
import com.bearwithheadphones.mooder.R;
import com.bearwithheadphones.mooder.Server.Tasks.ServerTask;

import java.util.Random;
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
    SwipeRefreshLayout mSwipeRefreshLayout;
    MoodsTmeline moodsTmeline;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.moods_timeline, container, false);

        moodsTmeline = this;
        final MoodsTimelineEntryAdapter moodsTimelineEntryAdapter = new MoodsTimelineEntryAdapter(inflater);


        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                     @Override
                                                     public void onRefresh() {
                                                         GetTimelineEntriesTask getTimelineEntriesTask = new GetTimelineEntriesTask(moodsTmeline);
                                                         getTimelineEntriesTask.moodsTimelineEntryAdapter = moodsTimelineEntryAdapter;
                                                         new ServerTasksExecutor(getActivity()).run(getTimelineEntriesTask);

                                                     }
                                                 });

        listView = (ListView)rootView.findViewById(R.id.listView);

        View progress = inflater.inflate(R.layout.list_view_progress, null);
        listView.addFooterView(progress);


        GetTimelineEntriesTask getTimelineEntriesTask = new GetTimelineEntriesTask(this);
        getTimelineEntriesTask.moodsTimelineEntryAdapter = moodsTimelineEntryAdapter;
        new ServerTasksExecutor(getActivity()).run(getTimelineEntriesTask);
        listView.setAdapter(moodsTimelineEntryAdapter);
        listView.setOnScrollListener(new EndlessScrollListener(moodsTmeline,moodsTimelineEntryAdapter));

        /*timer = new Timer();
        TimerTask periodicallyCheckForUpdates = new TimerTask() {
            @Override
            public void run() {
                GetTimelineEntriesTask getTimelineEntriesTask = new GetTimelineEntriesTask();
                getTimelineEntriesTask.moodsTimelineEntryAdapter = moodsTimelineEntryAdapter;
                getTimelineEntriesTask.length = new Random().nextInt(10);
                new ServerTasksExecutor(getActivity()).run(getTimelineEntriesTask);
            }
        };
        timer.schedule(periodicallyCheckForUpdates,0,20000);*/
    return rootView;
    }

    public void notifyWithresult(ServerTask.Result result){
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
