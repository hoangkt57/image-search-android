package com.kirito.app.imagesearch.detail.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.kirito.app.common.KEY_PHOTO_ID
import com.kirito.app.imagesearch.detail.DetailActivity

class StartDetailForResult : ActivityResultContract<Int, Int>() {

    override fun createIntent(context: Context, input: Int): Intent {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(KEY_PHOTO_ID, input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int {
        if (resultCode != Activity.RESULT_OK) {
            return 0
        }
        return intent?.getIntExtra(KEY_PHOTO_ID, 0) ?: 0
    }
}