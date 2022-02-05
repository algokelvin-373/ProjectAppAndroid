package com.algokelvin.moviecatalog.util

import android.app.Activity
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

object ConstMethod {
    fun Activity.glideImg(url: String, imageView: ImageView) {
        Glide.with(this).load(url).into(imageView)
    }
    fun Fragment.glideImg(url: String, imageView: ImageView) {
        Glide.with(this).load(url).into(imageView)
    }
}