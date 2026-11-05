package fhnw.emoba.blockbuster.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fhnw.emoba.blockbuster.data.Person
import fhnw.emoba.blockbuster.ui.components.MovieCard
import fhnw.emoba.blockbuster.ui.components.PersonCard
import fhnw.emoba.blockbuster.viewmodel.BlockbusterModel

@Composable
fun PeopleScreen(
    viewModel: BlockbusterModel
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = {
                viewModel.searchQuery = it
                if (it.isBlank()) {
                    viewModel.clearSearchResults()
                } else {
                    viewModel.searchPeople()
                }
            },
            placeholder = { Text("Which Actor are you looking for?") },
            keyboardActions = KeyboardActions(
                onSearch = { viewModel.searchPeople() }),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            trailingIcon = {
                IconButton(onClick = { viewModel.clearSearchResults() }) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                }
            }

        )

        LazyColumn() {
            items(viewModel.people) { person ->
                PersonCard(
                    person = person,
                    onClick = { viewModel.selectPerson(person.id) }
                )
            }
        }
    }
}