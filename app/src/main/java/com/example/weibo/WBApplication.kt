package com.example.weibo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class WBApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context
        fun getContext():Context{
            return context
        }
    }
}