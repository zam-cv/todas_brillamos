package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.header.CustomTopBar
import mx.cazv.todasbrillamos.view.components.InfoOrder
import mx.cazv.todasbrillamos.view.layouts.CustomLayout

@Composable
fun Orders(navController: NavHostController) {
    CustomLayout(
        navController = navController,
        topBar = {
            CustomTopBar {
                Text(text = "Custom Top Bar")
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp)
        ){
            InfoOrder()
            Spacer(modifier = Modifier.height(25.dp))
            InfoOrder()
            Spacer(modifier = Modifier.height(25.dp))
            InfoOrder()
            Spacer(modifier = Modifier.height(25.dp))
            MoreProducts(
                text = "Más productos",
                modifier = Modifier
                    .padding(start = 15.dp, top = 25.dp)
            )
            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}