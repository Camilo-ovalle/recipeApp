package com.example.recipies_app;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categories;
    private Context context;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    public CategoryAdapter(List<Category> categories, Context context, OnCategoryClickListener listener) {
        this.categories = categories;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);

        holder.tvCategoryName.setText(category.getName());
        holder.tvRecipeCount.setText(category.getRecipeCount() + " recetas");

        // Configurar color de fondo
        try {
            holder.cardBackground.setCardBackgroundColor(Color.parseColor(category.getBackgroundColor()));
        } catch (IllegalArgumentException e) {
            holder.cardBackground.setCardBackgroundColor(Color.parseColor("#FFB347"));
        }

        // Configurar icono
        int iconResId = context.getResources().getIdentifier(
                category.getIconName(),
                "drawable",
                context.getPackageName()
        );

        if (iconResId != 0) {
            holder.ivCategoryIcon.setImageResource(iconResId);
        } else {
            holder.ivCategoryIcon.setImageResource(R.drawable.ic_recipe_book);
        }

        // Click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCategoryClick(category);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        CardView cardBackground;
        ImageView ivCategoryIcon;
        TextView tvCategoryName;
        TextView tvRecipeCount;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardBackground = itemView.findViewById(R.id.card_category);
            ivCategoryIcon = itemView.findViewById(R.id.iv_category_icon);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
            tvRecipeCount = itemView.findViewById(R.id.tv_recipe_count);
        }
    }
}
