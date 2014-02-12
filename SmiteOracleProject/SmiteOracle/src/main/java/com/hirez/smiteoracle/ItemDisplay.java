package com.hirez.smiteoracle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemDisplay extends ActionBarActivity {

    ImageView icon;
    TextView itemName, itemDescription, stat1Name, stat1Desc, stat2Name, stat2Desc, stat3Name, stat3Desc, secondaryDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tiered_item);

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

        stat1Name.setText(i.getStringExtra("stat1Name"));
        stat2Name.setText(i.getStringExtra("stat2Name"));
        stat3Name.setText(i.getStringExtra("stat3Name"));

        stat1Desc.setText(i.getStringExtra("stat1Desc"));
        stat2Desc.setText(i.getStringExtra("stat2Desc"));
        stat3Desc.setText(i.getStringExtra("stat3Desc"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tiered_item_display, menu);
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
