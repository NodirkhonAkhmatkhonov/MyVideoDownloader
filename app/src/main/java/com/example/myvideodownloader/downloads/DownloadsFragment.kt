package com.example.myvideodownloader.downloads

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvideodownloader.player.PlayerActivity
import com.example.myvideodownloader.R
import com.example.myvideodownloader.SharedViewModel
import com.example.myvideodownloader.base.BaseFragment
import com.example.myvideodownloader.databinding.FragmentDownloadsBinding


class DownloadsFragment: BaseFragment<FragmentDownloadsBinding>(), DownloadsNavigator, OnItemClickListener {

    private lateinit var mSharedViewModel: SharedViewModel
    private lateinit var mViewModel: DownloadsViewModel
    private var videos = ArrayList<VideoModel>()
    private lateinit var videosAdapter: VideoAdapter

    override fun initView(view: View, mViewDataBinding: FragmentDownloadsBinding) {
        mSharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        mViewModel = ViewModelProvider(this).get(DownloadsViewModel::class.java)
        mViewDataBinding.downloadsViewModel = mViewModel
        mViewDataBinding.lifecycleOwner = this
        mViewModel.navigator = this

        setVideos()
        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        mViewDataBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        videosAdapter = VideoAdapter(videos, this)
        mViewDataBinding.recyclerView.adapter = videosAdapter
    }

    private fun initViewModel() {
        mSharedViewModel.completedDownloadLiveData.observe(this, Observer {
            videos.clear()
            setVideos()
        })
    }

    private fun setVideos() {
        val temp = context?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

        val mfile = temp?.listFiles()
        for (item in mfile!!.asList()) {
            val path = item.absolutePath.toString()
            var name = path.substring(path.lastIndexOf('/') + 1)
            name = name.substring(0, name.indexOf(".mp4"))

            if (path.endsWith(".mp4")) {
                val bmThumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND)
                videos.add(VideoModel(name, path, bmThumbnail))
            }
        }
    }

    override fun onItemClicked(video: VideoModel) {
        val intent = Intent(context, PlayerActivity::class.java)
        intent.putExtra("Path", video.path)
        startActivity(intent)
    }

    override fun getLayoutId() = R.layout.fragment_downloads
}