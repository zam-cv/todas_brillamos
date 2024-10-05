package mx.cazv.todasbrillamos.model.models

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

/**
 * Clase de datos que representa una configuración.
 *
 * @author Carlos Zamudio
 *
 * @property title El título de la configuración.
 * @property icon El icono de la configuración.
 * @property route La ruta de navegación de la configuración.
 * @property navController El NavController utilizado para la navegación.
 * @property action La acción opcional que se ejecutará al seleccionar la configuración.
 */
data class Setting (
    val title: String,
    val icon: ImageVector,
    val route: String,
    val navController: NavController,
    val action: (() -> Unit)? = null
)