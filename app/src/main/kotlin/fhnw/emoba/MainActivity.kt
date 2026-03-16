package fhnw.emoba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import fhnw.emoba.blockbuster.BlockbusterApp


class MainActivity : ComponentActivity() {
    private lateinit var app: AppBase<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = BlockbusterApp

        setContent {
            app.Content()
        }
    }
}