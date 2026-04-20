package fhnw.emoba.blockbuster.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhnw.emoba.blockbuster.data.MovieDetails
import fhnw.emoba.blockbuster.ui.components.TrailerPlayer
import fhnw.emoba.blockbuster.viewmodel.BlockbusterModel
import fhnw.emoba.blockbuster.viewmodel.ScreenID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DetailScreen(
    viewModel: BlockbusterModel
) {
    val movie = viewModel.selectedMovie

    if (movie == null) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        PosterImage(
            posterUrl = movie.posterUrl,
            title = movie.title,
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                DetailStat(
                    label = "Release",
                    value = movie.release_date.ifBlank { "N/A" },
                )
                DetailStat(
                    label = "Vote",
                    value = String.format(Locale.getDefault(), "%.1f / 10", movie.vote_average),
                )
                DetailStat(
                    label = "Budget",
                    value = formatMoney(movie.budget),
                )
                DetailStat(
                    label = "Revenue",
                    value = formatMoney(movie.revenue),
                )
            }
        }

        Text(
            text = movie.overview.ifBlank { "No description available." },
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 24.sp,
        )

        if (viewModel.selectedMovieTrailer?.key?.isNotBlank() == true) {
            TrailerPlayer(viewModel.selectedMovieTrailer!!.key)
        }
    }
}

@Composable
private fun PosterImage(
    posterUrl: String,
    title: String,
) {
    val posterState = rememberPosterImage(posterUrl)

    Card {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            when {
                posterUrl.isBlank() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f / 3f),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "No poster available",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                posterState.value != null -> {
                    Image(
                        bitmap = posterState.value!!,
                        contentDescription = title,
                        modifier = Modifier
                            .width(200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop,
                    )
                }
                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun rememberPosterImage(posterUrl: String) = produceState<ImageBitmap?>(initialValue = null, key1 = posterUrl) {
    if (posterUrl.isBlank()) {
        value = null
        return@produceState
    }

    value = withContext(Dispatchers.IO) {
        runCatching {
            URL(posterUrl).openStream().use { input ->
                BitmapFactory.decodeStream(input)?.asImageBitmap()
            }
        }.getOrNull()
    }
}

@Composable
private fun DetailStat(
    label: String,
    value: String,
) {
    Column(
        modifier = Modifier.padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
        )
    }
}

private fun formatMoney(amount: Long): String {
    if (amount <= 0L) return "N/A"
    return NumberFormat.getCurrencyInstance(Locale.US).format(amount)
}
