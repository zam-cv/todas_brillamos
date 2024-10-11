package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.cazv.todasbrillamos.ui.theme.BadgePink
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.view.components.footer.ChatBottomBar
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.ChatViewModel

/**
 * Archivo para mostrar el chat
 * @author Mariana Balderrábano, Carlos Zamudio
 */

/**
 * Pantalla de chat que permite al usuario interactuar con un asistente de inteligencia artificial.
 *
 * @param navController El NavHostController utilizado para la navegación.
 * @param authViewModel El ViewModel de autenticación.
 * @param viewModel El ViewModel del chat.
 */
@Composable
fun Chat(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    viewModel: ChatViewModel
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    CustomLayout(
        navController = navController,
        topBar = {
            BasicTopBar(title = "Chat", navController = navController)
        },
        bottomBar = {
            ChatBottomBar(
                onSendMessage = { message ->
                    CoroutineScope(Dispatchers.Main).launch {
                        val token = withContext(Dispatchers.IO) {
                            authViewModel.token()
                        }

                        if (token != null) {
                            viewModel.sendMessage(token, message)
                            coroutineScope.launch {
                                listState.animateScrollToItem(state.conversations.size - 1)
                            }
                        }
                    }
                }
            )
        },
        withScroll = false
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize()
        ) {
            item {
                Warning()
            }

            items(state.conversations) { message ->
                when (message.type) {
                    MessageType.USER -> MessageUser(message.descrip)
                    MessageType.CHAT -> MessageChat(message.descrip)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    LaunchedEffect(state.conversations.size) {
        if (state.conversations.isNotEmpty()) {
            listState.animateScrollToItem(state.conversations.size - 1)
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
                ", las respuestas pueden no ser del todo acertadas, por lo que también" +
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
    Box (modifier = Modifier
        .fillMaxWidth()
        .padding(end = 50.dp)) {
        Text(
            text = descrip,
            textAlign = TextAlign.Left,
            modifier = modifier
                .align(Alignment.CenterStart)
                .background(
                    BadgePink,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 12.dp,
                        bottomStart = 12.dp,
                        bottomEnd = 12.dp
                    )
                )
                .padding(12.dp),
            color = Color.Black
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

    Box (modifier = Modifier
        .fillMaxWidth()
        .padding(start = 50.dp)){
        Text(
            text = descrip,
            textAlign = TextAlign.Left,
            modifier = modifier
//                .widthIn(max = 300.dp)
                .align(Alignment.CenterEnd)
                .background(
                    Color(0xFFffffff),
                    shape = RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 0.dp,
                        bottomStart = 12.dp,
                        bottomEnd = 12.dp
                    )
                )
                .padding(12.dp),
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
 * Enumeración de tipos de mensajes posibles.
 */
enum class MessageType{
    CHAT,
    USER
}
