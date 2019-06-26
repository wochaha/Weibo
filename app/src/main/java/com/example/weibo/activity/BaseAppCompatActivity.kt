package com.example.weibo.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
open class BaseAppCompatActivity : AppCompatActivity() {
    private var backPressCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }

    override fun onBackPressed() {
        backPressCount += 1
        if (backPressCount == 2){
            super.onBackPressed()
        }else{
            Toast.makeText(this,"再按一次返回键退出!", Toast.LENGTH_SHORT).show()
        }
    }
}