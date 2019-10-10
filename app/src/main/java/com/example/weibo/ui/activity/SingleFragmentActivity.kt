package com.example.weibo.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.transaction
import com.bumptech.glide.Glide
import com.example.weibo.R
import com.example.weibo.WBApplication
import com.example.weibo.data.bean.WBUser
import com.example.weibo.ui.fragment.WBHomePageFragment
import com.example.weibo.ui.fragment.WBUserFragment
import com.example.weibo.utils.getUserInfo
import com.google.android.material.navigation.NavigationView
import com.sina.weibo.sdk.auth.AccessTokenKeeper
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.nav_headers.view.*

abstract class SingleFragmentActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    /**
     * @return 返回创建的fragment，在子类activity启动的时候使用该fragment
     */
    protected abstract fun createFragment() : Fragment

    private val token:Oauth2AccessToken = AccessTokenKeeper.readAccessToken(WBApplication.getContext())

    private lateinit var currentFragment:Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_home_page)

        loadUserInfo(token.uid)

        addFragment()

        nav_menu.setCheckedItem(R.id.home_page)
        nav_menu.getHeaderView(0).user_head_portrait.setOnClickListener {
            drawer_menu_layout.closeDrawers()
            replaceFragment(supportFragmentManager, WBUserFragment())
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
                //addToBackStack(null)
                currentFragment = fragment
            }
        }else{
            replaceFragment(manager,createFragment())
        }
    }

    /**
     * @param fragment 需要显示的fragment
     */
    fun replaceFragment(manager: FragmentManager,fragment: Fragment){
        if (!fragment.isAdded){
            manager.transaction {
                hide(currentFragment)
                add(R.id.weibo_content_container, fragment, fragment::class.java.name)
            }
        }else{
            if(currentFragment != fragment){
                manager.transaction {
                    hide(currentFragment)
                    show(fragment)
                }
            }
        }
        Log.d("REPLACE",fragment::class.java.name)
        currentFragment = fragment
    }

    //选项的启动逻辑
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.home_page -> {
                drawer_menu_layout.closeDrawers()
                if (currentFragment !is WBHomePageFragment){
                    replaceFragment(supportFragmentManager, WBHomePageFragment())
                }
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                drawer_menu_layout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadUserInfo(uid:String){
        Observable.create<WBUser> {
            val user = getUserInfo(uid)
            it.onNext(user)
            it.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                    if (it.id != "null"){
                        Glide.with(this).asBitmap().load(it.profileImageUrl).into(nav_menu.getHeaderView(0).user_head_portrait)
                        nav_menu.getHeaderView(0).user_nick_name.text = it.screenName
                        nav_menu.getHeaderView(0).friends_count.text = "关注:${it.friendsCount}"
                        nav_menu.getHeaderView(0).followers_count.text = "关注:${it.followersCount}"
                    }else{
                        Toast.makeText(this,"数据加载失败",Toast.LENGTH_SHORT).show()
                    }
            }.isDisposed
    }


}
