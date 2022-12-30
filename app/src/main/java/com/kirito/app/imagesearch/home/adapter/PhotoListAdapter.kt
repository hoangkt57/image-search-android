package com.kirito.app.imagesearch.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kirito.app.common.Photo
import com.kirito.app.imagesearch.databinding.ItemPhotoListBinding
import com.kirito.app.imagesearch.view.setOnSingleClickListener

class PhotoListAdapter : ListAdapter<Photo, PhotoListViewHolder>(PhotoDiffCallBack()) {

    var listener: OnItemClickListener? = null

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        val binding = ItemPhotoListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val holder = PhotoListViewHolder(binding)
        binding.root.setOnSingleClickListener {
            getItem(holder.absoluteAdapterPosition)?.let { photo ->
                listener?.onItemClick(photo)
            }
        }
        return holder
    }
}