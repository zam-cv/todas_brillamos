package mx.cazv.todasbrillamos

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import mx.cazv.todasbrillamos.model.Config
import mx.cazv.todasbrillamos.ui.theme.TodasBrillamosTheme
import mx.cazv.todasbrillamos.view.App

/**
 * Actividad principal de la aplicación.
 * Configura el tema y el contenido de la aplicación.
 * @author Carlos Zamudio
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        PaymentConfiguration.init(
            applicationContext,
            Config.STRIPE_PUBLISHABLE_KEY
        )

        enableEdgeToEdge()
        setContent {
            TodasBrillamosTheme {
                App()
            }
        }
    }
}