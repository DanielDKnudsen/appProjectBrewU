package com.danieldk.brewuappassignment2.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrewTypeAPI {
    @SerializedName("shortName")
    @Expose
    private String shortName;

    public String getShortName() {
        return shortName;
    }
}
