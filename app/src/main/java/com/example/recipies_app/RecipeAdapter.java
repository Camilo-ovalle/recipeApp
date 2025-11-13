package com.example.recipies_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    public static final int LAYOUT_TYPE_FEATURED = 0;  // Horizontal, para destacadas
    public static final int LAYOUT_TYPE_LIST = 1;      // Vertical, para listas

    private List<Recipe> recipes;
    private Context context;
    private int layoutType;
    private OnRecipeClickListener clickListener;

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public RecipeAdapter(List<Recipe> recipes, Context context, int layoutType, OnRecipeClickListener clickListener) {
        this.recipes = recipes;
        this.context = context;
        this.layoutType = layoutType;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (layoutType == LAYOUT_TYPE_FEATURED) {
            view = LayoutInflater.from(context).inflate(R.layout.item_recipe_featured, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_my_recipe, parent, false);
        }
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        holder.tvRecipeName.setText(recipe.getName());
        holder.tvCookTime.setText(recipe.getCookTime() != null ? recipe.getCookTime() : "N/A");
        holder.tvCalories.setText(recipe.getCalories() + " cal");

        // Para el layout de lista (item_my_recipe.xml), tenemos descripción y badge
        if (layoutType == LAYOUT_TYPE_LIST) {
            if (holder.tvRecipeDescription != null) {
                holder.tvRecipeDescription.setText(
                    recipe.getDescription() != null ? recipe.getDescription() : "Sin descripción"
                );
            }

            if (holder.tvUserBadge != null) {
                // Mostrar badge solo si es receta del usuario
                holder.tvUserBadge.setVisibility(
                    recipe.isUserRecipe() ? View.VISIBLE : View.GONE
                );
            }
        }

        // Click listener
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onRecipeClick(recipe);
            }
        });

        // TODO: Cargar imagen real cuando esté disponible
        // Para ahora usamos el icono por defecto
        // Puedes usar Glide o Picasso aquí si tienes URLs de imágenes
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecipeName;
        TextView tvCookTime;
        TextView tvCalories;
        TextView tvRecipeDescription;  // Solo en layout de lista
        TextView tvUserBadge;           // Solo en layout de lista
        ImageView ivRecipeImage;        // Solo en layout featured
        ImageView ivRecipeIcon;         // Solo en layout de lista

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            tvCookTime = itemView.findViewById(R.id.tv_cook_time);
            tvCalories = itemView.findViewById(R.id.tv_calories);

            // Estos IDs varían según el layout
            ivRecipeImage = itemView.findViewById(R.id.iv_recipe_image);  // featured
            ivRecipeIcon = itemView.findViewById(R.id.iv_recipe_icon);    // lista

            // Estos pueden ser null si usamos item_recipe_featured
            tvRecipeDescription = itemView.findViewById(R.id.tv_recipe_description);
            tvUserBadge = itemView.findViewById(R.id.tv_user_badge);
        }
    }

    // Método para actualizar la lista de recetas
    public void updateRecipes(List<Recipe> newRecipes) {
        this.recipes = newRecipes;
        notifyDataSetChanged();
    }
}
