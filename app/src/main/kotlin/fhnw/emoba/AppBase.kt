package fhnw.emoba

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.annotation.EmptySuper
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LifecycleStartEffect

abstract class AppBase<T : ViewModel>() {

    @Composable
    abstract fun createViewModel(activity: ComponentActivity): T

    /**
     * Das gesamte UI der App
     */
    @Composable
    abstract fun AppUi(viewModel: T)

    @EmptySuper
    open fun onStop(viewModel: T, activity: ComponentActivity) {}

    @Composable
    fun Content() {
        val activity = LocalActivity.current as? ComponentActivity
            ?: throw IllegalArgumentException("UI is not attached to a ComponentActivity")
        val viewModel = createViewModel(activity)

        LifecycleStartEffect(Unit) {
            onStopOrDispose { onStop(viewModel, activity) }
        }

        AppUi(viewModel)
    }
}