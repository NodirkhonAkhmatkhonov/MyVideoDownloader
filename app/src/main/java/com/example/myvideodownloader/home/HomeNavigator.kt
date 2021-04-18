package com.example.myvideodownloader.home

interface HomeNavigator {

    fun downloadFile()

    fun onStartPressed()
    fun onPausePressed()
    fun onCancelPressed()
}