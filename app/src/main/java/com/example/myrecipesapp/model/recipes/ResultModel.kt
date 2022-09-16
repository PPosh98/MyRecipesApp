package com.example.myrecipesapp.model.recipes


import com.google.gson.annotations.SerializedName

data class ResultModel(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("imageType")
    val imageType: String = "",
    @SerializedName("title")
    val title: String = ""
)