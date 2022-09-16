package com.example.myrecipesapp.ui.pagination

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}