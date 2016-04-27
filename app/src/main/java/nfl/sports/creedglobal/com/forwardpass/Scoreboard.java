package nfl.sports.creedglobal.com.forwardpass;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import nfl.sports.creedglobal.com.forwardpass.helper.CheckConnectivity;

public class Scoreboard extends ListActivity {
    public ProgressDialog pDialog;
    String kpos,kname,kfantasypoint="";
    String defpos,defname,deffantasypoint="";

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
            R.drawable.team1,
            R.drawable.team2,
            R.drawable.team3,
            R.drawable.team4,
            R.drawable.team5,
            R.drawable.team5,
            R.drawable.team6,
            R.drawable.team7,
            R.drawable.team8,
            R.drawable.team9,
            R.drawable.team10,
            R.drawable.team11,
            R.drawable.team12,
            R.drawable.team13,
            R.drawable.team14,
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
        TextView scoreTab = (TextView)findViewById(R.id.scoretxt);
        scoreTab.setBackgroundResource(R.drawable.bot_border);
        scoreTab.setAlpha((float) 0.7);
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
                        if (pos.equalsIgnoreCase("K")||pos.equalsIgnoreCase("DEF")){
                            if (pos.equalsIgnoreCase("K")){
                                kpos=pos;
                                kname=name;
                                kfantasypoint=fantasy;
                            }
                            else {
                                defpos=pos;
                                defname=name;
                                deffantasypoint=fantasy;
                            }
                        }
                        else {
                            //                        String icons=Integer.toString(icon[i]);

                            // Phone node is JSON Object
//                            JSONObject phone = c.getJSONObject(TAG_PHONE);
//                            String mobile = phone.getString(TAG_PHONE_MOBILE);
//                            String home = phone.getString(TAG_PHONE_HOME);
//                            String office = phone.getString(TAG_PHONE_OFFICE);

                            // tmp hashmap for single contact
                            HashMap<String, String> contact = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            contact.put(TAG_ID, id);
                            contact.put(TAG_POS, pos);
                            contact.put(TAG_NAME, name);
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
                            TAG_FANTASY_POINT},
                    new int[]{
                            R.id.postxt,
                            R.id.teamicon,
                            R.id.offensetxt,
                            R.id.fantasytxt});

            setListAdapter(adapter);



        }
    }

    public void gotoScoreboard(View view) {
//        startActivity(new Intent(getApplicationContext(),Scoreboard.class));
//        finish();
    }
    public void gotoTeam(View view) {
            startActivity(new Intent(getApplicationContext(), Team.class));
            finish();
    }
}
