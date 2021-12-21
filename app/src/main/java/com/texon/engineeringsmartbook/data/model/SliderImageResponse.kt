package com.texon.engineeringsmartbook.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SliderImageResponse(
    @SerializedName("data")
    val data: ArrayList<SliderData>,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("errors")
    val errors: String
): Serializable


data class SliderData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String,
): Serializable
