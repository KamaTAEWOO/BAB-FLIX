package com.meronacompany.design.common

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import timber.log.Timber

@Composable
fun YoutubePlayer(videoId: String) {
    // State to track if it's the first load or not
    val isFirstLoad = remember { mutableStateOf(true) }

    // Initialize the YouTube player only after the first update
    if (!isFirstLoad.value) {
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
                        youTubePlayer.cueVideo(videoId, 0f)
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

                // Mark that initialization is complete after the first load
                isFirstLoad.value = false

                youTubePlayerView
            },
            update = { playerView ->
                // You can add other update logic here if needed
            }
        )
    } else {
        Timber.e("YouTube player is not initialized")
        isFirstLoad.value = false // Reset the state to false
    }
}