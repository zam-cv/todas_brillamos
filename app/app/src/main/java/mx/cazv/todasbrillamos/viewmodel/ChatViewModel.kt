package mx.cazv.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.cazv.todasbrillamos.model.services.ChatService
import mx.cazv.todasbrillamos.view.screens.Message
import mx.cazv.todasbrillamos.view.screens.MessageType

/**
 * ViewModel para gestionar la lógica del chat. Maneja el envío y la recepción de mensajes en
 * la conversación.
 * @author Carlos Zamudio
 */
class ChatViewModel : ViewModel() {
    private val chatService = ChatService()

    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()

    /**
     * Envía un mensaje al servicio de chat y maneja la respuesta.
     *
     * @param token El token de autenticación del usuario.
     * @param content El contenido del mensaje a enviar.
     */
    fun sendMessage(token: String, content: String) {
        viewModelScope.launch {
            try {
                addMessage(Message(content, MessageType.USER))
                val response = chatService.sendMessage(token, content)
                addMessage(Message(response, MessageType.CHAT))
            } catch (e: Exception) {
                addMessage(Message("Lo siento, ha ocurrido un error. Por favor, intenta de nuevo.", MessageType.CHAT))
            }
        }
    }

    // Función para agregar un mensaje a la lista de conversaciones
    private fun addMessage(message: Message) {
        _state.value = _state.value.copy(
            conversations = _state.value.conversations + message
        )
    }
}

/**
 * Data class que representa el estado del chat.
 */
data class ChatState(
    val conversations: List<Message> = listOf(
        Message("Hola, ¿en qué puedo ayudarte?", MessageType.CHAT),
    )
)