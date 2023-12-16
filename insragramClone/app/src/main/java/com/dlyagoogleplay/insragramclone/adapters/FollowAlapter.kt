package com.dlyagoogleplay.insragramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dlyagoogleplay.insragramclone.Models.User
import com.dlyagoogleplay.insragramclone.R
import com.dlyagoogleplay.insragramclone.databinding.FollowRvBinding
import com.dlyagoogleplay.insragramclone.databinding.SearchRvBinding

class FollowAlapter(var context: Context, var followList: ArrayList<User>) : RecyclerView.Adapter<FollowAlapter .ViewHolder>() {
    inner class ViewHolder(var binding: FollowRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = FollowRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(followList.get(position).image)
            .placeholder(R.drawable.user) //placeholder - держатель места
            .into(holder.binding.profileImage)

        holder.binding.name.text = followList.get(position).name //имя присобачить к держателю
    }

    override fun getItemCount(): Int {
        return followList.size
    }

}