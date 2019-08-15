package com.example.weibo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weibo.R
import com.example.weibo.adapter.WBUserListRVAdapter
import com.example.weibo.bean.WBUser
import com.example.weibo.constant.Api
import com.example.weibo.utils.getUserList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_user_list.view.*

/**
 * 微博登录用户关注列表
 */
class WBUserListFragment : Fragment() {
    private val adapter = WBUserListRVAdapter()
    private lateinit var uid:String
    private lateinit var url:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uid = arguments?.getString("uid").toString()
        val tag = arguments?.getInt("tag",1)
        url = when (tag) {
            TYPE_FRIENDS -> Api.FRIENDS_LIST
            TYPE_FOLLOWERS -> Api.FOLLOWER_COUNT
            else -> Api.FRIENDS_LIST
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_list,container,false)
        view.user_list.layoutManager = LinearLayoutManager(this.context)
        view.user_list.adapter = adapter
        loadUserList(Api.FRIENDS_LIST,uid)
        refresh(false)
        return view
    }

    private fun loadUserList(url:String,uid:String){
        refresh(true)
        Observable.create<ArrayList<WBUser>> {
            val listUser = getUserList(url,uid)
            it.onNext(listUser)
            it.onComplete()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()){
                    Log.d(TAG,"it size : ${it.size}")
                    adapter.setData(it)
                }
            }.isDisposed
    }

    private fun refresh(re:Boolean) {
        val fragment = activity?.supportFragmentManager?.findFragmentByTag(WBUserFragment::class.java.name)
        if (fragment is WBUserFragment){
            if (re){
                adapter.reSet()
            }
            fragment.refresh(re)
        }
    }

    companion object{
        private const val TAG = "WBUserListFragment"

        const val TYPE_FRIENDS = 1
        const val TYPE_FOLLOWERS = 2

        fun newIntent(uid:String,tag:Int):WBUserListFragment{
            val bd = Bundle()
            bd.putString("uid",uid)
            bd.putInt("tag",tag)
            val fragment = WBUserListFragment()
            fragment.arguments = bd
            return fragment
        }
    }
}