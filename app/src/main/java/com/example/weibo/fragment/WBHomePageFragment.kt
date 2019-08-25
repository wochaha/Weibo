package com.example.weibo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        token = AccessTokenKeeper.readAccessToken(WBApplication.getContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wblist,container,false)

        val frag = WBItemRVFragment.newIntent(1)

        val manager = childFragmentManager

        val f = manager.findFragmentById(R.id.home_container)

        if (f == null){
            manager.transaction {
                add(R.id.home_container,frag,frag::class.java.name)
            }
        }

        return view
    }
}