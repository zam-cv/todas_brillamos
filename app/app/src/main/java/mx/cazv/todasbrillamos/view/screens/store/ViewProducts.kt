package mx.cazv.todasbrillamos.view.screens.store

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.cazv.todasbrillamos.model.ProductProvider


@Composable
fun ViewProducts(type: String) {
    if (type == "grid") {
        LazyVerticalGrid(
            // columns = GridCells.Adaptive(160.dp),
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            itemsIndexed(ProductProvider.productList) { _, product ->
                ProductGridItem(product = product)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
    else if (type == "list") {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            itemsIndexed(ProductProvider.productList) { _, product ->
                ProductColumnItem(product = product)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

}