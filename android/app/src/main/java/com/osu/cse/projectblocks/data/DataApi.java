package com.osu.cse.projectblocks.data;

import android.content.Context;
import android.provider.ContactsContract;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.URL;

/**
 * Created by niesmo on 11/8/2015.
 */


public class DataApi {
    private URL url;
    private RequestQueue queue;
    private static DataApi instance = null;
    protected DataApi() {
        // Exists only to defeat instantiation.
    }
    public static DataApi getInstance() {
        if(instance == null) {
            synchronized(DataApi.class) {
                if(instance == null) {
                    instance = new DataApi();
                }
            }
        }
        return instance;
    }

    public static void getCafeterias(Context c, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
        String resource ="cafeterias?limit=100";

        // create the request
        OrchestrateJsonObjectRequest jor = new OrchestrateJsonObjectRequest(resource, listener, errorListener);

        // Access the RequestQueue through your singleton class.
        RequestQueue queue = Volley.newRequestQueue(c);
        queue.add(jor);
    }
}