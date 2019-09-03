package com.example.weibo.bean

import com.google.gson.annotations.SerializedName
import com.sina.weibo.sdk.network.base.*
import com.sina.weibo.sdk.utils.WbUtils

class WBComment {
    @SerializedName("created_at")
    var createTime:String = ""

    @SerializedName("idstr")
    var idStr:String = "null"

    @SerializedName("user")
    var user:WBUser = WBUser()

    @SerializedName("text")
    var content:String = ""

    @SerializedName("status")
    var status:WBItem = WBItem()

    constructor()

    constructor(id:String,content:String,statusId:String){
        idStr = id
        this.content = content
        status.statusIdStr = statusId
    }
}