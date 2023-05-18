package com.example.appstorydicoding.data

import android.content.Context
import com.example.appstorydicoding.StoryRepository
import com.example.appstorydicoding.database.StoryDatabase

object Injection {
    fun provideRepository(context: Context) : StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService)
    }
}