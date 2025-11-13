package com.example.recipies_app;

public class ShoppingItem {
    private String name;
    private String quantity;
    private boolean isChecked;
    private String category;

    public ShoppingItem(String name, String quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.isChecked = false;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public String getCategory() {
        return category;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
