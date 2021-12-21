package com.texon.engineeringsmartbook.data.model.booksModel

import com.google.gson.annotations.SerializedName

data class BookAccessBySerial(
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
        var message: String // Access Granted
    )
}