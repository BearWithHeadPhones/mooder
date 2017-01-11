package com.bearwithheadphones.mooder;


        import android.content.Intent;
        import android.content.pm.PackageInfo;
        import android.content.pm.PackageManager;
        import android.content.pm.Signature;
        import android.os.Bundle;

        import android.support.v7.app.AppCompatActivity;
        import android.util.Base64;
        import android.util.Log;

        import android.view.MenuItem;
        import android.view.View;

        import android.widget.Button;


        import com.bearwithheadphones.mooder.MooderServer.MooderServerTasksExecutor;
        import com.bearwithheadphones.mooder.MooderServer.Tasks.GetAccessTokenTask;
        import com.facebook.AccessToken;
        import com.facebook.AccessTokenTracker;
        import com.facebook.CallbackManager;
        import com.facebook.FacebookCallback;
        import com.facebook.FacebookException;
        import com.facebook.FacebookSdk;
        import com.facebook.Profile;
        import com.facebook.ProfileTracker;
        import com.facebook.login.LoginResult;
        import com.facebook.login.widget.LoginButton;

        import java.security.MessageDigest;
        import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {

    public LoginButton loginButton;
    public CallbackManager callbackManager;
    public ProfileTracker profileTracker;
    public AccessTokenTracker accessTokenTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login_activity);

        callbackManager = CallbackManager.Factory.create();

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                Profile.setCurrentProfile(currentProfile);

                if(currentProfile != null && AccessToken.getCurrentAccessToken() != null ){
                    new MooderServerTasksExecutor().execute(new GetAccessTokenTask());
                    goToActivity();
                }

            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                AccessToken.setCurrentAccessToken(newAccessToken);
                if(newAccessToken != null && Profile.getCurrentProfile() != null)
                {
                    new MooderServerTasksExecutor().execute(new GetAccessTokenTask());
                    goToActivity();
                }


            }
        };

        //accessTokenTracker.startTracking();


        loginButton = (LoginButton)findViewById(R.id.connectWithFbButton);

        loginButton.setReadPermissions("user_friends");

        if (savedInstanceState == null) {
        }
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.bearwithheadphones.mooder",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if(accessToken != null && !accessToken.isExpired() && Profile.getCurrentProfile() != null){
            Log.d("AccessToken",accessToken.getToken().toString());

            new MooderServerTasksExecutor().execute(new GetAccessTokenTask());
            goToActivity();
        }


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if(Profile.getCurrentProfile() == null){
                    profileTracker.startTracking();
                    accessTokenTracker.startTracking();
                }else{
                    profileTracker.stopTracking();
                    accessTokenTracker.stopTracking();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });

        Button launch = (Button)findViewById(R.id.button);
        launch.setOnClickListener(new SquareImageView.OnClickListener() {
            public void onClick(View v) {

                goToActivity();

            }
        });



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        accessTokenTracker.stopTracking();
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


    public void goToActivity(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        //((Activity) getApplicationContext()).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        finish();
    }

}
