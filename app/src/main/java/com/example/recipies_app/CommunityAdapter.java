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

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder> {

    private List<CommunityRecipe> recipes;
    private Context context;
    private OnRecipeClickListener listener;

    public interface OnRecipeClickListener {
        void onRecipeClick(CommunityRecipe recipe);
        void onLikeClick(CommunityRecipe recipe, int position);
    }

    public CommunityAdapter(List<CommunityRecipe> recipes, Context context, OnRecipeClickListener listener) {
        this.recipes = recipes;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_community_recipe, parent, false);
        return new CommunityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityViewHolder holder, int position) {
        CommunityRecipe recipe = recipes.get(position);

        holder.tvRecipeName.setText(recipe.getRecipeName());
        holder.tvAuthorName.setText(recipe.getAuthorName());
        holder.tvDescription.setText(recipe.getDescription());
        holder.tvCookTime.setText(recipe.getCookTime());
        holder.tvDifficulty.setText(recipe.getDifficulty());
        holder.tvLikes.setText(String.valueOf(recipe.getLikes()));
        holder.tvCategory.setText(recipe.getCategory());

        // Like icon
        if (recipe.isLiked()) {
            holder.ivLike.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            holder.ivLike.setImageResource(R.drawable.ic_favorite_outline);
        }

        // Click listeners
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRecipeClick(recipe);
            }
        });

        holder.ivLike.setOnClickListener(v -> {
            recipe.toggleLike();
            notifyItemChanged(position);
            if (listener != null) {
                listener.onLikeClick(recipe, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class CommunityViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecipeName;
        TextView tvAuthorName;
        TextView tvDescription;
        TextView tvCookTime;
        TextView tvDifficulty;
        TextView tvLikes;
        TextView tvCategory;
        ImageView ivLike;

        public CommunityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            tvAuthorName = itemView.findViewById(R.id.tv_author_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvCookTime = itemView.findViewById(R.id.tv_cook_time);
            tvDifficulty = itemView.findViewById(R.id.tv_difficulty);
            tvLikes = itemView.findViewById(R.id.tv_likes);
            tvCategory = itemView.findViewById(R.id.tv_category);
            ivLike = itemView.findViewById(R.id.iv_like);
        }
    }
}
