package com.example.recipies_app;

public class RecipeStep {
    private int stepNumber;
    private String instruction;
    private int timerMinutes;
    private boolean hasTimer;

    public RecipeStep(int stepNumber, String instruction, int timerMinutes) {
        this.stepNumber = stepNumber;
        this.instruction = instruction;
        this.timerMinutes = timerMinutes;
        this.hasTimer = timerMinutes > 0;
    }

    public RecipeStep(int stepNumber, String instruction) {
        this(stepNumber, instruction, 0);
    }

    // Getters
    public int getStepNumber() {
        return stepNumber;
    }

    public String getInstruction() {
        return instruction;
    }

    public int getTimerMinutes() {
        return timerMinutes;
    }

    public boolean hasTimer() {
        return hasTimer;
    }

    // Setters
    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public void setTimerMinutes(int timerMinutes) {
        this.timerMinutes = timerMinutes;
        this.hasTimer = timerMinutes > 0;
    }
}
