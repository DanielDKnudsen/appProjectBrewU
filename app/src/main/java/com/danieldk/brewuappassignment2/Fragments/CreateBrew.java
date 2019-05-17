package com.danieldk.brewuappassignment2.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.danieldk.brewuappassignment2.Adaptor.RecyclerCreateAdaptor;
import com.danieldk.brewuappassignment2.Models.Step;
import com.danieldk.brewuappassignment2.R;
import com.danieldk.brewuappassignment2.ViewModels.BrewViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CreateBrew extends Fragment {

    private Context context;
    private FirebaseUser user;
    private BrewViewModel mViewModel;
    private RecyclerView recyclerView;
    private EditText txtBeerTitle;
    private EditText txtBrewtype;
    private TextView txtBrewTitle;
    private RecyclerCreateAdaptor mAdapter;
    private List<Step> steps;
    private FloatingActionButton btnCreateEmptyStep;

    public CreateBrew() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();

        return inflater.inflate(R.layout.create_brew_fragment, container, false);
    }

    public static CreateBrew newInstance() {
        return new CreateBrew();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(BrewViewModel.class);
        user = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = view.findViewById(R.id.recyclerView);
        txtBrewTitle = view.findViewById(R.id.txtBrewTitle);
        txtBrewtype = view.findViewById(R.id.txtBrewType);
        btnCreateEmptyStep = view.findViewById(R.id.btnCreateEmptyStep);
        List<Step> newList = new ArrayList<Step>();

        mAdapter = new RecyclerCreateAdaptor(newList, context);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        btnCreateEmptyStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.insert(mAdapter.getItemCount(), new Step());
            }
        });
    }
}
