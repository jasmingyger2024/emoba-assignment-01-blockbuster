package fhnw.emoba.blockbuster.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import fhnw.emoba.blockbuster.viewmodel.BlockbusterModel


@Composable
fun AppUI(model : BlockbusterModel){
    with(model){
        Box(
            contentAlignment = Alignment.Center,
            modifier         = Modifier.fillMaxSize()
        ){
            Text(
                text  = title,
                style = TextStyle(fontSize = 42.sp)
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun Preview(){
    AppUI(BlockbusterModel())
}