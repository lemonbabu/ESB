package com.texon.engineeringsmartbook.data.api

import com.texon.engineeringsmartbook.data.model.*
import com.texon.engineeringsmartbook.data.model.booksModel.AllBooksDataModel
import com.texon.engineeringsmartbook.data.model.booksModel.BookAccessBySerial
import com.texon.engineeringsmartbook.data.model.booksModel.BookDetailsDataModel
import com.texon.engineeringsmartbook.data.model.booksModel.BookOrderConfirmationResponse
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.http.*

interface ApiInterfaces {

    interface AppVersionInterface {
        @GET(".")
        fun getVersion(): Call<AppVersionResponse>
    }

    interface BookAccessInterface {
        @FormUrlEncoded
        @POST("scan_qr")
        fun getBook(
            @Field("url") url: String,
            @Header("Authorization") authorization: String
        ): Call<APiBookAccessResponses>
    }

    interface SendOtpInterface{
        @FormUrlEncoded
        @POST("otp_send")
        fun sendOtp(
            @Field("phone") phone: String
        ): Call<SendOtpResponses>
    }

    interface ForgotPasswordInterface{
        @FormUrlEncoded
        @POST("forgot_password")
        fun sendOtp(
            @Field("phone") phone: String
        ): Call<SendOtpResponses>
    }

    interface ChangePasswordInterface{
        @FormUrlEncoded
        @POST("change_password")
        fun changePass(
            @Field("phone") phone: String,
            @Field("otp_token") token: String,
            @Field("password") pass: String,
            @Field("password_confirmation") conPass: String
        ): Call<ChangePasswordResponse>
    }

    interface OtpVerifyInterface{
        @FormUrlEncoded
        @POST("otp_verify")
        fun verifyOtp(
            @Field("phone") phone: String,
            @Field("code") code: String
        ): Call<VerifyOtpResponses>
    }

    interface UserRegistrationInterface{
        @FormUrlEncoded
        @POST("register")
        fun userReg(
            @Field("phone") phone: String,
            @Field("otp_token") token: String,
            @Field("password") pass: String,
            @Field("password_confirmation") confPass: String,
            @Field("name") name: String,
            @Field("email") email: String,
            @Field("device_name") device: String,
        ): Call<UserRegResponses>
    }

    interface SliderImageInterface{
        @GET("slider")
        fun sliderImage(): Call<SliderImageResponse>
    }


    // Dashboard Data interface
    interface DashboardDataInterface{
        @GET("dashboard")
        fun dashBoardData(
            @Header("Authorization") auth: String
        ): Call<DashboardData>
    }

    // ALl books Data interface
    interface AllBooksDataInterface{
        @GET("book")
        fun allBooksData(): Call<AllBooksDataModel>
    }

    // Book Details Data interface
    interface BooksDetailsInterface{
        @DelicateCoroutinesApi
        @GET("book/{id}")
        fun booksDetailsData(
            @Path("id") id: Int,
        ): Call<BookDetailsDataModel>
    }


    // Book Access Data interface
    interface BookAccessBySerialInterface{
        @FormUrlEncoded
        @POST("get_access")
        fun bookAccessBySerial(
            @Field("book_id") id: Int,
            @Field("serial_no") serial: Long,
            @Header("Authorization") auth: String,
        ): Call<BookAccessBySerial>
    }


    // Profile Update interface
    interface ProfileUpdateInterface{
        @FormUrlEncoded
        @POST("user/update")
        fun updateProfile(
            @Field("name") name: String,
            @Field("email") email: String,
            @Header("Authorization") auth: String,
        ): Call<UpdateProfileResponse>
    }

    // Profile Update interface
    interface PasswordUpdateInterface{
        @FormUrlEncoded
        @POST("user/update_password")
        fun updatePassword(
            @Field("current_password") pass: String,
            @Field("new_password") newPass: String,
            @Field("new_password_confirmation") conPass: String,
            @Header("Authorization") auth: String,
        ): Call<UpdatePasswordResponse>
    }


    // Book Order  interface
    interface ConfirmOrderInterface{
        @FormUrlEncoded
        @POST("order/store")
        fun bookOrder(
            @Field("book_id") bookId: Int,
            @Field("quantity") qun: Int,
            @Field("payment_method") payMethod: String,
            @Field("account_no") accountNo: String,
            @Field("transaction_id") txId: String ,
            @Field("address") address: String,
            @Header("Authorization") auth: String,
        ): Call<BookOrderConfirmationResponse>
    }


}


