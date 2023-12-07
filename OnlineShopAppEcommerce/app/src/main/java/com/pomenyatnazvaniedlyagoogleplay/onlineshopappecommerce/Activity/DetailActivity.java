package com.pomenyatnazvaniedlyagoogleplay.onlineshopappecommerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pomenyatnazvaniedlyagoogleplay.onlineshopappecommerce.Domain.PopularDomain;
import com.pomenyatnazvaniedlyagoogleplay.onlineshopappecommerce.Helper.ManagementCart;
import com.pomenyatnazvaniedlyagoogleplay.onlineshopappecommerce.R;

//Определяем Активити компонент приложения, который представляет собой экран с пользовательским интерфейсом и функционалом, и с которым пользователи могут взаимодействовать для выполнения каких-либо действий, например набрать номер телефона, сделать фото, отправить письмо или просмотреть карту. Каждой Activity соответствует окно для прорисовки соответствующего пользовательского интерфейса. Обычно окно отображается во весь экран, однако его размер может быть меньше, и оно может размещаться поверх других окон (в последнее время стало распространена технология split-screen, когда приложение занимает только часть экрана, а также ваше приложение может быть запущено в режиме multi-window на устройстве ChromeOS и подобных ему).
//будем считать, что Activity - это отдельный экран приложения.
//Экран должен содержать интерфейс для взаимодействия пользователя с экраном, а также класс контроллера экрана, который содержит бизнес-логику связанную с экраном.
public class DetailActivity extends AppCompatActivity {
    private Button addToCartBtn; //поле для кнопкми покупка
    private TextView titleTxt, feeTxt, descriptionTxt, reviewTxt, scoreTxt; //поля для элементов на экране
    private ImageView picItem, backBtn; //поля для элементов типа картинки
    private PopularDomain object; //поле для объекта
    private  int  numberOrder = 1; //поле с числом 1
    private ManagementCart managmentCart; //поле для класса корзины покупок

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail); //выбор разметки макета для отображения

        managmentCart = new ManagementCart(this);
        initView(); // просмотр
        getBundle(); //
    }


    private void getBundle() {
        object = (PopularDomain) getIntent().getSerializableExtra("object");
        int drawableResourseId = this.getResources().getIdentifier(object.getPicUrl(), "drawable", this.getPackageName());

        Glide.with(this) //библиотека из которой загружаются изображения
                .load(drawableResourseId) //позиция
                .into(picItem);  //элемент макета где отображается картинка

        titleTxt.setText(object.getTitle());
        feeTxt.setText("$" + object.getPrice());
        descriptionTxt.setText(object.getDescription());
        reviewTxt.setText(object.getReview() + "");
        scoreTxt.setText(object.getScore() + "");

        addToCartBtn.setOnClickListener(v -> { //кнопка для покупки
            object.setNumberinCart(numberOrder);
            managmentCart.insertFood(object);
        });
        backBtn.setOnClickListener(v -> finish()); //кнопка для возврата

    }

    private void initView() { //функция для просмотра
        addToCartBtn = findViewById(R.id.addToCarBtn);
        feeTxt = findViewById(R.id.priceTxt);
        titleTxt = findViewById(R.id.titleTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        picItem = findViewById(R.id.itemPic);
        reviewTxt = findViewById(R.id.reviewTxt);
        scoreTxt = findViewById(R.id.scoreTxt);
        backBtn = findViewById(R.id.backBtn);
    }
}