package com.texon.engineeringsmartbook.data.api

import com.texon.engineeringsmartbook.data.model.booksModel.BookDashboardData
import com.texon.engineeringsmartbook.data.model.booksModel.TopicDetailsData
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.http.*

interface BooksApiInterfaces {

    // Book Dashboard Data interface
    interface BooksDashboardInterface{
        @DelicateCoroutinesApi
        @GET("book/{id}")
        fun booksDashboardData(
            @Path("id") id: Int,
        ): Call<BookDashboardData>
    }

    // Book Order  interface
    interface TopicAccessInterface{
        @FormUrlEncoded
        @POST("access_by_topic")
        fun topicData(
            @Field("topic_id") bookId: Int,
            @Header("Authorization") auth: String,
        ): Call<TopicDetailsData>
    }
}