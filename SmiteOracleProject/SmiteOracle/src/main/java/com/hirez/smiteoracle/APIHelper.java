package com.hirez.smiteoracle;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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

    public SmiteAPIHandler s = new SmiteAPIHandler();


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
    }

    //*******
    //getgods
    //*******

    //Example Response:
    //{
    //    TODO: Actually get this response...
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
                Log.v("long string", Integer.toString(jsonArray.toString().length()));
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
    }

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
}
