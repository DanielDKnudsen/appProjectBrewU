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

import com.danieldk.brewuappassignment2.Adaptor.BeerAdaptor;
import com.danieldk.brewuappassignment2.Models.Brew;
import com.danieldk.brewuappassignment2.R;
import com.danieldk.brewuappassignment2.ViewModels.BrewViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Iterator;
import java.util.Map;


public class DetailedBrew extends Fragment {

    private TextView txtBrewNameDetail;
    private TextView txtBrewTypeDetail;
    private RatingBar avgRating;
    private RatingBar myRating;
    private BrewViewModel mViewModel;
    private BeerAdaptor beerAdaptor;
    private Context context;
    private String brewId;
    private Brew selectedBrew;
    private FirebaseUser user;

    public DetailedBrew() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            brewId = bundle.getString("id");
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
        avgRating = view.findViewById(R.id.avgRating);
        myRating = view.findViewById(R.id.myRating);
        mViewModel.loadAllBrews();
        mViewModel.getAllBrews().observe(this, brews -> {
            for (Brew brew: brews
                 ) {

                if (brew.getId() == brewId) {

                    txtBrewNameDetail.setText(brew.getTitle());
                    txtBrewTypeDetail.setText(brew.getBeerType());
                    avgRating.setRating(brew.getAvgRating());
                    myRating.setRating(brew.getUserRating());
                }
            }
        });

        myRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                myRating.setRating(rating);
            }
        });
    }
}
