package com.texon.engineeringsmartbook.data.model

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("data")
    var data: Data,
    @SerializedName("errors")
    var errors: String,
    @SerializedName("message")
    var message: String, // OK
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Data(
        @SerializedName("name")
        var name: String, // Access Granted
        @SerializedName("email")
        var email: String // Access Granted
    )
}