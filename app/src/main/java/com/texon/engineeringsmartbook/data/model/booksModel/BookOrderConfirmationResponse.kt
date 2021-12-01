package com.texon.engineeringsmartbook.data.model.booksModel

data class BookOrderConfirmationResponse(
    var `data`: Data,
    var errors: String,
    var message: String, // OK
    var success: Boolean // true
) {
    data class Data(
        var message: String, // Order placed successfully
        var order: Order
    ) {
        data class Order(
            var account_no: String, // 01749076238
            var address: String, // {"name":"punam"}
            var amount: Int, // 450
            var book_id: String, // 5
            var created_at: String, // 2021-11-30T10:43:56.000000Z
            var id: Int, // 8
            var payment_method: String, // Bkash
            var quantity: String, // 1
            var status: List<Statu>,
            var transaction_id: String, // sdfadfsdfsfsfsf
            var updated_at: String, // 2021-11-30T10:43:56.000000Z
            var user_id: Int // 1
        ) {
            data class Statu(
                var date: String, // 2021-11-30T10:43:56.072755Z
                var description: String, // Your order is pending
                var title: String // Pending
            )
        }
    }
}