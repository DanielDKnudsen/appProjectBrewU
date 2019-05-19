package com.danieldk.brewuappassignment2.Adaptor;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danieldk.brewuappassignment2.Models.Step;
import com.danieldk.brewuappassignment2.R;

import java.util.List;

// Inspiration til brug af recyclerview og Cardview https://www.binpress.com/android-recyclerview-cardview-guide/

public class RecyclerDetailsAdaptor extends RecyclerView.Adapter<RecyclerDetailsAdaptor.MyViewHolder> {
    private List<Step> mDataset;
    private Context context;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cv;
        TextView title;
        TextView description;
        TextView temperature;
        TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            title = (TextView) itemView.findViewById(R.id.title);
            temperature = itemView.findViewById(R.id.temperature);
            time = itemView.findViewById(R.id.time);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerDetailsAdaptor(List<Step> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerDetailsAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_step_item, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText("Step: " + mDataset.get(position).getStepOrder());
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

