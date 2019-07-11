package com.example.weibo.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

fun bitmapToString(bitmap: Bitmap):String{
    val byteStream = ByteArrayOutputStream()
    val bytes = byteStream.toByteArray()
    return Base64.encodeToString(bytes,Base64.DEFAULT)
}