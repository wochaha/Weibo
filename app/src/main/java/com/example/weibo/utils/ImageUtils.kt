package com.example.weibo.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import com.example.weibo.WBApplication
import java.io.*

fun bitmapToString(bitmap: Bitmap):String{
    val byteStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteStream)
    val bytes = byteStream.toByteArray()
    return Base64.encodeToString(bytes,Base64.DEFAULT)
}

fun getDrawableUri(resId:Int):Uri{
    val res = WBApplication.getContext().resources
    return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"
            +res.getResourcePackageName(resId)+"/"
            +res.getResourceTypeName(resId)+"/"
            +res.getResourceEntryName(resId))
}