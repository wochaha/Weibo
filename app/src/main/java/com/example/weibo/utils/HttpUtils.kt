package com.example.weibo.utils

import android.content.Context
import android.nfc.Tag
import android.util.Log
import com.example.weibo.WBApplication
import com.example.weibo.bean.WBItem
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

//获取uid用户的关注或是粉丝列表的uid
fun getUserList(api:String,uid: String):ArrayList<WBUser>{
    val users = arrayListOf<WBUser>()

    val token = AccessTokenKeeper.readAccessToken(WBApplication.getContext())
    val urlBuilder = HttpUrl.parse(BASE_URL+ api)?.newBuilder()

    if (urlBuilder != null){
        urlBuilder.addQueryParameter("access_token",token.token)
        urlBuilder.addQueryParameter("uid",uid)
        urlBuilder.addQueryParameter("trim_status",0.toString())

        val urlStr = urlBuilder.build()

        val request = Request.Builder()
            .url(urlStr.toString())
            .get()
            .build()

        val client = OkHttpClient.Builder().build()

        val response = client.newCall(request).execute()

        val json = response.body()?.string()

        if (json == null){
            Log.d("friendList","请求数据失败!!!")
        }else{
            val jsonObject = JSONObject(json)
            val jsonArray = jsonObject.getJSONArray("users")
            Log.d("friendList","array length: ${jsonArray.length()} values:$jsonArray")
            for (i in 0 until jsonArray.length()){
                val jsonOb = jsonArray.getJSONObject(i)
                val ob = jsonOb.getString("status")
                val user = jsonToWBUser(jsonOb.toString())
                user.recentStatusContent = JSONObject(ob).getString("text")
                users.add(user)
            }
        }
    }else{
        Log.d("uids","url is null,request failure")
    }
    return users
}

/**
 * 获取微博
 * @param api 分两个接口，分别获取当前用户和当前用户关注的用户的微博
 * @return 返回的是处理好后的item组成的list
 */
fun getWBItemList(api:String):ArrayList<WBItem>{
    val list = arrayListOf<WBItem>()

    val token = AccessTokenKeeper.readAccessToken(WBApplication.getContext())
    val urlBuilder = HttpUrl.parse(BASE_URL+ api)?.newBuilder()

    if (urlBuilder != null){
        urlBuilder.addQueryParameter("access_token",token.token)

        val urlStr = urlBuilder.build()

        val request = Request.Builder()
            .url(urlStr.toString())
            .get()
            .build()

        val client = OkHttpClient.Builder().build()

        val response = client.newCall(request).execute()

        val json = response.body()?.string()

        if (json == null){
            Log.d("friendList","请求数据失败!!!")
        }else{
            val jsonObject = JSONObject(json)
            val jsonArray = jsonObject.getJSONArray("statuses")
            for (i in 0 until jsonArray.length()){
                val ob = jsonArray.getJSONObject(i)
                val item = jsonToWBItem(ob.toString())
                list.add(item)
            }
        }
    }else{
        Log.d("uids","url is null,request failure")
    }
    return list
}