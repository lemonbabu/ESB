package com.texon.engineeringsmartbook.model.api

import com.texon.engineeringsmartbook.model.data.APiBookAccessResponses
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaces {

    interface BookAccessInterface {
        @FormUrlEncoded
        @POST("scan_qr")
        fun getBook(
            @Field("url") url: String,
            @Header("Authorization") authorization: String
        ): Call<APiBookAccessResponses>
    }
}