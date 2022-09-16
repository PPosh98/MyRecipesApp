package com.example.myrecipesapp.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myrecipesapp.model.recipes.RecipesModel

@Entity(tableName = "recipes")
class RecipesEntity(
    val recipesModel: RecipesModel,
    @PrimaryKey(autoGenerate = false)
    val recipeQuery: String
    )