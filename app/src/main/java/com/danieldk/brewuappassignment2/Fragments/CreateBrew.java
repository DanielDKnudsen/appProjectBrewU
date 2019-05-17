package com.danieldk.brewuappassignment2.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danieldk.brewuappassignment2.Adaptor.RecyclerDetailsAdaptor;
import com.danieldk.brewuappassignment2.Models.Step;
import com.danieldk.brewuappassignment2.R;
import com.danieldk.brewuappassignment2.ViewModels.BrewViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class CreateBrew extends Fragment {

    private Context context;
    private String brewId;
    private String brewTitle;
    private FirebaseUser user;
    private BrewViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerDetailsAdaptor mAdapter;
    private List<Step> steps;
    private TextView txtBrewTitle;

    public CreateBrew() {
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
        recyclerView = view.findViewById(R.id.recyclerView);
    }
}
