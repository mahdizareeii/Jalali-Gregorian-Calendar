package com.calendar.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

fun View.setBackgroundFromDrawable(@DrawableRes id: Int, @IntRange(from = 0, to = 255) alpha: Int = 255) {
    background = ContextCompat.getDrawable(
        context,
        id
    )?.apply {
        this.alpha = alpha
    }
}

fun ImageView.setMutableDrawableColor(color: Int?) {
    color ?: return
    DrawableCompat.wrap(drawable).also {
        it.mutate().also { drawable ->
            DrawableCompat.setTint(drawable, color)
        }
    }
}

fun Context.dp(value: Int): Int = getDp(this, value)

fun View.dp(value: Int): Int = getDp(context, value)

fun getDp(context: Context, value: Int) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    value.toFloat(),
    context.resources.displayMetrics
).toInt()