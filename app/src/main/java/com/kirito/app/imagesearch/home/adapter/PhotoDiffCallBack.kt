package com.kirito.app.imagesearch.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kirito.app.common.Photo

class PhotoDiffCallBack : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}