package com.dlyagoogleplay.insragramclone.Post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.activity.result.contract.ActivityResultContracts
import com.dlyagoogleplay.insragramclone.HomeActivity
import com.dlyagoogleplay.insragramclone.Models.Post
import com.dlyagoogleplay.insragramclone.Models.User
import com.dlyagoogleplay.insragramclone.databinding.ActivityPostBinding
import com.dlyagoogleplay.insragramclone.utils.POST
import com.dlyagoogleplay.insragramclone.utils.POST_FOLDER
import com.dlyagoogleplay.insragramclone.utils.USER_NODE
import com.dlyagoogleplay.insragramclone.utils.uploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class PostActivity : AppCompatActivity() {
    val binding by lazy { //вкручиваем бандинг для доступа ко всем элементам
        ActivityPostBinding.inflate(layoutInflater) //раздуваем активити пост
    } //binding для доступа ко всем элементам

    var imageUrl: String? = null //создаем  ссылку на картинку, и приравниваем к нуля
    private val launcher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { //регистрация
            //создаем launcher - запуск
            //registerForActivityResult() API для регистрации обратного вызова результата.
            //принимает ActivityResultContract и возвращает ActivityResultCallback и возвращает ActivityResultLauncher, который вы используете для запуска другого действия.
            //создать подкласс ActivityResultContracts.GetContent
                uri -> //универсальный идентификатор ресурса
            uri?.let { //let - позволить способ избежать проблем с null - это использовать ключевое слово let вместе с оператором ?.:
                uploadImage(
                    uri,
                    POST_FOLDER
                ) { //применяем функцию для звгрузки изображения, uri - ссылка на изображение
                    //POST_FOLDER - название константы, "PostImages" - название папки куда сохранять изображения
                        url -> //ссфлка на картинку
                    if (url != null) { //если ссылка не пустая
                        binding.selectImage.setImageURI(uri)  //в окне поста вывести картинку по ссылке
                        imageUrl = url
                    }
                }

            }
        } //регистрация

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        setSupportActionBar(binding.materialToolbar) //верхний бар
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);

        //кнопка вернуться <- в верхнем баре
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        } //кнопка вернуться <- в верхнем баре

        //клик на картинку в окне добавить пост
        binding.selectImage.setOnClickListener { //добавляем картинку в окне для картиник в макете поста
            launcher.launch("image/*") //launch - регистрация картнки в файрбаз
        }   //клик на картинку в окне добавить пост


        //кнопка отменить
        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }


        //кнопка опубликовать пост
        binding.postButton.setOnClickListener { //ссылка на кнопку опубликовать
            Firebase.firestore.collection(USER_NODE).document().get().addOnSuccessListener {

                    var user = it.toObject<User>()!! //создаем список с объектами
                    val post: Post = Post(
                        postUrl = imageUrl!!,
                        caption = binding.caption.editText?.text.toString(),
                        uid = Firebase.auth.currentUser!!.uid,
                        time = System.currentTimeMillis().toString() )
                    //передаем 2 параметра в класс Post и constructor(postUrl: String, caption:String)
                    //imageUrl!! - ссылка на картинку
                    //Если переменная относится к nullable типу, Kotlin не даст с ней работать без предварительной проверки на null.
                    // “Оператор утверждения Это не null”
                    //С помощью него можно можно преобразовать nullable-типы в стандартный тип. Он работает без какой-либо проверки на наличие в переменной null, поэтому в случае если там действительно окажется null программа выбросит исключение NullPointerException. В продуктовой разработке рекомендуется использовать крайне осторожно и только в тех местах, где есть обоснованная уверенность, что null не придет. Потому что по сути мы сознательно отказываемся от проверки на null и подвергаем риску работу программы.
                    //получаем то самое NPE исключение.
                    //binding.caption.editText?.text.toString()) - текст записи поста вв виде строки


                    //создвние коллекции паки в файрбаз
                    Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                        //collection(POST) - папка пост
                        //addOnSuccessListener - прослушиватель, который вызывается в случае успешного завершения задачи.
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document()
                            .set(post).addOnSuccessListener {
                            //Firebase.auth.currentUser!!.uid - то ли пользователь
                            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                            finish()
                        }

                    }

                } //кнопка опубликовать пост
        }
    }
}