package com.example.weibo.utils

import android.util.Log
import android.view.View

fun hideView(vararg views:View){
    for (view in views){
        view.visibility = View.GONE
    }
    Log.d("hide","all views hide")
}

fun showView(vararg views:View){
    for (view in views){
        view.visibility = View.VISIBLE
    }
    Log.d("show","all views show")
}

