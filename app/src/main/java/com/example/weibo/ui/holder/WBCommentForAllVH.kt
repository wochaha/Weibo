package com.example.weibo.ui.holder

import android.view.View
import androidx.annotation.IntDef
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weibo.data.bean.WBCommentRelatedUser
import kotlinx.android.synthetic.main.item_comment_for_all.view.*

class WBCommentForAllVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun load(commentRU:WBCommentRelatedUser){
        val type = commentRU.enSureType()
        commentRU.handle()
        Glide.with(itemView)
            .load(commentRU.user.profileImageUrl)
            .into(itemView.comment_for_all_avatar)
        Glide.with(itemView)
            .load(commentRU.getStatusSimpleImageUrl())
            .into(itemView.comment_for_all_status_image)
        itemView.comment_for_all_nick_name.text = commentRU.user.name
        itemView.comment_for_all_create_time.text = commentRU.createTime
        itemView.comment_for_all_content.text = commentRU.replyOriginalCommentContent
        itemView.comment_for_all_status_content.text = commentRU.status.mContent
        itemView.comment_for_all_status_name.text = "@${commentRU.status.mUser.name}"

        when(type){
            ME_REPLY_COMMENT -> {
                itemView.comment_for_all_phone.visibility = View.VISIBLE
                itemView.comment_for_all_original_comment.visibility = View.VISIBLE
                itemView.comment_for_all_phone.text = commentRU.phoneType
                itemView.comment_for_all_original_comment.text = "@${commentRU.originalComment.user.name}：${commentRU.originalComment.content}"
            }
            ME_REPLY_STATUS -> {
                itemView.comment_for_all_phone.visibility = View.VISIBLE
                itemView.comment_for_all_phone.text = commentRU.phoneType
            }
            OTHERS_REPLY_COMMENT -> {
                itemView.comment_for_all_original_comment.visibility = View.VISIBLE
                itemView.comment_for_all_original_comment.text = "@${commentRU.originalComment.user.name} ${commentRU.originalComment.content}"
            }
        }
    }

    @Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(ME_REPLY_COMMENT, ME_REPLY_STATUS, OTHERS_REPLY_STATUS, OTHERS_REPLY_COMMENT)
    annotation class CommentTypeForAll

    companion object{
        const val ME_REPLY_STATUS = 6
        const val ME_REPLY_COMMENT = 7
        const val OTHERS_REPLY_STATUS = 8
        const val OTHERS_REPLY_COMMENT = 9
    }
}