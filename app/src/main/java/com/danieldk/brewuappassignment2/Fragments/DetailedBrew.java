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
import android.widget.TextView;

import com.danieldk.brewuappassignment2.Adaptor.BeerAdaptor;
import com.danieldk.brewuappassignment2.R;
import com.danieldk.brewuappassignment2.ViewModels.BrewViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class DetailedBrew extends Fragment {

    private BrewViewModel mViewModel;
    private BeerAdaptor beerAdaptor;
    private Context context;

    public DetailedBrew() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        return inflater.inflate(R.layout.detailed_brew_fragment, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            long test = bundle.getInt("id", 0); // Key, default value
        }
    }

    public static DetailedBrew newInstance() {
        return new DetailedBrew();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(BrewViewModel.class);

    }


}
