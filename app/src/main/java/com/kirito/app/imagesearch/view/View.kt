package com.kirito.app.imagesearch.view

import android.view.View

fun View.setOnSingleClickListener(block: () -> Unit) {
    setOnClickListener(OnSingleClickListener(block))
}