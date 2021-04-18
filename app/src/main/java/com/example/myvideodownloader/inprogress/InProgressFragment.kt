package com.example.myvideodownloader.inprogress

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvideodownloader.SharedViewModel
import com.example.myvideodownloader.R
import com.example.myvideodownloader.base.BaseFragment
import com.example.myvideodownloader.databinding.FragmentInProgressBinding
import com.example.myvideodownloader.utils.DownloadManagerUtil
import com.example.myvideodownloader.utils.MyApplication

class InProgressFragment : BaseFragment<FragmentInProgressBinding>(), InProgressNavigator,
    OnProgressItemClickListener {

    private lateinit var mSharedViewModel: SharedViewModel
    private lateinit var mViewModel: InProgressViewModel
    private val adapterInProgress: InProgressAdapter by lazy {
        InProgressAdapter(itemClickListener = this)
    }

    override fun initView(view: View, mViewDataBinding: FragmentInProgressBinding) {
        mSharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        mViewModel = ViewModelProvider(this).get(InProgressViewModel::class.java)
        mViewDataBinding.inProgressViewModel = mViewModel
        mViewDataBinding.lifecycleOwner = this
        mViewModel.navigator = this

        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        mViewDataBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterInProgress
        }
    }

    private fun initViewModel() {
        mSharedViewModel.startDownloadLiveData.observe(this, Observer { url ->
            var title = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".mp4"))
            title = title.capitalize()
            val desc = "Video File"

            val dm = DownloadManagerUtil(requireContext())
            if (dm.checkDownloadManagerEnable()) {
                val downloadId = dm.download(url, title, desc)
                val progressModel = ProgressModel(url, downloadId, 0)

                MyApplication.downloadList.add(progressModel)
                adapterInProgress.insertData(progressModel)
            }
        })
        MyApplication.completedDownloadLiveData.observe(this, Observer { downloadedId ->
            val item = MyApplication.downloadList.find { it.downloadId == downloadedId }

            MyApplication.downloadList.remove(item)
            adapterInProgress.deleteData(item!!)

            mSharedViewModel.completedDownloadLiveData.postValue(item.name)
        })
    }

    override fun getLayoutId() = R.layout.fragment_in_progress

    override fun onItemClicked(item: ProgressModel) {
        if (item.isPaused) {
            resumeDownload(item.downloadId)
        } else {
            pauseDownload(item.downloadId)
        }
    }

    private fun resumeDownload(downloadId: Long): Int {
        if (downloadId >= 0) {
            try {
                val mResolver: ContentResolver = requireContext().contentResolver
                val mBaseUri = Uri.parse("content://downloads/all_downloads")
                val values = ContentValues()
                //values.put(Downloads.Impl.COLUMN_CONTROL, Downloads.Impl.CONTROL_RUN);
                //values.put(Downloads.Impl.COLUMN_STATUS, Downloads.Impl.STATUS_RUNNING);
                values.put("control", 0)
                values.put("status", 192)

                return mResolver.update(
                    ContentUris.withAppendedId(mBaseUri, downloadId), values,
                    null, null
                )
            } catch (e: java.lang.Exception) {
                Log.d("Home", "Resuming encountered an error")
            }
        } else {
            Log.d("Home", "Download id not found")
        }
        return -1
    }

    private fun pauseDownload(downloadId: Long): Int {
        if (downloadId >= 0) {
            try {
                val mResolver: ContentResolver = requireContext().contentResolver
                val mBaseUri: Uri = Uri.parse("content://downloads/all_downloads")
                val values = ContentValues()
                //values.put(Downloads.Impl.COLUMN_CONTROL, Downloads.Impl.CONTROL_PAUSED);
                //values.put(Downloads.Impl.COLUMN_STATUS, Downloads.Impl.STATUS_PAUSED_BY_APP);
                values.put("control", 1)
                values.put("status", 193)
                return mResolver.update(
                    ContentUris
                        .withAppendedId(mBaseUri, downloadId), values,
                    null, null
                )
            } catch (e: Exception) {
                Log.d("InProgress", "Pausing encountered an error")
            }
        } else {
            Log.d("InProgress", "Download id not found")
        }
        return -1
    }
}