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
import com.texon.engineeringsmartbook.data.model.booksModel.AllBooksDataModel

class AllBooksAdapter(private var onBookClickListener: OnBookClickListener) : RecyclerView.Adapter<AllBooksAdapter.MyViewHolder>() {

    private var booksList: List<AllBooksDataModel.Data> = ArrayList()

    fun submitList(list: List<AllBooksDataModel.Data>){
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
        holder.bookPrice.text = books.price.toString()
        val avatar = books.avatar
        //load image into view
        Picasso.get().load(avatar).fit().into(holder.coverPic)

        holder.itemView.setOnClickListener {
            onBookClickListener.onBookClickListener(booksList[position])
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
        var oldBookList: List<AllBooksDataModel.Data>,
        var newBookList: List<AllBooksDataModel.Data>
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
            return oldBookList[oldItemPosition].equals(newBookList[newItemPosition])
        }

    }

    interface OnBookClickListener{
        fun onBookClickListener(result: AllBooksDataModel.Data)
    }
}

