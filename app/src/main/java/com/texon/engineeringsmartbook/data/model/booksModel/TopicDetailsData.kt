package com.texon.engineeringsmartbook.data.model.booksModel

data class TopicDetailsData(
    var `data`: Data,
    var errors: String,
    var message: String, // OK
    var success: Boolean // true
) {
    data class Data(
        var description: String, // <p>Here we discuss the basic operating principle of a steam power plant by a 3-d animitted steam power plant block diagram. Also we discuss 2 important math with basic.</p>
        var name: String, // Steam Power Plant ( Basic discussion + Problem solution)
        var youtube_id: String // 846l__mntCU
    )
}