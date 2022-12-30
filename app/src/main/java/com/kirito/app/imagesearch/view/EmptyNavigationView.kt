package com.kirito.app.imagesearch.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EmptyNavigationView : View {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            if (layoutParams.height != inset.bottom && inset.bottom > 0) {
                layoutParams.height = inset.bottom
                requestLayout()
            }
            if (layoutParams.width != inset.right && inset.right > 0) {
                layoutParams.width = inset.right
                requestLayout()
            }
            insets
        }
    }


}