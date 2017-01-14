package com.bearwithheadphones.mooder.Timeline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bearwithheadphones.mooder.R;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by bartoszcwynar on 12.04.2016.
 */
public class MoodsTimelineEntryAdapter extends BaseAdapter {

    public MoodsTimelineEntryAdapter(LayoutInflater inflater){

        this.inflater = inflater;
    }
    public ArrayList<TimelineEntry> entries = new ArrayList<TimelineEntry>();
    private final LayoutInflater inflater;

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TimelineEntry timelineEntry = entries.get(position);

        View rowView=this.inflater.inflate(R.layout.moods_timeline_entry, null, true);

        TextView username = (TextView) rowView.findViewById(R.id.username);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
        TextView description = (TextView) rowView.findViewById(R.id.description);
        TextView date = (TextView) rowView.findViewById(R.id.date);


        ProfilePictureView profilePicture = (ProfilePictureView) rowView.findViewById(R.id.photo);
        profilePicture.setProfileId(Profile.getCurrentProfile().getId());

        username.setText(timelineEntry.username);
        imageView.setImageBitmap(timelineEntry.moodBitmap);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        description.setText(timelineEntry.moodDescription);
        date.setText(new SimpleDateFormat("MM-dd-yyyy HH:mm").format(timelineEntry.date).toString());

        return rowView;
    }
}
