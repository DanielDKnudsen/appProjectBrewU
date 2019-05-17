package com.danieldk.brewuappassignment2.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.danieldk.brewuappassignment2.Adaptor.BrewAdaptor;
import com.danieldk.brewuappassignment2.BrewVolley;
import com.danieldk.brewuappassignment2.Models.Brew;
import com.danieldk.brewuappassignment2.Models.BrewTypeAPI;
import com.danieldk.brewuappassignment2.R;
import com.danieldk.brewuappassignment2.ViewModels.BrewViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllBrews extends Fragment {

    public static final String LOCAL_BROADCAST_ALL_BREWS = "ALLBREWS";

    private BrewViewModel mViewModel;
    private ListView listViewAllBeers;
    private List<Brew> brewList;
    private BrewAdaptor brewAdaptor;
    private ProgressBar loader;
    private Context context;
    private AutoCompleteTextView searchBrew;
    private BroadcastReceiver broadcastReceiver;

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    public static AllBrews newInstance() {
        return new AllBrews();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        context = container.getContext();

        IntentFilter filter = new IntentFilter();
        filter.addAction(BrewVolley.BROADCAST_BREWTYPES_RESULT);
        LocalBroadcastManager.getInstance(context).registerReceiver(onBrewVolleyResult, filter);

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
        mViewModel.getAllBrews().observe(this, brews -> {
            brewAdaptor = new BrewAdaptor(context, brews);
            listViewAllBeers.setAdapter(brewAdaptor);
            String searchText = searchBrew.getText().toString();
            if(!searchText.isEmpty())
            {
                brewAdaptor.getFilter().filter(searchText);
            }
            loader.setVisibility(view.GONE);
        });
        BrewVolley brewVolley = new BrewVolley(context);
        brewVolley.sendRequest();


        searchBrew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(brewAdaptor != null)
                {
                    brewAdaptor.getFilter().filter(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // goes to detail
        listViewAllBeers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Brew brew = (Brew) listViewAllBeers.getItemAtPosition(position);
                Fragment detailedBrew = new DetailedBrew();
                Bundle bundle = new Bundle();
                bundle.putString("id", brew.getId()); // hvad skal sendes med over?
                detailedBrew.setArguments(bundle);

                fragmentManager = getActivity().getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentContainer, detailedBrew);
                transaction.commit();
            }
        });
    }


    private final BroadcastReceiver onBrewVolleyResult = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<BrewTypeAPI> BeerTypesTest = (List<BrewTypeAPI>) intent.getExtras().getSerializable(BrewVolley.BREWTYPES_BUNDLE_RESULT);
            ArrayList<String> BrewTypes = new ArrayList<String>();

            for (int i = 0; i < BeerTypesTest.size(); i++) {
                BrewTypes.add(BeerTypesTest.get(i).getShortName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, BrewTypes);
            searchBrew.setAdapter(adapter);
        }
    };
}
