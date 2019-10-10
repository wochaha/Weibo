package com.example.weibo.data.bean

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder

class WBPicUrl() : Parcelable {
    @SerializedName("thumbnail_pic")
    var thumbnailPicture:String = "null"

    constructor(parcel: Parcel) : this() {
        thumbnailPicture = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(thumbnailPicture)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WBPicUrl> {
        override fun createFromParcel(parcel: Parcel): WBPicUrl {
            return WBPicUrl(parcel)
        }

        override fun newArray(size: Int): Array<WBPicUrl?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return StringBuilder().apply {
            append(thumbnailPicture)
        }.toString()
    }

    fun handle(){
        val replacement = "bmiddle"
        thumbnailPicture = thumbnailPicture.replace("thumbnail",replacement)
        Log.d("thumbnailPicture",thumbnailPicture)
    }
}