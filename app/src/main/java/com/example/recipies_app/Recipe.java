package com.example.recipies_app;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private String description;
    private String cookTime;
    private int calories;
    private String iconName;

    @SerializedName("isUserRecipe")
    private boolean isUserRecipe;

    private Integer userId;
    private String category;
    private String difficulty;
    private String imageUrl;
    private String createdAt;

    // Lista de ingredientes (viene de la API cuando se consulta una receta específica)
    private List<Ingredient> ingredients;

    // User asociado (opcional)
    private User user;

    // Constructor vacío para Gson
    public Recipe() {
    }

    // Constructor con campos básicos (para compatibilidad)
    public Recipe(String name, String description, String cookTime, String calories, String iconName, boolean isUserRecipe) {
        this.name = name;
        this.description = description;
        this.cookTime = cookTime;
        try {
            this.calories = Integer.parseInt(calories);
        } catch (NumberFormatException e) {
            this.calories = 0;
        }
        this.iconName = iconName;
        this.isUserRecipe = isUserRecipe;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCookTime() {
        return cookTime;
    }

    public int getCalories() {
        return calories;
    }

    // Método auxiliar para obtener calorías como String (compatibilidad)
    public String getCaloriesString() {
        return String.valueOf(calories);
    }

    public String getIconName() {
        return iconName;
    }

    public boolean isUserRecipe() {
        return isUserRecipe;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    // Método auxiliar para setear calorías desde String (compatibilidad)
    public void setCalories(String calories) {
        try {
            this.calories = Integer.parseInt(calories);
        } catch (NumberFormatException e) {
            this.calories = 0;
        }
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public void setUserRecipe(boolean userRecipe) {
        isUserRecipe = userRecipe;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setUser(User user) {
        this.user = user;
    }
}