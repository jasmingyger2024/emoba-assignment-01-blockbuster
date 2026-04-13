package fhnw.emoba.blockbuster.viewmodel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.People
import androidx.compose.ui.graphics.vector.ImageVector


enum class ScreenID (val title: String, val showNav: Boolean, val icon: ImageVector? = null){

    FAVORITES("Favorites", true, Icons.Filled.Favorite),
    MOVIES("Movies", true, Icons.Filled.Movie),
    PEOPLE("Actors", true, Icons.Filled.People),
    DETAIL("", false)
}