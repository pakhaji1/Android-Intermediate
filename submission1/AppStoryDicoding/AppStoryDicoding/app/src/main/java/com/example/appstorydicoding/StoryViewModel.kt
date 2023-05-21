package com.example.appstorydicoding

import androidx.lifecycle.ViewModel

class StoryViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getStory(token: String) = storyRepository.getStories(token)
}