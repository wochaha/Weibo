package com.example.weibo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.weibo.data.constant.Constants
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo

class WBApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        WbSdk.install(applicationContext,
            AuthInfo(applicationContext, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE)
        )
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context
        fun getContext():Context{
            return context
        }
    }
}