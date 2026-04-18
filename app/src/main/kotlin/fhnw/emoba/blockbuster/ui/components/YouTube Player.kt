package fhnw.emoba.blockbuster.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

@Composable
fun TrailerPlayer(videoId: String) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val playerView = remember(videoId) {
        YouTubePlayerView(context).apply {
            enableAutomaticInitialization = false
            initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            })
        }
    }

    DisposableEffect(playerView, lifecycle) {
        lifecycle.addObserver(playerView)

        onDispose {
            lifecycle.removeObserver(playerView)
            playerView.release()
        }
    }

    AndroidView(
        factory = { playerView },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}
