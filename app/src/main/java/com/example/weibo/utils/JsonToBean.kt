package com.example.weibo.utils

import com.google.gson.Gson

val gson = Gson()

inline fun <reified T> jsonToBean(json: String?):T{
    return if (json == null){
        val result = T::class.java
        val cons = result.getConstructor()
        cons.newInstance()
    }else{
        gson.fromJson(json,T::class.java)
    }
}