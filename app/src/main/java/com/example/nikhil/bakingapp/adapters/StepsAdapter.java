package com.example.nikhil.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhil.bakingapp.R;
import com.example.nikhil.bakingapp.pojos.Step;

import java.util.ArrayList;

/**
 * Created by NIKHIL on 23-12-2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepHolder> {

    private Context context;
    private ArrayList<Step> stepList = new ArrayList<>();
    private OnStepsClicked listener;

    public StepsAdapter(Context context, ArrayList<Step> stepList, OnStepsClicked listener) {
        this.context = context;
        this.stepList = stepList;
        this.listener = listener;
    }

    public interface OnStepsClicked {
        void onStepClicked(Step step);
    }

    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StepHolder(LayoutInflater.from(context).inflate(R.layout.list_item_step, parent, false));
    }

    @Override
    public void onBindViewHolder(StepHolder holder, final int position) {
        final Step step = stepList.get(position);
        holder.textViewStepName.setText(step.getShortDescription());
        holder.textViewStepName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onStepClicked(step);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (stepList == null)
            return 0;
        return stepList.size();
    }

    class StepHolder extends RecyclerView.ViewHolder {
        private TextView textViewStepName;

        public StepHolder(View itemView) {
            super(itemView);
            textViewStepName = itemView.findViewById(R.id.stepName);
        }
    }
}
