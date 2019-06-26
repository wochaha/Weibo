package com.example.weibo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.weibo.R

abstract class SingleFragmentActivity : AppCompatActivity() {
    protected abstract fun createFragment() : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val manager = supportFragmentManager
        var fragment = manager.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
//            获取对应的fragment
            fragment = createFragment()
            manager.beginTransaction()
                .add(R.id.fragment_container,fragment)
                .commit()
        }
    }
}
