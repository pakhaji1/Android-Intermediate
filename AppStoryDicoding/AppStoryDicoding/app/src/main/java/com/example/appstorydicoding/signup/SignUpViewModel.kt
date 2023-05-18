package com.example.appstorydicoding.signup

import androidx.lifecycle.ViewModel
import com.example.appstorydicoding.StoryRepository

class SignUpViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postSignUp(name: String, email: String, pass: String) =
        storyRepository.postSignUp(name, email, pass)
}