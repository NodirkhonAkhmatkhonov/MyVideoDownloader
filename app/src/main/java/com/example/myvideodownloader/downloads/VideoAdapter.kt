package com.example.myvideodownloader.downloads

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myvideodownloader.databinding.VideoItemBinding
import java.util.*


class VideoAdapter(private var items: ArrayList<VideoModel>, private val itemClickListener: OnItemClickListener): RecyclerView.Adapter<VideoAdapter.MyViewHolder>(){

    inner class MyViewHolder(private val itemBinding: VideoItemBinding) :
            RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(model: VideoModel, clickListener: OnItemClickListener) {
            itemBinding.ivThumbnail.setImageBitmap(model.bitmap)
            itemBinding.tvName.text = model.name

            itemBinding.itemview.setOnClickListener {
                clickListener.onItemClicked(model)
            }
        }
    }

    fun dataChanged(item: VideoModel) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(items[position], itemClickListener)
    }

    override fun getItemCount() = items.size
}

interface OnItemClickListener{
    fun onItemClicked(video: VideoModel)
}