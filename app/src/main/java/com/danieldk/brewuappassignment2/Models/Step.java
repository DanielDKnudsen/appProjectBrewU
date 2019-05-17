package com.danieldk.brewuappassignment2.Models;

import java.io.Serializable;

public class Step implements Serializable {
    private String brewId;
    private String description;
    private int stepOrder;
    private int temperature;
    private int time;

    public String getBrewId() {
        return brewId;
    }

    public void setBrewId(String brewId) {
        this.brewId = brewId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(int stepOrder) {
        this.stepOrder = stepOrder;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Step{" +
                "brewId='" + brewId + '\'' +
                ", description='" + description + '\'' +
                ", stepOrder=" + stepOrder +
                ", temperature=" + temperature +
                ", time=" + time +
                '}';
    }
}
