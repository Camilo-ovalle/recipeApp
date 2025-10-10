package com.example.recipies_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CreateRecipeActivity extends AppCompatActivity implements FoodItemAdapter.OnFoodDeleteListener {

    private ImageView ivBack;
    private EditText etRecipeName, etRecipeDescription, etCookTime;
    private EditText etFoodName, etFoodCalories;
    private Button btnAddFood, btnSaveRecipe;
    private RecyclerView rvFoodItems;
    private TextView tvTotalCalories;
    private CheckBox cbBreakfast, cbLunch, cbDinner;

    private List<FoodItem> foodItemsList;
    private FoodItemAdapter foodAdapter;
    private int totalCalories = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        initViews();
        setupRecyclerView();
        setupClickListeners();
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_back);
        etRecipeName = findViewById(R.id.et_recipe_name);
        etRecipeDescription = findViewById(R.id.et_recipe_description);
        etCookTime = findViewById(R.id.et_cook_time);
        etFoodName = findViewById(R.id.et_food_name);
        etFoodCalories = findViewById(R.id.et_food_calories);
        btnAddFood = findViewById(R.id.btn_add_food);
        btnSaveRecipe = findViewById(R.id.btn_save_recipe);
        rvFoodItems = findViewById(R.id.rv_food_items);
        tvTotalCalories = findViewById(R.id.tv_total_calories);
        cbBreakfast = findViewById(R.id.cb_breakfast);
        cbLunch = findViewById(R.id.cb_lunch);
        cbDinner = findViewById(R.id.cb_dinner);
    }

    private void setupRecyclerView() {
        foodItemsList = new ArrayList<>();
        foodAdapter = new FoodItemAdapter(foodItemsList, this);
        rvFoodItems.setLayoutManager(new LinearLayoutManager(this));
        rvFoodItems.setAdapter(foodAdapter);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFoodItem();
            }
        });

        btnSaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe();
            }
        });
    }

    private void addFoodItem() {
        String foodName = etFoodName.getText().toString().trim();
        String caloriesStr = etFoodCalories.getText().toString().trim();

        if (foodName.isEmpty()) {
            etFoodName.setError("Ingresa el nombre del alimento");
            etFoodName.requestFocus();
            return;
        }

        if (caloriesStr.isEmpty()) {
            etFoodCalories.setError("Ingresa las calorías");
            etFoodCalories.requestFocus();
            return;
        }

        try {
            int calories = Integer.parseInt(caloriesStr);
            FoodItem foodItem = new FoodItem(foodName, calories);
            foodItemsList.add(foodItem);
            foodAdapter.notifyItemInserted(foodItemsList.size() - 1);

            // Actualizar total de calorías
            totalCalories += calories;
            updateTotalCalories();

            // Limpiar campos
            etFoodName.setText("");
            etFoodCalories.setText("");
            etFoodName.requestFocus();

            Toast.makeText(this, "Alimento añadido", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            etFoodCalories.setError("Ingresa un número válido");
            etFoodCalories.requestFocus();
        }
    }

    @Override
    public void onFoodDelete(int position) {
        if (position >= 0 && position < foodItemsList.size()) {
            FoodItem removedItem = foodItemsList.get(position);
            totalCalories -= removedItem.getCalories();
            foodItemsList.remove(position);
            foodAdapter.notifyItemRemoved(position);
            updateTotalCalories();
            Toast.makeText(this, "Alimento eliminado", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTotalCalories() {
        tvTotalCalories.setText(totalCalories + " cal");
    }

    private void saveRecipe() {
        String recipeName = etRecipeName.getText().toString().trim();
        String recipeDescription = etRecipeDescription.getText().toString().trim();
        String cookTime = etCookTime.getText().toString().trim();

        if (recipeName.isEmpty()) {
            etRecipeName.setError("Ingresa el nombre de la receta");
            etRecipeName.requestFocus();
            return;
        }

        if (recipeDescription.isEmpty()) {
            etRecipeDescription.setError("Ingresa una descripción");
            etRecipeDescription.requestFocus();
            return;
        }

        if (cookTime.isEmpty()) {
            etCookTime.setError("Ingresa el tiempo de cocción");
            etCookTime.requestFocus();
            return;
        }

        if (foodItemsList.isEmpty()) {
            Toast.makeText(this, "Debes añadir al menos un alimento", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener las categorías seleccionadas
        ArrayList<String> selectedCategories = new ArrayList<>();
        if (cbBreakfast.isChecked()) {
            selectedCategories.add("Desayuno");
        }
        if (cbLunch.isChecked()) {
            selectedCategories.add("Almuerzo");
        }
        if (cbDinner.isChecked()) {
            selectedCategories.add("Cena");
        }

        if (selectedCategories.isEmpty()) {
            Toast.makeText(this, "Selecciona al menos una categoría", Toast.LENGTH_SHORT).show();
            return;
        }

        // Preparar las listas de alimentos para enviar
        ArrayList<String> foodNames = new ArrayList<>();
        ArrayList<Integer> foodCaloriesList = new ArrayList<>();
        for (FoodItem item : foodItemsList) {
            foodNames.add(item.getName());
            foodCaloriesList.add(item.getCalories());
        }

        // Preparar los datos para enviar a RecipeDetailActivity
        Intent intent = new Intent(CreateRecipeActivity.this, RecipeDetailActivity.class);
        intent.putExtra("recipe_name", recipeName);
        intent.putExtra("recipe_description", recipeDescription);
        intent.putExtra("cook_time", cookTime);
        intent.putExtra("total_calories", totalCalories + " cal");
        intent.putStringArrayListExtra("categories", selectedCategories);
        intent.putStringArrayListExtra("food_names", foodNames);
        intent.putIntegerArrayListExtra("food_calories", foodCaloriesList);

        // Iniciar RecipeDetailActivity con los datos
        startActivity(intent);
        finish();

        Toast.makeText(this, "Receta guardada exitosamente", Toast.LENGTH_SHORT).show();
    }
}
