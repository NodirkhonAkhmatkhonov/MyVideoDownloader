package com.example.myvideodownloader.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myvideodownloader.downloads.DownloadsFragment
import com.example.myvideodownloader.home.HomeFragment
import com.example.myvideodownloader.inprogress.InProgressFragment

class MyAdapter(activity: AppCompatActivity, private val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                InProgressFragment()
            }
            2 -> {
                DownloadsFragment()
            }
            else -> HomeFragment()
        }
    }
}