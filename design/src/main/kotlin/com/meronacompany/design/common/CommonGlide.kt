package com.meronacompany.design.common

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import androidx.compose.ui.graphics.asImageBitmap
import com.meronacompany.core.BuildConfig
import timber.log.Timber

@Composable
fun GlideImage(
    path: String,
    contentDescription: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    loading: @Composable (() -> Unit)? = { CircularProgressIndicator(modifier = Modifier.size(40.dp)) },
) {
    val context = LocalContext.current
    var imageBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    Timber.d("GlideImage: path=$path")

    LaunchedEffect(path) {
        isLoading = true
        Glide.with(context)
            .asBitmap() // Bitmap으로 변환
            .load("${BuildConfig.BASE_URL}$path")
            .into(object : CustomTarget<android.graphics.Bitmap>() {
                override fun onResourceReady(resource: android.graphics.Bitmap, transition: Transition<in android.graphics.Bitmap>?) {
                    imageBitmap = resource
                    isLoading = false
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    imageBitmap = null
                    isLoading = false
                }
            })
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            loading?.invoke()
        } else {
            imageBitmap?.let { bitmap ->
                Image(
                    painter = BitmapPainter(bitmap.asImageBitmap()),
                    contentDescription = contentDescription,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale,
                    alignment = alignment
                )
            }
        }
    }
}