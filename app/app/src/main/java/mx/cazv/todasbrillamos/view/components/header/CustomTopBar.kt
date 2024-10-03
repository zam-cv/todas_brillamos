package mx.cazv.todasbrillamos.view.components.header

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(content: @Composable () -> Unit) {
    TopAppBar(
        title = {
            content()
        }
    )
}