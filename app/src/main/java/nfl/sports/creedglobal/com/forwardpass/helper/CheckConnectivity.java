package nfl.sports.creedglobal.com.forwardpass.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.Toast;

import nfl.sports.creedglobal.com.forwardpass.MainActivity;

/**
 * Created by SUJEET on 4/26/2016.
 */
public class CheckConnectivity extends Activity{

    //    This method will check the internet connectivity from any resource in the device,  return type is true/false.
    public boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection

        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
//            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            Toast.makeText(this, " Internet not available ! ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
//    public void refresh(View view){
//        if(checkInternetConenction()){
//            startActivity(new Intent(this, MainActivity.class));
//        }
//        else {
//            Toast.makeText(this,"try again...",Toast.LENGTH_SHORT).show();
//        }
//    }

}

