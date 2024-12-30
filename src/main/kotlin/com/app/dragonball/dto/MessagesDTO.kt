package com.app.dragonball.dto

/**
 * Data Transfer Object (DTO) que representa un mensaje de respuesta estándar.
 *
 * Esta clase se utiliza para enviar información relacionada con una operación HTTP, incluyendo el mensaje,
 * el método HTTP utilizado y el estado de la operación (éxito o error).
 *
 * @property message El mensaje que describe el resultado de la operación. Puede ser un mensaje de éxito o un mensaje de error.
 * @property method El método HTTP utilizado para la operación, como "GET", "POST", "PUT", "DELETE".
 * @property status El estado de la operación. `true` indica que la operación fue exitosa, `false` indica que hubo un error.
 */
class MessagesDTO {
    var message: String? = null
    var method: String? = null
    var status: Boolean? = null
}
