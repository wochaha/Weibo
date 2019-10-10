package com.example.weibo.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract.CommonDataKinds.Relation.TYPE_FRIEND
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.weibo.R
import com.example.weibo.WBApplication
import com.example.weibo.ui.activity.SingleFragmentActivity
import com.example.weibo.ui.adapter.WBFragmentStatePagerAdapter
import com.example.weibo.data.bean.WBUser
import com.example.weibo.ui.fragment.WBUserListFragment.Companion.TYPE_FOLLOWERS
import com.example.weibo.utils.listener.OnWBPageChangeListener
import com.example.weibo.utils.listener.OnWBTabSelectedListener
import com.example.weibo.utils.getUserInfo
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.sina.weibo.sdk.auth.AccessTokenKeeper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_userinfo.view.*
import kotlinx.android.synthetic.main.fragment_userinfo.view.user_tab_layout

fun Fragment.toast(content:CharSequence){
    Toast.makeText(this.context,content,Toast.LENGTH_SHORT).show()
}


/**
 * 用户主页
 */
class WBUserFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener {

    private lateinit var userToolBar : Toolbar
    private lateinit var userAvatar : ImageView
    private lateinit var refreshLayout:SwipeRefreshLayout
    private lateinit var userPager:ViewPager
    private lateinit var userAppBar : AppBarLayout
    private lateinit var userTab:TabLayout

    private val token = AccessTokenKeeper.readAccessToken(WBApplication.getContext())
    private val fragments = arrayListOf<Fragment>(WBItemRVFragment.newIntent(0))
    private val tabs = arrayListOf("微博","关注","粉丝")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Log.d("token","token:${token.token}  uid:${token.uid}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_userinfo,container,false)

        userToolBar = view.user_name
        userAvatar = view.user_image
        refreshLayout = view.refresh_layout
        userPager = view.fragment_view_pager
        userAppBar = view.app_bar_layout
        userTab = view.user_tab_layout

        loadUserInfo(token.uid)

        return view
    }

    private fun initView(){
        //解决SwipeRefreshLayout和AppBarLayout的滑动冲突问题
        userAppBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, p1 ->
            refreshLayout.isEnabled = p1 >= 0
        })

        refreshLayout.setOnRefreshListener(this)

        for (s in tabs){
            val tab = userTab.newTab()
            tab.text = s
            userTab.addTab(tab)
        }

        userPager.offscreenPageLimit = 0

        userPager.currentItem = 0

        userPager.adapter =
            WBFragmentStatePagerAdapter(childFragmentManager, fragments)

        //tab被选中是设置对应的fragment至viewpager中
        userTab.addOnTabSelectedListener(OnWBTabSelectedListener(userPager))

        //viewpager切换fragment的时候设置想听的tab被选中
        userPager.addOnPageChangeListener(OnWBPageChangeListener(userTab))
    }

    fun refresh(refresh:Boolean){
        refreshLayout.isRefreshing = refresh
    }

    private fun loadUserInfo(uid:String){
        refresh(true)
        Observable.create<WBUser> {
            val user = getUserInfo(uid)
            it.onNext(user)
            Log.d("emit",user.toString())
            it.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.id != "null"){
                    //使用toolbar需要先将toolbar的参数设置好再用其替换actionbar
                    userToolBar.title = it.name
                    (activity as AppCompatActivity).setSupportActionBar(userToolBar)
                    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)

                    Glide.with(this)
                        .load(it.avatarHdUrl)
                        .placeholder(R.drawable.background)
                        .into(userAvatar)

                    fragments.add(WBUserListFragment.newIntent(it.id, TYPE_FRIEND))
                    fragments.add(WBUserListFragment.newIntent(it.id, TYPE_FOLLOWERS))
                    Log.d("WBUserFragment","viewpager有${fragments.size}页")

                    initView()
                    refresh(false)
                }else{
                    toast("没有获取到用户信息")
                }
            }.isDisposed
    }

    override fun onRefresh() {
        refreshLayout.isEnabled = true
        refresh(true)
        Handler(Looper.getMainLooper()).postDelayed({
            //view.refresh_layout.isEnabled = false
            refresh(false)
        },3000)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                if (activity is SingleFragmentActivity){
                    val ac = activity as SingleFragmentActivity
                    ac.replaceFragment(ac.supportFragmentManager, WBHomePageFragment())
                    Log.d("WBHomePageFragment","fragment replace")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}