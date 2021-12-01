package com.texon.engineeringsmartbook.ui.main.adapter

import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.texon.engineeringsmartbook.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.squareup.picasso.Picasso
import com.texon.engineeringsmartbook.data.model.DashboardData

class YourBooksAdapter(private var onYourBookClickListener: OnYourBookClickListener) : RecyclerView.Adapter<YourBooksAdapter.MyViewHolder>() {

    private var booksList: List<DashboardData.Data.DashboardBookDataModel> = ArrayList()

    fun submitList(list: List<DashboardData.Data.DashboardBookDataModel>){
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
            .inflate(R.layout.card_book_view, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val books = booksList[position]
        holder.bookTitle.text = books.name
        holder.bookPrice.text = ""
        val avatar = books.avatar
        //load image into view
        Picasso.get().load(avatar).fit().into(holder.coverPic)

        holder.itemView.setOnClickListener {
            onYourBookClickListener.onYourBookClickListener(booksList[position])
        }
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var bookTitle: TextView = view.findViewById(R.id.txtBookTitle)
        var bookPrice: TextView = view.findViewById(R.id.txtBookPrice)
        var coverPic: ImageView = view.findViewById(R.id.imgBookCover)
    }

    class BookDiffCallBack(
        private var oldBookList: List<DashboardData.Data.DashboardBookDataModel>,
        private var newBookList: List<DashboardData.Data.DashboardBookDataModel>
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

    interface OnYourBookClickListener{
        fun onYourBookClickListener(result: DashboardData.Data.DashboardBookDataModel)
    }

}

