package com.example.weibo.adapter

import android.graphics.Point
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weibo.R
import com.example.weibo.holder.WBItemPictureHolder
import kotlinx.android.synthetic.main.item_image.view.*
import java.io.File

class WBItemPictureRVAdapter(private val point: Point, private val list: ArrayList<File>) : RecyclerView.Adapter<WBItemPictureHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WBItemPictureHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image,parent,false)

        return WBItemPictureHolder(parent.context,view,point)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WBItemPictureHolder, position: Int) {
        val layoutParams = holder.itemView.item_user_upload_image.layoutParams
        layoutParams.height = (point.x/5.5).toInt()
        layoutParams.width = (point.x/5.5).toInt()
        holder.itemView.item_user_upload_image.layoutParams = layoutParams

        holder.load(list[position])
        Log.d("WBItemPictureRVAdapter","第${position + 1}张图片加载完成")
    }

    fun updatePicture(pictures:List<File>){
        list.clear()
        list.addAll(pictures)
        notifyDataSetChanged()
    }
}