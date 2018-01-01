package com.example.nikhil.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhil.bakingapp.R;
import com.example.nikhil.bakingapp.pojos.Ingredient;

import java.util.ArrayList;

/**
 * Created by NIKHIL on 23-12-2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private Context context;
    private ArrayList<Ingredient> ingredientsList = new ArrayList<>();

    public IngredientsAdapter(Context context, ArrayList<Ingredient> ingredientsList) {
        this.context = context;
        this.ingredientsList = ingredientsList;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_ingredient, parent, false));
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        Ingredient ingredient = ingredientsList.get(position);
        holder.textViewName.setText(ingredient.getIngredient());
        holder.textViewMeasure.setText(String.valueOf(ingredient.getQuantity()) + " " + String.valueOf(ingredient.getMeasure()));
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewMeasure;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.ingredientName);
            textViewMeasure = itemView.findViewById(R.id.ingredientMeasure);
        }
    }
}
