package com.example.weibo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weibo.R
import com.example.weibo.WBApplication
import com.example.weibo.adapter.WBUserListRVAdapter
import com.example.weibo.bean.WBUser
import com.example.weibo.constant.Api
import com.example.weibo.utils.getUserInfo
import com.example.weibo.utils.getUserListByUids
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AccessTokenKeeper
import com.sina.weibo.sdk.net.WeiboParameters
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_user_list.view.*
import kotlin.collections.ArrayList

/**
 * 微博登录用户关注列表
 */
class WBUserListFragment : Fragment() {
    private val token = AccessTokenKeeper.readAccessToken(WBApplication.getContext())
    private val ids = arrayListOf<Long>()
    private val users = arrayListOf<WBUser>()
    private lateinit var adapter:WBUserListRVAdapter
    private lateinit var uid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uid = arguments?.getString("uid").toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_list,container,false)
        loadUserList(Api.FRIENDS_LIST,uid,ids)
        view.user_list.layoutManager = LinearLayoutManager(this.context)
        view.user_list.adapter = adapter
        refresh(false)
        return view
    }

    private fun loadUserList(url:String,uid: String,ids:ArrayList<Long>){
        refresh(true)
        //获取所有好友id
        Observable.create<List<Long>> {
            val list = getUserListByUids(Api.BASE_URL+url,uid)
            it.onNext(list)
            it.onComplete()
        }.observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()){
                    ids.addAll(it)
                }
            }.isDisposed

        if (ids.isNotEmpty()){
            Observable.create<WBUser> {
                for (id in ids){
                    val user = getUserInfo(id.toString())
                    it.onNext(user)
                }
                it.onComplete()
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<WBUser>{
                    var disposable:Disposable? = null
                    override fun onComplete() {
                        if (users.isNotEmpty()){
                            adapter = WBUserListRVAdapter(users)
                        }
                        disposable?.dispose()
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }

                    override fun onNext(t: WBUser) {
                        if (t.id != "null"){
                            users.add(t)
                        }else{
                            Log.d(TAG,"user info is null!")
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
        }
    }

    fun refresh(re:Boolean) {
        val fragment = activity?.supportFragmentManager?.findFragmentByTag(WBUserFragment::class.java.name)
        if (fragment is WBUserFragment){
            fragment.refresh(re)
        }
    }

    companion object{
        private const val TAG = "WBUserListFragment"

        fun newIntent(uid:String):WBUserListFragment{
            val bd = Bundle()
            bd.putString("uid",uid)
            val fragment = WBUserListFragment()
            fragment.arguments = bd
            return fragment
        }
    }
}