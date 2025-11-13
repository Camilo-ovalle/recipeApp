package com.example.recipies_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipies_app.api.ApiResponse;
import com.example.recipies_app.api.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailActivity extends AppCompatActivity {

    private ImageView ivBack;
    private ImageView ivFavorite;
    private ImageView ivShare;
    private ImageView ivRecipeImage;
    private TextView tvRecipeName;
    private TextView tvRecipeDescription;
    private TextView tvCookTime;
    private TextView tvCalories;
    private TextView tvDifficulty;
    private TextView tvCategory;
    private RecyclerView rvIngredients;
    private Button btnViewSteps;

    private int recipeId;
    private Recipe currentRecipe;
    private boolean isFavorite = false;

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
        ivFavorite = findViewById(R.id.iv_favorite);
        ivShare = findViewById(R.id.iv_share);
        ivRecipeImage = findViewById(R.id.iv_recipe_image);
        tvRecipeName = findViewById(R.id.tv_recipe_name);
        tvRecipeDescription = findViewById(R.id.tv_recipe_description);
        tvCookTime = findViewById(R.id.tv_cook_time);
        tvCalories = findViewById(R.id.tv_calories);
        tvDifficulty = findViewById(R.id.tv_difficulty);
        tvCategory = findViewById(R.id.tv_category);
        rvIngredients = findViewById(R.id.rv_ingredients);
        btnViewSteps = findViewById(R.id.btn_view_steps);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(v -> finish());

        ivFavorite.setOnClickListener(v -> toggleFavorite());

        ivShare.setOnClickListener(v -> shareRecipe());

        btnViewSteps.setOnClickListener(v -> {
            if (currentRecipe != null) {
                Intent intent = new Intent(RecipeDetailActivity.this, RecipeStepsActivity.class);
                intent.putExtra("recipe_id", recipeId);
                intent.putExtra("recipe_name", currentRecipe.getName());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Cargando receta...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRecipeData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("recipe_id")) {
            recipeId = intent.getIntExtra("recipe_id", -1);

            if (recipeId != -1) {
                loadRecipeFromApi(recipeId);
            } else {
                Toast.makeText(this, "ID de receta inválido", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "No se recibió información de la receta", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadRecipeFromApi(int id) {
        Toast.makeText(this, "Cargando receta...", Toast.LENGTH_SHORT).show();

        Call<ApiResponse<Recipe>> call = RetrofitClient.getInstance()
                .getApiService()
                .getRecipeById(id);

        call.enqueue(new Callback<ApiResponse<Recipe>>() {
            @Override
            public void onResponse(Call<ApiResponse<Recipe>> call, Response<ApiResponse<Recipe>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Recipe> apiResponse = response.body();

                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        currentRecipe = apiResponse.getData();
                        displayRecipe(currentRecipe);
                    } else {
                        String error = apiResponse.getError() != null ? apiResponse.getError() : "Error desconocido";
                        Toast.makeText(RecipeDetailActivity.this,
                                "Error: " + error,
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RecipeDetailActivity.this,
                            "Error al cargar receta (código: " + response.code() + ")",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Recipe>> call, Throwable t) {
                Toast.makeText(RecipeDetailActivity.this,
                        "Error de conexión: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayRecipe(Recipe recipe) {
        // Nombre
        tvRecipeName.setText(recipe.getName());

        // Descripción
        if (recipe.getDescription() != null && !recipe.getDescription().isEmpty()) {
            tvRecipeDescription.setText(recipe.getDescription());
        } else {
            tvRecipeDescription.setText("Sin descripción disponible");
        }

        // Tiempo de cocción
        tvCookTime.setText(recipe.getCookTime() != null ? recipe.getCookTime() : "N/A");

        // Calorías
        tvCalories.setText(recipe.getCalories() + " cal");

        // Dificultad
        String difficulty = recipe.getDifficulty();
        if (difficulty != null) {
            tvDifficulty.setText(capitalizeFirst(difficulty));
        } else {
            tvDifficulty.setText("N/A");
        }

        // Categoría
        if (recipe.getCategory() != null && !recipe.getCategory().isEmpty()) {
            tvCategory.setText(capitalizeFirst(recipe.getCategory()));
        } else {
            tvCategory.setText("Sin categoría");
        }

        // Ingredientes
        List<Ingredient> ingredients = recipe.getIngredients();
        if (ingredients != null && !ingredients.isEmpty()) {
            IngredientAdapter adapter = new IngredientAdapter(ingredients);
            rvIngredients.setLayoutManager(new LinearLayoutManager(this));
            rvIngredients.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Esta receta no tiene ingredientes registrados", Toast.LENGTH_SHORT).show();
        }

        // TODO: Cargar imagen si imageUrl está disponible
        // Usar Glide o Picasso: Glide.with(this).load(recipe.getImageUrl()).into(ivRecipeImage);
    }

    private void toggleFavorite() {
        isFavorite = !isFavorite;

        if (isFavorite) {
            ivFavorite.setImageResource(R.drawable.ic_favorite_filled);
            Toast.makeText(this, "Agregado a favoritos", Toast.LENGTH_SHORT).show();
        } else {
            ivFavorite.setImageResource(R.drawable.ic_favorite_outline);
            Toast.makeText(this, "Removido de favoritos", Toast.LENGTH_SHORT).show();
        }

        // TODO: Implementar llamada a API para guardar/remover favorito
        // POST/DELETE /api/favorites
    }

    private void shareRecipe() {
        if (currentRecipe != null) {
            String shareText = "🍳 " + currentRecipe.getName() + "\n\n" +
                    currentRecipe.getDescription() + "\n\n" +
                    "⏱️ Tiempo: " + currentRecipe.getCookTime() + "\n" +
                    "🔥 Calorías: " + currentRecipe.getCalories() + " cal\n" +
                    "📊 Dificultad: " + currentRecipe.getDifficulty() + "\n\n" +
                    "¡Descarga RecipeApp para ver más recetas!";

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, currentRecipe.getName());
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(shareIntent, "Compartir receta vía"));
        } else {
            Toast.makeText(this, "Cargando receta...", Toast.LENGTH_SHORT).show();
        }
    }

    private String capitalizeFirst(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }
}