package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.ui.theme.Stroke
import mx.cazv.todasbrillamos.view.components.CustomButton
import mx.cazv.todasbrillamos.view.components.Input
import mx.cazv.todasbrillamos.view.layouts.MainLayout

/**
 * Archivo para mostrar el formulario de pagos.
 * @author Mariana Balderrábano
 */

/**
 * Pantalla de pagos que permite al usuario ingresar la información de su tarjeta y realizar un pago.
 *
 * @param navController El NavHostController utilizado para la navegación.
 */
@Composable
fun Payments(navController: NavHostController) {
    MainLayout(navController = navController) {
        Column (modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())) {
            Pay()
        }
    }
}

/**
 * Composable que muestra el formulario de pago con campos para la información de la tarjeta y el titular.
 */
@Composable
fun Pay(){

    Column {
        Spacer(modifier = Modifier.size(8.dp))
        CustomButton("Pay", Color.Black, imageId = R.drawable.google)
        Spacer(modifier = Modifier.size(20.dp))
        PrettyPrint()
        Spacer(modifier = Modifier.size(20.dp))
        NormalText(text = "Correo electrónico", sizeT = 15, colT = Color.Gray)
        Spacer(modifier = Modifier.size(8.dp))
        Input(placeholder = "")
        Spacer(modifier = Modifier.size(20.dp))
        NormalText(text = "Información de la tarjeta", sizeT = 15, colT = Color.Gray)
        Spacer(modifier = Modifier.size(8.dp))
        Input(placeholder = "1234 1234 1234 123",
            topStart = 10.dp,
            topEnd = 10.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp,
            imageId = R.drawable.cards,
            imageSize = 150.dp,
            padding = 1.dp,
            height = 65)
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Box (
                modifier = Modifier
                    .weight(1f)
            ) {
                Input(placeholder = "MM/AA",
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 10.dp,
                    height = 65)
            }

            Box (
                modifier = Modifier
                    .weight(1f)
            ) {
                Input(placeholder = "CVC",
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 10.dp,
                    bottomStart = 0.dp,
                    imageId = R.drawable.cvc,
                    imageSize = 40.dp,
                    padding = 1.dp,
                    height = 65)
            }
        }

        Spacer(modifier = Modifier.size(20.dp))

        NormalText(text = "Nombre del titular de la tarjeta", sizeT = 15, colT = Color.Gray)
        Spacer(modifier = Modifier.size(8.dp))
        Input(placeholder = "Nombre completo")

        Spacer(modifier = Modifier.size(20.dp))
        NormalText(text = "País o región", sizeT = 15, colT = Color.Gray)
        Spacer(modifier = Modifier.size(8.dp))
        //Input(placeholder = "México")
        CustomButton(text = "México", col = Color.White, colT = Color.Gray, height = 65, borderColor = Stroke, alignTextLeft = true, trailingIcon = Icons.Default.KeyboardArrowDown)
        Spacer(modifier = Modifier.size(14.dp))
        //CustomButton(text = "México", col = Color.White, colT = Color.Gray)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = true,
                onCheckedChange = { }
            )
            Column {
                Text(
                    text = "Guardar mi información mediante un proceso de compra seguro en un clic",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                NormalText(text = "Paga con mayor rapidez en Powdur y en todos los comercios que acepten Link", sizeT = 14, colT = Color.Gray)
            }

        }
        Spacer(modifier = Modifier.size(8.dp))


        //Input(placeholder = "1234 1234 1234 123" )
        CustomButton(text = "Pagar", col = Color(0xFF192552))
    }
}

/**
 * Composable que muestra un texto con líneas divisorias a los lados.
 *
 * @param text El texto a mostrar.
 */
@Composable
fun PrettyPrint(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            //.padding(horizontal = 16.dp)
    ){
        Divider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp),
            color = Color.Gray
        )

        Spacer(modifier = Modifier.width(8.dp))

        NormalText(text = "o pagar con tarjeta", sizeT = 18, colT = Color.Gray)

        Spacer(modifier = Modifier.width(8.dp))
        
        Divider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp),
            color = Color.Gray
        )

    }
}

/**
 * Composable que muestra un texto con un estilo específico.
 *
 * @param text El texto a mostrar.
 * @param sizeT El tamaño del texto.
 * @param colT El color del texto.
 */
@Composable
fun NormalText(text: String, sizeT: Int, colT: Color){
    Text(text = text,
        modifier = Modifier,
        fontSize = sizeT.sp,
        color = colT,
        fontWeight = FontWeight.W400)
}

//@Preview
//@Composable
//fun Payment(navController: NavHostController) {
//    val navController = rememberNavController()
//    Payments(navController = navController)
//}


