package com.texon.engineeringsmartbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.texon.engineeringsmartbook.Model.BookAdapter;
import com.texon.engineeringsmartbook.Model.BookModel;
import com.texon.engineeringsmartbook.view.ui.auth.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    final private List<BookModel> bookList = new ArrayList<>();
    private BookAdapter mAdapter;
    ImageButton btnHome, btnProfile, btnScan, btnBack;
    LinearLayout titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnBack = findViewById(R.id.btnBackMenu);
        btnBack.setVisibility(View.INVISIBLE);
        titleBar = findViewById(R.id.titleBarHeading);
        titleBar.setVisibility(View.VISIBLE);
        btnHome = findViewById(R.id.btnHomeMenu);
        btnHome.setImageResource(R.drawable.ic_home_primary_color);
        btnProfile = findViewById(R.id.btnProfileMenu);
        btnScan = findViewById(R.id.btnScannerMenu);

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), UserProfile.class);
            startActivity(intent);
            finish();
        });

        btnScan.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PaymentMethod.class);
            startActivity(intent);
            finish();
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerViewBookList);
        mAdapter = new BookAdapter(bookList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        bookData();

        RecyclerView recyclerView2 = findViewById(R.id.recyclerViewBookListAccess);
        mAdapter = new BookAdapter(bookList);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(mLayoutManager);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(mAdapter);

        RecyclerView recyclerView3 = findViewById(R.id.recyclerViewBookListAccessVideo);
        mAdapter = new BookAdapter(bookList);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView3.setLayoutManager(mLayoutManager);
        recyclerView3.setItemAnimator(new DefaultItemAnimator());
        //recyclerView3.setAdapter(mAdapter);



    }


    private void bookData() {
        BookModel book = new BookModel("Mad Max: Fury Road",  "2015");
        bookList.add(book);
        book = new BookModel("Inside Out", "2015");
        bookList.add(book);
        book = new BookModel("Star Wars: Episode VII - The Force Awakens", "2015");
        bookList.add(book);
        book = new BookModel("Shaun the Sheep", "2015");
        bookList.add(book);
        book = new BookModel("The Martian","2015");
        bookList.add(book);
        book = new BookModel("Mission: Impossible Rogue Nation", "2015");
        bookList.add(book);
        book = new BookModel("Up",  "2009");
        bookList.add(book);
        book = new BookModel("Star Trek", "2009");
        bookList.add(book);
        book = new BookModel("The LEGO bookModel",  "2014");
        bookList.add(book);
        book = new BookModel("Iron Man",  "2008");
        bookList.add(book);
        book = new BookModel("Aliens",  "1986");
        bookList.add(book);
        book = new BookModel("Chicken Run",  "2000");
        bookList.add(book);
        book = new BookModel("Back to the Future", "1985");
        bookList.add(book);
        book = new BookModel("Raiders of the Lost Ark",  "1981");
        bookList.add(book);
        book = new BookModel("Gold  finger", "1965");
        bookList.add(book);
        book = new BookModel("Guardians of the Galaxy",  "2014");
        bookList.add(book);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure? Do you want to exit this app?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }

}