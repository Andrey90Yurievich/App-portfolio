package com.dlyagoogleplay.insragramclone.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dlyagoogleplay.insragramclone.fragments.MyPostFragment


class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    val fragmentList = mutableListOf<Fragment>() //создание списка фрагментов
    val titleList = mutableListOf<String>()

    override fun getCount(): Int {
        return fragmentList.size //вернуть размер списка , количество в списке
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position) //вернуть позицию в списке
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList.get(position) //вернуть позицию в списке
    }
    //функция для добавления фрагмента
    fun addFragments(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }
}