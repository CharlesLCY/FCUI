package com.lcy.demo.converter

import android.databinding.BindingConversion
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author FanCoder.LCY
 * @date   2018/9/10 11:48
 * @email  15708478830@163.com
 * @desc
 **/
@BindingConversion
fun convertDate(date: Date): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(date)
}