package com.example.appstorydicoding.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appstorydicoding.response.ListStory

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStories(stories: List<ListStory>)

    @Query("SELECT * FROM story ORDER BY createdAt DESC")
    fun getStories(): LiveData<List<ListStory>>
}