package com.example.recipies_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipies_app.api.ApiResponse;
import com.example.recipies_app.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {

    private ImageView ivBack;
    private RecyclerView rvCategories;
    private CategoryAdapter adapter;
    private List<Category> categoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        initViews();
        setupClickListeners();
        setupRecyclerView();
        loadCategories();
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_back);
        rvCategories = findViewById(R.id.rv_categories);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupRecyclerView() {
        categoriesList = new ArrayList<>();

        // GridLayoutManager con 2 columnas
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvCategories.setLayoutManager(gridLayoutManager);

        adapter = new CategoryAdapter(categoriesList, this, new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(Category category) {
                // TODO: Navegar a lista de recetas filtradas por categoría
                Toast.makeText(CategoriesActivity.this,
                    "Categoría: " + category.getName(),
                    Toast.LENGTH_SHORT).show();
            }
        });

        rvCategories.setAdapter(adapter);
    }

    private void loadCategories() {
        // Obtener categorías desde la API
        Toast.makeText(this, "Cargando categorías...", Toast.LENGTH_SHORT).show();

        Call<ApiResponse<List<Category>>> call = RetrofitClient.getInstance()
                .getApiService()
                .getAllCategories();

        call.enqueue(new Callback<ApiResponse<List<Category>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Category>>> call, Response<ApiResponse<List<Category>>> response) {
                android.util.Log.d("CategoriesActivity", "onResponse - Código: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Category>> apiResponse = response.body();
                    android.util.Log.d("CategoriesActivity", "Success: " + apiResponse.isSuccess());

                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        // Limpiar lista actual
                        categoriesList.clear();
                        // Agregar categorías de la API
                        categoriesList.addAll(apiResponse.getData());

                        android.util.Log.d("CategoriesActivity", "Categorías cargadas: " + categoriesList.size());
                        Toast.makeText(CategoriesActivity.this,
                                "✓ " + categoriesList.size() + " categorías cargadas",
                                Toast.LENGTH_SHORT).show();

                        // Notificar cambios al adaptador
                        adapter.notifyDataSetChanged();
                    } else {
                        String error = apiResponse.getError() != null ? apiResponse.getError() : "Sin error específico";
                        android.util.Log.e("CategoriesActivity", "Error API: " + error);
                        Toast.makeText(CategoriesActivity.this,
                                "Error: " + error,
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    android.util.Log.e("CategoriesActivity", "Response no exitoso: " + response.code());
                    Toast.makeText(CategoriesActivity.this,
                            "Error al cargar categorías (código: " + response.code() + ")",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Category>>> call, Throwable t) {
                android.util.Log.e("CategoriesActivity", "onFailure: " + t.getMessage(), t);
                Toast.makeText(CategoriesActivity.this,
                        "Error de conexión: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
