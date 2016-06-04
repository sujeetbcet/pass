package nfl.sports.creedglobal.com.forwardpass;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import nfl.sports.creedglobal.com.forwardpass.helper.CheckConnectivity;

public class Scoreboard extends ListActivity {
    public ProgressDialog pDialog;
    String kpos,kname,kteam,kfantasypoint="";
    String defpos,dteam,defname,deffantasypoint="";
    TextView kpost,koffenset,kfantasyt,kopos,koteam,dpost,doffenset,dfantasyt,dopos,doteam, totalpointtxt;
    ImageView klogo,dlogo;
    int totalpoint=0;

    // URL to get contacts JSON
    private static String url = "http://zinee.in/demo/infox/players.php";
    // JSON Node names
    private static final String TAG_ID = "player_id";
    private static final String TAG_NAME = "player_name";
    private static final String TAG_POS = "position";
    private static final String TAG_ACTUAL_TEAM = "actual_team";
    private static final String TAG_FANTASY_POINT = "fantasy_point";
    private static final String TAG_RANK = "rank";
    private static final String TAG_COMP = "comp";
    private static final String TAG_ATT = "att";
    private static final String TAG_YDS = "yds";
    private static final String TAG_INT = "int";
    private static final String TAG_TD = "td";
    int[] icon = new int[]{
            R.drawable.oak,
            R.drawable.buf,
            R.drawable.sf,
            R.drawable.hou,
            R.drawable.jac,
            R.drawable.ne,
            R.drawable.atl,
            R.drawable.dal,
            R.drawable.ari,
            R.drawable.min,
            R.drawable.ari,
            R.drawable.was,
            R.drawable.car,
            R.drawable.den,
            R.drawable.default_team_logo,
    };

    // contacts JSONArray
    JSONArray contacts = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;
    ArrayList<HashMap<String,String>> rowlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        Button scoreTab = (Button) findViewById(R.id.scoretxt);
        scoreTab.setBackgroundResource(R.drawable.bot_border);
        kpost=(TextView)findViewById(R.id.kpostxt);
        klogo=(ImageView)findViewById(R.id.kteamicon);
        koffenset=(TextView)findViewById(R.id.koffensetxt);
        kfantasyt=(TextView)findViewById(R.id.kfantasytxt);
        kopos=(TextView)findViewById(R.id.kopostxt);
        koteam=(TextView)findViewById(R.id.koteamtxt);
        dopos=(TextView)findViewById(R.id.dopostxt);
        doteam=(TextView)findViewById(R.id.doteamtxt);

        dpost=(TextView)findViewById(R.id.dpostxt);
        dlogo=(ImageView)findViewById(R.id.dteamicon);
        doffenset=(TextView)findViewById(R.id.doffensetxt);
        dfantasyt=(TextView)findViewById(R.id.dfantasytxt);
        totalpointtxt =(TextView)findViewById(R.id.totalpoint);
//        scoreTab.setAlpha((float) 0.7);
        // Calling async task to get json
        new GetContacts().execute();
        contactList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();
        ListView row=getListView();

    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Scoreboard.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray("info");
                    Log.i("infoo","players count: "+contacts.length());
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString(TAG_ID);
                        String pos = c.getString(TAG_POS);
                        String name = c.getString(TAG_NAME);
                        String actual_team = c.getString(TAG_ACTUAL_TEAM);
                        String fantasy = c.getString(TAG_FANTASY_POINT);
                        String rank = c.getString(TAG_RANK);
                        String comp = c.getString(TAG_COMP);
                        String att = c.getString(TAG_ATT);
                        String yds = c.getString(TAG_YDS);
                        String intt = c.getString(TAG_INT);
                        String td = c.getString(TAG_TD);
                        totalpoint=totalpoint+Integer.parseInt(fantasy);
                        if (pos.equalsIgnoreCase("K")||pos.equalsIgnoreCase("DEF")){
                            if (pos.equalsIgnoreCase("K")){

                                kpos=pos;
                                kname=name;
                                kfantasypoint=fantasy;
                                kteam = actual_team;
                                String kdata[]={pos,name,fantasy,actual_team};

                            }
                            else {
                                defpos=pos;
                                defname=name;
                                deffantasypoint=fantasy;
                                dteam = actual_team;
                                String ddata[]={pos,name,fantasy,actual_team};
                            }
                        }
                        else {
                            //                        String icons=Integer.toString(icon[i]);
                            // tmp hashmap for single contact
                            HashMap<String, String> contact = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            contact.put(TAG_ID, id);
                            contact.put(TAG_POS, pos);
                            contact.put(TAG_NAME, name);
                            contact.put(TAG_POS,pos);
                            contact.put(TAG_ACTUAL_TEAM, actual_team);
                            contact.put(TAG_FANTASY_POINT, fantasy);
                            contact.put("icons", Integer.toString(icon[i]));

                            // adding contact to contact list
                            contactList.add(contact);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

            ListAdapter adapter = new SimpleAdapter(getApplicationContext(), contactList, R.layout.players,
                    new String[]{
                            TAG_POS,
                            "icons",
                            TAG_NAME,
                            TAG_POS,
                            TAG_ACTUAL_TEAM,
                            TAG_FANTASY_POINT},
                    new int[]{
                            R.id.postxt,
                            R.id.teamicon,
                            R.id.offensetxt,
                            R.id.playerpostxt,
                            R.id.playerteamtxt,
                            R.id.fantasytxt});
            setListAdapter(adapter);

            kpost.setText(kpos);
            klogo.setImageResource(R.drawable.car);
            koffenset.setText(kname);
            kfantasyt.setText(kfantasypoint);
            kopos.setText(kpos);
            koteam.setText(kteam);

            dpost.setText(defpos);
            dlogo.setImageResource(R.drawable.den);
            doffenset.setText(defname);
            dfantasyt.setText(deffantasypoint);
            dopos.setText(defpos);
            doteam.setText(dteam);
            totalpointtxt.setText(String.valueOf(totalpoint));
        }
    }

    public void gotoScoreboard(View view) {
//        startActivity(new Intent(getApplicationContext(),Scoreboard.class));
//        finish();
    }
    public void gotoTeam(View view) {
        if (new CheckConnectivity(this).isInternet()){
            startActivity(new Intent(getApplicationContext(), Team.class));
            finish();
        }
        else{
            Toast.makeText(this,"Internet not available !",Toast.LENGTH_SHORT).show();
        }

    }
//    it will logout the user from application & redirect to login screen
    public void logout(View view){
        Log.i("my_info : ", "logout>Payment");
        if (AccessToken.getCurrentAccessToken()!=null) {
            Log.i("my_info : ", "logout>Payment" + AccessToken.getCurrentAccessToken());
            LoginManager.getInstance().logOut();
            Log.i("my_info : ", String.valueOf("after  logout Access Token is :" + AccessToken.getCurrentAccessToken() == null));
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
        else {
            Log.i("my_info","user cannot be able to logout/ already logout");
            Toast.makeText(getApplicationContext(),"Already logged out",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
