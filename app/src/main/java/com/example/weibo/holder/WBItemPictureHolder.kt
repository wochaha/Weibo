package com.example.weibo.holder

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image.view.*
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.weibo.R
import com.example.weibo.utils.bitmapToString
import com.example.weibo.utils.getDrawableUri


class WBItemPictureHolder(val context: Context,itemView: View,point: Point) : RecyclerView.ViewHolder(itemView) {
    private var img:ImageView = itemView.item_user_upload_image
    private val size = (point.x/5.5).toInt()

    fun load(imgFile:File){

        Glide.with(context)
            .load(imgFile)
            .placeholder(R.drawable.ic_user_portrait)
            .override(size,size)
            .into(img)
    }
}