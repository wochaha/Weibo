package com.example.weibo.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weibo.R
import com.example.weibo.adapter.WBItemRVAdapter
import com.example.weibo.bean.WBItem
import com.example.weibo.bean.WBUser
import com.example.weibo.constant.Api.Companion.USER_WEIBO
import com.example.weibo.utils.getWBItemList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.wblist_recycler_view.view.*

/**
 * 微博列表fragment，便于复用
 */
class WBItemRVFragment : Fragment() {
    private var user:WBUser? = null
    private val mHandler = Handler(Looper.getMainLooper())
    private lateinit var layoutManager:LinearLayoutManager
    private var adapter = WBItemRVAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments?.getParcelable<WBUser>("user")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.wblist_recycler_view,container,false)
        val list = arrayListOf<WBItem>()

        layoutManager = LinearLayoutManager(this.context)
        view.weibo_recycler_view.layoutManager = layoutManager
        adapter = WBItemRVAdapter(list,true)
        view.weibo_recycler_view.adapter = adapter

        loadWBItemInfo()

        view.weibo_recycler_view.addOnScrollListener(OnWBScrollListener())
        return view
    }

    companion object{
        fun newIntent(user:WBUser):WBItemRVFragment{
            val bd = Bundle()
            bd.putParcelable("user",user)
            val fragment = WBItemRVFragment()
            fragment.arguments = bd
            return fragment
        }
    }

    //引用父fragment的函数
    fun refresh(re:Boolean) {
        val fragment = activity?.supportFragmentManager?.findFragmentByTag(WBUserFragment::class.java.name)
        if (fragment is WBUserFragment){
            fragment.refresh(re)
        }
    }

    private fun loadWBItemInfo(){
        Observable.create<ArrayList<WBItem>> {
            val list = getWBItemList(USER_WEIBO)
            if (list.isNotEmpty()){
                it.onNext(list)
            }
            it.onComplete()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()){
                    refresh(true)
                    adapter.updateItems(it)
                    refresh(false)
                }
            }.isDisposed
    }

    inner class OnWBScrollListener
        : RecyclerView.OnScrollListener() {

        //获取外部类的引用
        private fun getOuterReference() = this@WBItemRVFragment

        private var lastVisibleItem = 0

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            lastVisibleItem = getOuterReference().layoutManager.findLastVisibleItemPosition()
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE){
                if (lastVisibleItem+1 == getOuterReference().adapter.itemCount && getOuterReference().adapter.nextPage){
                    mHandler.post{
                        getOuterReference().refresh(true)
                    }
                    getOuterReference().loadWBItemInfo()
                    mHandler.post{
                        getOuterReference().refresh(false)
                    }
                }
            }
        }
    }
}