package com.bearwithheadphones.mooder.Timeline;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by bartoszcwynar on 14.01.2017.
 */
public class TimelineEntry {

    public TimelineEntry(String id, String name, Bitmap moodBitmap,String moodDescription, Date date)
    {
        this.id = id;
        this.name = name;
        this.moodBitmap = moodBitmap;
        this. moodDescription = moodDescription;
        this.date = date;
    }

    public String id;
    public String name;
    public Bitmap moodBitmap;
    public String moodDescription;
    public Date date;
}


