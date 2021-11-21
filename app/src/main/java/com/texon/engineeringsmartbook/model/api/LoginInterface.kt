package com.texon.engineeringsmartbook.model.api

import com.texon.engineeringsmartbook.model.data.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginInterface {
    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("device_name") device_name: String
    ):Call<LoginResponse>
}


