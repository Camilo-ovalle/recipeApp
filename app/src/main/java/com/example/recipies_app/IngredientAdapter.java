package com.example.recipies_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredients;

    public IngredientAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.tvIngredientName.setText(ingredient.getName());
        holder.tvIngredientQuantity.setText(ingredient.getQuantity());
        holder.tvIngredientCalories.setText(ingredient.getCalories() + " cal");
    }

    @Override
    public int getItemCount() {
        return ingredients != null ? ingredients.size() : 0;
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView tvIngredientName;
        TextView tvIngredientQuantity;
        TextView tvIngredientCalories;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredientName = itemView.findViewById(R.id.tv_ingredient_name);
            tvIngredientQuantity = itemView.findViewById(R.id.tv_ingredient_quantity);
            tvIngredientCalories = itemView.findViewById(R.id.tv_ingredient_calories);
        }
    }
}
