package com.hirez.smiteoracle;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

/**
 * Created by Andrew on 2/8/14.
 * This class is mostly to keep the SmiteAPIHandler class smaller.
 * Each API Function is defined here, and has the jsonobject/arrayrequest
 * set up. SmiteAPIHandler can then simply reach out to this for a request
 * object and add it to the queue from there. The API, according to Hi-Rez
 * is "self-documenting" which is such utter bullshit that I've taken it
 * upon myself to document the responses here.
 */
public class APIHelper {
    private static String devID = "1158";
    private static String authKey = "F8004A8443AE4B47B5526E8DA7E93212";
    private static String apiUrl = "http://api.smitegame.com/smiteapi.svc/";
    private static String responseType = "json";

    //*************
    //createsession
    //*************

    //Example Response:
    //{
    //    "ret_msg":"Approved",
    //    "timestamp":"2\/9\/2014 8:11:42 AM",
    //    "session_id":"889B8BC3CEAD41999162CE86CAF1A27C"
    //}
    public static JsonObjectRequest createsession(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "createsession";

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + timestamp;
        Log.v("url", url);

        return new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                File file;
                file = new File(SmiteAPIHandler.getContext().getFilesDir(), method);
                writeToFile(jsonObject.toString(), method);
                Log.v("file size", "" + file.length());
                SmiteAPIHandler.publishResults(method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                File file;
                file = new File(SmiteAPIHandler.getContext().getCacheDir(), method);
                SmiteAPIHandler.publishResults(method);
                Log.v("error", volleyError.toString());
            }
        });
    }
