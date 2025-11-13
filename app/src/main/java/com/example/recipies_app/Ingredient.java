package com.example.recipies_app;

public class Ingredient {
    private int id;
    private int recipeId;
    private String name;
    private String quantity;
    private int calories;

    // Constructor vacío para Gson
    public Ingredient() {
    }

    public Ingredient(String name, String quantity, int calories) {
        this.name = name;
        this.quantity = quantity;
        this.calories = calories;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public int getCalories() {
        return calories;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
