package com.example.weibo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.weibo.R
import com.example.weibo.WBApplication
import com.example.weibo.adapter.WBFragmentStatePagerAdapter
import com.example.weibo.listener.OnWBPageChangeListener
import com.example.weibo.listener.OnWBTabSelectedListener
import com.google.android.material.tabs.TabLayout
import com.sina.weibo.sdk.auth.AccessTokenKeeper
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import kotlinx.android.synthetic.main.fragment_wblist.view.*

class WBHomePageFragment : Fragment() {

    private lateinit var token: Oauth2AccessToken
    val fragments = arrayListOf<Fragment>(WBItemRVFragment(),WBItemRVFragment())
    private lateinit var tablayout:TabLayout
    private lateinit var viewPager:ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = AccessTokenKeeper.readAccessToken(WBApplication.getContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wblist,container,false)

        tablayout = view.home_page_tab_layout
        viewPager = view.home_page_view_pager

        initView()

        return view
    }

    private fun initView(){
        tablayout.newTab().text = "关注"
        tablayout.newTab().text = "热门"

        viewPager.currentItem = 0

        viewPager.adapter =
            WBFragmentStatePagerAdapter(fragmentManager!!, fragments)

        tablayout.addOnTabSelectedListener(OnWBTabSelectedListener(viewPager))

        viewPager.addOnPageChangeListener(OnWBPageChangeListener(tablayout))
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden){
            initView()
        }
    }

}