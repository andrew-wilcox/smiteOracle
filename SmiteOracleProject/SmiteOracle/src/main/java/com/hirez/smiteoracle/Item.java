package com.hirez.smiteoracle;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrew on 2/10/14.
 */

//  {
//    "DeviceName":"Hide of Leviathan",
//    "ItemId":7375,
//    "IconId":1824,
//    "StartingItem":false,
//    "Type":"Item",
//    "Price":830,
//    "RootItemId":7375,
//    "ret_msg":null,
//    "ShortDesc":"Crowd Control Reduction",
//    "ItemTier":1,
//    "ChildItemId":0,
//    "ItemDescription":
//      {
//        "Description":"This item gives the owner reduced crowd control durations.",
//        "Menuitems":
//          [
//            {
//              "Description":"Physical Protection",
//              "Value":"+15"
//            },
//            {
//              "Description":"Magical Protection",
//              "Value":"+15"
//            },
//            {
//              "Description":"Health",
//              "Value":"+50"
//            },
//            {
//              "Description":"Mana",
//              "Value":"+50"
//            }
//          ],
//        "SecondaryDescription":""
//      }
//  }
public class Item {
    private String itemName;
    private int itemId;
    private int iconId;
    private Boolean startingItem;
    private String type;
    private int price;
    private int rootItemId;
    private String shortDesc;
    private int itemTier;
    private int childItemId;
    private String description;
    private String secondaryDescription;
    private Map<String, String> stats = new HashMap<String, String>();

    public Item(JSONObject object)
    {
        try {
            itemName = object.getString("DeviceName");
            itemId = object.getInt("ItemId");
            iconId = object.getInt("IconId");
            startingItem = object.getBoolean("StartingItem");
            type = object.getString("Type");
            price = object.getInt("Price");
            rootItemId = object.getInt("RootItemId");
            shortDesc = object.getString("ShortDesc");
            itemTier = object.getInt("ItemTier");
            childItemId = object.getInt("ChildItemId");
            description = object.getJSONObject("ItemDescription").getString("Description");
            secondaryDescription = object.getJSONObject("ItemDescription").getString("SecondaryDescription");

            JSONArray statsArray = object.getJSONArray("Menuitems");
            JSONObject current;

            for(int i=0;i<statsArray.length();i++)
            {
                current = statsArray.getJSONObject(i);
                stats.put(current.getString("Description"), current.getString("Value"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getItemName() {
        return itemName;
    }

    public String getImageName() {
        return getItemName().replace(" ", "_").toLowerCase().replace("'", "");
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public Boolean getStartingItem() {
        return startingItem;
    }

    public void setStartingItem(Boolean startingItem) {
        this.startingItem = startingItem;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRootItemId() {
        return rootItemId;
    }

    public void setRootItemId(int rootItemId) {
        this.rootItemId = rootItemId;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public int getItemTier() {
        return itemTier;
    }

    public void setItemTier(int itemTier) {
        this.itemTier = itemTier;
    }

    public int getChildItemId() {
        return childItemId;
    }

    public void setChildItemId(int childItemId) {
        this.childItemId = childItemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecondaryDescription() {
        return secondaryDescription;
    }

    public void setSecondaryDescription(String secondaryDescription) {
        this.secondaryDescription = secondaryDescription;
    }

    public Map<String, String> getStats() {
        return stats;
    }

    public void setStats(Map<String, String> stats) {
        this.stats = stats;
    }
}
