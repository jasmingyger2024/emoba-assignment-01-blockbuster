package fhnw.emoba.blockbuster.viewmodel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.People
import androidx.compose.ui.graphics.vector.ImageVector


enum class ScreenID (val title: String, val showNav: Boolean, val pageTitle: String, val icon: ImageVector? = null){

    FAVORITES("Favorites", true, "Your Absolute Favorites", Icons.Filled.Favorite),
    MOVIES("Movies", true, "Looking for some Movies?", Icons.Filled.Movie ),
    PEOPLE("Actors", true,"Who's in the Movies?", Icons.Filled.People),
    DETAIL("", false, "change that!!!")
}