package fhnw.emoba.blockbuster.ui

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fhnw.emoba.R
import fhnw.emoba.blockbuster.viewmodel.BlockbusterModel
import fhnw.emoba.blockbuster.ui.theme.MoviesTheme
import fhnw.emoba.blockbuster.viewmodel.ScreenID
import fhnw.emoba.blockbuster.ui.screens.FavoritesScreen
import fhnw.emoba.blockbuster.ui.screens.MoviesScreen
import fhnw.emoba.blockbuster.ui.screens.PeopleScreen
import fhnw.emoba.blockbuster.ui.screens.DetailScreen



@Composable
fun BlockbusterUI(viewModel : BlockbusterModel){
    MoviesTheme {
        with(viewModel) {

            Scaffold(
                topBar = { topBar(viewModel) },
                content = { Body(viewModel, it) },
                bottomBar = { TabNav(viewModel) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun topBar(viewModel: BlockbusterModel) {
    with(viewModel) {
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painterResource(R.drawable.movie_db_logo), "Movie DB Logo", Modifier.height(30.dp))
                    Text(
                        text = selectedScreen.title,
                        modifier = Modifier.padding(start = 20.dp),
                        fontWeight = FontWeight.W700
                    )
                }
            }
        )
    }
}

@Composable
private fun Body(viewModel: BlockbusterModel, paddingValues: PaddingValues) {
    with(viewModel) {
        Crossfade(
            targetState = selectedScreen,
            animationSpec = tween(durationMillis = 3000, easing = Ease),
            modifier = Modifier.padding(paddingValues)
            ) { screen ->
                when (screen) {
                    ScreenID.FAVORITES  -> FavoritesScreen( navigateToScreen = { navigateTo(it) })
                    ScreenID.MOVIES     -> MoviesScreen(    navigateToScreen = { navigateTo(it) })
                    ScreenID.PEOPLE     -> PeopleScreen(    navigateToScreen = { navigateTo(it) })
                    ScreenID.DETAIL     -> DetailScreen(    navigateToScreen = { navigateTo(it) }, navigateBack = { navigateBack() })
                }

        }
    }
}

@Composable
private fun TabNav(viewModel: BlockbusterModel) {
    with(viewModel) {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            PrimaryTabRow(selectedTabIndex = selectedScreen.ordinal) {
                for (screen in ScreenID.entries) {
                    if (screen.showNav) {
                        Tab(
                            text = { Text(screen.title) },
                            icon = { screen.icon?.let { Icon(it, "icon") } },
                            selected = screen == selectedScreen,
                            onClick = { navigateTo(screen) },
                        )
                    }
                }
            }
        }
    }

    /** If there's an issue with the details page
     * with(viewModel) {
     *         // Only show the BottomAppBar if the current screen is configured to show navigation.
     *         // This avoids IndexOutOfBoundsException when selectedScreen is not in the tab list (like ScreenID.DETAIL).
     *         if (selectedScreen.showNav) {
     *             val navScreens = ScreenID.entries.filter { it.showNav }
     *             val selectedIndex = navScreens.indexOf(selectedScreen)
     *
     *             BottomAppBar(
     *                 containerColor = MaterialTheme.colorScheme.background,
     *             ) {
     *                 // Ensure the index is relative to the filtered list of screens shown in the TabRow
     *                 PrimaryTabRow(selectedTabIndex = selectedIndex) {
     *                     for (screen in navScreens) {
     *                         Tab(
     *                             text = { Text(screen.title) },
     *                             icon = { screen.icon?.let { Icon(it, "icon") } },
     *                             selected = screen == selectedScreen,
     *                             onClick = { navigateTo(screen) },
     *                         )
     *                     }
     *                 }
     *             }
     *         }
     *     }**/
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun Preview(){
    BlockbusterUI(BlockbusterModel())
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun FavoritesScreenPreview() {
    val vm = BlockbusterModel()
    vm.selectedScreen = ScreenID.FAVORITES
    BlockbusterUI(viewModel = vm)
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun MoviesScreenPreview() {
    val vm = BlockbusterModel()
    vm.selectedScreen = ScreenID.MOVIES
    BlockbusterUI(viewModel = vm)
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun PeopleScreenPreview() {
    val vm = BlockbusterModel()
    vm.selectedScreen = ScreenID.PEOPLE
    BlockbusterUI(viewModel = vm)
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun DetailScreenPreview() {
    val vm = BlockbusterModel()
    vm.selectedScreen = ScreenID.DETAIL
    BlockbusterUI(viewModel = vm)
}
