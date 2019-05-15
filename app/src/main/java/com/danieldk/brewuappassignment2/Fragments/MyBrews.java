package com.danieldk.brewuappassignment2.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.danieldk.brewuappassignment2.Adaptor.BeerAdaptor;
import com.danieldk.brewuappassignment2.Models.Brew;
import com.danieldk.brewuappassignment2.ViewModels.BrewViewModel;
import com.danieldk.brewuappassignment2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MyBrews extends Fragment {

    private BrewViewModel mViewModel;
    private ListView listViewMyBeers;
    private List<Brew> brewList;
    private BeerAdaptor beerAdaptor;
    private ProgressBar loader;
    private Context context;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    public static MyBrews newInstance() {
        return new MyBrews();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        return inflater.inflate(R.layout.my_brews_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(BrewViewModel.class);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        listViewMyBeers = view.findViewById(R.id.listMyBrews);
        loader = view.findViewById(R.id.loader);
        loader.setVisibility(view.VISIBLE);
        mViewModel.loadMyBrews();
        mViewModel.getMyBrews().observe(this, brews -> {
            beerAdaptor = new BeerAdaptor(context, brews);
            listViewMyBeers.setAdapter(beerAdaptor);
            loader.setVisibility(view.GONE);
        });

        // goes to detail
        listViewMyBeers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment detailedBrew = new DetailedBrew();

                fragmentManager = getActivity().getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentContainer,detailedBrew);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });

    }

    }
