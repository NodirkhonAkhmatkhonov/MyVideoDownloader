package com.example.myvideodownloader.base

import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>: ViewModel() {

    private lateinit var mNavigator: WeakReference<N>

    var navigator: N
        get() = mNavigator.get()!!
        set(navigator) {
            this.mNavigator = WeakReference(navigator)
        }
}