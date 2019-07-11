package com.example.weibo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.weibo.R
import com.example.weibo.WBApplication
import com.example.weibo.bean.WBUser
import com.example.weibo.utils.*
import com.sina.weibo.sdk.auth.AccessTokenKeeper
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_userinfo.view.*

fun Fragment.toast(content:CharSequence){
    Toast.makeText(this.context,content,Toast.LENGTH_SHORT).show()
}

class WBUserFragment : Fragment() {

    private lateinit var userName : TextView
    private lateinit var userImage : CircleImageView
    private lateinit var loadingProgressBar : ProgressBar
    private lateinit var userAvatarHdImage : ImageView

    private var userInfo:WBUser = WBUser()
    private val token = AccessTokenKeeper.readAccessToken(WBApplication.getContext())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("token",token.token)
        Log.d("uid",token.uid)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_userinfo,container,false)

        userName = view.user_name
        userImage = view.user_image
        userAvatarHdImage = view.avatar_hd_image
        loadingProgressBar = view.loading_bar

        loadUserInfo(this)

        return view
    }

    @SuppressLint("CheckResult")
    private fun loadUserInfo(fragment: Fragment){
        hideView(userImage,userAvatarHdImage,userName)
        loadingProgressBar.visibility = View.VISIBLE
        Observable.create<WBUser> {
            //因为本身请求网络数据的业务已经指定在io线程开启所以不再使用okhttp异步请求
            val user = getUserInfo(token.uid)
            it.onNext(user)
            Log.d("emit",user.toString())
            it.onComplete()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.id != "null"){
                    userInfo = it
                    userName.text = userInfo.name
                    Glide.with(fragment).load(userInfo.profileImageUrl).into(userImage)
                    Log.d("avatarUrl",userInfo.avatarHdUrl)
                    Log.d("user",userInfo.toString())
                }else{
                    toast("没有获取到用户信息")
                }
            }, { p0 -> Log.d("FAILURE",p0.message.toString() + Thread.currentThread().name) })
        showView(userName,userImage,userAvatarHdImage)
        loadingProgressBar.visibility = View.GONE
    }
}