package com.example.weibo.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weibo.R
import kotlinx.android.synthetic.main.item_weibo_foot.view.*

class WBItemFVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var view: View = itemView

    fun load(type:Int){
        when(type){
            LOAD_MORE -> view.foot_view_content.setText(R.string.load_more)
            NO_MORE -> view.foot_view_content.setText(R.string.no_more)
        }
    }

    companion object{
        const val LOAD_MORE = 0
        const val NO_MORE = 1
    }
}