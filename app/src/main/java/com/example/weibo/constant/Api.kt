package com.example.weibo.constant

class Api {
    companion object{
        //所有接口的前缀
        const val BASE_URL = "https://api.weibo.com/2/"

        //读取用户信息接口
        const val USER_INFO = "users/show.json"

        //获取用户关注列表的UID
        const val FRIENDS_LIST = "friendships/friends.json"

        //获取用户粉丝列表
        const val FOLLOWER_COUNT = "friendships/followers.json"

        const val USER_WEIBO = "statuses/user_timeline.json"

        const val HOME_WEIBO = "statuses/home_timeline.json"

        const val ITEM_COMMENTS = "comments/show.json"
    }
}