package com.example.appstorydicoding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.example.appstorydicoding.data.ApiService
import com.example.appstorydicoding.database.StoryDatabase
import com.example.appstorydicoding.remote.StoryRemoteMediator
import com.example.appstorydicoding.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository(
    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService)
{
    fun getStories(token: String): LiveData<PagingData<ListStory>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, token),
            pagingSourceFactory = {
                storyDatabase.storyDao().getStories()
            }
        ).liveData
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

    fun postSignUp(
        name: String,
        email: String,
        pass: String
    ): LiveData<Result<SignUp>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postRegister(name, email, pass)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getStoryWithLocation(location: Int, token: String): LiveData<Result<StoryResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.getStoriesWithLocation(location, token)
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d("Signup", e.message.toString())
                emit(Result.Error(e.message.toString()))
            }
        }

    fun uploadImage(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: Double,
        lon: Double,
    ): LiveData<Result<AddStory>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.uploadImage(token,file, description, lat, lon)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }
}