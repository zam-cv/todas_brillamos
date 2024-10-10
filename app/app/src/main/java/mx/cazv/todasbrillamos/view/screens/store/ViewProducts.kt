package mx.cazv.todasbrillamos.view.screens.store

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.model.models.ProductList
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.FavoritesViewModel

/**
 * Archivo para mostrar los productos en una vista de cuadrícula o lista.
 * @author Min Che Kim, Carlos Zamudio
 */

/**
 * Composable que muestra los productos en una vista de cuadrícula o lista, dependiendo del tipo especificado.
 */
@Composable
fun ViewProducts(
    type: String,
    products: ProductList,
    navController: NavHostController
) {
    if (type == "grid") {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentPadding = PaddingValues(
                top = 8.dp,
                bottom = 36.dp,
                start = 8.dp,
                end = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products.products) { product ->
                ProductGridItem(
                    product = product,
                    products.folder,
                    navController
                )
            }
        }
    } else if (type == "list") {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            items(products.products) { product ->
                ProductColumnItem(
                    product = product,
                    products.folder,
                    navController)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

/**
 * Composable que muestra los productos favoritos en una vista de cuadrícula.
 */
@Composable
fun ViewFavorites(
    authViewModel: AuthViewModel,
    favoritesViewModel: FavoritesViewModel,
    navController: NavHostController
) {
    val favoritesState by favoritesViewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        val token = authViewModel.token()
        if (token != null) {
            favoritesViewModel.loadFavorites(token)
        }
    }

    when {
        favoritesState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        favoritesState.favorites.favorites.isEmpty() -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No tienes favoritos guardados")
            }
        }
        else -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp),
            ) {
                itemsIndexed(favoritesState.favorites.favorites) { _, favorite ->
                    ProductGridItem(
                        folder = favoritesState.favorites.folder,
                        product = favorite,
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
