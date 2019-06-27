package com.example.weibo.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.weibo.R
import com.example.weibo.fragment.WBListFragment

class HomePageActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return WBListFragment()
    }
}
