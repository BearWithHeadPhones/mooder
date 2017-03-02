package com.bearwithheadphones.mooder;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bearwithheadphones.mooder.Server.ServerTasksExecutor;
import com.bearwithheadphones.mooder.Server.Tasks.ServerTask;
import com.bearwithheadphones.mooder.Server.Tasks.UpdateUsersMoodTask;
import com.facebook.FacebookSdk;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;

/**
 * Created by bartoszcwynar on 13.04.2016.
 */
public class UpdateMoodActivity extends AppCompatActivity {

    private ShareButton shareButton;
    private ProgressBar progressBar;
    EditText description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_update_mood);


        if (savedInstanceState == null) {
        }

        description = (EditText)findViewById(R.id.editText);
        SquareImageView mood= (SquareImageView)findViewById(R.id.imageView);

        Button updateMoodButton = (Button)findViewById(R.id.updateMoodButton);



        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        updateMoodButton.setOnClickListener(new SquareImageView.OnClickListener() {
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                progressBar.setVisibility(View.VISIBLE);
                new ServerTasksExecutor(getBaseContext()).run(new UpdateUsersMoodTask(getIntent()
                        .getStringExtra("moodName"), description.getText().toString(), UpdateMoodActivity.this));
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
        //shareButton.setText(description.getText().toString());
        shareButton.setVisibility(View.GONE);

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
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public void notifyWithresult(ServerTask.Result result){
        if(result == ServerTask.Result.NoInternetConnection)
        {
            Toast.makeText(this, "No internet connection available :(", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        progressBar.setVisibility(View.GONE);
        shareButton.setVisibility(View.VISIBLE);

        Toast.makeText(this,"You've successfully updated Your mood :)",Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"... now You can share it on Facebook",Toast.LENGTH_SHORT).show();
    }


}
