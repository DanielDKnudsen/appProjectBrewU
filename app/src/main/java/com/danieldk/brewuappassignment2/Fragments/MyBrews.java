package com.danieldk.brewuappassignment2.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.danieldk.brewuappassignment2.Adaptor.BeerAdaptor;
import com.danieldk.brewuappassignment2.Models.Brew;
import com.danieldk.brewuappassignment2.ViewModels.BrewViewModel;
import com.danieldk.brewuappassignment2.R;

import java.util.ArrayList;
import java.util.List;

public class MyBrews extends Fragment {

    private BrewViewModel mViewModel;
    private ListView listViewMyBeers;
    private List<Brew> brewList;
    private BeerAdaptor beerAdaptor;

    public static MyBrews newInstance() {
        return new MyBrews();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_brews_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BrewViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewModel.getBrews();
        brewList = (List<Brew>) mViewModel.loadBrews();
        beerAdaptor = new BeerAdaptor(this.getContext(),brewList);
        listViewMyBeers = view.findViewById(R.id.listViewMyBeers);
        listViewMyBeers.setAdapter(beerAdaptor);

    }
}
