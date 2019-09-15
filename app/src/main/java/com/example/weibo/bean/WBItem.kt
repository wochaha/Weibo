package com.example.weibo.bean

import android.icu.text.SimpleDateFormat
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.util.*
import java.util.regex.Pattern
import kotlin.text.StringBuilder

//微博item的超类
class WBItem : Parcelable,HandleData {
    private val TAG = "com.example.weibo.bean.WBItem"

    @SerializedName("idstr")
    var statusIdStr = "null"

    @SerializedName("source")
    var mSource:String = "null"

    @SerializedName("user")
    var mUser = WBUser()

    //后期需要处理表情等
    @SerializedName("text")
    var mContent:String = "null"

    @SerializedName("created_at")
    var mTime:String = "null"

    @SerializedName("comments_count")
    var count:Int = 0

    @SerializedName("reposts_count")
    var reposts_count:Int = 0

    var mCommCounts:String = "评论"
    var mRepostsCount = "转发"

    @SerializedName("thumbnail_pic")
    var smallPictureUrl = "null"

    @SerializedName("pic_urls")
    var picturesUrl = arrayListOf<WBPicUrl>()

    constructor(parcel: Parcel) : this() {
        statusIdStr = parcel.readString().toString()
        mSource = parcel.readString().toString()
        mUser = parcel.readParcelable(WBUser::class.java.classLoader)!!
        mContent = parcel.readString().toString()
        mTime = parcel.readString().toString()
        count = parcel.readInt()
        mCommCounts = parcel.readString().toString()
        smallPictureUrl = parcel.readString().toString()
    }

    constructor()

    constructor(user:WBUser,content:String,time:String,commCounts:Int,source:String){
        mUser = user
        mContent = content
        count = commCounts
        mTime = time
        mSource = source
    }


    override fun handle(){
        mCommCounts = handleComment(count)
        mRepostsCount = handleRepost(reposts_count)
        mSource = handleResourceType(mSource)
        mTime = handleTime(mTime)
    }



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(statusIdStr)
        parcel.writeString(mSource)
        parcel.writeParcelable(mUser, flags)
        parcel.writeString(mContent)
        parcel.writeString(mTime)
        parcel.writeInt(count)
        parcel.writeString(mCommCounts)
        parcel.writeString(smallPictureUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WBItem> {
        override fun createFromParcel(parcel: Parcel): WBItem {
            return WBItem(parcel)
        }

        override fun newArray(size: Int): Array<WBItem?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return StringBuilder().apply {
            append("id:$statusIdStr\n")
            append("transmitCount:$mRepostsCount\n")
            if(picturesUrl.size>0){
                append("pic size:${picturesUrl.size}\n")
                append(picturesUrl)
                append("\n${picturesUrl[0].thumbnailPicture.javaClass.name}")
            }
        }.toString()
    }
}