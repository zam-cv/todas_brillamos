package mx.cazv.todasbrillamos.view.screens.store

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.cazv.todasbrillamos.model.models.ProductList
import mx.cazv.todasbrillamos.model.models.ProductProvider

/**
 * Ver los productos dependiendo de la vista
 * @author: Min Che Kim
 */
@Composable
fun ViewProducts(type: String, products: ProductList) {
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
                ProductGridItem(product = product, products.folder)
            }
        }
    } else if (type == "list") {
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

@Composable
fun ViewFavorites() {
    LazyVerticalGrid(
        // columns = GridCells.Adaptive(160.dp),
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
/*        itemsIndexed(ProductProvider.favProductList) { _, product ->
            ProductGridItem(product = product)
            Spacer(modifier = Modifier.height(8.dp))
        }*/
    }
}
