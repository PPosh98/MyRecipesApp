package com.example.myrecipesapp.repository

import com.example.myrecipesapp.api.FetchAPI
import com.example.myrecipesapp.model.recipeInfo.RecipeInfoModel
import com.example.myrecipesapp.model.recipes.RecipesModel
import com.example.myrecipesapp.roomdb.RecipesDAO
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response

class RepositoryImplTest{

    private lateinit var repositoryImpl: RepositoryImpl

    @Mock
    lateinit var fetchAPI: FetchAPI

    @Mock
    lateinit var recipesDAO: RecipesDAO

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        repositoryImpl = RepositoryImpl(fetchAPI, recipesDAO)
    }

    @Test
    fun getRecipes_returnsSuccess() = runBlocking{
        val expectedResult = Response.success(RecipesModel())
        whenever(fetchAPI.getRecipes("chicken")).thenReturn(expectedResult)
        val result = repositoryImpl.getRecipes("chicken")
        assertEquals(expectedResult, result)
    }

    @Test
    fun getRecipeInfo_returnsSuccess() = runBlocking{
        val expectedResult = Response.success(RecipeInfoModel())
        whenever(fetchAPI.getRecipeInfo("75557")).thenReturn(expectedResult)
        val result = repositoryImpl.getRecipeInfo("75557")
        assertEquals(expectedResult, result)
    }
}