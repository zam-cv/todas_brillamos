package mx.cazv.todasbrillamos.model.states

import mx.cazv.todasbrillamos.view.screens.Message
import mx.cazv.todasbrillamos.view.screens.MessageType

data class ChatState(
    val conversations: List<Message> = listOf(
        Message("Hola, ¿en qué puedo ayudarte?", MessageType.CHAT),
    )
)