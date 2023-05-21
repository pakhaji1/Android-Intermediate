package com.example.appstorydicoding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.appstorydicoding.data.ApiService
import com.example.appstorydicoding.database.StoryDao
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception
import com.example.appstorydicoding.Result
import com.example.appstorydicoding.response.AddStory
import com.example.appstorydicoding.response.ListStory
import com.example.appstorydicoding.response.Login
import com.example.appstorydicoding.response.Register

class StoryRepository(
    private val storyDao: StoryDao,
    private val apiService: ApiService,
) {
    fun getStories(token: String): LiveData<Result<List<ListStory>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStories(token)
            val stories = response.listStory
            val storiesList = stories.map{story ->
                ListStory(
                    story.photoUrl,
                    story.createdAt,
                    story.name,
                    story.description,
                    story.id
                )
            }
            storyDao.addStories(storiesList)
        } catch (e: Exception) {
            Log.d("StoryRepository", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<ListStory>>> = storyDao.getStories().map { Result.Success(it) }
        emitSource(localData)
    }


    fun postLogin(email: String, pass: String): LiveData<Result<Login>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postLogin(email, pass)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Login", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postRegister(
        name: String,
        email: String,
        pass: String
    ): LiveData<Result<Register>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postRegister(name, email, pass)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("SignUp", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun uploadImage(
        file: MultipartBody.Part,
        description: RequestBody,
        token: String
    ): LiveData<Result<AddStory>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.uploadImage(file, description, token)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Login", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }
}