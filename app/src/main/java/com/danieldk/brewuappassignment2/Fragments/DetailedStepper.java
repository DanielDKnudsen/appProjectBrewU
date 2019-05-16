package com.danieldk.brewuappassignment2.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.danieldk.brewuappassignment2.Adaptor.BrewAdaptor;
import com.danieldk.brewuappassignment2.Adaptor.RecyclerAdaptor;
import com.danieldk.brewuappassignment2.Models.Brew;
import com.danieldk.brewuappassignment2.Models.Step;
import com.danieldk.brewuappassignment2.R;
import com.danieldk.brewuappassignment2.ViewModels.BrewViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class DetailedStepper extends Fragment {

    private Context context;
    private String brewId;
    private String brewTitle;
    private FirebaseUser user;
    private BrewViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerAdaptor mAdapter;
    private List<Step> steps;
    private TextView txtBrewTitle;

    public DetailedStepper() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            brewTitle = bundle.getString("BrewTitle");
            brewId = bundle.getString("BrewId");
        } else {
            brewId = savedInstanceState.getString("BrewId");
            brewTitle = savedInstanceState.getString("BrewTitle");
        }

        return inflater.inflate(R.layout.fragment_detailed_stepper, container, false);
    }

    public static DetailedBrew newInstance() {
        return new DetailedBrew();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(BrewViewModel.class);
        user = FirebaseAuth.getInstance().getCurrentUser();

        txtBrewTitle = view.findViewById(R.id.txtBrewTitle);
        recyclerView = view.findViewById(R.id.recyclerView);

        txtBrewTitle.setText(brewTitle);
        mViewModel.loadSteps(brewId);
        mViewModel.getSteps().observe(this, steps -> {
            this.steps = steps;
            mAdapter = new RecyclerAdaptor(steps, context);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        });
    }


}
