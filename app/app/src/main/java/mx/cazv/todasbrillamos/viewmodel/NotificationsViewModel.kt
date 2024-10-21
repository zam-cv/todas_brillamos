package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.models.GroupedNotification
import mx.cazv.todasbrillamos.model.services.NotificationService

/**
 * ViewModel para gestionar el estado de las notificaciones.
 * @author Carlos Zamudio
 */
class NotificationsViewModel : ViewModel() {
    private val notificationService = NotificationService()

    private val _state = MutableStateFlow(emptyList<GroupedNotification>())
    val state: StateFlow<List<GroupedNotification>> = _state.asStateFlow()

    /**
     * Carga la lista de notificaciones.
     *
     * @param token El token de autenticación.
     */
    fun loadNotifications(token: String) {
        viewModelScope.launch {
            try {
                _state.value = notificationService.getNotifications(token)
            } catch (e: Exception) {
                _state.value = emptyList()
            }
        }
    }

    /**
     * Obtiene el número de notificaciones no leídas.
     *
     * @param token El token de autenticación.
     * @return El número de notificaciones no leídas.
     */
    suspend fun getUnread(token: String): Int {
        return notificationService.getUnread(token)
    }
}
