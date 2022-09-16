package com.example.myrecipesapp.model.recipeInfo


import com.google.gson.annotations.SerializedName

data class RecipeInfoModel(
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int = 0,
    @SerializedName("cheap")
    val cheap: Boolean = false,
    @SerializedName("cookingMinutes")
    val cookingMinutes: Int = 0,
    @SerializedName("creditsText")
    val creditsText: String = "",
    @SerializedName("dairyFree")
    val dairyFree: Boolean = false,
    @SerializedName("dishTypes")
    val dishTypes: List<String> = emptyList(),
    @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredientModel> = emptyList(),
    @SerializedName("gaps")
    val gaps: String = "",
    @SerializedName("glutenFree")
    val glutenFree: Boolean = false,
    @SerializedName("healthScore")
    val healthScore: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("imageType")
    val imageType: String = "",
    @SerializedName("instructions")
    val instructions: String? = "",
    @SerializedName("license")
    val license: String = "",
    @SerializedName("lowFodmap")
    val lowFodmap: Boolean = false,
    @SerializedName("preparationMinutes")
    val preparationMinutes: Int = 0,
    @SerializedName("pricePerServing")
    val pricePerServing: Double = 0.0,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int = 0,
    @SerializedName("servings")
    val servings: Int = 0,
    @SerializedName("sourceName")
    val sourceName: String = "",
    @SerializedName("sourceUrl")
    val sourceUrl: String = "",
    @SerializedName("spoonacularSourceUrl")
    val spoonacularSourceUrl: String = "",
    @SerializedName("summary")
    val summary: String = "",
    @SerializedName("sustainable")
    val sustainable: Boolean = false,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("vegan")
    val vegan: Boolean = false,
    @SerializedName("vegetarian")
    val vegetarian: Boolean = false,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean = false,
    @SerializedName("veryPopular")
    val veryPopular: Boolean = false,
    @SerializedName("weightWatcherSmartPoints")
    val weightWatcherSmartPoints: Int = 0,
)