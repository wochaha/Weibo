package com.example.weibo.holder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weibo.bean.WBItem
import kotlinx.android.synthetic.main.item_weibo.view.*

/**
 * 微博item的holder
 */
class WBItemSimpleHolder
/**
 * @param context RV所在的context
 * @param item holder需要展示的数据的数据源
 * @param itemView holder的界面
 */(context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

    //加载数据
    fun load(item:WBItem){
        //Glide.with(context).load(item.mUser.profileImageUrl).into(itemView.blog_author_avatar)
        itemView.blog_author_nick_name.text = item.mUser.name
        itemView.blog_create_time.text = item.mTime
        itemView.blog_create_phone.text = item.mPhone
        itemView.blog_content.text = item.mContent
        itemView.blog_thumbs_counts.text = item.mCommCounts
        itemView.blog_thumbs_counts.text = item.mThumbsCounts
    }
}