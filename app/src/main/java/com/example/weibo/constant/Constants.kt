package com.example.weibo.constant

class Constants {
    companion object{
        const val APP_KEY = "859257403"
        const val REDIRECT_URL = "https://api.weibo.com/oauth2/default.html"
        const val SCOPE = "email," +
                "direct_messages_read," +
                "direct_messages_write," +
                "friendships_groups_read," +
                "friendships_groups_write," +
                "statuses_to_me_read," +
                "follow_app_official_microblog," +
                "invitation_write"
    }
}