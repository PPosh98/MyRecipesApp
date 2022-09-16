package com.example.myrecipesapp.repository

import com.example.myrecipesapp.api.FetchAPI
import com.example.myrecipesapp.model.recipeInfo.RecipeInfoModel
import com.example.myrecipesapp.model.recipes.RecipesModel
import com.example.myrecipesapp.roomdb.FavouritesEntity
import com.example.myrecipesapp.roomdb.RecipesDAO
import com.example.myrecipesapp.roomdb.RecipesEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val fetchAPI: FetchAPI, private val recipesDAO: RecipesDAO) :
    Repository {
    override suspend fun getRecipes(query: String): Response<RecipesModel> =
        fetchAPI.getRecipes(query)

    override suspend fun getRecipeInfo(id: String): Response<RecipeInfoModel> =
        fetchAPI.getRecipeInfo(id)

    override fun addRecipesToDB(recipesEntity: RecipesEntity) {
        recipesDAO.insertRecipes(recipesEntity)
    }

    override fun getRecipesDB(query: String): Flow<RecipesEntity> =
        recipesDAO.readRecipes(query)

    override fun addFavouriteToDB(favouritesEntity: FavouritesEntity) {
        recipesDAO.insertFavourites(favouritesEntity)
    }

    override fun removeFavouriteFromDB(recipeId: Int) {
        recipesDAO.deleteFavourite(recipeId)
    }

    override fun getFavouritesFromDB(): Flow<List<FavouritesEntity>> =
        recipesDAO.readFavourites()
}