package com.example.appstorydicoding.story

import androidx.lifecycle.ViewModel
import com.example.appstorydicoding.StoryRepository

class LocationViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getStoryWithLocation(location: Int, token: String) =
        storyRepository.getStoryWithLocation(location, token)
}