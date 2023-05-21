package com.example.appstorydicoding.login

import androidx.lifecycle.ViewModel
import com.example.appstorydicoding.StoryRepository

class LoginViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postLogin(email: String, pass: String) = storyRepository.postLogin(email, pass)
}