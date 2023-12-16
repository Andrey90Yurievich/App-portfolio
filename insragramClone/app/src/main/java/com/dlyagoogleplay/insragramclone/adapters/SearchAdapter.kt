package com.dlyagoogleplay.insragramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dlyagoogleplay.insragramclone.Models.Reel
import com.dlyagoogleplay.insragramclone.Models.User
import com.dlyagoogleplay.insragramclone.R
import com.dlyagoogleplay.insragramclone.databinding.FragmentSearchBinding
import com.dlyagoogleplay.insragramclone.databinding.SearchRvBinding
import com.dlyagoogleplay.insragramclone.utils.FOLLOW
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class SearchAdapter(var context: Context, var userList : ArrayList<User>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    //inner -внутренний, root - корень
    inner class ViewHolder(var binding : SearchRvBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    //здесь делаются вьюшки с макета шаблона для ресайкла во фрагмента поиска
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        var binding = SearchRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }


    //здесть привязка, то есть какие данные запихивать в ресайкл вью
    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        var isfollow  = false
        Glide.with(context)
            .load(userList.get(position))
            .placeholder(R.drawable.user) //placeholder - держатель места
            .into(holder.binding.profileImage)

        holder.binding.name.text = userList.get(position).name

                //если мыло уже есть в базе данных то выводить в кнопке что вы уже подписаны
                //если фйди пользователя равняется айди по названию мыла
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).whereEqualTo("email", userList.get(position).email).get().addOnSuccessListener {
             if (it.documents.size==0) { //если пустой
                 isfollow = false
             } else { //если не пустой то
                 holder.binding.follow.text = "Отписаться" //текст кнопки заменить на вы подписаны
                 isfollow = true
             }
        }


        //кнопка follow
        holder.binding.follow.setOnClickListener {
            if (isfollow) {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
                    .whereEqualTo("email", userList.get(position).email).get().addOnSuccessListener {
                        //при нажатии на кнопку отписаться то происходит удаление с базы данных
                   Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
                       .document(it.documents.get(0).id).delete()
                        holder.binding.follow.text = "подписаться"
                        isfollow=false
                }
            } else {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW) //добавляем в папку фолов в базе данных с узел лист объекты
                    .document().set(userList.get(position))
                //в кнопке будет запись после успешного регистрации данных в файрбаз
                holder.binding.follow.text = "Отписаться"
                isfollow = true
            }

        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }




}