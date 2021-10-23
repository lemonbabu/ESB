package com.texon.engineeringsmartbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.texon.engineeringsmartbook.Model.BookAdapter;
import com.texon.engineeringsmartbook.Model.BookModel;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    private List<BookModel> movieList = new ArrayList<>();
    private BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewBookList);
        mAdapter = new BookAdapter(movieList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareMovieData();

        RecyclerView recyclerView2 = findViewById(R.id.recyclerViewBookListAccess);
        mAdapter = new BookAdapter(movieList);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(mLayoutManager);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(mAdapter);

        RecyclerView recyclerView3 = findViewById(R.id.recyclerViewBookListAccessVideo);
        mAdapter = new BookAdapter(movieList);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView3.setLayoutManager(mLayoutManager);
        recyclerView3.setItemAnimator(new DefaultItemAnimator());
        recyclerView3.setAdapter(mAdapter);



    }


    private void prepareMovieData() {
        BookModel movie = new BookModel("Mad Max: Fury Road", "Action & Adventure", "2015");
        movieList.add(movie);
        movie = new BookModel("Inside Out", "Animation, Kids & Family", "2015");
        movieList.add(movie);
        movie = new BookModel("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        movieList.add(movie);
        movie = new BookModel("Shaun the Sheep", "Animation", "2015");
        movieList.add(movie);
        movie = new BookModel("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(movie);
        movie = new BookModel("Mission: Impossible Rogue Nation", "Action", "2015");
        movieList.add(movie);
        movie = new BookModel("Up", "Animation", "2009");
        movieList.add(movie);
        movie = new BookModel("Star Trek", "Science Fiction", "2009");
        movieList.add(movie);
        movie = new BookModel("The LEGO MovieModel", "Animation", "2014");
        movieList.add(movie);
        movie = new BookModel("Iron Man", "Action & Adventure", "2008");
        movieList.add(movie);
        movie = new BookModel("Aliens", "Science Fiction", "1986");
        movieList.add(movie);
        movie = new BookModel("Chicken Run", "Animation", "2000");
        movieList.add(movie);
        movie = new BookModel("Back to the Future", "Science Fiction", "1985");
        movieList.add(movie);
        movie = new BookModel("Raiders of the Lost Ark", "Action & Adventure", "1981");
        movieList.add(movie);
        movie = new BookModel("Goldfinger", "Action & Adventure", "1965");
        movieList.add(movie);
        movie = new BookModel("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(movie);
        mAdapter.notifyDataSetChanged();
    }
}