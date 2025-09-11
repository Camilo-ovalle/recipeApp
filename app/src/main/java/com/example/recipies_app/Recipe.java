package com.example.recipies_app;

public class Recipe {
    private String name;
    private String description;
    private String cookTime;
    private String calories;
    private String iconName;
    private boolean isUserRecipe;

    public Recipe(String name, String description, String cookTime, String calories, String iconName, boolean isUserRecipe) {
        this.name = name;
        this.description = description;
        this.cookTime = cookTime;
        this.calories = calories;
        this.iconName = iconName;
        this.isUserRecipe = isUserRecipe;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCookTime() {
        return cookTime;
    }

    public String getCalories() {
        return calories;
    }

    public String getIconName() {
        return iconName;
    }

    public boolean isUserRecipe() {
        return isUserRecipe;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public void setUserRecipe(boolean userRecipe) {
        isUserRecipe = userRecipe;
    }
}