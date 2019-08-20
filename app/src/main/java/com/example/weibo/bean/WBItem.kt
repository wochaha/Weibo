package com.example.weibo.bean

import android.icu.text.SimpleDateFormat
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder
import java.text.ParseException
import java.util.*
import java.util.regex.Pattern

//微博item的超类
class WBItem : Parcelable {
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

    @SerializedName("pic_ids")
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

    fun handle(){
        handleCommentAndRepost()
        handleResourceType()
        handleTime()
    }

    private fun handleCommentAndRepost(){
        mCommCounts = when {
            count >= 10000 -> "${count/10000}万"
            count in 1..9999 -> count.toString()
            else -> "评论"
        }
        
        mRepostsCount = when{
            reposts_count >= 10000 -> "${reposts_count/10000}万"
            reposts_count in 1..9999 -> reposts_count.toString()
            else -> "转发"
        }
    }

    private fun handleTime(){
        val jobTime: Date
        try {
            //微博创建时间字符串转date对象
            jobTime = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(mTime)

            //获取Calendar单例
            val calendar = Calendar.getInstance()

            //获取现在时间信息
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)+1
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

            //设置Calendar的时间为该条微博创建时间
            calendar.time = jobTime

            //获取该条微博创建时间信息
            val createdYear = calendar.get(Calendar.YEAR)
            val createdMonth = calendar.get(Calendar.MONTH)+1
            val createDay = calendar.get(Calendar.DAY_OF_MONTH)

            //处理时间字符串
            mTime = StringBuilder().apply {
                if (createdYear < currentYear){
                    append(SimpleDateFormat("yyyy-MM-dd HH:mm").format(jobTime))
                }else if (createdYear == currentYear){
                    if (createdMonth == currentMonth){
                        when {
                            currentDay - createDay == 0 -> {
                                append("今天")
                                append(" ")
                                append(SimpleDateFormat("HH:mm").format(jobTime))
                            }
                            currentDay - createDay == 1 -> {
                                append("昨天")
                                append(" ")
                                append(SimpleDateFormat("HH:mm").format(jobTime))
                            }
                            else -> append(SimpleDateFormat("MM-dd HH:mm").format(jobTime))
                        }
                    }else if (createdMonth < currentMonth){
                        append(SimpleDateFormat("MM-dd HH:mm").format(jobTime))
                    }
                }
            }.toString()

        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    private fun handleResourceType(){
        val regex = "<a.*?>(.*?)</a>"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(mSource)
        if (matcher.find()){
            mSource = "来自"+matcher.group(1)
        }
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
}