package com.dlyagoogleplay.insragramclone

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //сверху фиолетовая полоска станет прозрачной
        window.statusBarColor = Color.TRANSPARENT  //Объект Window — это окно верхнего уровня без границ и строки меню.
        //Color.TRANSPARENT  - полностью прозрачный цвет
                //время на загрузку
        Handler(Looper.getMainLooper()).postDelayed({//Handler - обработчик, Looper - петлитель, getMainLooper - получить главный петлитель
            //postDelayed - сообщение задержано
            if (FirebaseAuth.getInstance().currentUser==null)
                startActivity(Intent(this, SignUpActivity::class.java))
            else
                startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 3000)
    }
}