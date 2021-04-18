package com.example.myvideodownloader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.myvideodownloader.databinding.ActivityMainBinding
import com.example.myvideodownloader.utils.MyAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)

        mViewBinding.tabLayout.addTab(mViewBinding.tabLayout.newTab().setText(R.string.homeFragment), true)
        mViewBinding.tabLayout.addTab(mViewBinding.tabLayout.newTab().setText(R.string.inProgressFragment))
        mViewBinding.tabLayout.addTab(mViewBinding.tabLayout.newTab().setText(R.string.downloadsFragment))

        initTabLayout()
    }

    private fun initTabLayout() {
        val tabAdapter = MyAdapter(this, 3)
        mViewBinding.viewPager.adapter = tabAdapter

        mViewBinding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mViewBinding.viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        mViewBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mViewBinding.tabLayout.selectTab(mViewBinding.tabLayout.getTabAt(position))
            }
        })
    }
}