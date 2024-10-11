package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.layouts.StaticLayout
import mx.cazv.todasbrillamos.view.screens.store.CategoryFilter
import mx.cazv.todasbrillamos.view.screens.store.ToggleView
import mx.cazv.todasbrillamos.view.screens.store.ViewProducts
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.ProductsViewModel

/**
 * Pantalla de la tienda que muestra los productos disponibles y permite cambiar la vista y filtrar por categoría.
 * @author Carlos Zamudio
 * @author Min Che Kim
 *
 *  @param navController El NavHostController utilizado para la navegación.
 *  @param authViewModel El ViewModel de autenticación utilizado para obtener el token del usuario.
 *  @param productsViewModel El ViewModel de productos utilizado para cargar y gestionar los productos.
 */
@Composable
fun Store(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    productsViewModel: ProductsViewModel
) {
    val productsState = productsViewModel.state.collectAsState()
    var viewType by remember { mutableStateOf("grid") }
    var selectedCategory by remember { mutableStateOf("Ver todos") }

    LaunchedEffect(key1 = Unit) {
        val token = authViewModel.token()

        if (token != null) {
            productsViewModel.load(token, selectedCategory)
        }
    }

    LaunchedEffect(selectedCategory) {
        val token = authViewModel.token()
        if (token != null) {
            productsViewModel.load(token, selectedCategory)
        }
    }

    StaticLayout(navController = navController) {
        Column (modifier = Modifier
            .fillMaxSize()
        ){
            ToggleView(
                selectedType = viewType,
                onSelectionChange = { newType -> viewType = newType }
            )

            CategoryFilter(
                categories = productsState.value.categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    selectedCategory = category
                }
            )

            Box (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                ViewProducts(
                    type = viewType,
                    products = productsState.value.products,
                    navController
                )
            }
        }
    }
}