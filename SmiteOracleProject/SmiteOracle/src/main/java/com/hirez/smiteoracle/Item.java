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
    private String tier1SecondaryDescription;
    private String tier2SecondaryDescription;
    private String tier3SecondaryDescription;
    private Map<String, String> tier1stats = new HashMap<String, String>();
    private Map<String, String> tier2stats = new HashMap<String, String>();
    private Map<String, String> tier3stats = new HashMap<String, String>();

    public Item(){}

    public Item(JSONObject tier1, JSONObject tier2, JSONObject tier3)
    {
        try {
            itemName = tier1.getString("DeviceName");
            itemId = tier1.getInt("ItemId");
            iconId = tier1.getInt("IconId");
            startingItem = tier1.getBoolean("StartingItem");
            type = tier1.getString("Type");
            price = tier1.getInt("Price");
            rootItemId = tier1.getInt("RootItemId");
            shortDesc = tier1.getString("ShortDesc");
            childItemId = tier1.getInt("ChildItemId");
            description = tier1.getJSONObject("ItemDescription").getString("Description");

            //It was probably not necessary to do this for tier2, but since tier 1 and 3 already
            //had different descriptions, it seemed to make sense to just go ahead and future-
            //proof this for items with a different tier 2 passive
            tier1SecondaryDescription = tier1.getJSONObject("ItemDescription").getString("SecondaryDescription");
            tier2SecondaryDescription = tier2.getJSONObject("ItemDescription").getString("SecondaryDescription");
            tier3SecondaryDescription = tier3.getJSONObject("ItemDescription").getString("SecondaryDescription");

            //Get the stats of the item from tier1
            try {
                JSONArray statsArray = tier1.getJSONObject("ItemDescription").getJSONArray("Menuitems");
                JSONObject current;

                for(int i=0;i<statsArray.length();i++)
                {
                    current = statsArray.getJSONObject(i);
                    tier1stats.put(current.getString("Description"), current.getString("Value"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Get the stats of the item from tier2
            try {
                JSONArray statsArray = tier2.getJSONObject("ItemDescription").getJSONArray("Menuitems");
                JSONObject current;

                for(int i=0;i<statsArray.length();i++)
                {
                    current = statsArray.getJSONObject(i);
                    tier2stats.put(current.getString("Description"), current.getString("Value"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Get the stats of the item from tier3
            try {
                JSONArray statsArray = tier3.getJSONObject("ItemDescription").getJSONArray("Menuitems");
                JSONObject current;

                for(int i=0;i<statsArray.length();i++)
                {
                    current = statsArray.getJSONObject(i);
                    tier3stats.put(current.getString("Description"), current.getString("Value"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Item(JSONObject tier1)
    {
        try {
            itemName = tier1.getString("DeviceName");
            itemId = tier1.getInt("ItemId");
            iconId = tier1.getInt("IconId");
            startingItem = tier1.getBoolean("StartingItem");
            type = tier1.getString("Type");
            price = tier1.getInt("Price");
            rootItemId = tier1.getInt("RootItemId");
            shortDesc = tier1.getString("ShortDesc");
            childItemId = tier1.getInt("ChildItemId");
            description = tier1.getJSONObject("ItemDescription").getString("Description");

            tier1SecondaryDescription = tier1.getJSONObject("ItemDescription").getString("SecondaryDescription");

            try {
                JSONArray statsArray = tier1.getJSONObject("ItemDescription").getJSONArray("Menuitems");
                JSONObject current;

                for(int i=0;i<statsArray.length();i++)
                {
                    current = statsArray.getJSONObject(i);
                    tier1stats.put(current.getString("Description"), current.getString("Value"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTier1SecondaryDescription() {
        return tier1SecondaryDescription;
    }

    public void setTier1SecondaryDescription(String tier1SecondaryDescription) {
        this.tier1SecondaryDescription = tier1SecondaryDescription;
    }

    public String getTier2SecondaryDescription() {
        return tier2SecondaryDescription;
    }

    public void setTier2SecondaryDescription(String tier2SecondaryDescription) {
        this.tier2SecondaryDescription = tier2SecondaryDescription;
    }

    public String getTier3SecondaryDescription() {
        return tier3SecondaryDescription;
    }

    public void setTier3SecondaryDescription(String tier3SecondaryDescription) {
        this.tier3SecondaryDescription = tier3SecondaryDescription;
    }

    public Map<String, String> getTier1stats() {
        return tier1stats;
    }

    public void setTier1stats(Map<String, String> tier1stats) {
        this.tier1stats = tier1stats;
    }

    public Map<String, String> getTier2stats() {
        return tier2stats;
    }

    public void setTier2stats(Map<String, String> tier2stats) {
        this.tier2stats = tier2stats;
    }

    public Map<String, String> getTier3stats() {
        return tier3stats;
    }

    public void setTier3stats(Map<String, String> tier3stats) {
        this.tier3stats = tier3stats;
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
}
