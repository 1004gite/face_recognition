package com.example.capstonandroid.view

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.children
import com.example.capstonandroid.model.Datas
import com.example.capstonandroid.model.DebugEvent
import com.example.capstonandroid.model.DesignEvent
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.itemClickEvents
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*

class BindViews(var context: Context) {
    var activity_main: Activity = context as Activity
    lateinit var textureViewSetting: TextureViewSetting

    fun bindViews() {
        // textureView setting
        textureViewSetting = TextureViewSetting(context)
        textureViewSetting.setTextureView()
        // listview setting
        activity_main.listViewDebug.layoutParams.width =
            context.resources.displayMetrics.widthPixels / 3
        activity_main.listViewDebug.adapter =
            ArrayAdapter(context, android.R.layout.simple_list_item_1, Datas.instance.listFordbug)
        activity_main.listViewDebug.itemClickEvents()
            .subscribeBy(
                onNext = {
                    Datas.instance.debugEventSubject.onNext(Datas.instance.listFordbug[it.position])
                    Datas.instance.designEventSubject.onNext(DesignEvent.DebugLoadStart)
                },
                onError = { Log.e("listViewErr", "BindViews클래스의 listview에서 에러") }
            )
    }

}