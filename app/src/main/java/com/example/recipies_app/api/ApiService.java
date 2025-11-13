package com.example.recipies_app.api;

import com.example.recipies_app.Category;
import com.example.recipies_app.Recipe;
import com.example.recipies_app.ShoppingItem;
import com.example.recipies_app.MealPlan;
import com.example.recipies_app.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Interfaz de Retrofit para definir los endpoints de la API
 * Base URL: http://10.0.2.2:3000 (emulador) o http://IP_LOCAL:3000 (dispositivo físico)
 */
public interface ApiService {

    // ==================== RECETAS ====================

    /**
     * GET /api/recipes - Obtener todas las recetas
     */
    @GET("api/recipes")
    Call<ApiResponse<List<Recipe>>> getAllRecipes();

    /**
     * GET /api/recipes/:id - Obtener receta por ID con ingredientes
     */
    @GET("api/recipes/{id}")
    Call<ApiResponse<Recipe>> getRecipeById(@Path("id") int id);

    /**
     * GET /api/recipes/user/:userId - Recetas de un usuario
     */
    @GET("api/recipes/user/{userId}")
    Call<ApiResponse<List<Recipe>>> getRecipesByUser(@Path("userId") int userId);

    /**
     * GET /api/recipes/category/:category - Recetas por categoría
     */
    @GET("api/recipes/category/{category}")
    Call<ApiResponse<List<Recipe>>> getRecipesByCategory(@Path("category") String category);

    // ==================== CATEGORÍAS ====================

    /**
     * GET /api/categories - Obtener todas las categorías
     */
    @GET("api/categories")
    Call<ApiResponse<List<Category>>> getAllCategories();

    /**
     * GET /api/categories/:id - Obtener categoría por ID
     */
    @GET("api/categories/{id}")
    Call<ApiResponse<Category>> getCategoryById(@Path("id") int id);

    /**
     * GET /api/categories/:id/recipes - Recetas de una categoría
     */
    @GET("api/categories/{id}/recipes")
    Call<ApiResponse<List<Recipe>>> getRecipesByCategoryId(@Path("id") int id);

    // ==================== USUARIOS ====================

    /**
     * GET /api/users - Obtener todos los usuarios
     */
    @GET("api/users")
    Call<ApiResponse<List<User>>> getAllUsers();

    /**
     * GET /api/users/:id - Obtener usuario por ID
     */
    @GET("api/users/{id}")
    Call<ApiResponse<User>> getUserById(@Path("id") int id);

    // ==================== LISTA DE COMPRAS ====================

    /**
     * GET /api/shopping-list - Obtener todos los items
     */
    @GET("api/shopping-list")
    Call<ApiResponse<List<ShoppingItem>>> getAllShoppingItems();

    /**
     * GET /api/shopping-list/:userId - Items de un usuario
     */
    @GET("api/shopping-list/{userId}")
    Call<ApiResponse<List<ShoppingItem>>> getShoppingItemsByUser(@Path("userId") int userId);

    // ==================== PLANIFICADOR DE COMIDAS ====================

    /**
     * GET /api/meal-plans - Obtener todos los planes
     */
    @GET("api/meal-plans")
    Call<ApiResponse<List<MealPlan>>> getAllMealPlans();

    /**
     * GET /api/meal-plans/:userId - Planes de un usuario
     */
    @GET("api/meal-plans/{userId}")
    Call<ApiResponse<List<MealPlan>>> getMealPlansByUser(@Path("userId") int userId);

    /**
     * GET /api/meal-plans/:userId/week/:date - Planes de una semana
     */
    @GET("api/meal-plans/{userId}/week/{date}")
    Call<ApiResponse<List<MealPlan>>> getMealPlansByWeek(@Path("userId") int userId, @Path("date") String date);

    // ==================== FAVORITOS ====================

    /**
     * GET /api/favorites/:userId - Favoritos del usuario
     */
    @GET("api/favorites/{userId}")
    Call<ApiResponse<List<Recipe>>> getFavoritesByUser(@Path("userId") int userId);

    // ==================== REGISTRO DE USUARIOS ====================

    /**
     * POST /api/users/register - Registrar nuevo usuario
     */
    @POST("api/users/register")
    Call<ApiResponse<User>> registerUser(@Body User user);
}
