package mx.cazv.todasbrillamos.view.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.components.BottomBar
import mx.cazv.todasbrillamos.view.components.StoreButton

@Composable
fun CustomLayoutNoBottomBar(
    navController: NavHostController,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold (
        topBar = { topBar() },
        bottomBar = { bottomBar() }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}