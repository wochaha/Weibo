package com.example.weibo.utils

import com.example.weibo.bean.WBUser
import com.google.gson.Gson

val gson = Gson()

fun jsonToWBUser(json:String?): WBUser {
    return if (json == null){
        WBUser()
    }else{
        gson.fromJson(json,WBUser::class.java)
    }
}