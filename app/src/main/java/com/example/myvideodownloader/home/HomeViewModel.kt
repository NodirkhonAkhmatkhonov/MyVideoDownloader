package com.example.myvideodownloader.home

import com.example.myvideodownloader.base.BaseViewModel

class HomeViewModel: BaseViewModel<HomeNavigator>() {

    fun download() {
        navigator.downloadFile()
    }

    fun onPlayPressed() {
        navigator.onStartPressed()
    }

    fun onPausePressed() {
        navigator.onPausePressed()
    }

    fun onStopPressed() {
        navigator.onCancelPressed()
    }
}