package com.example.recipies_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MyRecipesActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvEmptyState;
    private RecyclerView rvMyRecipes;
    private MyRecipesAdapter adapter;
    private List<Recipe> myRecipesList;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fabAddRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);

        initViews();
        setupClickListeners();
        setupRecyclerView();
        checkForNewRecipe();
        loadMyRecipes();
    }

    @SuppressLint("WrongViewCast")
    private void initViews() {
        ivBack = findViewById(R.id.iv_back);
        tvEmptyState = findViewById(R.id.tv_empty_state);
        rvMyRecipes = findViewById(R.id.rv_my_recipes);
        fabAddRecipe = findViewById(R.id.fab_add_recipe);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fabAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRecipesActivity.this, CreateRecipeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerView() {
        myRecipesList = new ArrayList<>();
        adapter = new MyRecipesAdapter(myRecipesList, this);
        rvMyRecipes.setLayoutManager(new LinearLayoutManager(this));
        rvMyRecipes.setAdapter(adapter);
    }

    private void loadMyRecipes() {
        // Solo cargar recetas de ejemplo si no hay una receta nueva
        Intent intent = getIntent();
        if (intent == null || !intent.getBooleanExtra("is_new_recipe", false)) {
            // Simulamos algunas recetas del usuario
            myRecipesList.add(new Recipe(
                "Pasta Carbonara",
                "Una deliciosa pasta italiana con huevo y panceta",
                "30 min",
                "450 cal",
                "ic_pasta",
                true
            ));

            myRecipesList.add(new Recipe(
                "Ensalada César",
                "Ensalada fresca con pollo y aderezo césar",
                "15 min",
                "320 cal",
                "ic_salad",
                true
            ));

            myRecipesList.add(new Recipe(
                "Salmón a la Plancha",
                "Salmón fresco con verduras al vapor",
                "25 min",
                "380 cal",
                "ic_fish",
                true
            ));

            myRecipesList.add(new Recipe(
                "Smoothie Verde",
                "Batido saludable con espinaca y frutas",
                "5 min",
                "180 cal",
                "ic_smoothie",
                true
            ));
        }

        if (myRecipesList.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            rvMyRecipes.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            rvMyRecipes.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    private void checkForNewRecipe() {
        Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra("is_new_recipe", false)) {
            String recipeName = intent.getStringExtra("recipe_name");
            String recipeDescription = intent.getStringExtra("recipe_description");
            String cookTime = intent.getStringExtra("cook_time");
            String totalCalories = intent.getStringExtra("total_calories");

            // Crear nueva receta con los datos recibidos
            Recipe newRecipe = new Recipe(
                recipeName,
                recipeDescription,
                cookTime,
                totalCalories,
                "ic_my_recipes",
                true
            );

            // Añadir la receta al principio de la lista
            myRecipesList.add(0, newRecipe);

            // Actualizar la vista
            tvEmptyState.setVisibility(View.GONE);
            rvMyRecipes.setVisibility(View.VISIBLE);
            adapter.notifyItemInserted(0);
            rvMyRecipes.scrollToPosition(0);
        }
    }
}