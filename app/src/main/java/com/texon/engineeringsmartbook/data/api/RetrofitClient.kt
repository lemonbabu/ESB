package com.texon.engineeringsmartbook.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

import com.google.gson.Gson




object RetrofitClient {
    private const val URL = "https://engineeringsmartbook.com/api/v1/"

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()


    //retrofit builder for App version Checking
    fun getAppVersion(): ApiInterfaces.AppVersionInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.AppVersionInterface::class.java)
    }


    //retrofit builder for User Login
    fun getUserLogin(): LoginInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(LoginInterface::class.java)
    }

    //retrofit builder for Book Access
    fun getBookAccessByQRCode(): ApiInterfaces.BookAccessInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.BookAccessInterface::class.java)
    }


    //retrofit builder for Send Otp
    fun getSendOtp(): ApiInterfaces.SendOtpInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.SendOtpInterface::class.java)
    }

    //retrofit builder for Forgot Password
    fun getForgotOtp(): ApiInterfaces.ForgotPasswordInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.ForgotPasswordInterface::class.java)
    }

    //retrofit builder for Change Password
    fun getChangePassword(): ApiInterfaces.ChangePasswordInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.ChangePasswordInterface::class.java)
    }

    //retrofit builder for otp verification
    fun getOtpVerify(): ApiInterfaces.OtpVerifyInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.OtpVerifyInterface::class.java)
    }

    //retrofit builder for user registration
    fun getUserReg(): ApiInterfaces.UserRegistrationInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.UserRegistrationInterface::class.java)
    }

    //retrofit builder for user registration
    fun getSliderImage(): ApiInterfaces.SliderImageInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.SliderImageInterface::class.java)
    }


    //retrofit builder for Dashboard Data
    fun getDashboardData(): ApiInterfaces.DashboardDataInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.DashboardDataInterface::class.java)
    }

    //retrofit builder for All Books Data
    fun getAllBooks(): ApiInterfaces.AllBooksDataInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.AllBooksDataInterface::class.java)
    }

    //retrofit builder for Book Details Data
    fun getBookDetails(): ApiInterfaces.BooksDetailsInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.BooksDetailsInterface::class.java)
    }

    //retrofit builder for Book Access by Serial
    fun getBookAccessBySerial(): ApiInterfaces.BookAccessBySerialInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.BookAccessBySerialInterface::class.java)
    }

    //retrofit builder for Update Profile
    fun getUpdateProfile(): ApiInterfaces.ProfileUpdateInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.ProfileUpdateInterface::class.java)
    }


    //retrofit builder for Update Password
    fun getUpdatePassword(): ApiInterfaces.PasswordUpdateInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.PasswordUpdateInterface::class.java)
    }


    //retrofit builder for Book Order
    fun getConfirmOrder(): ApiInterfaces.ConfirmOrderInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.ConfirmOrderInterface::class.java)
    }

    //retrofit builder for Book Dashboard
    fun getBookDashboard(): BooksApiInterfaces.BooksDashboardInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(BooksApiInterfaces.BooksDashboardInterface::class.java)
    }

    //retrofit builder for Topic Access
    fun getTopicAccess(): BooksApiInterfaces.TopicAccessInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(BooksApiInterfaces.TopicAccessInterface::class.java)
    }


}