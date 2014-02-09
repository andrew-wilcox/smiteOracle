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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.Iterator;


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
                String methodName = bundle.getString("methodName");
                try{
                    Method m = ItemList.class.getMethod("handle" + methodName, String.class);
                    m.invoke(new ItemList(), methodName);
                } catch(Exception e){ Log.e("SmiteAPIHandler", "exception", e); }
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
    public void handlecreatesession(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        try{Log.v("handlecreatesession success", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        JSONObject jsonResponse = string2JSON(response);
        try{session_id = jsonResponse.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
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

    public void handlegetgods(String methodName)
    {
        String response = null;
        JSONObject agni = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        try{agni = string2JSON(response).getJSONArray("response").getJSONObject(0);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        /*Iterator<String> iter = agni.keys();
        while(iter.hasNext())
        {
            String key = iter.next();
            try{Log.v(key, agni.get(key).toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        }*/
        int maxLogSize = 1000;
        for(int i = 0; i <= agni.toString().length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > agni.toString().length() ? agni.toString().length() : end;
            Log.v("agni", agni.toString().substring(start, end));
        }
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
        JSONArray jArr = null;
        if(s.charAt(0) == '['){
            jObj = new JSONObject();
            try{jArr = new JSONArray(s);}catch (Exception e){Log.e("SmiteAPIHandler", "exception", e);}
            try{jObj.put("response", jArr);}catch (Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        }
        else if(s.charAt(0) == '{'){
            try{jObj = new JSONObject(s);}catch (Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        }
        return jObj;
    }

    private String readFromFile(String path) {

        String ret = "";

        try {
            InputStream inputStream = SmiteAPIHandler.getContext().openFileInput(path);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();

                //Automatically delete the file post-read
                //This shouldn't be a problem in this app, unless I forget this does this
                SmiteAPIHandler.getContext().deleteFile(path);
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
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
