package mx.cazv.todasbrillamos.view.screens.config

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.model.models.Setting
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel

/**
 * Archivo para mostrar la pantalla de configuración del usuario.
 * @author Carlos Zamudio
 */
/**
 * Composable que muestra una opción de configuración con un icono y un título.
 *
 * @param setting La configuración a mostrar.
 */
@Composable
fun Option(setting: Setting) {
    Column (
        modifier = Modifier
            .padding()
            .clickable {
                if (setting.action != null) {
                    setting.action?.invoke()
                } else {
                    setting.navController.navigate(setting.route)
                }
            }
            .background(Color.White)
            .drawBehind {
                val strokeWidth = 0.5.dp.toPx()
                val y = size.height - strokeWidth / 2

                drawLine(
                    Color.LightGray,
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            }
            .padding(top = 5.dp, bottom = 5.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 15.dp)
            ) {
                Icon(
                    setting.icon,
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(22.dp)
                        .align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(setting.title, fontWeight = FontWeight.W400)
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Settings",
                    modifier = Modifier
                        .size(43.dp)
                        .graphicsLayer(
                            scaleX = 0.6f,
                            scaleY = 1f
                        )
                )
            }
        }
    }
}

/**
 * Composable que muestra una sección de opciones de configuración.
 *
 * @param options La lista de configuraciones a mostrar.
 */
@Composable
fun OptionsSection(options: List<Setting>) {
    Column (
        modifier = Modifier
            .padding(top = 20.dp)
    ) {
        options.forEach {
            Option(it)
        }
    }
}

/**
 * Pantalla de configuración que muestra varias opciones de configuración para el usuario.
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param authViewModel El ViewModel de autenticación utilizado para gestionar el cierre de sesión.
 */
@Composable
fun Config(navController: NavHostController, authViewModel: AuthViewModel) {
    CustomLayout(
        navController = navController,
        withStoreButton = true,
        topBar = {
            BasicTopBar(title = "Configuración", navController = navController)
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
    ) {
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .background(BackgroundColor)
        ) {
            OptionsSection(
                options = listOf(
                    Setting("Editar perfil", Icons.Filled.Person, Routes.ROUTE_EDIT_PROFILE, navController),
                    Setting("Cambiar contraseña", Icons.Outlined.Lock, Routes.ROUTE_CHANGE_PASSWORD, navController),
                    Setting("Notificaciones", Icons.Outlined.Notifications, Routes.ROUTE_NOTIFICATIONS, navController),
                )
            )

            OptionsSection(
                options = listOf(
                    Setting("Quienes somos", Icons.Outlined.QuestionMark, Routes.ROUTE_ABOUT, navController),
                    Setting("Terminos y condiciones", Icons.AutoMirrored.Outlined.InsertDriveFile, Routes.ROUTE_TERMS_AND_POLICIES, navController),
                    Setting("Compartir esta aplicación", Icons.Filled.Share, Routes.ROUTE_HOME, navController),
                )
            )

            OptionsSection(
                options = listOf(
                    Setting(
                        "Cerrar sesión",
                        Icons.AutoMirrored.Filled.ExitToApp,
                        Routes.ROUTE_HOME, navController,
                        action = {
                            authViewModel.logout(navController)
                        }
                    )
                )
            )
        }
    }
}