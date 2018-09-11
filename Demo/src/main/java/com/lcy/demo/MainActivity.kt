package com.lcy.demo

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bindings: ActivityFirst = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        val item = ObItem()
        bindings.time = Date()
        bindings.obitem = item
        item.name.set("任盈盈")

        bindings.tv.setOnClickListener{
            item.name.set("123123")
        }
    }

}
