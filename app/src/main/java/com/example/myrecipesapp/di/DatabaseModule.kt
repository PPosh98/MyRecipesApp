package com.example.myrecipesapp.di

import android.content.Context
import androidx.room.Room
import com.example.myrecipesapp.roomdb.RecipesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        "RecipesDatabase"
    ).build()

    @Provides
    fun provideUsersDao(database: RecipesDatabase) = database.recipesDAO()
}