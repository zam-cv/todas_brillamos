package mx.cazv.todasbrillamos.view.screens.store

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.cazv.todasbrillamos.R

/**
 * Cambiar vista de los productos
 * @author: Min Che Kim
 */

@Composable
fun ToggleView(selectedType: String, onSelectionChange: (String) -> Unit) {
    val gridFill = R.drawable.ic_grid_view_fill
    val gridEmpty = R.drawable.ic_grid_view
    val listFill = R.drawable.ic_list_view_fill
    val listEmpty = R.drawable.ic_list_view

    Surface(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.wrapContentSize()
    ) {
        Row (modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(text = "Productos",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left ),
                modifier = Modifier.padding(start = 10.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Grid Icon
            Icon(
                painter = painterResource(
                    id = if (selectedType == "grid") gridFill else gridEmpty
                ),
                contentDescription = null,
                modifier = Modifier
                    .clickable { onSelectionChange("grid") }
                    .padding(end = 5.dp)
                    .size(28.dp)
            )

            // List Icon
            Icon(
                painter = painterResource(
                    id = if (selectedType == "list") listFill else listEmpty
                ),
                contentDescription = null,
                modifier = Modifier
                    .clickable { onSelectionChange("list") }
                    .padding(end = 12.dp)
                    .size(28.dp)
            )
        }
    }
}