package com.pomenyatnazvaniedlyagoogleplay.netflixmovies.Activity;
//Activity — это компонент приложения, который представляет собой экран с пользовательским интерфейсом и
// функционалом, и с которым пользователи могут взаимодействовать для выполнения каких-либо действий,
// например набрать номер телефона, сделать фото, отправить письмо или просмотреть карту. Каждой Activity
// соответствует окно для прорисовки соответствующего пользовательского интерфейса. Обычно окно отображается
// во весь экран, однако его размер может быть меньше, и оно может размещаться поверх других окон (в последнее
// время стало распространена технология split-screen, когда приложение занимает только часть экрана, а также
// ваше приложение может быть запущено в режиме multi-window на устройстве ChromeOS и подобных ему).
//Количество Activity в приложении может быть 1 или больше. Activity, которая запускается первой, считается
       // стартовой. Из нее можно запустить другую Activity этого же приложения, а также Activity других приложений.


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pomenyatnazvaniedlyagoogleplay.netflixmovies.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        AppCompatButton getInBtn = findViewById(R.id.getInBtn);
        getInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            }
        });

    }
}