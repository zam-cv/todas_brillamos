package mx.cazv.todasbrillamos.view

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun openUrl(context: Context, url:String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // show error message
    }
}