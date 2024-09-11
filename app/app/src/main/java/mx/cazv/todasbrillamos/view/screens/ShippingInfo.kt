package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.view.components.Input
import mx.cazv.todasbrillamos.view.layouts.MainLayout

@Composable
fun ShippingInfo(navController: NavHostController) {
    MainLayout(navController = navController) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize()
                .background(Color(0xFFFCFAF2))
        )
        {
            ClientData()


        }
    }
}

@Composable
fun ClientData(){
    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ){
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Box (
                modifier = Modifier
                    .weight(1f)
            ) {
                Input(placeholder = "Nombre")
            }

            Spacer(modifier = Modifier.size(10.dp))

            Box (
                modifier = Modifier
                    .weight(1f)
            ) {
                Input(placeholder = "Apellido")
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Input(placeholder = "Número telefónico")

        Spacer(modifier = Modifier.size(16.dp))

        Input(placeholder = "CURP")

        Spacer(modifier = Modifier.size(16.dp))

        Input(placeholder = "Correo electrónico")

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "Dirección", fontSize = 25.sp, modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(16.dp))

        Input(placeholder = "Calle")

        Spacer(modifier = Modifier.size(16.dp))

        Input(placeholder = "Número exterior")

        Spacer(modifier = Modifier.size(16.dp))

        Input(placeholder = "Número interior")

        Spacer(modifier = Modifier.size(16.dp))

        Input(placeholder = "Municipio")

        Spacer(modifier = Modifier.size(16.dp))

        Input(placeholder = "Ciudad")

        Spacer(modifier = Modifier.size(16.dp))

        Input(placeholder = "Estado")

        Spacer(modifier = Modifier.size(16.dp))

        Input(placeholder = "Código postal")

    }

}

@Preview
@Composable
fun ShippInfo() {
    val navController = rememberNavController()
    ShippingInfo(navController = navController)
}
