package com.example.myvideodownloader.utils

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast

class DownloadReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            Toast.makeText(context, "${dm.getUriForDownloadedFile(id)} is downloaded", Toast.LENGTH_LONG).show()

            MyApplication.completedDownloadLiveData.postValue(id)
        }
    }
}
