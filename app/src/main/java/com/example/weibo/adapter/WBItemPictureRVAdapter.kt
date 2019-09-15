package com.example.weibo.adapter

import android.graphics.Point
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.weibo.R
import com.example.weibo.bean.WBPicUrl
import com.example.weibo.holder.WBItemPictureHolder
import kotlinx.android.synthetic.main.item_image.view.*

class WBItemPictureRVAdapter(
    private val frag:Fragment,
    private var point: Point,
    private var list: ArrayList<WBPicUrl> = arrayListOf()
) : RecyclerView.Adapter<WBItemPictureHolder>() {
    private val TAG = "WBItemPictureRVAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WBItemPictureHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image,parent,false)
        val holder = WBItemPictureHolder(view)
        val layoutParams = holder.itemView.item_user_upload_image.layoutParams

        Log.d(TAG,"x:${point.x}  y:${point.y}")

        if (itemCount > 0) {
            val re: Int
            when (itemCount) {
                1 -> {
                    re = (point.x / 1.4).toInt()
                    layoutParams.width = re
                    layoutParams.height = (1.2 * re).toInt()
                }
                2 -> {
                    re = (point.x / 2.2).toInt()
                    layoutParams.width = re
                    layoutParams.height = (re * 1.2).toInt()
                }
                else -> {
                    re = (point.x / 3.4).toInt()
                    layoutParams.width = re
                    layoutParams.height = re
                }
            }
            holder.itemView.item_user_upload_image.layoutParams = layoutParams
        }

        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WBItemPictureHolder, position: Int) {
        if (itemCount > 0){
            Log.d(TAG,"正在加载第${position+1}张图片")
            holder.load(frag,list[position],holder.itemView.item_user_upload_image)
        }
    }

    fun updatePicture(pictures:List<WBPicUrl>){
        list.clear()
        list.addAll(pictures)
        notifyDataSetChanged()
    }
}