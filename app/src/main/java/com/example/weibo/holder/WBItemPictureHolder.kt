package com.example.weibo.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.weibo.R
import com.example.weibo.bean.WBPicUrl


class WBItemPictureHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun load(frag:Fragment,url: WBPicUrl,img:ImageView){
        val params = itemView.item_user_upload_image.layoutParams

        Glide.with(frag)
            .load(url)
            .placeholder(R.drawable.ic_user_portrait)
            .dontAnimate()
            .fitCenter()
            .override(params.width,params.height)
            .into(img)
    }
}