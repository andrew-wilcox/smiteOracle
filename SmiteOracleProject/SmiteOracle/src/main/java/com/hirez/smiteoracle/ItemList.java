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
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ItemList extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);
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
                JSONObject response = string2JSON(jsonString);

                try{
                    Method m = ItemList.class.getDeclaredMethod("handle" + methodName, JSONObject.class);
                    m.invoke(this, new Object[] { response });
                } catch(Exception e){
                    Log.d("Tag", e.getCause().toString());
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
        Log.v("Handling", "Create Session");
    }

    //****************
    //HELPER FUNCTIONS
    //****************

    private JSONObject string2JSON(String s)
    {
        JSONObject jObj = null;
        try{jObj = new JSONObject(s);}catch (Exception JSONException){}
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
        startService(i);
    }
}
