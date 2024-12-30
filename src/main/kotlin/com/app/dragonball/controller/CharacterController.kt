package com.app.dragonball.controller

import com.app.dragonball.dto.MessagesDTO
import com.app.dragonball.model.CharacterModel
import com.app.dragonball.service.CharacterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/character/")
@CrossOrigin(origins = ["*"])
class CharacterController
    @Autowired
    constructor(
        private val characterService: CharacterService,
    ) {
        /**
         * Endpoint para obtener la lista completa de personajes.
         *
         * Este método maneja las solicitudes HTTP GET a la ruta "/list" y devuelve una lista de objetos de tipo [CharacterModel].
         * En caso de un error al recuperar los personajes, se devuelve un código de estado HTTP 500 (INTERNAL_SERVER_ERROR).
         *
         * @return Un objeto [ResponseEntity] con el código de estado 200 OK si la operación es exitosa, o un código de estado 500 si ocurre un error.
         */
        @GetMapping("list")
        fun getAllListCharacters(): ResponseEntity<List<CharacterModel>> =
            try {
                ResponseEntity.ok(characterService.listCharacters())
            } catch (e: Exception) {
                ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }

        /**
         * Endpoint para obtener un personaje por su ID.
         *
         * Este método maneja las solicitudes HTTP GET a la ruta "/{id}", donde {id} es el identificador único del personaje.
         * Si el personaje se encuentra, se devuelve un objeto [CharacterModel] en la respuesta con el código de estado HTTP 200 OK.
         * Si ocurre un error (por ejemplo, si el personaje no se encuentra o hay un problema con la consulta), se devuelve un objeto [MessagesDTO]
         * con un mensaje de error y el código de estado HTTP 500 (INTERNAL_SERVER_ERROR).
         *
         * @param id El identificador único del personaje a obtener.
         * @return Un objeto [ResponseEntity] que contiene el personaje en caso de éxito, o un mensaje de error en caso de fallo.
         */
        @GetMapping("{id}")
        fun getCharacterId(
            @PathVariable("id") id: Long,
        ): ResponseEntity<Any> =
            try {
                ResponseEntity.ok(characterService.getCharacterById(id))
            } catch (e: Exception) {
                val badMessage =
                    MessagesDTO().apply {
                        message = e.message
                        method = "GET"
                        status = false
                    }
                ResponseEntity(badMessage, HttpStatus.INTERNAL_SERVER_ERROR)
            }

        /**
         * Endpoint para crear un nuevo personaje.
         *
         * Este método maneja las solicitudes HTTP POST a la ruta "/create-character", donde se recibe un objeto [CharacterModel]
         * en el cuerpo de la solicitud. Si el personaje se crea correctamente, se devuelve un mensaje con el ID del personaje creado
         * junto con un código de estado HTTP 201 (CREATED). En caso de error, se devuelve un mensaje de error con el código de estado
         * HTTP 500 (INTERNAL_SERVER_ERROR).
         *
         * @param character El objeto [CharacterModel] que contiene la información del personaje a crear.
         * @return Un objeto [ResponseEntity] que contiene un mensaje de éxito o error según el resultado de la operación.
         */
        @PostMapping("create-character")
        fun createCharacter(
            @RequestBody character: CharacterModel,
        ): ResponseEntity<Any> =
            try {
                val createdCharacter = characterService.createCharacter(character)

                val message =
                    MessagesDTO().apply {
                        message = "Person created with ID ${createdCharacter.id}"
                        method = "POST"
                        status = true
                    }
                ResponseEntity(message, HttpStatus.CREATED)
            } catch (e: Exception) {
                val badMessage =
                    MessagesDTO().apply {
                        message = "Error: ${e.message}"
                        method = "POST"
                        status = false
                    }
                ResponseEntity(badMessage, HttpStatus.INTERNAL_SERVER_ERROR)
            }

        /**
         * Endpoint para actualizar un personaje existente.
         *
         * Este método maneja las solicitudes HTTP PUT a la ruta "/update-character/{id}", donde se recibe el ID del personaje como
         * parámetro en la URL y un objeto [CharacterModel] en el cuerpo de la solicitud. Si el personaje se actualiza correctamente,
         * se devuelve un mensaje con el ID del personaje actualizado y un código de estado HTTP 200 (OK). En caso de error, se devuelve
         * un mensaje de error con el código de estado HTTP 400 (BAD_REQUEST) si los datos son inválidos, o un código HTTP 500
         * (INTERNAL_SERVER_ERROR) para errores generales.
         *
         * @param id El ID del personaje que se va a actualizar.
         * @param character El objeto [CharacterModel] con los nuevos datos del personaje.
         * @return Un objeto [ResponseEntity] que contiene un mensaje de éxito o error según el resultado de la operación.
         */
        @PutMapping("update-character/{id}")
        fun updateCharacter(
            @PathVariable("id") id: Long,
            @RequestBody character: CharacterModel,
        ): ResponseEntity<Any> =
            try {
                val updatedCharacter = characterService.updateCharacter(id, character)

                val message =
                    MessagesDTO().apply {
                        message = "Person updated with ID ${updatedCharacter.get().id} "
                        method = "PUT"
                        status = true
                    }
                ResponseEntity(message, HttpStatus.OK)
            } catch (e: IllegalArgumentException) {
                val badMessage =
                    MessagesDTO().apply {
                        message = "Invalid input: ${e.message}"
                        method = "PUT"
                        status = false
                    }
                ResponseEntity(badMessage, HttpStatus.BAD_REQUEST)
            } catch (e: Exception) {
                ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }

        /**
         * Endpoint para eliminar un personaje existente.
         *
         * Este método maneja las solicitudes HTTP DELETE a la ruta "/delete-character/{id}", donde se recibe el ID del personaje como
         * parámetro en la URL. Si el personaje se elimina correctamente, se devuelve un mensaje con el ID del personaje eliminado
         * y un código de estado HTTP 200 (OK). Si el personaje no existe, se devuelve un mensaje indicando que no se pudo eliminar
         * el personaje con el código de estado HTTP 400 (BAD_REQUEST). En caso de error, se devuelve un mensaje de error con un
         * código HTTP 400 (BAD_REQUEST) si los datos son inválidos, o un código HTTP 500 (INTERNAL_SERVER_ERROR) para errores
         * generales.
         *
         * @param id El ID del personaje que se va a eliminar.
         * @return Un objeto [ResponseEntity] que contiene un mensaje de éxito o error según el resultado de la operación.
         */
        @DeleteMapping("delete-character/{id}")
        fun deleteCharacter(
            @PathVariable("id") id: Long,
        ): ResponseEntity<Any> =
            try {
                val deleteCharacter = characterService.deleteCharacter(id)

                if (deleteCharacter) {
                    val message =
                        MessagesDTO().apply {
                            message = "Person deleted with ID $id "
                            method = "DELETE"
                            status = true
                        }
                    ResponseEntity(message, HttpStatus.OK)
                } else {
                    val badMessage =
                        MessagesDTO().apply {
                            message = "Person didnt delete with ID $id , because the person does not exist."
                            method = "DELETE"
                            status = false
                        }
                    ResponseEntity(badMessage, HttpStatus.BAD_REQUEST)
                }
            } catch (e: IllegalArgumentException) {
                val badMessage =
                    MessagesDTO().apply {
                        message = "Invalid input: ${e.message}"
                        method = "DELETE"
                        status = false
                    }
                ResponseEntity(badMessage, HttpStatus.BAD_REQUEST)
            } catch (e: Exception) {
                ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }
    }
