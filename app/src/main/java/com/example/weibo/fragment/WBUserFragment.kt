package com.example.weibo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.weibo.R
import com.example.weibo.adapter.WBFragmentStatePagerAdapter
import com.example.weibo.bean.WBUser
import com.example.weibo.listener.OnWBPageChangeListener
import com.example.weibo.listener.OnWBTabSelectedListener
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_userinfo.view.*
import kotlinx.android.synthetic.main.fragment_userinfo.view.user_tab_layout

fun Fragment.toast(content:CharSequence){
    Toast.makeText(this.context,content,Toast.LENGTH_SHORT).show()
}

class WBUserFragment : Fragment() {

    private lateinit var userName : Toolbar
    private lateinit var userImage : ImageView

//    private var userInfo:WBUser = WBUser()
//    private val token = AccessTokenKeeper.readAccessToken(WBApplication.getContext())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
//        Log.d("token",token.token)
//        Log.d("uid",token.uid)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_userinfo,container,false)

        userName = view.user_name
        userImage = view.user_image

        //解决SwipeRefreshLayout和AppBarLayout的滑动冲突问题
        view.app_bar_layout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, p1 ->
            view.refresh_layout.isEnabled = p1 >= 0
        })

        (activity as AppCompatActivity).setSupportActionBar(userName)

        val fragments = arrayListOf<Fragment>()

        for (i in 0..2){
            if (i != 1){
                fragments.add(TextViewFragment())
            }else{
                fragments.add(WBRVFragment.newIntent(WBUser()))
            }
            val tab = view.user_tab_layout.newTab()
            tab.text = "标题$i"
            view.user_tab_layout.addTab(tab)
        }

        view.fragment_view_pager.currentItem = 0

        view.fragment_view_pager.adapter =
            WBFragmentStatePagerAdapter(fragmentManager!!, fragments)

        //tab被选中是设置对应的fragment至viewpager中
        view.user_tab_layout.addOnTabSelectedListener(OnWBTabSelectedListener(view.fragment_view_pager))

        //viewpager切换fragment的时候设置想听的tab被选中
        view.fragment_view_pager.addOnPageChangeListener(OnWBPageChangeListener(view.user_tab_layout))

//        loadUserInfo(this)

        return view
    }

//    @SuppressLint("CheckResult")
//    private fun loadUserInfo(fragment: Fragment){
//        Observable.create<WBUser> {
//            //因为本身请求网络数据的业务已经指定在io线程开启所以不再使用okhttp异步请求
//            val user = getUserInfo(token.uid)
//            it.onNext(user)
//            Log.d("emit",user.toString())
//            it.onComplete()
//        }.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                if (it.id != "null"){
//                    userInfo = it
//                    //使用toolbar需要先将toolbar的参数设置好再用其替换actionbar
//                    userName.title = userInfo.name
//                    (activity as AppCompatActivity).run {
//                        setSupportActionBar(userName)
//                    }
//                    Glide.with(fragment).load(userInfo.avatarHdUrl).placeholder(R.drawable.background).into(userImage)
//                    Log.d("user",userInfo.toString())
//                }else{
//                    toast("没有获取到用户信息")
//                }
//            }, { p0 -> Log.d("FAILURE",p0.message.toString() + Thread.currentThread().name) })
//    }
}