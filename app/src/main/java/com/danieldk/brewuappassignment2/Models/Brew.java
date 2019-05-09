package com.danieldk.brewuappassignment2.Models;

import java.util.Date;

public class Brew {

    public Brew() {};
    private String Id;
    private String UserId;
    private String BeerType;
    private float UserRating;
    private int NumberOfRatings;
    private float AvgRating;
    private String Link;
    private String Username;
    private Date CreationDate;

    public String getId() {

        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getBeerType() {
        return BeerType;
    }

    public void setBeerType(String beerType) {
        BeerType = beerType;
    }

    public float getUserRating() {
        return UserRating;
    }

    public void setUserRating(float userRating) {
        UserRating = userRating;
    }

    public int getNumberOfRatings() {
        return NumberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        NumberOfRatings = numberOfRatings;
    }

    public float getAvgRating() {
        return AvgRating;
    }

    public void setAvgRating(float avgRating) {
        AvgRating = avgRating;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        CreationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Brew{" +
                "Id='" + Id + '\'' +
                ", UserId='" + UserId + '\'' +
                ", BeerType='" + BeerType + '\'' +
                ", UserRating=" + UserRating +
                ", NumberOfRatings=" + NumberOfRatings +
                ", AvgRating=" + AvgRating +
                ", Link='" + Link + '\'' +
                ", Username='" + Username + '\'' +
                ", CreationDate=" + CreationDate +
                '}';
    }
}
