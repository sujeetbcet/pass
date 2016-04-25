package nfl.sports.creedglobal.com.forwardpass;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Log.i("my_info : ", "Access Token is :" + AccessToken.getCurrentAccessToken());
        if (AccessToken.getCurrentAccessToken()!=null){
            Log.i("my_info :", "Access token is :" + AccessToken.getCurrentAccessToken() + "MainActivity oncreate if condition");

            startActivity(new Intent(this,Scoreboard.class));
        }
        else {
            Log.i("my_info : ","onCreate>MainActivity>else condition");
            setContentView(R.layout.activity_main);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("my_info","onRestart method of MainActivity");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("my_info : ", "onBackPressed of MainActivity");
        if (AccessToken.getCurrentAccessToken()==null){


        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i("my_info : ","onPause>MainActivity");
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("my_info : ", "onResume>MainActivity");
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
}
