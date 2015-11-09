package com.osu.cse.projectblocks.data;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by niesmo on 11/8/2015.
 */
public class OrchestrateJsonObjectRequest extends JsonObjectRequest {
    private static final String baseUrl = "https://api.orchestrate.io/v0/";
    public OrchestrateJsonObjectRequest(String resource, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(baseUrl + resource, listener, errorListener);
    }

    @Override
    public Map getHeaders() throws AuthFailureError{
        Map headers = new HashMap();
        headers.put("Authorization", "Basic YTc0OWIzYmMtMTQ0YS00NzE4LWI2ODktZjE2Y2FhNTc5YjM2Og==");
        return headers;
    }
}
