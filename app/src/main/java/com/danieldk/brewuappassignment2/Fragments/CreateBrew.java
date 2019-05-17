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
        recyclerView = view.findViewById(R.id.recyclerViewCreate);
        txtBeerTitle = view.findViewById(R.id.txtBeerTitle);
        txtBrewTitle = view.findViewById(R.id.txtBrewTitle);
        txtBrewtype = view.findViewById(R.id.txtBrewType);
        btnCreateEmptyStep = view.findViewById(R.id.btnCreateEmptyStep);
        btnSave = view.findViewById(R.id.btnSave);
        List<Step> newList = new ArrayList<Step>();

        Step step1 = new Step();
        step1.setDescription("Fyld 4 liter vand i en gryde, på minimum 8 liter, og varm op til 65°C. Når temperaturen når 65°C hældes malten (pose 3) i. Hold temperaturen stabil på 65°C i 60 minutter. Tag en grydeske og rør jævnligt rundt i 60 minutter.");
        step1.setTemperature(65);
        step1.setTime(60);
        step1.setStepOrder(1);

        Step step2 = new Step();
        step2.setDescription("Opvarm 3,5 liter vand i en gryde til 78° C. til brug under sparging (udmæskning). Efter 60 minutters mæskning, hæves temperaturen til 77°C.\n" +
                "Når temperaturen når 77°C lægges et stort dørslag på kanten af en gryde, på minimum 8 liter, og urten (væsken)\n" +
                "hældes gennem dørslaget og der udmæskes med 78°C varmt vand indtil, der er ca. 6,3 liter urt (væske) i gryden.");
        step2.setTemperature(78);
        step2.setStepOrder(2);

        Step step3 = new Step();
        step3.setDescription("Varm urten (væsken) i gryden til denne koger let (100°C)\n" +
                "Når urten koger let (100°C) tilsættes bitterhumlen, (pose 4)");
        step3.setTime(55);
        step3.setTemperature(100);
        step3.setStepOrder(3);

        Step step4 = new Step();
        step4.setDescription("Tilsæt smagshumle og klar urt (pose 5).");
        step4.setTime(5);
        step4.setTemperature(100);
        step4.setStepOrder(4);

        Step step5 = new Step();
        step5.setDescription("Læg en stor si på kanten af en gryde, på minimum 8 liter, og urten (væsken) hældes gennem sien. Gryden stilles køligt.\n" +
                "Desinficer termometeret med kogende vand, dette skal gøres hver gang temperaturen måles. Mål temperaturen og\n" +
                "afvent temperaturen falder til <26°C. Skyld gærtanken med rindende koldt vand. Den afkølede urt (væsken) hældes på\n" +
                "den rengjorte og desinficerede gærtank. Urten (væsken) skal nå op til første ring ved indsnævringen på gærtanken.\n" +
                "Hvis der er for lidt urt (væske) i gærtanken, efterfyld med koldt vand direkte fra vandhanen i gærtanken. Tag en prøve\n" +
                "af urten (væsken) til en vægtfyldemåling. Tap urten (væsken) via taphanen i målebægeret ca. 5 centimeter fra kanten.\n" +
                "Placeres vægtfyldemåleren forsigtigt i målebægeret og aflæs vægtfylden (OG). Noter vægtfylden nederst på siden\n" +
                "under ”Din OG”. Smag evt. på urten fra målebægeret for at følge udviklingen fra urt til øl. Urten fra målebægeret må\n" +
                "ikke hældes tilbage, men skal smides ud");
        step5.setStepOrder(5);

        Step step6 = new Step();
        step6.setDescription("Tilsæt gær (pose 2). Sæt gummitylle i låget og skold disse af med kogende vand. Skru låget på gærtanken. Sæt et\n" +
                "rengjort og desinficeret gærrør i gummityllen på låget og påfyld vand til det står i den nederste bøjning af gærrøret.\n" +
                "Stil gærtanken ved en temperatur på mellem 18°C – 23°C.");
        step6.setTime(20160);
        step6.setStepOrder(6);


        newList.add(step1);
        newList.add(step2);
        newList.add(step3);
        newList.add(step4);
        newList.add(step5);
        newList.add(step6);


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

                Toast toast = Toast.makeText(context,"Brew Created",Toast.LENGTH_SHORT);
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
