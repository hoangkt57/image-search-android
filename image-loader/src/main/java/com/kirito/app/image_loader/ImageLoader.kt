package com.kirito.app.image_loader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import android.util.Size
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ImageLoader {

    companion object {
        private val TAG = ImageLoader::class.java.name
    }

    private var view: ImageView? = null
    private var id: Int = 0
    private var url: String = ""
    private var size: Size = Size(0, 0)
    private var placeholder: Int = 0

    fun with(view: ImageView) = apply { this.view = view }

    fun id(id: Int) = apply { this.id = id }

    fun load(url: String) = apply { this.url = url }

    fun size(size: Size) = apply { this.size = size }

    fun placeholder(placeholder: Int) = apply { this.placeholder = placeholder }

    fun execute() {
        try {
            if (id == 0 || size.width == 0 || size.height == 0 || url.isEmpty()) {
                return
            }
            val view = this.view ?: return
            val activity = (view.context as AppCompatActivity)
            val fileName = "$id/${id}_$size.jpg"
            view.tag = fileName
            val bmp = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            canvas.drawColor(placeholder)
            view.setImageBitmap(bmp)
            activity.lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val originalFileName = detectFileName(url)
                    val file = File(activity.cacheDir, "$id/$originalFileName")
                    if (!file.exists()) {
                        file.parentFile?.mkdirs()
                        ImageDownloader.downloadImage(file, url)
                    }
                    val resizeFile = File(activity.cacheDir, fileName)
                    if (!resizeFile.exists()) {
                        resizeFile.parentFile?.mkdirs()
                        resizeFile(file, resizeFile, size)
                    }
                    val bitmap = BitmapFactory.decodeStream(resizeFile.inputStream())
                    withContext(Dispatchers.Main) {
                        if (view.tag == fileName) {
                            view.setImageBitmap(bitmap)
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "execute", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "execute", e)
        }
    }
}