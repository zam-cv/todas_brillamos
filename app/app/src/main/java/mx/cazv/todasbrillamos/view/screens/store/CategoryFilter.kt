package mx.cazv.todasbrillamos.view.screens.store

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.model.models.Category
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor

/**
 * Barra de categorias
 * @author: Min Che Kim
 *
 * @param categories lista de categorias
 */

@Composable
fun CategoryFilter(
    categories: List<Category>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    if (categories.isEmpty()) return

    val fSize = 18.sp

    Surface(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 15.dp)
            .background(BackgroundColor)
    ) {
        Row ( modifier = Modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .background(BackgroundColor)
        ) {
            LazyRow(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(24.dp))
            ) {
                itemsIndexed(categories) { _, category ->
                    val name = category.name

                    Text(
                        text = name,
                        style = TextStyle(
                            fontSize = fSize,
                            color = if (name == selectedCategory) Color.Black else Color.LightGray,
                            fontWeight = if (name == selectedCategory) FontWeight.ExtraBold else FontWeight.Normal
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(24.dp))
                            .clickable {
                                onCategorySelected(name)
                            }
                            .padding(
                                vertical = 12.dp,
                                horizontal = 16.dp,
                            ),
                    )
                }
            }
        }
    }
}