package com.enesk.foodrecipes.data.source.network

import com.enesk.foodrecipes.data.source.network.model.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    //https://api.spoonacular.com/recipes/complexSearch?number=50&apiKey=afc441cb71344918b659bff0a54cc71b&type=snack&diet=vegan&addRecipeInformation=true&fillIngredients=true

    //https://api.spoonacular.com/recipes/
    // complexSearch
    // ?number=50
    // &apiKey=afc441cb71344918b659bff0a54cc71b
    // &type=snack
    // &diet=vegan
    // &addRecipeInformation=true
    // &fillIngredients=true
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>
}