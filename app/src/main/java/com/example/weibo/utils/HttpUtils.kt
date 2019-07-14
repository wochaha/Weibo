package com.example.weibo.utils

import android.util.Log
import com.example.weibo.WBApplication
import com.example.weibo.bean.WBUser
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.example.weibo.constant.APIAddress.Companion.BASE_URL
import com.example.weibo.constant.APIAddress.Companion.USER_INFO
import com.google.gson.Gson
import com.sina.weibo.sdk.auth.AccessTokenKeeper
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

fun getUserInfo(uid:String):WBUser {
    val tag = "userInfo"
    var wbUser = WBUser()

    val token = AccessTokenKeeper.readAccessToken(WBApplication.getContext())

    val urlBuilder = HttpUrl.parse(BASE_URL+ USER_INFO)?.newBuilder()

    if (urlBuilder != null){
        urlBuilder.addQueryParameter("access_token",token.token)
        urlBuilder.addQueryParameter("uid",uid)

        val urlStr = urlBuilder.build()

        val request = Request.Builder()
            .url(urlStr.toString())
            .get()
            .build()

        val client = OkHttpClient.Builder().build()

        val response = client.newCall(request).execute()

        wbUser = jsonToWBUser(response.body()?.string())
    }else{
        Log.d(tag,"url is null,request failure")
    }
    return wbUser
}