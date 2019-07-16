package com.example.weibo.bean

//微博item的超类
class WBItem {
    lateinit var mPhone:String
    lateinit var mUser:WBUser
    lateinit var mContent:String
    lateinit var mTime:String
    lateinit var mCommCounts:String
    lateinit var mThumbsCounts:String

    constructor()

    constructor(user:WBUser,content:String,time:String,commCounts:Int,thumbsCounts:Int,phone:String){
        mUser = user
        mContent = content
        mCommCounts = when {
            commCounts >= 10000 -> "${commCounts/10000}万"
            commCounts in 1..9999 -> commCounts.toString()
            else -> "评论"
        }
        mThumbsCounts = when {
            thumbsCounts >= 10000 -> "${thumbsCounts/10000}万"
            thumbsCounts in 1..9999 -> thumbsCounts.toString()
            else -> "点赞"
        }
        mTime = time
        mPhone = phone
    }
}