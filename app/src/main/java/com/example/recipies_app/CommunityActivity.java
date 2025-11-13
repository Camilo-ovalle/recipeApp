package com.example.recipies_app;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.List;

public class CommunityActivity extends AppCompatActivity {

    private ImageView ivBack;
    private ChipGroup chipGroupFilters;
    private RecyclerView rvCommunityRecipes;
    private CommunityAdapter adapter;
    private List<CommunityRecipe> allRecipes;
    private List<CommunityRecipe> filteredRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        initViews();
        setupClickListeners();
        setupRecyclerView();
        loadCommunityRecipes();
        setupFilters();
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_back);
        chipGroupFilters = findViewById(R.id.chip_group_filters);
        rvCommunityRecipes = findViewById(R.id.rv_community_recipes);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        allRecipes = new ArrayList<>();
        filteredRecipes = new ArrayList<>();

        adapter = new CommunityAdapter(filteredRecipes, this, new CommunityAdapter.OnRecipeClickListener() {
            @Override
            public void onRecipeClick(CommunityRecipe recipe) {
                Toast.makeText(CommunityActivity.this,
                    "Receta: " + recipe.getRecipeName(),
                    Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLikeClick(CommunityRecipe recipe, int position) {
                String message = recipe.isLiked() ? "Te gusta esta receta" : "Ya no te gusta";
                Toast.makeText(CommunityActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        rvCommunityRecipes.setLayoutManager(new LinearLayoutManager(this));
        rvCommunityRecipes.setAdapter(adapter);
    }

    private void loadCommunityRecipes() {
        // Datos de ejemplo de recetas de la comunidad
        allRecipes.add(new CommunityRecipe(
            "Tacos al Pastor Auténticos",
            "Chef María González",
            "Deliciosos tacos con carne marinada y piña asada",
            "45 min",
            "Media",
            128,
            "Almuerzo"
        ));

        allRecipes.add(new CommunityRecipe(
            "Smoothie Bowl Tropical",
            "Ana Martínez",
            "Bowl saludable con frutas frescas y granola",
            "10 min",
            "Fácil",
            203,
            "Desayuno"
        ));

        allRecipes.add(new CommunityRecipe(
            "Lasaña Vegetariana",
            "Carlos Rodríguez",
            "Lasaña cremosa con verduras asadas y queso",
            "60 min",
            "Difícil",
            156,
            "Cena"
        ));

        allRecipes.add(new CommunityRecipe(
            "Cheesecake de Fresa",
            "Pastry Laura",
            "Postre cremoso con base de galleta y fresas frescas",
            "90 min",
            "Media",
            312,
            "Postres"
        ));

        allRecipes.add(new CommunityRecipe(
            "Poké Bowl de Salmón",
            "Sushi Master",
            "Bowl hawaiano con salmón fresco y arroz de sushi",
            "25 min",
            "Media",
            189,
            "Almuerzo"
        ));

        allRecipes.add(new CommunityRecipe(
            "Curry Tailandés Verde",
            "Thai Kitchen",
            "Curry picante con leche de coco y vegetales",
            "35 min",
            "Media",
            167,
            "Cena"
        ));

        allRecipes.add(new CommunityRecipe(
            "Pancakes Esponjosos",
            "Breakfast Lover",
            "Pancakes suaves con jarabe de maple y mantequilla",
            "20 min",
            "Fácil",
            245,
            "Desayuno"
        ));

        allRecipes.add(new CommunityRecipe(
            "Brownies de Chocolate",
            "Choco Addict",
            "Brownies fudgy con chips de chocolate extra",
            "40 min",
            "Fácil",
            278,
            "Postres"
        ));

        // Mostrar todas las recetas inicialmente
        filteredRecipes.clear();
        filteredRecipes.addAll(allRecipes);
        adapter.notifyDataSetChanged();
    }

    private void setupFilters() {
        chipGroupFilters.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) {
                // Si no hay filtros, mostrar todas
                filteredRecipes.clear();
                filteredRecipes.addAll(allRecipes);
            } else {
                // Obtener el chip seleccionado
                int selectedId = checkedIds.get(0);
                Chip selectedChip = findViewById(selectedId);
                String filter = selectedChip.getText().toString();

                // Filtrar recetas
                filteredRecipes.clear();
                if (filter.equals("Todas")) {
                    filteredRecipes.addAll(allRecipes);
                } else if (filter.equals("Populares")) {
                    // Ordenar por likes (descendente)
                    List<CommunityRecipe> sorted = new ArrayList<>(allRecipes);
                    sorted.sort((r1, r2) -> Integer.compare(r2.getLikes(), r1.getLikes()));
                    filteredRecipes.addAll(sorted);
                } else if (filter.equals("Recientes")) {
                    // Mostrar en orden inverso (simulando recientes)
                    for (int i = allRecipes.size() - 1; i >= 0; i--) {
                        filteredRecipes.add(allRecipes.get(i));
                    }
                } else {
                    // Filtrar por categoría
                    for (CommunityRecipe recipe : allRecipes) {
                        if (recipe.getCategory().equals(filter)) {
                            filteredRecipes.add(recipe);
                        }
                    }
                }
            }
            adapter.notifyDataSetChanged();
        });
    }
}
