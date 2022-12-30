package com.kirito.app.imagesearch.home.adapter

import android.content.res.Resources
import android.util.Size
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kirito.app.common.Photo
import com.kirito.app.image_loader.ImageLoader
import com.kirito.app.imagesearch.R
import com.kirito.app.imagesearch.databinding.ItemPhotoListBinding

class PhotoListViewHolder(private val binding: ItemPhotoListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Photo?) {
        if (item == null) {
            return
        }

        binding.name.text = item.photographer

        val color = ContextCompat.getColor(itemView.context, R.color.image_placeholder)
        val margin = itemView.resources.getDimensionPixelSize(R.dimen.photo_item_margin)
        val spanCount = itemView.resources.getInteger(R.integer.photo_list_span_count)
        val width = (Resources.getSystem().displayMetrics.widthPixels - margin * 2 * spanCount) / spanCount
        ImageLoader().with(binding.image)
            .id(item.id)
            .size(Size(width, width * item.height / item.width))
            .load(item.large2xUrl)
            .placeholder(color)
            .execute()
    }
}