package com.example.weibo.activity

import androidx.fragment.app.Fragment
import com.example.weibo.fragment.WBHomePageFragment

/**
 * 微博首页
 * @author koala.k
 */
class HomePageActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return WBHomePageFragment()
    }
}
