package com.example.myrecipesapp.model.recipeInfo


import com.google.gson.annotations.SerializedName

data class MeasuresModel(
    @SerializedName("metric")
    val metric: MetricModel
)