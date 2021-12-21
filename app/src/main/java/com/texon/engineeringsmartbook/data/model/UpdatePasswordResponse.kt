package com.texon.engineeringsmartbook.data.model

import com.google.gson.annotations.SerializedName

data class UpdatePasswordResponse(
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
        @SerializedName("message")
        var message: String, // Access Granted
    )
}