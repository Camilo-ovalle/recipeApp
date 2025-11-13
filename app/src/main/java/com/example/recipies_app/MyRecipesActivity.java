package com.example.recipies_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

public class MyRecipesActivity extends AppCompatActivity {

    // TODO: Obtener userId desde login/sesión. Por ahora usamos userId=1 (María García)
    private static final int CURRENT_USER_ID = 1;

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
        // Cargar recetas del usuario desde la API
        Call<ApiResponse<List<Recipe>>> call = RetrofitClient.getInstance()
                .getApiService()
                .getRecipesByUser(CURRENT_USER_ID);

        call.enqueue(new Callback<ApiResponse<List<Recipe>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Recipe>>> call, Response<ApiResponse<List<Recipe>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Recipe>> apiResponse = response.body();

                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        // Limpiar lista actual
                        myRecipesList.clear();
                        // Agregar recetas de la API
                        myRecipesList.addAll(apiResponse.getData());

                        // Verificar si la lista está vacía
                        if (myRecipesList.isEmpty()) {
                            tvEmptyState.setVisibility(View.VISIBLE);
                            rvMyRecipes.setVisibility(View.GONE);
                        } else {
                            tvEmptyState.setVisibility(View.GONE);
                            rvMyRecipes.setVisibility(View.VISIBLE);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        tvEmptyState.setVisibility(View.VISIBLE);
                        rvMyRecipes.setVisibility(View.GONE);
                        Toast.makeText(MyRecipesActivity.this,
                                "Error: " + apiResponse.getError(),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    tvEmptyState.setVisibility(View.VISIBLE);
                    rvMyRecipes.setVisibility(View.GONE);
                    Toast.makeText(MyRecipesActivity.this,
                            "Error al cargar recetas",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Recipe>>> call, Throwable t) {
                tvEmptyState.setVisibility(View.VISIBLE);
                rvMyRecipes.setVisibility(View.GONE);
                Toast.makeText(MyRecipesActivity.this,
                        "Error de conexión: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
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