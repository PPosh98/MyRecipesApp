package com.example.myrecipesapp.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myrecipesapp.model.recipes.ResultModel

@Entity(tableName = "favourites")
class FavouritesEntity(
    val resultModel: ResultModel
){
    @PrimaryKey(autoGenerate = false)
    var recipeID: Int = resultModel.id
    var recipeTitle: String = resultModel.title
    var recipeImage: String = resultModel.image
    var recipeImageType: String = resultModel.imageType
}