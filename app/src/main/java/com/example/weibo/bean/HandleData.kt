package com.example.weibo.bean

import android.icu.text.SimpleDateFormat
import java.text.ParseException
import java.util.*
import java.util.regex.Pattern

abstract class HandleData() {
    abstract fun handle()

    fun handleComment(count:Int):String{
        return when {
            count >= 10000 -> "${count/10000}万"
            count in 1..9999 -> count.toString()
            else -> "评论"
        }
    }

    fun handleRepost(count:Int):String{
        return when {
            count >= 10000 -> "${count/10000}万"
            count in 1..9999 -> count.toString()
            else -> "转发"
        }
    }

    fun handleTime(timeStr:String):String{
        val jobTime: Date
        try {
            //微博创建时间字符串转date对象
            jobTime = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(timeStr)

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
            return StringBuilder().apply {
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
        return "未知"
    }

    fun handleResourceType(mSource:String):String{
        val regex = "<a.*?>(.*?)</a>"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(mSource)
        if (matcher.find()){
            return "来自"+matcher.group(1)
        }
        return ""
    }
}