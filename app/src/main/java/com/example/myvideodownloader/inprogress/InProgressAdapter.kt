package com.example.myvideodownloader.inprogress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myvideodownloader.databinding.InProgressItemBinding
import com.example.myvideodownloader.downloads.OnItemClickListener
import com.example.myvideodownloader.downloads.VideoModel
import java.util.*


class InProgressAdapter(private var items: ArrayList<VideoModel>, private val itemClickListener: OnProgressItemClickListener): RecyclerView.Adapter<InProgressAdapter.MyViewHolder>(){

    inner class MyViewHolder(private val itemBinding: InProgressItemBinding) :
            RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(model: VideoModel, clickListener: OnProgressItemClickListener) {
            itemBinding.ivThumbnail.setImageBitmap(model.bitmap)
            itemBinding.tvName.text = model.name

            itemBinding.playPause.setOnClickListener {
                itemClickListener.onItemClicked(model)
            }
        }
    }

    fun dataChanged(item: VideoModel) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = InProgressItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(items[position], itemClickListener)
    }

    override fun getItemCount() = items.size
}

interface OnProgressItemClickListener{
    fun onItemClicked(video: VideoModel)
}