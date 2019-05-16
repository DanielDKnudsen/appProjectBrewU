package com.danieldk.brewuappassignment2.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.danieldk.brewuappassignment2.Adaptor.BrewAdaptor;
import com.danieldk.brewuappassignment2.Models.Brew;
import com.danieldk.brewuappassignment2.R;
import com.danieldk.brewuappassignment2.ViewModels.BrewViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class DetailedBrew extends Fragment {

    private TextView txtBrewNameDetail, txtBrewTypeDetail, txtBrewUserDetail;
    private RatingBar avgRating, myRating;
    private BrewViewModel mViewModel;
    private BrewAdaptor brewAdaptor;
    private Context context;
    private String brewId;
    private Brew selectedBrew;
    private FirebaseUser user;
    private Boolean myRatingSet;

    public DetailedBrew() {
        // Required empty public constructor
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mViewModel.UpdateBrew(selectedBrew);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            brewId = bundle.getString("id");
        }
        else if(savedInstanceState != null)
        {
            brewId = savedInstanceState.getString("id");
        }

        return inflater.inflate(R.layout.detailed_brew_fragment, container, false);
    }

    public static DetailedBrew newInstance() {
        return new DetailedBrew();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(BrewViewModel.class);

        user = FirebaseAuth.getInstance().getCurrentUser();

        txtBrewNameDetail = view.findViewById(R.id.txtBrewNameDetail);
        txtBrewTypeDetail = view.findViewById(R.id.txtBrewTypeDetail);
        txtBrewUserDetail = view.findViewById(R.id.txtBrewUserDetail);
        avgRating = view.findViewById(R.id.avgRating);
        myRating = view.findViewById(R.id.myRating);
        mViewModel.loadAllBrews();
        mViewModel.getAllBrews().observe(this, brews -> {
            for (Brew brew: brews) {
                if (brew.getId().equals(brewId)) {
                    selectedBrew = brew;
                    myRatingSet = false;
                    Map<String, String> map = (Map)brew.getUserRatings();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        if (user.getUid().contains(entry.getKey())) {
                            myRatingSet = true;
                            myRating.setRating(Float.parseFloat(entry.getValue()));
                        }
                    }

                    txtBrewNameDetail.setText(brew.getTitle());
                    txtBrewTypeDetail.setText(brew.getBeerType());
                    txtBrewUserDetail.setText(brew.getUsername());
                    avgRating.setRating(brew.getAvgRating());
                }
            }
        });

        myRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(selectedBrew!= null)
                {
                    myRating.setRating(rating);
                    Map<String, String> map = (Map)selectedBrew.getUserRatings();
                    float newAvgRating;
                    if (map.containsKey(user.getUid())) {
                        float oldRating = Float.parseFloat(map.get(user.getUid()));
                        newAvgRating = (
                                selectedBrew.getAvgRating()*map.size()+rating-oldRating)/map.size();
                    } else {
                        newAvgRating = (
                                selectedBrew.getAvgRating()*map.size()+rating)/(map.size()+1);
                    }
                    map.put(user.getUid(), Float.toString(rating));
                    avgRating.setRating(newAvgRating);
                    selectedBrew.setUserRatings(map);
                    selectedBrew.setAvgRating(avgRating.getRating());
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("id", brewId);
    }
}
