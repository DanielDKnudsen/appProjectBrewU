package com.danieldk.brewuappassignment2.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.danieldk.brewuappassignment2.Adaptor.BeerAdaptor;
import com.danieldk.brewuappassignment2.Models.Brew;
import com.danieldk.brewuappassignment2.R;
import com.danieldk.brewuappassignment2.ViewModels.BrewViewModel;

import java.util.List;

public class AllBrews extends Fragment {

    private BrewViewModel mViewModel;
    private ListView listViewAllBeers;
    private List<Brew> brewList;
    private BeerAdaptor beerAdaptor;
    private ProgressBar loader;
    private Context context;
    private EditText searchBrew;

    public static AllBrews newInstance() {
        return new AllBrews();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        return inflater.inflate(R.layout.all_brews_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(BrewViewModel.class);
        listViewAllBeers = view.findViewById(R.id.listAllBrews);
        loader = view.findViewById(R.id.loader);
        searchBrew = view.findViewById(R.id.SearchBrewType);
        loader.setVisibility(view.VISIBLE);
        mViewModel.loadAllBrews();
        mViewModel.getAllBrews().observe(this,brews ->{
            beerAdaptor = new BeerAdaptor(context,brews);
            listViewAllBeers.setAdapter(beerAdaptor);
            loader.setVisibility(view.GONE);
        });

        searchBrew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                beerAdaptor.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}
