package com.example.weibo.utils

import com.example.weibo.bean.WBItem
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

fun jsonToWBItem(json: String?):WBItem{
    return if (json == null){
        WBItem()
    }else{
        gson.fromJson(json,WBItem::class.java)
    }
}