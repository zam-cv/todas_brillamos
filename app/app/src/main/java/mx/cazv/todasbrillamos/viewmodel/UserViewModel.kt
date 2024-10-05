package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.models.ClientDetails
import mx.cazv.todasbrillamos.model.models.PasswordUpdate
import mx.cazv.todasbrillamos.model.services.UserService
import mx.cazv.todasbrillamos.model.states.UserState

/**
 * ViewModel para gestionar la información del usuario.
 * @author Carlos Zamudio
 */
class UserViewModel : ViewModel() {
    private val userService = UserService()

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state.asStateFlow()

    /**
     * Carga la información del usuario utilizando el token de autenticación.
     *
     * @param token El token de autenticación.
     */
    fun loadUserInfo(token: String) {
        viewModelScope.launch {
            try {
                val details = userService.getClient(token)
                _state.value = UserState(details)
            } catch (e: Exception) {
                _state.value = UserState()
            }
        }
    }

    /**
     * Actualiza la información del usuario.
     *
     * @param token El token de autenticación.
     * @param clientDetails Los detalles del cliente.
     */
    fun update(token: String, clientDetails: ClientDetails) {
        viewModelScope.launch {
            try {
                userService.update(token, clientDetails)
                _state.value = UserState(clientDetails)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    /**
     * Actualiza la contraseña del usuario.
     *
     * @param token El token de autenticación.
     * @param passwordUpdate La nueva contraseña.
     */
    fun updatePassword(token: String, passwordUpdate: PasswordUpdate) {
        viewModelScope.launch {
            try {
                userService.updatePassword(token, passwordUpdate)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}