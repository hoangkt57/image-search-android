package com.kirito.app.image_loader

import android.util.Log
import java.io.File
import java.net.HttpURLConnection
import java.net.URL


internal object ImageDownloader {

    private val TAG = ImageDownloader::class.java.name

    fun downloadImage(file: File, imageUrl: String) {
        try {
            val url = URL(imageUrl)
            val httpConn = url.openConnection() as HttpURLConnection
            val responseCode = httpConn.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                httpConn.inputStream.use { inputStream ->
                    file.outputStream().use { fileOut ->
                        inputStream.copyTo(fileOut)
                    }
                }
            } else {
                Log.e(TAG, "downloadImage - error - HTTP code: $responseCode")
            }
            httpConn.disconnect()
        } catch (e: Exception) {
            Log.e(TAG, "downloadImage - $e")
        }
    }
}