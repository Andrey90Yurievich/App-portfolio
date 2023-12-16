package com.dlyagoogleplay.insragramclone.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dlyagoogleplay.insragramclone.databinding.FragmentAddBinding
import com.dlyagoogleplay.insragramclone.Post.PostActivity
import com.dlyagoogleplay.insragramclone.Post.ReelsActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddBinding.inflate(inflater, container, false)


                //нажатие на пост
            binding.post.setOnClickListener {
                activity?.startActivity(Intent(requireContext(), PostActivity::class.java)) //requireContext - Верните тот Contextфрагмент, с которым в данный момент связан этот фрагмент.
                activity?.finish()
            }
                //нажатие на видео
            binding.reel.setOnClickListener {
                activity?.startActivity(Intent(requireContext(), ReelsActivity::class.java))
            }

        return binding.root
    }

    companion object {




    }
}