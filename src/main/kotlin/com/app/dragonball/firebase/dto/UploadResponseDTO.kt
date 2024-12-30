package com.app.dragonball.firebase.dto

/**
 * Data Transfer Object (DTO) que representa la respuesta de una operación de carga de archivos.
 *
 * Esta clase se utiliza para encapsular la información que se devuelve como resultado
 * de una operación de carga, típicamente para devolver un enlace al archivo cargado
 * y el estado de la operación (si tuvo éxito o no).
 */
class UploadResponseDTO {
    var link: String? = null
    var status: Boolean? = null
}
