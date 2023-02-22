package com.example.myrecipesapp.ui

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipesapp.model.favouriteRecipes.FavouritesModel
import com.example.myrecipesapp.model.recipeInfo.RecipeInfoModel
import com.example.myrecipesapp.model.recipes.RecipesModel
import com.example.myrecipesapp.model.recipes.ResultModel
import com.example.myrecipesapp.repository.Repository
import com.example.myrecipesapp.roomdb.FavouritesEntity
import com.example.myrecipesapp.roomdb.RecipesEntity
import com.example.myrecipesapp.ui.pagination.DefaultPaginator
import com.example.myrecipesapp.ui.screens.recipes.search.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository): ViewModel(){

    var screenState by mutableStateOf(ScreenState())

    private var recipesListState by mutableStateOf(RecipesModel().results)

    var recipeInfoState by mutableStateOf(RecipeInfoModel())

    var favouritesState by mutableStateOf(FavouritesModel())

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    private val paginator = DefaultPaginator(
        initialKey = screenState.page,
        onLoadUpdated = {
            screenState = screenState.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            getRecipesItems(nextPage, 10)
        },
        getNextKey = {
            screenState.page + 1
        },
        onError = {
            screenState = screenState.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            screenState = screenState.copy(
                items = screenState.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init {
        getRecipes("")
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavouritesFromDB().collect { favouriteEntity ->
                favouriteEntity.forEach {
                    val favouriteRecipe = ResultModel(
                        id = it.recipeID,
                        image = it.recipeImage,
                        imageType = it.recipeImageType,
                        title = it.recipeTitle
                    )
                    if (favouriteEntity != null && !favouritesState.results.contains(favouriteRecipe)) {
                        favouritesState.results.add(
                            favouriteRecipe
                        )
                    }
                }
            }
        }
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun getRecipes(query: String) {
        screenState.apply {
            endReached = false
            isLoading = false
            items = emptyList()
            error = null
            page = 0
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getRecipesDB(query).collect { recipesEntity ->
                    if (recipesEntity != null) {
                        recipesListState = recipesEntity.recipesModel.results
                        loadNextItems()
                    } else {
                        val response = repository.getRecipes(query)
                        if (response.isSuccessful){
                            response.body()?.let {
                                recipesListState = it.results
                                addRecipesToDB(it, query)
                                loadNextItems()
                            }
                        }
                    }
                }
            } catch (ex: Exception) {
            }
        }
    }

    private suspend fun getRecipesItems(page: Int, pageSize: Int): Result<List<ResultModel>> {
        delay(2000L)
        val startingIndex = page * pageSize
        return if(startingIndex + pageSize <= recipesListState.size) {
            Result.success(
                recipesListState.slice(startingIndex until startingIndex + pageSize)
            )
        } else if (recipesListState.isNotEmpty() && recipesListState.size < startingIndex + pageSize) {
            val remainder = recipesListState.size - startingIndex
            Result.success(
                recipesListState.slice(startingIndex until startingIndex + remainder)
            )
        } else Result.success(emptyList())
    }

    fun getRecipeInfo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getRecipeInfo(id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        recipeInfoState = it
                    }
                }
            } catch (ex: Exception) {
            }
        }
    }

    fun handleFavourite(id: Int) : Boolean {
        val favouriteRecipe = recipesListState.find { it.id == id }
        //check if recipe is a favourite
        val isFavourite = isFavourite(id)

        if (!isFavourite && favouriteRecipe != null){
            //if not favourite already, add to favourites
            favouritesState.results.add(favouriteRecipe)
            favouritesState = favouritesState.copy(count = favouritesState.results.size)
            addFavouriteToDB(favouriteRecipe)
            return false
        } else {
            //if favourite already, remove from favourites
            val recipeToRemove = favouritesState.results.find { it.id == id }
            favouritesState.results.removeIf { it.id == id }
            favouritesState = favouritesState.copy(count = favouritesState.results.size)
            if (recipeToRemove != null) {
                removeFavouriteFromDB(recipeToRemove)
            }
            return true
        }
    }

    fun isFavourite(id: Int): Boolean {
        val favouriteRecipe = recipesListState.find { it.id == id }
        return favouritesState.results.contains(favouriteRecipe)
    }

    private fun addRecipesToDB(recipesModel: RecipesModel, recipeQuery: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addRecipesToDB(RecipesEntity(recipesModel, recipeQuery))
        }
    }

    private fun addFavouriteToDB(recipe: ResultModel) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addFavouriteToDB(FavouritesEntity(recipe))
        }
    }

    private fun removeFavouriteFromDB(recipe: ResultModel) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeFavouriteFromDB(recipe.id)
        }
    }
}