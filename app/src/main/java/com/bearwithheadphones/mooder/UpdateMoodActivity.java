package com.bearwithheadphones.mooder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;

/**
 * Created by bartoszcwynar on 13.04.2016.
 */
public class UpdateMoodActivity extends AppCompatActivity {

    private ShareButton shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_update_mood);

        if (savedInstanceState == null) {
        }

        SquareImageView mood= (SquareImageView)findViewById(R.id.imageView);
        mood.setImageBitmap(new MoodsCreator().createMood(getIntent()
                        .getIntExtra("alpha", 100),
                getIntent().getIntExtra("red",100),
                getIntent().getIntExtra("green",100),
                getIntent().getIntExtra("blue",100)));
        mood.setScaleType(ImageView.ScaleType.FIT_XY);

        shareButton = (ShareButton) findViewById(R.id.share_btn);
        SharePhoto photo = new SharePhoto.Builder().setBitmap(new MoodsCreator().createMood(getIntent()
                .getIntExtra("alpha", 100),
                getIntent().getIntExtra("red",100),
                getIntent().getIntExtra("green",100),
                getIntent().getIntExtra("blue",100))).build();

        SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
        shareButton.setShareContent(content);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle app bar item clicks here. The app bar
        // automatically handles clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


}
