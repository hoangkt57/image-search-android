package com.kirito.app.image_loader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.Size
import java.io.File

const val TAG = "FileUtil"

internal fun detectFileName(url: String): String {
    try {
        val split = url.split("?")
        val last = split[0].lastIndexOf("/")
        val name = split[0].substring(last + 1, split[0].length)
        val option = split[1].replace("=", "").replace("&", "-")
        val splitName = name.split(".")
        return splitName[0] + "-" + option + "." + splitName[1]
    } catch (e: Exception) {
        Log.e(TAG, "detectFileName", e)
    }
    return ""
}

internal fun resizeFile(file: File, newFile: File, size: Size) {
    try {
        val bitmap = BitmapFactory.decodeStream(file.inputStream())
        val newBitmap = Bitmap.createScaledBitmap(bitmap, size.width, size.height, true)
        newFile.outputStream().use {
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, it)
        }
        newBitmap.recycle()
        bitmap.recycle()
    } catch (e: Exception) {
        Log.e(TAG, "resizeFile", e)
    }
}