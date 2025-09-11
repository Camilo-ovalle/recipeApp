package com.example.recipies_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyRecipesAdapter extends RecyclerView.Adapter<MyRecipesAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private Context context;

    public MyRecipesAdapter(List<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        
        holder.tvRecipeName.setText(recipe.getName());
        holder.tvRecipeDescription.setText(recipe.getDescription());
        holder.tvCookTime.setText(recipe.getCookTime());
        holder.tvCalories.setText(recipe.getCalories());
        
        // Por ahora usamos un icono por defecto, más adelante se puede mejorar
        holder.ivRecipeIcon.setImageResource(R.drawable.ic_recipe_book);
        
        // Mostrar badge de "Mi receta" si es del usuario
        if (recipe.isUserRecipe()) {
            holder.tvUserBadge.setVisibility(View.VISIBLE);
        } else {
            holder.tvUserBadge.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a detalle de receta
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra("recipe_name", recipe.getName());
                intent.putExtra("recipe_description", recipe.getDescription());
                intent.putExtra("cook_time", recipe.getCookTime());
                intent.putExtra("calories", recipe.getCalories());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRecipeIcon;
        TextView tvRecipeName;
        TextView tvRecipeDescription;
        TextView tvCookTime;
        TextView tvCalories;
        TextView tvUserBadge;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRecipeIcon = itemView.findViewById(R.id.iv_recipe_icon);
            tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            tvRecipeDescription = itemView.findViewById(R.id.tv_recipe_description);
            tvCookTime = itemView.findViewById(R.id.tv_cook_time);
            tvCalories = itemView.findViewById(R.id.tv_calories);
            tvUserBadge = itemView.findViewById(R.id.tv_user_badge);
        }
    }
}