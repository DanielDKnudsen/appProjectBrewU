package com.danieldk.brewuappassignment2.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.danieldk.brewuappassignment2.Models.Brew;
import com.danieldk.brewuappassignment2.R;

import java.util.List;

public class BeerAdaptor extends BaseAdapter {

    private List<Brew> BrewList;
    private Context context;
    private Brew brew;

    public BeerAdaptor(Context context, List<Brew> brews)
    {
        this.context = context;
        this.BrewList = brews;
    }

    @Override
    public int getCount() {
        return BrewList.size();
    }

    @Override
    public Object getItem(int position) {
        return BrewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater brewInflator = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = brewInflator.inflate(R.layout.brew_list_item, null);
        }
        brew = BrewList.get(position);

        if(brew != null)
        {
            TextView txtTitle = convertView.findViewById(R.id.txtBrewName);
            TextView txtType = convertView.findViewById(R.id.txtBrewType);

            txtTitle.setText(brew.getTitle());
            txtType.setText(brew.getBeerType());
        }

        return convertView;
    }

    public List<Brew> getBrewList() {
        return BrewList;
    }

    public void setBrewList(List<Brew> brewList) {
        BrewList = brewList;
    }
}
