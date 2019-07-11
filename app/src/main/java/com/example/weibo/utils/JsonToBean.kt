package com.example.weibo.utils

import android.util.Log
import com.example.weibo.bean.WBUser
import com.google.gson.Gson
import org.json.JSONObject

val gson = Gson()

fun jsonToWBUser(json:String?): WBUser {
    if (json == null){
        return WBUser()
    }else{
        val wbUser = gson.fromJson(json,WBUser::class.java)
        Log.d("userInfo",wbUser.toString())
        return wbUser
    }
}