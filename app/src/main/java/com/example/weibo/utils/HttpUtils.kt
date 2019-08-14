package com.example.weibo.utils

import android.util.Log
import com.example.weibo.WBApplication
import com.example.weibo.bean.WBUser
import com.example.weibo.constant.Api.Companion.BASE_URL
import com.example.weibo.constant.Api.Companion.FRIENDS_LIST
import com.example.weibo.constant.Api.Companion.USER_INFO
import com.google.gson.JsonArray
import com.sina.weibo.sdk.auth.AccessTokenKeeper
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject

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

//获取uid用户的关注或是粉丝列表的uid
fun getUserListByUids(api:String,uid: String):ArrayList<Long>{
    val token = AccessTokenKeeper.readAccessToken(WBApplication.getContext())
    val urlBuilder = HttpUrl.parse(BASE_URL+ api)?.newBuilder()

    val list = arrayListOf<Long>()
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

        val json = response.body()?.string()
        if (json == null){

        }else{
            val jsonObject = JSONObject(json)
            val jsonArray = jsonObject.getJSONArray("ids")
            for (i in 0..jsonArray.length()){
                list.add(jsonArray.getLong(i))
            }
        }
    }else{
        Log.d("uids","url is null,request failure")
    }
    return list
}