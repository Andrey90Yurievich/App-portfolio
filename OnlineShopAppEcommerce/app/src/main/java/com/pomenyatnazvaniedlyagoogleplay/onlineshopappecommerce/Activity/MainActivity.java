package com.pomenyatnazvaniedlyagoogleplay.onlineshopappecommerce.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.pomenyatnazvaniedlyagoogleplay.onlineshopappecommerce.Adapter.PopularListAdapter;
import com.pomenyatnazvaniedlyagoogleplay.onlineshopappecommerce.Domain.PopularDomain;
import com.pomenyatnazvaniedlyagoogleplay.onlineshopappecommerce.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterPopular;
    private RecyclerView recyclerViewPopular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initRecyclerView();
        bottom_navigation();

    }

    private void bottom_navigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);


        //создание кнопки хз компас
        homeBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainActivity.class)));
        //создание кнопки корзина
        cartBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CartActivity.class)));
    }

    private void initRecyclerView() {//список с данными
        ArrayList<PopularDomain> items = new ArrayList<>();
        items.add(new PopularDomain("Mac Book", "\"macOS is the most advanced\n" +
                "                desktop operating system in the world.\n" +
                "                 And with macOS Sonoma, work and play\n" +
                "                  on your Mac are even more powerful —\n" +
                "                  with new ways to elevate your video\n" +
                "                  presentations, boost your gaming\n" +
                "                  performance, and personalize your\n" +
                "                  device.\"", "pic1", 15, 4, 500));
        items.add(new PopularDomain("PS-5", "\"macOS is the most advanced\n" +
                "                desktop operating system in the world.\n" +
                "                 And with macOS Sonoma, work and play\n" +
                "                  on your Mac are even more powerful —\n" +
                "                  with new ways to elevate your video\n" +
                "                  presentations, boost your gaming\n" +
                "                  performance, and personalize your\n" +
                "                  device.\"", "pic2", 10, 4.5, 450));
        items.add(new PopularDomain("Phpne 14", "\"macOS is the most advanced\n" +
                "                desktop operating system in the world.\n" +
                "                 And with macOS Sonoma, work and play\n" +
                "                  on your Mac are even more powerful —\n" +
                "                  with new ways to elevate your video\n" +
                "                  presentations, boost your gaming\n" +
                "                  performance, and personalize your\n" +
                "                  device.\"", "pic3", 13, 4.2, 800));

        recyclerViewPopular = findViewById(R.id.view1);//ссылка на ресайкл вью
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterPopular = new PopularListAdapter(items);
        recyclerViewPopular.setAdapter(adapterPopular);
    }

}