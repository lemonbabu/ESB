package com.texon.engineeringsmartbook.data.model

import com.google.gson.annotations.SerializedName


data class DashboardData(
    @SerializedName("data")
    var data: Data,
    @SerializedName("errors")
    var errors: String,
    @SerializedName("message")
    var message: String, // SUCCESS
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Data(
        @SerializedName("notification")
        var notification: List<Notification>,
        @SerializedName("free_video")
        var free_video: List<DashboardBookDataModel>,
        @SerializedName("your_book")
        var your_book: List<DashboardBookDataModel>
    ) {
        data class DashboardBookDataModel(
            @SerializedName("avatar")
            var avatar: String, // https://engineeringsmartbook.com/storage
            @SerializedName("id")
            var id: Int, // 5
            @SerializedName("name")
            var name: String // Power System & Telecommunication
        ){
            override fun equals(other: Any?): Boolean {
                if (javaClass != other?.javaClass){
                    return false
                }
                other as DashboardBookDataModel
                if (id != other.id){
                    return false
                }
                if (avatar != other.avatar){
                    return false
                }
                if (name != other.name){
                    return false
                }
                return true
            }

            override fun hashCode(): Int {
                var result = avatar.hashCode()
                result = 31 * result + id
                result = 31 * result + name.hashCode()
                return result
            }
        }

//        data class FreeVideo(
//            var avatar: String, // https://engineeringsmartbook.com/storage
//            var id: Int, // 5
//            var name: String // Power System & Telecommunication
//        )

        data class Notification(
            @SerializedName("date")
            var date: String, // 2021-11-24T07:57:40.549254Z
            @SerializedName("description")
            var description: String, // Description
            @SerializedName("image")
            var image: String, // https://engineeringsmartbook.com/storage/images/notification/1.jpg
            @SerializedName("title")
            var title: String // Title
        )

//        data class YourBook(
//            var avatar: String, // https://engineeringsmartbook.com/storage
//            var id: Int, // 5
//            var name: String // Power System & Telecommunication
//        )

    }
}