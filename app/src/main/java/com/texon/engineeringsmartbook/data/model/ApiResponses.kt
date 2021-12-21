package com.texon.engineeringsmartbook.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class AppVersionResponse(
    @SerializedName("data")
    val data: VersionData,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("errors")
    val errors: String
): Serializable

data class VersionData(
    @SerializedName("app_version")
    val app_version: String
): Serializable


//Book Access Response
data class APiBookAccessResponses(
    @SerializedName("data")
    val data: BookAccess,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("errors")
    val errors: String
): Serializable

data class BookAccess(
    @SerializedName("name")
    val name: String,
    @SerializedName("youtube_id")
    val video_link: String,
    @SerializedName("description")
    val message: String,
): Serializable


// OTP sending system
data class SendOtpResponses(
    @SerializedName("data")
    val data: SendOtpData,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("errors")
    val errors: String
): Serializable

data class SendOtpData(
    @SerializedName("otp")
    val otp: String,
    @SerializedName("phone")
    val phone: String
): Serializable


// OTP verification system
data class VerifyOtpResponses(
    @SerializedName("data")
    val data: VerifyOtpData,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("errors")
    val errors: String
): Serializable


data class VerifyOtpData(
    @SerializedName("verify")
    val verify: Boolean,
    @SerializedName("otp_token")
    val token: String
): Serializable



// Password Changing system
data class ChangePasswordResponse(
    @SerializedName("data")
    val data: PasData,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("errors")
    val errors: String
): Serializable

data class PasData(
    @SerializedName("operation")
    val operation: String
): Serializable


