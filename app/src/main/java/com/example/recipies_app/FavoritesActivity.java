package com.example.recipies_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FavoritesActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvFavoritesCount;
    private LinearLayout llFavorite1;
    private LinearLayout llFavorite2;
    private LinearLayout llFavorite3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        initViews();
        setupClickListeners();
        loadFavoriteRecipes();
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_back);
        tvFavoritesCount = findViewById(R.id.tv_favorites_count);
        llFavorite1 = findViewById(R.id.ll_favorite_1);
        llFavorite2 = findViewById(R.id.ll_favorite_2);
        llFavorite3 = findViewById(R.id.ll_favorite_3);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Click listeners para cada receta favorita
        llFavorite1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecipeDetail();
            }
        });

        llFavorite2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecipeDetail();
            }
        });

        llFavorite3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecipeDetail();
            }
        });
    }

    private void loadFavoriteRecipes() {
        // Actualizar contador de favoritos
        tvFavoritesCount.setText("3 recetas favoritas");
    }

    private void openRecipeDetail() {
        Intent intent = new Intent(FavoritesActivity.this, RecipeDetailActivity.class);
        startActivity(intent);
    }
}