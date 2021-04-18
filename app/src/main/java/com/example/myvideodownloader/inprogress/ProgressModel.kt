package com.example.myvideodownloader.inprogress

import android.graphics.Bitmap

data class ProgressModel (
    val name: String,
    val downloadId: Long,
    var progress: Long,
    var isPaused: Boolean = false,
    var isFinished: Boolean = false,
    val bitmap: Bitmap? = null
)