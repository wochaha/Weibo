package com.example.weibo.ui.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.weibo.R
import com.example.weibo.data.bean.WBPicUrl


class WBItemPictureHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun load(frag:Fragment,url: WBPicUrl,img:ImageView){
        val params = itemView.item_user_upload_image.layoutParams
        url.handle()

        Glide.with(frag)
            .load(url.thumbnailPicture)
            .placeholder(R.drawable.ic_user_portrait)
            .dontAnimate()
            .centerCrop()
            .override(params.width,params.height)
            .into(img)
    }
}