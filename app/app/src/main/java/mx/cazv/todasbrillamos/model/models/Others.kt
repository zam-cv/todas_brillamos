package mx.cazv.todasbrillamos.model.models

/**
 * Data class con la información adicional de un cliente.
 * @author Carlos Zamudio
 *
 * @property CURP El CURP del cliente.
 * @property Street La calle donde vive el cliente.
 * @property Interior El número interior de la dirección del cliente.
 * @property Exterior El número exterior de la dirección del cliente.
 * @property City La ciudad donde vive el cliente.
 * @property State El estado donde vive el cliente.
 * @property ZIP El código postal del cliente.
 * @property Reference Una referencia adicional (punto de referencia) de la dirección del cliente.
 */
data class Others(
    val CURP: String,
    val Street: String,
    val Interior: String,
    val Exterior: String,
    val City: String,
    val State: String,
    val ZIP: String,
    val Reference: String
)