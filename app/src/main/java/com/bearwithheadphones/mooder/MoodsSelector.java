package com.bearwithheadphones.mooder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by bartoszcwynar on 12.04.2016.
 */
public class MoodsSelector extends Fragment {

    public MoodsSelector() {

    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.moods_selector, container, false);
        ImageAdapter imageAdapter = new ImageAdapter(rootView.getContext());


        MoodsCreator moodsCreator = new MoodsCreator();
        int x =0;
        while(x <1000){
            Random generator = new Random();
            imageAdapter.addSquareImageView(moodsCreator.createMood(1000, generator.nextInt(255), generator.nextInt(255), generator.nextInt(255)));
            x++;
        }


        for(SquareImageView squareImageView: imageAdapter.squareImageViews) {
            squareImageView.setOnClickListener(new SquareImageView.OnClickListener() {
                public void onClick(View v) {

                    //v.startAnimation(AnimationUtils.loadAnimation(rootView.getContext(), R.anim.mood_click_animation));
                    Intent intent = new Intent(rootView.getContext(), UpdateMoodActivity.class);
                    startActivity(intent);
                    ((Activity) rootView.getContext()).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                }
            });
        }


                //ListView listView = (ListView)rootView.findViewById(R.id.listview_forecast);

                GridView gridView = (GridView) rootView.findViewById(R.id.gridView);

                gridView.setAdapter(imageAdapter);
                return rootView;
            }
        }
