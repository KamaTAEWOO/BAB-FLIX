package com.meronacompany.design.common

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber

@Composable
fun YoutubePlayer(
    videoId: String,
    videoIdList: MutableStateFlow<List<String>>
) {
    val scope = rememberCoroutineScope()
    var lastVideoId by remember { mutableStateOf("") }
    var debounceJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(videoId) {
        if (videoId.isNotEmpty()) {
            debounceJob?.cancel()
            debounceJob = scope.launch {
                delay(100)
                lastVideoId = videoId
                Timber.d("YoutubePlayer - Applied videoId after debounce: $lastVideoId")
            }
        }
    }

    if (lastVideoId.isEmpty()) return

    Timber.d("YoutubePlayer - videoId: $lastVideoId")
    AndroidView(
        factory = { context ->
            val youTubePlayerView = YouTubePlayerView(context)
            youTubePlayerView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            // Initialize the player when the view is created
            youTubePlayerView.addYouTubePlayerListener(object : YouTubePlayerListener {
                override fun onApiChange(youTubePlayer: YouTubePlayer) {
                    Timber.d("onApiChange")
                }

                override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                    Timber.d("onCurrentSecond")
                }

                override fun onError(
                    youTubePlayer: YouTubePlayer,
                    error: PlayerConstants.PlayerError
                ) {
                    Timber.e("onError: $error")
                    val currentIndex = videoIdList.value.indexOfFirst { it == lastVideoId }
                    val nextIndex = currentIndex + 1
                    if (nextIndex in videoIdList.value.indices) {
                        val nextVideoId = videoIdList.value[nextIndex]
                        if (nextVideoId.isNotEmpty()) {
                            lastVideoId = nextVideoId
                            Timber.d("YoutubePlayer - Switching to next videoId: $lastVideoId")
                        }
                    }
                }

                override fun onPlaybackQualityChange(
                    youTubePlayer: YouTubePlayer,
                    playbackQuality: PlayerConstants.PlaybackQuality
                ) {
                    Timber.d("onPlaybackQualityChange")
                }

                override fun onPlaybackRateChange(
                    youTubePlayer: YouTubePlayer,
                    playbackRate: PlayerConstants.PlaybackRate
                ) {
                    Timber.d("onPlaybackRateChange")
                }

                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(lastVideoId, 0f)
                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState
                ) {
                    Timber.d("onStateChange: $state")
                }

                override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                    Timber.d("onVideoDuration")
                }

                override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
                    Timber.d("onVideoId")
                }

                override fun onVideoLoadedFraction(
                    youTubePlayer: YouTubePlayer,
                    loadedFraction: Float
                ) {
                    Timber.d("onVideoLoadedFraction")
                }
            })

            youTubePlayerView
        },
        update = { playerView ->
            // You can add other update logic here if needed
        }
    )
}