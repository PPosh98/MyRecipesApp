package com.example.myrecipesapp.repository

import com.example.myrecipesapp.model.recipeInfo.RecipeInfoModel
import com.example.myrecipesapp.model.recipes.RecipesModel
import com.example.myrecipesapp.roomdb.FavouritesEntity
import com.example.myrecipesapp.roomdb.RecipesEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {

    suspend fun getRecipes(query: String) : Response<RecipesModel>

    suspend fun getRecipeInfo(id: String) : Response<RecipeInfoModel>

    fun addRecipesToDB(recipesEntity: RecipesEntity)

    fun getRecipesDB(query: String) : Flow<RecipesEntity>

    fun addFavouriteToDB(favouritesEntity: FavouritesEntity)

    fun removeFavouriteFromDB(recipeId: Int)

    fun getFavouritesFromDB() : Flow<List<FavouritesEntity>>
}