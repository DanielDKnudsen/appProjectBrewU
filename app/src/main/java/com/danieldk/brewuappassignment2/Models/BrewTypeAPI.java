package com.danieldk.brewuappassignment2.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BrewTypeAPI implements Serializable {
    @SerializedName("shortName")
    @Expose
    private String shortName;

    public String getShortName() {
        return shortName;
    }
}
