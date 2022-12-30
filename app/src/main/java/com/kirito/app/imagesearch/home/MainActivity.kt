package com.kirito.app.imagesearch.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kirito.app.common.PAGE_SIZE
import com.kirito.app.common.Photo
import com.kirito.app.imagesearch.databinding.ActivityMainBinding
import com.kirito.app.imagesearch.detail.contract.StartDetailForResult
import com.kirito.app.imagesearch.home.adapter.OnItemClickListener
import com.kirito.app.imagesearch.home.adapter.PhotoListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemClickListener {

    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<MainViewModel>()

    private val adapter by lazy {
        PhotoListAdapter()
    }

    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(viewBinding.root)

        adapter.listener = this
        initView()
        updatePhotos()
    }

    override fun onDestroy() {
        super.onDestroy()

        viewBinding.editText.removeTextChangedListener(textWatcher)
        viewBinding.recyclerView.removeOnScrollListener(scrollListener)
        adapter.listener = null
    }

    private fun initView() {
        viewBinding.recyclerView.adapter = adapter
        viewBinding.recyclerView.addOnScrollListener(scrollListener)
        viewBinding.editText.addTextChangedListener(textWatcher)
    }

    private fun updatePhotos() {
        viewModel.photos.observe(this) { photos ->
            adapter.submitList(photos)
            if (photos.isEmpty()) {
                viewBinding.recyclerView.scrollToPosition(0)
                viewBinding.emptyText.visibility = View.VISIBLE
            } else {
                viewBinding.emptyText.visibility = View.GONE
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(newText: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (newText == null || newText.length < 2) {
                return
            }
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchPhoto(newText.toString().trim())
            }
        }

        override fun afterTextChanged(p0: Editable?) {

        }

    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
            val totalItemCount = layoutManager.itemCount
            val pastVisibleItems = layoutManager.findLastVisibleItemPositions(null)
            if (pastVisibleItems[0] + PAGE_SIZE > totalItemCount) {
                viewModel.loadMorePage()
            }
        }
    }

    override fun onItemClick(photo: Photo) {
        viewModel.savePhotos()
        startForResult.launch(photo.id)
    }

    private val startForResult = registerForActivityResult(StartDetailForResult()) { result ->
        viewBinding.recyclerView.scrollToPosition(result)
        viewModel.updatePhotos()
    }

}