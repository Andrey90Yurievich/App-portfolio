package com.dlyagoogleplay.insragramclone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dlyagoogleplay.insragramclone.Models.Post
import com.dlyagoogleplay.insragramclone.Models.User

import com.dlyagoogleplay.insragramclone.R
import com.dlyagoogleplay.insragramclone.adapters.FollowAlapter
import com.dlyagoogleplay.insragramclone.adapters.PostAdapter
import com.dlyagoogleplay.insragramclone.databinding.FragmentHomeBinding
import com.dlyagoogleplay.insragramclone.databinding.ReelBgBinding
import com.dlyagoogleplay.insragramclone.utils.FOLLOW
import com.dlyagoogleplay.insragramclone.utils.POST
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import org.checkerframework.checker.units.qual.A

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding //получаем доступ через привязку представлений ко всем эдементам макета fragment_home

    //дря ресайкла
    private var postList = ArrayList<Post>() //создаем список постов масиив по модели пост имя описание фйди и время
    private lateinit var adapter: PostAdapter //перетасиваем адаптер и все его функции

    private var followList = ArrayList<User>()

    private lateinit var followAdapter: FollowAlapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false) //присваиваем к бандингу элементы с макета fragment_home



            //ресайкл для постов
        adapter = PostAdapter(requireContext(), postList) //Вернуть Context, с которым в данный момент связан этот фрагмент.
        binding.postRv.layoutManager = LinearLayoutManager(requireContext()) //с вертикальный LinearLayoutManager создали список постов
        binding.postRv.adapter = adapter //пихаем в ресайкл текщий вернувшийся контекст




        followAdapter = FollowAlapter(requireContext(), followList)
        binding.followRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.followRv.adapter = followAdapter


        //верхний бар с лайком и поделится
        setHasOptionsMenu(true) //setHasOptionsMenu - В наборе есть меню опци, отображение в верхнем баре лайка и поделится
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2) //materialToolbar2 - верхний бар, setSupportActionBar - установить панель,



        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
            .get().addOnSuccessListener {
                var tempList = ArrayList<User>()
                followList.clear()
                for (i in it.documents) {
                    var user: User = i.toObject<User>()!!
                    tempList.add(user)
                }
                followList.addAll(tempList)
                followAdapter.notifyDataSetChanged()

            }




        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            var tempList = ArrayList<Post>()
            postList.clear()
            for (i in it.documents) {
                var post: Post = i.toObject<Post>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()


        }
        return binding.root //вернуть привязку или привязанные элементы
    }
    companion object {
    }

    //это в заголовке в верхнем баре иконки лайк и поделится
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            inflater.inflate(R.menu.option_menu, menu) //раздувать меню с меню макетика
            super.onCreateOptionsMenu(menu, inflater)
    }
}