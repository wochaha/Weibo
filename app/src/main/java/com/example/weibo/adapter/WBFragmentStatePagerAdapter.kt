package com.example.weibo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class WBFragmentStatePagerAdapter(fragmentManager: FragmentManager,list: ArrayList<Fragment>) : FragmentStatePagerAdapter(fragmentManager) {

    private val fragments  = list

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}