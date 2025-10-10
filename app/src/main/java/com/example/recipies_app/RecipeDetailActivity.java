package com.example.recipies_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private ImageView ivBack;
    private ImageView ivRecipeImage;
    private TextView tvRecipeName;
    private TextView tvRecipeDescription;
    private TextView tvCookTime;
    private TextView tvCalories;
    private TextView tvCategories;
    private RecyclerView rvFoodItemsDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        initViews();
        setupClickListeners();
        loadRecipeData();
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_back);
        ivRecipeImage = findViewById(R.id.iv_recipe_image);
        tvRecipeName = findViewById(R.id.tv_recipe_name);
        tvRecipeDescription = findViewById(R.id.tv_recipe_description);
        tvCookTime = findViewById(R.id.tv_cook_time);
        tvCalories = findViewById(R.id.tv_calories);
        tvCategories = findViewById(R.id.tv_categories);
        rvFoodItemsDetail = findViewById(R.id.rv_food_items_detail);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadRecipeData() {
        Intent intent = getIntent();
        if (intent != null) {
            String recipeName = intent.getStringExtra("recipe_name");
            String recipeDescription = intent.getStringExtra("recipe_description");
            String cookTime = intent.getStringExtra("cook_time");
            String totalCalories = intent.getStringExtra("total_calories");
            ArrayList<String> categories = intent.getStringArrayListExtra("categories");
            ArrayList<String> foodNames = intent.getStringArrayListExtra("food_names");
            ArrayList<Integer> foodCalories = intent.getIntegerArrayListExtra("food_calories");

            if (recipeName != null) {
                tvRecipeName.setText(recipeName);
            }

            if (recipeDescription != null) {
                tvRecipeDescription.setText(recipeDescription);
            }

            if (cookTime != null) {
                tvCookTime.setText(cookTime);
            }

            if (totalCalories != null) {
                tvCalories.setText(totalCalories);
            }


            if (categories != null && !categories.isEmpty()) {
                String categoriesText = String.join(", ", categories);
                tvCategories.setText(categoriesText);
            }

            if (foodNames != null && foodCalories != null && !foodNames.isEmpty()) {
                ArrayList<FoodItem> foodItems = new ArrayList<>();
                for (int i = 0; i < foodNames.size(); i++) {
                    foodItems.add(new FoodItem(foodNames.get(i), foodCalories.get(i)));
                }

                FoodItemAdapter adapter = new FoodItemAdapter(foodItems, null);
                rvFoodItemsDetail.setLayoutManager(new LinearLayoutManager(this));
                rvFoodItemsDetail.setAdapter(adapter);
            }
        }
    }
}