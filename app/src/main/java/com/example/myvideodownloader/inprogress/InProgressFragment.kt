package com.example.myvideodownloader.inprogress

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.myvideodownloader.R
import com.example.myvideodownloader.base.BaseFragment
import com.example.myvideodownloader.databinding.FragmentInProgressBinding
import com.example.myvideodownloader.downloads.OnItemClickListener
import com.example.myvideodownloader.downloads.VideoModel
import com.example.myvideodownloader.home.HomeViewModel

class InProgressFragment: BaseFragment<FragmentInProgressBinding>(), InProgressNavigator,
    OnItemClickListener {

    private lateinit var mViewModel: InProgressViewModel
    private lateinit var adapter: InProgressAdapter

    override fun initView(view: View, mViewDataBinding: FragmentInProgressBinding) {
        mViewModel = ViewModelProvider(this).get(InProgressViewModel::class.java)
        mViewDataBinding.inProgressViewModel = mViewModel
        mViewDataBinding.lifecycleOwner = this
        mViewModel.navigator = this
    }

    override fun getLayoutId() = R.layout.fragment_in_progress
    override fun onItemClicked(video: VideoModel) {

    }
}