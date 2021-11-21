package com.texon.engineeringsmartbook.model.data

import com.google.gson.annotations.SerializedName

data class APiBookAccessResponses(
    @SerializedName("data")
    val data: BookAccess,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("errors")
    val errors: String
)


data class BookAccess(
    @SerializedName("name")
    val name: String,
    @SerializedName("youtube_id")
    val video_link: String,
    @SerializedName("description")
    val message: String,
)
