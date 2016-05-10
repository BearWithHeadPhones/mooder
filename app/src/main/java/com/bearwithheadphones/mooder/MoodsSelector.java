package com.bearwithheadphones.mooder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;




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
            int red = generator.nextInt(255);
            int green = generator.nextInt(255);
            int blue = generator.nextInt(255);
            imageAdapter.addSquareImageView(moodsCreator.createMood(1000, red, green, blue),1000,red,green,blue);
            x++;
        }


        for(final SquareImageView squareImageView: imageAdapter.squareImageViews) {
            squareImageView.setOnClickListener(new SquareImageView.OnClickListener() {
                public void onClick(View v) {

                    //v.startAnimation(AnimationUtils.loadAnimation(rootView.getContext(), R.anim.mood_click_animation));
                    Intent intent = new Intent(rootView.getContext(), UpdateMoodActivity.class);

                    Log.d("MOODER", Integer.toString((Integer)squareImageView.getTag()));
                    intent.putExtra("alpha",squareImageView.alpha);
                    intent.putExtra("red",squareImageView.red);
                    intent.putExtra("green",squareImageView.green);
                    intent.putExtra("blue",squareImageView.blue);
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
