package com.texon.engineeringsmartbook.data.model.booksModel


data class AllBooksDataModel(
    var `data`: List<Data>,
    var errors: String,
    var message: String, // OK
    var success: Boolean // true
) {
    data class Data(
        var avatar: String, // books/November2021/ib9ofFHhv8hlyxvYvmJU.png
        var created_at: String, // 2021-11-11T05:11:23.000000Z
        var deleted_at: Any, // null
        var demo_content: String, // []
        var description: Any, // null
        var discount_type: String, // none
        var discount_value: Int, // 0
        var id: Int, // 5
        var name: String, // Power System & Telecommunication
        var order: Int, // 1
        var price: Int, // 460
        var slug: String, // power-system-and-telecommunication
        var total_sale: Int, // 0
        var updated_at: String // 2021-11-11T05:16:57.000000Z
    ){
        override fun equals(other: Any?): Boolean {
            if (javaClass != other?.javaClass){
                return false
            }
            other as Data
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
            result = 31 * result + created_at.hashCode()
            result = 31 * result + deleted_at.hashCode()
            result = 31 * result + demo_content.hashCode()
            result = 31 * result + description.hashCode()
            result = 31 * result + discount_type.hashCode()
            result = 31 * result + discount_value
            result = 31 * result + id
            result = 31 * result + name.hashCode()
            result = 31 * result + order
            result = 31 * result + price
            result = 31 * result + slug.hashCode()
            result = 31 * result + total_sale
            result = 31 * result + updated_at.hashCode()
            return result
        }
    }
}