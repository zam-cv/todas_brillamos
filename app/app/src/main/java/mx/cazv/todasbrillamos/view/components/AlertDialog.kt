package mx.cazv.todasbrillamos.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign

/**
 * Archivo para mostrar un cuadro de dialogo
 * @author Min Che Kim
 *
 * @param onDismissRequest La acción a realizar cuando se cierra el cuadro de diálogo.
 * @param onConfirmation La acción a realizar cuando se confirma la acción en el cuadro de diálogo.
 * @param dialogTitle El título del cuadro de diálogo.
 * @param dialogText El texto del cuadro de diálogo.
 * @param icon El icono del cuadro de diálogo.
 */
@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth().wrapContentSize(Alignment.Center)
            ) {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text("Confirmar")
                }
            }
        },
        dismissButton = {
//            TextButton(
//                onClick = {
//                    onDismissRequest()
//                }
//            ) {
//                Text("Dismiss")
//            }
        }
    )
}