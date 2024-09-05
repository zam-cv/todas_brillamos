package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.components.CustomBottomBar
import mx.cazv.todasbrillamos.view.components.CustomTopBar
import mx.cazv.todasbrillamos.view.layouts.BasicLayout
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.view.layouts.MainLayout
import androidx.compose.foundation.lazy.items


@Composable
fun Chat(navController: NavHostController) {
    CustomLayout(
        navController = navController,
        topBar = {
            CustomTopBar {
                Text(text = "Custom Top Bar")
            }
        },
        bottomBar = {
            CustomBottomBar {
                Text(text = "Custom Bottom Bar")
            }
        }
    ) {
        Column (modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCFAF2))){

        }

        LazyColumn(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize()
        ) {
            item{
                Warning()
            }
            items(messages) { message ->
                when (message.type) {
                    MessageType.USER -> MessageUser(message.descrip)
                    MessageType.CHAT -> MessageChat(message.descrip)
                }
            }
        }
    }
}

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

data class Message(val descrip:String, val type:MessageType)

enum class MessageType{
    CHAT,
    USER
}

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


