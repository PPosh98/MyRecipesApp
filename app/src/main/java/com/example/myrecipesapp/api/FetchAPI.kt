package com.example.myrecipesapp.api

import com.example.myrecipesapp.api.APIReference.API_KEY
import com.example.myrecipesapp.model.recipeInfo.RecipeInfoModel
import com.example.myrecipesapp.model.recipes.RecipesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FetchAPI {

    @GET("complexSearch")
    suspend fun getRecipes(
        @Query("query") query: String,
        @Query("number") number: String = "100",
        @Query("apiKey") apiKey: String = API_KEY
    ) : Response<RecipesModel>

    @GET("{id}/information")
    suspend fun getRecipeInfo(
        @Path("id") id: String,
        @Query("includeNutrition") includeNutrition: Boolean = false,
        @Query("apiKey") apiKey: String = API_KEY
    ) : Response<RecipeInfoModel>
}