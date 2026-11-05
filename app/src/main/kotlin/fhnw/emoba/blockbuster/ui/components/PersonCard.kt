package fhnw.emoba.blockbuster.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fhnw.emoba.blockbuster.data.Movie
import fhnw.emoba.blockbuster.data.Person

@Composable
fun PersonCard(
    person: Person,
    onClick: (Person) -> Unit,
    modifier: Modifier = Modifier,
) {

    Card(
        onClick = { onClick(person) },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
//            person.poster?.let {
//                Image(
//                    it,
//                    contentDescription = person.name,
//                    modifier = Modifier
//                        .width(100.dp)
//                        .padding(end = 16.dp),
//                    contentScale = ContentScale.Fit,
//                    alignment = Alignment.Center,
//                )
//            }
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = person.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {



                }

            }
        }
    }
}