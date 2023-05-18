package com.example.appstorydicoding

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.appstorydicoding.response.ListStory

class StoryViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getStory(token: String) : LiveData<PagingData<ListStory>> =
        storyRepository.getStories(token).cachedIn(viewModelScope)
}