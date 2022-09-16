package com.example.myrecipesapp.roomdb

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(recipesEntity: RecipesEntity)

    @Query("SELECT * FROM recipes WHERE recipeQuery LIKE :query")
    fun readRecipes(query: String) : Flow<RecipesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavourites(favouritesEntity: FavouritesEntity)

    @Query("SELECT * FROM favourites")
    fun readFavourites() : Flow<List<FavouritesEntity>>

    @Query("DELETE FROM favourites WHERE recipeID = :recipeID")
    fun deleteFavourite(recipeID: Int)
}