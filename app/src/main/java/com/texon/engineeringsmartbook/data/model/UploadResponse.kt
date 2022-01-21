package com.texon.engineeringsmartbook.data.model

data class UploadResponse(
    var `data`: Data,
    var errors: String, // []
    var message: String, // Avatar updated successfully
    var success: Boolean // false
) {
    data class Data(
        var avatar: String // https://engineeringsmartbook.com/storage/users/avatar/c8LSZpbl648tqfgNmFqK4Z6QyOnklkehsxVYzX5R.jpg
    )
}