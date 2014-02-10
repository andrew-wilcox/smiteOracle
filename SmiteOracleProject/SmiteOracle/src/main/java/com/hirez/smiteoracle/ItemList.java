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

    final int maxLogSize = 1000;

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
        //try{Log.v("handlecreatesession success", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        JSONObject jsonResponse = string2JSON(response);
        try{session_id = jsonResponse.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    //BROKEN
    /*public void handleping(String methodName)
    {
        String response = null;
        JSONObject agni = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        try{Log.v("handleping success", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{session_id = response.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }*/

    public void handletestsession(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{Log.v("handletestsession success", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetdataused(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{Log.v("handlegetdataused success", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetfriends(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{Log.v("handlegetfriends success", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetdemodetails(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{Log.v("handlegetdemodetails success", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetgodranks(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{Log.v("handlegetgodrankssuccess", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetgods(String methodName)
    {
        String response = null;
        JSONObject agni = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{logLongString("handlegetgods", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetitems(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{logLongString("handlegetitems", string2JSON(response).getJSONArray("response").getJSONObject(0).toString());}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetmatchdetails(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{logLongString(response, "handlegetmatchdetails");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetleagueleaderboard(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{logLongString(response, "handlegetleagueleaderboard");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetleagueseasons(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{Log.v("handlegetleagueseasons", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetmatchhistory(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{logLongString("handlegetmatchhistory", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetplayer(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{logLongString("handlegetplayer", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetqueuestats(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{logLongString("handlegetqueuestats", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegetteamdetails(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{logLongString("handlegetteamdetails", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    //BROKEN
    /*
    public void handlegetteammatchhistory(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{logLongString("handlegetteammatchhistory", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }
    */

    public void handlegetteamplayers(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{Log.v("handlegetteamplayers", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlegettopmatches(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{Log.v("handlegettopmatches", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
    }

    public void handlesearchteams(String methodName)
    {
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        //try{logLongString("handlesearchteams", response);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
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

    private void logLongString(String tag, String s)
    {
        for(int i = 0; i <= s.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > s.length() ? s.length() : end;
            Log.v(tag, s.substring(start, end));
        }
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
        i.putExtra("methodName", "searchteams");
        i.putExtra("data", l);
        this.startService(i);
    }
}
