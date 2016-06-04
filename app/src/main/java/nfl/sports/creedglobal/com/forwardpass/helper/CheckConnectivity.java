package nfl.sports.creedglobal.com.forwardpass.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by SUJEET on 4/26/2016.
 */
public class CheckConnectivity
{
        private Context _context;

        public CheckConnectivity(Context context){
            this._context = context;
        }

        public  boolean isInternet(){
            ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null)
            {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        {
                            Log.i("infoo","available internet through"+info[i]);
                            return true;
                        }
            }
            return false;
        }
}