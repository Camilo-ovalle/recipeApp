package com.example.recipies_app;

public class Category {
    private int id;
    private String name;
    private String iconName;
    private int recipeCount;
    private String backgroundColor;

    // Constructor vacío para Gson
    public Category() {
    }

    public Category(String name, String iconName, int recipeCount, String backgroundColor) {
        this.name = name;
        this.iconName = iconName;
        this.recipeCount = recipeCount;
        this.backgroundColor = backgroundColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getIconName() {
        return iconName;
    }

    public int getRecipeCount() {
        return recipeCount;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public void setRecipeCount(int recipeCount) {
        this.recipeCount = recipeCount;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
