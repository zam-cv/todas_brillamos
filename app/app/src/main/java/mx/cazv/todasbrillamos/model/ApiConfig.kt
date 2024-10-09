package mx.cazv.todasbrillamos.model

/**
 * Objeto que contiene la configuración de la API.
 * @author Carlos Zamudio
 */
object ApiConfig {
    private const val DEFAULT_BASE_URL = "http://10.0.2.2:8000/"

    val BASE_URL: String = System.getenv("API_BASE_URL") ?: DEFAULT_BASE_URL
}