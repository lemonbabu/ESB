package com.texon.engineeringsmartbook.ui.main.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.data.model.booksModel.TopicsContent
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class BookDashboardTopicContentAdapter (var topicsList: MutableList<TopicsContent>, private var onSubTopicClickListener: OnSubTopicClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var actionLock = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder = when (viewType) {
            TopicsContent.TOPICS -> TopicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_book_topics_item, parent, false))
            TopicsContent.SUBTOPICS -> SubTopicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_book_topics_child_item, parent, false))
            else -> TopicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_book_topics_item, parent, false))
        }
        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return topicsList[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val row = topicsList[position]
       // holder.topicNo.text = ""
        when(row.type){
            TopicsContent.TOPICS -> {
                (holder as TopicViewHolder).content.text = row.topics.name

                if(row.topics.topic.isEmpty()) {
                    (holder as TopicViewHolder).btnExpend.visibility = View.GONE
                }
                else {
                    if((holder as TopicViewHolder).btnExpend.visibility == View.GONE){
                        (holder as TopicViewHolder).btnExpend.visibility = View.VISIBLE
                    }

                    if (row.isExpanded) {
                        (holder as TopicViewHolder).btnExpend.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                    } else {
                        (holder as TopicViewHolder).btnExpend.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                    }

                    (holder as TopicViewHolder).btnExpend.setOnClickListener {
                        try{
                            if (!actionLock) {
                                actionLock = true
                                if (row.isExpanded) {
                                    row.isExpanded = false
                                    collapse(position)
                                } else {
                                    row.isExpanded = true
                                    expand(position)
                                }
                            }
                        }catch (e: Exception){
                            Log.d("Error", e.message.toString())
                        }
                    }
                }

            }
            TopicsContent.SUBTOPICS -> {
                (holder as SubTopicViewHolder).subContent.text = row.subTopic.name
                Picasso.get().load(row.subTopic.avatar).into(holder.img)
                holder.subContent.setOnClickListener {
                    onSubTopicClickListener.onSubTopicClickListener(row.subTopic.id, row.subTopic.avatar, row.subTopic.name)
                }
            }
        }


    }

    interface OnSubTopicClickListener{
        fun onSubTopicClickListener(id: Int, avatar: String, name: String)
    }

    override fun getItemCount() = topicsList.size


    class SubTopicViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var subContent: TextView = itemView.findViewById(R.id.txtSubTopicContent)
        var img: ImageView = itemView.findViewById(R.id.imgAvatar)
    }

    class TopicViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var content: TextView = itemView.findViewById(R.id.txtTopicContent)
        var btnExpend: ImageButton = itemView.findViewById(R.id.btnMore)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun expand(position: Int) {

        var nextPosition = position

        val row = topicsList[position]

        when (row.type) {

            TopicsContent.TOPICS -> {

                /**
                 * add element just below of clicked row
                 */
                for (i in row.topics.topic) {
                    Log.d("Error", i.toString())
                    topicsList.add(++nextPosition, TopicsContent(TopicsContent.SUBTOPICS, i))
                }

                notifyDataSetChanged()
            }
        }

        actionLock = false
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun collapse(position: Int) {
        val row = topicsList[position]
        val nextPosition = position + 1

        when (row.type) {

            TopicsContent.TOPICS -> {
                outerloop@ while (true) {
                    if (nextPosition == topicsList.size || topicsList[nextPosition].type == TopicsContent.TOPICS) {
                        break@outerloop
                    }
                    topicsList.removeAt(nextPosition)
                }

                notifyDataSetChanged()
            }
        }

        actionLock = false
    }


}

