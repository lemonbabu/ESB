package com.texon.engineeringsmartbook.model.data

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("data")
    val data: LoginData,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("errors")
    val errors: String
)
