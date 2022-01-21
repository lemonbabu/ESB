package com.texon.engineeringsmartbook.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.data.model.booksModel.BookDashboardData


class RecentTopicsAdapter (private var onTopicsClickListener: OnTopicsClickListener) : RecyclerView.Adapter<RecentTopicsAdapter.MyViewHolder>(){

    private var topicList: List<BookDashboardData.Data.ChapterDetail.Topic> = ArrayList()

    fun submitList(list: MutableList<BookDashboardData.Data.ChapterDetail.Topic>) {
        topicList = list as List<BookDashboardData.Data.ChapterDetail.Topic>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentTopicsAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_book_topics_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecentTopicsAdapter.MyViewHolder, position: Int) {
        val item = topicList[position]
        holder.title.text = item.name
        val avatar = item.avatar
        holder.btnMore.visibility = View.GONE

        //load image into view
        Picasso.get().load(avatar).into(holder.avatar)

        holder.itemView.setOnClickListener {
           onTopicsClickListener.onTopicsClickListener(topicList[position].id.toInt())
        }
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.txtTopicContent)
        var avatar: ImageView = view.findViewById(R.id.imgVideoThus)
        var btnMore: ImageButton = view.findViewById(R.id.btnMore)
    }


    interface OnTopicsClickListener{
        fun onTopicsClickListener(id: Int)
    }


}
