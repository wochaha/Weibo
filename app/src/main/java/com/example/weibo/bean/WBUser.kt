package com.example.weibo.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder

//微博用户超类
class WBUser : Parcelable{

    @SerializedName("idstr")
    var id:String = "null"

    @SerializedName("screen_name")
    var screenName:String = "null"

    @SerializedName("name")
    var name:String = "null"

    @SerializedName("description")
    var description:String = "太懒了，什么也没写"

    @SerializedName("profile_image_url")
    var profileImageUrl = "null"

    @SerializedName("avatar_hd")
    var avatarHdUrl = "null"

    @SerializedName("gender")
    var gender:String = "n"

    @SerializedName("followers_count")
    var followersCount:Int = 0

    @SerializedName("friends_count")
    var friendsCount:Int = 0

    @SerializedName("statuses_count")
    var statusesCount:Int = 0

    @SerializedName("favourites_count")
    var favourites_count:Int = 0

    constructor()

    constructor(_id:String,_screenName:String,_name:String,_descriptions:String,_profileImageUrl:String){
        id = _id
        screenName = _screenName
        name = _name
        description = _descriptions
        profileImageUrl = _profileImageUrl
    }

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(screenName)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(profileImageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WBUser> {
        override fun createFromParcel(parcel: Parcel): WBUser {
            return WBUser(parcel)
        }

        override fun newArray(size: Int): Array<WBUser?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("id:$id name:$name screenName:$screenName description:$description")
        return builder.toString()
    }
}