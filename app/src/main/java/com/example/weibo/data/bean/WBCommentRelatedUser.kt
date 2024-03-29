package com.example.weibo.data.bean

import android.os.Parcel
import android.os.Parcelable
import com.example.weibo.ui.holder.WBCommentForAllVH.CommentTypeForAll
import com.example.weibo.ui.holder.WBCommentForAllVH.Companion.ME_REPLY_COMMENT
import com.example.weibo.ui.holder.WBCommentForAllVH.Companion.ME_REPLY_STATUS
import com.example.weibo.ui.holder.WBCommentForAllVH.Companion.OTHERS_REPLY_COMMENT
import com.example.weibo.ui.holder.WBCommentForAllVH.Companion.OTHERS_REPLY_STATUS
import com.google.gson.annotations.SerializedName

class WBCommentRelatedUser : HandleData,Parcelable {
    constructor()

    @SerializedName("created_at")
    var createTime:String = ""

    @SerializedName("idstr")
    var idStr:String = "null"

    @SerializedName("source")
    var phoneType:String = ""

    //被回复的评论
    @SerializedName("reply_comment")
    var originalComment = WBComment()

    //回复的内容（包括用户名）
    @SerializedName("text")
    var replyOriginalCommentContent:String = ""

    //发出该评论(非被回复的评论)的人
    @SerializedName("user")
    var user = WBUser()

    @SerializedName("status")
    var status = WBItem()

    override fun handle() {
        createTime = handleTime(createTime)
        phoneType = handleResourceType(phoneType)
    }

    private fun isNotReplyOthersComment():Boolean = originalComment.idStr == "null"

    private fun isFromUser():Boolean = phoneType == ""

    @CommentTypeForAll
    fun enSureType():Int{
        return if (isFromUser() && isNotReplyOthersComment()){
            ME_REPLY_STATUS
        }else if (isFromUser() && !isNotReplyOthersComment()){
            ME_REPLY_COMMENT
        }else if (!isFromUser() && isNotReplyOthersComment()){
            OTHERS_REPLY_STATUS
        }else{
            OTHERS_REPLY_COMMENT
        }
    }

    fun getStatusSimpleImageUrl():String = if (status.picturesUrl.isEmpty()) user.profileImageUrl else status.picturesUrl[0].thumbnailPicture

    constructor(parcel: Parcel) : this() {
        createTime = parcel.readString().toString()
        idStr = parcel.readString().toString()
        phoneType = parcel.readString().toString()
        replyOriginalCommentContent = parcel.readString().toString()
        user = parcel.readParcelable(WBUser::class.java.classLoader)!!
        status = parcel.readParcelable(WBItem::class.java.classLoader)!!
        originalComment = parcel.readParcelable(WBComment::class.java.classLoader)!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(createTime)
        parcel.writeString(idStr)
        parcel.writeString(phoneType)
        parcel.writeString(replyOriginalCommentContent)
        parcel.writeParcelable(user, flags)
        parcel.writeParcelable(status, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WBCommentRelatedUser> {
        override fun createFromParcel(parcel: Parcel): WBCommentRelatedUser {
            return WBCommentRelatedUser(parcel)
        }

        override fun newArray(size: Int): Array<WBCommentRelatedUser?> {
            return arrayOfNulls(size)
        }
    }
}