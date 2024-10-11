package mx.cazv.todasbrillamos.viewmodel

import android.net.http.HttpException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.models.GroupedNotification
import mx.cazv.todasbrillamos.model.services.NotificationService
import mx.cazv.todasbrillamos.model.states.NotificationState
import okio.IOException

class NotificationsViewModel : ViewModel() {
    private val notificationService = NotificationService()

    private val _state = MutableStateFlow(NotificationState())
    val state: StateFlow<NotificationState> = _state.asStateFlow()

    /**
     * Carga las notificaciones agrupadas por fecha utilizando el token de autenticación y el ID del cliente.
     *
     * @param token El token de autenticación.
     */
//    suspend fun getNotifications(token: String) {
//        viewModelScope.launch {
//            try {
////                val result = notificationService.getNotifications(token)
////
////                if (result.isSuccess) {
////                    val notifications = result.getOrNull()
////                    _state.value = NotificationState(notifications ?: emptyList())
////                } else {
////                    _state.value = NotificationState(emptyList())
////                }
////            } catch (e: Exception) {
////                _state.value = NotificationState(emptyList()) // Manejar el error aquí si es necesario
////            }
//                val notifications = notificationService.getNotifications(token)
//
//                if (notifications != null) {
//                    _state.value = NotificationState(notifications)
//                } else {
//                    _state.value = NotificationState(GroupedNotification("", emptyList()), error = "Error fetching notifications")
//                }
//            } catch (e: IOException) {
//                // Manejar problemas de conectividad o errores relacionados con la red
//                _state.value = NotificationState(emptyList(), error = "Error de conexión")
//            } catch (e: HttpException) {
//                // Manejar respuestas HTTP no exitosas, por ejemplo, errores 4xx o 5xx
//                _state.value = NotificationState(emptyList(), error = "Error de servidor: ${e.message}")
//            } catch (e: Exception) {
//                // Manejar cualquier otro tipo de error no previsto
//                _state.value = NotificationState(emptyList(), error = "Error desconocido: ${e.message}")
//            }
//
//        }
//    }
//}

    suspend fun getNotifications(token: String) {
        try {
            val notifications = notificationService.getNotifications(token)

            if (notifications != null) {
                _state.value = NotificationState(notifications)
            } else {
                _state.value = NotificationState(
                    GroupedNotification("", emptyList()),
                    error = "Error fetching notifications"
                )
            }
        } catch (e: IOException) {
            // Manejar problemas de conectividad o errores relacionados con la red
            _state.value = NotificationState(
                GroupedNotification("", emptyList()),
                error = "Error de conexión"
            )
        } catch (e: HttpException) {
            // Manejar respuestas HTTP no exitosas, por ejemplo, errores 4xx o 5xx
            _state.value = NotificationState(
                GroupedNotification("", emptyList()),
                error = "Error de servidor: ${e.message}"
            )
        } catch (e: Exception) {
            // Manejar cualquier otro tipo de error no previsto
            _state.value = NotificationState(
                GroupedNotification("", emptyList()),
                error = "Error desconocido: ${e.message}"
            )
        }
    }
}
