package com.example.nikhil.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikhil.bakingapp.R;
import com.example.nikhil.bakingapp.pojos.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by NIKHIL on 23-12-2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Recipe> recipeList = new ArrayList<>();
    private OnRecipeClicked recipeListener;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeList, OnRecipeClicked recipeListener) {
        this.context = context;
        this.recipeList = recipeList;
        this.recipeListener = recipeListener;
    }

    public void notifyDataSetChanged(ArrayList<Recipe> recipes) {
        recipeList.clear();
        recipeList.addAll(recipes);
        notifyDataSetChanged();
    }

    public interface OnRecipeClicked {
        void onRecipeSelected(int position, ArrayList<Recipe> list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Recipe currentRecipe = recipeList.get(position);
        holder.nameTextView.setText(currentRecipe.getName());
        holder.nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeListener.onRecipeSelected(position, recipeList);
            }
        });
        if (currentRecipe.getImage().isEmpty())
            { holder.nameImageView.setVisibility(View.GONE); }
        else {
            Picasso.with(context).load(currentRecipe.getImage()).into(holder.nameImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (recipeList == null)
            return 0;
        return recipeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private ImageView nameImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.recipe_name_CardView);
            nameImageView = itemView.findViewById(R.id.recipeNameImageView);
        }
    }

}
