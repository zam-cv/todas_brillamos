package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mx.cazv.todasbrillamos.model.models.ProductRaw
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.FavoritesViewModel

@Composable
fun FavoriteComponent(
    authViewModel: AuthViewModel,
    favoritesViewModel: FavoritesViewModel,
    productRaw: ProductRaw
) {
    var isFavorite by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = productRaw.id) {
        val token = authViewModel.token()
        if (token != null) {
            isLoading = true
            isFavorite = favoritesViewModel.exists(token, productRaw.id)
            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .clickable {
                if (!isLoading) {
                    val token = authViewModel.token()
                    if (token != null) {
                        if (isFavorite) {
                            favoritesViewModel.deleteProductFromCart(token, productRaw.id)
                        } else {
                            favoritesViewModel.addFavorite(
                                token,
                                productRaw
                            )
                        }
                        isFavorite = !isFavorite
                    }
                }
            }
            .padding(5.dp)
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites",
            tint = if (isFavorite) Color.Red else Color.Gray,
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center)
        )
    }
}