package com.example.weibo.ui.holder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weibo.data.bean.WBUser
import kotlinx.android.synthetic.main.item_user.view.*

class WBUserListVH(val context: Context,itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun load(user:WBUser){
        Glide.with(context).asBitmap().load(user.avatarLargeUrl).into(itemView.user_avatar)
        itemView.list_user_nick_name.text = user.screenName
        itemView.verified_reason.text = user.verifiedReason
        itemView.recent_status.text = user.recentStatusContent
    }
}