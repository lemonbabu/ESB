package com.texon.engineeringsmartbook.data.model.booksModel

import androidx.annotation.IntDef

class TopicsContent {

    companion object{
        @IntDef(TOPICS, SUBTOPICS)
        @Retention(AnnotationRetention.SOURCE)
        annotation class RowType

        const val TOPICS = 1
        const val SUBTOPICS = 2
    }
    @RowType var type : Int

    lateinit var topics : BookDashboardData.Data.ChapterDetail

    lateinit var subTopic: BookDashboardData.Data.ChapterDetail.Topic

    var isExpanded : Boolean

    constructor(@RowType type : Int, topic : BookDashboardData.Data.ChapterDetail, isExpanded : Boolean = false){
        this.type = type
        this.topics = topic
        this.isExpanded = isExpanded
    }

    constructor(@RowType type : Int, subTopic : BookDashboardData.Data.ChapterDetail.Topic, isExpanded : Boolean = false){
        this.type = type
        this.subTopic = subTopic
        this.isExpanded = isExpanded
    }

}
