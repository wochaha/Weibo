package com.example.weibo.ui.fragment

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weibo.R
import com.example.weibo.ui.activity.HomePageActivity
import com.example.weibo.ui.activity.SingleFragmentActivity
import com.example.weibo.ui.adapter.WBCommentRVAdapter
import com.example.weibo.ui.adapter.WBItemPictureRVAdapter
import com.example.weibo.data.bean.WBComment
import com.example.weibo.data.bean.WBItem
import com.example.weibo.utils.getWBItemComments
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_wbdetail.view.*

class WBDetailPageFragment : Fragment() {
    private lateinit var wbItem: WBItem
    private lateinit var viewLayout:View
    private val commentAdapter = WBCommentRVAdapter()
    private val TAG = "WBDetailPageFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        wbItem = arguments?.getParcelable("item") ?: WBItem()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater.inflate(R.layout.fragment_wbdetail,container,false)

        (activity as AppCompatActivity).setSupportActionBar(viewLayout.weibo_detail_page_tool_bar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)

        Glide.with(this).load(wbItem.mUser.profileImageUrl).into(viewLayout.weibo_detail_page_avatar)
        viewLayout.weibo_detail_page_username.text = wbItem.mUser.name
        viewLayout.weibo_detail_page_source.text = wbItem.mSource
        viewLayout.weibo_detail_page_time.text = wbItem.mTime
        viewLayout.weibo_detail_page_content.text = wbItem.mContent
        viewLayout.weibo_detail_page_comment_list.layoutManager = LinearLayoutManager(this.context)
        viewLayout.weibo_detail_page_comment_list.adapter = commentAdapter

        Log.d(TAG,wbItem.toString())
        if (wbItem.picturesUrl.size > 0){
            val point = Point()
            val defaultDisplay = (context as HomePageActivity).windowManager.defaultDisplay
            defaultDisplay.getSize(point)
            Log.d(TAG,"x : ${point.x},y : ${point.y}")
            val pictureAdapter = WBItemPictureRVAdapter(this,point,wbItem.picturesUrl)
            val layoutManager = if (wbItem.picturesUrl.size >= 3){
                GridLayoutManager(context,3)
            }else{
                GridLayoutManager(context,wbItem.picturesUrl.size)
            }
            viewLayout.weibo_detail_page_picture_list.layoutManager = layoutManager
            viewLayout.weibo_detail_page_picture_list.adapter = pictureAdapter
        }

        Log.d(TAG,"init success")

        loadCommentsInfo()

        return viewLayout
    }

    private fun loadCommentsInfo(){
        Observable.create<ArrayList<WBComment>> {
            val list = getWBItemComments(wbItem.statusIdStr)
            if (list.isNotEmpty()){
                it.onNext(list)
            }
            it.onComplete()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                commentAdapter.update(it)
            }.isDisposed
        
    }

    companion object{
        fun newIntent(item:WBItem): WBDetailPageFragment {
            val bd = Bundle()
            bd.putParcelable("item",item)
            val fragment = WBDetailPageFragment()
            fragment.arguments = bd
            return fragment
        }
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