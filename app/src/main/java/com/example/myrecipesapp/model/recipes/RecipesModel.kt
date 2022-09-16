package com.example.myrecipesapp.model.recipes


import com.google.gson.annotations.SerializedName

data class RecipesModel(
    @SerializedName("number")
    val number: Int = 0,
    @SerializedName("offset")
    val offset: Int = 0,
    @SerializedName("results")
    val results: List<ResultModel> = listOf(),
    @SerializedName("totalResults")
    val totalResults: Int = 0
)