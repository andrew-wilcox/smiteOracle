package com.hirez.smiteoracle;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Andrew on 1/30/14.
 */
public class SmiteAPIHandler extends IntentService {

    private static RequestQueue queue;
    private static SmiteAPIHandler instance;

    private String sessionKey;

    private final IBinder mBinder = new LocalBinder();

    public SmiteAPIHandler()
    {
        super("SmiteAPIHandler");
        instance = this;
    }

    public static Context getContext(){
        return instance;
    }

    public void addToQueue(JsonObjectRequest request)
    {
        queue.add(request);
    }

    public void addToQueue(JsonArrayRequest request)
    {
        queue.add(request);
    }

    public void addToQueue(StringRequest request)
    {
        queue.add(request);
    }

    //*************
    //API FUNCTIONS
    //*************

    //This collection of functions are referenced by the reflection API calls upon
    //intents sent by the main activity.

    //Reference class 'APIHelper' for functionality detailed here

    public void createsession(String[] data){ addToQueue(APIHelper.createsession(data)); }

    /*public void ping(String[] data){ addToQueue(APIHelper.ping(data)); }

    public void testsession(String[] data){ addToQueue(APIHelper.testsession(data)); }

    public void getdataused(String[] data){ addToQueue(APIHelper.getdataused(data)); }

    public void getdemodetails(String[] data){ addToQueue(APIHelper.getdemodetails(data)); }

    public void getfriends(String[] data){ addToQueue(APIHelper.getfriends(data)); }

    public void getgodranks(String[] data){ addToQueue(APIHelper.getgodranks(data)); }*/

    public void getgods(String[] data){ addToQueue(APIHelper.getgods(data)); }

    /*public void getitems(String[] data){ addToQueue(APIHelper.getitems(data)); }

    public void getmatchdetails(String[] data){ addToQueue(APIHelper.getmatchdetails(data)); }

    public void getleagueleaderboard(String[] data){ addToQueue(APIHelper.getleagueleaderboard(data)); }

    public void getleagueseasons(String[] data){ addToQueue(APIHelper.getleagueseasons(data)); }

    public void getmatchhistory(String[] data){ addToQueue(APIHelper.getmatchhistory(data)); }

    public void getplayer(String[] data){ addToQueue(APIHelper.getplayer(data)); }

    public void getqueuestats(String[] data){ addToQueue(APIHelper.getqueuestats(data)); }

    public void getteamdetails(String[] data){ addToQueue(APIHelper.getteamdetails(data)); }

    public void getteammatchhistory(String[] data){ addToQueue(APIHelper.getteammatchhistory(data)); }

    public void getteamplayers(String[] data){ addToQueue(APIHelper.getteamplayers(data)); }

    public void gettopmatches(String[] data) { addToQueue(APIHelper.gettopmatches(data)); }

    public void searchteams(String[] data) { addToQueue(APIHelper.searchteams(data)); }*/

    //*****************
    //SERVICE FUNCTIONS
    //*****************

    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);
        queue = Volley.newRequestQueue(this);
        return Service.START_NOT_STICKY;
    }

    public IBinder onBind(Intent intent){
        return mBinder;
    }

    public class LocalBinder extends Binder {
        SmiteAPIHandler getService() {
            return SmiteAPIHandler.this;
        }
    }

    protected void onHandleIntent(Intent i){
        String methodName = i.getStringExtra("methodName");
        String[] data = i.getStringArrayExtra("data");

        try{
            Log.v("HandlingIntent", "Begin");
            Method m = SmiteAPIHandler.class.getDeclaredMethod(methodName, String[].class);
            m.invoke(this, new Object[] { data });
            Log.v("HandlingIntent", "End");
        } catch(Exception e){
            Log.e("SmiteAPIHandler", "exception", e);
        }
    }

    public static void publishResults(String method){
        Log.v("Publishing Method", method);
        Intent i = new Intent("com.hirez.smiteoracle.ItemList");
        i.putExtra("methodName", method);
        getContext().sendBroadcast(i);
    }
}
