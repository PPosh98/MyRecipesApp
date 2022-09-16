package com.example.myrecipesapp.model.favouriteRecipes

import com.example.myrecipesapp.model.recipes.ResultModel

data class FavouritesModel(
    val results: MutableList<ResultModel> = mutableListOf(),
    var count: Int = 0
)