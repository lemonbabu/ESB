package com.texon.engineeringsmartbook.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class LoginResponse(
    @SerializedName("data")
    val data: LoginData,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("errors")
    val errors: String
): Serializable
