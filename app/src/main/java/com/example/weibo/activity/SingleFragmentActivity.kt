package com.example.weibo.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.transaction
import com.example.weibo.R
import com.example.weibo.WBApplication
import com.example.weibo.fragment.WBUserFragment
import com.google.android.material.navigation.NavigationView
import com.sina.weibo.sdk.auth.AccessTokenKeeper
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.nav_headers.view.*
import java.util.*

abstract class SingleFragmentActivity : BaseAppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    /**
     * @return 返回创建的fragment，在子类activity启动的时候使用该fragment
     */
    protected abstract fun createFragment() : Fragment

    private var token:Oauth2AccessToken? = null

    private var currentFragment:Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        token = AccessTokenKeeper.readAccessToken(WBApplication.getContext())

        addFragment()

        nav_menu.setCheckedItem(R.id.home_page)
        nav_menu.getHeaderView(0).user_head_portrait.setOnClickListener {
            replaceFragment(supportFragmentManager,WBUserFragment())
            drawer_menu_layout.closeDrawers()
        }
        nav_menu.setNavigationItemSelectedListener(this)
    }

    //子类activity创建时调用添加fragment
    private fun addFragment(){
        val manager = supportFragmentManager
        var fragment:Fragment? = manager.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
//            获取对应的fragment
            fragment = createFragment()

            manager.transaction {
                add(R.id.weibo_content_container,fragment,fragment::class.java.name)
                currentFragment = fragment
            }
        }
    }

    /**
     * @param fragment 需要显示的fragment
     */
    fun replaceFragment(manager: FragmentManager,fragment: Fragment){
        if (!fragment.isAdded){
            if (currentFragment != null){
                manager.transaction {
                    hide(currentFragment!!)
                }
            }
            manager.transaction {
                add(R.id.weibo_content_container, fragment, fragment::class.java.name)
            }
        }else{
            manager.transaction {
                hide(currentFragment!!)
                show(fragment)
            }
        }
        currentFragment = fragment
    }

    //选项的启动逻辑
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.home_page -> {
                if (currentFragment !is WBUserFragment){
                    replaceFragment(supportFragmentManager,WBUserFragment())
                }
            }
        }
        drawer_menu_layout.closeDrawers()
        return true
    }
}
