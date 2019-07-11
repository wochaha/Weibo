package com.example.weibo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weibo.R
import com.example.weibo.WBApplication
import com.example.weibo.bean.WBUser
import com.sina.weibo.sdk.auth.AccessTokenKeeper
import com.sina.weibo.sdk.auth.Oauth2AccessToken

class WBListFragment : Fragment() {
    private lateinit var token: Oauth2AccessToken
    private val TAG = "TOKEN"
    private  var userInfo:WBUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = AccessTokenKeeper.readAccessToken(WBApplication.getContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wblist,container,false)

        return view
    }
}