/*
    //****
    //ping
    //****

    //Example Response:
    //{
    //    TODO: Actually get this response...
    //}
    public static JsonObjectRequest ping(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "ping";

        String url = apiUrl + method + responseType;
        Log.v("url", url);

        return new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                SmiteAPIHandler.publishResults(jsonObject.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    //***********
    //testsession
    //***********

    //Example Response:
    //"This was a successful test with the following parameters added: developer: 1158 time: 2\/9\/2014 8:47:52 AM signature: edf398f631b3355565cbd6c9924cbd21 session: 19550C551A7C473A899B1BC17DE21464"'

    //NOTE: Yes, this ACTUALLY returns a string
    public static StringRequest testsession(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "testsession";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp;
        Log.v("url", url);

        return new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                SmiteAPIHandler.publishResults(s, method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    //***********
    //getdataused
    //***********

    //Example Response:
    //[
    //    {
    //        "Active_Sessions":1,
    //        "Concurrent_Sessions":50,
    //        "ret_msg":null,
    //        "Request_Limit_Daily":7500,
    //        "Total_Sessions_Today":49,
    //        "Session_Time_Limit":15,
    //        "Session_Cap":500,
    //        "Total_Requests_Today":11
    //    }
    //]
    public static JsonArrayRequest getdataused(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getdataused";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id + '/' + timestamp;
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    //**************
    //getdemodetails
    //**************

    //Example Response:
    //[
    //    {
    //        "Recording_Started":"",
    //        "Match":45382616,
    //        "ret_msg":null,
    //        "Team1_Score":0,
    //        "Winning_Team":2,
    //        "Team2_AvgLevel":20,
    //        "Realtime_Spectators":0,
    //        "Team2_Kills":28,
    //        "Team1_Gold":74834,
    //        "Team2_Score":0,
    //        "Team2_Gold":75145,
    //        "Team1_Kills":25,
    //        "Offline_Spectators":0,
    //        "Match_Time":1216,
    //        "Recording_Ended":"",
    //        "Team1_AvgLevel":20,
    //        "Ban1":"",
    //        "Entry_Datetime":"2\/9\/2014 5:52:35 AM",
    //        "Ban2":""
    //     }
    //]
    public static JsonArrayRequest getdemodetails(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getdemodetails";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    //**********
    //getfriends
    //**********

    //Example Response:
    //[
    //    {"ret_msg":null,"name":"HiRezBart"},
    //    {"ret_msg":null,"name":"hellokellylink"}
    //]
    public static JsonArrayRequest getfriends(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getfriends";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    //***********
    //getgodranks
    //***********

    //Example Response:
    //[
    //    {"ret_msg":null,"Worshippers":899,"Rank":8,"god":"Arachne"},
    //    {"ret_msg":null,"Worshippers":355,"Rank":3,"god":"Anhur"},
    //    {"ret_msg":null,"Worshippers":292,"Rank":2,"god":"Vulcan"}
    //]
    public static JsonArrayRequest getgodranks(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getgodranks";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }*/

    //*******
    //getgods
    //*******

    //{
    //  [
          //<editor-fold desc="Agni Example">
    //    Example Response:
    //    {
    //       {
    //         "ManaPerFive": 4.7,
    //         "AttackSpeedPerLevel": 0.009,
    //         "Cons": "",
    //         "AbilityId2": 7811,
    //         "AbilityId1": 7812,
    //         "AbilityId4": 7824,
    //         "AbilityId3": 7818,
    //         "OnFreeRotation": "",
    //         "abilityDescription1": {
    //           "itemDescription": {
    //             "cooldown": "12s",
    //             "cost": "60\/70\/80\/90\/100",
    //             "description": "Agni summons a cloud of noxious fumes at his ground target location, doing damage every second. Firing any of Agni's abilities into the fumes detonates the gas, stunning all enemies in the radius.",
    //             "menuitems": [
    //               {
    //                 "value": "Ground Target",
    //                 "description": "Ability:"
    //               },
    //               {
    //                 "value": "Enemy",
    //                 "description": "Affects:"
    //               },
    //               {
    //                 "value": "Magical",
    //                 "description": "Damage:"
    //               },
    //               {
    //                 "value": "20",
    //                 "description": "Radius:"
    //               }
    //             ],
    //             "rankitems": [
    //               {
    //                 "value": "10\/20\/30\/40\/50 (+5% of your magical power)",
    //                 "description": "Damage per Tick:"
    //               },
    //               {
    //                 "value": "10s",
    //                 "description": "Fumes Duration:"
    //               },
    //               {
    //                 "value": "1s",
    //                 "description": "Stun Duration:"
    //               }
    //             ],
    //             "secondaryDescription": ""
    //           }
    //         },
    //         "Item1": "Healing Potion",
    //         "Pros": " High Area Damage",
    //         "Item2": "Meditation",
    //         "PhysicalProtectionPerLevel": 2.6,
    //         "Ability2": "Flame Wave",
    //         "AbilityId5": 7822,
    //         "Ability3": "Path of Flames",
    //         "Ability4": "Rain Fire",
    //         "Ability5": "Combustion",
    //         "MagicProtectionPerLevel": 0,
    //         "Ability1": "Noxious Fumes",
    //         "HP5PerLevel": 0.47,
    //         "PhysicalPowerPerLevel": 0,
    //         "HealthPerLevel": 71,
    //         "Item8": "Obsidian Shard",
    //         "Item7": "Void Stone",
    //         "HealthPerFive": 7,
    //         "Item9": "Rod of Tahuti",
    //         "Item4": "Vampiric Shroud",
    //         "Item3": "Sprint",
    //         "Item6": "Warlock's Sash",
    //         "Item5": "Shoes of the Magi",
    //         "Lore": "There are few elements as destructive or as purifying as fire. Agni, God of Fire, is the embodiment of both of these qualities, with a head for each.\\n\\nThough the source of his origin warrants debate - for there are many tales of his parentage ranging from two simple sticks rubbed together, to the cosmic energy that made all things at the beginning of time - Agni is a pivotal and important God with many duties to the Pantheon. He is the twin brother to Indra, God of the Heavens and Rains and chief among warriors. Conversely, Agni is chief among priests, acting as messenger between mortals and Gods. Every Hindu ritual and prayer is performed in front of a fire of some kind, so Agni carries the words and sacrifices, traveling between the Earth and the Heavens. He is welcome in every home and every hearth and much beloved by the Faithful.\\n\\nThrough his flames, Agni provides heat and light, but also cleanses impurities. Smoke from his pyres create the air and hold the Heavens aloft. The sun, a source of fire itself, brings life-giving energy to the world, and his lightning streaks the sky during storms.\\n\\nFor all his kindness and service, Agni has two faces. One is the face of kindness and purity, turned towards the people and Gods. His other face, grim and resolute, guides the God of Fire, to play his role in the cosmic cycle of creation and destruction, to burn and blacken all the atrocities of the world to ash.",
    //         "basicAttack": {
    //           "itemDescription": {
    //             "cooldown": "",
    //             "cost": "",
    //             "description": "",
    //             "menuitems": [
    //               {
    //                 "value": "34 + 1.5\/Lvl (+20% of Magical Power)",
    //                 "description": "Damage:"
    //               },
    //               {
    //                 "value": "None",
    //                 "description": "Progression:"
    //               }
    //             ],
    //             "rankitems": [
    //
    //             ],
    //             "secondaryDescription": ""
    //           }
    //         },
    //         "MagicProtection": 30,
    //         "PhysicalPower": 0,
    //         "Type": " Ranged, Magical",
    //         "abilityDescription4": {
    //           "itemDescription": {
    //             "cooldown": "Dependent on Halos",
    //             "cost": "0",
    //             "description": "Every 20 seconds, Agni gains a flaming halo that can be expended to summon a giant meteor at his ground target location. He can summon 1 every .8 seconds. Ignites Noxious Fumes.",
    //             "menuitems": [
    //               {
    //                 "value": "Ground Target",
    //                 "description": "Ability:"
    //               },
    //               {
    //                 "value": "Enemy",
    //                 "description": "Affects:"
    //               },
    //               {
    //                 "value": "Magical",
    //                 "description": "Damage:"
    //               },
    //               {
    //                 "value": "20",
    //                 "description": "Radius:"
    //               }
    //             ],
    //             "rankitems": [
    //               {
    //                 "value": "160\/195\/230\/265\/300 (+60% of your magical power)",
    //                 "description": "Damage:"
    //               },
    //               {
    //                 "value": "3",
    //                 "description": "Max Halos:"
    //               }
    //             ],
    //             "secondaryDescription": ""
    //           }
    //         },
    //         "ret_msg": null,
    //         "abilityDescription5": {
    //           "itemDescription": {
    //             "cooldown": "",
    //             "cost": "",
    //             "description": "After hitting with 4 basic attacks, Agni will gain a buff. On the next cast of Flame Wave or RainFire, all enemies hit by those abilities will be additionally set ablaze, taking damage every .5s for 3s.",
    //             "menuitems": [
    //               {
    //                 "value": "Enemy",
    //                 "description": "Affects:"
    //               },
    //               {
    //                 "value": "Magical",
    //                 "description": "Damage:"
    //               }
    //             ],
    //             "rankitems": [
    //               {
    //                 "value": "5 (+10% of your magical power)",
    //                 "description": "Damage per Tick:"
    //               }
    //             ],
    //             "secondaryDescription": ""
    //           }
    //         },
    //         "Health": 360,
    //         "abilityDescription2": {
    //           "itemDescription": {
    //             "cooldown": "15\/14\/13\/12\/11s",
    //             "cost": "60\/70\/80\/90\/100",
    //             "description": "Agni summons a wave of fire in front of him that scorches all enemies in its path.  Ignites Noxious Fumes.",
    //             "menuitems": [
    //               {
    //                 "value": "Line",
    //                 "description": "Ability:"
    //               },
    //               {
    //                 "value": "Enemy",
    //                 "description": "Affects:"
    //               },
    //               {
    //                 "value": "Magical",
    //                 "description": "Damage:"
    //               }
    //             ],
    //             "rankitems": [
    //               {
    //                 "value": "90\/140\/190\/240\/290 (+50% of your magical power)",
    //                 "description": "Damage:"
    //               }
    //             ],
    //             "secondaryDescription": ""
    //           }
    //         },
    //         "abilityDescription3": {
    //           "itemDescription": {
    //             "cooldown": "15s",
    //             "cost": "70\/75\/80\/85\/90",
    //             "description": "Agni blazes a path forward in a quick dash, leaving flamestrailing behind him. Any enemies passing through the flames catch fire and burn for damage every .5s for 2s. Ignites Noxious Fumes.",
    //               "menuitems": [
    //                 {
    //                   "value": "Line",
    //                   "description": "Ability:"
    //                 },
    //                 {
    //                   "value": "Enemy",
    //                   "description": "Affects:"
    //                 },
    //                 {
    //                   "value": "Magical",
    //                   "description": "Damage:"
    //                 }
    //               ],
    //               "rankitems": [
    //               {
    //                 "value": "20\/30\/40\/50\/60 (+15% of your magical power)",
    //                 "description": "Damage per Tick:"
    //               },
    //               {
    //                 "value": "3s",
    //                 "description": "Path Duration:"
    //               }
    //             ],
    //             "secondaryDescription": ""
    //           }
    //         },
    //         "ManaPerLevel": 45,
    //         "Mana": 255,
    //         "id": 1737,
    //         "Pantheon": "Hindu",
    //         "MP5PerLevel": 0.37,
    //         "Roles": " Mage",
    //         "AttackSpeed": 0.86,
    //         "PhysicalProtection": 11,
    //         "ItemId8": 7525,
    //         "ItemId9": 7600,
    //         "ItemId6": 7917,
    //         "ItemId7": 7791,
    //         "ItemId4": 8247,
    //         "ItemId5": 9633,
    //         "ItemId2": 8905,
    //         "ItemId3": 8881,
    //         "ItemId1": 7621,
    //         "Title": "God of Fire",
    //         "Name": "Agni",
    //         "Speed": 350
    //      }
    //    }
    //</editor-fold>
    //  ]
    //}

    public static JsonArrayRequest getgods(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getgods";
        final String session_id = ItemList.session_id;

        data[0] = "1";

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                File file;
                file = new File(SmiteAPIHandler.getContext().getFilesDir(), method);
                writeToFile(jsonArray.toString(), method);
                SmiteAPIHandler.publishResults(method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                File file;
                file = new File(SmiteAPIHandler.getContext().getCacheDir(), method);
                SmiteAPIHandler.publishResults(method);
                Log.v("error", volleyError.toString());
            }
        });
    }
