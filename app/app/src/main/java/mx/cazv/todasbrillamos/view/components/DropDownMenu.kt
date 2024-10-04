package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.cazv.todasbrillamos.ui.theme.AccentColor
import mx.cazv.todasbrillamos.view.screens.calendar.MinimalDialog
import mx.cazv.todasbrillamos.viewmodel.CalendarVM

@Composable
fun DropDownMenu(
    suggestions: List<String>,
    type: String,
    text: String = "",
    calendarVM: CalendarVM = viewModel()
){
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    val maxHeight = 200.dp

    Row (modifier = Modifier) {

        MinimalDialog(text,
            Modifier
                .weight(0.5f)
                .align(Alignment.CenterVertically))

        Column(modifier = Modifier
            .weight(10f)
            .padding(start = 20.dp, end = 20.dp),
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
                label = { Text(
                    when (type) {
                        "period" -> "¿Cuánto suele durar tu periodo?"
                        "cycle" -> "¿Cuánto dura tu ciclo?"
                        else -> ""
                    },
                    fontSize = 14.sp
                ) },
                readOnly = true,
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
                        calendarVM.updateSelectedNumber(selectedText, type)
                    },
                        modifier = Modifier.background(Color.White)
                    )
                }
            }
        }
    }

}