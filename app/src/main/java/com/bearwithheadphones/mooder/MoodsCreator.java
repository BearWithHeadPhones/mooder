package com.bearwithheadphones.mooder;

/**
 * Created by bartoszcwynar on 12.04.2016.
 */
import android.content.Context;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.util.Pair;

//import com.bearwithheadphones.mooder.Mood.Mood;

import com.bearwithheadphones.mooder.Mood.Mood;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MoodsCreator {

    private static MoodsCreator moodsCreator;

    //TODO get rid of contructor parameter
    private MoodsCreator(Resources resources){

        String result;
        try {
            InputStream inputStream = resources.openRawResource(R.raw.moods);

            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }
            result = buffer.toString();
            Log.d("MOODS", result);

            JSONObject moodsJson = new JSONObject(result);
            JSONArray moodsJsonArray = new JSONArray(moodsJson.get("moods").toString());

            for(int i =0;i<moodsJsonArray.length();++i){
                JSONObject moodfromJson = moodsJsonArray.getJSONObject(i);
                Mood mood = new Mood(moodfromJson.get("name").toString(),moodfromJson.getInt("red"),moodfromJson.getInt("green"), moodfromJson.getInt("blue"), moodfromJson.getInt("alpha"));

                Log.d("MOOD",mood.name + " "+mood.alpha + " "+ mood.red);
                moods.put(moodfromJson.get("name").toString(),mood);
            }

        Log.d("moodsMap",moods.toString());
        }
        catch(Exception e){

        }

    }

    //TODO get rid of contructor parameter
    public static synchronized  MoodsCreator getInstance(Resources resources){
        if(moodsCreator == null){
            moodsCreator = new MoodsCreator(resources);
        }
        return moodsCreator;
    }

    //TODO might be null be carefull
    public static synchronized  MoodsCreator getInstance(){
        return moodsCreator;
    }

    private Map<String,Mood> moods = new HashMap<String,Mood>();


    public Bitmap getMoodBitmapByName(String moodName, int width, int height){

        if(moods.containsKey(moodName)){
            Mood mood = moods.get(moodName);
            return createMood(mood.alpha,mood.red,mood.green, mood.blue, width, height);
        }

        return createMood(0,0,0,0,1,1);

    }

    public ArrayList<Pair<String,Bitmap>> getAllMoodBitmaps(){
        ArrayList<Pair<String,Bitmap>> allMoodBitmaps = new ArrayList<Pair<String,Bitmap>>();

        for(Map.Entry<String,Mood> mood: moods.entrySet()){
            allMoodBitmaps.add(new Pair<String,Bitmap>(mood.getKey(),
                    createMood(mood.getValue().alpha, mood.getValue().red, mood.getValue().green, mood.getValue().blue,1,1)));
        }
        Log.d("allMoodBitmaps",allMoodBitmaps.toString());
        return allMoodBitmaps;
    }

    private Bitmap createMood(int alpha,int red, int green,int blue, int width , int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();

        Log.d("createMood",alpha + " " +red + " '"+green +" " +blue);
        paint.setARGB(alpha, red, green, blue);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);
        return bitmap;
    }
}
