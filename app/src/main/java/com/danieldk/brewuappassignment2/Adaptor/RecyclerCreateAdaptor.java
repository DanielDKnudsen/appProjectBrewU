package com.danieldk.brewuappassignment2.Adaptor;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.danieldk.brewuappassignment2.Models.Step;
import com.danieldk.brewuappassignment2.R;

import java.util.List;

public class RecyclerCreateAdaptor extends RecyclerView.Adapter<RecyclerCreateAdaptor.CreateViewHolder> {
    private EditText title;
    private List<Step> mDataset;
    private Context context;


    public static class CreateViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cv;
        EditText description;
        EditText temperature;
        EditText time;

        public CreateViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            temperature = itemView.findViewById(R.id.temperature);
            time = itemView.findViewById(R.id.time);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerCreateAdaptor(List<Step> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerCreateAdaptor.CreateViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_step_item, parent, false);
        CreateViewHolder holder = new CreateViewHolder(v);
        return holder;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CreateViewHolder holder, int position) {
        holder.description.setText(mDataset.get(position).getDescription());
        holder.temperature.setText(String.valueOf(mDataset.get(position).getTemperature()));
        holder.time.setText(String.valueOf(mDataset.get(position).getTime()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, Step step) {
        mDataset.add(position, step);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(Step step) {
        int position = mDataset.indexOf(step);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }


}

