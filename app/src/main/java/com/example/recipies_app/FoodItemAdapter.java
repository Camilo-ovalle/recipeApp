package com.example.recipies_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.FoodViewHolder> {

    private List<FoodItem> foodItems;
    private OnFoodDeleteListener deleteListener;

    public interface OnFoodDeleteListener {
        void onFoodDelete(int position);
    }

    public FoodItemAdapter(List<FoodItem> foodItems, OnFoodDeleteListener deleteListener) {
        this.foodItems = foodItems;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem item = foodItems.get(position);
        holder.tvFoodName.setText(item.getName());
        holder.tvFoodCalories.setText(item.getCalories() + " cal");

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteListener != null) {
                    deleteListener.onFoodDelete(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName;
        TextView tvFoodCalories;
        ImageView ivDelete;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tv_food_name);
            tvFoodCalories = itemView.findViewById(R.id.tv_food_calories);
            ivDelete = itemView.findViewById(R.id.iv_delete_food);
        }
    }
}
