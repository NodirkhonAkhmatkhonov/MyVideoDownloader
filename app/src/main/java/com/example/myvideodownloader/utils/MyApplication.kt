package com.example.myvideodownloader.utils

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.myvideodownloader.inprogress.ProgressModel

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        var downloadList = mutableListOf<ProgressModel>()
        val completedDownloadLiveData = MutableLiveData<Long>()
    }
}