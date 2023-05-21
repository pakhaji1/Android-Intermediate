package com.example.appstorydicoding.signup

import androidx.lifecycle.ViewModel
import com.example.appstorydicoding.StoryRepository

class SignUpViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postRegister(name: String, email: String, pass: String) =
        storyRepository.postRegister(name, email, pass)
}