package com.texon.engineeringsmartbook.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.texon.engineeringsmartbook.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {
    private List<BookModel> booksList;
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle, bookPrice;
        MyViewHolder(View view) {
            super(view);
            bookTitle = view.findViewById(R.id.txtBookTitle);
            bookPrice = view.findViewById(R.id.txtBookPrice);
        }
    }
    public BookAdapter(List<BookModel> booksList) {
        this.booksList = booksList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_book__view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BookModel movie = booksList.get(position);
        holder.bookTitle.setText(movie.getTitle());
        holder.bookPrice.setText(movie.getPrice());
    }
    @Override
    public int getItemCount() {
        return booksList.size();
    }
}