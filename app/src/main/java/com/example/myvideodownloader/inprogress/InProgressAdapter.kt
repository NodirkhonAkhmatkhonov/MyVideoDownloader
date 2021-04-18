package com.example.myvideodownloader.inprogress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myvideodownloader.R
import com.example.myvideodownloader.databinding.InProgressItemBinding

class InProgressAdapter(
    private var items: MutableList<ProgressModel> = mutableListOf(),
    private val itemClickListener: OnProgressItemClickListener
): RecyclerView.Adapter<InProgressAdapter.MyViewHolder>(){

    inner class MyViewHolder(private val itemBinding: InProgressItemBinding) :
            RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(model: ProgressModel) {
            itemBinding.ivThumbnail.setImageBitmap(model.bitmap)
            itemBinding.tvName.text = model.name

            itemBinding.playPause.setOnClickListener {
                itemClickListener.onItemClicked(model)
                model.isPaused = !model.isPaused

                if (model.isPaused) {
                    itemBinding.playPause.setImageResource(R.drawable.ic_play)
                } else {
                    itemBinding.playPause.setImageResource(R.drawable.ic_pause)
                }
            }
        }
    }

    fun insertData(item: ProgressModel) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
        notifyDataSetChanged()
    }

    fun deleteData(item: ProgressModel) {
        val position = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = InProgressItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount() = items.size
}

interface OnProgressItemClickListener{
    fun onItemClicked(video: ProgressModel)
}