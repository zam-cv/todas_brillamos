package mx.cazv.todasbrillamos

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
    private lateinit var stripe: Stripe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PaymentConfiguration.init(
            applicationContext,
            Config.STRIPE_PUBLISHABLE_KEY
        )
        // Crea una instancia de Stripe
        stripe = Stripe(applicationContext, PaymentConfiguration.getInstance(applicationContext).publishableKey)

        enableEdgeToEdge()
        setContent {
            TodasBrillamosTheme {
                App(stripe)
            }
        }
    }
}