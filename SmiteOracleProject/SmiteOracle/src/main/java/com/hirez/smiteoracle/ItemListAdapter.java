package com.hirez.smiteoracle;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Andrew on 2/10/14.
 */
public class ItemListAdapter extends ArrayAdapter<Item> {
    Context context;
    int layoutResourceId;
    Item items[] = null;

    public ItemListAdapter(Context context, int layoutResourceId, Item[] items) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        ItemHolder holder =null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ItemHolder();
            holder.textTitle = (TextView)row.findViewById(R.id.itemName);
            holder.imgIcon = (ImageView)row.findViewById(R.id.icon);

            row.setTag(holder);
        }
        else
        {
            holder = (ItemHolder)row.getTag();
        }

        Item item = items[position];
        holder.textTitle.setText(item.getItemName());
        holder.imgIcon.setImageResource(context.getResources().getIdentifier(item.getItemName(), "drawable", "com.hirez.smiteoracle"));

        return row;
    }

    static class ItemHolder
    {
        ImageView imgIcon;
        TextView textTitle;
    }
}
