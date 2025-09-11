package com.example.recipies_app;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    private ImageView ivBack;
    private ImageView ivRecipeImage;
    private TextView tvRecipeName;
    private TextView tvRecipeSteps;

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
        tvRecipeSteps = findViewById(R.id.tv_recipe_steps);
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
        // Por ahora mostraremos datos de ejemplo
        tvRecipeName.setText("Pancakes Deliciosos");
        tvRecipeSteps.setText("Pasos de preparación:\n\n" +
                "1. Mezcla 2 tazas de harina con 2 cucharadas de azúcar\n\n" +
                "2. Agrega 2 huevos y 1½ tazas de leche\n\n" +
                "3. Mezcla hasta obtener una masa suave\n\n" +
                "4. Calienta una sartén a fuego medio\n\n" +
                "5. Vierte la mezcla y cocina 2-3 minutos por lado\n\n" +
                "6. Sirve caliente con miel o jarabe");
    }
}