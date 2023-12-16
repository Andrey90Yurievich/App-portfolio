package com.dlyagoogleplay.insragramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dlyagoogleplay.insragramclone.Models.Post
import com.dlyagoogleplay.insragramclone.databinding.MyPostRvDesingBinding
import com.squareup.picasso.Picasso

//передаем контекст в список
class MyPostRvAdapter(var context : Context, var postList: ArrayList<Post>) : RecyclerView.Adapter<MyPostRvAdapter.ViewHolder>() {

    inner class ViewHolder(var binding : MyPostRvDesingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //создаем переменную из фрагментов
        var binding = MyPostRvDesingBinding.inflate(LayoutInflater.from(context), parent, false)
                //запихать во вьюхолдер созданный binding
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size //тут всегда вернуть размер списка
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //Bind - связывать
        Picasso.get().load(postList.get(position).postUrl).into(holder.binding.postImage)
       // Picasso.get().(из библиотеки пикасо картинку принять и вставить в шаблон)load(postList.get(position))(принять позицию).into(holder.binding.postImage) (держатель картнки)
    }
}