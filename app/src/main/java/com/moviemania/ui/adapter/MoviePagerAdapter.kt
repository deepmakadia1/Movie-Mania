package com.moviemania.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*

class MoviePagerAdapter(
    fm: FragmentManager?,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fm!!, lifecycle) {
    private val mFragmentList: MutableList<Fragment> =
        ArrayList()


    fun addFrag(fragment: Fragment) {
        mFragmentList.add(fragment)
    }



    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }
}