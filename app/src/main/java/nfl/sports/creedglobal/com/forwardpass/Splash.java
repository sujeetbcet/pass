package nfl.sports.creedglobal.com.forwardpass;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.Profile;

public class Splash extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Log.i("my_info : ", "onCreate Splash");
        Log.i("my_info : ", "Access Token is :" + AccessToken.getCurrentAccessToken());
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

//              Showing splash screen with a timer. This will be useful when you
//              want to show case your app logo / company

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (AccessToken.getCurrentAccessToken()!=null){
                    Log.i("my_info : ", "if condition of run Splash");
                    startActivity(new Intent(Splash.this, Scoreboard.class));
                    Profile profile = Profile.getCurrentProfile();
                    Toast.makeText(getApplicationContext(),"Login with "+profile.getName(),Toast.LENGTH_LONG).show();
                    Log.i("my_info ", "profile details: " + profile.getName() + profile.getId());
                    // close this activity
                    finish();
                }
                else{
                    Log.i("my_info : ","else condition of Splash");
                    Intent i = new Intent(Splash.this, MainActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }


                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

