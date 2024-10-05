package mx.cazv.todasbrillamos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.services.UserService
import mx.cazv.todasbrillamos.model.states.UserState

class UserViewModel : ViewModel() {
    private val userService = UserService()

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state.asStateFlow()

    fun loadUserInfo(token: String) {
        viewModelScope.launch {
            try {
                val fullName = userService.fullname(token)
                _state.value = UserState(fullName = fullName.ifEmpty { "..." })
            } catch (e: Exception) {
                _state.value = UserState()
            }
        }
    }

    fun getUserState(): UserState {
        return _state.value
    }

    fun getFullName(): String {
        return _state.value.fullName
    }
}