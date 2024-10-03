package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.ui.theme.AccentColor
import mx.cazv.todasbrillamos.ui.theme.GrayB3

@Composable
fun DropDownMenu(suggestions: List<String>, type: String) {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    val maxHeight = 200.dp

    Row (modifier = Modifier) {
        Column (modifier = Modifier
            .weight(0.5f)
            .align(Alignment.CenterVertically)
            .offset(x = 15.dp)){
            Icon(painterResource(id = R.drawable.info_icon),
                contentDescription = "more info",
                tint = GrayB3)
        }
        Column(modifier = Modifier
            .weight(10f)
            .padding(start =20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textfieldSize = coordinates.size.toSize()
                    },
                label = {Text(if (type == "period") "¿Cuánto suele durar tu periodo?" else if (type == "cycle") "¿Cuánto dura tu ciclo?" else "")},
                trailingIcon = {
                    Icon(icon,"contentDescription",
                        Modifier.clickable { expanded = !expanded },
                        tint = AccentColor)
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .heightIn(max = maxHeight)
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                    .background(Color.White)
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(text = { Text(text = label) }, onClick = {
                        selectedText = label
                        expanded = false
                    },
                        modifier = Modifier.background(Color.White)
                    )
                }
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun DropDownMenuPreview() {
    val options = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    DropDownMenu(options, "period")
}