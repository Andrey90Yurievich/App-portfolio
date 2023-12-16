package com.dlyagoogleplay.insragramclone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dlyagoogleplay.insragramclone.Models.User
import com.dlyagoogleplay.insragramclone.R
import com.dlyagoogleplay.insragramclone.adapters.SearchAdapter
import com.dlyagoogleplay.insragramclone.databinding.FragmentSearchBinding
import com.dlyagoogleplay.insragramclone.utils.USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding //доступ ко всем элементам

    lateinit var adapter: SearchAdapter
    var userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.rv.layoutManager = LinearLayoutManager(requireContext()) //requireContext() - Вернуть Context, с которым в данный момент связан этот фрагмент.


        adapter = SearchAdapter(requireContext(), userList)
        binding.rv.adapter = adapter //через адаптре запизиваем данные в ресайкл вью


        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            //tempList - временный список
            var tempList = ArrayList<User>()
            userList.clear() //очичтить список перед его забиванием
            for (i in it.documents) { //перебираются все элементы в массиве
                //все превращаем в объекты
                if (i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())) { //если равны id пользователей и id из базы данных

                } else {
                    var user: User = i.toObject<User>()!!

                    tempList.add(user) //все объекты добавляются во временный список
                }

            }

            userList.addAll(tempList)  //все объекты с временного списка передаются в список
            adapter.notifyDataSetChanged()
        }



        //клик на кнопку лупа в поиске
        binding.searchButton.setOnClickListener() {
            var text = binding.searchView.text.toString() //текск который в окне поиска присваиваем к переменной текст
                //Firebase.firestore.collection(USER_NODE) (в базе данных ) .whereEqualTo("name", text)  (whereEqualTo - где имя равно введенному тексту)  .get()
            Firebase.firestore.collection(USER_NODE).whereEqualTo("name", text).get().addOnSuccessListener {







                var tempList = ArrayList<User>()
                userList.clear() //очичтить список перед его забиванием

                if (it.isEmpty) {

                } else {
                    for (i in it.documents) { //перебираются все элементы в массиве
                        //все превращаем в объекты
                        if (i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())) { //если равны id пользователей и id из базы данных

                        } else {
                            var user: User = i.toObject<User>()!!

                            tempList.add(user) //все объекты добавляются во временный список
                        }

                    }

                    userList.addAll(tempList)  //все объекты с временного списка передаются в список
                    adapter.notifyDataSetChanged()
                }








            }

        }

        return binding.root
    }

    companion object {


    }
}