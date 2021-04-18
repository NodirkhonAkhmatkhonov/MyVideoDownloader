package com.example.myvideodownloader.player

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myvideodownloader.databinding.ActivityPlayerBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class PlayerActivity: AppCompatActivity(){
    private val TAG = "PlayerActivity"
    private lateinit var mViewDataBinding: ActivityPlayerBinding
    private lateinit var player: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewDataBinding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(mViewDataBinding.root)

        val path = intent.getStringExtra("Path").toString()
        player = SimpleExoPlayer.Builder(this).build()
        mViewDataBinding.exoPlayer.player = player

        val mediaItem = MediaItem.fromUri(path)
        player.setMediaItem(mediaItem)
        player.prepare()

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        player.seekTo(sharedPref.getLong("position", 0))
        player.play()

    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ${player.currentPosition}")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ${player.currentPosition}")
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putLong("position", player.currentPosition)
            apply()
        }
        player.release()
    }
}