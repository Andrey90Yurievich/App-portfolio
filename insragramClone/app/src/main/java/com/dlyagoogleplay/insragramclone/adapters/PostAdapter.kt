package com.dlyagoogleplay.insragramclone.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dlyagoogleplay.insragramclone.Models.Post
import com.dlyagoogleplay.insragramclone.Models.User
import com.dlyagoogleplay.insragramclone.R
import com.dlyagoogleplay.insragramclone.databinding.PostRvBinding
import com.dlyagoogleplay.insragramclone.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class PostAdapter(var context : Context,var postList: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.MyHolder>() {

    inner class MyHolder(var binding: PostRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var binding = PostRvBinding.inflate(LayoutInflater.from(context),parent, false )
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try  {
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).get().addOnSuccessListener {
                var user = it.toObject<User>()
                Glide.with(context).load(user!!.image).placeholder(R.drawable.user).into(holder.binding.profileImage) //если есть изображение воткнуть в карточку есди нет то картинку профиля
                holder.binding.name.text = user.name //присвоить имя в карточке и вывести в поле карточки
            }
        } catch (e:Exception) {

        }





        //вставить в карточку картинку поста, если есть картинка то вставить картинку если нет то через плайсхолдер вставить изображение загрузки
        Glide
            .with(context)
            .load(postList.get(position).postUrl).placeholder(R.drawable.loading)
            .into(holder.binding.postImage ) //картинки из ростов вывести в карточках ресайкла постов


        //время вставляемое в карточку
        try {
            val text = TimeAgo.using(postList.get(position).time.toLong()) //Функция toLong используется для преобразования числа из других числовых типов (например, Int или Short) в тип Long. Тип Long представляет целое число со знаком, которое может принимать значения в диапазоне от -9223372036854775808 до 9223372036854775807.

            holder.binding.time.text  = text //
        } catch (e:Exception) {
            holder.binding.time.text  = "" //время в карточке
        }

        //кнопка делится
        holder.binding.share.setOnClickListener { //кнопка делится в окне
            var i =  Intent(Intent.ACTION_SEND) //двоичными данными с помощью действия ACTION_SEND
            i.type="text/plan"
            i.putExtra(Intent.EXTRA_TEXT, postList.get(position).postUrl)
            //Для передачи данных между двумя Activity используется объект Intent. Через его метод putExtra()
            // можно добавить ключ и связанное с ним значение.
            //Чтобы получить отправленные данные при загрузке SecondActivity, можно воспользоваться методом
            // get(), в который передается ключ объекта:
            context.startActivity(i)
        }


        holder.binding.caption.text  = postList.get(position).caption

        //лайк при нажатии на лайк сердечко красным
        holder.binding.like.setOnClickListener {
            holder.binding.like.setImageResource(R.drawable.heart_like) // setImageResource - установить ресурс изображения красное сердце
        }


    }

}