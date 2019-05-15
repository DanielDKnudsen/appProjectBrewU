package com.danieldk.brewuappassignment2.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrewRoot {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<BrewTypeAPI> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BrewTypeAPI> getData() {
        return data;
    }

    public void setData(List<BrewTypeAPI> data) {
        this.data = data;
    }
}
