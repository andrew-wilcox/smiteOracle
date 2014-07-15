package com.hirez.smiteoracle;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class ItemList extends Activity {

    static String session_id;

    ArrayList<Item> allItems;

    final int maxLogSize = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);
    }

    @Override
    protected void onStop(){
        super.onStop();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(receiver, new IntentFilter("com.hirez.smiteoracle.ItemList"));
        SystemClock.sleep(100);
        createSession();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("onReceive", "received");
            SystemClock.sleep(200);
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String methodName = bundle.getString("methodName");
                try{
                    Log.v("methodName", methodName);
                    if(methodName.equals("createsession"))
                    {
                        Log.v("handling", methodName);
                        handlecreatesession(methodName);
                    }
                    else if(methodName.equals("getitems"))
                    {
                        Log.v("handling", methodName);
                        handlegetitems(methodName);
                    }
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

    //*************
    //API FUNCTIONS
    //*************
    public void handlecreatesession(String methodName)
    {
        Log.e("handlecreatesession", "starting");
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        JSONObject jsonResponse = string2JSON(response);
        try{session_id = jsonResponse.getString("session_id");}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}

        getItems();
    }

    public void handlegetitems(String methodName)
    {
        ListView itemListView;
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        try {
            getItemList(string2JSON(response));
        } catch (Exception e) {
            e.printStackTrace();
        }

        itemListView = (ListView)findViewById(R.id.itemList);

        //This is huge: stops making it request every time it comes into focus
        //AND preserves scroll location if you open a view and then come back here
        if(itemListView.getAdapter() == null)
        {
            ItemListAdapter adapter = new ItemListAdapter(this, R.layout.item_row, allItems);

            itemListView.setAdapter(adapter);
            itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    if(allItems.get(position).getStartingItem())
                    {
                        Intent i = getStarterItemIntent(allItems.get(position));
                        startActivity(i);
                    }
                }
            });

            adapter.notifyDataSetChanged();
        }
    }

    public void getItemList(JSONObject object)
    {
        try {
            JSONArray arr = object.getJSONArray("response");

            allItems = new ArrayList<Item>();
            JSONObject tier2 = null;
            JSONObject tier3 = null;

            for(int i=0;i<arr.length();i++)
            {
                tier2 = null;
                tier3 = null;
                JSONObject currentItem = arr.getJSONObject(i);
                if(currentItem.getInt("ItemTier") == 1)
                {
                    for(int j=0;j<arr.length();j++)
                    {
                        JSONObject compareItem = arr.getJSONObject(j);
                        if(!compareItem.getString("Type").equals("Item"))
                        {
                            continue;
                        }
                        if(compareItem.getString("DeviceName").equals(currentItem.getString("DeviceName")) &&
                           compareItem.getInt("ItemTier") == 2)
                        {
                            tier2 = compareItem;
                        }
                        if(compareItem.getString("DeviceName").equals(currentItem.getString("DeviceName")) &&
                                compareItem.getInt("ItemTier") == 3)
                        {
                            tier3 = compareItem;
                        }
                    }
                    allItems.add(new Item(currentItem, tier2, tier3));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public void createSession()
    {
        String[] l = {};
        Intent i = new Intent(this, SmiteAPIHandler.class);
        i.putExtra("methodName", "createsession");
        i.putExtra("data", l);
        this.startService(i);
    }

    public void getItems()
    {
        String[] l = {"1"};
        Intent i = new Intent(this, SmiteAPIHandler.class);
        i.putExtra("methodName", "getitems");
        i.putExtra("data", l);
        this.startService(i);
    }

    public Intent getStarterItemIntent(Item i)
    {
        Intent intent = new Intent(this, StarterItemDisplay.class);

        intent.putExtra("itemName", i.getItemName());
        intent.putExtra("itemDescription", i.getDescription());
        intent.putExtra("imageName",i.getImageName());
        intent.putExtra("secondaryDescription", i.getTier1SecondaryDescription());

        Set keys = i.getTier1stats().keySet();
        int count = 1;
        for(Iterator iter = keys.iterator(); iter.hasNext();)
        {
            String key = (String) iter.next();
            String value = i.getTier1stats().get(key);
            intent.putExtra("tier1stat" + count + "Name", key);
            intent.putExtra("tier1stat" + count + "Desc", value);
            count++;
        }

        intent.putExtra("numStats", keys.size());

        return intent;
    }

    /*public Intent getItemIntent(Item i)
    {
        Intent intent = new Intent(this, ItemDisplay.class);

        intent.putExtra("imageName", i.getImageName());
        intent.putExtra("tier1SecondaryDescription", i.getTier1SecondaryDescription());
        intent.putExtra("tier3SecondaryDescription", i.getTier3SecondaryDescription());

        Set keys = i.getStats().keySet();
        int count = 1;
        for(Iterator iter = keys.iterator(); iter.hasNext();)
        {
            String key = (String) iter.next();
            String value = i.getStats().get(key);
            intent.putExtra("stat" + count + "Name", key);
            intent.putExtra("stat" + count + "Desc", value);
            count++;
        }

        return intent;
    }*/
}
