package com.example.weibo.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sina.weibo.sdk.network.base.*
import com.sina.weibo.sdk.utils.WbUtils

class WBComment :Parcelable {
    @SerializedName("created_at")
    var createTime:String = ""

    @SerializedName("idstr")
    var idStr:String = "null"

    @SerializedName("user")
    var user:WBUser = WBUser()

    @SerializedName("text")
    var content:String = ""

    @SerializedName("status")
    var status:WBItem = WBItem()

    constructor(parcel: Parcel) : this() {
        createTime = parcel.readString().toString()
        idStr = parcel.readString().toString()
        user = parcel.readParcelable(WBUser::class.java.classLoader)!!
        content = parcel.readString().toString()
        status = parcel.readParcelable(WBItem::class.java.classLoader)!!
    }

    constructor()

    constructor(id:String,content:String,statusId:String){
        idStr = id
        this.content = content
        status.statusIdStr = statusId
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(createTime)
        parcel.writeString(idStr)
        parcel.writeParcelable(user, flags)
        parcel.writeString(content)
        parcel.writeParcelable(status, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WBComment> {
        override fun createFromParcel(parcel: Parcel): WBComment {
            return WBComment(parcel)
        }

        override fun newArray(size: Int): Array<WBComment?> {
            return arrayOfNulls(size)
        }
    }
}