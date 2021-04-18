package com.example.myvideodownloader

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    var startDownloadLiveData = MutableLiveData<String>()

    var completedDownloadLiveData = MutableLiveData<String>()

}