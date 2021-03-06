package com.danieldk.brewuappassignment2.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;

import com.danieldk.brewuappassignment2.Models.Brew;
import com.danieldk.brewuappassignment2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class BrewAdaptor extends BaseAdapter implements Filterable {

    private Context context;
    private Brew brew;
    private List<Brew> OriginalBrews;
    private List<Brew> FilteredBrews;
    //found inspiration from movie app project
    public BrewAdaptor(Context context, List<Brew> brews)
    {
        this.context = context;
        this.OriginalBrews = brews;
        this.FilteredBrews = brews;
    }

    @Override
    public int getCount() {
        return FilteredBrews.size();
    }

    @Override
    public Object getItem(int position) {
        return FilteredBrews.get(position);
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
        brew = FilteredBrews.get(position);

        if(brew != null)
        {
            TextView txtTitle = convertView.findViewById(R.id.txtBrewName);
            TextView txtType = convertView.findViewById(R.id.txtBrewType);
            TextView txtUser = convertView.findViewById(R.id.txtUserName);
            RatingBar rating = convertView.findViewById(R.id.rating);

            txtTitle.setText(brew.getTitle());
            txtType.setText(brew.getBeerType());
            rating.setRating(brew.getAvgRating());
            txtUser.setText(brew.getUsername());
        }

        return convertView;
    }

    public List<Brew> getBrewList() {
        return FilteredBrews;
    }

    public void setBrewList(List<Brew> brewList) {
        OriginalBrews = brewList;
    }

    // Inspired from https://stackoverflow.com/questions/14118309/how-to-use-search-functionality-in-custom-list-view-in-android
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<Brew> Filtered = new ArrayList<Brew>();

                if (OriginalBrews == null) {
                    OriginalBrews = FilteredBrews;
                }

                if (constraint == null || constraint.length() == 0){
                    filterResults.count = OriginalBrews.size();
                    filterResults.values = OriginalBrews;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < OriginalBrews.size(); i++) {
                        String data = OriginalBrews.get(i).getBeerType();
                        String username = OriginalBrews.get(i).getUsername();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            Filtered.add(OriginalBrews.get(i));
                        } else if (username.toLowerCase().contains(constraint.toString()))
                        {
                            Filtered.add(OriginalBrews.get(i));
                        }
                    }
                    filterResults.count = Filtered.size();
                    filterResults.values = Filtered;
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                FilteredBrews = (List<Brew>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
