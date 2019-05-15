package com.danieldk.brewuappassignment2;

import com.danieldk.brewuappassignment2.Models.Brew;
import com.danieldk.brewuappassignment2.Models.BrewRoot;
import com.danieldk.brewuappassignment2.Models.BrewTypeAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class BrewJsonParser {
    public static List<BrewTypeAPI> parseBrewJsonWithGson(String jsonString){

        Gson gson = new GsonBuilder().create();
        BrewRoot brewInfo =  gson.fromJson(jsonString, BrewRoot.class);

        if(brewInfo != null) {

            return brewInfo.getData();

        } else {
            return null;
        }
    }
}
