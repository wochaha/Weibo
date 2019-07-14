package com.example.weibo.activity

import androidx.fragment.app.Fragment
import com.example.weibo.fragment.WBListFragment

class HomePageActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return WBListFragment()
    }
}
