package com.example.myrecipesapp.ui.screens.recipes.search

import com.example.myrecipesapp.model.recipes.ResultModel

data class ScreenState(
    var isLoading: Boolean = false,
    var items: List<ResultModel> = emptyList(),
    var error: String? = null,
    var endReached: Boolean = false,
    var page: Int = 0
)
