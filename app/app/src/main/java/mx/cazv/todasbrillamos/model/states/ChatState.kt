package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.view.screens.Message
import mx.cazv.todasbrillamos.view.screens.MessageType

/**
 * Clase de datos que representa el estado del chat en la aplicación.
 * @author Carlos Zamudio
 *
 * @property conversations Lista de mensajes que forman parte de la conversación en el chat.
 */
data class ChatState(
    val conversations: List<Message> = listOf(
        Message("Hola, ¿en qué puedo ayudarte?", MessageType.CHAT),
    )
)