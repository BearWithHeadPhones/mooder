package com.bearwithheadphones.mooder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bearwithheadphones.mooder.MooderServer.MooderServerTasksExecutor;
import com.bearwithheadphones.mooder.MooderServer.Tasks.GetAccessTokenTask;
import com.bearwithheadphones.mooder.MooderServer.Tasks.UpdateUsersMoodTask;
import com.facebook.FacebookSdk;
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

        Button updateMoodButton = (Button)findViewById(R.id.updateMoodButton);

        updateMoodButton.setOnClickListener(new SquareImageView.OnClickListener() {
            public void onClick(View v) {
                new MooderServerTasksExecutor().execute(new UpdateUsersMoodTask(getIntent()
                        .getStringExtra("moodName")));
            }
        });
        mood.setImageBitmap(MoodsCreator.getInstance(this.getResources()).getMoodBitmapByName(getIntent()
                .getStringExtra("moodName"),1,1));

        mood.setScaleType(ImageView.ScaleType.FIT_XY);

        shareButton = (ShareButton) findViewById(R.id.share_btn);
        SharePhoto photo = new SharePhoto.Builder().setBitmap(MoodsCreator.getInstance(this.getResources()).getMoodBitmapByName(getIntent()
                .getStringExtra("moodName"),500,500)).build();

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
