package com.example.weibo.fragment

import android.os.Bundle
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

class WBListFragment : Fragment() {
    private lateinit var token: Oauth2AccessToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = AccessTokenKeeper.readAccessToken(WBApplication.getContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wblist,container,false)
        val fragments = arrayListOf<Fragment>()

        for (i in 0..1){
            fragments.add(WBRVFragment())
        }

        view.home_page_tab_layout.newTab().text = "关注"
        view.home_page_tab_layout.newTab().text = "热门"

        view.home_page_view_pager.currentItem = 0

        view.home_page_view_pager.adapter =
            WBFragmentStatePagerAdapter(fragmentManager!!, fragments)

        view.home_page_tab_layout.addOnTabSelectedListener(OnWBTabSelectedListener(view.home_page_view_pager))

        view.home_page_view_pager.addOnPageChangeListener(OnWBPageChangeListener(view.home_page_tab_layout))

        return view
    }
}