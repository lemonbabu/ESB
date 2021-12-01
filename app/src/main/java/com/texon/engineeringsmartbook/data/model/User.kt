package com.texon.engineeringsmartbook.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class LoginData(
    @SerializedName("user")
    val user: User,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("token")
    val token: String
): Serializable


data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("avatar")
    val avatarUrl: String,
): Serializable