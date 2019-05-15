package com.danieldk.brewuappassignment2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class BrewService extends Service {
    public BrewService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    RequestQueue queue;


    public void sendRequest(){
        //send request using VolleyClass
        if(queue==null){
            queue = Volley.newRequestQueue(this);
        }
        String url = "http://api.brewerydb.com/v2/styles/?key=403ed18e8c5995605375fc25772082f4";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //txtResponse.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //txtResponse.setText("That didn't work! ");
            }
        });

        queue.add(stringRequest);
    }

    //attempt to decode the json response from weather server
    public void interpretBeerJSON(String jsonResonse){

        BrewJsonParser.parseBrewJsonWithGson(jsonResonse);
    }
}
