package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.layouts.StaticLayout
import mx.cazv.todasbrillamos.view.screens.store.CategoryFilter
import mx.cazv.todasbrillamos.view.screens.store.ToggleView
import mx.cazv.todasbrillamos.view.screens.store.ViewProducts


@Composable
fun Store(navController: NavHostController) {
    var viewType by remember { mutableStateOf("grid") }
    StaticLayout(navController = navController) {
        Column (modifier = Modifier
            .fillMaxSize()
        ){
            ToggleView(
                selectedType = viewType,
                onSelectionChange = { newType -> viewType = newType }
            )
            CategoryFilter()
            Box (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                ViewProducts(type = viewType)
            }
        }
    }
}