package com.dlyagoogleplay.insragramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.dlyagoogleplay.insragramclone.Models.User
//import com.dlyagoogleplay.insragramclone.Models.User
import com.dlyagoogleplay.insragramclone.databinding.ActivitySignUpBinding
import com.dlyagoogleplay.insragramclone.utils.USER_NODE
import com.dlyagoogleplay.insragramclone.utils.USER_PROFILE_FOLDER
import com.dlyagoogleplay.insragramclone.utils.uploadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


//окно для регистрации
class SignUpActivity : AppCompatActivity() {
    //делаем доступ ко всем элементам макета
    val binding by lazy { //lazy - ленивый
        ActivitySignUpBinding.inflate(layoutInflater) //раздуваем этот макет бандинг
    }
    lateinit var user: User //создание экземпляра класса юзер


    //переменная для запуска
    //registerForActivityResult(компонент для регистрации)(ActivityResultContracts(тоже для регистрации).GetContent(получить контент)())
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        uri ->
        uri?.let { //let - позволить
            uploadImage(uri, USER_PROFILE_FOLDER) {
                if (it != null) { //если ничего нет, it - содержание ыункции
                    user.image = it //содержание функции изображение присвоить к изображению модели
                    binding.profileImage.setImageURI(uri) //uri - созданный экземпляр класса
                    //setImageURI - ввести это экземпляр в экран для регистрации в круглое окно
                }
            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) //запустить созданный макет

        val text = "<font color=#000000>Already Have An Account </font><font color=#1e88e5>Login ?</font>"
        binding.login.setText(Html.fromHtml(text))
        user = User() //создаем по модели экземпляр класса
        //кнопка для регистрации


        //
        if (intent.hasExtra("MODE")) { //Возвращает true, если с данным именем связано дополнительное значение.
            if (intent.getIntExtra("MODE", -1) == 1) { //getIntExtra(), аналогично другим методам, возвращающим примитивы, также позволяет вам определить значение по умолчанию.
                // Они используются, когда значение не указано ключ отсутствует в Extras.


                //если никаких данных не привязано то где кнопка регистрации вывести кнопку обновить профиль
                binding.signUpBtn.text = "Обновить профиль"
                //
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                    // Firebase.firestore - используется для хранения наших данных в этом блоге и их извлечения в виде списка. Firebase Firestore — лучший вариант,
                    // когда вы пытаетесь сохранить или получить данные, соблюдая высокие стандарты безопасности Google.
                    // интегрировать Firebase в свой проект
                    // .collection(USER_NODE)(создание колекции).document(Firebase.auth.currentUser!!.uid).get()
                    // .addOnSuccessListener - прослушиватель для успешного завершения задачи


                    //короче в карточке фрагменте портфолио отражается введенные данные имени и пароля
                    user = it.toObject<User>()!! //преобразование в объект по типу данных модели
                    if (!user.image.isNullOrEmpty()) { //isNullOrEmpty - является нулевым или пустым
                        Picasso.get().load(user.image).into(binding.profileImage) //вместить картинку в окно регистрации
                    }
                    //это чтобы в полях были указаны даннные для редактирования
                        binding.name.editText?.setText(user.name)
                        binding.email.editText?.setText(user.email)
                        binding.password.editText?.setText(user.password)

                }
            }
        }


        //кнопка регистрации в окне регистрации
        binding.signUpBtn.setOnClickListener { //binding - связующий
            if  (intent.hasExtra("MODE")) {
                if (intent.getIntExtra("MODE", -1) == 1) {
                    Firebase.firestore.collection(USER_NODE)//
                        .document(Firebase.auth.currentUser!!.uid).set(user)
                        .addOnSuccessListener { //Добавляет прослушиватель, который вызывается в случае успешного завершения задачи.
                            //при успехе перемещает на HomeActivity
                            startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                            finish()
                        }
                }
            } else {


            if (binding.name.editText?.text.toString().equals("") or //если все по нулям
                binding.email.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")
            ) { //то высветить сообщение
                Toast.makeText(this@SignUpActivity, "Заполни информацию", Toast.LENGTH_SHORT).show()
            } else { //иначе передать в регистрацию данные мыло и пароль
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                            binding.email.editText?.text.toString(),
                            binding.password.editText?.text.toString()

                ).addOnCompleteListener { //добавляет слушатель, который вызывается, когда задача завершается.
                    result ->
                    if (result.isSuccessful) { //если успешно
                        Toast.makeText(this@SignUpActivity, "Успешный вход", Toast.LENGTH_SHORT).show()
                        // сообщение успешный вход
                        //то присобачить имя мыло и пароль к экземпляру юзер
                        user.name = binding.name.editText?.text.toString()
                        user.email = binding.email.editText?.text.toString()
                        user.password = binding.password.editText?.text.toString()
                        //добавляется юзер класс в папку USER_NODE
                        Firebase.firestore.collection(USER_NODE)//
                            .document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnCompleteListener { //по завершении выведет сообщение логин
                                startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                                finish()
                            }


                    } else { //иначе если неупешно выыедет сообщение что каие то данные или палль введены неправильно на энглиш
                        Toast.makeText(this@SignUpActivity, result.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            }

        }
        //плюсик для добавления картинки
        binding.addImage.setOnClickListener {
            launcher.launch("image/*") //launch - запуск
        }

        binding.login.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }
    }
}