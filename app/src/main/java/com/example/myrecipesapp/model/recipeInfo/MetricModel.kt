package com.example.myrecipesapp.model.recipeInfo


import com.google.gson.annotations.SerializedName

data class MetricModel(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("unitLong")
    val unitLong: String,
    @SerializedName("unitShort")
    val unitShort: String
)