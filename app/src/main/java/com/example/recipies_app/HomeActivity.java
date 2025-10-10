package com.example.recipies_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvFeaturedRecipes;
    private RecyclerView rvRecentRecipes;
    private ImageView ivSearch;
    private ImageView ivProfile;
    private Button btnNewRecipe;
    private LinearLayout llBreakfastCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setupClickListeners();
        setupRecyclerViews();
    }

    private void initViews() {
        rvFeaturedRecipes = findViewById(R.id.rv_featured_recipes);
        rvRecentRecipes = findViewById(R.id.rv_recent_recipes);
        ivSearch = findViewById(R.id.iv_search);
        ivProfile = findViewById(R.id.iv_profile);
        btnNewRecipe = findViewById(R.id.btn_new_recipe);
        llBreakfastCategory = findViewById(R.id.ll_breakfast_category);
    }

    private void setupClickListeners() {
        btnNewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CreateRecipeActivity.class);
                startActivity(intent);
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        llBreakfastCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RecipeDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerViews() {
        rvFeaturedRecipes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvRecentRecipes.setLayoutManager(new LinearLayoutManager(this));
    }
}