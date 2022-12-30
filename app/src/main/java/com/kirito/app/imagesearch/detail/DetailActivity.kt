package com.kirito.app.imagesearch.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import com.kirito.app.common.KEY_PHOTO_ID
import com.kirito.app.imagesearch.databinding.ActivityDetailBinding
import com.kirito.app.imagesearch.detail.adapter.PhotoPageAdapter
import com.kirito.app.imagesearch.view.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val photoId by lazy {
        intent?.getIntExtra(KEY_PHOTO_ID, 0) ?: 0
    }

    private val viewBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<DetailViewModel>()

    private val adapter by lazy {
        PhotoPageAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        initView()
        updatePhotos()
    }

    override fun onBackPressed() {
        viewModel.updatePhotos()
        val currentPosition = viewBinding.viewPager.currentItem
        val intent = Intent()
        intent.putExtra(KEY_PHOTO_ID, currentPosition)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun initView() {
        viewBinding.viewPager.adapter = adapter

        viewBinding.delete.setOnSingleClickListener {
            viewModel.deletePhoto(viewBinding.viewPager.currentItem)
        }
    }

    private fun updatePhotos() {
        viewModel.photos.observe(this) { newList ->
            val oldList = adapter.getItems()
            val isFirst = oldList.isEmpty()
            DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int = oldList.size
                override fun getNewListSize(): Int = newList.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    oldList[oldItemPosition].id == newList[newItemPosition].id

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    oldList[oldItemPosition] == newList[newItemPosition]
            }, true).dispatchUpdatesTo(viewBinding.viewPager.adapter!!)
            adapter.setItems(newList)
            if (isFirst) {
                val position = viewModel.findCurrentPosition(photoId)
                viewBinding.viewPager.setCurrentItem(position, false)
            }
        }
    }
}