/*
    //********
    //getitems
    //********

    //Example Response:
    //{
    //    TODO: Actually get this response...
    //}
    public static JsonArrayRequest getitems(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getitems";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    //***************
    //getmatchdetails
    //***************

    //Example Response:
    //{
    //    TODO: Actually get this response...
    //}
    public static JsonArrayRequest getmatchdetails(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getmatchdetails";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    //********************
    //getleagueleaderboard
    //********************

    //Example Response:
    //{
    //    TODO: Actually get this response...
    //}
    public static JsonArrayRequest getleagueleaderboard(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getleagueleaderboard";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0] + '/' + data[1] + '/' + data[2];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    //****************
    //getleagueseasons
    //****************

    //Example Response:
    //{
    //    TODO: Actually get this response...
    //}
    public static JsonArrayRequest getleagueseasons(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getleagueseasons";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    //***************
    //getmatchhistory
    //***************

    //Example Response:
    //{
    //    TODO: Actually get this response...
    //}
    public static JsonArrayRequest getmatchhistory(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getmatchhistory";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    //*********
    //getplayer
    //*********

    //Example Response:
    //{
    //    TODO: Actually get this response...
    //}
    public static JsonArrayRequest getplayer(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getplayer";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    public static JsonArrayRequest getqueuestats(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getqueuestats";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0] + '/' + data[1];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    //**************
    //getteamdetails
    //**************

    //Example Response:
    //{
    //    TODO: Actually get this response...
    //}
    public static JsonArrayRequest getteamdetails(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getteamdetails";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    //*******************
    //getteammatchhistory
    //*******************

    //Example Response:
    //{
    //    TODO: Actually get this response...
    //}
    public static JsonArrayRequest getteammatchhistory(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getteammatchhistory";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    //**************
    //getteamplayers
    //**************

    //Example Response:
    //{
    //    TODO: Actually get this response...
    //}
    public static JsonArrayRequest getteamplayers(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getteamplayers";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    //*************
    //gettopmatches
    //*************

    //Example Response:
    //{
    //    TODO: Actually get this response...
    //}
    public static JsonArrayRequest gettopmatches(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "getteamplayers";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }

    //***********
    //searchteams
    //***********

    //Example Response:
    //{
    //    TODO: Actually get this response...
    //}
    public static JsonArrayRequest searchteams(String[] data)
    {
        String timestamp = getTimestamp();
        final String method = "searchteams";
        final String session_id = ItemList.session_id;

        String url = apiUrl + method + responseType + '/' + devID + '/' + getSignature(method, timestamp) + '/' + session_id +  '/' + timestamp + '/' + data[0];
        Log.v("url", url);

        return new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                SmiteAPIHandler.publishResults(jsonArray.toString(), method);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmiteAPIHandler.publishResults(volleyError.toString(), method);
                Log.v("error", volleyError.toString());
            }
        });
    }*/

    public static String getTimestamp()
    {
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
        s.setTimeZone(TimeZone.getTimeZone("UTC"));
        return s.format(new Date());
    }

    public static String getSignature(String method, String timestamp) {
        return md5(devID + method + authKey + timestamp);
    }

    public static String md5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {}
        return null;
    }

    public static void writeToFile(final String fileContents, String fileName) {
        Context context = SmiteAPIHandler.getContext().getApplicationContext();
        try {
            Log.v("Contents", fileContents);
            FileWriter out = new FileWriter(new File(context.getFilesDir(), fileName));
            out.write(fileContents);
            out.close();
        } catch (IOException e) {
            Log.e("SmiteAPIHandler", "exception", e);
        }
    }

}
