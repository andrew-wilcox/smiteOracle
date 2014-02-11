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
import java.util.Iterator;
import java.util.Set;


public class ItemList extends Activity {

    static String session_id;

    private ArrayList<Item> items;
    private ArrayList<TieredItem> tieredItems;
    private ListView itemListView;

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
        String response = null;
        try{response = readFromFile(methodName);}catch(Exception e){Log.e("SmiteAPIHandler", "exception", e);}
        try {
            getItemList(string2JSON(response));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ItemListAdapter adapter = new ItemListAdapter(this, R.layout.item_row, items);

        itemListView = (ListView)findViewById(R.id.itemList);
        itemListView.setAdapter(adapter);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                if(items.get(position).getStartingItem())
                {
                    Intent i = getStarterItemIntent(items.get(position));
                    startActivity(i);
                }
            }
        });

        adapter.notifyDataSetChanged();
    }

    public void getItemList(JSONObject object)
    {
        ArrayList<Item> allItems = null;
        try {
            JSONArray arr = object.getJSONArray("response");

            items = new ArrayList<Item>();

            for(int i=0;i<arr.length();i++)
            {
                JSONObject j = arr.getJSONObject(i);
                if(j.getInt("ItemTier") == 1 && !j.getString("Type").equals("Consumable") && !j.getString("Type").equals("Active"))
                {
                    Log.v("item", j.toString());
                    items.add(new Item(arr.getJSONObject(i)));
                    allItems.add(new Item(arr.getJSONObject(i)));
                }
                else{allItems.add(new Item(arr.getJSONObject(i)));}
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //tieredItems = setupItemTierList(allItems);
        Log.v("tiereditems", tieredItems.toString());
    }

    public ArrayList<TieredItem> setupItemTierList(ArrayList<Item> items)
    {
        ArrayList<TieredItem> tieredItems = null;
        Item tier2 = null, tier3 = null;

        for(int i=0;i<items.size();i++)
        {
            Item currentItem = items.get(i);
            if(currentItem.getItemTier() == 1 && !(currentItem.getType().equals("Starter") || currentItem.getType().equals("Consumable"))){
                for(int j=0;j<items.size();j++)
                {
                    Item subItem = items.get(j);
                    if(subItem.getItemName().equals(currentItem.getItemName()) && subItem.getItemTier() == 2){
                        Log.v("Item Name", currentItem.getItemName());
                        Log.v("Tier", "2");
                        tier2 = subItem;
                    }
                    else if(subItem.getItemName().equals(currentItem.getItemName()) && subItem.getItemTier() == 3){
                        Log.v("Item Name", currentItem.getItemName());
                        Log.v("Tier", "3");
                        tier3 = subItem;
                    }

                    tieredItems.add(new TieredItem(currentItem, tier2, tier3));
                }
            }
        }

        return tieredItems;
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
        intent.putExtra("secondaryDescription", i.getSecondaryDescription());

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

        intent.putExtra("numStats", keys.size());

        return intent;
    }

    /*public Intent getItemIntent(Item i)
    {
        Intent intent = new Intent(this, ItemDisplay.class);

        intent.putExtra("imageName",i.getImageName());
        intent.putExtra("secondaryDescription", i.getSecondaryDescription());

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

        intent.putExtra("numStats", keys.size());

        return intent;
    }*/
}
