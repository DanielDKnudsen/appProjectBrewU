package com.danieldk.brewu;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.danieldk.brewu.Models.Brew;
import com.danieldk.brewu.ViewModels.BrewViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class MyBrews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_brews);

        BrewViewModel model = ViewModelProviders.of(this).get(BrewViewModel.class);
        model.getBrews();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Brew brew = new Brew();
        brew.setAvgRating(3);
        brew.setBeerType("IPA");
        brew.setCreationDate(new Date());
        brew.setId("1");
        brew.setLink("hej med dig");
        brew.setNumberOfRatings(30);
        brew.setUserRating(5);
        brew.setUsername("Daniel");
        brew.setUserId("2");
        //brew.setUsername(user.getDisplayName());
        //brew.setUserId(user.getUid());

        model.createBrew(brew);
    }
}
