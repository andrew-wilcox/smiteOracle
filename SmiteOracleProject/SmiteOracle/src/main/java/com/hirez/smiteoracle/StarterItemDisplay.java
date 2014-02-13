package com.hirez.smiteoracle;

import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class StarterItemDisplay extends ActionBarActivity {

    ImageView icon;
    TextView itemName, itemDescription, stat1Name, stat1Desc, stat2Name, stat2Desc, stat3Name, stat3Desc, secondaryDesc;
    static final String LOG_TAG = "intentLogging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starter_item);

        Intent i = this.getIntent();

        icon = (ImageView) findViewById(R.id.itemIcon);
        itemName = (TextView) findViewById(R.id.itemName);
        itemDescription = (TextView) findViewById(R.id.description);
        stat1Name = (TextView) findViewById(R.id.stat1Name);
        stat2Name = (TextView) findViewById(R.id.stat2Name);
        stat3Name = (TextView) findViewById(R.id.stat3Name);
        stat1Desc = (TextView) findViewById(R.id.stat1Desc);
        stat2Desc = (TextView) findViewById(R.id.stat2Desc);
        stat3Desc = (TextView) findViewById(R.id.stat3Desc);
        secondaryDesc = (TextView) findViewById(R.id.secondaryDescription);

        icon.setImageResource(this.getResources().getIdentifier(i.getStringExtra("imageName"), "drawable", "com.hirez.smiteoracle"));

        itemName.setText(i.getStringExtra("itemName"));
        itemDescription.setText(i.getStringExtra("itemDescription"));
        secondaryDesc.setText(i.getStringExtra("secondaryDescription"));

        ArrayList<String> stats = new ArrayList<String>();
        stats.add(i.getStringExtra("tier1stat1Name") + ' ' + i.getStringExtra("tier1stat1Desc"));
        if(i.getStringExtra("tier1stat2Name") != null)
        {
            stats.add(i.getStringExtra("tier1stat2Name") + ' ' + i.getStringExtra("tier1stat2Desc"));
        }
        if(i.getStringExtra("tier1stat3Name") != null)
        {
            stats.add(i.getStringExtra("tier1stat3Name") + ' ' + i.getStringExtra("tier1stat3Desc"));
        }

        for(int count=0;count<stats.size();count++)
        {
            View viewToLoad = LayoutInflater.from(
                    getApplicationContext()).inflate(
                    R.layout.stat, null);

            ((TextView) viewToLoad).setText(stats.get(count));
            ((LinearLayout) findViewById(R.id.statsList)).addView(viewToLoad);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.starter_item, menu);
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

    /*public static void dumpIntent(Intent i){

        Bundle bundle = i.getExtras();
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> it = keys.iterator();
            Log.e(LOG_TAG,"Dumping Intent start");
            while (it.hasNext()) {
                String key = it.next();
                Log.e(LOG_TAG,"[" + key + "=" + bundle.get(key)+"]");
            }
            Log.e(LOG_TAG,"Dumping Intent end");
        }
    }*/
}
