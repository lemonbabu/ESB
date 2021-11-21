package com.texon.engineeringsmartbook.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

import com.google.gson.Gson




object RetrofitClient {
    private const val URL = "https://engineeringsmartbook.com/api/v1/"

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    //retrofit builder for User Login
    fun getUserLogin(): LoginInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(LoginInterface::class.java)
    }

    //retrofit builder for User Login
    fun getBookAccessByQRCode(): ApiInterfaces.BookAccessInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.BookAccessInterface::class.java)
    }

}