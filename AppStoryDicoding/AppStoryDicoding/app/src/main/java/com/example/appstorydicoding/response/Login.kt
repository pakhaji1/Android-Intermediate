package com.example.appstorydicoding.response

import com.google.gson.annotations.SerializedName

data class Login(
    @field:SerializedName("loginResult")
    val loginResult: User,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class User(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String
)

data class SignUp(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
