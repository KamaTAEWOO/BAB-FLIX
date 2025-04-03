package com.meronacompany.design.common

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.meronacompany.design.R

@Composable
fun CommonGlideImage(
    path: String,
    contentDescription: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    loading: @Composable (() -> Unit)? = { CircularProgressIndicator(modifier = Modifier.size(20.dp)) }, // TODO : 로딩 인디케이터
) {
    val context = LocalContext.current
    var imageBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val baseImageUrl = "https://image.tmdb.org/t/p/"
    val baseImageSize = "w342" // 원하는 사이즈 (w92, w154, w185, w342, w500, w780, original 등 가능)

    LaunchedEffect(path) {
        isLoading = true
        Glide.with(context)
            .asBitmap() // Bitmap으로 변환
            .load("$baseImageUrl$baseImageSize$path")
            .into(object : CustomTarget<android.graphics.Bitmap>() {
                override fun onResourceReady(
                    resource: android.graphics.Bitmap,
                    transition: Transition<in android.graphics.Bitmap>?
                ) {
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
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_no_poster),
                contentDescription = "No image background",
                modifier = Modifier
                    .size(130.dp, 187.dp)
            )
        }
        imageBitmap?.let { bitmap ->
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
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