@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.app.dragonball.firebase.controller

import com.app.dragonball.firebase.dto.UploadResponseDTO
import com.app.dragonball.firebase.service.FirebaseStorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

/**
 * Controlador para manejar las solicitudes de carga y eliminación de imágenes a Firebase Storage.
 *
 * Este controlador expone dos puntos finales:
 * 1. Un `POST` para cargar imágenes a Firebase Storage.
 * 2. Un `DELETE` para eliminar imágenes desde Firebase Storage.
 *
 * Se utiliza el servicio `FirebaseStorageService` para gestionar las interacciones con Firebase Storage.
 */
@RestController
@RequestMapping("api/character/image")
@CrossOrigin(origins = ["*"])
class FirebaseController
    @Autowired
    constructor(
        private val firebaseStorageService: FirebaseStorageService,
    ) {
        /**
         * Carga una imagen a Firebase Storage.
         *
         * Este método maneja las solicitudes `POST` que envían un archivo (imagen) al servidor.
         * El archivo se sube a Firebase Storage y se devuelve la URL de descarga de la imagen cargada.
         *
         * @param multipartFile El archivo (imagen) que se va a cargar.
         * @return Una respuesta que contiene la URL de la imagen subida y el estado de la operación.
         */
        @PostMapping
        fun uploadImage(
            @RequestParam("file") multipartFile: MultipartFile?,
        ): ResponseEntity<Any> =
            try {
                println("multipartFile: $multipartFile")
                val uploadImage: String? = multipartFile?.let { firebaseStorageService.upload(it) }

                val message =
                    UploadResponseDTO().apply {
                        link = uploadImage
                        status = true
                    }
                ResponseEntity(message, HttpStatus.CREATED)
            } catch (e: Exception) {
                val badMessage =
                    UploadResponseDTO().apply {
                        link = null
                        status = false
                    }
                ResponseEntity(badMessage, HttpStatus.INTERNAL_SERVER_ERROR)
            }

        /**
         * Elimina una imagen desde Firebase Storage.
         *
         * Este método maneja las solicitudes `DELETE` para eliminar un archivo (imagen) de Firebase Storage.
         * El archivo se identifica por su nombre, que se pasa como parámetro en la URL.
         *
         * @param name El nombre del archivo que se desea eliminar de Firebase Storage.
         * @return Un mensaje con el resultado de la operación de eliminación.
         */
        @DeleteMapping("/{name}")
        fun deleteImage(
            @RequestParam("file") multipartFile: MultipartFile?,
            @PathVariable("name") name: String,
        ): String? = firebaseStorageService.deleteFile("$name.png")
    }
