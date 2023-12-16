package com.dlyagoogleplay.insragramclone.Post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dlyagoogleplay.insragramclone.HomeActivity
import com.dlyagoogleplay.insragramclone.Models.Reel
import com.dlyagoogleplay.insragramclone.Models.User
import com.dlyagoogleplay.insragramclone.databinding.ActivityReelsBinding
import com.dlyagoogleplay.insragramclone.utils.REEL
import com.dlyagoogleplay.insragramclone.utils.REEL_FOLDER
import com.dlyagoogleplay.insragramclone.utils.USER_NODE
import com.dlyagoogleplay.insragramclone.utils.uploadVideo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class ReelsActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }


    private lateinit var videoUrl : String

    lateinit var progressDialog : ProgressDialog

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { //регистрация
        //создаем launcher - запуск
        //registerForActivityResult() API для регистрации обратного вызова результата.
        //принимает ActivityResultContract и возвращает ActivityResultCallback и возвращает ActivityResultLauncher, который вы используете для запуска другого действия.
        //создать подкласс ActivityResultContracts.GetContent
            uri -> //универсальный идентификатор ресурса
        uri?.let { //let - позволить способ избежать проблем с null - это использовать ключевое слово let вместе с оператором ?.:
            uploadVideo(uri, REEL_FOLDER, progressDialog) { //
                //POST_FOLDER - название константы, "PostImages" - название папки куда сохранять изображения
                    url -> //ссфлка на картинку
                if (url != null) { //если ссылка не пустая

                    videoUrl = url
                }
            }

        }
    } //регистрация

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

            progressDialog=ProgressDialog(this)

        //кнопка добавить видео
        binding.selectReel.setOnClickListener { //добавляем видео в окне для картиник в макете поста
            launcher.launch("video/*") //launch - регистрация картнки в файрбаз
        }   //кнопка добавить видео

        //кнопка отменить
        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }




        // нопка опубликовать пост
        binding.postButton.setOnClickListener { //ссылка на кнопку опубликовать
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var user : User = it.toObject<User>()!!

                val reel: Reel = Reel(videoUrl!!, binding.caption.editText?.text.toString(),user.image!!)
                //binding.caption.editText?.text.toString()) - текст записи поста в виде строки

                //создвние коллекции паки в файрбаз
                Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).document().set(reel)
                        .addOnSuccessListener {  //addOnSuccessListener - прослушиватель, который вызывается в случае успешного завершения задачи.
                            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                            finish()
                        }
                }

            }

        }
    }
}