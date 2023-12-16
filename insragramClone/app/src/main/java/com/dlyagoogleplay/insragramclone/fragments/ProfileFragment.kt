package com.dlyagoogleplay.insragramclone.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dlyagoogleplay.insragramclone.Models.User
import com.dlyagoogleplay.insragramclone.R
import com.dlyagoogleplay.insragramclone.SignUpActivity
import com.dlyagoogleplay.insragramclone.adapters.ViewPagerAdapter
import com.dlyagoogleplay.insragramclone.databinding.FragmentProfileBinding
import com.dlyagoogleplay.insragramclone.utils.USER_NODE
import com.dlyagoogleplay.insragramclone.utils.USER_PROFILE_FOLDER
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
   private lateinit var binding : FragmentProfileBinding

   private lateinit var   viewPagerAdapter: ViewPagerAdapter //инициализирует viewPagerAdapter в этом фрашменте наследовав
   //ир есть с функциями от ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    //создание экрана фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater, container, false)


        //кнопка для редактирования профиля
        binding.editProfile.setOnClickListener { //кнопка редактировать на фрагменте портфолио
            //переключение на экран с регистрацией
            val intent = Intent(activity, SignUpActivity::class.java)
            //акже при переходе на другую активность мы можем указать какие-то данные, а принимающая
            // активность должна уметь обработать их. Для этих целей существуют методы типа putXXX().
            //Например, для передачи списка файлов из одной активности в другую:
            //Дополнения устанавливаются и читаются как объекты Bundle с использованием методов putExtras() и getExtras();
            intent.putExtra("MODE", 1)
            activity?.startActivity(intent) //Чтобы запустить новую активность, используется метод startActivity(Intent). Этот метод принимает
        // единственный параметр — объект Intent, описывающий активность, которая будет запускаться.
            activity?.finish()
        }
        viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager) //
        viewPagerAdapter.addFragments(MyPostFragment(), "My Post") //это закинуть в адаптер
        viewPagerAdapter.addFragments(MyReelsFragment(), "My Reels") //этот закинуть в адаптер
        binding.viewPager.adapter=viewPagerAdapter  //прикрутить адаптер к вью паджеру, через адаптер закидывать вьюшки
        binding.tabLayout.setupWithViewPager(binding.viewPager) //2 созданных окна вывести в экране табл лаяут

        return binding.root
    }

    companion object {

    }

    override fun onStart() {
        super.onStart()
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                // Firebase.firestore - используется для хранения наших данных в этом блоге и их извлечения в виде списка. Firebase Firestore — лучший вариант,
                // когда вы пытаетесь сохранить или получить данные, соблюдая высокие стандарты безопасности Google.
                // интегрировать Firebase в свой проект
                // .collection(USER_NODE)(создание колекции).document(Firebase.auth.currentUser!!.uid).get()
                // .addOnSuccessListener - прослушиватель для успешного завершения задачи


                //короче в карточке фрагменте портфолио отражается введенные данные имени и пароля
                val user: User = it.toObject<User>()!! //преобразование в объект по типу данных модели
                binding.name.text = user.name  //из модели поля имя и пароль присваиваются к макету имя и мыло
                binding.bio.text = user.email
                if (!user.image.isNullOrEmpty()) { //isNullOrEmpty - является нулевым или пустым
                    Picasso.get().load(user.image).into(binding.profileImage)
                }
            }
    }
}




