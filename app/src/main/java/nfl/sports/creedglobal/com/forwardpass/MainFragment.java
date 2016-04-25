package nfl.sports.creedglobal.com.forwardpass;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {
    private CallbackManager mcallbackManager;
    Profile profile = null;
    String username = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("my_info : ", "Access Token is :" + AccessToken.getCurrentAccessToken());
//        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mcallbackManager = CallbackManager.Factory.create();
        Log.i("my_info : ", "onCreate Method is called MainFragment");

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };
        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
    }

    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            //responsibe for fragment change , method defined in MainActivity.java
//                new MainActivity().showWait();
            Waiting waiting = new Waiting();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.add(R.id.mainactivity,waiting,"wait");
            transaction.commit();

            AccessToken accessToken = loginResult.getAccessToken();
            profile = Profile.getCurrentProfile();
            displayMsg(profile);
            if (profile != null) {
                Log.i("my_info ","userid using loginResult "+loginResult.getAccessToken().getUserId());
                username = profile.getFirstName()+" accessToken.getUserId()===>"+accessToken.getUserId();
            }
//            Toast.makeText(getContext(), "welcome " + username, Toast.LENGTH_LONG).show();
            Log.i("my_info :","profile.getName() ===>"+username);
            Log.i("my_info : ", "onSuccess Method is called");
            Log.i("my_info", "" + accessToken.getUserId());


//            username = profile.getId();
            // After Successful login to Facebook User will goto ScoreBoard
            gotoScoreBoard();
            Log.i("my_info", "onSucess finish() is called on" + getActivity());
            getActivity().finish();



        }

        @Override
        public void onCancel() {
            Log.i("my_info : ", "onCancel Method is called");
        }

        @Override
        public void onError(FacebookException error) {
            Log.i("my_info : ", "onError Method is called");
            Toast.makeText(getActivity(),"Check Connectivity...",Toast.LENGTH_LONG).show();
        }
    };

    public MainFragment() {
        Log.i("my_info :", "MainFragment Constructor method is called MainFragment");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("my_info :", "onCreateView method is called MainFragment");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("my_info :", "onViewCreated method is called MainFragment");
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(mcallbackManager, mCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("my_info :", "onActivityResult method is called MainFragment");
        mcallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void gotoScoreBoard() {
        Log.i("my_info :", "gotoScoreBoard method is called MainFragment");
        Intent intent;
        intent = new Intent(getActivity(), Scoreboard.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("my_info :", "onResume method is called MainFragment");
    }

    public void displayMsg(Profile profile) {
        if (profile != null) {
            String username = profile.getFirstName();
//            msgtxt.setText("Welcome" + username);
            Log.i("my_info :", "displayMsg method is called " + username);
            gotoScoreBoard();
        }
    }

}