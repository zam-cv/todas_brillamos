package mx.cazv.todasbrillamos.view.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.layouts.MainLayout
import mx.cazv.todasbrillamos.view.screens.MoreProducts

@Composable
fun Home(navController: NavHostController) {
    MainLayout(navController = navController) {
        Column(
            modifier = Modifier
                .padding(top = 75.dp, start = 15.dp, end = 15.dp, bottom = 25.dp)
        ){
            GreetingSec("Fernanda Herrera")
            Spacer(modifier = Modifier.height(30.dp))
            InteractiveCardsHome()
            MoreProducts(text = "Recomendado", modifier = Modifier.padding(top = 20.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Post()
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

