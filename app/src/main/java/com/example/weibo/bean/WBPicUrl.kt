package com.example.weibo.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class WBPicUrl() : Parcelable {
    @SerializedName("thumbnail_pic")
    var thumbnailPicture = "null"

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
}