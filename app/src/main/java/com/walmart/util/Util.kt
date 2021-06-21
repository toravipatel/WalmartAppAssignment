package com.walmart.util

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide

class Util {

    fun loadImageWithGlide(context:Activity?,imageView: ImageView,url:String){
        context?.let { context ->
            Glide
                .with(context)
                .load(url)
                .centerCrop()
                .into(imageView)
        }
    }
}