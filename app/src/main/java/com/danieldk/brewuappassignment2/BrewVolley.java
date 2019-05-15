package com.danieldk.brewuappassignment2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.danieldk.brewuappassignment2.Fragments.AllBrews;
import com.danieldk.brewuappassignment2.Models.Brew;
import com.danieldk.brewuappassignment2.Models.BrewTypeAPI;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class BrewVolley {

    public static final String BROADCAST_BREWTYPES_RESULT = "BROADCAST_BREWTYPES_RESULT";
    public static final String BREWTYPES_BUNDLE_RESULT = "brew_bundle_result";

    RequestQueue queue;
    private Context context;
    private List<BrewTypeAPI> BrewTypes;

    public BrewVolley(Context context) {
        this.context = context;
    }


    public void sendRequest(){
        //send request using VolleyClass
        if(queue==null){
            queue = Volley.newRequestQueue(context);
        }
        String url = "http://api.brewerydb.com/v2/styles/?key=403ed18e8c5995605375fc25772082f4";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        BrewTypes = interpretBeerJSON(response);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable(BREWTYPES_BUNDLE_RESULT, (Serializable) BrewTypes);

                        Intent intent = new Intent();
                        intent.setAction(BROADCAST_BREWTYPES_RESULT);
                        intent.putExtras(bundle);

                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //txtResponse.setText("That didn't work! ");
            }
        });

        queue.add(stringRequest);
    }

    public List<BrewTypeAPI> interpretBeerJSON(String jsonResponse){

        return BrewJsonParser.parseBrewJsonWithGson(jsonResponse);
    }
}
