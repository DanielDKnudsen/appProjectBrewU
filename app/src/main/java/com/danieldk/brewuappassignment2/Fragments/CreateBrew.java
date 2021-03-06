package com.danieldk.brewuappassignment2.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.danieldk.brewuappassignment2.Adaptor.RecyclerCreateAdaptor;
import com.danieldk.brewuappassignment2.Models.Brew;
import com.danieldk.brewuappassignment2.Models.Step;
import com.danieldk.brewuappassignment2.R;
import com.danieldk.brewuappassignment2.ViewModels.BrewViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Inspiration til brug og implementering af recyclerview https://www.binpress.com/android-recyclerview-cardview-guide/

public class CreateBrew extends Fragment {

    private Context context;
    private FirebaseUser user;
    private BrewViewModel mViewModel;
    private RecyclerView recyclerView;
    private EditText txtBeerTitle;
    private EditText txtBrewtype;
    private TextView txtBrewTitle;
    private RecyclerCreateAdaptor mAdapter;
    private FloatingActionButton btnCreateEmptyStep;
    private Button btnSave;

    public CreateBrew() {    }

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
        recyclerView = view.findViewById(R.id.recyclerViewCreate);
        txtBeerTitle = view.findViewById(R.id.txtBeerTitle);
        txtBrewTitle = view.findViewById(R.id.txtBrewTitle);
        txtBrewtype = view.findViewById(R.id.txtBrewType);
        btnCreateEmptyStep = view.findViewById(R.id.btnCreateEmptyStep);
        btnSave = view.findViewById(R.id.btnSave);
        List<Step> newList = new ArrayList<Step>();

        if (savedInstanceState != null) {
            txtBeerTitle.setText(savedInstanceState.getString("BrewTitle"));
            txtBrewtype.setText(savedInstanceState.getString("BrewTitle"));
            newList = (List<Step>)savedInstanceState.getSerializable("Steps");
        }

        mAdapter = new RecyclerCreateAdaptor(newList, context);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        btnCreateEmptyStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Step step = new Step();
                step.setTime(-1);
                step.setTemperature(-1);
                mAdapter.insert(mAdapter.getItemCount(), step);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Brew brew = new Brew();
                brew.setAvgRating(0);
                Map<String, String> map = new HashMap<>();
                map.put(user.getUid(), "0");
                brew.setUserRatings(map);
                brew.setBeerType(txtBrewtype.getText().toString());
                brew.setTitle(txtBeerTitle.getText().toString());
                brew.setUserId(user.getUid());
                brew.setUsername(user.getDisplayName());
                brew.setCreationDate(new Date());

                mViewModel.createBrew(brew, mAdapter.getSteps());

                Toast toast = Toast.makeText(context,R.string.brew_created,Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("Steps", (Serializable) mAdapter.getSteps());
        outState.putString("BrewType", txtBrewtype.getText().toString());
        outState.putString("BrewTitle", txtBeerTitle.getText().toString());
    }
}
