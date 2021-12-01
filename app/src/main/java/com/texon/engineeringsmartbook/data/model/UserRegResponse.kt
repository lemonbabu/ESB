package com.texon.engineeringsmartbook.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// OTP verification system
data class UserRegResponses(
    @SerializedName("data")
    val data: RegData,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("errors")
    val errors: String
): Serializable

data class RegData(
    @SerializedName("user")
    val user: RegUserData,
    @SerializedName("token_type")
    val token_type: String,
    @SerializedName("token")
    val token: String
): Serializable

data class RegUserData(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updated_at: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("id")
    val id: Int,
): Serializable
