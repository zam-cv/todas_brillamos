package mx.cazv.todasbrillamos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import mx.cazv.todasbrillamos.ui.theme.TodasBrillamosTheme
import mx.cazv.todasbrillamos.view.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodasBrillamosTheme {
                App()
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 840)
@Composable
fun GreetingPreview() {
    TodasBrillamosTheme {
       App()
    }
}