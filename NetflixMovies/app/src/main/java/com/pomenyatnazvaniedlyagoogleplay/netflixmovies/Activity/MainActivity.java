package com.pomenyatnazvaniedlyagoogleplay.netflixmovies.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pomenyatnazvaniedlyagoogleplay.netflixmovies.Adapter.FilmListAdapter;
import com.pomenyatnazvaniedlyagoogleplay.netflixmovies.Domain.ListFilm;
import com.pomenyatnazvaniedlyagoogleplay.netflixmovies.R;

    public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterNewMovies, adapterUpComming;
    private RecyclerView recyclerViewNewMovies, recyclerViewUpComming;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest, mStringRequest2;
    private ProgressBar loading1, loading2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        sendRequest1();
        sendRequest2();
    }

        private void sendRequest1() {
            mRequestQueue = Volley.newRequestQueue(this); //метод, который настраивает RequestQueue, создание экземпляра
            //2 главных класса Volley
            //Есть 2 основных класса:
            //Request queue
            //Request
            //Request queue: используется для отправки сетевых запросов, можете создавать класс request queue,
            // где хотите, но, как правило его создают во время запуска и используют как Singleton.
            //
            //Request: он содержит все необходимые детали для создания вызова Web API. Например: какой метод
            // использовать (GET или POST), данные запроса, response listener, error listener.
            loading1.setVisibility(View.VISIBLE);
            //Запросите строковый ответ по указанному URL-адресу
            mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", response -> {
                Gson gson = new Gson();
                loading1.setVisibility(View.GONE);

                ListFilm items = gson.fromJson(response, ListFilm.class);
                adapterNewMovies = new FilmListAdapter(items);
                recyclerViewNewMovies.setAdapter(adapterNewMovies);
            }, error -> {

                loading1.setVisibility(View.GONE);

            });
            mRequestQueue.add(mStringRequest);

        }
        private void sendRequest2() {
            mRequestQueue = Volley.newRequestQueue(this); //метод, который настраивает RequestQueue, создание экземпляра
            //2 главных класса Volley
            //Есть 2 основных класса:
            //Request queue
            //Request
            //Request queue: используется для отправки сетевых запросов, можете создавать класс request queue,
            // где хотите, но, как правило его создают во время запуска и используют как Singleton.
            //
            //Request: он содержит все необходимые детали для создания вызова Web API. Например: какой метод
            // использовать (GET или POST), данные запроса, response listener, error listener.
            loading2.setVisibility(View.VISIBLE);
            //Запросите строковый ответ по указанному URL-адресу
            mStringRequest2 = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=3", response -> {
                Gson gson = new Gson();
                loading2.setVisibility(View.GONE);

                ListFilm items = gson.fromJson(response, ListFilm.class);
                adapterUpComming = new FilmListAdapter(items);
                recyclerViewUpComming.setAdapter(adapterUpComming);
            }, error -> {
                loading2.setVisibility(View.GONE);
            });
            mRequestQueue.add(mStringRequest2);
        }

        private void initView() {
            recyclerViewNewMovies = findViewById(R.id.view1); //инициализировали ресайкл с название вью1 в активности майн
            recyclerViewNewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            //setLayoutManager-функция из класса библиотеки ресайкл вью, LinearLayoutManager-класс библиотека способ прокрутки горизонтальный,
            recyclerViewUpComming = findViewById(R.id.view2);
            recyclerViewUpComming.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            loading1 = findViewById(R.id.loading1);
            loading2 = findViewById(R.id.loading2);
    }
    }