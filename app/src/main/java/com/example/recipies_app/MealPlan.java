package com.example.recipies_app;

public class MealPlan {
    private String dayOfWeek;
    private String date;
    private String breakfast;
    private String lunch;
    private String dinner;
    private int totalCalories;

    public MealPlan(String dayOfWeek, String date) {
        this.dayOfWeek = dayOfWeek;
        this.date = date;
        this.breakfast = "";
        this.lunch = "";
        this.dinner = "";
        this.totalCalories = 0;
    }

    // Getters
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDate() {
        return date;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    // Setters
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public boolean hasAnyMeal() {
        return !breakfast.isEmpty() || !lunch.isEmpty() || !dinner.isEmpty();
    }
}
