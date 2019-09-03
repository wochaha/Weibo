package com.example.weibo.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weibo.bean.WBComment
import kotlinx.android.synthetic.main.item_comment.view.*

class WBCommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun load(comment:WBComment){
        Glide.with(itemView).load(comment.user.profileImageUrl).into(itemView.comment_avatar)
        itemView.comment_username.text = comment.user.name
        itemView.comment_content.text = comment.content
    }
}