package com.example.weibo.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weibo.R
import com.example.weibo.adapter.WBItemRecyclerViewAdapter
import com.example.weibo.bean.WBItem
import com.example.weibo.bean.WBUser
import kotlinx.android.synthetic.main.wblist_recycler_view.view.*

/**
 * 微博列表fragment，便于复用
 */
class WBRVFragment : Fragment() {
    private var user:WBUser? = null
    private val mHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments?.getParcelable<WBUser>("user")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.wblist_recycler_view,container,false)
        val list = arrayListOf<WBItem>()
        if (user != null){
            val item = WBItem(user!!,"哈哈哈红红火火恍恍惚惚","2019-07-01",12345,12345,"小米9")
            for (i in 1..10){
                list.add(item)
            }
        }
        val layoutManager = LinearLayoutManager(this.context)
        view.weibo_recycler_view.layoutManager = layoutManager
        val adapter = WBItemRecyclerViewAdapter(list,true)
        view.weibo_recycler_view.adapter = adapter

        var lastVisibleItem = 0
        view.weibo_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    if (lastVisibleItem+1 == adapter.itemCount && adapter.nextPage){
                        val newList = ArrayList<WBItem>()
                        val item = WBItem(user!!,"哈哈哈红红火火恍恍惚惚","2019-07-01",12345,12345,"小米x")
                        for (i in 1..5){
                            newList.add(item)
                        }
                        mHandler.postDelayed({
                            adapter.updateItems(newList,false)
                        },1500)
                    }
                }
            }
        })
        return view
    }

    companion object{
        fun newIntent(user:WBUser):WBRVFragment{
            val bd = Bundle()
            bd.putParcelable("user",user)
            val fragment = WBRVFragment()
            fragment.arguments = bd
            return fragment
        }
    }
}