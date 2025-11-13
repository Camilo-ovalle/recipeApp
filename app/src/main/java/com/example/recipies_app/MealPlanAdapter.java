package com.example.recipies_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapter.MealDayViewHolder> {

    private List<MealPlan> mealPlans;
    private Context context;
    private OnMealClickListener listener;

    public interface OnMealClickListener {
        void onBreakfastClick(MealPlan mealPlan, int position);
        void onLunchClick(MealPlan mealPlan, int position);
        void onDinnerClick(MealPlan mealPlan, int position);
    }

    public MealPlanAdapter(List<MealPlan> mealPlans, Context context, OnMealClickListener listener) {
        this.mealPlans = mealPlans;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal_day, parent, false);
        return new MealDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealDayViewHolder holder, int position) {
        MealPlan mealPlan = mealPlans.get(position);

        holder.tvDayOfWeek.setText(mealPlan.getDayOfWeek());
        holder.tvDate.setText(mealPlan.getDate());

        // Breakfast
        if (!mealPlan.getBreakfast().isEmpty()) {
            holder.tvBreakfast.setText(mealPlan.getBreakfast());
            holder.tvBreakfast.setVisibility(View.VISIBLE);
            holder.tvBreakfastEmpty.setVisibility(View.GONE);
        } else {
            holder.tvBreakfast.setVisibility(View.GONE);
            holder.tvBreakfastEmpty.setVisibility(View.VISIBLE);
        }

        // Lunch
        if (!mealPlan.getLunch().isEmpty()) {
            holder.tvLunch.setText(mealPlan.getLunch());
            holder.tvLunch.setVisibility(View.VISIBLE);
            holder.tvLunchEmpty.setVisibility(View.GONE);
        } else {
            holder.tvLunch.setVisibility(View.GONE);
            holder.tvLunchEmpty.setVisibility(View.VISIBLE);
        }

        // Dinner
        if (!mealPlan.getDinner().isEmpty()) {
            holder.tvDinner.setText(mealPlan.getDinner());
            holder.tvDinner.setVisibility(View.VISIBLE);
            holder.tvDinnerEmpty.setVisibility(View.GONE);
        } else {
            holder.tvDinner.setVisibility(View.GONE);
            holder.tvDinnerEmpty.setVisibility(View.VISIBLE);
        }

        // Calories
        if (mealPlan.getTotalCalories() > 0) {
            holder.tvCalories.setText(mealPlan.getTotalCalories() + " cal");
            holder.tvCalories.setVisibility(View.VISIBLE);
        } else {
            holder.tvCalories.setVisibility(View.GONE);
        }

        // Click listeners
        holder.layoutBreakfast.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBreakfastClick(mealPlan, position);
            }
        });

        holder.layoutLunch.setOnClickListener(v -> {
            if (listener != null) {
                listener.onLunchClick(mealPlan, position);
            }
        });

        holder.layoutDinner.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDinnerClick(mealPlan, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealPlans.size();
    }

    static class MealDayViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayOfWeek;
        TextView tvDate;
        LinearLayout layoutBreakfast;
        LinearLayout layoutLunch;
        LinearLayout layoutDinner;
        TextView tvBreakfast;
        TextView tvBreakfastEmpty;
        TextView tvLunch;
        TextView tvLunchEmpty;
        TextView tvDinner;
        TextView tvDinnerEmpty;
        TextView tvCalories;

        public MealDayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayOfWeek = itemView.findViewById(R.id.tv_day_of_week);
            tvDate = itemView.findViewById(R.id.tv_date);
            layoutBreakfast = itemView.findViewById(R.id.layout_breakfast);
            layoutLunch = itemView.findViewById(R.id.layout_lunch);
            layoutDinner = itemView.findViewById(R.id.layout_dinner);
            tvBreakfast = itemView.findViewById(R.id.tv_breakfast);
            tvBreakfastEmpty = itemView.findViewById(R.id.tv_breakfast_empty);
            tvLunch = itemView.findViewById(R.id.tv_lunch);
            tvLunchEmpty = itemView.findViewById(R.id.tv_lunch_empty);
            tvDinner = itemView.findViewById(R.id.tv_dinner);
            tvDinnerEmpty = itemView.findViewById(R.id.tv_dinner_empty);
            tvCalories = itemView.findViewById(R.id.tv_calories);
        }
    }
}
