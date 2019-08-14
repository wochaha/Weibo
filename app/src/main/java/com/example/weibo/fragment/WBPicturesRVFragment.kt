package com.example.weibo.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weibo.R
import com.example.weibo.WBApplication
import com.example.weibo.adapter.WBItemPictureRVAdapter
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.pictures_recycler_view.view.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

/**
 * 微博用户相册页
 */
class WBPicturesRVFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    private lateinit var avatar:String
    private lateinit var adapter:WBItemPictureRVAdapter
    private val files = arrayListOf<File>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        avatar = arguments?.getString("avatar").toString()
        Log.d("WBPicturesRVFragment",avatar)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pictures_recycler_view,container,false)
        recyclerView = view.pictures_recycler_view

        val list = arrayListOf<String>()
        for (i in 1..15){
            list.add(avatar)
        }
        loadPicturesAsFiles(list,files)

        return view
    }

    private fun initView(list: ArrayList<File>){
        recyclerView.layoutManager = GridLayoutManager(context,5)

        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val point = Point()

        windowManager.defaultDisplay.getSize(point)

        Log.d("WBPicturesRVFragment","point : ${point.x}")

        adapter = WBItemPictureRVAdapter(point,list)

        recyclerView.adapter = adapter
    }

    companion object{
        fun newIntent(url:String):WBPicturesRVFragment{
            val bd = Bundle()
            bd.putString("avatar",url)
            val fragment = WBPicturesRVFragment()
            fragment.arguments = bd
            return fragment
        }
    }

    fun refresh(re:Boolean) {
        val fragment = activity?.supportFragmentManager?.findFragmentByTag(WBUserFragment::class.java.name)
        if (fragment is WBUserFragment){
            fragment.refresh(re)
        }
    }

    @SuppressLint("CheckResult")
    private fun loadPicturesAsFiles(urlList:ArrayList<String>,results:ArrayList<File>){
        refresh(true)
        Observable
            .create<File> {
                for (url in urlList) {
                    val target = Glide
                        .with(WBApplication.getContext())
                        .asFile()
                        .load(url)
                        .submit()
                    val file = target.get()
                    it.onNext(file)
                }
                it.onComplete()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<File>{
                private lateinit var disposable:Disposable

                override fun onComplete() {
                    disposable.dispose()
                    Log.d("WBPicturesRVFragment","准备更新${results.size + 1}张图片")
                    initView(results)
                    refresh(false)
                    Log.d("WBPicturesRVFragment","图片更新完毕")
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: File) {
                    results.add(t)
                    Log.d("WBPicturesRVFragment","已有${results.size}个文件被下载")
                }

                override fun onError(e: Throwable) {
                    Log.d("WBPicturesRVFragment",e.message.toString())
                }

            })
    }
}