package com.texon.engineeringsmartbook.data.model.booksModel

import com.google.gson.annotations.SerializedName

data class BookOrderConfirmationResponse(
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
        var message: String, // Order placed successfully
        @SerializedName("order")
        var order: Order
    ) {
        data class Order(
            @SerializedName("account_no")
            var account_no: String, // 01749076238
            @SerializedName("address")
            var address: String, // {"name":"punam"}
            @SerializedName("amount")
            var amount: Int, // 450
            @SerializedName("book_id")
            var book_id: String, // 5
            @SerializedName("created_at")
            var created_at: String, // 2021-11-30T10:43:56.000000Z
            @SerializedName("id")
            var id: Int, // 8
            @SerializedName("payment_method")
            var payment_method: String, // Bkash
            @SerializedName("quantity")
            var quantity: String, // 1
            @SerializedName("status")
            var status: List<Statu>,
            @SerializedName("transaction_id")
            var transaction_id: String, // sdfadfsdfsfsfsf
            @SerializedName("updated_at")
            var updated_at: String, // 2021-11-30T10:43:56.000000Z
            @SerializedName("user_id")
            var user_id: Int // 1
        ) {
            data class Statu(
                @SerializedName("date")
                var date: String, // 2021-11-30T10:43:56.072755Z
                @SerializedName("description")
                var description: String, // Your order is pending
                @SerializedName("title")
                var title: String // Pending
            )
        }
    }
}