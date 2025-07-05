package com.meronacompany.design.common

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    loading: @Composable (() -> Unit)? = { CircularProgressIndicator(modifier = Modifier.size(20.dp)) },
    voteAverage: Double? = 0.0
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
            val density = LocalContext.current.resources.displayMetrics.density
            val bitmapWidthDp = imageBitmap?.width?.div(density)?.dp ?: 130.dp
            val bitmapHeightDp = imageBitmap?.height?.div(density)?.dp ?: 187.dp
            Image(
                painter = painterResource(id = R.drawable.ic_no_poster),
                contentDescription = "No image background",
                modifier = Modifier.size(bitmapWidthDp, bitmapHeightDp)
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

        voteAverage?.let {
            VoteAverageTag(it, modifier = Modifier.align(Alignment.TopStart))
        }
    }
}

@Composable
private fun VoteAverageTag(voteAverage: Double, modifier: Modifier = Modifier) {
    val (label, backgroundColor, textColor) = when {
        voteAverage >= 9.5 -> Triple("BEST", MaterialTheme.colorScheme.outlineVariant, Color.Black)
        voteAverage >= 8.5 -> Triple("HOT", MaterialTheme.colorScheme.surfaceVariant, Color.White)
        else -> null
    } ?: return

    Box(
        modifier = modifier
            .offset(x = 28.dp, y = 6.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(bottomEnd = 8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            style = MaterialTheme.typography.labelSmall
        )
    }
}