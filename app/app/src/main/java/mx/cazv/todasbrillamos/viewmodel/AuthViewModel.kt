package mx.cazv.todasbrillamos.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.services.AuthService
import mx.cazv.todasbrillamos.model.models.Credentials
import mx.cazv.todasbrillamos.model.models.SignInRequest
import mx.cazv.todasbrillamos.model.models.UserInfo
import mx.cazv.todasbrillamos.view.Routes

class AuthViewModel (application: Application): AndroidViewModel(application) {
    private val context = application.applicationContext
    private val remoteService = AuthService()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    companion object {
        private const val PREFS_NAME = "AuthPrefs"
        private const val KEY_AUTH_TOKEN = "token"
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val request = SignInRequest(email, password)
            val result = remoteService.signin(request)
            _authState.value = if (result != null) {
                saveToken(result.token)
                AuthState.SignInSuccess(result)
            } else {
                AuthState.Error("Sign in failed")
            }
        }
    }

    fun register(userInfo: UserInfo) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = remoteService.register(userInfo)
                _authState.value = if (result) {
                    AuthState.RegisterSuccess
                } else {
                    AuthState.Error("Registration failed: Server response was unsuccessful")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Registration failed: ${e.message}")
            }
        }
    }

    suspend fun verify(): Boolean {
        val token = getToken() ?: return false
        val result = remoteService.verify(token)

        if (result) {
            val credentials = Credentials(token)
            _authState.value = AuthState.SignInSuccess(credentials)
        }

        return result
    }

    fun saveToken(token: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(KEY_AUTH_TOKEN, token)
            apply()
        }
    }

    fun getToken(): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    fun logout(navController: NavHostController) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(KEY_AUTH_TOKEN)
            commit()
        }

        _authState.value = AuthState.Idle

        navController.navigate(Routes.ROUTE_LOGIN) {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
            launchSingleTop = true
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class SignInSuccess(val credentials: Credentials) : AuthState()
    object RegisterSuccess : AuthState()
    data class Error(val message: String) : AuthState()
}