package com.example.appstorydicoding.data

import com.example.appstorydicoding.response.AddStory
import com.example.appstorydicoding.response.Login
import com.example.appstorydicoding.response.Register
import com.example.appstorydicoding.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Register

    @FormUrlEncoded
    @POST("login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Login

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") auth: String
    ): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Header("Authorization") auth: String
    ): AddStory
}