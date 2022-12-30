package com.kirito.app.imagesearch.detail.adapter

import android.content.res.Resources
import android.util.Size
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kirito.app.common.Photo
import com.kirito.app.image_loader.ImageLoader
import com.kirito.app.imagesearch.R
import com.kirito.app.imagesearch.databinding.ItemPhotoDetailBinding

class PhotoPageViewHolder(private val binding: ItemPhotoDetailBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Photo?) {
        if (item == null) {
            return
        }
        val color = ContextCompat.getColor(itemView.context, R.color.image_placeholder)
        val width = Resources.getSystem().displayMetrics.widthPixels
        ImageLoader().with(binding.root)
            .id(item.id)
            .size(Size(width, width * item.height / item.width))
            .load(item.large2xUrl)
            .placeholder(color)
            .execute()
    }
}