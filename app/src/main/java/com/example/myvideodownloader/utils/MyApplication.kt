package com.example.myvideodownloader.utils

import android.app.Application

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        var downloadId = 0L
    }
}