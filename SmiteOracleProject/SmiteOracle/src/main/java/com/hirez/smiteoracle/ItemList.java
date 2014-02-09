package com.hirez.smiteoracle;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;


public class ItemList extends Activity {

    static TextView responseView;
    static String session_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);
        responseView = (TextView) findViewById(R.id.responseView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter("com.hirez.smiteoracle.ItemList"));
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String jsonString = bundle.getString("response");
                String methodName = bundle.getString("methodName");
                if(!jsonString.contains("[") && !jsonString.contains("{")){
                    try{
                        Method m = ItemList.class.getMethod("handle" + methodName, new Class[] { String.class });
                        m.invoke(new ItemList(), jsonString);
                    } catch(Exception e){
                        Log.e("SmiteAPIHandler", "exception", e);
                    }
                }
                else{
                    JSONObject response = string2JSON(jsonString);
                    try{
                        Method m = ItemList.class.getMethod("handle" + methodName, new Class[] { JSONObject.class });
                        m.invoke(new ItemList(), response);
                    } catch(Exception e){
                        Log.e("SmiteAPIHandler", "exception", e);
                    }
                }
            };
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    //*************
    //API FUNCTIONS
    //*************
    public void handlecreatesession(JSONObject response)
    {
        try{Log.v("handlecreatesession success", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handleping(JSONObject response)
    {
        try{Log.v("handleping success", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handletestsession(JSONObject response)
    {
        try{Log.v("handletestsession success", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetdataused(JSONObject response)
    {
        try{Log.v("handlegetdataused success", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetfriends(JSONObject response)
    {
        try{Log.v("handlegetfriends success", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetdemodetails(JSONObject response)
    {
        try{Log.v("handlegetdemodetails success", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}s
    }

    public void handlegetgodranks(JSONObject response)
    {
        try{Log.v("handlegetgodrankssuccess", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}s
    }

    public void handlegetgods(JSONObject response)
    {
        try{Log.v("handlegetgodssuccess", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}s
    }

    public void handlegetitems(JSONObject response)
    {
        try{Log.v("handlegetitemssuccess", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}s
    }

    public void handlegetmatchdetails(JSONObject response)
    {
        try{Log.v("handlegetmatchdetailssuccess", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}s
    }

    public void handlegetleagueleaderboard(JSONObject response)
    {
        try{Log.v("handlegetleagueleaderboardsuccess", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}s
    }

    public void handlegetleagueseasons(JSONObject response)
    {
        try{Log.v("handlegetleagueseasonssuccess", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}s
    }

    public void handlegetmatchhistory(JSONObject response)
    {
        try{Log.v("handlegetmatchhistorysuccess", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}s
    }

    public void handlegetplayer(JSONObject response)
    {
        try{Log.v("handlegetplayersuccess", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}s
    }

    public void handlegetteamdetails(JSONObject response)
    {
        try{Log.v("handlegetteamdetailssuccess", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}s
    }

    public void handlegetteammatchhistory(JSONObject response)
    {
        try{Log.v("handlegetteammatchhistorysuccess", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}s
    }

    public void handlegetteamplayers(JSONObject response)
    {
        try{Log.v("handlegetteamplayerssuccess", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}s
    }

    public void handlegettopmatches(JSONObject response)
    {
        try{Log.v("handlegettopmatchessuccess", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}s
    }

    public void handlesearchteams(JSONObject response)
    {
        try{Log.v("handlesearchteamssuccess", response.toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}s
    }

    //****************
    //HELPER FUNCTIONS
    //****************

    private JSONObject string2JSON(String s)
    {
        JSONObject jObj = null;
        if(s.charAt(0) == '['){
            jObj = new JSONObject();
            try{jObj.put("response", s);}catch (Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        }
        else if(s.charAt(0) == '{'){
            try{jObj = new JSONObject(s);}catch (Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        }
        return jObj;
    }

    //*******
    //BUTTONS
    //*******

    public void CreateSession(View v)
    {
        String[] l = {};
        Intent i = new Intent(this, SmiteAPIHandler.class);
        i.putExtra("methodName", "createsession");
        i.putExtra("data", l);
        this.startService(i);
    }

    public void GetDataUsed(View v)
    {
        String[] l = new String[1];
        Intent i = new Intent(this, SmiteAPIHandler.class);
        i.putExtra("methodName", "getgods");
        i.putExtra("data", l);
        this.startService(i);
    }
}
