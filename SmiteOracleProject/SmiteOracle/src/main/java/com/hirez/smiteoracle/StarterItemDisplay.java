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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class StarterItemDisplay extends ActionBarActivity {

    ImageView icon;
    TextView itemName, itemDescription, secondaryDesc;
    static final String LOG_TAG = "intentLogging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starter_item);

        Intent i = this.getIntent();

        icon = (ImageView) findViewById(R.id.itemIcon);
        itemName = (TextView) findViewById(R.id.itemName);
        itemDescription = (TextView) findViewById(R.id.description);
        secondaryDesc = (TextView) findViewById(R.id.secondaryDescription);

        icon.setImageResource(this.getResources().getIdentifier(i.getStringExtra("imageName"), "drawable", "com.hirez.smiteoracle"));

        itemName.setText(i.getStringExtra("itemName"));
        itemDescription.setText(i.getStringExtra("itemDescription"));
        secondaryDesc.setText(i.getStringExtra("secondaryDescription"));

        ArrayList<String> stats = new ArrayList<String>();
        int statCount = 1;
        while(i.getStringExtra("tier1stat" + statCount + "Name") != null)
        {
            stats.add(i.getStringExtra("tier1stat" + statCount + "Name") + ' ' + i.getStringExtra("tier1stat" + statCount + "Desc"));
            statCount++;
        }

        RelativeLayout parent = (RelativeLayout) findViewById(R.id.parentStarter);
        LayoutInflater Li = LayoutInflater.from(getApplicationContext());

        for(int count=0;count<stats.size();count++)
        {
            LinearLayout ll = (LinearLayout) Li.inflate(R.layout.stat, null);
            TextView tv = (TextView) ll.findViewById(R.id.stat);
            tv.setText(stats.get(count));
            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            ll.setId(count+1);

            if (count == 0)
            {
                p.addRule(RelativeLayout.BELOW, R.id.secondaryDescription);
            }
            else
            {
                p.addRule(RelativeLayout.BELOW, getResources().getIdentifier(String.valueOf(count), "id", this.getPackageName()));
            }

            parent.addView(ll, p);
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
}
