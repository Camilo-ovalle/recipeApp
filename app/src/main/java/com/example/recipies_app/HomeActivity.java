package com.example.recipies_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipies_app.api.ApiResponse;
import com.example.recipies_app.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvFeaturedRecipes;
    private RecyclerView rvRecentRecipes;
    private ImageView ivSearch;
    private ImageView ivProfile;
    private Button btnNewRecipe;
    private LinearLayout llBreakfastCategory;

    private RecipeAdapter featuredAdapter;
    private RecipeAdapter recentAdapter;
    private List<Recipe> featuredRecipes;
    private List<Recipe> recentRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setupClickListeners();
        setupRecyclerViews();
        loadRecipes();
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
                Intent intent = new Intent(HomeActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerViews() {
        // Setup Featured Recipes RecyclerView (Horizontal)
        featuredRecipes = new ArrayList<>();
        featuredAdapter = new RecipeAdapter(featuredRecipes, this, RecipeAdapter.LAYOUT_TYPE_FEATURED, recipe -> {
            // Navegar a detalle de receta
            Intent intent = new Intent(HomeActivity.this, RecipeDetailActivity.class);
            intent.putExtra("recipe_id", recipe.getId());
            intent.putExtra("recipe_name", recipe.getName());
            startActivity(intent);
        });
        rvFeaturedRecipes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvFeaturedRecipes.setAdapter(featuredAdapter);

        // Setup Recent Recipes RecyclerView (Vertical)
        recentRecipes = new ArrayList<>();
        recentAdapter = new RecipeAdapter(recentRecipes, this, RecipeAdapter.LAYOUT_TYPE_LIST, recipe -> {
            // Navegar a detalle de receta
            Intent intent = new Intent(HomeActivity.this, RecipeDetailActivity.class);
            intent.putExtra("recipe_id", recipe.getId());
            intent.putExtra("recipe_name", recipe.getName());
            startActivity(intent);
        });
        rvRecentRecipes.setLayoutManager(new LinearLayoutManager(this));
        rvRecentRecipes.setAdapter(recentAdapter);
    }

    private void loadRecipes() {
        Toast.makeText(this, "Cargando recetas...", Toast.LENGTH_SHORT).show();

        // Llamada a la API para obtener todas las recetas
        Call<ApiResponse<List<Recipe>>> call = RetrofitClient.getInstance()
                .getApiService()
                .getAllRecipes();

        call.enqueue(new Callback<ApiResponse<List<Recipe>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Recipe>>> call, Response<ApiResponse<List<Recipe>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Recipe>> apiResponse = response.body();

                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        List<Recipe> allRecipes = apiResponse.getData();

                        if (!allRecipes.isEmpty()) {
                            // Dividir recetas: primeras 5 para featured, el resto para recent
                            int featuredCount = Math.min(5, allRecipes.size());
                            featuredRecipes.clear();
                            featuredRecipes.addAll(allRecipes.subList(0, featuredCount));
                            featuredAdapter.notifyDataSetChanged();

                            // Recetas recientes (todas o las que sobran después de featured)
                            recentRecipes.clear();
                            if (allRecipes.size() > featuredCount) {
                                recentRecipes.addAll(allRecipes.subList(featuredCount, allRecipes.size()));
                            } else {
                                // Si hay pocas recetas, mostrar las mismas en ambos
                                recentRecipes.addAll(allRecipes);
                            }
                            recentAdapter.notifyDataSetChanged();

                            Toast.makeText(HomeActivity.this,
                                    "✓ " + allRecipes.size() + " recetas cargadas",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(HomeActivity.this,
                                    "No hay recetas disponibles",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String error = apiResponse.getError() != null ? apiResponse.getError() : "Error desconocido";
                        Toast.makeText(HomeActivity.this,
                                "Error: " + error,
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this,
                            "Error al cargar recetas (código: " + response.code() + ")",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Recipe>>> call, Throwable t) {
                Toast.makeText(HomeActivity.this,
                        "Error de conexión: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}