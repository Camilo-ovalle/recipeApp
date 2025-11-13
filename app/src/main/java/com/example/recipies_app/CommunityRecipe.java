package com.example.recipies_app;

public class CommunityRecipe {
    private String recipeName;
    private String authorName;
    private String authorAvatar;
    private String recipeImage;
    private String description;
    private String cookTime;
    private String difficulty;
    private int likes;
    private boolean isLiked;
    private String category;

    public CommunityRecipe(String recipeName, String authorName, String description,
                          String cookTime, String difficulty, int likes, String category) {
        this.recipeName = recipeName;
        this.authorName = authorName;
        this.description = description;
        this.cookTime = cookTime;
        this.difficulty = difficulty;
        this.likes = likes;
        this.category = category;
        this.isLiked = false;
        this.authorAvatar = "ic_profile_avatar";
        this.recipeImage = "ic_recipe_book";
    }

    // Getters
    public String getRecipeName() {
        return recipeName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public String getDescription() {
        return description;
    }

    public String getCookTime() {
        return cookTime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getLikes() {
        return likes;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public String getCategory() {
        return category;
    }

    // Setters
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public void toggleLike() {
        if (isLiked) {
            likes--;
            isLiked = false;
        } else {
            likes++;
            isLiked = true;
        }
    }
}
