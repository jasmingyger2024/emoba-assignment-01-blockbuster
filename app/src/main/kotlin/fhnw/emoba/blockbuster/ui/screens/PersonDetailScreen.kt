package fhnw.emoba.blockbuster.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhnw.emoba.blockbuster.viewmodel.BlockbusterModel

@Composable
fun PersonDetailScreen(
    viewModel: BlockbusterModel,
) {
    val person = viewModel.selectedPerson

    if (person == null) {
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
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    PersonStat(label = "Born", value = person.birthday.orNA())
                    PersonStat(label = "Died", value = person.deathday.orNA())
                    PersonStat(label = "Popularity", value = String.format("%.1f", person.popularity))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    PersonStat(label = "Adult", value = if (person.adult) "Yes" else "No")
                    PersonStat(label = "Gender", value = formatGender(person.gender))
                    PersonStat(label = "Homepage", value = person.homepage.orNA())
                }
            }
        }

        if (!person.place_of_birth.isNullOrBlank()) {
            Text(
                text = "Place of birth",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = person.place_of_birth,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        if (person.also_known_as.isNotEmpty()) {
            Text(
                text = "Also known as",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = person.also_known_as.joinToString(", "),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Text(
            text = "Biography",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = person.biography.ifBlank { "No biography available." },
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 24.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun PersonStat(
    label: String,
    value: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
        )
    }
}

private fun formatGender(gender: Int): String {
    return when (gender) {
        1 -> "Female"
        2 -> "Male"
        3 -> "Non-binary"
        else -> "Unknown"
    }
}

private fun String?.orNA(): String = if (this.isNullOrBlank()) "N/A" else this
