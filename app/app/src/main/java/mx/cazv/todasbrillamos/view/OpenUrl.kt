package mx.cazv.todasbrillamos.view

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

/**
 * Abre una URL en el navegador predeterminado.
 * @author Min Che Kim
 *
 * @param context El contexto desde el cual se llama a esta función.
 * @param url La URL a abrir.
 */
fun openUrl(context: Context, url:String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // show error message
    }
}