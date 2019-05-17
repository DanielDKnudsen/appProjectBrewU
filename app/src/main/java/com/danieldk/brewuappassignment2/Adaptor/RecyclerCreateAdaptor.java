package com.danieldk.brewuappassignment2.Adaptor;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.danieldk.brewuappassignment2.Models.Step;
import com.danieldk.brewuappassignment2.R;

import java.util.List;

public class RecyclerCreateAdaptor extends RecyclerView.Adapter<RecyclerCreateAdaptor.CreateViewHolder> {
    private List<Step> mDataset;
    private Context context;


    public static class CreateViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cv;
        TextView title;
        EditText description;
        EditText temperature;
        EditText time;

        public CreateViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cardViewCreate);
            time = itemView.findViewById(R.id.timeCreate);
            title = itemView.findViewById(R.id.titleCreate);
            description = itemView.findViewById(R.id.descriptionCreate);
            temperature = itemView.findViewById(R.id.temperatureCreate);
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_step_item, parent, false);
        CreateViewHolder holder = new CreateViewHolder(v);
        return holder;

    }

    public List<Step> getSteps() {
        return mDataset;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CreateViewHolder holder, int position) {
        holder.title.setText("Step: " + mDataset.get(position).getStepOrder());

        holder.description.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDataset.get(position).setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.temperature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDataset.get(position).setTemperature(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDataset.get(position).setTime(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        step.setStepOrder(position+1);
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

