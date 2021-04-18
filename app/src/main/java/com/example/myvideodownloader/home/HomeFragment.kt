package com.example.myvideodownloader.home

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.IntentFilter
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myvideodownloader.SharedViewModel
import com.example.myvideodownloader.R
import com.example.myvideodownloader.base.BaseFragment
import com.example.myvideodownloader.databinding.FragmentHomeBinding
import com.example.myvideodownloader.utils.DownloadManagerUtil
import com.example.myvideodownloader.utils.DownloadReceiver
import com.example.myvideodownloader.utils.MyApplication
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.offline.Downloader

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeNavigator, Player.EventListener {

    private lateinit var mSharedViewModel: SharedViewModel
    private lateinit var mViewModel: HomeViewModel

    private var downloadBroadcastReceiver: DownloadReceiver? = null

    private var downloader: Downloader? = null

    override fun initView(view: View, mViewDataBinding: FragmentHomeBinding) {
        mViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        mSharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        mViewDataBinding.homeViewModel = mViewModel
        mViewDataBinding.lifecycleOwner = this
        mViewModel.navigator = this

        downloadBroadcastReceiver = DownloadReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.DOWNLOAD_COMPLETE")
        intentFilter.addAction("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED")
        context?.registerReceiver(downloadBroadcastReceiver, intentFilter)
    }

    override fun getLayoutId() = R.layout.fragment_home

    override fun downloadFile() {
        val url = mViewDataBinding.etSearch.text.toString()
        if (url.endsWith(".mp4").not()) {
            return
        }

        mSharedViewModel.startDownloadLiveData.value = url




        var title = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".mp4"))
        title = title.capitalize()
        val desc = "Video File"

        val dm = DownloadManagerUtil(requireContext())
        if (dm.checkDownloadManagerEnable()) {
//            if (MyApplication.downloadId != 0L) {
//                dm.clearCurrentTask(MyApplication.downloadId)
//            }
//            MyApplication.downloadId = dm.download(url, title, desc)
        }
    }

    override fun onStartPressed() {
//        resumeDownload()
    }

    override fun onPausePressed() {
//        MyApplication.downloadId = pauseDownload().toLong()
//        val id = pauseDownload()
//        Toast.makeText(context, "Paused id = $id", Toast.LENGTH_LONG).show()
    }

    override fun onCancelPressed() {
        val dm = DownloadManagerUtil(requireContext())
//        dm.clearCurrentTask(MyApplication.downloadId)
    }

    override fun onDestroy() {
        super.onDestroy()
        context?.unregisterReceiver(downloadBroadcastReceiver)
    }
}
