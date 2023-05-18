package com.example.appstorydicoding.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appstorydicoding.response.ListStory

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStories(stories: List<ListStory>)

    @Query("SELECT * FROM story")
    fun getStories(): PagingSource<Int, ListStory>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}