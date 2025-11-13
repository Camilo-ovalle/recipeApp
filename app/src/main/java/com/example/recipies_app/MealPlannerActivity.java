package com.example.recipies_app;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MealPlannerActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvWeekRange;
    private RecyclerView rvMealPlanner;
    private MealPlanAdapter adapter;
    private List<MealPlan> weekMealPlans;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_planner);

        calendar = Calendar.getInstance();

        initViews();
        setupClickListeners();
        setupRecyclerView();
        loadWeekMealPlans();
        updateWeekRange();
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_back);
        tvWeekRange = findViewById(R.id.tv_week_range);
        rvMealPlanner = findViewById(R.id.rv_meal_planner);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        weekMealPlans = new ArrayList<>();

        adapter = new MealPlanAdapter(weekMealPlans, this, new MealPlanAdapter.OnMealClickListener() {
            @Override
            public void onBreakfastClick(MealPlan mealPlan, int position) {
                showMealOptionsDialog(mealPlan, "Desayuno", position, "breakfast");
            }

            @Override
            public void onLunchClick(MealPlan mealPlan, int position) {
                showMealOptionsDialog(mealPlan, "Almuerzo", position, "lunch");
            }

            @Override
            public void onDinnerClick(MealPlan mealPlan, int position) {
                showMealOptionsDialog(mealPlan, "Cena", position, "dinner");
            }
        });

        rvMealPlanner.setLayoutManager(new LinearLayoutManager(this));
        rvMealPlanner.setAdapter(adapter);
    }

    private void loadWeekMealPlans() {
        weekMealPlans.clear();

        // Obtener el lunes de la semana actual
        Calendar weekStart = (Calendar) calendar.clone();
        weekStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        String[] daysOfWeek = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());

        for (int i = 0; i < 7; i++) {
            String dayName = daysOfWeek[i];
            String date = dateFormat.format(weekStart.getTime());

            MealPlan mealPlan = new MealPlan(dayName, date);

            // Datos de ejemplo para algunos días
            if (i == 0) { // Lunes
                mealPlan.setBreakfast("Avena con frutas");
                mealPlan.setLunch("Ensalada César con pollo");
                mealPlan.setDinner("Salmón a la plancha");
                mealPlan.setTotalCalories(1850);
            } else if (i == 2) { // Miércoles
                mealPlan.setBreakfast("Tostadas con aguacate");
                mealPlan.setLunch("Pasta carbonara");
                mealPlan.setTotalCalories(1650);
            }

            weekMealPlans.add(mealPlan);
            weekStart.add(Calendar.DAY_OF_MONTH, 1);
        }

        adapter.notifyDataSetChanged();
    }

    private void updateWeekRange() {
        Calendar weekStart = (Calendar) calendar.clone();
        weekStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Calendar weekEnd = (Calendar) weekStart.clone();
        weekEnd.add(Calendar.DAY_OF_MONTH, 6);

        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM", Locale.getDefault());
        String range = dateFormat.format(weekStart.getTime()) + " - " + dateFormat.format(weekEnd.getTime());
        tvWeekRange.setText(range);
    }

    private void showMealOptionsDialog(MealPlan mealPlan, String mealType, int position, String mealField) {
        // Simular lista de recetas disponibles
        String[] recipes = {
            "Avena con frutas",
            "Tostadas con aguacate",
            "Smoothie verde",
            "Ensalada César con pollo",
            "Pasta carbonara",
            "Salmón a la plancha",
            "Pollo al horno",
            "Sopa de verduras",
            "Tacos de pescado",
            "Pizza casera"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar receta para " + mealType);

        builder.setItems(recipes, (dialog, which) -> {
            String selectedRecipe = recipes[which];

            switch (mealField) {
                case "breakfast":
                    mealPlan.setBreakfast(selectedRecipe);
                    break;
                case "lunch":
                    mealPlan.setLunch(selectedRecipe);
                    break;
                case "dinner":
                    mealPlan.setDinner(selectedRecipe);
                    break;
            }

            // Actualizar calorías (simuladas)
            updateTotalCalories(mealPlan);

            adapter.notifyItemChanged(position);
            Toast.makeText(this, selectedRecipe + " agregado", Toast.LENGTH_SHORT).show();
        });

        // Opción para quitar la comida si ya existe
        if (mealField.equals("breakfast") && !mealPlan.getBreakfast().isEmpty() ||
            mealField.equals("lunch") && !mealPlan.getLunch().isEmpty() ||
            mealField.equals("dinner") && !mealPlan.getDinner().isEmpty()) {

            builder.setNegativeButton("Quitar", (dialog, which) -> {
                switch (mealField) {
                    case "breakfast":
                        mealPlan.setBreakfast("");
                        break;
                    case "lunch":
                        mealPlan.setLunch("");
                        break;
                    case "dinner":
                        mealPlan.setDinner("");
                        break;
                }
                updateTotalCalories(mealPlan);
                adapter.notifyItemChanged(position);
            });
        }

        builder.setNeutralButton("Cancelar", null);
        builder.show();
    }

    private void updateTotalCalories(MealPlan mealPlan) {
        // Simulación de cálculo de calorías
        int totalCalories = 0;

        if (!mealPlan.getBreakfast().isEmpty()) {
            totalCalories += 400; // Promedio desayuno
        }
        if (!mealPlan.getLunch().isEmpty()) {
            totalCalories += 650; // Promedio almuerzo
        }
        if (!mealPlan.getDinner().isEmpty()) {
            totalCalories += 550; // Promedio cena
        }

        mealPlan.setTotalCalories(totalCalories);
    }
}
