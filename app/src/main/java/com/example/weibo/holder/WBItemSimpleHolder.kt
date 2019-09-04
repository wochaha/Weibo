package com.example.weibo.holder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weibo.activity.HomePageActivity
import com.example.weibo.activity.SingleFragmentActivity
import com.example.weibo.bean.WBItem
import com.example.weibo.fragment.WBDetailPageFragment
import kotlinx.android.synthetic.main.item_weibo.view.*

/**
 * 微博item的holder,之后将会实现相应的footView
 */
class WBItemSimpleHolder
/**
 * @param context RV所在的context
 * @param itemView holder的界面
 */(context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mContext = context
    //加载数据
    fun load(item:WBItem){
        item.handle()
        Glide.with(mContext).load(item.mUser.profileImageUrl).into(itemView.blog_author_avatar)
        itemView.blog_author_nick_name.text = item.mUser.name
        itemView.blog_create_time.text = item.mTime
        itemView.blog_create_phone.text = item.mSource
        itemView.blog_content.text = item.mContent
        itemView.blog_thumbs_counts_text.text = item.mCommCounts
        itemView.blog_transmit_button_text.text = item.mRepostsCount
        itemView.setOnClickListener {
            if (mContext is HomePageActivity){
                mContext.replaceFragment(mContext.supportFragmentManager,WBDetailPageFragment.newIntent(item))
            }
        }
    }
}