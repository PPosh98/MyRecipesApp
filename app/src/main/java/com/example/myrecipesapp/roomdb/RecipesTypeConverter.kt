package com.example.myrecipesapp.roomdb

import androidx.room.TypeConverter
import com.example.myrecipesapp.model.recipes.RecipesModel
import com.example.myrecipesapp.model.recipes.ResultModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun recipesToString(recipes: RecipesModel): String = gson.toJson(recipes)

    @TypeConverter
    fun stringToRecipes(data: String): RecipesModel {
        val listType = object : TypeToken<RecipesModel>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun favouriteToString(resultModel: ResultModel): String = gson.toJson(resultModel)

    @TypeConverter
    fun stringToFavourite(data: String): ResultModel {
        val listType = object : TypeToken<ResultModel>() {}.type
        return gson.fromJson(data, listType)
    }
}