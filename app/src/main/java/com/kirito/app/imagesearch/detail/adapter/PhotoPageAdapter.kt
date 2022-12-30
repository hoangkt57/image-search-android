package com.kirito.app.imagesearch.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kirito.app.common.Photo
import com.kirito.app.imagesearch.databinding.ItemPhotoDetailBinding

class PhotoPageAdapter : RecyclerView.Adapter<PhotoPageViewHolder>() {

    private val items: ArrayList<Photo> = arrayListOf()

    fun setItems(list: List<Photo>) {
        items.clear()
        items.addAll(list)
    }

    fun getItems() = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoPageViewHolder {
        return PhotoPageViewHolder(
            ItemPhotoDetailBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoPageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}