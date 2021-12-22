package com.texon.engineeringsmartbook.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.data.model.booksModel.BookDashboardData
import com.texon.engineeringsmartbook.data.model.booksModel.BookDetailsDataModel

class ChildAdapter (private var onBookClickListener: OnBookClickListener) : RecyclerView.Adapter<ChildAdapter.MyViewHolder>() {

    private var booksList: List<BookDashboardData.Data.ChapterDetail.Topic> = ArrayList()

    fun submitList(list: List<BookDashboardData.Data.ChapterDetail.Topic>){
        val oldList = booksList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            BookDiffCallBack(
                oldList,
                list
            )
        )
        booksList = list
        diffResult.dispatchUpdatesTo(this)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_book_topics_child_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val books = booksList[position]
       // holder.topicNo.text = ""
        holder.content.text = books.name

        //load image into view
        Picasso.get().load(books.avatar).fit().into(holder.avatar)

        holder.itemView.setOnClickListener {
            onBookClickListener.onBookClickListener(booksList[position])
        }
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //var topicNo: TextView = view.findViewById(R.id.txtTopicNo)
        var content: TextView = view.findViewById(R.id.txtTopicContent)
        var avatar: ImageView = view.findViewById(R.id.imgVideoThus)
    }

    class BookDiffCallBack(
        var oldBookList: List<BookDashboardData.Data.ChapterDetail.Topic>,
        var newBookList: List<BookDashboardData.Data.ChapterDetail.Topic>
    ): DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldBookList.size
        }

        override fun getNewListSize(): Int {
            return newBookList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldBookList[oldItemPosition].id == newBookList[newItemPosition].id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldBookList[oldItemPosition] == newBookList[newItemPosition]
        }

    }

    interface OnBookClickListener{
        fun onBookClickListener(result: BookDashboardData.Data.ChapterDetail.Topic)
    }
}

