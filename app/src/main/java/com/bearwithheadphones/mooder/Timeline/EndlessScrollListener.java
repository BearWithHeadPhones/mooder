package com.bearwithheadphones.mooder.Timeline;

import android.content.Context;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.bearwithheadphones.mooder.Server.ServerTasksExecutor;
import com.bearwithheadphones.mooder.Server.Tasks.GetTimelineEntriesTask;

import java.util.Random;

public class EndlessScrollListener implements AbsListView.OnScrollListener {

    private int count = 0;

    private boolean loading = true;
    private MoodsTimelineEntryAdapter moodsTimelineEntryAdapter;
    private MoodsTmeline moodsTmeline;

    public EndlessScrollListener(MoodsTmeline moodsTmeline, MoodsTimelineEntryAdapter moodsTimelineEntryAdapter){
        this.moodsTmeline = moodsTmeline;
        this.moodsTimelineEntryAdapter = moodsTimelineEntryAdapter;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (loading) {
            if (totalItemCount > firstVisibleItem + 2) {
                loading = false;

            }
        }

        Log.d("EndlessScrollListener", "total:" + totalItemCount + " first:" + firstVisibleItem);
        if (!loading && ( totalItemCount  == firstVisibleItem + 2)) {
            // I load the next page of gigs using a background task,
            // but you can call any function here.
            //new LoadGigsTask().execute(currentPage + 1);
            GetTimelineEntriesTask getTimelineEntriesTask = new GetTimelineEntriesTask(moodsTmeline);
            getTimelineEntriesTask.moodsTimelineEntryAdapter = moodsTimelineEntryAdapter;
            count++;
            getTimelineEntriesTask.length  = (int)(Math.floor(totalItemCount / 10) + 1);
            new ServerTasksExecutor(moodsTmeline.getContext()).run(getTimelineEntriesTask);
            Log.d("EndlessScrollListener", "UPDATE LIStView ");
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
}
