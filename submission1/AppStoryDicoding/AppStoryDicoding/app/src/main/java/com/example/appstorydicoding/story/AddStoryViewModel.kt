package com.example.appstorydicoding.story

import androidx.lifecycle.ViewModel
import com.example.appstorydicoding.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun uploadImage(
        file: MultipartBody.Part,
        description: RequestBody,
        token: String
    ) = storyRepository.uploadImage(file, description, token)
}