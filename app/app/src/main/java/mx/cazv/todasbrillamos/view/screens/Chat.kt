package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.view.components.footer.ChatBottomBar
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar

/**
 * Archivo para mostrar el chat
 * @author Mariana Balderrábano
 */

/**
 * Pantalla de chat que permite al usuario interactuar con un asistente de inteligencia artificial.
 *
 * @param navController El NavHostController utilizado para la navegación.
 */
@Composable
fun Chat(navController: NavHostController) {
    CustomLayout(
        navController = navController,
        topBar = {
            BasicTopBar(title = "Chat", navController = navController)
        },
        bottomBar = {
            ChatBottomBar()
        }
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize()
        ) {
            Warning()
            messages.forEach { message ->
                when (message.type) {
                    MessageType.USER -> MessageUser(message.descrip)
                    MessageType.CHAT -> MessageChat(message.descrip)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Composable que muestra una advertencia sobre el uso del chat.
 */
@Composable
fun Warning(){
    Column {
        Text(text = "Este es un chat que contesta tus dudas con ayuda de Inteligencia Artificial" +
                " , las respuestas pueden no ser del todo acertadas, por lo que también" +
                " podrá sugerirte especialistas expertos con los que podrás ponerte en contacto " +
                "y recibir atención personalizada",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth())

    }
}


/**
 * Composable que muestra un mensaje del chat.
 *
 * @param descrip La descripción del mensaje.
 * @param modifier Modificador para personalizar la apariencia y el comportamiento del componente.
 */
@Composable
fun MessageChat( descrip: String, modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(16.dp))
    Row (modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start){
        Text(
            text = descrip,
            textAlign = TextAlign.Left,
            modifier = modifier
                .background(
                    Color(0xFFfae7ec),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 12.dp,
                        bottomStart = 12.dp,
                        bottomEnd = 12.dp
                    )
                )
                .padding(10.dp)
                .weight(3f)
                ,
            color = Color.Black)
        Text(
            text = "" ,
            modifier = modifier
                .weight(1f)
        )
    }
}

/**
 * Composable que muestra un mensaje del usuario.
 *
 * @param descrip La descripción del mensaje.
 * @param modifier Modificador para personalizar la apariencia y el comportamiento del componente.
 */
@Composable
fun MessageUser(descrip: String, modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(16.dp))
    Row (modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End){
        Text(
            text = "" ,
            modifier = modifier
                .weight(1f)
        )
        Text(
            text = descrip,
            textAlign = TextAlign.Right,
            modifier = modifier
                .background(
                    Color(0xFFffffff),
                    shape = RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 0.dp,
                        bottomStart = 12.dp,
                        bottomEnd = 12.dp
                    )
                )
                .padding(10.dp)
                .weight(3f),
            color = Color.Black)
    }
}

/**
 * Datos de un mensaje.
 *
 * @param descrip La descripción del mensaje.
 * @param type El tipo de mensaje (usuario o chat).
 */
data class Message(val descrip:String, val type:MessageType)

/**
 * Tipos de mensajes posibles.
 */
enum class MessageType{
    CHAT,
    USER
}

/**
 * Lista de mensajes de ejemplo.
 */
val messages = listOf(
    Message("Este es un ejemplo de texto por la parte del usuario", MessageType.USER) ,
    Message("Este es un ejemplo de texto por la parte del chat", MessageType.CHAT),
    Message("Este es un ejemplo de texto por la parte del usuario", MessageType.USER) ,
    Message("Este es un ejemplo de texto por la parte del chat", MessageType.CHAT),
    Message("Este es un ejemplo de texto por la parte del usuario", MessageType.USER) ,
    Message("Este es un ejemplo de texto por la parte del chat", MessageType.CHAT),
    Message("Este es un ejemplo de texto por la parte del usuario", MessageType.USER) ,
    Message("Este es un ejemplo de texto por la parte del chat", MessageType.CHAT),
    Message("Este es un ejemplo de texto por la parte del usuario", MessageType.USER) ,
    Message("Este es un ejemplo de texto por la parte del chat", MessageType.CHAT),
    Message("Este es un ejemplo de texto por la parte del usuario", MessageType.USER) ,
    Message("Este es un ejemplo de texto por la parte del chat", MessageType.CHAT),
    Message("Este es un ejemplo de texto por la parte del usuario", MessageType.USER) ,
    Message("Este es un ejemplo de texto por la parte del chat", MessageType.CHAT),
    Message("Este es un ejemplo de texto por la parte del usuario", MessageType.USER) ,
    Message("Este es un ejemplo de texto por la parte del chat", MessageType.CHAT)
)


