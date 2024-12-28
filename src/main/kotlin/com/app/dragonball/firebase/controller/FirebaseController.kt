@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.app.dragonball.firebase.controller

import com.app.dragonball.firebase.dto.UploadResponseDTO
import com.app.dragonball.firebase.service.FirebaseStorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("api/character/image")
@CrossOrigin(origins = ["*"])
class FirebaseController
    @Autowired
    constructor(
        private val firebaseStorageService: FirebaseStorageService,
    ) {
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

        @DeleteMapping("/{name}")
        fun deleteImage(
            @RequestParam("file") multipartFile: MultipartFile?,
            @PathVariable("name") name: String,
        ): String? = firebaseStorageService.deleteFile("$name.png")
    }
