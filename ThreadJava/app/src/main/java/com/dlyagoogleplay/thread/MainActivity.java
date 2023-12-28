package com.dlyagoogleplay.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean is_started = false; //переменная со значением ложь
    private int counter = 0;//переменная для счетчика
    private TextView tv; //переменная создана

    //основной поток
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //вывод на экран
        tv = findViewById(R.id.textView); //переменной присвоить textView

    }


    //второстепенный поток
    public void onClickStart(View view) {
        is_started = true;
        new Thread(new Runnable() { //создается поток
            @Override
            public void run() {
                //цикл пока
                while (is_started) { //цикл будет работать пока значение true
                    counter++; //счетчик в
                    runOnUiThread(new Runnable() { //функция которая запускает на основном потоке  все что происходит в цикле
                        @Override
                        public void run() {
                            tv.setText(String.valueOf(counter)); //значение int счетчика превратили в строку
                        }
                    });
                    try { //словить ошибку
                        //нтервал времени
                        Thread.sleep(1000); //остановит поток на 1 секунду
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }).start(); //запускается поток
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        is_started = false;
    }
}